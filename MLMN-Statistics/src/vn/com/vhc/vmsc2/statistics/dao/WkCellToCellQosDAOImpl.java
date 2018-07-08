package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkCellToCellQos;

public class WkCellToCellQosDAOImpl extends SqlMapClientDaoSupport implements WkCellToCellQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public WkCellToCellQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, String fromCell, String toCell, Integer week, Integer year) {
        WkCellToCellQos key = new WkCellToCellQos();
        key.setBscid(bscid);
        key.setFromCell(fromCell);
        key.setToCell(toCell);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_CELL_TO_CELL_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public void insert(WkCellToCellQos record) {
        getSqlMapClientTemplate().insert("WK_CELL_TO_CELL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public void insertSelective(WkCellToCellQos record) {
        getSqlMapClientTemplate().insert("WK_CELL_TO_CELL_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public WkCellToCellQos selectByPrimaryKey(String bscid, String fromCell, String toCell, Integer week, Integer year) {
        WkCellToCellQos key = new WkCellToCellQos();
        key.setBscid(bscid);
        key.setFromCell(fromCell);
        key.setToCell(toCell);
        key.setWeek(week);
        key.setYear(year);
        WkCellToCellQos record = (WkCellToCellQos) getSqlMapClientTemplate().queryForObject("WK_CELL_TO_CELL_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkCellToCellQos record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TO_CELL_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:51 ICT 2010
     */
    public int updateByPrimaryKey(WkCellToCellQos record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TO_CELL_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkCellToCellQos> filterCellToCells(String bscid, String fromCell, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, int startRecord, int endRecord, String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startYear", startYear.toString());
		map.put("startWeek", startWeek.toString());
		map.put("endYear",  endYear.toString());
		map.put("endWeek", endWeek.toString());
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("column", column);
		map.put("order", order);
		
		return getSqlMapClientTemplate().queryForList("WK_CELL_TO_CELL_QOS.filterCellToCells", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<WkCellToCellQos> filterCellToCells(String bscid, String fromCell, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startYear", startYear.toString());
		map.put("startWeek", startWeek.toString());
		map.put("endYear",  endYear.toString());
		map.put("endWeek", endWeek.toString());
		
		return getSqlMapClientTemplate().queryForList("WK_CELL_TO_CELL_QOS.filterCellToCells3", map);
	}

	@SuppressWarnings("unchecked")
	public List<WkCellToCellQos> filterCellsToCell(String bscid, String toCell, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, int startRecord, int endRecord, String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startYear", startYear.toString());
		map.put("startWeek", startWeek.toString());
		map.put("endYear",  endYear.toString());
		map.put("endWeek", endWeek.toString());
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("column", column);
		map.put("order", order);
		
		return getSqlMapClientTemplate().queryForList("WK_CELL_TO_CELL_QOS.filterCellsToCell", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<WkCellToCellQos> filterCellsToCell(String bscid, String toCell, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startYear", startYear.toString());
		map.put("startWeek", startWeek.toString());
		map.put("endYear",  endYear.toString());
		map.put("endWeek", endWeek.toString());
		
		return getSqlMapClientTemplate().queryForList("WK_CELL_TO_CELL_QOS.filterCellsToCell3", map);
	}

	public Integer countFromCell(String bscid, String fromCell, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startYear", startYear.toString());
		map.put("startWeek", startWeek.toString());
		map.put("endYear",  endYear.toString());
		map.put("endWeek", endWeek.toString());
		
		return (Integer) getSqlMapClientTemplate().queryForObject("WK_CELL_TO_CELL_QOS.countFromCell", map);
	}

	public Integer countToCell(String bscid, String toCell, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startYear", startYear.toString());
		map.put("startWeek", startWeek.toString());
		map.put("endYear",  endYear.toString());
		map.put("endWeek", endWeek.toString());
		
		return (Integer) getSqlMapClientTemplate().queryForObject("WK_CELL_TO_CELL_QOS.countFromCell", map);
	}
}