package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvinceIbc;

public class VRpWkProvinceIbcDAOImpl extends SqlMapClientDaoSupport implements VRpWkProvinceIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_IBC
     *
     * @ibatorgenerated Fri Nov 19 10:59:56 ICT 2010
     */
    public VRpWkProvinceIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_IBC
     *
     * @ibatorgenerated Fri Nov 19 10:59:56 ICT 2010
     */
    public void insert(VRpWkProvinceIbc record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PROVINCE_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_IBC
     *
     * @ibatorgenerated Fri Nov 19 10:59:56 ICT 2010
     */
    public void insertSelective(VRpWkProvinceIbc record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PROVINCE_IBC.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpWkProvinceIbc> filter(Integer week,Integer year, String province, String region){
    	VRpWkProvinceIbc key = new VRpWkProvinceIbc();
    	key.setProvince(province);
    	key.setWeek(week);
    	key.setYear(year);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_PROVINCE_IBC.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpWkProvinceIbc> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String province, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_PROVINCE_IBC.filter2", map);
    }
}