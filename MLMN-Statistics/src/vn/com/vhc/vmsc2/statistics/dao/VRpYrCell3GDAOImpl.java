package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrCell3G;

public class VRpYrCell3GDAOImpl extends SqlMapClientDaoSupport implements VRpYrCell3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_CELL_3G
     *
     * @ibatorgenerated Mon Oct 12 08:56:42 ICT 2015
     */
    public VRpYrCell3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_CELL_3G
     *
     * @ibatorgenerated Mon Oct 12 08:56:42 ICT 2015
     */
    public void insert(VRpYrCell3G record) {
        getSqlMapClientTemplate().insert("V_RP_YR_CELL_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_CELL_3G
     *
     * @ibatorgenerated Mon Oct 12 08:56:42 ICT 2015
     */
    public void insertSelective(VRpYrCell3G record) {
        getSqlMapClientTemplate().insert("V_RP_YR_CELL_3G.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<VRpYrCell3G> filter(String startYear, String endYear, String cellid, String bscid, String province,
			String region,  String column, int order) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		  map.put("P_START_YEAR", startYear);
		  map.put("P_END_YEAR", endYear);
		  map.put("P_PROVINCE", province);
		  map.put("P_BSC_ID", bscid);
		  map.put("P_CELL_ID", cellid);
		  map.put("P_REGION", region);
		  map.put("order", order);
		  map.put("column", column);
		  map.put("P_DATA", null);
		  return getSqlMapClientTemplate().queryForList("V_RP_YR_CELL_3G.filter", map);
	}
}