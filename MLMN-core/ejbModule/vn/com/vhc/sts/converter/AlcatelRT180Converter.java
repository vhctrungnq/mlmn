package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;


import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class AlcatelRT180Converter extends STS_BasicConverter {
	
	private static final Logger logger = Logger
	.getLogger(AlcatelConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath,
			Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			File outFileAlcatelRT180 = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outFileAlcatelRT180));

			String strLine = "";
			String bscid = "";
			String day = "";
			String hour = "";
			boolean headerAlcatelRT180 = false;
			while ((strLine = reader.readLine()) != null) {
				if (headerAlcatelRT180 == false) {
					if (strLine.contains("Name of BSC")){
						String [] strArray = strLine.split(":");
						bscid = strArray[1].trim();
					}
					if (strLine.contains("Measurement begin date and time")){
						String [] strArray = strLine.split(":");
						String strDate = strArray[1];
						day = strDate.substring(0,11).trim();
						hour = String.valueOf(Integer.parseInt(strDate.substring(12,14).trim())+3);
					}
					if (strLine.contains("CELL_CI_ADJ")) {
						strLine = "BSCID" + separator + "DAY" + separator + "HOUR" + separator + strLine;
						headerAlcatelRT180 = true;
					}
					strLine = commentChar + strLine;
					writer.write(strLine+"\n");
				} else { 
					writer.write(bscid + separator + day + separator + hour + separator + strLine+ "\n");
				}
			}

		} catch (IOException ioe) {
			throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
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
}
