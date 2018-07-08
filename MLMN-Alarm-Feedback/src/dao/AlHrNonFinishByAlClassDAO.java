package dao;

import java.util.List;

import vo.AlHrNonFinishByAlClass;
import vo.SYS_PARAMETER;

public interface AlHrNonFinishByAlClassDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_NON_FINISH_BY_AL_CLASS
     *
     * @ibatorgenerated Sat Jan 12 09:57:35 ICT 2013
     */
    void insert(AlHrNonFinishByAlClass record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_HR_NON_FINISH_BY_AL_CLASS
     *
     * @ibatorgenerated Sat Jan 12 09:57:35 ICT 2013
     */
    void insertSelective(AlHrNonFinishByAlClass record);

	List<AlHrNonFinishByAlClass> getDistinctSystemHr(String date);

	List<AlHrNonFinishByAlClass> getDistinctAlarmClassHr(String date,
			String system);

	List<SYS_PARAMETER> titleFormHR();

	List<AlHrNonFinishByAlClass> getDistinctHour(String date, String system);

	List<AlHrNonFinishByAlClass> getBieuDo(String date, String hour,
			String system, String alarmClass);

	List<AlHrNonFinishByAlClass> getListCb(String date, String hour,
			String column, String order);

	List<AlHrNonFinishByAlClass> getDistinctMinutes(String date, String hour,
			String system);
}