package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHo;

public class VRpWkCellHoDAOImpl extends SqlMapClientDaoSupport implements VRpWkCellHoDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO
     *
     * @ibatorgenerated Tue Nov 16 11:27:09 ICT 2010
     */
    public VRpWkCellHoDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO
     *
     * @ibatorgenerated Tue Nov 16 11:27:09 ICT 2010
     */
    public void insert(VRpWkCellHo record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_HO.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_HO
     *
     * @ibatorgenerated Tue Nov 16 11:27:09 ICT 2010
     */
    public void insertSelective(VRpWkCellHo record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_HO.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkCellHo> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_HO.filterDetails", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpWkCellHo> filter(String bscid, String cellid, Integer week, Integer year, int startRecord, int endRecord, String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("week", week);
		map.put("year",  year);
		map.put("startRecord", startRecord);
        map.put("endRecord", endRecord);
        map.put("column", column);
        map.put("order", order);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_HO.filter", map);
	}

	public Integer countRow(String bscid, String cellid, Integer week, Integer year) {
		VRpWkCellHo key = new VRpWkCellHo();
		key.setBscid(bscid);
		key.setCellid(cellid);
		key.setWeek(week);
		key.setYear(year);
		return (Integer) getSqlMapClientTemplate().queryForObject("V_RP_WK_CELL_HO.countRow", key);
	}
}