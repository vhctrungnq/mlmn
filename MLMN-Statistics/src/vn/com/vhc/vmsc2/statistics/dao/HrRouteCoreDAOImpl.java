package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrRouteCore;

public class HrRouteCoreDAOImpl extends SqlMapClientDaoSupport implements HrRouteCoreDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public HrRouteCoreDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String fromNode, Integer hour, String routeid, String toNode) {
        HrRouteCore key = new HrRouteCore();
        key.setDay(day);
        key.setFromNode(fromNode);
        key.setHour(hour);
        key.setRouteid(routeid);
        key.setToNode(toNode);
        int rows = getSqlMapClientTemplate().delete("HR_ROUTE_CORE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public void insert(HrRouteCore record) {
        getSqlMapClientTemplate().insert("HR_ROUTE_CORE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public void insertSelective(HrRouteCore record) {
        getSqlMapClientTemplate().insert("HR_ROUTE_CORE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public HrRouteCore selectByPrimaryKey(Date day, String fromNode, Integer hour, String routeid, String toNode) {
        HrRouteCore key = new HrRouteCore();
        key.setDay(day);
        key.setFromNode(fromNode);
        key.setHour(hour);
        key.setRouteid(routeid);
        key.setToNode(toNode);
        HrRouteCore record = (HrRouteCore) getSqlMapClientTemplate().queryForObject("HR_ROUTE_CORE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrRouteCore record) {
        int rows = getSqlMapClientTemplate().update("HR_ROUTE_CORE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_ROUTE_CORE
     *
     * @ibatorgenerated Tue Oct 26 15:40:07 ICT 2010
     */
    public int updateByPrimaryKey(HrRouteCore record) {
        int rows = getSqlMapClientTemplate().update("HR_ROUTE_CORE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<HrRouteCore> filter(Date day,String hour, String fromNode, String toNode){
    	HrRouteCore key = new HrRouteCore();
    	key.setDay(day);
    	key.setHour(Integer.parseInt(hour));
        key.setFromNode(fromNode);
        key.setToNode(toNode);
        return getSqlMapClientTemplate().queryForList("HR_ROUTE_CORE.filter",key);
    }
}