package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyHlrRegisterQos;

public interface DyHlrRegisterQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:38 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String hlrid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:38 ICT 2010
     */
    void insert(DyHlrRegisterQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:38 ICT 2010
     */
    void insertSelective(DyHlrRegisterQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:38 ICT 2010
     */
    DyHlrRegisterQos selectByPrimaryKey(Date day, String hlrid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:38 ICT 2010
     */
    int updateByPrimaryKeySelective(DyHlrRegisterQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_HLR_REGISTER_QOS
     *
     * @ibatorgenerated Thu Oct 14 15:21:38 ICT 2010
     */
    int updateByPrimaryKey(DyHlrRegisterQos record);
}