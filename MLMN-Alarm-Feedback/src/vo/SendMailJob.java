package vo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.ss.usermodel.*;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Controller;

import vn.com.vhc.alarm.core.AL_Global;
import vo.alarm.utils.Mail;
import vo.alarm.utils.MailMutiple;
import vo.alarm.utils.SendMailSetting;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

@Controller
public class SendMailJob implements Job{

	public SendMailJob(){}
	private static Logger logger = Logger.getLogger(SendMailJob.class.getName());
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	//private DateFormat dffull = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static DataSource dataSource = null;
	private static CallableStatement cs = null;
	private static Connection con = null;
	//ket noi database
	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:/vmsc6_alarm");
			logger.info("Init Datasource success.");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
	
	// LAY DANH SACH CAC MAIL CAN GUI TAI THOI DIEM CHAY JOB
	public static List<SysMailParameterMaster> mailList(String dateTime) throws SQLException{
			ResultSet result = null;
			List<SysMailParameterMaster> recordList = new ArrayList<SysMailParameterMaster>();
			//con = getDBConnection();
			
			try {
				con = dataSource.getConnection(); 
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL_MLMN.PR_MAIL_PARAMETER_MASTER_GET(?,?)}"
	            );
	        	cs.registerOutParameter(2, OracleTypes.CURSOR);
	        	cs.setString(1, dateTime);
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(2);
			//in du lieu trong result set ra 
			while (result.next()) {
			   SysMailParameterMaster record  = new SysMailParameterMaster();
			   record.setMailName(result.getString("MAIL_NAME"));
			   record.setMailLevel(result.getString("MAIL_LEVEL"));
			   record.setMailTo(result.getString("MAIL_TO"));
			   record.setContentHeader(result.getString("CONTENT_HEADER"));
			   record.setContentRooter(result.getString("CONTENT_ROOTER"));
			   record.setRootFileAttached(result.getString("ROOT_FILE_ATTACHED"));
			   record.setSendingTime(result.getInt("SENDING_TIME"));
			   record.setMailId(result.getString("MAIL_ID"));
			   record.setDay(result.getDate("DAY"));
			   record.setTypeMailHour(result.getString("TYPE_MAIL_HOUR"));
			   record.setCcMail(result.getString("CC_MAIL"));
			   record.setBccMail(result.getString("BCC_MAIL"));
			   recordList.add(record);
	         }
	    	
		} 
		catch(Exception ex)
		{
			logger.warn("SENDMAIL_QUERY", ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					logger.warn("RESULT_CLOSE", ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (Exception ex)
                {
					logger.warn("CONECTION_CLOSE", ex);
                }
			}

		}
		return recordList;
	}
	//Lay danh sach dinh nghia block se hien thi trong  mail se gui nay
	public static List<SysMailParameter> blockList(String mailLevel,String mailId) throws SQLException{
		ResultSet result = null;
		List<SysMailParameter> recordList = new ArrayList<SysMailParameter>();
		//con = getDBConnection();
		try {
				con = dataSource.getConnection(); 
        	cs = con.prepareCall(
                "{call PK_AL_SEND_MAIL_MLMN.PR_MAIL_PARAMETER_BLOCK_GET(?,?,?)}"
            );
        	//cs.setQueryTimeout(seconds);
        	cs.registerOutParameter(3, OracleTypes.CURSOR);
        	cs.setString(1, mailLevel);
        	cs.setString(2, mailId);
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(3);
			//in du lieu trong result set ra 
			while (result.next()) {
			   SysMailParameter record  = new SysMailParameter();
			   record.setMailLevel(result.getString("MAIL_LEVEL"));
			   record.setBlockName(result.getString("BLOCK_NAME"));
			   record.setTableName(result.getString("TABLE_NAME"));
			   record.setWhereCondition(result.getString("WHERE_CONDITION"));
			   record.setMailId(result.getString("MAIL_ID"));
			   record.setOrderBy(result.getString("ORDER_BY"));
			   record.setOrdering(result.getInt("ORDERING"));
			   record.setFileName(result.getString("FILE_NAME"));
			   record.setBlockId(result.getString("BLOCK_ID"));
			   recordList.add(record);
	         }
	    	
	} 
		catch(Exception ex)
		{
			logger.warn("SENDMAIL_QUERY", ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					logger.warn("RESULT_CLOSE", ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (Exception ex)
                {
					logger.warn("CONECTION_CLOSE", ex);
                }
			}

		}
		return recordList;
	}
	//Lay style dinh nghia cot se hien thi trong mail theo cac muc nguong khac nhau
	public static List<CHighlightConfigs> highlightList(String tableName,String blockID) throws SQLException{
			ResultSet result = null;
			//String s="";
			List<CHighlightConfigs> recordList = new ArrayList<CHighlightConfigs>();
			//con = getDBConnection();
			try {
				con = dataSource.getConnection(); 
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL_MLMN.PR_HIGHLIGHT_CONFIG_GET(?,?,?)}"
	            );
	        	cs.registerOutParameter(3, OracleTypes.CURSOR);
	        	cs.setString(1, tableName);
	        	cs.setString(2, blockID);
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(3);
				//in du lieu trong result set ra 
        	    //tao chuoi to mau
					while (result.next()) {
						
					   CHighlightConfigs record  = new CHighlightConfigs();
					   record.setKey(result.getString("KEY"));
					   record.setFormula(result.getString("FORMULA"));
					   record.setKpi(result.getString("KPI"));
					   record.setValue(result.getString("VALUE"));
					   record.setStyle(result.getString("STYLE"));
					   record.setTYPE_STYLE(result.getString("TYPE_STYLE"));
					   record.setTYPE_VALUE(result.getString("TYPE_VALUE"));
					   record.setODERING(result.getInt("ODERING"));
					   record.setEXCEL_STYLE(result.getShort("EXCEL_STYLE"));
					   recordList.add(record);
					}
					
		    	
		} 
			catch(Exception ex)
			{
				logger.warn("SENDMAIL_QUERY", ex);
			}
			finally {
				if (result != null) {
					try
					{
						result.close();
					}
					catch (Exception ex)
	                {
						logger.warn("RESULT_CLOSE", ex);
	                }
				}
				if (cs != null) {
					try
					{
						cs.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
	                }
				}
				if (con != null) {
					try
					{
						con.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CONECTION_CLOSE", ex);
	                }
				}

			}
			return recordList;
	}	
	//Lay danh sach email trong nhom nguoi can gui thu
	public static List<SysDefineSmsEmail> userList(String groupName) throws SQLException{
				ResultSet result = null;
				List<SysDefineSmsEmail> recordList = new ArrayList<SysDefineSmsEmail>();
				//con = getDBConnection();
				try {
					con = dataSource.getConnection(); 
		        	cs = con.prepareCall(
		                "{call PK_AL_SEND_MAIL_MLMN.PR_USER_SEND_MAIL_GET(?,?)}"
		            );
		        	cs.registerOutParameter(2, OracleTypes.CURSOR);
		        	cs.setString(1, groupName);
		        	cs.executeQuery();
		        	result = (ResultSet) cs.getObject(2);

					//in du lieu trong result set ra 
					while (result.next()) {
						
						SysDefineSmsEmail record  = new SysDefineSmsEmail();
					   record.setGroupUser(result.getString("GROUP_USER"));
					   record.setGroupName(result.getString("GROUP_NAME"));
					   recordList.add(record);
			         }
			    	
			} 
				catch(Exception ex)
				{
					logger.warn("SENDMAIL_QUERY", ex);
				}
				finally {
					if (result != null) {
						try
						{
							result.close();
						}
						catch (Exception ex)
		                {
							logger.warn("RESULT_CLOSE", ex);
		                }
					}
					if (cs != null) {
						try
						{
							cs.close();
						}
						catch (Exception ex)
		                {
							logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
		                }
					}
					if (con != null) {
						try
						{
							con.close();
						}
						catch( Exception ex)
		                {
							logger.warn("CONECTION_CLOSE", ex);
		                }
					}

				}
				return recordList;
		}	
	// Luu noi dung mail gui di
	private void saveMailSended(String subject, String userTo, String content,
				String status,String mailId,String mailLevel,String hour) throws SQLException {
	//con = getDBConnection();
			try {
				con = dataSource.getConnection(); 
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL_MLMN.PR_SEND_MAIL_INSERT(?,?,?,?,?,?,?)}"
	            );
	        	cs.setString(1, subject);
	        	cs.setString(2, userTo);
	        	cs.setString(3, status);
	        	cs.setString(4, content);
	        	cs.setString(5, mailId);
	        	cs.setString(6, mailLevel);
	        	cs.setString(7, hour);
	        	cs.executeQuery();
				
		} 
			catch(Exception ex)
			{
				logger.warn("SENDMAIL_QUERY", ex);
			}
			finally {
				if (cs != null) {
					try
					{
						cs.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
	                }
				}
				if (con != null) {
					try
					{
						con.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CONECTION_CLOSE", ex);
	                }
				}

			}
			
		}
	//Lay danh sach cot dinh nghia bloc se hien thi cua tung block trong mail
	public static List<CTableConfig> columnList(String tableName,String blockId) throws SQLException{
					ResultSet result = null;
					List<CTableConfig> recordList = new ArrayList<CTableConfig>();
					//con = getDBConnection();
					try {
						con = dataSource.getConnection(); 
			        	cs = con.prepareCall(
			                "{call PK_AL_SEND_MAIL_MLMN.PR_COLUMN_TABLE_CONFIG_GET(?,?,?)}"
			            );
			        	cs.registerOutParameter(3, OracleTypes.CURSOR);
			        	cs.setString(1, tableName);
			        	cs.setString(2, blockId);
			        	cs.executeQuery();
			        	result = (ResultSet) cs.getObject(3);
						//in du lieu trong result set ra 
						while (result.next()) {
							
						   CTableConfig record  = new CTableConfig();
						   record.setTableName(result.getString("TABLE_NAME"));
						   record.setTableColumn(result.getString("TABLE_COLUMN"));
						   record.setDataField(result.getString("DATA_FIELD"));
						   record.setDataType(result.getString("DATA_TYPE"));
						   record.setColumnName(result.getString("COLUMN_NAME"));
						   record.setStyle(result.getString("STYLE"));
						   record.setColumnGroup(result.getString("COLUMN_GROUP"));
						   record.setColumnGroupHeader(result.getString("COLUMN_GROUP_HEADER"));
						   record.setMergeRow(result.getString("MERGE_ROW"));
						   record.setBlockId(result.getString("BLOCK_ID"));
						   record.setInChart(result.getString("IN_CHART"));
						   recordList.add(record);
				         }
				    	
				}
					catch(Exception ex)
					{
						logger.warn("SENDMAIL_QUERY", ex);
						System.out.println(ex.toString());
					}
					
					finally {
						if (result != null) {
							try
							{
								result.close();
							}
							catch (Exception ex)
			                {
								logger.warn("RESULT_CLOSE", ex);
			                }
						}
						if (cs != null) {
							try
							{
								cs.close();
							}
							catch (Exception ex)
			                {
								logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
			                }
						}
						if (con != null) {
							try
							{
								con.close();
							}
							catch (Exception ex)
			                {
								logger.warn("CONECTION_CLOSE", ex);
			                }
						}

					}
					return recordList;
			}	
	//Lay noi dung cua moi block
	public static String getDataBlock(String tableName,String titleBlock,String dateTime,String time,List<CTableConfig> columnList,List<CHighlightConfigs> highlightList,String mailID,String blockId) throws SQLException{
		
			ResultSet result = null;
			String content="<br>";
			System.out.println(tableName);
			try {
				//con = getDBConnection();
			con = dataSource.getConnection();   
				cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL_MLMN.PR_DATA_MAIL_GET(?, ?, ?, ?, ?,?,?)}"
	            );
	        	cs.registerOutParameter(7, OracleTypes.CURSOR);
	        	cs.setString(1, tableName);
	        	cs.setString(2, dateTime);
	        	cs.setString(3, time);
	        	cs.setString(4, mailID);
	        	cs.setString(5, blockId);
	        	cs.setString(6, titleBlock);
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(7);
	        	//SO COT TREN GRID
				Integer countColumn= columnList.size()+1;
				String columnGroup= null;
				String valueColumnGroup=null;
				int sttGroup=1;
	        	//TAO DU LIEU MAIL
				titleBlock= titleBlock.replace("#DATE#", dateTime).replace("#HOUR#", time+":00:00 ");
		        content=content +"<h2 align=\"left\" style=\"font-weight: bold;font-size: 12;\">"+titleBlock+"</h2>";
				content=content +"<table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \""+tableName+"\" >";
				content=content + "<thead>";
				//Group header
				String headerTop= "";
				if (columnList.size()>0 && columnList.get(0).getColumnGroupHeader()!=null)
				{
					
					String colTemp="";
					int numColTrmp=0;
					for (CTableConfig cTableConfig : columnList) {
						if (!cTableConfig.getColumnGroupHeader().equals("N"))
						{
							if (!colTemp.equalsIgnoreCase(cTableConfig.getColumnGroupHeader()))
							{
								if (numColTrmp>0)
								{
									headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp+1));
							//		System.out.println("replace:"+headerTop);
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
							//	System.out.println("replace:"+headerTop);
							}
							headerTop=headerTop + "<th style=\"border:1px solid #000000;\" rowspan=\"2\">"+cTableConfig.getColumnName()+"</th>";
							colTemp = "";
							numColTrmp=0;
						}
						
					}
					if (numColTrmp>0)
					{
						numColTrmp++;
						headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp+1));
					}
					colTemp = "";
					numColTrmp=0;
				}
				if (!headerTop.equals(""))
				{
					content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color:#00B050;color:white;\">"+headerTop+"</tr>";
				}
				content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color:#00B050;color:white;\">";
				//content=content + <th style=\"border:1px solid  #0099CC;width:50px;\">STT</th>";
				for (CTableConfig cTableConfig : columnList) {
					if (!headerTop.equals(""))
					{
						if (!cTableConfig.getColumnGroupHeader().equalsIgnoreCase("N"))
						{
							content=content + "<th  style=\"border:1px solid #000000;\" >"+cTableConfig.getColumnName()+"</th>";
						}
					}
					else
					{
						content=content + "<th style=\"border:1px solid #000000;\" >"+cTableConfig.getColumnName()+"</th>";
					}
					columnGroup = cTableConfig.getColumnGroup();
				}
				content=content + "</tr>";
				content=content + "</thead>";
				content=content + "<tbody>";
			//	 System.out.println("content header: " + content);
				//in du lieu trong result set ra 
				SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
				int i=1;
				String columnMege=null;
				int numRow=0;
				
				while (result.next()) {
					//Nhom du lieu
					if ((columnGroup!=null&&valueColumnGroup==null)||(columnGroup!=null&&!valueColumnGroup.equals(result.getString(columnGroup))))
					{
						valueColumnGroup = result.getString(columnGroup);
						content=content + "<tr><td style=\"border:1px solid;background-color : #ffecb4; font-weight: bold;\" colspan=\""+countColumn+"\">"+sttGroup+". "+valueColumnGroup+"</td></tr>";
						sttGroup++;	
						i=1;
						if (numRow>0)
						{
					//		System.out.println(content);
							content = content.replace("#R#", String.valueOf(numRow));
					//		System.out.println("replace:"+content);
							numRow=0;
							columnMege=null;
						}
					}
					content=content + "<tr>";
					
					
					for (CTableConfig cTableConfig : columnList) {	
						if (cTableConfig.getTableColumn()!=null)
						{
							if (cTableConfig.getColumnName().equalsIgnoreCase("STT"))
							{
								content=content +"<td style=\"border:1px solid;width:50px;\">"+i+"</td>";
							}
							else {
								//kIEM TRA NEU NO BI MERGE.=>kIEM TRA NO CO PHAI LA GIA TRI DAU TIEN KHONG? NEU PHAI THI IN NO RA VOI ROWSPAN=NUMROWN. nEU NO KHONG PHAI LA GIA TRI DAU TIEN
								// THI KHONG IN NO.CHO DEN KHI NO KHONG PHAI LA GIA TRI NAY NUA THI THAY THE ROWNUM= J(BIEN DEM SO DONG).
							/*	System.out.println("Column:"+cTableConfig.getTableColumn());
								System.out.println("MEGER:"+cTableConfig.getMergeRow());*/
								
								if (cTableConfig.getMergeRow()!=null && cTableConfig.getMergeRow().equalsIgnoreCase("Y"))
								{
									//neu no la gia tri dau tien
									if (columnMege==null)
									{
										numRow=1;
										try
										{
											columnMege = result.getString(cTableConfig.getTableColumn());
										}catch(Exception exp){}
										content=content + "<td rowspan=\"#R#\" style=\"border:1px solid;";
										for (CHighlightConfigs cHighlightConfigs : highlightList) {
											if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
											{
												try{
													String formular=cHighlightConfigs.getFormula();
													String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
													String value="0";
													if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
													{
														value = result.getString(cHighlightConfigs.getValue());
													}
													else
													{
														value = cHighlightConfigs.getValue();
													}
													if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
															||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
															||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
															||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
														)
													{
														content=content + cHighlightConfigs.getStyle();
													}
													else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
															||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
													{
														content=content + cHighlightConfigs.getStyle();
													}
													// them dieu kien gop khoang va
													else if (formular.contains("&"))
													{
														String[] formularList = formular.split("&");
														String[] valueList = value.split("&");
														boolean ktra=true;
														if (formularList.length == valueList.length)
														{
															for (int l = 0; l  < formularList.length; l ++)
															{
																if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																	||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																	||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																	||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																	||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																	||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																	)
																{
																	ktra = false;
																	break;
																}
															}
															if (ktra == true)
															{
																content=content + cHighlightConfigs.getStyle();
															}
														}
													}
													// them dieu kien gop khoang hoac
													else if (formular.contains("|"))
													{
														
														String[] formularList = formular.split("|");
														String[] valueList = value.split("|");
														boolean ktra=false;
														if (formularList.length == valueList.length)
														{
															for (int l = 0; l  < formularList.length; l ++)
															{
																if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																	||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																	||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																	||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																	||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																	||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																	)
																{
																	ktra = true;
																	break;
																}
															}
															if (ktra == true)
															{
																content=content + cHighlightConfigs.getStyle();
															}
														}
													}
												}catch(Exception exp){}
											}
										}
										if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
										{
											content=content + cTableConfig.getStyle();
										}
										content=content + "\"";
									
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
											content=content + " align=\"left\" name=\""+cTableConfig.getTableColumn()+"\">"+resultF;
										}
										else if (cTableConfig.getDataType().equalsIgnoreCase("date"))
										{
											String resultF ="";
											try{
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
											
											content=content + " align=\"right\" name=\""+cTableConfig.getTableColumn()+"\">"+resultF;
										}
										else
										{
											content=content + " align=\"left\" name=\""+cTableConfig.getTableColumn()+"\">"+result.getObject(cTableConfig.getTableColumn());
										}
										content=content + "</td>";
									}// Neu no khong phai la gia tri null thi kiem tra no co bat dau boi gia tri khac khong. Neu phai thi thay the va khoi tao lai.
									else
									{
										try{
											if (columnMege!=null && !columnMege.equals(result.getString(cTableConfig.getTableColumn())))
											{
									//			System.out.println(content);
												content = content.replace("#R#", String.valueOf(numRow));
									//			System.out.println("replace:"+content);
												numRow=1;
												columnMege = result.getString(cTableConfig.getTableColumn());
												content=content + "<td rowspan=\"#R#\" style=\"border:1px solid;";
												for (CHighlightConfigs cHighlightConfigs : highlightList) {
													if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
													{
/*														if (cHighlightConfigs.getFormula().equals(">=")&& ((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())>= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())>= result.getDouble(cHighlightConfigs.getValue()))))
														{
															content=content + cHighlightConfigs.getStyle();
														}
														else if (cHighlightConfigs.getFormula().equals(">")&&((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())> Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())> result.getDouble(cHighlightConfigs.getValue()))))
														{
															content=content + cHighlightConfigs.getStyle();
														}
														else if (cHighlightConfigs.getFormula().equals("<=")&&((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())<= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())<= result.getDouble(cHighlightConfigs.getValue()))))
														{
															content=content + cHighlightConfigs.getStyle();
														}
														else if (cHighlightConfigs.getFormula().equals("<")&&((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())< Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())< result.getDouble(cHighlightConfigs.getValue()))))
														{
															content=content + cHighlightConfigs.getStyle();
														}
														else if (cHighlightConfigs.getFormula().equals("!=")&&!result.getString(cTableConfig.getTableColumn()).equalsIgnoreCase(cHighlightConfigs.getValue()))
														{
															content=content + cHighlightConfigs.getStyle();
														}
														else if ((cHighlightConfigs.getFormula().equals("==")||cHighlightConfigs.getFormula()==null)&&result.getString(cTableConfig.getTableColumn()).equalsIgnoreCase(cHighlightConfigs.getValue()))
														{
															content=content + cHighlightConfigs.getStyle();
														}*/
														
														try{
															String formular=cHighlightConfigs.getFormula();
															String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
															String value="0";
															if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
															{
																value = result.getString(cHighlightConfigs.getValue());
															}
															else
															{
																value = cHighlightConfigs.getValue();
															}
															if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
																	||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
																	||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
																	||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
																)
															{
																content=content + cHighlightConfigs.getStyle();
															}
															else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
																	||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
															{
																content=content + cHighlightConfigs.getStyle();
															}
															// them dieu kien gop khoang va
															else if (formular.contains("&"))
															{
																String[] formularList = formular.split("&");
																String[] valueList = value.split("&");
																boolean ktra=true;
																if (formularList.length == valueList.length)
																{
																	for (int l = 0; l  < formularList.length; l ++)
																	{
																		if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																			||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																			||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																			||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																			||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																			||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																			)
																		{
																			ktra = false;
																			break;
																		}
																	}
																	if (ktra == true)
																	{
																		content=content + cHighlightConfigs.getStyle();
																	}
																}
															}
															// them dieu kien gop khoang hoac
															else if (formular.contains("|"))
															{
																
																String[] formularList = formular.split("|");
																String[] valueList = value.split("|");
																boolean ktra=false;
																if (formularList.length == valueList.length)
																{
																	for (int l = 0; l  < formularList.length; l ++)
																	{
																		if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																			||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																			||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																			||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																			||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																			||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																			)
																		{
																			ktra = true;
																			break;
																		}
																	}
																	if (ktra == true)
																	{
																		content=content + cHighlightConfigs.getStyle();
																	}
																}
															}
														}catch(Exception exp){}
													}
												}
												if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
												{
													content=content + cTableConfig.getStyle();
												}
												content=content + "\"";
											
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
											else
											{
												numRow++;
											}
										}catch(Exception exp){}
									}
									
									
								}
								else
								{
									content=content + "<td style=\"border:1px solid;";
									for (CHighlightConfigs cHighlightConfigs : highlightList) {
										if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
										{
											try{
												String formular=cHighlightConfigs.getFormula();
												String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
												String value="0";
												if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
												{
													value = result.getString(cHighlightConfigs.getValue());
												}
												else
												{
													value = cHighlightConfigs.getValue();
												}
												if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
														||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
														||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
														||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
													)
												{
													content=content + cHighlightConfigs.getStyle();
												}
												else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
														||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
												{
													content=content + cHighlightConfigs.getStyle();
												}
												// them dieu kien gop khoang va
												else if (formular.contains("&"))
												{
													String[] formularList = formular.split("&");
													String[] valueList = value.split("&");
													boolean ktra=true;
													if (formularList.length == valueList.length)
													{
														for (int l = 0; l  < formularList.length; l ++)
														{
															if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																)
															{
																ktra = false;
																break;
															}
														}
														if (ktra == true)
														{
															content=content + cHighlightConfigs.getStyle();
														}
													}
												}
												// them dieu kien gop khoang hoac
												else if (formular.contains("|"))
												{
													
													String[] formularList = formular.split("|");
													String[] valueList = value.split("|");
													boolean ktra=false;
													if (formularList.length == valueList.length)
													{
														for (int l = 0; l  < formularList.length; l ++)
														{
															if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																)
															{
																ktra = true;
																break;
															}
														}
														if (ktra == true)
														{
															content=content + cHighlightConfigs.getStyle();
														}
													}
												}
											}catch(Exception exp){}
										}
									}
									if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
									{
										content=content + cTableConfig.getStyle();
									}
									content=content + "\"";
								
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
						}
					}
				//    System.out.println("content: " + content);
		           content=content + "</tr>";
		           i++;
		          
		        }
			 if (numRow>0)
				{
				 content = content.replace("#R#", String.valueOf(numRow));
				}
				content=content + "</tbody>";
				content=content + "</table>";
				
				
			} 
			catch(Exception ex)
			{
				logger.warn("SENDMAIL_QUERY", ex);
			}
			finally {
				if (result != null) {
					try
					{
						result.close();
					}
					catch (Exception ex)
	                {
						logger.warn("RESULT_CLOSE", ex);
	                }
				}
				if (cs != null) {
					try
					{
						cs.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
	                }
				}
				if (con != null) {
					try
					{
						con.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CONECTION_CLOSE", ex);
	                }
				}

			}
			
			return content;
		}
	
	private String getDataWorkBlock(String tableName, String blockName,
			String day, String hourStr, String mailName, String mailTo,
			List<CTableConfig> columnList, List<CHighlightConfigs> highlightList, String filePath,String fileName,String mailID) throws SQLException {

		ResultSet result = null;
	//	ResultSet resultExport = null;
		String content="<br>";
		System.out.println(tableName);
		try {
			//con = getDBConnection();
			con = dataSource.getConnection(); 
        	cs = con.prepareCall(
                "{call PK_AL_SEND_MAIL_MLMN.PR_DATA_WORK_MAIL_GET(?, ?, ?, ?,?,?,?,?)}"
            );
        	cs.registerOutParameter(8, OracleTypes.CURSOR);
        	cs.setString(1, tableName);
        	cs.setString(2, blockName);
        	cs.setString(3, day);
        	cs.setString(4, hourStr);
        	cs.setString(5, mailName);
        	cs.setString(6, mailTo);
        	cs.setString(7, mailID);
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(8);
        	//resultExport = (ResultSet) cs.getObject(8);
        	//SO COT TREN GRID
			Integer countColumn= columnList.size()+1;
			String columnGroup= null;
			String valueColumnGroup=null;
			int sttGroup=1;
        	//TAO DU LIEU MAIL
	        content=content +"<h2 align=\"left\" style=\"font-weight: bold;font-size: 12;\">"+blockName+"</h2>";
			content=content +"<table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \""+tableName+"\" >";
			content=content + "<thead>";
			//Group header
			String headerTop= "";
			if (columnList.size()>0 && columnList.get(0).getColumnGroupHeader()!=null)
			{
				
				String colTemp="";
				int numColTrmp=0;
				for (CTableConfig cTableConfig : columnList) {
					if (!cTableConfig.getColumnGroupHeader().equals("N"))
					{
						if (!colTemp.equalsIgnoreCase(cTableConfig.getColumnGroupHeader()))
						{
							if (numColTrmp>0)
							{
								headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp+1));
							//	System.out.println("replace:"+headerTop);
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
				//			System.out.println("replace:"+headerTop);
						}
						headerTop=headerTop + "<th style=\"border:1px solid #000000;\" rowspan=\"2\">"+cTableConfig.getColumnName()+"</th>";
						colTemp = "";
						numColTrmp=0;
					}
					
				}
				if (numColTrmp>0)
				{
					numColTrmp++;
					headerTop = headerTop.replace("#C#", String.valueOf(numColTrmp+1));
				}
				colTemp = "";
				numColTrmp=0;
			}
			if (!headerTop.equals(""))
			{
				content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color:#00B050;color:white;\">"+headerTop+"</tr>";
			}
			content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color: #00B050;color:white;\">";
			//content=content + <th style=\"border:1px solid  #0099CC;width:50px;\">STT</th>";
			for (CTableConfig cTableConfig : columnList) {
				if (!headerTop.equals(""))
				{
					if (!cTableConfig.getColumnGroupHeader().equalsIgnoreCase("N"))
					{
						content=content + "<th  style=\"border:1px solid #000000;\" >"+cTableConfig.getColumnName()+"</th>";
					}
				}
				else
				{
					content=content + "<th style=\"border:1px solid #000000;\" >"+cTableConfig.getColumnName()+"</th>";
				}
				columnGroup = cTableConfig.getColumnGroup();
			}
			content=content + "</tr>";
			content=content + "</thead>";
			content=content + "<tbody>";
			// System.out.println("content header: " + content);
			//in du lieu trong result set ra 
			SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
			int i=1;
			String columnMege=null;
			int numRow=0;
			
			while (result.next()) {
				//Nhom du lieu
				if ((columnGroup!=null&&valueColumnGroup==null)||(columnGroup!=null&&!valueColumnGroup.equals(result.getString(columnGroup))))
				{
					valueColumnGroup = result.getString(columnGroup);
					content=content + "<tr><td style=\"border:1px solid;background-color : #ffecb4; font-weight: bold;\" colspan=\""+countColumn+"\">"+sttGroup+". "+valueColumnGroup+"</td></tr>";
					sttGroup++;	
					i=1;
					if (numRow>0)
					{
				//		System.out.println(content);
						content = content.replace("#R#", String.valueOf(numRow));
				//		System.out.println("replace:"+content);
						numRow=0;
						columnMege=null;
					}
				}
				content=content + "<tr>";
				
				
				for (CTableConfig cTableConfig : columnList) {	
					if (cTableConfig.getTableColumn()!=null)
					{
						if (cTableConfig.getColumnName().equalsIgnoreCase("STT"))
						{
							content=content +"<td style=\"border:1px solid;width:50px;\">"+i+"</td>";
						}
						else {
							//kIEM TRA NEU NO BI MERGE.=>kIEM TRA NO CO PHAI LA GIA TRI DAU TIEN KHONG? NEU PHAI THI IN NO RA VOI ROWSPAN=NUMROWN. nEU NO KHONG PHAI LA GIA TRI DAU TIEN
							// THI KHONG IN NO.CHO DEN KHI NO KHONG PHAI LA GIA TRI NAY NUA THI THAY THE ROWNUM= J(BIEN DEM SO DONG).
						/*	System.out.println("Column:"+cTableConfig.getTableColumn());
							System.out.println("MEGER:"+cTableConfig.getMergeRow());*/
							
							if (cTableConfig.getMergeRow()!=null && cTableConfig.getMergeRow().equalsIgnoreCase("Y"))
							{
								//neu no la gia tri dau tien
								if (columnMege==null)
								{
									numRow=1;
									try
									{
										columnMege = result.getString(cTableConfig.getTableColumn());
									}catch(Exception exp){}
									content=content + "<td rowspan=\"#R#\" style=\"border:1px solid;";
									for (CHighlightConfigs cHighlightConfigs : highlightList) {
										if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
										{
											try{
												String formular=cHighlightConfigs.getFormula();
												String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
												String value="0";
												if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
												{
													value = result.getString(cHighlightConfigs.getValue());
												}
												else
												{
													value = cHighlightConfigs.getValue();
												}
												if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
														||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
														||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
														||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
													)
												{
													content=content + cHighlightConfigs.getStyle();
												}
												else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
														||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
												{
													content=content + cHighlightConfigs.getStyle();
												}
												// them dieu kien gop khoang va
												else if (formular.contains("&"))
												{
													String[] formularList = formular.split("&");
													String[] valueList = value.split("&");
													boolean ktra=true;
													if (formularList.length == valueList.length)
													{
														for (int l = 0; l  < formularList.length; l ++)
														{
															if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																)
															{
																ktra = false;
																break;
															}
														}
														if (ktra == true)
														{
															content=content + cHighlightConfigs.getStyle();
														}
													}
												}
												// them dieu kien gop khoang hoac
												else if (formular.contains("|"))
												{
													
													String[] formularList = formular.split("|");
													String[] valueList = value.split("|");
													boolean ktra=false;
													if (formularList.length == valueList.length)
													{
														for (int l = 0; l  < formularList.length; l ++)
														{
															if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																)
															{
																ktra = true;
																break;
															}
														}
														if (ktra == true)
														{
															content=content + cHighlightConfigs.getStyle();
														}
													}
												}
											}catch(Exception exp){}
										}
									}
									if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
									{
										content=content + cTableConfig.getStyle();
									}
									content=content + "\"";
								
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
										content=content + " align=\"left\" name=\""+cTableConfig.getTableColumn()+"\">"+resultF;
									}
									else if (cTableConfig.getDataType().equalsIgnoreCase("date"))
									{
										String resultF ="";
										try{
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
								}// Neu no khong phai la gia tri null thi kiem tra no co bat dau boi gia tri khac khong. Neu phai thi thay the va khoi tao lai.
								else
								{
									try{
										if (columnMege!=null && !columnMege.equals(result.getString(cTableConfig.getTableColumn())))
										{
									//		System.out.println(content);
											content = content.replace("#R#", String.valueOf(numRow));
									//		System.out.println("replace:"+content);
											numRow=1;
											columnMege = result.getString(cTableConfig.getTableColumn());
											content=content + "<td rowspan=\"#R#\" style=\"border:1px solid;";
											for (CHighlightConfigs cHighlightConfigs : highlightList) {
												if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
												{
													try{
														String formular=cHighlightConfigs.getFormula();
														String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
														String value="0";
														if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
														{
															value = result.getString(cHighlightConfigs.getValue());
														}
														else
														{
															value = cHighlightConfigs.getValue();
														}
														if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
																||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
																||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
																||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
															)
														{
															content=content + cHighlightConfigs.getStyle();
														}
														else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
																||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
														{
															content=content + cHighlightConfigs.getStyle();
														}
														// them dieu kien gop khoang va
														else if (formular.contains("&"))
														{
															String[] formularList = formular.split("&");
															String[] valueList = value.split("&");
															boolean ktra=true;
															if (formularList.length == valueList.length)
															{
																for (int l = 0; l  < formularList.length; l ++)
																{
																	if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																		||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																		||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																		||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																		||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																		||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																		)
																	{
																		ktra = false;
																		break;
																	}
																}
																if (ktra == true)
																{
																	content=content + cHighlightConfigs.getStyle();
																}
															}
														}
														// them dieu kien gop khoang hoac
														else if (formular.contains("|"))
														{
															
															String[] formularList = formular.split("|");
															String[] valueList = value.split("|");
															boolean ktra=false;
															if (formularList.length == valueList.length)
															{
																for (int l = 0; l  < formularList.length; l ++)
																{
																	if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
																		||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
																		||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
																		||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
																		||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
																		||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
																		)
																	{
																		ktra = true;
																		break;
																	}
																}
																if (ktra == true)
																{
																	content=content + cHighlightConfigs.getStyle();
																}
															}
														}
													}catch(Exception exp){}
												}
											}
											if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
											{
												content=content + cTableConfig.getStyle();
											}
											content=content + "\"";
										
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
										else
										{
											numRow++;
										}
									}catch(Exception exp){}
								}
								
								
							}
							else
							{
								content=content + "<td style=\"border:1px solid;";
								for (CHighlightConfigs cHighlightConfigs : highlightList) {
									if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
									{
										try{
											String formular=cHighlightConfigs.getFormula();
											String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
											String value="0";
											if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
											{
												value = result.getString(cHighlightConfigs.getValue());
											}
											else
											{
												value = cHighlightConfigs.getValue();
											}
											if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
													||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
													||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
													||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
												)
											{
												content=content + cHighlightConfigs.getStyle();
											}
											else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
													||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
											{
												content=content + cHighlightConfigs.getStyle();
											}
											// them dieu kien gop khoang va
											else if (formular.contains("&"))
											{
												String[] formularList = formular.split("&");
												String[] valueList = value.split("&");
												boolean ktra=true;
												if (formularList.length == valueList.length)
												{
													for (int l = 0; l  < formularList.length; l ++)
													{
														if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
															||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
															||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
															||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
															)
														{
															ktra = false;
															break;
														}
													}
													if (ktra == true)
													{
														content=content + cHighlightConfigs.getStyle();
													}
												}
											}
											// them dieu kien gop khoang hoac
											else if (formular.contains("|"))
											{
												
												String[] formularList = formular.split("|");
												String[] valueList = value.split("|");
												boolean ktra=false;
												if (formularList.length == valueList.length)
												{
													for (int l = 0; l  < formularList.length; l ++)
													{
														if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
															||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
															||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
															||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
															)
														{
															ktra = true;
															break;
														}
													}
													if (ktra == true)
													{
														content=content + cHighlightConfigs.getStyle();
													}
												}
											}
										}catch(Exception exp){}
									}
								}
								if (cTableConfig.getStyle()!=null && !cTableConfig.getStyle().equals(""))
								{
									content=content + cTableConfig.getStyle();
								}
								content=content + "\"";
							
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
					}
				}
			  //  System.out.println("content: " + content);
	           content=content + "</tr>";
	           i++;
	          
	        }
		 if (numRow>0)
			{
			 content = content.replace("#R#", String.valueOf(numRow));
			}
			content=content + "</tbody>";
			content=content + "</table>";
			
			
		} 
		catch(Exception ex)
		{
			logger.warn("SENDMAIL_QUERY", ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					logger.warn("RESULT_CLOSE", ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (Exception ex)
                {
					logger.warn("CONECTION_CLOSE", ex);
                }
			}

		}
		return content;
	}
	//Lay danh sach email trong nhom nguoi can gui thu
		public static List<M_FILE_ATTACHMENT> fileAttachList(String tableName, String blockName,
				String day, String hourStr, String mailName, String mailTo,
				String fileName,String mailID) throws SQLException {

					ResultSet result = null;
					List<M_FILE_ATTACHMENT> recordList = new ArrayList<M_FILE_ATTACHMENT>();
					//con = getDBConnection();
					try {
						con = dataSource.getConnection(); 
						cs = con.prepareCall(
				                "{call PK_AL_SEND_MAIL_MLMN.PR_FILE_ATTACH_MAIL_GET(?, ?, ?, ?,?,?,?,?)}"
				            );
				        	cs.registerOutParameter(8, OracleTypes.CURSOR);
				        	cs.setString(1, tableName);
				        	cs.setString(2, blockName);
				        	cs.setString(3, day);
				        	cs.setString(4, hourStr);
				        	cs.setString(5, mailName);
				        	cs.setString(6, mailTo);
				        	cs.setString(7, mailID);
				        	cs.executeQuery();
				        	result = (ResultSet) cs.getObject(8);

						//in du lieu trong result set ra 
						while (result.next()) {
							
							M_FILE_ATTACHMENT record  = new M_FILE_ATTACHMENT();
							   record.setFilePath(result.getString("FILE_PATH"));
							   record.setFileName(result.getString("FILE_NAME"));
							   record.setFileImage(result.getString("FILE_IMAGE"));
							   record.setIdWorkMana(result.getInt("ID_WORK_MANA"));
							   record.setDescription(result.getString("DESCRIPTION"));
							   record.setOrdering(result.getInt("ORDERING"));
						   recordList.add(record);
				         }
				    	
				}
					catch(Exception ex)
					{
						logger.warn("SENDMAIL_QUERY", ex);
					}
					finally {
						if (result != null) {
							try
							{
								result.close();
							}
							catch (Exception ex)
			                {
								logger.warn("RESULT_CLOSE", ex);
			                }
						}
						if (cs != null) {
							try
							{
								cs.close();
							}
							catch (Exception ex)
			                {
								logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
			                }
						}
						if (con != null) {
							try
							{
								con.close();
							}
							catch (Exception ex)
			                {
								logger.warn("CONECTION_CLOSE", ex);
			                }
						}

					}
					return recordList;
			}	
	//Export data to excel 
	@SuppressWarnings("deprecation")
	private void exportToExcel(String mailId, String tableName,String dateTime, String time, String filePath, String fileName,String title,List<CTableConfig> columnList,String mailID,String sheetId) throws SQLException {
		ResultSet result = null;
		System.out.println(tableName);
		
		try {
			//con = getDBConnection();
			con = dataSource.getConnection(); 
        	cs = con.prepareCall(
                "{call PK_AL_SEND_MAIL_MLMN.PR_DATA_ATTACH_MAIL_GET(?, ?, ?, ?, ?,?)}"
            );
        	cs.registerOutParameter(6, OracleTypes.CURSOR);
        	cs.setString(1, tableName);
        	cs.setString(2, dateTime);
        	cs.setString(3, time);
        	cs.setString(4, mailID);
        	cs.setString(5, sheetId);
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(6);
        	
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(tableName);
			
			int rownum = 0;
			//caption
			HSSFCellStyle style1 = workbook.createCellStyle();
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
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
             //headerStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
             headerStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);
             HSSFFont bold = workbook.createFont();
             bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
             bold.setColor(HSSFColor.BLACK.index);
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
          
            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
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
								String resultF ="";
								try
								{
									resultF = formatter.format(result.getDate(cTableConfig.getTableColumn()));
								}
								catch(Exception exp)
								{
									resultF= result.getString(cTableConfig.getTableColumn());
								}
								cell.setCellValue(resultF);
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
				logger.warn("SENDMAIL_QUERY", e);
			}
			//export
			String ext = "xls";
			String outfile = fileName + "." + ext;
			File tempFile = new File(filePath + "/" + outfile);
			
			try {
				 FileOutputStream out =
		                new FileOutputStream(tempFile);
		        workbook.write(out);
		        out.close();
		        System.out.println("Excel written successfully..");   
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		        logger.warn("CREATE_FILE_EXPORT", e);
		    } catch (IOException e) {
		    	logger.warn("CREATE_CONTENT_EXPORT", e);
		        e.printStackTrace();
		    }
		} 
		catch(Exception ex)
		{
			logger.warn("EXPORT_FILE", ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					logger.warn("RESULT_CLOSE", ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
					logger.warn("CONECTION_CLOSE", ex);
                }
			}

		}
		
	}
	private List<SysMailParameter> getSheetList(String mailId, String fileAttId) {
		ResultSet result = null;
		List<SysMailParameter> recordList = new ArrayList<SysMailParameter>();
		//con = getDBConnection();
		try {
				con = dataSource.getConnection(); 
				cs = con.prepareCall(
                "{call PK_AL_SEND_MAIL_MLMN.Pr_Mail_Attach_Sheet_Get(?,?,?)}"
            );
        	//cs.setQueryTimeout(seconds);
        	cs.registerOutParameter(3, OracleTypes.CURSOR);
        	cs.setString(1, mailId);
        	cs.setString(2, fileAttId);
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(3);
			//in du lieu trong result set ra 
			while (result.next()) {
			   SysMailParameter record  = new SysMailParameter();
			   record.setMailLevel(result.getString("MAIL_LEVEL"));
			   record.setBlockName(result.getString("BLOCK_NAME"));
			   record.setTableName(result.getString("TABLE_NAME"));
			   record.setWhereCondition(result.getString("WHERE_CONDITION"));
			   record.setMailId(result.getString("MAIL_ID"));
			   record.setOrderBy(result.getString("ORDER_BY"));
			   record.setOrdering(result.getInt("ORDERING"));
			   record.setFileName(result.getString("FILE_NAME"));
			   record.setTypeChart(result.getString("TYPE_CHART"));
			   record.setBlockId(result.getString("BLOCK_ID"));
			   record.setSheetId(result.getString("SHEET_ID"));
			   recordList.add(record);
	         }
	    	
	} 
		catch(Exception ex)
		{
			logger.warn("SENDMAIL_QUERY", ex);
		}
		finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (Exception ex)
                {
					logger.warn("RESULT_CLOSE", ex);
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (Exception ex)
                {
					logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (Exception ex)
                {
					logger.warn("CONECTION_CLOSE", ex);
                }
			}

		}
		return recordList;
	}
	@SuppressWarnings("deprecation")
	private String exportToExcelMultile(String fileAttId, String filePath,
			String fileName, String day, String hourStr, String mailId,List<File> fileImage,String fontChart) throws SQLException {
		
		String content="";
		//Tao file
		HSSFWorkbook workbook = new HSSFWorkbook();
		//style caption
		HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont bold1 = workbook.createFont();
        bold1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bold1.setFontHeightInPoints((short) 14);
        //bold1.setColor(HSSFColor.WHITE.index);
        style1.setFont(bold1);
        
    
		
		//Tao style header cho file
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
		
        //Lay danh sach cac sheet cung voi cac thong tin: table, dieu kien, sap xep, ten sheet, kieu bieu do
        List<SysMailParameter> sheetList = getSheetList(mailId,fileAttId);
        String lastSheet="";
        int lastRow=0;
        int lastCol=-1;
        int positionRowT=0;
        int positionColT=0;
        //Voi moi sheet. 
		for (SysMailParameter sheetMaster : sheetList) {
			int rownum = 0;
			int colN=0;
			int beginRangeR=0; 	//Dong bat dau lay du lieu ve bieu do
			int endRangeR=0;		//Dong ket thuc lay du lieu ve bieu do
			int beginRangeC=0; 	//Cot bat dau lay du lieu ve bieu do
			int endRangeC=0;		//Cot ket thuc lay du lieu ve bieu do
			HSSFSheet sheet;
			System.out.println("lastSheet:"+lastSheet);
			 if (lastSheet.equalsIgnoreCase(sheetMaster.getSheetId()))
			 {
				 sheet = workbook.getSheet(lastSheet);
				 if (lastRow<100)
				 {
					 rownum = lastRow;
					 colN = positionColT;
				 }
				 else
				 {
					 rownum = positionRowT;
					 colN = lastCol;
				 }
			 }
			 else
			 {
				//tao sheet
				System.out.println("Block name:"+sheetMaster.getSheetId());
				sheet = workbook.createSheet(sheetMaster.getSheetId());
				lastSheet=sheetMaster.getSheetId();
			 }
			
			//- Lấy List<CTableConfig> columnList cho moi sheet
			List<CTableConfig> columnList = columnList(sheetMaster.getTableName(),sheetMaster.getBlockId());
			List<CHighlightConfigs> highlightList=highlightList(sheetMaster.getTableName(),sheetMaster.getBlockId());
			int colnum= colN;
			//tao noi dung cation
			//rownum++;
			HSSFRow xlsRow1;
			if (sheet.getRow(rownum)!=null)
			{
				xlsRow1 = sheet.getRow(rownum);
			}
			else
			{
				xlsRow1 = sheet.createRow(rownum);
			}
	        xlsRow1.setHeightInPoints(30);
	        HSSFCell cell1 = xlsRow1.createCell(colnum);
	        cell1.setCellStyle(style1);
	        cell1.setCellValue(sheetMaster.getBlockName());
	        sheet.addMergedRegion(new CellRangeAddress(xlsRow1.getRowNum(),xlsRow1.getRowNum(),cell1.getColumnIndex(),cell1.getColumnIndex()+ (columnList.size()-1)));
			
	        //Tao header cho bang du lieu 
			HSSFRow header;
			rownum++;
			if (sheet.getRow(rownum)!=null)
			{
				header = sheet.getRow(rownum);
			}
			else
			{
				header = sheet.createRow(rownum);
			}
			
			for (CTableConfig cTableConfig : columnList) {
				if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
				{
					HSSFCell cell = header.createCell(colnum++);
					cell.setCellValue(cTableConfig.getColumnName());
					cell.setCellStyle(headerStyle);
				}
				
			}
			
			//set begin range data for chart
			beginRangeR = rownum+1;
			beginRangeC = colN;
			//tao body cho file
			ResultSet result = null;
			System.out.println(sheetMaster.getTableName()+"---"+sheetMaster.getSheetId());
	
			try {
				//con = getDBConnection();
				con = dataSource.getConnection(); 
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL_MLMN.PR_DATA_ATTACH_MAIL_GET(?, ?, ?, ?, ?,?)}"
	            );
	        	cs.registerOutParameter(6, OracleTypes.CURSOR);
	        	cs.setString(1, sheetMaster.getTableName());
	        	cs.setString(2, day);
	        	cs.setString(3, hourStr);
	        	cs.setString(4, sheetMaster.getMailId());
	        	cs.setString(5, sheetMaster.getSheetId());
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(6);
	    
	            SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
				try {
					while (result.next()) {
						colnum= colN;
						//HSSFRow row = sheet.createRow(rownum);
						HSSFRow xlsRow;
						rownum++;
						if (sheet.getRow(rownum)!=null)
						{
							xlsRow = sheet.getRow(rownum);
						}
						else
						{
							xlsRow = sheet.createRow(rownum);
						}
						for (CTableConfig cTableConfig : columnList) {
							if (!cTableConfig.getColumnName().equalsIgnoreCase("STT"))
							{
								HSSFCell cell = xlsRow.createCell(colnum++);
								 //Tao style cho body
						        HSSFCellStyle style = workbook.createCellStyle();
						        style.setBorderBottom((short) 1);
						        style.setBorderLeft((short) 1);
						        style.setBorderRight((short) 1);
						        style.setBorderTop((short) 1);
						        
								if (cTableConfig.getDataType().equalsIgnoreCase("date"))
								{
									String resultF ="";
									try
									{
										resultF = formatter.format(result.getDate(cTableConfig.getTableColumn()));
									}
									catch(Exception exp)
									{
										resultF= result.getString(cTableConfig.getTableColumn());
									}
									cell.setCellValue(resultF);
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
								Short color=null;
								for (CHighlightConfigs cHighlightConfigs : highlightList) {
									if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey())&&cHighlightConfigs.getEXCEL_STYLE()!=null)
									{
										try{
											String formular=cHighlightConfigs.getFormula();
											String valueColumn = result.getString(cTableConfig.getTableColumn())==null?"0":result.getString(cTableConfig.getTableColumn()).trim();
											String value="0";
											if (cHighlightConfigs.getTYPE_STYLE()!=null && cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE"))
											{
												value = result.getString(cHighlightConfigs.getValue());
											}
											else
											{
												value = cHighlightConfigs.getValue();
											}
											if ((formular.equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(value))
													||(formular.equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(value))
													||(formular.equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(value))
													||(formular.equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(value))
												)
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											else if ((formular.equals("!=")&&!valueColumn.equalsIgnoreCase(value))
													||((formular.equals("==")||formular==null)&&valueColumn.equalsIgnoreCase(value)))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											// them dieu kien gop khoang va
											else if (formular.contains("&"))
											{
												String[] formularList = formular.split("&");
												String[] valueList = value.split("&");
												boolean ktra=true;
												if (formularList.length == valueList.length)
												{
													for (int l = 0; l  < formularList.length; l ++)
													{
														if (!((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
															||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
															||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
															||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
															)
														{
															ktra = false;
															break;
														}
													}
													if (ktra == true)
													{
														color=cHighlightConfigs.getEXCEL_STYLE();
													}
												}
											}
											// them dieu kien gop khoang hoac
											else if (formular.contains("|"))
											{
												String[] formularList = formular.split("|");
												String[] valueList = value.split("|");
												boolean ktra=false;
												if (formularList.length == valueList.length)
												{
													for (int l = 0; l  < formularList.length; l ++)
													{
														if (((formularList[l].equals(">=")&& Double.parseDouble(valueColumn) >= Double.parseDouble(valueList[l]))
															||(formularList[l].equals(">")&& Double.parseDouble(valueColumn) > Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<=")&& Double.parseDouble(valueColumn) <= Double.parseDouble(valueList[l]))
															||(formularList[l].equals("<")&& Double.parseDouble(valueColumn) < Double.parseDouble(valueList[l]))
															||(formularList[l].equals("!=")&& !valueColumn.equals(valueList[l]))
															||(formularList[l].equals("==")&& valueColumn.equals(valueList[l])))
															)
														{
															ktra = true;
															break;
														}
													}
													if (ktra == true)
													{
														color=cHighlightConfigs.getEXCEL_STYLE();
													}
												}
											}
										}catch(Exception exp){}
										/*try{
											if (cHighlightConfigs.getFormula().equals(">=")&& ((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())>= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())>= result.getDouble(cHighlightConfigs.getValue()))))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											else if (cHighlightConfigs.getFormula().equals(">")&&((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())> Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())> result.getDouble(cHighlightConfigs.getValue()))))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											else if (cHighlightConfigs.getFormula().equals("<=")&&((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())<= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())<= result.getDouble(cHighlightConfigs.getValue()))))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											else if (cHighlightConfigs.getFormula().equals("<")&&((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") && result.getDouble(cTableConfig.getTableColumn())< Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())< result.getDouble(cHighlightConfigs.getValue()))))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											else if (cHighlightConfigs.getFormula().equals("!=")&&!result.getString(cTableConfig.getTableColumn()).equalsIgnoreCase(cHighlightConfigs.getValue()))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											else if ((cHighlightConfigs.getFormula().equals("==")||cHighlightConfigs.getFormula()==null)&&result.getString(cTableConfig.getTableColumn()).equalsIgnoreCase(cHighlightConfigs.getValue()))
											{
												color=cHighlightConfigs.getEXCEL_STYLE();
											}
											
										}catch(Exception exp){}*/
									}
								}
								if (color!=null)
								{
									style.setFillBackgroundColor(color);
									style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									style.setFillForegroundColor(color);
								}
								 cell.setCellStyle(style);
							}
						}
					}
					// adjust the column widths
		           /* int colCount = 0;
		            while (colCount < colnum)
		            {
		                sheet.autoSizeColumn((short) colCount++);
		            }*/
		           //font chu 
		            Font fontT;
		            Font fontItem;
		            try
		            {
			           File f = new File(fontChart);
				       FileInputStream in = new FileInputStream(f);
				       Font font = Font.createFont(Font.TRUETYPE_FONT, in);
				       fontT = font.deriveFont(java.awt.Font.BOLD, 16);
				       fontItem = font.deriveFont(java.awt.Font.BOLD, 12);
		            }
		            catch(Exception exp)
		            {
		            	fontT = new java.awt.Font("Arial", java.awt.Font.BOLD, 16) ;
		            	fontItem = new java.awt.Font("Arial", java.awt.Font.BOLD, 12) ;
		            }
					//set end range data for chart
					endRangeR = rownum;
					endRangeC = colnum;
					if (sheetMaster.getTypeChart()!=null)
					{
						/*short positionCol=0;
						short positionRow=0;*/
						//lay du lieu ve bieu do
						DefaultPieDataset  datasetPie = new DefaultPieDataset ();
						
						DefaultCategoryDataset dataset = new DefaultCategoryDataset();
						double minValue = Double.MAX_VALUE;
						double maxValue = Double.MIN_VALUE;
						if (sheetMaster.getTypeChart().contains("PIVOT"))
						{
							List<String> xAxit = new ArrayList<String>();
							//beginRangeR=beginRangeR+1;
							for (int i=beginRangeR;i<=endRangeR;i++)
							{
								String vlu=sheet.getRow(i).getCell(beginRangeC).getRichStringCellValue().toString();
								//System.out.println("vlu:"+vlu);
								xAxit.add(vlu);
							}
							//doc du lieu tung cot dua vao du lieu bieu do
							//XYSeriesCollection dataset = new XYSeriesCollection();
							
							int numCol=beginRangeC-1;
							for (CTableConfig cTableConfig : columnList) {
								if (cTableConfig.getInChart()!=null && cTableConfig.getInChart().equalsIgnoreCase("N"))
								{
									numCol=numCol+1;
								}
								else
								{
									numCol++;
											
									for (int i=beginRangeR;i<=endRangeR;i++)
									{
										/*System.out.println("column:"+cTableConfig.getColumnName()+"i:"+i+"---"+numCol);
										System.out.println("value:"+sheet.getRow(i).getCell(numCol).getRichStringCellValue().getString());
										*/
										double vlu= Double.parseDouble(sheet.getRow(i).getCell(numCol).getRichStringCellValue().getString());
										/*System.out.println("vlu:"+vlu);
										System.out.println("xAxit.get(i-beginRangeR):"+xAxit.get(i-beginRangeR));*/
										dataset.addValue(vlu, cTableConfig.getColumnName(), xAxit.get(i-beginRangeR));
										minValue = Math.min(vlu, minValue);
										maxValue = Math.max(vlu, maxValue);
										
										if (sheetMaster.getTypeChart().contains("PIE")&&sheet.getRow(i).getCell(beginRangeC).getRichStringCellValue().toString().equals(day))
										{
											double vluP= Double.parseDouble(sheet.getRow(i).getCell(numCol).getRichStringCellValue().getString());
											datasetPie.setValue(cTableConfig.getColumnName(), vluP);
										}
										
									}		
									// lay du lieu cho bieu do pie
									/*if (sheetMaster.getTypeChart().contains("PIE")&&beginRangeR<=endRangeR)
									{
										double vlu= Double.parseDouble(sheet.getRow(beginRangeR).getCell(numCol).getRichStringCellValue().getString());
										datasetPie.setValue(cTableConfig.getColumnName(), vlu);
									}*/
									
								}
								
							}
							
							/*positionCol = (short) (numCol+1);
							positionRow = (short) beginRange;*/
							
						}
						else
						{
							//List<String> serierList = new ArrayList<String>();
							//beginRangeR=beginRangeR+1;
							
							for (int i=beginRangeR;i<=endRangeR;i++)
							{
								String vlu=sheet.getRow(i).getCell(beginRangeC).getRichStringCellValue().toString();
								
								/*if (sheetMaster.getTypeChart().contains("PIE")&&columnList.size()>1)
								{
									double value= Double.parseDouble(sheet.getRow(i).getCell(beginRangeC+1).getRichStringCellValue().getString());
									datasetPie.setValue(vlu, value);
								}*/
								for (int j = 1; j < columnList.size(); j++) {
									if (columnList.get(j).getInChart()==null || (columnList.get(j).getInChart()!=null&&!columnList.get(j).getInChart().equalsIgnoreCase("N")))
									{
									
										double value= Double.parseDouble(sheet.getRow(i).getCell(beginRangeC+j).getRichStringCellValue().getString());
										dataset.addValue(value ,vlu , columnList.get(j).getColumnName());
										minValue = Math.min(value, minValue);
										maxValue = Math.max(value, maxValue);
										if (sheetMaster.getTypeChart().contains("PIE")&&columnList.get(j).getColumnName().equals(day))
										{
											double valueP= Double.parseDouble(sheet.getRow(i).getCell(j).getRichStringCellValue().getString());
											datasetPie.setValue(vlu, valueP);
										}
									}
									
								}
							}
							
							/*positionCol = (short) (columnList.size()+1);
							positionRow = (short) beginRange;*/
							
						}
						HSSFPatriarch drawing = sheet.createDrawingPatriarch();
						
						if (sheetMaster.getTypeChart().contains("LINE"))
						{
							//Ve bieu do.
							/* Step -2:Define the JFreeChart object to create line chart */
					        JFreeChart lineChartObject=ChartFactory.createLineChart(sheetMaster.getBlockName(),null,null,dataset,PlotOrientation.VERTICAL,true,true,false);                
					        lineChartObject.setBackgroundPaint(Color.white);
	
					        final CategoryPlot plot = (CategoryPlot) lineChartObject.getPlot();
					      //  plot.setBackgroundPaint(Color.GREEN);
					        plot.setBackgroundPaint(new Color(240,240,240) );
					        plot.setRangeGridlinePaint(Color.BLACK);
					        plot.setBackgroundAlpha(0.2f); 
					        
					       //reange axits
					        try
					        {
						        if (minValue-1>0)
						        {
						        	minValue=minValue-1;
						        }
						        maxValue = maxValue+0.5;
						        Range range = new Range(minValue, maxValue);
						        ValueAxis rangeAxis = plot.getRangeAxis();
						        rangeAxis.setAutoRange(true);
						        rangeAxis.setRange(range);
					        }
					        catch(Exception exp)
					        {
					        	logger.error("Mail chart: "+exp.toString());
					        	System.out.println("Su dung range chart mac dinh");
					        }
					        
					       /* plot.setRangePannable(true);
					        plot.setRangeGridlinesVisible(false);*/
					        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
					        renderer.setBaseShapesVisible(true);
					        renderer.setDrawOutlines(true);
					        renderer.setUseFillPaint(true);
					        renderer.setBaseFillPaint(Color.white);
					    
					       renderer.setStroke(new BasicStroke(2.0f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_BEVEL));
					       
					       lineChartObject.getTitle().setFont(fontT);
					       lineChartObject.getLegend().setItemFont(fontItem);
					    /*     renderer.setSeriesStroke(0, new BasicStroke(3.0f));
					        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));
					        renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));*/
					        /* Step -3 : Write line chart to a file */               
					         int width=800; /* Width of the image */
					         int height=500; /* Height of the image */        
					         
					         String outfile = sheetMaster.getSheetId().trim()+ (new SimpleDateFormat("yyMMddHHmmss")).format(new Date()) + ".png";
					         String fileNameChart = filePath + "/" + outfile;
					         File lineChart=new File(fileNameChart);              
					         try {
								ChartUtilities.saveChartAsPNG(lineChart,lineChartObject,width,height);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} 
					        
					         /* Write chart as PNG to Output Stream */
			                ByteArrayOutputStream chart_out = new ByteArrayOutputStream();          
			                ChartUtilities.writeChartAsPNG(chart_out,lineChartObject,width,height);
			                /* We can now read the byte data from output stream and stamp the chart to Excel worksheet */
			                int my_picture_id = workbook.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_PNG);
			                /* we close the output stream as we don't need this anymore */
			                chart_out.close();
			                /* Create the drawing container */
			              //  HSSFPatriarch drawing = sheet.createDrawingPatriarch();
			                /* Create an anchor point */
			                HSSFClientAnchor my_anchor =  new HSSFClientAnchor();
			                /* Define top left corner, and we can resize picture suitable from there */
			                my_anchor.setCol1((short) colN);
			                my_anchor.setRow1(rownum+5);
			                rownum = rownum+40;
			                /*my_anchor.setCol1(positionCol);
			                my_anchor.setRow1(positionRow);*/
			                /* Invoke createPicture and pass the anchor point and ID */
			                HSSFPicture  my_picture = drawing.createPicture((HSSFClientAnchor) my_anchor, my_picture_id);
			                /* Call resize method, which resizes the image */
			                my_picture.resize();
			                
			                //add file anh
					         fileImage.add(lineChart);
						}
						//tao bieu do pie
				         if (sheetMaster.getTypeChart().contains("PIE"))
				         {
				        	 String title = "TỶ LỆ TRONG NGÀY "+day;
				        	 JFreeChart mychart = ChartFactory.createPieChart(title,datasetPie,true,true,false); 
				        	 mychart.setBackgroundPaint(Color.white);
				       
					        final PiePlot plot = (PiePlot) mychart.getPlot();
					       // plot.setBackgroundPaint(Color.cyan); 	
					        plot.setBackgroundPaint(new Color(248,248,248) );
					        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
					        plot.setCircular(true);
					        plot.setLabelGenerator(null); 
					        //plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));
					        plot.setNoDataMessage("No data available");
					        
					        mychart.getTitle().setFont(fontT);
					        mychart.getLegend().setItemFont(fontItem);
					        
					       /* mychart.getTitle().setFont(new java.awt.Font(fontChart, java.awt.Font.BOLD, 14));
					        mychart.getLegend().setItemFont(new java.awt.Font(fontChart, java.awt.Font.BOLD, 12));*/

				        	 int width=500; /* Width of the image */
					         int height=450; /* Height of the image */        
					         
					         String outfilePie = sheetMaster.getSheetId().trim()+ (new SimpleDateFormat("yyMMddHHmmss")).format(new Date()) + "_pie.png";
					         String fileNameChart = filePath + "/" + outfilePie;
					         File pieChart=new File(fileNameChart);              
					         try {
								ChartUtilities.saveChartAsPNG(pieChart,mychart,width,height);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} 
					        
					         /* Write chart as PNG to Output Stream */
			                ByteArrayOutputStream chart_out = new ByteArrayOutputStream();          
			                ChartUtilities.writeChartAsPNG(chart_out,mychart,width,height);
			                /* We can now read the byte data from output stream and stamp the chart to Excel worksheet */
			                int my_picture_id = workbook.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_PNG);
			                /* we close the output stream as we don't need this anymore */
			                chart_out.close();
			                /* Create the drawing container */
			               // HSSFPatriarch drawing = sheet.createDrawingPatriarch();
			                //HSSFPatriarch drawing = sheet.getDrawingPatriarch();
			                /* Create an anchor point */
			                HSSFClientAnchor my_anchor =  new HSSFClientAnchor();
			                /* Define top left corner, and we can resize picture suitable from there */
			                my_anchor.setCol1((short) colN);
			                my_anchor.setRow1(rownum+5);
			                rownum = rownum+40;
			               
			                /* Invoke createPicture and pass the anchor point and ID */
			                HSSFPicture  my_picture = drawing.createPicture((HSSFClientAnchor) my_anchor, my_picture_id);
			                /* Call resize method, which resizes the image */
			                my_picture.resize();
			               //add file anh
					         fileImage.add(pieChart);
				      
				         }
				         
				       //tao bieu do pie
				         if (sheetMaster.getTypeChart().contains("BAR"))
				         {
				        	//Ve bieu do.
								/* Step -2:Define the JFreeChart object to create line chart */
						        JFreeChart barChartObject=ChartFactory.createBarChart(sheetMaster.getBlockName(),null,null,dataset,PlotOrientation.VERTICAL,true,true,false);                
						        barChartObject.setBackgroundPaint(Color.white);
		
						        final CategoryPlot plot = (CategoryPlot) barChartObject.getPlot();
						      //  plot.setBackgroundPaint(Color.GREEN);
						        plot.setBackgroundPaint(new Color(240,240,240) );
						        plot.setRangeGridlinePaint(Color.BLACK);
						        plot.setBackgroundAlpha(0.2f); 
						        //reange axits
						        ValueAxis rangeAxis = plot.getRangeAxis();
						        rangeAxis.setAutoRange(true);
						        if (minValue-1>0)
						        {
						        	minValue=minValue-1;
						        }
						        maxValue = maxValue+0.5;
						        rangeAxis.setRange(new Range(minValue, maxValue));
						        
						        barChartObject.getTitle().setFont(fontT);
						        barChartObject.getLegend().setItemFont(fontItem);
						         
						       /* plot.setRangePannable(true);
						        plot.setRangeGridlinesVisible(false);*/
						        /*LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
						        renderer.setBaseShapesVisible(true);
						        renderer.setDrawOutlines(true);
						        renderer.setUseFillPaint(true);
						        renderer.setBaseFillPaint(Color.white);
						    
						       renderer.setStroke(new BasicStroke(2.0f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_BEVEL));*/
						    /*     renderer.setSeriesStroke(0, new BasicStroke(3.0f));
						        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));
						        renderer.setSeriesShape(0, new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));*/
						        /* Step -3 : Write line chart to a file */               
						         int width=1000; /* Width of the image */
						         int height=500; /* Height of the image */        
						         
						         String outfile = sheetMaster.getSheetId().trim()+ (new SimpleDateFormat("yyMMddHHmmss")).format(new Date()) + "_bar.png";
						         String fileNameChart = filePath + "/" + outfile;
						         File barChart=new File(fileNameChart);              
						         try {
									ChartUtilities.saveChartAsPNG(barChart,barChartObject,width,height);
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								} 
						        
						         /* Write chart as PNG to Output Stream */
				                ByteArrayOutputStream chart_out = new ByteArrayOutputStream();          
				                ChartUtilities.writeChartAsPNG(chart_out,barChartObject,width,height);
				                /* We can now read the byte data from output stream and stamp the chart to Excel worksheet */
				                int my_picture_id = workbook.addPicture(chart_out.toByteArray(), Workbook.PICTURE_TYPE_PNG);
				                /* we close the output stream as we don't need this anymore */
				                chart_out.close();
				                /* Create the drawing container */
				              //  HSSFPatriarch drawing = sheet.createDrawingPatriarch();
				                /* Create an anchor point */
				                HSSFClientAnchor my_anchor =  new HSSFClientAnchor();
				                /* Define top left corner, and we can resize picture suitable from there */
				                my_anchor.setCol1((short) colN);
				                my_anchor.setRow1(rownum+5);
				                rownum = rownum+40;
				                /*my_anchor.setCol1(positionCol);
				                my_anchor.setRow1(positionRow);*/
				                /* Invoke createPicture and pass the anchor point and ID */
				                HSSFPicture  my_picture = drawing.createPicture((HSSFClientAnchor) my_anchor, my_picture_id);
				                /* Call resize method, which resizes the image */
				                my_picture.resize();
				                
				                //add file anh
						         fileImage.add(barChart);
				      
				         }
					}
					// kiem tra lay so thu tu cot dong co the tao tiep du lieu tren 1 sheet
					
					if (columnList.size()<14&&sheetMaster.getTypeChart()!=null)
					{
						colnum = colN+14;
					}
					else
					{
						colnum =colnum+1;
					}
					
					if (rownum<100)
					{
						if (lastCol==-1)
						{
							lastCol=0;
						}
						positionColT = lastCol;
						rownum=rownum+2;
						positionRowT = rownum;
					}
					else
					{
						positionColT = colnum+1;
						positionRowT = lastRow;
					}
					/*lastRow = rownum;
					lastCol = colnum;*/
					lastRow = positionRowT;
					lastCol = positionColT;

				} catch (Exception e) {
					e.printStackTrace();
					logger.warn("SENDMAIL_QUERY", e);
				}
				
			} 
			catch(Exception ex)
			{
				logger.warn("EXPORT_FILE", ex);
			}
			finally {
				if (result != null) {
					try
					{
						result.close();
					}
					catch (Exception ex)
	                {
						logger.warn("RESULT_CLOSE", ex);
	                }
				}
				if (cs != null) {
					try
					{
						cs.close();
					}
					catch (Exception ex)
	                {
						logger.warn("CALL_ABLE_STATEMENT_CLOSE", ex);
	                }
				}
				if (con != null) {
					try
					{
						con.close();
					}
					catch (SQLException ex)
	                {
						logger.warn("CONECTION_CLOSE", ex);
	                }
				}

			}
			
			
			
		}
		//export
		String ext = "xls";
		String outfile = fileName + "." + ext;
		File tempFile = new File(filePath + "/" + outfile);
		
		try {
			 FileOutputStream out =
	                new FileOutputStream(tempFile);
	        workbook.write(out);
	        out.close();
	        System.out.println("Excel written successfully..");   
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        logger.warn("CREATE_FILE_EXPORT", e);
	    } 
		catch (IOException e) {
	    	logger.warn("CREATE_CONTENT_EXPORT", e);
	        e.printStackTrace();
	    }	
		return content;		
				//- Lay data de ve chart va export du lieu ra file
				//- Ve bieu do export ra file image
				//- Tao sheet va export du lieu ra sheet
				//- Copy hinh anh bieu do vao file export
			//add sheet vao file
		//Export file		
	}
	
	// Goi cau lenh chay job		
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.warn("START JOB SEND MAIL");
		
		/*try {
			SendMailJob.loadSystemConfig();
			SendMailJob.loadMailConfig();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		*/
		//Lay cac thong so de gui mail, trang thai gui mail
		String statusSend= AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_STATUS);
		Integer startHour =null;
		if (AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_START_HOUR)!= null)
		{
		 startHour = Integer.parseInt(AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_START_HOUR));
		}
		else
			startHour = 1;
		Integer endHour = null;
		if (AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_START_HOUR)!= null)
		{
			endHour = Integer.parseInt(AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_END_HOUR));
		}
		else
			endHour = 23;
		// LAY THONG SO MAY CHU GUI MAIL
		String userFrom = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_USER_FROM);
		String password = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_PASSWORD_FROM);
		String userTo = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_USER_TO);
		String subject = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_SUBJECT);
		String filePath= AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_FILEPATH);
		String fontChart = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_FONT);
		String serverNode = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_SERVER);
		System.out.println("userFrom: "+ userFrom);
		System.out.println("password: "+ password);
		System.out.println("userTo: "+ userTo);
		System.out.println("subject: "+ subject);
		
		//LAY THONG SO CAI DAT GUI MAIL
		Properties props = AL_Global.MAIL_CONFIG;
		String server = getServerNode();
		//test
		/*String server = "1";
		String serverNode ="1";*/
		System.out.println("server node config: "+ server);
		System.out.println("server node data: "+ serverNode);
		// Kiem tra trang thai gui mail
		//Neu dang la trang thai gui mail thi kiem tra gio gui mai co nam trong khoang 8->23h?
		if (statusSend!=null && statusSend.equals("Y") && serverNode!=null && server!=null && serverNode.equalsIgnoreCase(server))
		{
			System.out.println("server duoc phep chay tien trinh mail: ");
			Calendar cal = Calendar.getInstance();	
			SimpleDateFormat formatter2= new SimpleDateFormat("yyyyMMddhhmm");
			String dateNow = formatter2.format(cal.getTime());
			cal.setTime(new Date());
			String dateTimeStr= df.format(cal.getTime());
			//String dateTimeFull= dffull.format(cal.getTime());
			System.out.println("cal.HOUR: "+ cal.get(Calendar.HOUR_OF_DAY));
			int hour=cal.get(Calendar.HOUR_OF_DAY);
			
			if ( hour>=startHour.intValue() && hour<= endHour.intValue())
			{
				//LAY DANH SACH CAC MUC GUI MAIL CAN DUOC GUI VAO THOI DIEM HIEN TAI.
				// VOI MOI MUC LAY CAC BLOCK CHI TIEU GUI MAIL
				//danh sach mail can gui tai thoi diem nay, voi moi mail lay danh sach cac block can gui trong mail. goi toi ham lay du lieu cho cac block
				List<SysMailParameterMaster> mailLis;
				
				try {
					mailLis = mailList(dateTimeStr);
				
				for (SysMailParameterMaster sysMailParameterMaster : mailLis) {
					
					String mailId= sysMailParameterMaster.getMailId();
					String mailLevel = sysMailParameterMaster.getMailLevel();
					String day ="";
					if (sysMailParameterMaster.getDay()!=null)
					{
						day = df.format(sysMailParameterMaster.getDay());
					}
					else
					{
						day = df.format(new Date());
					}
					String typeMailHour=sysMailParameterMaster.getTypeMailHour();
					String contentHead = sysMailParameterMaster.getContentHeader();
					String contentRoot = sysMailParameterMaster.getContentRooter();
					//Xu ly dia chi thu gui va gui mail den tung thanh vien trong nhom.
					if (sysMailParameterMaster.getSendingTime()!=null){
						hour = sysMailParameterMaster.getSendingTime();
					}
					String hourStr="";
					if (hour>=0&& hour<=9)
					{
						hourStr="0"+String.valueOf(hour);
					}
					else
					{
						hourStr=String.valueOf(hour);
					}
					//subject
					String title = sysMailParameterMaster.getMailName().replace("#DATE#", day).replace("#HOUR#", hourStr+":00:00");
					subject = title;
					
					saveMailSended(subject,null,"Dang gui","C",mailId,mailLevel,hourStr);
					
					String mailTo="";
					String mailCCTo="";
					String mailBCCTo="";
					if (typeMailHour!=null && typeMailHour.equals("IWORK"))
					{
						mailTo = sysMailParameterMaster.getMailTo();
						if (mailTo!=null)
						{
							mailTo= mailTo.trim().replaceAll(";",",");
						}
						mailCCTo=sysMailParameterMaster.getCcMail();
						if (mailCCTo!=null)
						{
							mailCCTo= mailCCTo.trim().replaceAll(";",",");
						}
						mailBCCTo=sysMailParameterMaster.getBccMail();
						if (mailBCCTo!=null)
						{
							mailBCCTo= mailBCCTo.trim().replaceAll(";",",");
						}
					}
					else
					{
						String addressMailTo = sysMailParameterMaster.getMailTo().trim().replaceAll(";",",");
						String[] groupListTo = addressMailTo.split(",");
						String temp="";
						for (String group : groupListTo) {
							
							if (group!=null && !group.equals(""))
							{
								String regex = 
										"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
												+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
							    Pattern pattern = Pattern.compile(regex);
							    Matcher matcher = pattern.matcher(group.trim());
							    //Neu la email ca nhan
							    if (matcher.matches()) {
							    	System.out.println("send mail");
							    	userTo = group.trim();
							    	mailTo+=temp+userTo;
							    	temp=",";
							    	
							    }
							    else   //neu la nhom thi can lay ra danh sach email can gui
							    {
							    	System.out.println("send nhom");
							    	if (group!=null && !group.equalsIgnoreCase(""))
							    	{
								    	List<SysDefineSmsEmail> userList = userList(group.trim());
								    	for (SysDefineSmsEmail user : userList) {
								    		System.out.println("getEmail:"+user.getGroupUser());
								    		if (user.getGroupUser()!=null && !user.getGroupUser().equals(""))
								    		{
								    			mailTo += temp+ user.getGroupUser();
								    			temp=",";
								    		}
									    		
								    	}
							    	}
								}
							  }
							}
						//cc
						if (sysMailParameterMaster.getCcMail()!=null)
						{
							String addressMailCC = sysMailParameterMaster.getCcMail().trim().replaceAll(";",",");
							String[] groupListCC= addressMailCC.split(",");
							String tempCC="";
							for (String group : groupListCC) {
								
								if (group!=null && !group.equals(""))
								{
									String regex = 
											"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
													+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
								    Pattern pattern = Pattern.compile(regex);
								    Matcher matcher = pattern.matcher(group.trim());
								    //Neu la email ca nhan
								    if (matcher.matches()) {
								    	System.out.println("send mail");
								    	userTo = group.trim();
								    	mailCCTo+=tempCC+userTo;
								    	tempCC=",";
								    	
								    }
								    else   //neu la nhom thi can lay ra danh sach email can gui
								    {
								    	System.out.println("send nhom");
								    	if (group!=null && !group.equalsIgnoreCase(""))
								    	{
									    	List<SysDefineSmsEmail> userList = userList(group.trim());
									    	for (SysDefineSmsEmail user : userList) {
									    		System.out.println("getEmail:"+user.getGroupUser());
									    		if (user.getGroupUser()!=null && !user.getGroupUser().equals(""))
									    		{
									    			mailCCTo += tempCC+ user.getGroupUser();
									    			tempCC=",";
									    		}
										    		
									    	}
								    	}
									}
								  }
								}
						}
						//bcc
						if (sysMailParameterMaster.getBccMail()!=null)
						{
							String addressMailBCC = sysMailParameterMaster.getBccMail().trim().replaceAll(";",",");
							String[] groupListBCC= addressMailBCC.split(",");
							String tempBCC="";
							for (String group : groupListBCC) {
								
								if (group!=null && !group.equals(""))
								{
									String regex = 
											"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
													+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
								    Pattern pattern = Pattern.compile(regex);
								    Matcher matcher = pattern.matcher(group.trim());
								    //Neu la email ca nhan
								    if (matcher.matches()) {
								    	System.out.println("send mail");
								    	userTo = group.trim();
								    	mailBCCTo+=tempBCC+userTo;
								    	tempBCC=",";
								    	
								    }
								    else   //neu la nhom thi can lay ra danh sach email can gui
								    {
								    	System.out.println("send nhom");
								    	if (group!=null && !group.equalsIgnoreCase(""))
								    	{
									    	List<SysDefineSmsEmail> userList = userList(group.trim());
									    	for (SysDefineSmsEmail user : userList) {
									    		System.out.println("getEmail:"+user.getGroupUser());
									    		if (user.getGroupUser()!=null && !user.getGroupUser().equals(""))
									    		{
									    			mailBCCTo += tempBCC+user.getGroupUser();
									    			tempBCC=",";
									    		}
										    		
									    	}
								    	}
									}
								  }
								}
						}
					}
					 
					//subject = title+" ["+hour+":00:00 - "+day+"]";
					//file path
					if (sysMailParameterMaster.getRootFileAttached()!=null)
					{
						filePath = sysMailParameterMaster.getRootFileAttached();
					}
					List<String> file = new ArrayList<String>();
					List<String> fileName = new ArrayList<String>();
					
					//Content
					String content = "";
					if (contentHead!=null&&!contentHead.equals(""))
					{
						content = "<span>"+contentHead.replace("#DATE#", day).replace("#HOUR#", hourStr+":00:00 ")+"</span>";
						
					}
					//content=content + "<br/>";
					content=content + "<center><div>";
					/*content=content + "<table>";
					content=content + "<tr><td  align=\"center\"><h1 style=\"font-weight: bold;\">"+title+"</h1></td></tr>";
					content=content + "<tr><td align=\"center\"><h2 style=\"font-weight: bold; font-size: 35;\">Ngày: "+day +" - Giờ: "+ String.valueOf(hour) +":00:00  </h2></td>";
					content=content + "</tr></table>";*/
					//content=content + "<div align=\"left\"><h2 style=\"font-weight: bold;font-size: 32;\">"+title+"</h2></div>";
					// luu trang thai dang thuc hien gui mail nay
					if (typeMailHour!=null && typeMailHour.equals("IWORK"))
					{
						String errorSend="";
						String status="S";
						
						List<SysMailParameter> blockList = blockList(mailLevel,mailId);
						for (SysMailParameter block : blockList) {
						//content=content + "<br/>";
						//System.out.println(block.getTableName());
							try
							{
								String tableName = block.getTableName();
								// lay du lieu gui mail
								List<CTableConfig> columnList = columnList(tableName,block.getBlockId());
								List<CHighlightConfigs> highlightList=highlightList(tableName,block.getBlockId());
								mailTo=sysMailParameterMaster.getMailTo();
								content=content + getDataWorkBlock(tableName,block.getBlockName(),day,hourStr,sysMailParameterMaster.getMailName(),mailTo,columnList,highlightList,filePath,block.getFileName()+dateNow,mailId);
								List<M_FILE_ATTACHMENT> fileAttachList = fileAttachList(tableName,block.getBlockName(),day,hourStr,sysMailParameterMaster.getMailName(),mailTo,block.getFileName()+dateNow,mailId);
								if (fileAttachList.size()>0)
								{
									for (M_FILE_ATTACHMENT m_FILE_ATTACHMENT : fileAttachList) {
										fileName.add(m_FILE_ATTACHMENT.getFilePath());
									}
								}
									
							}
							catch(Exception exp)
							{
								logger.warn("DATA",exp);
								errorSend=errorSend+"/ErrorSend:"+block.getTableName();
								status="F";
							}
						}
						//content=content + "<br/>";
						content=content +"</div></center>";
						if (contentRoot!=null&&!contentRoot.equals(""))
						{
							content=content + "<br/><span>"+contentRoot.replace("#DATE#", day).replace("#HOUR#", hourStr+":00:00 ")+"</span>";
							
						}
						
						//System.out.println("content: "+ content);
						
						//attach file
						if (filePath!=null &&!filePath.equals("")&&file.size()>0)
						{
							
							for (String item: file) {
								if (item != null && !item.trim().equals("")) {
									fileName.add(filePath.concat("/").concat(item));
								}
							}
						}
						/*if (filePath!=null &&!filePath.equals("")&&file.size()>0)
						{
							fileName = new ArrayList<String>();
							
							//zip file to send
							String fileZipName= filePath.concat("/").concat(mailId+dateNow+".zip");
							zipFiles(filePath,file,fileZipName);
							fileName.add(fileZipName);
						}
						*/
						//gui mail
						try
						{
							System.out.println("mail to:"+mailTo);
							Mail mail = new Mail(props, fileName, userFrom, password,
									mailTo,mailCCTo,mailBCCTo, subject, content);
							
							/*Mail mail = new Mail(props, fileName, "vananhct@vhc.com.vn", "gaconchaylonton",
									mailTo, subject, content);*/
							
							String result = mail.send();
							if(result != null)
							{
								logger.warn("Gui mail that bai");
								System.out.println("Gui mail that bai");
								status="F";
								errorSend="Error address.password email or manny different information.";
							}
						}
						catch(Exception ex)
						{
							logger.warn("SENDING_MAIL", ex);
							System.out.println("Gui mail that bai");
							//errorSend="Error in sending mail: " + ex.toString().substring(0, 3000);
							if (ex.toString() != null && ex.toString().length() > 3000) {
								errorSend="Error in sending mail: " + ex.toString().substring(0, 3000);
							} else {
								errorSend="Error in sending mail: " + ex.toString();
							}
							status="F";
						}
						saveMailSended(subject,mailTo,errorSend,status,mailId,mailLevel,hourStr);
				}
				else if (typeMailHour!=null && (typeMailHour.equals("2G")||typeMailHour.equals("3G")))
				{
					String errorSend="";
					String status="S";
						List<SysMailParameter> blockList = blockList(mailLevel,mailId);
						for (SysMailParameter block : blockList) {
						try
						{
							String tableName = block.getTableName();
							// lay du lieu gui mail
							List<CTableConfig> columnList = columnList(tableName,block.getBlockId());
							List<CHighlightConfigs> highlightList=highlightList(tableName,block.getBlockId());
							content=content + getDataBlock(tableName,block.getBlockName(),day,hourStr,columnList,highlightList,mailId,block.getBlockId());
							if (block.getFileName()!=null&&!block.getFileName().equalsIgnoreCase("null"))
							{
								exportToExcel(block.getMailId(),block.getFileName(),day,hourStr,filePath,block.getFileName()+dateNow,block.getBlockName(),columnList,mailId,block.getSheetId());
					        	file.add(block.getFileName()+dateNow+".xls");
							}
						}
						catch(Exception exp)
						{
							logger.warn("DATA", exp);
							//System.out.println(exp.toString());
							errorSend=errorSend+"/ErrorSend:"+block.getTableName();
							status="F";
						}
						}
						//content=content + "<br/>";
						content=content +"</div></center>";
						if (contentRoot!=null&&!contentRoot.equals(""))
						{
							content=content + "<br/><span>"+contentRoot.replace("#DATE#", day).replace("#HOUR#", hourStr+":00:00 ")+"</span>";
							
						}
						
						//System.out.println("content: "+ content);
						
						//attach file
						/*item
						{
							fileName = new ArrayList<String>();
							for (String item: file) {
								if (item != null && !item.trim().equals("")) {
									fileName.add(filePath.concat("/").concat(item));
								}
							}
						}*/
						if (filePath!=null &&!filePath.equals("")&&file.size()>0)
						{
							fileName = new ArrayList<String>();
							for (String item: file) {
								if (item != null && !item.trim().equals("")) {
									fileName.add(filePath.concat("/").concat(item));
								}
							}
							/*//zip file to send
							String fileZipName= filePath.concat("/").concat(mailId+dateNow+".zip");
							zipFiles(filePath,file,fileZipName);
							fileName.add(fileZipName);*/
						}
						//gui mail
						try
						{
							System.out.println("mail to:"+mailTo);
							Mail mail = new Mail(props, fileName, userFrom, password,
									mailTo,mailCCTo,mailBCCTo, subject, content);
							
							/*Mail mail = new Mail(props, fileName, "vananhct@vhc.com.vn", "gaconchaylonton",
									mailTo, subject, content);*/
							
							String result = mail.send();
							if(result != null)
							{
								logger.warn("Gui mail that bai");
								System.out.println("Gui mail that bai");
								status="F";
								errorSend="Error address.password email or manny different information.";
							}
						}
						catch(Exception ex)
						{
							logger.warn("SENDING_MAIL:", ex);
							System.out.println("Gui mail that bai");
							//errorSend="Error in sending mail";
							if (ex.toString() != null && ex.toString().length() > 3000) {
								errorSend="Error in sending mail: " + ex.toString().substring(0, 3000);
							} else {
								errorSend="Error in sending mail: " + ex.toString();
							}
							status="F";
						}
						saveMailSended(subject,mailTo,errorSend,status,mailId,mailLevel,hourStr);
					}
					else
					{
						String errorSend="";
						String status="S";
						List<String> fileAttList = new ArrayList<String>();
						List<File> fileImage = new ArrayList<File>();
						//Du lieu hien thi tren mat mail
						List<SysMailParameter> blockList = blockList(mailLevel,mailId);
						for (SysMailParameter block : blockList) {
							try
							{
								String tableName = block.getTableName();
								// lay du lieu gui mail
								List<CTableConfig> columnList = columnList(tableName,block.getBlockId());
								List<CHighlightConfigs> highlightList=highlightList(tableName,block.getBlockId());
								content=content + getDataBlock(tableName,block.getBlockName(),day,hourStr,columnList,highlightList,mailId,block.getBlockId());
								if (block.getFileName()!=null&&!block.getFileName().equalsIgnoreCase("null"))
								{
									if (!file.contains(block.getFileName()+dateNow+".xls"))
									{
											content=content + exportToExcelMultile(block.getFileName(),filePath,block.getFileName()+dateNow,day,hourStr,mailId,fileImage,fontChart);
											file.add(block.getFileName()+dateNow+".xls");
									}
									
						        	
								}
							}
							catch(Exception exp)
							{
								logger.warn("DATA", exp);
								System.out.println(exp.toString());
								errorSend=errorSend+"/ErrorSend:"+block.getTableName();
								status="F";
							}
						}
						for (File fileIma : fileImage) {
							  content += " <br/>";
							  content += "    <p><img src='cid:" + fileIma.getName() + "'></p>";
						}
						
						
						/*if (block.getFileName()!=null&&!block.getFileName().equalsIgnoreCase("null"))
						{
							exportToExcelMultile(block.getMailId(),block.getFileName(),day,hourStr,filePath,block.getFileName()+dateNow,block.getBlockName(),columnList,mailId);
				        	file.add(block.getFileName());
						}*/
						//content=content + "<br/>";
						content=content +"</div></center>";
						if (contentRoot!=null&&!contentRoot.equals(""))
						{
							content=content + "<br/><span>"+contentRoot.replace("#DATE#", day).replace("#HOUR#", hourStr+":00:00 ")+"</span>";
							
						}
						
						//System.out.println("content: "+ content);
						//attach file
						if (filePath!=null &&!filePath.equals("")&&file.size()>0)
						{
							fileName = new ArrayList<String>();
							for (String item: file) {
								if (item != null && !item.trim().equals("")) {
									fileName.add(filePath.concat("/").concat(item));
								}
							}
							//zip file to send
							/*String fileZipName= filePath.concat("/").concat(mailId+dateNow+".zip");
							zipFiles(filePath,file,fileZipName);
							fileName.add(fileZipName);*/
						}
						//gui mail
						try
						{
							System.out.println("mail to:"+mailTo);
							MailMutiple mail = new MailMutiple(props, fileName,fileImage, userFrom, password,
									mailTo,mailCCTo,mailBCCTo, subject, content);
							
							/*Mail mail = new Mail(props, fileName, "vananhct@vhc.com.vn", "gaconchaylonton",
									mailTo, subject, content);*/
							
							String result = mail.send();
							if(result != null)
							{
								logger.warn("Gui mail that bai");
								System.out.println("Gui mail that bai");
								status="F";
								errorSend="Error address.password email or manny different information.";
							}
						}
						catch(Exception ex)
						{
							Writer result = new StringWriter();
				            PrintWriter printWriter = new PrintWriter(result);
				            ex.getCause().printStackTrace(printWriter);
				            String logMsg = result.toString();
				            printWriter.close();
				            
							logger.warn("SENDING_MAIL", ex);
							System.out.println("Gui mail that bai");
							//errorSend="Error in sending mail: " + ex.toString().substring(0, 3000);
							if (logMsg != null && logMsg.length() > 3000) {
								errorSend="Error in sending mail: " + logMsg.substring(0, 3000);
							} else {
								errorSend="Error in sending mail: " + logMsg;
							}
							status="F";
						}
						saveMailSended(subject,mailTo,errorSend,status,mailId,mailLevel,hourStr);
					}
			
				
				}
			}
				catch (SQLException e) {
				e.printStackTrace();
				logger.warn("SENDING_MAIL",e);
			}	
				
				
			}
		}		
	}
	
	
	
	/*public void zipFiles(String path,List<String> files, String fileZipname) {
		FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(fileZipname);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            for(String fileName:files){
            	String filePath = path.concat("/").concat(fileName);
                File input = new File(filePath);
                fis = new FileInputStream(input);
                ZipEntry ze = new ZipEntry(input.getName());
                System.out.println("Zipping the file: "+input.getName());
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4*1024];
                int size = 0;
                while((size = fis.read(tmp)) != -1){
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            System.out.println("Done... Zipped the files...");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try{
                if(fos != null) fos.close();
            } catch(Exception ex){
                 
            }
        }
    }*/
 	//test
	private static Connection getDBConnection() {

		System.out.println("-------- Oracle JDBC Connection Testing ------");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			System.out.println("Oracle JDBC Driver Registered!");

		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			//return DriverManager.getConnection("jdbc:oracle:thin:@vhc.com.vn:1521:vhc", "VMSC6_ALARM", "ALARM");
			return DriverManager.getConnection("jdbc:oracle:thin:@10.18.18.66:1521:ddh", "ALARM", "ALARM201309");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			return null;
		}

	}
	/*public static void loadSystemConfig() throws SQLException {
		Connection con = null;
		AL_Global.MAIL_SYSTEM_CONFIG.clear();
		ResultSet result = null;
		try {
			//con = getDBConnection();
			con = dataSource.getConnection(); 
			cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL_MLMN.PR_SYS_CONFIG_SEND(?)}"
            );
        	cs.registerOutParameter(1, OracleTypes.CURSOR);
        	cs.executeQuery();
        	result = (ResultSet) cs.getObject(1);
			String key = null;
			String value = null;
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				//System.out.println("Key: "+ key+"---- value: "+value);
				if (key != null && value != null) {
					AL_Global.MAIL_SYSTEM_CONFIG.setProperty(key, value);
				}
			}
			
		} finally {
			if (result != null) {
				try
				{
					result.close();
				}
				catch (SQLException ex)
                {
                }
			}
			if (cs != null) {
				try
				{
					cs.close();
				}
				catch (SQLException ex)
                {
                }
			}
			if (con != null) {
				try
				{
					con.close();
				}
				catch (SQLException ex)
                {
                }
			}
		}
	}
	//Lay cac gia tri config mail trong c_MAIL_SYSTEM_CONFIG
			public static void loadMailConfig() throws SQLException {
				Connection con = null;
				CallableStatement cs = null;
				ResultSet result = null;
				AL_Global.MAIL_CONFIG.clear();
				try {
					//con = getDBConnection();
					con = dataSource.getConnection(); 
					cs = con.prepareCall(
		                "{call PK_AL_SEND_MAIL_MLMN.PR_SYS_CONFIG_MASTER_SEND(?)}"
		            );
		        	cs.registerOutParameter(1, OracleTypes.CURSOR);
		        	cs.executeQuery();
		        	result = (ResultSet) cs.getObject(1);
			       //in du lieu trong result set ra 
		        	String key = null;
					String value = null;
					while (result.next()) {
						key = result.getString(1);
						value = result.getString(2);
						System.out.println("Key: "+ key+"---- value: "+value);
						if (key != null && value != null) {
							AL_Global.MAIL_CONFIG.setProperty(key, value);
						}
					}
					
				} finally {
					if (result != null) {
						try
						{
							result.close();
						}
						catch (SQLException ex)
		                {
		                }
					}
					if (cs != null) {
						try
						{
							cs.close();
						}
						catch (SQLException ex)
		                {
		                }
					}
					if (con != null) {
						try
						{
							con.close();
						}
						catch (SQLException ex)
		                {
		                }
					}

				}
			}*/
			
	public String getServerNode() {
		String propFileName = System.getProperty("jboss.server.home.dir") + "/conf/system_mlmn.properties";
		InputStream propsStream = null;
		try {
			propsStream = new FileInputStream(propFileName);
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			return props.getProperty("SERVER_NODE");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return null;
	}
		
	
	public static void main(String arg[]) throws JobExecutionException {
		SendMailJob job = new SendMailJob();
		//String content= "<td rowspan=\"#R#\" style=\"border: 1px dotted  #0099CC;";
		//content.replace("#R#", String.valueOf(3));
		//.out.println(content);
		job.execute(null);
	}
}
