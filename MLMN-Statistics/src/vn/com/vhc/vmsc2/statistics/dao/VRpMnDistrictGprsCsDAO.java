package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrictGprsCs;


public interface VRpMnDistrictGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Tue Dec 21 11:40:56 ICT 2010
     */
    void insert(VRpMnDistrictGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Tue Dec 21 11:40:56 ICT 2010
     */
    void insertSelective(VRpMnDistrictGprsCs record);

	List<VRpMnDistrictGprsCs> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String province, String district, String region);
	
}