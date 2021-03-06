package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrBscCore;

public class HrBscCoreDAOImpl extends SqlMapClientDaoSupport implements HrBscCoreDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public HrBscCoreDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public int deleteByPrimaryKey(String bscid, Date day, Integer hour) {
		HrBscCore key = new HrBscCore();
		key.setBscid(bscid);
		key.setDay(day);
		key.setHour(hour);
		int rows = getSqlMapClientTemplate().delete("HR_BSC_CORE.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public void insert(HrBscCore record) {
		getSqlMapClientTemplate().insert("HR_BSC_CORE.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public void insertSelective(HrBscCore record) {
		getSqlMapClientTemplate().insert("HR_BSC_CORE.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public HrBscCore selectByPrimaryKey(String bscid, Date day, Integer hour) {
		HrBscCore key = new HrBscCore();
		key.setBscid(bscid);
		key.setDay(day);
		key.setHour(hour);
		HrBscCore record = (HrBscCore) getSqlMapClientTemplate().queryForObject("HR_BSC_CORE.ibatorgenerated_selectByPrimaryKey", key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public int updateByPrimaryKeySelective(HrBscCore record) {
		int rows = getSqlMapClientTemplate().update("HR_BSC_CORE.ibatorgenerated_updateByPrimaryKeySelective", record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table HR_BSC_CORE
	 * 
	 * @ibatorgenerated Mon Oct 18 10:35:49 ICT 2010
	 */
	public int updateByPrimaryKey(HrBscCore record) {
		int rows = getSqlMapClientTemplate().update("HR_BSC_CORE.ibatorgenerated_updateByPrimaryKey", record);
		return rows;
	}

	@SuppressWarnings("unchecked")
	public List<HrBscCore> filterDayHourAndBsc(Date day, Integer hour, String bscid) {
		HrBscCore key = new HrBscCore();
		key.setDay(day);
		key.setHour(hour);
		key.setBscid(bscid);
		return getSqlMapClientTemplate().queryForList("HR_BSC_CORE.filterDayHourAndBsc", key);
	}

	@SuppressWarnings("unchecked")
	public List<HrBscCore> filter(String bscid, String day, Integer startHour, Integer endHour) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("day", day);
		map.put("startHour", startHour.toString());
		map.put("endHour", endHour.toString());

		return getSqlMapClientTemplate().queryForList("HR_BSC_CORE.filter", map);
	}
}