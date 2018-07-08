package vn.com.vhc.vmsc2.statistics.domain;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.USER_ID
     *
     * @ibatorgenerated Thu Dec 02 09:00:20 ICT 2010
     */
    private Integer userId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.FULL_NAME
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private String fullName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.DEPARTMENT
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private String department;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.EMAIL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    @Email
    private String email;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.MOBILE
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    @Pattern(regexp="[^a-zA-Z]*$")
    @Size(min=1, max=15)
    private String mobile;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.RECEIVE_SMS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private String receiveSms;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.RECEIVE_MAIL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private String receiveMail;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.RECEIVE_LOG
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private String receiveLog;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.ALERT_LEVEL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private Integer alertLevel;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.PASSWORD
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
	@NotEmpty
    private String password;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.ENABLED
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    private boolean enabled;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column A_ALERT_USERS.USERNAME
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
	@NotEmpty
    @Size(min=1, max=20)
    private String username;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.USER_ID
     *
     * @return the value of A_ALERT_USERS.USER_ID
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.USER_ID
     *
     * @param userId the value for A_ALERT_USERS.USER_ID
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.FULL_NAME
     *
     * @return the value of A_ALERT_USERS.FULL_NAME
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.FULL_NAME
     *
     * @param fullName the value for A_ALERT_USERS.FULL_NAME
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.DEPARTMENT
     *
     * @return the value of A_ALERT_USERS.DEPARTMENT
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getDepartment() {
        return department;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.DEPARTMENT
     *
     * @param department the value for A_ALERT_USERS.DEPARTMENT
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.EMAIL
     *
     * @return the value of A_ALERT_USERS.EMAIL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.EMAIL
     *
     * @param email the value for A_ALERT_USERS.EMAIL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.MOBILE
     *
     * @return the value of A_ALERT_USERS.MOBILE
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.MOBILE
     *
     * @param mobile the value for A_ALERT_USERS.MOBILE
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.RECEIVE_SMS
     *
     * @return the value of A_ALERT_USERS.RECEIVE_SMS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getReceiveSms() {
        return receiveSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.RECEIVE_SMS
     *
     * @param receiveSms the value for A_ALERT_USERS.RECEIVE_SMS
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setReceiveSms(String receiveSms) {
        this.receiveSms = receiveSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.RECEIVE_MAIL
     *
     * @return the value of A_ALERT_USERS.RECEIVE_MAIL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getReceiveMail() {
        return receiveMail;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.RECEIVE_MAIL
     *
     * @param receiveMail the value for A_ALERT_USERS.RECEIVE_MAIL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setReceiveMail(String receiveMail) {
        this.receiveMail = receiveMail;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.RECEIVE_LOG
     *
     * @return the value of A_ALERT_USERS.RECEIVE_LOG
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getReceiveLog() {
        return receiveLog;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.RECEIVE_LOG
     *
     * @param receiveLog the value for A_ALERT_USERS.RECEIVE_LOG
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setReceiveLog(String receiveLog) {
        this.receiveLog = receiveLog;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.ALERT_LEVEL
     *
     * @return the value of A_ALERT_USERS.ALERT_LEVEL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public Integer getAlertLevel() {
        return alertLevel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.ALERT_LEVEL
     *
     * @param alertLevel the value for A_ALERT_USERS.ALERT_LEVEL
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setAlertLevel(Integer alertLevel) {
        this.alertLevel = alertLevel;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.PASSWORD
     *
     * @return the value of A_ALERT_USERS.PASSWORD
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.PASSWORD
     *
     * @param password the value for A_ALERT_USERS.PASSWORD
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.ENABLED
     *
     * @return the value of A_ALERT_USERS.ENABLED
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.ENABLED
     *
     * @param enabled the value for A_ALERT_USERS.ENABLED
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column A_ALERT_USERS.USERNAME
     *
     * @return the value of A_ALERT_USERS.USERNAME
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column A_ALERT_USERS.USERNAME
     *
     * @param username the value for A_ALERT_USERS.USERNAME
     *
     * @ibatorgenerated Thu Dec 02 09:00:21 ICT 2010
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}

	private List<Role> roles;
    
}