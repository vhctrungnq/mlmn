package dao;

import java.util.List;

import vo.RpHrSite2g3g;

public interface RpHrSite2g3gDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Fri Nov 15 16:43:21 ICT 2013
     */
    void insert(RpHrSite2g3g record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Fri Nov 15 16:43:21 ICT 2013
     */
    void insertSelective(RpHrSite2g3g record);

	List<RpHrSite2g3g> getTraffic2g3gInfoFilter(String siteid);
}