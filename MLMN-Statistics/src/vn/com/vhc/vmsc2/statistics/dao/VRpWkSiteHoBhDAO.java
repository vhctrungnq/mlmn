package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteHoBh;


public interface VRpWkSiteHoBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:49 ICT 2010
     */
    void insert(VRpWkSiteHoBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:49 ICT 2010
     */
    void insertSelective(VRpWkSiteHoBh record);

	List<VRpWkSiteHoBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String siteid, String region);
}