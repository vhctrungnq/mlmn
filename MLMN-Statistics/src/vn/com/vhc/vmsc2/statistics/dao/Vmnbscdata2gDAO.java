package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Vmnbscdata2g;

public interface Vmnbscdata2gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_MN_BSC_DATA_2G
     *
     * @ibatorgenerated Thu Aug 02 12:15:18 ICT 2012
     */
    void insert(Vmnbscdata2g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_MN_BSC_DATA_2G
     *
     * @ibatorgenerated Thu Aug 02 12:15:18 ICT 2012
     */
    void insertSelective(Vmnbscdata2g record);
    List<Vmnbscdata2g> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid);
    List<Vmnbscdata2g> filter2(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid);
}