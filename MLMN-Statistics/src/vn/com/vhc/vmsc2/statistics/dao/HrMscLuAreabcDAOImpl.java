package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.HrMscLuAreabc;

public class HrMscLuAreabcDAOImpl extends SqlMapClientDaoSupport implements HrMscLuAreabcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public HrMscLuAreabcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid, String obj) {
        HrMscLuAreabc key = new HrMscLuAreabc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setObj(obj);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_LU_AREA_OBJ_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public void insert(HrMscLuAreabc record) {
        getSqlMapClientTemplate().insert("HR_MSC_LU_AREA_OBJ_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public void insertSelective(HrMscLuAreabc record) {
        getSqlMapClientTemplate().insert("HR_MSC_LU_AREA_OBJ_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public HrMscLuAreabc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid, String obj) {
        HrMscLuAreabc key = new HrMscLuAreabc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setObj(obj);
        HrMscLuAreabc record = (HrMscLuAreabc) getSqlMapClientTemplate().queryForObject("HR_MSC_LU_AREA_OBJ_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscLuAreabc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_LU_AREA_OBJ_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LU_AREA_OBJ_BC
     *
     * @ibatorgenerated Fri Oct 12 11:27:02 ICT 2012
     */
    public int updateByPrimaryKey(HrMscLuAreabc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_LU_AREA_OBJ_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscLuAreabc> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_LU_AREA_OBJ_BC.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscLuAreabc> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_LU_AREA_OBJ_BC.filter2", map);
      	}
}