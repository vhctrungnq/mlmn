package vn.com.vhc.alarm.model;

/**
 * @author galaxy
 * @createDate 1:22:05 PM
 * AlarmZteModel.java
 *
 */
public class AlarmZteModel { 
	private String vendor;
	private String network;
	private String perceivedSeverity;
	private String alarmType;
	private String location;
	private String ne;
	private String alarmCode;
	private String specificProblem;
	private String raisedTime;
	private String neType;
	private String alarmId;
	private String clearedTime;
	private String ackState;
	private String severity;
	private String site;
	
	public String getAlarmLine(){
		return vendor+";"+network+";"+perceivedSeverity+";"+alarmType+";"+location+";"+ne+";"+alarmCode+";"+specificProblem+";"+raisedTime+";"+neType+";"+alarmId+";"+clearedTime+";"+ackState+";"+severity+";"+site;
	}
	/**
	 * @return the ackState
	 */
	public String getAckState() {
		return ackState;
	}
	/**
	 * @param ackState the ackState to set
	 */
	public void setAckState(String ackState) {
		this.ackState = ackState;
	}
	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return the vendor
	 */
	public String getVendor() {
		if(vendor == null) return "";
		return vendor;
	}
	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	/**
	 * @return the network
	 */
	public String getNetwork() {
		if (network == null) return "";
		return network;
	}
	/**
	 * @param network the network to set
	 */
	public void setNetwork(String network) {
		this.network = network;
	}
	/**
	 * @return the perceivedSeverity
	 */
	public String getPerceivedSeverity() {
		if(perceivedSeverity == null) return "";
		return perceivedSeverity;
	}
	/**
	 * @param perceivedSeverity the perceivedSeverity to set
	 */
	public void setPerceivedSeverity(String perceivedSeverity) {
		this.perceivedSeverity = perceivedSeverity;
	}
	/**
	 * @return the alarmType
	 */
	public String getAlarmType() {
		if(alarmType==null) return "";
		return alarmType;
	}
	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		if(location==null) return "";
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the ne
	 */
	public String getNe() {
		if(ne==null) return "";
		return ne;
	}
	/**
	 * @param ne the ne to set
	 */
	public void setNe(String ne) {
		this.ne = ne;
	}
	/**
	 * @return the alarmCode
	 */
	public String getAlarmCode() {
		if(alarmCode==null) return "";
		return alarmCode;
	}
	/**
	 * @param alarmCode the alarmCode to set
	 */
	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode;
	}
	/**
	 * @return the specificProblem
	 */
	public String getSpecificProblem() {
		if(specificProblem==null) return "";
		return specificProblem;
	}
	/**
	 * @param specificProblem the specificProblem to set
	 */
	public void setSpecificProblem(String specificProblem) {
		this.specificProblem = specificProblem;
	}
	/**
	 * @return the raisedTime
	 */
	public String getRaisedTime() {
		if(raisedTime==null) return "";
		return raisedTime;
	}
	/**
	 * @param raisedTime the raisedTime to set
	 */
	public void setRaisedTime(String raisedTime) {
		this.raisedTime = raisedTime;
	}
	/**
	 * @return the neType
	 */
	public String getNeType() {
		if(neType==null) return "";
		return neType;
	}
	/**
	 * @param neType the neType to set
	 */
	public void setNeType(String neType) {
		this.neType = neType;
	}
	/**
	 * @return the alarmId
	 */
	public String getAlarmId() {
		if(alarmId==null) return "";
		return alarmId;
	}
	/**
	 * @param alarmId the alarmId to set
	 */
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	/**
	 * @return the clearedTime
	 */
	public String getClearedTime() {
		if(clearedTime==null) return "";
		return clearedTime;
	}
	/**
	 * @param clearedTime the clearedTime to set
	 */
	public void setClearedTime(String clearedTime) {
		this.clearedTime = clearedTime;
	}
	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

}
