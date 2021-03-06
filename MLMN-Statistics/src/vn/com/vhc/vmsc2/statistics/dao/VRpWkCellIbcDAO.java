package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellIbc;

public interface VRpWkCellIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL
     *
     * @ibatorgenerated Thu Nov 11 16:05:39 ICT 2010
     */
    void insert(VRpWkCellIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL
     *
     * @ibatorgenerated Thu Nov 11 16:05:39 ICT 2010
     */
    void insertSelective(VRpWkCellIbc record);
    List<VRpWkCellIbc> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province, String region);
    List<VRpWkCellIbc> filter(Integer startWeek, Integer startYear,Integer endWeek,Integer endYear,String bscid, String cellid);
}