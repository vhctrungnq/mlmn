package vn.com.vhc.alarm.utils;

public class DataTools {
	public static String getData(String strLine, String charSub) {
		return strLine.substring(strLine.indexOf(charSub)+1, strLine.length()).trim();
	}
	
	// Chuyen doi dang hien thi muc do canh bao
	public static String formatSeverity(String severity){
		String a = "";
		
		if(severity.toUpperCase().equals("CRITICAL")){ 
			a = "A1"; 
		}else if(severity.toUpperCase().equals("MAJOR")){ 
			a = "A2";
		}else if(severity.toUpperCase().equals("MINOR")){ 
			a = "A3";
		}else if(severity.toUpperCase().equals("WARNING")){ 
			a = "A4";
		}else{ 
			a = severity;
		}
		
		return a;
	}
}
