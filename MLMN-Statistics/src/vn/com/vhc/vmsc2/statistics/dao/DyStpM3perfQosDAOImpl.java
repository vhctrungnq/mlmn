package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyStpM3perfQos;

public class DyStpM3perfQosDAOImpl extends SqlMapClientDaoSupport implements DyStpM3perfQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public DyStpM3perfQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String stpid) {
        DyStpM3perfQos key = new DyStpM3perfQos();
        key.setDay(day);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("DY_STP_M3PERF_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public void insert(DyStpM3perfQos record) {
        getSqlMapClientTemplate().insert("DY_STP_M3PERF_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public void insertSelective(DyStpM3perfQos record) {
        getSqlMapClientTemplate().insert("DY_STP_M3PERF_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public DyStpM3perfQos selectByPrimaryKey(Date day, String stpid) {
        DyStpM3perfQos key = new DyStpM3perfQos();
        key.setDay(day);
        key.setStpid(stpid);
        DyStpM3perfQos record = (DyStpM3perfQos) getSqlMapClientTemplate().queryForObject("DY_STP_M3PERF_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyStpM3perfQos record) {
        int rows = getSqlMapClientTemplate().update("DY_STP_M3PERF_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int updateByPrimaryKey(DyStpM3perfQos record) {
        int rows = getSqlMapClientTemplate().update("DY_STP_M3PERF_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}