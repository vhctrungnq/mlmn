package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.DyRegionQos;

public class DyRegionQosDAOImpl extends SqlMapClientDaoSupport implements DyRegionQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public DyRegionQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String region) {
        DyRegionQos key = new DyRegionQos();
        key.setDay(day);
        key.setRegion(region);
        int rows = getSqlMapClientTemplate().delete("DY_REGION_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public void insert(DyRegionQos record) {
        getSqlMapClientTemplate().insert("DY_REGION_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public void insertSelective(DyRegionQos record) {
        getSqlMapClientTemplate().insert("DY_REGION_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public DyRegionQos selectByPrimaryKey(Date day, String region) {
        DyRegionQos key = new DyRegionQos();
        key.setDay(day);
        key.setRegion(region);
        DyRegionQos record = (DyRegionQos) getSqlMapClientTemplate().queryForObject("DY_REGION_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyRegionQos record) {
        int rows = getSqlMapClientTemplate().update("DY_REGION_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_REGION_QOS
     *
     * @ibatorgenerated Fri Nov 16 16:36:41 ICT 2012
     */
    public int updateByPrimaryKey(DyRegionQos record) {
        int rows = getSqlMapClientTemplate().update("DY_REGION_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyRegionQos> filter(String region,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("region", region);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_REGION_QOS.filter", map);
    }
}