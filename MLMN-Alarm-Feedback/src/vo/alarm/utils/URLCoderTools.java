package vo.alarm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

public class URLCoderTools {
	private static final String enc = "UTF-8";
	private static Logger logger = Logger.getLogger(URLCoderTools.class.getName());
	
	public static String decode(String str){
		try {
			return URLDecoder.decode(str, enc);
		} catch (UnsupportedEncodingException e) {
			logger.warn("Error decode: " + e.toString());
			return "";
		}
	}
}
