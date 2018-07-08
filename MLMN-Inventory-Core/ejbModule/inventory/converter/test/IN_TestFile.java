package inventory.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import inventory.cni.IN_BasicConverter;
import inventory.converter.InventoryEricsson3GConverter;
import inventory.converter.InventoryHuaweiCoreCsConverter;
import inventory.converter.InventoryHuaweiCoreCsConverter2;
import inventory.util.exceptions.IN_ConvertException;

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
		File folder = new File("E:\\project\\inventory\\New folder1");
		String destinationPath = "C:\\Users\\VHC\\Desktop\\out\\";
		if (!folder.exists()) {
			System.out.println("folder not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();

		IN_BasicConverter converter = new InventoryEricsson3GConverter();
		for (File file : folder.listFiles()) {
			try {
				long startTime = System.currentTimeMillis();
				converter.convertFile(file, destinationPath, params);
				long endTime = System.currentTimeMillis();
				//System.out.println("Thoi gian thuc hien : "+(endTime - startTime));
				//System.out.println("Convert is success !");
			} catch (IN_ConvertException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String args[]){
		testConvertFile();
	}
}
