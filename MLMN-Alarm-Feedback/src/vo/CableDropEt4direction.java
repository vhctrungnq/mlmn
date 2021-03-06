package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class CableDropEt4direction implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.ID
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.CIRCUIT
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    @NotEmpty
    private String circuit;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.DIP_NAME
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String dipName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.DIRECTION
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String direction;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.DIPP
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String dipp;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.DDF_HEAD
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    @NotEmpty
    private String ddfHead;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.DIP_FINISH
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String dipFinish;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.STATUS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.CREAT_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private Date creatDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_DROP_ET4_DIRECTIONS.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CABLE_DROP_ET4_DIRECTIONS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.ID
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.ID
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.ID
     *
     * @param id the value for CABLE_DROP_ET4_DIRECTIONS.ID
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.CIRCUIT
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.CIRCUIT
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getCircuit() {
        return circuit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.CIRCUIT
     *
     * @param circuit the value for CABLE_DROP_ET4_DIRECTIONS.CIRCUIT
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIP_NAME
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.DIP_NAME
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getDipName() {
        return dipName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIP_NAME
     *
     * @param dipName the value for CABLE_DROP_ET4_DIRECTIONS.DIP_NAME
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setDipName(String dipName) {
        this.dipName = dipName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIRECTION
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.DIRECTION
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getDirection() {
        return direction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIRECTION
     *
     * @param direction the value for CABLE_DROP_ET4_DIRECTIONS.DIRECTION
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIPP
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.DIPP
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getDipp() {
        return dipp;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIPP
     *
     * @param dipp the value for CABLE_DROP_ET4_DIRECTIONS.DIPP
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setDipp(String dipp) {
        this.dipp = dipp;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.DDF_HEAD
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.DDF_HEAD
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getDdfHead() {
        return ddfHead;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.DDF_HEAD
     *
     * @param ddfHead the value for CABLE_DROP_ET4_DIRECTIONS.DDF_HEAD
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setDdfHead(String ddfHead) {
        this.ddfHead = ddfHead;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIP_FINISH
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.DIP_FINISH
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getDipFinish() {
        return dipFinish;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.DIP_FINISH
     *
     * @param dipFinish the value for CABLE_DROP_ET4_DIRECTIONS.DIP_FINISH
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setDipFinish(String dipFinish) {
        this.dipFinish = dipFinish;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.DESCRIPTION
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.DESCRIPTION
     *
     * @param description the value for CABLE_DROP_ET4_DIRECTIONS.DESCRIPTION
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.STATUS
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.STATUS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.STATUS
     *
     * @param status the value for CABLE_DROP_ET4_DIRECTIONS.STATUS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.CREATED_BY
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.CREATED_BY
     *
     * @param createdBy the value for CABLE_DROP_ET4_DIRECTIONS.CREATED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.CREAT_DATE
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.CREAT_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public Date getCreatDate() {
        return creatDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.CREAT_DATE
     *
     * @param creatDate the value for CABLE_DROP_ET4_DIRECTIONS.CREAT_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.MODIFIED_BY
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.MODIFIED_BY
     *
     * @param modifiedBy the value for CABLE_DROP_ET4_DIRECTIONS.MODIFIED_BY
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_DROP_ET4_DIRECTIONS.MODIFY_DATE
     *
     * @return the value of CABLE_DROP_ET4_DIRECTIONS.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_DROP_ET4_DIRECTIONS.MODIFY_DATE
     *
     * @param modifyDate the value for CABLE_DROP_ET4_DIRECTIONS.MODIFY_DATE
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    @NotEmpty
    private String ddfFinish;
    
    public String getDdfFinish() {
        return ddfFinish;
    }
    
    public void setDdfFinish(String ddfFinish) {
        this.ddfFinish = ddfFinish;
    }
    
    
}