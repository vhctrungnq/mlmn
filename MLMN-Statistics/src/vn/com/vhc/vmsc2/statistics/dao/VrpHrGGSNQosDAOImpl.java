package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VrpHrGGSNQos;

public class VrpHrGGSNQosDAOImpl extends SqlMapClientDaoSupport implements VrpHrGGSNQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_GGSN_QOS
     *
     * @ibatorgenerated Wed Nov 28 15:03:31 ICT 2012
     */
    public VrpHrGGSNQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_GGSN_QOS
     *
     * @ibatorgenerated Wed Nov 28 15:03:31 ICT 2012
     */
    public void insert(VrpHrGGSNQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_GGSN_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_GGSN_QOS
     *
     * @ibatorgenerated Wed Nov 28 15:03:31 ICT 2012
     */
    public void insertSelective(VrpHrGGSNQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_GGSN_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
   	public List<VrpHrGGSNQos> filter(String startHour, Date startDate, String endHour,  Date endDate, String region, String ggsnName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
		map.put("ggsnName", ggsnName);

    	return getSqlMapClientTemplate().queryForList("V_RP_HR_GGSN_QOS.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<VrpHrGGSNQos> filter2(String startHour, Date startDate, String endHour, String region, String ggsnName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("region", region);
		map.put("ggsnName", ggsnName);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_GGSN_QOS.filter2", map);
   	}
}