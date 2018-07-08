package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellData2g;


public interface VRpMnCellData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:25 GMT+07:00 2011
     */
    void insert(VRpMnCellData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:11:25 GMT+07:00 2011
     */
    void insertSelective(VRpMnCellData2g record);

	List<VRpMnCellData2g> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String cellid, String province, String bscid,
			String region);

	List<VRpMnCellData2g> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String cellid);
}