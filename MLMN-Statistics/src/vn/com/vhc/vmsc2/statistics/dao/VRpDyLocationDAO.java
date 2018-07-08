package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocation;


public interface VRpDyLocationDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION
     *
     * @ibatorgenerated Fri Dec 17 13:48:29 ICT 2010
     */
    void insert(VRpDyLocation record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION
     *
     * @ibatorgenerated Fri Dec 17 13:48:29 ICT 2010
     */
    void insertSelective(VRpDyLocation record);

	List<VRpDyLocation> filter(String startDate, String endDate, String location, String region);
}