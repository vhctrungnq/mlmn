package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTraffProvince3G;

public class VRpDyCellTraffProvince3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellTraffProvince3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFF_PROVINCE_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    public VRpDyCellTraffProvince3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFF_PROVINCE_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    public void insert(VRpDyCellTraffProvince3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_TRAFF_PROVINCE_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_TRAFF_PROVINCE_3G
     *
     * @ibatorgenerated Mon Jun 13 15:33:08 ICT 2011
     */
    public void insertSelective(VRpDyCellTraffProvince3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_TRAFF_PROVINCE_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellTraffProvince3G> filter(String province, String startDate, String endDate, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("province", province);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_TRAFF_PROVINCE_3G.filter", map);
	}
}