package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsQosBh;


public interface VRpDyLocationGprsQosBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:23 ICT 2010
     */
    void insert(VRpDyLocationGprsQosBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:23 ICT 2010
     */
    void insertSelective(VRpDyLocationGprsQosBh record);

	List<VRpDyLocationGprsQosBh> filterDetails(String startDate, String endDate, String location, String region);
}