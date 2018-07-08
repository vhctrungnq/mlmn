package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpDyTrxTotal3g;

public class VRpDyTrxTotal3gDAOImpl extends SqlMapClientDaoSupport implements VRpDyTrxTotal3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_TRX_TOTAL_3G
     *
     * @ibatorgenerated Sun Dec 15 16:14:46 ICT 2013
     */
    public VRpDyTrxTotal3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_TRX_TOTAL_3G
     *
     * @ibatorgenerated Sun Dec 15 16:14:46 ICT 2013
     */
    public void insert(VRpDyTrxTotal3g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_TRX_TOTAL_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_TRX_TOTAL_3G
     *
     * @ibatorgenerated Sun Dec 15 16:14:46 ICT 2013
     */
    public void insertSelective(VRpDyTrxTotal3g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_TRX_TOTAL_3G.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<VRpDyTrxTotal3g> getVRpDyTrxTotal3g(
			String startDate, 
			String endDate, 
			String vendor,
			String rncid) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_START_DATE", startDate);
		parms.put("P_END_DATE", endDate);
		parms.put("P_VENDOR", vendor);
		parms.put("P_RNCID", rncid);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("V_RP_DY_TRX_TOTAL_3G.getVRpDyTrxTotal3g", parms);
	}
}