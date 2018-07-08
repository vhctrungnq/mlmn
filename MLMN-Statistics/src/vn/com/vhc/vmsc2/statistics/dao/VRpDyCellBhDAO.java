package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBh;


public interface VRpDyCellBhDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    void insert(VRpDyCellBh record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    void insertSelective(VRpDyCellBh record);

	List<VRpDyCellBh> filter(String startDate, String endDate, String bscid, String cellid);
}