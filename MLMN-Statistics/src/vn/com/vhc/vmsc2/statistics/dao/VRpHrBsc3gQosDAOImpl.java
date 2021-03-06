package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBsc3gQos;

public class VRpHrBsc3gQosDAOImpl extends SqlMapClientDaoSupport implements VRpHrBsc3gQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_3G_QOS
     *
     * @ibatorgenerated Fri Aug 17 14:32:33 ICT 2012
     */
    public VRpHrBsc3gQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_3G_QOS
     *
     * @ibatorgenerated Fri Aug 17 14:32:33 ICT 2012
     */
    public void insert(VRpHrBsc3gQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_3G_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BSC_3G_QOS
     *
     * @ibatorgenerated Fri Aug 17 14:32:33 ICT 2012
     */
    public void insertSelective(VRpHrBsc3gQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BSC_3G_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<VRpHrBsc3gQos> filterAccessibility1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("bscid", bscid);
		map.put("mscid",mscid);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterAccessibility1", map);
	}   
    @SuppressWarnings("unchecked")
	public List<VRpHrBsc3gQos> filterAccessibility2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("endDate", endDate);
		map.put("bscid", bscid);
		map.put("mscid",mscid);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterAccessibility2", map);
	}
    
    @SuppressWarnings("unchecked")
  	public List<VRpHrBsc3gQos> filterDropRate1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("startHour", startHour);
  		map.put("endHour", endHour);
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("mscid",mscid);
  		map.put("region", region);
  		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterDropRate1", map);
  	}   
      @SuppressWarnings("unchecked")
  	public List<VRpHrBsc3gQos> filterDropRate2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("startHour", startHour);
  		map.put("endHour", endHour);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("mscid",mscid);
  		map.put("region", region);
  		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterDropRate2", map);
  	}
      
      @SuppressWarnings("unchecked")
  	public List<VRpHrBsc3gQos> filterBlocking1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("startHour", startHour);
  		map.put("endHour", endHour);
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("mscid",mscid);
  		map.put("region", region);
  		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterBlocking1", map);
  	}   
      @SuppressWarnings("unchecked")
  	public List<VRpHrBsc3gQos> filterBlocking2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("startHour", startHour);
  		map.put("endHour", endHour);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("mscid",mscid);
  		map.put("region", region);
  		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterBlocking2", map);
  	}
      
      @SuppressWarnings("unchecked")
  	public List<VRpHrBsc3gQos> filterCallCompletion1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("startHour", startHour);
  		map.put("endHour", endHour);
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("mscid",mscid);
  		map.put("region", region);
  		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterCallCompletion1", map);
  	}   
      @SuppressWarnings("unchecked")
  	public List<VRpHrBsc3gQos> filterCallCompletion2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region) {
  		Map<String, Object> map = new HashMap<String, Object>();
  		map.put("startHour", startHour);
  		map.put("endHour", endHour);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("mscid",mscid);
  		map.put("region", region);
  		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterCallCompletion2", map);
  	}
      
      @SuppressWarnings("unchecked")
    	public List<VRpHrBsc3gQos> filterIntergrity1(String startHour,Date startDate, String endHour, Date endDate, String bscid, String mscid, String region) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("bscid", bscid);
    		map.put("mscid",mscid);
    		map.put("region", region);
    		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterIntergrity1", map);
    	}   
        @SuppressWarnings("unchecked")
    	public List<VRpHrBsc3gQos> filterIntergrity2(String startHour, String endHour, Date endDate, String bscid, String mscid, String region) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("endDate", endDate);
    		map.put("bscid", bscid);
    		map.put("mscid",mscid);
    		map.put("region", region);
    		return getSqlMapClientTemplate().queryForList("V_RP_HR_BSC_3G_QOS.filterIntergrity2", map);
    	}
}