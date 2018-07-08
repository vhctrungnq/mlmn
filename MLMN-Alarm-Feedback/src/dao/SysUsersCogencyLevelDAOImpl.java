package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.SysUsersCogencyLevel;

public class SysUsersCogencyLevelDAOImpl extends SqlMapClientDaoSupport implements SysUsersCogencyLevelDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public SysUsersCogencyLevelDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        SysUsersCogencyLevel key = new SysUsersCogencyLevel();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("SYS_USERS_COGENCY_LEVEL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public void insert(SysUsersCogencyLevel record) {
        getSqlMapClientTemplate().insert("SYS_USERS_COGENCY_LEVEL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public void insertSelective(SysUsersCogencyLevel record) {
        getSqlMapClientTemplate().insert("SYS_USERS_COGENCY_LEVEL.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public SysUsersCogencyLevel selectByPrimaryKey(Integer id) {
        SysUsersCogencyLevel key = new SysUsersCogencyLevel();
        key.setId(id);
        SysUsersCogencyLevel record = (SysUsersCogencyLevel) getSqlMapClientTemplate().queryForObject("SYS_USERS_COGENCY_LEVEL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public int updateByPrimaryKeySelective(SysUsersCogencyLevel record) {
        int rows = getSqlMapClientTemplate().update("SYS_USERS_COGENCY_LEVEL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USERS_COGENCY_LEVEL
     *
     * @ibatorgenerated Sun Oct 28 22:48:57 ICT 2012
     */
    public int updateByPrimaryKey(SysUsersCogencyLevel record) {
        int rows = getSqlMapClientTemplate().update("SYS_USERS_COGENCY_LEVEL.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<SysUsersCogencyLevel> loadCogencyLevelByUsername(String username){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", username);
		parms.put("P_DATA", null);
		
		List<SysUsersCogencyLevel> record = getSqlMapClientTemplate().queryForList("SYS_USERS_COGENCY_LEVEL.loadCogencyLevelByUsername", parms);
		
		return record;
	}
    
    @SuppressWarnings("unchecked")
	public List<SysUsersCogencyLevel> selectCogencyLevelByUsernameLevel(String username, String level){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", username);
		parms.put("P_LEVEL", level);
		parms.put("P_DATA", null);
		
		getSqlMapClientTemplate().queryForList("SYS_USERS_COGENCY_LEVEL.selectCogencyLevelByUsernameLevel", parms);
		List<SysUsersCogencyLevel> record = (List<SysUsersCogencyLevel>) parms.get("P_DATA");
		
		return record;
	}
    
    public int delete(String username, String level) {
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("username", username);
		parms.put("lever", level);
        int rows = getSqlMapClientTemplate().delete("SYS_USERS_COGENCY_LEVEL.delete", parms);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUsersCogencyLevel> getUserForSendSMS() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("SYS_USERS_COGENCY_LEVEL.getUserForSendSMS", parms);
		
	}
}