package controller;
 

import inventory.utils.IN_DateTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.RAlarmLogTemp;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import com.csvreader.CsvReader;
import com.google.gson.Gson;

import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.RAlarmLogDAO;
import dao.RAlarmLogTempDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;

@Controller
public class ImportFileConvertController  extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	
	@Autowired
	private RAlarmLogTempDAO rAlarmLogTempDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DateFormat df_full2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	private DateFormat df_full3 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");  
	
	private AlarmEricssonConverter _AlarmEricssonConverter = new AlarmEricssonConverter();
	private AlarmNokia2Converter _AlarmNokia2Converter =  new AlarmNokia2Converter();
	private String sep=">>";
	/*Import va convert file
	 * @param function: history/active
	 */
	
	@RequestMapping(value = "importAlarm", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = true) String function,@RequestParam(required = false) String netWork,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(function,null,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}

		model.addAttribute("function", function);
		model.addAttribute("netWork", netWork);
		return "jspalarm/GSHeThong/importAlarm";
	}
	
		@RequestMapping(value = "importAlarm", method = RequestMethod.POST)
		public String list(
				@RequestParam("filePath") MultipartFile filePath,
				@RequestParam(required = true) String function,
				@RequestParam(required = false) String netWork,
				Model model, HttpServletRequest request) throws ParseException, IOException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(function,null,"UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			model.addAttribute("function", function);
			model.addAttribute("netWork", netWork);
			List<RAlarmLogTemp> fallList = new ArrayList<RAlarmLogTemp>();
			List<RAlarmLogTemp> successist = new ArrayList<RAlarmLogTemp>();
			//*Kiem tra dinh dang file 
			String vendor="";
			InputStream file;
			//800000000
			System.out.println("filePath.getSize(): "+filePath.getSize());
			if (!filePath.isEmpty()&&filePath.getSize()<=800000000) {

				String[] ten= filePath.getOriginalFilename().split("\\.");
				String fileExtn = ten[ten.length-1];
				String filename=ten[0];
				file = filePath.getInputStream();
				System.out.println("Duoi file: "+fileExtn);
				System.out.println("Ten file: "+filename);
				int countRecordN = 0;
				//System.out.println("Ten file getOriginalFilename: "+filePath.toString());
				if ((fileExtn.equalsIgnoreCase("xls")||fileExtn.equalsIgnoreCase("txt")||fileExtn.equalsIgnoreCase("csv"))&&(filename.toUpperCase().contains("ALU")||filename.toUpperCase().contains("NSN")||filename.toUpperCase().contains("HUAWEI")||filename.toUpperCase().contains("ERICSSON"))) {
					if (!fileExtn.equalsIgnoreCase("csv"))
					{
						if (filename.toUpperCase().contains("ALU"))
						{
							vendor = "ALCATEL";
							//GOI DEN HAM CONVERT DU LIEU CHO FILE ALCATEL
							fallList = convertFileAlcatel(file,username,vendor,model,request);
						}
						if (filename.toUpperCase().contains("NSN"))
						{
							vendor = "NOKIA SIEMENS"; 
							fallList = convertFileNokia(file, username,request); 
							
						}
						if (filename.toUpperCase().contains("HUAWEI")||filename.toUpperCase().contains("CORE"))
						{
							vendor = "HUAWEI";
							//GOI DEN HAM CONVERT DU LIEU CHO FILE ALCATEL
							fallList = convertFileHuawei(file,filename,username,vendor,model,request);
						}
						if (filename.toUpperCase().contains("ERICSSON"))
						{
							vendor = "ERICSSON";
							//List<RAlarmLogTemp> _AlarmLogList = _AlarmEricssonConverter.convertFile(file, username);
							fallList = convertEricsson(file, username,request);
							//countRecordN = _AlarmLogList.size();
						}
					}
					else
					{
						if (filename.toUpperCase().contains("HUAWEI"))
						{
							vendor = "HUAWEI";
							//GOI DEN HAM CONVERT DU LIEU CHO FILE ALCATEL
							fallList = convertFileHuaweiCSV(file, username,request);
						}
						if (filename.toUpperCase().contains("ALU"))
						{
							vendor = "ALCATEL";
							//GOI DEN HAM CONVERT DU LIEU CHO FILE ALCATEL
							fallList = convertFileALUCSV(file,username,vendor,request);
						}
					}
					model.addAttribute("vendor", vendor);
					model.addAttribute("function", function);
					model.addAttribute("netWork", netWork);
					
					//* Xu ly dong bo du lieu history:
					//- Select ra danh sach alarm trong bang temp
					//- Kiem tra record da ton tai trong r_alarm_log_active chua? Neu chua thi insert, 
					//-neu da ton tai va co su sai khac thi cap nhat: cap nhat lai trang thai da thay doi.
					//-Nguoc lai khong lam gi: cap nhat trang thai khong lam gi
					//* Xu ly dong bo du lieu active:
					//- Select ra danh sach alarm active trong khoang thoi gian sdate< maxsdate trong temp va edate null.
					//- Kiem tra record da ton tai trong r_alarm_log_tenp chua? Neu ton tai thi cap nhat edate = sate + 10 phut. cap nhat trang thai da thay doi trong tep.
					//-Nguoc lai khong lam gi: cap nhat trang thai khong lam gi
					try
					{
						rAlarmLogTempDAO.insertActiveToRAlarmLog(username,vendor,function);
					}
					catch(Exception exp)
					{
						saveMessageKey(request, "import.importFall");
					}
					
					//Lay thong tin mindate, maxdate, so dong insert trong alarmlogtemp
					RAlarmLogTemp information = rAlarmLogTempDAO.getInformaionInsert(vendor,username);
					if (information!=null)
					{
						Integer total = 0;
						
						 total = fallList.size()+ information.getQuality();
					
						 if (information.getMinDate()!=null)
							{
								model.addAttribute("fromDate", "Thời gian từ: "+df_full.format(information.getMinDate()));
							}
							if (information.getMaxDate()!=null)
							{
								model.addAttribute("toDate", "Đến: "+df_full.format(information.getMaxDate()));
							}
						model.addAttribute("countSuccess","Import thành công: "+ information.getQuality()+"/"+total);
					}
					//do du lieu ra grid 2G,3G
					List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ALARM_LOG_TEMP");
					//Lay du lieu cac cot an hien cua grid 
					List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ALARM_LOG_TEMP");
					
					List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKeyAndKPI("ALARM_LOG_TEMP","statusUpdate");
					String hightlight = HelpTableConfigs.highLightCol(highlightConfigListCol,"gridSuccess");
					// grid success
					String urlS = "dataSuccessList.htm?vendor="+vendor+"&username="+username;
					String gridSuccess = HelpTableConfigs.getGridPagingUrl(configList, "gridSuccess", urlS, "listSuccess", listSource, "menuSuccess", hightlight, null,null);
					model.addAttribute("gridSuccess", gridSuccess);
					
					// grid alarm don't have at file
					/*String urlD = "dataDontExit.htm?vendor="+vendor+"&username="+username;
					String hightlight2 = HelpTableConfigs.highLightCol(highlightConfigListCol,"gridDExit");
					String gridDExit = HelpTableConfigs.getGridPagingUrl(configList, "gridDExit", urlD, "listSuccesD", listSource, "menuD", hightlight2, null,null);
					model.addAttribute("gridDExit", gridDExit);
					*/
					
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileNameS = "AlarmSuccess_" + dateNow;
					model.addAttribute("exportFileNameS", exportFileNameS);
					
				}
				else {
					 saveMessageKey(request, "cautruc.typeFIle");
				}
			}
			else {
				saveMessageKey(request, "cautruc.emptyFile");
			}
			return "jspalarm/GSHeThong/importAlarm";
			
		}
		// Lay danh sach canh bao da convert thanh cong
		@RequestMapping("dataSuccessList")
		public @ResponseBody 
		void dataSuccessList(@RequestParam(required = false) String vendor,
				@RequestParam(required = false) String username,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			System.out.println("vendor:       "+vendor);
			System.out.println("username:       "+username);
			
			List<RAlarmLogTemp> dataList = new ArrayList<RAlarmLogTemp>();
			dataList = rAlarmLogTempDAO.getAlarmLogTemp(vendor.trim(),username.trim(),"ALL");
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
			
		 }
		
		//Lay danh sach canh bao co trong database nhung khong ton tai trong file
		@RequestMapping("dataDontExit")
		public @ResponseBody 
		void dataDontExit(@RequestParam(required = false) String vendor,
				@RequestParam(required = false) String username,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			System.out.println("vendor:       "+vendor);
			System.out.println("username:       "+username);
			
			List<RAlarmLogTemp> dataList = new ArrayList<RAlarmLogTemp>();
			dataList = rAlarmLogTempDAO.getAlarmLogTemp(vendor.trim(),username.trim(),"U");
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
			
		 }
		/*Convert file theo vendor Huawei
		 * @param file: duong dan file import
		 * @param username: username
		 * @param vendor: vendor
		 */
		
		private List<RAlarmLogTemp> convertFileHuawei(InputStream file,String filename, String username,
				String vendor,Model model,
				HttpServletRequest request) throws IOException {
			//Delete du lieu trong bang temp theo vendor va username nay
			rAlarmLogTempDAO.deleteAlarmLogTemp(username, vendor);
			//convert du lieu va insert vao temp
			List<RAlarmLogTemp> fallList = new ArrayList<RAlarmLogTemp>();
			try
			{
				@SuppressWarnings("rawtypes")
				List sheetData = UploadTools.readXlsFile(file);
				
				String ne_Site;
				String sdate;
				String edate;
				String severity;
				String alarmInfo;
				String alarmId;
				String alarmName;
				String ackStatus;
				String ackUser;
				String ackTime;
				String network;
				String neType;
				String fmAlarmid;
				String clrStatus;
				String evttype;
				if (sheetData.size() >7) {
	        		for (int i=1; i<sheetData.size(); i++) {
	    	        	RAlarmLogTemp alarmLogTemp = new RAlarmLogTemp();
	        			List data= (List) sheetData.get(i);
	        			if (data.size()>30&&!data.get(1).toString().toUpperCase().contains("STATUS")&& !data.get(1).toString().trim().equals(""))
	        			{ 
	        				if (filename.toLowerCase().contains("core"))
	        				{
	        					ackStatus			= data.get(1).toString().trim().equals("-")?null:data.get(1).toString().trim();
			        			ackUser				= data.get(2).toString().trim().equals("-")?null:data.get(2).toString().trim();
			        			ackTime				= data.get(3).toString().trim().equals("-")?null:data.get(3).toString().trim();
			        			alarmId				= data.get(5).toString().trim().equals("-")?null:data.get(5).toString().trim();
			        			ne_Site				= data.get(6).toString().trim().equals("-")?null:data.get(6).toString().trim();
			        			sdate				= data.get(26).toString().trim().equals("-")?null:data.get(26).toString().trim();//7
			        			edate				= data.get(10).toString().trim().equals("-")?null:data.get(10).toString().trim();
			        			fmAlarmid			= data.get(14).toString().trim().equals("-")?null:data.get(14).toString().trim();
			        			alarmInfo			= data.get(20).toString().trim().equals("-")?null:data.get(20).toString().trim();
			        			alarmName			= data.get(24).toString().trim().equals("-")?null:data.get(24).toString().trim();
			        			neType				= data.get(25).toString().trim().equals("-")?null:data.get(25).toString().trim();
			        			severity			= data.get(29).toString().trim().equals("-")?null:data.get(29).toString().trim();
			        			evttype				= data.get(34).toString().trim().equals("-")?null:data.get(34).toString().trim();
			        			clrStatus			= data.get(9).toString().trim().equals("-")?null:data.get(9).toString().trim();
	        				
	        				}
	        				else
	        				{
		        				ackStatus			= data.get(1).toString().trim().equals("-")?null:data.get(1).toString().trim();
			        			ackUser				= data.get(2).toString().trim().equals("-")?null:data.get(2).toString().trim();
			        			ackTime				= data.get(3).toString().trim().equals("-")?null:data.get(3).toString().trim();
			        			alarmId				= data.get(5).toString().trim().equals("-")?null:data.get(5).toString().trim();
			        			ne_Site				= data.get(6).toString().trim().equals("-")?null:data.get(6).toString().trim();
			        			sdate				= data.get(26).toString().trim().equals("-")?null:data.get(26).toString().trim();//7
			        			edate				= data.get(11).toString().trim().equals("-")?null:data.get(11).toString().trim();
			        			fmAlarmid			= data.get(14).toString().trim().equals("-")?null:data.get(14).toString().trim();
			        			alarmInfo			= data.get(18).toString().trim().equals("-")?null:data.get(18).toString().trim();
			        			alarmName			= data.get(24).toString().trim().equals("-")?null:data.get(24).toString().trim();
			        			neType				= data.get(25).toString().trim().equals("-")?null:data.get(25).toString().trim();
			        			severity			= data.get(29).toString().trim().equals("-")?null:data.get(29).toString().trim();
			        			evttype				= data.get(32).toString().trim().equals("-")?null:data.get(32).toString().trim();
			        			clrStatus			= data.get(13).toString().trim().equals("-")?null:data.get(13).toString().trim();
	        				}
	        				
		        			alarmLogTemp.setAckTimeStr(ackTime);
	        				alarmLogTemp.setSdateStr(sdate);
	        				alarmLogTemp.setAlarmIdStr(alarmId);
	        				alarmLogTemp.setFmAlarmidStr(fmAlarmid);
	        				alarmLogTemp.setEdateStr(edate);
		        			
		        			alarmLogTemp.setUsername(username);
		        			alarmLogTemp.setVendor(vendor);
		        			alarmLogTemp.setAckStatus(ackStatus);
		        			alarmLogTemp.setAckUser(ackUser);
		        			alarmLogTemp.setAlarmName(alarmName);
		        			alarmLogTemp.setNeType(neType);
		        			alarmLogTemp.setEvttype(evttype);
		        			alarmLogTemp.setClrStatus(clrStatus);
		        			alarmLogTemp.setAlarmInfo(alarmInfo);
		        		
		        			if (neType!=null)
		        			{
			        			if(neType.contains("BSC")){
			        			    neType = "BSC";
			        			   }else if(neType.contains("BTS")){
			        			    neType = "BTS";
			        			   }
			        			alarmLogTemp.setNeType(neType);
		        			}
		        		
		        			//phan tich ne, site
		        			if (ne_Site!=null)
		        			{
		        				String[] ne_site = ne_Site.split("/");
		        				alarmLogTemp.setNe(ne_site[0]);
		        				if (ne_site.length>1)
		        				{
		        					alarmLogTemp.setSite(ne_site[1]);
		        				}
		        				/*else
		        				{
		        					alarmLogTemp.setSite(ne_site[0]);
		        				}*/
		        				
		        				if(ne_site[0].substring(0,1).equalsIgnoreCase("B")){
									network = "2G";
								}else if(ne_site[0].substring(0,1).equalsIgnoreCase("R")){
									network = "3G";
								}else if(ne_site[0].substring(0,1).equalsIgnoreCase("S")||ne_site[0].substring(0,1).equalsIgnoreCase("G")){
									network = "PS_CORE";
								}else{
									network = "CS_CORE";
								}
		        				alarmLogTemp.setNetwork(network);
		        			}
		        			
		        			if (severity!=null)
		        			{
		        				if(severity.equalsIgnoreCase("critical")){
		        					severity = "A1";
								}else if(severity.equalsIgnoreCase("major")){
									severity = "A2";
								}else if(severity.equalsIgnoreCase("minor")){
									severity = "A3";
								}else if(severity.equalsIgnoreCase("warning")){
									severity = "A4";
								}else{
									severity = "O";
								}
		        				alarmLogTemp.setSeverity(severity);
		        			}
		        			try
		        			{
		        				if (ackTime!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", ackTime))
		            			{
		            				alarmLogTemp.setAckTime(df_full3.parse(ackTime));
		            					if (!ackTime.equals(df_full3.format(alarmLogTemp.getAckTime())))
		            					{
		            						alarmLogTemp.setAckTime(df_full.parse(ackTime));
		            					}
		            				
		            			}
		            			if (sdate!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", sdate))
		            			{
		            				
		            					alarmLogTemp.setSdate(df_full3.parse(sdate));
		            					if (!sdate.equals(df_full3.format(alarmLogTemp.getSdate())))
		            					{
		            						alarmLogTemp.setSdate(df_full.parse(sdate));
		            					}
		            				
		            			}
		            			if (edate!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", edate))
		            			{
		            				alarmLogTemp.setEdate(df_full3.parse(edate));
		        					if (!edate.equals(df_full3.format(alarmLogTemp.getEdate())))
		        					{
		        						alarmLogTemp.setEdate(df_full.parse(edate));
		        					}
		            				
		            			}
			        			if (alarmId!=null)
			        			{
			        				alarmLogTemp.setAlarmId(Integer.parseInt(alarmId));
			        			}
			        			if (fmAlarmid!=null)
			        			{
			        				alarmLogTemp.setFmAlarmid(Integer.parseInt(fmAlarmid));
			        			}
			        			//insert
			        			
			        			rAlarmLogTempDAO.insertToTemp(alarmLogTemp,"R_ALARM_LOG_TEMP");
		        			}
		        			catch(Exception exp)
		        			{
		        				System.out.println("Insert FALL");
		        				System.out.println("alarmId: "+alarmId); 
							/*	System.out.println("ne: "+ne_Site); 
								System.out.println("alarmName: "+alarmName); 
								System.out.println("sdate: "+sdate); 
								System.out.println("edate: "+edate); 
								System.out.println("alarmInfo: "+alarmInfo); */	
		        				fallList.add(alarmLogTemp);
		        			}
		        		}
	        		}
				}
				}
			catch(Exception exp)
			{
				saveMessageKey(request, "import.overMaxSize");
			}
			return fallList;
		}
		
		/*Convert file theo vendor Alcatel
		 * @param file: duong dan file import
		 * @param username: username
		 * @param vendor: vendor
		 */
		private List<RAlarmLogTemp> convertFileAlcatel(InputStream file, String username,
				String vendor,Model model,
				HttpServletRequest request) throws IOException, ParseException {
			
			//Delete du lieu trong bang temp theo vendor va username nay
			rAlarmLogTempDAO.deleteAlarmLogTemp(username, vendor);
			List<RAlarmLogTemp> fallList = new ArrayList<RAlarmLogTemp>();
			//convert du lieu va insert vao temp
			try
			{
			@SuppressWarnings("rawtypes")
			List sheetData = UploadTools.readXlsFile(file);
			
			String sdate;
			String edate;
			String severity;
			String alarmName;
			String ackStatus;
			String ackUser;
			String ackTime;
			String causebySys;
			String network;
			String ne_Site;
			
    		for (int i=1; i<sheetData.size(); i++) {
	        	RAlarmLogTemp alarmLogTemp = new RAlarmLogTemp();
    			List data= (List) sheetData.get(i);
    			if (data.size()>40 && !data.get(1).toString().toUpperCase().contains("STATUS") && !data.get(1).toString().trim().equals(""))
    			{ 
    				ackStatus			= data.get(1).toString().trim().equals("-")?null:data.get(1).toString().trim();
        			ackTime				= data.get(2).toString().trim().equals("-")?null:data.get(2).toString().trim();
        			ackUser				= data.get(3).toString().trim().equals("-")?null:data.get(3).toString().trim();
        			edate				= data.get(17).toString().trim().equals("-")?null:data.get(17).toString().trim();
        			sdate				= data.get(20).toString().trim().equals("-")?null:data.get(20).toString().trim();
        			ne_Site				= data.get(22).toString().trim().equals("-")?null:data.get(22).toString().trim();
        			severity			= data.get(27).toString().trim().equals("-")?null:data.get(27).toString().trim();
        			causebySys			= data.get(29).toString().trim().equals("-")?null:data.get(29).toString().trim();
        			alarmName			= data.get(37).toString().trim().equals("-")?null:data.get(37).toString().trim();
        			
        			alarmLogTemp.setAckTimeStr(ackTime);
    				alarmLogTemp.setSdateStr(sdate);
    				alarmLogTemp.setEdateStr(edate);
        			
        			alarmLogTemp.setUsername(username);
        			alarmLogTemp.setVendor(vendor);
        			alarmLogTemp.setAckStatus(ackStatus);
        			alarmLogTemp.setAckUser(ackUser);
        			alarmLogTemp.setCausebySys(causebySys);
        			alarmLogTemp.setAlarmInfo(ne_Site);
        			alarmLogTemp.setObjectReference(ne_Site);
        			//severity
        			if (severity!=null)
        			{
        				if(severity.equalsIgnoreCase("critical")){
        					severity = "A1";
						}else if(severity.equalsIgnoreCase("major")){
							severity = "A2";
						}else if(severity.equalsIgnoreCase("minor")){
							severity = "A3";
						}else if(severity.equalsIgnoreCase("warning")){
							severity = "A4";
						}else{
							severity = "O";
						}
        				alarmLogTemp.setSeverity(severity);
        			}
        			//alarm name
        			if (alarmName!=null)
        			{
        				String[] temp = alarmName.split(";");
        				if (temp.length>1)
        				{
        					alarmName = temp[1]+","+temp[0];
        				}
        				alarmLogTemp.setAlarmName(alarmName);
        			}
        		
    			try
    			{
        			//phan tich ne, site,alarm info, 
        			if (ne_Site!=null)
        			{
        				String[] temp = ne_Site.split(" ");
        				alarmLogTemp.setNe(temp[0]);
        				if (alarmName!=null && alarmName.contains("BSC"))
        				{
        					//BBTTL1A Abis TP 30[_30]
        					//BTNTN1A
        					alarmLogTemp.setNeType("BSC");
        					int lenght = temp.length;
        					if (lenght>1)
        					{
        						alarmLogTemp.setObjType(temp[1]);
        						String port = temp[lenght-1].substring(0,temp[lenght-1].indexOf("["));
        						alarmLogTemp.setBscport(port);
        						
        					}
        				}
        				else if (alarmName!=null && (alarmName.contains("BTS")||alarmName.toLowerCase().contains("failed")))
        				{
        					//BBTLG1A PTTB45
        					//BBTPT1A PTPT04 ra 2
        					alarmLogTemp.setNeType("BTS");
        					int lenght = temp.length;
        					if (lenght>1)
        					{
        						alarmLogTemp.setSite(temp[1]);
        						
        					}
        					
        				}
        				else if (alarmName!=null &&(alarmName.contains("CELL")||alarmName.toLowerCase().contains("service")))
        				{
        					//BTNTN1A TYTN27 TYTN273
        					alarmLogTemp.setNeType("BTS");
        					
        					int lenght = temp.length;
        					if (lenght>1)
        					{
        						alarmLogTemp.setSite(temp[1]);
        					}
        					if (lenght>2)
        					{
        						alarmLogTemp.setCellid(temp[2]);
        					}
        					
        				}
        				else if (alarmName!=null &&alarmName.contains("TRUNK"))
        				{
        					//---TRUNK
        					//BBTLG1A Ater TP 26
        					//-- TSC-TRUNK
        					//BTNDM1A TC A TP 51
        					
        					int lenght = temp.length;
        					if (lenght>1)
        					{
        						alarmLogTemp.setObjType(temp[1]);
        						alarmLogTemp.setBscport(temp[lenght-1]);
        					}
        					
        				}
        				else if (alarmName!=null &&(alarmName.contains("RX-TX")||alarmName.contains("SW-AN")||alarmName.contains("OMU")))
        				{
        					//BTNTC1A TYTC19 rack1 shelf2 trage 10
        					//BBDPG1A BDPG44 rack1 shelf3 agc9e 4
        					//BNTNS1A NTBA11 rack1 shelf3 suma 7
        					//BNTPR1A NTPR02 tre 3
        					int lenght = temp.length;
        					if (lenght>1)
        					{
        						alarmLogTemp.setSite(temp[1]);
        						
        						if (lenght>2 && temp[2].toLowerCase().contains("rack"))
        						{
        							alarmLogTemp.setRack(Integer.parseInt(temp[2].substring(temp[2].toLowerCase().indexOf("k")+1,temp[2].length() )));
        		
        						}
        						if (lenght>3 && temp[3].toLowerCase().contains("shelf"))
        						{
        							alarmLogTemp.setSubrack(Integer.parseInt(temp[3].substring(temp[3].toLowerCase().indexOf("f")+1, temp[3].length())));
        						}
        						if (lenght>4)
        						{
        							alarmLogTemp.setSlot(Integer.parseInt(temp[lenght-1]));
        						}
        					}

        				}
        				else if (alarmName!=null &&(alarmName.contains("RAI")||alarmName.contains("CRC4")||alarmName.contains("AIS")||alarmName.toLowerCase().contains("mode")))
        				{
        					//MFSBD1A rack1 Shelf 3 Board 12 TP 1 ie LIU 6 TP 9
        					
        					int lenght = temp.length;
        					
        					if (lenght>1 && temp[1].toLowerCase().contains("rack"))
        					{
        						alarmLogTemp.setRack(Integer.parseInt(temp[1].substring(temp[1].toLowerCase().indexOf("k")+1,temp[1].length())));
        					}
        					if (lenght>3)
    						{
    							alarmLogTemp.setSubrack(Integer.parseInt(temp[3]));
    						}
    						if (lenght>8)
    						{
    							alarmLogTemp.setBscport(temp[lenght-1]);
    						}
        				}
        				else if (alarmName!=null && alarmName.contains("ENVIRON"))
        				{
        					//BLDDA1A TC rack9 shelf4 jbmte2 3
        					
        					int lenght = temp.length;
        					
        					if (lenght>1)
        					{
        						alarmLogTemp.setObjType(temp[1]);
        					}
        					if (lenght>2  && temp[2].toLowerCase().contains("rack"))
    						{
    							alarmLogTemp.setRack(Integer.parseInt(temp[2].substring(temp[2].toLowerCase().indexOf("k")+1,temp[2].length())));
    						}
    						if (lenght>3  && temp[3].toLowerCase().contains("shelf"))
    						{
    							alarmLogTemp.setSubrack(Integer.parseInt(temp[3].substring(temp[3].toLowerCase().indexOf("f")+1, temp[3].length())));
    						}
    						if (lenght>4)
    						{
    							alarmLogTemp.setSlot(Integer.parseInt(temp[lenght-1]));
    						}
        					
        				}
        				
        				
        				if(temp[0].substring(0,1).equalsIgnoreCase("B")||temp[0].substring(0,2).equalsIgnoreCase("MF")||temp[0].substring(0,2).equalsIgnoreCase("VMS")){
							network = "2G";
						}else if(temp[0].substring(0,1).equalsIgnoreCase("R")){
							network = "3G";
						}else if(temp[0].substring(0,1).equalsIgnoreCase("S")||temp[0].substring(0,1).equalsIgnoreCase("G")){
							network = "PS_CORE";
						}else{
							network = "CS_CORE";
						}
        				alarmLogTemp.setNetwork(network);
        			}
    
        			
        			if (ackTime!=null)
        			{
        				alarmLogTemp.setAckTime(df_full.parse(ackTime));
        				if (!ackTime.equals(df_full.format(alarmLogTemp.getAckTime())))
    					{
    						alarmLogTemp.setAckTime(df_full3.parse(ackTime));
    					}
        				
        			}
        			if (sdate!=null)
        			{
        				
        				alarmLogTemp.setSdate(df_full.parse(sdate));
    					if (!sdate.equals(df_full.format(alarmLogTemp.getSdate())))
    					{
    						alarmLogTemp.setSdate(df_full3.parse(sdate));
    					}
        				
        			}
        			if (edate!=null)
        			{
        				alarmLogTemp.setEdate(df_full.parse(edate));
    					if (!edate.equals(df_full.format(alarmLogTemp.getEdate())))
    					{
    						alarmLogTemp.setEdate(df_full3.parse(edate));
    					}
        				
        			}
	        			//insert
        			//System.out.println("sdate:"+sdate+"---"+alarmLogTemp.getSdate());
    				
	        			rAlarmLogTempDAO.insertToTemp(alarmLogTemp,"R_ALARM_LOG_TEMP");
	        			
        			}
        			catch(Exception exp)
        			{
        				System.out.println("------------------iNSERT Khong THANH CONG");
        				System.out.println("ne_Site: "+ ne_Site);
        				/*System.out.println("ne: "+ alarmLogTemp.getNe());
        				System.out.println("alarmName: "+ alarmName);
        				
	    				System.out.println("sdate:"+sdate);
	    				System.out.println("edate:"+edate);
	    				System.out.println("ackTime:"+ackTime);
						System.out.println("ObjType:"+alarmLogTemp.getObjType());
						System.out.println("Bscport:"+alarmLogTemp.getBscport());
						System.out.println("Site:"+alarmLogTemp.getSite());
    					System.out.println("Cellid:"+alarmLogTemp.getCellid());*/
						
						
        				fallList.add(alarmLogTemp);
        			}
        		}
    			}
			}
			catch(Exception exp)
			{
				saveMessageKey(request, "import.overMaxSize");
			}
			return fallList;
		}
		
		/*@ANHNT 16-10-2013
		INSERT DATA ALARM  ERICSSON*/
		
		private List<RAlarmLogTemp> insertDataAlarm(List<RAlarmLogTemp> alarmLogList, String username, String vendor){
			System.out.println("username: "+username);
			rAlarmLogTempDAO.deleteAlarmLogTemp(username, vendor);
			List<RAlarmLogTemp> fallList = new ArrayList<RAlarmLogTemp>();

			for(RAlarmLogTemp item: alarmLogList){ 
				try {
					item.setUsername(username);
					rAlarmLogTempDAO.insertToTemp(item,"R_ALARM_LOG_TEMP");
					
				} catch (Exception e) {
					System.out.println("Insert FALL");
    				System.out.println("ne: "+item.getNe()); 
					/*System.out.println("alarmName: "+item.getAlarmName()); 
					System.out.println("sdate: "+item.getSdate()); 
					System.out.println("edate: "+item.getEdate()); 
					System.out.println("alarmInfo: "+item.getAlarmInfo());
					System.out.println("getFmAlarmid: "+item.getFmAlarmid());*/
					fallList.add(item);
				}
			} 
		
			return fallList; 
		} 
		
		
		 /*
		  * Insert NOKIA
		  * Update by: Thangpx
		  * Date: 19/03/2014
		 */
		private List<RAlarmLogTemp> convertFileNokia(InputStream pathFile, String username,
				HttpServletRequest request) throws NumberFormatException, IOException, ParseException{  		
			
				Reader reader = null;
				//Doc tung byte
			    reader = new InputStreamReader(pathFile); 
				BufferedReader buffReader = new BufferedReader(reader); 
				
				List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
				String strLine = "",alarmId = "",severity = "",bts = "",ne = "",alarmName = "",sdate = ""
						,edate = "",ackDate = "",ackUser = "",alarmInfo = "",fmAlarmId = "";
				int count = 0 ; 
				boolean block = false;   
				
				rAlarmLogTempDAO.deleteAlarmLogTemp(username, "NOKIA SIEMENS");		
				rAlarmLogTempDAO.deleteAlarmLogNSNTemp(username);
				 
			try {
				RAlarmLogTemp record = new RAlarmLogTemp();
				while ((strLine = buffReader.readLine()) != null)
				{
					
					strLine = strLine.trim();
					// Danh dau block
					if(strLine.contains("*") || IN_DateTools.isValidNumber(strLine.split(" ")[0])){
						block = true;
						count = 0; 
					} 
					
					if(block == true){

						count ++;
						// Lay alarmid va severity
						if(count ==1){
						strLine = unSpace(strLine.trim());
						String[] str = strLine.split(" ");
						alarmId = str[0];
						severity = str[1];
						if(severity.equals("***")){
							severity = "A1";
						}else if(severity.equals("**")){
							severity = "A2";
						}else if(severity.equals("*")){
							severity = "A3";
						}
						record.setAlarmId(Integer.parseInt(alarmId));
						record.setSeverity(severity); 
						}
						// Lay bscid(ne)
					else  if(count == 2){
						strLine = unSpace(strLine);
						ne = strLine.split(" ")[0];
						alarmInfo += strLine+sep; 
						record.setNe(ne);
					}
						// Lay site
					else if(count == 3){
						bts =  strLine.trim();
						alarmInfo += strLine+sep; 
						 
					}
					// Lay alarmName
					else if(count == 4){
						alarmName = strLine.trim();  
						alarmInfo += strLine+sep; 
						record.setAlarmName(alarmName); 
					}
					// Lay sdate va edate
					else if(count == 5){ 
						strLine = unSpace(strLine.trim());
						alarmInfo += strLine+sep;
						String[] str = strLine.split(" ");
						try { 
							sdate = str[1]+" "+str[2];
							edate = str[4]+" "+str[5];
						} catch (Exception e) { 
						}
						 if(!sdate.equals(""))
							 record.setSdate( df_full2.parse(sdate));
						 else
							 record.setSdateStr(sdate);
						 if(!edate.equals(""))
							 record.setEdate( df_full2.parse(edate));
						 else
							 record.setEdateStr(edate); 
					}
						// Lay fmAlarmId
					if(strLine.contains("Notif ID")){
						strLine = strLine.trim();
						fmAlarmId = strLine.substring(strLine.lastIndexOf(" ")+1); 
						record.setFmAlarmidStr(fmAlarmId);
					}
					
					// Lay ackUser va ackDate
					if(strLine.contains("Acked by")){
						strLine = strLine.trim();
						ackDate = strLine.substring(strLine.indexOf("Acked")+6, strLine.lastIndexOf("Acked") - 1);
						ackUser = strLine.substring(strLine.lastIndexOf(" ")+1);
						alarmInfo += strLine+sep; 
						record.setAckUser(ackUser);
						if (!ackDate.equals(""))
							record.setAckTime(df_full2.parse(ackDate));  
						else
							record.setAckTimeStr(ackDate); 
					} 
					if(strLine.contains("Started"))
						block = false;
					} 
					
					if(block == false && count > 0 && !strLine.equals(""))
					{
						// Lay alarmInfo, ObjectReference, username
						record.setAlarmInfo(alarmInfo);
						record.setObjectReference(bts); 
						record.setVendor("NOKIA SIEMENS");
						record.setUsername(username);
					
						
						try {
							System.out.println(strLine);
							rAlarmLogTempDAO.insertToTemp(record,"R_ALARM_LOG_NSN_TEMP"); 
							
						} catch (Exception e) {  
							alarmLogList.add(record);
						}
						finally {
							alarmInfo = "";
							alarmId = "";
							severity = "";
							bts = "";
							ne = ""; 
							alarmName = "";
							sdate = "";
							edate = ""; 
							ackDate = "";
							ackUser = "";  
							fmAlarmId = "";  
							record = new RAlarmLogTemp();
						}
					} 
				}
				
			}catch (Exception e) {
				System.out.println("Error System: "+ e.getMessage());
			}  finally {
				if (reader != null) {
					try {
						reader.close(); 
						rAlarmLogTempDAO.insertNokia("NOKIA SIEMENS", username); 
						
					} catch (IOException e) { 
						System.out.println("Error System: "+ e.getMessage());
						saveMessageKey(request, "import.overMaxSize");
						if (!e.getClass().getSimpleName().equals("ClientAbortException")) {
					        throw e;
					    }
					}
				}
				}
			
			return alarmLogList;
		} 
		
		public List<RAlarmLogTemp> convertEricsson(InputStream pathFile, String userName,
				HttpServletRequest request){
			String _Ne = "",_Bts="";
			List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
			DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			rAlarmLogTempDAO.deleteAlarmLogTemp(userName, "ERICSSON");
			try { 
//				FileInputStream pathFile1 = new FileInputStream(pathFile);
				Reader reader = new InputStreamReader(pathFile); 
				
				//Doc tung byte
				BufferedReader buffReader = new BufferedReader(reader); 
				
				String strLine = null; 
				boolean block = false; 
				String[] str = new String[30];
				String line = "";
				int i=0;
				int count = 0, count1 = 0;
				while ((strLine = buffReader.readLine()) != null)
				{
					//System.out.println("Dong "+i +":"+ strLine);
					count++;
					if(strLine.contains("=======")){  
						block = true;
						i = 0; 
						str = new String[30];
						continue;
					} 
					
					if(block == true){ 
						try {
							if(i == 12 || i == 17 || i == 19){
								String[] str1 = strLine.split(" ");
								str[i] = str1[1]+ " " +str1[2];
							}else if(i == 1){
								str[i] = strLine.split(":")[1].trim();
								String[] str2 = strLine.split(",");
								_Ne = str2[1].split("=")[1];
								_Bts = str2[2].split("=")[1];
								if(_Ne.equals(_Bts)){
									_Bts = "";
								}
							}else if(i == 2){
								String str3 = strLine.split(":")[1].trim();
								if(str3.equalsIgnoreCase("Major")|| str3.equalsIgnoreCase("Cleared")){
									str[i] = "A2"; 
								}else if(str3.equalsIgnoreCase("Critical")){
									str[i] = "A1"; 
								}else if(str3.equalsIgnoreCase("Warning")){
									str[i] = "A4"; 
								}else if(str3.equalsIgnoreCase("Minor")){
									str[i] = "A3"; 
								}else {
									str[i] = "O"; 
								}
							}else{
								str[i] = strLine.split(":")[1].trim();
							}
							
							if(i == 29){
								count1++;
								block = false; 
								RAlarmLogTemp record = new RAlarmLogTemp(); 
								
								record.setSdateStr(str[17]);
								record.setEdateStr(str[12]);
								record.setFmAlarmidStr(str[0]);
								
								try {
									record.setEdate(df_full.parse(str[12]));
								} catch (Exception e) {
									// TODO: handle exception
								}
								try {
									record.setSdate(df_full.parse(str[17])); 
								} catch (Exception e) {
									// TODO: handle exception
								}
								 
								record.setFmAlarmid(Integer.parseInt(str[0]));
								record.setObjectReference(str[1]);
								record.setSeverity(str[2]);
								record.setEvttype(str[18]);
								record.setCauseby(str[23]);
								record.setAlarmInfo(str[25]);
								record.setAlarmName(str[29]);
								record.setVendor("ERICSSON");
								record.setNe(_Ne);
								record.setSite(_Bts);
								record.setUsername(userName);
								String network;
								if(_Ne.substring(0,1).equalsIgnoreCase("B")){
									network = "2G";
								}else if(_Ne.substring(0,1).equalsIgnoreCase("R")){
									network = "3G";
								}else if(_Ne.substring(0,1).equalsIgnoreCase("S")||_Ne.substring(0,1).equalsIgnoreCase("G")){
									network = "PS_CORE";
								}else{
									network = "CS_CORE";
								}
								record.setNetwork(network);
								line += str[12]+";"+str[17]+";"+str[0]+";"+str[1]+";"+str[2]+";"+str[18]+";"
										+str[23]+";"+str[25]+";"+str[29]+";"+_Ne+";"+_Bts;
								
								_Ne = ""; _Bts = ""; line = "";
								//alarmLogList.add(record);
								try{
									rAlarmLogTempDAO.insertToTemp(record,"R_ALARM_LOG_TEMP");
								}catch(Exception e){
									alarmLogList.add(record);
									System.out.println(line);
								}
							}
							i++; 
						} catch (Exception e) {
							str[i] = ""; 
							i++;
						}  
					} 
				}
			//	System.out.println(count1 +"/"+ count);
				try {
					reader.close(); 	
				} catch (IOException e) { 
					System.out.println("Error System: "+ e.getMessage());
					saveMessageKey(request, "import.overMaxSize");
					if (!e.getClass().getSimpleName().equals("ClientAbortException")) {
				        throw e;
				    }
				}
			}catch (Exception e) { 
				saveMessageKey(request, "import.overMaxSize");
			}
			return alarmLogList;
		} 
			
		private String unSpace(String strLine){
			while(strLine.indexOf("  ") > 0){
				strLine = strLine.replace("  ", " ");
			}
			return strLine;
		}
		
		// convert file Huawei dang csv
		private List<RAlarmLogTemp> convertFileHuaweiCSV(InputStream pathFile, String userName,
				HttpServletRequest request) throws NumberFormatException, IOException, ParseException{ 
				//Tao mot con tro de connect den pathFile  
			    // FileInputStream pathFile1 = new FileInputStream(pathFile);
				//writer = new BufferedWriter(new FileWriter(pathOut+"/"+"AlarmHuawei"));
			
				rAlarmLogTempDAO.deleteAlarmLogTemp(userName, "HUAWEI");
			
				List<RAlarmLogTemp> alarmLogList = new ArrayList<RAlarmLogTemp>();
				DateFormat df_full3 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
				String severity="";
				String ackStatus="";
				String ackTime="";
				String ackUser="";
				String alarmId="";
				String neList="";
				String sdate ="";
				String clrStatus="";
				String edate ="";
				String alarmInfo ="";
				String fmAlarmid ="";
				String alarmName ="";
				String neType ="";
				String evttype ="";
				String line = "";
				String network = "";
				String  _Ne = "", _Bts = "";
				boolean block = false;
				int count = 0;
				
			try {
					
				Reader reader = new InputStreamReader(pathFile); 
				//Doc tung byte
				CsvReader buffReader = new CsvReader(reader); 
				//buffReader.readHeaders(); 
				
				while (buffReader.readRecord())
				{
					if(buffReader.get(1).toLowerCase().contains("severity")||buffReader.get(1).toLowerCase().contains("status")){
						String[] header = buffReader.getValues();
						buffReader.setHeaders(header);
						block = true;
						continue;
					}
					if(block){  
						severity = buffReader.get("Severity").replace("-", "");
						ackStatus = buffReader.get("Acknowledgement Status").replace("-", "");
						ackTime = buffReader.get("Acknowledgement Time(ST)").replace("-", "");
						ackUser = buffReader.get("Acknowledgement User").replace("-", "");
						alarmId = buffReader.get("Alarm ID").replace("-", "");
						neList = buffReader.get("Alarm Source").replace("-", "");
						sdate = buffReader.get("Occurrence Time(NT)").replace("-", "");
						clrStatus = buffReader.get("Clearance Status").replace("-", "");
						edate = buffReader.get("Clearance Time(NT)").replace("-", "");
						alarmInfo = buffReader.get("Location Information").replace("-", ""); 
						fmAlarmid = buffReader.get("Log Serial Number").replace("-", ""); 
						alarmName = buffReader.get("Name").replace("-", "");
						neType =  buffReader.get("NE Type").replace("-", "");
						evttype = buffReader.get("Type").replace("-", "");
						
					//Lay thong tin muc do canh bao
					if(severity.equalsIgnoreCase("critical")){
						severity = "A1";
					}else if(severity.equalsIgnoreCase("major")){
						severity = "A2";
					}else if(severity.equalsIgnoreCase("minor")){
						severity = "A3";
					}else if(severity.equalsIgnoreCase("warning")){
						severity = "A4";
					}else{
						severity = "O";
					}
					
					//Lay thong tin NE
					_Ne = neList.split("/")[0];
					try {
						_Bts = neList.split("/")[1];
					} catch (Exception e) {} 
					
					//Lay thong tin network
					if (_Ne!=null && !_Ne.equals(""))
					{
						if(_Ne.substring(0,1).equalsIgnoreCase("B")){
							network = "2G";
						}else if(_Ne.substring(0,1).equalsIgnoreCase("R")){
							network = "3G";
						}else if(_Ne.substring(0,1).equalsIgnoreCase("S")||_Ne.substring(0,1).equalsIgnoreCase("G")){
							network = "PS_CORE";
						}else{
							network = "CS_CORE";
						}
					}
					
					//Lay thong tin NE_TYPE
					if(neType.contains("BSC")){
						neType = "BSC";
					}else if(neType.equals("GBTS")){
						neType = "BTS";
					}
					
					RAlarmLogTemp alarmLogTemp = new RAlarmLogTemp(); 
					if (ackTime!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", ackTime))
        			{
        				alarmLogTemp.setAckTime(df_full3.parse(ackTime));
        					if (!ackTime.equals(df_full3.format(alarmLogTemp.getAckTime())))
        					{
        						alarmLogTemp.setAckTime(df_full.parse(ackTime));
        					}
        				
        			}
        			if (sdate!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", sdate))
        			{
        				
        					alarmLogTemp.setSdate(df_full3.parse(sdate));
        					if (!sdate.equals(df_full3.format(alarmLogTemp.getSdate())))
        					{
        						alarmLogTemp.setSdate(df_full.parse(sdate));
        					}
        				
        			}
        			if (edate!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", edate))
        			{
        				alarmLogTemp.setEdate(df_full3.parse(edate));
    					if (!edate.equals(df_full3.format(alarmLogTemp.getEdate())))
    					{
    						alarmLogTemp.setEdate(df_full.parse(edate));
    					}
        				
        			}
					if(!alarmId.equals("")){
						alarmLogTemp.setAlarmId(Integer.parseInt(alarmId));
					}
					if(!fmAlarmid.equals("")){
						alarmLogTemp.setFmAlarmid(Integer.parseInt(fmAlarmid));
					} 
					alarmLogTemp.setAckTimeStr(ackTime);
					alarmLogTemp.setSdateStr(sdate);
					alarmLogTemp.setAlarmIdStr(alarmId);
					alarmLogTemp.setEdateStr(edate);
					alarmLogTemp.setFmAlarmidStr(fmAlarmid);
					
					alarmLogTemp.setNe(_Ne);
					alarmLogTemp.setSite(_Bts);
					alarmLogTemp.setNetwork(network);
	    			alarmLogTemp.setSeverity(severity);
	    			alarmLogTemp.setUsername(userName);
	    			alarmLogTemp.setVendor("HUAWEI");
	    			alarmLogTemp.setAckStatus(ackStatus);
	    			alarmLogTemp.setAckUser(ackUser);
	    			alarmLogTemp.setAlarmName(alarmName);
	    			alarmLogTemp.setNeType(neType);
	    			alarmLogTemp.setEvttype(evttype);
	    			alarmLogTemp.setClrStatus(clrStatus);
	    			alarmLogTemp.setAlarmInfo(alarmInfo);
	    			
	    			line = ackTime+";"+sdate+";"+alarmId+";"+fmAlarmid+";"+edate+";"+_Ne+";"+_Bts+";"+network+";"+
	    					severity+";"+userName+";"+ackStatus+";"+ackUser+";"+alarmName+";"+neType+";"+alarmInfo;
	    			count++;
	    			//System.out.println(count);
	    			//alarmLogList.add(alarmLogTemp);
	    			try {
	    				//System.out.println("strLine: "+line);
						rAlarmLogTempDAO.insertToTemp(alarmLogTemp,"R_ALARM_LOG_TEMP");
						alarmId = "";
						fmAlarmid = "";
						network = "";
						severity = "";
						_Bts = "";
						_Ne = ""; 
						alarmName = "";
						sdate = "";
						edate = ""; 
						ackTime = "";
						ackStatus = "";
						ackUser = ""; 
						alarmInfo = "";
						neType = "";
						//System.out.println("Insert success");
						//System.out.println(alarmId+";"+severity+";"+bts+";"+alarmName+";"+sdate+";"+edate+";"+ackDate+";"+ackUser+";"+alarmInfo);
						
					} catch (Exception e) {
						//System.out.println("strLine: "+line);
						System.out.println("alarmId: "+alarmId); 
					/*	System.out.println("ne: "+_Ne); 
						System.out.println("bts: "+_Bts); 
						System.out.println("alarmName: "+alarmName); 
						System.out.println("sdate: "+sdate); 
						System.out.println("edate: "+edate); 
						System.out.println("alarmInfo: "+alarmInfo); */
						System.out.println("Insert FALL");
					//	logger.warn(e);
						alarmLogList.add(alarmLogTemp);
					}  
				}
	    	}
			buffReader.close();
			reader.close();
		}catch (Exception e) {
			System.out.println("strLine: "+line);
			saveMessageKey(request, "import.overMaxSize");
		}
		return alarmLogList;
	} 
		
		// convert file Huawei dang csv
		private List<RAlarmLogTemp> convertFileALUCSV(InputStream pathFile, String userName,String vendor,
				HttpServletRequest request){ 
				//Tao mot con tro de connect den pathFile  
			    // FileInputStream pathFile1 = new FileInputStream(pathFile);
				//writer = new BufferedWriter(new FileWriter(pathOut+"/"+"AlarmHuawei"));
			
				rAlarmLogTempDAO.deleteAlarmLogTemp(userName, vendor);
			
				List<RAlarmLogTemp> fallList = new ArrayList<RAlarmLogTemp>();
				String sdate;
				String edate;
				String severity;
				String alarmName;
				String ackStatus;
				String ackUser;
				String ackTime;
				String causebySys;
				String network;
				String ne_Site;
				String line="";
				boolean block = false;
				
			try {
					
				Reader reader = new InputStreamReader(pathFile); 
				//Doc tung byte
				CsvReader buffReader = new CsvReader(reader); 
				//buffReader.readHeaders(); 
				
				while (buffReader.readRecord())
				{
					RAlarmLogTemp alarmLogTemp = new RAlarmLogTemp();
					if(buffReader.get(1).toLowerCase().contains("severity")||buffReader.get(1).toLowerCase().contains("status")){
							String[] header = buffReader.getValues();
						buffReader.setHeaders(header);
						block = true;
						continue;
					}
					if(block){  
						
						ackStatus			= buffReader.get("Acknowledgement Status").replace("-", "");
	        			ackTime				= buffReader.get("Acknowledgement Time").replace("-", "");
	        			ackUser				= buffReader.get("Acknowledgement User Name").replace("-", "");
	        			edate				= buffReader.get("Clearing Time").replace("-", "");
	        			sdate				= buffReader.get("Event Date and Time").replace("-", "");
	        			ne_Site				= buffReader.get("Friendly Name").replace("-", "");
	        			severity			= buffReader.get("Perceived Severity").replace("-", "");
	        			causebySys			= buffReader.get("Probable Cause (name)").replace("-", "");
	        			alarmName			= buffReader.get("Specific Problems").replace("-", "");
	    
						
						alarmLogTemp.setAckTimeStr(ackTime);
	    				alarmLogTemp.setSdateStr(sdate);
	    				alarmLogTemp.setEdateStr(edate);
	        			
	        			alarmLogTemp.setUsername(userName);
	        			alarmLogTemp.setVendor(vendor);
	        			alarmLogTemp.setAckStatus(ackStatus);
	        			alarmLogTemp.setAckUser(ackUser);
	        			alarmLogTemp.setCausebySys(causebySys);
	        			alarmLogTemp.setAlarmInfo(ne_Site);
	        			alarmLogTemp.setObjectReference(ne_Site);
	        			//severity
	        			if (severity!=null)
	        			{
	        				if(severity.equalsIgnoreCase("critical")){
	        					severity = "A1";
							}else if(severity.equalsIgnoreCase("major")){
								severity = "A2";
							}else if(severity.equalsIgnoreCase("minor")){
								severity = "A3";
							}else if(severity.equalsIgnoreCase("warning")){
								severity = "A4";
							}else{
								severity = "O";
							}
	        				alarmLogTemp.setSeverity(severity);
	        			}
	        			//alarm name
	        			if (alarmName!=null)
	        			{
	        				String[] temp = alarmName.split(";");
	        				if (temp.length>1)
	        				{
	        					alarmName = temp[1]+","+temp[0];
	        				}
	        				alarmLogTemp.setAlarmName(alarmName);
	        			}
	        		
	    			try
	    			{
	        			//phan tich ne, site,alarm info, 
	        			if (ne_Site!=null)
	        			{
	        				String[] temp = ne_Site.split(" ");
	        				alarmLogTemp.setNe(temp[0]);
	        				if (alarmName.contains("BSC"))
	        				{
	        					//BBTTL1A Abis TP 30[_30]
	        					//BTNTN1A
	        					alarmLogTemp.setNeType("BSC");
	        					int lenght = temp.length;
	        					if (lenght>1)
	        					{
	        						alarmLogTemp.setObjType(temp[1]);
	        						String port = temp[lenght-1].substring(0,temp[lenght-1].indexOf("["));
	        						alarmLogTemp.setBscport(port);
	        						
	        					}
	        				}
	        				else if (alarmName.contains("BTS")||alarmName.toLowerCase().contains("failed"))
	        				{
	        					//BBTLG1A PTTB45
	        					//BBTPT1A PTPT04 ra 2
	        					alarmLogTemp.setNeType("BTS");
	        					int lenght = temp.length;
	        					if (lenght>1)
	        					{
	        						alarmLogTemp.setSite(temp[1]);
	        						
	        					}
	        					
	        				}
	        				else if (alarmName.contains("CELL")||alarmName.toLowerCase().contains("service"))
	        				{
	        					//BTNTN1A TYTN27 TYTN273
	        					alarmLogTemp.setNeType("BTS");
	        					
	        					int lenght = temp.length;
	        					if (lenght>1)
	        					{
	        						alarmLogTemp.setSite(temp[1]);
	        					}
	        					if (lenght>2)
	        					{
	        						alarmLogTemp.setCellid(temp[2]);
	        					}
	        					
	        				}
	        				else if (alarmName.contains("TRUNK"))
	        				{
	        					//---TRUNK
	        					//BBTLG1A Ater TP 26
	        					//-- TSC-TRUNK
	        					//BTNDM1A TC A TP 51
	        					
	        					int lenght = temp.length;
	        					if (lenght>1)
	        					{
	        						alarmLogTemp.setObjType(temp[1]);
	        						alarmLogTemp.setBscport(temp[lenght-1]);
	        					}
	        					
	        				}
	        				else if (alarmName.contains("RX-TX")||alarmName.contains("SW-AN")||alarmName.contains("OMU"))
	        				{
	        					//BTNTC1A TYTC19 rack1 shelf2 trage 10
	        					//BBDPG1A BDPG44 rack1 shelf3 agc9e 4
	        					//BNTNS1A NTBA11 rack1 shelf3 suma 7
	        					//BNTPR1A NTPR02 tre 3
	        					int lenght = temp.length;
	        					if (lenght>1)
	        					{
	        						alarmLogTemp.setSite(temp[1]);
	        						
	        						if (lenght>2 && temp[2].toLowerCase().contains("rack"))
	        						{
	        							alarmLogTemp.setRack(Integer.parseInt(temp[2].substring(temp[2].toLowerCase().indexOf("k")+1,temp[2].length() )));
	        		
	        						}
	        						if (lenght>3 && temp[3].toLowerCase().contains("shelf"))
	        						{
	        							alarmLogTemp.setSubrack(Integer.parseInt(temp[3].substring(temp[3].toLowerCase().indexOf("f")+1, temp[3].length())));
	        						}
	        						if (lenght>4)
	        						{
	        							alarmLogTemp.setSlot(Integer.parseInt(temp[lenght-1]));
	        						}
	        					}

	        				}
	        				else if (alarmName.contains("RAI")||alarmName.contains("CRC4")||alarmName.contains("AIS")||alarmName.toLowerCase().contains("mode"))
	        				{
	        					//MFSBD1A rack1 Shelf 3 Board 12 TP 1 ie LIU 6 TP 9
	        					
	        					int lenght = temp.length;
	        					
	        					if (lenght>1 && temp[1].toLowerCase().contains("rack"))
	        					{
	        						alarmLogTemp.setRack(Integer.parseInt(temp[1].substring(temp[1].toLowerCase().indexOf("k")+1,temp[1].length())));
	        					}
	        					if (lenght>3)
	    						{
	    							alarmLogTemp.setSubrack(Integer.parseInt(temp[3]));
	    						}
	    						if (lenght>8)
	    						{
	    							alarmLogTemp.setBscport(temp[lenght-1]);
	    						}
	        				}
	        				else if (alarmName.contains("ENVIRON"))
	        				{
	        					//BLDDA1A TC rack9 shelf4 jbmte2 3
	        					
	        					int lenght = temp.length;
	        					
	        					if (lenght>1)
	        					{
	        						alarmLogTemp.setObjType(temp[1]);
	        					}
	        					if (lenght>2  && temp[2].toLowerCase().contains("rack"))
	    						{
	    							alarmLogTemp.setRack(Integer.parseInt(temp[2].substring(temp[2].toLowerCase().indexOf("k")+1,temp[2].length())));
	    						}
	    						if (lenght>3  && temp[3].toLowerCase().contains("shelf"))
	    						{
	    							alarmLogTemp.setSubrack(Integer.parseInt(temp[3].substring(temp[3].toLowerCase().indexOf("f")+1, temp[3].length())));
	    						}
	    						if (lenght>4)
	    						{
	    							alarmLogTemp.setSlot(Integer.parseInt(temp[lenght-1]));
	    						}
	        					
	        				}
	        				
	        				
	        				if(temp[0].substring(0,1).equalsIgnoreCase("B")){
								network = "2G";
							}else if(temp[0].substring(0,1).equalsIgnoreCase("R")){
								network = "3G";
							}else if(temp[0].substring(0,1).equalsIgnoreCase("S")||temp[0].substring(0,1).equalsIgnoreCase("G")){
								network = "PS_CORE";
							}else{
								network = "CS_CORE";
							}
	        				alarmLogTemp.setNetwork(network);
	        			}
	        			if (ackTime!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", ackTime))
	        			{
	        				alarmLogTemp.setAckTime(df_full3.parse(ackTime));
	        					if (!ackTime.equals(df_full3.format(alarmLogTemp.getAckTime())))
	        					{
	        						alarmLogTemp.setAckTime(df_full.parse(ackTime));
	        					}
	        				
	        			}
	        			if (sdate!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", sdate))
	        			{
	        				
	        					alarmLogTemp.setSdate(df_full3.parse(sdate));
	        					if (!sdate.equals(df_full3.format(alarmLogTemp.getSdate())))
	        					{
	        						alarmLogTemp.setSdate(df_full.parse(sdate));
	        					}
	        				
	        			}
	        			if (edate!=null&&DateTools.isValid("MM/dd/yyyy HH:mm:ss", edate))
	        			{
	        				alarmLogTemp.setEdate(df_full3.parse(edate));
	    					if (!edate.equals(df_full3.format(alarmLogTemp.getEdate())))
	    					{
	    						alarmLogTemp.setEdate(df_full.parse(edate));
	    					}
	        				
	        			}
		        			//insert
		        			rAlarmLogTempDAO.insertToTemp(alarmLogTemp,"R_ALARM_LOG_TEMP");
		        			
	        			}
	        			catch(Exception exp)
	        			{
	        				System.out.println("------------------iNSERT Khong THANH CONG");
	        				System.out.println("ne_Site: "+ ne_Site);
	        				/*System.out.println("ne: "+ alarmLogTemp.getNe());
		    				System.out.println("sdate:"+sdate);
		    				System.out.println("edate:"+edate);
		    				System.out.println("ackTime:"+ackTime);
							System.out.println("ObjType:"+alarmLogTemp.getObjType());
							System.out.println("Bscport:"+alarmLogTemp.getBscport());
							System.out.println("Site:"+alarmLogTemp.getSite());
	    					System.out.println("Cellid:"+alarmLogTemp.getCellid());*/
							
	        				fallList.add(alarmLogTemp);
	        			}
	        		}
	    	}
			buffReader.close();
		}catch (Exception e) {
			System.out.println("strLine: "+line);
			saveMessageKey(request, "import.overMaxSize");
		}
		return fallList;
	} 
		//cap nhat trang thai ket thuc huy nhan tin
		@RequestMapping(value = "updateEdate", method = RequestMethod.GET)
		public String updateEdate(@RequestParam (required = true) String vendor,
				@RequestParam (required = true) String functionA,
				@RequestParam (required = true) String network,
				@RequestParam (required = true) String etime,
				@RequestParam(required = false) String causebyFinished,
				HttpServletRequest request, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(functionA,null,"UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			String gridDExit ="",gridSuccess="";
			if (vendor !=null && !vendor.equals(""))
			{
				RAlarmLogTemp information = rAlarmLogTempDAO.getInformaionInsert(vendor,username);
				if (information!=null)
				{
					if (information.getMinDate()!=null)
					{
						model.addAttribute("fromDate", "Thời gian từ: "+df_full.format(information.getMinDate()));
					}
					if (information.getMaxDate()!=null)
					{
						model.addAttribute("toDate", "Đến: "+df_full.format(information.getMaxDate()));
						
					}
					//model.addAttribute("countSuccess", "Insert: "+information.getQualityInsert()+"/"+information.getQuality()+" - Update:"+information.getQualityUpdate()+"/"+information.getQuality());
				}
				//do du lieu ra grid 2G,3G
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ALARM_LOG_TEMP");
				//Lay du lieu cac cot an hien cua grid 
				List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ALARM_LOG_TEMP");
				
				List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKeyAndKPI("ALARM_LOG_TEMP","statusUpdate");
				String hightlight = HelpTableConfigs.highLightCol(highlightConfigListCol,"gridSuccess");
				// grid success
				String urlS = "dataSuccessList.htm?vendor="+vendor+"&username="+username;
				gridSuccess = HelpTableConfigs.getGridPagingUrl(configList, "gridSuccess", urlS, "listSuccess", listSource, "menuSuccess", hightlight, null,null);
				
				// grid alarm don't have at file
				String urlD = "dataDontExit.htm?vendor="+vendor+"&username="+username;
				String hightlight2 = HelpTableConfigs.highLightCol(highlightConfigListCol,"gridDExit");
				gridDExit = HelpTableConfigs.getGridPagingUrl(configList, "gridDExit", urlD, "listSuccesD", listSource, "menuD", hightlight2, null,null);
				model.addAttribute("gridSuccess", gridSuccess);
				model.addAttribute("gridDExit", gridDExit);
				if (etime!=null && !etime.equals(""))
				{
					try {
						Date edate=df_full.parse(etime);
						//UPDATE
							try
							{
								//System.out.println("net work: "+network);
								int result = rAlarmLogTempDAO.updateEdate(username,vendor,network,etime,causebyFinished);
								if (result==1)// cap nhat thanh cong
								{
									String ms="Cập nhật thành công";
									if (network.equals("2G"))
									{
										ms+=" các cảnh báo active 2G";
									}
									if (network.equals("3G"))
									{
										ms+=" các cảnh báo active 3G";
									}
									if (network.contains("CORE"))
									{
										ms+=" các cảnh báo active Core";
									}
									model.addAttribute("messageUpdate",ms);
								}
								else if (result==0)// thoi gian ket thuc be hon thoi gian bat dau
								{
									//saveMessageKey(request, "import.importFall");
									String ms="Quá trình cập nhật thông tin";
									if (network.equals("2G"))
									{
										ms+=" cảnh báo active 2G";
									}
									if (network.equals("3G"))
									{
										ms+=" cảnh báo active 3G";
									}
									if (network.contains("CORE"))
									{
										ms+=" cảnh báo active Core";
									}
									ms+=" gặp lỗi: Thời gian kết thúc bé hơn thời gian bắt đầu";
									model.addAttribute("messageUpdate", ms);
								}
							}
							catch(Exception exp)
							{
								//saveMessageKey(request, "import.importFall");
								String ms="Quá trình cập nhật thông tin";
								if (network.equals("2G"))
								{
									ms+=" cảnh báo active 2G";
								}
								if (network.equals("3G"))
								{
									ms+=" cảnh báo active 3G";
								}
								if (network.contains("CORE"))
								{
									ms+=" cảnh báo active Core";
								}
								ms+=" gặp lỗi";
								model.addAttribute("messageUpdate", ms);
							}	
					} catch (ParseException e) {
						String ms="Thời gian kết thúc nhập vào không đúng định dạng";
						model.addAttribute("messageUpdate", ms);
					}
				}
				else
				{
					String ms="Chưa nhập thời gian kết thúc hoặc không có cảnh báo treo";
					model.addAttribute("messageUpdate", ms);
				}
				
			}
				// Lay ten file export
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
				String dateNow = formatter.format(currentDate.getTime());
				String exportFileNameS = "AlarmSuccess_" + dateNow;
				model.addAttribute("exportFileNameS", exportFileNameS);
				
				model.addAttribute("gridSuccess", gridSuccess);
				model.addAttribute("gridDExit", gridDExit);
				
				model.addAttribute("vendor", vendor);
				model.addAttribute("etime", etime);
				model.addAttribute("function", functionA);
				model.addAttribute("causebyFinished", causebyFinished);
				return "jspalarm/GSHeThong/importAlarm";
		}
		
		//cap nhat trang thai ket thuc huy nhan tin
		@RequestMapping(value = "updateEdateAll", method = RequestMethod.GET)
		public String updateEdateAll(@RequestParam (required = true) String vendor,@RequestParam (required = true) String function,
				HttpServletRequest request, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(function,null,"UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
		//	rAlarmLogTempDAO.updateAll(username,vendor);
			//* Xu ly dong bo du lieu history:
			//- Select ra danh sach alarm trong bang temp
			//- Kiem tra record da ton tai trong r_alarm_log_active chua? Neu chua thi insert, 
			//-neu da ton tai va co su sai khac thi cap nhat: cap nhat lai trang thai da thay doi.
			//-Nguoc lai khong lam gi: cap nhat trang thai khong lam gi
			//* Xu ly dong bo du lieu active:
			//- Select ra danh sach alarm active trong khoang thoi gian sdate< maxsdate trong temp va edate null.
			//- Kiem tra record da ton tai trong r_alarm_log_tenp chua? Neu ton tai thi cap nhat edate = sate + 10 phut. cap nhat trang thai da thay doi trong tep.
			//-Nguoc lai khong lam gi: cap nhat trang thai khong lam gi
			String gridDExit ="",gridSuccess="";
			if (vendor !=null && !vendor.equals(""))
			{
				try
				{
					rAlarmLogTempDAO.updateAll(username,vendor);
					model.addAttribute("messageInsert", "Cập nhật thành công");
				}
				catch(Exception exp)
				{
					//saveMessageKey(request, "import.importFall");
					model.addAttribute("messageInsert", "Quá trình cập nhật thông tin gặp lỗi");
				}
				//Lay thong tin mindate, maxdate, so dong insert trong alarmlogtemp
				RAlarmLogTemp information = rAlarmLogTempDAO.getInformaionInsert(vendor,username);
				if (information!=null)
				{
					if (information.getMinDate()!=null)
					{
						model.addAttribute("fromDate", "Thời gian từ: "+df_full.format(information.getMinDate()));
					}
					if (information.getMaxDate()!=null)
					{
						model.addAttribute("toDate", "Đến: "+df_full.format(information.getMaxDate()));
					}
					if (information.getQualityInsert()!=null)
					{
						model.addAttribute("countSuccess", "Insert: "+information.getQualityInsert()+"/"+information.getQuality()+" - Update:"+information.getQualityUpdate()+"/"+information.getQuality());
					}
					
				}
				//do du lieu ra grid 2G,3G
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ALARM_LOG_TEMP");
				//Lay du lieu cac cot an hien cua grid 
				List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ALARM_LOG_TEMP");
				
				List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKeyAndKPI("ALARM_LOG_TEMP","statusUpdate");
				String hightlight = HelpTableConfigs.highLightCol(highlightConfigListCol,"gridSuccess");
				// grid success
				String urlS = "dataSuccessList.htm?vendor="+vendor+"&username="+username;
				gridSuccess = HelpTableConfigs.getGridPagingUrl(configList, "gridSuccess", urlS, "listSuccess", listSource, "menuSuccess", hightlight, null,null);
				// grid alarm don't have at file
				String urlD = "dataDontExit.htm?vendor="+vendor+"&username="+username;
				String hightlight2 = HelpTableConfigs.highLightCol(highlightConfigListCol,"gridDExit");
				gridDExit = HelpTableConfigs.getGridPagingUrl(configList, "gridDExit", urlD, "listSuccesD", listSource, "menuD", hightlight2, null,null);
				
			}
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileNameS = "AlarmSuccess_" + dateNow;
			model.addAttribute("exportFileNameS", exportFileNameS);
			
			model.addAttribute("gridSuccess", gridSuccess);
			model.addAttribute("gridDExit", gridDExit);
			model.addAttribute("vendor", vendor);
			model.addAttribute("function", function);
			
			return "jspalarm/GSHeThong/importAlarm";
		}
		
}
