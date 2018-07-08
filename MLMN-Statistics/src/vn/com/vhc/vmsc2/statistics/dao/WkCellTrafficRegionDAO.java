package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficRegion;


public interface WkCellTrafficRegionDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int deleteByPrimaryKey(String region, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(WkCellTrafficRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(WkCellTrafficRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    WkCellTrafficRegion selectByPrimaryKey(String region, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKeySelective(WkCellTrafficRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKey(WkCellTrafficRegion record);

	List<WkCellTrafficRegion> filter(String region, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear);
}