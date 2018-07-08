package dao;

import java.util.List;

import vo.CoreLog;
import vo.SYS_PARAMETER;
import vo.alarm.utils.TimeLog;



public interface CoreLogDAO {

	List<SYS_PARAMETER> titleCoreLog(String TypeForm);

	List<CoreLog> filter(String startDate, String endDate, int startRecord,
			int endRecord, String column, int order);

	Integer countRow(String startDate, String endDate);

	
}