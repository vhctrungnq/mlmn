package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.AlMonitorSite;
import vo.AlMonitorTemperatureSite;
import vo.AlShift;
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import dao.AlAlarmTypesDAO;
import dao.AlMonitorSiteDAO;
import dao.AlMonitorTemperatureSiteDAO;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;

/*
 * Created by: AnhCTV
 * Created date: 15/11/2013
*/
@Controller
@RequestMapping("/monitor/*")
public class AlMonitorSiteController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private AlMonitorSiteDAO alMonitorSiteDAO;
	
	@Autowired
	private AlMonitorTemperatureSiteDAO alMonitorTemperatureSiteDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//	private DateFormat df_full1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	// Lay id truc ca hien tai
		private AlShift getCaTrucHT(String region)
		{
			List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
			caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
			String sang = caList.get(0).getValue();
			String chieu = caList.get(1).getValue();
			String toi = caList.get(2).getValue();
			String caT="";
			String ngayT="";
			AlShift shift = new AlShift();
			Date currentTime = new Date();
			int hour = currentTime.getHours();
			ngayT= df.format(currentTime);
			if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
				caT = "Sáng";
			}
			if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
				caT = "Chiều";
			}
			if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
				caT = "Tối";
			}
			// Lay idshift
			 shift = alShiftDAO.getCaTrucGanNhat(region);
			if (shift==null)
			{
				shift= alShiftDAO.getCaTruc(caT,ngayT,region);
			}
			return shift;
		}
		
	/* Theo doi trang thai site/cell
	 * @param sdate: Thoi gian bat dau
	 * @param edate: Thoi gian ket thuc
	 * @param site: Nhieu site
	 * @param content: content
	 * @param network: 2G/3G
	 */
	@RequestMapping(value = "sitelist")
	public ModelAndView sitelist(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String content,
			@RequestParam(required = false) String network,
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = alMonitorSiteDAO.titleForm("site",null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		if (sdate == null || sdate.equals("")
				||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdate)==false))
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			cal.add(Calendar.DATE,-7);
			sdate = df.format(cal.getTime())+" "+"00:00";
		}
		
		if (edate == null || edate.equals("")
				||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", edate)==false))
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			edate = df.format(cal.getTime())+" "+"23:59";
		}
		model.addAttribute("sdate", sdate);			
		model.addAttribute("edate", edate);
		sdate = sdate+":00";
		edate = edate+":00";
		if (site!=null)
		{
			site=site.replaceAll(" ", "");
		}
		else
		{
			site="";
		}
		
		if (content!=null)
		{
			content=content.trim();
		}
		else
		{
			content="";
		}
		if (network!=null)
		{
			network=network.trim();
		}
		else
		{
			network="";
		}

		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MONITOR_SITE");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MONITOR_SITE");
		//url
		String url = "dataMonitorSite.htm?sdate="+sdate+"&edate="+edate+"&network="+network+"&site="+site+"&content="+content;
		//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport);	
		
		//Lay danh sach Cell theo user
				List<String> cellList = sysUserEquipmentNameDAO.getCellList(network,null,null,null);
				//String cellListStr ="";
				String cellArray="var siteList = new Array(";
				String cn="";
				for (String cell : cellList) {
				//	cellListStr = cellListStr + cn +cell;
					cellArray = cellArray + cn +"\""+cell+"\"";
					cn=",";
				}
				cellArray = cellArray+");";
				model.addAttribute("siteList", cellArray);    
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AL_MONITOR_SITE_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		
		model.addAttribute("site", site);
		model.addAttribute("content", content);
		model.addAttribute("username", username);	
		model.addAttribute("network", network);	
		
		return new ModelAndView("jspalarm/GSSite/monitorSiteList");
	}

	@RequestMapping("dataMonitorSite")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String network,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String content,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<AlMonitorSite> monitorSiteList = new ArrayList<AlMonitorSite>();
		monitorSiteList = alMonitorSiteDAO.getMonitorSiteListAll(sdate,edate,network,site,content);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(monitorSiteList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	//Xoa
	@RequestMapping(value = "sitedelete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,
			HttpServletRequest request, Model model) {
		try {
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				System.out.println(id);
				alMonitorSiteDAO.deleteByPrimaryKey(Integer.parseInt(id));
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:sitelist.htm";
	}
	
	//Them, sua
		@RequestMapping(value = "siteform", method = RequestMethod.GET)
		public String form(@RequestParam (required = false) String id,
				HttpServletRequest request, Model model) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = alMonitorSiteDAO.titleForm("site","ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			AlMonitorSite monitorSite = new AlMonitorSite();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String sdate = "";
			String edate="";
			if (id!=null&&!id.equals(""))
			{
				monitorSite = alMonitorSiteDAO.selectByPrimaryKey(Integer.parseInt(id));
				sdate = monitorSite.getSdateStr();
				edate = monitorSite.getEdateStr();
			}
			else
			{
				sdate = df_full.format(cal.getTime());
			}
			
			model.addAttribute("monitorSite", monitorSite);		
			//Lay danh sach Cell theo user
			List<String> siteList = sysUserEquipmentNameDAO.getCellList(null,null,null,null);
			//String cellListStr ="";
			String cellArray="var siteList = new Array(";
			String cn="";
			for (String cell : siteList) {
			//	cellListStr = cellListStr + cn +cell;
				cellArray = cellArray + cn +"\""+cell+"\"";
				cn=",";
			}
			cellArray = cellArray+");";
			model.addAttribute("siteList", cellArray); 
			model.addAttribute("sdate", sdate); 
			model.addAttribute("edate", edate); 
			
			return "jspalarm/GSSite/monitorSiteForm";
		}
		
		@RequestMapping(value = "siteform", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("monitorSite") @Valid AlMonitorSite monitorSite, BindingResult result,
				@RequestParam(required = false) String network,
				@RequestParam(required = false) String site,
				@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = alMonitorSiteDAO.titleForm("site","ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			//Lay danh sach Cell theo user
			List<String> cellList = sysUserEquipmentNameDAO.getCellList(null,null,null,null);
			//String cellListStr ="";
			String cellArray="var siteList = new Array(";
			String cn="";
			for (String cell : cellList) {
			//	cellListStr = cellListStr + cn +cell;
				cellArray = cellArray + cn +"\""+cell+"\"";
				cn=",";
			}
			cellArray = cellArray+");";
			
			monitorSite.setNetwork(network);
			monitorSite.setSite(site);
			
		if (sdate==null || (sdate!=null && (sdate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdate+":00")==false)))
		{
			model.addAttribute("errorsdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("siteList", cellArray); 
			model.addAttribute("sdate", sdate); 
			model.addAttribute("edate", edate); 
			model.addAttribute("monitorSite", monitorSite); 
			return "jspalarm/GSSite/monitorSiteForm";
		}
		if (edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", edate+":00")==false)
		{
			model.addAttribute("erroredate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("siteList", cellArray); 
			model.addAttribute("sdate", sdate); 
			model.addAttribute("edate", edate); 
			model.addAttribute("monitorSite", monitorSite); 
			return "jspalarm/GSSite/monitorSiteForm";
		}
		if (monitorSite.getNetwork()==null)
		{
			model.addAttribute("errorNetwork", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("siteList", cellArray); 
			model.addAttribute("sdate", sdate); 
			model.addAttribute("edate", edate); 
			model.addAttribute("monitorSite", monitorSite); 
			return "jspalarm/GSSite/monitorSiteForm";
		}
		if (monitorSite.getSite()==null)
		{
			model.addAttribute("errorSite", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("siteList", cellArray); 
			model.addAttribute("sdate", sdate); 
			model.addAttribute("edate", edate); 
			model.addAttribute("monitorSite", monitorSite); 
			return "jspalarm/GSSite/monitorSiteForm";
		}
			if (monitorSite.getCauseby()!=null  && monitorSite.getCauseby().length()>=400)
			{
				model.addAttribute("errorcauseby", "*");
				saveMessageKey(request, "message.error.overlenght");
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("sdate", sdate); 
				model.addAttribute("edate", edate); 
				model.addAttribute("monitorSite", monitorSite); 
				return "jspalarm/GSSite/monitorSiteForm";
			}
			if (monitorSite.getContent()!=null  && monitorSite.getContent().length()>=400)
			{
				model.addAttribute("errorContenty", "*");
				saveMessageKey(request, "message.error.overlenght");
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("sdate", sdate); 
				model.addAttribute("edate", edate); 
				model.addAttribute("monitorSite", monitorSite); 
				return "jspalarm/GSSite/monitorSiteForm";
			}
			
			if (monitorSite.getDescription()!=null  && monitorSite.getDescription().length()>=400)
			{
				model.addAttribute("errordescription", "*");
				saveMessageKey(request, "message.error.overlenght");
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("sdate", sdate); 
				model.addAttribute("edate", edate); 
				model.addAttribute("monitorSite", monitorSite); 
				return "jspalarm/GSSite/monitorSiteForm";
			}
			if (monitorSite.getProcessUser()!=null  && monitorSite.getProcessUser().length()>=50)
			{
				model.addAttribute("errorProcessUser", "*");
				saveMessageKey(request, "message.error.overlenght");
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("sdate", sdate); 
				model.addAttribute("edate", edate); 
				model.addAttribute("monitorSite", monitorSite); 
				return "jspalarm/GSSite/monitorSiteForm";
			}
			if (monitorSite.getRequierUser()!=null  && monitorSite.getRequierUser().length()>=50)
			{
				model.addAttribute("errorRequierUser", "*");
				saveMessageKey(request, "message.error.overlenght");
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("sdate", sdate); 
				model.addAttribute("edate", edate); 
				model.addAttribute("monitorSite", monitorSite); 
				return "jspalarm/GSSite/monitorSiteForm";
			}
			System.out.println("eror:"+result.getObjectName());
			if (result.hasErrors() ) {
				saveMessageKey(request, "alarmExtend.errorField");
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("sdate", sdate); 
				model.addAttribute("edate", edate); 
				model.addAttribute("monitorSite", monitorSite); 
				return "jspalarm/GSSite/monitorSiteForm";
			}
			else
			{
				if (sdate != null && !sdate.equals("")) {
					monitorSite.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate+":00"));
				}
				if (edate != null && !edate.equals("")) {
					monitorSite.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate+":00"));
				}
				if (monitorSite.getId()==null)
				{
					monitorSite.setCreateDate(new Date());
					monitorSite.setCreatedBy(username);
					alMonitorSiteDAO.insert(monitorSite);
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}
				else
				{
					//sua chua
					monitorSite.setModifiedBy(username);
					monitorSite.setModifyDate(new Date());
					alMonitorSiteDAO.updateByPrimaryKey(monitorSite);
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
				}
			}
			return "redirect:sitelist.htm";
		}
		
		/* Theo doi nhiet do site/cell
		 * @param sdate: Thoi gian bat dau
		 * @param edate: Thoi gian ket thuc
		 * @param site: Nhieu Cell/site
		 */
		@RequestMapping(value = "temperaturelist")
		public ModelAndView temperaturesitelist(
				@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				@RequestParam(required = false) String site,
				@RequestParam(required = false) String region,
				Model model, HttpServletRequest request) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = alMonitorSiteDAO.titleForm("temperature",null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			if (sdate == null || sdate.equals("")
					||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdate)==false))
			{
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				sdate = df.format(cal.getTime())+" "+"00:00";
			}
			
			if (edate == null || edate.equals("")
					||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", edate)==false))
			{
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				edate = df.format(cal.getTime())+" "+"23:59";
			}
			model.addAttribute("sdate", sdate);			
			model.addAttribute("edate", edate);
			sdate = sdate+":00";
			edate = edate+":00";
			if (site!=null)
			{
				site=site.trim();
			}
			else
			{
				site="";
			}
			
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MONITOR_TEMPERATURE");
			//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MONITOR_TEMPERATURE");
			//url
			String url = "dataTemperatureSite.htm?sdate="+sdate+"&edate="+edate+"&site="+site+"&region="+region;
			//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport);	
			
			//Lay danh sach Cell theo user
			List<String> siteList = alMonitorTemperatureSiteDAO.getSiteList();
			//String cellListStr ="";
			String cellArray="var siteList = new Array(";
			String cn="";
			for (String cell : siteList) {
			//	cellListStr = cellListStr + cn +cell;
				cellArray = cellArray + cn +"\""+cell+"\"";
				cn=",";
			}
			cellArray = cellArray+");";
			model.addAttribute("siteList", cellArray);    
					
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "AL_MONITOR_TEMPERATURE_SITE_"+ dateNow;
			model.addAttribute("exportFileName", exportFileName);
			
			
			model.addAttribute("site", site);
			model.addAttribute("username", username);	
			model.addAttribute("region", region);	
			return new ModelAndView("jspalarm/GSSite/monitorTemperatureList");
		}

		@RequestMapping("dataTemperatureSite")
		public @ResponseBody 
		void dataTemperatureSite(@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				@RequestParam(required = false) String site,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			
			List<AlMonitorTemperatureSite> monitorSiteList = new ArrayList<AlMonitorTemperatureSite>();
			monitorSiteList = alMonitorTemperatureSiteDAO.getMonitorTemperatureSiteList(sdate,edate,site);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(monitorSiteList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		 }
		
		//Xoa
		@RequestMapping(value = "temperaturedelete", method = RequestMethod.GET)
		public String temperaturedelete(@RequestParam (required = true) String idList,
				@RequestParam (required = false) String note,
				@RequestParam (required = false) String region,
				HttpServletRequest request, Model model) {
			try {
				String[] idArray = idList.split(",");
				for (String id : idArray) {
					System.out.println(id);
					alMonitorTemperatureSiteDAO.deleteByPrimaryKey(Integer.parseInt(id));
				}
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			String path="";
			if (note.equals("caTruc"))
			{
				path="redirect:/caTruc/list.htm?warningTp=TEMPERATURE_SITE"+"&region="+region;
			}
			else if (note.equalsIgnoreCase("filter")) {
				path= "redirect:/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE"+"&region="+region;
			}
			else
				path="redirect:temperaturelist.htm";
			return path;
		}
		
		//Them, sua
			@RequestMapping(value = "temperatureForm", method = RequestMethod.GET)
			public String temperatureForm(@RequestParam (required = false) String id,
					@RequestParam (required = true) String note,
					@RequestParam (required = false) String region,
					
					HttpServletRequest request, Model model) {
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				List<SYS_PARAMETER> sysParemeterTitle = alMonitorSiteDAO.titleForm("temperature","ADD");
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
				}
				AlMonitorTemperatureSite monitorSite = new AlMonitorTemperatureSite();
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String checkDate = "";
				if (id!=null&&!id.equals(""))
				{
					monitorSite = alMonitorTemperatureSiteDAO.selectByPrimaryKey(Integer.parseInt(id));
					checkDate = monitorSite.getCheckDateStr();
				}
				else
				{
					checkDate = df_full.format(cal.getTime());
				}
				model.addAttribute("monitorSite", monitorSite);		
				
				//Lay danh sach Cell theo user
				List<String> siteList =alMonitorTemperatureSiteDAO.getSiteList();
				//String cellListStr ="";
				String cellArray="var siteList = new Array(";
				String cn="";
				for (String cell : siteList) {
				//	cellListStr = cellListStr + cn +cell;
					cellArray = cellArray + cn +"\""+cell+"\"";
					cn=",";
				}
				cellArray = cellArray+");";
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("checkDate", checkDate); 
				model.addAttribute("note", note);	
				model.addAttribute("region", region);	
				
				return "jspalarm/GSSite/monitorTemperatureForm";
			}
			
			@RequestMapping(value = "temperatureForm", method = RequestMethod.POST)
			public String onSubmitTemperature(@ModelAttribute("monitorSite") @Valid AlMonitorTemperatureSite monitorSite, BindingResult result,
					@RequestParam(required = false) String checkDate,
					@RequestParam (required = false) String note,
					@RequestParam (required = false) String region,
					ModelMap model, HttpServletRequest request) throws ParseException {
				//Duong dan tra ve
				String path="";
				if (note.equals("caTruc"))
				{
					path="redirect:/caTruc/list.htm?warningTp=TEMPERATURE_SITE"+"&region="+region;
				}
				else if (note.equalsIgnoreCase("filter")) {
					path= "redirect:/caTruc/listFilter.htm?warningTp=TEMPERATURE_SITE"+"&region="+region;
				}
				else
					path="redirect:temperaturelist.htm";
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				SysUsers userLogin = sysUsersDao.selectByUsename(username);
				AlShift shift = getCaTrucHT(userLogin.getRegion());
				
				List<SYS_PARAMETER> sysParemeterTitle = alMonitorSiteDAO.titleForm("temperature","ADD");
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
				}
				boolean checkCaTruc= false;
				int shiftID = 1;
				if (shift!=null)
				{
					String[] user = shift.getNhanCaVien().split(",");
					if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
					{
						checkCaTruc=true;
					}
					for (String item : user) {
						if (item.equals(username))
						{
							checkCaTruc=true;
							break;
						}
					}
					model.addAttribute("checkCaTruc", checkCaTruc);
					shiftID = shift.getShiftId();
				}
				else
				{
					checkCaTruc=true;
				}
				
				if (checkCaTruc==false)
				{
					saveMessageKey(request, "cautruc.KhongCoQuyen");
					model.addAttribute("note", note);
					model.addAttribute("region", region);
					
					return "jspalarm/GSSite/monitorTemperatureForm";
				}
				//Lay danh sach Cell theo user
				List<String> siteList = alMonitorTemperatureSiteDAO.getSiteList();
				//String cellListStr ="";
				String cellArray="var siteList = new Array(";
				String cn="";
				for (String cell : siteList) {
				//	cellListStr = cellListStr + cn +cell;
					cellArray = cellArray + cn +"\""+cell+"\"";
					cn=",";
				}
				cellArray = cellArray+");";
			String error="";
			if (checkDate==null || (checkDate!=null && (checkDate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", checkDate+":00")==false)))
			{
				model.addAttribute("errorsdate", "*");
				error= "alarmExtend.errorField";
			}
			
			if (monitorSite.getSite()!=null  && monitorSite.getSite().length()>=80)
			{
				model.addAttribute("errorSite", "*");
				error= "alarmExtend.errorField";
			}
			if (monitorSite.getContactUser()!=null  && monitorSite.getContactUser().length()>=150)
			{
				model.addAttribute("errorcontactUser", "*");
				error= "alarmExtend.errorField";
			}
			if (monitorSite.getPhoneNumber()!=null  && monitorSite.getPhoneNumber().length()>=20)
			{
				model.addAttribute("errorphoneNumber", "*");
				error= "alarmExtend.errorField";
			}
			
			if (monitorSite.getDescription()!=null  && monitorSite.getDescription().length()>=400)
			{
				model.addAttribute("errordescription", "*");
				error= "alarmExtend.errorField";
			}
			if (monitorSite.getStatusElectricity()!=null  && monitorSite.getStatusElectricity().length()>=150)
			{
				model.addAttribute("errorstatusElectricity", "*");
				error= "alarmExtend.errorField";
			}
			if (monitorSite.getStatusFridge()!=null  && monitorSite.getStatusFridge().length()>=150)
			{
				model.addAttribute("errorstatusFridge", "*");
				error= "alarmExtend.errorField";
			}
			System.out.println("eror:"+result.getObjectName());
			if (result.hasErrors()||!error.equals("")) {
				error= "alarmExtend.errorField";
				saveMessageKey(request, error);
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("checkDate", checkDate); 
				model.addAttribute("monitorSite", monitorSite); 
				model.addAttribute("siteList", cellArray); 
				model.addAttribute("checkDate", checkDate); 
				model.addAttribute("monitorSite", monitorSite); 
				model.addAttribute("note", note);
				model.addAttribute("region", region);
				return "jspalarm/GSSite/monitorTemperatureForm";
			}
			else
			{
				System.out.println("checkDate:"+checkDate);
				if (checkDate != null && !checkDate.equals("")) {
					System.out.println(checkDate.substring(0,10));
					monitorSite.setDay((new SimpleDateFormat("dd/MM/yyyy")).parse(checkDate.substring(0,10)));
					monitorSite.setCheckDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(checkDate+":00"));
				}
				monitorSite.setShiftID(shiftID);
				if (monitorSite.getId()==null)
				{
					monitorSite.setCreateDate(new Date());
					monitorSite.setCreatedBy(username);
					alMonitorTemperatureSiteDAO.insert(monitorSite);
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}
				else
				{
					//sua chua
					monitorSite.setModifiedBy(username);
					monitorSite.setModifyDate(new Date());
					alMonitorTemperatureSiteDAO.updateByPrimaryKey(monitorSite);
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
				}
			}
			return path;
		}
		

}
