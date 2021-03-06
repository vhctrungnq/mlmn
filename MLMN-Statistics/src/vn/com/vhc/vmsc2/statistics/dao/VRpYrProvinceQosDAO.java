package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrProvinceQos;

public interface VRpYrProvinceQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_PROVINCE_QOS
     *
     * @ibatorgenerated Wed Oct 07 10:24:58 ICT 2015
     */
    void insert(VRpYrProvinceQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_PROVINCE_QOS
     *
     * @ibatorgenerated Wed Oct 07 10:24:58 ICT 2015
     */
    void insertSelective(VRpYrProvinceQos record);

	List<VRpYrProvinceQos> filter(String startYear, String endYear, String province, String region, int order, String column);
}