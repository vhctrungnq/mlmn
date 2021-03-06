package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.WkStpDestinationQos;

public interface WkStpDestinationQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    int deleteByPrimaryKey(String stpid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    void insert(WkStpDestinationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    void insertSelective(WkStpDestinationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    WkStpDestinationQos selectByPrimaryKey(String stpid, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    int updateByPrimaryKeySelective(WkStpDestinationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    int updateByPrimaryKey(WkStpDestinationQos record);
}