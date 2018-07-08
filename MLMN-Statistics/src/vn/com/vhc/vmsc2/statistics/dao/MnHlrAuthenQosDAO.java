package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnHlrAuthenQos;

public interface MnHlrAuthenQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    int deleteByPrimaryKey(String hlrid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    void insert(MnHlrAuthenQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    void insertSelective(MnHlrAuthenQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    MnHlrAuthenQos selectByPrimaryKey(String hlrid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    int updateByPrimaryKeySelective(MnHlrAuthenQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_HLR_AUTHEN_QOS
     *
     * @ibatorgenerated Thu Oct 21 14:03:01 ICT 2010
     */
    int updateByPrimaryKey(MnHlrAuthenQos record);
}