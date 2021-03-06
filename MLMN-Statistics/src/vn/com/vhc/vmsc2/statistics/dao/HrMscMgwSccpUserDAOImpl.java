package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwSccpUser;

public class HrMscMgwSccpUserDAOImpl extends SqlMapClientDaoSupport implements HrMscMgwSccpUserDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public HrMscMgwSccpUserDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid, String userlabel) {
        HrMscMgwSccpUser key = new HrMscMgwSccpUser();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setUserlabel(userlabel);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_MGW_SCCP_USER.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public void insert(HrMscMgwSccpUser record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_SCCP_USER.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public void insertSelective(HrMscMgwSccpUser record) {
        getSqlMapClientTemplate().insert("HR_MSC_MGW_SCCP_USER.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public HrMscMgwSccpUser selectByPrimaryKey(Date day, Integer hour, String mscid, String userlabel) {
        HrMscMgwSccpUser key = new HrMscMgwSccpUser();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setUserlabel(userlabel);
        HrMscMgwSccpUser record = (HrMscMgwSccpUser) getSqlMapClientTemplate().queryForObject("HR_MSC_MGW_SCCP_USER.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscMgwSccpUser record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_SCCP_USER.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MGW_SCCP_USER
     *
     * @ibatorgenerated Tue Oct 23 12:00:41 ICT 2012
     */
    public int updateByPrimaryKey(HrMscMgwSccpUser record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MGW_SCCP_USER.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscMgwSccpUser> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_SCCP_USER.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscMgwSccpUser> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_MGW_SCCP_USER.filter2", map);
      	}
}