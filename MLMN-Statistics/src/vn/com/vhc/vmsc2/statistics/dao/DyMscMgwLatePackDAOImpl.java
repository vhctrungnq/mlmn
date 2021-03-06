package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwLatePack;

public class DyMscMgwLatePackDAOImpl extends SqlMapClientDaoSupport implements DyMscMgwLatePackDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public DyMscMgwLatePackDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscMgwLatePack key = new DyMscMgwLatePack();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_MGW_LATE_PACKET.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public void insert(DyMscMgwLatePack record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_LATE_PACKET.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public void insertSelective(DyMscMgwLatePack record) {
        getSqlMapClientTemplate().insert("DY_MSC_MGW_LATE_PACKET.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public DyMscMgwLatePack selectByPrimaryKey(Date day, String mscid) {
        DyMscMgwLatePack key = new DyMscMgwLatePack();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscMgwLatePack record = (DyMscMgwLatePack) getSqlMapClientTemplate().queryForObject("DY_MSC_MGW_LATE_PACKET.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscMgwLatePack record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_LATE_PACKET.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_MGW_LATE_PACKET
     *
     * @ibatorgenerated Mon Nov 05 09:25:11 ICT 2012
     */
    public int updateByPrimaryKey(DyMscMgwLatePack record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_MGW_LATE_PACKET.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
    public List<DyMscMgwLatePack> filter(String mscid,String startDate,String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("mscid", mscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    return getSqlMapClientTemplate().queryForList("DY_MSC_MGW_LATE_PACKET.filter", map);
    }
}