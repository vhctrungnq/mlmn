package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmDistrict2g;


public interface VRpYrIrsrAlarmDistrict2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_DISTRICT_2G
     *
     * @ibatorgenerated Thu Sep 15 11:45:15 GMT+07:00 2011
     */
    void insert(VRpYrIrsrAlarmDistrict2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_DISTRICT_2G
     *
     * @ibatorgenerated Thu Sep 15 11:45:15 GMT+07:00 2011
     */
    void insertSelective(VRpYrIrsrAlarmDistrict2g record);

	List<VRpYrIrsrAlarmDistrict2g> filter(String region, String province, String district, Integer startYear, Integer endYear);
}