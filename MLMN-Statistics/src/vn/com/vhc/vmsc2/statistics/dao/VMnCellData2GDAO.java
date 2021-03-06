package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VMnCellData2G;

public interface VMnCellData2GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_MN_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:57:56 ICT 2012
     */
    void insert(VMnCellData2G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_MN_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:57:56 ICT 2012
     */
    void insertSelective(VMnCellData2G record);
    List<VMnCellData2G> filterNosucc(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String cellid);
    List<VMnCellData2G> filterGprs(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String cellid);
}