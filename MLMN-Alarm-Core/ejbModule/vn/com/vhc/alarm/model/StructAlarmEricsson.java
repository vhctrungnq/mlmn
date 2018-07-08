package vn.com.vhc.alarm.model;

/**
 * @author galaxy
 * @createDate 11:48:26 AM
 * StructAlarmEricsson3G.java
 *
 */
public class StructAlarmEricsson {
	public String type;
	public String vendor;
	public String network;
	public String object_of_reperence;
	public String mscid;
	public String site;
	public String bc; 
	public String log_record_id;
	public String sdate;
	public String edate;
	public String event_type; 
	public String alarm_class;
	public String perceived_severity;
	public String probable_cause;
	public String alarm_name;
	public String problem_text; 
	public String fm_alarm_id;
	public String ne_type; 
	public String repeat_count;
	public String correlated_id;
	public String cell;
	
	@Override
    public String toString() {
    	return 
    				(type					==null?"":type)
			+ ";" + (vendor					==null?"":vendor)
			+ ";" + (network				==null?"":network)
			+ ";" + (object_of_reperence 	==null?"":object_of_reperence)
			+ ";" + (mscid					==null?"":mscid)
			+ ";" + (site					==null?"":site)
			+ ";" + (cell						==null?"":cell)
			/*+ ";" + (log_record_id			==null?"":log_record_id)*/
			+ ";" + (sdate					==null?"":sdate)
			+ ";" + (edate					==null?"":edate)
			+ ";" + (event_type				==null?"":event_type) 
			+ ";" + (alarm_class			==null?"":alarm_class)
			/*+ ";" + (perceived_severity		==null?"":perceived_severity)*/
			+ ";" + (probable_cause			==null?"":probable_cause)
			+ ";" + (alarm_name				==null?"":alarm_name)
			+ ";" + (problem_text			==null?"":problem_text.trim())
			+ ";" + (fm_alarm_id			==null?"":fm_alarm_id)
			+ ";" + (ne_type				==null?"":ne_type);
    }
}
