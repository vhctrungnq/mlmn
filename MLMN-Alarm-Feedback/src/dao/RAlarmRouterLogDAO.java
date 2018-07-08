package dao;

import java.util.List;

import vo.RAlarmRouterLog;
import vo.alarm.utils.RAlarmRouterLogFilter;

public interface RAlarmRouterLogDAO {
    
    List<RAlarmRouterLog> filter(RAlarmRouterLogFilter filter);
}