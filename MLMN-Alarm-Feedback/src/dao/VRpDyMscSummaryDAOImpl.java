package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpDyMscSummary;


public class VRpDyMscSummaryDAOImpl extends SqlMapClientDaoSupport implements VRpDyMscSummaryDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_SUMMARY
     *
     * @ibatorgenerated Mon Jan 07 11:53:57 ICT 2013
     */
    public VRpDyMscSummaryDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_SUMMARY
     *
     * @ibatorgenerated Mon Jan 07 11:53:57 ICT 2013
     */
    public void insert(VRpDyMscSummary record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MSC_SUMMARY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_SUMMARY
     *
     * @ibatorgenerated Mon Jan 07 11:53:57 ICT 2013
     */
    public void insertSelective(VRpDyMscSummary record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MSC_SUMMARY.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyMscSummary> filterCSCore(String startDate, String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("startDate",startDate);
    	map.put("endDate",endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_SUMMARY.filter", map);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyMscSummary> filerOfHomePage(String tableName, String day, String region) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_DAY", 		day);
    	parms.put("P_REGION", 		region);
    	parms.put("P_DATA", 				null);
    	
		
		List<VRpDyMscSummary> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_SUMMARY.filerOfHomePage", parms);
        return record;
	}
}