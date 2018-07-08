package vn.com.vhc.alarm.model;

public class Alarm2GModel {
	private String vendor;
	private String network;
	private String type;
	private String logRecordId;
	private String startDate;
	private String endDate;
	private String eventType;
	private String objectOfReference;
	private String perceivedSeverity;
	private String specificProblem;
	private String problemText;
	private String correlatedId; 
	private String bscId;
	private String btsId;
	private String severity;
	private String fmAlarmId;
	
	public String getBscId() {
		if(bscId == null) return "";
		return bscId;
	}
	public void setBscId(String bscId) {
		this.bscId = bscId;
	}
	public String getBtsId() {
		if(btsId == null) return "";
		return btsId;
	}
	public void setBtsId(String btsId) {
		this.btsId = btsId;
	}
	public String getVendor() {
		if(vendor == null) return "";
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getNetwork() {
		if(network == null) return "";
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getType() {
		if(type == null) return "";
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLogRecordId() {
		if(logRecordId == null) return "";
		return logRecordId;
	}
	public void setLogRecordId(String logRecordId) {
		this.logRecordId = logRecordId;
	}
	public String getStartDate() {
		if(startDate == null) return "";
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		if(endDate == null) return "";
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEventType() {
		if(eventType == null) return "";
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getObjectOfReference() {
		if(objectOfReference == null) return "";
		return objectOfReference;
	}
	public void setObjectOfReference(String objectOfReference) {
		this.objectOfReference = objectOfReference;
	}
	public String getPerceivedSeverity() {
		if(perceivedSeverity == null) return "";
		return perceivedSeverity;
	}
	public void setPerceivedSeverity(String perceivedSeverity) {
		this.perceivedSeverity = perceivedSeverity;
	}
	public String getSpecificProblem() {
		if(specificProblem == null) return "";
		return specificProblem;
	}
	public void setSpecificProblem(String specificProblem) {
		this.specificProblem = specificProblem;
	}
	public String getProblemText() {
		if(problemText == null) return "";
		return problemText;
	}
	public void setProblemText(String problemText) {
		this.problemText = problemText;
	}
	public String getCorrelatedId() {
		if(correlatedId == null) return "";
		return correlatedId;
	}
	public void setCorrelatedId(String correlatedId) {
		this.correlatedId = correlatedId;
	}
	public String getSeverity() {
		if(severity == null) return "";
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getFmAlarmId() {
		return fmAlarmId;
	}
	public void setFmAlarmId(String fmAlarmId) {
		this.fmAlarmId = fmAlarmId;
	}
	
	public String getNeType(){
		if(!getBtsId().equals("")){
			return "BTS";
		}else{
			return "BSC";
		} 
	}
}
