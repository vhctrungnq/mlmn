package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscDcrsQos;


public interface VRpDyBscDcrsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:41 ICT 2011
     */
    void insert(VRpDyBscDcrsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:41 ICT 2011
     */
    void insertSelective(VRpDyBscDcrsQos record);
    List<VRpDyBscDcrsQos> filter(String startDate, String endDate, String bscid, String region);
}