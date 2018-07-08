package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmBsc3g;


public interface VRpDyIrsrAlarmBsc3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IRSR_ALARM_BSC_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insert(VRpDyIrsrAlarmBsc3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IRSR_ALARM_BSC_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insertSelective(VRpDyIrsrAlarmBsc3g record);

	List<VRpDyIrsrAlarmBsc3g> filter(String bscid, String startDate, String endDate, String region);
}