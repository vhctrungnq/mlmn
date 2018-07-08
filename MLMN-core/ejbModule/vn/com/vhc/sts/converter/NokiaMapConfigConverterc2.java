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

public class NokiaMapConfigConverterc2 extends STS_BasicConverter {

	private static final Logger logger = Logger
			.getLogger(NokiaMapConfigConverterc2.class.getName());
	
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
			List<String> output = new ArrayList<String>();
			
			while((strLine = reader.readLine()) != null) {
				output.add(strLine);
			}
			
			boolean done = true;
			
			if (output.size() >=2 ) {
				output.remove(1);
			}
			
			for (String item: output) {
				if (done) {
					writer.write(commentChar + item);
					done = false;
				}
				else {
					writer.write(item);
				}
				
				writer.newLine();
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
