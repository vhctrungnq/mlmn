package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrDistrict3G;

public interface VRpYrDistrict3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_3G
     *
     * @ibatorgenerated Thu Oct 15 09:49:16 ICT 2015
     */
    void insert(VRpYrDistrict3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_3G
     *
     * @ibatorgenerated Thu Oct 15 09:49:16 ICT 2015
     */
    void insertSelective(VRpYrDistrict3G record);
    List<VRpYrDistrict3G> filter(String startYear, String endYear, String province, String district, String region, Integer order, String column);
}