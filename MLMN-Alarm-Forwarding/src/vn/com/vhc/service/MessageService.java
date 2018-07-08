// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.service;

import vn.com.vhc.model.Message;

/**
 * Message service interface
 * 
 * @author datnh
 *
 */
public interface MessageService {

	/**
	 * Process message
	 * @param message
	 */
	void processMessage(Message message);
}
