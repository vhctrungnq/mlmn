package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;


import org.junit.Test;

import vn.com.vhc.sts.cni.converter.HuaweiConverter;
import vn.com.vhc.sts.converter.AlcatelConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlcatelConverterTest {
	@Test
	public static void testConvertFile() {
		String fileName = "pmresult_100666003_60_201305090700_201305090800.csv";
		File file = new File("E:\\gnosdata\\selection\\huawei\\" + fileName);
		String destinationPath = "E:\\gnosdata\\converter\\huawei\\";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");
		HuaweiConverter hw = new HuaweiConverter();
		//AlcatelConverter alcatelConverter = new AlcatelConverter();
		try {
			hw.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	public static void main(String[] args)
	{
		testConvertFile();
	}

}
