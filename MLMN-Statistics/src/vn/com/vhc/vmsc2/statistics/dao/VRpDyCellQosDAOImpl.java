package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpDYCellQos;

public class VRpDyCellQosDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_QOS
     *
     * @ibatorgenerated Mon Jul 23 15:02:47 ICT 2012
     */
    public VRpDyCellQosDAOImpl() {
        super() ;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_QOS
     *
     * @ibatorgenerated Mon Jul 23 15:02:47 ICT 2012
     */
    public void insert(VRpDYCellQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_QOS
     *
     * @ibatorgenerated Mon Jul 23 15:02:47 ICT 2012
     */
    public void insertSelective(VRpDYCellQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
  	public List<VRpDYCellQos> filterhigh(String startDate, String endDate, String bscid, String cellid) {
  		Map<String, String> map = new HashMap<String, String>();
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("cellid", cellid);
  		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_QOS.filterhigh", map);
  	}
    
    @SuppressWarnings("unchecked")
  	public List<VRpDYCellQos> filterlow(String startDate, String endDate, String bscid, String cellid) {
  		Map<String, String> map = new HashMap<String, String>();
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("cellid", cellid);
  		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_QOS.filterlow", map);
  	}
    
    @SuppressWarnings("unchecked")
  	public List<VRpDYCellQos> filterno(String startDate, String endDate, String bscid, String cellid) {
  		Map<String, String> map = new HashMap<String, String>();
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		map.put("bscid", bscid);
  		map.put("cellid", cellid);
  		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_QOS.filterno", map);
  	}
}