package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmHistoryNokia2GConvert;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmLogNokiaConvertTest {
	@Test
	public static void testConvertFile() {
		String fileName = "NSN-2G_NSN-2G_NOKIA_HISTORY.BSGGV1N.ZAHP-20130328.161732-20130328.174952.txt";
		File file = new File("/home/thanhlv/oracle/rawfile/download/" + fileName);
		String destinationPath = "/home/thanhlv/oracle/rawfile/convert/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmHistoryNokia2GConvert mSGSNConverter = new AlarmHistoryNokia2GConvert();
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
