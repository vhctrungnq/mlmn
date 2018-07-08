package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyHBscCpuQos;

public interface DyHBscCpuQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    void insert(DyHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    void insertSelective(DyHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    DyHBscCpuQos selectByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    int updateByPrimaryKeySelective(DyHBscCpuQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_H_BSC_CPU_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:02:41 ICT 2010
     */
    int updateByPrimaryKey(DyHBscCpuQos record);
}