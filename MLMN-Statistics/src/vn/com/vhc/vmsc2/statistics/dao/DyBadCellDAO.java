package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyBadCell;

public interface DyBadCellDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BAD_CELL
     *
     * @ibatorgenerated Mon Dec 20 08:54:57 ICT 2010
     */
    int deleteByPrimaryKey(String bscid, String cellid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BAD_CELL
     *
     * @ibatorgenerated Mon Dec 20 08:54:57 ICT 2010
     */
    void insert(DyBadCell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BAD_CELL
     *
     * @ibatorgenerated Mon Dec 20 08:54:57 ICT 2010
     */
    void insertSelective(DyBadCell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BAD_CELL
     *
     * @ibatorgenerated Mon Dec 20 08:54:57 ICT 2010
     */
    DyBadCell selectByPrimaryKey(String bscid, String cellid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BAD_CELL
     *
     * @ibatorgenerated Mon Dec 20 08:54:57 ICT 2010
     */
    int updateByPrimaryKeySelective(DyBadCell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BAD_CELL
     *
     * @ibatorgenerated Mon Dec 20 08:54:57 ICT 2010
     */
    int updateByPrimaryKey(DyBadCell record);

	List<DyBadCell> filter(String bscid, String cellid, String startDate, String endDate);
}