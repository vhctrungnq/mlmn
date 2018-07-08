package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class ConvertNokia3G extends AL_BasicConverter {

	private static final String header = "#VENDOR;ALARM_LOG_ID;SDATE;EDATE;ALARM_CLASS;OBJ_NAME;NE;SITE;CELL;ALARM_NAME;ALARM_INFO;OBJ_CAUSE;NETWORK;MO";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		// TODO Auto-generated method stub
		BufferedReader reader = null;
		BufferedWriter out = null;
		StringBuilder str = new StringBuilder();
		String line;
		String[] moBlock;
		String[] tmp;
		prepareParams(params);
		makeDirectory(direcPath);
		try {
			reader = new BufferedReader(new FileReader(file));
			out = new BufferedWriter(new FileWriter(direcPath + "/" + file.getName()));
			str.append(header + "\n");
			while ((line = reader.readLine()) != null) {
				str.append("NOKIA SIEMENS;");
				tmp = line.split(";");
				for (int i = 0; i < tmp.length; i++) {
				
					// xoa khoang trang thua trong thoi gian neu co
					if (i == 1 || i == 2) {
						str.append(tmp[i].replaceAll("\\s+", " ").trim() + ";");
					} else if (i == 3) {
						if (tmp[i].equalsIgnoreCase("critical")) {
							str.append("A1;");
						} else if (tmp[i].equalsIgnoreCase("major")) {
							str.append("A2;");
						} else if (tmp[i].equalsIgnoreCase("minor")) {
							str.append("A3;");
						} else {
							str.append("Indeterminate;");
						}
					} else {
						str.append(tmp[i] + ";");
					}
					
				}
				
				moBlock = tmp[4].split("/");
				if (tmp[5].startsWith("R")) {
					str.append("3G;");
					for (String mo : moBlock) {
						if (mo.startsWith("WBTS-")) {
							str.append(mo.substring(5));
							break;
						}
					}
				} else if (tmp[5].startsWith("B")){
					str.append("2G;");
					for (String mo : moBlock) {
						if (mo.startsWith("BCF-")) {
							str.append(mo.substring(4));
							break;
						}
					}
				} else {
					str.append(";");
				}
				str.append("\n");
			}

			out.write(str.toString());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
