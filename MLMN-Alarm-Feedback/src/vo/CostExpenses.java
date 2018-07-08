package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;


public class CostExpenses implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.ID
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.EXPENSES_CODE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    @NotEmpty()
    private String expensesCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.EXPENSES_NAME
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    @NotEmpty()
    private String expensesName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.EXPENSES_SUPER
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private String expensesSuper;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.IS_ENABLE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private String isEnable;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.DESCRIPTION
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.WORK_ID
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private String workId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.ORDERING
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private Integer ordering;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.CREATED_BY
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.CREATE_DATE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.MODIFIED_BY
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.MODIFY_DATE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column COST_EXPENSES.ROOT_NO
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private Integer rootNo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table COST_EXPENSES
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.ID
     *
     * @return the value of COST_EXPENSES.ID
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.ID
     *
     * @param id the value for COST_EXPENSES.ID
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.EXPENSES_CODE
     *
     * @return the value of COST_EXPENSES.EXPENSES_CODE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getExpensesCode() {
        return expensesCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.EXPENSES_CODE
     *
     * @param expensesCode the value for COST_EXPENSES.EXPENSES_CODE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setExpensesCode(String expensesCode) {
        this.expensesCode = expensesCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.EXPENSES_NAME
     *
     * @return the value of COST_EXPENSES.EXPENSES_NAME
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getExpensesName() {
        return expensesName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.EXPENSES_NAME
     *
     * @param expensesName the value for COST_EXPENSES.EXPENSES_NAME
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setExpensesName(String expensesName) {
        this.expensesName = expensesName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.EXPENSES_SUPER
     *
     * @return the value of COST_EXPENSES.EXPENSES_SUPER
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getExpensesSuper() {
        return expensesSuper;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.EXPENSES_SUPER
     *
     * @param expensesSuper the value for COST_EXPENSES.EXPENSES_SUPER
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setExpensesSuper(String expensesSuper) {
        this.expensesSuper = expensesSuper;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.IS_ENABLE
     *
     * @return the value of COST_EXPENSES.IS_ENABLE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getIsEnable() {
        return isEnable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.IS_ENABLE
     *
     * @param isEnable the value for COST_EXPENSES.IS_ENABLE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.DESCRIPTION
     *
     * @return the value of COST_EXPENSES.DESCRIPTION
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.DESCRIPTION
     *
     * @param description the value for COST_EXPENSES.DESCRIPTION
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.WORK_ID
     *
     * @return the value of COST_EXPENSES.WORK_ID
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.WORK_ID
     *
     * @param workId the value for COST_EXPENSES.WORK_ID
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.ORDERING
     *
     * @return the value of COST_EXPENSES.ORDERING
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.ORDERING
     *
     * @param ordering the value for COST_EXPENSES.ORDERING
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.CREATED_BY
     *
     * @return the value of COST_EXPENSES.CREATED_BY
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.CREATED_BY
     *
     * @param createdBy the value for COST_EXPENSES.CREATED_BY
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.CREATE_DATE
     *
     * @return the value of COST_EXPENSES.CREATE_DATE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.CREATE_DATE
     *
     * @param createDate the value for COST_EXPENSES.CREATE_DATE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.MODIFIED_BY
     *
     * @return the value of COST_EXPENSES.MODIFIED_BY
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.MODIFIED_BY
     *
     * @param modifiedBy the value for COST_EXPENSES.MODIFIED_BY
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.MODIFY_DATE
     *
     * @return the value of COST_EXPENSES.MODIFY_DATE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.MODIFY_DATE
     *
     * @param modifyDate the value for COST_EXPENSES.MODIFY_DATE
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column COST_EXPENSES.ROOT_NO
     *
     * @return the value of COST_EXPENSES.ROOT_NO
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public Integer getRootNo() {
        return rootNo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column COST_EXPENSES.ROOT_NO
     *
     * @param rootNo the value for COST_EXPENSES.ROOT_NO
     *
     * @ibatorgenerated Tue May 07 16:15:11 ICT 2013
     */
    public void setRootNo(Integer rootNo) {
        this.rootNo = rootNo;
    }
    
private String orderingStr;
    
    public String getOrderingStr() {
		return orderingStr;
	}

	public void setOrderingStr(String orderingStr) {
		this.orderingStr = orderingStr;
	}
}