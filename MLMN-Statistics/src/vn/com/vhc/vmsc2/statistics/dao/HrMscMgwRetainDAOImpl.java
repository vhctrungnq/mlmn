package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwRetain;

public class HrMscMgwRetainDAOImpl extends SqlMapClientDaoSupport implements HrMscMgwRetainDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public HrMscMgwRetainDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMgwRetain key = new HrMscMgwRetain();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_MGW_RETAIN.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public void insert(HrMscMgwRetain record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_RETAIN.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public void insertSelective(HrMscMgwRetain record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_RETAIN.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public HrMscMgwRetain selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMgwRetain key = new HrMscMgwRetain();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscMgwRetain record = (HrMscMgwRetain) getSqlMapClientTemplate().queryForObject("HR_MSC_MGW_RETAIN.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscMgwRetain record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_RETAIN.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_RETAIN
     *
     * @ibatorgenerated Mon Oct 22 14:48:53 ICT 2012
     */
    public int updateByPrimaryKey(HrMscMgwRetain record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_RETAIN.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscMgwRetain> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_RETAIN.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscMgwRetain> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_RETAIN.filter2", map);
      	}
}