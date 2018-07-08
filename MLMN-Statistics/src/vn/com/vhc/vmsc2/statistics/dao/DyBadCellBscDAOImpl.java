package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyBadCellBsc;

public class DyBadCellBscDAOImpl extends SqlMapClientDaoSupport implements DyBadCellBscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_BSC
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    public DyBadCellBscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_BSC
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    public void insert(DyBadCellBsc record) {
        getSqlMapClientTemplate().insert("V_DY_BAD_CELL_BSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_DY_BAD_CELL_BSC
     *
     * @ibatorgenerated Mon Dec 20 08:57:06 ICT 2010
     */
    public void insertSelective(DyBadCellBsc record) {
        getSqlMapClientTemplate().insert("V_DY_BAD_CELL_BSC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<DyBadCellBsc> filter(String bscid, String startDate, String endDate, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_DY_BAD_CELL_BSC.filter", map);
	}
}