package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.CableDropEt4direction;
import vo.SYS_PARAMETER;
import vo.alarm.utils.CableDropEt4directionFilter;

public class CableDropEt4directionDAOImpl extends SqlMapClientDaoSupport implements CableDropEt4directionDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_DIRECTIONS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public CableDropEt4directionDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_DIRECTIONS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
    	CableDropEt4direction key = new CableDropEt4direction();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }
    
    
    
    public void insert(CableDropEt4direction record) {
        getSqlMapClientTemplate().insert("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_DIRECTIONS
     *
     * @ibatorgenerated Wed Apr 03 10:44:11 ICT 2013
     */
    public void insertSelective(CableDropEt4direction record) {
        getSqlMapClientTemplate().insert("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_insertSelective", record);
    }
    
    
    public CableDropEt4direction selectById(Integer id) {
    	CableDropEt4direction key = new CableDropEt4direction();
        key.setId(id);
        CableDropEt4direction record = (CableDropEt4direction) getSqlMapClientTemplate().queryForObject("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_selectById", key);
        return record;
    }
    
    public CableDropEt4direction selectByPrimaryKey(String circuit, String ddfHead, String ddfFinish){
    	CableDropEt4direction key = new CableDropEt4direction();
        key.setCircuit(circuit);
        key.setDdfHead(ddfHead);
        key.setDdfFinish(ddfFinish);
        CableDropEt4direction record = (CableDropEt4direction) getSqlMapClientTemplate().queryForObject("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }
    
    public int updateByPrimaryKeySelective(CableDropEt4direction record) {
        int rows = getSqlMapClientTemplate().update("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public int updateByPrimaryKey(CableDropEt4direction record) {
        int rows = getSqlMapClientTemplate().update("CABLE_DROP_ET4_DIRECTIONS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
 	@Override
 	public List<CableDropEt4direction> getList(CableDropEt4directionFilter filter,String column, int order, Integer delData){
 		Map<String, Object> map = new HashMap<String, Object>(); 
 		map.put("p_CIRCUIT",filter.getCircuit());
 		map.put("p_DIP_NAME", filter.getDipName());
 		map.put("p_DIP_FINISH", filter.getDipFinish());
 		map.put("p_DIRECTION", filter.getDirection());
 		map.put("p_DIPP", filter.getDipp());
 		map.put("p_DDF_HEAD", filter.getDdfHead());
 		map.put("p_DDF_FINISH", filter.getDdfFinish());
 		map.put("p_STATUS", filter.getStatus());
 		
 		
 		map.put("p_COLUMN", column);
 		map.put("p_ORDER", order);
 		map.put("p_DELDATA", delData);
 		
 		map.put("p_DATA", null);
 		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_DIRECTIONS.getList", map);

 	}
     
     @SuppressWarnings("unchecked")
    	@Override
    	public List<SYS_PARAMETER> titleForm(String type_form){
    		Map<String, Object> map = new HashMap<String, Object>(); 
    		map.put("p_TYPE_FORM",type_form); 
    		 
    		map.put("p_DATA", null);
    		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_DIRECTIONS.titleForm", map);

    	}
    	
    	@SuppressWarnings("unchecked")
    	public List<String> getAllCiruits(String term) {
    		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_DIRECTIONS.getAllCiruit", term);
    	}
}