package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.jboss.logging.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class ZteCellMapConverter extends STS_BasicConverter {
	private static final Logger logger = Logger.getLogger(ZteCellMapConverter.class);

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		// TODO Auto-generated method stub
		prepareParams(params);
		makeDirectory(direcPath);

		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outFile));

			String line = "";
			// get Header
			line = reader.readLine().replaceAll(",", ";");
			writer.write("#" + line + "\n");
			while ((line = reader.readLine()) != null) {
				String lineOut = "";
				String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				for (String item : items) {
					lineOut += item + ";";
				}
				lineOut = lineOut.replaceAll("\"", "");
				writer.write(lineOut.substring(0, lineOut.length()-1) + "\n");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e, e);
				}
			}
			
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e, e);e.printStackTrace();
				}
			}
		}
		
	}

}
