package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrProvince3G;

public interface VRpQrProvince3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_PROVINCE_3G
     *
     * @ibatorgenerated Thu Oct 15 15:58:02 ICT 2015
     */
    void insert(VRpQrProvince3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_PROVINCE_3G
     *
     * @ibatorgenerated Thu Oct 15 15:58:02 ICT 2015
     */
    void insertSelective(VRpQrProvince3G record);
    List<VRpQrProvince3G> filter(String startQuarter, String startYear, String endQuarter, String endYear, String province, String region, Integer order, String column);
}