package vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class AlWorkCalendar implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.ID
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.EMAIL
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    @NotEmpty()
    private String email;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.DAY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private Date day;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.SHIFT
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    @NotEmpty()
    private String shift;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.FUNCTION
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    @NotEmpty()
    private String function;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.EMAIL_BEFORE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private String emailBefore;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.DESCRITION
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.CREATED_BY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.CREATE_DATE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.MODIFIED_BY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_WORK_CALENDAR.MODIFY_DATE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table AL_WORK_CALENDAR
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.ID
     *
     * @return the value of AL_WORK_CALENDAR.ID
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.ID
     *
     * @param id the value for AL_WORK_CALENDAR.ID
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.EMAIL
     *
     * @return the value of AL_WORK_CALENDAR.EMAIL
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.EMAIL
     *
     * @param email the value for AL_WORK_CALENDAR.EMAIL
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.DAY
     *
     * @return the value of AL_WORK_CALENDAR.DAY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public Date getDay() {
        return day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.DAY
     *
     * @param day the value for AL_WORK_CALENDAR.DAY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.SHIFT
     *
     * @return the value of AL_WORK_CALENDAR.SHIFT
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getShift() {
        return shift;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.SHIFT
     *
     * @param shift the value for AL_WORK_CALENDAR.SHIFT
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.FUNCTION
     *
     * @return the value of AL_WORK_CALENDAR.FUNCTION
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getFunction() {
        return function;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.FUNCTION
     *
     * @param function the value for AL_WORK_CALENDAR.FUNCTION
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.EMAIL_BEFORE
     *
     * @return the value of AL_WORK_CALENDAR.EMAIL_BEFORE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getEmailBefore() {
        return emailBefore;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.EMAIL_BEFORE
     *
     * @param emailBefore the value for AL_WORK_CALENDAR.EMAIL_BEFORE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setEmailBefore(String emailBefore) {
        this.emailBefore = emailBefore;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.DESCRITION
     *
     * @return the value of AL_WORK_CALENDAR.DESCRITION
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.DESCRITION
     *
     * @param descrition the value for AL_WORK_CALENDAR.DESCRITION
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.CREATED_BY
     *
     * @return the value of AL_WORK_CALENDAR.CREATED_BY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.CREATED_BY
     *
     * @param createdBy the value for AL_WORK_CALENDAR.CREATED_BY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.CREATE_DATE
     *
     * @return the value of AL_WORK_CALENDAR.CREATE_DATE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.CREATE_DATE
     *
     * @param createDate the value for AL_WORK_CALENDAR.CREATE_DATE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.MODIFIED_BY
     *
     * @return the value of AL_WORK_CALENDAR.MODIFIED_BY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.MODIFIED_BY
     *
     * @param modifiedBy the value for AL_WORK_CALENDAR.MODIFIED_BY
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_WORK_CALENDAR.MODIFY_DATE
     *
     * @return the value of AL_WORK_CALENDAR.MODIFY_DATE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_WORK_CALENDAR.MODIFY_DATE
     *
     * @param modifyDate the value for AL_WORK_CALENDAR.MODIFY_DATE
     *
     * @ibatorgenerated Sat Nov 30 14:28:11 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    private String deptCode;
    private String fullname;
    private String phone;
    private String position;
    private String fullnameBefore;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFullnameBefore() {
		return fullnameBefore;
	}

	public void setFullnameBefore(String fullnameBefore) {
		this.fullnameBefore = fullnameBefore;
	}
    
	private String shifts;

	public String getShifts() {
		return shifts;
	}

	public void setShifts(String shifts) {
		this.shifts = shifts;
	}
	
	public String getDayStr() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if(day != null)
			return df.format(day);
		else
			return null;
	}
	
	private Integer idUser;
	private Integer idUserBefore;

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdUserBefore() {
		return idUserBefore;
	}

	public void setIdUserBefore(Integer idUserBefore) {
		this.idUserBefore = idUserBefore;
	}
	
	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	private String team;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	private String shiftMode;

	public String getShiftMode() {
		return shiftMode;
	}

	public void setShiftMode(String shiftMode) {
		this.shiftMode = shiftMode;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	 public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@NotEmpty()
	private String region;
	 
	 private String location;
}