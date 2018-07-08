package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlHandingLibrary;
import vo.AlShift;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.RAlarmLog;
import vo.RAlarmLogTemp;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.AlAlarmTypesDAO;
import dao.AlHandingLibraryDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;

/*
 * Created by: AnhCTV
 * Created date: ..
 * Update by: ThangPX. Date:  24.07.2013
 * Content update: Form 2g,3g
*/
@Controller
@RequestMapping("/handing/*")
public class AlHandlingLibraryController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private AlHandingLibraryDAO alHandingLibraryDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	/*List danh sach thu vien xu ly
	 * @param sdate: Thoi gian bat dau
	 * @param edate: Thoi gian ket thuc
	 * @param bscid: Nhieu BSC
	 * @param cellid: Nhieu Cell
	 * @param vendor: Nha cung cap
	 * @param alarmName: Ten canh bao
	 * @param reload: Trang thai load form
	 * @param reloadStr: Trang thai load form
	 * @param timeLoad: Thoi gian load form
	 * function : 2G/3G
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmName,
			@RequestParam(required = false) String alarmMappingName,
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = alHandingLibraryDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		if (vendor!=null)
		{
			vendor=vendor.trim();
		}
		else
		{
			vendor="";
		}
		if (alarmType!=null)
		{
			alarmType=alarmType.trim();
		}
		else
		{
			alarmType="";
		}

		if (alarmName!=null)
		{
			alarmName=alarmName.trim();
		}
		else
		{
			alarmName="";
		}
		if (alarmMappingName!=null)
		{
			alarmMappingName=alarmMappingName.trim();
		}
		else
		{
			alarmMappingName="";
		}
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_HANDLING_LIBRARY");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_HANDLING_LIBRARY");
		//url
		String url = "dataHandling.htm?vendor="+vendor+"&alarmType="+alarmType+"&alarmName="+alarmName+"&alarmMappingName="+alarmMappingName;
		//Grid
		String gridHading = HelpTableConfigs.getGridPagingUrl(configList, "gridHading", url, "listHading", listSource, "Menu", null, "multiplerowsextended","Y");
		model.addAttribute("gridHading", gridHading);	
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AL_HANDLING_LIBRARY_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		
		model.addAttribute("alarmName", alarmName);
		model.addAttribute("vendor", vendor);
		model.addAttribute("alarmType", alarmType);
		model.addAttribute("alarmMappingName", alarmMappingName);
		
		return new ModelAndView("jspalarm/handingLibrary/handingList");
	}

	@RequestMapping("dataHandling")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmName,
			@RequestParam(required = false) String alarmMappingName,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<AlHandingLibrary> handingList = new ArrayList<AlHandingLibrary>();
		handingList = alHandingLibraryDAO.getHandingListAll(vendor,alarmType,alarmName,alarmMappingName);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(handingList);
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
				alHandingLibraryDAO.deleteByPrimaryKey(Integer.parseInt(id));
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
			List<SYS_PARAMETER> sysParemeterTitle = alHandingLibraryDAO.titleForm("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			AlHandingLibrary handing = new AlHandingLibrary();
			String ne="";
			String dict="";
			String alarmType="";
			String province="";
			if (id!=null&&!id.equals(""))
			{
				handing = alHandingLibraryDAO.selectByPrimaryKey(Integer.parseInt(id));
			}
			
			model.addAttribute("handing", handing);		
				
			return "jspalarm/handingLibrary/handingForm";
		}
		
		@RequestMapping(value = "form", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("handing") @Valid AlHandingLibrary handing, BindingResult result,
				@RequestParam(required = false) String vendor,
				@RequestParam(required = false) String alarmType,
				@RequestParam(required = false) String alarmName,
				@RequestParam(required = false) String alarmMappingName,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = alHandingLibraryDAO.titleForm("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			model.addAttribute("vendor",vendor);	
			model.addAttribute("alarmType",alarmType);	
			model.addAttribute("alarmName",alarmName);	
			model.addAttribute("alarmMappingName",alarmMappingName);	
			
			if (handing.getCauseby()!=null  && handing.getCauseby().length()>=900)
			{
				model.addAttribute("errorcauseby", "*");
				saveMessageKey(request, "message.error.overlenght");
				return "jspalarm/handingLibrary/handingForm";
			}
			if (handing.getActionProcess()!=null  && handing.getActionProcess().length()>=900)
			{
				model.addAttribute("erroractionProcess", "*");
				saveMessageKey(request, "message.error.overlenght");
				return "jspalarm/handingLibrary/handingForm";
			}
			if (handing.getDescription()!=null  && handing.getDescription().length()>=900)
			{
				model.addAttribute("errordescription", "*");
				saveMessageKey(request, "message.error.overlenght");
				return "jspalarm/handingLibrary/handingForm";
			}
			System.out.println("eror:"+result.getObjectName());
			if (result.hasErrors() ) {
				saveMessageKey(request, "alarmExtend.errorField");
				 return "jspalarm/handingLibrary/handingForm";
			}
			else
			{
				handing.setVendor(vendor);
				handing.setAlarmType(alarmType);
				handing.setAlarmMappingName(alarmMappingName);
				handing.setAlarmName(alarmName);
				
				if (handing.getId()==null)
				{
					handing.setCreateDate(new Date());
					handing.setCreatedBy(username);
					alHandingLibraryDAO.insert(handing);
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}
				else
				{
					//sua chua
					handing.setModifiedBy(username);
					handing.setModifyDate(new Date());
					alHandingLibraryDAO.updateByPrimaryKey(handing);
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
				}
			}
			model.addAttribute("vendor","");	
			model.addAttribute("alarmType","");	
			model.addAttribute("alarmName","");	
			model.addAttribute("alarmMappingName","");	
			return "redirect:list.htm";
		}
		
}
