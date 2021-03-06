package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrDistrictData2g;


public interface VRpHrDistrictData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:37 GMT+07:00 2011
     */
    void insert(VRpHrDistrictData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:37 GMT+07:00 2011
     */
    void insertSelective(VRpHrDistrictData2g record);

	List<VRpHrDistrictData2g> filter(String startHour, String endHour, String day, String province, String district, String region);
}