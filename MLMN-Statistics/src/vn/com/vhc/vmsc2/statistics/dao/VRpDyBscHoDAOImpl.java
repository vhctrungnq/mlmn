package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHo;

public class VRpDyBscHoDAOImpl extends SqlMapClientDaoSupport implements VRpDyBscHoDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:40 ICT 2010
     */
    public VRpDyBscHoDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:40 ICT 2010
     */
    public void insert(VRpDyBscHo record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_HO.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_HO
     *
     * @ibatorgenerated Thu Nov 18 15:00:40 ICT 2010
     */
    public void insertSelective(VRpDyBscHo record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_HO.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyBscHo> filterDetails(String startDate, String endDate, String bscid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_HO.filterDetails", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpDyBscHo> filter(String bscid, Date d, String region) {
		VRpDyBscHo key = new VRpDyBscHo();
		key.setBscid(bscid);
		key.setDay(d);
		key.setRegion(region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_HO.filter", key);
	}
	
	@SuppressWarnings("unchecked")
	public List<VRpDyBscHo> filterLS(String startDate, String endDate, String bscid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_HO.filterLS", map);
	}
}