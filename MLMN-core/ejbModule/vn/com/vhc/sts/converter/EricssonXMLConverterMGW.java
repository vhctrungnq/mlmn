package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class EricssonXMLConverterMGW extends STS_BasicConverter {

	private List<DataMap> maps = new ArrayList<DataMap>();

	private String header = "";
	private int fileId = -1;
	private String lineMore[] = new String[13];
	private String sep = ";";
	private static Logger logger = Logger.getLogger(EricssonXMLConverterMGW.class.getName());

	public EricssonXMLConverterMGW() {
		loadDataMapping();
	}
	private void loadDataMapping() {
		maps.add(new DataMap(
				"MGWAPPLICATION",
				"pmNrOfEmergencyCalls,pmNrOfMediaStreamChannelsBusy",
				"R_E_MGWAPPLICATION_CORE",0));
		maps.add(new DataMap(
				"VMGW",
				"pmGcpNrOfReceivedMessages,pmGcpNrOfSentMessages",
				"R_E_VMGW_CORE",1));
		maps.add(new DataMap(
				"TDMTERMGRP",
				"pmNrOfTdmTermsReq,pmNrOfTdmTermsRej",
				"R_E_TDMTERMGRP_CORE",2));
		maps.add(new DataMap(
				"GIGABITETHERNET",
				"pmDot1qTpVlanPortInDiscardsLink1,pmDot1qTpVlanPortInDiscardsLink2",
				"R_E_GIGABITETHERNET_CORE",3));
		maps.add(new DataMap(
				"IPACCESSHOSTGPB",
				"pmIcmpOutParmProbs,pmIpFragOKs",
				"R_E_IPACCESSHOSTGPB_CORE",4));
		maps.add(new DataMap(
				"MSCPOOLPROXY",
				"pmTotalNoOfMscSelBasedOnRC,pmTotalNoOfMscSelBasedOnReSel",
				"R_E_MSCPOOLPROXY_CORE",5));
		maps.add(new DataMap(
				"SCCPSP",
				"pmNoOfConInUseExceedHighWaterMark,pmNoOfConInUseReceededLowWaterMark",
				"R_E_SCCPSP_CORE",6));
		maps.add(new DataMap(
				"JITTERHAND",
				"pmAtmCnConnLatePktsRatio0,pmAtmCnConnLatePktsRatio1",
				"R_E_JITTERHAND_CORE",7));
		maps.add(new DataMap(
				"IMBASICMESSAGE",
				"pmCallAttempts,pmFailedCallAttempts",
				"R_E_IMBASICMESSAGE_CORE",8));
		maps.add(new DataMap(
				"TFOSERVICE",
				"pmTfoAmrNbDroppedCalls,pmTfoAmrNbEndPointMode",
				"R_E_TFOSERVICE_CORE",9));
		maps.add(new DataMap(
				"IPETSERVICE",
				"pmBusyUnitsAmrWb,pmForcedRelease",
				"R_E_IPETSERVICE_CORE",10));
		maps.add(new DataMap(
				"IPINTERFACE",
				"pmDot1qTpVlanPortInFrames,pmDot1qTpVlanPortOutFrames",
				"R_E_IPINTERFACE_CORE",11));
		maps.add(new DataMap(
				"PLUGINUNIT",
				"pmProcessorLoad,userlabel",
				"R_E_PLUGINUNIT_CORE",12));
	}
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);

		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}

		try {
			int blockCount = 0;
			//
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}
			});
			// parse file
			Document doc = docBuilder.parse(file);

			Node parentNode = doc.getElementsByTagName("mdc").item(0);
			NodeList nodeList = parentNode.getChildNodes();
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node node = nodeList.item(index);
				if (node.getNodeType() == 3) {
					continue;
				}
				if (node != null && node.getNodeName().equalsIgnoreCase("md")) {
					try {
						blockCount += extractMeasData(direcPath, file.getName(), node);
					} catch (Exception e) {
						logger.warn("Extract data faiule in node at index: " + index);
					}
				}
			}

			if (blockCount == 0) {
				throw new STS_ConvertException("Not found necessary data in the file");
			}
            if(blockCount > 0)
            	this.updateRecordStatus();
		} catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "gnos-0306", e);
		}

		logger.info("Convert file: " + file.getName() + " success");
	}

	private int extractMeasData(String direcPath, String fName, Node measData) throws Exception {
		Node measInfo = getChildByNodeName(measData, "mi");
		if (measInfo == null) {
			return 0;
		}

		String timestamp = "";
		List<String> measTypes = new ArrayList<String>();
		List<Node> measValues = new ArrayList<Node>();

		NodeList nodeList = measInfo.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node n = nodeList.item(index);
			if (n.getNodeType() == 3) {
				continue;
			}
			// meastype
			if (n.getNodeName().equalsIgnoreCase("mt")) {
				measTypes.add(n.getTextContent());
			}
			// time
			if (n.getNodeName().equalsIgnoreCase("mts")) {
				timestamp = n.getTextContent();
				timestamp = timestamp.substring(0, timestamp.length() - 1);
				timestamp = this.getTimestamp(timestamp);
			}
			// value
			if (n.getNodeName().equalsIgnoreCase("mv")) {
				measValues.add(n);
			}
		}
		// validate meastype
		String key = this.getKey(measTypes);
		if (key == null) {
			return 0;
		}
		if (measValues.size() == 0) {
			return 0;
		}

		String fileName = key + "_" + getFileName(fName);
		File outFile = new File(direcPath, fileName);
		if (outFile.exists()) {
			outFile.delete();
		}
		String mscId = this.getMSCId(measData);
		String newLine  = "";
		int indexCount = -1;
		
	    if(key != "")
		{
			indexCount = this.getIndex(measTypes);
		}
			int count = 0;
			if(indexCount > -1)
			{
				
				if(key == "PLUGINUNIT")
				{
					header = "MSCID" + sep + "TIME" + sep + "SLOT"+ sep + "BOARD";
					for (String s : measTypes) {
						if (header.length() > 0) {
							header += sep;
						}
						header += s;
					}
					
					for (Node n : measValues) {
						try {
							String strLine = this.getMeasValueProcessor(n);
							lineMore[indexCount] = lineMore[indexCount] + mscId + sep + timestamp + sep + strLine + sep + newLine ;
							lineMore[indexCount] = lineMore[indexCount].replace("null", "");
							lineMore[indexCount] += "\n";
							
						} catch (Exception e) {
						}
					}
				}
				else if(key == "VMGW")
				{
					header = "MSCID" + sep + "TIME" + sep + "USERLABLE";
					for (String s : measTypes) {
						if (header.length() > 0) {
							header += sep;
						}
						header += s;
					}
					
					for (Node n : measValues) {
						try {
							String strLine = this.getValuesVMGW(n);
							lineMore[indexCount] = lineMore[indexCount] + mscId + sep + timestamp + sep + strLine + sep + newLine ;
							lineMore[indexCount] = lineMore[indexCount].replace("null", "");
							lineMore[indexCount] += "\n";
							
						} catch (Exception e) {
						}
					}
				}
				else
				{
					header = "MSCID" + sep + "TIME";
					
					for (String s : measTypes) {
						if (header.length() > 0) {
							header += sep;
						}
						header += s;
					}
					for (Node n : measValues) {
						try {
							newLine = this.getValues(n);
								lineMore[indexCount] = lineMore[indexCount] + mscId + sep + timestamp + sep + newLine ;
								lineMore[indexCount] = lineMore[indexCount].replace("null", "");
								lineMore[indexCount] += "\n";
								//lineMore[indexCount] = lineMore[indexCount].replaceAll("[ \t]+(\r\n?|\n)", "$1");
								
						} catch (Exception e) {
						}
					}
				}
					
				String line = lineMore[indexCount];
			
				writeLine(outFile, line);
				count ++;
			}
	// insert a record into database
			if(count == 1)
			{
				this.genarateDBRecord(fileName, getTableName(key));
			}
				
		
		return 1;
	}
	private String getMeasValueProcessor(Node measValue) {
		String value = "";

		NodeList nodeList = measValue.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() == 3) {
				continue;
			}

			if (node.getNodeName().equalsIgnoreCase("moid")) {
				if (value.length() > 0) {
					value += sep;
				}
				value += this.getSlotAndBoard(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("r")) {
				if (value.length() > 0) {
					value += sep;
				}
				value += node.getTextContent();
				
			}
		}
		return value;
	}
	private String getSlotAndBoard(String moid)
	{
		String[] str = moid.split(",");
		
		 String slot = getMoid(str[3]);
		 String board = getMoid(str[2]);
		 return slot + sep + board;
	}
	
	private String getKey(List<String> types) {
		String key = null;
		if (this.maps.size() > 0) {
			for (DataMap dm : maps) {
				if (this.validateMeasType(types, dm.getMeasTypeList())) {
					key = dm.getKey();
					break;
				}
			}
		}
		return key;
	}
	private int getIndex(List<String> types) {
		int i = -1;
		if (this.maps.size() > 0) {
			for (DataMap dm : maps) {
				if (this.validateMeasType(types, dm.getMeasTypeList())) {
					i = dm.getintdex();
					break;
				}
			}
		}
		return i;
	}
	private String getTableName(String key) {
		String tableName = "";
		if (this.maps.size() > 0) {
			for (DataMap dm : maps) {
				if (dm.getKey().equalsIgnoreCase(key)) {
					tableName = dm.getTableName();
					break;
				}
			}
		}
		return tableName;
	}

	private String getMSCId(Node node) {
		String mscId = "";

		String nednContent = "";
		Node neidNode = getChildByNodeName(node, "neid");
		NodeList nodeList = neidNode.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getNodeName().equalsIgnoreCase("neun")) {
					mscId = nodeList.item(i).getTextContent();
				}
				if (nodeList.item(i).getNodeName().equalsIgnoreCase("nedn")) {
					nednContent = nodeList.item(i).getTextContent();
				}
			}
		}
		if (mscId.equalsIgnoreCase("") && !nednContent.equalsIgnoreCase("")) {
			int index = nednContent.lastIndexOf("=");
			mscId = nednContent.substring(index + 1, nednContent.length());
		}

		return mscId;
	}

private boolean validateMeasType(List<String> types, String measTypeList) {
		if (types == null || types.size() == 0 || measTypeList.length() == 0) {
			return false;
		}
		
		String measTypes[] = measTypeList.split(",");
		
		if (measTypes.length > 0) {
			boolean flag = false;
			for (String s : measTypes) {
				flag = false;
				for (String t : types) {
					if (t.equalsIgnoreCase(s)) {
						flag = true;
						break;
					}
				}
				if (flag == false) {
					return false;
				}
			}
		}
		return true;
	}

	private void writeFileHeader(File outFile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		List<String> list = getFileHeaderInfo();
		for (String s : list) {
			writer.write(commentChar + s);
			writer.newLine();
		}
		if (header != null && header.length() > 0) {
			writer.write(commentChar + " ORIGINAL HEADER:");
			writer.newLine();
			writer.write(commentChar + header);
			writer.newLine();
			writer.write(commentChar + "---------------------------------------------");
			writer.newLine();
			writer.write(commentChar + getSimpleText(header));
			writer.newLine();
		}
		writer.close();
	}

	private void writeLine(File outFile, String line) throws IOException {
		// write header
		if (!outFile.exists()) {
			writeFileHeader(outFile);
		}
		// write data
		if (line != null && line.length() > 0) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
			writer.append(line);
			writer.newLine();
			writer.flush();
			writer.close();
		}
	}

	private final String[] ORIGINAL = { "Active", "Attempt", "Connect", "Establish", "Idle", "Incoming", "Interactive", "Normal", "Outgoing", "Packet",
			"Release", "Samples", "Sharing", "Source", "Speech", "Stream", "Success", "Switch", "System", "Throughput", "Traffic", "Volume", "Return",
			"Rejected", "Failed", "Target", "Resel", "Order", "pm","Emergency","Channels","Capacity","Transport","Media","Average","Static","UsedBandwidth", "Received","Messages","Discards","Broadcast","Multicast","Exceed","Receeded","Total","Sessions","Default" };
	private final String[] REPLAMENT = { "Act", "Att", "Conn", "Estlsh", "Idl", "Incm", "Intact", "Norm", "Outg", "Pkt", "Rels", "Sampl", "Shar", "Src",
			"Spch", "Strm", "Succ", "Swt", "Sys", "Thput", "Traf", "Vol", "Rtrn", "Rej", "Fail", "Trg", "Res", "Ord", "","Emer","Chan","Cap","Trans","Med","Avg","Stt","UsedBanWid","Rec","Ms","Dc","Brc","Mulc","Ex","Re","Tot", "Sess","Df" };

	private String getSimpleText(String text) {
		String sh = "";
		String elms[] = text.split(sep);
		if (elms.length > 0) {
			for (String col : elms) {
				for (int i = 0; i < ORIGINAL.length; i++) {
					if (col.indexOf(ORIGINAL[i]) >= 0) {
						col = col.replace(ORIGINAL[i], REPLAMENT[i]);
					}
				}
				if (sh.length() > 0) {
					sh += sep;
				}
				sh += col;
			}
		}
		return sh;
	}
	private String getValuesVMGW(Node measValue) {
		String value = "";

		NodeList nodeList = measValue.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() == 3) {
				continue;
			}

			if (node.getNodeName().equalsIgnoreCase("moid")) {
				if (value.length() > 0) {
					value += separator;
				}
				value += this.getMoid(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("r")) {
				if (value.length() > 0) {
					value += separator;
				}
				value += node.getTextContent();
			}
		}
		return value;
	}
	private String getMoid(String moid) {
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}
	private String getValues(Node measValue) {
		String value = "";

		NodeList nodeList = measValue.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() == 3) {
				continue;
			}
			if(node.getNodeName().equalsIgnoreCase("r"))
			{
				if (value.length() > 0) {
					value += sep;
				}
				value += node.getTextContent();
			}
			
			
		}
		return value;
	}
private Node getChildByNodeName(Node node, String name) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node n = nodeList.item(index);
				if (n != null && n.getNodeName().equalsIgnoreCase(name)) {
					return n;
				}
			}
		}
		return null;
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

	private class DataMap {
		private String key = "";
		private String measTypeList = "";
		private String tableName = "";
		private Integer i;
		public DataMap(String key, String measTypeList, String tableName, Integer intdex) {
			this.key = key;
			this.measTypeList = measTypeList;
			this.tableName = tableName;
			this.i = intdex;
		}

		public String getKey() {
			return key;
		}
		public int getintdex()
		{
			return i;
		}
		public String getMeasTypeList() {
			return measTypeList;
		}

		public String getTableName() {
			return tableName;
		}
	}
	private String getTimestamp(String timestamp) {
		DateTime dateTime = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		dateTime = formatter.parseDateTime(timestamp).plusHours(7);
		dateTime = dateTime.minusMinutes(15);

		return dateTime.toString(formatter);
	}
}
