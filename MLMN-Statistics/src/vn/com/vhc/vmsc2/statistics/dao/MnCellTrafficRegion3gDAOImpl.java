package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnCellTrafficRegion3g;

public class MnCellTrafficRegion3gDAOImpl extends SqlMapClientDaoSupport implements MnCellTrafficRegion3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public MnCellTrafficRegion3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(Integer month, String region, Integer year) {
        MnCellTrafficRegion3g key = new MnCellTrafficRegion3g();
        key.setMonth(month);
        key.setRegion(region);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_CELL_TRAFFIC_REGION_3G.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(MnCellTrafficRegion3g record) {
        getSqlMapClientTemplate().insert("MN_CELL_TRAFFIC_REGION_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(MnCellTrafficRegion3g record) {
        getSqlMapClientTemplate().insert("MN_CELL_TRAFFIC_REGION_3G.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public MnCellTrafficRegion3g selectByPrimaryKey(Integer month, String region, Integer year) {
        MnCellTrafficRegion3g key = new MnCellTrafficRegion3g();
        key.setMonth(month);
        key.setRegion(region);
        key.setYear(year);
        MnCellTrafficRegion3g record = (MnCellTrafficRegion3g) getSqlMapClientTemplate().queryForObject("MN_CELL_TRAFFIC_REGION_3G.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(MnCellTrafficRegion3g record) {
        int rows = getSqlMapClientTemplate().update("MN_CELL_TRAFFIC_REGION_3G.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_REGION_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(MnCellTrafficRegion3g record) {
        int rows = getSqlMapClientTemplate().update("MN_CELL_TRAFFIC_REGION_3G.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<MnCellTrafficRegion3g> filter(String region, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("MN_CELL_TRAFFIC_REGION_3G.filter", map);
	}
}