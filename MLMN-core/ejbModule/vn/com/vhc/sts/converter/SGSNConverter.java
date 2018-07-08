package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class SGSNConverter  extends STS_BasicConverter {

	private static final Logger logger = Logger
	.getLogger(SGSNConverter.class.getName());
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {

		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			File outFile = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outFile));
			
			boolean firstLine =false;
			boolean firstBlock =false;
			
			String nameSGSN ="";
			String strLine = "";
			
			List<String> headerList = new ArrayList<String>();
			List<String> contentsList = new ArrayList<String>();
			while ((strLine = reader.readLine()) != null) {
				if (firstLine == false) {
					firstLine = true;
					nameSGSN = getSGSNId(strLine);
					headerList.add("#SGSNID");
					contentsList.add(nameSGSN);
				} else {
					if (firstBlock == false){
						if (strLine.contains(";")==true){
							strLine = strLine.replace(";", "");
							String tmpStr[] = strLine.split(separator);
							headerList.add(tmpStr[0]);
							contentsList.add(tmpStr[1]);
							
							String header ="";
							String contents ="";
							for (int i=0; i< headerList.size()-1;i++) {
								if (i==1){
									header +="";
									contents+="";
								} else {
									header += headerList.get(i)+separator;
									contents +=contentsList.get(i)+separator;
								}
							}
							header += headerList.get(headerList.size()-1)+"\n";
							contents += contentsList.get(contentsList.size()-1)+"\n";
							writer.write(header);
							writer.write(contents);
							contentsList.clear();
							contentsList.add(nameSGSN);
							firstBlock = true;
						} else {
							if (strLine.length() == 0) {
								strLine="THANHLV,THANHLV";
							}
							String tmpStr[] = strLine.split(separator);
							headerList.add(tmpStr[0]);
							contentsList.add(tmpStr[1]);
						}
					}
					else {
						if (strLine.contains(";")==true){
							strLine = strLine.replace(";", "");
							String tmpStr[] = strLine.split(separator);
							headerList.add(tmpStr[0]);
							contentsList.add(tmpStr[1]);
							
							String contents ="";
							for (int i=0; i< contentsList.size()-1;i++) {
								if (i==1){
									contents+="";
								} else {
									contents +=contentsList.get(i)+separator;
								}
							}
							contents += contentsList.get(contentsList.size()-1)+"\n";
							writer.write(contents);
							contentsList.clear();
							contentsList.add(nameSGSN);
						} else {
							if (strLine.length() == 0) {
								strLine="THANHLV,THANHLV";
							}
							String tmpStr[] = strLine.split(separator);
							contentsList.add(tmpStr[1]);
						}
					}
				}
			}
		} catch (Exception ioe) {
			throw new STS_ConvertException(ioe.getMessage(), "GNOS-0306", ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
	}
	
	public String getSGSNId(String strSource){
		String tmpStr[] = strSource.split(separator);
		return tmpStr[0];
	}
}

