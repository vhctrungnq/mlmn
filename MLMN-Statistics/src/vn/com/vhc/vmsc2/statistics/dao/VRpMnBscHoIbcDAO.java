package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHoIbc;


public interface VRpMnBscHoIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:51 ICT 2010
     */
    void insert(VRpMnBscHoIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:51 ICT 2010
     */
    void insertSelective(VRpMnBscHoIbc record);

	List<VRpMnBscHoIbc> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String region);

	List<VRpMnBscHoIbc> filter(String bscid, Integer month, Integer year, String region);
}