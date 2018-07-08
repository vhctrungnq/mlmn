package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCell;

public interface VRpWkCellDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL
     *
     * @ibatorgenerated Thu Nov 11 16:05:39 ICT 2010
     */
    void insert(VRpWkCell record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL
     *
     * @ibatorgenerated Thu Nov 11 16:05:39 ICT 2010
     */
    void insertSelective(VRpWkCell record);
    List<VRpWkCell> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province, String region, int startRecord, int endRecord, String column, String order);
    List<VRpWkCell> filter(Integer startWeek, Integer startYear,Integer endWeek,Integer endYear,String bscid, String cellid);
    Integer countRow(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province, String region);

	List<VRpWkCell> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid, String province, String region);
	List<VRpWkCell> filterwkloss(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid);
	List<VRpWkCell> filter1(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid);
}