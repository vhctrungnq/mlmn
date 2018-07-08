// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.model;

/**
 * Alarm config type
 * 
 * @author datnh
 *
 */
public class AlarmType {

	private String blockColumnName;
	private String blockValue;
	private String alarmType;
	private String alarmMappingName;
	private String alarmMappingId;
	private String isMonitor;
	private String isSendSms;
	
	public AlarmType(String blockColumnName, String blockValue, String alarmType, String alarmMappingName, 
			String alarmMappingId, String isMonitor, String isSendSms) {
		this.blockColumnName = blockColumnName;
		this.blockValue = blockValue;
		this.alarmType = alarmType;
		this.alarmMappingName = alarmMappingName;
		this.alarmMappingId = alarmMappingId;
		this.isMonitor = isMonitor;
		this.isSendSms = isSendSms;
	}
	
	public String getBlockColumnName() {
		return blockColumnName;
	}
	public void setBlockColumnName(String blockColumnName) {
		this.blockColumnName = blockColumnName;
	}
	public String getBlockValue() {
		return blockValue;
	}

	public void setBlockValue(String blockValue) {
		this.blockValue = blockValue;
	}

	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmMappingName() {
		return alarmMappingName;
	}
	public void setAlarmMappingName(String alarmMappingName) {
		this.alarmMappingName = alarmMappingName;
	}
	public String getAlarmMappingId() {
		return alarmMappingId;
	}
	public void setAlarmMappingId(String alarmMappingId) {
		this.alarmMappingId = alarmMappingId;
	}

	/**
	 * @return the isMonitor
	 */
	public String getIsMonitor() {
		return isMonitor;
	}

	/**
	 * @param isMonitor the isMonitor to set
	 */
	public void setIsMonitor(String isMonitor) {
		this.isMonitor = isMonitor;
	}

	/**
	 * @return the isSendSms
	 */
	public String getIsSendSms() {
		return isSendSms;
	}

	/**
	 * @param isSendSms the isSendSms to set
	 */
	public void setIsSendSms(String isSendSms) {
		this.isSendSms = isSendSms;
	}
}
