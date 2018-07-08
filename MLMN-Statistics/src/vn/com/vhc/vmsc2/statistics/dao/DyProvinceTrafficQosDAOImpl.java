package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyProvinceTrafficQos;

public class DyProvinceTrafficQosDAOImpl extends SqlMapClientDaoSupport implements DyProvinceTrafficQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public DyProvinceTrafficQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public int deleteByPrimaryKey(Date day, String province, String region) {
        DyProvinceTrafficQos key = new DyProvinceTrafficQos();
        key.setDay(day);
        key.setProvince(province);
        key.setRegion(region);
        int rows = getSqlMapClientTemplate().delete("DY_PROVINCE_TRAFFIC_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public void insert(DyProvinceTrafficQos record) {
        getSqlMapClientTemplate().insert("DY_PROVINCE_TRAFFIC_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public void insertSelective(DyProvinceTrafficQos record) {
        getSqlMapClientTemplate().insert("DY_PROVINCE_TRAFFIC_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public DyProvinceTrafficQos selectByPrimaryKey(Date day, String province, String region) {
        DyProvinceTrafficQos key = new DyProvinceTrafficQos();
        key.setDay(day);
        key.setProvince(province);
        key.setRegion(region);
        DyProvinceTrafficQos record = (DyProvinceTrafficQos) getSqlMapClientTemplate().queryForObject("DY_PROVINCE_TRAFFIC_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public int updateByPrimaryKeySelective(DyProvinceTrafficQos record) {
        int rows = getSqlMapClientTemplate().update("DY_PROVINCE_TRAFFIC_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_PROVINCE_TRAFFIC_QOS
     *
     * @ibatorgenerated Thu Feb 24 13:39:31 ICT 2011
     */
    public int updateByPrimaryKey(DyProvinceTrafficQos record) {
        int rows = getSqlMapClientTemplate().update("DY_PROVINCE_TRAFFIC_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyProvinceTrafficQos> filter(String startDate,String endDate, String province, String region){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("province", province);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
		map.put("region", region);
    return getSqlMapClientTemplate().queryForList("DY_PROVINCE_TRAFFIC_QOS.filter2", map);
    }
}