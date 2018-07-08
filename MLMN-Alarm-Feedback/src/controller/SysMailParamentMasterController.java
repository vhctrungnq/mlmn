package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
 
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysMailParameterMaster;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import dao.AlAlarmTypesDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysMailParameterMasterDAO;

/*
 * Created by: AnhNT
 * Created date: 09.12.2013
*/
@Controller
@RequestMapping("/dinh-nghia-mail/*")
public class SysMailParamentMasterController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private SysMailParameterMasterDAO sysMailParamenterMasterDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;
	 
	@RequestMapping(value = "list")
	public ModelAndView list(
			@RequestParam(required = false) String mailLevel,
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = sysMailParamenterMasterDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		} 
		
		if (mailLevel!=null) 
			mailLevel=mailLevel.trim(); 
		else 
			mailLevel=""; 
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("SYS_MAIL_PARAMETER_MASTER");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("SYS_MAIL_PARAMETER_MASTER");
		//url
		String url = "dataMailParameterMaster.htm?mailLevel="+mailLevel;
		//Grid
		String gridMailParameterMaster = HelpTableConfigs.getGridPagingUrl(configList, "gridMailParameterMaster", url, "listMailParameterMaster", listSource, "Menu", null, "multiplerowsextended","Y");
		model.addAttribute("gridMailParameterMaster", gridMailParameterMaster);	
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SYS_MAIL_PARAMETER_MASTER_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		
		model.addAttribute("mailLevel", mailLevel); 
	
		
		return new ModelAndView("jspalarm/sysMailParameterMaster/mailMasterList");
	}

	@RequestMapping("dataMailParameterMaster")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String  mailLevel,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<SysMailParameterMaster> mailMasterList = new ArrayList<SysMailParameterMaster>();
		mailMasterList = sysMailParamenterMasterDAO.getMailParameterMasterListAll(mailLevel,null);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(mailMasterList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	//Xoa
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,
			HttpServletRequest request, Model model) {
		try {
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				System.out.println(id);
				sysMailParamenterMasterDAO.deleteByPrimaryKey(Integer.parseInt(id));
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	//Them, sua
		@RequestMapping(value = "form", method = RequestMethod.GET)
		public String form(@RequestParam (required = true) String id,
				HttpServletRequest request, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = sysMailParamenterMasterDAO.titleForm("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			SysMailParameterMaster mailMaster = new SysMailParameterMaster(); 
			
			if (id!=null&&!id.equals(""))
			{
				mailMaster = sysMailParamenterMasterDAO.selectByPrimaryKey(Integer.parseInt(id));
			}
			
			model.addAttribute("mailMaster", mailMaster);		
			//Danh sach nguoi nhan mail
			List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
	 		alarmList = sysDefineSmsEmailDAO.getListAll("MAIL", null, null);
	 		String groupArray="var groupList = new Array(";
			String cn="";
			for (SysDefineSmsEmail sys : alarmList) {
				groupArray = groupArray + cn +"\""+sys.getGroupName()+"\"";
				cn=",";
			}
			groupArray = groupArray+");";
			model.addAttribute("groupList", groupArray);
			return "jspalarm/sysMailParameterMaster/mailMasterForm";
		}
		
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("mailMaster") @Valid SysMailParameterMaster mailMaster, BindingResult result,
			@RequestParam(required = false) String mailLevel,
			@RequestParam(required = false) String isEnable,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = sysMailParamenterMasterDAO.titleForm("ADD");
		//Danh sach nguoi nhan mail
		List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
 		alarmList = sysDefineSmsEmailDAO.getListAll("MAIL", null, null);
 		String groupArray="var groupList = new Array(";
		String cn="";
		for (SysDefineSmsEmail sys : alarmList) {
			groupArray = groupArray + cn +"\""+sys.getGroupName()+"\"";
			cn=",";
		}
		groupArray = groupArray+");";
		model.addAttribute("groupList", groupArray);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		mailMaster.setMailLevel(mailLevel);
		mailMaster.setIsEnable(isEnable);
		
		if (mailMaster.getMailName()!=null  && mailMaster.getMailName().length()>=500)
		{
			model.addAttribute("errors", "*");
			saveMessageKey(request, "message.error.overlenght");
			return "jspalarm/sysMailParameterMaster/mailMasterForm";
		}
		if (mailMaster.getTypeMailHour()!=null  && mailMaster.getTypeMailHour().length()>=15)
		{
			model.addAttribute("errors", "*");
			saveMessageKey(request, "message.error.overlenght");
			return "jspalarm/sysMailParameterMaster/mailMasterForm";
		} 
		System.out.println("eror:"+result.getObjectName());
		if (result.hasErrors() ) {
			saveMessageKey(request, "alarmExtend.errorField");
			return "jspalarm/sysMailParameterMaster/mailMasterForm";
		}
		else
		{
			
			if (mailMaster.getId()==null)
			{
				sysMailParamenterMasterDAO.insertSelective(mailMaster);
				saveMessageKey(request, "alarmExtend.insertSucceFull");
			}
			else
			{
				//sua chua
				sysMailParamenterMasterDAO.updateByPrimaryKeySelective(mailMaster);
				saveMessageKey(request, "alarmExtend.updateSuccelfull");	
			}
		}
		model.addAttribute("mailMaster", mailMaster);
		
		model.addAttribute("mailLevel",mailLevel);	
		model.addAttribute("isEnable", isEnable);
		return "redirect:list.htm";
	} 
} 