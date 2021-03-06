package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkRouteCore;

public class WkRouteCoreDAOImpl extends SqlMapClientDaoSupport implements WkRouteCoreDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public WkRouteCoreDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public int deleteByPrimaryKey(String routeid, Integer week, Integer year) {
        WkRouteCore key = new WkRouteCore();
        key.setRouteid(routeid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_ROUTE_CORE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public void insert(WkRouteCore record) {
        getSqlMapClientTemplate().insert("WK_ROUTE_CORE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public void insertSelective(WkRouteCore record) {
        getSqlMapClientTemplate().insert("WK_ROUTE_CORE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public WkRouteCore selectByPrimaryKey(String routeid, Integer week, Integer year) {
        WkRouteCore key = new WkRouteCore();
        key.setRouteid(routeid);
        key.setWeek(week);
        key.setYear(year);
        WkRouteCore record = (WkRouteCore) getSqlMapClientTemplate().queryForObject("WK_ROUTE_CORE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkRouteCore record) {
        int rows = getSqlMapClientTemplate().update("WK_ROUTE_CORE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public int updateByPrimaryKey(WkRouteCore record) {
        int rows = getSqlMapClientTemplate().update("WK_ROUTE_CORE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<WkRouteCore> filter(String week,String year, String fromNode, String toNode){
    	WkRouteCore key = new WkRouteCore();
    	key.setWeek(Integer.parseInt(week));
    	key.setYear(Integer.parseInt(year));
        key.setFromNode(fromNode);
        key.setToNode(toNode);
        return getSqlMapClientTemplate().queryForList("WK_ROUTE_CORE.filter",key);
    }
}