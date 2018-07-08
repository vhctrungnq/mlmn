package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyBscCssrQos;

public interface DyBscCssrQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:34 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:34 ICT 2010
     */
    void insert(DyBscCssrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:34 ICT 2010
     */
    void insertSelective(DyBscCssrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:34 ICT 2010
     */
    DyBscCssrQos selectByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:34 ICT 2010
     */
    int updateByPrimaryKeySelective(DyBscCssrQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CSSR_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:34 ICT 2010
     */
    int updateByPrimaryKey(DyBscCssrQos record);
}