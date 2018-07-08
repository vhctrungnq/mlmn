package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwRetain;

public class DyMscMgwRetainDAOImpl extends SqlMapClientDaoSupport implements DyMscMgwRetainDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public DyMscMgwRetainDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscMgwRetain key = new DyMscMgwRetain();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_MGW_RETAIN.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public void insert(DyMscMgwRetain record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_RETAIN.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public void insertSelective(DyMscMgwRetain record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_RETAIN.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public DyMscMgwRetain selectByPrimaryKey(Date day, String mscid) {
        DyMscMgwRetain key = new DyMscMgwRetain();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscMgwRetain record = (DyMscMgwRetain) getSqlMapClientTemplate().queryForObject("DY_MSC_MGW_RETAIN.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscMgwRetain record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_RETAIN.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:49:12 ICT 2012
     */
    public int updateByPrimaryKey(DyMscMgwRetain record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_RETAIN.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyMscMgwRetain> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_MGW_RETAIN.filter", map);
    }
}