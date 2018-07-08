package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceIbc;

public class VRpMnProvinceIbcDAOImpl extends SqlMapClientDaoSupport implements VRpMnProvinceIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_IBC
     *
     * @ibatorgenerated Fri Nov 19 10:59:49 ICT 2010
     */
    public VRpMnProvinceIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_IBC
     *
     * @ibatorgenerated Fri Nov 19 10:59:49 ICT 2010
     */
    public void insert(VRpMnProvinceIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_PROVINCE_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_IBC
     *
     * @ibatorgenerated Fri Nov 19 10:59:49 ICT 2010
     */
    public void insertSelective(VRpMnProvinceIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_PROVINCE_IBC.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpMnProvinceIbc> filter(Integer month,Integer year, String province, String region){
    	VRpMnProvinceIbc key = new VRpMnProvinceIbc();
    	key.setProvince(province);
    	key.setMonth(month);
    	key.setYear(year);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_PROVINCE_IBC.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpMnProvinceIbc> filter(Integer startMonth,Integer startYear, Integer endMonth,Integer endYear,String province, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_PROVINCE_IBC.filter2", map);
    }
}