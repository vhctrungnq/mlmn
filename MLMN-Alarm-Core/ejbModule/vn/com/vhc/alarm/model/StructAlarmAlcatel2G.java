package vn.com.vhc.alarm.model;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) by VHCSoft 2016
 * </p>
 * <p>
 * Company: VietNam High Technology Software JSC.
 * </p>
 * <p>
 * Create Date:Jan 13, 2017
 * </p>
 * 
 * @author VHC - Software
 * @version 1.0
 */

public class StructAlarmAlcatel2G {
	public String atype;
	public String evttime;
	public String vendor;
	public String network;
	public String netype;
	public String ne;
	public String site;
	public String cell;
	public String frdnameInfo;
	public String severity;
	public String evttype;
	public String pbcause;
	public String specpb;
	public String clrtime;
	public String repcount;
	public String fmAlarmId;
	public String alarmName;
	
	public String getHeader(){
		return "#vendor;atype;evttime;netype;ne;site;cell;frdnameInfo;severity;evttype;pbcause;alarmName;clrtime;network";
	}
	
	@Override
    public String toString() {
    	return 
    			(vendor			==null?"":vendor)
    		+ ";" +(atype			==null?"":atype)
			+ ";" + (evttime		==null?"":evttime) 
			+ ";" + (netype			==null?"":netype)
			+ ";" + (ne				==null?"":ne) 
			+ ";" + (site			==null?"":site)
			+ ";" + (cell			==null?"":cell)
			+ ";" + (frdnameInfo	==null?"":frdnameInfo)
			+ ";" + (severity		==null?"":severity) 
			+ ";" + (evttype		==null?"":evttype) 
			+ ";" + (pbcause		==null?"":pbcause)
			+ ";" + (alarmName	    ==null?"":alarmName)
			+ ";" + (clrtime		==null?"":clrtime)
			+ ";" + (network		==null?"":network) ;
    }

}

