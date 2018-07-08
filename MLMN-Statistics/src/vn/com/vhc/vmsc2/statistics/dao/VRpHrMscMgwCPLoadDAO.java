package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;


import vn.com.vhc.vmsc2.statistics.domain.VRpHrMscMgwCPLoad;

public interface VRpHrMscMgwCPLoadDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_MSC_MGW_CPLOAD
     *
     * @ibatorgenerated Mon Jan 07 17:35:49 ICT 2013
     */
    void insert(VRpHrMscMgwCPLoad record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_MSC_MGW_CPLOAD
     *
     * @ibatorgenerated Mon Jan 07 17:35:49 ICT 2013
     */
    void insertSelective(VRpHrMscMgwCPLoad record);
    List<VRpHrMscMgwCPLoad> filter(String startHour, Date startDate, String endHour, Date endDate, String mscid);
   	List<VRpHrMscMgwCPLoad> filter2(String startHour, Date startDate, String endHour, String mscid);

}