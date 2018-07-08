package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;


import org.junit.Test;

import vn.com.vhc.sts.converter.AlarmLogIRSR3GConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlarmLogIRSR3GConverterTest {

	@Test
	public static void testConvertFile() {
		String fileName = "logRBDTM1E.txt1707111035.txt";
		File file = new File("E:/Test/" + fileName);
		String destinationPath = "E:/Test/Output/";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");

		AlarmLogIRSR3GConverter mSGSNConverter = new AlarmLogIRSR3GConverter();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	public static void main(String arg[]){
		testConvertFile();
	}
}
