package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionProvince3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvince3G;


public interface VRpDyProvince3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 11:01:01 ICT 2011
     */
    void insert(VRpDyProvince3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 11:01:01 ICT 2011
     */
    void insertSelective(VRpDyProvince3G record);

	List<VRpDyProvince3G> filter(String startDate, String endDate, String province, String region);
	
	/**
	 * @author TrungNQ
	 * @createDate 25/11/2015 
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionProvince3g> Province3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String province, String region);
}