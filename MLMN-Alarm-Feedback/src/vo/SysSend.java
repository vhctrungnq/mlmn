package vo;

import java.io.Serializable;
import java.util.Date;

public class SysSend implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.TITLE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String title;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.USERNAME
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String username;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.EMAIL
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String email;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.PHONE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String phone;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.MESSAGE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String message;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private Date send;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.IS_SMS
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String isSms;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.CREATE_DATE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.TICKETID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String ticketid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.PAKH_CHITIET_ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private Integer pakhChitietId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.SMS_CONTENT_ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private Integer smsContentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.SEND_CC
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private String sendCc;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_SEND.IS_SCAN
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private Short isScan;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.ID
     *
     * @return the value of SYS_SEND.ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.ID
     *
     * @param id the value for SYS_SEND.ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.TITLE
     *
     * @return the value of SYS_SEND.TITLE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.TITLE
     *
     * @param title the value for SYS_SEND.TITLE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.USERNAME
     *
     * @return the value of SYS_SEND.USERNAME
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.USERNAME
     *
     * @param username the value for SYS_SEND.USERNAME
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.EMAIL
     *
     * @return the value of SYS_SEND.EMAIL
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.EMAIL
     *
     * @param email the value for SYS_SEND.EMAIL
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.PHONE
     *
     * @return the value of SYS_SEND.PHONE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.PHONE
     *
     * @param phone the value for SYS_SEND.PHONE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.MESSAGE
     *
     * @return the value of SYS_SEND.MESSAGE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.MESSAGE
     *
     * @param message the value for SYS_SEND.MESSAGE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.SEND
     *
     * @return the value of SYS_SEND.SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public Date getSend() {
        return send;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.SEND
     *
     * @param send the value for SYS_SEND.SEND
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setSend(Date send) {
        this.send = send;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.IS_SMS
     *
     * @return the value of SYS_SEND.IS_SMS
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getIsSms() {
        return isSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.IS_SMS
     *
     * @param isSms the value for SYS_SEND.IS_SMS
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.CREATE_DATE
     *
     * @return the value of SYS_SEND.CREATE_DATE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.CREATE_DATE
     *
     * @param createDate the value for SYS_SEND.CREATE_DATE
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.TICKETID
     *
     * @return the value of SYS_SEND.TICKETID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getTicketid() {
        return ticketid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.TICKETID
     *
     * @param ticketid the value for SYS_SEND.TICKETID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.PAKH_CHITIET_ID
     *
     * @return the value of SYS_SEND.PAKH_CHITIET_ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public Integer getPakhChitietId() {
        return pakhChitietId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.PAKH_CHITIET_ID
     *
     * @param pakhChitietId the value for SYS_SEND.PAKH_CHITIET_ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setPakhChitietId(Integer pakhChitietId) {
        this.pakhChitietId = pakhChitietId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.SMS_CONTENT_ID
     *
     * @return the value of SYS_SEND.SMS_CONTENT_ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public Integer getSmsContentId() {
        return smsContentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.SMS_CONTENT_ID
     *
     * @param smsContentId the value for SYS_SEND.SMS_CONTENT_ID
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setSmsContentId(Integer smsContentId) {
        this.smsContentId = smsContentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.SEND_CC
     *
     * @return the value of SYS_SEND.SEND_CC
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public String getSendCc() {
        return sendCc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.SEND_CC
     *
     * @param sendCc the value for SYS_SEND.SEND_CC
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setSendCc(String sendCc) {
        this.sendCc = sendCc;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_SEND.IS_SCAN
     *
     * @return the value of SYS_SEND.IS_SCAN
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public Short getIsScan() {
        return isScan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_SEND.IS_SCAN
     *
     * @param isScan the value for SYS_SEND.IS_SCAN
     *
     * @ibatorgenerated Sat Dec 28 00:42:53 ICT 2013
     */
    public void setIsScan(Short isScan) {
        this.isScan = isScan;
    }
}