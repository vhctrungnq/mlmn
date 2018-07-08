package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyMscRabassig;

public class DyMscRabassigDAOImpl extends SqlMapClientDaoSupport implements DyMscRabassigDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public DyMscRabassigDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscRabassig key = new DyMscRabassig();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_RAB_ASSIG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public void insert(DyMscRabassig record) {
        getSqlMapClientTemplate().insert("DY_MSC_RAB_ASSIG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public void insertSelective(DyMscRabassig record) {
        getSqlMapClientTemplate().insert("DY_MSC_RAB_ASSIG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public DyMscRabassig selectByPrimaryKey(Date day, String mscid) {
        DyMscRabassig key = new DyMscRabassig();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscRabassig record = (DyMscRabassig) getSqlMapClientTemplate().queryForObject("DY_MSC_RAB_ASSIG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscRabassig record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_RAB_ASSIG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_RAB_ASSIG
     *
     * @ibatorgenerated Fri Oct 12 09:47:20 ICT 2012
     */
    public int updateByPrimaryKey(DyMscRabassig record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_RAB_ASSIG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyMscRabassig> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_RAB_ASSIG.filter", map);
    }
}