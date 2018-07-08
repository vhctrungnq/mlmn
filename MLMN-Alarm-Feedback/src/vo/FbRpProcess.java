package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class FbRpProcess implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.ID
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.DAY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Date day;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.FROM_RP_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Date fromRpDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.TO_RP_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Date toRpDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.FB_TYPE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private String fbType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.NETWORK_TYPE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private String networkType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.TOTAL_FB
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Integer totalFb;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.CAUSEDBY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    @NotEmpty()
    private String causedby;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.RESPONSE_CONTENT
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    @NotEmpty()
    private String responseContent;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.DESCRIPTION
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.CREATED_BY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.CREATE_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.MODIFIED_BY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_RP_PROCESS.MODIFY_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table FB_RP_PROCESS
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.ID
     *
     * @return the value of FB_RP_PROCESS.ID
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.ID
     *
     * @param id the value for FB_RP_PROCESS.ID
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.DAY
     *
     * @return the value of FB_RP_PROCESS.DAY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Date getDay() {
        return day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.DAY
     *
     * @param day the value for FB_RP_PROCESS.DAY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.FROM_RP_DATE
     *
     * @return the value of FB_RP_PROCESS.FROM_RP_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Date getFromRpDate() {
        return fromRpDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.FROM_RP_DATE
     *
     * @param fromRpDate the value for FB_RP_PROCESS.FROM_RP_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setFromRpDate(Date fromRpDate) {
        this.fromRpDate = fromRpDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.TO_RP_DATE
     *
     * @return the value of FB_RP_PROCESS.TO_RP_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Date getToRpDate() {
        return toRpDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.TO_RP_DATE
     *
     * @param toRpDate the value for FB_RP_PROCESS.TO_RP_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setToRpDate(Date toRpDate) {
        this.toRpDate = toRpDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.FB_TYPE
     *
     * @return the value of FB_RP_PROCESS.FB_TYPE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getFbType() {
        return fbType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.FB_TYPE
     *
     * @param fbType the value for FB_RP_PROCESS.FB_TYPE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setFbType(String fbType) {
        this.fbType = fbType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.NETWORK_TYPE
     *
     * @return the value of FB_RP_PROCESS.NETWORK_TYPE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getNetworkType() {
        return networkType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.NETWORK_TYPE
     *
     * @param networkType the value for FB_RP_PROCESS.NETWORK_TYPE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.TOTAL_FB
     *
     * @return the value of FB_RP_PROCESS.TOTAL_FB
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Integer getTotalFb() {
        return totalFb;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.TOTAL_FB
     *
     * @param totalFb the value for FB_RP_PROCESS.TOTAL_FB
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setTotalFb(Integer totalFb) {
        this.totalFb = totalFb;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.CAUSEDBY
     *
     * @return the value of FB_RP_PROCESS.CAUSEDBY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getCausedby() {
        return causedby;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.CAUSEDBY
     *
     * @param causedby the value for FB_RP_PROCESS.CAUSEDBY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setCausedby(String causedby) {
        this.causedby = causedby;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.RESPONSE_CONTENT
     *
     * @return the value of FB_RP_PROCESS.RESPONSE_CONTENT
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getResponseContent() {
        return responseContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.RESPONSE_CONTENT
     *
     * @param responseContent the value for FB_RP_PROCESS.RESPONSE_CONTENT
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.DESCRIPTION
     *
     * @return the value of FB_RP_PROCESS.DESCRIPTION
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.DESCRIPTION
     *
     * @param description the value for FB_RP_PROCESS.DESCRIPTION
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.CREATED_BY
     *
     * @return the value of FB_RP_PROCESS.CREATED_BY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.CREATED_BY
     *
     * @param createdBy the value for FB_RP_PROCESS.CREATED_BY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.CREATE_DATE
     *
     * @return the value of FB_RP_PROCESS.CREATE_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.CREATE_DATE
     *
     * @param createDate the value for FB_RP_PROCESS.CREATE_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.MODIFIED_BY
     *
     * @return the value of FB_RP_PROCESS.MODIFIED_BY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.MODIFIED_BY
     *
     * @param modifiedBy the value for FB_RP_PROCESS.MODIFIED_BY
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_RP_PROCESS.MODIFY_DATE
     *
     * @return the value of FB_RP_PROCESS.MODIFY_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_RP_PROCESS.MODIFY_DATE
     *
     * @param modifyDate the value for FB_RP_PROCESS.MODIFY_DATE
     *
     * @ibatorgenerated Mon Dec 03 10:30:35 ICT 2012
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    private String fbName;
    private String ngayBaoCao;

	public String getFbName() {
		return fbName;
	}

	public void setFbName(String fbName) {
		this.fbName = fbName;
	}

	public String getNgayBaoCao() {
		return ngayBaoCao;
	}

	public void setNgayBaoCao(String ngayBaoCao) {
		this.ngayBaoCao = ngayBaoCao;
	}
	
	private String deptProcess;

	public String getDeptProcess() {
		return deptProcess;
	}

	public void setDeptProcess(String deptProcess) {
		this.deptProcess = deptProcess;
	}
	
	private String abbreviated;

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}
    
}