package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.BadcellConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellQosHo;

public class VRpHrCellQosHoDAOImpl extends SqlMapClientDaoSupport implements VRpHrCellQosHoDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL_QOS_HO
	 * 
	 * @ibatorgenerated Tue Nov 23 09:06:58 ICT 2010
	 */
	public VRpHrCellQosHoDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL_QOS_HO
	 * 
	 * @ibatorgenerated Tue Nov 23 09:06:58 ICT 2010
	 */
	public void insert(VRpHrCellQosHo record) {
		getSqlMapClientTemplate().insert("V_RP_HR_CELL_QOS_HO.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_HR_CELL_QOS_HO
	 * 
	 * @ibatorgenerated Tue Nov 23 09:06:58 ICT 2010
	 */
	public void insertSelective(VRpHrCellQosHo record) {
		getSqlMapClientTemplate().insert("V_RP_HR_CELL_QOS_HO.ibatorgenerated_insertSelective", record);
	}

	@SuppressWarnings("unchecked")
	public List<VRpHrCellQosHo> getBadcell(String province, String bscid, String cellid, String vendor, Date day, List<BadcellConfig> badcellConfigList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", day);
		map.put("province", province);
		map.put("bscid", bscid);
		map.put("cellid", cellid);
		map.put("vendor", vendor);
		map.put("badcellConfigList", badcellConfigList);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_CELL_QOS_HO.getBadcell", map);
	}
}