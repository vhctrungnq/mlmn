package vn.com.vhc.sts.converter;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

//@author: ThangPX 
//Convert file excel and upload to database
public class GGSNConverterXls extends STS_BasicConverter {

	private static final Logger logger = Logger.getLogger(GGSNConverterXls.class.getName());
	private int fileId = -1;

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {

		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}

		String filePath = file.getPath();
		String fileName = file.getName();
		readXlsFile(fileName, filePath);

	}

	@SuppressWarnings("deprecation")
	private void readXlsFile(String fileName, String filePath) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			String value = "";
			int cols = 0; // No of columns
			int tmp = 0;
			String strDay = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.indexOf("."));
			// This trick ensures that we get the data properly even if it
			// doesn't start from first few rows
			for (int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}
			value += "(TO_DATE('" + strDay + "','ddMMyyyy'),";
			for (int r = 1; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null) {
					for (int c = 1; c < cols; c++) {
						cell = row.getCell((short) c);
						if (cell != null) {
							if (c == 1)

								value += "'" + cell + "',";
							else
								value += cell + ",";
						} else {
							if (c == 1)
								value += "'SUM',";
							else
								value += "0,";
						}

					}
					insertDatabase("DY_GGSN_SUMMARY", value);
					value = "(TO_DATE('" + strDay + "','ddMMyyyy'),";

				}
			}
			updateRecordStatus();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	private void insertDatabase(String tableName, String value) throws SQLException {
		Connection conn = null;
		String queryStr;

		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			queryStr = "INSERT INTO " + tableName + selectDatabase(tableName);

			queryStr += value;
			queryStr = queryStr.substring(0, queryStr.lastIndexOf(",")) + ")";
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

	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set \n" + " IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
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

	private String selectDatabase(String tableName) {
		Connection conn = null;
		String file_column_header = "(";
		try {
			try {
				conn = STS_Global.DATA_SOURCE.getConnection();
				Statement st = conn.createStatement();

				String queryStr = "select TABLE_COLUMN from I_IMPORT_MAPPING where STATUS = 'Y' AND RAW_TABLE = '" + tableName + "' order by FILE_COLUMN";

				//
				ResultSet rs = st.executeQuery(queryStr);
				while (rs.next()) {
					file_column_header += rs.getString("TABLE_COLUMN") + ",";
				}
				file_column_header = file_column_header.substring(0, file_column_header.lastIndexOf(",")) + ") VALUES ";
				rs.close();
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}

		return file_column_header;
	}
}
