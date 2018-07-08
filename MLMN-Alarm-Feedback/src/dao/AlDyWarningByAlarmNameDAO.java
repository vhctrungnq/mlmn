package dao;

import java.util.List;

import vo.AlDyWarningByAlarmName;

public interface AlDyWarningByAlarmNameDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Sun Dec 23 22:59:02 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Sun Dec 23 22:59:02 ICT 2012
     */
    void insert(AlDyWarningByAlarmName record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Sun Dec 23 22:59:02 ICT 2012
     */
    void insertSelective(AlDyWarningByAlarmName record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Sun Dec 23 22:59:02 ICT 2012
     */
    AlDyWarningByAlarmName selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Sun Dec 23 22:59:02 ICT 2012
     */
    int updateByPrimaryKeySelective(AlDyWarningByAlarmName record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Sun Dec 23 22:59:02 ICT 2012
     */
    int updateByPrimaryKey(AlDyWarningByAlarmName record);

	List<AlDyWarningByAlarmName> soLuongCBNhieuNhat(String system,
			String date, String startDate, String endDate);

	List<AlDyWarningByAlarmName> distinctSystem(String date, String startDate, String endDate);

	List<AlDyWarningByAlarmName> getDyWarningAnList(String date,
			String startDate, String endDate, String column, String order);

}