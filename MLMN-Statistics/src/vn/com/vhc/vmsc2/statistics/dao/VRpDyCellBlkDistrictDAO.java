package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkDistrict;


public interface VRpDyCellBlkDistrictDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(VRpDyCellBlkDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(VRpDyCellBlkDistrict record);

	List<VRpDyCellBlkDistrict> filter(String province, String district, String startDate, String endDate);
}