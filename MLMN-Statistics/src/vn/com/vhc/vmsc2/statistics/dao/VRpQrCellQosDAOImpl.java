package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrCellQos;

public class VRpQrCellQosDAOImpl extends SqlMapClientDaoSupport implements VRpQrCellQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_CELL_QOS
     *
     * @ibatorgenerated Wed Sep 23 15:25:46 ICT 2015
     */
    public VRpQrCellQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_CELL_QOS
     *
     * @ibatorgenerated Wed Sep 23 15:25:46 ICT 2015
     */
    public void insert(VRpQrCellQos record) {
        getSqlMapClientTemplate().insert("V_RP_QR_CELL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_CELL_QOS
     *
     * @ibatorgenerated Wed Sep 23 15:25:46 ICT 2015
     */
    public void insertSelective(VRpQrCellQos record) {
        getSqlMapClientTemplate().insert("V_RP_QR_CELL_QOS.ibatorgenerated_insertSelective", record);
    }

	/* (non-Javadoc)
	 * @see vn.com.vhc.vmsc2.statistics.dao.VRpQrCellQosDAO#filter(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<VRpQrCellQos> filter(String startQuarter, String startYear, String endQuarter, String endYear, 
			String cellid, String province, String bscid, String region, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_quarter",startQuarter);
		map.put("p_start_year", startYear);
		map.put("p_end_quarter", endQuarter);
		map.put("p_end_year", endYear);
		map.put("p_cellid", cellid);
		map.put("p_province", province);
		map.put("p_bscid", bscid);
		map.put("p_region", region);
		
		map.put("order", order);
		map.put("column", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_QR_CELL_QOS.filter", map);
	}
}