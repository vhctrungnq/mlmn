package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.HrMscRabassigObjbc;

public class HrMscRabassigObjbcDAOImpl extends SqlMapClientDaoSupport implements HrMscRabassigObjbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public HrMscRabassigObjbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid, String obj) {
        HrMscRabassigObjbc key = new HrMscRabassigObjbc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setObj(obj);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_RAB_ASSIG_OBJ_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public void insert(HrMscRabassigObjbc record) {
        getSqlMapClientTemplate().insert("HR_MSC_RAB_ASSIG_OBJ_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public void insertSelective(HrMscRabassigObjbc record) {
        getSqlMapClientTemplate().insert("HR_MSC_RAB_ASSIG_OBJ_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public HrMscRabassigObjbc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid, String obj) {
        HrMscRabassigObjbc key = new HrMscRabassigObjbc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setObj(obj);
        HrMscRabassigObjbc record = (HrMscRabassigObjbc) getSqlMapClientTemplate().queryForObject("HR_MSC_RAB_ASSIG_OBJ_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscRabassigObjbc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_RAB_ASSIG_OBJ_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_RAB_ASSIG_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 10:12:40 ICT 2012
     */
    public int updateByPrimaryKey(HrMscRabassigObjbc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_RAB_ASSIG_OBJ_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscRabassigObjbc> filter(String startHour, Date startDate, String endHour,  Date endDate, String mscid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("mscid", mscid);	

    	return getSqlMapClientTemplate().queryForList("HR_MSC_RAB_ASSIG_OBJ_BC.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<HrMscRabassigObjbc> filter2(String startHour, Date startDate, String endHour, String mscid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("mscid", mscid);
		return getSqlMapClientTemplate().queryForList("HR_MSC_RAB_ASSIG_OBJ_BC.filter2", map);
   	}
}