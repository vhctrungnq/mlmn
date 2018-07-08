package vn.com.vhc.vmsc2.statistics.dao;

import vn.com.vhc.vmsc2.statistics.domain.MnStpAssonciationQos;

public interface MnStpAssonciationQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    int deleteByPrimaryKey(Integer month, String stpid, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    void insert(MnStpAssonciationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    void insertSelective(MnStpAssonciationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    MnStpAssonciationQos selectByPrimaryKey(Integer month, String stpid, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    int updateByPrimaryKeySelective(MnStpAssonciationQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    int updateByPrimaryKey(MnStpAssonciationQos record);
}