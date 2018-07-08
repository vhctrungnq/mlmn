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
* 
* @author Mr.DatNH
*
*/

public class BSCConverter extends STS_BasicConverter {

	private static final Logger logger = Logger.getLogger(BSCConverter.class.getName());
	
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
			
			String firstLine = reader.readLine();	// first line in raw file: BSC437126,1,201202150300,60;
			
			String strList[] = firstLine.split(separator);
			
			String BSCID = strList[0].substring(3);
			String TIME = strList[2];
			
			String DATETIME = getDate(TIME, separator);
			
			String LINEHEADER = commentChar + "BSCID" + separator + "DAY" + separator + "HOUR";// + separator + "CELLID";
			
			String FIRSTROW = BSCID + separator + DATETIME;
			
			String FIRSTLINE = "";
			
			String strLine = "";
			strLine = reader.readLine();
			
			boolean first = true;
			
			strLine = reader.readLine();
			
			while(strLine != null) {
				if (first) {
					if (strLine.contains(";")) {
						strLine = strLine.substring(0, strLine.length() - 1);
						LINEHEADER += separator + strLine.split(separator)[0];
						FIRSTLINE += separator + strLine.split(separator)[1];
						
						writer.write(LINEHEADER);
						writer.newLine();
						
						writer.write(FIRSTROW + FIRSTLINE);
						writer.newLine();
						
						if ((strLine = reader.readLine()) != null) {
							writer.write(FIRSTROW);
							strLine = reader.readLine();
						}
						
						first = false;
						
					}
					else {
						LINEHEADER += separator + strLine.split(separator)[0];
						FIRSTLINE += separator + strLine.split(separator)[1];
						
						strLine = reader.readLine();
					}
					
				}
				else {
					if (strLine.contains(";")) {
						strLine = strLine.substring(0, strLine.length() - 1);
						writer.write(separator + strLine.split(separator)[1]);
						writer.newLine();
						
						if ((strLine = reader.readLine()) != null) {
							writer.write(FIRSTROW);
							strLine = reader.readLine();
						}
						
					}
					else {
						writer.write(separator + strLine.split(separator)[1]);
						
						strLine = reader.readLine();
					}
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
	
	private String getDate(String time, String separator) {
		String Date = "";
		
		Date += time.substring(0, 8);
		
		Date += separator;
		
		Date += time.substring(8, 10);
		
		return Date;
	}
	
}

/*import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import net.sts.vms.gnos.cni.BasicConverter;
import net.sts.vms.gnos.util.exceptions.ConvertException;

*//**
 * 
 * @author Datnh
 *
 *//*
public class BSCConverter extends BasicConverter {

	private static final Logger logger = Logger
			.getLogger(EricssonConverter.class.getName());
	
	BSCID VARCHAR2(30 BYTE), 
	DAY DATE, 
	HOUR NUMBER, 
	CELLID VARCHAR2(30 BYTE),
	period_duration NUMBER,
	bts_id NUMBER,
	changed_flag NUMBER,
	ave_dl_sig_str NUMBER,
	undef_denom1 NUMBER,
	ncc NUMBER,
	bcc NUMBER,
	bcch NUMBER,
	period_start_time NUMBER,
	period_stop_time NUMBER,
	trx_type NUMBER,
	period_real_start_time NUMBER,
	period_real_stop_time NUMBER,
	segment_id NUMBER,
	
	@Override
	public void convertFile(File file, String direcPath, 
			Hashtable<Byte, String> params) throws ConvertException {

		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			File outfile = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outfile));
			
			String lineHeader = "BSCID" + separator + "DAY" + separator + "HOUR" + separator + "CELLID";
			String strLine = "";
			
			boolean first = true;
			boolean firstHeader = true;
			
			String firstLine = reader.readLine();	// first line in raw file: BSC437126,1,201202150300,60;
			
			String strList[] = firstLine.split(separator);
			
			String BSCID = strList[0];
			String TIME = strList[2];
			
			String DAY = getDate(TIME);
			String HOUR = TIME.substring(8, 10);
			
			String valueLine = "";
			
			//List<String> output = new ArrayList<String>();
			
			boolean d = true;
			int i = 1;
			while((strLine = reader.readLine()) != null) {
				if (first) {
					//BTS_ID
					valueLine = BSCID + separator + DAY + separator + HOUR + separator + "CELLID";
					first = false;
					continue;
				}
				else {
					//if (d) {
						if (strLine.contains(";")) {
							first = true;
							strLine = strLine.substring(0, strLine.length() - 1);
							
							String strValue[] = strLine.split(separator);
							
							lineHeader += separator + strValue[0];
							//valueLine += separator + strValue[1];
							
							if (firstHeader) {
								writer.write(lineHeader);
								writer.newLine();
								
								//writer.write(valueLine);
								//writer.newLine();
								
								//output.add(lineHeader);
								d= false;
								firstHeader = false;
							}
							else {
								//output.add(valueLine);
								//writer.write(valueLine);
								writer.newLine();
								System.out.println(i++);
							}
							
							//System.out.println(valueLine);
						}
						else {
							//String strValue[] = strLine.split(separator);
							if (firstHeader)
							lineHeader += separator + strLine.split(separator)[0];
							//valueLine += separator + strValue[1];
							writer.write(separator + strLine.split(separator)[1]);
							//System.out.println(separator + strValue[1]);
						}
						
					
					//}
					//else {
						//i++;
						//System.out.println(i);
					//}
				}
			}
			
			for (String item: output) {
				writer.write(item);
				writer.newLine();
			}
			
		} catch (IOException ioe) {
			throw new ConvertException(ioe.getMessage(), "GNOS-0306", ioe);
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
	
	public String getDate(String time) {
		String Date = "";
		
		Date += time.substring(6, 8) + "-";
		Date += time.substring(4, 6) + "-";
		Date += time.substring(0, 4);
		
		return Date;
	}

}*/
