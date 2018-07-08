package vn.com.vhc.alarm.cni.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.SQLException; 
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.info.AL_ImportedDirectlyInfo;
import vn.com.vhc.alarm.cni.info.AL_MappingInfo; 
import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.util.exceptions.AL_ImportException;

public class AL_FileImporter {
	
	
	private static final String DATA_TYPE_VARCHAR = "VARCHAR2";
	private static final String DATA_TYPE_NUMBER = "NUMBER";
	private static final String DATA_TYPE_DATE = "DATE";
	private static final String DATA_TYPE_TIMESTAMP = "TIMESTAMP";
	private DataSource ds = null;

	private static Logger logger = Logger.getLogger(AL_FileImporter.class
			.getName());

	public AL_FileImporter(DataSource ds) {
		this.ds = ds;
	} 
	/**
	 * 
	 * Old version
	 */
/*	public void doImport(AL_ImportedDirectlyInfo info,
			Vector<AL_MappingInfo> map) throws AL_ImportException {

		if (map == null || map.size() == 0) {
			throw new AL_ImportException("Import mapping not found for table "
					+ info.getRawTable(), "VMSC-0352");
		}

		Connection conn = null;
		PreparedStatement pst = null;
		
		String comment = info.getCommentChar();
		String sep = info.getSeparatorChar();
		String insState = buildPreparedStatement(info.getRawTable(), map);
		int cnt = 0;
		int totalLine = 0;
		int sizeLimit = 0;
		String values[] = null;
		String header = "";
		boolean isCreateFileColumn = false;

		File file = new File(info.getFilePath(), info.getFileName());
		BufferedReader reader = null;
		try {
			conn = this.ds.getConnection();
			conn.setAutoCommit(false);

			pst = conn.prepareStatement(insState);

			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) {
					continue;
				}
				// validate comment character in the line
				if (comment != null && !comment.trim().equalsIgnoreCase("")) {
					if (line.startsWith(comment)) {
						header = line.replace(comment, "");
						continue;
					}
				}
				// make file column again when the mapping using file header
				if (isCreateFileColumn == false && info.isIByFileHeader()) {
					map = makeFileColumn(map, header, sep, info.getRawTable());
					isCreateFileColumn = true;
				}
				// get size limit for split data from mapping data
				if (sizeLimit == 0) {
					sizeLimit = this.getSizeLimit(map);
					sizeLimit = (sizeLimit == -1) ? 5000 : (sizeLimit + 2);
				}

				totalLine++;
				info.setRowCountInFile(totalLine);
				try {
					values = line.split(sep, sizeLimit);
					cnt += this.exePreStatement(pst, values, map, info.getRawTable());
					
					info.setRowCountImported(cnt);
					
				} catch (SQLException sql) {
					info.setMessageCode("VMSC-0362");
					logger.warn(AL_Global.IMPORT_ERR
							+ "["
							+ info.getFileName()
							+ "] ["
							+ line
							+ "] ["
							+ (sql.getMessage() != null ? sql.getMessage()
									.trim() : "") + "]");
				} catch (AL_ImportException ime) {
					info.setMessageCode(ime.getErrorCode());
					logger.warn(AL_Global.IMPORT_ERR
							+ "["
							+ info.getFileName()
							+ "] ["
							+ line
							+ "] ["
							+ (ime.getMessage() != null ? ime.getMessage()
									.trim() : "") + "]");
				} 
			}
			
			conn.commit();
			// map.clear();
			//
			if (totalLine > 0 && cnt == 0) {
				throw new AL_ImportException("0 record inserted to database",
						info.getMessageCode());
			}

			info.setMessageCode("VMSC-0350");
			pst.close();

		} catch (SQLException e) {
			throw new AL_ImportException("Can't executed the PreparedStatement");
		} catch (FileNotFoundException fioe) {
			throw new AL_ImportException("File not found", "VMSC-0366");
		} catch (IOException e) {
			throw new AL_ImportException(
					"File could not be opened or operated on as requested",
					"VMSC-0360");
		} finally {
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (Exception e) {
				logger.warn("Close database connection error: "
						+ e.getMessage());
			}
			try {
				reader.close();
			} catch (Exception e) {
				logger.warn("Close file error: " + e.getMessage());
			}
		}
	}
*/
	/**
	 * 
	 * doImport2(info, map)
	 * Parameters:
	 * 	AL_ImportedDirectlyInfo info: store the import info data.
	 * 	Vector<AL_MappingInfo> map: mapping data
	 * 
	 * Description:
	 * 	This is extension version of doImport, this allows to insert a batch of queries within one round.
	 * 
	 */
	public void doImport(AL_ImportedDirectlyInfo info,
			Vector<AL_MappingInfo> map) throws AL_ImportException {

		if (map == null || map.size() == 0) {
			throw new AL_ImportException("Import mapping not found for table "
					+ info.getRawTable(), "VMSC-0352");
		}

		Connection conn = null;
		PreparedStatement pst = null;
		
		String comment = info.getCommentChar();
		String sep = info.getSeparatorChar();
		String insState = buildPreparedStatement(info.getRawTable(), map);
		int cnt = 0;
		int totalLine = 0;
		int sizeLimit = 0;
		String values[] = null;
		String header = "";
		boolean isCreateFileColumn = false;
		
		/* Maximum rows in a batch per round*/
		final int C_MAX_BATCH = 500;
		int max_batch = 0;
		File file = new File(info.getFilePath(), info.getFileName());
		BufferedReader reader = null;
		try {
			conn = this.ds.getConnection();
			conn.setAutoCommit(false);

			pst = conn.prepareStatement(insState);

			reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) {
					continue;
				}
				// validate comment character in the line
				if (comment != null && !comment.trim().equalsIgnoreCase("")) {
					if (line.startsWith(comment)) {
						header = line.replace(comment, "");
						continue;
					}
				}
				// make file column again when the mapping using file header
				if (isCreateFileColumn == false && info.isIByFileHeader()) {
					map = makeFileColumn(map, header, sep, info.getRawTable());
					isCreateFileColumn = true;
				}
				// get size limit for split data from mapping data
				if (sizeLimit == 0) {
					sizeLimit = this.getSizeLimit(map);
					sizeLimit = (sizeLimit == -1) ? 5000 : (sizeLimit + 2);
				}

				totalLine++;
				info.setRowCountInFile(totalLine);
				try {
					values = line.split(sep, sizeLimit);
					cnt ++;
					this.addBatchStmt(pst, values, map, info.getRawTable());
					info.setRowCountImported(cnt);
					/* */
					max_batch++;
					if(max_batch % C_MAX_BATCH == 0)
					{
						pst.executeBatch();
						max_batch = 0;
					}
				} catch (SQLException sql) {
					info.setMessageCode("VMSC-0362");
					logger.warn(AL_Global.IMPORT_ERR
							+ "["
							+ info.getFileName()
							+ "] ["
							+ line
							+ "] ["
							+ (sql.getMessage() != null ? sql.getMessage()
									.trim() : "") + "]");
				} catch (AL_ImportException ime) {
					info.setMessageCode(ime.getErrorCode());
					logger.warn(AL_Global.IMPORT_ERR
							+ "["
							+ info.getFileName()
							+ "] ["
							+ line
							+ "] ["
							+ (ime.getMessage() != null ? ime.getMessage()
									.trim() : "") + "]");
				} 
			}
			
			/* Insert remaining records */
			pst.executeBatch();
			max_batch = 0;
			conn.commit();
			// map.clear();
			//
			if (totalLine > 0 && cnt == 0) {
				throw new AL_ImportException("0 record inserted to database",
						info.getMessageCode());
			}

			info.setMessageCode("VMSC-0350");
			pst.close();

		} catch (SQLException e) {
			logger.error(e, e);
		} catch (FileNotFoundException fioe) {
			throw new AL_ImportException("File not found", "VMSC-0366");
		} catch (IOException e) {
		    logger.error(e, e);;
		} finally {
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (Exception e) {
				logger.warn("Close database connection error: "
						+ e.getMessage());
			}
			try {
				reader.close();
			} catch (Exception e) {
				logger.warn("Close file error: " + e.getMessage());
			}
		}
	}
	
	private int getSizeLimit(Vector<AL_MappingInfo> map) {
		int sl = -1;
		if (map != null && map.size() > 0) {
			for (AL_MappingInfo info : map) {
				try {
					int t = Integer.parseInt(info.getFileColumn());
					if (t > sl) {
						sl = t;
					}
				} catch (NumberFormatException nfe) {
					sl = -1;
					break;
				}
			}
		}
		return sl;
	}
	/*
	 * exePreStatement()
	 * Purpose:
	 * 	To insert a record into rawTable. Values specified by lstValues.
	 */
/*	private int exePreStatement(PreparedStatement preStm, String[] lstValues,
			Vector<AL_MappingInfo> mapping, String rawTable)
			throws AL_ImportException, SQLException {
		if (mapping.size() > lstValues.length) {
			throw new AL_ImportException("File format error, file column = "
					+ lstValues.length + "; require at least " + mapping.size());
		}

		String dataType = "";
		String dataFormat = "";
		String value = "";
		int columnIdx = -1;
		int index = 0;
		AL_MappingInfo rowMap = null;

		while (index < mapping.size()) {
			rowMap = mapping.get(index);
			try {
				columnIdx = Integer.parseInt(rowMap.getFileColumn());
			} catch (NumberFormatException nfe) {
				throw new AL_ImportException("File column invalid: "
						+ rowMap.getFileColumn(), "VMSC-0365");
			}
			if (columnIdx == -1) {
				throw new AL_ImportException("Column " + rawTable + "."
						+ rowMap.getTableColumn() + " not found at index -1",
						"VMSC-0365");
			}
			if (columnIdx >= lstValues.length) {
				throw new AL_ImportException("Column " + rawTable + "."
						+ rowMap.getTableColumn() + " not found at index "
						+ columnIdx + " (total columns " + lstValues.length
						+ ")", "VMSC-0361");
			}
			value = lstValues[columnIdx].trim();
			index++;

			dataType = rowMap.getDataType();
			if (dataType.equalsIgnoreCase(DATA_TYPE_NUMBER)) {
				try {
					if (value.length() == 0 || value.indexOf("?") > -1) {
						preStm.setNull(index, Types.NUMERIC);
					} else {
						preStm.setDouble(index, Double.parseDouble(value));
					}
				} catch (Exception e) {
					throw new AL_ImportException(e.getMessage(), "VMSC-0367");
				}
			} else if (dataType.equalsIgnoreCase(DATA_TYPE_VARCHAR)) {
				if (value.length() == 0 || value.indexOf("?") > -1) {
					preStm.setNull(index, Types.VARCHAR);
				} else {
					preStm.setString(index, value);
				}
			} else if (dataType.equalsIgnoreCase(DATA_TYPE_DATE)
					|| dataType.equalsIgnoreCase(DATA_TYPE_TIMESTAMP)) {
				if (value.length() == 0 || value.indexOf("?") > -1) {
					preStm.setNull(index, Types.DATE);
				} else {
					dataFormat = rowMap.getDataFormat();
					if (dataFormat == null || dataFormat.length() == 0) {
						throw new AL_ImportException(
								"Datetime datatype must have dataformat",
								"VMSC-0363");
					}
					String originalDataFmt = dataFormat;
					try {
						if (dataFormat.trim().equalsIgnoreCase("DDD")) {
							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.DAY_OF_YEAR,
									Integer.parseInt(value));
							java.sql.Date d = new java.sql.Date(
									cal.getTimeInMillis());

							preStm.setDate(index, d);
						} else {
							dataFormat = dataFormat.replaceAll("(mi|MI|Mi|mI)",
									"mm");
							dataFormat = dataFormat.replaceAll("(HH|Hh|hH)",
									"hh");
							dataFormat = dataFormat.replaceAll(
									"(PM|AM|pm|am|Pm|pM|Am|aM)", "aa");
							dataFormat = dataFormat.replaceAll(
									"(HH24|hh24|Hh24|hH24)", "HH");
							dataFormat = dataFormat.replaceAll("(SS|sS|Ss)",
									"ss");
							SimpleDateFormat format = new SimpleDateFormat(
									dataFormat);
							Date d = format.parse(value);

							preStm.setTimestamp(index,
									new Timestamp(d.getTime()));
						}
					} catch (Exception e) {
						throw new AL_ImportException("Parse datetime error ("
								+ originalDataFmt + "->" + dataFormat + "): "
								+ e.getMessage(), "VMSC-0367");
					}
				}
			}
		}

		return preStm.executeUpdate();
		// preStm.addBatch();
	}*/

	/**
	 * 
	 * addBatchStmt(PreparedStatement, String[], Vector<AL_MappingInfo>, String)
	 * 
	 * The extended version of exePreStatement method
	 * Parameters:
	 * 	PreparedStatement: SQL exec handle
	 * 	String[]: containts column's value separated by ","
	 * 	Vector<AL_MappingInfo>: points to map containing mapping values
	 * 	String: specifies raw table's name
	 * 
	 * Description:
	 * 	This method formats an insert query based on values provided by input parameters and add to batch.
	 * 
	 * Return:
	 * 	int
	 * 
	 * Implemented: tp
	 */
	private int addBatchStmt(PreparedStatement preStm, String[] lstValues,
			Vector<AL_MappingInfo> mapping, String rawTable)
			throws AL_ImportException, SQLException {
		if (mapping.size() > lstValues.length) {
			throw new AL_ImportException("File format error, file column = "
					+ lstValues.length + "; require at least " + mapping.size());
		}

		String dataType = "";
		String dataFormat = "";
		String value = "";
		int columnIdx = -1;
		int index = 0;
		AL_MappingInfo rowMap = null;

		while (index < mapping.size()) {
			rowMap = mapping.get(index);
			try {
				columnIdx = Integer.parseInt(rowMap.getFileColumn());
			} catch (NumberFormatException nfe) {
				throw new AL_ImportException("File column invalid: "
						+ rowMap.getFileColumn(), "VMSC-0365");
			}
			if (columnIdx == -1) {
				throw new AL_ImportException("Column " + rawTable + "."
						+ rowMap.getTableColumn() + " not found at index -1",
						"VMSC-0365");
			}
			if (columnIdx >= lstValues.length) {
				throw new AL_ImportException("Column " + rawTable + "."
						+ rowMap.getTableColumn() + " not found at index "
						+ columnIdx + " (total columns " + lstValues.length
						+ ")", "VMSC-0361");
			}
			value = lstValues[columnIdx].trim();
			index++;

			dataType = rowMap.getDataType();
			if (dataType.equalsIgnoreCase(DATA_TYPE_NUMBER)) {
				try {
					if (value.length() == 0 || value.indexOf("?") > -1) {
						preStm.setNull(index, Types.NUMERIC);
					} else {
						preStm.setDouble(index, Double.parseDouble(value));
					}
				} catch (Exception e) {
					throw new AL_ImportException(e.getMessage(), "VMSC-0367");
				}
			} else if (dataType.equalsIgnoreCase(DATA_TYPE_VARCHAR)) {
				if (value.length() == 0 || value.indexOf("?") > -1) {
					preStm.setNull(index, Types.VARCHAR);
				} else {
					preStm.setString(index, value);
				}
			} else if (dataType.equalsIgnoreCase(DATA_TYPE_DATE)
					|| dataType.equalsIgnoreCase(DATA_TYPE_TIMESTAMP)) {
				if (value.length() == 0 || value.indexOf("?") > -1) {
					preStm.setNull(index, Types.DATE);
				} else {
					dataFormat = rowMap.getDataFormat();
					if (dataFormat == null || dataFormat.length() == 0) {
						throw new AL_ImportException(
								"Datetime datatype must have dataformat",
								"VMSC-0363");
					}
					String originalDataFmt = dataFormat;
					try {
						if (dataFormat.trim().equalsIgnoreCase("DDD")) {
							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.DAY_OF_YEAR,
									Integer.parseInt(value));
							java.sql.Date d = new java.sql.Date(
									cal.getTimeInMillis());

							preStm.setDate(index, d);
						} else {
							dataFormat = dataFormat.replaceAll("(mi|MI|Mi|mI)",
									"mm");
							dataFormat = dataFormat.replaceAll("(HH|Hh|hH)",
									"hh");
							dataFormat = dataFormat.replaceAll(
									"(PM|AM|pm|am|Pm|pM|Am|aM)", "aa");
							dataFormat = dataFormat.replaceAll(
									"(HH24|hh24|Hh24|hH24)", "HH");
							dataFormat = dataFormat.replaceAll("(SS|sS|Ss)",
									"ss");
							SimpleDateFormat format = new SimpleDateFormat(
									dataFormat);
							Date d = format.parse(value);

							preStm.setTimestamp(index,
									new Timestamp(d.getTime()));
						}
					} catch (Exception e) {
						throw new AL_ImportException("Parse datetime error ("
								+ originalDataFmt + "->" + dataFormat + "): "
								+ e.getMessage(), "VMSC-0367");
					}
				}
			}
		}
		preStm.addBatch();
		return 1;
	}
	
	private String buildPreparedStatement(String rawTable,
			Vector<AL_MappingInfo> mapping) throws AL_ImportException {

		AL_MappingInfo rowMap;

		StringBuffer buf = new StringBuffer("INSERT /*+ APPEND */ INTO ");
		buf.append(rawTable);
		buf.append("(");

		int cnt = 0;
		for (cnt = 0; cnt < mapping.size(); cnt++) {
			rowMap = mapping.get(cnt);
			buf.append(rowMap.getTableColumn());
			buf.append(", ");
		}
		buf.delete(buf.length() - 2, buf.length());
		buf.append(") VALUES (");

		for (int i = 0; i < cnt - 1; i++) {
			buf.append("?, ");
		}
		buf.append("?)");

		return buf.toString();
	}

	private Vector<AL_MappingInfo> makeFileColumn(
			Vector<AL_MappingInfo> mapping, String header, String sep,
			String rawTable) throws AL_ImportException {
		Vector<AL_MappingInfo> lstMap = this.cloneMapping(mapping);

		String columnNames[] = header.split(sep);

		if (columnNames.length > 0) {
			String col = "";
			String colNotExist = "";
			AL_MappingInfo map = null;
			for (int i = 0; i < lstMap.size(); i++) {
				map = lstMap.get(i);
				col = map.getFileColumnHeader();
				col = col.toUpperCase();

				map.setFileColumn("-1");
				for (int j = 0; j < columnNames.length; j++) {
					String str = columnNames[j].toUpperCase();
					if (col.equalsIgnoreCase(str)) {
						map.setFileColumn(String.valueOf(j));
					}
				}
				if (map.getFileColumn().equalsIgnoreCase("-1")) {
					if (colNotExist.length() > 0) {
						colNotExist += ";";
					}
					colNotExist += col;
				}
			}

			if (colNotExist.length() > 0) {
				throw new AL_ImportException("Column headers '" + colNotExist
						+ "' not found for " + rawTable, "VMSC-0362");
			}
		}
		return lstMap;
	}

	private Vector<AL_MappingInfo> cloneMapping(Vector<AL_MappingInfo> map) {
		Vector<AL_MappingInfo> list = new Vector<AL_MappingInfo>();
		AL_MappingInfo info = null;
		for (AL_MappingInfo m : map) {
			info = new AL_MappingInfo();

			info.setTableColumn(m.getTableColumn());
			info.setFileColumn(m.getFileColumn());
			info.setFileColumnHeader(m.getFileColumnHeader());
			info.setDataType(m.getDataType());
			info.setDataFormat(m.getDataFormat());

			list.add(info);
		}
		return list;
	} 
}
