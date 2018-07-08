package vo.alarm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
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
	
	public static boolean isValidNumber(String number) {
		
		try {
			Integer.parseInt(number);
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
	public static Date minusMonth(Date curentDate, int duration) {
		Date date = new DateTime().minusMonths(duration).toDate();
		  return date;
		 }
	public static Date minusWeek(Date curentDate, int duration) {
		Date date = new DateTime().minusWeeks(duration).toDate();
		  return date;
		 }
	public static Date minusDay(Date curentDate, int duration) {
		Date date = new DateTime().minusDays(duration).toDate();
		  return date;
		 }
	public static boolean  isValidDate(String date){  
        boolean isValid = false;  
        String expression = "^[0-1][1-2][- / ]?(0[1-9]|[12][0-9]|3[01])[- /]?(18|19|20|21)\\d{2}$";  
        /* 
        * ^[0-1][1-2] : The month starts with a 0 and a digit between 1 and 2 
        * [- / ]?: Followed by  an optional "-" or "/". 
        * (0[1-9]|[12][0-9]|3[01]) : The day part must be either between 01-09, or 10-29 or 30-31. 
        * [- / ]?: Day part will be followed by  an optional "-" or "/". 
        * (18|19|20|21)\\d{2}$ : Year begins with either 18, 19, 20 or 21 and ends with two digits. 
        */  
        CharSequence inputStr = date;  
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(inputStr);  
        if(matcher.matches()){  
            isValid = true;  
        }  
        return isValid;  
    }  
	public static Date convertWeekToFirstDate( int week, int year ) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set( Calendar.YEAR, year );
		cal.set( Calendar.HOUR, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.WEEK_OF_YEAR, week );
		cal.set( Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
		return( cal.getTime() );
		} 
	public static Date convertWeekToLastDate( int week, int year ) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.set( Calendar.YEAR, year );
		cal.set( Calendar.HOUR, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.WEEK_OF_YEAR, week );
		cal.set( Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
		cal.add(Calendar.DATE,6);
		return( cal.getTime() );
		} 
	
	public static void main(String arg[]) {
		/*int hour = getThoiGianXL(0, "22:33");
		int minu = getThoiGianXL(1, "22:33");
		
		System.out.println(hour + ":" + minu);
		
		System.out.println(addDate(new Date(), 1, 0 , 0, 0));
		*/
		
		System.out.println(convertWeekToFirstDate(2,2018));
		System.out.println(convertWeekToLastDate(2,2018));
		
	}
	
	
}
