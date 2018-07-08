package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.HrHBscPchQos;

public interface HrHBscPchQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    void insert(HrHBscPchQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    void insertSelective(HrHBscPchQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    HrHBscPchQos selectByPrimaryKey(String bscid, Date day, Integer hour);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    int updateByPrimaryKeySelective(HrHBscPchQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:17 ICT 2010
     */
    int updateByPrimaryKey(HrHBscPchQos record);
}