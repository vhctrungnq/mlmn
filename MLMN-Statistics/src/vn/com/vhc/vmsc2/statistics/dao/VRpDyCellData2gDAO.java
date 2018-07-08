package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.OptionCellData2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellData2g;


public interface VRpDyCellData2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:09:57 GMT+07:00 2011
     */
    void insert(VRpDyCellData2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:09:57 GMT+07:00 2011
     */
    void insertSelective(VRpDyCellData2g record);

	List<VRpDyCellData2g> filter(String startDate, String endDate, String cellid, String province, String bscid, String region);

	List<VRpDyCellData2g> filter(String startDate, String endDate, String bscid, String cellid);

	List<OptionCellData2g> cellData2gOption(String startDate, String endDate, int startHour, int endHour, String bscid, String cellid, String province,
			String region, String column, int order);

	OptionCellData2g countCellData2gOption(String startDate, String endDate, int startHour, int endHour, String bscid, String cellid, String province,
			String region);
}