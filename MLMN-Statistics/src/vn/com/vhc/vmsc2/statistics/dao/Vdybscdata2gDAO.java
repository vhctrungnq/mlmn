package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;


import vn.com.vhc.vmsc2.statistics.domain.Vdybscdata2g;

public interface Vdybscdata2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BSC_DATA_2G
     *
     * @ibatorgenerated Wed Aug 01 15:44:59 ICT 2012
     */
    void insert(Vdybscdata2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BSC_DATA_2G
     *
     * @ibatorgenerated Wed Aug 01 15:44:59 ICT 2012
     */
    void insertSelective(Vdybscdata2g record);
    List<Vdybscdata2g> filter(String starDate, String endDate, String bscid);
    List<Vdybscdata2g> filter2(String starDate, String endDate, String bscid);
}