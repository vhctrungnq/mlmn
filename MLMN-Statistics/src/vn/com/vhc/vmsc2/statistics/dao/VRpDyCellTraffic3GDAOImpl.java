package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTraffic3G;

public class VRpDyCellTraffic3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellTraffic3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    public VRpDyCellTraffic3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    public void insert(VRpDyCellTraffic3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_TRAFFIC_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFFIC_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    public void insertSelective(VRpDyCellTraffic3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_TRAFFIC_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellTraffic3G> filter(String bscid, String cellid, String startDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_TRAFFIC_3G.filter", map);
	}
}