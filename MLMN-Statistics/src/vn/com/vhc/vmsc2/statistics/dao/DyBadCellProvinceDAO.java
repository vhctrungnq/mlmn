package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.DyBadCellProvince;


public interface DyBadCellProvinceDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_PROVINCE
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    void insert(DyBadCellProvince record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_PROVINCE
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    void insertSelective(DyBadCellProvince record);

	List<DyBadCellProvince> filter(String province, String startDate, String endDate, String region);
}