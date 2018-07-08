package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrDistrict3G;

public interface VRpQrDistrict3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_3G
     *
     * @ibatorgenerated Thu Oct 15 09:49:04 ICT 2015
     */
    void insert(VRpQrDistrict3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_3G
     *
     * @ibatorgenerated Thu Oct 15 09:49:04 ICT 2015
     */
    void insertSelective(VRpQrDistrict3G record);
    List<VRpQrDistrict3G> filter(String startQuarter, String startYear, String endQuarter, String endYear, String province, String district, String region, Integer order, String column);
}