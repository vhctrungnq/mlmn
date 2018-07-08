package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnOverview;

public interface VRpDySgsnOverviewDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW
     *
     * @ibatorgenerated Tue Oct 15 15:30:51 ICT 2013
     */
    void insert(VRpDySgsnOverview record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW
     *
     * @ibatorgenerated Tue Oct 15 15:30:51 ICT 2013
     */
    void insertSelective(VRpDySgsnOverview record);
    List<VRpDySgsnOverview> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour);
    List<VRpDySgsnOverview> filterDaily(String tableName, String sgsnid,String startDate, String endDate);
    List<VRpDySgsnOverview> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear);
    List<VRpDySgsnOverview> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear);

}