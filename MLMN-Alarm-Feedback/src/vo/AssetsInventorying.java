package vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AssetsInventorying implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.ID
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.PRODUCT_CODE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    @NotEmpty()
    private String productCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.SERIAL_NO
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String serialNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.AMOUNT
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    @NotNull()
    private Integer amount;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.UNIT
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String unit;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.IMPORT_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private Date importDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.PRODUCE_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private Date produceDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.VENDOR
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.ORIGIN
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String origin;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.STATUS
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.DEPARTMENT_ID
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String departmentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.USERS
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String users;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.DESCRIPTION
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.CREATED_BY
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.CREATE_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.MODIFIED_BY
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.MODIFY_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ASSETS_INVENTORYING.PRODUCT_NAME
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    @NotEmpty()
    private String productName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table ASSETS_INVENTORYING
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.ID
     *
     * @return the value of ASSETS_INVENTORYING.ID
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.ID
     *
     * @param id the value for ASSETS_INVENTORYING.ID
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.PRODUCT_CODE
     *
     * @return the value of ASSETS_INVENTORYING.PRODUCT_CODE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.PRODUCT_CODE
     *
     * @param productCode the value for ASSETS_INVENTORYING.PRODUCT_CODE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.SERIAL_NO
     *
     * @return the value of ASSETS_INVENTORYING.SERIAL_NO
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.SERIAL_NO
     *
     * @param serialNo the value for ASSETS_INVENTORYING.SERIAL_NO
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.AMOUNT
     *
     * @return the value of ASSETS_INVENTORYING.AMOUNT
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.AMOUNT
     *
     * @param amount the value for ASSETS_INVENTORYING.AMOUNT
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.UNIT
     *
     * @return the value of ASSETS_INVENTORYING.UNIT
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.UNIT
     *
     * @param unit the value for ASSETS_INVENTORYING.UNIT
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.IMPORT_DATE
     *
     * @return the value of ASSETS_INVENTORYING.IMPORT_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public Date getImportDate() {
        return importDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.IMPORT_DATE
     *
     * @param importDate the value for ASSETS_INVENTORYING.IMPORT_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.PRODUCE_DATE
     *
     * @return the value of ASSETS_INVENTORYING.PRODUCE_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public Date getProduceDate() {
        return produceDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.PRODUCE_DATE
     *
     * @param produceDate the value for ASSETS_INVENTORYING.PRODUCE_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.VENDOR
     *
     * @return the value of ASSETS_INVENTORYING.VENDOR
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.VENDOR
     *
     * @param vendor the value for ASSETS_INVENTORYING.VENDOR
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.ORIGIN
     *
     * @return the value of ASSETS_INVENTORYING.ORIGIN
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.ORIGIN
     *
     * @param origin the value for ASSETS_INVENTORYING.ORIGIN
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.STATUS
     *
     * @return the value of ASSETS_INVENTORYING.STATUS
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.STATUS
     *
     * @param status the value for ASSETS_INVENTORYING.STATUS
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.DEPARTMENT_ID
     *
     * @return the value of ASSETS_INVENTORYING.DEPARTMENT_ID
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.DEPARTMENT_ID
     *
     * @param departmentId the value for ASSETS_INVENTORYING.DEPARTMENT_ID
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.USERS
     *
     * @return the value of ASSETS_INVENTORYING.USERS
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getUsers() {
        return users;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.USERS
     *
     * @param users the value for ASSETS_INVENTORYING.USERS
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setUsers(String users) {
        this.users = users;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.DESCRIPTION
     *
     * @return the value of ASSETS_INVENTORYING.DESCRIPTION
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.DESCRIPTION
     *
     * @param description the value for ASSETS_INVENTORYING.DESCRIPTION
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.CREATED_BY
     *
     * @return the value of ASSETS_INVENTORYING.CREATED_BY
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.CREATED_BY
     *
     * @param createdBy the value for ASSETS_INVENTORYING.CREATED_BY
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.CREATE_DATE
     *
     * @return the value of ASSETS_INVENTORYING.CREATE_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.CREATE_DATE
     *
     * @param createDate the value for ASSETS_INVENTORYING.CREATE_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.MODIFIED_BY
     *
     * @return the value of ASSETS_INVENTORYING.MODIFIED_BY
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.MODIFIED_BY
     *
     * @param modifiedBy the value for ASSETS_INVENTORYING.MODIFIED_BY
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.MODIFY_DATE
     *
     * @return the value of ASSETS_INVENTORYING.MODIFY_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.MODIFY_DATE
     *
     * @param modifyDate the value for ASSETS_INVENTORYING.MODIFY_DATE
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ASSETS_INVENTORYING.PRODUCT_NAME
     *
     * @return the value of ASSETS_INVENTORYING.PRODUCT_NAME
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ASSETS_INVENTORYING.PRODUCT_NAME
     *
     * @param productName the value for ASSETS_INVENTORYING.PRODUCT_NAME
     *
     * @ibatorgenerated Fri May 03 16:24:45 ICT 2013
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getDepartmentUse() {
		return departmentUse;
	}

	public void setDepartmentUse(String departmentUse) {
		this.departmentUse = departmentUse;
	}

	private String departmentUse;
	
	private Integer ss;
	private Integer dangSD;
	public Integer getSs() {
		return ss;
	}

	public void setSs(Integer ss) {
		this.ss = ss;
	}

	public Integer getDangSD() {
		return dangSD;
	}

	public void setDangSD(Integer dangSD) {
		this.dangSD = dangSD;
	}

	public Integer getBaoHanh() {
		return baoHanh;
	}

	public void setBaoHanh(Integer baoHanh) {
		this.baoHanh = baoHanh;
	}

	public Integer getKhongSD() {
		return khongSD;
	}

	public void setKhongSD(Integer khongSD) {
		this.khongSD = khongSD;
	}

	public Integer getLech() {
		return lech;
	}

	public void setLech(Integer lech) {
		this.lech = lech;
	}

	public String getAsTypesId() {
		return asTypesId;
	}

	public void setAsTypesId(String asTypesId) {
		this.asTypesId = asTypesId;
	}

	public String getDotKiemKe() {
		return dotKiemKe;
	}

	public void setDotKiemKe(String dotKiemKe) {
		this.dotKiemKe = dotKiemKe;
	}

	public Integer getSlSoSach() {
		return slSoSach;
	}

	public void setSlSoSach(Integer slSoSach) {
		this.slSoSach = slSoSach;
	}

	public Integer getLechCu() {
		return lechCu;
	}

	public void setLechCu(Integer lechCu) {
		this.lechCu = lechCu;
	}

	public Integer getSlDangSd() {
		return slDangSd;
	}

	public void setSlDangSd(Integer slDangSd) {
		this.slDangSd = slDangSd;
	}

	public Integer getSlKhongSd() {
		return slKhongSd;
	}

	public void setSlKhongSd(Integer slKhongSd) {
		this.slKhongSd = slKhongSd;
	}

	public Integer getSlBaoHanh() {
		return slBaoHanh;
	}

	public void setSlBaoHanh(Integer slBaoHanh) {
		this.slBaoHanh = slBaoHanh;
	}

	private Integer baoHanh;
	private Integer khongSD;
	private Integer lech;
	private Integer lechCu;
	private String dotKiemKe;
	private Integer slSoSach;
	private Integer slDangSd;
	private Integer slKhongSd;
	private Integer slBaoHanh;
	
	private String asTypesId;
}