package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmLog3GConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmLog3GConverterTest {

	@Test
	public static void testConvertFile() {
		String fileName = "OSS_RSGGV1E.txt";
		File file = new File("E:/alarmdata/" + fileName);
		String destinationPath = "E:/alarmdata/converter/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ",");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");
		
		Date sdate = new Date();
		
		AlarmLog3GConverter mSGSNConverter = new AlarmLog3GConverter();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (AL_ConvertException e) {
			e.printStackTrace();
		}
		
		Date edate = new Date(); 
		
		System.out.println("Convert is success !");
		System.out.println((edate.getTime()-sdate.getTime())/1000);
	}
	public static void main(String arg[]){
		testConvertFile();
	}
}
