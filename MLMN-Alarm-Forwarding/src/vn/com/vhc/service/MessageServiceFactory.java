// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.service;

public class MessageServiceFactory {

	public static MessageService getMessageService() {
		return new MessageServiceImpl();
	}
}
