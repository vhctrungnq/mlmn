package vn.com.vhc.vmsc2.statistics.domain;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class HBscLac {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.BSCID
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9]{0,50}$")
    private String bscid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */  
    private Integer lac;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.VENDOR
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.REGION
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    private String region;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.RAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */ 
    private Integer rac;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.CN
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    @NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9]{0,50}$")
    private String cn;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_BSC_LAC.LAUNCH_DATE
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    private Date launchDate;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.BSCID
     *
     * @return the value of H_BSC_LAC.BSCID
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public String getBscid() {
        return bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.BSCID
     *
     * @param bscid the value for H_BSC_LAC.BSCID
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setBscid(String bscid) {
        this.bscid = bscid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.LAC
     *
     * @return the value of H_BSC_LAC.LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public Integer getLac() {
        return lac;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.LAC
     *
     * @param lac the value for H_BSC_LAC.LAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setLac(Integer lac) {
        this.lac = lac;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.VENDOR
     *
     * @return the value of H_BSC_LAC.VENDOR
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.VENDOR
     *
     * @param vendor the value for H_BSC_LAC.VENDOR
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.REGION
     *
     * @return the value of H_BSC_LAC.REGION
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.REGION
     *
     * @param region the value for H_BSC_LAC.REGION
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.RAC
     *
     * @return the value of H_BSC_LAC.RAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public Integer getRac() {
        return rac;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.RAC
     *
     * @param rac the value for H_BSC_LAC.RAC
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setRac(Integer rac) {
        this.rac = rac;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.CN
     *
     * @return the value of H_BSC_LAC.CN
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public String getCn() {
        return cn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.CN
     *
     * @param cn the value for H_BSC_LAC.CN
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setCn(String cn) {
        this.cn = cn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_BSC_LAC.LAUNCH_DATE
     *
     * @return the value of H_BSC_LAC.LAUNCH_DATE
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public Date getLaunchDate() {
        return launchDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_BSC_LAC.LAUNCH_DATE
     *
     * @param launchDate the value for H_BSC_LAC.LAUNCH_DATE
     *
     * @ibatorgenerated Fri Jan 10 15:05:18 ICT 2014
     */
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }
}