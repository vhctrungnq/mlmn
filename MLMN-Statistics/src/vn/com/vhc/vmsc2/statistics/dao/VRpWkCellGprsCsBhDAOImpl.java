package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsCsBh;

public class VRpWkCellGprsCsBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkCellGprsCsBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_CS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:09:58 ICT 2010
     */
    public VRpWkCellGprsCsBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_CS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:09:58 ICT 2010
     */
    public void insert(VRpWkCellGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_GPRS_CS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_GPRS_CS_BH
     *
     * @ibatorgenerated Mon Nov 15 14:09:58 ICT 2010
     */
    public void insertSelective(VRpWkCellGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_GPRS_CS_BH.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpWkCellGprsCsBh> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_GPRS_CS_BH.filter", map);
    }
}