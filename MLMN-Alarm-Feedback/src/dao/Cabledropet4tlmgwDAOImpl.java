package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.Cabledropet4tlmgw;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Cabledropet4tlmgwFilter;

public class Cabledropet4tlmgwDAOImpl extends SqlMapClientDaoSupport implements Cabledropet4tlmgwDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_TLMGW
     *
     * @ibatorgenerated Tue Apr 09 13:57:58 ICT 2013
     */
    public Cabledropet4tlmgwDAOImpl() {
        super();
    }
    
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_TLMGW
     *
     * @ibatorgenerated Tue Apr 09 13:57:58 ICT 2013
     */
    public void insert(Cabledropet4tlmgw record) {
        getSqlMapClientTemplate().insert("CABLE_DROP_ET4_TLMGW.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_DROP_ET4_TLMGW
     *
     * @ibatorgenerated Tue Apr 09 13:57:58 ICT 2013
     */
    public void insertSelective(Cabledropet4tlmgw record) {
        getSqlMapClientTemplate().insert("CABLE_DROP_ET4_TLMGW.ibatorgenerated_insertSelective", record);
    }
    
    
    public int updateByPrimaryKey(Cabledropet4tlmgw record) {
        int rows = getSqlMapClientTemplate().update("CABLE_DROP_ET4_TLMGW.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    public Cabledropet4tlmgw selectById(Integer id){
    	Cabledropet4tlmgw key = new Cabledropet4tlmgw();
        key.setId(id);
        Cabledropet4tlmgw record = (Cabledropet4tlmgw) getSqlMapClientTemplate().queryForObject("CABLE_DROP_ET4_TLMGW.ibatorgenerated_selectById", key);
        return record;
    }
    
    public int deleteByPrimaryKey(Integer id) {
    	Cabledropet4tlmgw key = new Cabledropet4tlmgw();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CABLE_DROP_ET4_TLMGW.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }
    
    public Cabledropet4tlmgw selectByPrimaryKey(String system, String frSlotPort, String ddfEt4){
    	Cabledropet4tlmgw key = new Cabledropet4tlmgw();
        key.setSystem(system);
        key.setFrSlotPort(frSlotPort);
        key.setDdfEt4(ddfEt4);
        Cabledropet4tlmgw record = (Cabledropet4tlmgw) getSqlMapClientTemplate().queryForObject("CABLE_DROP_ET4_TLMGW.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<Cabledropet4tlmgw> getList(Cabledropet4tlmgwFilter filter,String column, int order, Integer delData){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("p_SYSTEM",filter.getSystem());
		map.put("p_FR_SLOT_PORT",filter.getFrSlotPort());
		map.put("p_DDF_ET4", filter.getDdfEt4());
		
		map.put("p_COLUMN", column);
		map.put("p_ORDER", order);
		map.put("p_DELDATA", delData);
		map.put("p_DATA", null);
		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_TLMGW.getList", map);

	}
    
    @SuppressWarnings("unchecked")
   	@Override
   	public List<SYS_PARAMETER> titleForm(String type_form){
   		Map<String, Object> map = new HashMap<String, Object>(); 
   		map.put("p_TYPE_FORM",type_form); 
   		 
   		map.put("p_DATA", null);
   		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_TLMGW.titleForm", map);

   	}
   	
   	@SuppressWarnings("unchecked")
	@Override
	public List<Cabledropet4tlmgw> getEt4MgwExist(String system, String frSlotPort, String id){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("P_SYSTEM",system);
		map.put("P_FR_SLOT_PORT",frSlotPort);
		map.put("P_ID", id);
		map.put("p_DATA", null);
		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_TLMGW.getEt4MgwExist", map);

	}
   	
   	@SuppressWarnings("unchecked")
	public List<String> getAllSystems(String term) {
		return getSqlMapClientTemplate().queryForList("CABLE_DROP_ET4_TLMGW.getAllSystem", term);
	}
}