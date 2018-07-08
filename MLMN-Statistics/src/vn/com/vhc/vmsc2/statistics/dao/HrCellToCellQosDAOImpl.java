package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrCellToCellQos;

public class HrCellToCellQosDAOImpl extends SqlMapClientDaoSupport implements HrCellToCellQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public HrCellToCellQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Date day, String fromCell, Integer hour, String toCell) {
        HrCellToCellQos key = new HrCellToCellQos();
        key.setBscid(bscid);
        key.setDay(day);
        key.setFromCell(fromCell);
        key.setHour(hour);
        key.setToCell(toCell);
        int rows = getSqlMapClientTemplate().delete("HR_CELL_TO_CELL_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public void insert(HrCellToCellQos record) {
        getSqlMapClientTemplate().insert("HR_CELL_TO_CELL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public void insertSelective(HrCellToCellQos record) {
        getSqlMapClientTemplate().insert("HR_CELL_TO_CELL_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public HrCellToCellQos selectByPrimaryKey(String bscid, Date day, String fromCell, Integer hour, String toCell) {
        HrCellToCellQos key = new HrCellToCellQos();
        key.setBscid(bscid);
        key.setDay(day);
        key.setFromCell(fromCell);
        key.setHour(hour);
        key.setToCell(toCell);
        HrCellToCellQos record = (HrCellToCellQos) getSqlMapClientTemplate().queryForObject("HR_CELL_TO_CELL_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrCellToCellQos record) {
        int rows = getSqlMapClientTemplate().update("HR_CELL_TO_CELL_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_CELL_TO_CELL_QOS
     *
     * @ibatorgenerated Tue Dec 07 13:59:02 ICT 2010
     */
    public int updateByPrimaryKey(HrCellToCellQos record) {
        int rows = getSqlMapClientTemplate().update("HR_CELL_TO_CELL_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<HrCellToCellQos> filterCellToCells(String bscid, String fromCell, String startHour, String startDate, String endHour, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", fromCell);
		map.put("startDate", startDate);
		map.put("startHour", startHour);
		map.put("endDate",  endDate);
		map.put("endHour", endHour);
		
		return getSqlMapClientTemplate().queryForList("HR_CELL_TO_CELL_QOS.filterCellToCells", map);
	}

	@SuppressWarnings("unchecked")
	public List<HrCellToCellQos> filterCellsToCell(String bscid, String toCell, String startHour, String startDate, String endHour, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("cellid", toCell);
		map.put("startDate", startDate);
		map.put("startHour", startHour);
		map.put("endDate",  endDate);
		map.put("endHour", endHour);
		
		return getSqlMapClientTemplate().queryForList("HR_CELL_TO_CELL_QOS.filterCellsToCell", map);
	}
}