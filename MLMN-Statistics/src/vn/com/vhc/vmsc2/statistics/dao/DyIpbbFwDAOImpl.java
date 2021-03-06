package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyIpbbFw;

public class DyIpbbFwDAOImpl extends SqlMapClientDaoSupport implements DyIpbbFwDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public DyIpbbFwDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public int deleteByPrimaryKey(Date day, String route, String scp) {
        DyIpbbFw key = new DyIpbbFw();
        key.setDay(day);
        key.setRoute(route);
        key.setScp(scp);
        int rows = getSqlMapClientTemplate().delete("DY_IPBB_FW.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public void insert(DyIpbbFw record) {
        getSqlMapClientTemplate().insert("DY_IPBB_FW.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public void insertSelective(DyIpbbFw record) {
        getSqlMapClientTemplate().insert("DY_IPBB_FW.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public DyIpbbFw selectByPrimaryKey(Date day, String route, String scp) {
        DyIpbbFw key = new DyIpbbFw();
        key.setDay(day);
        key.setRoute(route);
        key.setScp(scp);
        DyIpbbFw record = (DyIpbbFw) getSqlMapClientTemplate().queryForObject("DY_IPBB_FW.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public int updateByPrimaryKeySelective(DyIpbbFw record) {
        int rows = getSqlMapClientTemplate().update("DY_IPBB_FW.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:36 ICT 2013
     */
    public int updateByPrimaryKey(DyIpbbFw record) {
        int rows = getSqlMapClientTemplate().update("DY_IPBB_FW.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<DyIpbbFw> filter(String startDate, String endDate, String routeid, String scp) {
   		Map<String, String> map = new HashMap<String, String>();
       	map.put("routeid", routeid);
       	map.put("scp", scp);
       	map.put("startDate", startDate);
       	map.put("endDate", endDate);
       	
       	return getSqlMapClientTemplate().queryForList("DY_IPBB_FW.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<DyIpbbFw> getData(String startDate, String endDate, String route, String scp) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_START_DATE", startDate);
    	parms.put("P_END_DATE", endDate);
    	parms.put("P_ROUTE_ID", route);
    	parms.put("P_SCP", scp);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("DY_IPBB_FW.getData", parms);
    }
    @SuppressWarnings("unchecked")
    public List<DyIpbbFw> getDayList(String startDate, String endDate) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_START_DATE", startDate);
    	parms.put("P_END_DATE", endDate);
		parms.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("DY_IPBB_FW.getDayList", parms);
    }
}