package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.OptionBscData2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscData2g;

public class VRpDyBscData2gDAOImpl extends SqlMapClientDaoSupport implements VRpDyBscData2gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:09:42 GMT+07:00 2011
     */
    public VRpDyBscData2gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:09:42 GMT+07:00 2011
     */
    public void insert(VRpDyBscData2g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_DATA_2G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:09:42 GMT+07:00 2011
     */
    public void insertSelective(VRpDyBscData2g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_DATA_2G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyBscData2g> filter(String startDate, String endDate, String bscid, String region) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("bscid", bscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
		map.put("region", region);
    return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_DATA_2G.filter", map);
	}

	@SuppressWarnings("unchecked")
	public List<OptionBscData2g> bscData2gOption(String startDate, String endDate, int startHour, int endHour, String bscid, String region,
			int startRecord, int endRecord, String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hour", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_bscid", bscid);
		map.put("p_region", region);
		map.put("p_start_record", startRecord);
		map.put("p_end_record", endRecord);
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_DATA_2G.bscData2gOption", map);
	}

	public OptionBscData2g countBscData2gOption(String startDate, String endDate, int startHour, int endHour, String bscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hour", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_bscid", bscid);
		map.put("p_region", region);
		map.put("P_DATA", null);
		return (OptionBscData2g) getSqlMapClientTemplate().queryForObject("V_RP_DY_BSC_DATA_2G.countBscData2gOption", map);
	}

}