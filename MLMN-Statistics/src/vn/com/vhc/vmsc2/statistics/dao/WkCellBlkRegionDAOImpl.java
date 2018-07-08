package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkCellBlkRegion;

public class WkCellBlkRegionDAOImpl extends SqlMapClientDaoSupport implements WkCellBlkRegionDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellBlkRegionDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String region, Integer week, Integer year) {
        WkCellBlkRegion key = new WkCellBlkRegion();
        key.setRegion(region);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_CELL_BLK_REGION.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(WkCellBlkRegion record) {
        getSqlMapClientTemplate().insert("WK_CELL_BLK_REGION.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(WkCellBlkRegion record) {
        getSqlMapClientTemplate().insert("WK_CELL_BLK_REGION.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellBlkRegion selectByPrimaryKey(String region, Integer week, Integer year) {
        WkCellBlkRegion key = new WkCellBlkRegion();
        key.setRegion(region);
        key.setWeek(week);
        key.setYear(year);
        WkCellBlkRegion record = (WkCellBlkRegion) getSqlMapClientTemplate().queryForObject("WK_CELL_BLK_REGION.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(WkCellBlkRegion record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_BLK_REGION.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(WkCellBlkRegion record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_BLK_REGION.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkCellBlkRegion> filter(String region, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("WK_CELL_BLK_REGION.filter", map);
	}
}