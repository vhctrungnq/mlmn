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
/**
 * 
 * @author Mr.QuanNH
 * Read file XML Ericsson using DocumentBuilderFactory
 *
 */
public class EricssonXMLConverter extends STS_BasicConverter {

	private List<DataMap> maps = new ArrayList<DataMap>();

	private String header = "";
	private int fileId = -1;
	private String line2 = "";
	private static Logger logger = Logger.getLogger(EricssonXMLConverter.class.getName());

	public EricssonXMLConverter() {
		loadDataMapping();
	}

	private void loadDataMapping() {
		// File RNC
				maps.add(new DataMap("UTRANCELL", "pmNoLoadSharingRrcConn,pmTotNoRrcConnectReq", "R_E_UTRANCELL_3G"));
				maps.add(new DataMap("TRAFF", "pmBmcTrafficVolume,pmCmAttDlHls,pmCmAttDlSf2", "R_E_TRAFF_3G"));
				maps.add(new DataMap("DOWNTIME_EUL", "pmEulDowntimeMan,pmEulDowntimeAuto", "R_E_DOWNTIME_EUL_3G"));
				maps.add(new DataMap("DOWNTIME_HS", "pmHsDowntimeMan,pmHsDowntimeAuto", "R_E_DOWNTIME_HS_3G"));
				maps.add(new DataMap("DROP", "pmNoTimesRlDelFrActSet,pmNoTimesRlAddToActSet", "R_E_DROP_3G"));
				maps.add(new DataMap("HANDOVER_CELL", "pmNoAttOutIratHoMulti,pmNoAttOutIratHoSpeech", "R_E_HANDOVER_CELL_3G"));				
				//maps.add(new DataMap("HANDOVER", "pmNoSbHoMeasStart,pmNoSuccessSbHo", "R_E_HANDOVER_RNC_3G"));
				maps.add(new DataMap("UNITLOAD", "pmSumMeasuredLoad,pmSamplesMeasuredLoad", "R_E_UNITLOAD_3G"));
				maps.add(new DataMap("UNITLOAD2", "pmSamplesMeasuredLoad,pmSumMeasuredLoad", "R_E_UNITLOAD_3G"));
				maps.add(new DataMap("CCDEVICES", "pmSumCcSpMeasLoad,pmSamplesCcSpMeasLoad", "R_E_CCDEVICES_3G"));
				maps.add(new DataMap("DCDEVICES", "pmSumDcSpMeasLoad,pmSamplesDcSpMeasLoad", "R_E_DCDEVICES_3G"));
				maps.add(new DataMap("PDRDEVICES", "pmSumPdrSpMeasLoad,pmSamplesPdrSpMeasLoad", "R_E_PDRDEVICES_3G"));
				maps.add(new DataMap("ROUTE_DATA", "pmNoRoutedIpBytesDl,pmNoRoutedIpBytesUl", "R_E_ROUTE_DATA_3G"));
				maps.add(new DataMap("CAPACITY", "pmSumCapacity,pmSamplesCapacity", "R_E_CAPACITY_3G"));
				maps.add(new DataMap("UTIL_BWPORT", "pmIfInBroadcastPkts,pmIfInDiscards,pmIfInErrors,pmIfInOctetsHi", "R_E_UTIL_BWPORT_3G"));
				//File BTS
				maps.add(new DataMap("UTIL_ULCE", "pmSetupAttemptsSf128,pmSetupFailuresSf8", "R_E_UTIL_ULCE_3G"));
				maps.add(new DataMap("UTIL_DLCE", "pmSetupAttemptsSf128,pmUsedADch", "R_E_UTIL_DLCE_3G"));

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

			this.updateRecordStatus();
		} catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "GNOS-0306", e);
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

		if (key == "UTRANCELL_TO_CELL_HO"||key == "HANDOVER_CELL"||key == "HANDOVER_UTRANCELL") {
			header = "TIME" + "\t" + "BSCID" + "\t" + "FROMCELLID" + "\t" + "TOCELLID";
			for (String s : measTypes) {
				if (header.length() > 0) {
					header += "\t";
				}
				header += s;
			}

			// make BscId
			String bscId = this.getBSCId(measData);
			// make line data
			for (Node n : measValues) {
				try {
					String line = this.getMeasValueCellToCell(n);
					line = timestamp + "\t" + bscId + "\t" + line;
					writeLine(outFile, line);
				} catch (Exception e) {
				}
			}

		}
		else if(key == "UTIL_DLCE"||key == "UTIL_ULCE") 
		{
			header = "TIME" + separator + "BTSID" + separator + "UPLBASEBANDPOOL";
			for (String s : measTypes) {
				if (header.length() > 0) {
					header += separator;
				}
				header += s;
			}
			// make line data
			// make BscId
			String btsid = this.getBSCId(measData);
			for (Node n : measValues) {
				try {
					String line = this.getMeasValue(n);
					line = timestamp + separator + btsid + separator + line;
					writeLine(outFile, line);
				} catch (Exception e) {
				}
			}
		}
		else if(key == "UTIL_BWPORT") 
		{
			header = "TIME" + separator + "BSCID" + separator + "OBJ";
			for (String s : measTypes) {
				if (header.length() > 0) {
					header += separator;
				}
				header += s;
			}
			// make line data
			// make BscId
			String btsid = this.getBSCId(measData);
			for (Node n : measValues) {
				try {
					String line = this.getMeasValueUnitload(n);
					line2 += timestamp + separator + btsid + separator + line + "\n";
					
				} catch (Exception e) {
				}
			}
			writeLine(outFile, line2);
		}
		else {
			// make header
			header = "TIME" + separator + "BSCID" + separator + "CELLID";
			for (String s : measTypes) {
				if (header.length() > 0) {
					header += separator;
				}
				header += s;
			}

			// make BscId
			String bscId = this.getBSCId(measData);
			if (key == "DOWNTIME_EUL"||key == "DOWNTIME_HS") {
				// make line data
				for (Node n : measValues) {
					try {
						String line = this.getMeasValueDowntime(n);
						line = timestamp + separator + bscId + separator + line;
						writeLine(outFile, line);
					} catch (Exception e) {
					}
				}
			}
			else if (key.equalsIgnoreCase("UNITLOAD")) {
				// make line data
				for (Node n : measValues) {
					try {
						String line = this.getMeasValueUnitload(n);
						line = timestamp + separator + bscId + separator + line;
						writeLine(outFile, line);
					} catch (Exception e) {
					}
				}
				
			
			}
			else {
				// make line data
				for (Node n : measValues) {
					try {
						String line = this.getMeasValue(n);
						line = timestamp + separator + bscId + separator + line;
						writeLine(outFile, line);
					} catch (Exception e) {
					}
				}
			}
		}
		// insert a record into database
		this.genarateDBRecord(fileName, getTableName(key));
		
		return 1;
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

	private String getBSCId(Node node) {
		String bscId = "";

		String nednContent = "";
		Node neidNode = getChildByNodeName(node, "neid");
		NodeList nodeList = neidNode.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getNodeName().equalsIgnoreCase("neun")) {
					bscId = nodeList.item(i).getTextContent();
				}
				if (nodeList.item(i).getNodeName().equalsIgnoreCase("nedn")) {
					nednContent = nodeList.item(i).getTextContent();
				}
			}
		}
		if (bscId.equalsIgnoreCase("") && !nednContent.equalsIgnoreCase("")) {
			int index = nednContent.lastIndexOf("=");
			bscId = nednContent.substring(index + 1, nednContent.length());
		}

		return bscId;
	}

	private String getMeasValueCellToCell(Node measValue) {
		String value = "";

		NodeList nodeList = measValue.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() == 3) {
				continue;
			}

			if (node.getNodeName().equalsIgnoreCase("moid")) {
				if (value.length() > 0) {
					value += "\t";
				}
				value += this.getFromCellId(node.getTextContent()) + "\t" + this.getToCellId(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("r")) {
				if (value.length() > 0) {
					value += "\t";
				}
				value += node.getTextContent();
			}
		}
		return value;
	}
	private String getMeasValueUnitload(Node measValue)
	{
		String value = "";

		NodeList nodeList = measValue.getChildNodes();
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			if (node.getNodeType() == 3) {
				continue;
			}
			if (value.length() > 0) {
				value += separator;
			}
	
			if (node.getNodeName().equalsIgnoreCase("moid")) {
				
				value += this.getRncModule(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("r")) {
			
				value += node.getTextContent();
			}
		}
		return value;
	}
	private String getMeasValue(Node measValue) {
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
				value += this.getCellId(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("r")) {
				if (value.length() > 0) {
					value += separator;
				}
				value += node.getTextContent();
			}
		}
		return value;
	}
	
	private String getMeasValueDowntime(Node measValue) {
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
				value += this.getCellIdDowntime(node.getTextContent());
			} else if (node.getNodeName().equalsIgnoreCase("r")) {
				if (value.length() > 0) {
					value += separator;
				}
				value += node.getTextContent();
			}
		}
		return value;
	}
	//Update by ThangPX
	private boolean validateMeasType(List<String> types, String measTypeList) {
		if (types == null || types.size() == 0 || measTypeList.length() == 0) {
			return false;
		}
		String measTypes[] = measTypeList.split(",");
		if(types.size() < measTypes.length)
			return false;
		else
		{
			for(int i = 0; i < measTypes.length; i++)
			{
				if(!types.get(i).equalsIgnoreCase(measTypes[i]))
					return false;
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
			"Rejected", "Failed", "Target", "Resel", "Order", "pm" };
	private final String[] REPLAMENT = { "Act", "Att", "Conn", "Estlsh", "Idl", "Incm", "Intact", "Norm", "Outg", "Pkt", "Rels", "Sampl", "Shar", "Src",
			"Spch", "Strm", "Succ", "Swt", "Sys", "Thput", "Traf", "Vol", "Rtrn", "Rej", "Fail", "Trg", "Re", "Ord", "" };

	private String getRncModule(String moid)
	{
		String[] strTemp;
		if(moid.contains("Subrack"))
		{
			strTemp = moid.split(",");
			moid = strTemp[2].substring(strTemp[2].indexOf("=")+1,strTemp[2].length());
			moid +="-" + strTemp[3].substring(strTemp[3].indexOf("=")+1,strTemp[3].length());
			moid +="-" + strTemp[6].substring(strTemp[6].indexOf("=")+1,strTemp[6].length());
			
		}
		else
			moid = moid.substring(moid.lastIndexOf("=") + 1, moid.length());
		
		return moid;
	}
	private String getSimpleText(String text) {
		String sh = "";
		String elms[] = text.split(separator);
		if (elms.length > 0) {
			for (String col : elms) {
				for (int i = 0; i < ORIGINAL.length; i++) {
					if (col.indexOf(ORIGINAL[i]) >= 0) {
						col = col.replace(ORIGINAL[i], REPLAMENT[i]);
					}
				}
				if (sh.length() > 0) {
					sh += separator;
				}
				sh += col;
			}
		}
		return sh;
	}

	private String getCellId(String moid) {
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}
	
	private String getCellIdDowntime(String moid) {
		moid = moid.substring(moid.lastIndexOf("UtranCell")+1,moid.length());
		moid = moid.substring(0,moid.indexOf(","));
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}

	private String getFromCellId(String moid) {
		moid = moid.substring(0, moid.lastIndexOf(","));
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}

	private String getToCellId(String moid) {
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
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

		public DataMap(String key, String measTypeList, String tableName) {
			this.key = key;
			this.measTypeList = measTypeList;
			this.tableName = tableName;
		}

		public String getKey() {
			return key;
		}

		public String getMeasTypeList() {
			return measTypeList;
		}

		public String getTableName() {
			return tableName;
		}
	}
}
