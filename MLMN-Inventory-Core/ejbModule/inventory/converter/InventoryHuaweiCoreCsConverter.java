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
NAME:       InventoryHuaweiCoreCsConverter
PURPOSE:	Convert file inventory Huawei Core CS

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        14/10/2013       AnhNT      		1. Ten file : MmlTaskResult
		   17/12/2013       AnhNT      		Sua phan tich product name
******************************************************************************/
public class InventoryHuaweiCoreCsConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryHuaweiCoreCsConverter.class.getName());
	
	private String sep = ";"; 
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;  
		String vendor = "HUAWEI";
		String ne = "", neType = "", productName = "", day ="", line ="";
		boolean block = false;
		int count = 0;
		int[] lengthArr = new int[8];
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;NE_TYPE;NE;SCAN_DATE;OBJ_TYPE;RACK;SUBRACK;SLOT;LOCATION;PRODUCT_CODE;PRODUCT_NAME;SERI_NO;PRODUCT_DATE\n");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){   
				if(strLine.trim().length() == 0){
					continue;
				}
				
				if(strLine.contains("+++")){ 
					int temp = seach(strLine);
					if(temp == -1){
						strLine = strLine.trim();
						strLine = unSpace(strLine);
						String[] str = strLine.split(" ");
						ne = str[1];
						day = str[2];
					}else{
						ne = strLine.substring(temp+1, temp+8);
						day = strLine.substring(strLine.lastIndexOf("  ")+2,strLine.lastIndexOf(" "));
					} 
					
					if(ne.substring(0, 2).equals("MG")){
						neType = "MGW";
					}else{
						neType = "MSC";
					}
				} 
				
				if(strLine.trim().equalsIgnoreCase("Electronic Elabel Information") 
						|| strLine.trim().equalsIgnoreCase("Electronic Label")){
					block = true;
					count = 0;
					continue;
				}
				
				if(block == true){
					count ++;
					try {
						if(count == 2){
							lengthArr = new int[8];
							strLine = strLine.toUpperCase(); 
							lengthArr[0] = strLine.substring(0, strLine.indexOf("RACK")).length();
							lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("RACK"), strLine.indexOf("SUBRACK")).length();
							lengthArr[2] = lengthArr[1] + strLine.substring(strLine.indexOf("SUBRACK"), strLine.indexOf("SLOT")).length();
							lengthArr[3] = lengthArr[2] + strLine.substring(strLine.indexOf("SLOT"), strLine.indexOf("LOCATION")).length();
							lengthArr[4] = lengthArr[3] + strLine.substring(strLine.indexOf("LOCATION"), strLine.indexOf("BOARD")).length();
							lengthArr[5] = lengthArr[4] + strLine.substring(strLine.indexOf("BOARD"), strLine.indexOf("BAR")).length();
							lengthArr[6] = lengthArr[5] + strLine.substring(strLine.indexOf("BAR"), strLine.indexOf("ITEM")).length();
							lengthArr[7] = lengthArr[6] + strLine.substring(strLine.indexOf("ITEM"), strLine.indexOf("MANUFACTURED")).length();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					try {
						if(count >= 3){
							if(strLine.trim().contains("Number of results")){
								block = false;
								count = 0;
								continue;
							}else{
								String _ObjectType = strLine.substring(0, lengthArr[0]).trim();
								String _Rack = strLine.substring(lengthArr[0], lengthArr[1]).trim();
								String _SubRack = strLine.substring(lengthArr[1], lengthArr[2]).trim();
								String _Slot = strLine.substring(lengthArr[2], lengthArr[3]).trim();
								String _Location = strLine.substring(lengthArr[3], lengthArr[4]).trim();
								String _BoardType = strLine.substring(lengthArr[4], lengthArr[5]).trim();
								String _BarCode = strLine.substring(lengthArr[5], lengthArr[6]).trim();
								//String _Item = strLine.substring(lengthArr[6], lengthArr[7]).trim();
								String _Manufactured = strLine.substring(lengthArr[7]).trim();
								_ObjectType = _ObjectType.toUpperCase();
								
								if((_ObjectType.equalsIgnoreCase("PORT") && _BoardType.equalsIgnoreCase("NULL")) 
										|| (_ObjectType.equalsIgnoreCase("DAUGHTERBOARD") && _BoardType.equalsIgnoreCase("NULL"))){ 
									continue;
								}else{
									if(_Manufactured.equalsIgnoreCase("NULL")){
										_Manufactured = "";
									}
									
									if(_BoardType.substring(_BoardType.length()-2).equalsIgnoreCase("HW")){
										productName = "SFP";
									}else{
										if(_BoardType.contains("-")){
											productName = _BoardType.substring(0,_BoardType.indexOf("-"));
										}else{
											productName = _BoardType.substring(4,8);
										} 
									}
											
									line += vendor+sep+neType+sep+ne+sep+day+sep+_ObjectType+sep+_Rack+sep+_SubRack+sep+_Slot
											+sep+_Location+sep+_BoardType+sep+productName+sep+_BarCode+sep+_Manufactured;
									writerFile.write(line);
									writerFile.newLine();
									line = "";
									productName = "";
									continue;
								} 
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}  
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
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
	
	// Tim kiem ky tu danh dau su bat dau cua ten MSC
	private int seach(String strLine) {
		int count = -1;
		for ( int i = 0; i < strLine.length(); ++i ){
			char c = strLine.charAt(i);
			int j = (int) c;
			if(j == 7){
				count = i;
				break;
			} 
		} 
		return count;
	}
	
	// Xoa ky tu space
	private String unSpace(String strLine){
		while(strLine.indexOf("  ") > 0){
			strLine = strLine.replace("  ", " ");
		}
		return strLine;
	} 
}
