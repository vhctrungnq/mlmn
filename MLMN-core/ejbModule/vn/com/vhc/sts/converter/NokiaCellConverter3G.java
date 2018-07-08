package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.model.Nokia3gStruct;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/**
 * Convert Nokia Siement Cell 3G
 * @author Mr.DatNH
 *
 */

/**
 * 
 * Update by TRUNGNQ 10/11/2017 xu ly truong hop  
 * cac node (khoi) measurementType co so luong node (counter) khac nhau 
 * chi lay cac counter dung trong cong thuc tinh kpi
 * Khi can them moi counter vao mot bang, dau tien them field vao class Nokia3gStruct
 * sau do sua lai cac method toString va createHeader cua cac class tuong ung
 * o package vn.com.vhc.sts.model
 */
public class NokiaCellConverter3G extends STS_BasicConverter {

	private static Logger logger = Logger.getLogger(NokiaCellConverter3G.class.getName());
	
	private final String _OBJECT_LIST = "CELL_RESOURCE, TRAFFIC, CELL_THRPUT, CELL_THROUGHPUT_WBTS, HSDPA_WBTS, SERVICE_LEVEL, PACKET_CALL, INTER_SYSTEM_HANDOVER, SOFT_HANDOVER";
	
	private static String _PREFIX_TABLE = "R_N_3G_";
	private Nokia3gStruct nokia3g;
	//private static String _DAY;
	//private static String _HOUR;
	
	private int fileId = -1;

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		
		/*prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		String lineHeaderFirst = commentChar + "OBJECTNAME" + separator + "DAY" + separator + "HOUR";
		String lineHeaderLast = commentChar + "OBJECTNAME" + separator + "DAY" + separator + "HOUR";
		
		BufferedWriter writer = null;
		
		boolean first = true;
		
		String _OBJECTTYPE = "";
		
		try {

			List<String> output = new ArrayList<String>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			
			// Node PMSetup
			Node PMSetupNode = document.getElementsByTagName("PMSetup").item(0); 
			Element PMSetupElement = (Element) PMSetupNode.getChildNodes();
			String startTime = PMSetupElement.getAttribute("startTime");
			
			String _DAYHOUR = getDayHour(startTime, separator);
			
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
					
					// Get RNC, BTS, CELL
					//strLine = getHeaderValue(cdata, separator);			// test error?
					strLine = cdata;
					
					//strLine += separator + _DAY + separator + _HOUR;
					strLine += _DAYHOUR;
					
					// PMTarget
					NodeList PMTargetList = element.getElementsByTagName("PMTarget");
					
					NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();
					
					if (first) {
						Element PMTargetElement = (Element) PMTargetNode;
						_OBJECTTYPE = PMTargetElement.getAttribute("measurementType");
						
						_OBJECTTYPE = _OBJECTTYPE.toUpperCase();
						
						// Bo qua file khong su dung de tong hop cong thuc
						if (_OBJECT_LIST.indexOf(_OBJECTTYPE) < 0) {
							logger.info("Convert file " + file.getName() + " are ignored");
						 	updateRecordStatus();
							return;
						}
						
					}
	                int j = 0;
					for (int n=0; n< PMTargetNode.getLength(); n++) {
						if (n%2 == 0)
							continue;
						else {
							if (first) {
								j++;
								lineHeaderLast += separator + "M"+j;
								lineHeaderFirst += separator + PMTargetNode.item(n).getNodeName();
							}
							strLine += separator + PMTargetNode.item(n).getTextContent();
						}
					}
					first = false;
					
					output.add(strLine);
				}
			}
			
			// Outfile: ObjectType + '.' + file_name
			String _NEW_FILE = _OBJECTTYPE + "." + file.getName();
			String _TABLE = _PREFIX_TABLE + _OBJECTTYPE;
			
			File outfile = new File(direcPath, _NEW_FILE);
			writer = new BufferedWriter(new FileWriter(outfile));
			
			// write file
			writer.write(lineHeaderLast);
			writer.newLine();
			writer.write(lineHeaderFirst);
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
	
	// etlexpmx_WCEL_20120214030123_1121491
	/*public void getDateTime(String date) {
		_DAY = date.substring(0, 8);
		_HOUR = date.substring(9, 10);
	}*/
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params, Connection conn) 
			throws STS_ConvertException {
		
		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);				//set Directory
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		//String lineHeaderFirst = commentChar + "OBJECTNAME" + separator + "DAY" + separator + "HOUR";
		String lineHeader = commentChar + "OBJECTNAME" + separator + "TIME";
		
		BufferedWriter writer = null;
		
		boolean first = true;
		String rncid = "";
		String _OBJECTTYPE = "";
		Class<Nokia3gStruct> nokia3gClass = Nokia3gStruct.class;
		
		try {

			List<String> output = new ArrayList<String>();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			
			// Node PMSetup
			Node PMSetupNode = document.getElementsByTagName("PMSetup").item(0); 
			Element PMSetupElement = (Element) PMSetupNode.getChildNodes();
			String startTime = PMSetupElement.getAttribute("startTime");

			String _DAYHOUR = getTime(startTime, separator);
			
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
					
					String tmp = cdata.substring(0, cdata.indexOf("/WBTS"));
					if (!rncid.contains(tmp)) {
						
						if (rncid.length() > 0) {
							tmp = "," + tmp;
						}
						rncid += tmp;
					}
					strLine = cdata;
					
					//strLine += separator + _DAY + separator + _HOUR;
					strLine += _DAYHOUR;
					
					// PMTarget
					NodeList PMTargetList = element.getElementsByTagName("PMTarget");
					
					NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();
					
					if (first) {
						Element PMTargetElement = (Element) PMTargetNode;
						_OBJECTTYPE = PMTargetElement.getAttribute("measurementType");
						
						_OBJECTTYPE = _OBJECTTYPE.toUpperCase();
						
						// Bo qua file khong su dung de tong hop cong thuc
						if (_OBJECT_LIST.indexOf(_OBJECTTYPE) < 0) {
							logger.info("Convert file " + file.getName() + " are ignored");
						 	updateRecordStatus(conn);
							return;
						}
						
						nokia3g = Nokia3gStruct.getModel(_OBJECTTYPE);
						lineHeader += separator + nokia3g.createHeader(separator);				
						
					}
					
	           //     int j = 0;
	                
	                // xu ly moi counter
					for (int n=0; n< PMTargetNode.getLength(); n++) {
						if (n%2 == 0)
							continue;
						else {
							
							//strLine += separator + PMTargetNode.item(n).getTextContent();
							String counterName = PMTargetNode.item(n).getNodeName();
							
							// chi lay nhung counter dung trong khong thuc tong hop
							if (Arrays.asList(nokia3g.getFieldList().split(",")).contains(counterName)) {
								Field counter = nokia3gClass.getField(counterName);
								counter.set(nokia3g, PMTargetNode.item(n).getTextContent());
							}
							
							
						}
					}
					first = false;
					strLine += separator + nokia3g.toString(separator);
					output.add(strLine);
				}
			}
			
			// Outfile: ObjectType + '.' + file_name
			String _NEW_FILE = rncid.replaceAll("/", ".") + "_" +  _OBJECTTYPE + "." + file.getName();
			String _TABLE = _PREFIX_TABLE + _OBJECTTYPE;
			
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
	
	// 2012-02-14T23:00:00.000+07:00:00
	public String getTime(String date, String separator) {
		
		String regex = "[-T:]";
		
		Pattern p = Pattern.compile(regex);
	    String[] items = p.split(date);
	    
	    return separator + items[0] + items[1] + items[2] + items[3];
	}
	
	// Convert 'PLMN-PLMN/RNC-14/WBTS-65/WCEL-2'
	public String getHeaderValue(String cdata, String sep) {

		String value[] = cdata.split("/");
		
		String RNCID = value[1].split("-")[1];
		String BTSID = value[2].split("-")[1];
		String CELLID = value[3].split("-")[1];
		
		String headerValue = RNCID + sep + BTSID + sep + CELLID;
		
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

	
	private void genarateDBRecord(String fileName, String tableName, Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR,  SERVER_NODE, SUB_DIR from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
		String patternId = "";
		String nodeName = fileName.substring(0, fileName.indexOf("_") - 1);
		String serverNode = "";
		String folder = "";
		ResultSet rs = st.executeQuery(queryStr);
		while (rs.next()) {
			patternId = rs.getString("PATTERN_ID");;
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
