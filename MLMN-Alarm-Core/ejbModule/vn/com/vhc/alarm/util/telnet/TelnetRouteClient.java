package vn.com.vhc.alarm.util.telnet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.util.AL_Setting;

public class TelnetRouteClient {
	private static InputStream readFromServer;
	private static Socket clientSocket;
	private static PrintWriter outToServer;
	public static final String DATE_FORMAT_NOW = "MMM dd";
	private static int count = 0;
	//private static String destinationPath = "E:/gnosdata/download/";
	private static String destinationPath = AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.BASE_LOCAL_DIR);

	private static void Send(String strToServer) {
		
		outToServer.println(strToServer);
	}
	
	// Lay ngay thang hien tai
	/*Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT_NOW);
		
    String p_Time = sdf1.format(cal.getTime());*/
    
	private static String Read() throws IOException, Exception {
		
		Thread.sleep(1000);
		String strReturnFromServer = "";
		byte[] buffer = new byte[50000];
		int bytes_read;
		// Doc du lieu sever gui ve theo kieu byte
		bytes_read = readFromServer.read(buffer);
		// Chuyen du lieu kieu byte sang kieu string
		strReturnFromServer = new String(buffer, 0, bytes_read);
		
		return strReturnFromServer;
	}

	public void CloseTelnet() throws IOException {

		clientSocket.close();
	}
	
	public void TelnetRoute(String p_Time, String p_RouteName,
			String p_IP, String p_Username, String p_Password)
			throws Exception, IOException {
		// WsC1.SendData ("ZAHP::NR=7401,:" & SD & "," & st & "," & ED & "," &
		// ET & ";" & Chr$(13))

		String FromServer;
		String ToServer;
		String strIP = p_IP;
		int strPort = 23;
		String strUsername = p_Username.toString().trim();
		String strPassword = p_Password.toString().trim();
		
		System.out.println("Time = "+p_Time);
		System.out.println("p_RouteName = "+p_RouteName);
		System.out.println("strPassword = "+strPassword);
		System.out.println("strUsername = "+strUsername);

		clientSocket = new Socket(strIP, strPort);
		outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
		readFromServer = clientSocket.getInputStream();

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		BufferedWriter Writer;
		String strFileName = "";
		strFileName = p_RouteName+"_"+p_Time.replaceAll(" ", "");
		File outFile = new File(destinationPath+"/IPBB", strFileName+p_RouteName + ".txt");
		Writer = new BufferedWriter(new FileWriter(outFile));
		System.out.println(strFileName);

		while (true) {
			ToServer = null;
			FromServer = Read();
			System.out.println(FromServer);
			// ghi du lieu vao file
			if (strFileName.length() > 1)
				
				FromServer = FromServer.replaceAll("", "");
				Writer.write(FromServer.replaceAll("--More--", ""));

			if (FromServer.contains("Username") == true) {
				
				ToServer = strUsername;
				Send(ToServer);
				
			} else if (FromServer.contains("Password") == true) {
				
				ToServer = strPassword;
				Send(ToServer);

			} else if (FromServer.contains("#") == true) {
				if(count == 0){

					count = count + 1;
					ToServer = "show log "+"| in "+p_Time+"\r\n";

					Send(ToServer);
					Thread.sleep(3000);
				
				}else{
					count = 0;
					System.out.println("quit");
					Writer.close();
					break;
				}
			} 
			else if (FromServer.contains("--More--")){
				
				ToServer = "\r\n";
				Send(ToServer);
			}
			else {

				ToServer = inFromUser.readLine();
				if (ToServer.equals("Q") || ToServer.equals("q")) {

					Send(ToServer);
				
					break;
				}

				else {
					Send(ToServer);
				}
			}

		}
		CloseTelnet();
		Writer.close();
	}
}
