package vo;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;

public class CableLineAttributes implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.CABLE_LINE_ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    @NotEmpty()
    private String cableLineId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.CABLE_TYPE_ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    @NotEmpty()
    private String cableTypeId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.LABEL
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    @NotEmpty()
    private String label;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.VALUE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private String value;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.FIELD_COLUMN_KEY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    @NotEmpty()
    private String fieldColumnKey;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.ORDERING
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private Integer ordering;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.CREATED_BY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.CREATE_DATE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.MODIFIED_BY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_LINE_ATTIBUTES.MODIFY_DATE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CABLE_LINE_ATTIBUTES
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.ID
     *
     * @return the value of CABLE_LINE_ATTIBUTES.ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.ID
     *
     * @param id the value for CABLE_LINE_ATTIBUTES.ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.CABLE_LINE_ID
     *
     * @return the value of CABLE_LINE_ATTIBUTES.CABLE_LINE_ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getCableLineId() {
        return cableLineId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.CABLE_LINE_ID
     *
     * @param cableLineId the value for CABLE_LINE_ATTIBUTES.CABLE_LINE_ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setCableLineId(String cableLineId) {
        this.cableLineId = cableLineId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.CABLE_TYPE_ID
     *
     * @return the value of CABLE_LINE_ATTIBUTES.CABLE_TYPE_ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getCableTypeId() {
        return cableTypeId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.CABLE_TYPE_ID
     *
     * @param cableTypeId the value for CABLE_LINE_ATTIBUTES.CABLE_TYPE_ID
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setCableTypeId(String cableTypeId) {
        this.cableTypeId = cableTypeId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.LABEL
     *
     * @return the value of CABLE_LINE_ATTIBUTES.LABEL
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.LABEL
     *
     * @param label the value for CABLE_LINE_ATTIBUTES.LABEL
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.VALUE
     *
     * @return the value of CABLE_LINE_ATTIBUTES.VALUE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.VALUE
     *
     * @param value the value for CABLE_LINE_ATTIBUTES.VALUE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.FIELD_COLUMN_KEY
     *
     * @return the value of CABLE_LINE_ATTIBUTES.FIELD_COLUMN_KEY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getFieldColumnKey() {
        return fieldColumnKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.FIELD_COLUMN_KEY
     *
     * @param fieldColumnKey the value for CABLE_LINE_ATTIBUTES.FIELD_COLUMN_KEY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setFieldColumnKey(String fieldColumnKey) {
        this.fieldColumnKey = fieldColumnKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.ORDERING
     *
     * @return the value of CABLE_LINE_ATTIBUTES.ORDERING
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.ORDERING
     *
     * @param ordering the value for CABLE_LINE_ATTIBUTES.ORDERING
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.CREATED_BY
     *
     * @return the value of CABLE_LINE_ATTIBUTES.CREATED_BY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.CREATED_BY
     *
     * @param createdBy the value for CABLE_LINE_ATTIBUTES.CREATED_BY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.CREATE_DATE
     *
     * @return the value of CABLE_LINE_ATTIBUTES.CREATE_DATE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.CREATE_DATE
     *
     * @param createDate the value for CABLE_LINE_ATTIBUTES.CREATE_DATE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.MODIFIED_BY
     *
     * @return the value of CABLE_LINE_ATTIBUTES.MODIFIED_BY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.MODIFIED_BY
     *
     * @param modifiedBy the value for CABLE_LINE_ATTIBUTES.MODIFIED_BY
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_LINE_ATTIBUTES.MODIFY_DATE
     *
     * @return the value of CABLE_LINE_ATTIBUTES.MODIFY_DATE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_LINE_ATTIBUTES.MODIFY_DATE
     *
     * @param modifyDate the value for CABLE_LINE_ATTIBUTES.MODIFY_DATE
     *
     * @ibatorgenerated Thu Dec 13 16:32:59 ICT 2012
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}