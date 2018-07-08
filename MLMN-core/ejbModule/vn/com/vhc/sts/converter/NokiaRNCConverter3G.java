package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
/**
 * 
 * @author Mr.DatNH. Update by ThangPX
 * @category Convertfile RNC, WBTS vendor Nokiasiemens
 * @param Input files (*.xml)
 * @return Output files(*.xml)
 *
 */
public class NokiaRNCConverter3G extends STS_BasicConverter {

	private static Logger logger = Logger.getLogger(NokiaBTSConverter3G.class.getName());
	private final String _OBJECT_LIST = "CELL_RESOURCE,TRAFFIC,CELL_THRPUT,CELL_THROUGHPUT_WBTS,HSDPA_WBTS,SERVICE_LEVEL,PACKET_CALL,INTER_SYSTEM_HANDOVER,SOFT_HANDOVER,IP_BASED_ROUTE,WBTS_HW,WBTS_R99_HW,UNIT_LOAD,RNC_CAPA_USAGE,FRAME_PROTOCOL_WBTS";
	private static String _PREFIX_TABLE = "R_N_3G_";
	private int fileId = -1;
	private static String _DATE;
	private static String _DAY;
	private static String _HOUR;
	
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
/*		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		//Create header
		String lineHeader = commentChar + "OBJECTNAME" + separator + "TIME";
		
		
		String _OBJECTTYPE = "";
		boolean flag = true;
		try {

			List<String> output = new ArrayList<String>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			//Get time
			NodeList timeNode = document.getElementsByTagName("PMSetup");
			Element eTime = (Element)timeNode.item(0);
			_DATE = getTimeFromFileName(eTime.getAttribute("startTime"), "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}");
			getDateTime(_DATE);
			
			// Get block
			NodeList rList = document.getElementsByTagName("PMMOResult");		// List node PMMOResult
			int rNodeLength = rList.getLength();
			// Get data
			for(int i=0; i < rNodeLength; i++)
			{
				
				Node PMMOResultNode = rList.item(i);
				if(PMMOResultNode.getNodeType()==3)
					continue;
			//	System.out.println("PMMOResultNode: "+PMMOResultNode.getNodeType());
				String strLine = "";
				if (PMMOResultNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) PMMOResultNode;
				String cdata = "";
				//Get Mo
				NodeList MOList = element.getElementsByTagName("MO");
				for (int j=0; j< MOList.getLength(); j++) {
					Node MONode = MOList.item(j);
					if(MONode.getNodeType()==3)
						continue;
                	NodeList MO = MOList.item(j).getChildNodes();
                	
                    if (!cdata.equals(""))
                		break;
                	for (int k = 0; k < MO.getLength(); k++) {
                    	Node DN = MO.item(k);
                    	if (DN.getNodeType()!=3) {
                    		cdata = DN.getTextContent().trim();
                    		break;
                    	}
                    }
                }
				strLine = cdata;
				strLine += separator + _DAY  +" "+ _HOUR;
				// PMTarget
				NodeList PMTargetList = element.getElementsByTagName("PMTarget");
				NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();
				if(i==0)
				{
					Element PMTargetElement = (Element) PMTargetNode;
					_OBJECTTYPE = PMTargetElement.getAttribute("measurementType");
					_OBJECTTYPE = _OBJECTTYPE.toUpperCase();
					
					if (_OBJECT_LIST.indexOf(_OBJECTTYPE) < 0) {
						logger.info("File: " + file.getName() + " is ignored");
						updateRecordStatus();
						break;
					}
					flag = false;
				}
				if(flag==false)
				{
					for (int n=0; n< PMTargetNode.getLength(); n++) {
						Node n1 = PMTargetNode.item(n);
						if (n1.getNodeType()==3)
							continue;
						else {
							if(i==0)
								lineHeader += separator + PMTargetNode.item(n).getNodeName();
							strLine += separator + PMTargetNode.item(n).getTextContent();
						}
					}
					output.add(strLine);
					
				}
					
			}
				
				
			}
			if(flag==false)
			{
			
				// Outfile: ObjectType + '.' + file_name
				String fileName = _OBJECTTYPE + "." + getFileName(file.getName());
				String _TABLE = _PREFIX_TABLE + _OBJECTTYPE;
				
				File outFile = new File(direcPath, fileName);
				if (outFile.exists()) {
					outFile.delete();
				}
				
				writeLine(outFile,lineHeader);
				for (String item: output) {
					writeLine(outFile,item);
				}
				
				try {
					logger.info("Convert success file : "+fileName);
					genarateDBRecord(fileName, _TABLE);
					updateRecordStatus();
					
					
				} catch (SQLException e) {
					logger.error("Can not insert/update to database");
				}
			}
		}
		catch(Exception e) {
			logger.debug(e);
			throw new STS_ConvertException(e.getMessage(), "GNOS-0306", e);
		}*/
	}
	/*------------------------------------------*/
	private void writeLine(File outFile, String line) throws IOException {
		// write data
		if (line != null && line.length() > 0) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
			writer.append(line);
			writer.newLine();
			writer.flush();
			writer.close();
		}
	}
	//Insert to database files valid
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
//	//Update to database file invalid
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
	
	// startTime="2013-11-18T12:30"
	
	public void getDateTime(String date) {
		String[] str = date.split("T");
		_DAY = str[0];
		_HOUR = str[1];
	}
	// Convert 'PLMN-PLMN/RNC-14'
	public String getHeaderValue(String cdata, String sep) {
		String objectName = cdata.substring(cdata.indexOf("DATA["),cdata.indexOf("]]"));
		String headerValue = objectName;
		return headerValue;

	}
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params, Connection conn) 
			throws STS_ConvertException {
		

		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		//Create header
		String lineHeader = commentChar + "OBJECTNAME" + separator + "TIME";
		
		
		String _OBJECTTYPE = "";
		boolean flag = true;
		try {

			List<String> output = new ArrayList<String>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			//Get time
			NodeList timeNode = document.getElementsByTagName("PMSetup");
			Element eTime = (Element)timeNode.item(0);
			_DATE = getTimeFromFileName(eTime.getAttribute("startTime"), "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}");
			getDateTime(_DATE);
			
			// Get block
			NodeList rList = document.getElementsByTagName("PMMOResult");		// List node PMMOResult
			int rNodeLength = rList.getLength();
			// Get data
			for(int i=0; i < rNodeLength; i++)
			{
				
				Node PMMOResultNode = rList.item(i);
				if(PMMOResultNode.getNodeType()==3)
					continue;
			//	System.out.println("PMMOResultNode: "+PMMOResultNode.getNodeType());
				String strLine = "";
				if (PMMOResultNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) PMMOResultNode;
				String cdata = "";
				//Get Mo
				NodeList MOList = element.getElementsByTagName("MO");
				for (int j=0; j< MOList.getLength(); j++) {
					Node MONode = MOList.item(j);
					if(MONode.getNodeType()==3)
						continue;
                	NodeList MO = MOList.item(j).getChildNodes();
                	
                    if (!cdata.equals(""))
                		break;
                	for (int k = 0; k < MO.getLength(); k++) {
                    	Node DN = MO.item(k);
                    	if (DN.getNodeType()!=3) {
                    		cdata = DN.getTextContent().trim();
                    		break;
                    	}
                    }
                }
				strLine = cdata;
				strLine += separator + _DAY  +" "+ _HOUR;
				// PMTarget
				NodeList PMTargetList = element.getElementsByTagName("PMTarget");
				NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();
				if(i==0)
				{
					Element PMTargetElement = (Element) PMTargetNode;
					_OBJECTTYPE = PMTargetElement.getAttribute("measurementType");
					_OBJECTTYPE = _OBJECTTYPE.toUpperCase();
					
					if (_OBJECT_LIST.indexOf(_OBJECTTYPE) < 0) {
						logger.info("File: " + file.getName() + " is ignored");
						updateRecordStatus(conn);
						break;
					}
					flag = false;
				}
				if(flag==false)
				{
					for (int n=0; n< PMTargetNode.getLength(); n++) {
						Node n1 = PMTargetNode.item(n);
						if (n1.getNodeType()==3)
							continue;
						else {
							if(i==0)
								lineHeader += separator + PMTargetNode.item(n).getNodeName();
							strLine += separator + PMTargetNode.item(n).getTextContent();
						}
					}
					output.add(strLine);
					
				}
					
			}
				
				
			}
			if(flag==false)
			{
			
				// Outfile: ObjectType + '.' + file_name
				String fileName = _OBJECTTYPE + "." + getFileName(file.getName());
				String _TABLE = _PREFIX_TABLE + _OBJECTTYPE;
				
				File outFile = new File(direcPath, fileName);
				if (outFile.exists()) {
					outFile.delete();
				}
				
				writeLine(outFile,lineHeader);
				for (String item: output) {
					writeLine(outFile,item);
				}
				
				try {
					logger.info("Convert success file : "+fileName);
					genarateDBRecord(fileName, _TABLE, conn);
					updateRecordStatus(conn);
					
					
				} catch (SQLException e) {
					logger.error("Can not insert/update to database");
				}
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
