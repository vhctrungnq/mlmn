package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.csvreader.CsvReader;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author VHCSOFT
 * @param file
 * @param direcPath
 * @param params
 * 
 * @return file converter
 * */
public class IpbbNodeBConverter extends STS_BasicConverter {

	private static final Logger logger = Logger.getLogger(BSCConverter.class.getName());
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		CsvReader reader = null;
		BufferedWriter wIpbbNodeB = null;
		try { 
			reader = new CsvReader(file.getPath()); 
			
			if(reader.readHeaders()){
				File outFile = new File(direcPath, file.getName());
				wIpbbNodeB = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
				
				//write head
				wIpbbNodeB.write("#ID;DATE_TIME;DATE_TIME_RAW;TRAFFIC_TOTAL_VOL;TRAFFIC_TOTAL_VOL_RAW;TRAFFIC_TOTAL_SPEED;TRAFFIC_TOTAL_SPEED_RAW;"
						+ "TRAFFIC_IN_VOL;TRAFFIC_IN_VOL_RAW;TRAFFIC_IN_SPEED;TRAFFIC_IN_SPEED_RAW;TRAFFIC_OUT_VOL;TRAFFIC_OUT_VOL_RAW;TRAFFIC_OUT_SPEED;"
						+ "TRAFFIC_OUT_SPEED_RAW;COVERAGE;COVERAGE_RAW");
				
				//  write record data file
				while (reader.readRecord()){
					try {
						String id=reader.get("ID");
						String dateTime=reader.get("Date Time");
						String dateTimeRaw=reader.get("Date Time(RAW)");
						String traffTotalVol=reader.get("Traffic Total (volume)").substring(0, reader.get("Traffic Total (volume)").indexOf(" "));
						String traffTotalVolRaw=reader.get("Traffic Total (volume)(RAW)");
						String traffTotalSpeed=reader.get("Traffic Total (speed)").substring(0, reader.get("Traffic Total (speed)").indexOf(" "));
						String traffTotalSpeedRaw=reader.get("Traffic Total (speed)(RAW)");
						String traffInVol=reader.get("Traffic In (volume)").substring(0, reader.get("Traffic Total (volume)").indexOf(" "));
						String traffInVolRaw=reader.get("Traffic In (volume)(RAW)");
						String traffInSpeed=reader.get("Traffic In (speed)").substring(0, reader.get("Traffic In (speed)").indexOf(" "));
						String traffInSpeedRaw=reader.get("Traffic In (speed)(RAW)");
						String traffOutVol=reader.get("Traffic Out (volume)").substring(0, reader.get("Traffic Out (volume)").indexOf(" "));
						String traffOutVolRaw=reader.get("Traffic Out (volume)(RAW)");
						String traffOutSpeed=reader.get("Traffic Out (speed)").substring(0, reader.get("Traffic Out (speed)").indexOf(" "));
						String traffOutSpeedRaw=reader.get("Traffic Out (speed)(RAW)");
						String coverage=reader.get("Coverage").substring(0, reader.get("Coverage").indexOf(" "));
						String coverageRaw=reader.get("Coverage(RAW)");
						
						wIpbbNodeB.newLine();
						wIpbbNodeB.write(id+";"+dateTime+";"+dateTimeRaw+";"+STS_Util.getFloatToStr(traffTotalVol)+";"+traffTotalVolRaw+";"+STS_Util.getFloatToStr(traffTotalSpeed)
								+";"+traffTotalSpeedRaw+";"+STS_Util.getFloatToStr(traffInVol)+";"+traffInVolRaw+";"+STS_Util.getFloatToStr(traffInSpeed)+";"+traffInSpeedRaw+";"+STS_Util.getFloatToStr(traffOutVol)
								+";"+traffOutVolRaw+";"+STS_Util.getFloatToStr(traffOutSpeed)+";"+traffOutSpeedRaw+";"+STS_Util.getFloatToStr(coverage)+";"+STS_Util.getFloatToStr(coverageRaw));
					} catch (Exception e) {
						break; 
					}
				} 
			}
		} catch (IOException ioe) {
			throw new STS_ConvertException(ioe.getMessage(),ioe);
		} finally {
			if (reader != null) {
				try {
					wIpbbNodeB.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader.close();
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
		
	} 
}