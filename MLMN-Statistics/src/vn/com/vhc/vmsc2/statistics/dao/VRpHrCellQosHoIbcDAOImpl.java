package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.BadcellConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellQosHoIbc;

public class VRpHrCellQosHoIbcDAOImpl extends SqlMapClientDaoSupport implements VRpHrCellQosHoIbcDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL_QOS_HO_IBC
	 * 
	 * @ibatorgenerated Tue Nov 23 09:06:58 ICT 2010
	 */
	public VRpHrCellQosHoIbcDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL_QOS_HO_IBC
	 * 
	 * @ibatorgenerated Tue Nov 23 09:06:58 ICT 2010
	 */
	public void insert(VRpHrCellQosHoIbc record) {
		getSqlMapClientTemplate().insert("V_RP_HR_CELL_QOS_HO_IBC.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL_QOS_HO_IBC
	 * 
	 * @ibatorgenerated Tue Nov 23 09:06:58 ICT 2010
	 */
	public void insertSelective(VRpHrCellQosHoIbc record) {
		getSqlMapClientTemplate().insert("V_RP_HR_CELL_QOS_HO_IBC.ibatorgenerated_insertSelective", record);
	}

	@SuppressWarnings("unchecked")
	public List<VRpHrCellQosHoIbc> getBadcell(String province, String bscid, String cellid, String vendor, Date day, List<BadcellConfig> badcellConfigList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", day);
		map.put("province", province);
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("vendor", vendor);
		map.put("badcellConfigList", badcellConfigList);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_CELL_QOS_HO_IBC.getBadcell", map);
	}
}