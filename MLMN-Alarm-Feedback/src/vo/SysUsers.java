package vo;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class SysUsers {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.USERNAME
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
	@NotEmpty()
    private String username;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.ID
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.PASSWORD
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String password;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.FULLNAME
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    @NotEmpty()
    private String fullname;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.SEX
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String sex;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.POSITION
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String position;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.PHONE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    /*@NotEmpty()
    @Pattern(regexp="([0-9])*")*/
    private String phone;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
   /* @NotEmpty()
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")*/
    private String email;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.LOGIN_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String loginBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.RECEIVING_SMS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String receivingSms;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.RECEIVING_EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String receivingEmail;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.IS_ENABLE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String isEnable;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.ACTIVE_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private Date activeDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.EXPIRED
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private Date expired;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.DESCRIPTION
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.CREATED_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.CREATE_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.MODIFIED_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.MODIFY_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.MA_PHONG
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    @NotEmpty()
    private String maPhong;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.ROLES_ADD_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String rolesAddUsers;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.CC_EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String ccEmail;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_USERS.CC_SMS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    private String ccSms;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.USERNAME
     *
     * @return the value of SYS_USERS.USERNAME
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.USERNAME
     *
     * @param username the value for SYS_USERS.USERNAME
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.ID
     *
     * @return the value of SYS_USERS.ID
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.ID
     *
     * @param id the value for SYS_USERS.ID
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.PASSWORD
     *
     * @return the value of SYS_USERS.PASSWORD
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.PASSWORD
     *
     * @param password the value for SYS_USERS.PASSWORD
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.FULLNAME
     *
     * @return the value of SYS_USERS.FULLNAME
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.FULLNAME
     *
     * @param fullname the value for SYS_USERS.FULLNAME
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.SEX
     *
     * @return the value of SYS_USERS.SEX
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.SEX
     *
     * @param sex the value for SYS_USERS.SEX
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.POSITION
     *
     * @return the value of SYS_USERS.POSITION
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.POSITION
     *
     * @param position the value for SYS_USERS.POSITION
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.PHONE
     *
     * @return the value of SYS_USERS.PHONE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.PHONE
     *
     * @param phone the value for SYS_USERS.PHONE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.EMAIL
     *
     * @return the value of SYS_USERS.EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.EMAIL
     *
     * @param email the value for SYS_USERS.EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.LOGIN_BY
     *
     * @return the value of SYS_USERS.LOGIN_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getLoginBy() {
        return loginBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.LOGIN_BY
     *
     * @param loginBy the value for SYS_USERS.LOGIN_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.RECEIVING_SMS
     *
     * @return the value of SYS_USERS.RECEIVING_SMS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getReceivingSms() {
        return receivingSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.RECEIVING_SMS
     *
     * @param receivingSms the value for SYS_USERS.RECEIVING_SMS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setReceivingSms(String receivingSms) {
        this.receivingSms = receivingSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.RECEIVING_EMAIL
     *
     * @return the value of SYS_USERS.RECEIVING_EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getReceivingEmail() {
        return receivingEmail;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.RECEIVING_EMAIL
     *
     * @param receivingEmail the value for SYS_USERS.RECEIVING_EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setReceivingEmail(String receivingEmail) {
        this.receivingEmail = receivingEmail;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.IS_ENABLE
     *
     * @return the value of SYS_USERS.IS_ENABLE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getIsEnable() {
        return isEnable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.IS_ENABLE
     *
     * @param isEnable the value for SYS_USERS.IS_ENABLE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.ACTIVE_DATE
     *
     * @return the value of SYS_USERS.ACTIVE_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public Date getActiveDate() {
        return activeDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.ACTIVE_DATE
     *
     * @param activeDate the value for SYS_USERS.ACTIVE_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.EXPIRED
     *
     * @return the value of SYS_USERS.EXPIRED
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public Date getExpired() {
        return expired;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.EXPIRED
     *
     * @param expired the value for SYS_USERS.EXPIRED
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setExpired(Date expired) {
        this.expired = expired;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.DESCRIPTION
     *
     * @return the value of SYS_USERS.DESCRIPTION
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.DESCRIPTION
     *
     * @param description the value for SYS_USERS.DESCRIPTION
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.CREATED_BY
     *
     * @return the value of SYS_USERS.CREATED_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.CREATED_BY
     *
     * @param createdBy the value for SYS_USERS.CREATED_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.CREATE_DATE
     *
     * @return the value of SYS_USERS.CREATE_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.CREATE_DATE
     *
     * @param createDate the value for SYS_USERS.CREATE_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.MODIFIED_BY
     *
     * @return the value of SYS_USERS.MODIFIED_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.MODIFIED_BY
     *
     * @param modifiedBy the value for SYS_USERS.MODIFIED_BY
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.MODIFY_DATE
     *
     * @return the value of SYS_USERS.MODIFY_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.MODIFY_DATE
     *
     * @param modifyDate the value for SYS_USERS.MODIFY_DATE
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.MA_PHONG
     *
     * @return the value of SYS_USERS.MA_PHONG
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getMaPhong() {
        return maPhong;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.MA_PHONG
     *
     * @param maPhong the value for SYS_USERS.MA_PHONG
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.ROLES_ADD_USERS
     *
     * @return the value of SYS_USERS.ROLES_ADD_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getRolesAddUsers() {
        return rolesAddUsers;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.ROLES_ADD_USERS
     *
     * @param rolesAddUsers the value for SYS_USERS.ROLES_ADD_USERS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setRolesAddUsers(String rolesAddUsers) {
        this.rolesAddUsers = rolesAddUsers;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.CC_EMAIL
     *
     * @return the value of SYS_USERS.CC_EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getCcEmail() {
        return ccEmail;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.CC_EMAIL
     *
     * @param ccEmail the value for SYS_USERS.CC_EMAIL
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_USERS.CC_SMS
     *
     * @return the value of SYS_USERS.CC_SMS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public String getCcSms() {
        return ccSms;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_USERS.CC_SMS
     *
     * @param ccSms the value for SYS_USERS.CC_SMS
     *
     * @ibatorgenerated Wed Oct 24 16:22:23 ICT 2012
     */
    public void setCcSms(String ccSms) {
        this.ccSms = ccSms;
    }
    
    private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	private String activeDateStr;
    public String getActiveDateStr() {
		return activeDateStr;
	}

	public void setActiveDateStr(String activeDateStr) {
		this.activeDateStr = activeDateStr;
	}
	
	private String expiredStr;
	
	public String getExpiredStr() {
		return expiredStr;
	}

	public void setExpiredStr(String expiredStr) {
		this.expiredStr = expiredStr;
	}

	private String abbreviated;
	private String nameTrangThai;
	private String quyenTaoNd;
	private String reNameEmail;
	private String reNameSms;
	
	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}

	public String getNameTrangThai() {
		return nameTrangThai;
	}

	public void setNameTrangThai(String nameTrangThai) {
		this.nameTrangThai = nameTrangThai;
	}

	public String getQuyenTaoNd() {
		return quyenTaoNd;
	}

	public void setQuyenTaoNd(String quyenTaoNd) {
		this.quyenTaoNd = quyenTaoNd;
	}

	public String getReNameEmail() {
		return reNameEmail;
	}

	public void setReNameEmail(String reNameEmail) {
		this.reNameEmail = reNameEmail;
	}

	public String getReNameSms() {
		return reNameSms;
	}

	public void setReNameSms(String reNameSms) {
		this.reNameSms = reNameSms;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	private String sexStr;
	String isRoleSystem;
	String isSmsLeaseline;
	String isRoleManager;
	String team;
	String subTeam;
	private String regionRole;

	public String getIsRoleSystem() {
		return isRoleSystem;
	}

	public void setIsRoleSystem(String isRoleSystem) {
		this.isRoleSystem = isRoleSystem;
	}

	public String getIsSmsLeaseline() {
		return isSmsLeaseline;
	}

	public void setIsSmsLeaseline(String isSmsLeaseline) {
		this.isSmsLeaseline = isSmsLeaseline;
	}

	public String getIsRoleManager() {
		return isRoleManager;
	}

	public void setIsRoleManager(String isRoleManager) {
		this.isRoleManager = isRoleManager;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getSubTeam() {
		return subTeam;
	}

	public void setSubTeam(String subTeam) {
		this.subTeam = subTeam;
	}

	public String getRegionRole() {
		return regionRole;
	}

	public void setRegionRole(String regionRole) {
		this.regionRole = regionRole;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	private String region;
	
}