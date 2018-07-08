package vn.com.vhc.alarm.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.converter.AlarmHistoryEricsson2GConvert;
import vn.com.vhc.alarm.converter.AlarmHistoryEricsson2GConvert_MLMT;
import vn.com.vhc.alarm.converter.AlarmHistoryZteMTConverter;
import vn.com.vhc.alarm.converter.AlarmLog3GConverter;
import vn.com.vhc.alarm.converter.ConvertNokia3G;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class testconvert {
	@Test
	public static void testConvertFile() {
		String destinationPath = "C:\\Users\\Trung\\Desktop\\out";
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		params.put(AL_Setting.SEPARATOR_KEY, ";");
		params.put(AL_Setting.COMMENT_CHAR_KEY, "#");

		AL_BasicConverter converter = new AlarmHistoryEricsson2GConvert();
		//AlarmMBL_ABL_MOConverter converter =new AlarmMBL_ABL_MOConverter(); 
		//AlarmHistoryAlcater2GConvert converter = new AlarmHistoryAlcater2GConvert();
		try {
			File folder = new File("C:\\Users\\Trung\\Desktop\\alarm\\");
			for (File file : folder.listFiles()) {
				converter.convertFile(file, destinationPath, params);
				System.out.println(file.getName()+"Convert is success !");
			}
			
		} catch (AL_ConvertException e) {
			e.printStackTrace();
		}
		//
	}
	public static void main(String arg[]){
		testConvertFile();
		//Pattern patt = Pattern.complie("")
	}
}
