 package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;



/******************************************************************************
NAME:       InventoryHuaweiCoreCsConverter2
PURPOSE:	Convert file inventory Huawei Core CS

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        14/10/2013       AnhNT      		1. Ten file : MmlTaskResult
******************************************************************************/
public class InventoryHuaweiCoreCsConverter2 extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryHuaweiCoreCsConverter2.class.getName());
	
	private String sep = ";";
//	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	//private DateFormat format1 = new SimpleDateFormat("yyyy-M-d");
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;  
		String ne = "", neType = "", productName = "", day ="", line ="";
		boolean block = false;
		int count = 0;
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;NE_TYPE;NE;SCAN_DATE;OBJ_TYPE;RACK;SUBRACK;SLOT;LOCATION;PRODUCT_CODE;PRODUCT_NAME;SERI_NO;PRODUCT_DATE\n");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){   
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
					if(count > 3){
						if(strLine.trim().contains("Number of results")){
							block = false;
							count = 0;
							continue;
						}
						strLine = strLine.trim();
						strLine = unSpace(strLine);
						String[] strList = strLine.split(" ");
						try { 
							if((strList[0].equalsIgnoreCase("PORT") && strList[5].equalsIgnoreCase("NULL")) 
									|| (strList[0].equalsIgnoreCase("DAUGHTERBOARD") && strList[5].equalsIgnoreCase("NULL"))){ 
								continue;
							}else{
								if(strList[8].equalsIgnoreCase("NULL")){
									strList[8] = "";
								} 
								
								if(strList[5].substring(strList[5].length()-2).equalsIgnoreCase("HW")){
									productName = "SFP";
								}else{
									if(strList[5].contains("-")){
										productName = strList[5].substring(0,strList[5].indexOf("-"));
									}else{
										productName = strList[5].substring(4,8);
									} 
								}
										
								line += "HUAWEI;"+neType+sep+ne+sep+day+sep+strList[0]+sep+strList[1]+sep+strList[2]+sep+strList[3]
										+sep+strList[4]+sep+strList[5]+sep+productName+sep+strList[6]+sep+strList[8];
								writerFile.write(line);
								writerFile.newLine();
								line = "";
								continue;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}  
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
