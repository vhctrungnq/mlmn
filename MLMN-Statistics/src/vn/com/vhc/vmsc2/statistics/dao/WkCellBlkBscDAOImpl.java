package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkBsc;

public class WkCellBlkBscDAOImpl extends SqlMapClientDaoSupport implements WkCellBlkBscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellBlkBscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String bscid, Integer week, Integer year) {
        WkCellBlkBsc key = new WkCellBlkBsc();
        key.setBscid(bscid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_CELL_BLK_BSC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(WkCellBlkBsc record) {
        getSqlMapClientTemplate().insert("WK_CELL_BLK_BSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(WkCellBlkBsc record) {
        getSqlMapClientTemplate().insert("WK_CELL_BLK_BSC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellBlkBsc selectByPrimaryKey(String bscid, Integer week, Integer year) {
        WkCellBlkBsc key = new WkCellBlkBsc();
        key.setBscid(bscid);
        key.setWeek(week);
        key.setYear(year);
        WkCellBlkBsc record = (WkCellBlkBsc) getSqlMapClientTemplate().queryForObject("WK_CELL_BLK_BSC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(WkCellBlkBsc record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_BLK_BSC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_BSC
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(WkCellBlkBsc record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_BLK_BSC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkCellBlkBsc> filter(String bscid, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("WK_CELL_BLK_BSC.filter", map);
	}
}