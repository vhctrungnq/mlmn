package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.TaiThietBi;

public class TaiThietBiDAOImpl extends SqlMapClientDaoSupport implements TaiThietBiDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_TAI_THIET_BI
     *
     * @ibatorgenerated Wed Dec 09 14:05:50 ICT 2015
     */
    public TaiThietBiDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_TAI_THIET_BI
     *
     * @ibatorgenerated Wed Dec 09 14:05:50 ICT 2015
     */
    public void insert(TaiThietBi record) {
        getSqlMapClientTemplate().insert("HR_TAI_THIET_BI.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_TAI_THIET_BI
     *
     * @ibatorgenerated Wed Dec 09 14:05:50 ICT 2015
     */
    public void insertSelective(TaiThietBi record) {
        getSqlMapClientTemplate().insert("HR_TAI_THIET_BI.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterHr(String sDate, String eDate, String sHour, String eHour, 
			String ne, String slot, String region, String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sDate",sDate);
		map.put("eDate", eDate);
		map.put("sHour", sHour);
		map.put("eHour", eHour);
		map.put("neid", ne);
		map.put("slot", slot); 
		map.put("region", region);
		map.put("nguongtren", nguongtren);
		map.put("nguongduoi", nguongduoi); 
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterHr", map);
	}
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterDy(String sDate, String eDate,  
			String ne, String slot, String region, String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sDate",sDate);
		map.put("eDate", eDate); 
		map.put("neid", ne);
		map.put("slot", slot); 
		map.put("region", region);
		map.put("nguongtren", nguongtren);
		map.put("nguongduoi", nguongduoi);
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterDy", map);
	}
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterWk(String sYear, String eYear, String sWeek, String eWeek, 
			String ne, String slot, String region, String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sYear",sYear);
		map.put("eYear", eYear);
		map.put("sWeek", sWeek);
		map.put("eWeek", eWeek);
		map.put("neid", ne);
		map.put("slot", slot); 
		map.put("region", region);
		map.put("nguongtren", nguongtren);
		map.put("nguongduoi", nguongduoi);
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterWk", map);
	}
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterMn(String sYear, String eYear, String sMonth, String eMonth, 
			String ne, String slot, String region, String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sYear",sYear);
		map.put("eYear", eYear);
		map.put("sMonth", sMonth);
		map.put("eMonth", eMonth);
		map.put("neid", ne);
		map.put("slot", slot); 
		map.put("region", region); 
		map.put("nguongtren", nguongtren); 
		map.put("nguongduoi", nguongduoi); 
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterMn", map);
	}
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterQr(String sYear, String eYear, String sQuarter, String eQuarter, 
			String ne, String slot, String region,String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sYear",sYear);
		map.put("eYear", eYear);
		map.put("sQuarter", sQuarter);
		map.put("eQuarter", eQuarter);
		map.put("neid", ne);
		map.put("slot", slot); 
		map.put("region", region); 
		map.put("nguongtren", nguongtren); 
		map.put("nguongduoi", nguongduoi); 
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterQr", map);
	}
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterYr(String sYear, String eYear, String ne, String slot,
			String region, String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sYear",sYear);
		map.put("eYear", eYear); 
		map.put("neid", ne);
		map.put("slot", slot);
		map.put("region", region); 
		map.put("nguongtren", nguongtren); 
		map.put("nguongduoi", nguongduoi); 
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterYr", map);
	}
    
    @SuppressWarnings("unchecked")
	public List<TaiThietBi> filterOption(String sDate, String eDate,  
			String ne, String slot, String region, String nguongtren, String nguongduoi, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sDate",sDate);
		map.put("eDate", eDate); 
		map.put("neid", ne);
		map.put("slot", slot); 
		map.put("region", region);
		map.put("nguongtren", nguongtren);
		map.put("nguongduoi", nguongduoi);
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_TAI_THIET_BI.filterOption", map);
	}
}