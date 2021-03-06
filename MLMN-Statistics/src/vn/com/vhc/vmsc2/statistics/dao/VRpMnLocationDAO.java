package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocation;


public interface VRpMnLocationDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION
     *
     * @ibatorgenerated Fri Dec 17 13:48:29 ICT 2010
     */
    void insert(VRpMnLocation record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION
     *
     * @ibatorgenerated Fri Dec 17 13:48:29 ICT 2010
     */
    void insertSelective(VRpMnLocation record);

	List<VRpMnLocation> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String location, String region);
}