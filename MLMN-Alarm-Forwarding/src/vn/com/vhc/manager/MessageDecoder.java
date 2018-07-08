// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.manager;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.common.AppConfig;
import vn.com.vhc.common.AppEnv;
import vn.com.vhc.exception.AppConfigException;
import vn.com.vhc.model.Message;
import vn.com.vhc.util.StringUtil;
import vn.com.vhc.common.ChannelBufferHelper;

/**
 * Decoder message
 * 
 * @author datnh
 *
 */
public class MessageDecoder extends FrameDecoder {

	private static final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
	
	private ChannelBuffer cbMessageDelimiter;
	private String stMessageDelimiter;
	
	protected static String alarmMessage = "";
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		
		Message message = null;
		buffer.markReaderIndex();
		ChannelBuffer rawMessage = buffer.copy();
		
		try {
			// load config
			loadConfig();
			
			// read a frame
			ChannelBuffer msgData = readFrame(buffer, cbMessageDelimiter);
			
			//String msg = StringUtil.removeSpaceCharacter(new String(msgData.array()));
			String msg = new String(msgData.array());
			logger.debug(msg);
			
			msg = StringUtil.removeSpaceCharacter(msg).trim();
			
			// skip
			if (alarmMessage.equals("") && !msg.contains("#E##S#"))
				return null;
			
			// execute block alarm message
			if (msg.contains("#E##S#") && !alarmMessage.equals("")) {
				message = new Message();
				message.setData(alarmMessage);
				message.setDelimiter(stMessageDelimiter);
					
				alarmMessage = "";
			}
			
			if (!alarmMessage.equals("")) {
				alarmMessage = alarmMessage.concat(stMessageDelimiter);
			}
			
			alarmMessage = alarmMessage.concat(msg);
		} catch(NegativeArraySizeException e) {
			message = null;
		} catch (Exception e) {
			logger.error("Error when decode message {}", new String(rawMessage.array()), e);
			message = null;
		}
		
		/*catch (Exception e) {
			logger.error("Error when decode message {}", new String(rawMessage.array()), e);
			message = null;
		}*/
		
		return message;
	}
	
	/**
	 * Load application configuration.
	 * 
	 * @throws AppConfigException
	 */
	private void loadConfig() throws AppConfigException {
		
		if (stMessageDelimiter == null) {
			PropertiesConfiguration config = AppConfig.getPropertiesConfig();
			stMessageDelimiter = config.getString(AppEnv.MESSAGE_DELIMITER, AppEnv.DEFAULT_MESSAGE_DELIMITER);
			cbMessageDelimiter = ChannelBufferHelper.fromString(stMessageDelimiter);
		}
	}
	
	/**
	 * Read frame by delimiter, move pointer to the next bytes.
	 * 
	 * @param buffer
	 * @param delimiter
	 * @return
	 */
	private ChannelBuffer readFrame(ChannelBuffer buffer, ChannelBuffer delimiter) {
		int frameLength = ChannelBufferHelper.indexOf(buffer, delimiter);
		ChannelBuffer frame = extractFrame(buffer, buffer.readerIndex(), frameLength);
		buffer.skipBytes(frameLength + delimiter.capacity());

		return frame;
	}

}
