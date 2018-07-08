package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyBadCellDistrict;

public class DyBadCellDistrictDAOImpl extends SqlMapClientDaoSupport implements DyBadCellDistrictDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public DyBadCellDistrictDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insert(DyBadCellDistrict record) {
        getSqlMapClientTemplate().insert("V_DY_BAD_CELL_DISTRICT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_DISTRICT
     *
     * @ibatorgenerated Tue Jul 05 14:51:42 ICT 2011
     */
    public void insertSelective(DyBadCellDistrict record) {
        getSqlMapClientTemplate().insert("V_DY_BAD_CELL_DISTRICT.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<DyBadCellDistrict> filter(String province, String district, String startDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("V_DY_BAD_CELL_DISTRICT.filter", map);
	}
}