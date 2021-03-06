package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.HrStpRetransmitionQos;

public interface HrStpRetransmitionQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_RETRANSMITION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    int deleteByPrimaryKey(Date day, Integer hour, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_RETRANSMITION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    void insert(HrStpRetransmitionQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_RETRANSMITION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    void insertSelective(HrStpRetransmitionQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_RETRANSMITION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    HrStpRetransmitionQos selectByPrimaryKey(Date day, Integer hour, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_RETRANSMITION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    int updateByPrimaryKeySelective(HrStpRetransmitionQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_RETRANSMITION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    int updateByPrimaryKey(HrStpRetransmitionQos record);
}