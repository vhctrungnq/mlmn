package controller;  

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class AlarmTestConverte {
	@Test
	public static void testConvertFile() {
		String pathInFile = "C:/Users/Bui Quang/Desktop/Logfile/Logfile/Huawei/Huawei_23102013_2.csv";
		String pathOutFile = "D:/alarmdata/converter/huawei/";
		String userName = "admin"; 
		
		AlarmHuaweiCSVConverter converter = new AlarmHuaweiCSVConverter();
		FileInputStream fileInPutStream;
		try {
			fileInPutStream = new FileInputStream(pathInFile); 
		long startTime = System.currentTimeMillis();
		converter.convertFile(fileInPutStream, userName);
		long endTime = System.currentTimeMillis();
		System.out.println("Thoi gian thuc hien : "+(endTime - startTime));
		System.out.println("Convert is success !");  
		
		System.out.println("Convert is success !");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void main(String arg[]){
		testConvertFile();
	}
}
