package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscTraffic;

public interface VRpDyMscTrafficDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_TRAFFIC
     *
     * @ibatorgenerated Sat Oct 12 14:34:54 ICT 2013
     */
    void insert(VRpDyMscTraffic record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_TRAFFIC
     *
     * @ibatorgenerated Sat Oct 12 14:34:54 ICT 2013
     */
    void insertSelective(VRpDyMscTraffic record);
    List<VRpDyMscTraffic> filterHourly(String tableName, String mscid,String startDate, String startHour, String endHour);
    List<VRpDyMscTraffic> filterDaily(String tableName, String mscid,String startDate, String endDate);
    List<VRpDyMscTraffic> filterMonthly(String tableName,String mscid,String startMonth,String endMonth,String startYear,String endYear);
    List<VRpDyMscTraffic> filterWeekly(String tableName,String mscid,String startWeek,String endWeek,String startYear,String endYear);

}