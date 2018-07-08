package vn.com.vhc.sts.converter;
/*anhnt@vhc.com.vn*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class AlarmLog3GConverter2 extends STS_BasicConverter {
	
	private static final Logger logger = Logger
			.getLogger(AlarmLogIRSR3GConverter.class.getName());
	
	private int fileId = -1;
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
		if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
			fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
		}
		
		BufferedReader reader = null;
		BufferedWriter writerStartAlarm = null;
		BufferedWriter writerClearingAlarm = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerStartAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarm-3G_"+getFileName(outFile.getName())));
			writerStartAlarm.write("#STARTDAY,BSCID,MO,RSITE,ALARM,SLOGAN,ID\n");
			writerClearingAlarm = new BufferedWriter(new FileWriter(outFile.getParent()+"/ClearingAlarm-3G_"+getFileName(outFile.getName())));
			writerClearingAlarm.write("#ENDDAY,BSCID,MO,RSITE,ALARM,SLOGAN,ID\n");
			String strLine = "";
			boolean blockAlarmClearing = false;
			boolean blockStartAlarm = false;
			boolean blockAlamCeasingContent = false;
			boolean blockAlamCeasingContent1 = false;
			boolean blockAlamCeasingContent2 = false;
			boolean blockAlamCeasingContent3 = false;
			boolean blockAlamCeasingContent4 = false;
			boolean blockAlamStartContent = false;
			boolean blockAlamStartContent1 = false;
			boolean blockAlamStartContent2 = false;
			boolean blockAlamStartContent3 = false;
			boolean blockAlamStartContent4 = false;
			int clearing = 0;
			int start = 0;
			int count = 0;
			while ((strLine = reader.readLine()) != null){
				//Lay du lieu tu khoi (--ALARM FROM--....--ALARM END--)
				if(strLine.contains("ALARM FROM")){
					blockAlarmClearing = false;
					blockStartAlarm = true;
					start = 0;
				}
				if(blockStartAlarm == true){
					if(!strLine.contains("ALARM END")){
						start++;
						startConvertDate(start,strLine,writerStartAlarm);
						
						if(strLine.contains("Specific Problem:") && (strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT") || strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECTS IN TRANSCEIVER GROUP MANUALLY BLOCKED"))){
							blockAlamStartContent = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("DIGITAL PATH QUALITY SUPERVISION")){
							blockAlamStartContent1 = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("AP SCHEDULED BACKUP FAILURE")){
							blockAlamStartContent2 = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")){
							blockAlamStartContent3 = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("DIGITAL PATH FAULT SUPERVISION")){
							blockAlamStartContent4 = true;
							count = 0;
						}
						//doc du lieu alarm "RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT" && "RADIO X-CEIVER ADMINISTRATION MANAGED OBJECTS IN TRANSCEIVER GROUP MANUALLY BLOCKED"
						if(blockAlamStartContent == true){
							if(!strLine.contains("END")){
								count++;
								if(count == 9){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										if(str[i].length() > 0 ){
											j++;
											if(j>0){
												writerStartAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamStartContent = false;
							}
						}
						//doc du lieu alarm "DIGITAL PATH QUALITY SUPERVISION"
						if(blockAlamStartContent1 == true){
							if(!strLine.contains("END")){
								count++;
								System.out.println("count = "+count);
								if(count == 9){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										System.out.println("str["+i+"]= "+str[i]);
										if(str[i].length() > 0 ){
											j++;
											if(j>0 && j<4){
												writerStartAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamStartContent1 = false;
							}
						}
						//doc du lieu alarm "AP SCHEDULED BACKUP FAILURE"
						if(blockAlamStartContent2 == true){
							if(!strLine.contains("END")){
								count++;
								System.out.println("count = "+count);
								if(count == 8){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										System.out.println("str["+i+"]= "+str[i]);
										if(str[i].length() > 0 ){
											j++;
											if(j>0){
												writerStartAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamStartContent2 = false;
							}
						}	
						//doc du lieu "CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION"
						/*if(blockAlamStartContent3 == true){
							if(!strLine.contains("END")){
								count++;
								if(count == 7){
									String[] str = strLine.split(" ");
									String sub = strLine.substring(beginIndex)
									int j = 0;
									int k=0;
									for(int i=0;i< str.length;i++){
										if(str[i].length() > 0){
											j++;
											if(j > 0){
												writerStartAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}
						}*/
						//doc du lieu "DIGITAL PATH FAULT SUPERVISION"
						if(blockAlamStartContent4 == true){
							if(!strLine.contains("END")){
								count++;
								if(count == 8){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										System.out.println("str["+i+"]= "+str[i]);
										if(str[i].length() > 0 ){
											j++;
											if(j==1 || j==2){
												writerStartAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamStartContent4 = false;
							}
						}
						alarmNumber(strLine, writerStartAlarm);
					}else{
						blockStartAlarm = false;
					}
				}
				
				//Lay du lieu tu khoi (--ALARM CLEARING FROM--....--ALARM END--)
				if(strLine.contains("ALARM CLEARING FROM")){
					blockAlarmClearing = true;
					blockStartAlarm = false;
					clearing = 0;
				}
				if(blockAlarmClearing == true){
					if(!strLine.contains("ALARM END")){
						clearing++;
						startConvertDate(clearing,strLine,writerClearingAlarm);
						
						if(strLine.contains("Specific Problem:") && (strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT") || strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECTS IN TRANSCEIVER GROUP MANUALLY BLOCKED"))){
							blockAlamCeasingContent = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("DIGITAL PATH QUALITY SUPERVISION")){
							blockAlamCeasingContent1 = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("AP SCHEDULED BACKUP FAILURE")){
							blockAlamCeasingContent2 = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")){
							blockAlamCeasingContent3 = true;
							count = 0;
						}
						if(strLine.contains("Specific Problem:") && strLine.contains("DIGITAL PATH FAULT SUPERVISION")){
							blockAlamCeasingContent4 = true;
							count = 0;
						}
						//doc du lieu alarm "RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT" && "RADIO X-CEIVER ADMINISTRATION MANAGED OBJECTS IN TRANSCEIVER GROUP MANUALLY BLOCKED"
						if(blockAlamCeasingContent == true){
							if(!strLine.contains("END")){
								count++;
								if(count == 9){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										if(str[i].length() > 0 ){
											j++;
											if(j>0){
												writerClearingAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamCeasingContent = false;
							}
						}
						//doc du lieu alarm "DIGITAL PATH QUALITY SUPERVISION"
						if(blockAlamCeasingContent1 == true){
							if(!strLine.contains("END")){
								count++;
								System.out.println("count = "+count);
								if(count == 9){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										System.out.println("str["+i+"]= "+str[i]);
										if(str[i].length() > 0 ){
											j++;
											if(j>0 && j<4){
												writerClearingAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamCeasingContent1 = false;
							}
						}
						//doc du lieu alarm "AP SCHEDULED BACKUP FAILURE"
						if(blockAlamCeasingContent2 == true){
							if(!strLine.contains("END")){
								count++;
								System.out.println("count = "+count);
								if(count == 8){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										System.out.println("str["+i+"]= "+str[i]);
										if(str[i].length() > 0 ){
											j++;
											if(j>0){
												writerClearingAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamCeasingContent2 = false;
							}
						}	
						//doc du lieu "CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION"
						/*if(blockAlamCeasingContent3 == true){
							if(!strLine.contains("END")){
								count++;
								if(count == 7){
									String[] str = strLine.split(" ");
									String sub = strLine.substring(beginIndex)
									int j = 0;
									int k=0;
									for(int i=0;i< str.length;i++){
										if(str[i].length() > 0){
											j++;
											if(j > 0){
												writerClearingAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}
						}*/
						//doc du lieu "DIGITAL PATH FAULT SUPERVISION"
						if(blockAlamCeasingContent4 == true){
							if(!strLine.contains("END")){
								count++;
								if(count == 8){
									String[] str = strLine.split(" ");
									int j=0;
									for(int i=0; i< str.length; i++){
										System.out.println("str["+i+"]= "+str[i]);
										if(str[i].length() > 0 ){
											j++;
											if(j==1 || j==2){
												writerClearingAlarm.write(str[i].trim()+",");
											}
										}
									}
								}
							}else{
								blockAlamCeasingContent4 = false;
							}
						}
						alarmNumber(strLine, writerClearingAlarm);
					}else{
						blockAlarmClearing = false;
					}
				}
			}
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerStartAlarm.close();
					writerClearingAlarm.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
	//Convert file lan 2
		public void convertFile2(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
			prepareParams(params);
			makeDirectory(direcPath);
			
			// insert record and update status in C_RAW_FILES_MLMN table
			if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
				fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
			}
			
			BufferedReader reader = null;
			BufferedWriter wStartAlarmPathQS = null;
			BufferedWriter wStartAlarmRadioOF = null;
			BufferedWriter wStartAlarmApBF = null;
			BufferedWriter wStartAlarmRadioMB = null;
			BufferedWriter wStartAlarmCellAS = null;
			BufferedWriter wStartAlarmPathFS = null;
			
			try {
				reader = new BufferedReader(new FileReader(file));
				File outFile = new File(direcPath, file.getName());
				wStartAlarmPathQS = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmPathQS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
				wStartAlarmRadioOF = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmRadioOF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
				wStartAlarmApBF = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmApBF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
				wStartAlarmRadioMB = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmRadioMB-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
				wStartAlarmCellAS = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmCellAS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
				wStartAlarmPathFS = new BufferedWriter(new FileWriter(outFile.getParent()+"/StartAlarmPathFS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
				
				wStartAlarmPathQS.write("#StartDay,Bsc,Alarm-Name,Dip,Sesl2,QSV,Alarm-Number\n");
				wStartAlarmRadioOF.write("#StartDay,Bsc,Alarm-Name,Mo,Rsite,ALARM,SLOGAN,Alarm-Number\n");
				wStartAlarmApBF.write("#StartDay,Bsc,Alarm-Name,AP,APNAME,NODE,NODENAME,Alarm-Number\n");
				wStartAlarmRadioMB.write("#StartDay,Bsc,Alarm-Name,Mo,Rsite,Alarm-Number\n");
				wStartAlarmCellAS.write("#StartDay,Bsc,Alarm-Name,CELL,SCTYPE,CHTYPE,CHRATE,SPV,Alarm-Number\n");
				wStartAlarmPathFS.write("#StartDay,Bsc,Alarm-Name,DIP,FAULT,Alarm-Number\n");
				
				String strLine = "";
				while ((strLine = reader.readLine()) != null){
					if(strLine.contains("DIGITAL PATH QUALITY SUPERVISION")){
						wStartAlarmPathQS.write(strLine);
						wStartAlarmPathQS.newLine();
					}
					if(strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT")){
						wStartAlarmRadioOF.write(strLine);
						wStartAlarmRadioOF.newLine();
					}
					if(strLine.contains("AP SCHEDULED BACKUP FAILURE")){
						wStartAlarmApBF.write(strLine);
						wStartAlarmApBF.newLine();
					}
					if(strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECTS IN TRANSCEIVER GROUP MANUALLY BLOCKED")){
						wStartAlarmRadioMB.write(strLine);
						wStartAlarmRadioMB.newLine();
					}
					if(strLine.contains("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")){
						wStartAlarmCellAS.write(strLine);
						wStartAlarmCellAS.newLine();
					}
					if(strLine.contains("DIGITAL PATH FAULT SUPERVISION")){
						wStartAlarmPathFS.write(strLine);
						wStartAlarmPathFS.newLine();
					}
				}
				this.genarateDBRecord("StartAlarmPathQS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_START_PATHQS_3G");
				this.genarateDBRecord("StartAlarmRadioOF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_START_RADIOOF_3G");
				this.genarateDBRecord("StartAlarmApBF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_START_APBF_3G");
				this.genarateDBRecord("StartAlarmRadioMB-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_START_RADIOMB_3G");
				this.genarateDBRecord("StartAlarmCellAS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_START_CELLAS_3G");
				this.genarateDBRecord("StartAlarmPathFS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_START_PATHFS_3G");
				this.updateRecordStatus();
			}catch (Exception e) {
				throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
			}
			finally {
				if (reader != null) {
					try {
						reader.close();
						wStartAlarmPathQS.close();
						wStartAlarmRadioOF.close();
						wStartAlarmApBF.close();
						wStartAlarmRadioMB.close();
						wStartAlarmCellAS.close();
						wStartAlarmPathFS.close();
					} catch (IOException e) {
						logger.warn("Close IO stream failure " + e);
					}
				}
			}

			logger.info("Convert file: " + file.getName() + " success");
		}
		//Convert file lan 2
			public void convertFile3(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
				prepareParams(params);
				makeDirectory(direcPath);
				
				// insert record and update status in C_RAW_FILES_MLMN table
				if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
					fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
				}
				
				BufferedReader reader = null;
				BufferedWriter wCeasingAlarmPathQS = null;
				BufferedWriter wCeasingAlarmRadioOF = null;
				BufferedWriter wCeasingAlarmApBF = null;
				BufferedWriter wCeasingAlarmRadioMB = null;
				BufferedWriter wCeasingAlarmCellAS = null;
				BufferedWriter wCeasingAlarmPathFS = null;
				
				try {
					reader = new BufferedReader(new FileReader(file));
					File outFile = new File(direcPath, file.getName());
					wCeasingAlarmPathQS = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarmPathQS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
					wCeasingAlarmRadioOF = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarmRadioOF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
					wCeasingAlarmApBF = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarmApBF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
					wCeasingAlarmRadioMB = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarmRadioMB-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
					wCeasingAlarmCellAS = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarmCellAS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
					wCeasingAlarmPathFS = new BufferedWriter(new FileWriter(outFile.getParent()+"/CeasingAlarmPathFS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length()))));
					
					wCeasingAlarmPathQS.write("#EndDay,Bsc,Alarm-Name,Dip,Sesl2,QSV,Alarm-Number\n");
					wCeasingAlarmRadioOF.write("#EndDay,Bsc,Alarm-Name,Mo,Rsite,ALARM,SLOGAN,Alarm-Number\n");
					wCeasingAlarmApBF.write("#EndDay,Bsc,Alarm-Name,AP,APNAME,NODE,NODENAME,Alarm-Number\n");
					wCeasingAlarmRadioMB.write("#EndDay,Bsc,Alarm-Name,Mo,Rsite,Alarm-Number\n");
					wCeasingAlarmCellAS.write("#EndDay,Bsc,Alarm-Name,CELL,SCTYPE,CHTYPE,CHRATE,SPV,Alarm-Number\n");
					wCeasingAlarmPathFS.write("#EndDay,Bsc,Alarm-Name,DIP,FAULT,Alarm-Number\n");
					
					String strLine = "";
					while ((strLine = reader.readLine()) != null){
						if(strLine.contains("DIGITAL PATH QUALITY SUPERVISION")){
							wCeasingAlarmPathQS.write(strLine);
							wCeasingAlarmPathQS.newLine();
						}
						if(strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECT FAULT")){
							wCeasingAlarmRadioOF.write(strLine);
							wCeasingAlarmRadioOF.newLine();
						}
						if(strLine.contains("AP SCHEDULED BACKUP FAILURE")){
							wCeasingAlarmApBF.write(strLine);
							wCeasingAlarmApBF.newLine();
						}
						if(strLine.contains("RADIO X-CEIVER ADMINISTRATION MANAGED OBJECTS IN TRANSCEIVER GROUP MANUALLY BLOCKED")){
							wCeasingAlarmRadioMB.write(strLine);
							wCeasingAlarmRadioMB.newLine();
						}
						if(strLine.contains("CELL LOGICAL CHANNEL AVAILABILITY SUPERVISION")){
							wCeasingAlarmCellAS.write(strLine);
							wCeasingAlarmCellAS.newLine();
						}
						if(strLine.contains("DIGITAL PATH FAULT SUPERVISION")){
							wCeasingAlarmPathFS.write(strLine);
							wCeasingAlarmPathFS.newLine();
						}
					}
					this.genarateDBRecord("CeasingAlarmPathQS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CEASING_PATHQS_3G");
					this.genarateDBRecord("CeasingAlarmRadioOF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CEASING_RADIOOF_3G");
					this.genarateDBRecord("CeasingAlarmApBF-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CEASING_APBF_3G");
					this.genarateDBRecord("CeasingAlarmRadioMB-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CEASING_RADIOMB_3G");
					this.genarateDBRecord("CeasingAlarmCellAS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CEASING_CELLAS_3G");
					this.genarateDBRecord("CeasingAlarmPathFS-3G_"+getFileName(outFile.getName().substring(outFile.getName().lastIndexOf("_"), outFile.getName().length())), "R_LOGS_CEASING_PATHFS_3G");
					this.updateRecordStatus();
				}catch (Exception e) {
					throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
				}
				finally {
					if (reader != null) {
						try {
							reader.close();
							wCeasingAlarmPathQS.close();
							wCeasingAlarmRadioOF.close();
							wCeasingAlarmApBF.close();
							wCeasingAlarmRadioMB.close();
							wCeasingAlarmCellAS.close();
							wCeasingAlarmPathFS.close();
						} catch (IOException e) {
							logger.warn("Close IO stream failure " + e);
						}
					}
				}
	
				logger.info("Convert file: " + file.getName() + " success");
			}
	private static void startConvertDate(int clearing,String strLine,BufferedWriter file){
		try{
			if (clearing == 2) {
				String[] str = strLine.split(" ");
				int  j = 0;
				for (int i = 0; i <str.length; i++) {
					if (str[i].length() >0) {
						j++;
						if (j == 3 || j== 4)
							file.write(str[i].trim()+" ");
					}
				}
				file.write(",");
			}
			if (clearing == 3) {
				String[] str = strLine.split(":");
				int j = 0;
				for (int i = 0; i <str.length; i++) {
					if (str[i].length() >0) {
						j ++;
						if (j == 2) {
							String[] strNode = str[i].split(","); 
							file.write(strNode[2].trim().substring(strNode[2].trim().lastIndexOf("=")+1,strNode[2].length())+",");
						}
					}
				}
			}
			if(clearing == 7){
				String[] str = strLine.split(":");
				int j = 0;
				for(int i=0; i< str.length; i++){
					if(str[i].length() > 0){
						j++;
						if(j==2){
							file.write(str[i].trim()+",");
						}
					}
				}
			}
		}catch(Exception e){
		}
	}
	
	private void alarmNumber(String strLine, BufferedWriter file){
		try{
			if(strLine.contains("Alarm Number:")){
				String[] str = strLine.split(":");
				int j = 0;
				for(int i=0; i< str.length; i++){
					if(str[i].length() > 0){
						j++;
						if(j==2){
							file.write(str[i].trim());
						}
					}
				}
				file.newLine();
			}
		}catch(Exception e){
			
		}
	}
	private void updateRecordStatus() throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();
			// update state
			String queryStr = "update /*+ APPEND NOLOGGING */ C_RAW_FILES_MLMN set \n" + "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" + "where FILE_ID = " + fileId;
			st.execute(queryStr);
			st.close();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}
	
	private void genarateDBRecord(String fileName, String tableName) throws SQLException {
		Connection conn = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			Statement st = conn.createStatement();

			String queryStr = "select PATTERN_ID, \n" + "DAY, HOUR, NODE_NAME, SERVER_NODE from C_RAW_FILES_MLMN \n" + "where FILE_ID = " + fileId;
			String patternId = "";
			String day = "";
			String hour = "";
			String nodeName = "";
			String serverNode = "";
			//
			ResultSet rs = st.executeQuery(queryStr);
			while (rs.next()) {
				patternId = rs.getString("PATTERN_ID");
				day = STS_Util.getTime(rs.getDate("DAY"));
				hour = rs.getString("HOUR");
				nodeName = rs.getString("NODE_NAME");
				serverNode = rs.getString("SERVER_NODE");
			}
			if (patternId.length() > 0) {
				queryStr = "insert /*+ NOLOGGING */ into C_RAW_FILES_MLMN(FILE_ID,\n" + "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" + "CONVERT_FLAG, IMPORT_FLAG, \n"
						+ "NODE_NAME, RAW_TABLE, SERVER_NODE) values(SEQ_CRF.NEXTVAL, " + patternId + ", '" + fileName + "', to_date('" + day + "', '"
						+ STS_Setting.DB_TIME_FORMAT + "'), " + hour + ", 1, 0, '" + nodeName + "', '" + tableName + "', '" + serverNode + "')";

				st.execute(queryStr);
			}

			rs.close();
			st.close();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}
}
