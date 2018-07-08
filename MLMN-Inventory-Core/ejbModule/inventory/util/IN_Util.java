package inventory.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Vector;


public class IN_Util {

    public static int getCurrentYear() {
        String year = IN_Util.getTime(new Date(), "yyyy");
        return Integer.parseInt(year);
    }

    /**
     * @return
     */
    public static String getCurrentDate() {
        return IN_Util.getTime(new Date(), IN_Setting.DATE_FORMAT);
    }

    /**
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        return IN_Util.getTime(date, IN_Setting.DATE_FORMAT);
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String getDate(Date date, String format) {
        return IN_Util.getTime(date, format);
    }

    /**
     * @return
     */
    public static String getCurrentTime() {
        return IN_Util.getTime(new Date());
    }

    /**
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        return IN_Util.getTime(date, IN_Setting.TIME_FORMAT);
    }

    public static String getTime(long minisecond, String format) {
        return IN_Util.getTime(new Date(minisecond), format);
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String getTime(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getTime(String time, String currentFormat,
                                 String newFormat) {
        SimpleDateFormat format = new SimpleDateFormat(currentFormat);
        Date date;
        try {
            date = format.parse(time);
            format.applyPattern(newFormat);
            return format.format(date);
        } catch (ParseException e) {
            return time;
        }
    }

    public static Date getDate(String sdate, String format) {
        Date date = null;
        SimpleDateFormat sdFormat = new SimpleDateFormat(format);
        try {
            date = sdFormat.parse(sdate);
        } catch (ParseException e) {
        }
        return date;
    }

    public static long getTimeMinisecond(String time, String format) {
        long minisecond = 0;
        Date date = IN_Util.getDate(time, format);
        if (date != null) {
            minisecond = date.getTime();
        }
        return minisecond;
    }

    /**
     * @param miniseconds
     * @return
     */
    public static int getTimeDuration(long miniseconds) {
        int duration = 0;
        if (miniseconds > 0) {
            long miniSystem = System.currentTimeMillis();
            miniSystem = miniSystem - miniseconds;
            duration = Integer.parseInt(String.valueOf(miniSystem));
        }
        return duration;
    }

    /**
     * @param items
     */
    public static Vector<String> toVector(String[] items) {
        Vector<String> vct = new Vector<String>();
        if (items != null && items.length > 0) {
            for (String e : items) {
                vct.add(e);
            }
        }
        return vct;
    }
}
