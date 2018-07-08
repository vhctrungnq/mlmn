package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.sts.converter.AlarmLog3GConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlarmLog3GConverterTest {

	@Test
	public static void testConvertFile() {
		String fileName = "BSG011E.txt";
		File file = new File("E:/Test/" + fileName);
		String destinationPath = "E:/Test/Output/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");
		
		AlarmLog3GConverter mSGSNConverter = new AlarmLog3GConverter();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	@Test
	public static void testConvertFile1() {
		String fileName = "ClearingAlarmOF-3G_BSG011E.txt";
		File file = new File("E:/Test/Output/" + fileName);
		String destinationPath = "E:/Test/Output/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");
		
		AlarmLog3GConverter mSGSNConverter = new AlarmLog3GConverter();
		try {
			mSGSNConverter.convertFile2(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	public static void main(String arg[]){
		testConvertFile();
		testConvertFile1();
	}
}
