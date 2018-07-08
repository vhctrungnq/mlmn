package vn.com.vhc.sts.converter.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.converter.SGSNConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class ConvertTest extends STS_BasicConverter {
	private static final Logger logger = Logger.getLogger(SGSNConverter.class.getName());
	private int fileId = -1;

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException{
	prepareParams(params);
	makeDirectory(direcPath);

	BufferedReader reader = null;
	BufferedWriter writer = null;
	int count = 0;
	try {
		reader = new BufferedReader(new FileReader(file));

		File outFile = new File(direcPath, file.getName());
		writer = new BufferedWriter(new FileWriter(outFile));
		String type = getTypeName(file.getName());
		

		String line = "";
		
		String time="";
		String num  = "";
		String[] node = file.getName().split("_");
		String nodeName = "'"+ node[0] + "','" + node[1] + node[2] + "','" + node[3] + "',";
		String line2=  "";
		int counter = 0;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0) {
				continue;
			}
			if(line.contains("<Data")&&line.contains("&#")&&!line.contains("Create")&&!line.contains("Page"))
			{
				String strLine = line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
				
				if(isNumberic(strLine.substring(2,3)))
				{
					counter ++;
					strLine = relapceString(strLine);
					strLine = strLine.replace("&#", "");
					//line2 += strLine;
					if(strLine.contains("AM")||strLine.contains("PM"))
					{
						 time += strLine;
					}
					else
					{
						 num += strLine;
					}
					if(counter%2==0)
					{
					    line2 = nodeName;
						String[] time2 = time.split("-");
						String day = time2[0].substring(0, time2[0].indexOf(" "));
						String strTime = time2[0];
						strTime = strTime.trim();
						String tmps2 =  covertToDate(strTime) + "," +  covertToDate(day + time2[1]) ;
						//value += tmps2;
						
						line2 += tmps2+","+ num;
						System.out.println(line2);
						time = "";
						num = "";
						line2="";
					}
					
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
	private String relapceString(String str)
	{
		String strDest= "";
		String[] charNew = str.split("&#");
		for(int i=1;i<charNew.length;i++)
		{
			//String s = charNew[i].substring(0,2);
			//String hc = String.valueOf(charNew[i].substring(0,2).charAt(0));
			int hc = Integer.parseInt(charNew[i].substring(0,2));
			
			char str2 =  (char)(hc);
			//char strN = (char)str2;
			charNew[i]= charNew[i].replace(charNew[i].substring(0, 2), String.valueOf(str2));
			strDest += charNew[i];
		}
		return strDest;
	}
	@SuppressWarnings({ "rawtypes", "deprecation" })
	private List readXlsFile(String fileName, String filePath) {
		List sheetData = new ArrayList();
		try {

			HSSFWorkbook wb_hssf; // Declare HSSF WorkBook
			HSSFSheet sheet = null; // sheet can be used as common for XSSF and
									// HSSF WorkBook
			wb_hssf = new HSSFWorkbook(new FileInputStream(filePath));
			sheet = wb_hssf.getSheetAt(0);
			HSSFRow row; // No of rows
			HSSFCell cell;
			int rows = sheet.getPhysicalNumberOfRows();
			String value = "";
			int cols = 0; // No of columns
			int tmp = 0;
			String[] node = fileName.split("_");
			String nodeName = "'"+ node[0] + "','" + node[1] + node[2] + "','" + node[3] + "',";
			for (int i = 0; i < 5 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}
			for (int r = 1; r < rows; r++) {
				value = nodeName;
				row = sheet.getRow(r);
				if (row != null) {
					for (int c = 1; c < cols; c++) {
						cell = row.getCell((short) c);

						if (cell != null) {
							String tmps = cell.getRichStringCellValue().getString();
							if (!tmps.equalsIgnoreCase("")) {
								if (!tmps.contains("AM") && !tmps.contains("PM")) {
									if (isNumberic(tmps)) {
										value += tmps + ",";
										insertDatabase("R_E_IPBB_FW",value);
									}

								} else {

									String[] time = tmps.split("-");
									String day = time[0].substring(0, time[0].indexOf(" "));
									String strTime = time[0];
									strTime = strTime.trim();
									String tmps2 =  covertToDate(strTime) + "," +  covertToDate(day + time[1]) + ",";
									value += tmps2;

								}

							}

						}

					}
					

				}
			}
			updateRecordStatus();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return sheetData;

	}

	private boolean isNumberic(String str) {
		try {

			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	private Date isValidDate(String pattern, String date) {
		DateFormat df = new SimpleDateFormat(pattern);
		Date newDate ;
		try {
			newDate = df.parse(date);
		} catch (Exception ex) {
			newDate = null;
		}
		return newDate;
	}
	private String covertToDate(String soureDate)
	{
		String newDate = "";
		if (this.isValidDate("MM/dd/yyyy hh:mm aa", soureDate)!= null)
			newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.isValidDate("MM/dd/yyyy hh:mm aa", soureDate));
		if (this.isValidDate("MM/dd/yyyy hh:mm:ss aa", soureDate)!= null)
			newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.isValidDate("MM/dd/yyyy hh:mm:ss aa", soureDate));
		return "TO_DATE('"+newDate+"','yyyy-MM-dd HH24:mi')";
	}
	private void insertDatabase(String tableName, String value) throws SQLException {
		Connection conn = null;
		String queryStr;

		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			queryStr = "INSERT INTO " + tableName + selectDatabase(tableName);
			queryStr += value;
			queryStr = queryStr.substring(0, queryStr.lastIndexOf(",")) + ")";
			st.execute(queryStr);

			st.close();
		} finally {
			if (conn != null) {
				try {

					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}

	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+ NOLOGGING */ C_RAW_FILES set \n" + " IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
			st.execute(queryStr);
			st.close();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}

	private String selectDatabase(String tableName) {
		Connection conn = null;
		String file_column_header = "(";
		try {
			try {
				conn = STS_Global.DATA_SOURCE.getConnection();
				Statement st = conn.createStatement();

				String queryStr = "select TABLE_COLUMN from I_IMPORT_MAPPING where STATUS = 'Y' AND RAW_TABLE = '" + tableName + "' order by FILE_COLUMN";

				//
				ResultSet rs = st.executeQuery(queryStr);
				while (rs.next()) {
					file_column_header += rs.getString("TABLE_COLUMN") + ",";
				}
				file_column_header = file_column_header.substring(0, file_column_header.lastIndexOf(",")) + ") VALUES ";
				rs.close();
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}

		return file_column_header;
	}
public String getTypeName(String strSource) {
		
		String regex = "(SESSION_FPC)_(.*)_ ([0-9]{4})(.*)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(strSource.trim());

        String typeNode = "";
        if (m.find()) {
        	typeNode = m.group(3).trim();
        }
		return typeNode;
	}
}
