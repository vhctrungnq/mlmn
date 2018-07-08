package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkCellTrafficDistrict;

public class WkCellTrafficDistrictDAOImpl extends SqlMapClientDaoSupport implements WkCellTrafficDistrictDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellTrafficDistrictDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String district, String province, Integer week, Integer year) {
        WkCellTrafficDistrict key = new WkCellTrafficDistrict();
        key.setDistrict(district);
        key.setProvince(province);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_CELL_TRAFFIC_DISTRICT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(WkCellTrafficDistrict record) {
        getSqlMapClientTemplate().insert("WK_CELL_TRAFFIC_DISTRICT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(WkCellTrafficDistrict record) {
        getSqlMapClientTemplate().insert("WK_CELL_TRAFFIC_DISTRICT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public WkCellTrafficDistrict selectByPrimaryKey(String district, String province, Integer week, Integer year) {
        WkCellTrafficDistrict key = new WkCellTrafficDistrict();
        key.setDistrict(district);
        key.setProvince(province);
        key.setWeek(week);
        key.setYear(year);
        WkCellTrafficDistrict record = (WkCellTrafficDistrict) getSqlMapClientTemplate().queryForObject("WK_CELL_TRAFFIC_DISTRICT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(WkCellTrafficDistrict record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TRAFFIC_DISTRICT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(WkCellTrafficDistrict record) {
        int rows = getSqlMapClientTemplate().update("WK_CELL_TRAFFIC_DISTRICT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkCellTrafficDistrict> filter(String province, String district, Integer startWeek, Integer startYear, Integer endWeek, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("WK_CELL_TRAFFIC_DISTRICT.filter", map);
	}
}