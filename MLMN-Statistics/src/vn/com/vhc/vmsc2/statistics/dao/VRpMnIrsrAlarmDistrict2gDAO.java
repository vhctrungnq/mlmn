package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnIrsrAlarmDistrict2g;


public interface VRpMnIrsrAlarmDistrict2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_IRSR_ALARM_DISTRICT_2G
     *
     * @ibatorgenerated Thu Sep 15 11:40:03 GMT+07:00 2011
     */
    void insert(VRpMnIrsrAlarmDistrict2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_IRSR_ALARM_DISTRICT_2G
     *
     * @ibatorgenerated Thu Sep 15 11:40:03 GMT+07:00 2011
     */
    void insertSelective(VRpMnIrsrAlarmDistrict2g record);

	List<VRpMnIrsrAlarmDistrict2g> filter(String region, String province, String district, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear);
}