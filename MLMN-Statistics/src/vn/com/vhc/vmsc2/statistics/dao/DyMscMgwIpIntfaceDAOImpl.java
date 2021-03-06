package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwIpIntface;

public class DyMscMgwIpIntfaceDAOImpl extends SqlMapClientDaoSupport implements DyMscMgwIpIntfaceDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public DyMscMgwIpIntfaceDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscMgwIpIntface key = new DyMscMgwIpIntface();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_MGW_IP_INTERFACE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public void insert(DyMscMgwIpIntface record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_IP_INTERFACE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public void insertSelective(DyMscMgwIpIntface record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_IP_INTERFACE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public DyMscMgwIpIntface selectByPrimaryKey(Date day, String mscid) {
        DyMscMgwIpIntface key = new DyMscMgwIpIntface();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscMgwIpIntface record = (DyMscMgwIpIntface) getSqlMapClientTemplate().queryForObject("DY_MSC_MGW_IP_INTERFACE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscMgwIpIntface record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_IP_INTERFACE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:50 ICT 2012
     */
    public int updateByPrimaryKey(DyMscMgwIpIntface record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_IP_INTERFACE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<DyMscMgwIpIntface> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_MGW_IP_INTERFACE.filter", map);
    }
}