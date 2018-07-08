package dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.RAlarmLossPowerLog;

public class RAlarmLossPowerLogDAOImpl extends SqlMapClientDaoSupport implements RAlarmLossPowerLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public RAlarmLossPowerLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public int deleteByPrimaryKey(Integer id) {
        RAlarmLossPowerLog key = new RAlarmLossPowerLog();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("R_ALARM_LOSS_POWER_LOG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public void insert(RAlarmLossPowerLog record) {
        getSqlMapClientTemplate().insert("R_ALARM_LOSS_POWER_LOG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public void insertSelective(RAlarmLossPowerLog record) {
        getSqlMapClientTemplate().insert("R_ALARM_LOSS_POWER_LOG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public RAlarmLossPowerLog selectByPrimaryKey(Integer id) {
        RAlarmLossPowerLog key = new RAlarmLossPowerLog();
        key.setId(id);
        RAlarmLossPowerLog record = (RAlarmLossPowerLog) getSqlMapClientTemplate().queryForObject("R_ALARM_LOSS_POWER_LOG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public int updateByPrimaryKeySelective(RAlarmLossPowerLog record) {
        int rows = getSqlMapClientTemplate().update("R_ALARM_LOSS_POWER_LOG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    public int updateByPrimaryKey(RAlarmLossPowerLog record) {
        int rows = getSqlMapClientTemplate().update("R_ALARM_LOSS_POWER_LOG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	
}