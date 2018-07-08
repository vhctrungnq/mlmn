package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionBsc3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBsc3G;


public interface VRpDyBsc3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:24:43 ICT 2011
     */
    void insert(VRpDyBsc3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:24:43 ICT 2011
     */
    void insertSelective(VRpDyBsc3G record);

	List<VRpDyBsc3G> filter(String startDate, String endDate, String bscid, String region);

	/**
	 * @author TrungNQ
	 * @createDate 11/11/2015 16:21
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionBsc3g> Bsc3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String region);
			 
	
	OptionBsc3g countBsc3gOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String region);

}