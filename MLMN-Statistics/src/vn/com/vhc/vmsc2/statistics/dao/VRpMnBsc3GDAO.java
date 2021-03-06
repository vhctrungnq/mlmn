package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBsc3G;


public interface VRpMnBsc3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:24:43 ICT 2011
     */
    void insert(VRpMnBsc3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:24:43 ICT 2011
     */
    void insertSelective(VRpMnBsc3G record);

	List<VRpMnBsc3G> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String region);
}