package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellRegion3G;


public interface VRpDyBadCellRegion3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_REGION_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    void insert(VRpDyBadCellRegion3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_REGION_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    void insertSelective(VRpDyBadCellRegion3G record);

	List<VRpDyBadCellRegion3G> filter(String region, String startDate, String endDate);
}