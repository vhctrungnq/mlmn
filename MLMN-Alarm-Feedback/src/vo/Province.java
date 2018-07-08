package vo;

import org.hibernate.validator.constraints.NotEmpty;

public class Province {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.CODE
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
	@NotEmpty
    private String code;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.REGION
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
	@NotEmpty
    private String region;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.PROVINCE
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    private String province;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.MARKS
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    private String marks;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.DISTRICT
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    private String district;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.LOCATION
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    private String location;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_PROVINCES_CODE.BRANCH
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    private String branch;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.CODE
     *
     * @return the value of H_PROVINCES_CODE.CODE
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.CODE
     *
     * @param code the value for H_PROVINCES_CODE.CODE
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.REGION
     *
     * @return the value of H_PROVINCES_CODE.REGION
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.REGION
     *
     * @param region the value for H_PROVINCES_CODE.REGION
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.PROVINCE
     *
     * @return the value of H_PROVINCES_CODE.PROVINCE
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.PROVINCE
     *
     * @param province the value for H_PROVINCES_CODE.PROVINCE
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.MARKS
     *
     * @return the value of H_PROVINCES_CODE.MARKS
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getMarks() {
        return marks;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.MARKS
     *
     * @param marks the value for H_PROVINCES_CODE.MARKS
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setMarks(String marks) {
        this.marks = marks;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.DISTRICT
     *
     * @return the value of H_PROVINCES_CODE.DISTRICT
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getDistrict() {
        return district;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.DISTRICT
     *
     * @param district the value for H_PROVINCES_CODE.DISTRICT
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.LOCATION
     *
     * @return the value of H_PROVINCES_CODE.LOCATION
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.LOCATION
     *
     * @param location the value for H_PROVINCES_CODE.LOCATION
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_PROVINCES_CODE.BRANCH
     *
     * @return the value of H_PROVINCES_CODE.BRANCH
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public String getBranch() {
        return branch;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_PROVINCES_CODE.BRANCH
     *
     * @param branch the value for H_PROVINCES_CODE.BRANCH
     *
     * @ibatorgenerated Tue Mar 15 10:03:21 ICT 2011
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
}