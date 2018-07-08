package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTrafficBsc;


public interface VRpDyCellTrafficBscDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    void insert(VRpDyCellTrafficBsc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    void insertSelective(VRpDyCellTrafficBsc record);

	List<VRpDyCellTrafficBsc> filter(String bscid, String startDate, String endDate, String region);
}