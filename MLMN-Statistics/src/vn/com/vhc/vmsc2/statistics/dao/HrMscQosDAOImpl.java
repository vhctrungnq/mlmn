package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscQos;

public class HrMscQosDAOImpl extends SqlMapClientDaoSupport implements HrMscQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public HrMscQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscQos key = new HrMscQos();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public void insert(HrMscQos record) {
        getSqlMapClientTemplate().insert("HR_MSC_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public void insertSelective(HrMscQos record) {
        getSqlMapClientTemplate().insert("HR_MSC_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public HrMscQos selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscQos key = new HrMscQos();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscQos record = (HrMscQos) getSqlMapClientTemplate().queryForObject("HR_MSC_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrMscQos record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_QOS
     *
     * @ibatorgenerated Thu Oct 07 11:13:53 ICT 2010
     */
    public int updateByPrimaryKey(HrMscQos record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<HrMscQos> filterDayHourAndMsc(String mscid, Date d, Integer hour) {
		HrMscQos key = new HrMscQos();
        key.setDay(d);
        key.setHour(hour);
        key.setMscid(mscid);
        return getSqlMapClientTemplate().queryForList("HR_MSC_QOS.filterDayHourAndMsc", key);
	}
	@SuppressWarnings("unchecked")
	public List<HrMscQos> filter(String mscid, String day, Integer startHour, Integer endHour) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mscid", mscid);
		map.put("day", day);
		map.put("startHour", startHour.toString());
		map.put("endHour", endHour.toString());

		return getSqlMapClientTemplate().queryForList("HR_MSC_QOS.filter", map);
	}
}