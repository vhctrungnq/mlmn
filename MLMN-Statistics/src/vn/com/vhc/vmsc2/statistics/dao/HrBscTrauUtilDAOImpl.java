package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrBscTrauUtil;

public class HrBscTrauUtilDAOImpl extends SqlMapClientDaoSupport implements HrBscTrauUtilDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public HrBscTrauUtilDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public int deleteByPrimaryKey(String bscid, Date day, Integer hour) {
        HrBscTrauUtil key = new HrBscTrauUtil();
        key.setBscid(bscid);
        key.setDay(day);
        key.setHour(hour);
        int rows = getSqlMapClientTemplate().delete("HR_BSC_TRAU_UTIL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public void insert(HrBscTrauUtil record) {
        getSqlMapClientTemplate().insert("HR_BSC_TRAU_UTIL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public void insertSelective(HrBscTrauUtil record) {
        getSqlMapClientTemplate().insert("HR_BSC_TRAU_UTIL.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public HrBscTrauUtil selectByPrimaryKey(String bscid, Date day, Integer hour) {
        HrBscTrauUtil key = new HrBscTrauUtil();
        key.setBscid(bscid);
        key.setDay(day);
        key.setHour(hour);
        HrBscTrauUtil record = (HrBscTrauUtil) getSqlMapClientTemplate().queryForObject("HR_BSC_TRAU_UTIL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public int updateByPrimaryKeySelective(HrBscTrauUtil record) {
        int rows = getSqlMapClientTemplate().update("HR_BSC_TRAU_UTIL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_TRAU_UTIL
     *
     * @ibatorgenerated Tue Jul 23 17:59:39 ICT 2013
     */
    public int updateByPrimaryKey(HrBscTrauUtil record) {
        int rows = getSqlMapClientTemplate().update("HR_BSC_TRAU_UTIL.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<HrBscTrauUtil> getHrBscTrauUtilFilter(String bscid, String startDate, String endDate, String shour, String ehour, String column, String order) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_BSCID", bscid);
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
    	map.put("P_SHOUR", shour);
    	map.put("P_EHOUR", ehour);
    	map.put("P_COLUMN", column);
    	map.put("P_ORDER", order);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("HR_BSC_TRAU_UTIL.getHrBscTrauUtilFilter", map);
    }
}