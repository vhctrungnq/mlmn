package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsQos;


public interface VRpWkLocationGprsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS
     *
     * @ibatorgenerated Fri Nov 19 10:35:49 ICT 2010
     */
    void insert(VRpWkLocationGprsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS
     *
     * @ibatorgenerated Fri Nov 19 10:35:49 ICT 2010
     */
    void insertSelective(VRpWkLocationGprsQos record);

	List<VRpWkLocationGprsQos> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String location, String region);

	List<VRpWkLocationGprsQos> filter(String location, Integer week, Integer year, String region);
}