package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceGprsQosBh;


public interface VRpMnProvinceGprsQosBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_GPRS_QOS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:02:28 ICT 2010
     */
    void insert(VRpMnProvinceGprsQosBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_GPRS_QOS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:02:28 ICT 2010
     */
    void insertSelective(VRpMnProvinceGprsQosBh record);

	List<VRpMnProvinceGprsQosBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String province, String region);
}