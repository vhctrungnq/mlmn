package vn.com.vhc.vmsc2.statistics.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {
	
	public static boolean isValid(String pattern, String date) {
		DateFormat df = new SimpleDateFormat(pattern);

		try {
			df.parse(date);
			return true;
		} catch(Exception ex) {
			return false;
		}

	}
	 
	public static String DateToTicketId() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String ticketid = df.format(new Date());
		return ticketid;
	}
	
	public static Date addDate(Date date, int dayNum, int hourNum, int minuteNum, int secondNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayNum);
		
		cal.add(Calendar.HOUR_OF_DAY, hourNum);
		cal.add(Calendar.MINUTE, minuteNum);
		cal.add(Calendar.SECOND, secondNum);
		
		return cal.getTime();
	}
	
	public static int getThoiGianXL (int pos, String time) {
		int postC = time.indexOf(":");
		
		int hour = Integer.parseInt(time.substring(0, postC));
		int minu = Integer.parseInt(time.substring(postC + 1));
		
		if (pos == 0) {
			return hour;
		}
		return minu;
	}
	
	public static Date startMonth(Date curentDate) {
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(curentDate);
		  cal.set(Calendar.DAY_OF_MONTH,1);
		  return cal.getTime();
		 }
	
	public static void main(String arg[]) {
		int hour = getThoiGianXL(0, "22:33");
		int minu = getThoiGianXL(1, "22:33");
		
		System.out.println(hour + ":" + minu);
		
		System.out.println(addDate(new Date(), 1, 0 , 0, 0));
	}
	
	public static int getQuarterByMonth(int month){
		if(month == 1 || month == 2 || month == 3) return 1;
		else if(month == 4 || month == 5 || month == 6) return 2;
		else if(month == 7 || month == 8 || month == 9) return 3;
		else return 4;
	}
}
