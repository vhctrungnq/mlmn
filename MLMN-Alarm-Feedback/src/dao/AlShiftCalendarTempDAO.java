package dao;

import java.util.List;

import vo.AlShiftCalendarTemp;

public interface AlShiftCalendarTempDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_CALENDAR_TEMP
     *
     * @ibatorgenerated Fri Apr 12 16:56:46 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_CALENDAR_TEMP
     *
     * @ibatorgenerated Fri Apr 12 16:56:46 ICT 2013
     */
    void insert(AlShiftCalendarTemp record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_CALENDAR_TEMP
     *
     * @ibatorgenerated Fri Apr 12 16:56:46 ICT 2013
     */
    void insertSelective(AlShiftCalendarTemp record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_CALENDAR_TEMP
     *
     * @ibatorgenerated Fri Apr 12 16:56:46 ICT 2013
     */
    AlShiftCalendarTemp selectByPrimaryKey(Integer id);
    public List<AlShiftCalendarTemp> getAll();

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_CALENDAR_TEMP
     *
     * @ibatorgenerated Fri Apr 12 16:56:46 ICT 2013
     */
    int updateByPrimaryKeySelective(AlShiftCalendarTemp record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_SHIFT_CALENDAR_TEMP
     *
     * @ibatorgenerated Fri Apr 12 16:56:46 ICT 2013
     */
    int updateByPrimaryKey(AlShiftCalendarTemp record);

}