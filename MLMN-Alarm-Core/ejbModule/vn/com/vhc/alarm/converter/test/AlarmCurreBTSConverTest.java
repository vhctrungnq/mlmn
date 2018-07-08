package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmCurrentlyBSCConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmCurreBTSConverTest {
	@Test
	public static void testConvertFile() {
		String fileName = "NSN-Active_ACTIVE.BSG121N.ZAHO-20130402.081805-20130402.130542.0.txt";
		File file = new File("/home/thanhlv/oracle/rawfile/selection/nokia-active-bsc/" + fileName);
		String destinationPath = "/home/thanhlv/oracle/rawfile/convert/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AlarmCurrentlyBSCConverter mSGSNConverter = new AlarmCurrentlyBSCConverter();
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
