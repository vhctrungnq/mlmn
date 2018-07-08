package vn.com.vhc.alarm.util.telnet;

import org.apache.commons.net.telnet.*;

/*import java.io.BufferedReader;*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
/*import java.io.InputStreamReader;*/
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.core.AL_CoreServiceThread;

public class TCPClient {

	private InputStream readfromServer;
	private TelnetClient telnetClient = new TelnetClient();;
	private PrintWriter outtoServer;
	private Logger logger = Logger.getLogger(AL_CoreServiceThread.class
			.getName());
	/*
	 * private static String destinationPath = "E:/alarmdata/download/";
	 */
	/*
	 * private static String destinationPath =
	 * "/home/oracle/u02/rawfile/alarm/download";
	 */

	private String fromServer = "";
	private String toServer = "";

	/*
	 * private String destinationPath = AL_Global.SYSTEM_CONFIG
	 * .getProperty(AL_Setting.BASE_LOCAL_DIR) ;
	 */

	public void openTelnet(String ipAddress, int port) {
		try {
			telnetClient.connect(ipAddress, port);
			telnetClient.setDefaultTimeout(10000);
			telnetClient.setKeepAlive(true);
		} catch (IOException e) {
			logger.error("Can't connect to server: " + e.getMessage());
			try {
				telnetClient.disconnect();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

	private String Read() {
		try {

			Thread.sleep(1000);
			String strReturnfromServer = "";
			byte[] buffer = new byte[5000000];
			int bytes_read;
			bytes_read = readfromServer.read(buffer);
			strReturnfromServer = new String(buffer, 0, bytes_read);
			return strReturnfromServer;
		} catch (Exception e) {
			CloseTelnet();
			return " ";

		}
	}

	private void Send(String strtoServer) {
		outtoServer.println(strtoServer);

	}

	public void CloseTelnet() {

		try {
			telnetClient.disconnect();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void Command3G(String strCommand, String p_SeverName,
			String p_AlarmTypeID, String p_SDate, String p_STime,
			String p_EDate, String p_ETime, String p_IP, String p_Username,
			String p_Password, String p_Command, String destinationPath) {
		// WsC1.SendData ("ZAHP::NR=7401,:" & SD & "," & st & "," & ED & "," &
		// ET & ";" & Chr$(13))
		try {

			/*
			 * BufferedReader inFromUser = new BufferedReader(new
			 * InputStreamReader( System.in));
			 */
			BufferedWriter Writer;
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();
			int iCount = 0;
			/*
			 * String demo = "RNC_TEMP"+p_IP+p_AlarmTypeID.toString() + ".txt";
			 * File outFile = new File(destinationPath, demo); Writer = new
			 * BufferedWriter(new FileWriter(outFile)); String strFileName = "";
			 */

			String strFileName = "NOKIA_" + p_SeverName + "_"
					+ strCommand.replaceAll(":", "") + "." + p_AlarmTypeID
					+ "_" + p_SDate.replaceAll("-", "") + "."
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
					if (fromServer.length() > 1)
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
					} else if (fromServer.contains("COMMAND EXECUTED") == true) {
						System.out.println("Quit:" + iCount);
						break;

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						iCount++;
						System.out.println("Chay lan:" + iCount + "\r\n"
								+ strFileName);
						if (iCount >= 20)
							break;

					}

				}
			} catch (Exception e) {
				logger.error("Can't close file " + strFileName);
			} finally {
				Writer.close();
			}
		} catch (Exception e) {
			System.out.println("Error write file");
		}
	}

	public void Command2G(String p_SeverName, String p_AlarmTypeID,
			String p_SDate, String p_STime, String p_IP, String p_Username,
			String p_Password, String p_Command, String destinationPath) {
		// WsC1.SendData ("ZEOH:" & SD & "," & st & ":NR=7704;" & Chr$(13))
		try {
			
			BufferedWriter Writer;

			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream(); 

			int iCount = 0;
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = "NOKIA_HISTORY" + p_SeverName + "_" + "ZEOH" + "_" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour;

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
					if (fromServer.length() > 1)
						Writer.write(fromServer);

					if (fromServer.contains("ENTER USERNAME") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);
					} else if (fromServer.contains("ENTER PASSWORD") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);

					} else if (fromServer.contains("MAIN LEVEL COMMAND") == true) {

						toServer = "ZEOH;"+ p_Command;

						Send(toServer);
					} else if (fromServer.contains("COMMAND EXECUTED") == true) {
						System.out.println("Quit:" + iCount);
						break;

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						iCount++;
						System.out.println("Chay lan:" + iCount + "\r\n"
								+ strFileName);
						if (iCount >= 20)
							break;

					}

				}
			} catch (Exception e) {
				logger.error("Can't close file " + strFileName);
			} finally {
				Writer.close();
			}
		} catch (Exception e) {
			System.out.println("Error write file: "+e);
		}
	}

	public void CommandActiveNokia(String p_ServerName, String p_AlarmTypeID,
			String p_SDate, String p_STime, String p_IP, String p_Username,
			String p_Password, int p_Count, String p_Command,
			String destinationPath) {
		// "10.21.129.132";
		try {

			/*
			 * telnetClient = new TelnetClient(); telnetClient.connect(p_IP,
			 * port); telnetClient.setKeepAlive(true); outtoServer = new
			 * PrintWriter(telnetClient.getOutputStream(), true); readfromServer
			 * = telnetClient.getInputStream();
			 */
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();
			BufferedWriter Writer;

			Date pDate = new Date();
			int iCount = 0;
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = "ACTIVE." + p_ServerName + "." + p_AlarmTypeID
					+ "-" + p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;

			File outFile = new File(destinationPath + "/NSN-Active",
					strFileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			/*System.out.println(strFileName);*/
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					/*System.out.println(fromServer);*/
					// ghi du lieu vao file
					if (fromServer.length() > 1)
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

					} else if (fromServer.contains("COMMAND EXECUTED") == true) {
						System.out.println("Quit:" + iCount);
						break;

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						iCount++;
						if (iCount >= 20)
							break;

					}
				}

			} catch (Exception ce) {
				// Xu ly cac truong hop loi o day

				logger.info("Error Download: " + ce.getMessage());

			} finally {

				Writer.close();

			}
		} catch (Exception e) {
			System.out.println("Error write file");
		}
	}

	public void CommandActiveEricsson(String p_SeverName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			int p_Count, String p_Command, String destinationPath) {
		// WsC1.SendData ("ZEOH:" & SD & "," & st & ":NR=7704;" & Chr$(13))
		try {

			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);// "10.21.129.132";
			int iCount = 0;
			/*
			 * telnetClient = new TelnetClient(); telnetClient.connect(p_IP,
			 * port); telnetClient.setKeepAlive(true); outtoServer = new
			 * PrintWriter(telnetClient.getOutputStream(), true); readfromServer
			 * = telnetClient.getInputStream();
			 */
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			/*
			 * BufferedReader inFromUser = new BufferedReader(new
			 * InputStreamReader( System.in));
			 */
			BufferedWriter Writer;

			String fileName = "ERICSSON_ACTIVE." + p_SeverName + "-"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;

			File strFileName = new File(destinationPath + "/ERICSSON-Active",
					fileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(strFileName));
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					/*System.out.println(fromServer);*/

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

					} else if (fromServer.contains("END")) {
						System.out.println("Quit:" + iCount);
						break;

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						iCount++;

						System.out.println("Chay lan:" + iCount + "\r\n"
								+ strFileName);
						if (iCount >= 20)
							break;

					}

				}
			} catch (Exception e) {
				logger.error("Can't close file " + fileName);
			} finally {
				Writer.close();
			}
			System.out.println("Quit OSS :" + p_SeverName + iCount);
		} catch (Exception e) {
			System.out.println("Error write file");
		}
	}

	public void CommandMBL(String ip, String bsc, String userName, String pass,
			String p_Command, String destinationPath) {
		try {

			String FromServer;
			String ToServer;

			String strUsername = userName;// "vhcacc";
			String strPassword = pass;// "123456";
			int iCount = 0;
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd_HHmmss");

			String strDay = dfDay.format(pDate);

			// telnetClient = new TelnetClient();
			// clientSocket.connect(strIP);
			// telnetClient.connect(strIP, strPort);
			// telnetClient.setKeepAlive(true);

			// PrintWriter w = new
			// PrintWriter(clientSocket.getOutputStream(),true);
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			BufferedWriter Writer;

			String fileName = "ERICSSON_MBL_ABL." + bsc + "-" + strDay;

			File strFileName = new File(destinationPath + "/ERICSSON-MBL",
					fileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(strFileName));
			try {
				while (true) {
					ToServer = null;
					// Send("\n");
					FromServer = Read();
					/*System.out.println(FromServer);*/

					// ghi du lieu vao file
					if (FromServer.length() > 1)
						Writer.write(FromServer);

					if (FromServer.contains("login:") == true) {

						ToServer = strUsername + p_Command;
						Send(ToServer);

					} else if (FromServer.contains("Password:") == true) {

						ToServer = strPassword + p_Command;
						Send(ToServer);

					} else if (FromServer.contains(">") == true) {

						ToServer = "eaw" + " " + bsc + p_Command;
						Send(ToServer);

					} else if (FromServer.contains("<")) {
						ToServer = "rxmsp:moty=rxotrx;" + p_Command;
						Send(ToServer);
					} else if (fromServer.contains("END")) {
						System.out.println("Quit:" + iCount);
						break;

					} else {
						// KET THUC DU LIEU TRA VE
						// CloseTelnet();
						iCount++;
						System.out.println("Chay lan:" + iCount + "\r\n"
								+ strFileName);
						if (iCount >= 40)
							break;
					}

				}

			} catch (Exception e) {
				logger.error("Can't close file " + fileName);
			} finally {
				Writer.close();
			}
			System.out.println("Quit ABL MBL:" + iCount);
		} catch (Exception e) {
			System.out.println("Error write file");
		}
	}

	// Telnet lay du lieu Active Ericsson 3G va MGW
	public void CommandActiveE3GCore(String p_SeverName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			int p_Count, String p_Command, String destinationPath)
			 {
		try {
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			BufferedWriter Writer;

			String fileName = "ERICSSON_ACTIVE." + p_SeverName + "-"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour + "." + p_Count;

			File outFile = new File(destinationPath + "/ERICSSON-Active",
					fileName + ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			try {
				while (true) {
					toServer = null;
					fromServer = Read();
					/*System.out.println(fromServer);*/

					Writer.write(fromServer);

					if (fromServer.trim().equalsIgnoreCase("login:") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);
					} else if (fromServer.trim().equalsIgnoreCase("Password:") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);
						Thread.sleep(100);

						toServer = "amos " + p_SeverName + p_Command;
						Send(toServer);
						Thread.sleep(100);

						toServer = "alt" + p_Command;
						Send(toServer);
						Thread.sleep(100);
					} else if (fromServer.contains(">>> Total:")) {

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

	public void CommandMBLCELL(String ip, String bsc, List<String> mo,
			String userName, String pass, String p_Command,
			String destinationPath) {
		try {

			String FromServer;
			String ToServer;

			String strUsername = userName;// "vhcacc";
			String strPassword = pass;// "123456";

			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd_HHmmss");

			String strDay = dfDay.format(pDate);

int j = 0; 
			
			//openTelnet(ip, 23);
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			BufferedWriter Writer;

			String fileName = "ERICSSON_MBL_ABL_CELL." + bsc + "-" + strDay;

			File outFile = new File(destinationPath + "/ERICSSON-MBL-CELL", fileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			try {
				while (true) {
					if (j == mo.size()) {
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
						/*System.out.println(ToServer);*/
						Send(ToServer);

					} else if (FromServer.trim().equalsIgnoreCase("Password:") == true) {

						ToServer = strPassword + p_Command;
						/*System.out.println(ToServer);*/
						Send(ToServer);

					} else if (FromServer.contains(">") == true) {

						ToServer = "eaw" + " " + bsc + p_Command;
						Send(ToServer);
						FromServer = Read();
						Writer.write(FromServer);
						/*System.out.println(FromServer);*/

					}

					if (FromServer.contains("<")) {

						if (j == mo.size()) {
							Writer.close();
							break;
						}

						for (int i = 0; i < mo.size(); i++) {
							j++;
							ToServer = "RXMOP:MO=" + mo.get(i) + ";"
									+ p_Command;
							Send(ToServer);
							FromServer = Read();
							Writer.write(FromServer);
							System.out.println(FromServer);
							// Thread.sleep(300);
						}
					}
				}

			} catch (Exception e) {
				logger.error("Can't close file " + fileName);
			} finally {
				Writer.close();
			}
		} catch (Exception e) {

		}

	}
	
	public void Command_GGSN(String p_SeverName, String p_SDate,
			String p_STime, String p_IP, String p_Username, String p_Password,
			String p_Command, String destinationPath) {

		try {
			
			openTelnet(p_IP, 23);
			
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();
			BufferedWriter Writer;

			int iCount = 0;
			Date pDate = new Date();

			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
			DateFormat dfHour = new SimpleDateFormat("HHmmss");

			String strDay = dfDay.format(pDate);
			String strHour = dfHour.format(pDate);

			String strFileName = "ACTIVE_GGSN." + p_SeverName + "_"
					+ p_SDate.replaceAll("-", "") + "."
					+ p_STime.replaceAll("-", "") + "-" + strDay + "."
					+ strHour;

			File outFile = new File(destinationPath + "/GGSN-2", strFileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			 System.out.println(strFileName); 
			try {
				while (true) {

					toServer = null;
					fromServer = Read();
					 System.out.println(fromServer.replaceAll("--More--", ""));
					// ghi du lieu vao file
					if (fromServer.length() > 1)
						Writer.write(fromServer.replaceAll("--More--", "")); 

					if (fromServer.contains("login:") == true) {

						toServer = p_Username + p_Command;
						Send(toServer);
					} else if (fromServer.contains("Password:") == true) {

						toServer = p_Password + p_Command;
						Send(toServer);

					} else if (fromServer.contains("Terminal type?") == true) {

						toServer = "\n";

						Send(toServer);
					} else if (fromServer.contains("#") == true) {

						toServer = "clish" + p_Command;

						Send(toServer);

					} else if (fromServer.contains("-- More --") == true) {
						toServer = p_Command;

						Send(toServer);

					} else if (fromServer.contains(">") == true) {
						if(iCount == 0){
							toServer = "show fm alarms" + p_Command;
							Send(toServer);
							iCount = 1;
						}else{
							break;
						} 
					} 
				}
			} catch (Exception e) {

			} finally {
				Writer.close();

			}
		} catch (Exception e) {
			System.out.println("Error write file" + e.getMessage());
		}
	}

}
