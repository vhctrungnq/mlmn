package controller.alarm;



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

import controller.BaseController;

import vo.AlShift;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.AlDyRpErrorPower;
import vo.VAlHBsc;
import vo.WarningDipSame;
import vo.alarm.utils.DateTools;

import dao.AlDyRpErrorPowerDAO;
import dao.AlShiftDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlDyRpErrorPowerDAO;
import dao.VAlHBscDAO;
import dao.VAlHCellDAO;

@Controller
@RequestMapping("/ErrorPower/")
public class VAlDyRpErrorPowerController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired 
	private AlDyRpErrorPowerDAO alDyRpErrorPowerDAO;
	
	@Autowired 
	private VAlHBscDAO vAlHBscDAO;
	
	@Autowired 
	private VAlHCellDAO vAlHCellDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	// Lay id truc ca hien tai
	private AlShift getCaTrucHT(String username,String region) {
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		String sang = caList.get(0).getValue();
		String chieu = caList.get(1).getValue();
		String toi = caList.get(2).getValue();
		String caT = "";
		String ngayT = "";
		AlShift shift = new AlShift();
		Date currentTime = new Date();
		@SuppressWarnings("deprecation")
		int hour = currentTime.getHours();
		ngayT = df_full.format(currentTime);
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
		shift = alShiftDAO.checkRoleCaTruc(caT,ngayT, username, region);
		return shift;
	}
	@RequestMapping("list")
	public ModelAndView list(
			@RequestParam(required = false) String Sday,
			@RequestParam(required = false) String Eday,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String action,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpErrorPowerDAO.titleErrorPower(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<AlDyRpErrorPower> alarmList = new ArrayList<AlDyRpErrorPower>();
		
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (Sday == null || Sday.equals("")||(Sday!=null && !Sday.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", Sday+" 00:00:00")==false))
		{
			Calendar c= Calendar.getInstance();
			c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Sday = df_full.format(c.getTime());
		}
		if (Eday == null || Eday.equals("")||(Eday!=null && !Eday.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", Eday+" 00:00:00")==false))
		{
			Eday = df_full.format(cal.getTime());
		}
		if (site==null)
		{
			site="";
		}
		else
			site = site.trim();
		
		model.addAttribute("bscid", bscid);
		model.addAttribute("site", site);
		model.addAttribute("team", teamProcess);
		model.addAttribute("Sday", Sday);
		model.addAttribute("Eday", Eday);
		model.addAttribute("action", action);
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);

		List<String> bscidList = vAlHBscDAO.getBSC23GByTeam(teamProcess);
		model.addAttribute("bscidList", bscidList);
		if (action!=null && action.equals("startJob"))
		{
			try
			{
				alDyRpErrorPowerDAO.getStartJobErrorPower();
				saveMessageKey(request, "alarmExtend.startJobSuccefull");
				Eday = df_full.format(new Date());
			}
			catch(Exception exp)
			{
				saveMessageKey(request, "alarmExtend.startJobFall");
			}
		}
		try
		{
			if (site!=null && !site.equals("") )
				site = site.replace(" ", ",");
			if (bscid!=null && !bscid.equals(""))
				bscid = bscid.replace(" ", ",");
			alarmList = alDyRpErrorPowerDAO.getVAlDyRpErrorPowerFilter(Sday,Eday,bscid,site,teamProcess,order,column);
			
		}
		catch(Exception exp)
		{
			
		}
	model.addAttribute("alarmList", alarmList);
	
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "VAlRbErrorPower" + dateNow;
	model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCMatDien/LoiNguonList");
	}
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpErrorPowerDAO.titleErrorPower("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		AlDyRpErrorPower alarm = new AlDyRpErrorPower();
		if (id!=null)
		{
			alarm = alDyRpErrorPowerDAO.selectByPrimaryKey(Integer.parseInt(id));
		}
		model.addAttribute("sdate", alarm.getSdateStr() );
		model.addAttribute("team", alarm.getTeamProcess());
		model.addAttribute("vAlDyRpErrorPower", alarm);		
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		
		return "jsp/BCMatDien/LoiNguonForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("vAlDyRpErrorPower") @Valid AlDyRpErrorPower vAlDyRpErrorPower, BindingResult result,
			@RequestParam(required = false) List<String> userProcess,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String resultContent,
			ModelMap model, HttpServletRequest request) throws ParseException {
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpErrorPowerDAO.titleErrorPower("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		String userProc ="";
		if (userProcess!=null && userProcess.size()>0)
		{
			userProc= userProcess.get(0);
			for (int i=1;i<userProcess.size();i++)
			{
				userProc+=","+ userProcess.get(i);
			}
		}
		model.addAttribute("vAlDyRpErrorPower", vAlDyRpErrorPower);	
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		model.addAttribute("team", teamProcess);
		model.addAttribute("sdate",sdate);
		
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		Integer shiftId =null;
		if (shift!=null)
		{
			checkCaTruc=true;
			shiftId = shift.getShiftId();
		}
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			 return "jsp/BCMatDien/LoiNguonForm";
		}
		if (sdate==null || (sdate!=null && (sdate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdate+":00")==false)))
		{
			model.addAttribute("errorday", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/LoiNguonForm";
		}
		if (userProc.length()>255)
		{
			saveMessageKey(request, "alarmExtend.moreUse");
			 return "jsp/BCTruyenDan/LoiNguonForm";
		}
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/BCMatDien/LoiNguonForm";
		}
		else
		{
			vAlDyRpErrorPower.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate+":00"));
			vAlDyRpErrorPower.setTeamProcess(teamProcess);
			vAlDyRpErrorPower.setResultContent(resultContent);
			vAlDyRpErrorPower.setUserProcess(userProc);
			vAlDyRpErrorPower.setShiftId(shiftId);
			
			if (vAlDyRpErrorPower.getId()==null)
			{
					alDyRpErrorPowerDAO.insertSelective(vAlDyRpErrorPower);
					saveMessageKey(request, "vAlDyRpErrorPower.insertSucceFull");
			}	
			else{
					alDyRpErrorPowerDAO.updateByPrimaryKeySelective(vAlDyRpErrorPower);
					saveMessageKey(request, "vAlDyRpErrorPower.updateSuccelfull");	
				}	
		}	
		model.addAttribute("bscid", "");
		model.addAttribute("site",  "");
		model.addAttribute("userProcess", "");
		model.addAttribute("team", "");
		return "redirect:list.htm";
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			HttpServletRequest request, Model model) {	
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (checkCaTruc==true)
		{
			try {
				alDyRpErrorPowerDAO.deleteByPrimaryKey(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		}
		else
			saveMessageKey(request, "messsage.confirm.deletefailure");

		return "redirect:list.htm";
	}
}
