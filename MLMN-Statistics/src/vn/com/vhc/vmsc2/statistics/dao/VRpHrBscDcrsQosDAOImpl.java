package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscDcrsQos;

public class VRpHrBscDcrsQosDAOImpl extends SqlMapClientDaoSupport implements VRpHrBscDcrsQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:31 ICT 2011
     */
    public VRpHrBscDcrsQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:31 ICT 2011
     */
    public void insert(VRpHrBscDcrsQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_DCRS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_DCRS_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:08:31 ICT 2011
     */
    public void insertSelective(VRpHrBscDcrsQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_DCRS_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<VRpHrBscDcrsQos> filter(String startHour, String endHour, Date day, String bscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bscid", bscid);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_DCRS_QOS.filter2", map);
	}
}