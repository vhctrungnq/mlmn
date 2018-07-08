// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.manager;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.model.Message;
import vn.com.vhc.task.TaskManager;

/**
 * Message handler
 * @author datnh
 */
public class MessageHandler extends SimpleChannelHandler {

	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel ch = e.getChannel();
		
		logger.info("[INFO] Connect from {}", ch.getLocalAddress());
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel ch = e.getChannel();

		logger.info("[INFO] Disconnect from {}", ch.getLocalAddress());
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Message message = (Message) e.getMessage();
		
		if (message != null)
			TaskManager.execute(message);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.error("Channel Exception", e.getCause());

		Channel ch = e.getChannel();
		ch.close();
		logger.info("[INFO] Channel {} is closed", ch.getId());
	}
}
