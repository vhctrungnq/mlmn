package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyHlrSubscribersQos;

public interface DyHlrSubscribersQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:15 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String hlrid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:15 ICT 2010
     */
    void insert(DyHlrSubscribersQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:15 ICT 2010
     */
    void insertSelective(DyHlrSubscribersQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:15 ICT 2010
     */
    DyHlrSubscribersQos selectByPrimaryKey(Date day, String hlrid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:15 ICT 2010
     */
    int updateByPrimaryKeySelective(DyHlrSubscribersQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_SUBSCRIBERS_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:15 ICT 2010
     */
    int updateByPrimaryKey(DyHlrSubscribersQos record);
}