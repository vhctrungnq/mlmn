package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.RpHrCell2g3g;

public interface RpHrCell2g3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_CELL_2G_3G
     *
     * @ibatorgenerated Wed Nov 20 17:42:47 ICT 2013
     */
    void insert(RpHrCell2g3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_CELL_2G_3G
     *
     * @ibatorgenerated Wed Nov 20 17:42:47 ICT 2013
     */
    void insertSelective(RpHrCell2g3g record);

	List<RpHrCell2g3g> getTraffic2g3gInfoFilter(String siteid);
}