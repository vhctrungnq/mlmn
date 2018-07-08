package vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CableOdfTypes implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.ID
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.SCHEMA_NAME
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    @NotEmpty()
    private String schemaName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.LOCATION_PORT
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    @NotNull()
    private Integer locationPort;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.ORDERING
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private Integer ordering;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.CREATE_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_ODF_TYPES.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CABLE_ODF_TYPES
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.ID
     *
     * @return the value of CABLE_ODF_TYPES.ID
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.ID
     *
     * @param id the value for CABLE_ODF_TYPES.ID
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.SCHEMA_NAME
     *
     * @return the value of CABLE_ODF_TYPES.SCHEMA_NAME
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.SCHEMA_NAME
     *
     * @param schemaName the value for CABLE_ODF_TYPES.SCHEMA_NAME
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.LOCATION_PORT
     *
     * @return the value of CABLE_ODF_TYPES.LOCATION_PORT
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public Integer getLocationPort() {
        return locationPort;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.LOCATION_PORT
     *
     * @param locationPort the value for CABLE_ODF_TYPES.LOCATION_PORT
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setLocationPort(Integer locationPort) {
        this.locationPort = locationPort;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.ORDERING
     *
     * @return the value of CABLE_ODF_TYPES.ORDERING
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.ORDERING
     *
     * @param ordering the value for CABLE_ODF_TYPES.ORDERING
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.DESCRIPTION
     *
     * @return the value of CABLE_ODF_TYPES.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.DESCRIPTION
     *
     * @param description the value for CABLE_ODF_TYPES.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.CREATED_BY
     *
     * @return the value of CABLE_ODF_TYPES.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.CREATED_BY
     *
     * @param createdBy the value for CABLE_ODF_TYPES.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.CREATE_DATE
     *
     * @return the value of CABLE_ODF_TYPES.CREATE_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.CREATE_DATE
     *
     * @param createDate the value for CABLE_ODF_TYPES.CREATE_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.MODIFIED_BY
     *
     * @return the value of CABLE_ODF_TYPES.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.MODIFIED_BY
     *
     * @param modifiedBy the value for CABLE_ODF_TYPES.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_ODF_TYPES.MODIFY_DATE
     *
     * @return the value of CABLE_ODF_TYPES.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_ODF_TYPES.MODIFY_DATE
     *
     * @param modifyDate the value for CABLE_ODF_TYPES.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:05:29 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}