package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceBh;


public interface VRpDyProvinceBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insert(VRpDyProvinceBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insertSelective(VRpDyProvinceBh record);

	List<VRpDyProvinceBh> filter(String startDate, String endDate, String province, String region);
}