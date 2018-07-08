package dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.HProvinceFb;
import vo.SYS_PARAMETER;
import vo.alarm.utils.ProvinceFilter;

public class HProvinceFbDAOImpl extends SqlMapClientDaoSupport implements HProvinceFbDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public HProvinceFbDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public int deleteByPrimaryKey(Integer id) {
        HProvinceFb key = new HProvinceFb();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("H_PROVINCE_FB.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public void insert(HProvinceFb record) {
        getSqlMapClientTemplate().insert("H_PROVINCE_FB.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public void insertSelective(HProvinceFb record) {
        getSqlMapClientTemplate().insert("H_PROVINCE_FB.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public HProvinceFb selectByPrimaryKey(Integer id) {
        HProvinceFb key = new HProvinceFb();
        key.setId(id);
        HProvinceFb record = (HProvinceFb) getSqlMapClientTemplate().queryForObject("H_PROVINCE_FB.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public int updateByPrimaryKeySelective(HProvinceFb record) {
        int rows = getSqlMapClientTemplate().update("H_PROVINCE_FB.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_PROVINCE_FB
     *
     * @ibatorgenerated Wed Aug 28 16:01:26 ICT 2013
     */
    public int updateByPrimaryKey(HProvinceFb record) {
        int rows = getSqlMapClientTemplate().update("H_PROVINCE_FB.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<HProvinceFb> selectProvincesCode(){
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
		
		List<HProvinceFb> record = getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.selectProvincesCode", parms);
		return record;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getProvinceFbByDeptCode(String deptProcess) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DEPT_CODE", deptProcess);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getProvinceFbByDeptCode", map);
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getDistrictCodeList(String provinceName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_PROVINCE", provinceName);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getDistrictCodeList", map);
	}
    
    @SuppressWarnings("unchecked")
    @Override
    public List<HProvinceFb> distinctProvinceCode(){
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
		
		List<HProvinceFb> record = getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.distinctProvinceCode", parms);
		return record;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String typeForm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_TYPE_FORM", typeForm);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.titleForm", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getProvinceList(ProvinceFilter filter,
			String teamProcess, String column, int order) {
   	Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_CODE", filter.getCode());
		map.put("P_DISTRICT", filter.getDistrict());
		map.put("P_LOCATION", filter.getLocation());
		map.put("P_PROVINCE", filter.getProvince());
		map.put("P_BRANCH", filter.getBranch());
		map.put("P_REGION", filter.getRegion());
		map.put("P_DEPT_CODE", teamProcess);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getProvinceList", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> regionList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.regionList", map);
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> checkExits(String code, String district) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_CODE", code);
		map.put("P_DISTRICT", district);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.checkExits", map);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getProviceByDistrictAndProvince(
			String district, String province) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT_NAME", district);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getProviceByDistrictAndProvince", map);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getTeamFbList(String deptCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DEPT_CODE", deptCode);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getTeamFbList", map);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getProvinceForFbWeekList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getProvinceForFbWeekList", map);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getDataForFeedbackWeek(String startDate, String endDate, String fbType, String fbSendTelecom, String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SEND_TELECOM", fbSendTelecom);
		map.put("P_USER_NAME", username);
		map.put("P_START_DATE", startDate);
		map.put("P_END_DATE", endDate);
		map.put("P_FB_TYPE", fbType);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getDataForFeedbackWeek", map);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getProvinceByRegion(String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_REGION", region);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getProvinceByRegion", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HProvinceFb> getDistrictByRegionProvine(String region,
			String province) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_REGION", region);
		map.put("P_PROVINCE", province);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getDistrictByRegionProvine", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllProvince() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("H_PROVINCE_FB.getAllProvince");
	}
}