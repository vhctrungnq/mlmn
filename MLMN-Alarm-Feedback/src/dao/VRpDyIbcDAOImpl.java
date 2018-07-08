package dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpDyIbc;


public class VRpDyIbcDAOImpl extends SqlMapClientDaoSupport implements VRpDyIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IBC
     *
     * @ibatorgenerated Mon May 08 15:58:26 ICT 2017
     */
    public VRpDyIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IBC
     *
     * @ibatorgenerated Mon May 08 15:58:26 ICT 2017
     */
    public void insert(VRpDyIbc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IBC
     *
     * @ibatorgenerated Mon May 08 15:58:26 ICT 2017
     */
    public void insertSelective(VRpDyIbc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IBC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getListCellIbc() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("V_RP_DY_IBC.getListCellIbc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VRpDyIbc> getDataList(String startDate, String endDate, int startHour, int endHour, String cellid) {
		// TODO Auto-generated method stub
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("p_start_date", startDate);
		parms.put("p_start_hour", startHour);
		parms.put("p_end_date", endDate);
		parms.put("p_end_hour", endHour);
		parms.put("cellid", cellid);
		parms.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_IBC.getDataList", parms);
	}

	@Override
	public String getThresholdParam(String paramName) {
		// TODO Auto-generated method stub
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("p_param_name", paramName);
		parms.put("p_data", null);
		getSqlMapClientTemplate().queryForObject("V_RP_DY_IBC.getThresholdParam", parms);
		return (String) parms.get("p_data");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VRpDyIbc> getTrafficDataList(String startDate, String endDate, 
			String cellid) {
		// TODO Auto-generated method stub
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("p_start_date", startDate);
		parms.put("p_end_date", endDate);
		parms.put("cellid", cellid);
		parms.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_IBC.getTrafficDataList", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCellIbc(String network) {
		// TODO Auto-generated method stub
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("network", network);
		parms.put("data", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_IBC.getCellIbc", parms);
	}
}