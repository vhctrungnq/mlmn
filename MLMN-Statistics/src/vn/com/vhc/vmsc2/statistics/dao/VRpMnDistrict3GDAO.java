package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrict3G;


public interface VRpMnDistrict3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    void insert(VRpMnDistrict3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    void insertSelective(VRpMnDistrict3G record);

	List<VRpMnDistrict3G> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String province, String district, String region);
}