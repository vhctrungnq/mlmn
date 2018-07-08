package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnCellTrafficDistrict;

public class MnCellTrafficDistrictDAOImpl extends SqlMapClientDaoSupport implements MnCellTrafficDistrictDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public MnCellTrafficDistrictDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int deleteByPrimaryKey(String district, Integer month, String province, Integer year) {
        MnCellTrafficDistrict key = new MnCellTrafficDistrict();
        key.setDistrict(district);
        key.setMonth(month);
        key.setProvince(province);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_CELL_TRAFFIC_DISTRICT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(MnCellTrafficDistrict record) {
        getSqlMapClientTemplate().insert("MN_CELL_TRAFFIC_DISTRICT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(MnCellTrafficDistrict record) {
        getSqlMapClientTemplate().insert("MN_CELL_TRAFFIC_DISTRICT.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public MnCellTrafficDistrict selectByPrimaryKey(String district, Integer month, String province, Integer year) {
        MnCellTrafficDistrict key = new MnCellTrafficDistrict();
        key.setDistrict(district);
        key.setMonth(month);
        key.setProvince(province);
        key.setYear(year);
        MnCellTrafficDistrict record = (MnCellTrafficDistrict) getSqlMapClientTemplate().queryForObject("MN_CELL_TRAFFIC_DISTRICT.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKeySelective(MnCellTrafficDistrict record) {
        int rows = getSqlMapClientTemplate().update("MN_CELL_TRAFFIC_DISTRICT.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_CELL_TRAFFIC_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public int updateByPrimaryKey(MnCellTrafficDistrict record) {
        int rows = getSqlMapClientTemplate().update("MN_CELL_TRAFFIC_DISTRICT.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<MnCellTrafficDistrict> filter(String province, String district, Integer startMonth, Integer startYear, Integer endMonth, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("MN_CELL_TRAFFIC_DISTRICT.filter", map);
	}
}