package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellBhIbc;

public class VRpMnCellBhIbcDAOImpl extends SqlMapClientDaoSupport implements VRpMnCellBhIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_BH_IBC
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public VRpMnCellBhIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_BH_IBC
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public void insert(VRpMnCellBhIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_CELL_BH_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_BH_IBC
     *
     * @ibatorgenerated Sun Dec 12 22:53:34 ICT 2010
     */
    public void insertSelective(VRpMnCellBhIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_CELL_BH_IBC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnCellBhIbc> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startMonth", startMonth.toString());
		map.put("startYear", startYear.toString());
		map.put("endMonth", endMonth.toString());
		map.put("endYear", endYear.toString());
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_CELL_BH_IBC.filter", map);
	}
}