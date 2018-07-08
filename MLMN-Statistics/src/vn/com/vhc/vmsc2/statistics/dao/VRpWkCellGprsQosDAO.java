package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsQos;

public interface VRpWkCellGprsQosDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS
     *
     * @ibatorgenerated Mon Nov 15 14:08:46 ICT 2010
     */
    void insert(VRpWkCellGprsQos record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS
     *
     * @ibatorgenerated Mon Nov 15 14:08:46 ICT 2010
     */
    void insertSelective(VRpWkCellGprsQos record);
    List<VRpWkCellGprsQos> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid,String province,String bscid, String region, int startRecord, int endRecord, String column, String order);
    List<VRpWkCellGprsQos> filter(Integer startWeek, Integer startYear,Integer endWeek,Integer endYear,String bscid, String cellid);
    Integer countRow(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province, String region);

	List<VRpWkCellGprsQos> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String province, String bscid,
			String region);
}