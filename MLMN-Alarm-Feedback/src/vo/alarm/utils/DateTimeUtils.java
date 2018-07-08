package vo.alarm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class DateTimeUtils {
	public static Map<Date, Map<String, String>> rangeDate(Date startDate, String startHour, Date endDate, String endHour) {
		Map<Date, Map<String, String>> result = new LinkedHashMap<Date, Map<String, String>>();

		if (startDate.compareTo(endDate) == 0) {
			Map<String, String> hours = new HashMap<String, String>();
			hours.put("startHour", startHour);
			hours.put("endHour", endHour);

			result.put(startDate, hours);
		} else if (startDate.compareTo(endDate) < 0) {
			Map<String, String> hours = new HashMap<String, String>();
			hours.put("startHour", startHour);
			hours.put("endHour", "23");

			result.put(startDate, hours);

			DateTime dt = new DateTime(startDate);
			dt = dt.minusDays(-1);

			while (dt.toDate().compareTo(endDate) < 0) {
				Map<String, String> hourss = new HashMap<String, String>();
				hourss.put("startHour", "");
				hourss.put("endHour", "");

				result.put(dt.toDate(), hourss);
				dt = dt.minusDays(-1);
			}

			Map<String, String> hoursss = new HashMap<String, String>();
			hoursss.put("startHour", "0");
			hoursss.put("endHour", endHour);

			result.put(endDate, hoursss);
		}

		return result;
	}
	
	public static boolean isValid(String pattern, String date) {
		DateFormat df = new SimpleDateFormat(pattern);

		try {
			df.parse(date);
			return true;
		} catch(Exception ex) {
			return false;
		}

	}
}
