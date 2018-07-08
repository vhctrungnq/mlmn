package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VrpWkGGSNQos;

public class VrpWkGGSNQosDAOImpl extends SqlMapClientDaoSupport implements VrpWkGGSNQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_GGSN_QOS
     *
     * @ibatorgenerated Wed Nov 28 15:04:05 ICT 2012
     */
    public VrpWkGGSNQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_GGSN_QOS
     *
     * @ibatorgenerated Wed Nov 28 15:04:05 ICT 2012
     */
    public void insert(VrpWkGGSNQos record) {
        getSqlMapClientTemplate().insert("V_RP_WK_GGSN_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_GGSN_QOS
     *
     * @ibatorgenerated Wed Nov 28 15:04:05 ICT 2012
     */
    public void insertSelective(VrpWkGGSNQos record) {
        getSqlMapClientTemplate().insert("V_RP_WK_GGSN_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VrpWkGGSNQos> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String region, String ggsnName){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
		map.put("ggsnName", ggsnName);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_GGSN_QOS.filter", map);
    }
}