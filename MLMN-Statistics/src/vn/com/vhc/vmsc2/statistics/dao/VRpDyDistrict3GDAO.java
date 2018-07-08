package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrict3G;


public interface VRpDyDistrict3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 11:02:21 ICT 2011
     */
    void insert(VRpDyDistrict3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 11:02:21 ICT 2011
     */
    void insertSelective(VRpDyDistrict3G record);

	List<VRpDyDistrict3G> filter(String startDate, String endDate, String province, String district, String region);

	/**
	 * @author TrungNQ
	 * @createDate 24/11/2015 
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionDistrict3g> District3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String region, String province, String district, 
			int startRecord, int endRecord, String column, int order);
	
	OptionDistrict3g countDistrict3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String region, String province, String district);
}