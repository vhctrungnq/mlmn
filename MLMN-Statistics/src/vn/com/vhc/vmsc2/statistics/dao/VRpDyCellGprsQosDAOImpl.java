package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellGprsQos;

public class VRpDyCellGprsQosDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellGprsQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 11 16:06:33 ICT 2010
     */
    public VRpDyCellGprsQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 11 16:06:33 ICT 2010
     */
    public void insert(VRpDyCellGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_GPRS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 11 16:06:33 ICT 2010
     */
    public void insertSelective(VRpDyCellGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_GPRS_QOS.ibatorgenerated_insertSelective", record);
    }
    

    @SuppressWarnings("unchecked")
    public List<VRpDyCellGprsQos> filter(String startDate, String endDate, String cellid,String province, String bscid, String region, int startRecord, int endRecord, String column, String order){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("province", province);
		map.put("region", region);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("startRecord", startRecord);
		map.put("endRecord", endRecord);
		map.put("column", column);
		map.put("order", order);

    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_GPRS_QOS.filter", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCellGprsQos> filter(String startDate, String endDate, String cellid,String province, String bscid, String region){
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("province", province);
		map.put("region", region);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_GPRS_QOS.filter3", map);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCellGprsQos> filter(String startDate,String endDate, String bscid,String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_GPRS_QOS.filter2", map);
    }

	public Integer countRow(String startDate, String endDate, String cellid, String province, String bscid, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("province", province);
		map.put("region", region);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		
		return (Integer) getSqlMapClientTemplate().queryForObject("V_RP_DY_CELL_GPRS_QOS.countRow", map);
	}
}