package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteQosBh;


public interface VRpWkSiteQosBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insert(VRpWkSiteQosBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insertSelective(VRpWkSiteQosBh record);

	List<VRpWkSiteQosBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String siteid, String region);
}