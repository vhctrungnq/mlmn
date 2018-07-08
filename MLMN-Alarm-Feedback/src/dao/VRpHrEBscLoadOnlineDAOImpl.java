package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpHrEBscLoad3gOnline;
import vo.VRpHrEBscLoadOnline;
import vo.VRpHrNLoadOnline;

public class VRpHrEBscLoadOnlineDAOImpl extends SqlMapClientDaoSupport implements VRpHrEBscLoadOnlineDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_E_BSC_LOAD_ONLINE
     *
     * @ibatorgenerated Mon Jan 13 11:15:04 ICT 2014
     */
    public VRpHrEBscLoadOnlineDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_E_BSC_LOAD_ONLINE
     *
     * @ibatorgenerated Mon Jan 13 11:15:04 ICT 2014
     */
    public void insert(VRpHrEBscLoadOnline record) {
        getSqlMapClientTemplate().insert("V_RP_HR_E_BSC_LOAD_ONLINE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_E_BSC_LOAD_ONLINE
     *
     * @ibatorgenerated Mon Jan 13 11:15:04 ICT 2014
     */
    public void insertSelective(VRpHrEBscLoadOnline record) {
        getSqlMapClientTemplate().insert("V_RP_HR_E_BSC_LOAD_ONLINE.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpHrEBscLoad3gOnline> filter3G(String function, String startDate, String endDate, String ne, String cardType) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FUNCTION", 		function);
		parms.put("P_START_DATE", 		startDate); 
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_NE", 		ne);
    	parms.put("P_CARDTYPE", 		cardType);
    	parms.put("P_DATA", 				null);
    	 
		List<VRpHrEBscLoad3gOnline> record = getSqlMapClientTemplate().queryForList("V_RP_HR_E_BSC_LOAD_3G_ONLINE.filter3G", parms);
        return record;
	}
    @SuppressWarnings("unchecked")
    public List<VRpHrEBscLoadOnline> filterEricsson(String function, String startDate, String endDate, String ne, String cardType) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FUNCTION", 		function);
		parms.put("P_START_DATE", 		startDate); 
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_NE", 		ne);
    	parms.put("P_CARDTYPE", 		cardType);
    	parms.put("P_DATA", 				null);
    	 
		List<VRpHrEBscLoadOnline> record = getSqlMapClientTemplate().queryForList("V_RP_HR_E_BSC_LOAD_ONLINE.filterEricsson", parms);
        return record;
	}
    @SuppressWarnings("unchecked")
    public List<VRpHrNLoadOnline> filterNokia(String function, String startDate, String endDate, String ne, String cardType) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FUNCTION", 		function);
		parms.put("P_START_DATE", 		startDate); 
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_NE", 		ne);
    	parms.put("P_CARDTYPE", 		cardType);
    	parms.put("P_DATA", 				null);
    	 
		List<VRpHrNLoadOnline> record = getSqlMapClientTemplate().queryForList("V_RP_HR_N_LOAD_ONLINE.filterNokia", parms);
        return record;
	}
}