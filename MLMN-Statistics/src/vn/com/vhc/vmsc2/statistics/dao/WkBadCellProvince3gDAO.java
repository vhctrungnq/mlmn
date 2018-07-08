package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.WkBadCellProvince3g;


public interface WkBadCellProvince3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int deleteByPrimaryKey(String province, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insert(WkBadCellProvince3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    void insertSelective(WkBadCellProvince3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    WkBadCellProvince3g selectByPrimaryKey(String province, Integer week, Integer year);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKeySelective(WkBadCellProvince3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    int updateByPrimaryKey(WkBadCellProvince3g record);

	List<WkBadCellProvince3g> filter(String province, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear);
}