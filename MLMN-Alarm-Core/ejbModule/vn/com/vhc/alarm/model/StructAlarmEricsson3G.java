package vn.com.vhc.alarm.model;

/**
 * @author galaxy
 * @createDate 11:48:26 AM
 * StructAlarmEricsson3G.java
 *
 */
public class StructAlarmEricsson3G {
	public String type;
	public String vendor;
	public String network;
	public String object_of_reperence;
	public String rncid;
	public String alarm_level;
	public String site;
	public String cellid;
	public String sector;
	public String anten_error;
	public String log_record_id;
	public String sdate;
	public String edate;
	public String event_type;
	public String object_class;
	public String alarm_class;
	public String perceived_severity;
	public String probable_cause;
	public String alarm_name;
	public String problem_text;
	public String problem_data;
	public String correlated_id;
	public String previous_severity;
	public String record_type;
	public String object_type;
	public String logging_time;
	public String repeat_count;
	public String fm_alarm_id;
	public String ne_type; 
	
	@Override
    public String toString() {
    	return 
    				(type					==null?"":type)
			+ ";" + (vendor					==null?"":vendor)
			+ ";" + (network				==null?"":network)
			+ ";" + (object_of_reperence 	==null?"":object_of_reperence)
			+ ";" + (rncid					==null?"":rncid) 
			+ ";" + (site					==null?"":site)
			+ ";" + (cellid					==null?"":cellid)
			+ ";" + (sector					==null?"":sector)
			+ ";" + (anten_error			==null?"":anten_error) 
			+ ";" + (sdate					==null?"":sdate)
			+ ";" + (edate					==null?"":edate)
			+ ";" + (event_type				==null?"":event_type) 
			+ ";" + (alarm_class			==null?"":alarm_class)  
			+ ";" + (probable_cause			==null?"":probable_cause)  
			+ ";" + (alarm_name				==null?"":alarm_name)
			+ ";" + (problem_text			==null?"":problem_text.trim())  
			+ ";" + (fm_alarm_id			==null?"":fm_alarm_id)
			+ ";" + (ne_type				==null?"":ne_type);
    }
}
