package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellGprsCsBh;


public interface VRpDyCellGprsCsBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_CS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:09:58 ICT 2010
     */
    void insert(VRpDyCellGprsCsBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_CS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:09:58 ICT 2010
     */
    void insertSelective(VRpDyCellGprsCsBh record);
    
    List<VRpDyCellGprsCsBh> filter(String startDate, String endDate, String bscid,String cellid);
}