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

public class AAAConverter extends STS_BasicConverter {

	private static final Logger logger = Logger
			.getLogger(AAAConverter.class.getName());
	
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
			String fileName = file.getName();
			String nodeid = fileName.substring(fileName.indexOf("cont.")+5,fileName.lastIndexOf("."));
			boolean flag = false;
			while((strLine = reader.readLine()) != null) {
		
				if(flag==false)
					strLine="#NODEID"+"\t"+strLine;
				else
					strLine=nodeid+"\t"+strLine;
				writer.write(strLine);
				writer.newLine();
				flag = true;
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
