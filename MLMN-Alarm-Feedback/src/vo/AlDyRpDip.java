package vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class AlDyRpDip implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.ID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.DAY
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private Date day;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.BSCID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    @NotEmpty()
    private String bscid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.DIP
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    @NotEmpty()
    private String dip;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.ITEMS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private Integer items;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.TOTAL_TIME
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private Integer totalTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.ALARM_TYPE
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private String alarmType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.CAUSEBY_CONTENT
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private String causebyContent;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.RESULT_CONTENT
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private String resultContent;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.TEAM_PROCESS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private String teamProcess;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.USER_PROCESS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private String userProcess;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.SHIFT_ID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private Integer shiftId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.SITE
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */

    private String site;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_RP_DIP.ALARM
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private String alarm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table AL_DY_RP_DIP
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.ID
     *
     * @return the value of AL_DY_RP_DIP.ID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.ID
     *
     * @param id the value for AL_DY_RP_DIP.ID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.DAY
     *
     * @return the value of AL_DY_RP_DIP.DAY
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public Date getDay() {
        return day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.DAY
     *
     * @param day the value for AL_DY_RP_DIP.DAY
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.BSCID
     *
     * @return the value of AL_DY_RP_DIP.BSCID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getBscid() {
        return bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.BSCID
     *
     * @param bscid the value for AL_DY_RP_DIP.BSCID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setBscid(String bscid) {
        this.bscid = bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.DIP
     *
     * @return the value of AL_DY_RP_DIP.DIP
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getDip() {
        return dip;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.DIP
     *
     * @param dip the value for AL_DY_RP_DIP.DIP
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setDip(String dip) {
        this.dip = dip;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.ITEMS
     *
     * @return the value of AL_DY_RP_DIP.ITEMS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public Integer getItems() {
        return items;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.ITEMS
     *
     * @param items the value for AL_DY_RP_DIP.ITEMS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setItems(Integer items) {
        this.items = items;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.TOTAL_TIME
     *
     * @return the value of AL_DY_RP_DIP.TOTAL_TIME
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public Integer getTotalTime() {
        return totalTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.TOTAL_TIME
     *
     * @param totalTime the value for AL_DY_RP_DIP.TOTAL_TIME
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.ALARM_TYPE
     *
     * @return the value of AL_DY_RP_DIP.ALARM_TYPE
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getAlarmType() {
        return alarmType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.ALARM_TYPE
     *
     * @param alarmType the value for AL_DY_RP_DIP.ALARM_TYPE
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.CAUSEBY_CONTENT
     *
     * @return the value of AL_DY_RP_DIP.CAUSEBY_CONTENT
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getCausebyContent() {
        return causebyContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.CAUSEBY_CONTENT
     *
     * @param causebyContent the value for AL_DY_RP_DIP.CAUSEBY_CONTENT
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setCausebyContent(String causebyContent) {
        this.causebyContent = causebyContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.RESULT_CONTENT
     *
     * @return the value of AL_DY_RP_DIP.RESULT_CONTENT
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getResultContent() {
        return resultContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.RESULT_CONTENT
     *
     * @param resultContent the value for AL_DY_RP_DIP.RESULT_CONTENT
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.TEAM_PROCESS
     *
     * @return the value of AL_DY_RP_DIP.TEAM_PROCESS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getTeamProcess() {
        return teamProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.TEAM_PROCESS
     *
     * @param teamProcess the value for AL_DY_RP_DIP.TEAM_PROCESS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setTeamProcess(String teamProcess) {
        this.teamProcess = teamProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.USER_PROCESS
     *
     * @return the value of AL_DY_RP_DIP.USER_PROCESS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getUserProcess() {
        return userProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.USER_PROCESS
     *
     * @param userProcess the value for AL_DY_RP_DIP.USER_PROCESS
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setUserProcess(String userProcess) {
        this.userProcess = userProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.SHIFT_ID
     *
     * @return the value of AL_DY_RP_DIP.SHIFT_ID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public Integer getShiftId() {
        return shiftId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.SHIFT_ID
     *
     * @param shiftId the value for AL_DY_RP_DIP.SHIFT_ID
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.SITE
     *
     * @return the value of AL_DY_RP_DIP.SITE
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getSite() {
        return site;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.SITE
     *
     * @param site the value for AL_DY_RP_DIP.SITE
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_RP_DIP.ALARM
     *
     * @return the value of AL_DY_RP_DIP.ALARM
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public String getAlarm() {
        return alarm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_RP_DIP.ALARM
     *
     * @param alarm the value for AL_DY_RP_DIP.ALARM
     *
     * @ibatorgenerated Thu Dec 06 15:39:30 ICT 2012
     */
    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
    public String getDayStr() {
		if (day!=null)
    		return (new SimpleDateFormat("dd/MM/yyyy").format(day));
    	return null;
	}
    
    public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	private String transType;
}