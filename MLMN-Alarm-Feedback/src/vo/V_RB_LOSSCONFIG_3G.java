package vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class V_RB_LOSSCONFIG_3G {

	    private Date sdate;

	    private Date edate;

	    private Date causeSdate;

	    private Date causeEdate;
	    
	    private Date ddhBaoMch;

	    @NotEmpty()
	    private String rncid;

	    @NotEmpty()
	    private String alarmLevel;

	    private String cellid;

	    private String alarmName;

	    private Integer fmAlarmId;

	    private Integer shiftId;

	    private Integer id;

	    private String causeby;

	    private String causebySy;

	    private String transType;

	    private String actionProcess;

	    private String description;

	    private String alarmClass;

	    private String alarmType;

	    private String alarmAlias;

	    private String dvtTeamProcess;

	    private String dvtUserProcess;

	    private Integer duration;
	    
	    private String statusView;

	    private static final long serialVersionUID = 1L;

	  
	    public Date getSdate() {
	        return sdate;
	    }

	    public void setSdate(Date sdate) {
	        this.sdate = sdate;
	    }

	    public Date getEdate() {
	        return edate;
	    }

	    public void setEdate(Date edate) {
	        this.edate = edate;
	    }

	    public String getRncid() {
	        return rncid;
	    }

	  
	    public void setRncid(String rncid) {
	        this.rncid = rncid;
	    }

	    public String getAlarmLevel() {
	        return alarmLevel;
	    }

	  
	    public void setAlarmLevel(String alarmLevel) {
	        this.alarmLevel = alarmLevel;
	    }

	    public String getCellid() {
	        return cellid;
	    }

	    public void setCellid(String cellid) {
	        this.cellid = cellid;
	    }

	    public String getAlarmName() {
	        return alarmName;
	    }

	    public void setAlarmName(String alarmName) {
	        this.alarmName = alarmName;
	    }

	    public Integer getFmAlarmId() {
	        return fmAlarmId;
	    }

	    public void setFmAlarmId(Integer fmAlarmId) {
	        this.fmAlarmId = fmAlarmId;
	    }

	    public Integer getShiftId() {
	        return shiftId;
	    }

	    public void setShiftId(Integer shiftId) {
	        this.shiftId = shiftId;
	    }

	    public Integer getId() {
	        return id;
	    }

	  
	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getCauseby() {
	        return causeby;
	    }

	   
	    public void setCauseby(String causeby) {
	        this.causeby = causeby;
	    }

	    public String getCausebySy() {
	        return causebySy;
	    }

	    public void setCausebySy(String causebySy) {
	        this.causebySy = causebySy;
	    }

	    public String getTransType() {
	        return transType;
	    }

	    public void setTransType(String transType) {
	        this.transType = transType;
	    }

	    public String getActionProcess() {
	        return actionProcess;
	    }

	    public void setActionProcess(String actionProcess) {
	        this.actionProcess = actionProcess;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getAlarmClass() {
	        return alarmClass;
	    }

	    public void setAlarmClass(String alarmClass) {
	        this.alarmClass = alarmClass;
	    }

	    public String getAlarmType() {
	        return alarmType;
	    }

	    public void setAlarmType(String alarmType) {
	        this.alarmType = alarmType;
	    }

	    public String getAlarmAlias() {
	        return alarmAlias;
	    }

	    public void setAlarmAlias(String alarmAlias) {
	        this.alarmAlias = alarmAlias;
	    }

	    public String getDvtTeamProcess() {
	        return dvtTeamProcess;
	    }

	    public void setDvtTeamProcess(String dvtTeamProcess) {
	        this.dvtTeamProcess = dvtTeamProcess;
	    }
	    public String getDvtUserProcess() {
	        return dvtUserProcess;
	    }
	    public void setDvtUserProcess(String dvtUserProcess) {
	        this.dvtUserProcess = dvtUserProcess;
	    }
	    public Integer getDuration() {
	        return duration;
	    }

	    public void setDuration(Integer duration) {
	        this.duration = duration;
	    }
	    public String getSdateStr()
	    {
	    	if (sdate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(sdate));
	    	return "";
	    }
	    public String getEdateStr()
	    {
	    	if (edate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(edate));
	    		
	    	return "";
	    }

		public Date getCauseSdate() {
			return causeSdate;
		}

		public void setCauseSdate(Date causeSdate) {
			this.causeSdate = causeSdate;
		}

		public Date getCauseEdate() {
			return causeEdate;
		}

		public void setCauseEdate(Date causeEdate) {
			this.causeEdate = causeEdate;
		}

		public Date getDdhBaoMch() {
			return ddhBaoMch;
		}

		public void setDdhBaoMch(Date ddhBaoMch) {
			this.ddhBaoMch = ddhBaoMch;
		}
		 public String getCauseSdateStr()
	    {
	    	if (causeSdate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(causeSdate));
	    	return "";
	    }
	    public String getCauseEdateStr()
	    {
	    	if (causeEdate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(causeEdate));
	    		
	    	return "";
	    }
	    public String getDdhBaoMchStr()
	    {
	    	if (ddhBaoMch!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ddhBaoMch));
	    		
	    	return "";
	    }

		public String getStatusView() {
			return statusView;
		}

		public void setStatusView(String statusView) {
			this.statusView = statusView;
		}
	}
