package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmCurrentlySGSNConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class SGSNConvertTest {
	@Test
	public static void testConvertFile() {
		String fileName = "ACTIVE.SGDNI1H.ZAHP-20131022.095624-20131022.110353.1.txt";
		File file = new File("D:/alarmdata/download/nokia/" + fileName);
		String destinationPath = "D:/alarmdata/converter/sgsn/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmCurrentlySGSNConverter mSGSNConverter = new AlarmCurrentlySGSNConverter();
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
