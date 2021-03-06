package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsQosBh;

public class VRpWkCellGprsQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkCellGprsQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:10:35 ICT 2010
     */
    public VRpWkCellGprsQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:10:35 ICT 2010
     */
    public void insert(VRpWkCellGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_GPRS_QOS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_QOS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:10:35 ICT 2010
     */
    public void insertSelective(VRpWkCellGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_GPRS_QOS_BH.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpWkCellGprsQosBh> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_GPRS_QOS_BH.filter", map);
    }
}