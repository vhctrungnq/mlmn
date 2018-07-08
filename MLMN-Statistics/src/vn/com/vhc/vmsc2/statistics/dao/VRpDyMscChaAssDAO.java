package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscChaAss;

public interface VRpDyMscChaAssDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_CHA_ASS
     *
     * @ibatorgenerated Fri Oct 11 14:30:50 ICT 2013
     */
    void insert(VRpDyMscChaAss record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_CHA_ASS
     *
     * @ibatorgenerated Fri Oct 11 14:30:50 ICT 2013
     */
    void insertSelective(VRpDyMscChaAss record);
    List<VRpDyMscChaAss> filterHourly(String tableName, String mscid,String startDate, String startHour, String endHour);
    List<VRpDyMscChaAss> filterDaily(String tableName, String mscid,String startDate, String endDate);
    List<VRpDyMscChaAss> filterMonthly(String tableName,String mscid,String startMonth,String endMonth,String startYear,String endYear);
    List<VRpDyMscChaAss> filterWeekly(String tableName,String mscid,String startWeek,String endWeek,String startYear,String endYear);
}