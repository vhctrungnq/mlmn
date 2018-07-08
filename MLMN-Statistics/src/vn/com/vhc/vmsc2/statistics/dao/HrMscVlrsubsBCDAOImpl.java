package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrMscVlrsubsBC;

public class HrMscVlrsubsBCDAOImpl extends SqlMapClientDaoSupport implements HrMscVlrsubsBCDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public HrMscVlrsubsBCDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscVlrsubsBC key = new HrMscVlrsubsBC();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("HR_MSC_VLRSUBS_BC.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public void insert(HrMscVlrsubsBC record) {
        getSqlMapClientTemplate().insert("HR_MSC_VLRSUBS_BC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public void insertSelective(HrMscVlrsubsBC record) {
        getSqlMapClientTemplate().insert("HR_MSC_VLRSUBS_BC.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public HrMscVlrsubsBC selectByPrimaryKey(Date day, Integer hour, String mscid) {
        HrMscVlrsubsBC key = new HrMscVlrsubsBC();
        key.setDay(day);
        key.setHour(hour);
        key.setMscid(mscid);
        HrMscVlrsubsBC record = (HrMscVlrsubsBC) getSqlMapClientTemplate().queryForObject("HR_MSC_VLRSUBS_BC.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public int updateByPrimaryKeySelective(HrMscVlrsubsBC record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_VLRSUBS_BC.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_MSC_VLRSUBS_BC
     *
     * @ibatorgenerated Wed Jan 09 14:03:13 ICT 2013
     */
    public int updateByPrimaryKey(HrMscVlrsubsBC record) {
        int rows = getSqlMapClientTemplate().update("HR_MSC_VLRSUBS_BC.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<HrMscVlrsubsBC> filter(String startHour, Date startDate, String endHour,  Date endDate, String mscid) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("mscid", mscid);

    		return getSqlMapClientTemplate().queryForList("HR_MSC_VLRSUBS_BC.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<HrMscVlrsubsBC> filter2(String startHour, Date startDate, String endHour, String mscid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("mscid", mscid);
		return getSqlMapClientTemplate().queryForList("HR_MSC_VLRSUBS_BC.filter2", map);
   	}
   }