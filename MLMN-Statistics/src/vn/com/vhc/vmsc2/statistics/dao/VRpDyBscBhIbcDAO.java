package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscBhIbc;


public interface VRpDyBscBhIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insert(VRpDyBscBhIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    void insertSelective(VRpDyBscBhIbc record);

	List<VRpDyBscBhIbc> filter(String startDate, String endDate, String bscid, String region);
}