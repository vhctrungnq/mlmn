package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.Vwkcell3gQos;

public class Vwkcell3gQosDAOImpl extends SqlMapClientDaoSupport implements Vwkcell3gQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:42:38 ICT 2012
     */
    public Vwkcell3gQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:42:38 ICT 2012
     */
    public void insert(Vwkcell3gQos record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_3G_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_CELL_3G_QOS
     *
     * @ibatorgenerated Tue Aug 07 09:42:38 ICT 2012
     */
    public void insertSelective(Vwkcell3gQos record) {
        getSqlMapClientTemplate().insert("V_RP_WK_CELL_3G_QOS.ibatorgenerated_insertSelective", record);
    }
    /*Unavailable cells*/
    @SuppressWarnings("unchecked")
    public List<Vwkcell3gQos> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("bscid", bscid);
		map.put("cellid", cellid);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G_QOS.filter", map);
    }
    /*NO HSDPA DATA CELLS*/
    @SuppressWarnings("unchecked")
    public List<Vwkcell3gQos> filter1(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("bscid", bscid);
		map.put("cellid", cellid);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G_QOS.filter1", map);
    }
    /*Low CSSR RNC*/
    @SuppressWarnings("unchecked")
    public List<Vwkcell3gQos> filter2(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("bscid", bscid);
		map.put("cellid", cellid);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G_QOS.filter2", map);
    }
    /*High HSDPA DATA 3G CELLS*/
    @SuppressWarnings("unchecked")
    public List<Vwkcell3gQos> filter3(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String bscid, String cellid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("bscid", bscid);
		map.put("cellid", cellid);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_CELL_3G_QOS.filter3", map);
    }
}