package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForFe;

public class VRpDyHlrForFeDAOImpl extends SqlMapClientDaoSupport implements VRpDyHlrForFeDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 15:20:13 ICT 2012
     */
    public VRpDyHlrForFeDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 15:20:13 ICT 2012
     */
    public void insert(VRpDyHlrForFe record) {
        getSqlMapClientTemplate().insert("V_RP_DY_HLR_FOR_FE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_FE
     *
     * @ibatorgenerated Mon Nov 26 15:20:13 ICT 2012
     */
    public void insertSelective(VRpDyHlrForFe record) {
        getSqlMapClientTemplate().insert("V_RP_DY_HLR_FOR_FE.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<VRpDyHlrForFe> filter(String startDate, String endDate, String nodeid){
    	Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("nodeid", nodeid);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_HLR_FOR_FE.filter", map);
    }
    @SuppressWarnings("unchecked")
  	public List<VRpDyHlrForFe> filterDay(String startDate, String endDate){
      	Map<String, String> map = new HashMap<String, String>();
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		return getSqlMapClientTemplate().queryForList("V_RP_DY_HLR_FOR_FE.filterDay", map);
      }
    
}