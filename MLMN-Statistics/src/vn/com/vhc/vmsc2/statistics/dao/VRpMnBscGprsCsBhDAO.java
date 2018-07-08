package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscGprsCsBh;


public interface VRpMnBscGprsCsBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    void insert(VRpMnBscGprsCsBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    void insertSelective(VRpMnBscGprsCsBh record);
    
    List<VRpMnBscGprsCsBh> filter(Integer startMonth, Integer startYear, Integer endMonth,Integer endYear, String bscid, String region);
}