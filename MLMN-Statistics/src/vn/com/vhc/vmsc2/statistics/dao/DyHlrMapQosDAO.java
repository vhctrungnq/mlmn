package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyHlrMapQos;

public interface DyHlrMapQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String hlrid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    void insert(DyHlrMapQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    void insertSelective(DyHlrMapQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    DyHlrMapQos selectByPrimaryKey(Date day, String hlrid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    int updateByPrimaryKeySelective(DyHlrMapQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_MAP_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:22:00 ICT 2010
     */
    int updateByPrimaryKey(DyHlrMapQos record);
}