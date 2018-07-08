package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.jboss.util.file.Files;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class Ericsson2gConverter extends STS_BasicConverter {
	
	private String[] arrStringHandover = {"NICELASS","NCELLREL", "NECELLREL", "NECELASS"};
	private static final Logger logger = Logger.getLogger(Ericsson2gConverter.class);
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		// TODO Auto-generated method stub
		prepareParams(params);
		makeDirectory(direcPath);
		for (String str : arrStringHandover) {
			if (file.getName().contains(str)) {
				BufferedReader reader = null;
				BufferedWriter writer = null;
				File outFile = new File(direcPath, file.getName());
				try {
					reader = new BufferedReader(new FileReader(file));
					writer = new BufferedWriter(new FileWriter(outFile));
					String line = "";
					while ((line = reader.readLine()) != null) {
						if (line.startsWith("#")) {
							// tach cellid thanh from_cell va to_cell
							String[] oldHeader = line.split(separator);
							int n = oldHeader.length;
							String[] newHeader = new String[n + 1];
							for(int i = 0; i <=n; i++) {
								if (i <= 3) {
									newHeader[i] = oldHeader[i];
								} else if (i > 5) {
									newHeader[i] = oldHeader[i-1];
								}								
							}
							
							newHeader[4] = "FROM_CELL";
							newHeader[5] = "TO_CELL";
							for (int i = 0; i < n; i++) {
								writer.write(newHeader[i] + separator);
							}
							writer.write(newHeader[n] + "\n");
							
						} else {
							line = line.replaceAll("-", separator);
							writer.write(line + "\n");
						}
						
					}
					
					
				} catch (IOException e) {
					logger.error(e, e);
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return;
			}
		}

		try {
			Files.copy(file, new File(direcPath, file.getName()));
		} catch (IOException e) {
			logger.error(e, e);
		}
	}
}
