package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.sts.converter.AAAXMLConverter;
import vn.com.vhc.sts.converter.CmdLicenseConverter;
import vn.com.vhc.sts.converter.IPBBConverterXls;
import vn.com.vhc.sts.converter.NokiaCellConverter3G;
import vn.com.vhc.sts.converter.NokiaRNCConverter3G;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class EricssonConverterTest {
	@Test
	public static void testConvertFile() {
		String fileName = "Nokia-3G_etlexpmx_WCEL_20141209091237_1158001.xml";
		/*File file = new File("E:/gnosdata/convert/ericssonCore/" + fileName);
		String destinationPath = "E:\\gnosdata\\convert\\ericssonCore\\";*/
		File file = new File("D:\\TEST\\gnosdata\\selection\\input\\" + fileName);
		//String fileName2 = "E:\\gnosdata\\selection\\sgsn\\" + fileName;
		String destinationPath = "D:\\TEST\\gnosdata\\selection\\output\\";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(STS_Setting.SEPARATOR_KEY, ";");
		params.put(STS_Setting.COMMENT_CHAR_KEY, "#");
		//AlcatelConverter obj = new AlcatelConverter();
		//AlcatelRT180Converter obj = new AlcatelRT180Converter();
		//EricssonConverter2 obj = new EricssonConverter2();
		//NokiaCellConverter3G obj = new NokiaCellConverter3G();
		//NdsPcsConvertterCsv obj = new NdsPcsConvertterCsv();
		//BSCConverter obj = new BSCConverter();
		//NokiaBTSConverter3G obj = new NokiaBTSConverter3G();
		//BSCConverter obj = new BSCConverter();
		//SGSNConverter obj = new SGSNConverter();
		//IPBBConverterCacti obj = new IPBBConverterCacti();
		//DongBoAlarmConverterTest obj = new DongBoAlarmConverterTest();
		//AAAConverter obj = new AAAConverter();
		//NokiaMapConfigConverter obj = new NokiaMapConfigConverter();
		//EricssonXMLConverterMGW obj = new EricssonXMLConverterMGW();
		NokiaCellConverter3G obj = new NokiaCellConverter3G();
		//NokiaMapConfigConverter obj = new NokiaMapConfigConverter();
		//  CmdLicenseConverter obj = new CmdLicenseConverter();
		//AAAXMLConverter obj = new AAAXMLConverter();
		 //HuaweiConverter obj = new HuaweiConverter();
		//IPBBConverterXls  obj = new IPBBConverterXls();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date dt = new Date();
			
			System.out.println(df.format(dt));
			obj.convertFile(file, destinationPath, params);
			//ericssonConverter.convertFile(file, destinationPath, params);
			//ericssonConverter.convertFile(file, destinationPath, params);
			Date dt2 = new Date();
			System.out.println(df.format(dt2));
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}

		System.out.println("Convert is success !");
	}
	public static void main(String[] args)
	{
		/*char s = 54;
		char a = (char)s;
		System.out.println(a);
		String s = "SubNetwork=ONRM_ROOT_MO_R,SubNetwork=RBTPT1E,MeContext=RBTPT1E";
		int index = s.lastIndexOf("=");
		String bscId = s.substring(index + 1, s.length());
		//get btsid
		int idbts = s.lastIndexOf("SubNetwork=");
		int idbts2 = s.lastIndexOf(",");
		String btsId = s.substring(idbts + 11, idbts2-1);
		System.out.println(bscId);
		System.out.println(btsId);*/
		
		testConvertFile();
	}

}
