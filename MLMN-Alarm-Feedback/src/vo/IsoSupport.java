package vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class IsoSupport implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.ID
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.VENDOR
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.NE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private String ne;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.NE_TYPE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private String neType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.BOARD_NAME
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    @NotEmpty()
    private String boardName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.VALUE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    @NotNull()
    private Integer value;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.UNIT
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    @NotEmpty()
    private String unit;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.CREATED_BY
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.CREATE_DATE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.MODIFIED_BY
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column ISO_SUPPORT.MODIFY_DATE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table ISO_SUPPORT
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.ID
     *
     * @return the value of ISO_SUPPORT.ID
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.ID
     *
     * @param id the value for ISO_SUPPORT.ID
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.VENDOR
     *
     * @return the value of ISO_SUPPORT.VENDOR
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.VENDOR
     *
     * @param vendor the value for ISO_SUPPORT.VENDOR
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.NE
     *
     * @return the value of ISO_SUPPORT.NE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getNe() {
        return ne;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.NE
     *
     * @param ne the value for ISO_SUPPORT.NE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setNe(String ne) {
        this.ne = ne;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.NE_TYPE
     *
     * @return the value of ISO_SUPPORT.NE_TYPE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getNeType() {
        return neType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.NE_TYPE
     *
     * @param neType the value for ISO_SUPPORT.NE_TYPE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setNeType(String neType) {
        this.neType = neType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.BOARD_NAME
     *
     * @return the value of ISO_SUPPORT.BOARD_NAME
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.BOARD_NAME
     *
     * @param boardName the value for ISO_SUPPORT.BOARD_NAME
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.VALUE
     *
     * @return the value of ISO_SUPPORT.VALUE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public Integer getValue() {
        return value;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.VALUE
     *
     * @param value the value for ISO_SUPPORT.VALUE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.UNIT
     *
     * @return the value of ISO_SUPPORT.UNIT
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.UNIT
     *
     * @param unit the value for ISO_SUPPORT.UNIT
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.CREATED_BY
     *
     * @return the value of ISO_SUPPORT.CREATED_BY
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.CREATED_BY
     *
     * @param createdBy the value for ISO_SUPPORT.CREATED_BY
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.CREATE_DATE
     *
     * @return the value of ISO_SUPPORT.CREATE_DATE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.CREATE_DATE
     *
     * @param createDate the value for ISO_SUPPORT.CREATE_DATE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.MODIFIED_BY
     *
     * @return the value of ISO_SUPPORT.MODIFIED_BY
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.MODIFIED_BY
     *
     * @param modifiedBy the value for ISO_SUPPORT.MODIFIED_BY
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column ISO_SUPPORT.MODIFY_DATE
     *
     * @return the value of ISO_SUPPORT.MODIFY_DATE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column ISO_SUPPORT.MODIFY_DATE
     *
     * @param modifyDate the value for ISO_SUPPORT.MODIFY_DATE
     *
     * @ibatorgenerated Sat Sep 21 23:06:12 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}