package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellProvince3G;


public interface VRpDyBadCellProvince3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    void insert(VRpDyBadCellProvince3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_PROVINCE_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    void insertSelective(VRpDyBadCellProvince3G record);

	List<VRpDyBadCellProvince3G> filter(String province, String startDate, String endDate, String region);
}