package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.DyBscSignalUtil;

public class DyBscSignalUtilDAOImpl extends SqlMapClientDaoSupport implements DyBscSignalUtilDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public DyBscSignalUtilDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public int deleteByPrimaryKey(String bscid, Date day) {
        DyBscSignalUtil key = new DyBscSignalUtil();
        key.setBscid(bscid);
        key.setDay(day);
        int rows = getSqlMapClientTemplate().delete("DY_BSC_SIGNAL_UTIL.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public void insert(DyBscSignalUtil record) {
        getSqlMapClientTemplate().insert("DY_BSC_SIGNAL_UTIL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public void insertSelective(DyBscSignalUtil record) {
        getSqlMapClientTemplate().insert("DY_BSC_SIGNAL_UTIL.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public DyBscSignalUtil selectByPrimaryKey(String bscid, Date day) {
        DyBscSignalUtil key = new DyBscSignalUtil();
        key.setBscid(bscid);
        key.setDay(day);
        DyBscSignalUtil record = (DyBscSignalUtil) getSqlMapClientTemplate().queryForObject("DY_BSC_SIGNAL_UTIL.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public int updateByPrimaryKeySelective(DyBscSignalUtil record) {
        int rows = getSqlMapClientTemplate().update("DY_BSC_SIGNAL_UTIL.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_SIGNAL_UTIL
     *
     * @ibatorgenerated Tue Jul 23 18:02:13 ICT 2013
     */
    public int updateByPrimaryKey(DyBscSignalUtil record) {
        int rows = getSqlMapClientTemplate().update("DY_BSC_SIGNAL_UTIL.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyBscSignalUtil> getDyBscSignalUtilFilter(String bscid, String startDate, String endDate, String column, String order) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_BSCID", bscid);
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
    	map.put("P_COLUMN", column);
    	map.put("P_ORDER", order);
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("DY_BSC_SIGNAL_UTIL.getDyBscSignalUtilFilter", map);
    }
}