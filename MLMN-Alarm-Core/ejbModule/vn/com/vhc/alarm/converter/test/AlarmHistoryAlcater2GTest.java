package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmHistoryAlcater2GConvert;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmHistoryAlcater2GTest {
	@Test
	public static void testConvertFile() {
		String fileName = "20131010.2036.cal.csv";
		File file = new File("D:/alarmdata/download/alcatel/" + fileName);
		String destinationPath = "D:/alarmdata/converter/alcatel/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmHistoryAlcater2GConvert converter = new AlarmHistoryAlcater2GConvert();
		try {
			long startTime = System.currentTimeMillis();
			converter.convertFile(file, destinationPath, params);
			long endTime = System.currentTimeMillis();
			System.out.println("Thoi gian thuc hien : "+(endTime - startTime)+" millis");
			System.out.println("Convert is success !");
		} catch (AL_ConvertException e) {
			e.printStackTrace();
		}
	}
	public static void main(String arg[]){
		testConvertFile();
	}
}
