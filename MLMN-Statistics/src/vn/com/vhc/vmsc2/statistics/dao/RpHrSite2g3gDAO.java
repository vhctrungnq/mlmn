package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.RpHrSite2g3g;

public interface RpHrSite2g3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Sat Nov 16 11:03:33 ICT 2013
     */
    void insert(RpHrSite2g3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Sat Nov 16 11:03:33 ICT 2013
     */
    void insertSelective(RpHrSite2g3g record);

	List<RpHrSite2g3g> getTraffic2g3gInfoFilter(String siteid);
}