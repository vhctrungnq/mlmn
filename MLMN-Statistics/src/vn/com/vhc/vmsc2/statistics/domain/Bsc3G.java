package vn.com.vhc.vmsc2.statistics.domain;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

public class Bsc3G {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_3G.BSCID
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9]{0,50}$")
	 private String bscid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_3G.VENDOR
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_3G.REGION
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    private String region;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_3G.LAUNCH_DATE
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    private Date launchDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_3G.MSCID
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    private String mscid;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_3G.BSCID
     *
     * @return the value of H_BSC_3G.BSCID
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    private String team;
    private String dept;
    @NumberFormat
    private Float smsCssr;
    @NumberFormat
    private Float smsDrc;
    private String province;
    private String locationName;
    private String subTeam;
    private String nsei;
    private Date activeDate;
    private String neGroup;
    private String strSmsCssr;
    private String strSmsDrc;
    
    public String getStrSmsCssr() {
		return strSmsCssr;
	}

	public void setStrSmsCssr(String strSmsCssr) {
		this.strSmsCssr = strSmsCssr;
	}

	public String getStrSmsDrc() {
		return strSmsDrc;
	}

	public void setStrSmsDrc(String strSmsDrc) {
		this.strSmsDrc = strSmsDrc;
	}

	public Float getSmsCssr() {
		return smsCssr;
	}

	public void setSmsCssr(Float smsCssr) {
		this.smsCssr = smsCssr;
	}

	public Float getSmsDrc() {
		return smsDrc;
	}

	public void setSmsDrc(Float smsDrc) {
		this.smsDrc = smsDrc;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getSubTeam() {
		return subTeam;
	}

	public void setSubTeam(String subTeam) {
		this.subTeam = subTeam;
	}

	public String getNsei() {
		return nsei;
	}

	public void setNsei(String nsei) {
		this.nsei = nsei;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public String getNeGroup() {
		return neGroup;
	}

	public void setNeGroup(String neGroup) {
		this.neGroup = neGroup;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	} 
	
    public String getBscid() {
        return bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_3G.BSCID
     *
     * @param bscid the value for H_BSC_3G.BSCID
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void setBscid(String bscid) {
        this.bscid = bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_3G.VENDOR
     *
     * @return the value of H_BSC_3G.VENDOR
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_3G.VENDOR
     *
     * @param vendor the value for H_BSC_3G.VENDOR
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_3G.REGION
     *
     * @return the value of H_BSC_3G.REGION
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_3G.REGION
     *
     * @param region the value for H_BSC_3G.REGION
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_3G.LAUNCH_DATE
     *
     * @return the value of H_BSC_3G.LAUNCH_DATE
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public Date getLaunchDate() {
        return launchDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_3G.LAUNCH_DATE
     *
     * @param launchDate the value for H_BSC_3G.LAUNCH_DATE
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_3G.MSCID
     *
     * @return the value of H_BSC_3G.MSCID
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public String getMscid() {
        return mscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_3G.MSCID
     *
     * @param mscid the value for H_BSC_3G.MSCID
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void setMscid(String mscid) {
        this.mscid = mscid;
    }
    
    private String activeDateStr;
    public String getActiveDateStr() {
		return activeDateStr;
	}

	public void setActiveDateStr(String activeDateStr) {
		this.activeDateStr = activeDateStr;
	}
}