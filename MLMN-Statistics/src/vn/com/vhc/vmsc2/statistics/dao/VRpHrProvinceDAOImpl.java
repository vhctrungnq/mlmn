package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvince;

public class VRpHrProvinceDAOImpl extends SqlMapClientDaoSupport implements VRpHrProvinceDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE
     *
     * @ibatorgenerated Thu Nov 18 11:42:52 ICT 2010
     */
    public VRpHrProvinceDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE
     *
     * @ibatorgenerated Thu Nov 18 11:42:52 ICT 2010
     */
    public void insert(VRpHrProvince record) {
        getSqlMapClientTemplate().insert("V_RP_HR_PROVINCE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE
     *
     * @ibatorgenerated Thu Nov 18 11:42:52 ICT 2010
     */
    public void insertSelective(VRpHrProvince record) {
        getSqlMapClientTemplate().insert("V_RP_HR_PROVINCE.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpHrProvince> filter(Date day,Integer hour,  String province, String region){
    	VRpHrProvince key = new VRpHrProvince();
    	key.setProvince(province);
    	key.setDay(day);
    	key.setHour(hour);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_HR_PROVINCE.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpHrProvince> filter(String startHour,String endHour,String day,String province, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_HR_PROVINCE.filter2", map);
    }
}