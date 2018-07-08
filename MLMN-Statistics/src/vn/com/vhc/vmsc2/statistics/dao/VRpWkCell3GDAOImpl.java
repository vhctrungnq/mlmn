package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCell3G;

public class VRpWkCell3GDAOImpl extends SqlMapClientDaoSupport implements VRpWkCell3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public VRpWkCell3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public void insert(VRpWkCell3G record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public void insertSelective(VRpWkCell3G record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkCell3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province,
			String region, int startRecord, int endRecord, String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("province", province);
		map.put("region", region);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("column", column);
		map.put("order", order);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G.filter", map);
	}

	public Integer countRow(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String cellid, String bscid, String province, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("province", province);
		map.put("region", region);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		return (Integer) getSqlMapClientTemplate().queryForObject("V_RP_WK_CELL_3G.countRow", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpWkCell3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G.filter2", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<VRpWkCell3G> wkHighHSDPACells(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G.wkHighHSDPACells", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<VRpWkCell3G> wkNoHSDPACells(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G.wkNoHSDPACells", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<VRpWkCell3G> wkLowCssrRnc(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String bscid, String cellid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G.wkLowCssrRnc", map);
	}
}