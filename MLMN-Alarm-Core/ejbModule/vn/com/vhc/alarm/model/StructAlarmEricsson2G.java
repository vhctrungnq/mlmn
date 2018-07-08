package vn.com.vhc.alarm.model;

public class StructAlarmEricsson2G {
	public String vendor;
	public String network;
	public String type;
	public String logRecordId;
	public String startDate;
	public String endDate;
	public String eventType;
	public String objectOfReference;
	public String perceivedSeverity;
	public String specificProblem;
	public String problemText;
	public String correlatedId; 
	public String bscId;
	public String btsId;
	public String severity;
	public String fmAlarmId;
	public String neType;
	public String cell;
	public String mo;
	public String dip; 
	public String repeatCount;
	
	@Override
    public String toString() {
    	return 
    				(type				==null?"":type)
			+ ";" + (vendor				==null?"":vendor)
			+ ";" + (network			==null?"":network)
			+ ";" + (neType				==null?"":neType)
			+ ";" + (bscId				==null?"":bscId)
			+ ";" + (btsId				==null?"":btsId)
			+ ";" + (cell				==null?"":cell)
			+ ";" + (mo					==null?"":mo)
			+ ";" + (dip				==null?"":dip) 
			+ ";" + (startDate 			==null?"":startDate)
			+ ";" + (endDate			==null?"":endDate)
			+ ";" + (eventType			==null?"":eventType)
			+ ";" + (objectOfReference			==null?"":objectOfReference)
			+ ";" + (specificProblem			==null?"":specificProblem)
			+ ";" + (problemText				==null?"":problemText) 
			+ ";" + (severity					==null?"":severity)
			+ ";" + (fmAlarmId					==null?"":fmAlarmId);
    }
}
