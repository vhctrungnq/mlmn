package dao;

import java.util.List;

import vo.AlDyWarningByAlarmName;
import vo.AlHrWarningByAlarmName;
import vo.SYS_PARAMETER;

public interface AlHrWarningByAlarmNameDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Wed Dec 26 09:57:02 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Wed Dec 26 09:57:02 ICT 2012
     */
    void insert(AlHrWarningByAlarmName record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Wed Dec 26 09:57:02 ICT 2012
     */
    void insertSelective(AlHrWarningByAlarmName record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Wed Dec 26 09:57:02 ICT 2012
     */
    AlHrWarningByAlarmName selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Wed Dec 26 09:57:02 ICT 2012
     */
    int updateByPrimaryKeySelective(AlHrWarningByAlarmName record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_WARNING_BY_ALARM_NAME
     *
     * @ibatorgenerated Wed Dec 26 09:57:02 ICT 2012
     */
    int updateByPrimaryKey(AlHrWarningByAlarmName record);

	List<AlDyWarningByAlarmName> distinctSystem(String date, String hour);

	List<AlDyWarningByAlarmName> soLuongCBNhieuNhat(String system, String date,
			String hour);

	List<SYS_PARAMETER> titleFormHrAlamName();

	List<AlHrWarningByAlarmName> getHrWarningAnList(String date, String hour,
			String column, String order);
}