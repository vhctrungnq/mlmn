package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.CableSwitchboard;

public class CableSwitchboardDAOImpl extends SqlMapClientDaoSupport implements CableSwitchboardDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public CableSwitchboardDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public int deleteByPrimaryKey(Integer id) {
        CableSwitchboard key = new CableSwitchboard();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CABLE_SWITCHBOARD.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public void insert(CableSwitchboard record) {
        getSqlMapClientTemplate().insert("CABLE_SWITCHBOARD.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public void insertSelective(CableSwitchboard record) {
        getSqlMapClientTemplate().insert("CABLE_SWITCHBOARD.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public CableSwitchboard selectByPrimaryKey(Integer id) {
        CableSwitchboard key = new CableSwitchboard();
        key.setId(id);
        CableSwitchboard record = (CableSwitchboard) getSqlMapClientTemplate().queryForObject("CABLE_SWITCHBOARD.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public int updateByPrimaryKeySelective(CableSwitchboard record) {
        int rows = getSqlMapClientTemplate().update("CABLE_SWITCHBOARD.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_SWITCHBOARD
     *
     * @ibatorgenerated Fri Feb 28 11:10:57 ICT 2014
     */
    public int updateByPrimaryKey(CableSwitchboard record) {
        int rows = getSqlMapClientTemplate().update("CABLE_SWITCHBOARD.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<CableSwitchboard> getCableSwitchboardFilter(String vendor, String site, String name,
			Integer startRecord, Integer endRecord, String sortfield, String sortorder, String strWhere) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_VENDOR", vendor);
    	map.put("P_SITE", site);
    	map.put("P_NAME", name);
    	map.put("P_START_RECORD", startRecord);
    	map.put("P_END_RECORD", endRecord);
    	map.put("P_SOFT_FIELD", sortfield);
    	map.put("P_SOFT_ORDER", sortorder);
    	map.put("P_STR_WHERE", strWhere);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_SWITCHBOARD.getCableSwitchboardFilter", map);
    }
    
    @Override
    public int countCableSwitchboard(String vendor, String site, String name, String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_VENDOR", vendor);
    	map.put("P_SITE", site);
    	map.put("P_NAME", name);
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("CABLE_SWITCHBOARD.countCableSwitchboard", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<CableSwitchboard> checkCableSwitchboardUk(String vendor, String neType, String name,
			String site, String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_VENDOR", vendor);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_NAME", name);
    	map.put("P_SITE", site);
    	map.put("P_ID", id);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_SWITCHBOARD.checkCableSwitchboardUk", map);
    }
    
    @Override
    public int insertAndResult(CableSwitchboard record) {
        Integer id = (Integer) getSqlMapClientTemplate().insert("CABLE_SWITCHBOARD.insertAndResult", record);
        return id;
    }
}