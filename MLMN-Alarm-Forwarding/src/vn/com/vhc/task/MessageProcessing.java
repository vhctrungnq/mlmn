// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.task;

import vn.com.vhc.model.Message;
import vn.com.vhc.service.MessageService;
import vn.com.vhc.service.MessageServiceFactory;

/**
 * Message processing
 * @author datnh
 */
public class MessageProcessing implements Runnable {
	
	private Message message;
	
	public MessageProcessing(Message message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		//logger.info("Processing message {}", message);
		
		MessageService messageService = MessageServiceFactory.getMessageService();
		
		messageService.processMessage(message);
		
		//logger.info("Processing message {}.. done!", message);
	}

}
