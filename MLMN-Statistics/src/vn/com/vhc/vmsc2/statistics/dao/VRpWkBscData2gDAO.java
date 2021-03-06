package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscData2g;


public interface VRpWkBscData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:14 GMT+07:00 2011
     */
    void insert(VRpWkBscData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:14 GMT+07:00 2011
     */
    void insertSelective(VRpWkBscData2g record);

	List<VRpWkBscData2g> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String region);
}