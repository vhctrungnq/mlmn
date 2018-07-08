package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHoIbc;

public class VRpWkCellHoIbcDAOImpl extends SqlMapClientDaoSupport implements VRpWkCellHoIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO_IBC
     *
     * @ibatorgenerated Tue Nov 16 11:27:09 ICT 2010
     */
    public VRpWkCellHoIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO_IBC
     *
     * @ibatorgenerated Tue Nov 16 11:27:09 ICT 2010
     */
    public void insert(VRpWkCellHoIbc record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_HO_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO_IBC
     *
     * @ibatorgenerated Tue Nov 16 11:27:09 ICT 2010
     */
    public void insertSelective(VRpWkCellHoIbc record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_HO_IBC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkCellHoIbc> filter(String bscid, String cellid, Integer week, Integer year) {
		VRpWkCellHoIbc key = new VRpWkCellHoIbc();
		key.setBscid(bscid);
		key.setCellid(cellid);
		key.setWeek(week);
		key.setYear(year);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_HO_IBC.filter", key);
	}

	@SuppressWarnings("unchecked")
	public List<VRpWkCellHoIbc> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_HO_IBC.filterDetails", map);
	}
}