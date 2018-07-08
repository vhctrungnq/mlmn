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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author ThanhLV
 *
 */
public class AlcatelConverter extends STS_BasicConverter {

	private static final Logger logger = Logger
			.getLogger(AlcatelConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath,
			Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);

		BufferedReader reader = null;
		BufferedWriter writer = null;
		BufferedWriter writerTrx = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			File outFile = new File(direcPath, file.getName());
			File outFileTrx = new File(direcPath, file.getName() + ".TRX");
			writer = new BufferedWriter(new FileWriter(outFile));
			writerTrx = new BufferedWriter(new FileWriter(outFileTrx));

			String strLine = "";
			List<String> block = new ArrayList<String>();
			List<String> blockFirstList = new ArrayList<String>();
			List<String> blockSecondList = new ArrayList<String>();
			int count = 0;
			int firstTime = 0;
			String tmp = "";
			while ((strLine = reader.readLine()) != null) {
				strLine = strLine.replace("\t", ",");
				if (count == 0) {
					if (!strLine.trim().isEmpty()) {
						tmp += strLine + "\n";
						blockFirstList.add(commentChar + strLine);
						blockSecondList.add(commentChar + strLine);
					} else {
						if (tmp != "")
							block.add(tmp);
						count++;
						tmp = "";
						if (count == 5)
							break;
						continue;
					}
				} else {
					if (count <= 3) {
						if (!strLine.trim().isEmpty()) {
							firstTime++;
							if (firstTime == 1) {
								strLine = "\n#DAY" + separator + "HOUR"
										+ separator + "BSCID" + separator
										+ strLine;
							} else if (firstTime >= 1) {
								strLine = getDate(block.get(0)) + strLine;
							}
						} else {
							firstTime = 0;
							count++;
							if (count == 5)
								break;
							continue;
						}
						blockFirstList.add(strLine);
					}
					if (3 < count && count <= 5) {
						if (!strLine.trim().isEmpty()) {
							firstTime++;
							if (firstTime == 1)
								strLine = "\n#DAY" + separator + "HOUR"
										+ separator + "BSCID" + separator
										+ strLine;
							else if (firstTime >= 1) {
								strLine = getDate(block.get(0)) + strLine;
							}
						} else {
							firstTime = 0;
							count++;
							if (count == 5)
								break;
							continue;
						}
						blockSecondList.add(strLine);
					}

				}
			}
			for (int i = 0; i < blockFirstList.size(); i++)
				writer.write(blockFirstList.get(i) + "\n");

			for (int i = 0; i < blockSecondList.size(); i++)
				writerTrx.write(blockSecondList.get(i) + "\n");

		} catch (IOException ioe) {
			throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
					writerTrx.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
	}

	/*private String getDate(String strSource) {
		String[] strArray = strSource.split("/");
		String[] strArrSub = strArray[strArray.length - 1].split("\\.");
		int beginIndex = strArrSub[0].length() - 2;
		String getHour = strArrSub[0].substring(beginIndex);
		int hour = Integer.parseInt(getHour) - 1;
		String getDay = strArray[strArray.length - 2];
		String getBscid = strArray[strArray.length - 3];
		strSource = getDay + separator + hour + separator + getBscid + separator;
		return strSource;
	}*/
	//Update by ThangPX (Get date in file)
	 private String getDate(String strSource) {
		String[] strArray = strSource.split("\\n");
		String getBscid = strArray[1].substring(strArray[1].indexOf(":,")+2, strArray[1].length());
		String dateAndTime = strArray[3].substring(strArray[3].indexOf(":,")+2,strArray[3].length());

		String strDate = "",strHour="";
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		
		DateTime dateTime = new DateTime();
		dateTime = formatter.parseDateTime(dateAndTime);
		
		DateTimeFormatter formatDest= DateTimeFormat.forPattern("yyyyMMdd");
		strDate = dateTime.toString(formatDest);
		strHour = String.valueOf(dateTime.getHourOfDay());
		strSource = strDate + separator + strHour + separator + getBscid + separator;
		return strSource;
	 }
}
