package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HIpbbNodeB;

public class HIpbbNodeBDAOImpl extends SqlMapClientDaoSupport implements HIpbbNodeBDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_NODEB
     *
     * @ibatorgenerated Mon Jun 06 10:23:05 ICT 2016
     */
    public HIpbbNodeBDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_NODEB
     *
     * @ibatorgenerated Mon Jun 06 10:23:05 ICT 2016
     */
    public void insert(HIpbbNodeB record) {
        getSqlMapClientTemplate().insert("H_IPBB_NODEB.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_IPBB_NODEB
     *
     * @ibatorgenerated Mon Jun 06 10:23:05 ICT 2016
     */
    public void insertSelective(HIpbbNodeB record) {
        getSqlMapClientTemplate().insert("H_IPBB_NODEB.ibatorgenerated_insertSelective", record);
    }
     
    public List<HIpbbNodeB> getDataByFilter(String nodeid, String tentram, String type){
		Map<String, Object> parms = new HashMap<String, Object>();
	
		parms.put("nodeid", nodeid);
		parms.put("tentram", tentram);
		parms.put("type", type);
		parms.put("P_DATA",null);
		
		@SuppressWarnings("unchecked")
		List<HIpbbNodeB> record = getSqlMapClientTemplate().queryForList("H_IPBB_NODEB.getDataByFilter", parms);
	    return record; 
  	} 
    
    public HIpbbNodeB selectByPrimaryKey(Integer id) {
    	HIpbbNodeB key = new HIpbbNodeB();
		key.setId(id);
		HIpbbNodeB record = (HIpbbNodeB) getSqlMapClientTemplate().queryForObject("H_IPBB_NODEB.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}
    
    public int deleteByPrimaryKey(Integer id) {
    	HIpbbNodeB key = new HIpbbNodeB();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete("H_IPBB_NODEB.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}
    
    public int updateByPrimaryKey(HIpbbNodeB record) {
		int rows = getSqlMapClientTemplate().update("H_IPBB_NODEB.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}
    
    public HIpbbNodeB selectByNodeBId(Integer nodeBId) {
    	HIpbbNodeB key = new HIpbbNodeB();
		key.setNodebId(nodeBId);;
		HIpbbNodeB record = (HIpbbNodeB) getSqlMapClientTemplate().queryForObject("H_IPBB_NODEB.ibatorgenerated_selectByNodeBId", key);
		return record;
	}
}