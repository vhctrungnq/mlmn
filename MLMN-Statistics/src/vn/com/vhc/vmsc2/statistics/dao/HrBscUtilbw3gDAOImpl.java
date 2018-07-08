package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.HrBscUtilbw3g; 

public class HrBscUtilbw3gDAOImpl extends SqlMapClientDaoSupport implements HrBscUtilbw3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public HrBscUtilbw3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public int deleteByPrimaryKey(String bscid, Date day, Integer hour) {
        HrBscUtilbw3g key = new HrBscUtilbw3g();
        key.setBscid(bscid);
        key.setDay(day);
        key.setHour(hour);
        int rows = getSqlMapClientTemplate().delete("HR_BSC_UTILBW_3G.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public void insert(HrBscUtilbw3g record) {
        getSqlMapClientTemplate().insert("HR_BSC_UTILBW_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public void insertSelective(HrBscUtilbw3g record) {
        getSqlMapClientTemplate().insert("HR_BSC_UTILBW_3G.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public HrBscUtilbw3g selectByPrimaryKey(String bscid, Date day, Integer hour) {
        HrBscUtilbw3g key = new HrBscUtilbw3g();
        key.setBscid(bscid);
        key.setDay(day);
        key.setHour(hour);
        HrBscUtilbw3g record = (HrBscUtilbw3g) getSqlMapClientTemplate().queryForObject("HR_BSC_UTILBW_3G.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public int updateByPrimaryKeySelective(HrBscUtilbw3g record) {
        int rows = getSqlMapClientTemplate().update("HR_BSC_UTILBW_3G.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_BSC_UTILBW_3G
     *
     * @ibatorgenerated Mon Jul 22 14:59:19 ICT 2013
     */
    public int updateByPrimaryKey(HrBscUtilbw3g record) {
        int rows = getSqlMapClientTemplate().update("HR_BSC_UTILBW_3G.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	public List<HrBscUtilbw3g> dataList(String sdate, String shour, String edate, String ehour, String bsc, int order, String column){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("p_sdate", sdate);
		map.put("p_shour", shour);
		map.put("p_edate", edate);
		map.put("p_ehour", ehour);
		map.put("p_bsc", bsc);
		
		map.put("p_column", column);
		map.put("p_order", order);
		map.put("p_DATA", null);
		return getSqlMapClientTemplate().queryForList("HR_BSC_UTILBW_3G.dataList", map); 
	}
}