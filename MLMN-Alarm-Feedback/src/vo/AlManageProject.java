package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class AlManageProject implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.ID
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.PROJECT_CODE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    @NotEmpty()
    private String projectCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.PROJECT_NAME
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    @NotEmpty()
    private String projectName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.PROJECT_TYPE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String projectType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.START_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date startDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.END_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date endDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.CREATED_BY
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.CREATE_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.MODIFIED_BY
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.MODIFY_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.VENDOR
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String vendor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.TYPE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String type;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.TEAM
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String team;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.SUPERVISOR
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String supervisor;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.SITE_NAME
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String siteName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.NODE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String node;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date deliveryPlanDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_RATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String deliveryPlanRate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_VENDOR_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String deliveryPlanVendorStaff;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_VMS_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String deliveryPlanVmsStaff;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String deliveryPlanDescription;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date installationPlanDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_RATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Integer installationPlanRate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_STATUS
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String installationPlanStatus;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_VENDOR_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String installationPlanVendorStaff;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_VMS_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String installationPlanVmsStaff;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String installationPlanDescription;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.COMMISSIONING_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date commissioningPlan;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.INTEGRATION_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date integrationPlan;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.ACCEPTANCE_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private Date acceptancePlan;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.COMMISSIONING_RESPONSIBLE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String commissioningResponsible;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.ATND
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String atnd;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.MANAGER
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String manager;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column AL_MANAGE_PROJECT.SUPPORTER
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private String supporter;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.ID
     *
     * @return the value of AL_MANAGE_PROJECT.ID
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.ID
     *
     * @param id the value for AL_MANAGE_PROJECT.ID
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.PROJECT_CODE
     *
     * @return the value of AL_MANAGE_PROJECT.PROJECT_CODE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.PROJECT_CODE
     *
     * @param projectCode the value for AL_MANAGE_PROJECT.PROJECT_CODE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.PROJECT_NAME
     *
     * @return the value of AL_MANAGE_PROJECT.PROJECT_NAME
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.PROJECT_NAME
     *
     * @param projectName the value for AL_MANAGE_PROJECT.PROJECT_NAME
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.PROJECT_TYPE
     *
     * @return the value of AL_MANAGE_PROJECT.PROJECT_TYPE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getProjectType() {
        return projectType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.PROJECT_TYPE
     *
     * @param projectType the value for AL_MANAGE_PROJECT.PROJECT_TYPE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.START_DATE
     *
     * @return the value of AL_MANAGE_PROJECT.START_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.START_DATE
     *
     * @param startDate the value for AL_MANAGE_PROJECT.START_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.END_DATE
     *
     * @return the value of AL_MANAGE_PROJECT.END_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.END_DATE
     *
     * @param endDate the value for AL_MANAGE_PROJECT.END_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.DESCRIPTION
     *
     * @return the value of AL_MANAGE_PROJECT.DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.DESCRIPTION
     *
     * @param description the value for AL_MANAGE_PROJECT.DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.CREATED_BY
     *
     * @return the value of AL_MANAGE_PROJECT.CREATED_BY
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.CREATED_BY
     *
     * @param createdBy the value for AL_MANAGE_PROJECT.CREATED_BY
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.CREATE_DATE
     *
     * @return the value of AL_MANAGE_PROJECT.CREATE_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.CREATE_DATE
     *
     * @param createDate the value for AL_MANAGE_PROJECT.CREATE_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.MODIFIED_BY
     *
     * @return the value of AL_MANAGE_PROJECT.MODIFIED_BY
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.MODIFIED_BY
     *
     * @param modifiedBy the value for AL_MANAGE_PROJECT.MODIFIED_BY
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.MODIFY_DATE
     *
     * @return the value of AL_MANAGE_PROJECT.MODIFY_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.MODIFY_DATE
     *
     * @param modifyDate the value for AL_MANAGE_PROJECT.MODIFY_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.VENDOR
     *
     * @return the value of AL_MANAGE_PROJECT.VENDOR
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.VENDOR
     *
     * @param vendor the value for AL_MANAGE_PROJECT.VENDOR
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.TYPE
     *
     * @return the value of AL_MANAGE_PROJECT.TYPE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.TYPE
     *
     * @param type the value for AL_MANAGE_PROJECT.TYPE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.TEAM
     *
     * @return the value of AL_MANAGE_PROJECT.TEAM
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getTeam() {
        return team;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.TEAM
     *
     * @param team the value for AL_MANAGE_PROJECT.TEAM
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.SUPERVISOR
     *
     * @return the value of AL_MANAGE_PROJECT.SUPERVISOR
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.SUPERVISOR
     *
     * @param supervisor the value for AL_MANAGE_PROJECT.SUPERVISOR
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.SITE_NAME
     *
     * @return the value of AL_MANAGE_PROJECT.SITE_NAME
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.SITE_NAME
     *
     * @param siteName the value for AL_MANAGE_PROJECT.SITE_NAME
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.NODE
     *
     * @return the value of AL_MANAGE_PROJECT.NODE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getNode() {
        return node;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.NODE
     *
     * @param node the value for AL_MANAGE_PROJECT.NODE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setNode(String node) {
        this.node = node;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_DATE
     *
     * @return the value of AL_MANAGE_PROJECT.DELIVERY_PLAN_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getDeliveryPlanDate() {
        return deliveryPlanDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_DATE
     *
     * @param deliveryPlanDate the value for AL_MANAGE_PROJECT.DELIVERY_PLAN_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setDeliveryPlanDate(Date deliveryPlanDate) {
        this.deliveryPlanDate = deliveryPlanDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_RATE
     *
     * @return the value of AL_MANAGE_PROJECT.DELIVERY_PLAN_RATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getDeliveryPlanRate() {
        return deliveryPlanRate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_RATE
     *
     * @param deliveryPlanRate the value for AL_MANAGE_PROJECT.DELIVERY_PLAN_RATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setDeliveryPlanRate(String deliveryPlanRate) {
        this.deliveryPlanRate = deliveryPlanRate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_VENDOR_STAFF
     *
     * @return the value of AL_MANAGE_PROJECT.DELIVERY_PLAN_VENDOR_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getDeliveryPlanVendorStaff() {
        return deliveryPlanVendorStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_VENDOR_STAFF
     *
     * @param deliveryPlanVendorStaff the value for AL_MANAGE_PROJECT.DELIVERY_PLAN_VENDOR_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setDeliveryPlanVendorStaff(String deliveryPlanVendorStaff) {
        this.deliveryPlanVendorStaff = deliveryPlanVendorStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_VMS_STAFF
     *
     * @return the value of AL_MANAGE_PROJECT.DELIVERY_PLAN_VMS_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getDeliveryPlanVmsStaff() {
        return deliveryPlanVmsStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_VMS_STAFF
     *
     * @param deliveryPlanVmsStaff the value for AL_MANAGE_PROJECT.DELIVERY_PLAN_VMS_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setDeliveryPlanVmsStaff(String deliveryPlanVmsStaff) {
        this.deliveryPlanVmsStaff = deliveryPlanVmsStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_DESCRIPTION
     *
     * @return the value of AL_MANAGE_PROJECT.DELIVERY_PLAN_DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getDeliveryPlanDescription() {
        return deliveryPlanDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.DELIVERY_PLAN_DESCRIPTION
     *
     * @param deliveryPlanDescription the value for AL_MANAGE_PROJECT.DELIVERY_PLAN_DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setDeliveryPlanDescription(String deliveryPlanDescription) {
        this.deliveryPlanDescription = deliveryPlanDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_DATE
     *
     * @return the value of AL_MANAGE_PROJECT.INSTALLATION_PLAN_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getInstallationPlanDate() {
        return installationPlanDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_DATE
     *
     * @param installationPlanDate the value for AL_MANAGE_PROJECT.INSTALLATION_PLAN_DATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setInstallationPlanDate(Date installationPlanDate) {
        this.installationPlanDate = installationPlanDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_RATE
     *
     * @return the value of AL_MANAGE_PROJECT.INSTALLATION_PLAN_RATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Integer getInstallationPlanRate() {
        return installationPlanRate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_RATE
     *
     * @param installationPlanRate the value for AL_MANAGE_PROJECT.INSTALLATION_PLAN_RATE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setInstallationPlanRate(Integer installationPlanRate) {
        this.installationPlanRate = installationPlanRate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_STATUS
     *
     * @return the value of AL_MANAGE_PROJECT.INSTALLATION_PLAN_STATUS
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getInstallationPlanStatus() {
        return installationPlanStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_STATUS
     *
     * @param installationPlanStatus the value for AL_MANAGE_PROJECT.INSTALLATION_PLAN_STATUS
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setInstallationPlanStatus(String installationPlanStatus) {
        this.installationPlanStatus = installationPlanStatus;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_VENDOR_STAFF
     *
     * @return the value of AL_MANAGE_PROJECT.INSTALLATION_PLAN_VENDOR_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getInstallationPlanVendorStaff() {
        return installationPlanVendorStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_VENDOR_STAFF
     *
     * @param installationPlanVendorStaff the value for AL_MANAGE_PROJECT.INSTALLATION_PLAN_VENDOR_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setInstallationPlanVendorStaff(String installationPlanVendorStaff) {
        this.installationPlanVendorStaff = installationPlanVendorStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_VMS_STAFF
     *
     * @return the value of AL_MANAGE_PROJECT.INSTALLATION_PLAN_VMS_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getInstallationPlanVmsStaff() {
        return installationPlanVmsStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_VMS_STAFF
     *
     * @param installationPlanVmsStaff the value for AL_MANAGE_PROJECT.INSTALLATION_PLAN_VMS_STAFF
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setInstallationPlanVmsStaff(String installationPlanVmsStaff) {
        this.installationPlanVmsStaff = installationPlanVmsStaff;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_DESCRIPTION
     *
     * @return the value of AL_MANAGE_PROJECT.INSTALLATION_PLAN_DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getInstallationPlanDescription() {
        return installationPlanDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INSTALLATION_PLAN_DESCRIPTION
     *
     * @param installationPlanDescription the value for AL_MANAGE_PROJECT.INSTALLATION_PLAN_DESCRIPTION
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setInstallationPlanDescription(String installationPlanDescription) {
        this.installationPlanDescription = installationPlanDescription;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.COMMISSIONING_PLAN
     *
     * @return the value of AL_MANAGE_PROJECT.COMMISSIONING_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getCommissioningPlan() {
        return commissioningPlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.COMMISSIONING_PLAN
     *
     * @param commissioningPlan the value for AL_MANAGE_PROJECT.COMMISSIONING_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setCommissioningPlan(Date commissioningPlan) {
        this.commissioningPlan = commissioningPlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.INTEGRATION_PLAN
     *
     * @return the value of AL_MANAGE_PROJECT.INTEGRATION_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getIntegrationPlan() {
        return integrationPlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.INTEGRATION_PLAN
     *
     * @param integrationPlan the value for AL_MANAGE_PROJECT.INTEGRATION_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setIntegrationPlan(Date integrationPlan) {
        this.integrationPlan = integrationPlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.ACCEPTANCE_PLAN
     *
     * @return the value of AL_MANAGE_PROJECT.ACCEPTANCE_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public Date getAcceptancePlan() {
        return acceptancePlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.ACCEPTANCE_PLAN
     *
     * @param acceptancePlan the value for AL_MANAGE_PROJECT.ACCEPTANCE_PLAN
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setAcceptancePlan(Date acceptancePlan) {
        this.acceptancePlan = acceptancePlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.COMMISSIONING_RESPONSIBLE
     *
     * @return the value of AL_MANAGE_PROJECT.COMMISSIONING_RESPONSIBLE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getCommissioningResponsible() {
        return commissioningResponsible;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.COMMISSIONING_RESPONSIBLE
     *
     * @param commissioningResponsible the value for AL_MANAGE_PROJECT.COMMISSIONING_RESPONSIBLE
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setCommissioningResponsible(String commissioningResponsible) {
        this.commissioningResponsible = commissioningResponsible;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.ATND
     *
     * @return the value of AL_MANAGE_PROJECT.ATND
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getAtnd() {
        return atnd;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.ATND
     *
     * @param atnd the value for AL_MANAGE_PROJECT.ATND
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setAtnd(String atnd) {
        this.atnd = atnd;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.MANAGER
     *
     * @return the value of AL_MANAGE_PROJECT.MANAGER
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getManager() {
        return manager;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.MANAGER
     *
     * @param manager the value for AL_MANAGE_PROJECT.MANAGER
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column AL_MANAGE_PROJECT.SUPPORTER
     *
     * @return the value of AL_MANAGE_PROJECT.SUPPORTER
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public String getSupporter() {
        return supporter;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column AL_MANAGE_PROJECT.SUPPORTER
     *
     * @param supporter the value for AL_MANAGE_PROJECT.SUPPORTER
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void setSupporter(String supporter) {
        this.supporter = supporter;
    }
    public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	private String region;
}