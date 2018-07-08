package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrStpAssonciationQos;

public class HrStpAssonciationQosDAOImpl extends SqlMapClientDaoSupport implements HrStpAssonciationQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpAssonciationQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpAssonciationQos key = new HrStpAssonciationQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("HR_STP_ASSOCIATION_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insert(HrStpAssonciationQos record) {
        getSqlMapClientTemplate().insert("HR_STP_ASSOCIATION_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insertSelective(HrStpAssonciationQos record) {
        getSqlMapClientTemplate().insert("HR_STP_ASSOCIATION_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpAssonciationQos selectByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpAssonciationQos key = new HrStpAssonciationQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        HrStpAssonciationQos record = (HrStpAssonciationQos) getSqlMapClientTemplate().queryForObject("HR_STP_ASSOCIATION_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrStpAssonciationQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_ASSOCIATION_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_ASSOCIATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKey(HrStpAssonciationQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_ASSOCIATION_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}