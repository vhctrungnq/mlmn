package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficDistrict;


public interface WkCellTrafficDistrictDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int deleteByPrimaryKey(String district, String province, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(WkCellTrafficDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(WkCellTrafficDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    WkCellTrafficDistrict selectByPrimaryKey(String district, String province, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKeySelective(WkCellTrafficDistrict record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKey(WkCellTrafficDistrict record);

	List<WkCellTrafficDistrict> filter(String province, String district, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear);
}