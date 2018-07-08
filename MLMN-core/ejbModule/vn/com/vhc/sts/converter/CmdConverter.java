package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
/**
 * 
 * @author ThangPX
 * @return: converted File 
 *
 */
public class CmdConverter extends STS_BasicConverter {


	private static Logger logger = Logger.getLogger(CmdConverter.class.getName());
	String sep="\n";
	private int fileId=-1;
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		
		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);	
		BufferedReader reader = null;
		// insert record and update status in C_RAW_FILES_MLMN table
				if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
					fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
				}
		try {
			reader = new BufferedReader(new FileReader(file));
			String fileName = file.getName();
			
			String fileNameMem =  getFileName(fileName+"_MEM");
			File outFileMem = new File(direcPath,fileNameMem);
			String fileNameCpu =  getFileName(fileName+"_CPU");
			File outFile = new File(direcPath, fileNameCpu);
			
			
			//strLine is current Line, datetime is Date, line is summary line
			String strLine = "",line="", strLineMem="", lineMem ="", dateTime="";
			String[] s = fileName.split("_");
			String type = s[s.length-2];
			String sar = s[s.length-1];
			
			
			
			boolean flag = false;// flag=true?Begin Block:next
			// Start file Memory
			boolean flagMem = false;
			while((strLine = reader.readLine()) != null) {
				// Get Date in file
				if(strLine.contains("Linux"))
				{
					dateTime = replaceLine(strLine);
					String[] strDate = dateTime.split(",");
					for(int a=0; a < strDate.length; a++)
					{
						if(isValid("yyyy-MM-dd", strDate[a]))
						{
							dateTime = strDate[a];
							break;
						}
					} 
					
				}
				
				// Get Header of file
				int i = strLine.indexOf("CPU     %user");
				int k = strLine.indexOf("CPU      %usr");
				if(i>0 || k > 0)
				{
					flag = true;
					if(i>0)
						strLine = replaceHeader(strLine, i);
					else 
						strLine = replaceHeader(strLine, k);
					strLine = commentChar + "TYPE,SAR,DATETIME,"+replaceLine(strLine);
					line = strLine + sep;
				}
				else
				{
					// Get data file
					if(flag == true)
					{
						if(strLine.contains("Average"))
						{
							line = line.trim();
							writeFile(outFile, line);
							flag = false;
						}
						line +=type + ","+ sar+ "," + dateTime+" "+ replaceLine(strLine)+sep;
						
						
							
					}
				}
				
				
				int j = strLine.indexOf("kbmemfree");
				
				if(j>0)
				{
					flagMem = true;
					strLineMem = replaceHeader(strLine, j);
					strLineMem = commentChar + "TYPE,SAR,DATETIME,"+replaceLine(strLineMem);
					lineMem = strLineMem + sep;
				}
				else
				{
					// Get data file
					if(flagMem == true)
					{
						if(strLine.contains("Average"))
						{
							lineMem = lineMem.trim();
							writeFile(outFileMem, lineMem);
							lineMem="";
							break;
						}
						lineMem +=type + "," + sar+ "," + dateTime+" "+ replaceLine(strLine)+sep;
					}
				}
				
			}
			try {
				genarateDBRecord(fileNameCpu,"R_CSI_CMD_CPU");
				genarateDBRecord(fileNameMem,"R_CSI_CMD_MEM");
				updateRecordStatus();
				
			} catch (SQLException e) {
				
			}
			
			
		} catch (IOException ioe) {
			throw new STS_ConvertException(ioe.getMessage(), "GNOS-0306", ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
		
	}
	//get date time
	private static boolean isValid(String pattern, String date) {
		DateFormat df = new SimpleDateFormat(pattern);

		try {
			df.parse(date);
			return true;
		} catch(Exception ex) {
			return false;
		}

	}
	// Replace header
	private String replaceHeader(String source, int index)
	{

		source = source.substring(index,source.length());
		source = source.replace("%", "");
	
		return source	;
	}
	// Replace data
	private String replaceLine(String source)
	{

		while(source.contains("  "))
		{
			source = source.replace("  ", " ");
		}
		source = source.replace("\t", ",");
		source = source.replace(" ", ",");
		return source	;
	}
	private void writeFile(File outFile, String line) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		
		if (line != null && line.length() > 0) {
			writer.write(line);
			writer.newLine();
			writer.close();
		}
	}
	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
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

	private void genarateDBRecord(String fileName, String tableName) throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, SERVER_NODE from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
			String patternId = "";
			String day = "";
			String hour = "";
			String nodeName = "";
			String serverNode = "";
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				patternId = rs.getString("PATTERN_ID");
				day = STS_Util.getTime(rs.getDate("DAY"));
				hour = rs.getString("HOUR");
				nodeName = rs.getString("NODE_NAME");
				serverNode = rs.getString("SERVER_NODE");
			}
			if (patternId.length() > 0) {
				queryStr = "insert /*+ NOLOGGING */ into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
						+ "NODE_NAME, RAW_TABLE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
						+ STS_Setting.DB_TIME_FORMAT + "'), " + hour + ", 1, 0, '" + nodeName + "', '" + tableName + "', '" + serverNode + "')";

				st.execute(queryStr);
			}

			rs.close();
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
}
