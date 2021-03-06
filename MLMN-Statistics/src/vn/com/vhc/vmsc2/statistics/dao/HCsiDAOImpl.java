package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HCsi;

public class HCsiDAOImpl extends SqlMapClientDaoSupport implements HCsiDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public HCsiDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public int deleteByPrimaryKey(String nodeid, String nodeSub, String province, String region) {
        HCsi key = new HCsi();
        key.setNodeid(nodeid);
        key.setNodeSub(nodeSub);
        key.setProvince(province);
        key.setRegion(region);
        int rows = getSqlMapClientTemplate().delete("H_CSI.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public void insert(HCsi record) {
        getSqlMapClientTemplate().insert("H_CSI.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public void insertSelective(HCsi record) {
        getSqlMapClientTemplate().insert("H_CSI.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public HCsi selectByPrimaryKey(String nodeid, String nodeSub, String province, String region) {
        HCsi key = new HCsi();
        key.setNodeid(nodeid);
        key.setNodeSub(nodeSub);
        key.setProvince(province);
        key.setRegion(region);
        HCsi record = (HCsi) getSqlMapClientTemplate().queryForObject("H_CSI.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public int updateByPrimaryKeySelective(HCsi record) {
        int rows = getSqlMapClientTemplate().update("H_CSI.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_CSI
     *
     * @ibatorgenerated Thu Jan 02 10:34:02 ICT 2014
     */
    public int updateByPrimaryKey(HCsi record) {
        int rows = getSqlMapClientTemplate().update("H_CSI.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    public List<HCsi> filter(String nodeid, String nodeSub, String province, String region) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_NODEID", 		nodeid);
    	parms.put("P_NODE_SUB", 		nodeSub);
    	parms.put("P_PROVINCE", 		province);
    	parms.put("P_REGION", 		region);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<HCsi> record = getSqlMapClientTemplate().queryForList("H_CSI.filter", parms);
        return record;
	}
}