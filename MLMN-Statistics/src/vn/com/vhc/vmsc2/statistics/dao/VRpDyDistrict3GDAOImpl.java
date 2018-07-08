package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.OptionDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrict3G;

public class VRpDyDistrict3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyDistrict3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 11:02:21 ICT 2011
     */
    public VRpDyDistrict3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 11:02:21 ICT 2011
     */
    public void insert(VRpDyDistrict3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_DISTRICT_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 11:02:21 ICT 2011
     */
    public void insertSelective(VRpDyDistrict3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_DISTRICT_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyDistrict3G> filter(String startDate, String endDate, String province, String district, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("district", district);
		map.put("province", province);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_DISTRICT_3G.filter", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<OptionDistrict3g> District3gOption(String startDate, String endDate, int startHour, int endHour, String region, String province,
			String district, int startRecord, int endRecord,
			String column, int order) {
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
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_DISTRICT_3G.district3gOption", map);
	}

	public OptionDistrict3g countDistrict3gOption(String startDate, String endDate, int startHour, int endHour, String region, String province,
			String district) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_date", startDate);
		map.put("p_end_date", endDate);
		map.put("p_start_hour", startHour);
		map.put("p_end_hour", endHour);
		map.put("p_region", region);
		map.put("p_province", province);
		map.put("p_district", district);
		map.put("P_DATA", null);
		return (OptionDistrict3g) getSqlMapClientTemplate().queryForObject("V_RP_DY_DISTRICT_3G.countDistrict3gOption", map);
	}
}