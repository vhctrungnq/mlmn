package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpDySiteGroupsCausby;

public class VRpDySiteGroupsCausbyDAOImpl extends SqlMapClientDaoSupport implements VRpDySiteGroupsCausbyDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_GROUPS_CAUSBY
     *
     * @ibatorgenerated Fri Aug 29 14:52:31 ICT 2014
     */
    public VRpDySiteGroupsCausbyDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_GROUPS_CAUSBY
     *
     * @ibatorgenerated Fri Aug 29 14:52:31 ICT 2014
     */
    public void insert(VRpDySiteGroupsCausby record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE_GROUPS_CAUSBY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_GROUPS_CAUSBY
     *
     * @ibatorgenerated Fri Aug 29 14:52:31 ICT 2014
     */
    public void insertSelective(VRpDySiteGroupsCausby record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE_GROUPS_CAUSBY.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
	@Override
	public List<VRpDySiteGroupsCausby> getInfo(String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate); 
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("V_RP_DY_SITE_GROUPS_CAUSBY.getInfo", map);
	} 
}