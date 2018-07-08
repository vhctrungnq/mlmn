package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.SysUserRoles;

public class SysUserRolesDAOImpl extends SqlMapClientDaoSupport implements SysUserRolesDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public SysUserRolesDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        SysUserRoles key = new SysUserRoles();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("SYS_USER_ROLES.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public void insert(SysUserRoles record) {
        getSqlMapClientTemplate().insert("SYS_USER_ROLES.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public void insertSelective(SysUserRoles record) {
        getSqlMapClientTemplate().insert("SYS_USER_ROLES.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public SysUserRoles selectByPrimaryKey(Integer id) {
        SysUserRoles key = new SysUserRoles();
        key.setId(id);
        SysUserRoles record = (SysUserRoles) getSqlMapClientTemplate().queryForObject("SYS_USER_ROLES.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public int updateByPrimaryKeySelective(SysUserRoles record) {
        int rows = getSqlMapClientTemplate().update("SYS_USER_ROLES.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_USER_ROLES
     *
     * @ibatorgenerated Wed Oct 24 15:52:36 ICT 2012
     */
    public int updateByPrimaryKey(SysUserRoles record) {
        int rows = getSqlMapClientTemplate().update("SYS_USER_ROLES.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    public int countRowSysUserRolesByUsername(String username){
		
		Integer rows = (Integer) getSqlMapClientTemplate().queryForObject("SYS_USER_ROLES.countRowSysUserRolesByUsername", username);
        return rows;
    }
    
    public int updateByUsername(SysUserRoles record) {
        int rows = getSqlMapClientTemplate().update("SYS_USER_ROLES.updateByUsername", record);
        return rows;
    }
    
    public int deleteByUsername(String username) {
        int rows = getSqlMapClientTemplate().delete("SYS_USER_ROLES.deleteByUsername", username);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SysUserRoles> selectSysUserRolesByUsername(String username){
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", username);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("SYS_USER_ROLES.selectSysUserRolesByUsername", parms);
    }
/*Phan quyen quan ly khu vuc cho user. AnhCTV.23.09.2015*/
	@Override
	public int insertRegionRole(String username, String regionList,
			String createdBy) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_USERNAME", username);
       	parms.put("P_REGION_LIST", regionList);
   		parms.put("P_CREATED_BY", createdBy);
           int rows = getSqlMapClientTemplate().update("SYS_USER_ROLES.insertRegionRole", parms);
           return rows;
	}
    @SuppressWarnings("unchecked")
	@Override
	public List<SysUserRoles> selectRegionRoleByUsername(String username) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", username);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("SYS_USER_ROLES.selectRegionRoleByUsername", parms);
	}

	
}