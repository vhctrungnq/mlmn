package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellCssrDetails3G;

public class VRpDyCellCssrDetails3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellCssrDetails3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public VRpDyCellCssrDetails3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public void insert(VRpDyCellCssrDetails3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_CSSR_DETAILS_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public void insertSelective(VRpDyCellCssrDetails3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_CSSR_DETAILS_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyCellCssrDetails3G> filter(String startDate, String endDate, String bscid, String cellid) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_CSSR_DETAILS_3G.filter", map);
	}
}