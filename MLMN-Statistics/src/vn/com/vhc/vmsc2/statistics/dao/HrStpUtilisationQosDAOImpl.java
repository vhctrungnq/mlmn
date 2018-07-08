package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrStpUtilisationQos;

public class HrStpUtilisationQosDAOImpl extends SqlMapClientDaoSupport implements HrStpUtilisationQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpUtilisationQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpUtilisationQos key = new HrStpUtilisationQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("HR_STP_UTILISATION_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insert(HrStpUtilisationQos record) {
        getSqlMapClientTemplate().insert("HR_STP_UTILISATION_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insertSelective(HrStpUtilisationQos record) {
        getSqlMapClientTemplate().insert("HR_STP_UTILISATION_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpUtilisationQos selectByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpUtilisationQos key = new HrStpUtilisationQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        HrStpUtilisationQos record = (HrStpUtilisationQos) getSqlMapClientTemplate().queryForObject("HR_STP_UTILISATION_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrStpUtilisationQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_UTILISATION_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_UTILISATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKey(HrStpUtilisationQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_UTILISATION_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}