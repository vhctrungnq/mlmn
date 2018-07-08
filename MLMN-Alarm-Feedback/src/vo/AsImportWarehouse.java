package vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AsImportWarehouse implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.ID
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    @NotEmpty()
    private String productCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.SERIAL_NO
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    @NotEmpty()
    private String serialNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.AMOUNT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    @NotNull()
    private Integer amount;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.UNIT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String unit;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.IMPORT_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private Date importDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.PRODUCE_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private Date produceDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.VENDOR
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.JECT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String ject;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.STATUS
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.CREATE_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AS_IMPORT_WAREHOUSE.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.ID
     *
     * @return the value of AS_IMPORT_WAREHOUSE.ID
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.ID
     *
     * @param id the value for AS_IMPORT_WAREHOUSE.ID
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @return the value of AS_IMPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @param productCode the value for AS_IMPORT_WAREHOUSE.PRODUCT_CODE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.SERIAL_NO
     *
     * @return the value of AS_IMPORT_WAREHOUSE.SERIAL_NO
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.SERIAL_NO
     *
     * @param serialNo the value for AS_IMPORT_WAREHOUSE.SERIAL_NO
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.AMOUNT
     *
     * @return the value of AS_IMPORT_WAREHOUSE.AMOUNT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.AMOUNT
     *
     * @param amount the value for AS_IMPORT_WAREHOUSE.AMOUNT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.UNIT
     *
     * @return the value of AS_IMPORT_WAREHOUSE.UNIT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.UNIT
     *
     * @param unit the value for AS_IMPORT_WAREHOUSE.UNIT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.IMPORT_DATE
     *
     * @return the value of AS_IMPORT_WAREHOUSE.IMPORT_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public Date getImportDate() {
        return importDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.IMPORT_DATE
     *
     * @param importDate the value for AS_IMPORT_WAREHOUSE.IMPORT_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.PRODUCE_DATE
     *
     * @return the value of AS_IMPORT_WAREHOUSE.PRODUCE_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public Date getProduceDate() {
        return produceDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.PRODUCE_DATE
     *
     * @param produceDate the value for AS_IMPORT_WAREHOUSE.PRODUCE_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.VENDOR
     *
     * @return the value of AS_IMPORT_WAREHOUSE.VENDOR
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.VENDOR
     *
     * @param vendor the value for AS_IMPORT_WAREHOUSE.VENDOR
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.JECT
     *
     * @return the value of AS_IMPORT_WAREHOUSE.JECT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getJect() {
        return ject;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.JECT
     *
     * @param ject the value for AS_IMPORT_WAREHOUSE.JECT
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setJect(String ject) {
        this.ject = ject;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.STATUS
     *
     * @return the value of AS_IMPORT_WAREHOUSE.STATUS
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.STATUS
     *
     * @param status the value for AS_IMPORT_WAREHOUSE.STATUS
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.DESCRIPTION
     *
     * @return the value of AS_IMPORT_WAREHOUSE.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.DESCRIPTION
     *
     * @param description the value for AS_IMPORT_WAREHOUSE.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.CREATED_BY
     *
     * @return the value of AS_IMPORT_WAREHOUSE.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.CREATED_BY
     *
     * @param createdBy the value for AS_IMPORT_WAREHOUSE.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.CREATE_DATE
     *
     * @return the value of AS_IMPORT_WAREHOUSE.CREATE_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.CREATE_DATE
     *
     * @param createDate the value for AS_IMPORT_WAREHOUSE.CREATE_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.MODIFIED_BY
     *
     * @return the value of AS_IMPORT_WAREHOUSE.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.MODIFIED_BY
     *
     * @param modifiedBy the value for AS_IMPORT_WAREHOUSE.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AS_IMPORT_WAREHOUSE.MODIFY_DATE
     *
     * @return the value of AS_IMPORT_WAREHOUSE.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AS_IMPORT_WAREHOUSE.MODIFY_DATE
     *
     * @param modifyDate the value for AS_IMPORT_WAREHOUSE.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    private String asTypesId;
    public String getAsTypesId() {
		return asTypesId;
	}

	public void setAsTypesId(String asTypesId) {
		this.asTypesId = asTypesId;
	}

	public String getAsTypesName() {
		return asTypesName;
	}

	public void setAsTypesName(String asTypesName) {
		this.asTypesName = asTypesName;
	}

	public String getIdAssets() {
		return idAssets;
	}

	public void setIdAssets(String idAssets) {
		this.idAssets = idAssets;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	private String asTypesName;
	
	private String idAssets;
	
	@NotEmpty()
	private String productName;
	
	private Date warrantyDate;
	
	private String slKiemke;

	public Date getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getSlKiemke() {
		return slKiemke;
	}

	public void setSlKiemke(String slKiemke) {
		this.slKiemke = slKiemke;
	}
	
	private Integer dangSd;
	private Integer chuaSd;

	public Integer getDangSd() {
		return dangSd;
	}

	public void setDangSd(Integer dangSd) {
		this.dangSd = dangSd;
	}

	public Integer getChuaSd() {
		return chuaSd;
	}

	public void setChuaSd(Integer chuaSd) {
		this.chuaSd = chuaSd;
	}
	
	
}