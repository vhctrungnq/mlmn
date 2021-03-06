package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvinceGprsCs;


public interface VRpHrProvinceGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 16:57:08 ICT 2010
     */
    void insert(VRpHrProvinceGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 16:57:08 ICT 2010
     */
    void insertSelective(VRpHrProvinceGprsCs record);

    List<VRpHrProvinceGprsCs> filter2(String startHour, Date startDate, String endHour, String region, String province);
    List<VRpHrProvinceGprsCs> filter(String startHour, Date startDate, String endHour,Date endDate, String region, String province);
}