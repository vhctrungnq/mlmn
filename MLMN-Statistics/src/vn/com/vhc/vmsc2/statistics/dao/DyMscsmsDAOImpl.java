package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.DyMscsms;

public class DyMscsmsDAOImpl extends SqlMapClientDaoSupport implements DyMscsmsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public DyMscsmsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscsms key = new DyMscsms();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_SMS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public void insert(DyMscsms record) {
        getSqlMapClientTemplate().insert("DY_MSC_SMS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public void insertSelective(DyMscsms record) {
        getSqlMapClientTemplate().insert("DY_MSC_SMS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public DyMscsms selectByPrimaryKey(Date day, String mscid) {
        DyMscsms key = new DyMscsms();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscsms record = (DyMscsms) getSqlMapClientTemplate().queryForObject("DY_MSC_SMS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscsms record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_SMS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_SMS
     *
     * @ibatorgenerated Fri Oct 12 16:08:35 ICT 2012
     */
    public int updateByPrimaryKey(DyMscsms record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_SMS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyMscsms> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_SMS.filter", map);
    }
}