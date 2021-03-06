package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvince3G;


public interface VRpWkProvince3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    void insert(VRpWkProvince3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    void insertSelective(VRpWkProvince3G record);

	List<VRpWkProvince3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String province, String region);
}