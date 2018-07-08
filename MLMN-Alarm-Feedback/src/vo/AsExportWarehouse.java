package vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotEmpty;

public class AsExportWarehouse implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.ID
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    @NotEmpty
    private String productCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.SERIAL_NO
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String serialNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.UNIT
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String unit;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.VENDOR
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.AMOUNT_EX
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    @NotNull
    private Integer amountEx;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.EXPORT_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    @NotNull
    private Date exportDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.VOTES_NO
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    @NotEmpty
    private String votesNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.USERS_EX
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String usersEx;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.AMOUNT_RETURN
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private Integer amountReturn;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.DATE_RETURN
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private Date dateReturn;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.DEPARTMENT_ID
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String departmentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.USERS
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String users;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.DESCRIPTION
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.CREATED_BY
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.CREATE_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.MODIFIED_BY
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_EXPORT_WAREHOUSE.MODIFY_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.ID
     *
     * @return the value of AS_EXPORT_WAREHOUSE.ID
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.ID
     *
     * @param id the value for AS_EXPORT_WAREHOUSE.ID
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @return the value of AS_EXPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @param productCode the value for AS_EXPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.SERIAL_NO
     *
     * @return the value of AS_EXPORT_WAREHOUSE.SERIAL_NO
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.SERIAL_NO
     *
     * @param serialNo the value for AS_EXPORT_WAREHOUSE.SERIAL_NO
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.UNIT
     *
     * @return the value of AS_EXPORT_WAREHOUSE.UNIT
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.UNIT
     *
     * @param unit the value for AS_EXPORT_WAREHOUSE.UNIT
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.VENDOR
     *
     * @return the value of AS_EXPORT_WAREHOUSE.VENDOR
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.VENDOR
     *
     * @param vendor the value for AS_EXPORT_WAREHOUSE.VENDOR
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.AMOUNT_EX
     *
     * @return the value of AS_EXPORT_WAREHOUSE.AMOUNT_EX
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public Integer getAmountEx() {
        return amountEx;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.AMOUNT_EX
     *
     * @param amountEx the value for AS_EXPORT_WAREHOUSE.AMOUNT_EX
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setAmountEx(Integer amountEx) {
        this.amountEx = amountEx;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.EXPORT_DATE
     *
     * @return the value of AS_EXPORT_WAREHOUSE.EXPORT_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public Date getExportDate() {
        return exportDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.EXPORT_DATE
     *
     * @param exportDate the value for AS_EXPORT_WAREHOUSE.EXPORT_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.VOTES_NO
     *
     * @return the value of AS_EXPORT_WAREHOUSE.VOTES_NO
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getVotesNo() {
        return votesNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.VOTES_NO
     *
     * @param votesNo the value for AS_EXPORT_WAREHOUSE.VOTES_NO
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setVotesNo(String votesNo) {
        this.votesNo = votesNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.USERS_EX
     *
     * @return the value of AS_EXPORT_WAREHOUSE.USERS_EX
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getUsersEx() {
        return usersEx;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.USERS_EX
     *
     * @param usersEx the value for AS_EXPORT_WAREHOUSE.USERS_EX
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setUsersEx(String usersEx) {
        this.usersEx = usersEx;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.AMOUNT_RETURN
     *
     * @return the value of AS_EXPORT_WAREHOUSE.AMOUNT_RETURN
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public Integer getAmountReturn() {
        return amountReturn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.AMOUNT_RETURN
     *
     * @param amountReturn the value for AS_EXPORT_WAREHOUSE.AMOUNT_RETURN
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setAmountReturn(Integer amountReturn) {
        this.amountReturn = amountReturn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.DATE_RETURN
     *
     * @return the value of AS_EXPORT_WAREHOUSE.DATE_RETURN
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public Date getDateReturn() {
        return dateReturn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.DATE_RETURN
     *
     * @param dateReturn the value for AS_EXPORT_WAREHOUSE.DATE_RETURN
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.DEPARTMENT_ID
     *
     * @return the value of AS_EXPORT_WAREHOUSE.DEPARTMENT_ID
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.DEPARTMENT_ID
     *
     * @param departmentId the value for AS_EXPORT_WAREHOUSE.DEPARTMENT_ID
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.USERS
     *
     * @return the value of AS_EXPORT_WAREHOUSE.USERS
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getUsers() {
        return users;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.USERS
     *
     * @param users the value for AS_EXPORT_WAREHOUSE.USERS
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setUsers(String users) {
        this.users = users;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.DESCRIPTION
     *
     * @return the value of AS_EXPORT_WAREHOUSE.DESCRIPTION
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.DESCRIPTION
     *
     * @param description the value for AS_EXPORT_WAREHOUSE.DESCRIPTION
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.CREATED_BY
     *
     * @return the value of AS_EXPORT_WAREHOUSE.CREATED_BY
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.CREATED_BY
     *
     * @param createdBy the value for AS_EXPORT_WAREHOUSE.CREATED_BY
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.CREATE_DATE
     *
     * @return the value of AS_EXPORT_WAREHOUSE.CREATE_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.CREATE_DATE
     *
     * @param createDate the value for AS_EXPORT_WAREHOUSE.CREATE_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.MODIFIED_BY
     *
     * @return the value of AS_EXPORT_WAREHOUSE.MODIFIED_BY
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.MODIFIED_BY
     *
     * @param modifiedBy the value for AS_EXPORT_WAREHOUSE.MODIFIED_BY
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_EXPORT_WAREHOUSE.MODIFY_DATE
     *
     * @return the value of AS_EXPORT_WAREHOUSE.MODIFY_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_EXPORT_WAREHOUSE.MODIFY_DATE
     *
     * @param modifyDate the value for AS_EXPORT_WAREHOUSE.MODIFY_DATE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    } 

	private String organization;
	private String assetsName;
	
	private String idAssets;
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	public String getAssetsName() {
		return assetsName;
	}

	public void setAssetsName(String assetsName) {
		this.assetsName = assetsName;
	} 
	
	public String getNgayXuatTS() {
		  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		  if(exportDate != null)
		   return df.format(exportDate);
		  else
		   return null;
		 }
	
	public String getNgayTraTS() {
		  SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		  if(dateReturn != null)
		   return df.format(dateReturn);
		  else
		   return null;
		 }

	public String getIdAssets() {
		return idAssets;
	}

	public void setIdAssets(String idAssets) {
		this.idAssets = idAssets;
	}
	
	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLyDoXuat() {
		return lyDoXuat;
	}

	public void setLyDoXuat(String lyDoXuat) {
		this.lyDoXuat = lyDoXuat;
	} 
	
	public String getNguonLayTB() {
		return nguonLayTB;
	}

	public void setNguonLayTB(String nguonLayTB) {
		this.nguonLayTB = nguonLayTB;
	}

	public String getBaoGomTB() {
		return baoGomTB;
	}

	public void setBaoGomTB(String baoGomTB) {
		this.baoGomTB = baoGomTB;
	}

	private String rownum;
	private String productName;
	private String lyDoXuat;
	private String nguonLayTB;
	private String baoGomTB;
}