package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmActiveCoreConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmActiveCoreConvertest {
	@Test
	public static void testConvertFile() {
		String fileName = "ERICSSON-Active_ERICSSON_ACTIVE.MGHC01E-20130403.142004-20130403.152022.143.txt";
		File file = new File("/home/thanhlv/oracle/rawfile/selection/ericsson-active-msc/" + fileName);
		String destinationPath = "/home/thanhlv/oracle/rawfile/converter";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmActiveCoreConverter mSGSNConverter = new AlarmActiveCoreConverter();
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
