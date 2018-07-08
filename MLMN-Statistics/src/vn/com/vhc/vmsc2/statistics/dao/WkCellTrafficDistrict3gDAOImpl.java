package vn.com.vhc.vmsc2.statistics.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficDistrict3g;

public class WkCellTrafficDistrict3gDAOImpl extends SqlMapClientDaoSupport implements WkCellTrafficDistrict3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellTrafficDistrict3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String district, BigDecimal week, String province, Integer year) {
        WkCellTrafficDistrict3g key = new WkCellTrafficDistrict3g();
        key.setDistrict(district);
        key.setWeek(week);
        key.setProvince(province);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_CELL_TRAFFIC_DISTRICT_3G.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(WkCellTrafficDistrict3g record) {
        getSqlMapClientTemplate().insert("WK_CELL_TRAFFIC_DISTRICT_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(WkCellTrafficDistrict3g record) {
        getSqlMapClientTemplate().insert("WK_CELL_TRAFFIC_DISTRICT_3G.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellTrafficDistrict3g selectByPrimaryKey(String district, BigDecimal week, String province, Integer year) {
        WkCellTrafficDistrict3g key = new WkCellTrafficDistrict3g();
        key.setDistrict(district);
        key.setWeek(week);
        key.setProvince(province);
        key.setYear(year);
        WkCellTrafficDistrict3g record = (WkCellTrafficDistrict3g) getSqlMapClientTemplate().queryForObject("WK_CELL_TRAFFIC_DISTRICT_3G.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(WkCellTrafficDistrict3g record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TRAFFIC_DISTRICT_3G.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(WkCellTrafficDistrict3g record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TRAFFIC_DISTRICT_3G.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkCellTrafficDistrict3g> filter(String province, String district, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("WK_CELL_TRAFFIC_DISTRICT_3G.filter", map);
	}
}