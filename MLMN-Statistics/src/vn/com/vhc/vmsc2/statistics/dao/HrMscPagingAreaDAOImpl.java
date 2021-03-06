package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscPagingArea;

public class HrMscPagingAreaDAOImpl extends SqlMapClientDaoSupport implements HrMscPagingAreaDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public HrMscPagingAreaDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscPagingArea key = new HrMscPagingArea();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_PAGING_AREA.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public void insert(HrMscPagingArea record) {
        getSqlMapClientTemplate().insert("HR_MSC_PAGING_AREA.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public void insertSelective(HrMscPagingArea record) {
        getSqlMapClientTemplate().insert("HR_MSC_PAGING_AREA.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public HrMscPagingArea selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscPagingArea key = new HrMscPagingArea();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscPagingArea record = (HrMscPagingArea) getSqlMapClientTemplate().queryForObject("HR_MSC_PAGING_AREA.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscPagingArea record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_PAGING_AREA.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_PAGING_AREA
     *
     * @ibatorgenerated Mon Oct 15 15:47:16 ICT 2012
     */
    public int updateByPrimaryKey(HrMscPagingArea record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_PAGING_AREA.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscPagingArea> filter(String startHour, Date startDate, String endHour,Date endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("mscid", mscid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_PAGING_AREA.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscPagingArea> filter2(String startHour, Date startDate, String endHour, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_PAGING_AREA.filter2", map);
      	}
}