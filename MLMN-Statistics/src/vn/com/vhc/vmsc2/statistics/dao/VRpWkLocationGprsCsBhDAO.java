package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsCsBh;


public interface VRpWkLocationGprsCsBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:32 ICT 2010
     */
    void insert(VRpWkLocationGprsCsBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:32 ICT 2010
     */
    void insertSelective(VRpWkLocationGprsCsBh record);

	List<VRpWkLocationGprsCsBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String location, String region);
}