package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkDistrict;


public interface WkCellBlkDistrictDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int deleteByPrimaryKey(String district, String province, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(WkCellBlkDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(WkCellBlkDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    WkCellBlkDistrict selectByPrimaryKey(String district, String province, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKeySelective(WkCellBlkDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKey(WkCellBlkDistrict record);

	List<WkCellBlkDistrict> filter(String province, String district, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear);
}