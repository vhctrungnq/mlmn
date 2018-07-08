package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnHBscCpuQos;

public interface MnHBscCpuQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    void insert(MnHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    void insertSelective(MnHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    MnHBscCpuQos selectByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int updateByPrimaryKeySelective(MnHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int updateByPrimaryKey(MnHBscCpuQos record);
}