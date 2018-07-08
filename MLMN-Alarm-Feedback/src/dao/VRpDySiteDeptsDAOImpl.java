package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpDySiteDepts;

public class VRpDySiteDeptsDAOImpl extends SqlMapClientDaoSupport implements VRpDySiteDeptsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_DEPTS
     *
     * @ibatorgenerated Wed May 28 15:59:50 ICT 2014
     */
    public VRpDySiteDeptsDAOImpl() {
        super();
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<VRpDySiteDepts> getInfo(String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate); 
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("V_RP_DY_SITE_DEPTS.getInfo", map);
	}
}