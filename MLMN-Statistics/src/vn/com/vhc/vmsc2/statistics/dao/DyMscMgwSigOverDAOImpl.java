package vn.com.vhc.vmsc2.statistics.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwSigOver;

public class DyMscMgwSigOverDAOImpl extends SqlMapClientDaoSupport implements DyMscMgwSigOverDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public DyMscMgwSigOverDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscMgwSigOver key = new DyMscMgwSigOver();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_MGW_SIG_OVER.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public void insert(DyMscMgwSigOver record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_SIG_OVER.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public void insertSelective(DyMscMgwSigOver record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_SIG_OVER.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public DyMscMgwSigOver selectByPrimaryKey(Date day, String mscid) {
        DyMscMgwSigOver key = new DyMscMgwSigOver();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscMgwSigOver record = (DyMscMgwSigOver) getSqlMapClientTemplate().queryForObject("DY_MSC_MGW_SIG_OVER.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscMgwSigOver record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_SIG_OVER.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_SIG_OVER
     *
     * @ibatorgenerated Tue Oct 23 09:12:52 ICT 2012
     */
    public int updateByPrimaryKey(DyMscMgwSigOver record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_SIG_OVER.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyMscMgwSigOver> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_MGW_SIG_OVER.filter", map);
    }
}