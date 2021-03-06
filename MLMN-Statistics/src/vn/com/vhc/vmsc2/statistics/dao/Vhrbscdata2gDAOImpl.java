package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.Vhrbscdata2g;

public class Vhrbscdata2gDAOImpl extends SqlMapClientDaoSupport implements Vhrbscdata2gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Wed Aug 01 11:31:36 ICT 2012
     */
    public Vhrbscdata2gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Wed Aug 01 11:31:36 ICT 2012
     */
    public void insert(Vhrbscdata2g record) {
        getSqlMapClientTemplate().insert("V_HR_BSC_DATA_2G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_HR_BSC_DATA_2G
     *
     * @ibatorgenerated Wed Aug 01 11:31:36 ICT 2012
     */
    public void insertSelective(Vhrbscdata2g record) {
        getSqlMapClientTemplate().insert("V_HR_BSC_DATA_2G.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
   	public List<Vhrbscdata2g> filter(String startHour, Date startDate, String endHour,  Date endDate, String bscid) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("bscid", bscid);

    		return getSqlMapClientTemplate().queryForList("V_HR_BSC_DATA_2G.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<Vhrbscdata2g> filter2(String startHour, Date startDate, String endHour, String bscid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("bscid", bscid);
		return getSqlMapClientTemplate().queryForList("V_HR_BSC_DATA_2G.filter2", map);
   	}
    @SuppressWarnings("unchecked")
   	public List<Vhrbscdata2g> filterTF(String startHour, Date startDate, String endHour,  Date endDate, String bscid) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("startHour", startHour);
    		map.put("endHour", endHour);
    		map.put("startDate", startDate);
    		map.put("endDate", endDate);
    		map.put("bscid", bscid);

    		return getSqlMapClientTemplate().queryForList("V_HR_BSC_DATA_2G.filter", map);
   	}
    @SuppressWarnings("unchecked")
    public List<Vhrbscdata2g> filterTF2(String startHour, Date startDate, String endHour, String bscid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("startDate", startDate);
		map.put("bscid", bscid);
		return getSqlMapClientTemplate().queryForList("V_HR_BSC_DATA_2G.filter2", map);
   	}
}