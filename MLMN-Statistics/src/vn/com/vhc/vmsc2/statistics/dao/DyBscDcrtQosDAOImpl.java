package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyBscDcrtQos;

public class DyBscDcrtQosDAOImpl extends SqlMapClientDaoSupport implements DyBscDcrtQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public DyBscDcrtQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, Date day) {
        DyBscDcrtQos key = new DyBscDcrtQos();
        key.setBscid(bscid);
        key.setDay(day);
        int rows = getSqlMapClientTemplate().delete("DY_BSC_DCRT_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public void insert(DyBscDcrtQos record) {
        getSqlMapClientTemplate().insert("DY_BSC_DCRT_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public void insertSelective(DyBscDcrtQos record) {
        getSqlMapClientTemplate().insert("DY_BSC_DCRT_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public DyBscDcrtQos selectByPrimaryKey(String bscid, Date day) {
        DyBscDcrtQos key = new DyBscDcrtQos();
        key.setBscid(bscid);
        key.setDay(day);
        DyBscDcrtQos record = (DyBscDcrtQos) getSqlMapClientTemplate().queryForObject("DY_BSC_DCRT_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyBscDcrtQos record) {
        int rows = getSqlMapClientTemplate().update("DY_BSC_DCRT_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_BSC_DCRT_QOS
     *
     * @ibatorgenerated Tue Aug 24 16:45:55 ICT 2010
     */
    public int updateByPrimaryKey(DyBscDcrtQos record) {
        int rows = getSqlMapClientTemplate().update("DY_BSC_DCRT_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyBscDcrtQos> filter(String startDate,String endDate, String bscid, String region){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("bscid", bscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
		map.put("region", region);
    return getSqlMapClientTemplate().queryForList("DY_BSC_DCRT_QOS.filter2", map);
    }
}