package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBsc3G;


public interface VRpWkBsc3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:24:43 ICT 2011
     */
    void insert(VRpWkBsc3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:24:43 ICT 2011
     */
    void insertSelective(VRpWkBsc3G record);

	List<VRpWkBsc3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String region);
}