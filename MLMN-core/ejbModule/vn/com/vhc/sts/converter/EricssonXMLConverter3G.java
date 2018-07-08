package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
import vn.com.vhc.sts.utils.STS_XMLReaderManager;


/**
 * 
 * @author Mr.ThanhLV. Update by: ThangPX
 * @category Read file XML Ericsson using XMLReader
 * Updated by TrungNQ 19/3/2017 ghep cac doan code if (i == 1) {}
 * if (i == 2) {} thanh if (i == 1) {} else if (i == 2) {}
 * 
 * Updated by TrungNQ 23/3/2017 xu ly cac file co ten tag khac chuan cu
 */
public class EricssonXMLConverter3G extends STS_BasicConverter {

	
	private List<DataMap> maps = new ArrayList<DataMap>();

//	private int fileId = -1;

	private String direcPath;
	private String fName;
	private String patternId;
	String severNode;
	private static Logger logger = Logger.getLogger(EricssonXMLConverter3G.class.getName());

	public EricssonXMLConverter3G() {
		//Connection conn = null;
/*		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			String query = "select PATTERN_ID, SERVER_NODE from C_File_Patterns_Mlmn where convert_class= '"
					+ EricssonXMLConverter3G.class.getName()  + "'";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				patternId = rs.getString("PATTERN_ID");
				severNode = rs.getString("SERVER_NODE");
			}
			
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}
		}
		*/
		
		loadDataMapping();
	}

	private void loadDataMapping() {
		// File RNC
		maps.add(new DataMap("UTRANCELL", "pmNoLoadSharingRrcConn,pmTotNoRrcConnectReq", "R_E_UTRANCELL_3G"));
		maps.add(new DataMap("TRAFF", "pmBmcTrafficVolume,pmCmAttDlHls,pmCmAttDlSf2", "R_E_TRAFF_3G"));
		maps.add(new DataMap("DOWNTIME_EUL", "pmEulDowntimeMan,pmEulDowntimeAuto", "R_E_DOWNTIME_EUL_3G"));
		maps.add(new DataMap("DOWNTIME_HS", "pmHsDowntimeMan,pmHsDowntimeAuto", "R_E_DOWNTIME_HS_3G"));
		maps.add(new DataMap("DROP", "pmNoTimesRlDelFrActSet,pmNoTimesRlAddToActSet", "R_E_DROP_3G"));
		//maps.add(new DataMap("HANDOVER_CELL", "pmNoAttOutIratHoMultipmNoAttOutIratHoMulti,pmNoAttOutIratHoSpeech", "R_E_HANDOVER_CELL_3G"));
		maps.add(new DataMap("HANDOVER_CELL", "pmAttLbhoSpeech,pmFailLbhoSpeechGsmFailure", "R_E_HANDOVER_CELL_3G"));
		maps.add(new DataMap("HANDOVER_UTRANCELL", "pmAttLoadBasedIfho,pmAttNonBlindIfhoPsIntEul", "R_E_HANDOVER_UTRANCELL_3G"));
		maps.add(new DataMap("HANDOVER", "pmNoSbHoMeasStart,pmNoSuccessSbHo", "R_E_HANDOVER_RNC_3G"));
		maps.add(new DataMap("UNITLOAD", "pmSumMeasuredLoad,pmSamplesMeasuredLoad", "R_E_UNITLOAD_3G"));
		// database khong co bang R_E_UNITLOAD2_3G ??
		//maps.add(new DataMap("UNITLOAD2", "pmSamplesMeasuredLoad,pmSumMeasuredLoad", "R_E_UNITLOAD2_3G"));
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

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		BufferedReader reader = null;
		this.direcPath = direcPath;
		this.fName = file.getName();
	//	String firstLine = "";
		// insert record and update status in C_RAW_FILES_MLMN table
	/*	if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}*/
		XMLReader parser = null;
		try {
			
			parser = STS_XMLReaderManager.getInstance().getXMLReader();
			reader = new BufferedReader(new FileReader(file));
			parser.setContentHandler(new MySAXHandler());
			parser.setEntityResolver(new CustomResolver());
			parser.parse(file.getPath());					
			
		//	this.updateRecordStatus();
		} catch (Exception e) {
			logger.debug(e);
			throw new STS_ConvertException(e.getMessage(), "gnos-0306", e);
		} finally {
			if (parser != null) {
				STS_XMLReaderManager.getInstance().releaseXMLReader(parser);
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}
		}

		//logger.info("Convert file: " + file.getName() + " success");
	}

	private class CustomResolver implements EntityResolver {
		public InputSource resolveEntity(String publicId, String systemId) {
			return new InputSource(new StringReader(""));
		}
	}

	private class MySAXHandler implements ContentHandler {

		final StringBuffer sb = new StringBuffer();
		String s;
		String header = null;
		String line = null;
		String bscId = null;
		String timestamp = null;
		String key = null;
		List<String> measTypes = new ArrayList<String>();
		List<String> measValues = new ArrayList<String>();
		List<String> measValuesAllBlock = new ArrayList<String>();
		private void reset() {
			header = null;
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
		}

		public void characters(char[] text, int start, int length) throws SAXException {
			sb.append(text, start, length);
		}

		public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {
			s = sb.toString().trim();
			sb.delete(0, sb.length());

			if (qualifiedName.equalsIgnoreCase("neun")) {
				bscId = s;
			} else if (qualifiedName.equalsIgnoreCase("nedn")) {
				if (bscId.equalsIgnoreCase("") && !s.equalsIgnoreCase("")) {
					int index = s.lastIndexOf("=");
					bscId = s.substring(index + 1, s.length());
				}
			} else if (qualifiedName.equalsIgnoreCase("mts")) {
				timestamp = s.substring(0, s.length() - 1);
				timestamp = getTimestamp(timestamp);
			} else if (qualifiedName.equalsIgnoreCase("mt")) {
				measTypes.add(s);
			} else if (qualifiedName.equalsIgnoreCase("moid")) {
				if (key == null) {
					key = getKey(measTypes);
				}

				if (key != null) {
					if (key == "UTRANCELL_TO_CELL_HO" || key == "HANDOVER_CELL" || key == "HANDOVER_UTRANCELL") {
						// make header
						header = "TIME" +separator+ "BSCID" +separator+ "FROMCELLID" + separator + "TOCELLID";
						for (String s : measTypes) {
							header += separator + s;
						}

						// make line data
						line = getMeasValueCellToCell(s);

					}
					
					else {
						// make header
						header = "TIME" + separator + "BSCID" + separator + "CELLID";
						for (String s : measTypes) {
							header += separator + s;
						}

						// make line data
						if (key == "DOWNTIME_EUL" || key == "DOWNTIME_HS") {
							line = getCellIdDowntime(s);
						}
						else if (key == "UNITLOAD" || key == "UTIL_BWPORT") {
							line = getRncModule(s);
						}
						else {
							line = getCellId(s);
						}
					}
				}
			} else if (qualifiedName.equalsIgnoreCase("r")) {
				if (key != null) {
					line += separator + s;
				}
			} else if (qualifiedName.equalsIgnoreCase("mv")) {
				if(key == "UTIL_BWPORT")
					measValuesAllBlock.add(line);
				else
					measValues.add(line);
			} else if (qualifiedName.equalsIgnoreCase("md")) {
				if (key != null) {
					String fileName = key + "_" + getFileName(fName);
					File outFile = new File(direcPath, fileName);
					if (outFile.exists()) {
						outFile.delete();
					}
					// write header
					try {
						writeFileHeader(outFile, header);
					} catch (IOException e1) {
						logger.error(e1.getMessage());
					}
					if(key == "UTIL_BWPORT")
					{
						for (String s : measValuesAllBlock) {
							try {
								writeLine(outFile, timestamp + separator + bscId + separator + s);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}
					}
					else
					{
						for (String s : measValues) {
							try {
								writeLine(outFile, timestamp + separator + bscId + separator + s);
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}
					}

					// insert a record into database
					/*try {
						genarateDBRecord(fileName, getTableName(key));
					} catch (SQLException e) {
						logger.error(e.getMessage());
					}*/

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

		// ValidateMeasType. Update by ThangPX
		private boolean validateMeasType(List<String> types, String measTypeList) {

			if (types == null || types.size() == 0 || measTypeList.length() == 0) {
				return false;
			}
			String measTypes[] = measTypeList.split(",");
			if(types.size() < measTypes.length)
				return false;
			for(int i = 0; i < measTypes.length; i++)
			{
				if(!types.get(i).equalsIgnoreCase(measTypes[i]))
					return false;

			}
			
			return true;
		}

	/*------------------------------------------*/
	private String getRncModule(String moid)
	{
		String[] strTemp;
		strTemp = moid.split(",");
			moid = strTemp[2].substring(strTemp[2].indexOf("=")+1,strTemp[2].length());
			moid +="-" + strTemp[3].substring(strTemp[3].indexOf("=")+1,strTemp[3].length());
			moid +="-" + strTemp[6].substring(strTemp[6].indexOf("=")+1,strTemp[6].length());
			
		return moid;
	}
	private String getMeasValueCellToCell(String moid) {
		return this.getFromCellId(moid) + separator + this.getToCellId(moid);
	}

	private String getFromCellId(String moid) {
		try {
			moid = moid.substring(0, moid.lastIndexOf(","));
			return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
	}

	private String getToCellId(String moid) {
		try {
			return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
	}

	private String getCellIdDowntime(String moid) {
		try {
			moid = moid.substring(moid.lastIndexOf("UtranCell") + 1, moid.length());
			moid = moid.substring(0, moid.indexOf(","));
			return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
	}

	private String getCellId(String moid) {
		try {
			return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
		} catch (StringIndexOutOfBoundsException e) {
			return "";
		}
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

	private void writeFileHeader(File outFile, String header) throws IOException {
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
			
			String queryStr = "select FILE_NAME from C_RAW_FILES_MLMN \n" + "where PATTERN_ID = " + patternId;
			Date date = new Date();
			String day = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
			List<String> fileList = new ArrayList<String>();
			boolean isNewFile = true;
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				fileList.add(rs.getString("FILE_NAME"));
			}
			
			// TRUNGNQ 3/5/2017 mot file chi co 1 ban ghi trong bang C_RAW_FILES_MLMN de tranh import nhieu lan
			for (String file : fileList) {
				if (fileName.equals(file)) {
					isNewFile = false;
					break;
				}
			}
			
			if (isNewFile && patternId.length() > 0) {			
				queryStr = "insert /*+ NOLOGGING */ into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY," + "CONVERT_FLAG, IMPORT_FLAG, \n"
						+ "RAW_TABLE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
						+ STS_Setting.DB_TIME_FORMAT + "'), " + " 1, 0," +  "'" + tableName + "', '" + severNode + "')";

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

	private String getTimestamp(String timestamp) {
		DateTime dateTime = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		dateTime = formatter.parseDateTime(timestamp).plusHours(7);
		dateTime = dateTime.minusMinutes(15);

		return dateTime.toString(formatter);
	}

}
