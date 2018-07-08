package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.converter.AlarmCurrentlyBSCConverter;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmCurreBSCConverTest {
	@Test
	public static void testConvertFile() {
		String fileName = "NOKIA_HISTORY.BSGHM1N-20130325.000000-20130325.150414.txt";
		File file = new File("/home/thanhlv/oracle/rawfile/download/NSN-2G/" + fileName);
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
