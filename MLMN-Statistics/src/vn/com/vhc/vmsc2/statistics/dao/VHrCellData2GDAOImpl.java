package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VHrCellData2G;

public class VHrCellData2GDAOImpl extends SqlMapClientDaoSupport implements VHrCellData2GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:55:43 ICT 2012
     */
    public VHrCellData2GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:55:43 ICT 2012
     */
    public void insert(VHrCellData2G record) {
        getSqlMapClientTemplate().insert("V_HR_CELL_DATA_2G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_CELL_DATA_2G
     *
     * @ibatorgenerated Wed Dec 19 15:55:43 ICT 2012
     */
    public void insertSelective(VHrCellData2G record) {
        getSqlMapClientTemplate().insert("V_HR_CELL_DATA_2G.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
   	public List<VHrCellData2G> filter(String startHour, Date startDate, String endHour,  Date endDate, String bscid, String cellid) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("bscid", bscid);
    		map.put("cellid", cellid);

    		return getSqlMapClientTemplate().queryForList("V_HR_CELL_DATA_2G.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<VHrCellData2G> filter2(String startHour, Date startDate, String endHour, String bscid, String cellid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		
		return getSqlMapClientTemplate().queryForList("V_HR_CELL_DATA_2G.filter2", map);
   	}
    @SuppressWarnings("unchecked")
   	public List<VHrCellData2G> filterTF(String startHour, Date startDate, String endHour,  Date endDate, String bscid, String cellid) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("bscid", bscid);
    		map.put("cellid", cellid);

    		return getSqlMapClientTemplate().queryForList("V_HR_CELL_DATA_2G.filterTF", map);
   	}
    @SuppressWarnings("unchecked")
    public List<VHrCellData2G> filterTF2(String startHour, Date startDate, String endHour, String bscid, String cellid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		
		return getSqlMapClientTemplate().queryForList("V_HR_CELL_DATA_2G.filterTF2", map);
   	}
}