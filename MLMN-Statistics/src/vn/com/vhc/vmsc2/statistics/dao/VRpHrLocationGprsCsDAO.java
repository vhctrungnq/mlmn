package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrLocationGprsCs;


public interface VRpHrLocationGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:19 ICT 2010
     */
    void insert(VRpHrLocationGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:19 ICT 2010
     */
    void insertSelective(VRpHrLocationGprsCs record);

	List<VRpHrLocationGprsCs> filter(String startHour, String endHour, String day, String location, String region);
}