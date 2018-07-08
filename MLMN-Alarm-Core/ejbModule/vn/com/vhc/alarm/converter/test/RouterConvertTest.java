package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.RouterLogConvert;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class RouterConvertTest {
	@Test
	public static void testConvertFile() {
		String fileName = "Router_syslog.2013-03-14 07-28.1.txt";
		File file = new File("/home/thanhlv/oracle/u02/alarm/rawfile/download/IPBB/router/" + fileName);
		String destinationPath = "/home/thanhlv/oracle/u02/alarm/rawfile/converter/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		RouterLogConvert mSGSNConverter = new RouterLogConvert();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (AL_ConvertException e) {
			e.printStackTrace();
		}
		System.out.println("Convert is success !");
	}
	public static void main(String arg[]){
		testConvertFile();
	}
}
