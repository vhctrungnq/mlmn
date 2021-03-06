package vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class CableTransmissionE1 implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.ID
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private Integer id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.DIRECTION_ID
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    @NotEmpty()
    private String directionId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.RP
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String rp;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.EM
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String em;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.DEV
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    @NotEmpty()
    private String dev;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.SNT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String snt;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.DIP
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String dip;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.SNTINL
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private Integer sntinl;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.DDF_OUT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String ddfOut;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.STATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String state;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.PLANE_NEXT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String planeNext;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.NODE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String node;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.DIRECTION_TRANSMISSION
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String directionTransmission;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.TYPE_CAB
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String typeCab;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.CREATED_BY
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String createdBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.CREATE_DATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.MODIFIED_BY
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String modifiedBy;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.MODIFY_DATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private Date modifyDate;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column CABLE_TRANSMISSION_E1.DESCRIPTION
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table CABLE_TRANSMISSION_E1
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.ID
     *
     * @return the value of CABLE_TRANSMISSION_E1.ID
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.ID
     *
     * @param id the value for CABLE_TRANSMISSION_E1.ID
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.DIRECTION_ID
     *
     * @return the value of CABLE_TRANSMISSION_E1.DIRECTION_ID
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getDirectionId() {
        return directionId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.DIRECTION_ID
     *
     * @param directionId the value for CABLE_TRANSMISSION_E1.DIRECTION_ID
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setDirectionId(String directionId) {
        this.directionId = directionId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.RP
     *
     * @return the value of CABLE_TRANSMISSION_E1.RP
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getRp() {
        return rp;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.RP
     *
     * @param rp the value for CABLE_TRANSMISSION_E1.RP
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setRp(String rp) {
        this.rp = rp;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.EM
     *
     * @return the value of CABLE_TRANSMISSION_E1.EM
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getEm() {
        return em;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.EM
     *
     * @param em the value for CABLE_TRANSMISSION_E1.EM
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setEm(String em) {
        this.em = em;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.DEV
     *
     * @return the value of CABLE_TRANSMISSION_E1.DEV
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getDev() {
        return dev;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.DEV
     *
     * @param dev the value for CABLE_TRANSMISSION_E1.DEV
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setDev(String dev) {
        this.dev = dev;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.SNT
     *
     * @return the value of CABLE_TRANSMISSION_E1.SNT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getSnt() {
        return snt;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.SNT
     *
     * @param snt the value for CABLE_TRANSMISSION_E1.SNT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setSnt(String snt) {
        this.snt = snt;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.DIP
     *
     * @return the value of CABLE_TRANSMISSION_E1.DIP
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getDip() {
        return dip;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.DIP
     *
     * @param dip the value for CABLE_TRANSMISSION_E1.DIP
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setDip(String dip) {
        this.dip = dip;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.SNTINL
     *
     * @return the value of CABLE_TRANSMISSION_E1.SNTINL
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public Integer getSntinl() {
        return sntinl;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.SNTINL
     *
     * @param sntinl the value for CABLE_TRANSMISSION_E1.SNTINL
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setSntinl(Integer sntinl) {
        this.sntinl = sntinl;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.DDF_OUT
     *
     * @return the value of CABLE_TRANSMISSION_E1.DDF_OUT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getDdfOut() {
        return ddfOut;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.DDF_OUT
     *
     * @param ddfOut the value for CABLE_TRANSMISSION_E1.DDF_OUT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setDdfOut(String ddfOut) {
        this.ddfOut = ddfOut;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.STATE
     *
     * @return the value of CABLE_TRANSMISSION_E1.STATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.STATE
     *
     * @param state the value for CABLE_TRANSMISSION_E1.STATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.PLANE_NEXT
     *
     * @return the value of CABLE_TRANSMISSION_E1.PLANE_NEXT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getPlaneNext() {
        return planeNext;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.PLANE_NEXT
     *
     * @param planeNext the value for CABLE_TRANSMISSION_E1.PLANE_NEXT
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setPlaneNext(String planeNext) {
        this.planeNext = planeNext;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.NODE
     *
     * @return the value of CABLE_TRANSMISSION_E1.NODE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getNode() {
        return node;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.NODE
     *
     * @param node the value for CABLE_TRANSMISSION_E1.NODE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setNode(String node) {
        this.node = node;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.DIRECTION_TRANSMISSION
     *
     * @return the value of CABLE_TRANSMISSION_E1.DIRECTION_TRANSMISSION
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getDirectionTransmission() {
        return directionTransmission;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.DIRECTION_TRANSMISSION
     *
     * @param directionTransmission the value for CABLE_TRANSMISSION_E1.DIRECTION_TRANSMISSION
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setDirectionTransmission(String directionTransmission) {
        this.directionTransmission = directionTransmission;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.TYPE_CAB
     *
     * @return the value of CABLE_TRANSMISSION_E1.TYPE_CAB
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getTypeCab() {
        return typeCab;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.TYPE_CAB
     *
     * @param typeCab the value for CABLE_TRANSMISSION_E1.TYPE_CAB
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setTypeCab(String typeCab) {
        this.typeCab = typeCab;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.CREATED_BY
     *
     * @return the value of CABLE_TRANSMISSION_E1.CREATED_BY
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.CREATED_BY
     *
     * @param createdBy the value for CABLE_TRANSMISSION_E1.CREATED_BY
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.CREATE_DATE
     *
     * @return the value of CABLE_TRANSMISSION_E1.CREATE_DATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.CREATE_DATE
     *
     * @param createDate the value for CABLE_TRANSMISSION_E1.CREATE_DATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.MODIFIED_BY
     *
     * @return the value of CABLE_TRANSMISSION_E1.MODIFIED_BY
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.MODIFIED_BY
     *
     * @param modifiedBy the value for CABLE_TRANSMISSION_E1.MODIFIED_BY
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.MODIFY_DATE
     *
     * @return the value of CABLE_TRANSMISSION_E1.MODIFY_DATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.MODIFY_DATE
     *
     * @param modifyDate the value for CABLE_TRANSMISSION_E1.MODIFY_DATE
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CABLE_TRANSMISSION_E1.DESCRIPTION
     *
     * @return the value of CABLE_TRANSMISSION_E1.DESCRIPTION
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CABLE_TRANSMISSION_E1.DESCRIPTION
     *
     * @param description the value for CABLE_TRANSMISSION_E1.DESCRIPTION
     *
     * @ibatorgenerated Thu Mar 21 14:19:35 ICT 2013
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    private String sntinlStr;
    
    public String getSntinlStr() {
		return sntinlStr;
	}

	public void setSntinlStr(String sntinlStr) {
		this.sntinlStr = sntinlStr;
	}

	
}