package vo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Controller;

import oracle.jdbc.OracleTypes;
import vn.com.vhc.alarm.core.AL_Global;
import vo.alarm.utils.Mail;
import vo.alarm.utils.SendMailSetting;

@Controller
public class SendMailWorkJob implements Job{
	public SendMailWorkJob(){}
	private static Logger logger = Logger.getLogger(SendMailWorkJob.class.getName());
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
			con = getDBConnection();
			
			try {
				//con = dataSource.getConnection();  
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL.PR_MAIL_PARAMETER_MASTER_GET(?,?)}"
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
			   recordList.add(record);
	         }
	    	
		} 
			finally {
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
			return recordList;
	}
	//Lay danh sach dinh nghia block se hien thi trong  mail se gui nay
	public static List<SysMailParameter> blockList(String mailLevel,String mailId) throws SQLException{
		ResultSet result = null;
		List<SysMailParameter> recordList = new ArrayList<SysMailParameter>();
		con = getDBConnection();
		try {
				//con = dataSource.getConnection();  
        	cs = con.prepareCall(
                "{call PK_AL_SEND_MAIL.PR_MAIL_PARAMETER_BLOCK_GET(?,?,?)}"
            );
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
			   recordList.add(record);
	         }
	    	
	} 
		finally {
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
		return recordList;
	}
	//Lay style dinh nghia cot se hien thi trong mail theo cac muc nguong khac nhau
	public static List<CHighlightConfigs> highlightList(String tableName) throws SQLException{
			ResultSet result = null;
			//String s="";
			List<CHighlightConfigs> recordList = new ArrayList<CHighlightConfigs>();
			con = getDBConnection();
			try {
				//con = dataSource.getConnection();  
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL.PR_HIGHLIGHT_CONFIG_GET(?,?)}"
	            );
	        	cs.registerOutParameter(2, OracleTypes.CURSOR);
	        	cs.setString(1, tableName);
	        	cs.executeQuery();
	        	result = (ResultSet) cs.getObject(2);
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
					   recordList.add(record);
					}
					
		    	
		} 
			finally {
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
			return recordList;
	}	
	//Lay danh sach email trong nhom nguoi can gui thu
	public static List<SysDefineSmsEmail> userList(String groupName) throws SQLException{
				ResultSet result = null;
				List<SysDefineSmsEmail> recordList = new ArrayList<SysDefineSmsEmail>();
				con = getDBConnection();
				try {
					//con = dataSource.getConnection();  
		        	cs = con.prepareCall(
		                "{call PK_AL_SEND_MAIL.PR_USER_SEND_MAIL_GET(?,?)}"
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
				finally {
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
				return recordList;
		}	
	// Luu noi dung mail gui di
	private void saveMailSended(String subject, String userTo, String content,
				String status,String mailId,String mailLevel,String hour) throws SQLException {
	con = getDBConnection();
			try {
				//con = dataSource.getConnection();  
	        	cs = con.prepareCall(
	                "{call PK_AL_SEND_MAIL.PR_SEND_MAIL_INSERT(?,?,?,?,?,?,?)}"
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
			finally {
				
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
	//Lay danh sach cot dinh nghia bloc se hien thi cua tung block trong mail
	public static List<CTableConfig> columnList(String tableName) throws SQLException{
					ResultSet result = null;
					List<CTableConfig> recordList = new ArrayList<CTableConfig>();
					con = getDBConnection();
					try {
						//con = dataSource.getConnection();  
			        	cs = con.prepareCall(
			                "{call PK_AL_SEND_MAIL.PR_COLUMN_TABLE_CONFIG_GET(?,?)}"
			            );
			        	cs.registerOutParameter(2, OracleTypes.CURSOR);
			        	cs.setString(1, tableName);
			        	cs.executeQuery();
			        	result = (ResultSet) cs.getObject(2);
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
						   recordList.add(record);
				         }
				    	
				} 
					finally {
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
					return recordList;
			}	
	
	private String getDataWorkBlock(String tableName, String blockName,
			String day, String hourStr, String mailName, String mailTo,
			List<CTableConfig> columnList, List<CHighlightConfigs> highlightList, String filePath,String fileName,String mailID) throws SQLException {

		ResultSet result = null;
	//	ResultSet resultExport = null;
		String content="<br>";
		System.out.println(tableName);
		try {
			con = getDBConnection();
			//con = dataSource.getConnection();  
        	cs = con.prepareCall(
                "{call PK_AL_SEND_MAIL.PR_DATA_WORK_MAIL_GET(?, ?, ?, ?,?,?,?,?)}"
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
			 System.out.println("content header: " + content);
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
						System.out.println(content);
						content = content.replace("#R#", String.valueOf(numRow));
						System.out.println("replace:"+content);
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
												if (cHighlightConfigs.getFormula().equals(">=")&& ((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())>= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())>= result.getDouble(cHighlightConfigs.getValue()))))
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
											System.out.println(content);
											content = content.replace("#R#", String.valueOf(numRow));
											System.out.println("replace:"+content);
											numRow=1;
											columnMege = result.getString(cTableConfig.getTableColumn());
											content=content + "<td rowspan=\"#R#\" style=\"border:1px solid;";
											for (CHighlightConfigs cHighlightConfigs : highlightList) {
												if (cTableConfig.getTableColumn().equalsIgnoreCase(cHighlightConfigs.getKey()))
												{
													if (cHighlightConfigs.getFormula().equals(">=")&& ((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())>= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())>= result.getDouble(cHighlightConfigs.getValue()))))
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
													}
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
											if (cHighlightConfigs.getFormula().equals(">=")&& ((cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("O") &&result.getDouble(cTableConfig.getTableColumn())>= Double.parseDouble(cHighlightConfigs.getValue()))||(cHighlightConfigs.getTYPE_STYLE().equalsIgnoreCase("COLUMN_TABLE") && result.getDouble(cTableConfig.getTableColumn())>= result.getDouble(cHighlightConfigs.getValue()))))
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
		finally {
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
		
		return content;
	}
	//Lay danh sach email trong nhom nguoi can gui thu
		public static List<M_FILE_ATTACHMENT> fileAttachList(String tableName, String blockName,
				String day, String hourStr, String mailName, String mailTo,
				List<CTableConfig> columnList, List<CHighlightConfigs> highlightList, String filePath,String fileName,String mailID) throws SQLException {

					ResultSet result = null;
					List<M_FILE_ATTACHMENT> recordList = new ArrayList<M_FILE_ATTACHMENT>();
					con = getDBConnection();
					try {
						cs = con.prepareCall(
				                "{call PK_AL_SEND_MAIL.PR_FILE_ATTACH_MAIL_GET(?, ?, ?, ?,?,?,?,?)}"
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
					finally {
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
					return recordList;
			}	

	
	// Goi cau lenh chay job		
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
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
		System.out.println("userFrom: "+ userFrom);
		System.out.println("password: "+ password);
		System.out.println("userTo: "+ userTo);
		System.out.println("subject: "+ subject);
		
		//LAY THONG SO CAI DAT GUI MAIL
		Properties props = AL_Global.MAIL_CONFIG;
		String errorSend="";
		// Kiem tra trang thai gui mail
		//Neu dang la trang thai gui mail thi kiem tra gio gui mai co nam trong khoang 8->23h?
		if (statusSend!=null && statusSend.equals("Y"))
		{
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
				String status="S";
				try {
					mailLis = mailList(dateTimeStr);
				
				for (SysMailParameterMaster sysMailParameterMaster : mailLis) {
					String mailId= sysMailParameterMaster.getMailId();
					String mailLevel = sysMailParameterMaster.getMailLevel();
					String day = df.format(sysMailParameterMaster.getDay());
					String typeMailHour=sysMailParameterMaster.getTypeMailHour();
					String contentHead = sysMailParameterMaster.getContentHeader();
					String contentRoot = sysMailParameterMaster.getContentRooter();
					List<String> fileName = null;
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
					//subject = title+" ["+hour+":00:00 - "+day+"]";
					//file path
					if (sysMailParameterMaster.getRootFileAttached()!=null)
					{
						filePath = sysMailParameterMaster.getRootFileAttached();
					}
					List<String> file = new ArrayList<String>();
					//Content
					String content = "";
					if (contentHead!=null&&!contentHead.equals(""))
					{
						content = "<span>"+contentHead.replace("#DATE#", day).replace("#HOUR#", hourStr+":00:00 ")+"</span>";
						
					}
					content=content + "<br/>";
					content=content + "<center><div>";
					/*content=content + "<table>";
					content=content + "<tr><td  align=\"center\"><h1 style=\"font-weight: bold;\">"+title+"</h1></td></tr>";
					content=content + "<tr><td align=\"center\"><h2 style=\"font-weight: bold; font-size: 35;\">Ngày: "+day +" - Giờ: "+ String.valueOf(hour) +":00:00  </h2></td>";
					content=content + "</tr></table>";*/
					//content=content + "<div align=\"left\"><h2 style=\"font-weight: bold;font-size: 32;\">"+title+"</h2></div>";
					// luu trang thai dang thuc hien gui mail nay
					saveMailSended(subject,null,"Dang gui","C",mailId,mailLevel,hourStr);
					
					List<SysMailParameter> blockList = blockList(mailLevel,mailId);
					for (SysMailParameter block : blockList) {
						//content=content + "<br/>";
						//System.out.println(block.getTableName());
						try
							{
								String tableName = block.getTableName();
								// lay du lieu gui mail
								List<CTableConfig> columnList = columnList(tableName);
								List<CHighlightConfigs> highlightList=highlightList(tableName);
								String mailTo=sysMailParameterMaster.getMailTo();
								content=content + getDataWorkBlock(tableName,block.getBlockName(),day,hourStr,sysMailParameterMaster.getMailName(),mailTo,columnList,highlightList,filePath,block.getFileName()+dateNow,mailId);
								List<M_FILE_ATTACHMENT> fileAttachList = fileAttachList(tableName,block.getBlockName(),day,hourStr,sysMailParameterMaster.getMailName(),mailTo,columnList,highlightList,filePath,block.getFileName()+dateNow,mailId);
								if (fileAttachList.size()>0)
								{
									fileName = new ArrayList<String>();
									for (M_FILE_ATTACHMENT m_FILE_ATTACHMENT : fileAttachList) {
										fileName.add(m_FILE_ATTACHMENT.getFilePath());
									}
								}
									
							}
							catch(Exception exp)
							{
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
						fileName = new ArrayList<String>();
						for (String item: file) {
							if (item != null && !item.trim().equals("")) {
								fileName.add(filePath.concat("/").concat(item+dateNow+".xls"));
							}
						}
					}
					//Xu ly dia chi thu gui va gui mail den tung thanh vien trong nhom.
					String addressMail = sysMailParameterMaster.getMailTo().replaceAll(";",",");
					String[] groupList = addressMail.split(",");
					String mailTo="";
					String temp="";
					for (String group : groupList) {
						
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
							    			mailTo += user.getGroupUser();
							    			temp=",";
							    		}
								    		
							    	}
						    	}
							}
						  }
						}
					//gui mail
					try
					{
						System.out.println("mail to:"+mailTo);
						Mail mail = new Mail(props, fileName, userFrom, password,
								mailTo,null,null, subject, content);
						
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
						logger.warn("Gui mail that bai");
						System.out.println("Gui mail that bai");
						errorSend="Error in sending mail";
						status="F";
					}
					saveMailSended(subject,mailTo,errorSend,status,mailId,mailLevel,hourStr);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					
				}	
				
				
			}
		}		
	}
	
	
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
			return DriverManager.getConnection("jdbc:oracle:thin:@10.30.6.22:1521:VHKT", "alarm", "alarm123");
			//return DriverManager.getConnection("jdbc:oracle:thin:@10.18.18.66:1521:ddh", "ALARM", "ALARM201309");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			return null;
		}

	}
	public static void main(String arg[]) throws JobExecutionException {
		SendMailWorkJob job = new SendMailWorkJob();
		//String content= "<td rowspan=\"#R#\" style=\"border: 1px dotted  #0099CC;";
		//content.replace("#R#", String.valueOf(3));
		//.out.println(content);
		job.execute(null);
	}
}
