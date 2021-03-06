package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMmBc;

public class HrMscMmBcDAOImpl extends SqlMapClientDaoSupport implements HrMscMmBcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public HrMscMmBcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid) {
        HrMscMmBc key = new HrMscMmBc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_MM_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public void insert(HrMscMmBc record) {
        getSqlMapClientTemplate().insert("HR_MSC_MM_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public void insertSelective(HrMscMmBc record) {
        getSqlMapClientTemplate().insert("HR_MSC_MM_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public HrMscMmBc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid) {
        HrMscMmBc key = new HrMscMmBc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscMmBc record = (HrMscMmBc) getSqlMapClientTemplate().queryForObject("HR_MSC_MM_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscMmBc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MM_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MM_BC
     *
     * @ibatorgenerated Thu Oct 18 14:41:48 ICT 2012
     */
    public int updateByPrimaryKey(HrMscMmBc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MM_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    @SuppressWarnings("unchecked")
	public List<HrMscMmBc> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_MM_BC.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscMmBc> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_MM_BC.filter2", map);
      	}
	
}