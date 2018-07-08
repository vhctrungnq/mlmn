package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import vo.AlShift;
import vo.HQualityNetwork;
import vo.SYS_PARAMETER;
import vo.SysUsers;

import dao.AlShiftDAO;
import dao.HQualityNetworkDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/qualityNetwork")
public class HQualityNetworkController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired 
	private HQualityNetworkDAO hQualityNetworkDAO;
	
	private DateFormat df_full1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false) String groupName,
			@RequestParam(required = false) String qualityName,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
	
		List<SYS_PARAMETER> sysParemeterTitle = hQualityNetworkDAO.titleQualityNetwork(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		if (qualityName==null)
		{
			qualityName="";
		}
		else
			qualityName = qualityName.trim();
		int order = 0;
		String column = "GROUP_NAME";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<HQualityNetwork> alarmList = new ArrayList<HQualityNetwork>();
		try
		{
			alarmList = hQualityNetworkDAO.getQualityNetworkList(groupName,qualityName, column, order);	
		}
		catch (Exception exp)
		{
			alarmList= null;
		}
		model.addAttribute("alarmList", alarmList);
		
		List<SYS_PARAMETER> groupNameList = sysParameterDao.getGroupQualityList();
		model.addAttribute("groupNameList", groupNameList);
		
		List<String> qualityNameList = hQualityNetworkDAO.getQualityName(groupName);
		model.addAttribute("qualityNameList", qualityNameList);
		
		model.addAttribute("groupName", groupName);
		model.addAttribute("qualityName", qualityName);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "HQualityNetwork_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		
		return new ModelAndView("jsp/QualityNetwork/qualityList");
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			HttpServletRequest request, Model model) {
	
		try {
			hQualityNetworkDAO.deleteByPrimaryKey(Integer.parseInt(id));
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
		
		List<SYS_PARAMETER> sysParemeterTitle = hQualityNetworkDAO.titleQualityNetwork("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		HQualityNetwork hQualityNetwork = new HQualityNetwork();
		
		if (id!=null)
		{
			hQualityNetwork = hQualityNetworkDAO.selectByPrimaryKey(Integer.parseInt(id));
		}
		model.addAttribute("hQualityNetwork", hQualityNetwork);		
		
		List<SYS_PARAMETER> groupNameList = sysParameterDao.getGroupQualityList();
		model.addAttribute("groupNameList", groupNameList);
		
		List<SYS_PARAMETER> unitList = hQualityNetworkDAO.getUnitList();
		model.addAttribute("unitList", unitList);
		
		List<SYS_PARAMETER> conditionList = hQualityNetworkDAO.getConditionList();
		model.addAttribute("conditionList", conditionList);
		
		model.addAttribute("groupName", hQualityNetwork.getGroupName());
		model.addAttribute("qualityUnit", hQualityNetwork.getQualityUnit());
		model.addAttribute("qualityCondition", hQualityNetwork.getQualityCondition());	
		
		return "jsp/QualityNetwork/qualityForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("hQualityNetwork") @Valid HQualityNetwork hQualityNetwork,
			BindingResult result,
			@RequestParam(required = false) String groupName,
			@RequestParam(required = false) String qualityCondition,
			@RequestParam(required = false) String qualityUnit,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		List<SYS_PARAMETER> sysParemeterTitle = hQualityNetworkDAO.titleQualityNetwork("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		
		List<SYS_PARAMETER> groupNameList = sysParameterDao.getGroupQualityList();
		
		List<SYS_PARAMETER> unitList = hQualityNetworkDAO.getUnitList();
	
		List<SYS_PARAMETER> conditionList = hQualityNetworkDAO.getConditionList();
		
		if (result.hasErrors()) {
			model.addAttribute("conditionList", conditionList);
			model.addAttribute("unitList", unitList);
			model.addAttribute("groupNameList", groupNameList);
			model.addAttribute("hQualityNetwork", hQualityNetwork);	
			model.addAttribute("titleF", title);
			model.addAttribute("groupName",groupName);
			model.addAttribute("qualityCondition", qualityCondition);
			model.addAttribute("qualityUnit", qualityUnit);
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/QualityNetwork/qualityForm";
		}
		else
		{
			hQualityNetwork.setGroupName(groupName);
			hQualityNetwork.setQualityCondition(qualityCondition);
			hQualityNetwork.setQualityUnit(qualityUnit);
			if (hQualityNetwork.getId()==null)
			{
					hQualityNetwork.setCreateDate(new Date());
					hQualityNetwork.setCreatedBy(username);
					hQualityNetworkDAO.insertSelective(hQualityNetwork);
					saveMessageKey(request, "hQualityNetwork.insertSucceFull");
			}		
			else
			{
					hQualityNetwork.setModifyDate(new Date());
					hQualityNetwork.setModifiedBy(username);
					hQualityNetworkDAO.update(hQualityNetwork);
					saveMessageKey(request, "hQualityNetwork.updateSuccelfull");	
			}
				
		}
		return "redirect:list.htm";
	}

}
