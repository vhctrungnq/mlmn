package vn.com.vhc.vmsc2.statistics.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Sgsn {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSNID
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
	@NotEmpty()
    private String sgsnid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.REGION
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private String region;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_NAME
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    @NotEmpty()
    private String sgsnName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.UI_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Long uiAttachCapacityLicense;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.GB_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Long gbAttachCapacityLicense;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.TOTAL_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Long totalAttachCapacityLicense;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.THOUGHPUT_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Long thoughputCapacityLicense;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.PDP_CONTEXT_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Long pdpContextCapacityLicense;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_ATTACT_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Float sgsnAttactSuccr;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_PSSR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Float sgsnPssr;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_PAGE_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Float sgsnPageSuccr;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_AUTH_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Float sgsnAuthSuccr;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_INTRA_RAU_SUCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Float sgsnIntraRauSucr;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SGSN_INTER_RAU_SUCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private Float sgsnInterRauSucr;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.LOCATION_NAME
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private String locationName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.DEPT
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private String dept;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.TEAM
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private String team;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.SUB_TEAM
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private String subTeam;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_SGSN.NE_GROUP
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    private String neGroup;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSNID
     *
     * @return the value of H_SGSN.SGSNID
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getSgsnid() {
        return sgsnid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSNID
     *
     * @param sgsnid the value for H_SGSN.SGSNID
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnid(String sgsnid) {
        this.sgsnid = sgsnid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.REGION
     *
     * @return the value of H_SGSN.REGION
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.REGION
     *
     * @param region the value for H_SGSN.REGION
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_NAME
     *
     * @return the value of H_SGSN.SGSN_NAME
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getSgsnName() {
        return sgsnName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_NAME
     *
     * @param sgsnName the value for H_SGSN.SGSN_NAME
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnName(String sgsnName) {
        this.sgsnName = sgsnName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.UI_ATTACH_CAPACITY_LICENSE
     *
     * @return the value of H_SGSN.UI_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Long getUiAttachCapacityLicense() {
        return uiAttachCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.UI_ATTACH_CAPACITY_LICENSE
     *
     * @param uiAttachCapacityLicense the value for H_SGSN.UI_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setUiAttachCapacityLicense(Long uiAttachCapacityLicense) {
        this.uiAttachCapacityLicense = uiAttachCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.GB_ATTACH_CAPACITY_LICENSE
     *
     * @return the value of H_SGSN.GB_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Long getGbAttachCapacityLicense() {
        return gbAttachCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.GB_ATTACH_CAPACITY_LICENSE
     *
     * @param gbAttachCapacityLicense the value for H_SGSN.GB_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setGbAttachCapacityLicense(Long gbAttachCapacityLicense) {
        this.gbAttachCapacityLicense = gbAttachCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.TOTAL_ATTACH_CAPACITY_LICENSE
     *
     * @return the value of H_SGSN.TOTAL_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Long getTotalAttachCapacityLicense() {
        return totalAttachCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.TOTAL_ATTACH_CAPACITY_LICENSE
     *
     * @param totalAttachCapacityLicense the value for H_SGSN.TOTAL_ATTACH_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setTotalAttachCapacityLicense(Long totalAttachCapacityLicense) {
        this.totalAttachCapacityLicense = totalAttachCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.THOUGHPUT_CAPACITY_LICENSE
     *
     * @return the value of H_SGSN.THOUGHPUT_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Long getThoughputCapacityLicense() {
        return thoughputCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.THOUGHPUT_CAPACITY_LICENSE
     *
     * @param thoughputCapacityLicense the value for H_SGSN.THOUGHPUT_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setThoughputCapacityLicense(Long thoughputCapacityLicense) {
        this.thoughputCapacityLicense = thoughputCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.PDP_CONTEXT_CAPACITY_LICENSE
     *
     * @return the value of H_SGSN.PDP_CONTEXT_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Long getPdpContextCapacityLicense() {
        return pdpContextCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.PDP_CONTEXT_CAPACITY_LICENSE
     *
     * @param pdpContextCapacityLicense the value for H_SGSN.PDP_CONTEXT_CAPACITY_LICENSE
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setPdpContextCapacityLicense(Long pdpContextCapacityLicense) {
        this.pdpContextCapacityLicense = pdpContextCapacityLicense;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_ATTACT_SUCCR
     *
     * @return the value of H_SGSN.SGSN_ATTACT_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Float getSgsnAttactSuccr() {
        return sgsnAttactSuccr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_ATTACT_SUCCR
     *
     * @param sgsnAttactSuccr the value for H_SGSN.SGSN_ATTACT_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnAttactSuccr(Float sgsnAttactSuccr) {
        this.sgsnAttactSuccr = sgsnAttactSuccr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_PSSR
     *
     * @return the value of H_SGSN.SGSN_PSSR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Float getSgsnPssr() {
        return sgsnPssr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_PSSR
     *
     * @param sgsnPssr the value for H_SGSN.SGSN_PSSR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnPssr(Float sgsnPssr) {
        this.sgsnPssr = sgsnPssr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_PAGE_SUCCR
     *
     * @return the value of H_SGSN.SGSN_PAGE_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Float getSgsnPageSuccr() {
        return sgsnPageSuccr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_PAGE_SUCCR
     *
     * @param sgsnPageSuccr the value for H_SGSN.SGSN_PAGE_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnPageSuccr(Float sgsnPageSuccr) {
        this.sgsnPageSuccr = sgsnPageSuccr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_AUTH_SUCCR
     *
     * @return the value of H_SGSN.SGSN_AUTH_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Float getSgsnAuthSuccr() {
        return sgsnAuthSuccr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_AUTH_SUCCR
     *
     * @param sgsnAuthSuccr the value for H_SGSN.SGSN_AUTH_SUCCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnAuthSuccr(Float sgsnAuthSuccr) {
        this.sgsnAuthSuccr = sgsnAuthSuccr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_INTRA_RAU_SUCR
     *
     * @return the value of H_SGSN.SGSN_INTRA_RAU_SUCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Float getSgsnIntraRauSucr() {
        return sgsnIntraRauSucr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_INTRA_RAU_SUCR
     *
     * @param sgsnIntraRauSucr the value for H_SGSN.SGSN_INTRA_RAU_SUCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnIntraRauSucr(Float sgsnIntraRauSucr) {
        this.sgsnIntraRauSucr = sgsnIntraRauSucr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SGSN_INTER_RAU_SUCR
     *
     * @return the value of H_SGSN.SGSN_INTER_RAU_SUCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Float getSgsnInterRauSucr() {
        return sgsnInterRauSucr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SGSN_INTER_RAU_SUCR
     *
     * @param sgsnInterRauSucr the value for H_SGSN.SGSN_INTER_RAU_SUCR
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSgsnInterRauSucr(Float sgsnInterRauSucr) {
        this.sgsnInterRauSucr = sgsnInterRauSucr;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.LOCATION_NAME
     *
     * @return the value of H_SGSN.LOCATION_NAME
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.LOCATION_NAME
     *
     * @param locationName the value for H_SGSN.LOCATION_NAME
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.DEPT
     *
     * @return the value of H_SGSN.DEPT
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getDept() {
        return dept;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.DEPT
     *
     * @param dept the value for H_SGSN.DEPT
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.TEAM
     *
     * @return the value of H_SGSN.TEAM
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getTeam() {
        return team;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.TEAM
     *
     * @param team the value for H_SGSN.TEAM
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.SUB_TEAM
     *
     * @return the value of H_SGSN.SUB_TEAM
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getSubTeam() {
        return subTeam;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.SUB_TEAM
     *
     * @param subTeam the value for H_SGSN.SUB_TEAM
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setSubTeam(String subTeam) {
        this.subTeam = subTeam;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_SGSN.NE_GROUP
     *
     * @return the value of H_SGSN.NE_GROUP
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public String getNeGroup() {
        return neGroup;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_SGSN.NE_GROUP
     *
     * @param neGroup the value for H_SGSN.NE_GROUP
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void setNeGroup(String neGroup) {
        this.neGroup = neGroup;
    }
    
    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
}