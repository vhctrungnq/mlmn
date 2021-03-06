package dao;

import java.util.List;

import vo.RalarmCsiLog;
import vo.alarm.utils.RalarmCsiLogFilter;

public interface RalarmCsiLogDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_CSI_LOG
     *
     * @ibatorgenerated Wed Dec 25 16:32:18 ICT 2013
     */
    void insert(RalarmCsiLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_CSI_LOG
     *
     * @ibatorgenerated Wed Dec 25 16:32:18 ICT 2013
     */
    void insertSelective(RalarmCsiLog record);
    
    List<RalarmCsiLog> filter(RalarmCsiLogFilter filter, String column, int order);
}