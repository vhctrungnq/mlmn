package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscIbc;

public class VRpHrBscIbcDAOImpl extends SqlMapClientDaoSupport implements VRpHrBscIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_IBC
     *
     * @ibatorgenerated Thu Dec 16 17:05:15 ICT 2010
     */
    public VRpHrBscIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_IBC
     *
     * @ibatorgenerated Thu Dec 16 17:05:15 ICT 2010
     */
    public void insert(VRpHrBscIbc record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_IBC
     *
     * @ibatorgenerated Thu Dec 16 17:05:15 ICT 2010
     */
    public void insertSelective(VRpHrBscIbc record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_IBC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpHrBscIbc> filter(Date d, Integer hour, String bscid, String region) {
		VRpHrBscIbc key = new VRpHrBscIbc();
		key.setBscid(bscid);
		key.setDay(d);
		key.setHour(hour);
		key.setRegion(region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_IBC.filter", key);
	}

	@SuppressWarnings("unchecked")
	public List<VRpHrBscIbc> filter(String startHour, String endHour, Date day, String bscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_IBC.filter2", map);
	}
}