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

public class CellConfConverter extends STS_BasicConverter {

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

			File outFileCellConf = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outFileCellConf));

			String strLine = "";
			boolean blockCellConf = false;
			boolean headerCellConf = false;
			while ((strLine = reader.readLine()) != null) {
				if (strLine.equals("START CELL_SECTION")) {
					blockCellConf = true;
					headerCellConf = true;
				} else {
					if (blockCellConf == true) {

						if (headerCellConf == true) {
							strLine = strLine.replace("#", "#RESULT_TIME,");
							strLine = strLine.replace(" ", "");
							writer.write(strLine.replace(";", "\n"));
							headerCellConf = false;
						} else if (strLine.equals("END CELL_SECTION")) {
							break;
						} else {
							String tmp = getDate(file.getName()) + ","
									+ strLine.replace(";", "\n");
							tmp = tmp.replace(" ", "");
							writer.write(tmp);
						}
					}
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

	public String getDate(String strSource) {
		String[] strArray = strSource.split("\\.");
		strSource = strArray[strArray.length - 1];
		strSource = strSource.substring(0, 10);
		return strSource;
	}
}
