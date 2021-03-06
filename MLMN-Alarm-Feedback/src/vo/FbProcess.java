package vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class FbProcess implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.FB_CODE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String fbCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.FB_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    @NotEmpty()
    private String fbType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.SUBSCRIBERS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    @NotEmpty()
    private String subscribers;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.SUBSCRIBER_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String subscriberType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.NETWORK_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String networkType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.FB_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private Date fbDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.FB_CONTENT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    @NotEmpty()
    private String fbContent;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.DEADLINE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private Date deadline;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.DISTRICT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String district;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.WARDS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String wards;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.PROVINCE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String province;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.ADDRESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String address;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.DEPT_PROCESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String deptProcess;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.RESPONSE_CONTENT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String responseContent;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.PROCESS_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private Date processDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.STATUS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.CREATED_BY
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.CREATE_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.MODIFIED_BY
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.MODIFY_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.BSC_RNC
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String bscRnc;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.CENTRAL_FB_ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String centralFbId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.FB_USER
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String fbUser;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column FB_PROCESS.PROCESS_USER
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String processUser;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table FB_PROCESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String chuaXuly;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.ID
     *
     * @return the value of FB_PROCESS.ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private String tlChuaXuly;
    
    private String fbSendTelecom;
    private Date checkTimeTelecom;
    private String fbComment;
    private String isFeedbacked;
    

    public String getFbSendTelecom() {
		return fbSendTelecom;
	}

	public void setFbSendTelecom(String fbSendTelecom) {
		this.fbSendTelecom = fbSendTelecom;
	}

	public Date getCheckTimeTelecom() {
		return checkTimeTelecom;
	}

	public void setCheckTimeTelecom(Date checkTimeTelecom) {
		this.checkTimeTelecom = checkTimeTelecom;
	}

	public String getFbComment() {
		return fbComment;
	}

	public void setFbComment(String fbComment) {
		this.fbComment = fbComment;
	}

	public String getIsFeedbacked() {
		return isFeedbacked;
	}

	public void setIsFeedbacked(String isFeedbacked) {
		this.isFeedbacked = isFeedbacked;
	}

	public String getChuaXuly() {
		return chuaXuly;
	}

	public void setChuaXuly(String chuaXuly) {
		this.chuaXuly = chuaXuly;
	}

	public String getTlChuaXuly() {
		return tlChuaXuly;
	}

	public void setTlChuaXuly(String tlChuaXuly) {
		this.tlChuaXuly = tlChuaXuly;
	}

	/**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.ID
     *
     * @return the value of FB_PROCESS.ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.ID
     *
     * @return the value of FB_PROCESS.ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.ID
     *
     * @param id the value for FB_PROCESS.ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.FB_CODE
     *
     * @return the value of FB_PROCESS.FB_CODE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getFbCode() {
        return fbCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.FB_CODE
     *
     * @param fbCode the value for FB_PROCESS.FB_CODE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setFbCode(String fbCode) {
        this.fbCode = fbCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.FB_TYPE
     *
     * @return the value of FB_PROCESS.FB_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getFbType() {
        return fbType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.FB_TYPE
     *
     * @param fbType the value for FB_PROCESS.FB_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setFbType(String fbType) {
        this.fbType = fbType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.SUBSCRIBERS
     *
     * @return the value of FB_PROCESS.SUBSCRIBERS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getSubscribers() {
        return subscribers;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.SUBSCRIBERS
     *
     * @param subscribers the value for FB_PROCESS.SUBSCRIBERS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setSubscribers(String subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.SUBSCRIBER_TYPE
     *
     * @return the value of FB_PROCESS.SUBSCRIBER_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getSubscriberType() {
        return subscriberType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.SUBSCRIBER_TYPE
     *
     * @param subscriberType the value for FB_PROCESS.SUBSCRIBER_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.NETWORK_TYPE
     *
     * @return the value of FB_PROCESS.NETWORK_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getNetworkType() {
        return networkType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.NETWORK_TYPE
     *
     * @param networkType the value for FB_PROCESS.NETWORK_TYPE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.FB_DATE
     *
     * @return the value of FB_PROCESS.FB_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public Date getFbDate() {
        return fbDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.FB_DATE
     *
     * @param fbDate the value for FB_PROCESS.FB_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setFbDate(Date fbDate) {
        this.fbDate = fbDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.FB_CONTENT
     *
     * @return the value of FB_PROCESS.FB_CONTENT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getFbContent() {
        return fbContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.FB_CONTENT
     *
     * @param fbContent the value for FB_PROCESS.FB_CONTENT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setFbContent(String fbContent) {
        this.fbContent = fbContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.DEADLINE
     *
     * @return the value of FB_PROCESS.DEADLINE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.DEADLINE
     *
     * @param deadline the value for FB_PROCESS.DEADLINE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.DISTRICT
     *
     * @return the value of FB_PROCESS.DISTRICT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getDistrict() {
        return district;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.DISTRICT
     *
     * @param district the value for FB_PROCESS.DISTRICT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.WARDS
     *
     * @return the value of FB_PROCESS.WARDS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getWards() {
        return wards;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.WARDS
     *
     * @param wards the value for FB_PROCESS.WARDS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setWards(String wards) {
        this.wards = wards;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.PROVINCE
     *
     * @return the value of FB_PROCESS.PROVINCE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.PROVINCE
     *
     * @param province the value for FB_PROCESS.PROVINCE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.ADDRESS
     *
     * @return the value of FB_PROCESS.ADDRESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.ADDRESS
     *
     * @param address the value for FB_PROCESS.ADDRESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.DEPT_PROCESS
     *
     * @return the value of FB_PROCESS.DEPT_PROCESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getDeptProcess() {
        return deptProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.DEPT_PROCESS
     *
     * @param deptProcess the value for FB_PROCESS.DEPT_PROCESS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setDeptProcess(String deptProcess) {
        this.deptProcess = deptProcess;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.RESPONSE_CONTENT
     *
     * @return the value of FB_PROCESS.RESPONSE_CONTENT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getResponseContent() {
        return responseContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.RESPONSE_CONTENT
     *
     * @param responseContent the value for FB_PROCESS.RESPONSE_CONTENT
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.PROCESS_DATE
     *
     * @return the value of FB_PROCESS.PROCESS_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.PROCESS_DATE
     *
     * @param processDate the value for FB_PROCESS.PROCESS_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.STATUS
     *
     * @return the value of FB_PROCESS.STATUS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.STATUS
     *
     * @param status the value for FB_PROCESS.STATUS
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.CREATED_BY
     *
     * @return the value of FB_PROCESS.CREATED_BY
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.CREATED_BY
     *
     * @param createdBy the value for FB_PROCESS.CREATED_BY
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.CREATE_DATE
     *
     * @return the value of FB_PROCESS.CREATE_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.CREATE_DATE
     *
     * @param createDate the value for FB_PROCESS.CREATE_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.MODIFIED_BY
     *
     * @return the value of FB_PROCESS.MODIFIED_BY
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.MODIFIED_BY
     *
     * @param modifiedBy the value for FB_PROCESS.MODIFIED_BY
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.MODIFY_DATE
     *
     * @return the value of FB_PROCESS.MODIFY_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.MODIFY_DATE
     *
     * @param modifyDate the value for FB_PROCESS.MODIFY_DATE
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.BSC_RNC
     *
     * @return the value of FB_PROCESS.BSC_RNC
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getBscRnc() {
        return bscRnc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.BSC_RNC
     *
     * @param bscRnc the value for FB_PROCESS.BSC_RNC
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setBscRnc(String bscRnc) {
        this.bscRnc = bscRnc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.CENTRAL_FB_ID
     *
     * @return the value of FB_PROCESS.CENTRAL_FB_ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getCentralFbId() {
        return centralFbId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.CENTRAL_FB_ID
     *
     * @param centralFbId the value for FB_PROCESS.CENTRAL_FB_ID
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setCentralFbId(String centralFbId) {
        this.centralFbId = centralFbId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.FB_USER
     *
     * @return the value of FB_PROCESS.FB_USER
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getFbUser() {
        return fbUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.FB_USER
     *
     * @param fbUser the value for FB_PROCESS.FB_USER
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setFbUser(String fbUser) {
        this.fbUser = fbUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column FB_PROCESS.PROCESS_USER
     *
     * @return the value of FB_PROCESS.PROCESS_USER
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public String getProcessUser() {
        return processUser;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column FB_PROCESS.PROCESS_USER
     *
     * @param processUser the value for FB_PROCESS.PROCESS_USER
     *
     * @ibatorgenerated Sat Nov 24 11:41:43 ICT 2012
     */
    public void setProcessUser(String processUser) {
        this.processUser = processUser;
    }
    
    private String nameStatus;

	private String nameSubscriberType;

	public String getNameStatus() {
		return nameStatus;
	}

	public void setNameStatus(String nameStatus) {
		this.nameStatus = nameStatus;
	}

	public String getNameSubscriberType() {
		return nameSubscriberType;
	}

	public void setNameSubscriberType(String nameSubscriberType) {
		this.nameSubscriberType = nameSubscriberType;
	}
	
	public String getNgayPhanAnh() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(fbDate != null)
			return df.format(fbDate);
		else
			return null;
	}
	
	private String abbreviated;

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}
	
	private String phuongQuan;

	public String getPhuongQuan() {
		return phuongQuan;
	}

	public void setPhuongQuan(String phuongQuan) {
		this.phuongQuan = phuongQuan;
	}
	
	private String fbName;

	public String getFbName() {
		return fbName;
	}

	public void setFbName(String fbName) {
		this.fbName = fbName;
	}
	
	public String getThoiGianXuLy() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(processDate != null)
			return df.format(processDate);
		else
			return null;
	}
	
	private String districtName;
	private String villageName;

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	
	public String getFleetNo() {
		return fleetNo;
	}

	public void setFleetNo(String fleetNo) {
		this.fleetNo = fleetNo;
	}

	public Date getFleetDate() {
		return fleetDate;
	}

	public void setFleetDate(Date fleetDate) {
		this.fleetDate = fleetDate;
	}

	public String getCausedby() {
		return causedby;
	}

	public void setCausedby(String causedby) {
		this.causedby = causedby;
	}

	private String fleetNo;
	private Date fleetDate;
	private String causedby;
	
	private String tgConLai;

	public String getTgConLai() {
		return tgConLai;
	}

	public void setTgConLai(String tgConLai) {
		this.tgConLai = tgConLai;
	}
	
	public String getHanXuLy() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if(deadline != null)
			return df.format(deadline);
		else		
			return null;
	}

	private String tgXuLy;

	public String getTgXuLy() {
		return tgXuLy;
	}

	public void setTgXuLy(String tgXuLy) {
		this.tgXuLy = tgXuLy;
	}
	
	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getVipCode() {
		return vipCode;
	}

	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	private String qty;
	
	private String vipCode;
	
	private String vipName;
	
	private Integer haiG;
	private Integer baG;
	private Integer other;
	private Integer total;

	public Integer getHaiG() {
		return haiG;
	}

	public void setHaiG(Integer haiG) {
		this.haiG = haiG;
	}

	public Integer getBaG() {
		return baG;
	}

	public void setBaG(Integer baG) {
		this.baG = baG;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	private Integer trongHan;
	private Integer quaHan;
	private String tlQuaHan;
	private String tlTrongHan;

	public Integer getTrongHan() {
		return trongHan;
	}

	public void setTrongHan(Integer trongHan) {
		this.trongHan = trongHan;
	}

	public Integer getQuaHan() {
		return quaHan;
	}

	public void setQuaHan(Integer quaHan) {
		this.quaHan = quaHan;
	}

	public String getTlQuaHan() {
		return tlQuaHan;
	}

	public void setTlQuaHan(String tlQuaHan) {
		this.tlQuaHan = tlQuaHan;
	}

	public String getTlTrongHan() {
		return tlTrongHan;
	}

	public void setTlTrongHan(String tlTrongHan) {
		this.tlTrongHan = tlTrongHan;
	}
	
	private Integer fbIbc;

	public Integer getFbIbc() {
		return fbIbc;
	}

	public void setFbIbc(Integer fbIbc) {
		this.fbIbc = fbIbc;
	}
	
	public String getFbIbcName() {
		return fbIbcName;
	}

	public void setFbIbcName(String fbIbcName) {
		this.fbIbcName = fbIbcName;
	}

	private String fbIbcName;
	
	private String soThueBao;

	public String getSoThueBao() {
		return soThueBao;
	}

	public void setSoThueBao(String soThueBao) {
		this.soThueBao = soThueBao;
	}
	
	private String provinceName;

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	private String team;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	private String processStatus;
	private String processHandleMethod;
	private String processMotional;

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessHandleMethod() {
		return processHandleMethod;
	}

	public void setProcessHandleMethod(String processHandleMethod) {
		this.processHandleMethod = processHandleMethod;
	}

	public String getProcessMotional() {
		return processMotional;
	}

	public void setProcessMotional(String processMotional) {
		this.processMotional = processMotional;
	}
	
	private String processResultLocal;

	public String getProcessResultLocal() {
		return processResultLocal;
	}

	public void setProcessResultLocal(String processResultLocal) {
		this.processResultLocal = processResultLocal;
	}
	private String site;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	private Integer fbQty;

	public Integer getFbQty() {
		return fbQty;
	}

	public void setFbQty(Integer fbQty) {
		this.fbQty = fbQty;
	}
	
	private String deletedBy;
	private Date deleteDate;

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	public String getFbDVT48hour() {
		return fbDVT48hour;
	}

	public void setFbDVT48hour(String fbDVT48hour) {
		this.fbDVT48hour = fbDVT48hour;
	}

	public String getFbDVT5day() {
		return fbDVT5day;
	}

	public void setFbDVT5day(String fbDVT5day) {
		this.fbDVT5day = fbDVT5day;
	}

	public Date getCheckTimeTelecom48H() {
		return checkTimeTelecom48H;
	}

	public void setCheckTimeTelecom48H(Date checkTimeTelecom48H) {
		this.checkTimeTelecom48H = checkTimeTelecom48H;
	}

	public Date getCheckTimeTelecom5DAY() {
		return checkTimeTelecom5DAY;
	}

	public void setCheckTimeTelecom5DAY(Date checkTimeTelecom5DAY) {
		this.checkTimeTelecom5DAY = checkTimeTelecom5DAY;
	}

	public Date getDvtFeedBackedDate() {
		return dvtFeedBackedDate;
	}

	public void setDvtFeedBackedDate(Date dvtFeedBackedDate) {
		this.dvtFeedBackedDate = dvtFeedBackedDate;
	}

	public String getTgXuLyDVT48h() {
		return tgXuLyDVT48h;
	}

	public void setTgXuLyDVT48h(String tgXuLyDVT48h) {
		this.tgXuLyDVT48h = tgXuLyDVT48h;
	}

	public String getTgXuLyDVT5day() {
		return tgXuLyDVT5day;
	}

	public void setTgXuLyDVT5day(String tgXuLyDVT5day) {
		this.tgXuLyDVT5day = tgXuLyDVT5day;
	}

	public String getDvtFeedbackContent() {
		return dvtFeedbackContent;
	}

	public void setDvtFeedbackContent(String dvtFeedbackContent) {
		this.dvtFeedbackContent = dvtFeedbackContent;
	}

	public String getTotalProcess() {
		return totalProcess;
	}

	public void setTotalProcess(String totalProcess) {
		this.totalProcess = totalProcess;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDeptFbId() {
		return deptFbId;
	}

	public void setDeptFbId(String deptFbId) {
		this.deptFbId = deptFbId;
	}

	public String getIsDiemDen() {
		return isDiemDen;
	}

	public void setIsDiemDen(String isDiemDen) {
		this.isDiemDen = isDiemDen;
	}

	public Long getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Long soLuong) {
		this.soLuong = soLuong;
	}

	private String fbDVT48hour;
	private String fbDVT5day;
	private Date checkTimeTelecom48H;
	private Date checkTimeTelecom5DAY;
	private Date dvtFeedBackedDate; //DVT_FEEDBACKED_DATE
	private String tgXuLyDVT48h;
	private String tgXuLyDVT5day;
	private String dvtFeedbackContent;
	private String totalProcess;
	private String note;
	
	public String getSlPhanAnhLai() {
		return slPhanAnhLai;
	}

	public void setSlPhanAnhLai(String slPhanAnhLai) {
		this.slPhanAnhLai = slPhanAnhLai;
	}

	public String getToaDoDeXuat() {
		return toaDoDeXuat;
	}

	public void setToaDoDeXuat(String toaDoDeXuat) {
		this.toaDoDeXuat = toaDoDeXuat;
	}

	private String deptFbId;
	private String isDiemDen;
	
	private Long soLuong;
	
	private String slPhanAnhLai;
	private String toaDoDeXuat;
	
	public String getCheckTimeTelecomStr() {
		if (checkTimeTelecom!=null)
     		return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(checkTimeTelecom));
     		
     	return null;
	}
	
	public String getDvtFeedBackedDateStr() {
		if (dvtFeedBackedDate!=null)
     		return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dvtFeedBackedDate));
     		
     	return null;
	}
	
}