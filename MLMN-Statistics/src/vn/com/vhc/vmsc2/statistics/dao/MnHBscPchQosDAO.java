package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnHBscPchQos;

public interface MnHBscPchQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    void insert(MnHBscPchQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    void insertSelective(MnHBscPchQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    MnHBscPchQos selectByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int updateByPrimaryKeySelective(MnHBscPchQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_PCH_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int updateByPrimaryKey(MnHBscPchQos record);
}