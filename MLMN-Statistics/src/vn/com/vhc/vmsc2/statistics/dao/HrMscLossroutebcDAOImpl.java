package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscLossroutebc;

public class HrMscLossroutebcDAOImpl extends SqlMapClientDaoSupport implements HrMscLossroutebcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public HrMscLossroutebcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public int deleteByPrimaryKey(String bc, Date day, Integer hour, String mscid, String routeid) {
        HrMscLossroutebc key = new HrMscLossroutebc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setRouteid(routeid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_LOSS_ROUTE_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public void insert(HrMscLossroutebc record) {
        getSqlMapClientTemplate().insert("HR_MSC_LOSS_ROUTE_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public void insertSelective(HrMscLossroutebc record) {
        getSqlMapClientTemplate().insert("HR_MSC_LOSS_ROUTE_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public HrMscLossroutebc selectByPrimaryKey(String bc, Date day, Integer hour, String mscid, String routeid) {
        HrMscLossroutebc key = new HrMscLossroutebc();
        key.setBc(bc);
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        key.setRouteid(routeid);
        HrMscLossroutebc record = (HrMscLossroutebc) getSqlMapClientTemplate().queryForObject("HR_MSC_LOSS_ROUTE_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public int updateByPrimaryKeySelective(HrMscLossroutebc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_LOSS_ROUTE_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_LOSS_ROUTE_BC
     *
     * @ibatorgenerated Wed Oct 10 11:14:26 ICT 2012
     */
    public int updateByPrimaryKey(HrMscLossroutebc record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_LOSS_ROUTE_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscLossroutebc> filter(String startHour, Date startDate, String endHour,Date endDate, String routeid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("routeid", routeid);
       	return getSqlMapClientTemplate().queryForList("HR_MSC_LOSS_ROUTE_BC.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<HrMscLossroutebc> filter2(String startHour, Date startDate, String endHour, String routeid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("routeid", routeid);
   		return getSqlMapClientTemplate().queryForList("HR_MSC_LOSS_ROUTE_BC.filter2", map);
      	}
}