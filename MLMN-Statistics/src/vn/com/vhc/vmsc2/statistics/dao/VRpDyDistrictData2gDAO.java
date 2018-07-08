package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionDistrictData2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictData2g;


public interface VRpDyDistrictData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:04 GMT+07:00 2011
     */
    void insert(VRpDyDistrictData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:04 GMT+07:00 2011
     */
    void insertSelective(VRpDyDistrictData2g record);

	List<VRpDyDistrictData2g> filter(String startDate, String endDate, String province, String district, String region);

	/**
	 * @author TrungNQ
	 * @createDate 2/11/2015
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionDistrictData2g> districtData2gOption(
			String startDate, String endDate, int startHour, int endHour,
			String region, String province, String district,int startRecord, int endRecord, String column, int order);
	
	OptionDistrictData2g countDistrictData2gOption(
			String startDate, String endDate, int startHour, int endHour,
			String region, String province, String district);
}