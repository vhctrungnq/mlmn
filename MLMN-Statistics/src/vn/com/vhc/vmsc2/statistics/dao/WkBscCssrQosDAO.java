package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.WkBscCssrQos;

public interface WkBscCssrQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    void insert(WkBscCssrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    void insertSelective(WkBscCssrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    WkBscCssrQos selectByPrimaryKey(String bscid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int updateByPrimaryKeySelective(WkBscCssrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Oct 19 14:12:31 ICT 2010
     */
    int updateByPrimaryKey(WkBscCssrQos record);
}