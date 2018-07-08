package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.OptionDistrictData2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictData2g;

public class VRpDyDistrictData2gDAOImpl extends SqlMapClientDaoSupport implements VRpDyDistrictData2gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:04 GMT+07:00 2011
     */
    public VRpDyDistrictData2gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:04 GMT+07:00 2011
     */
    public void insert(VRpDyDistrictData2g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_DISTRICT_DATA_2G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:04 GMT+07:00 2011
     */
    public void insertSelective(VRpDyDistrictData2g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_DISTRICT_DATA_2G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyDistrictData2g> filter(String startDate, String endDate, String province, String district, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("district", district);
		map.put("province", province);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_DISTRICT_DATA_2G.filter", map);
	}

	@SuppressWarnings("unchecked")
	public List<OptionDistrictData2g> districtData2gOption(String startDate, String endDate, int startHour, int endHour, String region, String province,
			String district, int startRecord, int endRecord, String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hour", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_region", region);
		map.put("p_province", province);
		map.put("p_district", district);
		map.put("p_start_record", startRecord);
		map.put("p_end_record", endRecord);
		map.put("p_column", column);
		map.put("p_order", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_DISTRICT_DATA_2G.districtData2gOption", map);
	}

	public OptionDistrictData2g countDistrictData2gOption(String startDate, String endDate, int startHour, int endHour, String region, String province,
			String district) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hout", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_region", region);
		map.put("p_province", province);
		map.put("p_district", district);
		map.put("P_DATA", null);
		return (OptionDistrictData2g) getSqlMapClientTemplate().queryForObject("V_RP_DY_DISTRICT_DATA_2G.countDistrictData2gOption", map);

	}
}