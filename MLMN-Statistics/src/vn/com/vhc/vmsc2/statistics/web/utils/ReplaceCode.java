package vn.com.vhc.vmsc2.statistics.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReplaceCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		{
			try {
				File directory = new File("/home/thanhlv/Projects/VMS/VMSC2-Statistics/WebContent/WEB-INF/jsp");
				File files[] = directory.listFiles();
				for (File file : files) {
					BufferedReader reader = new BufferedReader(new FileReader(file));

					String line = "", oldtext = "";
					while ((line = reader.readLine()) != null) {
						oldtext += line + "\r\n";
					}
					reader.close();

					// replace
					if (oldtext.indexOf("firstDay: 1") != -1) {
						System.out.println(file.getAbsolutePath());
						String newtext = oldtext.replaceAll("firstDay: 1,\r\n", "");
						
						FileWriter writer = new FileWriter(file.getAbsolutePath());
						writer.write(newtext);
						writer.close();
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
