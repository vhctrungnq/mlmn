package inventory.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IN_TestUtil {

	public IN_TestUtil() {
	}

	public static void main(String[] args) throws Exception {
		String input = "                      298721656 213366580  69936096  76% /";
		String regex = ".*?\\s+([0-9]+)\\s+([0-9]+)\\s+([0-9]+).+%.+";
		// String filePath = "D:/temp/fileout.xls";
		searchString(regex, input);
		// searchString(regex, input);

		// System.out.println(parseDate("20090923dsf_200909231000",
		// "yyyyMMddHHmm"));
		SimpleDateFormat format = new SimpleDateFormat("DDD");
		Date d = format.parse("269");
		System.out.println(d);
	}

	public static void checkPattern(String pattern, String fileName) {
		Pattern objPattern = Pattern.compile(pattern);
		Matcher m = objPattern.matcher(fileName);
		if (m.matches()) {
			System.out.println("Group count: " + m.groupCount());
			for (int i = 1; i <= m.groupCount(); i++) {
				System.out.println("Group " + i + ": " + m.group(i));
			}
		} else {
			System.out.println("Not matches");
		}
	}

	public static void searchString(String pattern, String input) {
		Pattern objPattern = Pattern.compile(pattern);
		Matcher m = objPattern.matcher(input);
		boolean found = false;
		while (m.find()) {
			System.out.println("Found '" + m.group(3) + "' from index '"
					+ m.start() + "' to index '" + m.end() + "'");
			found = true;
		}
		if (!found) {
			System.out.println("Not matches");
		}
	}

	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date parseDate(String date, String pattern)
			throws ParseException {
		return new SimpleDateFormat(pattern).parse(date);
	}
}
