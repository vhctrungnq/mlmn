package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrStpM3perfQos;

public class HrStpM3perfQosDAOImpl extends SqlMapClientDaoSupport implements HrStpM3perfQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpM3perfQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpM3perfQos key = new HrStpM3perfQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("HR_STP_M3PERF_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insert(HrStpM3perfQos record) {
        getSqlMapClientTemplate().insert("HR_STP_M3PERF_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insertSelective(HrStpM3perfQos record) {
        getSqlMapClientTemplate().insert("HR_STP_M3PERF_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpM3perfQos selectByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpM3perfQos key = new HrStpM3perfQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        HrStpM3perfQos record = (HrStpM3perfQos) getSqlMapClientTemplate().queryForObject("HR_STP_M3PERF_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrStpM3perfQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_M3PERF_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKey(HrStpM3perfQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_M3PERF_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}