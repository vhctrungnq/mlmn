package vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class AlDyMblAblLog implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.DAY
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private Date day;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.BSCID
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
   
    private String bscid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.CELLID
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String cellid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.MO
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    @NotEmpty()
    private String mo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.ALARM_TYPE
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String alarmType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.VENDOR
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.DVT_TEAM_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String dvtTeamProcess;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.DVT_USER_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String dvtUserProcess;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.CAUSEBY
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String causeby;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.CAUSEBY_DVT
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String causebyDvt;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.ACTION_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String actionProcess;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.DESCRIPTION
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_DY_MBL_ABL_LOG.CREATE_DATE
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table AL_DY_MBL_ABL_LOG
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.DAY
     *
     * @return the value of AL_DY_MBL_ABL_LOG.DAY
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public Date getDay() {
        return day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.DAY
     *
     * @param day the value for AL_DY_MBL_ABL_LOG.DAY
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.BSCID
     *
     * @return the value of AL_DY_MBL_ABL_LOG.BSCID
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getBscid() {
        return bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.BSCID
     *
     * @param bscid the value for AL_DY_MBL_ABL_LOG.BSCID
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setBscid(String bscid) {
        this.bscid = bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.CELLID
     *
     * @return the value of AL_DY_MBL_ABL_LOG.CELLID
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getCellid() {
        return cellid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.CELLID
     *
     * @param cellid the value for AL_DY_MBL_ABL_LOG.CELLID
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.MO
     *
     * @return the value of AL_DY_MBL_ABL_LOG.MO
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getMo() {
        return mo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.MO
     *
     * @param mo the value for AL_DY_MBL_ABL_LOG.MO
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setMo(String mo) {
        this.mo = mo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.ALARM_TYPE
     *
     * @return the value of AL_DY_MBL_ABL_LOG.ALARM_TYPE
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getAlarmType() {
        return alarmType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.ALARM_TYPE
     *
     * @param alarmType the value for AL_DY_MBL_ABL_LOG.ALARM_TYPE
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.VENDOR
     *
     * @return the value of AL_DY_MBL_ABL_LOG.VENDOR
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.VENDOR
     *
     * @param vendor the value for AL_DY_MBL_ABL_LOG.VENDOR
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.DVT_TEAM_PROCESS
     *
     * @return the value of AL_DY_MBL_ABL_LOG.DVT_TEAM_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getDvtTeamProcess() {
        return dvtTeamProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.DVT_TEAM_PROCESS
     *
     * @param dvtTeamProcess the value for AL_DY_MBL_ABL_LOG.DVT_TEAM_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setDvtTeamProcess(String dvtTeamProcess) {
        this.dvtTeamProcess = dvtTeamProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.DVT_USER_PROCESS
     *
     * @return the value of AL_DY_MBL_ABL_LOG.DVT_USER_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getDvtUserProcess() {
        return dvtUserProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.DVT_USER_PROCESS
     *
     * @param dvtUserProcess the value for AL_DY_MBL_ABL_LOG.DVT_USER_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setDvtUserProcess(String dvtUserProcess) {
        this.dvtUserProcess = dvtUserProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.CAUSEBY
     *
     * @return the value of AL_DY_MBL_ABL_LOG.CAUSEBY
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getCauseby() {
        return causeby;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.CAUSEBY
     *
     * @param causeby the value for AL_DY_MBL_ABL_LOG.CAUSEBY
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setCauseby(String causeby) {
        this.causeby = causeby;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.CAUSEBY_DVT
     *
     * @return the value of AL_DY_MBL_ABL_LOG.CAUSEBY_DVT
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getCausebyDvt() {
        return causebyDvt;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.CAUSEBY_DVT
     *
     * @param causebyDvt the value for AL_DY_MBL_ABL_LOG.CAUSEBY_DVT
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setCausebyDvt(String causebyDvt) {
        this.causebyDvt = causebyDvt;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.ACTION_PROCESS
     *
     * @return the value of AL_DY_MBL_ABL_LOG.ACTION_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getActionProcess() {
        return actionProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.ACTION_PROCESS
     *
     * @param actionProcess the value for AL_DY_MBL_ABL_LOG.ACTION_PROCESS
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setActionProcess(String actionProcess) {
        this.actionProcess = actionProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.DESCRIPTION
     *
     * @return the value of AL_DY_MBL_ABL_LOG.DESCRIPTION
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.DESCRIPTION
     *
     * @param description the value for AL_DY_MBL_ABL_LOG.DESCRIPTION
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_DY_MBL_ABL_LOG.CREATE_DATE
     *
     * @return the value of AL_DY_MBL_ABL_LOG.CREATE_DATE
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_DY_MBL_ABL_LOG.CREATE_DATE
     *
     * @param createDate the value for AL_DY_MBL_ABL_LOG.CREATE_DATE
     *
     * @ibatorgenerated Wed Jan 16 11:10:41 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getTrx() {
		return trx;
	}

	public void setTrx(Integer trx) {
		this.trx = trx;
	}
	private Integer trx;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDayStr() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if(day != null)
			return df.format(day);
		else
			return null;
	}
	 public Integer getShiftId() {
		return shiftId;
	}

	public void setShiftId(Integer shiftId) {
		this.shiftId = shiftId;
	}
	private Integer shiftId;
	 
	
}