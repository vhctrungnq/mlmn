// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.common.AppConfig;
import vn.com.vhc.model.StructAlarm;

/**
 * Message queue
 * @author datnh
 */
public class QueueUtil {

	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private static final Logger logger = LoggerFactory.getLogger(QueueUtil.class);
	
	static {
	    long initialDelay = 0;
		long period = 60*1000;
		
		scheduler.scheduleAtFixedRate(new MessageQueue(), initialDelay, period, TimeUnit.MILLISECONDS);
		
		logger.info("[INFO] Config QueueUtil successful");
	}
	
	public static void init() {}
}


/**
 * Message runable
 * @author datnh
 *
 */
class MessageQueue implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageQueue.class);
	
	@Override
	public void run() {
		logger.warn("QUEUE");
		
		Calendar cal = Calendar.getInstance();
	    //cal.add(Calendar.HOUR_OF_DAY, -1);
		cal.add(Calendar.MINUTE, -1);
	    
	    BufferedReader br = null;
	    int rowQueue = 0;
	    String fileName = null;
	    
	    try {
			fileName = AppConfig.getLocation("log") + "/queue/alarm-queue.log." + (new SimpleDateFormat("yyyyMMdd.HHmm")).format(cal.getTime());
			
			br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(fileName))));
			
			StructAlarm structAlarm;
			String[] msg;
			
			String db = "";
			String strLine = "";
			while ((strLine = br.readLine()) != null) {
				
				rowQueue ++;
				
				db = strLine.substring(14,15);		// database
				strLine = strLine.substring(16);	// alarm content
				
				structAlarm = new StructAlarm();
				
				msg = StringUtil.parseString(strLine, ',');
				try {
					structAlarm.alarmStatus 	= msg[0];
					structAlarm.networkType 	= msg[1];
					structAlarm.cellid 			= msg[2];
					structAlarm.alarmType 		= msg[3];
					structAlarm.alarmClass 		= msg[4];
					structAlarm.alarmName 		= msg[5];
					structAlarm.site 			= msg[6];
					structAlarm.bscId 			= msg[7];
					structAlarm.alarmInfo 		= msg[8];
					structAlarm.alarmNumber 	= msg[9];
					structAlarm.alarmId 		= msg[10];
					
					structAlarm.intId 			= msg[11];
					structAlarm.notifId 		= msg[12];
					structAlarm.userInfo 		= msg[13];
					structAlarm.node 			= msg[14];
					structAlarm.sDate 			= msg[15];
					structAlarm.eDate 			= msg[16];
					structAlarm.objectName 		= msg[17];
					structAlarm.alarmMappingId 	= msg[18];
					structAlarm.alarmMappingName = msg[19];
					structAlarm.isMonitor 		= msg[20];
					structAlarm.isSendSms 		= msg[21];
					
					DbUtil.insertAlarm(Integer.parseInt(db), structAlarm);
				} catch (Exception e) {
					logger.error("Queue.message({}) error", structAlarm, e);		// error
				}
			}
			structAlarm = null;
			
		} catch (Exception e) {
			logger.error("MessageQueue error", e);
		} finally {
			try {
				br.close();
				br = null;
				
				// Delete empty file
				if (rowQueue <= 2) {
					FileUtil.deleteFile(fileName);
				}
			} catch (Exception e) {
				logger.error("MessageQueue: BufferedReader error", e);
			}
		}
	    
	    logger.warn("[INFO] Execute queue alarm log; date=" + new Date() + "; rowQueue="+rowQueue);
	}
}