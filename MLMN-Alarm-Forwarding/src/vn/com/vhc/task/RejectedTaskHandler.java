// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.task;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle rejected task when queue overflows
 * @author datnh
 */
public class RejectedTaskHandler implements RejectedExecutionHandler {

	private static final Logger logger = LoggerFactory.getLogger(RejectedTaskHandler.class);
	
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		logger.error(runnable.toString() + " : I've been rejected !");
	}
}
