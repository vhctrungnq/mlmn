package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnStpM3perfQos;

public interface MnStpM3perfQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    int deleteByPrimaryKey(Integer month, String stpid, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    void insert(MnStpM3perfQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    void insertSelective(MnStpM3perfQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    MnStpM3perfQos selectByPrimaryKey(Integer month, String stpid, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    int updateByPrimaryKeySelective(MnStpM3perfQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    int updateByPrimaryKey(MnStpM3perfQos record);
}