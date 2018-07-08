package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteHoBh;


public interface VRpMnSiteHoBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:44 ICT 2010
     */
    void insert(VRpMnSiteHoBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:44 ICT 2010
     */
    void insertSelective(VRpMnSiteHoBh record);

	List<VRpMnSiteHoBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String siteid, String region);
}