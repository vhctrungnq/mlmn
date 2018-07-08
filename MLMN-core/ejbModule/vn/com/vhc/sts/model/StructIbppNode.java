// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.sts.model;

import java.io.Serializable;

/**
 * Struct ipbb Node doi tac
 * 
 * @author anhnt
 *
 */
public class StructIbppNode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3987278849812288854L;
	
	public String id;
	public String dateTime;
	public String dateTimeRaw;
	public String traffTotalVol;
	public String traffTotalVolRaw;
	public String traffTotalSpeed;
	public String traffTotalSpeedRaw;
	public String traffInVol;
	public String traffInVolRaw;
	public String traffInSpeed;
	public String traffInSpeedRaw;
	public String traffOutVol;
	public String traffOutVolRaw;
	public String traffOutSpeed;
	public String traffOutSpeedRaw;
	public String coverage;
	public String coverageRaw;
    
    @Override
    public String toString() {
    	return 
    				(id		==null?"":id)
			+ ";" + (dateTime		==null?"":dateTime)
			+ ";" + (dateTimeRaw			==null?"":dateTimeRaw)
			+ ";" + (traffTotalVol			==null?"":traffTotalVol)
			+ ";" + (traffTotalVolRaw		==null?"":traffTotalVolRaw)
			+ ";" + (traffTotalSpeed		==null?"":traffTotalSpeed)
			+ ";" + (traffTotalSpeedRaw		==null?"":traffTotalSpeedRaw)
			+ ";" + (traffInVol				==null?"":traffInVol)
			+ ";" + (traffInVolRaw			==null?"":traffInVolRaw)
			+ ";" + (traffInSpeed			==null?"":traffInSpeed)
			+ ";" + (traffInSpeedRaw		==null?"":traffInSpeedRaw)
			+ ";" + (traffOutVol			==null?"":traffOutVol)
			+ ";" + (traffOutVolRaw			==null?"":traffOutVolRaw)
			+ ";" + (traffOutSpeed			==null?"":traffOutSpeed)
			+ ";" + (traffOutSpeedRaw		==null?"":traffOutSpeedRaw)
			+ ";" + (coverage				==null?"":coverage)
			+ ";" + (coverageRaw			==null?"":coverageRaw);
    }
}
