package vn.com.vhc.vmsc2.statistics.web.utils.test;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;


public class DateTimeUtilsTest {
	@Test
	public void testRangeDate() {
		DateTime dt = new DateTime();
		Date endDate = dt.toDate();
		Date startDate = dt.minusDays(2).toDate();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, "1", endDate, "20");
		for(Date date:dates.keySet()){
			System.out.println(dates.get(date).get("startHour"));
			System.out.println(dates.get(date).get("endHour"));
		}
		
	}
}
