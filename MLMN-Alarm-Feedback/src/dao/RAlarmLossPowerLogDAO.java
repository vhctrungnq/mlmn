package dao;

import vo.RAlarmLossPowerLog;

public interface RAlarmLossPowerLogDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    void insert(RAlarmLossPowerLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    void insertSelective(RAlarmLossPowerLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    RAlarmLossPowerLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    int updateByPrimaryKeySelective(RAlarmLossPowerLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_LOSS_POWER_LOG
     *
     * @ibatorgenerated Tue Jan 14 11:41:23 ICT 2014
     */
    int updateByPrimaryKey(RAlarmLossPowerLog record);


}