package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.sts.converter.IPBBConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class SGSNConverterTest {
	@Test
	public static void testConvertFile() {
		String fileName = "903_BDI_2-BDN-Gi0_5_6__ACBDN2C-BDN-Gi8_5 #5_Sat-11-28-2015.xml";
		File file = new File("C:/Users/BUIQUANG/Desktop/" + fileName);
		String destinationPath = "C:/Users/BUIQUANG/Desktop/Output/";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");

		IPBBConverter mSGSNConverter = new IPBBConverter();
		try {
			mSGSNConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	
	public static void main(String args[]){
		testConvertFile();
	}
}
