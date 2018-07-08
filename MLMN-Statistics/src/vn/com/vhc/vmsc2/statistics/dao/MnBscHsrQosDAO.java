package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnBscHsrQos;

public interface MnBscHsrQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    void insert(MnBscHsrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    void insertSelective(MnBscHsrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    MnBscHsrQos selectByPrimaryKey(String bscid, Integer month, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int updateByPrimaryKeySelective(MnBscHsrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_BSC_HSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:13:04 ICT 2010
     */
    int updateByPrimaryKey(MnBscHsrQos record);
}