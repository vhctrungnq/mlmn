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
 * Struct alarm
 * 
 * @author datnh
 *
 */
public class StructAlarm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3987278849812288854L;
	
	public String networkType;
	public String cellid;
    public String alarmType;
    public String alarmClass;
    public String alarmName;
    public String site;
    public String bscId;
    public String alarmInfo;
    public String alarmNumber;
    public String alarmId;
    public String alarmStatus;
    public String intId;
    public String notifId;
    public String userInfo;
    public String node;
    public String sDate;
    public String eDate;
    public String objectName;
    public String alarmMappingId;
    public String alarmMappingName;
    //public String info;
    public String isMonitor;
    public String isSendSms;
    
    @Override
    public String toString() {
    	return 
    				(alarmStatus		==null?"":alarmStatus)
			+ "," + (networkType		==null?"":networkType)
			+ "," + (cellid				==null?"":cellid)
			+ "," + (alarmType			==null?"":alarmType)
			+ "," + (alarmClass			==null?"":alarmClass)
			+ "," + (alarmName			==null?"":alarmName)
			+ "," + (site				==null?"":site)
			+ "," + (bscId				==null?"":bscId)
			+ "," + (alarmInfo			==null?"":alarmInfo)
			+ "," + (alarmNumber		==null?"":alarmNumber)
			+ "," + (alarmId			==null?"":alarmId)
			+ "," + (intId				==null?"":intId)
			+ "," + (notifId			==null?"":notifId)
			+ "," + (userInfo			==null?"":userInfo)
			+ "," + (node				==null?"":node)
			+ "," + (sDate				==null?"":sDate)
			+ "," + (eDate				==null?"":eDate)
			+ "," + (objectName			==null?"":objectName)
			+ "," + (alarmMappingId		==null?"":alarmMappingId)
			+ "," + (alarmMappingName	==null?"":alarmMappingName)
			+ "," + (isMonitor			==null?"":isMonitor)
			+ "," + (isSendSms			==null?"":isSendSms);
    }
}
