package vn.com.vhc.vmsc2.statistics.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnStpM3perfQos;

public class MnStpM3perfQosDAOImpl extends SqlMapClientDaoSupport implements MnStpM3perfQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public MnStpM3perfQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public int deleteByPrimaryKey(Integer month, String stpid, Integer year) {
        MnStpM3perfQos key = new MnStpM3perfQos();
        key.setMonth(month);
        key.setStpid(stpid);
        key.setYear(year);
        int rows = getSqlMapClientTemplate().delete("MN_STP_M3PERF_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public void insert(MnStpM3perfQos record) {
        getSqlMapClientTemplate().insert("MN_STP_M3PERF_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public void insertSelective(MnStpM3perfQos record) {
        getSqlMapClientTemplate().insert("MN_STP_M3PERF_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public MnStpM3perfQos selectByPrimaryKey(Integer month, String stpid, Integer year) {
        MnStpM3perfQos key = new MnStpM3perfQos();
        key.setMonth(month);
        key.setStpid(stpid);
        key.setYear(year);
        MnStpM3perfQos record = (MnStpM3perfQos) getSqlMapClientTemplate().queryForObject("MN_STP_M3PERF_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public int updateByPrimaryKeySelective(MnStpM3perfQos record) {
        int rows = getSqlMapClientTemplate().update("MN_STP_M3PERF_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table MN_STP_M3PERF_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:43:43 ICT 2010
     */
    public int updateByPrimaryKey(MnStpM3perfQos record) {
        int rows = getSqlMapClientTemplate().update("MN_STP_M3PERF_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}