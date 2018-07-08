package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import vn.com.vhc.vmsc2.statistics.domain.Route;
import vn.com.vhc.vmsc2.statistics.web.filter.RouteFilter;

public interface RouteDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTE_CORE
     *
     * @ibatorgenerated Thu Oct 21 11:50:23 ICT 2010
     */
    int deleteByPrimaryKey(String fromMscid, String routeid, String toMscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTE_CORE
     *
     * @ibatorgenerated Thu Oct 21 11:50:23 ICT 2010
     */
    void insert(Route record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTE_CORE
     *
     * @ibatorgenerated Thu Oct 21 11:50:23 ICT 2010
     */
    void insertSelective(Route record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTE_CORE
     *
     * @ibatorgenerated Thu Oct 21 11:50:23 ICT 2010
     */
    Route selectByPrimaryKey(String fromMscid, String routeid, String toMscid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTE_CORE
     *
     * @ibatorgenerated Thu Oct 21 11:50:23 ICT 2010
     */
    int updateByPrimaryKeySelective(Route record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTE_CORE
     *
     * @ibatorgenerated Thu Oct 21 11:50:23 ICT 2010
     */
    int updateByPrimaryKey(Route record);

	List<Route> filter(RouteFilter filter);
	List<Route> getRouteName();
	List<Route> getSPCName();
}