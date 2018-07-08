package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class NokiaBTSConverter3G extends STS_BasicConverter {

	private static Logger logger = Logger.getLogger(NokiaBTSConverter3G.class.getName());
	
	private static String _DATE;
	private static String _DAY;
	private static String _HOUR;
	
	private int fileId = -1;
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
/*		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		String lineHeader = commentChar + "BSCID" + separator + "SITEID" + separator + "DAY" + separator + "HOUR";
		
		_DATE = getTimeFromFileName(file.getName(), "[0-9]{8}[0-9]{2}[0-9]{2}[0-9]{2}");
		
		getDateTime(_DATE);
		
		BufferedWriter writer = null;
		
		boolean first = true;
		
		String ObjectType = "";
		
		try {

			List<String> output = new ArrayList<String>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			
			// Get block
			NodeList rList = document.getElementsByTagName("PMMOResult");		// List node PMMOResult
			
			int rNodeLength = rList.getLength();
			
			// Xu ly moi node
			for (int i = 0; i< rNodeLength; i++) {
				Node PMMOResultNode = rList.item(i);
				
				String strLine = "";
				
				// Get CDATA
				if (PMMOResultNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) PMMOResultNode;
					
					// MO
					NodeList MOList = element.getElementsByTagName("MO");
					
					String cdata = "";
					
					for (int j=0; j< MOList.getLength(); j++) {
	                	NodeList MO = MOList.item(j).getChildNodes();
	                    
	                	if (!cdata.equals(""))
	                		break;
	                	
	                    for (int k = 0; k < MO.getLength(); k++) {
	                    	Node DN = MO.item(k);
	                    	String dnTxt = DN.getTextContent().trim();
	                    	if (!dnTxt.equals("")) {
	                    		cdata = dnTxt;
	                    		break;
	                    	}
	                    }
	                }
					
					// Get RNC, BTS
					strLine = getHeaderValue(cdata, separator);
					
					strLine += separator + _DAY + separator + _HOUR;
					
					// PMTarget
					NodeList PMTargetList = element.getElementsByTagName("PMTarget");
					
					NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();
					
					if (first) {
						Element PMTargetElement = (Element) PMTargetNode;
						ObjectType = PMTargetElement.getAttribute("measurementType");
						
						//first = true;
					}
	                
					for (int n=0; n< PMTargetNode.getLength(); n++) {
						if (n%2 == 0)
							continue;
						else {
							if (first) {
								lineHeader += separator + PMTargetNode.item(n).getNodeName();
							}
							strLine += separator + PMTargetNode.item(n).getTextContent();
						}
					}
					first = false;
					
					output.add(strLine);
				}
			}
			
			// Outfile: ObjectType + '.' + file_name
			String _NEW_FILE = ObjectType.toLowerCase() + "." + file.getName();
			String _TABLE = "R_N_3G_" + ObjectType;
			
			File outfile = new File(direcPath, _NEW_FILE);
			writer = new BufferedWriter(new FileWriter(outfile));
			
			// write file
			writer.write(lineHeader);
			writer.newLine();
			for (String item: output) {
				writer.write(item);
				writer.newLine();
			}
			
			writer.close();
			
			try {
				genarateDBRecord(_NEW_FILE, _TABLE);
				
				updateRecordStatus();
				
			} catch (SQLException e) {
			}
			
		}
		catch(Exception e) {
			logger.debug(e);
			throw new STS_ConvertException(e.getMessage(), "GNOS-0306", e);
		}*/
		
	}
	
	// etlexpmx_WBTS_20120214221220_1124555
	public void getDateTime(String date) {
		_DAY = date.substring(0, 8);
		_HOUR = date.substring(9, 10);
	}
	
	// Convert 'PLMN-PLMN/RNC-14/WBTS-1/FTM-1/CESIF-1'
	public String getHeaderValue(String cdata, String sep) {

		String value[] = cdata.split("/");
		
		String RNCID = value[1].split("-")[1];
		String BTSID = value[2].split("-")[1];
		
		String headerValue = RNCID + sep + BTSID;
		
		return headerValue;

	}
	
//	private void genarateDBRecord(String fileName, String tableName) throws SQLException {
//		Connection conn = null;
//		try {
//			conn = STS_Global.DATA_SOURCE.getConnection();
//			Statement st = conn.createStatement();
//
//			String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, SERVER_NODE from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
//			String patternId = "";
//			String day = "";
//			String hour = "";
//			String nodeName = "";
//			String serverNode = "";
//			//
//			ResultSet rs = st.executeQuery(queryStr);
//			while (rs.next()) {
//				patternId = rs.getString("PATTERN_ID");
//				day = STS_Util.getTime(rs.getDate("DAY"));
//				hour = rs.getString("HOUR");
//				nodeName = rs.getString("NODE_NAME");
//				serverNode = rs.getString("SERVER_NODE");
//			}
//			if (patternId.length() > 0) {
//				queryStr = "insert /*+ NOLOGGING */ into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
//						+ "NODE_NAME, RAW_TABLE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
//						+ STS_Setting.DB_TIME_FORMAT + "'), " + hour + ", 1, 0, '" + nodeName + "', '" + tableName + "', '" + serverNode + "')";
//
//				st.execute(queryStr);
//			}
//
//			rs.close();
//			st.close();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e) {
//					logger.warn("Cannot close connection to database");
//				}
//			}
//		}
//	}
//	
//	private void updateRecordStatus() throws SQLException {
//		Connection conn = null;
//		try {
//			conn = STS_Global.DATA_SOURCE.getConnection();
//			Statement st = conn.createStatement();
//			// update state
//			String queryStr = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
//			st.execute(queryStr);
//			st.close();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e) {
//					logger.warn("Cannot close connection to database");
//				}
//			}
//		}
//	}

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params, Connection conn) 
			throws STS_ConvertException {

		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		String lineHeader = commentChar + "BSCID" + separator + "SITEID" + separator + "DAY" + separator + "HOUR";
		
		_DATE = getTimeFromFileName(file.getName(), "[0-9]{8}[0-9]{2}[0-9]{2}[0-9]{2}");
		
		getDateTime(_DATE);
		
		BufferedWriter writer = null;
		
		boolean first = true;
		
		String ObjectType = "";
		
		try {

			List<String> output = new ArrayList<String>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			
			// Get block
			NodeList rList = document.getElementsByTagName("PMMOResult");		// List node PMMOResult
			
			int rNodeLength = rList.getLength();
			
			// Xu ly moi node
			for (int i = 0; i< rNodeLength; i++) {
				Node PMMOResultNode = rList.item(i);
				
				String strLine = "";
				
				// Get CDATA
				if (PMMOResultNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) PMMOResultNode;
					
					// MO
					NodeList MOList = element.getElementsByTagName("MO");
					
					String cdata = "";
					
					for (int j=0; j< MOList.getLength(); j++) {
	                	NodeList MO = MOList.item(j).getChildNodes();
	                    
	                	if (!cdata.equals(""))
	                		break;
	                	
	                    for (int k = 0; k < MO.getLength(); k++) {
	                    	Node DN = MO.item(k);
	                    	String dnTxt = DN.getTextContent().trim();
	                    	if (!dnTxt.equals("")) {
	                    		cdata = dnTxt;
	                    		break;
	                    	}
	                    }
	                }
					
					// Get RNC, BTS
					strLine = getHeaderValue(cdata, separator);
					
					strLine += separator + _DAY + separator + _HOUR;
					
					// PMTarget
					NodeList PMTargetList = element.getElementsByTagName("PMTarget");
					
					NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();
					
					if (first) {
						Element PMTargetElement = (Element) PMTargetNode;
						ObjectType = PMTargetElement.getAttribute("measurementType");
						
						//first = true;
					}
	                
					for (int n=0; n< PMTargetNode.getLength(); n++) {
						if (n%2 == 0)
							continue;
						else {
							if (first) {
								lineHeader += separator + PMTargetNode.item(n).getNodeName();
							}
							strLine += separator + PMTargetNode.item(n).getTextContent();
						}
					}
					first = false;
					
					output.add(strLine);
				}
			}
			
			// Outfile: ObjectType + '.' + file_name
			String _NEW_FILE = ObjectType.toLowerCase() + "." + file.getName();
			String _TABLE = "R_N_3G_" + ObjectType;
			
			File outfile = new File(direcPath, _NEW_FILE);
			writer = new BufferedWriter(new FileWriter(outfile));
			
			// write file
			writer.write(lineHeader);
			writer.newLine();
			for (String item: output) {
				writer.write(item);
				writer.newLine();
			}
			
			writer.close();
			
			try {
				genarateDBRecord(_NEW_FILE, _TABLE, conn);
				
				updateRecordStatus(conn);
				
			} catch (SQLException e) {
				logger.error(e, e);
			}
			
		}
		catch(Exception e) {
			logger.debug(e);
			throw new STS_ConvertException(e.getMessage(), "GNOS-0306", e);
		}
		
	}	
	
	private void genarateDBRecord(String fileName, String tableName, Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, SERVER_NODE, SUB_DIR from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
		String patternId = "";
		String nodeName = "";
		String serverNode = "";
		String folder = "";
		//
		ResultSet rs = st.executeQuery(queryStr);
		while (rs.next()) {
			patternId = rs.getString("PATTERN_ID");;
			nodeName = rs.getString("NODE_NAME");
			serverNode = rs.getString("SERVER_NODE");
			folder = rs.getString("SUB_DIR");
		}
		if (patternId.length() > 0) {
			queryStr = "insert /*+ NOLOGGING */ into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
					+ "NODE_NAME, RAW_TABLE, SUB_DIR, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName 
					+ "',1, 0, '" + nodeName + "', '" + tableName + "', '" + folder +  "', '" 
					+serverNode + "')";
 
			st.execute(queryStr);
		}

		rs.close();
		st.close();
	}
	
	private void updateRecordStatus(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		// update state
		String queryStr = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
		st.execute(queryStr);
		st.close();
	}	
}
