package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellIbc;

public class VRpMnCellIbcDAOImpl extends SqlMapClientDaoSupport implements VRpMnCellIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_IBC
     *
     * @ibatorgenerated Thu Nov 11 16:05:33 ICT 2010
     */
    public VRpMnCellIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_IBC
     *
     * @ibatorgenerated Thu Nov 11 16:05:33 ICT 2010
     */
    public void insert(VRpMnCellIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_CELL_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_CELL_IBC
     *
     * @ibatorgenerated Thu Nov 11 16:05:33 ICT 2010
     */
    public void insertSelective(VRpMnCellIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_CELL_IBC.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpMnCellIbc> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String cellid, String bscid, String province, String region){
    	Map<String, String> map1 = new HashMap<String, String>();
		map1.put("cellid", cellid);
		map1.put("bscid", bscid);
    	map1.put(province, province);
		map1.put("startMonth", Integer.toString(startMonth));
		map1.put("endMonth", Integer.toString(endMonth));
		map1.put("startYear", Integer.toString(startYear));
		map1.put("endYear", Integer.toString(endYear));
    	map1.put(region, region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_CELL_IBC.filter", map1);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpMnCellIbc> filter(Integer startMonth,Integer startYear, Integer endMonth,Integer endYear,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_CELL_IBC.filter2", map);
    }
}