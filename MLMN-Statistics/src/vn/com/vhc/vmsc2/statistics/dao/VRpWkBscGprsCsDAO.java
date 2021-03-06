package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscGprsCs;


public interface VRpWkBscGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    void insert(VRpWkBscGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    void insertSelective(VRpWkBscGprsCs record);

    
    List<VRpWkBscGprsCs> filter(Integer week,Integer year, String bscid, String region);
    List<VRpWkBscGprsCs> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear,String bscid, String region);
}