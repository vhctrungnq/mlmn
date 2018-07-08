package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvinceGprsQos;


public interface VRpWkProvinceGprsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:01:08 ICT 2010
     */
    void insert(VRpWkProvinceGprsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:01:08 ICT 2010
     */
    void insertSelective(VRpWkProvinceGprsQos record);

	List<VRpWkProvinceGprsQos> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String province, String region);

	List<VRpWkProvinceGprsQos> filter(String province, Integer week, Integer year, String region);
}