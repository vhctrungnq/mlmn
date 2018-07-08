package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.DyStpOutOfBlueQos;

public class DyStpOutOfBlueQosDAOImpl extends SqlMapClientDaoSupport implements DyStpOutOfBlueQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public DyStpOutOfBlueQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int deleteByPrimaryKey(Date day, String stpid) {
        DyStpOutOfBlueQos key = new DyStpOutOfBlueQos();
        key.setDay(day);
        key.setStpid(stpid);
        int rows = getSqlMapClientTemplate().delete("DY_STP_OUTOFBLUE_QOS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public void insert(DyStpOutOfBlueQos record) {
        getSqlMapClientTemplate().insert("DY_STP_OUTOFBLUE_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public void insertSelective(DyStpOutOfBlueQos record) {
        getSqlMapClientTemplate().insert("DY_STP_OUTOFBLUE_QOS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public DyStpOutOfBlueQos selectByPrimaryKey(Date day, String stpid) {
        DyStpOutOfBlueQos key = new DyStpOutOfBlueQos();
        key.setDay(day);
        key.setStpid(stpid);
        DyStpOutOfBlueQos record = (DyStpOutOfBlueQos) getSqlMapClientTemplate().queryForObject("DY_STP_OUTOFBLUE_QOS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int updateByPrimaryKeySelective(DyStpOutOfBlueQos record) {
        int rows = getSqlMapClientTemplate().update("DY_STP_OUTOFBLUE_QOS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table DY_STP_OUTOFBLUE_QOS
     *
     * @ibatorgenerated Wed Oct 27 15:46:17 ICT 2010
     */
    public int updateByPrimaryKey(DyStpOutOfBlueQos record) {
        int rows = getSqlMapClientTemplate().update("DY_STP_OUTOFBLUE_QOS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
}