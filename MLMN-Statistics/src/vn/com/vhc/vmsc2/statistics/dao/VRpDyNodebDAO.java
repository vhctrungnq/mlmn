package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyNodeb;

public interface VRpDyNodebDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_NODEB
     *
     * @ibatorgenerated Tue Jan 07 15:38:18 ICT 2014
     */
    void insert(VRpDyNodeb record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_NODEB
     *
     * @ibatorgenerated Tue Jan 07 15:38:18 ICT 2014
     */
    void insertSelective(VRpDyNodeb record);
    List<VRpDyNodeb> filterHourly(String tableName, String linkid,String startDate, String startHour, String endHour);
    List<VRpDyNodeb> filterDaily(String tableName, String linkid,String startDate, String endDate);
    
}