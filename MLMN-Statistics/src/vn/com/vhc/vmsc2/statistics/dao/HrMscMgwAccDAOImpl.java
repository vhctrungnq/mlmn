package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwAcc;

public class HrMscMgwAccDAOImpl extends SqlMapClientDaoSupport implements HrMscMgwAccDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public HrMscMgwAccDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMgwAcc key = new HrMscMgwAcc();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_MGW_ACC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public void insert(HrMscMgwAcc record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_ACC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public void insertSelective(HrMscMgwAcc record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_ACC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public HrMscMgwAcc selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMgwAcc key = new HrMscMgwAcc();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscMgwAcc record = (HrMscMgwAcc) getSqlMapClientTemplate().queryForObject("HR_MSC_MGW_ACC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscMgwAcc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_ACC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_ACC
     *
     * @ibatorgenerated Mon Oct 22 10:39:03 ICT 2012
     */
    public int updateByPrimaryKey(HrMscMgwAcc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_ACC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscMgwAcc> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_ACC.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscMgwAcc> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_ACC.filter2", map);
      	}
}