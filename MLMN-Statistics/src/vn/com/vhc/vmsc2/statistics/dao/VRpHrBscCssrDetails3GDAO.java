package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscCssrDetails3G;


public interface VRpHrBscCssrDetails3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    void insert(VRpHrBscCssrDetails3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    void insertSelective(VRpHrBscCssrDetails3G record);

	List<VRpHrBscCssrDetails3G> filter(String startHour, String endHour, Date day, String bscid, String region);
}