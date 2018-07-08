package vn.com.vhc.alarm.util.telnet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.download.AL_DyMblAblLog;
import vn.com.vhc.alarm.cni.info.AL_CServersTelnet;
import vn.com.vhc.alarm.cni.info.AL_CSystemConfig;
import vn.com.vhc.alarm.core.AL_Global;

	public class TCPClientTest {
		
		
		private static Logger logger = Logger.getLogger(AL_Global.class.getName()); 
		
		public static final String TELNET_DELAY_HOUR = "telnet.delay.hour"; 
		
		private static InputStream readfromServer;
		private static TelnetClient telnetClient = new TelnetClient();
		private static PrintWriter outtoServer; 
		private static List<String> listMo = new ArrayList<String>();
		private static String pServerNamABL = "";
		private static ALTelnetClient cls;

		private static String fromServer = "";
		private static String toServer = "";
		
		@SuppressWarnings("unused")
		private static String RAW_DOWNLOAD_DIR = "raw.download.dir";
		
		//private static String destinationPath = getSystemConfig(RAW_DOWNLOAD_DIR).getParamValue();
		private static String destinationPath = "/home/thanhlv/oracle/u02/alarm/rawfile/download";
		
		public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";

		public static void openTelnet(String ipAddress, int port) throws IOException {
			try {
				telnetClient.connect(ipAddress, port);
				telnetClient.setKeepAlive(true);
			} catch (IOException e) {
				logger.error("Can't connect to server: " + e.getMessage());
				telnetClient.disconnect();
				e.printStackTrace();
			}

		}

		private static String Read() throws IOException, Exception {
			Thread.sleep(500);
			String strReturnfromServer = "";
			byte[] buffer = new byte[50000];
			int bytes_read;
			bytes_read = readfromServer.read(buffer);
			strReturnfromServer = new String(buffer, 0, bytes_read);
			return strReturnfromServer;
		}

		private static void Send(String strtoServer) {
			outtoServer.println(strtoServer);

		}

		public static void CloseTelnet() throws IOException {

			telnetClient.disconnect();
		}

		public static void Command3G(String strCommand, String p_ServerName,
				String p_AlarmTypeID, String p_SDate, String p_STime,
				String p_EDate, String p_ETime, String p_IP, String p_Username,
				String p_Password, String p_Command) throws Exception, IOException {
			// WsC1.SendData ("ZAHP::NR=7401,:" & SD & "," & st & "," & ED & "," &
			// ET & ";" & Chr$(13))

			/*
			 * BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
			 * System.in));
			 */
			BufferedWriter Writer;
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			/*
			 * String demo = "RNC_TEMP"+p_IP+p_AlarmTypeID.toString() + ".txt"; File
			 * outFile = new File(destinationPath, demo); Writer = new
			 * BufferedWriter(new FileWriter(outFile)); String strFileName = "";
			 */

			String strFileName = "NOKIA_" + p_ServerName + "_"
					+ strCommand.replaceAll(":", "") + "." + p_AlarmTypeID + "_"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-"
					+ p_EDate.replaceAll("-", "") + "."
					+ p_ETime.replaceAll("-", "");

			File outFile = new File(destinationPath + "/NSN-3G", strFileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					// ghi du lieu vao file
					if (strFileName.length() > 1)
						Writer.write(fromServer);

					if (fromServer.contains("ENTER USERNAME") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);

					} else if (fromServer.contains("ENTER PASSWORD") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);

					} else if (fromServer.contains("MAIN LEVEL COMMAND") == true) {

						toServer = strCommand + "=" + p_AlarmTypeID + ",:"
								+ p_SDate + "," + p_STime + "," + p_EDate + ","
								+ p_ETime + ";" + p_Command;
						Send(toServer);
						Thread.sleep(5000);

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						break;
					}

				}
			} catch (Exception e) {
				logger.error("Can't close file " + strFileName);
			} finally {
				Writer.close();
			}
		}

		public static void Command2G(String p_ServerName, String p_AlarmTypeID,
				String p_SDate, String p_STime, String p_IP, String p_Username,
				String p_Password, String p_Command) throws Exception, IOException {
			// WsC1.SendData ("ZEOH:" & SD & "," & st & ":NR=7704;" & Chr$(13))
			System.out.println("Telnet " + p_IP + " Username  " + p_Username);
			// "10.21.129.132";

			/*
			 * telnetClient = new TelnetClient(); // clientSocket.connect(p_IP);
			 * telnetClient.connect(p_IP, port); telnetClient.setKeepAlive(true);
			 * outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			 * readfromServer = telnetClient.getInputStream();
			 */

			/*
			 * BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
			 * System.in));
			 */
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();
			BufferedWriter Writer;
			/*
			 * String demo = "BSC_TEMP"+p_IP+p_AlarmTypeID.toString() + ".txt"; File
			 * outFile = new File(destinationPath, demo); Writer = new
			 * BufferedWriter(new FileWriter(outFile)); String strFileName = "";
			 */

			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = "NOKIA_" + p_ServerName + "_" + "ZEOH.NR."
					+ p_AlarmTypeID + "_" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "." + strHour;

			File outFile = new File(destinationPath + "/NSN-2G", strFileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			System.out.println(strFileName);
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					System.out.println(fromServer);
					// ghi du lieu vao file
					if (strFileName.length() > 1)
						Writer.write(fromServer);

					if (fromServer.contains("ENTER USERNAME") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);
					} else if (fromServer.contains("ENTER PASSWORD") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);

					} else if (fromServer.contains("MAIN LEVEL COMMAND") == true) {

						toServer = "ZEOH;"+p_Command;

						Send(toServer);
						Thread.sleep(5000);

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						break;
					}

				}
			} catch (Exception e) {
				logger.error("Can't close file " + strFileName);
			} finally {
				Writer.close();
			}
		}

		public static void CommandActiveNokia(String p_ServerName, String p_AlarmTypeID,
				String p_SDate, String p_STime, String p_IP, String p_Username,
				String p_Password, int p_Count, String p_Command) throws Exception,
				IOException {
			// "10.21.129.132";

			/*
			 * telnetClient = new TelnetClient(); telnetClient.connect(p_IP, port);
			 * telnetClient.setKeepAlive(true); outtoServer = new
			 * PrintWriter(telnetClient.getOutputStream(), true); readfromServer =
			 * telnetClient.getInputStream();
			 */
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();
			BufferedWriter Writer;

			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = "ACTIVE." + p_ServerName + "." + p_AlarmTypeID
					+ "-" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "." + strHour
					+ "." + p_Count;

			File outFile = new File(destinationPath + "/NSN-Active", strFileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			System.out.println(strFileName);
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					System.out.println(fromServer);
					// ghi du lieu vao file
					if (strFileName.length() > 1)
						Writer.write(fromServer);

					if (fromServer.contains("ENTER USERNAME") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);
					} else if (fromServer.contains("ENTER PASSWORD") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);

					} else if (fromServer.contains("MAIN LEVEL COMMAND") == true) {

						toServer = p_AlarmTypeID + ";" + p_Command;

						Send(toServer);
						Thread.sleep(5000);

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						break;
					}
				}

			} catch (Exception ce) {
				// Xu ly cac truong hop loi o day

				logger.info("Error Download: " + ce.getMessage());

			} finally {

				Writer.close();

			}

		}
		
		public static void CommandActiveEricsson(String p_ServerName, String p_SDate,
				String p_STime, String p_IP, String p_Username, String p_Password,
				int p_Count, String p_Command) throws Exception, IOException {
			// WsC1.SendData ("ZEOH:" & SD & "," & st & ":NR=7704;" & Chr$(13))

			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);// "10.21.129.132";

			/*
			 * telnetClient = new TelnetClient(); telnetClient.connect(p_IP, port);
			 * telnetClient.setKeepAlive(true); outtoServer = new
			 * PrintWriter(telnetClient.getOutputStream(), true); readfromServer =
			 * telnetClient.getInputStream();
			 */
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			/*
			 * BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
			 * System.in));
			 */
			BufferedWriter Writer;

			String fileName = "ERICSSON_ACTIVE." + p_ServerName + "-"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "." + strHour
					+ "." + p_Count;

			File outFile = new File(destinationPath + "/ERICSSON-Active", fileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					System.out.println(fromServer);

					Writer.write(fromServer);

					if (fromServer.contains("login name") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);
					} else if (fromServer.contains("password") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);

					} else if (fromServer.contains("Windows NT Domain:")) {
						toServer = p_Command;
						Send(toServer);
					}

					else if (fromServer.contains("Login ok") == true) {

						toServer = "mml" + p_Command;
						Send(toServer);
						toServer = "allip;" + p_Command;
						Send(toServer);
						Thread.sleep(2000);
					} else if (fromServer.contains("END")) {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						// Writer.close();
						break;
					}
				}
			} catch (Exception e) {
				logger.error("Can't close file " + fileName);
			} finally {
				Writer.close();
			}
		}

		public static void CommandMBL(String ip, String bsc, String userName, String pass)
				throws Exception, IOException {

			String FromServer;
			String ToServer;
			
			String strUsername = userName;// "vhcacc";
			String strPassword = pass;// "123456";

			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd_HHmmss");

			String strDay = dfDay.format(pDate);

			// telnetClient = new TelnetClient();
			// clientSocket.connect(strIP);
			// telnetClient.connect(strIP, strPort);
			// telnetClient.setKeepAlive(true);
			
			// PrintWriter w = new PrintWriter(clientSocket.getOutputStream(),true);
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			BufferedWriter Writer;

			String fileName = "ERICSSON_MBL_ABL." + bsc + "-" + strDay;

			File outFile = new File(destinationPath + "/ERICSSON-MBL", fileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			try {
				while (true) {
					ToServer = null;
					// Send("\n");
					FromServer = Read();
					System.out.println(FromServer);

					// ghi du lieu vao file
					if (fileName.length() > 1)
						Writer.write(FromServer);

					if (FromServer.trim().equalsIgnoreCase("login:") == true) {

						ToServer = strUsername + "\n";
						Send(ToServer);

					} else if (FromServer.trim().equalsIgnoreCase("Password:") == true) {

						ToServer = strPassword + "\n";
						Send(ToServer);

					} else if (FromServer.contains(">") == true) {

						ToServer = "eaw" + " " + bsc + "\n";
						Send(ToServer);

					} else if (FromServer.contains("END")) {
						Writer.close();
						// KET THUC DU LIEU TRA VE
						CloseTelnet();
						break;
					} else if (FromServer.contains("<")) {
						ToServer = "rxmsp:moty=rxotrx;" + "\n";
						Send(ToServer);
						Thread.sleep(100);
					}

				}

			} catch (Exception e) {
				logger.error("Can't close file " + fileName);
			} finally {
				Writer.close();
			}

		}
		
		public static void CommandMBLCELL(String ip, String bsc, List<String> mo, String userName, String pass, String p_Command)
		{
			try{
				String FromServer;
				String ToServer;
				
				String strUsername = userName;// "vhcacc";
				String strPassword = pass;// "123456";

				Date pDate = new Date();

				DateFormat dfDay = new SimpleDateFormat("yyyyMMdd_HHmmss");

				String strDay = dfDay.format(pDate);
				
				int j = 0; 
				

				openTelnet(ip, 23);
				System.out.println("Telnet thanh cong");
				
				outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
				readfromServer = telnetClient.getInputStream();

				BufferedWriter Writer;

				String fileName = "ERICSSON_MBL_ABL_CELL." + bsc + "-" + strDay;

				File outFile = new File(destinationPath + "/ERICSSON-MBL", fileName
						+ ".txt");
				Writer = new BufferedWriter(new FileWriter(outFile));
				try {
					while (true) {
						if(j == mo.size()){
							break;						
						} 
						
						ToServer = null;
						// Send("\n");
						FromServer = Read();
						System.out.println(FromServer);

						// ghi du lieu vao file
						if (fileName.length() > 1)
							Writer.write(FromServer);

						if (FromServer.trim().equalsIgnoreCase("login:") == true) {

							ToServer = strUsername + p_Command;
							System.out.println(ToServer);
							Send(ToServer);

						} else if (FromServer.trim().equalsIgnoreCase("Password:") == true) {

							ToServer = strPassword + p_Command;
							System.out.println(ToServer);
							Send(ToServer);

						} else if (FromServer.contains(">") == true) {

							ToServer = "eaw" + " " + bsc + p_Command;
							Send(ToServer);
							FromServer = Read();
							Writer.write(FromServer);
							System.out.println(FromServer);

						}
						
						if (FromServer.contains("<")){	
							
							if(j ==  mo.size()){
								Writer.close();
								break;						
							} 
							
							for(int i =0; i < mo.size(); i++){
								j++;
								ToServer = "RXMOP:MO=" +mo.get(i)+";"+ p_Command;
								Send(ToServer);	
								FromServer = Read();
								Writer.write(FromServer);
								System.out.println(FromServer);
								//Thread.sleep(300);
							}						
						}
					}

				} catch (Exception e) {
					logger.error("Can't close file " + fileName);
				} finally {
					Writer.close();
					CloseTelnet();
				}
			}catch(Exception e){
				
			}
		}
		
		public static  void CommandActiveE3GCore(String p_ServerName, String p_SDate,
				String p_STime, String p_IP, String p_Username, String p_Password,
				int p_Count, String p_Command, String destinationPath){
			try {
				Date pDate = new Date();

				DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
				DateFormat dfHour = new SimpleDateFormat("HHmmss");

				String strDay = dfDay.format(pDate);
				String strHour = dfHour.format(pDate); 
				
				outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
				readfromServer = telnetClient.getInputStream();

				BufferedWriter Writer;

				String fileName = "ERICSSON_ACTIVE." + p_ServerName + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "." + strHour
						+ "." + p_Count;

				File outFile = new File(destinationPath + "/ERICSSON-Active", fileName
						+ ".txt");
				Writer = new BufferedWriter(new FileWriter(outFile));
				try {
					while (true) {
						toServer = null;
						fromServer = Read();
						System.out.println(fromServer);

						Writer.write(fromServer);

						if (fromServer.trim().equalsIgnoreCase("login:") == true) {

							toServer = p_Username + p_Command;
							Send(toServer);					
						}else if (fromServer.trim().equalsIgnoreCase("Password:") == true) {

							toServer = p_Password + p_Command;
							Send(toServer);
							Thread.sleep(100);

							toServer = "amos " +p_ServerName+ p_Command;
							Send(toServer);
							Thread.sleep(100);
							
							toServer = "alt" + p_Command;						
							Send(toServer);
							Thread.sleep(100);
						}
						else if (fromServer.contains(">>> Total:")) {
							
							break;
						}
					}
				} catch (Exception e) {
					logger.error("Can't close file " + fileName);
				} finally {
					Writer.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		public static  void Commad_GGSN(String p_ServerName, String p_SDate,
				String p_STime, String p_IP, String p_Username, String p_Password,
				String p_Command, String destinationPath){
			try {
				Date pDate = new Date();

				DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
				DateFormat dfHour = new SimpleDateFormat("HHmmss");

				String strDay = dfDay.format(pDate);
				String strHour = dfHour.format(pDate); 
				
				outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
				readfromServer = telnetClient.getInputStream();

				BufferedWriter Writer;

				String fileName = "ERICSSON_GGSN." + p_ServerName + "-"
						+ p_SDate.replaceAll("-", "") + "."
						+ p_STime.replaceAll("-", "") + "-" + strDay + "." + strHour;

				File outFile = new File(destinationPath + "/ERICSSON-Active", fileName
						+ ".txt");
				Writer = new BufferedWriter(new FileWriter(outFile));
				try {
					while (true) {
						toServer = null;
						fromServer = Read();
						System.out.println(fromServer);

						Writer.write(fromServer);

						if (fromServer.trim().equalsIgnoreCase("login:") == true) {

							toServer = p_Username + p_Command;
							Send(toServer);					
						}else if (fromServer.trim().equalsIgnoreCase("Password:") == true) {

							toServer = p_Password + p_Command;
							Send(toServer);
							Thread.sleep(100);
						}else if(fromServer.contains("[xterm]")){
							
							toServer = p_Command;
							Send(toServer);
						}else if(fromServer.contains("www") && fromServer.contains("#")){
							
							toServer = "clish"+p_Command;
							Send(toServer);
							Thread.sleep(1000);
							toServer = "show fm alarms"+p_Command;
							Send(toServer);
						}else if(fromServer.trim().equalsIgnoreCase("www."+p_ServerName+".com:2>")){
							Writer.close();
							CloseTelnet();
							break;
						}
					}
				} catch (Exception e) {
					logger.error("Can't close file " + fileName);
				} finally {
					Writer.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	 	
	public static void main(String arg[]) throws IOException, Exception { 
		
		initTaskQueues();
		System.out.println("initTaskQueues().size() = "+initTaskQueues().size());
		for(int i = 0; i < initTaskQueues().size(); i++){
			doTask(initTaskQueues().get(i));
		} 
	}

	public static List<AL_CServersTelnet> initTaskQueues() {
		List<AL_CServersTelnet> addTask = new ArrayList<AL_CServersTelnet>();
		Connection conn = null;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = "select * from C_SERVERS_TELNET where upper(status) = 'Y' order by ORDERING asc";

			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
				cal.add(Calendar.HOUR_OF_DAY, 0);
				String SysDate = sdf.format(cal.getTime());
				String Edate = SysDate.substring(0, SysDate.indexOf(" "))
						.toString().trim();
				String Etime = SysDate
						.substring(SysDate.indexOf(" ") + 1, SysDate.length())
						.toString().trim();

				AL_CServersTelnet downloadInfo = new AL_CServersTelnet();
				
				
				pServerNamABL = rs.getString("SERVER_NAME");
				downloadInfo.setPromptUser(rs.getString("PROMPT_USER"));
				downloadInfo.setPromptPass(rs.getString("PROMPT_PASS"));
				downloadInfo.setPromptCommand(rs.getString("PROMPT_COMMAND"));
				downloadInfo.setServerId(rs.getInt("SERVER_ID"));
				downloadInfo.setCount(rs.getInt("COUNT"));
				downloadInfo.setType(rs.getString("TYPE"));
				downloadInfo.setIpAddress(rs.getString("IP_ADDRESS"));
				downloadInfo.setServerName(rs.getString("SERVER_NAME"));
				downloadInfo.setTelnetUser(rs.getString("TELNET_USER"));
				downloadInfo.setTelnetPassword(rs.getString("TELNET_PASSWORD"));
				downloadInfo.setAlarmName(rs.getString("ALARM_NAME"));
				downloadInfo.setAlarmType(rs.getString("ALARM_TYPE"));
				downloadInfo.setPort(23);
				downloadInfo.seteDate(Edate);
				downloadInfo.seteTime(Etime);
				downloadInfo.setSysDate(SysDate);
				downloadInfo.setsDate(rs.getString("LOGIN_TIME")
						.substring(0, rs.getString("LOGIN_TIME").indexOf(" "))
						.toString().trim());
				downloadInfo.setsTime(rs
						.getString("LOGIN_TIME")
						.substring(rs.getString("LOGIN_TIME").indexOf(" ") + 1,
								rs.getString("LOGIN_TIME").length()).toString()
						.trim());

				downloadInfo.setLoginTime(rs.getString("LOGIN_TIME"));
				downloadInfo.setNode(rs.getString("NODE"));
				downloadInfo.setCommand(rs.getString("COMMAND"));
				downloadInfo.setTaskInfo("Server:"
						+ rs.getString("SERVER_NAME") + "----TYPE:"
						+ rs.getString("TYPE") + "----Alarm_name:"
						+ rs.getString("ALARM_NAME") + "\r\n");

				addTask.add(downloadInfo);
			} 
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
		return addTask;
	}
	
	public static void doTask(AL_CServersTelnet input) {
		AL_CServersTelnet beanInfo = (AL_CServersTelnet) input;

		String p_AlarmTypeID, p_SDate, p_STime, p_EDate, p_ETime, p_IP, p_Username, p_Password, p_SeverName, p_Command;
		int p_ServerId;
		int p_Count;
		p_SeverName = beanInfo.getServerName();
		p_AlarmTypeID = beanInfo.getAlarmName();
		p_SDate = beanInfo.getsDate();
		p_STime = beanInfo.getsTime();
		p_IP = beanInfo.getIpAddress();
		p_Username = beanInfo.getTelnetUser();
		p_Password = beanInfo.getTelnetPassword();
		p_Count = beanInfo.getCount();
		p_EDate = beanInfo.geteDate();
		p_ETime = beanInfo.geteTime();
		p_ServerId = beanInfo.getServerId(); 
		p_Command = beanInfo.getCommand();
		
	 	if (p_Command.equalsIgnoreCase("n"))
			p_Command = "\n";
		else
			p_Command = "\r\n"; 
	 	
		String strMessage = "";
		strMessage = "Alarm type " + beanInfo.getNode() + " "
				+ beanInfo.getAlarmType() + " TYPE:" + beanInfo.getType()
				+ " IP " + p_IP + " Username  " + p_Username + " ALARM TYPE: ";
		logger.info(strMessage);

		TCPClient send = new TCPClient();

		try {

			send.openTelnet(p_IP, 23);
			if (beanInfo.getAlarmType().equals("ALARM_RNC")) {

				if (beanInfo.getNode().equals("NOKIA")) {
					if (beanInfo.getType().contains("ACTIVE")) {
						cls = new ALTelnetClient();
						cls.set_DestinationPath(destinationPath);
						cls.set_Debug(true);
						
						//telnet lay du lieu NOKIA ACTIVE 3G
						cls.CommandNSN_Active_3G(p_SeverName, p_SDate, p_STime,
								p_IP, p_Username, p_Password);
					} else if (beanInfo.getType().contains("HISTORY")) {
						
						// telnet lay du lieu NOKIA HISTORY 3G
						send.Command3G("ZAHP::NR", p_SeverName, p_AlarmTypeID,
								p_SDate, p_STime, p_EDate, p_ETime, p_IP,
								p_Username, p_Password, p_Command,destinationPath);
					}
				} else if (beanInfo.getNode().equals("ERICSSON")) {
					if (beanInfo.getType().contains("ACTIVE")) {
						
						// telnet lay du lieu ERICSSON ACTIVE 3G VA ACTIVE MGW(MGH011E,...)
						send.CommandActiveE3GCore(p_SeverName, p_SDate,
								p_STime, p_IP, p_Username, p_Password, p_Count,
								p_Command, destinationPath);
					}
					// HISTORY DOWN LOAD BANG CRONTAB
					// continue;

				}

			} else if (beanInfo.getAlarmType().equals("ALARM_BSC")) {
				if (beanInfo.getNode().equals("NOKIA")) {
					if (beanInfo.getType().contains("HISTORY")) {
						
						// telnet lay du lieu NOKIA HISTORY 2G
						String[] AlarmTypeId = p_AlarmTypeID.replaceAll(";", ",").split(",");
						for(int i = 0; i< AlarmTypeId.length; i++){
							send.Command2G(p_SeverName, AlarmTypeId[i], p_SDate,
									p_STime, p_IP, p_Username, p_Password,
									p_Command, destinationPath);
						}
					} else if (beanInfo.getType().contains("ACTIVE")) {
						
						cls = new ALTelnetClient();
						cls.set_DestinationPath(destinationPath);
						cls.set_Debug(true);
						
						//telnet lay du lieu NOKIA ACTIVE 2G(ZAHO,ZEOL)
						cls.CommandNSN_Active(p_SeverName, p_SDate, p_STime,
								p_IP, p_Username, p_Password);

					}
				} else if (beanInfo.getNode().equals("ERICSSON")) {
					if (beanInfo.getType().contains("ACTIVE")) {

						cls = new ALTelnetClient();
						cls.set_DestinationPath(destinationPath);
						cls.set_Debug(true);
						cls.set_ReadWaitTimeout(30000);
					/*	p_Username = "ossuser_";
						p_Password = "ossuser0(0";
						*/
						//telnet lay du lieu ERICSSON ACTIVE 2G va ACTIVE STP
						cls.CommandERS_Active_2G(p_SeverName, p_SDate,
								p_STime, p_IP, p_Username, p_Password, p_Count);

					} else if (beanInfo.getType().contains("ABL_MBL")) {
						if (!p_SDate.equals(p_EDate)) {
							cls = new ALTelnetClient();
							cls.set_DestinationPath(destinationPath);
							cls.set_Debug(false);
							cls.set_ReadWaitTimeout(15000);
							cls.set_ConnectTimeout(5000);

							// Telnet lay du lieu Ericsson ABL_MBL
							// download 1 lan trong ngay
							String[] p_ArrayIP_BSC = pServerNamABL.replace(";",
									",").split(",");

							for (int iIP = 0; iIP < p_ArrayIP_BSC.length; iIP++) {
								
								// p_Server = p_ArrayIP_BSC[iIP].toString();
								cls.CommandMBL(p_IP,
										p_ArrayIP_BSC[iIP].toString(),
										p_Username, p_Password);								
							}

						}else{
							String[] p_ArrayIP_BSC = pServerNamABL.replace(";",",").split(",");
							for(int i = 0; i < p_ArrayIP_BSC.length; i++){
								getMo(p_ArrayIP_BSC[i]);
								
								for(int j = 0; j<listMo.size();j++){
									System.out.println("mo["+i+"] = "+listMo.get(i));
								}
								
								if(listMo.size() > 0){
									
									// telnet lay du lieu Ericsson CELL ABL_MBL( chay sau khi telnet ABL_MBL)	
									send.CommandMBLCELL(p_IP, p_ArrayIP_BSC[i], listMo, p_Username, 
											p_Password, p_Command, destinationPath);									
								}							
							}
						}										
					}
				}
			} else if (beanInfo.getAlarmType().equals("ALARM_SGSN")) {
				
				//telnet lay du lieu ERICSSON SGSN
				send.CommandActiveNokia(p_SeverName, p_AlarmTypeID, p_SDate,
						p_STime, p_IP, p_Username, p_Password, p_Count,
						p_Command, destinationPath);
			}
			/*else if (beanInfo.getAlarmType().equals("ALARM_GGSN")) {
				
				/// DOWN LOAD GGSN

			}*/
			int delayTime = Integer.parseInt(getSystemConfig(TELNET_DELAY_HOUR).getParamValue());
			upTime(p_Count, p_ServerId,delayTime);

		} catch (Exception ce) {
			// Xu ly cac truong hop loi o day

			logger.info("Error Thead Download: " + ce.getMessage());

		} finally { 
			send.CloseTelnet();

		}

	}
	
	public static void getMo(String bscid) {
		Connection conn = null;
		try {
			conn = getDBConnection();
			
			Statement st = conn.createStatement();

			String queryStr = "select MO from al_dy_mbl_abl_log "
					+ " where BSCID = '"+ bscid +"' and CELLID IS NULL" + " and trunc(day) = to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')";

			ResultSet rs = st.executeQuery(queryStr);
			
			while (rs.next()) {
				
				AL_DyMblAblLog getMoByBsc = new AL_DyMblAblLog();
				getMoByBsc.setMo(rs.getString("MO"));

				listMo.add(getMoByBsc.getMo());
			}
			
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
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
	
	private static void upTime(int count, int serverId, int delayTime) {

		Connection conn = null;

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		cal.add(Calendar.HOUR_OF_DAY, -delayTime);
		String SysDate = sdf.format(cal.getTime());

		count = count + 1;
		try {
			conn = getDBConnection();
			Statement st = conn.createStatement();

			String queryStr = "update C_SERVERS_TELNET set  COUNT ='" + count
					+ "'" + "," + "LOGIN_TIME = '" + SysDate + "'" + " "
					+ "where SERVER_ID = '" + serverId + "'";

			st.execute(queryStr); 
			st.close();

		} catch (SQLException e) {
			logger.warn("Try upTime: " + e);
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

	public static AL_CSystemConfig getSystemConfig(String paramName) {
		
		AL_CSystemConfig systemCofigList = new AL_CSystemConfig();
		Connection conn = null;
		
		try {
			conn = getDBConnection();
			
			Statement st = conn.createStatement();

			String queryStr = "select PARAM_NAME,PARAM_VALUE,DESCRIPTION,CONFIG_TYPE from C_SYSTEM_CONFIGS_MLMN " +
					" where  PARAM_NAME = '"+paramName+"'";
			
			ResultSet rs = st.executeQuery(queryStr);
			
			while (rs.next()) { 
				
				systemCofigList.setConfigType(rs.getString("CONFIG_TYPE"));
				systemCofigList.setDescription(rs.getString("DESCRIPTION")); 
				systemCofigList.setParamName(rs.getString("PARAM_NAME"));
				systemCofigList.setParamValue(rs.getString("PARAM_VALUE"));
			}
			
			rs.close();
			st.close();
		} catch (SQLException e) {
			logger.warn("File list to convert is empty" + e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
		return systemCofigList;
	}

	public static Connection getDBConnection() {
		String DRIVER_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource"; 
		String DBCONN_STRING = "jdbc:oracle:thin:@10.151.73.73:1522:orcl";
		String USERNAME = "ALARM";
		String PASSWORD = "ALARM";

		Connection conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
			conn = DriverManager.getConnection(DBCONN_STRING, USERNAME, PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}
}
