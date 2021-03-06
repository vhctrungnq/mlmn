package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.MnBscCploadQos;


public interface MnBscCploadQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    void insert(MnBscCploadQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    void insertSelective(MnBscCploadQos record);
    
    List<MnBscCploadQos> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String region);
}