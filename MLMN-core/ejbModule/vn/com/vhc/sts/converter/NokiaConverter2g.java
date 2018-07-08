package vn.com.vhc.sts.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.model.Nokia2gStruct;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author VHC-TRUNGNQ
 * 
 *  Chuyen file raw nokia2g tu xml --> csv dua tren y tuong phan tich file 3g
 *         
 */
public class NokiaConverter2g extends STS_BasicConverter {

	private static Logger logger = Logger.getLogger(NokiaConverter2g.class.getName());

	private final String _OBJECT_LIST = "TRAFFIC,HO,SERVICE,RESAVAIL";

	private static String _PREFIX_TABLE = "R_N_2G_";
	private Nokia2gStruct nokia2g;
	// private static String _DAY;
	// private static String _HOUR;

	private int fileId = -1;

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {

	}

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params, Connection conn) throws STS_ConvertException {

		prepareParams(params); // set params: separator, commentChar, nodeName
		makeDirectory(direcPath); // set Directory

		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}

		// String lineHeaderFirst = commentChar + "OBJECTNAME" + separator + "DAY" +
		// separator + "HOUR";
		String lineHeader = "";

		Map<String, BufferedWriter> map = new HashMap<String, BufferedWriter>();
		boolean first = true;

		String objectType = "";
		Class<Nokia2gStruct> nokia2gClass = Nokia2gStruct.class;

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);

			// Node PMSetup
			Node PMSetupNode = document.getElementsByTagName("PMSetup").item(0);
			Element PMSetupElement = (Element) PMSetupNode.getChildNodes();
			String startTime = PMSetupElement.getAttribute("startTime");
			
			String _DAYHOUR = getTime(startTime, separator);
			String bscid = "";

			// Get block
			NodeList rList = document.getElementsByTagName("PMMOResult"); // List node PMMOResult

			int rNodeLength = rList.getLength();

			// Xu ly moi node
			for (int i = 0; i < rNodeLength; i++) {
				Node PMMOResultNode = rList.item(i);

				String strLine = "";

				// Get CDATA
				if (PMMOResultNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) PMMOResultNode;
					
					// PMTarget
					NodeList PMTargetList = element.getElementsByTagName("PMTarget");

					NodeList PMTargetNode = PMTargetList.item(0).getChildNodes();

					Element PMTargetElement = (Element) PMTargetNode;
					String measurementType = PMTargetElement.getAttribute("measurementType");
					if (!objectType.equals(measurementType)) {
						first = true;
					}
					objectType = PMTargetElement.getAttribute("measurementType");
					objectType = objectType.toUpperCase();

					// Bo qua file khong su dung de tong hop cong thuc
					if (_OBJECT_LIST.indexOf(objectType) < 0) {
						continue;
					}
					
					BufferedWriter writer = null;
					if (map.get(objectType) == null) {
						File newFile = new File(direcPath, objectType + "_" + file.getName());
						writer = new BufferedWriter(new FileWriter(newFile));
						map.put(objectType, writer);
					} else {
						writer = map.get(objectType);
					}
					

					// MO
					NodeList MOList = element.getElementsByTagName("MO");

					String cdata = "";

					for (int j = 0; j < MOList.getLength(); j++) {
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

					String tmp = cdata.substring(0, cdata.indexOf("/BCF"));
					if (!bscid.contains(tmp)) {
						
						if (bscid.length() > 0) {
							tmp = "," + tmp;
						}
						bscid += tmp;
					}
					strLine = cdata;

					// strLine += separator + _DAY + separator + _HOUR;
					strLine += _DAYHOUR;

					if (first) {
						nokia2g = Nokia2gStruct.getModel(objectType);
						lineHeader = commentChar + "OBJECTNAME" + separator + "TIME";
						lineHeader += separator + nokia2g.createHeader(separator);
						writer.write(lineHeader);
						writer.newLine();

					}

					// int j = 0;

					// xu ly moi counter
					for (int n = 0; n < PMTargetNode.getLength(); n++) {
						if (n % 2 == 0)
							continue;
						else {

							// strLine += separator + PMTargetNode.item(n).getTextContent();
							String counterName = PMTargetNode.item(n).getNodeName();

							// chi lay nhung counter dung trong khong thuc tong hop
							if (Arrays.asList(nokia2g.getFieldList().split(",")).contains(counterName)) {
								Field counter = nokia2gClass.getField(counterName);
								counter.set(nokia2g, PMTargetNode.item(n).getTextContent());
							}

						}
					}
					first = false;
					strLine += separator + nokia2g.toString(separator);
					writer.write(strLine);
					writer.newLine();
				}
			}

			// file khong chua bat ki mot measurementType nao su dung trong cong thuc
			updateRecordStatus(conn);
			
			if (map.isEmpty()) {
				logger.info(file.getName() + " khong chua counter can tinh toan");
			 	return;
			}
			
			for (String key : map.keySet()) {			
				String tableName = _PREFIX_TABLE + key + "_V2";
				String fileName = key + "_" + file.getName();
				genarateDBRecord(fileName, tableName, bscid, conn);
				map.get(key).close();
			}
			

		} catch (Exception e) {
			logger.error(e);
			throw new STS_ConvertException(e.getMessage(), "GNOS-0306", e);
		}
	}

	// 2012-02-14T23:00:00.000+07:00:00
	private String getTime(String date, String separator) {

		String regex = "[-T:]";

		Pattern p = Pattern.compile(regex);
		String[] items = p.split(date);
		
		return separator + items[0] + items[1] + items[2] + items[3];
	}


	private void genarateDBRecord(String fileName, String tableName, String node, Connection conn) throws SQLException {
		Statement st = conn.createStatement();

		String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, SERVER_NODE, SUB_DIR from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
		String patternId = "";
		String nodeName = node;
		String serverNode = "";
		String folder = "";
		//
		ResultSet rs = st.executeQuery(queryStr);
		while (rs.next()) {
			patternId = rs.getString("PATTERN_ID");
			serverNode = rs.getString("SERVER_NODE");
			folder = rs.getString("SUB_DIR");
		}
		if (patternId.length() > 0) {
			queryStr = "insert  into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
					+ "NODE_NAME, RAW_TABLE, SUB_DIR, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "',1, 0, '" + nodeName + "', '"
					+ tableName + "', '" + folder + "', '" + serverNode + "')";

			st.execute(queryStr);
		}

		rs.close();
		st.close();
	}

	private void updateRecordStatus(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
		// update state
		String queryStr = "update C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
		st.execute(queryStr);
		st.close();
	}

}
