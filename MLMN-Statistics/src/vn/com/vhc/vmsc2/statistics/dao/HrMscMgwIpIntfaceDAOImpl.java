package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwIpIntface;

public class HrMscMgwIpIntfaceDAOImpl extends SqlMapClientDaoSupport implements HrMscMgwIpIntfaceDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public HrMscMgwIpIntfaceDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMgwIpIntface key = new HrMscMgwIpIntface();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_MGW_IP_INTERFACE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public void insert(HrMscMgwIpIntface record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_IP_INTERFACE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public void insertSelective(HrMscMgwIpIntface record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_IP_INTERFACE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public HrMscMgwIpIntface selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMgwIpIntface key = new HrMscMgwIpIntface();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscMgwIpIntface record = (HrMscMgwIpIntface) getSqlMapClientTemplate().queryForObject("HR_MSC_MGW_IP_INTERFACE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscMgwIpIntface record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_IP_INTERFACE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_IP_INTERFACE
     *
     * @ibatorgenerated Mon Nov 05 17:18:27 ICT 2012
     */
    public int updateByPrimaryKey(HrMscMgwIpIntface record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_IP_INTERFACE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscMgwIpIntface> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_IP_INTERFACE.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscMgwIpIntface> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_IP_INTERFACE.filter2", map);
      	}
}