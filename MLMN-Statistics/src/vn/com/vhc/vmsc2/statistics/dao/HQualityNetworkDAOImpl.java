package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HQualityNetwork;

public class HQualityNetworkDAOImpl extends SqlMapClientDaoSupport implements HQualityNetworkDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public HQualityNetworkDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public int deleteByPrimaryKey(Integer id) {
        HQualityNetwork key = new HQualityNetwork();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("H_QUALITY_NETWORK.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public void insert(HQualityNetwork record) {
        getSqlMapClientTemplate().insert("H_QUALITY_NETWORK.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public void insertSelective(HQualityNetwork record) {
        getSqlMapClientTemplate().insert("H_QUALITY_NETWORK.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public HQualityNetwork selectByPrimaryKey(Integer id) {
        HQualityNetwork key = new HQualityNetwork();
        key.setId(id);
        HQualityNetwork record = (HQualityNetwork) getSqlMapClientTemplate().queryForObject("H_QUALITY_NETWORK.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public int updateByPrimaryKeySelective(HQualityNetwork record) {
        int rows = getSqlMapClientTemplate().update("H_QUALITY_NETWORK.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_QUALITY_NETWORK
     *
     * @ibatorgenerated Tue Feb 11 09:34:21 ICT 2014
     */
    public int updateByPrimaryKey(HQualityNetwork record) {
        int rows = getSqlMapClientTemplate().update("H_QUALITY_NETWORK.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<HQualityNetwork> getHQualityNetwork(String groupName, String qualityName, String qualityCode,
			String column, String order){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_GROUP_NAME", groupName);
		parms.put("P_QUALITY_NAME", qualityName);
		parms.put("P_QUALITY_CODE", qualityCode);
		parms.put("P_COLUMN", column);
		parms.put("P_ORDER", order);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("H_QUALITY_NETWORK.getHQualityNetwork", parms);
	}
    
    
    @SuppressWarnings("unchecked")
	public List<HQualityNetwork> checkUniqueCodeQuaNet(String qualityCode, String id){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_QUALITY_CODE", qualityCode);
		parms.put("P_ID", id);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("H_QUALITY_NETWORK.checkUniqueCodeQuaNet", parms);
	}
    
    public int updateByQualityCode(HQualityNetwork record) {
        int rows = getSqlMapClientTemplate().update("H_QUALITY_NETWORK.ibatorgenerated_updateByQualityCode", record);
        return rows;
    }
}