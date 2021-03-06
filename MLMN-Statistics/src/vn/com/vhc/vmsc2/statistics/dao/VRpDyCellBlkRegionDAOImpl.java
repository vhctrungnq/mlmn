package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBlkRegion;

public class VRpDyCellBlkRegionDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellBlkRegionDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public VRpDyCellBlkRegionDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public void insert(VRpDyCellBlkRegion record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BLK_REGION.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_BLK_REGION
     *
     * @ibatorgenerated Tue Apr 05 15:49:04 ICT 2011
     */
    public void insertSelective(VRpDyCellBlkRegion record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_BLK_REGION.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellBlkRegion> filter(String region, String startDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_BLK_REGION.filter", map);
	}
}