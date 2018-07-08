package vn.com.vhc.util;

import java.io.File;

public class FileUtil {

	public static boolean moveTo(String fromFile, String toDirectory) {
		
		File afile = new File(fromFile);
		
		if(afile.renameTo(new File(toDirectory.concat("/") + afile.getName()))){
			return true;
		} else {
			return false;
		}
	}

	public static boolean deleteFile(String file) {
		File afile = new File(file);
		
		return afile.delete();
	}
}
