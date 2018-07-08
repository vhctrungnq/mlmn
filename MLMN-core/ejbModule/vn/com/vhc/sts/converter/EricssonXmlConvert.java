package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
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

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class EricssonXmlConvert extends STS_BasicConverter {

	private List<DataMap> maps = new ArrayList<DataMap>();

	private String header = "";
	private int fileId = -1;

	private String direcPath;
	private String fName;

	private static Logger logger = Logger.getLogger(EricssonXMLConverter.class.getName());

	public EricssonXmlConvert() {
		loadDataMapping();
	}

	private void loadDataMapping() {
		maps.add(new DataMap("UTRANCELL", "pmNoCsStreamDchDiscNormal,pmNoCsStreamDchDiscAbnorm", "R_E_UTRANCELL_3G"));
		maps.add(new DataMap("TRAFF", "pmCmAttDlHls,pmCmAttDlSf2", "R_E_TRAFF_3G"));
		maps.add(new DataMap("DOWNTIME_EUL", "pmEulDowntimeMan,pmEulDowntimeAuto", "R_E_DOWNTIME_EUL_3G"));
		maps.add(new DataMap("DOWNTIME_HS", "pmHsDowntimeMan,pmHsDowntimeAuto", "R_E_DOWNTIME_HS_3G"));
		maps.add(new DataMap("DROP", "pmNoTimesRlDelFrActSet,pmNoTimesRlAddToActSet", "R_E_DROP_3G"));
		maps.add(new DataMap("HANDOVER_CELL", "pmAttLbhoSpeech,pmFailLbhoSpeechGsmFailure", "R_E_HANDOVER_CELL_3G"));
		maps.add(new DataMap("HANDOVER_UTRANCELL", "pmAttNonBlindIfhoPsIntEul,pmAttNonBlindIfhoPsIntHs", "R_E_HANDOVER_UTRANCELL_3G"));
		maps.add(new DataMap("HANDOVER", "pmNoSbHoMeasStart,pmNoSuccessSbHo", "R_E_HANDOVER_RNC_3G"));
		maps.add(new DataMap("UTRANCELL_TO_CELL_HO", "pmRlAddAttemptsBestCellSpeech,pmRlAddSuccessBestCellSpeech", "R_E_UTRANCELL_TO_CELL_HO_3G"));
	}

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);

		this.direcPath = direcPath;
		this.fName = file.getName();

		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}

		try {
			XMLReader parser;
			parser = XMLReaderFactory.createXMLReader();
			parser.setContentHandler(new MySAXHandler());
			parser.setEntityResolver(new CustomResolver());
			parser.parse(file.getAbsolutePath()+file.getName());

			// this.updateRecordStatus();
		} catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}

		logger.info("Convert file: " + file.getName() + " success");
	}

	private class CustomResolver implements EntityResolver {
		public InputSource resolveEntity(String publicId, String systemId) {
			if (publicId == null)
				return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
			else
				return null;
		}
	}

	private class MySAXHandler implements ContentHandler {
		boolean neun = false;
		boolean mts = false;
		boolean mt = false;
		boolean moid = false;
		boolean r = false;

		String line = null;
		String bscId = null;
		String timestamp = null;
		String key = null;
		List<String> measTypes = new ArrayList<String>();
		List<String> measValues = new ArrayList<String>();

		private void reset() {
			line = null;
			bscId = null;
			timestamp = null;
			key = null;
			measTypes.clear();
			measValues.clear();
		}

		public void endDocument() throws SAXException {
		}

		public void endPrefixMapping(String arg0) throws SAXException {
		}

		public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		}

		public void processingInstruction(String arg0, String arg1) throws SAXException {
		}

		public void setDocumentLocator(Locator arg0) {
		}

		public void skippedEntity(String arg0) throws SAXException {
		}

		public void startDocument() throws SAXException {
		}

		public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		}

		public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes atts) throws SAXException {
			if (qualifiedName.equalsIgnoreCase("neun")) {
				neun = true;
			}
			if (qualifiedName.equalsIgnoreCase("mts")) {
				mts = true;
			}
			if (qualifiedName.equalsIgnoreCase("mt")) {
				mt = true;
			}
			if (qualifiedName.equalsIgnoreCase("moid")) {
				moid = true;
			}
			if (qualifiedName.equalsIgnoreCase("r")) {
				r = true;
			}
		}

		public void characters(char[] text, int start, int length) throws SAXException {
			if (neun) {
				bscId = new String(text, start, length);
				neun = false;
			}
			if (mts) {
				timestamp = new String(text, start, length - 1);
				mts = false;
			}
			if (mt) {
				measTypes.add(new String(text, start, length));
				mt = false;
			}
			if (moid) {
				if (key == null) {
					key = getKey(measTypes);
				}

				if (key != null) {
					if (key == "UTRANCELL_TO_CELL_HO" || key == "HANDOVER_CELL" || key == "HANDOVER_UTRANCELL") {
						// make header
						header = "TIME" + "\t" + "BSCID" + "\t" + "FROMCELLID" + "\t" + "TOCELLID";
						for (String s : measTypes) {
							if (header.length() > 0) {
								header += "\t";
							}
							header += s;
						}

						// make line data
						line = getMeasValueCellToCell(new String(text, start, length));

					} else {
						// make header
						header = "TIME" + separator + "BSCID" + separator + "CELLID";
						for (String s : measTypes) {
							if (header.length() > 0) {
								header += separator;
							}
							header += s;
						}

						if (key == "DOWNTIME_EUL" || key == "DOWNTIME_HS") {
							// make line data
							line = getCellIdDowntime(new String(text, start, length));
						} else {
							// make line data
							line = getCellId(new String(text, start, length));
						}
					}
				}
				moid = false;
			}
			if (r) {
				if (key != null) {
					line += separator + new String(text, start, length);
					r = false;
				}
			}
		}

		public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {

			if (qualifiedName.equalsIgnoreCase("mv")) {
				measValues.add(line);
			}

			if (qualifiedName.equalsIgnoreCase("md")) {
				if (key != null) {
					String fileName = key + "_" + getFileName(fName);
					File outFile = new File(direcPath, fileName);
					if (outFile.exists()) {
						outFile.delete();
					}

					for (String s : measValues) {
						try {
							writeLine(outFile, timestamp + separator + bscId + separator + s);
						} catch (Exception e) {
						}
					}

					// insert a record into database
					// try {
					// genarateDBRecord(fileName, getTableName(key));
					// } catch (SQLException e) {
					// }
				}

				reset();
			}

		}

	}

	/*------------------------------------------*/
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

	/*------------------------------------------*/
	private String getMeasValueCellToCell(String moid) {
		return this.getFromCellId(moid) + separator + this.getToCellId(moid);
	}

	private String getFromCellId(String moid) {
		moid = moid.substring(0, moid.lastIndexOf(","));
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}

	private String getToCellId(String moid) {
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}

	private String getCellIdDowntime(String moid) {
		moid = moid.substring(moid.lastIndexOf("UtranCell") + 1, moid.length());
		moid = moid.substring(0, moid.indexOf(","));
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}

	private String getCellId(String moid) {
		return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
	}

	/*------------------------------------------*/
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

	/*------------------------------------------*/
	private final String[] ORIGINAL = { "Active", "Attempt", "Connect", "Establish", "Idle", "Incoming", "Interactive", "Normal", "Outgoing", "Packet",
			"Release", "Samples", "Sharing", "Source", "Speech", "Stream", "Success", "Switch", "System", "Throughput", "Traffic", "Volume", "Return",
			"Rejected", "Failed", "Target", "Resel", "Order", "pm" };
	private final String[] REPLAMENT = { "Act", "Att", "Conn", "Estlsh", "Idl", "Incm", "Intact", "Norm", "Outg", "Pkt", "Rels", "Sampl", "Shar", "Src",
			"Spch", "Strm", "Succ", "Swt", "Sys", "Thput", "Traf", "Vol", "Rtrn", "Rej", "Fail", "Trg", "Re", "Ord", "" };

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

	/*------------------------------------------*/
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

	/*-----------------------------------------*/
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
