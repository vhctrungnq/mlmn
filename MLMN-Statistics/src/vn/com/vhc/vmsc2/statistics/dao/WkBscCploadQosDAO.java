package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkBscCploadQos;


public interface WkBscCploadQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    void insert(WkBscCploadQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    void insertSelective(WkBscCploadQos record);
    
    List<WkBscCploadQos> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String region);
}