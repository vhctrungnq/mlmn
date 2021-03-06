package vn.com.vhc.vmsc2.statistics.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyMsccpload;

public class DyMsccploadDAOImpl extends SqlMapClientDaoSupport implements DyMsccploadDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public DyMsccploadDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMsccpload key = new DyMsccpload();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_CP_LOAD.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public void insert(DyMsccpload record) {
        getSqlMapClientTemplate().insert("DY_MSC_CP_LOAD.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public void insertSelective(DyMsccpload record) {
        getSqlMapClientTemplate().insert("DY_MSC_CP_LOAD.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public DyMsccpload selectByPrimaryKey(Date day, String mscid) {
        DyMsccpload key = new DyMsccpload();
        key.setDay(day);
        key.setMscid(mscid);
        DyMsccpload record = (DyMsccpload) getSqlMapClientTemplate().queryForObject("DY_MSC_CP_LOAD.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMsccpload record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_CP_LOAD.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CP_LOAD
     *
     * @ibatorgenerated Mon Oct 08 11:19:42 ICT 2012
     */
    public int updateByPrimaryKey(DyMsccpload record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_CP_LOAD.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyMsccpload> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_CP_LOAD.filter", map);
    }
    @SuppressWarnings("unchecked")
    public List<DyMsccpload> filterDay(String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_CP_LOAD.filterDay", map);
    }
}