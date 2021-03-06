package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.WkStpUtilisationQos;

public class WkStpUtilisationQosDAOImpl extends SqlMapClientDaoSupport implements WkStpUtilisationQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public WkStpUtilisationQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int deleteByPrimaryKey(String stpid, Integer week, Integer year) {
        WkStpUtilisationQos key = new WkStpUtilisationQos();
        key.setStpid(stpid);
        key.setWeek(week);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("WK_STP_UTILISATION_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public void insert(WkStpUtilisationQos record) {
        getSqlMapClientTemplate().insert("WK_STP_UTILISATION_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public void insertSelective(WkStpUtilisationQos record) {
        getSqlMapClientTemplate().insert("WK_STP_UTILISATION_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public WkStpUtilisationQos selectByPrimaryKey(String stpid, Integer week, Integer year) {
        WkStpUtilisationQos key = new WkStpUtilisationQos();
        key.setStpid(stpid);
        key.setWeek(week);
        key.setYear(year);
        WkStpUtilisationQos record = (WkStpUtilisationQos) getSqlMapClientTemplate().queryForObject("WK_STP_UTILISATION_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int updateByPrimaryKeySelective(WkStpUtilisationQos record) {
        int rows = getSqlMapClientTemplate().update("WK_STP_UTILISATION_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table WK_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:45:27 ICT 2010
     */
    public int updateByPrimaryKey(WkStpUtilisationQos record) {
        int rows = getSqlMapClientTemplate().update("WK_STP_UTILISATION_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}