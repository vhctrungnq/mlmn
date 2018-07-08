// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.common;

/**
 * Application enviroment
 * @author datnh
 */
public class AppEnv {

	public static final String PORT = "server.port";
	public static final int DEFAULT_PORT = 5555;
	
	public static final String DATABASE_NO = "database.no";
	public static final int DEFAULT_DATABASE_NO = 1;
	
	public static final String TASK_POOL_SIZE = "task.poolSize";
	public static final int TASK_POOL_SIZE_DEFAULT_VALUE = 10000;
	public static final String TASK_RUNNING = "task.runningTask";
	public static final int TASK_RUNNING_DEFAULT_VALUE = 10;
	
	public static final String MESSAGE_DELIMITER = "conf.messageDelimiter";
	public static final String DEFAULT_MESSAGE_DELIMITER = "\n";
	
	public static final String QUEUE_MINUTE = "queue.minute";
	public static final int DEFAULT_QUEUE_MINUTE = 20;
}
