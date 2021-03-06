package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkStpM3dataQos;

public class WkStpM3dataQosDAOImpl extends SqlMapClientDaoSupport implements WkStpM3dataQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public WkStpM3dataQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int deleteByPrimaryKey(String stpid, Integer week, Integer year) {
        WkStpM3dataQos key = new WkStpM3dataQos();
        key.setStpid(stpid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_STP_M3DATA_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public void insert(WkStpM3dataQos record) {
        getSqlMapClientTemplate().insert("WK_STP_M3DATA_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public void insertSelective(WkStpM3dataQos record) {
        getSqlMapClientTemplate().insert("WK_STP_M3DATA_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public WkStpM3dataQos selectByPrimaryKey(String stpid, Integer week, Integer year) {
        WkStpM3dataQos key = new WkStpM3dataQos();
        key.setStpid(stpid);
        key.setWeek(week);
        key.setYear(year);
        WkStpM3dataQos record = (WkStpM3dataQos) getSqlMapClientTemplate().queryForObject("WK_STP_M3DATA_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkStpM3dataQos record) {
        int rows = getSqlMapClientTemplate().update("WK_STP_M3DATA_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int updateByPrimaryKey(WkStpM3dataQos record) {
        int rows = getSqlMapClientTemplate().update("WK_STP_M3DATA_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}