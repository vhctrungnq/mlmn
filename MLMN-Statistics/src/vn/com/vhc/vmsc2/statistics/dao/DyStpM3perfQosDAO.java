package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;

import vn.com.vhc.vmsc2.statistics.domain.DyStpM3perfQos;

public interface DyStpM3perfQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int deleteByPrimaryKey(Date day, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    void insert(DyStpM3perfQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    void insertSelective(DyStpM3perfQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    DyStpM3perfQos selectByPrimaryKey(Date day, String stpid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int updateByPrimaryKeySelective(DyStpM3perfQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    int updateByPrimaryKey(DyStpM3perfQos record);
}