package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
import vn.com.vhc.vmsc2.statistics.web.filter.SGSNFilter;

public class SgsnDAOImpl extends SqlMapClientDaoSupport implements SgsnDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public SgsnDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public int deleteByPrimaryKey(String sgsnid) {
        Sgsn key = new Sgsn();
        key.setSgsnid(sgsnid);
        int rows = getSqlMapClientTemplate().delete("H_SGSN.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void insert(Sgsn record) {
        getSqlMapClientTemplate().insert("H_SGSN.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public void insertSelective(Sgsn record) {
        getSqlMapClientTemplate().insert("H_SGSN.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public Sgsn selectByPrimaryKey(String sgsnid) {
        Sgsn key = new Sgsn();
        key.setSgsnid(sgsnid);
        Sgsn record = (Sgsn) getSqlMapClientTemplate().queryForObject("H_SGSN.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public int updateByPrimaryKeySelective(Sgsn record) {
        int rows = getSqlMapClientTemplate().update("H_SGSN.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_SGSN
     *
     * @ibatorgenerated Fri Feb 07 15:42:58 ICT 2014
     */
    public int updateByPrimaryKey(Sgsn record) {
        int rows = getSqlMapClientTemplate().update("H_SGSN.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Sgsn> selectBySgsnName(String sgsnName) {
		return getSqlMapClientTemplate().queryForList("H_SGSN.getSgsnBySgsnName", sgsnName);
	}


    @SuppressWarnings("unchecked")
  	public List<Sgsn> getAllSGSNName() {
  		return getSqlMapClientTemplate().queryForList("H_SGSN.getSgsnName");
  	}
    
    @SuppressWarnings("unchecked")
	public List<Sgsn> getAllSGSN() {
		return getSqlMapClientTemplate().queryForList("H_SGSN.getAll");
	}
	
	@SuppressWarnings("unchecked")
	public List<Sgsn> getAllRegion() {
		return getSqlMapClientTemplate().queryForList("H_SGSN.getAll");
	}

	@SuppressWarnings("unchecked")
	public List<String> getSgsnids(String term) {
		return getSqlMapClientTemplate().queryForList("H_SGSN.getSgsn", term);
	}

	@SuppressWarnings("unchecked")
	public List<Sgsn> filter(SGSNFilter filter) {
		return getSqlMapClientTemplate().queryForList("H_SGSN.filter", filter);
	}

	@SuppressWarnings("unchecked")
	public List<Sgsn> getAllSGSNID() {
		return getSqlMapClientTemplate().queryForList("H_SGSN.getSgsnId");
	}
	
	public int deleteById(Integer id) {
        Sgsn key = new Sgsn();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("H_SGSN.ibatorgenerated_deleteById", key);
        return rows;
    }
	
	public Sgsn selectById(Integer id) {
        Sgsn key = new Sgsn();
        key.setId(id);
        Sgsn record = (Sgsn) getSqlMapClientTemplate().queryForObject("H_SGSN.ibatorgenerated_selectById", key);
        return record;
    }
	
	@SuppressWarnings("unchecked")
	public List<Sgsn> checkUniqueSgsn(String sgsnid, String id) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_SGSNID", sgsnid);
		parms.put("P_ID", id);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("H_SGSN.checkUniqueSgsn", parms);
	}
	
	public int updateById(Sgsn record) {
        int rows = getSqlMapClientTemplate().update("H_SGSN.ibatorgenerated_updateById", record);
        return rows;
    }
}