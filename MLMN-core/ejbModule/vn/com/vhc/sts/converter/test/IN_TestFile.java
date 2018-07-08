package vn.com.vhc.sts.converter.test; 

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.sts.converter.IpbbNodeBConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/******************************************************************************
NAME:       EricssonXMLConverterTest
PURPOSE:

REVISIONS:
Ver        Date        Author           Description
---------  ----------  ---------------  ------------------------------------
1.0        12/06/2009             1. Created this package.
******************************************************************************/
public class IN_TestFile {
	@Test
	public static void testConvertFile() {
		String fileName = "54702-2016-06-14-00-00-00.csv";
		File file = new File("E:\\rawfile\\download\\NodeB-Ipbb\\" + fileName);
		String destinationPath = "E:\\rawfile\\converter\\";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		// params.put(Setting.FILE_ID_KEY, "1");

		//EricssonXmlConvert ericssonConverter = new EricssonXmlConvert();
		//EricssonXMLConverterMGW ericssonConverter = new EricssonXMLConverterMGW();
		//IPBBConverter ipbb = new IPBBConverter();
		//IsoLicenseSoftWareHuaweiBsc converter = new IsoLicenseSoftWareHuaweiBsc();
		///InventoryAlcatelBTSConverter converter = new InventoryAlcatelBTSConverter();
		//InventoryAlcatelBSCConverter converter = new InventoryAlcatelBSCConverter();
		//IsoAbisHuaweiConverter converter = new IsoAbisHuaweiConverter();
		//IsoAbisNokiaRNCConverter converter = new IsoAbisNokiaRNCConverter();
		//IsoPortRNCSGSNConverter converter = new IsoPortRNCSGSNConverter();
		//IsoPortSGSNBSCConverter converter = new IsoPortSGSNBSCConverter();
		//IsoLicenseSWEricssonRNCConverter converter = new IsoLicenseSWEricssonRNCConverter();
		//InventoryIPBBCiscoConverter converter = new InventoryIPBBCiscoConverter();
		IpbbNodeBConverter converter = new IpbbNodeBConverter();
		//IsoLicenseSWHuaweiMSCConverter converter = new IsoLicenseSWHuaweiMSCConverter();
		try {
			long startTime = System.currentTimeMillis();
			converter.convertFile(file, destinationPath, params);
			long endTime = System.currentTimeMillis();
			
			System.out.println("Thoi gian thuc hien : "+(endTime - startTime));
			System.out.println("Convert is success !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		testConvertFile();
	}
}
