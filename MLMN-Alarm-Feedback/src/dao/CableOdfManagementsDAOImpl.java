package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.CableOdfManagements;

public class CableOdfManagementsDAOImpl extends SqlMapClientDaoSupport implements CableOdfManagementsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public CableOdfManagementsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        CableOdfManagements key = new CableOdfManagements();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("CABLE_ODF_MANAGEMENTS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public void insert(CableOdfManagements record) {
        getSqlMapClientTemplate().insert("CABLE_ODF_MANAGEMENTS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public void insertSelective(CableOdfManagements record) {
        getSqlMapClientTemplate().insert("CABLE_ODF_MANAGEMENTS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public CableOdfManagements selectByPrimaryKey(Integer id) {
        CableOdfManagements key = new CableOdfManagements();
        key.setId(id);
        CableOdfManagements record = (CableOdfManagements) getSqlMapClientTemplate().queryForObject("CABLE_ODF_MANAGEMENTS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public int updateByPrimaryKeySelective(CableOdfManagements record) {
        int rows = getSqlMapClientTemplate().update("CABLE_ODF_MANAGEMENTS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_ODF_MANAGEMENTS
     *
     * @ibatorgenerated Wed Apr 03 15:30:35 ICT 2013
     */
    public int updateByPrimaryKey(CableOdfManagements record) {
        int rows = getSqlMapClientTemplate().update("CABLE_ODF_MANAGEMENTS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<CableOdfManagements> getCableOdfManFilter(String schemaName, String name, String vendor, String schemaLink, String nameLink, String column, String order, Integer delData){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SCHEMA_NAME", schemaName);
		map.put("P_NAME", name);
		map.put("P_VENDOR", vendor);
		map.put("P_SCHEMA_LINK", schemaLink);
		map.put("P_NAME_LINK", nameLink);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DELDATA", delData);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_ODF_MANAGEMENTS.getCableOdfManFilter", map);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<CableOdfManagements> testInsertPortSchemaname(String idOdfTypes, String port, String id){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_ID_ODF_TYPES", idOdfTypes);
		map.put("P_PORT", port);
		map.put("P_ID", id);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("CABLE_ODF_MANAGEMENTS.testInsertPortSchemaname", map);
    }
}