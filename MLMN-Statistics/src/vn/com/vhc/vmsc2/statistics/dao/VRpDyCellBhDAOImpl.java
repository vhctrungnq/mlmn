package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBh;

public class VRpDyCellBhDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public VRpDyCellBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public void insert(VRpDyCellBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public void insertSelective(VRpDyCellBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellBh> filter(String startDate, String endDate, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_BH.filter", map);
	}
}