package vn.com.vhc.alarm.util.telnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;
 
public class Main2 {
	
	private static InputStream readfromServer;
	private static TelnetClient telnetClient = new TelnetClient();;
	private static PrintWriter outtoServer;
	private static Logger logger = Logger.getLogger(Main2.class
			.getName()); 
	
	public static void openTelnet(String ipAddress, int port) {
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

	private static String Read() {
		try {

			Thread.sleep(1000);
			String strReturnfromServer = "";
			byte[] buffer = new byte[500000];
			int bytes_read;
			bytes_read = readfromServer.read(buffer);
			strReturnfromServer = new String(buffer, 0, bytes_read);
			return strReturnfromServer;
		} catch (Exception e) {
			CloseTelnet();
			return " ";

		}
	}

	private static void Send(String strtoServer) {
		outtoServer.println(strtoServer);

	}

	public static void CloseTelnet() {

		try {
			telnetClient.disconnect();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	private static String path = "/home/thanhlv/oracle/u02/alarm/rawfile/converter/";
	private static String pathDown = "/home/thanhlv/oracle/u02/alarm/rawfile/download/";
	private static String bscName = "";
	private static String moName = "";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File(path); 
		fileName(file);
	} 
	public static void CommandMBLCELL(String ip, String bsc, String[] mo,
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
			
			openTelnet(ip, 23);
			outtoServer = new PrintWriter(telnetClient.getOutputStream(), true);
			readfromServer = telnetClient.getInputStream();

			BufferedWriter Writer;

			String fileName = "ERICSSON_MBL_ABL_CELL." + bsc + "-" + strDay;

			File outFile = new File(destinationPath + "/ERICSSON-MBL", fileName
					+ ".txt");
			Writer = new BufferedWriter(new FileWriter(outFile));
			try {
				while (true) {
					if (j == mo.length) {
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

					if (FromServer.contains("<")) {

						if (j == mo.length) {
							Writer.close();
							break;
						}

						for (int i = 0; i < mo.length; i++) {
							j++;
							ToServer = "RXMOP:MO=" + mo[i] + ";"
									+ p_Command;
							Send(ToServer);
							FromServer = Read();
							Writer.write(FromServer); 
							System.out.println(FromServer);
						}
					}
				} 
			} catch (Exception e) {
				logger.error("Can't close file " + fileName);
			} finally {
				Writer.close();
				CloseTelnet();
			}
		} catch (Exception e) {

		}

	}
	
	public static boolean fileName(File dir) { 
		
	        String regex = "(MO_B.*)(.txt)";
	        Pattern timePattern = Pattern.compile(regex);
	        boolean flag=false;
	        File listFile[] = dir.listFiles();
	        if(listFile != null) {
	                for(int i=0; i<listFile.length; i++) {
	                	/*
	                        if(listFile[i].isDirectory()) {
	                        	fileName(listFile[i]);
	                        } else {}*/

                    	Matcher m = timePattern.matcher(listFile[i].getName());
                    	
                            if(m.find())
                            {
                            	System.out.println("fileName["+i+"]"+listFile[i].getName());
                            	BufferedReader reader = null;
                				try {
                					reader = new BufferedReader(new FileReader(listFile[i]));
                    				String strLine = ""; 
                    				
                					while ((strLine = reader.readLine()) != null){
                						String[] str = strLine.split(";");
                						bscName = str[0].toString().trim();
                						moName = str[1].substring(0,str[1].length()-1);
                						String[] listMo = moName.split(",");
                						System.out.println("listMo = "+listMo.length);
                						String p_IP = "10.19.208.38";
                						String p_Username = "vhcacc";
                						String p_Password = "vhc@1234";
                						String p_Command = "\r\n";
                						
                						// telnet lay du lieu Ericsson CELL ABL_MBL( chay sau khi telnet ABL_MBL)	
                						CommandMBLCELL(p_IP, bscName, listMo, p_Username, p_Password, p_Command, pathDown);
            							listFile[i].delete();
            							System.out.println("delete thanh cong.");
                					}
                				} catch (IOException e) {
                					// TODO Auto-generated catch block
                					e.printStackTrace();
                				}finally {
                					if (reader != null) {
                						try {
                							reader.close();
                						} catch (IOException e) {
                							logger.warn("Close IO stream failure " + e);
                						}
                					}
                				}
                              flag= true;
                            }  
	                }
	        }
	        return flag;
	}
}
