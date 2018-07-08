package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.WkBscDcrsQos;

public interface WkBscDcrsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_DCRS_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_DCRS_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    void insert(WkBscDcrsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_DCRS_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    void insertSelective(WkBscDcrsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_DCRS_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    WkBscDcrsQos selectByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_DCRS_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int updateByPrimaryKeySelective(WkBscDcrsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_DCRS_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int updateByPrimaryKey(WkBscDcrsQos record);
}