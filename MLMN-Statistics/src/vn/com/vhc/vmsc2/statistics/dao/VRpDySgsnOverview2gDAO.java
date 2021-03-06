package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnOverview2g;

public interface VRpDySgsnOverview2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW_2G
     *
     * @ibatorgenerated Tue Oct 15 16:47:23 ICT 2013
     */
    void insert(VRpDySgsnOverview2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW_2G
     *
     * @ibatorgenerated Tue Oct 15 16:47:23 ICT 2013
     */
    void insertSelective(VRpDySgsnOverview2g record);
    List<VRpDySgsnOverview2g> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour);
    List<VRpDySgsnOverview2g> filterDaily(String tableName, String sgsnid,String startDate, String endDate);
    List<VRpDySgsnOverview2g> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear);
    List<VRpDySgsnOverview2g> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear);

}