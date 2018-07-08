package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellBh3G;


public interface VRpMnCellBh3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_BH_3G
     *
     * @ibatorgenerated Fri Apr 29 13:24:32 ICT 2011
     */
    void insert(VRpMnCellBh3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_BH_3G
     *
     * @ibatorgenerated Fri Apr 29 13:24:32 ICT 2011
     */
    void insertSelective(VRpMnCellBh3G record);

	List<VRpMnCellBh3G> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String cellid);
}