package inventory.download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jscape.inet.telnet.TelnetException;
import com.jscape.inet.telnet.TelnetSession;



public class TelnetNokiaTest {
	
	public static void main(String args[]) {
		 String destinationPath = "D:/inventorydata/download/";
		String[] p_ServerName = {"BDNNT1N","BDNLT1N","BVTVT1N","BBDTM1N","BVTBR1N","BDNBH1N"};
		String p_SDate = "2013-10-31";
		String p_STime = "00-00-00";
		String[] p_IP = {"10.22.20.228","10.22.18.228","10.22.32.228","10.22.43.228","10.22.34.228","10.22.16.228"};
		String p_Username = "DDHGMS";
		String p_Password = "VMS6@BIENHOA";
		String neType = "BSC";
		// String p_Alarm_type = "ACTIVE";
		// String p_Command ="\r\n";

		
		//  IN_TelnetClient telnet = new IN_TelnetClient();
		//  telnet.set_DestinationPath(destinationPath);
		 
		Date pDate = new Date();
		DateFormat dfHour = new SimpleDateFormat("HH-mm-ss");
		System.out.println("Bat dau :"+pDate);
		String strHour1 = dfHour.format(pDate);
		//for(int i = 0; i < p_IP.length; i++) telnet.TelnetNSN_BSC(neType, p_ServerName[i], p_SDate, p_IP[i], p_Username, p_Password);

	/*System.out.println("Starting thread 1...");
		
		TelnetThread t1 = new TelnetThread("RNC", "RBDTM1N", "2013-10-31",
				"10.23.57.132", "DDHGMS", "VMS6@BIENHOA");
		t1.start();
		
		
		System.out.println("Starting thread 2...");
		TelnetThread t2 = new TelnetThread("RNC", "RBDTM2N", "2013-10-31",
				"10.23.70.132", "DDHGMS", "VMS6@BIENHOA");
		t2.start();
		
		
		System.out.println("Starting thread 3...");
		TelnetThread t3 = new TelnetThread("RNC", "RBTPT2N", "2013-10-31",
				"10.23.145.132", "DDHGMS", "VMS6@BIENHOA");
		t3.start();
		
		
		
		System.out.println("Starting thread 5...");
		TelnetThread t5 = new TelnetThread("RNC", "RDNBH1N", "2013-10-31",
				"10.23.1.132", "DDHGMS", "VMS6@BIENHOA");
		t5.start();
		
		System.out.println("Starting thread 6...");
		TelnetThread t6 = new TelnetThread("RNC", "RDNBH2N", "2013-10-31",
				"10.23.26.132", "DDHGMS", "VMS6@BIENHOA");
		t6.start();
		
		System.out.println("Starting thread 7...");
		TelnetThread t7 = new TelnetThread("BSC", "BDNNT1N", "2013-10-31",
				"10.22.20.228", "DDHGMS", "VMS6@BIENHOA");
		t7.start();*/
		pDate = new Date();
		//DateFormat dfHour = new SimpleDateFormat("HH-mm-ss");
		System.out.println("Ket thuc :"+pDate);
		ExecutorService executor = Executors.newFixedThreadPool(8);
		 
		Runnable r5 = new TelnetThread("RNC", "RBDTM2N", "2013-10-31",
				"10.23.70.132", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r5);
		
		Runnable r1 = new TelnetThread("BSC", "BDNLT1N", "2013-10-31",
				"10.22.18.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r1);
		
		Runnable r2 = new TelnetThread("BSC", "BDNNT1N", "2013-10-31",
				"10.22.20.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r2);
		
		Runnable r3 = new TelnetThread("BSC", "BVTVT1N", "2013-10-31",
				"10.22.32.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r3);
		
		Runnable r4 = new TelnetThread("BSC", "BVTBR1N", "2013-10-31",
				"10.22.34.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r4);

/*		Runnable r5 = new TelnetThread("BSC", "BDNTB1N", "2013-10-31",
				"10.22.22.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r5);*/
		
		Runnable r6 = new TelnetThread("RNC", "RBDTM1N", "2013-10-31",
				"10.23.57.132", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r6);
		/*Runnable r6 = new TelnetThread("BSC", "BBDTM1N", "2013-10-31",
				"10.22.43.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r6);*/
		
		Runnable r7 = new TelnetThread("BSC", "BBDTA1N", "2013-10-31",
				"10.22.46.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r7);

		Runnable r8 = new TelnetThread("BSC", "BDNBH1N", "2013-10-31",
				"10.22.16.228", "DDHGMS", "VMS6@BIENHOA");
		executor.execute(r8);
		
	}
}