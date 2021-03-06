package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmRegion3g;


public interface VRpQrIrsrAlarmRegion3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insert(VRpQrIrsrAlarmRegion3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insertSelective(VRpQrIrsrAlarmRegion3g record);

	List<VRpQrIrsrAlarmRegion3g> filter(String region, Integer startQuarter, Integer startYear, Integer endQuarter, Integer endYear);
}