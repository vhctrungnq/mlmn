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
NAME:       IsoTRXEricsson2GConverter
PURPOSE:	Conventer thong tin port&card RNC-SGSN

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        27/11/2013       AnhNT      		1. Raw file format (ERICSSON_TRX).(B.*)-([0-9]{8})-([0-9]{6})
											2. Khi telnet de ten file tho giong dinh dang tren.
											3. Cu phap lenh telnet < 
												Lenh 1 : eaw + ten BSC
												Lenh 2 : rxmop:moty=rxotrx;
******************************************************************************/
public class IsoTRXEricsson2GConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoTRXEricsson2GConverter.class.getName());

	private String sep = ";";
	private String line = "";  
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;   
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DAY;VENDOR;NE_TYPE;NE;MO;CELL;CHGR;TEI;SIG;MCTRI;DCP1;SWVERREPL;SWVERDLD;SWVERACT;DCP2;TRX_INFO\n");			
			String strLine = "";
			//Ngay thang lay du lieu
			Date pDate = new Date(); 
			DateFormat dfDay = new SimpleDateFormat("yyyyMMdd"); 
			String day = dfDay.format(pDate); 
			
			String vendor = "ERICSSON",neType = "BSC";
			//Thong tin Ne
			String ne = "";
			//Thong tin chi tiet
			String trxInfo = "";
			//Thong do dai cua tung cot
			int[] _header1 = new int[6] ; 
			int[] _header2 = new int[3]; 
			//Danh dau la da doc head
			boolean block1 = false;
			boolean block2 = false;
			//Danh so dong du lieu
			int count = 0;
			//Thong tin dcp2
			String dcp2List = ""; 
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() == 0){
					continue;
				}
				strLine = strLine.trim();
				//Lay thong tin Ne
				if(strLine.contains("eaw")){
					ne = strLine.split(" ")[1];
					continue;
				}
				//Doc do dai head
				if(block1 == false){
					if(strLine.contains("MO") && strLine.contains("CELL")){ 
						_header1[0] = strLine.substring(strLine.indexOf("MO"), strLine.indexOf("CELL")).length();
						_header1[1] = _header1[0]+ strLine.substring(strLine.indexOf("CELL"), strLine.indexOf("CHGR")).length();
						_header1[2] = _header1[1]+ strLine.substring(strLine.indexOf("CHGR"), strLine.indexOf("TEI")).length();
						_header1[3] = _header1[2]+ strLine.substring(strLine.indexOf("TEI"), strLine.indexOf("SIG")).length();
						_header1[4] = _header1[3]+ strLine.substring(strLine.indexOf("SIG"), strLine.indexOf("MCTRI")).length();
						_header1[5] = _header1[4]+ strLine.substring(strLine.indexOf("MCTRI"), strLine.indexOf("DCP1")).length();
						block1 = true;
					}  
				} 
				
				//Ghi thong tin doc duoc vao file
				if(strLine.contains("MO") && strLine.contains("CELL")){ 
					if(trxInfo.length() > 0){
						writerFile.write(line+sep+trxInfo);
						writerFile.newLine();
					}
					count = 0;
					line = "";
					trxInfo = ""; 
					dcp2List = "";
				}
				
				//Doc thong tin TRX
				if(block1){
					count++; 
					if(count >= 1){
						trxInfo += "<->"+this.delSpace(strLine);
					} 
					if(count == 2){
						String mo = strLine.substring(0, _header1[0]).trim();
						String cell = strLine.substring(_header1[0], _header1[1]).trim();
						String chgr = strLine.substring(_header1[1], _header1[2]).trim();
						String tel = strLine.substring(_header1[2], _header1[3]).trim();
						String sig = strLine.substring(_header1[3], _header1[4]).trim();
						String mctri = strLine.substring(_header1[4], _header1[5]).trim();
						String dcp1 = strLine.substring(_header1[5]).trim();
						line += day+sep+vendor+sep+neType+sep+ne+sep+mo+sep+cell+sep+chgr+sep+tel+sep+sig+sep+mctri+sep+dcp1+sep; 
					}
					if(block2 == false){
						if(count == 3){ 
							_header2[0] = strLine.substring(strLine.indexOf("SWVERREPL"),strLine.indexOf("SWVERDLD")).length();
							_header2[1] =_header2[0] + strLine.substring(strLine.indexOf("SWVERDLD"),strLine.indexOf("SWVERACT")).length();
							_header2[2] =_header2[1] + strLine.substring(strLine.indexOf("SWVERACT"),strLine.indexOf("DCP2")).length(); 
							block2 = true;
						}
					}
					if(block2){
						if(count == 4){
							String _SWVERREPL = strLine.substring(0, _header2[0]).trim();
							String _SWVERDLD = strLine.substring(_header2[0], _header2[1]).trim();
							String _SWVERACT = strLine.substring(_header2[1], _header2[2]).trim();
							String dcp2 = strLine.substring(_header2[2]).trim();
							dcp2List = dcp2;
							line += _SWVERREPL+sep+_SWVERDLD+sep+_SWVERACT+sep+dcp2;
						}
					}
					
					if(count > 4){
						dcp2List = " "+strLine.trim(); 
						line += dcp2List;
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
	
	//Xoa ky tu space(chi de lai 1)
	private String delSpace(String str){
		while(str.indexOf("  ")>= 0)
		{
			str = str.replace("  ", " ");
		}
		return str;
	}
}
