package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.AlAlarmWorks;
import vo.FbProcessFilter;
import vo.FbType;
import vo.SYS_PARAMETER;
import vo.SmsContents;

import dao.FbProcessDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/processFilter")
public class FbProcessFilterController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	
	
	@RequestMapping("/list")
	public ModelAndView list(
			@ModelAttribute("filter") @Valid FbProcessFilter filter,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String networkType,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = fbProcessDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "FB_TYPE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("fbRpProcess").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("fbRpProcess").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<FbProcessFilter> alarmList = new ArrayList<FbProcessFilter>();
		filter.setFbType(fbType);
		filter.setNetworkType(networkType);
		/*try
		{*/
			alarmList = fbProcessDAO.getProcessList(filter,column, order);	
		/*}
		catch (Exception exp)
		{
			alarmList= null;
		}*/
		model.addAttribute("fbProcessList", alarmList);
		model.addAttribute("fbType", fbType);
		model.addAttribute("networkType", networkType);
		model.addAttribute("filter", filter);
		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "FbProcessFilter_" + dateNow;
		model.addAttribute("exportFileName", exportFileName); 
		
		
		return new ModelAndView("jspfeedback/ProcessFilter");
	}
	
	@ModelAttribute("fbTypeList")
	public List<FbType> fbTypeList() {
		return fbProcessDAO.getFBTypeList();
	}
	
	@ModelAttribute("networkTypeList")
	public List<SYS_PARAMETER> networkTypeList() {
		return fbProcessDAO.networkTypeList();
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			HttpServletRequest request, Model model) {
	
		
			try {
				fbProcessDAO.deleteFbProcessFilter(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id,
			ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = fbProcessDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		FbProcessFilter fbProcess = new FbProcessFilter();
		
		if (id!=null)
		{
			fbProcess = fbProcessDAO.selectFbProcessFilterByID(Integer.parseInt(id));
			model.addAttribute("fbType", fbProcess.getFbType());
			model.addAttribute("networkType", fbProcess.getNetworkType());
		}
		model.addAttribute("fbProcess", fbProcess);		
		model.addAttribute("titleF", title);
		return "jspfeedback/ProcessFilterForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("fbProcess") @Valid FbProcessFilter fbProcess,
			BindingResult result,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String networkType,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SYS_PARAMETER> sysParemeterTitle = fbProcessDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		
		if (result.hasErrors()) {
			
			model.addAttribute("fbProcess", fbProcess);
			model.addAttribute("fbType", fbType);	
			model.addAttribute("networkType",networkType);
			model.addAttribute("titleF", title);
			saveMessageKey(request, "fbRpProcess.errorField");
			return "jspfeedback/ProcessFilterForm";
		}
		else
		{
			fbProcess.setFbType(fbType);
			fbProcess.setNetworkType(networkType);
			fbProcess.setModifyDate(new Date());
			fbProcess.setModifiedBy(username);
			fbProcessDAO.updateFbProcessFilter(fbProcess);
			saveMessageKey(request, "fbRpProcess.updateSuccelfull");	
				
		}
		
		
		return "redirect:list.htm";
	}
	@RequestMapping("insertHandle")
	public @ResponseBody
	String insertHandle(@RequestBody FbProcessFilter fbProcess) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		try {
			fbProcess.setModifyDate(new Date());
			fbProcess.setModifiedBy(username);
			fbProcessDAO.updateFbProcessFilter(fbProcess);
			return "1";
		} catch (Exception e) {
			return "0";
		} 

	}
}
