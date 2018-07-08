package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyBadCellProvince;

public class DyBadCellProvinceDAOImpl extends SqlMapClientDaoSupport implements DyBadCellProvinceDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_PROVINCE
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    public DyBadCellProvinceDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_PROVINCE
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    public void insert(DyBadCellProvince record) {
        getSqlMapClientTemplate().insert("V_DY_BAD_CELL_PROVINCE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_PROVINCE
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    public void insertSelective(DyBadCellProvince record) {
        getSqlMapClientTemplate().insert("V_DY_BAD_CELL_PROVINCE.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<DyBadCellProvince> filter(String province, String startDate, String endDate, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_DY_BAD_CELL_PROVINCE.filter", map);
	}
}