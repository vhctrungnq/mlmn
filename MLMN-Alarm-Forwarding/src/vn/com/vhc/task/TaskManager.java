// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.common.AppEnv;
import vn.com.vhc.common.AppConfig;
import vn.com.vhc.exception.AppConfigException;
import vn.com.vhc.model.Message;
import vn.com.vhc.task.RejectedTaskHandler;
import vn.com.vhc.task.MessageProcessing;

/**
 * Task manager
 * @author datnh
 */
public class TaskManager {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);
	
	private static BlockingQueue<Runnable> worksQueue = null;
	private static RejectedExecutionHandler executionHandler = new RejectedTaskHandler();
	private static ThreadPoolExecutor executor = null;
	
	static {
		try {
			PropertiesConfiguration config = AppConfig.getPropertiesConfig();
			
			int poolSize 	= config.getInt(AppEnv.TASK_POOL_SIZE, AppEnv.TASK_POOL_SIZE_DEFAULT_VALUE);
			int runningTask = config.getInt(AppEnv.TASK_RUNNING, AppEnv.TASK_RUNNING_DEFAULT_VALUE);
			
			worksQueue = new ArrayBlockingQueue<Runnable>(poolSize);
			executor = new ThreadPoolExecutor(runningTask, runningTask * 2, 10, TimeUnit.SECONDS, worksQueue, executionHandler);
			
			executor.allowCoreThreadTimeOut(true);
		} catch (AppConfigException ex) {
			logger.error("Error init app config: " + ex.toString());
		}
	}
	
	/**
	 * Execute message
	 * 
	 * @param message
	 */
	public static void execute(Message message) {
		
		MessageProcessing messageProcessing = new MessageProcessing(message);
		executor.execute(messageProcessing);
	}

}
