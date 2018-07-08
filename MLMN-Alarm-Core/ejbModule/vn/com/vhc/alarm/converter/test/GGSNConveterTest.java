package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmHistoryGGSNConvert;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class GGSNConveterTest {
	@Test
	public static void testConvertFile() {
		String fileName = "GGSN-2_HISTORY_GGSN_SSH.FNGHCM_1N_20130322.195634.txt";
		File file = new File("/home/thanhlv/oracle/u02/alarm/rawfile/download/" + fileName);
		String destinationPath = "/home/thanhlv/oracle/u02/alarm/rawfile/converter/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmHistoryGGSNConvert mSGSNConverter = new AlarmHistoryGGSNConvert();
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
