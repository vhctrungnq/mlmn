package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHoBhIbc;


public interface VRpWkCellHoBhIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO_BH
     *
     * @ibatorgenerated Tue Nov 16 11:26:47 ICT 2010
     */
    void insert(VRpWkCellHoBhIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO_BH
     *
     * @ibatorgenerated Tue Nov 16 11:26:47 ICT 2010
     */
    void insertSelective(VRpWkCellHoBhIbc record);

	List<VRpWkCellHoBhIbc> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String cellid);
}