package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.web.filter.Bsc3gFilter;

public class Bsc3GDAOImpl extends SqlMapClientDaoSupport implements Bsc3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public Bsc3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public int deleteByPrimaryKey(String bscid) {
        Bsc3G key = new Bsc3G();
        key.setBscid(bscid);
        int rows = getSqlMapClientTemplate().delete("H_BSC_3G.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void insert(Bsc3G record) {
        getSqlMapClientTemplate().insert("H_BSC_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public void insertSelective(Bsc3G record) {
        getSqlMapClientTemplate().insert("H_BSC_3G.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public Bsc3G selectByPrimaryKey(String bscid) {
        Bsc3G key = new Bsc3G();
        key.setBscid(bscid);
        Bsc3G record = (Bsc3G) getSqlMapClientTemplate().queryForObject("H_BSC_3G.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public int updateByPrimaryKeySelective(Bsc3G record) {
        int rows = getSqlMapClientTemplate().update("H_BSC_3G.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_BSC_3G
     *
     * @ibatorgenerated Mon Apr 25 11:33:17 ICT 2011
     */
    public int updateByPrimaryKey(Bsc3G record) {
        int rows = getSqlMapClientTemplate().update("H_BSC_3G.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<Bsc3G> getAllBsc() {
		return getSqlMapClientTemplate().queryForList("H_BSC_3G.getAll");
	}

	@SuppressWarnings("unchecked")
	public List<String> getBscids3G(String term) {
		return getSqlMapClientTemplate().queryForList("H_BSC_3G.getBscid", term);
	}

	@SuppressWarnings("unchecked")
	public List<Bsc3G> getAll() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("H_BSC_3G.getAll");
	}
	
	  
    @SuppressWarnings("unchecked")
	public List<Bsc3G> filter(Bsc3gFilter filter) {
    	Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("p_vendor", filter.getVendor());
		map.put("p_team", filter.getTeam());
		map.put("p_dept", filter.getDept());
		map.put("p_subteam", filter.getSubTeam());
		map.put("p_bsc", filter.getBscid());
		map.put("p_msc", filter.getMscid());
		map.put("p_locationname", filter.getLocationName());
		
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_BSC_3G.filter", map); 
	}

    @SuppressWarnings("unchecked")
	public List<Bsc3G> getBsc3GByVendor(String vendor){
    	Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("P_VENDOR", vendor);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("H_BSC_3G.selectBsc3g", map);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBscName() {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("H_BSC_3G.getAllBscName");
	}
}