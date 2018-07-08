package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmRegion3g;


public interface VRpDyIrsrAlarmRegion3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insert(VRpDyIrsrAlarmRegion3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insertSelective(VRpDyIrsrAlarmRegion3g record);

	List<VRpDyIrsrAlarmRegion3g> filter(String region, String startDate, String endDate);
}