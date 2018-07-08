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
NAME:       IsoLicenseSWEricssonRNCConverter
PURPOSE:	Convert data license software Ericsson RNC

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        19/10/2013       AnhNT      		1. Raw file format (R_E_RNC_LICENSE_SW).([0-9]{8}).(*)
******************************************************************************/
public class IsoLicenseSWEricssonRNCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoLicenseSWEricssonRNCConverter.class.getName());
	 
	String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter writer = null; 	
		String line = "";
		String day = "";
		String ne = "";
		String vendor = "ERICSSON";
		String neType = "RNC";
		boolean block = false;
		boolean block2 = false;
		int count = 0 ;
		int temp = 0;
		int[] lengthList = new int[9]; 
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			
			// create file writer
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			// writer header
			writer.write("#DAY;NE;RNC_FEATURE;FEATURE_STATUS;FEATURE_CODE;LICENSE_STATE;SERVICE_STATE;FAJ;FEATURE_NAME;START_DATE;STOP_DATE;CHANGE_REASON;VENDOR;NE_TYPE\n");
			
			// Su dung doc du lieu tung dong de su ly, lay thong tin can thiet
			String strLine = "";
			 
			while ((strLine = reader.readLine()) != null){ 
				if(strLine.length() == 0){
					continue;
				}
				temp++;
				
				if(temp == 2){
					ne = strLine.substring(0, strLine.indexOf(">"));
				}
				
				if(temp == 3){
					day = strLine.substring(0, strLine.indexOf(" ")).replace(":", "");
					continue;
				}
				
				if(strLine.contains("RncFeature MO")){
					lengthList = new int[9];
					block =  true;
					block2 = false;
					count = 0;   
					lengthList[0] = strLine.substring(strLine.indexOf("RncFeature MO"), strLine.indexOf("featureState")).length();
					lengthList[1] = strLine.substring(strLine.indexOf("featureState"), strLine.indexOf("keyId")).length();
					lengthList[2] = strLine.substring(strLine.indexOf("keyId"), strLine.indexOf("licenseState")).length();
					lengthList[3] = strLine.substring(strLine.indexOf("licenseState"), strLine.indexOf("serviceState")).length();
					lengthList[4] = strLine.substring(strLine.indexOf("serviceState"), strLine.indexOf("FAJ")).length();
					lengthList[5] = strLine.substring(strLine.indexOf("FAJ"), strLine.indexOf("userLabel")).length();
					continue;
				} 
				
				if(strLine.contains("FeatureId")){
					lengthList = new int[9];
					block =  false;
					block2 = true;
					count = 0;
					lengthList[0] = strLine.substring(strLine.indexOf("FeatureId"), strLine.indexOf("StartDate")).length();
					lengthList[1] = strLine.substring(strLine.indexOf("StartDate"), strLine.indexOf("StopDate")).length();
					lengthList[2] = strLine.substring(strLine.indexOf("StopDate"), strLine.indexOf("Status")).length();
					lengthList[3] = strLine.substring(strLine.indexOf("Status"), strLine.indexOf("ChangeReason")).length();
					lengthList[4] = strLine.substring(strLine.indexOf("ChangeReason"), strLine.indexOf("Description")).length();
					continue;
				}
				
				if(block == true){
					if(strLine.contains("===")){
						count++;
						if(count >= 2){
							block = false; 
						}
						continue;
					}
					String[] str = new String[10];
					try { 
						//RNC_FEATURE
						str[0] = strLine.substring(0, DQ(lengthList,0)).trim();
						//FEATURE_STATUS
						str[1] = strLine.substring(DQ(lengthList,0), DQ(lengthList,1)).split(" ")[0].trim();
						if(str[1].equalsIgnoreCase("Enabled")){
							str[1] = "ON";
						}else{
							str[1] = "OFF";
						}
						//FEATURE_CODE
						str[2] = strLine.substring(DQ(lengthList,1), DQ(lengthList,2)).trim();
						//LICENSE_STATE
						str[3] = strLine.substring(DQ(lengthList,2), DQ(lengthList,3)).split(" ")[0].trim();
						//SERVICE_STATE
						str[4] = strLine.substring(DQ(lengthList,3), DQ(lengthList,4)).split(" ")[0].trim();
						//FAJ
						str[5] = strLine.substring(DQ(lengthList,4), DQ(lengthList,5)).trim();
						//FEATURE_NAME
						str[6] = strLine.substring(DQ(lengthList,5)).trim(); 
					} catch (Exception e) {
						// TODO: handle exception
						logger.warn(e);
					}  
					for(int i = 0; i < str.length; i++){
						line += str[i]+sep;
					} 
					writer.write(day+sep+ne+sep+line.replace("null", "")+vendor+sep+neType);
					writer.newLine();
					line = "";
					continue;
				}
				
				if(block2 == true){ 
					if(strLine.contains("===")){
						count++;
						if(count >= 2){
							block2 = false; 
						}
						continue;
					}
					
					String[] str = new String[10];
					try {
						//FEATURE_CODE
						str[2] = strLine.substring(0, DQ(lengthList,0)).trim();
						//START_DATE
						str[7] = strLine.substring(DQ(lengthList,0), DQ(lengthList,1)).trim();
						//STOP_DATE
						str[8] = strLine.substring(DQ(lengthList,1), DQ(lengthList,2)).trim();
						//FEATURE_STATUS
						str[1] = strLine.substring(DQ(lengthList,2), DQ(lengthList,3)).trim();
						if(str[1].equalsIgnoreCase("Enabled")){
							str[1] = "ON";
						}else{
							str[1] = "OFF";
						}
						//CHANGE_REASON
						str[9] = strLine.substring(DQ(lengthList,3), DQ(lengthList,4)).trim(); 
						//FEATURE_NAME
						str[6] = strLine.substring(DQ(lengthList,4)).trim();  
					} catch (Exception e) {
						// TODO: handle exception
					}
					for(int i = 0; i < str.length; i++){
						line += str[i]+sep;
					}  
					writer.write(day+sep+ne+sep+line.replace("null", "")+vendor+sep+neType);
					writer.newLine();
					line = "";
					continue;
				}
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	} 
	
	public int DQ(int[] in, int index){
		int count = in[index];
		if(index > 0){
			count += DQ(in,index - 1); 
		} 
		return count; 
	}
}
