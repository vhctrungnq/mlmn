package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/******************************************************************************
NAME:       InventoryNokiaConverter
PURPOSE:	Convert file inventory nokia RNC, BTS, SGSN

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        07/10/2013       AnhNT      	1.
******************************************************************************/
public class InventoryNokiaConverter extends IN_BasicConverter {
	
	private String sep = ";";
	private int fileId = -1;
	private static Logger logger = Logger.getLogger(InventoryNokiaConverter.class.getName());
	
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(IN_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(IN_Setting.FILE_ID_KEY));
		}
		
		BufferedWriter writer = null; 
		BufferedWriter writerSupport = null;
		String day = "";
		String neId = "";
		String neType = "";
		String moid_fuut = "",functionalUnitId = "",functionalUnitType="";
		String fileName = "";
		fileName = file.getName().substring(file.getName().indexOf("_") + 1, file.getName().length());
		try {
			File outFile = new File(direcPath, file.getName()); 
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName()))); 
			writerSupport = new BufferedWriter(new FileWriter(outFile.getParent()+"/SUPPORT_"+getFileName(outFile.getName())));
			writer.write("#day;vendor;neid;netype;moid;unitTypeActual;unitTypeExpected;position;serialNumber;identificationCode;fileName\n");
			writerSupport.write("#day;moid_fuut;functionalUnitId;functionalUnitType;moid_support;fileName\n");
			
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			// parse file
			Document doc = docBuilder.parse(file);
			
			Node header_node = doc.getElementsByTagName("Header").item(0);
			Element header_element = (Element) header_node;
			day = header_element.getAttribute("time").substring(0, 10);
			
			Node ne_node = doc.getElementsByTagName("NE").item(0);
			Element ne_element = (Element) ne_node;
			neId = ne_element.getAttribute("NEId");
			neType = ne_element.getAttribute("NEType");
			
			if(neType.contains("BSC")){
				neType = "BSC";
			}else if(neType.contains("WBTS")){
				neType = "NODEB";
			}
			
			NodeList ne_nodeList = ne_node.getChildNodes(); 
			//Duyet tat bo node con thuoc node cha "<NE"
			for(int i = 0; i < ne_nodeList.getLength(); i++){
				// Doc du lieu node con thu k
				Node ne_nodeChild = ne_nodeList.item(i);  
				if (ne_nodeChild.getNodeType() == 3) {
					continue;
				}  
				
				if (ne_nodeChild != null && ne_nodeChild.getNodeName().equalsIgnoreCase("EQHO")) {
					// Lay gia tri trong the EQHO
					if(neType.equals("SGSN")){
						getDataEqhoTag(ne_nodeChild, day, neType, neId, writer, fileName);
					}
					NodeList eqho_nodeList = ne_nodeChild.getChildNodes();
					for(int j = 0; j < eqho_nodeList.getLength(); j++){
						Node eqho_nodeChild = eqho_nodeList.item(j);  
						if (eqho_nodeChild.getNodeType() == 3) {
							continue;
						} 
						
						
						if(eqho_nodeChild != null && eqho_nodeChild.getNodeName().equalsIgnoreCase("UNIT")){
							String moid = "";
							String unitTypeActual = "";
							String unitTypeExpected = "";
							String position = "";
							String serialNumber = "";
							String identificationCode = "";
							
							Element unit_element = (Element) eqho_nodeChild;
							moid = unit_element.getAttribute("MOID");
							unitTypeActual = unit_element.getAttribute("unitTypeActual");
							unitTypeExpected = unit_element.getAttribute("unitTypeExpected"); 
							serialNumber = unit_element.getAttribute("serialNumber");
							identificationCode = unit_element.getAttribute("identificationCode"); 
							
							position = moid.split("/")[3];
							
							String line = day+sep+"NOKIA SIEMENS"+sep+neId+sep+neType+sep +moid +sep+unitTypeActual+sep +unitTypeExpected +sep +position.substring(position.indexOf("-")+1, position.length()) +sep +serialNumber +sep + identificationCode + sep + fileName;
							writer.write(line);  
							writer.newLine(); 
							continue;
						}
						
						if(eqho_nodeChild != null && eqho_nodeChild.getNodeName().equalsIgnoreCase("EQHO")){
							extractMeasDataEqho(day,neType,neId,writer, eqho_nodeChild, fileName);
							continue;
						}
						
					} 
				}
				
				if (ne_nodeChild != null && ne_nodeChild.getNodeName().equalsIgnoreCase("FUUT")) {
					moid_fuut = ""; functionalUnitId = ""; functionalUnitType="";
					Element fuut_node = (Element) ne_nodeChild;
					
					moid_fuut = fuut_node.getAttribute("MOID");
					functionalUnitId = fuut_node.getAttribute("functionalUnitId");
					functionalUnitType = fuut_node.getAttribute("functionalUnitType");
					try{
						extractMeasDataFuut(day,neType,neId,moid_fuut,functionalUnitId,functionalUnitType,writerSupport, ne_nodeChild, fileName);
					}catch(Exception e){
					}
				}
			} 
			this.genarateDBRecord(getFileName(outFile.getName()), "R_N_ISO_INVENTORY");
			this.genarateDBRecord("SUPPORT_"+getFileName(outFile.getName()), "R_N_ISO_INVENTORY_SUPPORT"); 
			this.updateRecordStatus();
		} catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
		}finally {
			try {
				writer.close();
				writerSupport.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		logger.info("Convert file: " + file.getName() + " success");
	}

	private void getDataEqhoTag(Node eqho, String day, String neType,String neId, BufferedWriter writer, String fileName){
		String moid = "";
		String unitTypeActual = "";
		String unitTypeExpected = "";
		String position = "";
		String serialNumber = "";
		String identificationCode = "";
		Element row_element = (Element) eqho;
		moid = row_element.getAttribute("MOID");
		unitTypeActual = row_element.getAttribute("unitTypeActual");
		unitTypeExpected = row_element.getAttribute("unitTypeExpected");
		position = row_element.getAttribute("position");
		serialNumber = row_element.getAttribute("serialNumber");
		identificationCode = row_element.getAttribute("identificationCode"); 
		
		String line = day+sep+"NOKIA SIEMENS"+sep+neId+sep+neType+sep +moid +sep+unitTypeActual+sep +unitTypeExpected +sep +position +sep +serialNumber +sep + identificationCode + sep + fileName;
		try {
			writer.write(line);
			writer.newLine();
		} catch (IOException e) { 
			e.printStackTrace();
		}  
		
		
	}
	private void extractMeasDataEqho(String day, String neType,String neId, BufferedWriter writer, Node measData, String fileName) throws Exception {
		// Doc node con "<EQHO" thuoc node cha measData
		//Node measInfo = getChildByNodeName(measData, "EQHO");
		
		String moid = "";
		String unitTypeActual = "";
		String unitTypeExpected = "";
		String position = "";
		String serialNumber = "";
		String identificationCode = "";

		NodeList nodeList = measData.getChildNodes(); 
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node n = nodeList.item(index);
			if (n.getNodeType() == 3) {
				continue;
			}
			
			if(n != null && n.getNodeName().equalsIgnoreCase("UNIT")){
				Element row_element = (Element) n;
				moid = row_element.getAttribute("MOID");
				unitTypeActual = row_element.getAttribute("unitTypeActual");
				unitTypeExpected = row_element.getAttribute("unitTypeExpected");
				position = row_element.getAttribute("position");
				serialNumber = row_element.getAttribute("serialNumber");
				identificationCode = row_element.getAttribute("identificationCode"); 
				
				String line = day+sep+"NOKIA SIEMENS"+sep+neId+sep+neType+sep +moid +sep+unitTypeActual+sep +unitTypeExpected +sep +position +sep +serialNumber +sep + identificationCode + sep + fileName;
				writer.write(line);  
				writer.newLine();
			}
		}    
	}

	private void extractMeasDataFuut(String day,String neType, String neId, String moid_fuut,
			String functionalUnitId, String functionalUnitType, BufferedWriter writerSupport, Node measData, String fileName) throws Exception {
		// Doc node con "<supportedByUnitList" thuoc node cha measData
		Node measInfo = getChildByNodeName(measData, "supportedByUnitList");
		
		String moid_support = ""; 

		NodeList nodeList = measInfo.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node n = nodeList.item(index);
			if (n.getNodeType() == 3) {
				continue;
			}
			
			if(n != null && n.getNodeName().equalsIgnoreCase("MOID")){
				Element row_element = (Element) n;
				moid_support = row_element.getTextContent();
				
				String line = day+sep+moid_fuut+sep+functionalUnitId+sep +functionalUnitType +sep+moid_support + sep + fileName;
				writerSupport.write(line); 
				writerSupport.newLine();
			}
		}    
	}
	
	private Node getChildByNodeName(Node node, String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node n = nodeList.item(index);
				if (n != null && n.getNodeName().equalsIgnoreCase(name)) {
					return n ;
				}
			}
		}
		return null;
	}
	
	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = IN_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
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
			conn = IN_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, MODULE, SERVER_NODE from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
			String patternId = "";
			String day = "";
			String hour = "";
			String nodeName = "";
			String module = "";
			String serverNode = "";
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				patternId = rs.getString("PATTERN_ID");
				day = IN_Util.getTime(rs.getDate("DAY"));
				hour = rs.getString("HOUR");
				nodeName = rs.getString("NODE_NAME");
				module = rs.getString("MODULE");
				serverNode = rs.getString("SERVER_NODE");
			}
			if (patternId.length() > 0) {
				queryStr = "insert into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
						+ "NODE_NAME, RAW_TABLE, MODULE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
						+ IN_Setting.DB_TIME_FORMAT + "'), " + hour + ", 1, 0, '" + nodeName + "', '" + tableName + "','" + module + "', '" + serverNode + "')";

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
