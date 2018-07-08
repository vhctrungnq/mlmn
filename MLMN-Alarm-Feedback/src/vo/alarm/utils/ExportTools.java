package vo.alarm.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import vo.CTableConfig;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportTools {

	private static DataSource dataSource = null;
	private static CallableStatement cs = null;
	private static Connection con = null;
	private static SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
	private static DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private static Logger logger = LoggerFactory.getLogger(ExportTools.class);
	//ket noi database
	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:/vmsc6_alarm");
			//logger.info("Init Datasource success.");
		} catch (NamingException e) {
			//logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
	// Create cellFormat Header
	public static WritableCellFormat getCellFormatHeader(WritableCellFormat cellFormatHeader) throws WriteException {
		cellFormatHeader.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormatHeader.setBackground(Colour.GREEN, Pattern.SOLID);
		cellFormatHeader.setAlignment(Alignment.CENTRE);
		
		// create create a bold font
	    WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
	    times10ptBold.setColour(Colour.WHITE);
	    cellFormatHeader.setFont(times10ptBold);
	    
		return cellFormatHeader;
	}
	
	public static WritableCellFormat getCellFormat(WritableCellFormat cellFormat) throws WriteException {
		
	    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
		return cellFormat;
	}
	
	public static String unescapeHtml(String msg) {
		if (msg!=null&& !msg.equals(""))
		{
			String str= StringEscapeUtils.unescapeHtml(msg);
			return str.replaceAll("</?(font|p|h1|h2|h3|h4|h5|and h6|strong|em|abbr|acronym|address|bdo|blockquote|cite|q|code|ins|del|dfn|kbd|pre|samp|var|br){1}.*?/?>", "");
		}
		return "";
	}
	
	public static void exportToExcel(String procedureName, List<String> paramList,
			File tempFile, String exportFileName, String sheetName,
			String title, List<CTableConfig> columnList,String isUnesCapeHtml) {
		ResultSet result = null;
		//System.out.println(tableName);
		//PK_AL_SEND_MAIL.PR_DATA_ATTACH_MAIL_GET(?, ?, ?, ?, ?,?)
		int count = paramList.size()+1;
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+procedureName+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.CURSOR);
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(count);
        	
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(sheetName);
			
			int rownum = 0;
			//caption
			HSSFCellStyle style1 = workbook.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
             HSSFFont bold1 = workbook.createFont();
            bold1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bold1.setFontHeightInPoints((short) 14);
            style1.setFont(bold1);
            
            
            HSSFRow xlsRow1 = sheet.createRow(rownum++);
            xlsRow1.setHeightInPoints(30);
            HSSFCell cell1 = xlsRow1.createCell(0);
            cell1.setCellStyle(style1);
            cell1.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(xlsRow1.getRowNum(),cell1.getColumnIndex(), xlsRow1.getRowNum(), (columnList.size()-1)));
            	
			//Tao header cho file
            HSSFCellStyle headerStyle = workbook.createCellStyle();
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            headerStyle.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
            headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(new HSSFColor.GREEN().getIndex());
            
            HSSFFont bold = workbook.createFont();
            bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bold.setColor(HSSFColor.WHITE.index);
            headerStyle.setFont(bold);

			HSSFRow header = sheet.createRow(rownum++);
			int colnum=0;
			for (CTableConfig cTableConfig : columnList) {
				if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
				{
					HSSFCell cell = header.createCell(colnum++);
					cell.setCellValue(cTableConfig.getColumnName());
					cell.setCellStyle(headerStyle);
				}
				
			}
			//tao body cho file
			HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);
            style.setBorderTop((short) 1);
          
            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			try {
				while (result.next()) {
					colnum = 0;
					//HSSFRow row = sheet.createRow(rownum);
					HSSFRow xlsRow = sheet.createRow(rownum++);
					for (CTableConfig cTableConfig : columnList) {
						if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
						{
							HSSFCell cell = xlsRow.createCell(colnum++);
							if (cTableConfig.getDataType().equalsIgnoreCase("date"))
							{
								System.out.println(cTableConfig.getTableColumn());
								try
								{
									String myDateStr = result.getString(cTableConfig.getTableColumn()); //field is of type Date
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
									Date myDate = sdf.parse(myDateStr);
									cell.setCellValue(myDate);
								}
								catch(Exception exp)
								{
									cell.setCellValue(result.getString(cTableConfig.getTableColumn()));
								}
								
							}
							
							else
							{
								String resultF ="";
								if (cTableConfig.getDataType().equalsIgnoreCase("string"))
								{
									resultF= result.getString(cTableConfig.getTableColumn());
									if (resultF==null || resultF.equalsIgnoreCase("null"))
									{
										resultF="";
									}
									else
									{
										if (isUnesCapeHtml!=null&&isUnesCapeHtml.equals("Y"))
										{
											resultF=unescapeHtml(resultF);
										}
										
									}
								}
								else
								{
									try
										{	
											resultF= result.getString(cTableConfig.getTableColumn());
											if (resultF==null || resultF.equalsIgnoreCase("null"))
											{
												resultF="0";
											}
											if (Double.parseDouble(resultF)<1&&Double.parseDouble(resultF)>0)
											{
												resultF = "0"+result.getString(cTableConfig.getTableColumn());
											}
										}
										catch(Exception exp){}
								}
							
								cell.setCellValue(resultF);
							}
							 cell.setCellStyle(style);
						}
					}
				}
				
				// adjust the column widths
	            int colCount = 0;
	            while (colCount <= colnum)
	            {
	                sheet.autoSizeColumn((short) colCount++);
	            }

			} catch (Exception e) {
				e.printStackTrace();
				//logger.warn("SENDMAIL_QUERY", e);
				System.out.println("export file :"+e);
			}
			//export
			try {
				 FileOutputStream out =
		                new FileOutputStream(tempFile);
		        workbook.write(out);
		        out.close();
		        System.out.println("Excel written successfully..");   
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		  //      logger.warn("CREATE_FILE_EXPORT", e);
		        System.out.println("export file :"+e);
		    } catch (IOException e) {
		  //  	logger.warn("CREATE_CONTENT_EXPORT", e);
		    	System.out.println("export file :"+e);
		        e.printStackTrace();
		    }
		} 
		catch(Exception ex)
		{
			//logger.warn("EXPORT_FILE", ex);
			System.out.println("export file :"+ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					//logger.warn("RESULT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					//logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					//logger.warn("CONECTION_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}

		}
		
	}
	
	public static void exportToCSV(String procedureName, List<String> paramList,
			File tempFile, String exportFileName, String sheetName,
			String title, List<CTableConfig> columnList,String isUnesCapeHtml) {
		String CSV_SEPARATOR = ";";
		ResultSet result = null;
		//System.out.println(tableName);
		//PK_AL_SEND_MAIL.PR_DATA_ATTACH_MAIL_GET(?, ?, ?, ?, ?,?)
		int count = paramList.size()+1;
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+procedureName+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.CURSOR);
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(count);
        	
        	Writer writer = null;
        	//SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        	//BufferedOutputStream bout = null;
            try {
            	writer = new BufferedWriter(new OutputStreamWriter(
	                    new FileOutputStream(tempFile.getPath()), "UTF-8"));
            	// bout = new BufferedOutputStream( new FileOutputStream(tempFile.getPath()));
                String lineHeader="";
    			for (CTableConfig cTableConfig : columnList) {
    				if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
    				{
    					lineHeader=lineHeader+cTableConfig.getColumnName()+CSV_SEPARATOR;	
    					
    				}
    			}
    			lineHeader+=System.getProperty("line.separator");
    			 writer.write(lineHeader);
    			//bout.write(lineHeader.getBytes());
    			while (result.next()) {
    				String line="";
    				for (CTableConfig cTableConfig : columnList) {
    					if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
						{
    						String resultF ="";
							resultF= result.getString(cTableConfig.getTableColumn());
							if (resultF==null || resultF.equalsIgnoreCase("null"))
							{
								resultF="";
							}
							else
							{
								if (isUnesCapeHtml!=null&&isUnesCapeHtml.equals("Y"))
								{
									resultF=unescapeHtml(resultF);
								}
								
							}
							line=line+resultF+CSV_SEPARATOR;		
						}
							
					}
    				line+=System.getProperty("line.separator");
    				//bout.write(line.getBytes());
    				writer.write(line);
    			
    			}
    			System.out.println("Excel written successfully..");   
            }
            catch (IOException e) {
            	System.out.println("export file :"+e);
            } finally {
               /*if (bout != null) {
                    try {
                       bout.close();
    	                } catch (Exception e) {
    	                	System.out.println("OUT file :"+e);
                    }
               }*/
            	if (writer != null) {
                    try {
                        writer.close();
    	                } catch (Exception e) {
    	                	System.out.println("OUT file :"+e);
    	                }
                }
            }
		    
		} 
		catch(Exception ex)
		{
			//logger.warn("EXPORT_FILE", ex);
			System.out.println("RESULT file :"+ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					//logger.warn("RESULT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					//logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					//logger.warn("CONECTION_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}

		}
		
	}
	
	public static void exportToExcelString(String procedureName, List<String> paramList,
			File tempFile, String exportFileName, String sheetName,
			String title, List<CTableConfig> columnList,String isUnesCapeHtml) {
		ResultSet result = null;
		//System.out.println(tableName);
		//PK_AL_SEND_MAIL.PR_DATA_ATTACH_MAIL_GET(?, ?, ?, ?, ?,?)
		int count = paramList.size()+1;
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+procedureName+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.CURSOR);
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(count);
        	
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(sheetName);
			
			int rownum = 0;
			//caption
		/*	HSSFCellStyle style1 = workbook.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
             HSSFFont bold1 = workbook.createFont();
            bold1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bold1.setFontHeightInPoints((short) 14);
            style1.setFont(bold1);
            
            
            HSSFRow xlsRow1 = sheet.createRow(rownum++);
            xlsRow1.setHeightInPoints(30);
            HSSFCell cell1 = xlsRow1.createCell(0);
            cell1.setCellStyle(style1);
            cell1.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(xlsRow1.getRowNum(),cell1.getColumnIndex(), xlsRow1.getRowNum(), (columnList.size()-1)));
           */ 	
			//Tao header cho file
            HSSFCellStyle headerStyle = workbook.createCellStyle();
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            headerStyle.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
            headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(new HSSFColor.GREEN().getIndex());
            
            HSSFFont bold = workbook.createFont();
            bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bold.setColor(HSSFColor.WHITE.index);
            headerStyle.setFont(bold);

			HSSFRow header = sheet.createRow(rownum++);
			int colnum=0;
			for (CTableConfig cTableConfig : columnList) {
					HSSFCell cell = header.createCell(colnum++);
					cell.setCellValue(cTableConfig.getColumnName());
					cell.setCellStyle(headerStyle);
			}
			//tao body cho file
			HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);
            style.setBorderTop((short) 1);
          
           // SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			try {
				while (result.next()) {
					colnum = 0;
					//HSSFRow row = sheet.createRow(rownum);
					HSSFRow xlsRow = sheet.createRow(rownum++);
					for (CTableConfig cTableConfig : columnList) {
						HSSFCell cell = xlsRow.createCell(colnum++);
							String resultF ="";
							resultF= result.getString(cTableConfig.getTableColumn());
								/*if (resultF==null || resultF.equalsIgnoreCase("null"))
								{
									resultF="";
								}
								else
								{
									if (isUnesCapeHtml!=null&&isUnesCapeHtml.equals("Y"))
									{
										resultF=unescapeHtml(resultF);
									}
									
								}
							*/
							cell.setCellValue(resultF);
							cell.setCellStyle(style);
						}
				}
				
				// adjust the column widths
	            int colCount = 0;
	            while (colCount <= colnum)
	            {
	                sheet.autoSizeColumn((short) colCount++);
	            }

			} catch (Exception e) {
				e.printStackTrace();
				//logger.warn("SENDMAIL_QUERY", e);
				System.out.println("export file :"+e);
			}
			//export
			try {
				 FileOutputStream out =
		                new FileOutputStream(tempFile);
		        workbook.write(out);
		        out.close();
		        System.out.println("Excel written successfully..");   
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		  //      logger.warn("CREATE_FILE_EXPORT", e);
		        System.out.println("export file :"+e);
		    } catch (IOException e) {
		  //  	logger.warn("CREATE_CONTENT_EXPORT", e);
		    	System.out.println("export file :"+e);
		        e.printStackTrace();
		    }
		} 
		catch(Exception ex)
		{
			//logger.warn("EXPORT_FILE", ex);
			System.out.println("export file :"+ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					//logger.warn("RESULT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					//logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					//logger.warn("CONECTION_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}

		}
		
	}
	
	
	public static void useBufferedFileOutPutStream(List<String> content, String filePath) {
	       Writer writer = null;

		        try {
	 
		            // Using OutputStreamWriter you don't have to convert the String to byte[]
		            writer = new BufferedWriter(new OutputStreamWriter(
	                    new FileOutputStream(filePath), "UTF-8"));
		 
		            for (String line : content) {
	                line += System.getProperty("line.separator");
		                writer.write(line);
	            }
		 
	        } catch (IOException e) {
	 
	        } finally {
		 
		            if (writer != null) {
		                try {
	                    writer.close();
	                } catch (Exception e) {
	 
		                }
		            }
	        }
	    }
	
	private static SimpleDateFormat formatter_full= new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static String exportToStringArray(String procedureName, List<String> paramList, List<CTableConfig> columnList) {
		
		ResultSet result = null;
		int count = paramList.size()+1;
		String data= null;
		
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+procedureName+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.CURSOR);
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(count);

			data="[";
			String conSum="";
			int i= 0;
			try {
				while (result.next()) {
					String array="{";
					String con="";
					for (CTableConfig cTableConfig : columnList) {	
						if (cTableConfig.getTableColumn()!=null)
						{
							if (cTableConfig.getColumnName().equalsIgnoreCase("STT"))
							{
								i++;
								array=array +con +"\""+cTableConfig.getDataField()+"\": "+"\""+i+"\"";
							}
							else {
								if (cTableConfig.getDataType().equalsIgnoreCase("string"))
								{
									//String result = result.getString(cTableConfig.getTableColumn())==null?"":result.getString(cTableConfig.getTableColumn());
									String resultF ="";
									try{
										resultF = result.getString(cTableConfig.getTableColumn());
										if (resultF==null || resultF.equalsIgnoreCase("null"))
										{
											resultF="";
										}
									}
									catch(Exception exp){}
									
									array=array +con +"\""+cTableConfig.getDataField()+"\": "+"\""+resultF+"\"";
									
								}
								else if (cTableConfig.getDataType().equalsIgnoreCase("date"))
								{
									String resultF ="";
									try{
										System.out.println(result.getDate(cTableConfig.getTableColumn()));
										resultF = formatter_full.format(result.getDate(cTableConfig.getTableColumn()));
									}
									catch(Exception exp){}
									
									array=array +con +"\""+cTableConfig.getDataField()+"\": "+"\""+resultF+"\"";
								}
								else if (cTableConfig.getDataType().equalsIgnoreCase("number"))
								{
									String resultF ="0";
									try
										{
											resultF= result.getString(cTableConfig.getTableColumn());
											if (resultF==null || resultF.equalsIgnoreCase("null"))
											{
												resultF="0";
											}
											if ((Double.parseDouble(resultF)<1&&Double.parseDouble(resultF)>0))
											{
												resultF = "0"+result.getString(cTableConfig.getTableColumn());
											}
											if ((Double.parseDouble(resultF)>-1&&Double.parseDouble(resultF)<0))
											{
												Double resl =Double.parseDouble(resultF)*(-1);
												resultF = "-"+resl;
											}
										}
										catch(Exception exp){}
									
										array=array +con +"\""+cTableConfig.getDataField()+"\": "+"\""+resultF+"\"";
								}
								else
								{
									array=array +con +"\""+cTableConfig.getDataField()+"\": "+ "\""+result.getObject(cTableConfig.getTableColumn())+"\"";
								}
							}
						}
						
						con=",";
					}
					array= array+ "}";
					//them vao danh sach du lieu
					data = data + conSum+ array;	
					conSum=",";
				}
				
				data = data +"]";
				System.out.println("localdata:"+ data);
			} catch (Exception e) {
				e.printStackTrace();
				//logger.warn("SENDMAIL_QUERY", e);
				System.out.println("SENDMAIL_QUERY:"+ e);
			}
			
		} 
		catch(Exception ex)
		{
			//logger.warn("EXPORT_FILE", ex);
			System.out.println("SENDMAIL_QUERY:"+ ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					//logger.warn("RESULT_CLOSE", ex);
					System.out.println("SENDMAIL_QUERY :"+ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					//logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
					System.out.println("SENDMAIL_QUERY :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					//logger.warn("CONECTION_CLOSE", ex);
					System.out.println("SENDMAIL_QUERY :"+ex);
                }
			}

		}
		return data;
	}
	
	/**
	 * get sql query of procedure
	 * @param procedureName
	 * @param paramList
	 * @param columnList
	 * @return
	 */
	public static String getProcedureSqlQuery(String procedureName, List<String> paramList, List<CTableConfig> columnList) {
		
		int count = paramList.size()+1;
		String data= null;
		
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+procedureName+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.VARCHAR);
        	cs.registerOutParameter(count + 1, OracleTypes.CURSOR);
        	
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	data = (String) cs.getObject(count + 1);
		} 
		catch(Exception ex)
		{
			logger.error("getProcedureSqlQuery", ex);
			System.out.println("getProcedureSqlQuery:"+ ex);
		}
		finally {
			
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.error("getProcedureSqlQuery", ex);
					System.out.println("getProcedureSqlQuery :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					logger.error("getProcedureSqlQuery", ex);
					System.out.println("getProcedureSqlQuery :"+ex);
                }
			}

		}
		return data;
	}
	
	/**
	 * get json data from sql query
	 * @param sql
	 * @return
	 */
	public static String exportToStringArray(String sql) {
		
		ArrayList<String[]> data = getDataFromSqlQuery(sql);
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("[");
		
		int i = 0;
		int j = 0;
		String condition = "";
		String[] firstRow = data.get(0);
		
		for(String[] header: data) {
			if (i == 0) {
				i++;
				continue;
			}
			
			builder.append(condition).append("{");
			
			condition = "";
			for(j = 0; j < firstRow.length; j++) {
				builder.append(condition).append("\"").append(firstRow[j]).append("\":\"").append(header[j]).append("\"");
				condition = ",";
			}
			
			builder.append("}");
		}
		
		builder.append("]");
		
		return builder.toString();
	}
	
	/**
	 * get dynamic data from sql query
	 * @param sql
	 * @return
	 */
	public static ArrayList<String[]> getDataFromSqlQuery(String sql) {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			String[] record = new String[columnCount];
			
			// header
			for (int i = 1; i < columnCount + 1; i++ ) {
				record[i-1] = rsmd.getColumnLabel(i);
			}
			data.add(record);
			
			// data
			while(rs.next()) {
				
				record = new String[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					record[i-1] = rs.getString(i);
				}
				data.add(record);
			}
		} 
		catch(Exception ex)
		{
			logger.error("getProcedureSqlQuery", ex);
			System.out.println("getProcedureSqlQuery:"+ ex);
		}
		finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.error("getProcedureSqlQuery", ex);
					System.out.println("getProcedureSqlQuery :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					logger.error("getProcedureSqlQuery", ex);
					System.out.println("getProcedureSqlQuery :"+ex);
                }
			}

		}
		return data;
	}
	
	public static String exportToStringHTML(String procedureName, List<String> paramList, List<CTableConfig> columnList,String titleBlock,String tableName) {

		ResultSet result = null;
		int count = paramList.size()+1;
		String content="";
		
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+procedureName+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.CURSOR);
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(count);

        	//SO COT TREN GRId
        	content=content +"<h2 align=\"left\" style=\"font-weight: bold;font-size: 12;\">"+titleBlock+"</h2>";
			content=content +"<table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \""+tableName+"\" >";
			content=content + "<thead>";
			//Group header
			String headerTop= "<th style=\"border:1px solid #000000;\" rowspan=\"#R#\">STT</th>";
			String isHeaderTop="N";
			int rowCount=1;
			if (columnList.size()>0)
			{
				String colTemp="";
				int numColTrmp=0;
				for (CTableConfig cTableConfig : columnList) {
					if (cTableConfig.getColumnGroupHeader()!=null)
					{
						isHeaderTop ="Y";
						rowCount=2;
						if (!colTemp.equalsIgnoreCase(cTableConfig.getColumnGroupHeader()))
						{
							if (numColTrmp>0)
							{
								headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp+1));
								System.out.println("replace:"+headerTop);
							}
							headerTop=headerTop + "<th style=\"border:1px solid #000000;\" colspan=\"#C#\">"+cTableConfig.getColumnGroupHeader()+"</th>";
							
							colTemp = cTableConfig.getColumnGroupHeader();
							numColTrmp=0;
						}
						else
						{
							numColTrmp++;
						}
					}
					else
					{
						if (numColTrmp>0)
						{
							headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp+1));
							System.out.println("replace:"+headerTop);
						}
						if (cTableConfig.getIsEnable().equalsIgnoreCase("Y")){
							headerTop=headerTop + "<th style=\"border:1px solid #000000;\" rowspan=\"#R#\">"+cTableConfig.getColumnName()+"</th>";
							colTemp = "";
							numColTrmp=0;
						}
					}
					
				}
				if (numColTrmp>0)
				{
					numColTrmp++;
					headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp));
				}
				colTemp = "";
				numColTrmp=0;
			}
			if (!headerTop.equals(""))
			{
				headerTop = headerTop.replace("#R#", String.valueOf(rowCount));
				content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color:gainsboro;color:black;\">"+headerTop+"</tr>";
			}
			if (isHeaderTop.equals("Y")){
				content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color:gainsboro;color:black;\">";
				//content=content + <th style=\"border:1px solid  #0099CC;width:50px;\">STT</th>";
				for (CTableConfig cTableConfig : columnList) {
					if (!headerTop.equals(""))
					{
						if (cTableConfig.getColumnGroup()!= null && cTableConfig.getIsEnable().equalsIgnoreCase("Y"))
						{
							content=content + "<th  style=\"border:1px solid #000000;\" >"+cTableConfig.getColumnName()+"</th>";
						}
					}
					else if (cTableConfig.getIsEnable().equalsIgnoreCase("Y"))
					{
						content=content + "<th style=\"border:1px solid #000000;\" >"+cTableConfig.getColumnName()+"</th>";
					}
				}
				content=content + "</tr>";
			}
			content=content + "</thead>";
			content=content + "<tbody>";
			 System.out.println("content header: " + content);
			//in du lieu trong result set ra 
			SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
			int i=1;
			while (result.next()) {
				content=content + "<tr>";
				content=content +"<td style=\"border:1px solid #000000;width:50px;\">"+i+"</td>";
				for (CTableConfig cTableConfig : columnList) {	
					if (cTableConfig.getTableColumn()!=null && cTableConfig.getIsEnable().equalsIgnoreCase("Y"))
					{
						content=content + "<td style=\"border:1px solid #000000;";
							if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
							{
								content=content + cTableConfig.getStyle();
							}
							content=content + "\"";
						System.out.println(content);
							if (cTableConfig.getDataType().equalsIgnoreCase("string"))
							{
								//String result = result.getString(cTableConfig.getTableColumn())==null?"":result.getString(cTableConfig.getTableColumn());
								String resultF ="";
								try
								{
									resultF = result.getString(cTableConfig.getTableColumn());
									if (resultF==null || resultF.equalsIgnoreCase("null"))
									{
										resultF="";
									}
								}
								catch(Exception exp){}
				
								content=content + " align=\"left\" name=\""+cTableConfig.getTableColumn()+"\">"+resultF;
							}
							else if (cTableConfig.getDataType().equalsIgnoreCase("date"))
							{
								String resultF ="";
								try
								{
									resultF = formatter.format(result.getDate(cTableConfig.getTableColumn()));
								}
								catch(Exception exp){}
								content=content + " align=\"left\" name=\""+cTableConfig.getTableColumn()+"\">"+resultF;
							}
							else if (cTableConfig.getDataType().equalsIgnoreCase("number"))
							{
								String resultF ="0";
								//resultF = String.valueOf(result.getDouble(cTableConfig.getTableColumn()));
								try
									{
										resultF= result.getString(cTableConfig.getTableColumn());
										if (resultF==null || resultF.equalsIgnoreCase("null"))
										{
											resultF="0";
										}
										if (Double.parseDouble(resultF)<1&&Double.parseDouble(resultF)>0)
										{
											resultF = "0"+result.getString(cTableConfig.getTableColumn());
										}
										if ((Double.parseDouble(resultF)>-1&&Double.parseDouble(resultF)<0))
										{
											Double resl =Double.parseDouble(resultF)*(-1);
											resultF = "-"+resl;
										}
									}
									catch(Exception exp){}
								content=content + " align=\"right\" name=\""+cTableConfig.getTableColumn()+"\">"+resultF;
							}
							else
							{
								content=content + " align=\"left\" name=\""+cTableConfig.getTableColumn()+"\">"+result.getObject(cTableConfig.getTableColumn());
							}
							content=content + "</td>";
					}
				}
			  //  System.out.println("content: " + content);
	           content=content + "</tr>";
	           i++;
	          
	        }
			content=content + "</tbody>";
			content=content + "</table>";
			
			
		} 
		catch(Exception ex)
		{
			System.out.println("SENDMAIL_QUERY:"+ ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					System.out.println("RESULT_CLOSE:"+  ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					System.out.println("CALL_ABLE_STATEMENT_CLOSE:"+  ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (Exception ex)
                {
					System.out.println("CONECTION_CLOSE:"+  ex);
                }
			}

		}
		
		return content;
	}

	public static int exportToSheetContinue(String sqlWhere,
			List<String> paramList, String feedbackType,
			List<CTableConfig> columnList, HSSFWorkbook workbook, HSSFSheet sheet, int rownum, String isUnesCapeHtml) {
		ResultSet result = null;
		//System.out.println(tableName);
		//PK_AL_SEND_MAIL.PR_DATA_ATTACH_MAIL_GET(?, ?, ?, ?, ?,?)
		int count = paramList.size()+1;
		try {
			//con = getDBConnection();
			con = dataSource.getConnection();     
        	cs = con.prepareCall(
                "{call "+sqlWhere+"}"
            );
        	
        	cs.registerOutParameter(count, OracleTypes.CURSOR);
        	for (int i=1;i<count;i++)
        	{
        		cs.setString(i, paramList.get(i-1));
        	}
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(count);
        	
			//Tao header cho file
            HSSFCellStyle headerStyle = workbook.createCellStyle();
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            headerStyle.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
            headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(new HSSFColor.GREEN().getIndex());
            
            HSSFFont bold = workbook.createFont();
            bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            bold.setColor(HSSFColor.WHITE.index);
            headerStyle.setFont(bold);

			HSSFRow header = sheet.createRow(rownum++);
			int colnum=0;
			for (CTableConfig cTableConfig : columnList) {
				if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
				{
					HSSFCell cell = header.createCell(colnum++);
					cell.setCellValue(cTableConfig.getColumnName());
					cell.setCellStyle(headerStyle);
				}
				
			}
			//tao body cho file
			HSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom((short) 1);
            style.setBorderLeft((short) 1);
            style.setBorderRight((short) 1);
            style.setBorderTop((short) 1);
          
            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			try {
				while (result.next()) {
					colnum = 0;
					//HSSFRow row = sheet.createRow(rownum);
					HSSFRow xlsRow = sheet.createRow(rownum++);
					for (CTableConfig cTableConfig : columnList) {
						if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
						{
							HSSFCell cell = xlsRow.createCell(colnum++);
							if (cTableConfig.getDataType().equalsIgnoreCase("date"))
							{
								System.out.println(cTableConfig.getTableColumn());
								try
								{
									String myDateStr = result.getString(cTableConfig.getTableColumn()); //field is of type Date
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
									Date myDate = sdf.parse(myDateStr);
									cell.setCellValue(myDate);
								}
								catch(Exception exp)
								{
									cell.setCellValue(result.getString(cTableConfig.getTableColumn()));
								}
								
							}
							
							else
							{
								String resultF ="";
								if (cTableConfig.getDataType().equalsIgnoreCase("string"))
								{
									resultF= result.getString(cTableConfig.getTableColumn());
									if (resultF==null || resultF.equalsIgnoreCase("null"))
									{
										resultF="";
									}
									else
									{
										if (isUnesCapeHtml!=null&&isUnesCapeHtml.equals("Y"))
										{
											resultF=unescapeHtml(resultF);
										}
										
									}
								}
								else
								{
									try
										{	
											resultF= result.getString(cTableConfig.getTableColumn());
											if (resultF==null || resultF.equalsIgnoreCase("null"))
											{
												resultF="0";
											}
											if (Double.parseDouble(resultF)<1&&Double.parseDouble(resultF)>0)
											{
												resultF = "0"+result.getString(cTableConfig.getTableColumn());
											}
										}
										catch(Exception exp){}
								}
							
								cell.setCellValue(resultF);
							}
							 cell.setCellStyle(style);
						}
					}
				}
				
				// adjust the column widths
	            int colCount = 0;
	            while (colCount <= colnum)
	            {
	                sheet.autoSizeColumn((short) colCount++);
	            }

			} catch (Exception e) {
				e.printStackTrace();
				//logger.warn("SENDMAIL_QUERY", e);
				System.out.println("export file :"+e);
			}
		} 
		catch(Exception ex)
		{
			//logger.warn("EXPORT_FILE", ex);
			System.out.println("export file :"+ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					//logger.warn("RESULT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					//logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					//logger.warn("CONECTION_CLOSE", ex);
					System.out.println("export file :"+ex);
                }
			}

		}
		return rownum;
	}

	/* ham export excel
	className: Truyen vao ten cua lop thuc the <chi trong pk vo>. Dinh dang: pk.className
	dataObjs: so ban ghi duoc ghi vao file export
	headers: Lay header cua grid
	request, response
*/
	public static void exportFileExcel(String className, List<Object> dataObjs, List<CTableConfig> headers,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// temp file
			String basePath = request.getSession().getServletContext().getRealPath("");
			String dataDir = basePath + "/upload";
			String tempName = UUID.randomUUID().toString();
			String ext = "xls";
			String outfile = tempName + "." + ext;
			File tempFile = new File(dataDir + "/" + outfile);
	
			// create data in file
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet(className, 0);
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
	
			int row = 0;
			int colurm = 0;
			Label label = null;
			// row 0: header
			for (CTableConfig header : headers) {
				if(header.getIsEnable().equals("Y")){
					label = new Label(colurm, row, header.getColumnName(), cellFormatHeader);
					sheet.addCell(label);
					colurm++;
				}
			}
			// row 1->n data
			row = 1;
			colurm = 0;
			Field field;
			Object fieldValue;
	
			// vong lap so dong du lieu
			for (Object obj : dataObjs) {
				// check object phai la truong hop cua class dua vao
				if (Class.forName("vo."+className).isInstance(obj)) {
					// vong lap so cot duoc lay ra
					for (CTableConfig header : headers) {
						if(header.getIsEnable().equals("Y")){
							field = obj.getClass().getDeclaredField(header.getDataField());
							field.setAccessible(true);
							fieldValue = field.get(obj);
							// ghi vao file excel
							label = new Label(colurm, row, checkTypeObject(fieldValue), cellFormat);
							sheet.addCell(label);
							colurm++;
						}
					}
				}
				colurm = 0;
				row++;
			}
	
			workbook.write();
			workbook.close();
			// return
			String dateNow = formatter.format(new Date());
			String filename = "File_"+className + "_" + dateNow;
			response.setContentType("application/ms-excel");
			response.setContentLength((int) tempFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");
			FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
			// xoa file temp
			tempFile.delete();
	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// check object type and return String
		static String checkTypeObject(Object value) {
			String result = "";
			if (value == null) {
				result = "";
			} else if (value instanceof Date) {
				result = sdf.format((Date) value);
			} else {
				result = String.valueOf(value);
			}
			return result;
		}
}
