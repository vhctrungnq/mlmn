package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTraffic;

public class VRpDyCellTrafficDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellTrafficDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public VRpDyCellTrafficDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public void insert(VRpDyCellTraffic record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_TRAFFIC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public void insertSelective(VRpDyCellTraffic record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_TRAFFIC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellTraffic> filter(String bscid, String cellid, String startDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_TRAFFIC.filter", map);
	}
}