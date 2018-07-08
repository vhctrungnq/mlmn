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

public class EricssonMapConfigConverter3G extends STS_BasicConverter {

	private static final Logger logger = Logger 
			.getLogger(EricssonMapConfigConverter3G.class.getName());
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		
		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			File outfile = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outfile));
			
			String strLine = "";
			String line = "";
			String utrancell = "UTRANCELL=";
			while((strLine = reader.readLine()) != null) {
				if(strLine.toUpperCase().contains(utrancell))
				{
					String[] strArrayTmp = strLine.split(" ");
					line += strArrayTmp[0].substring(strArrayTmp[0].indexOf("=")+1, strArrayTmp[0].length()) + ",";
					line += strArrayTmp[1]+ ",";
					line += strArrayTmp[2].substring(strArrayTmp[2].indexOf("=")+1, strArrayTmp[2].length())+ ",";
					line = line.substring(0, line.length() - 1);
					writer.write(line);
					writer.newLine();
					line = "";
				}
			}
		} catch (IOException ioe) {
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

}
