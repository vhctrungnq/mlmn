package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmCurrentlyRNCConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmCurreRNCConverTest {
	@Test
	public static void testConvertFile() {
		String fileName = "NSN-Active_ACTIVE.RSGHM1N.ZAAP-20130402.132822-20130402.143349.426.txt";
		File file = new File("/home/thanhlv/oracle/rawfile/download/" + fileName);
		String destinationPath = "/home/thanhlv/oracle/rawfile/convert/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmCurrentlyRNCConverter mSGSNConverter = new AlarmCurrentlyRNCConverter();
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
