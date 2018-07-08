package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellDistrict3g;


public interface VRpDyBadCellDistrict3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:56:05 ICT 2011
     */
    void insert(VRpDyBadCellDistrict3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:56:05 ICT 2011
     */
    void insertSelective(VRpDyBadCellDistrict3g record);

	List<VRpDyBadCellDistrict3g> filter(String province, String district, String startDate, String endDate);
}