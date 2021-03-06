package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.MDepartment;
import vo.SYS_PARAMETER;

public class MDepartmentDAOImpl extends SqlMapClientDaoSupport implements MDepartmentDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public MDepartmentDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        MDepartment key = new MDepartment();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("M_DEPARTMENT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public void insert(MDepartment record) {
        getSqlMapClientTemplate().insert("M_DEPARTMENT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public void insertSelective(MDepartment record) {
        getSqlMapClientTemplate().insert("M_DEPARTMENT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public MDepartment selectByPrimaryKey(String deptCode) {
        MDepartment key = new MDepartment();
        key.setDeptCode(deptCode);
        MDepartment record = (MDepartment) getSqlMapClientTemplate().queryForObject("M_DEPARTMENT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public int updateByPrimaryKeySelective(MDepartment record) {
        int rows = getSqlMapClientTemplate().update("M_DEPARTMENT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table M_DEPARTMENT
     *
     * @ibatorgenerated Thu Oct 25 11:19:35 ICT 2012
     */
    public int updateByPrimaryKey(MDepartment record) {
        int rows = getSqlMapClientTemplate().update("M_DEPARTMENT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getUserList(String system) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("system", system);
		
		return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getUserList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<MDepartment> getPhongBanFilter( String maPhong, String tenVietTat, String tenPhong, String isEnable, String column, String order){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DEPT_CODE", maPhong);
		parms.put("P_ABBREVIATED", tenVietTat);
		parms.put("P_DEPT_NAME", tenPhong);
		parms.put("P_IS_ENABLE", isEnable);
		parms.put("P_COLUMN", column);
		parms.put("P_ORDER", order);
		parms.put("P_DATA", null);
		List<MDepartment> phongBan = (List<MDepartment>) getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getPhongBanFilter", parms);
		
		return phongBan;
	}

	public MDepartment selectByID(String id)
	{

		MDepartment key = new MDepartment();
		key.setId(Integer.parseInt(id));
		MDepartment record = (MDepartment) getSqlMapClientTemplate().queryForObject("M_DEPARTMENT.selectByID", key);	
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public List<MDepartment> getPhongBanCha(){
		List<MDepartment> record = getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getPhongBanCha");
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public List<MDepartment> getDepartmentList(){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
		List<MDepartment> record = (List<MDepartment>) getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getDepartmentList", parms);
		
		return record;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getDepartmentDontId(String id){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", id);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getDepartmentDontId", parms);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getDepartementBySystem(){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getDepartmentBySys", parms);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> checkUserExcuteExits(String alarm, String user) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("system", alarm);
		parms.put("username", user);
		return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.checkUserExcuteExits", parms);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> distinctNhomPhongBan(){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
		List<MDepartment> record = getSqlMapClientTemplate().queryForList("M_DEPARTMENT.distinctNhomPhongBan", parms);
		
		return record;
	}
	
	@Override
	public int countPhongBanCha(Integer id){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", id);
		parms.put("P_DATA", null);
		getSqlMapClientTemplate().queryForObject("M_DEPARTMENT.countPhongBanCha", parms);
		Integer record = (Integer) parms.get("P_DATA");
		
		return record;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> getSystemListBySp(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getSystemListBySp", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> titleDepartment(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.titleDepartment", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> getTrangThai(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getTrangThai", parms);
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> checkUserAlarmExits(String user) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DEPARMENT", user);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.checkUserAlarmExits", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<MDepartment> getDepartmentByEquipment(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getDepartmentByEquipment", parms);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getDepartmentForShiftList(String stt) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_STT", stt);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getDepartmentForShiftList", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> loadTeamByDepartment(String department) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DEPARTMENT", department);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.loadTeamByDepartment", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getVMDepartmentBacklog(String deptCode) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DEPT_CODE", deptCode);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getVMDepartmentBacklog", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getDepartmentOfUser(String userLogin) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_USERNAME", userLogin);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getDepartmentOfUser", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MDepartment> getGroupByDeptTeam(String dept, String team) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DEPARTMENT", dept);
		parms.put("P_TEAM", team);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("M_DEPARTMENT.getGroupByDeptTeam", parms);
	}
	
}