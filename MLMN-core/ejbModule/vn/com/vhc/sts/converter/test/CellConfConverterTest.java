package vn.com.vhc.sts.converter.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Hashtable;


import org.junit.Test;

import vn.com.vhc.sts.converter.CellConfConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class CellConfConverterTest {
	@Test
	public void testConvertFile() {
		String fileName = "BSSConf.vmsc2srv01.20101228093500";
		File file = new File("/home/quan/" + fileName);
		String destinationPath = "/home/quan/Desktop/";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ",");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");

		CellConfConverter cellConfConverter = new CellConfConverter();
		try {
			cellConfConverter.convertFile(file, destinationPath, params);
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	
	@Test
	public void testGetDate(){
		String str = "BSSConf.vmsc2srv01.20101228093500";
		String expResult = "2010122809";
		
		CellConfConverter cellConfConverter = new CellConfConverter();
		String result = cellConfConverter.getDate(str);
        
		assertEquals(expResult, result);
	}
}
