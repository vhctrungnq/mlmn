package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmRegion2g;


public interface VRpQrIrsrAlarmRegion2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_2G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insert(VRpQrIrsrAlarmRegion2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_2G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    void insertSelective(VRpQrIrsrAlarmRegion2g record);

	List<VRpQrIrsrAlarmRegion2g> filter(String region, Integer startQuarter, Integer startYear, Integer endQuarter, Integer endYear);

	
}