package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationGprsCsBh;


public interface VRpMnLocationGprsCsBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:56 ICT 2010
     */
    void insert(VRpMnLocationGprsCsBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:56 ICT 2010
     */
    void insertSelective(VRpMnLocationGprsCsBh record);

	List<VRpMnLocationGprsCsBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String location, String region);
}