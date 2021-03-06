package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteQosBh;


public interface VRpMnSiteQosBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insert(VRpMnSiteQosBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insertSelective(VRpMnSiteQosBh record);

	List<VRpMnSiteQosBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String siteid, String region);
}