package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyStpDestinationQos;

public interface DyStpDestinationQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    void insert(DyStpDestinationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    void insertSelective(DyStpDestinationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    DyStpDestinationQos selectByPrimaryKey(Date day, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int updateByPrimaryKeySelective(DyStpDestinationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int updateByPrimaryKey(DyStpDestinationQos record);
}