package vn.com.vhc.util;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * Remove space character
	 * @param msg
	 * @return
	 */
	public static String removeSpaceCharacter(String msg) {
		return msg.replaceAll("\\s+", " ");
	}
	
	/**
	 * Get value by field
	 * @param field
	 * @param str
	 * @param delim
	 * @return
	 */
	public static String getValueByField(String field, String str, String delim){
		Pattern pattern =  Pattern.compile(field +" .[^" + delim + "]{0,}");
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			String tmp = matcher.group();
			tmp = tmp.substring(tmp.indexOf(" ") + 1);
			if(tmp.indexOf(",") == 0) return "";
			if(tmp.indexOf(";") == (tmp.length()-1)) return tmp.substring(0,tmp.length()-1);
			return tmp; 
		}
		else return null;
	}
	
	public static String[] parseString(String value, char delim) {
		return parseString(value, String.valueOf(delim), true);
	}

	@SuppressWarnings("rawtypes")
	public static String[] parseString(String value, String sdelim, boolean trim) {
		if (value != null) {
			boolean skipNL = sdelim.equals("\r\n"); // special case

			/* parse */
			java.util.List<String> v1 = new Vector<String>();
			ListUtil.toList(new StringTokenizer(value, sdelim, true), v1);

			/* examine all tokens to make sure we include blank items */
			int dupDelim = 1; // assume we've started with a delimiter
			boolean consumeNextNL = false;
			java.util.List<String> v2 = new Vector<String>();
			for (Iterator i = v1.iterator(); i.hasNext();) {
				String s = (String) i.next();
				if ((s.length() == 1) && (sdelim.indexOf(s) >= 0)) {
					// found a delimiter
					if (skipNL) {
						char ch = s.charAt(0);
						if (consumeNextNL && (ch == '\n')) {
							consumeNextNL = false;
						} else {
							consumeNextNL = (ch == '\r');
							if (dupDelim > 0) {
								v2.add("");
							} // blank item
							dupDelim++;
						}
					} else {
						if (dupDelim > 0) {
							v2.add("");
						} // blank item
						dupDelim++;
					}
				} else {
					v2.add(trim ? s.trim() : s);
					dupDelim = 0;
					consumeNextNL = false;
				}
			}
			if (dupDelim > 0) {
				v2.add("");
			} // final blank item

			/* return parsed array */
			return v2.toArray(new String[v2.size()]);

		} else {
			/* nothing parsed */
			return new String[0];

		}
	}
	
	public static void main(String arg[]) {
		System.out.println(removeSpaceCharacter("dd    hi "));
		
		System.out.println(getValueByField("NotifId", "IntId  755188  NotifId  317431", " "));
	}
}
