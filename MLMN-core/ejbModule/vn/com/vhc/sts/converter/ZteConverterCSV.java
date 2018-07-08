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

/**
 * <p>
 * Hello
 * </p>
 */
public class ZteConverterCSV extends STS_BasicConverter {

	private static final Logger logger = Logger.getLogger(BSCConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(new File(direcPath, file.getName())));
			// create header
			String line = "#" + reader.readLine();
			writer.write(line + "\n");
			while ((line = reader.readLine()) != null) {
				writer.write(line + "\n");
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
					logger.error(e, e);
				}
			}
		}

	}
}