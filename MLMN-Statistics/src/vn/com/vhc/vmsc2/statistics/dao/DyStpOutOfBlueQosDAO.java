package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyStpOutOfBlueQos;

public interface DyStpOutOfBlueQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    void insert(DyStpOutOfBlueQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    void insertSelective(DyStpOutOfBlueQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    DyStpOutOfBlueQos selectByPrimaryKey(Date day, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int updateByPrimaryKeySelective(DyStpOutOfBlueQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int updateByPrimaryKey(DyStpOutOfBlueQos record);
}