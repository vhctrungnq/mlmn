package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrStpM3dataQos;

public class HrStpM3dataQosDAOImpl extends SqlMapClientDaoSupport implements HrStpM3dataQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpM3dataQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpM3dataQos key = new HrStpM3dataQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("HR_STP_M3DATA_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insert(HrStpM3dataQos record) {
        getSqlMapClientTemplate().insert("HR_STP_M3DATA_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public void insertSelective(HrStpM3dataQos record) {
        getSqlMapClientTemplate().insert("HR_STP_M3DATA_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public HrStpM3dataQos selectByPrimaryKey(Date day, Integer hour, String stpid) {
        HrStpM3dataQos key = new HrStpM3dataQos();
        key.setDay(day);
        key.setHour(hour);
        key.setStpid(stpid);
        HrStpM3dataQos record = (HrStpM3dataQos) getSqlMapClientTemplate().queryForObject("HR_STP_M3DATA_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKeySelective(HrStpM3dataQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_M3DATA_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table HR_STP_M3DATA_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:47:00 ICT 2010
     */
    public int updateByPrimaryKey(HrStpM3dataQos record) {
        int rows = getSqlMapClientTemplate().update("HR_STP_M3DATA_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}