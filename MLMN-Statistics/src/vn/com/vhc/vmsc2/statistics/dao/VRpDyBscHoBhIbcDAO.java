package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHoBhIbc;


public interface VRpDyBscHoBhIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO_BH
     *
     * @ibatorgenerated Thu Nov 18 15:01:07 ICT 2010
     */
    void insert(VRpDyBscHoBhIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO_BH
     *
     * @ibatorgenerated Thu Nov 18 15:01:07 ICT 2010
     */
    void insertSelective(VRpDyBscHoBhIbc record);

	List<VRpDyBscHoBhIbc> filterDetails(String startDate, String endDate, String bscid, String region);
}