package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkRegion;


public interface WkCellBlkRegionDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int deleteByPrimaryKey(String region, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(WkCellBlkRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(WkCellBlkRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    WkCellBlkRegion selectByPrimaryKey(String region, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKeySelective(WkCellBlkRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKey(WkCellBlkRegion record);

	List<WkCellBlkRegion> filter(String region, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear);
}