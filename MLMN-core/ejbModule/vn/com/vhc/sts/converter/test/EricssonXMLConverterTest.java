package vn.com.vhc.sts.converter.test;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import vn.com.vhc.sts.converter.EricssonXMLConverter3G;
import vn.com.vhc.sts.converter.IPBBConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

public class EricssonXMLConverterTest {
	@Test
	public static void testConvertFile() {
		String fileName = "IPBB_903_BDI_2-BDN-Gi0_4_1__ACBDN2C-BDN-Gi6_6 #1_Sat-11-14-2015.xml";
		File file = new File("E:/rawfile/download/" + fileName);
		String destinationPath = "E:/rawfile/converter/";
		if (!file.exists()) {
			System.out.println("File not found !");
			return;
		}
		
		Hashtable<Byte, String> params = new Hashtable<Byte, String>();
		// params.put(Setting.FILE_ID_KEY, "1");

		//EricssonXMLConverter ericssonConverter = new EricssonXMLConverter();
		IPBBConverter ericssonConverter = new IPBBConverter();
		//IPBBConverter ipbb = new IPBBConverter();
		try {
			long startTime = System.currentTimeMillis();
			ericssonConverter.convertFile(file, destinationPath, params);
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime);
			System.out.println("Convert is success !");
		} catch (STS_ConvertException e) {
			e.printStackTrace();
		}
		
		
	}
	public static void main(String args[]){
		testConvertFile();
		//String str = getSimpleText("pmNoRabEstAttemptPsIntNonHs,pmNoRabEstSuccessPsIntNonHs");
		//System.out.println(str);
		
		
	}
	/*------------------------------------------*/
	private final static String[] ORIGINAL = { "Active", "Attempt", "Connect", "Establish", "Idle", "Incoming", "Interactive", "Normal", "Outgoing", "Packet",
			"Release", "Samples", "Sharing", "Source", "Speech", "Stream", "Success", "Switch", "System", "Throughput", "Traffic", "Volume", "Return",
			"Rejected", "Failed", "Target", "Resel", "Order", "pm" };
	private final static String[] REPLAMENT = { "Act", "Att", "Conn", "Estlsh", "Idl", "Incm", "Intact", "Norm", "Outg", "Pkt", "Rels", "Sampl", "Shar", "Src",
			"Spch", "Strm", "Succ", "Swt", "Sys", "Thput", "Traf", "Vol", "Rtrn", "Rej", "Fail", "Trg", "Re", "Ord", "" };

	private static String getSimpleText(String text) {
		String sh = "";
		String elms[] = text.split(",");
		if (elms.length > 0) {
			for (String col : elms) {
				for (int i = 0; i < ORIGINAL.length; i++) {
					if (col.indexOf(ORIGINAL[i]) >= 0) {
						col = col.replace(ORIGINAL[i], REPLAMENT[i]);
					}
				}
				if (sh.length() > 0) {
					sh += ",";
				}
				sh += col;
			}
		}
		return sh;
	}
}
