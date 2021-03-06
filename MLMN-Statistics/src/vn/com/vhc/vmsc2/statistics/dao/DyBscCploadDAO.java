package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyBscCpload;

public interface DyBscCploadDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CPLOAD
     *
     * @ibatorgenerated Mon Jul 22 13:59:28 ICT 2013
     */
    int deleteByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CPLOAD
     *
     * @ibatorgenerated Mon Jul 22 13:59:28 ICT 2013
     */
    void insert(DyBscCpload record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CPLOAD
     *
     * @ibatorgenerated Mon Jul 22 13:59:28 ICT 2013
     */
    void insertSelective(DyBscCpload record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CPLOAD
     *
     * @ibatorgenerated Mon Jul 22 13:59:28 ICT 2013
     */
    DyBscCpload selectByPrimaryKey(String bscid, Date day);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CPLOAD
     *
     * @ibatorgenerated Mon Jul 22 13:59:28 ICT 2013
     */
    int updateByPrimaryKeySelective(DyBscCpload record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_CPLOAD
     *
     * @ibatorgenerated Mon Jul 22 13:59:28 ICT 2013
     */
    int updateByPrimaryKey(DyBscCpload record);

	List<DyBscCpload> getDyBscCpload(String bscid, String startDate, String endDate, String column, String order);

	List<DyBscCpload> getDyDayBscCpload(String endDate);

	List<DyBscCpload> getCploadRateByBscid(String bscid, String endDate);
}