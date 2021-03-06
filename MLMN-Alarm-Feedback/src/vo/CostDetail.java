package vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CostDetail implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.ID
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.EXPENSES_CODE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String expensesCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.WORK_NAME
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    @NotEmpty
    private String workName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.COST_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    @NotNull
    private Integer costYear;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DEPARTMENT_ID
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    @NotEmpty
    private String departmentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.TASK_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String taskNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DELIVERY_PLAN_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double deliveryPlanYear;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.ADJUSTMENT_PLAN_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double adjustmentPlanYear;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DONE_TO_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double doneToLastm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DELIVERY_PLAN_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double deliveryPlanCurrentm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DONE_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double doneCurrentm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.COMULATIVE_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double comulativeCurrentm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.REMAINING_COST
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double remainingCost;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.RATE_DONE_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double rateDoneLastm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.LEADER_RESPONSIVE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String leaderResponsive;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.JOB_CLASSIFICATION
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String jobClassification;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.REASON_NOT_DONE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String reasonNotDone;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.ADJUST_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Double adjustLastm;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.STATUS_PLAN
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String statusPlan;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.CONTRACT_NAME
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String contractName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.CONTRACT_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Integer contractNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.CONTRACT_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Date contractDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.CONTRACT_TYPE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String contractType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DEPARTMENT_DONE_CONTRACT
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String departmentDoneContract;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.WORK_CODE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String workCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.DESCRIPTION
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.CREATED_BY
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.CREATE_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.MODIFIED_BY
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.MODIFY_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.ROOT_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private Integer rootNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.WORK_SUPER
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */

    private Integer workSuper;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.COST_MONTH
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    @NotNull
    private Integer costMonth;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_DETAIL.COST_TYPE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private String workId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table COST_DETAIL
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.ID
     *
     * @return the value of COST_DETAIL.ID
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.ID
     *
     * @param id the value for COST_DETAIL.ID
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.EXPENSES_CODE
     *
     * @return the value of COST_DETAIL.EXPENSES_CODE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getExpensesCode() {
        return expensesCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.EXPENSES_CODE
     *
     * @param expensesCode the value for COST_DETAIL.EXPENSES_CODE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setExpensesCode(String expensesCode) {
        this.expensesCode = expensesCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.WORK_NAME
     *
     * @return the value of COST_DETAIL.WORK_NAME
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getWorkName() {
        return workName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.WORK_NAME
     *
     * @param workName the value for COST_DETAIL.WORK_NAME
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.COST_YEAR
     *
     * @return the value of COST_DETAIL.COST_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Integer getCostYear() {
        return costYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.COST_YEAR
     *
     * @param costYear the value for COST_DETAIL.COST_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setCostYear(Integer costYear) {
        this.costYear = costYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DEPARTMENT_ID
     *
     * @return the value of COST_DETAIL.DEPARTMENT_ID
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DEPARTMENT_ID
     *
     * @param departmentId the value for COST_DETAIL.DEPARTMENT_ID
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.TASK_NO
     *
     * @return the value of COST_DETAIL.TASK_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getTaskNo() {
        return taskNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.TASK_NO
     *
     * @param taskNo the value for COST_DETAIL.TASK_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DELIVERY_PLAN_YEAR
     *
     * @return the value of COST_DETAIL.DELIVERY_PLAN_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getDeliveryPlanYear() {
        return deliveryPlanYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DELIVERY_PLAN_YEAR
     *
     * @param deliveryPlanYear the value for COST_DETAIL.DELIVERY_PLAN_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDeliveryPlanYear(Double deliveryPlanYear) {
        this.deliveryPlanYear = deliveryPlanYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.ADJUSTMENT_PLAN_YEAR
     *
     * @return the value of COST_DETAIL.ADJUSTMENT_PLAN_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getAdjustmentPlanYear() {
        return adjustmentPlanYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.ADJUSTMENT_PLAN_YEAR
     *
     * @param adjustmentPlanYear the value for COST_DETAIL.ADJUSTMENT_PLAN_YEAR
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setAdjustmentPlanYear(Double adjustmentPlanYear) {
        this.adjustmentPlanYear = adjustmentPlanYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DONE_TO_LASTM
     *
     * @return the value of COST_DETAIL.DONE_TO_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getDoneToLastm() {
        return doneToLastm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DONE_TO_LASTM
     *
     * @param doneToLastm the value for COST_DETAIL.DONE_TO_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDoneToLastm(Double doneToLastm) {
        this.doneToLastm = doneToLastm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DELIVERY_PLAN_CURRENTM
     *
     * @return the value of COST_DETAIL.DELIVERY_PLAN_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getDeliveryPlanCurrentm() {
        return deliveryPlanCurrentm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DELIVERY_PLAN_CURRENTM
     *
     * @param deliveryPlanCurrentm the value for COST_DETAIL.DELIVERY_PLAN_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDeliveryPlanCurrentm(Double deliveryPlanCurrentm) {
        this.deliveryPlanCurrentm = deliveryPlanCurrentm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DONE_CURRENTM
     *
     * @return the value of COST_DETAIL.DONE_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getDoneCurrentm() {
        return doneCurrentm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DONE_CURRENTM
     *
     * @param doneCurrentm the value for COST_DETAIL.DONE_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDoneCurrentm(Double doneCurrentm) {
        this.doneCurrentm = doneCurrentm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.COMULATIVE_CURRENTM
     *
     * @return the value of COST_DETAIL.COMULATIVE_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getComulativeCurrentm() {
        return comulativeCurrentm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.COMULATIVE_CURRENTM
     *
     * @param comulativeCurrentm the value for COST_DETAIL.COMULATIVE_CURRENTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setComulativeCurrentm(Double comulativeCurrentm) {
        this.comulativeCurrentm = comulativeCurrentm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.REMAINING_COST
     *
     * @return the value of COST_DETAIL.REMAINING_COST
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getRemainingCost() {
        return remainingCost;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.REMAINING_COST
     *
     * @param remainingCost the value for COST_DETAIL.REMAINING_COST
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setRemainingCost(Double remainingCost) {
        this.remainingCost = remainingCost;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.RATE_DONE_LASTM
     *
     * @return the value of COST_DETAIL.RATE_DONE_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getRateDoneLastm() {
        return rateDoneLastm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.RATE_DONE_LASTM
     *
     * @param rateDoneLastm the value for COST_DETAIL.RATE_DONE_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setRateDoneLastm(Double rateDoneLastm) {
    	
        this.rateDoneLastm = rateDoneLastm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.LEADER_RESPONSIVE
     *
     * @return the value of COST_DETAIL.LEADER_RESPONSIVE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getLeaderResponsive() {
        return leaderResponsive;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.LEADER_RESPONSIVE
     *
     * @param leaderResponsive the value for COST_DETAIL.LEADER_RESPONSIVE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setLeaderResponsive(String leaderResponsive) {
        this.leaderResponsive = leaderResponsive;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.JOB_CLASSIFICATION
     *
     * @return the value of COST_DETAIL.JOB_CLASSIFICATION
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getJobClassification() {
        return jobClassification;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.JOB_CLASSIFICATION
     *
     * @param jobClassification the value for COST_DETAIL.JOB_CLASSIFICATION
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setJobClassification(String jobClassification) {
        this.jobClassification = jobClassification;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.REASON_NOT_DONE
     *
     * @return the value of COST_DETAIL.REASON_NOT_DONE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getReasonNotDone() {
        return reasonNotDone;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.REASON_NOT_DONE
     *
     * @param reasonNotDone the value for COST_DETAIL.REASON_NOT_DONE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setReasonNotDone(String reasonNotDone) {
        this.reasonNotDone = reasonNotDone;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.ADJUST_LASTM
     *
     * @return the value of COST_DETAIL.ADJUST_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Double getAdjustLastm() {
        return adjustLastm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.ADJUST_LASTM
     *
     * @param adjustLastm the value for COST_DETAIL.ADJUST_LASTM
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setAdjustLastm(Double adjustLastm) {
        this.adjustLastm = adjustLastm;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.STATUS_PLAN
     *
     * @return the value of COST_DETAIL.STATUS_PLAN
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getStatusPlan() {
        return statusPlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.STATUS_PLAN
     *
     * @param statusPlan the value for COST_DETAIL.STATUS_PLAN
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setStatusPlan(String statusPlan) {
        this.statusPlan = statusPlan;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.CONTRACT_NAME
     *
     * @return the value of COST_DETAIL.CONTRACT_NAME
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.CONTRACT_NAME
     *
     * @param contractName the value for COST_DETAIL.CONTRACT_NAME
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.CONTRACT_NO
     *
     * @return the value of COST_DETAIL.CONTRACT_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Integer getContractNo() {
        return contractNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.CONTRACT_NO
     *
     * @param contractNo the value for COST_DETAIL.CONTRACT_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setContractNo(Integer contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.CONTRACT_DATE
     *
     * @return the value of COST_DETAIL.CONTRACT_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Date getContractDate() {
        return contractDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.CONTRACT_DATE
     *
     * @param contractDate the value for COST_DETAIL.CONTRACT_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.CONTRACT_TYPE
     *
     * @return the value of COST_DETAIL.CONTRACT_TYPE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.CONTRACT_TYPE
     *
     * @param contractType the value for COST_DETAIL.CONTRACT_TYPE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DEPARTMENT_DONE_CONTRACT
     *
     * @return the value of COST_DETAIL.DEPARTMENT_DONE_CONTRACT
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getDepartmentDoneContract() {
        return departmentDoneContract;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DEPARTMENT_DONE_CONTRACT
     *
     * @param departmentDoneContract the value for COST_DETAIL.DEPARTMENT_DONE_CONTRACT
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDepartmentDoneContract(String departmentDoneContract) {
        this.departmentDoneContract = departmentDoneContract;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.WORK_CODE
     *
     * @return the value of COST_DETAIL.WORK_CODE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getWorkCode() {
        return workCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.WORK_CODE
     *
     * @param workCode the value for COST_DETAIL.WORK_CODE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.DESCRIPTION
     *
     * @return the value of COST_DETAIL.DESCRIPTION
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.DESCRIPTION
     *
     * @param description the value for COST_DETAIL.DESCRIPTION
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.CREATED_BY
     *
     * @return the value of COST_DETAIL.CREATED_BY
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.CREATED_BY
     *
     * @param createdBy the value for COST_DETAIL.CREATED_BY
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.CREATE_DATE
     *
     * @return the value of COST_DETAIL.CREATE_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.CREATE_DATE
     *
     * @param createDate the value for COST_DETAIL.CREATE_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.MODIFIED_BY
     *
     * @return the value of COST_DETAIL.MODIFIED_BY
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.MODIFIED_BY
     *
     * @param modifiedBy the value for COST_DETAIL.MODIFIED_BY
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.MODIFY_DATE
     *
     * @return the value of COST_DETAIL.MODIFY_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.MODIFY_DATE
     *
     * @param modifyDate the value for COST_DETAIL.MODIFY_DATE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.ROOT_NO
     *
     * @return the value of COST_DETAIL.ROOT_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Integer getRootNo() {
        return rootNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.ROOT_NO
     *
     * @param rootNo the value for COST_DETAIL.ROOT_NO
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setRootNo(Integer rootNo) {
        this.rootNo = rootNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.WORK_SUPER
     *
     * @return the value of COST_DETAIL.WORK_SUPER
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Integer getWorkSuper() {
        return workSuper;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.WORK_SUPER
     *
     * @param workSuper the value for COST_DETAIL.WORK_SUPER
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setWorkSuper(Integer workSuper) {
        this.workSuper = workSuper;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.COST_MONTH
     *
     * @return the value of COST_DETAIL.COST_MONTH
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public Integer getCostMonth() {
        return costMonth;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.COST_MONTH
     *
     * @param costMonth the value for COST_DETAIL.COST_MONTH
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setCostMonth(Integer costMonth) {
        this.costMonth = costMonth;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_DETAIL.COST_TYPE
     *
     * @return the value of COST_DETAIL.COST_TYPE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_DETAIL.COST_TYPE
     *
     * @param workId the value for COST_DETAIL.COST_TYPE
     *
     * @ibatorgenerated Wed May 15 15:26:21 ICT 2013
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }
    
    private String expensesName;
  	
  	private String deptName;
  	
    public String getExpensesName() {
  		return expensesName;
  	}

  	public void setExpensesName(String expensesName) {
  		this.expensesName = expensesName;
  	}

  	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
  	public String getRateDoneLastmStr()
  	{
  		/*NumberFormat defaultFormat = NumberFormat.getPercentInstance();
  		defaultFormat.setMaximumFractionDigits(1);*/
  		if (getRateDoneLastm()!=null)
  		{
  			DecimalFormat df = new DecimalFormat("#.##");
  	    	return df.format(rateDoneLastm)+"%";
  		}
  		else
  		{
  			return "";
  		}
  	}
  	
  	public String getContractDateStr()
    {
    	if (contractDate!=null)
    		return (new SimpleDateFormat("dd/MM/yyyy").format(contractDate));
    		
    	return null;
    }
  	
  	
  	public Double getComulativeLasttm() {
		return comulativeLasttm;
	}

	public void setComulativeLasttm(Double comulativeLasttm) {
		this.comulativeLasttm = comulativeLasttm;
	}

	private Double comulativeLasttm;
  	
}