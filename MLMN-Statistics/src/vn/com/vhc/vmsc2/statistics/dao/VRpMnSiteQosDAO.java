package vn.com.vhc.vmsc2.statistics.dao;

import java.util.*;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteQos;

public interface VRpMnSiteQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:13:55 ICT 2010
     */
    void insert(VRpMnSiteQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:13:55 ICT 2010
     */
    void insertSelective(VRpMnSiteQos record);
    List<VRpMnSiteQos> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String siteid, String region);

	List<VRpMnSiteQos> filter(String province,String bscid, String siteid, Integer month, Integer year, String region);
}