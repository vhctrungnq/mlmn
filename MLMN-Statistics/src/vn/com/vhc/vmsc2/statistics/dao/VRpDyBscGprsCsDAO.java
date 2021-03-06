package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionBscGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscGprsCs;


public interface VRpDyBscGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 17:14:07 ICT 2010
     */
    void insert(VRpDyBscGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 17:14:07 ICT 2010
     */
    void insertSelective(VRpDyBscGprsCs record);
    List<VRpDyBscGprsCs> filter(Date day,String bscid, String region);
    List<VRpDyBscGprsCs> filter(String startDate, String endDate, String bscid, String region);

    /**
	 * @author TrungNQ
	 * @createDate 30/10/2015 16:21
	 * @decription Tim kiem du lieu muc tuy chon
	 * 
	 * */
	List<OptionBscGprsCs> bscGprsCsOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String region, 
			int startRecord, int endRecord, String column, int order);
	
	OptionBscGprsCs countBscGprsCsOption(
			String startDate, String endDate, int startHour, int endHour,
			String bscid, String region);
}