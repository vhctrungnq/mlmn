package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;


/******************************************************************************
NAME:       IsoPortBSCSGSNConverter
PURPOSE:	Conventer thong tin port&card BSC-SGSN

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        16/09/2013       AnhNT      		1. Raw file format (R_BSC_SGSN_PORT).([0-9]{8})
											2. Khi telnet de ten file tho giong dinh dang tren.
											3. Cu phap lenh telnet: < ZFWO:PAPU=0&&16;
******************************************************************************/
public class IsoPortSGSNBSCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoPortSGSNBSCConverter.class.getName());

	String sep = ";";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		int blockNum = 0;
		boolean blockPapu = false;
		String bsc = "", papu = "",localIp = ""; 
		String sgsn = "";
		String day = "";
		String line = "";  
		int count= 0;
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DAY;SGSN;VENDOR;NE_TYPE;NE;PAPU;NS_VC_ID;NAME;ADM_STA;OP_STATE;LOCAL_UDP_P;RDW;RSW;RPNBR;REMOTE_IP_ENDPOIT;LOCAL_IP_ADDR;LDW;LSW;REM_UDP_P\n");			
			String strLine = "";
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() == 0){
					continue;
				}
				String temp = "";
				try{
					temp = strLine.substring(0, 4);
				}catch(Exception e){}
				if(temp.equals("SGSN")){
					while (strLine.indexOf("   ") > 0) {
						strLine = strLine.replace("   ", "  ");
					}
					String[] str = strLine.split("  ");
					sgsn = str[1].trim();
					day = str[2].trim()+ " "+str[3].trim(); 
					line += day+sep+sgsn;
					continue;
				}  
				
				if(strLine.contains("NSEI-")){
					blockPapu = false;
					while(strLine.indexOf("  ") > 0){
						strLine = strLine.replace("  ", " ");
					}
					String[] str = strLine.split(" ");
					if(str.length == 3){
						count = 0 ;
						bsc = str[0];
						papu = str[1].substring(5, str[1].length());
						//System.out.println("Papu1 = "+papu);
						blockNum = 1;
					}else{
						count = 0;
						bsc = str[0]+str[1];
						blockNum = 2;
					}
					continue;
				}
				
				if(blockNum == 1){
					count ++;
					//ghi du lieu vao file
					if(count == 4){ 
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						String[] str = strLine.split(" ");
						String data = "";
						for(int i = 0 ; i < str.length ; i++){
							data += str[i]+sep;
						} 
						
						writerFile.write(line+";NOKIA SIEMENS;"+"BSC"+sep+bsc+sep+Integer.parseInt(papu)+sep+data+";;;");
						writerFile.newLine();
					}
				}else if(blockNum == 2){
					if(strLine.contains("PAPU-")){
						count = 0;
						papu = strLine.substring(5, strLine.indexOf(" "));
						//System.out.println("Papu2 = "+papu);
						localIp = strLine.substring(strLine.lastIndexOf("-")+1, strLine.length());	
						blockPapu = true;
						continue;
					}
					if(blockPapu == true){
						count ++;
						try{
							if(count >=4 ){
								strLine = strLine.trim();
								while(strLine.indexOf("  ") > 0){
									strLine = strLine.replace("  ", " ");
								}
								String[] str = strLine.split(" "); 	 						
								writerFile.write(line+";NOKIA SIEMENS;"+"BSC"+sep+bsc+sep+Integer.parseInt(papu)+sep+";;;"+str[1]+";;"+str[5]+";"+str[6]+";;"+str[0]+";"+localIp+";"+str[2]+";"+str[3]+";"+str[4]);
								writerFile.newLine();
							}
						}catch(Exception ex){
							logger.warn("Try catch" +ex);
							//System.out.println(strLine);
						} 
					}
				}
			}  
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close(); 
					writerFile.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		} 
	}  
}
