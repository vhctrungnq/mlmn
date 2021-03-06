package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnTraffic;

public interface VRpDySgsnTrafficDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_TRAFFIC
     *
     * @ibatorgenerated Thu Oct 17 09:24:44 ICT 2013
     */
    void insert(VRpDySgsnTraffic record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_TRAFFIC
     *
     * @ibatorgenerated Thu Oct 17 09:24:44 ICT 2013
     */
    void insertSelective(VRpDySgsnTraffic record);
    List<VRpDySgsnTraffic> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour);
    List<VRpDySgsnTraffic> filterDaily(String tableName, String sgsnid,String startDate, String endDate);
    List<VRpDySgsnTraffic> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear);
    List<VRpDySgsnTraffic> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear);

}