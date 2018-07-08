package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteQosBh;


public interface VRpDySiteQosBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insert(VRpDySiteQosBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insertSelective(VRpDySiteQosBh record);

	List<VRpDySiteQosBh> filterDetails(String startDate, String endDate, String bscid, String siteid, String region);
}