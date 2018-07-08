package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscData2g;

public interface VRpHrBscData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:30 GMT+07:00 2011
     */
    void insert(VRpHrBscData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:30 GMT+07:00 2011
     */
    void insertSelective(VRpHrBscData2g record);

	List<VRpHrBscData2g> filter(String startHour, String endHour, Date day, String bscid, String region);
}