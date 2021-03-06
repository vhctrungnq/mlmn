package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvinceGprsQos;


public interface VRpHrProvinceGprsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:02:03 ICT 2010
     */
    void insert(VRpHrProvinceGprsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:02:03 ICT 2010
     */
    void insertSelective(VRpHrProvinceGprsQos record);

	List<VRpHrProvinceGprsQos> filter(String startHour, String endHour, String day, String province, String region);
}