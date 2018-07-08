package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.HRouter;
import vo.alarm.utils.HRouterFilter;

public class HRouterDAOImpl extends SqlMapClientDaoSupport implements HRouterDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public HRouterDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        HRouter key = new HRouter();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("H_ROUTER.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public void insert(HRouter record) {
        getSqlMapClientTemplate().insert("H_ROUTER.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public void insertSelective(HRouter record) {
        getSqlMapClientTemplate().insert("H_ROUTER.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public HRouter selectByPrimaryKey(Integer id) {
        HRouter key = new HRouter();
        key.setId(id);
        HRouter record = (HRouter) getSqlMapClientTemplate().queryForObject("H_ROUTER.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public HRouter selectByUnikey(String routerName) {
        HRouter key = new HRouter();
        key.setRouterName(routerName);
        HRouter record = (HRouter) getSqlMapClientTemplate().queryForObject("H_ROUTER.selectByUnikey", key);
        return record;
    }
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public int updateByPrimaryKeySelective(HRouter record) {
        int rows = getSqlMapClientTemplate().update("H_ROUTER.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_ROUTER
     *
     * @ibatorgenerated Mon Feb 04 09:35:03 ICT 2013
     */
    public int updateByPrimaryKey(HRouter record) {
        int rows = getSqlMapClientTemplate().update("H_ROUTER.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
	@SuppressWarnings("unchecked")
	public List<HRouter> filter(HRouterFilter filter) {
		return getSqlMapClientTemplate().queryForList("H_ROUTER.filter", filter);
	}
	
	@SuppressWarnings("unchecked")
	public List<HRouter> getRouterName(String routeName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("routeName", routeName);
		return getSqlMapClientTemplate().queryForList("H_ROUTER.getRouterName",map);
	}
	
	@SuppressWarnings("unchecked")
	public List<HRouter> getRouterId(){
		return getSqlMapClientTemplate().queryForList("H_ROUTER.getRouterId");
	}
}