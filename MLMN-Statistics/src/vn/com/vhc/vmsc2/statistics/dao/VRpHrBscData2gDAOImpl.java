package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscData2g;

public class VRpHrBscData2gDAOImpl extends SqlMapClientDaoSupport implements VRpHrBscData2gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:30 GMT+07:00 2011
     */
    public VRpHrBscData2gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:30 GMT+07:00 2011
     */
    public void insert(VRpHrBscData2g record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_DATA_2G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Mon Sep 12 09:10:30 GMT+07:00 2011
     */
    public void insertSelective(VRpHrBscData2g record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_DATA_2G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpHrBscData2g> filter(String startHour, String endHour, Date day, String bscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_DATA_2G.filter", map);
	}
}