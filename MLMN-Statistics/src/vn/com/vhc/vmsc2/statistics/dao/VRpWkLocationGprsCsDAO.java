package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsCs;


public interface VRpWkLocationGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:07 ICT 2010
     */
    void insert(VRpWkLocationGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:07 ICT 2010
     */
    void insertSelective(VRpWkLocationGprsCs record);

	List<VRpWkLocationGprsCs> filter(String location, Float week, Float year, String region);

	List<VRpWkLocationGprsCs> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String location, String region);
}