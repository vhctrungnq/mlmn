package vn.com.vhc.vmsc2.statistics.domain;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class S7linkset {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.LINKSETID
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
	@NotEmpty
	@Pattern(regexp="^[a-z0-9]{0,30}$")
    private String linksetid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.NODEID
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
	@NotEmpty
	@Pattern(regexp="^[a-z0-9]{0,50}$")
    private String nodeid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.REGION
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    private String region;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.LINKSET_NAME
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    private String linksetName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.MARK
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    private String mark;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.LAUNCH_DATE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    private Date launchDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.LAST_ACTIVE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    private Date lastActive;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_S7LINKSET.LINK_DEVICE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
   
    private Integer linkDevice;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.LINKSETID
     *
     * @return the value of H_S7LINKSET.LINKSETID
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public String getLinksetid() {
        return linksetid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.LINKSETID
     *
     * @param linksetid the value for H_S7LINKSET.LINKSETID
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setLinksetid(String linksetid) {
        this.linksetid = linksetid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.NODEID
     *
     * @return the value of H_S7LINKSET.NODEID
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public String getNodeid() {
        return nodeid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.NODEID
     *
     * @param nodeid the value for H_S7LINKSET.NODEID
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.REGION
     *
     * @return the value of H_S7LINKSET.REGION
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.REGION
     *
     * @param region the value for H_S7LINKSET.REGION
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.LINKSET_NAME
     *
     * @return the value of H_S7LINKSET.LINKSET_NAME
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public String getLinksetName() {
        return linksetName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.LINKSET_NAME
     *
     * @param linksetName the value for H_S7LINKSET.LINKSET_NAME
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setLinksetName(String linksetName) {
        this.linksetName = linksetName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.MARK
     *
     * @return the value of H_S7LINKSET.MARK
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public String getMark() {
        return mark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.MARK
     *
     * @param mark the value for H_S7LINKSET.MARK
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.LAUNCH_DATE
     *
     * @return the value of H_S7LINKSET.LAUNCH_DATE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public Date getLaunchDate() {
        return launchDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.LAUNCH_DATE
     *
     * @param launchDate the value for H_S7LINKSET.LAUNCH_DATE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.LAST_ACTIVE
     *
     * @return the value of H_S7LINKSET.LAST_ACTIVE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public Date getLastActive() {
        return lastActive;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.LAST_ACTIVE
     *
     * @param lastActive the value for H_S7LINKSET.LAST_ACTIVE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_S7LINKSET.LINK_DEVICE
     *
     * @return the value of H_S7LINKSET.LINK_DEVICE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public Integer getLinkDevice() {
        return linkDevice;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_S7LINKSET.LINK_DEVICE
     *
     * @param linkDevice the value for H_S7LINKSET.LINK_DEVICE
     *
     * @ibatorgenerated Tue Oct 19 14:29:06 ICT 2010
     */
    public void setLinkDevice(Integer linkDevice) {
        this.linkDevice = linkDevice;
    }
}