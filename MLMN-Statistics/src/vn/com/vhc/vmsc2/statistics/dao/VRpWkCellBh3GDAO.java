package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellBh3G;


public interface VRpWkCellBh3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_BH_3G
     *
     * @ibatorgenerated Fri Apr 29 13:24:32 ICT 2011
     */
    void insert(VRpWkCellBh3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_BH_3G
     *
     * @ibatorgenerated Fri Apr 29 13:24:32 ICT 2011
     */
    void insertSelective(VRpWkCellBh3G record);

	List<VRpWkCellBh3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid);
}