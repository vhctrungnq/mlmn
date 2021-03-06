package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsQosBh;

public interface VRpWkCellGprsQosBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:10:35 ICT 2010
     */
    void insert(VRpWkCellGprsQosBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:10:35 ICT 2010
     */
    void insertSelective(VRpWkCellGprsQosBh record);
    
    List<VRpWkCellGprsQosBh> filter(Integer startWeek, Integer startYear,Integer endWeek,Integer endYear, String bscid,String cellid);
}