package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvinceCssrDeta3G;


public interface VRpHrProvinceCssrDeta3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_CSSR_DETA_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    void insert(VRpHrProvinceCssrDeta3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_CSSR_DETA_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    void insertSelective(VRpHrProvinceCssrDeta3G record);

	List<VRpHrProvinceCssrDeta3G> filter(String startHour, String endHour, Date day, String province, String region);
}