package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscCallSetupBc;

public class HrMscCallSetupBcDAOImpl extends SqlMapClientDaoSupport implements HrMscCallSetupBcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public HrMscCallSetupBcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscCallSetupBc key = new HrMscCallSetupBc();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_CALL_SET_UP_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public void insert(HrMscCallSetupBc record) {
        getSqlMapClientTemplate().insert("HR_MSC_CALL_SET_UP_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public void insertSelective(HrMscCallSetupBc record) {
        getSqlMapClientTemplate().insert("HR_MSC_CALL_SET_UP_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public HrMscCallSetupBc selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscCallSetupBc key = new HrMscCallSetupBc();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscCallSetupBc record = (HrMscCallSetupBc) getSqlMapClientTemplate().queryForObject("HR_MSC_CALL_SET_UP_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscCallSetupBc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_CALL_SET_UP_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_CALL_SET_UP_BC
     *
     * @ibatorgenerated Mon Aug 27 09:21:00 ICT 2012
     */
    public int updateByPrimaryKey(HrMscCallSetupBc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_CALL_SET_UP_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscCallSetupBc> filter(String startHour, Date startDate, String endHour,  Date endDate, String mscid) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("mscid", mscid);

    		return getSqlMapClientTemplate().queryForList("HR_MSC_CALL_SET_UP_BC.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<HrMscCallSetupBc> filter2(String startHour, Date startDate, String endHour, String mscid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("mscid", mscid);
		return getSqlMapClientTemplate().queryForList("HR_MSC_CALL_SET_UP_BC.filter2", map);
   	}
}