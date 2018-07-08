package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrictData2g;


public interface VRpWkDistrictData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:07 GMT+07:00 2011
     */
    void insert(VRpWkDistrictData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:07 GMT+07:00 2011
     */
    void insertSelective(VRpWkDistrictData2g record);

	List<VRpWkDistrictData2g> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String province, String district,
			String region);
}