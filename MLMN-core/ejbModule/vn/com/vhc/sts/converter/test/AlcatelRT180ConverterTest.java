package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;


import org.junit.Test;

import vn.com.vhc.sts.converter.AlcatelRT180Converter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlcatelRT180ConverterTest {
	@Test
	public void testConvertFile() {
		String fileName = "R18000001.073";
		File file = new File("/home/thanhlv/" + fileName);
		String destinationPath = "/home/thanhlv/OutputAlcatel/";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, "\t");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");

		AlcatelRT180Converter alcatelConverter = new AlcatelRT180Converter();
		try {
			alcatelConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
}
