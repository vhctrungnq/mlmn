package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;


public class HWards implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.ID
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.VILLAGE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    @NotEmpty()
    private String village;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.VILLAGE_NAME
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    @NotEmpty()
    private String villageName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.ORDERING
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private Integer ordering;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.DESCRIPTION
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.CREATED_BY
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.CREATE_DATE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.MODIFIED_BY
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.MODIFY_DATE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.DISTRICT_CODE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    @NotEmpty()
    private String districtCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_WARDS.PROVINCE_CODE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    @NotEmpty()
    private String provinceCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table H_WARDS
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.ID
     *
     * @return the value of H_WARDS.ID
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.ID
     *
     * @param id the value for H_WARDS.ID
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.VILLAGE
     *
     * @return the value of H_WARDS.VILLAGE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getVillage() {
        return village;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.VILLAGE
     *
     * @param village the value for H_WARDS.VILLAGE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setVillage(String village) {
        this.village = village;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.VILLAGE_NAME
     *
     * @return the value of H_WARDS.VILLAGE_NAME
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getVillageName() {
        return villageName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.VILLAGE_NAME
     *
     * @param villageName the value for H_WARDS.VILLAGE_NAME
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.ORDERING
     *
     * @return the value of H_WARDS.ORDERING
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.ORDERING
     *
     * @param ordering the value for H_WARDS.ORDERING
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.DESCRIPTION
     *
     * @return the value of H_WARDS.DESCRIPTION
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.DESCRIPTION
     *
     * @param description the value for H_WARDS.DESCRIPTION
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.CREATED_BY
     *
     * @return the value of H_WARDS.CREATED_BY
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.CREATED_BY
     *
     * @param createdBy the value for H_WARDS.CREATED_BY
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.CREATE_DATE
     *
     * @return the value of H_WARDS.CREATE_DATE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.CREATE_DATE
     *
     * @param createDate the value for H_WARDS.CREATE_DATE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.MODIFIED_BY
     *
     * @return the value of H_WARDS.MODIFIED_BY
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.MODIFIED_BY
     *
     * @param modifiedBy the value for H_WARDS.MODIFIED_BY
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.MODIFY_DATE
     *
     * @return the value of H_WARDS.MODIFY_DATE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.MODIFY_DATE
     *
     * @param modifyDate the value for H_WARDS.MODIFY_DATE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.DISTRICT_CODE
     *
     * @return the value of H_WARDS.DISTRICT_CODE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.DISTRICT_CODE
     *
     * @param districtCode the value for H_WARDS.DISTRICT_CODE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_WARDS.PROVINCE_CODE
     *
     * @return the value of H_WARDS.PROVINCE_CODE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_WARDS.PROVINCE_CODE
     *
     * @param provinceCode the value for H_WARDS.PROVINCE_CODE
     *
     * @ibatorgenerated Sun Mar 10 21:57:34 ICT 2013
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    
    public String getOrderingStr() {
		return orderingStr;
	}

	public void setOrderingStr(String orderingStr) {
		this.orderingStr = orderingStr;
	}

	public String getVillageNameTK() {
		return villageNameTK;
	}

	public void setVillageNameTK(String villageNameTK) {
		this.villageNameTK = villageNameTK;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	private String orderingStr;
    private String villageNameTK;
    private String districtName;
    private String provinceName;
}