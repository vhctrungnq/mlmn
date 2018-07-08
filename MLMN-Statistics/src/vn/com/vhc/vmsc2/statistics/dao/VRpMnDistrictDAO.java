package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrict;


public interface VRpMnDistrictDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT
     *
     * @ibatorgenerated Mon Dec 20 15:55:54 ICT 2010
     */
    void insert(VRpMnDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT
     *
     * @ibatorgenerated Mon Dec 20 15:55:54 ICT 2010
     */
    void insertSelective(VRpMnDistrict record);

	List<VRpMnDistrict> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String province, String district, String region);
}