package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkBadCellRegion;


public interface WkBadCellRegionDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int deleteByPrimaryKey(String region, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(WkBadCellRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(WkBadCellRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    WkBadCellRegion selectByPrimaryKey(String region, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKeySelective(WkBadCellRegion record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKey(WkBadCellRegion record);

	List<WkBadCellRegion> filter(String region, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear);
}