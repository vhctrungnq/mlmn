package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrM2000;

public class VRpDyHlrM2000DAOImpl extends SqlMapClientDaoSupport implements VRpDyHlrM2000DAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_HLR_M2000
	 * 
	 * @ibatorgenerated Mon Dec 03 10:14:20 ICT 2012
	 */
	public VRpDyHlrM2000DAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_HLR_M2000
	 * 
	 * @ibatorgenerated Mon Dec 03 10:14:20 ICT 2012
	 */
	public void insert(VRpDyHlrM2000 record) {
		getSqlMapClientTemplate().insert("V_RP_DY_HLR_M2000.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_DY_HLR_M2000
	 * 
	 * @ibatorgenerated Mon Dec 03 10:14:20 ICT 2012
	 */
	public void insertSelective(VRpDyHlrM2000 record) {
		getSqlMapClientTemplate().insert("V_RP_DY_HLR_M2000.ibatorgenerated_insertSelective", record);
	}

	@SuppressWarnings("unchecked")
	public List<VRpDyHlrM2000> filter(String startDate, String endDate, String nodeid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("nodeid", nodeid);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_HLR_M2000.filter", map);
	}
}