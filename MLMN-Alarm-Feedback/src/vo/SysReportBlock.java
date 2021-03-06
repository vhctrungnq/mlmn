package vo;

import java.io.Serializable;
import java.util.Date;

public class SysReportBlock implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.ID
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.REPORT_TYPE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String reportType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.REPORT_FUNCTION
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String reportFunction;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.BLOCK_ID
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String blockId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.BLOCK_NAME
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String blockName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.ORDERING
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private Integer ordering;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.ENABLE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String enable;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.SQL_WHERE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String sqlWhere;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.CREATE_DATE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.MODIFIED_DATE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private Date modifiedDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.CREATED_BY
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_REPORT_BLOCK.MODIFIED_BY
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_REPORT_BLOCK
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.ID
     *
     * @return the value of SYS_REPORT_BLOCK.ID
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.ID
     *
     * @param id the value for SYS_REPORT_BLOCK.ID
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.REPORT_TYPE
     *
     * @return the value of SYS_REPORT_BLOCK.REPORT_TYPE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.REPORT_TYPE
     *
     * @param reportType the value for SYS_REPORT_BLOCK.REPORT_TYPE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.REPORT_FUNCTION
     *
     * @return the value of SYS_REPORT_BLOCK.REPORT_FUNCTION
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getReportFunction() {
        return reportFunction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.REPORT_FUNCTION
     *
     * @param reportFunction the value for SYS_REPORT_BLOCK.REPORT_FUNCTION
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setReportFunction(String reportFunction) {
        this.reportFunction = reportFunction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.BLOCK_ID
     *
     * @return the value of SYS_REPORT_BLOCK.BLOCK_ID
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getBlockId() {
        return blockId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.BLOCK_ID
     *
     * @param blockId the value for SYS_REPORT_BLOCK.BLOCK_ID
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.BLOCK_NAME
     *
     * @return the value of SYS_REPORT_BLOCK.BLOCK_NAME
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getBlockName() {
        return blockName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.BLOCK_NAME
     *
     * @param blockName the value for SYS_REPORT_BLOCK.BLOCK_NAME
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.ORDERING
     *
     * @return the value of SYS_REPORT_BLOCK.ORDERING
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public Integer getOrdering() {
        return ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.ORDERING
     *
     * @param ordering the value for SYS_REPORT_BLOCK.ORDERING
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.ENABLE
     *
     * @return the value of SYS_REPORT_BLOCK.ENABLE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getEnable() {
        return enable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.ENABLE
     *
     * @param enable the value for SYS_REPORT_BLOCK.ENABLE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.SQL_WHERE
     *
     * @return the value of SYS_REPORT_BLOCK.SQL_WHERE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getSqlWhere() {
        return sqlWhere;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.SQL_WHERE
     *
     * @param sqlWhere the value for SYS_REPORT_BLOCK.SQL_WHERE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setSqlWhere(String sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.CREATE_DATE
     *
     * @return the value of SYS_REPORT_BLOCK.CREATE_DATE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.CREATE_DATE
     *
     * @param createDate the value for SYS_REPORT_BLOCK.CREATE_DATE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.MODIFIED_DATE
     *
     * @return the value of SYS_REPORT_BLOCK.MODIFIED_DATE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.MODIFIED_DATE
     *
     * @param modifiedDate the value for SYS_REPORT_BLOCK.MODIFIED_DATE
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.CREATED_BY
     *
     * @return the value of SYS_REPORT_BLOCK.CREATED_BY
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.CREATED_BY
     *
     * @param createdBy the value for SYS_REPORT_BLOCK.CREATED_BY
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_REPORT_BLOCK.MODIFIED_BY
     *
     * @return the value of SYS_REPORT_BLOCK.MODIFIED_BY
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_REPORT_BLOCK.MODIFIED_BY
     *
     * @param modifiedBy the value for SYS_REPORT_BLOCK.MODIFIED_BY
     *
     * @ibatorgenerated Wed Nov 18 16:53:58 ICT 2015
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public String getColumnsGroup() {
		return columnsGroup;
	}

	public void setColumnsGroup(String columnsGroup) {
		this.columnsGroup = columnsGroup;
	}

	private String columnsGroup;
	
	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	
	private String sqlQuery;
}