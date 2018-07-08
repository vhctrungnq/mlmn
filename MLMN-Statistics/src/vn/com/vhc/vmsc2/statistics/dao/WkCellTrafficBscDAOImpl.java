package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficBsc;

public class WkCellTrafficBscDAOImpl extends SqlMapClientDaoSupport implements WkCellTrafficBscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellTrafficBscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String bscid, Integer week, Integer year) {
        WkCellTrafficBsc key = new WkCellTrafficBsc();
        key.setBscid(bscid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_CELL_TRAFFIC_BSC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(WkCellTrafficBsc record) {
        getSqlMapClientTemplate().insert("WK_CELL_TRAFFIC_BSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(WkCellTrafficBsc record) {
        getSqlMapClientTemplate().insert("WK_CELL_TRAFFIC_BSC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellTrafficBsc selectByPrimaryKey(String bscid, Integer week, Integer year) {
        WkCellTrafficBsc key = new WkCellTrafficBsc();
        key.setBscid(bscid);
        key.setWeek(week);
        key.setYear(year);
        WkCellTrafficBsc record = (WkCellTrafficBsc) getSqlMapClientTemplate().queryForObject("WK_CELL_TRAFFIC_BSC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(WkCellTrafficBsc record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TRAFFIC_BSC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(WkCellTrafficBsc record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TRAFFIC_BSC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkCellTrafficBsc> filter(String bscid, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("WK_CELL_TRAFFIC_BSC.filter", map);
	}
}