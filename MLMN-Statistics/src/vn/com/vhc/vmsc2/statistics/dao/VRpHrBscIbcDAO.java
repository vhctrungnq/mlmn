package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscIbc;


public interface VRpHrBscIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC
     *
     * @ibatorgenerated Thu Dec 16 17:05:15 ICT 2010
     */
    void insert(VRpHrBscIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC
     *
     * @ibatorgenerated Thu Dec 16 17:05:15 ICT 2010
     */
    void insertSelective(VRpHrBscIbc record);

	List<VRpHrBscIbc> filter(Date d, Integer hour, String bscid, String region);

	List<VRpHrBscIbc> filter(String startHour, String endHour, Date day, String bscid, String region);
}