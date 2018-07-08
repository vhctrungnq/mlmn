package vn.com.vhc.vmsc2.statistics.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkBscTrapeventQos;

public class WkBscTrapeventQosDAOImpl extends SqlMapClientDaoSupport implements WkBscTrapeventQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public WkBscTrapeventQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public int deleteByPrimaryKey(String bscid, String type, Integer week, Integer year) {
        WkBscTrapeventQos key = new WkBscTrapeventQos();
        key.setBscid(bscid);
        key.setType(type);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_BSC_TRAPEVENT_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public void insert(WkBscTrapeventQos record) {
        getSqlMapClientTemplate().insert("WK_BSC_TRAPEVENT_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public void insertSelective(WkBscTrapeventQos record) {
        getSqlMapClientTemplate().insert("WK_BSC_TRAPEVENT_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public WkBscTrapeventQos selectByPrimaryKey(String bscid, String type, Integer week, Integer year) {
        WkBscTrapeventQos key = new WkBscTrapeventQos();
        key.setBscid(bscid);
        key.setType(type);
        key.setWeek(week);
        key.setYear(year);
        WkBscTrapeventQos record = (WkBscTrapeventQos) getSqlMapClientTemplate().queryForObject("WK_BSC_TRAPEVENT_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkBscTrapeventQos record) {
        int rows = getSqlMapClientTemplate().update("WK_BSC_TRAPEVENT_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_BSC_TRAPEVENT_QOS
     *
     * @ibatorgenerated Mon Dec 20 17:05:37 ICT 2010
     */
    public int updateByPrimaryKey(WkBscTrapeventQos record) {
        int rows = getSqlMapClientTemplate().update("WK_BSC_TRAPEVENT_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	public List<WkBscTrapeventQos> select(String bscid, Integer week, Integer year) {
		WkBscTrapeventQos key = new WkBscTrapeventQos();
		key.setWeek(week);
		key.setYear(year);
		key.setBscid(bscid);
		return getSqlMapClientTemplate().queryForList("WK_BSC_TRAPEVENT_QOS.select", key);
	}
}