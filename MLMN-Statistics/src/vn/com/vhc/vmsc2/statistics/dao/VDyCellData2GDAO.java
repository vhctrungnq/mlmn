package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VDyCellData2G;

public interface VDyCellData2GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:56:48 ICT 2012
     */
    void insert(VDyCellData2G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:56:48 ICT 2012
     */
    void insertSelective(VDyCellData2G record);
    
    List<VDyCellData2G> filter(String startDate, String endDate, String bscid, String cellid);
    List<VDyCellData2G> filter2(String startDate, String endDate, String bscid, String cellid);
}