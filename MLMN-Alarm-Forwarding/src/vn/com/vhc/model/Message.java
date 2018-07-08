// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.model;

import java.io.Serializable;

/**
 * Alarm mesage
 * 
 * @author datnh
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8580157384002178441L;
	
	/**
	 * Data of message
	 */
	private String data;
	
	/**
	 * Delimiter message
	 */
	private String delimiter;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public String toString() {
		return "Message [" + new String(data) + "]";
	}
}
