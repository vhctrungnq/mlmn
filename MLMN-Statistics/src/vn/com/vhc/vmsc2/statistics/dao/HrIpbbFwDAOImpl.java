package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrIpbbFw;

public class HrIpbbFwDAOImpl extends SqlMapClientDaoSupport implements HrIpbbFwDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public HrIpbbFwDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public int deleteByPrimaryKey(String distanceTime, String route, String scp) {
        HrIpbbFw key = new HrIpbbFw();
        key.setDistanceTime(distanceTime);
        key.setRoute(route);
        key.setScp(scp);
        int rows = getSqlMapClientTemplate().delete("HR_IPBB_FW.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public void insert(HrIpbbFw record) {
        getSqlMapClientTemplate().insert("HR_IPBB_FW.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public void insertSelective(HrIpbbFw record) {
        getSqlMapClientTemplate().insert("HR_IPBB_FW.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public HrIpbbFw selectByPrimaryKey(String distanceTime, String route, String scp) {
        HrIpbbFw key = new HrIpbbFw();
        key.setDistanceTime(distanceTime);
        key.setRoute(route);
        key.setScp(scp);
        HrIpbbFw record = (HrIpbbFw) getSqlMapClientTemplate().queryForObject("HR_IPBB_FW.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public int updateByPrimaryKeySelective(HrIpbbFw record) {
        int rows = getSqlMapClientTemplate().update("HR_IPBB_FW.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_IPBB_FW
     *
     * @ibatorgenerated Tue Jul 02 14:34:27 ICT 2013
     */
    public int updateByPrimaryKey(HrIpbbFw record) {
        int rows = getSqlMapClientTemplate().update("HR_IPBB_FW.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrIpbbFw> filter(String startDate, String endDate, String routeid, String scp) {
   		Map<String, String> map = new HashMap<String, String>();
       	map.put("routeid", routeid);
       	map.put("scp", scp);
       	map.put("startDate", startDate);
       	map.put("endDate", endDate);
       	
       	return getSqlMapClientTemplate().queryForList("HR_IPBB_FW.filter", map);
   	}

}