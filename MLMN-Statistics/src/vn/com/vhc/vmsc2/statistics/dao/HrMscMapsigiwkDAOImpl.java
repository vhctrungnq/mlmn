package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscMapsigiwk;

public class HrMscMapsigiwkDAOImpl extends SqlMapClientDaoSupport implements HrMscMapsigiwkDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public HrMscMapsigiwkDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMapsigiwk key = new HrMscMapsigiwk();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_MAPSIGIWRK.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public void insert(HrMscMapsigiwk record) {
        getSqlMapClientTemplate().insert("HR_MSC_MAPSIGIWRK.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public void insertSelective(HrMscMapsigiwk record) {
        getSqlMapClientTemplate().insert("HR_MSC_MAPSIGIWRK.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public HrMscMapsigiwk selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscMapsigiwk key = new HrMscMapsigiwk();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscMapsigiwk record = (HrMscMapsigiwk) getSqlMapClientTemplate().queryForObject("HR_MSC_MAPSIGIWRK.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public int updateByPrimaryKeySelective(HrMscMapsigiwk record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MAPSIGIWRK.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_MAPSIGIWRK
     *
     * @ibatorgenerated Thu Jan 03 11:44:58 ICT 2013
     */
    public int updateByPrimaryKey(HrMscMapsigiwk record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_MAPSIGIWRK.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    @SuppressWarnings("unchecked")
   	public List<HrMscMapsigiwk> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_MAPSIGIWRK.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscMapsigiwk> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_MAPSIGIWRK.filter2", map);
      	}
}