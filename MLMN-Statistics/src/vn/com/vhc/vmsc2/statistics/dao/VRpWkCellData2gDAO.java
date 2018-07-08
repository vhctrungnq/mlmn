package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellData2g;


public interface VRpWkCellData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:20 GMT+07:00 2011
     */
    void insert(VRpWkCellData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:20 GMT+07:00 2011
     */
    void insertSelective(VRpWkCellData2g record);

	List<VRpWkCellData2g> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String province,
			String bscid, String region);

	List<VRpWkCellData2g> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid);
}