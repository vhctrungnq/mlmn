package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.converter.EricssonXMLConverter3G;
import vn.com.vhc.sts.converter.NokiaCellConverter3G;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class TestConverter {

	public static void testConvertFile() {
		String destinationPath = "C:\\Users\\VHC\\Desktop\\out";

		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ";");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");

		STS_BasicConverter converter = new NokiaCellConverter3G();

		File folder = new File("C:\\Users\\VHC\\Desktop\\test");
		for (File file : folder.listFiles()) {
			try {
				converter.convertFile(file, destinationPath, params, null);
			} catch (STS_ConvertException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(file.getName() + "Convert is success !");
		}

		//
	}

	public static void main(String arg[]) {
		testConvertFile();
		// Pattern patt = Pattern.complie("")
		
	}

}
