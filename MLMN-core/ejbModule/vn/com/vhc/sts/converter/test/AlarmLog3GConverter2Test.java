package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.sts.converter.AlarmLog3GConverter2;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlarmLog3GConverter2Test {

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

		AlarmLog3GConverter2 mSGSNConverter = new AlarmLog3GConverter2();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	@Test
	public static void testConvertFile1() {
		String fileName = "StartAlarm-3G_BSG011E.txt";
		File file = new File("E:/Test/Output/" + fileName);
		String destinationPath = "E:/Test/Output/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");
		
		AlarmLog3GConverter2 mSGSNConverter = new AlarmLog3GConverter2();
		try {
			mSGSNConverter.convertFile2(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	@Test
	public static void testConvertFile2() {
		String fileName = "ClearingAlarm-3G_BSG011E.txt";
		File file = new File("E:/Test/Output/" + fileName);
		String destinationPath = "E:/Test/Output/";
		
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");
		
		AlarmLog3GConverter2 mSGSNConverter = new AlarmLog3GConverter2();
		try {
			mSGSNConverter.convertFile3(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	public static void main(String arg[]){
		testConvertFile();
		testConvertFile1();
		testConvertFile2();
	}
}
