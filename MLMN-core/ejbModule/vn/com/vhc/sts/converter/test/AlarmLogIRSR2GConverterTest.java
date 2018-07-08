package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;


import org.junit.Test;

import vn.com.vhc.sts.converter.AlarmLogIRSR2GConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlarmLogIRSR2GConverterTest {

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

		AlarmLogIRSR2GConverter mSGSNConverter = new AlarmLogIRSR2GConverter();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}
		System.out.println("Convert is success !");
	}
	public static void main(String args[])
	{
		testConvertFile();
	}
}
