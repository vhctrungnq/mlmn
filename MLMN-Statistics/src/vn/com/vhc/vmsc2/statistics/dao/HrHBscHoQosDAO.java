package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.HrHBscHoQos;

public interface HrHBscHoQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    void insert(HrHBscHoQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    void insertSelective(HrHBscHoQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    HrHBscHoQos selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    int updateByPrimaryKeySelective(HrHBscHoQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    int updateByPrimaryKey(HrHBscHoQos record);
}