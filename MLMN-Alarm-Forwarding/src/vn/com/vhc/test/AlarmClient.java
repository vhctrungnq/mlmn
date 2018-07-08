package vn.com.vhc.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class AlarmClient {

	private static final String fileName = "C:\\Users\\VHC-TRUNGNGAONGO\\Desktop\\alarm-debug.log.20170215.18";

	private static void start()  {
		
		try {
			Socket socket = null;
			OutputStream out = null;
			
			BufferedReader br = null;
			
			int round = 0;
			while (round < 1) {
				
				round ++;
				
				System.out.println(new Date());
				
				socket = new Socket("localhost", 1234);
				out = socket.getOutputStream();
				
				br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(fileName))));
				
				String strLine = "";
				while ((strLine = br.readLine()) != null) {
					out.write((strLine+"\n").getBytes());
					out.flush();
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					/*i += 1;
					if (i > 18) {
						break;
					}*/
				}
				
				br.close();
				br = null;
				
				out.close();
				out = null;
				socket.close();
				socket = null;
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void main(String arg[]) {
		start();
	}
}
