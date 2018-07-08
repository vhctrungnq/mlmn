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
NAME:       InventoryHuaweiOsnConverter
PURPOSE:	Convert file inventory Huawei Core CS

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        03/09/2014       ThangPX      		1. Ten file : OSN_Inventory_date
******************************************************************************/
public class InventoryHuaweiOsnConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryHuaweiOsnConverter.class.getName());
	
	private String sep = ";"; 
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;  
		String vendor = "HUAWEI";
		String neName = "", neType = "OSN", slot = "", softWare_vs="", productName = "",seriNo="", productCode = "", day ="", line ="", productDate="";
		boolean block = false;
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;SCAN_DATE;NE_TYPE;NE_NAME;SLOT;PRODUCT_CODE;PRODUCT_NAME;SERI_NO;PRODUCT_DATE;SOFTWARE_VESION\n");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){   
				if(strLine.trim().length() == 0){
					continue;
				}
				
				if(strLine.contains("Save Time")){ 
					productDate = subStringbyKey(strLine,": ",",");
					day = subStringbyKey(productDate,null," ");
				  
			    }
				if(strLine.contains("Board Name"))
					block = true;
				if(block == true)
				{ 
					String[] arrayTmp = strLine.split(","); 
					if(!arrayTmp[14].equals("-") && !arrayTmp[14].equals("Board Bar Code"))
					{
						productName = subStringbyKey(arrayTmp[0],"-",null);
						productCode = arrayTmp[1];
						seriNo = arrayTmp[14];
						slot = arrayTmp[5];
						if(!arrayTmp[7].equals("/"))
							softWare_vs = arrayTmp[7];
						neName = arrayTmp[2];
						line = vendor + sep + day + sep + neType + sep + neName +sep + slot + sep + productCode + sep + productName + sep + seriNo + sep + productDate + sep + softWare_vs;
						writerFile.write(line);
						writerFile.newLine(); 
						line ="";
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
	
	 
	
	private String subStringbyKey(String strLine, String startKey, String endKey)
	{
		String strDest = "";
		if(startKey== null)
			strDest  = strLine.substring(0,strLine.indexOf(endKey));
		else if(endKey == null)
			strDest  = strLine.substring(strLine.indexOf(startKey)+startKey.length(),strLine.length());
		else
			strDest  = strLine.substring(strLine.indexOf(startKey)+startKey.length(),strLine.indexOf(endKey));
		return strDest;
	} 
}
