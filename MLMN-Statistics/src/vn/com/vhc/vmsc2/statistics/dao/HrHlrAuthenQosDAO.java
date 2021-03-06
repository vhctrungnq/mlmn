package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.HrHlrAuthenQos;

public interface HrHlrAuthenQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:20:39 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String hlrid, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:20:39 ICT 2010
     */
    void insert(HrHlrAuthenQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:20:39 ICT 2010
     */
    void insertSelective(HrHlrAuthenQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:20:39 ICT 2010
     */
    HrHlrAuthenQos selectByPrimaryKey(Date day, String hlrid, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:20:39 ICT 2010
     */
    int updateByPrimaryKeySelective(HrHlrAuthenQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:20:39 ICT 2010
     */
    int updateByPrimaryKey(HrHlrAuthenQos record);
}