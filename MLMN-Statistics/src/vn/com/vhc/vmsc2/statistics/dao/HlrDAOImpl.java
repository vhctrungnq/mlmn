package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.Hlr;
import vn.com.vhc.vmsc2.statistics.web.filter.HlrFilter;

public class HlrDAOImpl extends SqlMapClientDaoSupport implements HlrDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public HlrDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public int deleteByPrimaryKey(String hlrid) {
        Hlr key = new Hlr();
        key.setHlrid(hlrid);
        int rows = getSqlMapClientTemplate().delete("H_HLR.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public void insert(Hlr record) {
        getSqlMapClientTemplate().insert("H_HLR.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public void insertSelective(Hlr record) {
        getSqlMapClientTemplate().insert("H_HLR.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public Hlr selectByPrimaryKey(String hlrid) {
        Hlr key = new Hlr();
        key.setHlrid(hlrid);
        Hlr record = (Hlr) getSqlMapClientTemplate().queryForObject("H_HLR.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public int updateByPrimaryKeySelective(Hlr record) {
        int rows = getSqlMapClientTemplate().update("H_HLR.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table H_HLR
     *
     * @ibatorgenerated Wed Oct 13 10:36:37 ICT 2010
     */
    public int updateByPrimaryKey(Hlr record) {
        int rows = getSqlMapClientTemplate().update("H_HLR.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<Hlr> getAll() {
    	return getSqlMapClientTemplate().queryForList("H_HLR.getAll");
    }
    
    @SuppressWarnings("unchecked")
	public List<Hlr> filter(HlrFilter filter) {
		return getSqlMapClientTemplate().queryForList("H_HLR.filter", filter);
	}

    @SuppressWarnings("unchecked")
	public List<String> getHlrids(String term) {
		return getSqlMapClientTemplate().queryForList("H_HLR.getHlr", term);
	}
}