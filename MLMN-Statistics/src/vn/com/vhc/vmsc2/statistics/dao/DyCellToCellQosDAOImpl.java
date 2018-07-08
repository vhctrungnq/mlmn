package vn.com.vhc.vmsc2.statistics.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyCellToCellQos;

public class DyCellToCellQosDAOImpl extends SqlMapClientDaoSupport implements DyCellToCellQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public DyCellToCellQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Date day, String fromCell, String toCell) {
        DyCellToCellQos key = new DyCellToCellQos();
        key.setBscid(bscid);
        key.setDay(day);
        key.setFromCell(fromCell);
        key.setToCell(toCell);
        int rows = getSqlMapClientTemplate().delete("DY_CELL_TO_CELL_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public void insert(DyCellToCellQos record) {
        getSqlMapClientTemplate().insert("DY_CELL_TO_CELL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public void insertSelective(DyCellToCellQos record) {
        getSqlMapClientTemplate().insert("DY_CELL_TO_CELL_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public DyCellToCellQos selectByPrimaryKey(String bscid, Date day, String fromCell, String toCell) {
        DyCellToCellQos key = new DyCellToCellQos();
        key.setBscid(bscid);
        key.setDay(day);
        key.setFromCell(fromCell);
        key.setToCell(toCell);
        DyCellToCellQos record = (DyCellToCellQos) getSqlMapClientTemplate().queryForObject("DY_CELL_TO_CELL_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyCellToCellQos record) {
        int rows = getSqlMapClientTemplate().update("DY_CELL_TO_CELL_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:58:57 ICT 2010
     */
    public int updateByPrimaryKey(DyCellToCellQos record) {
        int rows = getSqlMapClientTemplate().update("DY_CELL_TO_CELL_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<DyCellToCellQos> filterCellToCells(String bscid, String fromCell, Date startDate, Date endDate, int startRecord, int endRecord, String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("column", column);
		map.put("order", order);
		
		return getSqlMapClientTemplate().queryForList("DY_CELL_TO_CELL_QOS.filterCellToCells", map);
	}

	@SuppressWarnings("unchecked")
	public List<DyCellToCellQos> filterCellsToCell(String bscid, String toCell, String startDate, String endDate, int startRecord, int endRecord, String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("column", column);
		map.put("order", order);
		
		return getSqlMapClientTemplate().queryForList("DY_CELL_TO_CELL_QOS.filterCellsToCell", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<DyCellToCellQos> filterCellsToCell(String bscid, String toCell, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return getSqlMapClientTemplate().queryForList("DY_CELL_TO_CELL_QOS.filterCellsToCell3", map);
	}

	public Integer countFromCell(String bscid, String fromCell, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return (Integer) getSqlMapClientTemplate().queryForObject("DY_CELL_TO_CELL_QOS.countFromCell", map);
	}

	public Integer countToCell(String bscid, String toCell, String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return (Integer) getSqlMapClientTemplate().queryForObject("DY_CELL_TO_CELL_QOS.countToCell", map);
	}

	@SuppressWarnings("unchecked")
	public List<DyCellToCellQos> filterCellToCells(String bscid, String fromCell, Date startDate, Date endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return getSqlMapClientTemplate().queryForList("DY_CELL_TO_CELL_QOS.filterCellToCells3", map);
	}
}