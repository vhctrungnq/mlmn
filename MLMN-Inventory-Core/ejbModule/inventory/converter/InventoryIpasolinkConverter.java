package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;
import inventory.utils.IN_DateTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Hashtable; 

import org.apache.log4j.Logger;


/******************************************************************************
NAME:       InventoryIpasolinkConverter
PURPOSE:	Convert file inventory ipasolink

REVISIONS:

Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        02/01/2014       AnhNT      		1. Raw file format .csv (yyyyMMdd_ipasolink.csv)
******************************************************************************/
public class InventoryIpasolinkConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryIpasolinkConverter.class.getName());
	 
	private String sep = ";";
	private String columnL="", columnT="", columnR="", columnJ="", columnBI="", columnAX;
	private String day= "",ipAddress="",ne="",neType ="", locationName="",productName="",productCode="",serialNumber="",activeDate="", slot="", description="";
	/*Danh dau dau khoi du lieu inventory tra ve*/
	boolean block = false;
	/*Dem so ban ghi chua trong mot khoi*/
	int count = 0;
	/*Chua du lieu ban ghi 1(doi voi Ipaso) hoac Chua thong tin header(doi voi Neo,V4)*/
	String[] str1 = null;
	/*Chua du lieu ban ghi 2(doi voi Ipaso) hoac Chua thong tin data(doi voi Neo,V4)*/
	String[] str2 = null;
	/*Chua du lieu ban ghi 3*/
	String[] str3 = null;
	
	// Su dung doc du lieu tung dong de su ly, lay thong tin can thiet
	String strLine = "";
				
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter writer = null; 	 
		
		boolean blockIpasolink = false;
		boolean blockNeo = false;
		boolean blockV4 = false;
		//Dem so ban ghi trong file
		int countLine = 0;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			// create file writer
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));  
			writer.write("#DAY;IP_ADDRESS;NE_TYPE;NE;LOCATION_NAME;PRODUCT_NAME;PRODUCT_CODE;SERIAL_NUMBER;ACTIVE_DATE;SLOT;DESCRIPTION");
			
			while ((strLine = reader.readLine()) != null){
				//Bo qua ban ghi khong chua du lieu
				if(strLine.length() < 1){
					continue;
				}
				countLine++;
				if(countLine == 1){
					//Kiem tra du lieu thuoc ipasolink, neo hay v4
					String str = strLine.split(",")[3].trim().toUpperCase();
					
					if(str.contains("IPASOLINK")){
						blockIpasolink = true;
						blockNeo = false;
						blockV4 = false;
					}else if(str.contains("PASOLINK V4")){
						blockIpasolink = false;
						blockNeo = false;
						blockV4 = true;
					}else if(str.contains("PASOLINK NEO")){
						blockIpasolink = false;
						blockNeo = true;
						blockV4 = false;
					}
				}
				
				if(blockIpasolink){
					this.ipasolink(writer);
				}else if(blockNeo){
					this.ipasoNeo(writer);
				}else if(blockV4){
					this.ipasoV4(writer);
				}
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
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
	
	private void ipasolink( BufferedWriter writer){
		/*Du lieu duoc chia thanh tung khoi 
		 * Bat dau moi khoi la ban ghi co chua thong tin ngay thang
		 * Moi khoi gom 3 ban ghi
		 * Ban ghi 1 : chua thong tin DAY;IP_ADDRESS;NE;LOCATION_NAME;PRODUCT_NAME
		 * Ban ghi 2 : thong tin header
		 * Ban ghi 3 : Du lieu tuong ung voi header cua ban ghi 2
		 * */ 
		try {
			if(count == 0 ){
				str1 = null;
				str1 = strLine.split(",");
				if(IN_DateTools.isValid("yyyy/MM/dd", str1[0])){
					block = true; 
				} 
			}
			
			if(block){
				count ++;  
				if(count == 1){
					try {
						day = str1[0].trim();
						ipAddress = str1[1].trim();
						ne = str1[2].trim();
						neType = str1[3].trim();
						locationName = str1[6].trim();
						productName = str1[7].trim();
					} catch (Exception e) {
						// TODO: handle exception
					} 
				}
				
				if(count == 2){
					str2 = null;
					str2 = strLine.split(",");
				}
				
				if(count == 3){
					str3 = null;
					str3 = strLine.split(",");
					
					if(str2[0].equals("Name") && str2[1].equals("Serial No.") && str2[2].equals("Manufactured Date")){ 
						productCode = str3[0].trim().replace("*", "");
						serialNumber = str3[1].trim().replace("*", "");
						activeDate = str3[2].trim().replace("*", "");
					}else{
						for(int i=0;i<str2.length;i++){
							if(str2[i].contains("Name")){
								productName = str3[i].trim().replace("*", "");
								continue;
							}else if(str2[i].contains("Code No.")){
								productCode = str3[i].trim().replace("*", "");
								continue;
							}else if(str2[i].contains("Serial No.")){
								serialNumber = str3[i].trim().replace("*", "");
								continue;
							}else if(str2[i].contains("Manufactured Date")){
								activeDate = str3[i].trim().replace("*", "");
								break;
							}
						}
					}
					
					String line = day+sep+ipAddress+sep+neType+sep+ne+sep+locationName+sep+productName+sep+
							productCode+sep+serialNumber+sep+activeDate+sep+slot+sep+description;
					writer.newLine();
					writer.write(line);
					line="";count=0;block=false;
					this.refresh();
				}
			}
		}catch(Exception e){
			
		}
	}

	private void ipasoNeo(BufferedWriter writer){
		/*Du lieu duoc phan tich tu dong 7 den dong 9
		 * Du lieu product code va serial duoc ghi tren cung mot ban ghi,
		 * can kiem tra ra map dung gia tri product code va serial tuong ung,
		 * moi mot cap gia tri (product code, serial) duoc insert = 1 ban ghi trong DB
		 * */ 
		try {
			count++;
			if(count == 7){
				String[] str = strLine.split(",");
				
				day = str[0].trim();
				ipAddress = str[1].trim();
				ne =  str[2].trim();
				neType = str[3].trim();
				locationName = str[str.length - 1].trim();
			}else if(count == 8){
				str1 = null;
				str1 = strLine.split(",");
			}else if(count == 9){
				str2 = strLine.split(",");
				
				/*
				 * Lay thong tin product code, serial number
				 * i duyet tu dau den cuoi xau
				 * tuong ung voi moi i. j duyet tu cuoi xau den vi tri i
				 * neu replace(ten header tai vi tri i) = ten header tai vi tri k
				 * ta lay duoc cap gia tri code va serial tuong ung
				 * */
				for(int i = 0; i < str1.length; i++){
					for(int j = str1.length - 1; j > i; j--){
						if(str1[i].contains("CODE No.")){
							if(str1[i].trim().replace("CODE", "SERIAL").equals(str1[j].trim())){
								try {
									productName = str1[i].trim();
									productCode = str2[i].trim().replace("*", "");
									serialNumber = str2[j].trim().replace("*", "");
								} catch (Exception e) {
									// TODO: handle exception
								}
								String line = day+sep+ipAddress+sep+neType+sep+ne+sep+locationName+sep
													+productName+sep+productCode+sep+serialNumber+sep+activeDate+sep+slot+sep+description;
								writer.newLine();
								writer.write(line);
								productCode = "";serialNumber="";productName="";
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
	}
	
	private void ipasoV4(BufferedWriter writer){
		/*Du lieu duoc phan tich tu dong 10 den dong 12
		 * Du lieu product code va serial duoc ghi tren cung mot ban ghi,
		 * can kiem tra ra map dung gia tri product code va serial tuong ung,
		 * moi mot cap gia tri (product code, serial) duoc insert = 1 ban ghi trong DB
		 * */ 
		try {
			count++;
			if(count == 5){
				 
				String[] str = strLine.split(",");
				columnAX = str[49].trim();
			}
			if(count == 6){
				String[] str = strLine.split(",");
				columnAX = columnAX + "=" + str[49].trim();
			}
			if(count == 10){
				String[] str = strLine.split(",");
				
				day = str[0].trim();
				ipAddress = str[1].trim();
				ne = str[2].trim();
				neType = str[3].trim();
				locationName = str[str.length - 1].trim();
			}else if(count == 11){
				str1 = strLine.split(",");
			}else if(count == 12){
				str2 = strLine.split(",");
			}
			else if(count == 17){ 
				String[] str = strLine.split(",");
				columnL = str[11];
				columnT = str[19];
				columnR = str[17];
				columnJ = str[9]; 
				columnBI = str[60];
			}
			else if(count == 18){
					/*
				 * Lay thong tin product code, serial number
				 * i duyet tu dau den cuoi xau
				 * tuong ung voi moi i. j duyet tu cuoi xau den vi tri i
				 * neu replace(ten header tai vi tri i) = ten header tai vi tri k
				 * ta lay duoc cap gia tri code va serial tuong ung
				 * */
				for(int i = 0; i < str1.length; i++){ 
					if(str1[i].equals("PMC SOFTWARE VERSION")){
						continue;
					}
					for(int j = str1.length - 1; j > i; j--){
						if(str1[i].equals("PMC CODE No.")){
							if(str1[i].trim().replace("CODE", "SERIAL").equals(str1[j].trim())){
								try {
									productName = str1[i].trim().substring(0, str1[i].indexOf(" "));
									productCode = str2[i].trim().replace("*", "");
									serialNumber = str2[j].trim().replace("*", "");
								} catch (Exception e) {
									// TODO: handle exception
								}
								String[] str = strLine.split(",");
								description = columnAX + "," + columnL + "=" + str[11] + "," + columnT + "=" + str[19] + "," + columnR + "=" + str[17] + "," + columnJ +"="+str[9]+ "," + columnBI +"="+str[60]; 
								slot = subString(str[11], null, "[GHz]");
								String line = day+sep+ipAddress+sep+neType+sep+ne+sep+locationName+sep
													+productName+sep+productCode+sep+serialNumber+sep+activeDate+sep+slot+sep+description;
								writer.newLine();
								writer.write(line);
								productCode = "";serialNumber="";productName="";
							}
						}else if(str1[i].contains("SOFTWARE VERSION")){
							if(str1[i].trim().replace("SOFTWARE VERSION", "SERIAL No.").equals(str1[j].trim())){
								try {
									productName = str1[i].trim().substring(0, str1[i].indexOf(" "));
									productCode = str2[i].trim().replace("*", "");
									serialNumber = str2[j].trim().replace("*", "");
								} catch (Exception e) {
									// TODO: handle exception
								}
								String[] str = strLine.split(",");
								description = columnAX + "," + columnL + "=" + str[11] + "," + columnT + "=" + str[19] + "," + columnR + "=" + str[17] + "," + columnJ +"="+str[9]+ "," + columnBI +"="+str[60]; 
								slot = subString(str[11], null, "[GHz]");
								String line = day+sep+ipAddress+sep+neType+sep+ne+sep+locationName+sep
													+productName+sep+productCode+sep+serialNumber+sep+activeDate+sep+slot+sep+description;
								writer.newLine();
								writer.write(line);
								productCode = "";serialNumber="";productName="";
							}
						}else{
							break;
						}
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception 
		} 
	}

	private void refresh(){
		day= "";
		ipAddress="";
		ne="";neType ="";
		locationName="";
		productName="";
		productCode="";
		serialNumber="";
		activeDate=""; 
		description="";
		slot="";
	}
	private String subString(String str, String charOld, String charNew){
		if (charOld == null)
			str = str.substring(0, str.indexOf(charNew));
		else
			str = str.substring(str.indexOf(charOld)+charOld.length(), str.indexOf(charNew));
		return str;
	}
}