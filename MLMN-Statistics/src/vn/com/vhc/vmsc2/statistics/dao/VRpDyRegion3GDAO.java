package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion3G;


public interface VRpDyRegion3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_3G
     *
     * @ibatorgenerated Mon Apr 25 11:01:01 ICT 2011
     */
    void insert(VRpDyRegion3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_3G
     *
     * @ibatorgenerated Mon Apr 25 11:01:01 ICT 2011
     */
    void insertSelective(VRpDyRegion3G record);

	List<VRpDyRegion3G> filter(String startDate, String endDate);

	List<VRpDyRegion3G> filter(String startDate, String endDate, String region);
}