package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsCs;


public interface VRpWkCellGprsCsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_CS
     *
     * @ibatorgenerated Mon Nov 15 14:09:02 ICT 2010
     */
    void insert(VRpWkCellGprsCs record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_CS
     *
     * @ibatorgenerated Mon Nov 15 14:09:02 ICT 2010
     */
    void insertSelective(VRpWkCellGprsCs record);
    
    List<VRpWkCellGprsCs> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid,String province,String bscid, String region, int startRecord, int endRecord, String column, String order);
    List<VRpWkCellGprsCs> filter(Integer startWeek, Integer startYear,Integer endWeek,Integer endYear,String bscid, String cellid);
    Integer countRow(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province, String region);

	List<VRpWkCellGprsCs> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String province, String bscid,
			String region);
}