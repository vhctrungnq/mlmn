package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrDistrict3G;

public class VRpHrDistrict3GDAOImpl extends SqlMapClientDaoSupport implements VRpHrDistrict3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public VRpHrDistrict3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public void insert(VRpHrDistrict3G record) {
        getSqlMapClientTemplate().insert("V_RP_HR_DISTRICT_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_DISTRICT_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public void insertSelective(VRpHrDistrict3G record) {
        getSqlMapClientTemplate().insert("V_RP_HR_DISTRICT_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpHrDistrict3G> filter(String startHour, String endHour, String day, String province, String district, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("district", district);
		map.put("province", province);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_HR_DISTRICT_3G.filter", map);
	}
}