package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrictIbc;


public interface VRpWkDistrictIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT_IBC
     *
     * @ibatorgenerated Mon Dec 20 16:09:19 ICT 2010
     */
    void insert(VRpWkDistrictIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT_IBC
     *
     * @ibatorgenerated Mon Dec 20 16:09:19 ICT 2010
     */
    void insertSelective(VRpWkDistrictIbc record);

	List<VRpWkDistrictIbc> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String province, String district, String region);
}