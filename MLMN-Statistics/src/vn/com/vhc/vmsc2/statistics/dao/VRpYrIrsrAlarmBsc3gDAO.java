package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmBsc3g;


public interface VRpYrIrsrAlarmBsc3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_BSC_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insert(VRpYrIrsrAlarmBsc3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_BSC_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insertSelective(VRpYrIrsrAlarmBsc3g record);

	List<VRpYrIrsrAlarmBsc3g> filter(String bscid, Integer startYear, Integer endYear);
}