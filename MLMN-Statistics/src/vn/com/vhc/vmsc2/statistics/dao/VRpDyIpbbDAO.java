package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbb;

public interface VRpDyIpbbDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB
     *
     * @ibatorgenerated Tue Oct 29 12:31:51 ICT 2013
     */
    void insert(VRpDyIpbb record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB
     *
     * @ibatorgenerated Tue Oct 29 12:31:51 ICT 2013
     */
    void insertSelective(VRpDyIpbb record);
    List<VRpDyIpbb> filterHourly(String tableName, String routeName, String interfaceName, String startDate, String startHour, String endHour);
    List<VRpDyIpbb> filterDaily(String tableName, String routeName, String interfaceName,String startDate, String endDate);
    List<VRpDyIpbb> filterMonthly(String tableName,String routeName, String interfaceName,String startMonth,String endMonth,String startYear,String endYear);
    List<VRpDyIpbb> filterWeekly(String tableName,String routeName, String interfaceName,String startWeek,String endWeek,String startYear,String endYear);

}