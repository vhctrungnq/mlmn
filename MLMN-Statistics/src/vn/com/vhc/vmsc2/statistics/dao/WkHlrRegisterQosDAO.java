package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.WkHlrRegisterQos;

public interface WkHlrRegisterQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Mar 31 15:44:22 ICT 2011
     */
    int deleteByPrimaryKey(String hlrid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Mar 31 15:44:22 ICT 2011
     */
    void insert(WkHlrRegisterQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Mar 31 15:44:22 ICT 2011
     */
    void insertSelective(WkHlrRegisterQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Mar 31 15:44:22 ICT 2011
     */
    WkHlrRegisterQos selectByPrimaryKey(String hlrid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Mar 31 15:44:22 ICT 2011
     */
    int updateByPrimaryKeySelective(WkHlrRegisterQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Mar 31 15:44:22 ICT 2011
     */
    int updateByPrimaryKey(WkHlrRegisterQos record);
}