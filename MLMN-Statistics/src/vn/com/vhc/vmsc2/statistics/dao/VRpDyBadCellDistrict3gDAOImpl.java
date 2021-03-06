package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellDistrict3g;

public class VRpDyBadCellDistrict3gDAOImpl extends SqlMapClientDaoSupport implements VRpDyBadCellDistrict3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:56:05 ICT 2011
     */
    public VRpDyBadCellDistrict3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:56:05 ICT 2011
     */
    public void insert(VRpDyBadCellDistrict3g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BAD_CELL_DISTRICT_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BAD_CELL_DISTRICT_3G
     *
     * @ibatorgenerated Tue Jul 05 14:56:05 ICT 2011
     */
    public void insertSelective(VRpDyBadCellDistrict3g record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BAD_CELL_DISTRICT_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyBadCellDistrict3g> filter(String province, String district, String startDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_BAD_CELL_DISTRICT_3G.filter", map);
	}
}