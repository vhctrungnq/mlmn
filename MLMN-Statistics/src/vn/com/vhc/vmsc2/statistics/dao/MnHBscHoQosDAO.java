package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnHBscHoQos;

public interface MnHBscHoQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    void insert(MnHBscHoQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    void insertSelective(MnHBscHoQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    MnHBscHoQos selectByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int updateByPrimaryKeySelective(MnHBscHoQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_H_BSC_HO_QOS
     *
     * @ibatorgenerated Wed Nov 10 10:03:01 ICT 2010
     */
    int updateByPrimaryKey(MnHBscHoQos record);
}