package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyStpDestinationQos;

public class DyStpDestinationQosDAOImpl extends SqlMapClientDaoSupport implements DyStpDestinationQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public DyStpDestinationQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String stpid) {
        DyStpDestinationQos key = new DyStpDestinationQos();
        key.setDay(day);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("DY_STP_DESTINATION_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public void insert(DyStpDestinationQos record) {
        getSqlMapClientTemplate().insert("DY_STP_DESTINATION_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public void insertSelective(DyStpDestinationQos record) {
        getSqlMapClientTemplate().insert("DY_STP_DESTINATION_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public DyStpDestinationQos selectByPrimaryKey(Date day, String stpid) {
        DyStpDestinationQos key = new DyStpDestinationQos();
        key.setDay(day);
        key.setStpid(stpid);
        DyStpDestinationQos record = (DyStpDestinationQos) getSqlMapClientTemplate().queryForObject("DY_STP_DESTINATION_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyStpDestinationQos record) {
        int rows = getSqlMapClientTemplate().update("DY_STP_DESTINATION_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_DESTINATION_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int updateByPrimaryKey(DyStpDestinationQos record) {
        int rows = getSqlMapClientTemplate().update("DY_STP_DESTINATION_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}