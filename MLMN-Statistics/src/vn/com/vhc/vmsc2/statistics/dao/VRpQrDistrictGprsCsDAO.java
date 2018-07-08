package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrDistrictGprsCs;

public interface VRpQrDistrictGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:04 ICT 2015
     */
    void insert(VRpQrDistrictGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:04 ICT 2015
     */
    void insertSelective(VRpQrDistrictGprsCs record);
	List<VRpQrDistrictGprsCs> filter(String startQuarter, String startYear, String endQuarter, String endYear, String province, String district, String region, Integer order, String column);
}