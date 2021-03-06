package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyMscChaAssig;

public class DyMscChaAssigDAOImpl extends SqlMapClientDaoSupport implements DyMscChaAssigDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public DyMscChaAssigDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public int deleteByPrimaryKey(Date day, String mscid) {
        DyMscChaAssig key = new DyMscChaAssig();
        key.setDay(day);
        key.setMscid(mscid);
        int rows = getSqlMapClientTemplate().delete("DY_MSC_CHA_ASSIG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public void insert(DyMscChaAssig record) {
        getSqlMapClientTemplate().insert("DY_MSC_CHA_ASSIG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public void insertSelective(DyMscChaAssig record) {
        getSqlMapClientTemplate().insert("DY_MSC_CHA_ASSIG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public DyMscChaAssig selectByPrimaryKey(Date day, String mscid) {
        DyMscChaAssig key = new DyMscChaAssig();
        key.setDay(day);
        key.setMscid(mscid);
        DyMscChaAssig record = (DyMscChaAssig) getSqlMapClientTemplate().queryForObject("DY_MSC_CHA_ASSIG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public int updateByPrimaryKeySelective(DyMscChaAssig record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_CHA_ASSIG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_MSC_CHA_ASSIG
     *
     * @ibatorgenerated Mon Oct 15 11:30:56 ICT 2012
     */
    public int updateByPrimaryKey(DyMscChaAssig record) {
        int rows = getSqlMapClientTemplate().update("DY_MSC_CHA_ASSIG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
   	public List<DyMscChaAssig> filter(String startDate, String endDate, String mscid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startDate", startDate);
   		map.put("endDate", endDate);
   		map.put("mscid", mscid);
   		return getSqlMapClientTemplate().queryForList("DY_MSC_CHA_ASSIG.filter", map);
   	}
}