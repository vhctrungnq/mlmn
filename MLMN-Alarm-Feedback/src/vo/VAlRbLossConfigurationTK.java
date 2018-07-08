package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VAlRbLossConfigurationTK {
	 private Integer id;
	 private Integer alarmId;
	 private String bscid;
	 private String cellid;
	 private Date sdate;
	 private Date edate;

	 private Date ddhBaoMd;
	 private Date mchSdate;
	 private Date mchEdate;
	 private Date ddhBaoMch;
	 private String causebySy;
	 private String dvtTeamProcess;

	 private String dvtUserProcess;

	 private String ungCuuMpd;

	 private String description;
	 private Integer shiftId;
	 
	 private String statusView;

	     public Integer getId() {
	        return id;
	    }
	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public Integer getAlarmId() {
	        return alarmId;
	    }

	     public void setAlarmId(Integer alarmId) {
	        this.alarmId = alarmId;
	    }

	    public String getBscid() {
	        return bscid;
	    }

	    public void setBscid(String bscid) {
	        this.bscid = bscid;
	    }

	   public String getCellid() {
	        return cellid;
	    }

	     public void setCellid(String cellid) {
	        this.cellid = cellid;
	    }

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
	    public Date getDdhBaoMd() {
	        return ddhBaoMd;
	    }

	   public void setDdhBaoMd(Date ddhBaoMd) {
	        this.ddhBaoMd = ddhBaoMd;
	    }

	    public Date getMchSdate() {
	        return mchSdate;
	    }

	    public void setMchSdate(Date mchSdate) {
	        this.mchSdate = mchSdate;
	    }

	   public Date getMchEdate() {
	        return mchEdate;
	    }

	   public void setMchEdate(Date mchEdate) {
	        this.mchEdate = mchEdate;
	    }

	     public Date getDdhBaoMch() {
	        return ddhBaoMch;
	    }

	   public void setDdhBaoMch(Date ddhBaoMch) {
	        this.ddhBaoMch = ddhBaoMch;
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

	    public String getUngCuuMpd() {
	        return ungCuuMpd;
	    }
	    public void setUngCuuMpd(String ungCuuMpd) {
	        this.ungCuuMpd = ungCuuMpd;
	    }

	      public String getDescription() {
	        return description;
	    }

	      public void setDescription(String description) {
	        this.description = description;
	    }

	      public Integer getShiftId() {
	        return shiftId;
	    }

	     public void setShiftId(Integer shiftId) {
	        this.shiftId = shiftId;
	    }

	   
	    public String getSdateStr()
	    {
	    	if (sdate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(sdate));
	    	return null;
	    }
	    public String getEdateStr()
	    {
	    	if (edate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(edate));
	    		
	    	return null;
	    }
	    public String getDDHBaoMdStr()
	    {
	    	if (ddhBaoMd!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ddhBaoMd));
	    	return null;
	    }
	    public String getMchSdateStr()
	    {
	    	if (mchSdate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(mchSdate));
	    		
	    	return null;
	    }
	    public String getMCHEdateStr()
	    {
	    	if (mchEdate!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(mchEdate));
	    	return null;
	    }
	    public String getDDHBaoMCHStr()
	    {
	    	if (ddhBaoMch!=null)
	    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ddhBaoMch));
	    		
	    	return null;
	    }
	   
	    private String tgMD;
	    private String tgMCH;
	    public String getTgMD() {
			return tgMD;
		}

		public void setTgMD(String tgMD) {
			this.tgMD = tgMD;
		}
		public String gettgMCH() {
			return tgMCH;
		}

		public void settgMCH(String tgMCH) {
			this.tgMCH = tgMCH;
		}
		private String causeby;
		private String actionProcess;
		private String alarmType;
		 public String getCauseby() {
			return causeby;
		}

		public void setCauseby(String causeby) {
			this.causeby = causeby;
		}

		public String getActionProcess() {
			return actionProcess;
		}

		public void setActionProcess(String actionProcess) {
			this.actionProcess = actionProcess;
		}

		public String getAlarmType() {
			return alarmType;
		}

		public void setAlarmType(String alarmType) {
			this.alarmType = alarmType;
		}
		public String getCausebySy() {
			return causebySy;
		}
		public void setCausebySy(String causebySy) {
			this.causebySy = causebySy;
		}
		public String getStatusView() {
			return statusView;
		}
		public void setStatusView(String statusView) {
			this.statusView = statusView;
		}

}
