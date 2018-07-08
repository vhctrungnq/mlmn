package dao;

import java.util.List;

import vo.VAlShiftCalendar;
import vo.VAlShiftFilter;

public interface VAlShiftCalendarDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_SHIFT_CALENDAR
     *
     * @ibatorgenerated Wed Apr 10 14:05:55 ICT 2013
     */
    void insert(VAlShiftCalendar record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_SHIFT_CALENDAR
     *
     * @ibatorgenerated Wed Apr 10 14:05:55 ICT 2013
     */
    void insertSelective(VAlShiftCalendar record);
    
    List<VAlShiftCalendar> getList(VAlShiftFilter filter,String column, int order);
}