package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellIbc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class VRpDyCellIbcDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_IBC
     *
     * @ibatorgenerated Thu Dec 09 09:29:56 ICT 2010
     */
    public VRpDyCellIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_IBC
     *
     * @ibatorgenerated Thu Dec 09 09:29:56 ICT 2010
     */
    public void insert(VRpDyCellIbc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_IBC
     *
     * @ibatorgenerated Thu Dec 09 09:29:56 ICT 2010
     */
    public void insertSelective(VRpDyCellIbc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_IBC.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyCellIbc> filter(Date day,String cellid, String province, String bscid){
    	VRpDyCellIbc key = new VRpDyCellIbc();
    	key.setCellid(cellid);
    	key.setProvince(province);
    	key.setBscid(bscid);
    	key.setDay(day);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_IBC.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyCellIbc> filter(String startDate,String endDate,String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cellid", cellid);
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_IBC.filter2", map);
    }
}