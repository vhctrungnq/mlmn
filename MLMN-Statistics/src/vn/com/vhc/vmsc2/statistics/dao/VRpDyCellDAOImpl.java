package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.OptionCell;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell;

public class VRpDyCellDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL
     *
     * @ibatorgenerated Thu Jul 26 16:30:13 ICT 2012
     */
    public VRpDyCellDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL
     *
     * @ibatorgenerated Thu Jul 26 16:30:13 ICT 2012
     */
    public void insert(VRpDyCell record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL
     *
     * @ibatorgenerated Thu Jul 26 16:30:13 ICT 2012
     */
    public void insertSelective(VRpDyCell record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filter(Date day,String cellid, String province, String bscid){
    	VRpDyCell key = new VRpDyCell();
    	key.setCellid(cellid);
    	key.setProvince(province);
    	key.setBscid(bscid);
    	key.setDay(day);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filter(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filter2", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filtertDrpr(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filtertDrpr", map);
    }
    
   @SuppressWarnings("unchecked")
    public List<VRpDyCell> filtersDrpr(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filtersDrpr", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filtertBlkr(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filtertBlkr", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filtersBlkr(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filtersBlkr", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filterdyloss(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filterdyloss", map);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filterCenter2(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filterCenter2", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCell> filterCenter(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.filterCenter", map);
    }

	@SuppressWarnings("unchecked")
	public List<OptionCell> cellOption(String startDate, String endDate, int startHour, int endHour, String bscid, String cellid, String province,
			String region, int startRecord, int endRecord, String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hour", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_bscid", bscid);
		map.put("p_cellid", cellid);
		map.put("p_province", province);
		map.put("p_region", region);
		
		map.put("p_start_record", startRecord);
		map.put("p_end_record", endRecord);
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL.cellOption", map);
	}

	public OptionCell countCellOption(String startDate, String endDate, int startHour, int endHour, String bscid, String cellid, String province, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hour", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_bscid", bscid);
		map.put("p_cellid", cellid);
		map.put("p_province", province);
		map.put("p_region", region);
		map.put("P_DATA", null);
		return (OptionCell) getSqlMapClientTemplate().queryForObject("V_RP_DY_CELL.countCellOption", map);
	}
}