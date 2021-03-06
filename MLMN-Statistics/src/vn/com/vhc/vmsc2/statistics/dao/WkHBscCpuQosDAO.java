package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.WkHBscCpuQos;

public interface WkHBscCpuQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:21 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:21 ICT 2010
     */
    void insert(WkHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:21 ICT 2010
     */
    void insertSelective(WkHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:21 ICT 2010
     */
    WkHBscCpuQos selectByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:21 ICT 2010
     */
    int updateByPrimaryKeySelective(WkHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:21 ICT 2010
     */
    int updateByPrimaryKey(WkHBscCpuQos record);
}