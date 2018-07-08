package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBhIbc;

public class VRpDyCellBhIbcDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellBhIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH_IBC
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public VRpDyCellBhIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH_IBC
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public void insert(VRpDyCellBhIbc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BH_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BH_IBC
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public void insertSelective(VRpDyCellBhIbc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BH_IBC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellBhIbc> filter(String startDate, String endDate, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_BH_IBC.filter", map);
	}
}