package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmLogCoreConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmLogCoreConverterTest {
	@Test
	public static void testConvertFile() {
		String fileName = "nocmb_MGHN01E.TXT";
		File file = new File("E:\\alarmdata\\" + fileName);
		String destinationPath = "E:\\alarmdata\\converter\\";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ",");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmLogCoreConverter mSGSNConverter = new AlarmLogCoreConverter();
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
