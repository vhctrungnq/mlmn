package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.AlManageProject;

public class AlManageProjectDAOImpl extends SqlMapClientDaoSupport implements AlManageProjectDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public AlManageProjectDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        AlManageProject key = new AlManageProject();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("AL_MANAGE_PROJECT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void insert(AlManageProject record) {
        getSqlMapClientTemplate().insert("AL_MANAGE_PROJECT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public void insertSelective(AlManageProject record) {
        getSqlMapClientTemplate().insert("AL_MANAGE_PROJECT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public AlManageProject selectByPrimaryKey(Integer id) {
        AlManageProject key = new AlManageProject();
        key.setId(id);
        AlManageProject record = (AlManageProject) getSqlMapClientTemplate().queryForObject("AL_MANAGE_PROJECT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public int updateByPrimaryKeySelective(AlManageProject record) {
        int rows = getSqlMapClientTemplate().update("AL_MANAGE_PROJECT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_MANAGE_PROJECT
     *
     * @ibatorgenerated Thu Nov 21 14:37:10 ICT 2013
     */
    public int updateByPrimaryKey(AlManageProject record) {
        int rows = getSqlMapClientTemplate().update("AL_MANAGE_PROJECT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AlManageProject> getAlManageProjectFilter(String projectCode, String projectName, String type, String region) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PROJECT_CODE", projectCode);
    	map.put("P_PROJECT_NAME", projectName);
    	map.put("P_TYPE", type);
    	map.put("P_REGION", region);
    	
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AL_MANAGE_PROJECT.getAlManageProjectFilter", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AlManageProject> checkProjectCode(String projectCode, String type, String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PROJECT_CODE", projectCode);
    	map.put("P_TYPE", type);
    	map.put("P_ID", id);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AL_MANAGE_PROJECT.checkProjectCode", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AlManageProject> distinctProjectType(String type) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_TYPE", type);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AL_MANAGE_PROJECT.distinctProjectType", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AlManageProject> distinctVendor(String type) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_TYPE", type);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AL_MANAGE_PROJECT.distinctVendor", map);
    }
    
    @Override
	public AlManageProject getObjectProjectByCode(String projectType, String projectCode) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_PROJECT_TYPE", projectType);
    	map.put("P_PROJECT_CODE", projectCode);
		map.put("P_DATA", null);
		
		return (AlManageProject) getSqlMapClientTemplate().queryForObject("AL_MANAGE_PROJECT.getObjectProjectByCode", map);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<AlManageProject> getProjectUnfinishedList(String type) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_TYPE", type);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("AL_MANAGE_PROJECT.getProjectUnfinishedList", map);
    }
}