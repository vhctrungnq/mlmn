package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellHoBhIbc;


public interface VRpMnCellHoBhIbcDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_HO_BH
     *
     * @ibatorgenerated Tue Nov 16 11:26:54 ICT 2010
     */
    void insert(VRpMnCellHoBhIbc record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_HO_BH
     *
     * @ibatorgenerated Tue Nov 16 11:26:54 ICT 2010
     */
    void insertSelective(VRpMnCellHoBhIbc record);

	List<VRpMnCellHoBhIbc> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String cellid);
}