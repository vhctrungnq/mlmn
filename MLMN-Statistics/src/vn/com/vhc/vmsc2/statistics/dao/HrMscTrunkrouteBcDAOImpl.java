package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.HrMscTrunkrouteBc;

public class HrMscTrunkrouteBcDAOImpl extends SqlMapClientDaoSupport implements HrMscTrunkrouteBcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public HrMscTrunkrouteBcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid, String routeid) {
        HrMscTrunkrouteBc key = new HrMscTrunkrouteBc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setRouteid(routeid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_TRUNK_ROUTE_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public void insert(HrMscTrunkrouteBc record) {
        getSqlMapClientTemplate().insert("HR_MSC_TRUNK_ROUTE_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public void insertSelective(HrMscTrunkrouteBc record) {
        getSqlMapClientTemplate().insert("HR_MSC_TRUNK_ROUTE_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public HrMscTrunkrouteBc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid, String routeid) {
        HrMscTrunkrouteBc key = new HrMscTrunkrouteBc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setRouteid(routeid);
        HrMscTrunkrouteBc record = (HrMscTrunkrouteBc) getSqlMapClientTemplate().queryForObject("HR_MSC_TRUNK_ROUTE_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscTrunkrouteBc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_TRUNK_ROUTE_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_TRUNK_ROUTE_BC
     *
     * @ibatorgenerated Mon Oct 15 10:26:52 ICT 2012
     */
    public int updateByPrimaryKey(HrMscTrunkrouteBc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_TRUNK_ROUTE_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscTrunkrouteBc> filter(String startHour, Date startDate, String endHour,Date endDate, String routeid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("routeid", routeid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_TRUNK_ROUTE_BC.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscTrunkrouteBc> filter2(String startHour, Date startDate, String endHour, String routeid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("routeid", routeid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_TRUNK_ROUTE_BC.filter2", map);
      	}
}