package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkProvince;

public class VRpDyCellBlkProvinceDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellBlkProvinceDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_PROVINCE
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public VRpDyCellBlkProvinceDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_PROVINCE
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public void insert(VRpDyCellBlkProvince record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BLK_PROVINCE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_PROVINCE
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public void insertSelective(VRpDyCellBlkProvince record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BLK_PROVINCE.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellBlkProvince> filter(String province, String startDate, String endDate, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_BLK_PROVINCE.filter", map);
	}
}