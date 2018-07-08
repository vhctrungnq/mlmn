package inventory.cni;

import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.exceptions.IN_ConvertException;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;



public abstract class IN_BasicConverter implements IN_IConverter {

	private static Logger logger = Logger.getLogger(IN_BasicConverter.class.getName());

	protected String separator = "\t";
	protected String commentChar = "@";
	protected String nodeName = "";

	public abstract void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException;

	public void prepareParams(Hashtable<Byte, String> params) throws IN_ConvertException {
		if (params == null) {
			throw new IN_ConvertException("Invalid parameter");
		}
		if (params.containsKey(IN_Setting.SEPARATOR_KEY)) {
			separator = params.get(IN_Setting.SEPARATOR_KEY);
		}
		if (params.containsKey(IN_Setting.COMMENT_CHAR_KEY)) {
			commentChar = params.get(IN_Setting.COMMENT_CHAR_KEY);
		}
		if (params.containsKey(IN_Setting.NODE_NAME_KEY)) {
			nodeName = params.get(IN_Setting.NODE_NAME_KEY);
		}
	}

	public void makeDirectory(String direcPath) throws IN_ConvertException {
		if (direcPath == null || direcPath.length() == 0) {
			throw new IN_ConvertException("Invalid directory parameter");
		}
		// make a destination directory
		File dir = new File(direcPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * @return
	 */
	public List<String> getFileHeaderInfo() {
		List<String> list = new ArrayList<String>();
		list.add("---------------------------------------------");
		list.add(" File duoc chuan hoa boi he thong VMSC2");
		list.add(" Thoi gian chuan hoa: " + IN_Util.getCurrentTime());
		list.add(" Class xu ly: " + this.getClass().getName());
		list.add("---------------------------------------------");

		return list;
	}

	public List<String> getFileHeaderInfo(int columnCount) {
		List<String> list = getFileHeaderInfo();
		if (columnCount <= 0) {
			return list;
		}
		list.remove(list.size() - 1);
		list.add(" Tong so cot du lieu: " + columnCount);
		list.add("---------------------------------------------");

		return list;
	}

	public List<String> getFileHeaderInfo(String[] columnNames, String sep) {
		List<String> list = getFileHeaderInfo();
		if (columnNames == null || columnNames.length == 0) {
			return list;
		}
		list.remove(list.size() - 1);
		list.add(" Tong so cot du lieu: " + columnNames.length);
		list.add("---------------------------------------------");
		String cols = "";
		for (String s : columnNames) {
			if (cols.length() > 0) {
				cols += sep;
			}
			cols += s;
		}
		list.add(cols);

		return list;
	}

	public List<String> getFileHeaderInfo(List<String> columnNames, String sep) {
		List<String> list = getFileHeaderInfo();
		if (columnNames == null || columnNames.size() == 0) {
			return list;
		}
		list.remove(list.size() - 1);
		list.add(" Tong so cot du lieu: " + columnNames.size());
		list.add("---------------------------------------------");
		String cols = "";
		for (String s : columnNames) {
			if (cols.length() > 0) {
				cols += sep;
			}
			cols += s;
		}
		list.add(cols);

		return list;
	}

	public String getFileName(String fileName) {
		String name = "";
		if (fileName != null && fileName.length() > 0) {
			int indexOf = fileName.lastIndexOf(".");
			name = (indexOf == -1) ? fileName : fileName.substring(0, indexOf);
		}
		name += IN_Setting.FILE_EXTENSION;
		// System.out.println("Stand file name: " + name);
		return name;
	}

	public String getTimeFromFileName(String fileName, String regex) {
		String time = "";

		Pattern timePattern = Pattern.compile(regex);
		Matcher m = timePattern.matcher(fileName.trim());
		if (m.find()) {
			time = m.group().trim();
		}
		return time;
	}

	public void executeSQLCommand(String sqlCommand) throws SQLException {
		Connection conn = null;
		try {
			conn = IN_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			st.execute(sqlCommand);
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

	public void duplicateRecord(long fileId, String fileName, String rawTable) throws SQLException {
		String sql = "insert /*+ APPEND NOLOGGING */ into C_RAW_FILES_MLMN (FILE_ID,PATTERN_ID,FILE_NAME,DAY," + "HOUR,CONVERT_FLAG,IMPORT_FLAG, NODE_NAME, RAW_TABLE, ORIGINAL_NAME) "
				+ "select SEQ_CRF.NEXTVAL,PATTERN_ID, '" + fileName + "',DAY,HOUR,1,0,NODE_NAME,'" + rawTable
				+ "',ORIGINAL_NAME from C_RAW_FILES_MLMN where FILE_ID = " + fileId;

		executeSQLCommand(sql);
	}
}
