package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class HAlTransErrorType implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.ID
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.GROUP_NAME
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    @NotEmpty()
    private String groupName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.NAME
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    @NotEmpty()
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.DESCRIPTION
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.CREATE_DATE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.CREATED_BY
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.MODIFIED_BY
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column H_AL_TRANS_ERROR_TYPE.MODIFY_DATE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table H_AL_TRANS_ERROR_TYPE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.ID
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.ID
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.ID
     *
     * @param id the value for H_AL_TRANS_ERROR_TYPE.ID
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.GROUP_NAME
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.GROUP_NAME
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.GROUP_NAME
     *
     * @param groupName the value for H_AL_TRANS_ERROR_TYPE.GROUP_NAME
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.NAME
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.NAME
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.NAME
     *
     * @param name the value for H_AL_TRANS_ERROR_TYPE.NAME
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.DESCRIPTION
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.DESCRIPTION
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.DESCRIPTION
     *
     * @param description the value for H_AL_TRANS_ERROR_TYPE.DESCRIPTION
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.CREATE_DATE
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.CREATE_DATE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.CREATE_DATE
     *
     * @param createDate the value for H_AL_TRANS_ERROR_TYPE.CREATE_DATE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.CREATED_BY
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.CREATED_BY
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.CREATED_BY
     *
     * @param createdBy the value for H_AL_TRANS_ERROR_TYPE.CREATED_BY
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.MODIFIED_BY
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.MODIFIED_BY
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.MODIFIED_BY
     *
     * @param modifiedBy the value for H_AL_TRANS_ERROR_TYPE.MODIFIED_BY
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column H_AL_TRANS_ERROR_TYPE.MODIFY_DATE
     *
     * @return the value of H_AL_TRANS_ERROR_TYPE.MODIFY_DATE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column H_AL_TRANS_ERROR_TYPE.MODIFY_DATE
     *
     * @param modifyDate the value for H_AL_TRANS_ERROR_TYPE.MODIFY_DATE
     *
     * @ibatorgenerated Tue Feb 19 16:33:23 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}