package controller.alarm;

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

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
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

import vo.AlDyRpDip;
import vo.AlDyRpDip3Day;
import vo.AlDyRpErrorDip;
import vo.AlDyRpTotal3g;
import vo.AlShift;
import vo.DetailLostConfig;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.VAlAlarmLog;
import vo.VAlHBsc;
import vo.VAlRbLossComunication;
import vo.WarningDipSame;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;
import vo.alarm_site.BCTongHop3G;
import dao.AlDyRpDipDAO;
import dao.AlDyRpErrorDipDAO;
import dao.AlShiftDAO;
import dao.HAlTransErrorTypeDAO;
import dao.HAlTransTypeDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlAlarmLogDAO;
import dao.VAlHBscDAO;
import dao.VAlHCellDAO;
import dao.VAlRbLossComunicationDAO;

@Controller
@RequestMapping("/ErrorDip/")
public class MatTruyenDanController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired 
	private AlDyRpDipDAO alDyRpDipDAO;
	
	@Autowired 
	private AlDyRpErrorDipDAO alDyRpErrorDipDAO;
	
	@Autowired 
	private VAlHBscDAO vAlHBscDAO;
	
	@Autowired 
	private VAlHCellDAO vAlHCellDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired 
	private VAlAlarmLogDAO vAlAlarmLogDAO;
	
	@Autowired 
	private HAlTransTypeDAO hAlTransTypeDAO;
	
	@Autowired 
	private HAlTransErrorTypeDAO hAlTransErrorTypeDAO;
	
	@Autowired 
	private VAlRbLossComunicationDAO vAlRbLossComunicationDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	// Lay id truc ca hien tai
	private AlShift getCaTrucHT(String username,String region)
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
		ngayT= df_full.format(currentTime);
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
	
	
	@ModelAttribute("teamList")
	public List<MDepartment> teamList() {
		return mDepartmentDAO.getDepartementBySystem();
		
	}
	
	@ModelAttribute("transTypeList")
	public List<String> transTypeList() {
		return hAlTransTypeDAO.getNameTransTypeList();
	}
	
	@ModelAttribute("faultTypeList")
	public List<String> faultTypeList() {
		return hAlTransErrorTypeDAO.getNameTransErrorType();
	}
	  
	
	@RequestMapping("mat_han")
	public ModelAndView list(
			@RequestParam(required = false) String Sday,
			@RequestParam(required = false) String Eday,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String dip,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String dvtUserProcess,
			@RequestParam(required = false) String faultType,
			@RequestParam(required = false) String transType,
			
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpErrorDipDAO.titleErrorDip(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (dip==null)
		{
			dip="";
		}
		else
			dip = dip.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		if (dvtUserProcess==null)
		{
			dvtUserProcess="";
		}
		else
			dvtUserProcess = dvtUserProcess.trim();
		int order = 0;
		String column = "BSCID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<AlDyRpErrorDip> alarmList = new ArrayList<AlDyRpErrorDip>();
		
		if(Sday == null|| Eday == null
				||(Sday!=null && !Sday.equals("") && DateTools.isValid("dd/MM/yyyy", Sday)==false)
				||(Eday!=null && !Eday.equals("") && DateTools.isValid("dd/MM/yyyy", Eday)==false)	)
			{
				long time = System.currentTimeMillis();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date startDay = dt.toDate();
				Date destDay = new Date(time);
				Sday   = df_full.format(startDay);
				Eday 	= df_full.format(destDay);
			}
		model.addAttribute("bscid", bscid);
		model.addAttribute("team", teamProcess);
		model.addAttribute("Sday", Sday);
		model.addAttribute("Eday", Eday);
		model.addAttribute("dip", dip);
		model.addAttribute("dvtUserProcess", dvtUserProcess);
		model.addAttribute("faultType", faultType);
		model.addAttribute("transType", transType);
		
		List<String> bscidList = vAlHBscDAO.getBSC23GByTeam(teamProcess);
		model.addAttribute("bscidList", bscidList);
	
		
	/*	try
		{*/
			if (bscid!=null && !bscid.equals("") )
				bscid = bscid.replace(" ", ",");
			if (dip!=null && !dip.equals("") )
				dip = dip.replace(" ", ",");
			alarmList = alDyRpErrorDipDAO.getAlDyRpErrorDipFilter(Sday,Eday,bscid,dip,teamProcess,dvtUserProcess,transType,faultType,order,column);
		/*}
		catch(Exception exp)
		{
			alarmList =null;
		}
		*/
		
		model.addAttribute("alarmList", alarmList);
		
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "AlRbErrorDip" + dateNow;
	model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCTruyenDan/DipMatHanList");
	}
	@RequestMapping(value = "formMH", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpErrorDipDAO.titleErrorDip("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		
		AlDyRpErrorDip alarm = new AlDyRpErrorDip();
		if (id!=null)
		{
			alarm = alDyRpErrorDipDAO.selectByPrimaryKey(Integer.parseInt(id));
		}
		model.addAttribute("day", alarm.getDayStr() );
		model.addAttribute("team", alarm.getTeamProcess());
		model.addAttribute("alDyRpErrorDip", alarm);		
		model.addAttribute("transType", alarm.getTransType());
		model.addAttribute("faultType", alarm.getFaultType());
		model.addAttribute("sdate", alarm.getSdateStr() );
		model.addAttribute("edate", alarm.getEdateStr() );
		
		return "jsp/BCTruyenDan/DipMatHanForm";
	}
	
	@RequestMapping(value = "formMH", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("alDyRpErrorDip") @Valid AlDyRpErrorDip alDyRpErrorDip, BindingResult result,
			@RequestParam(required = false) List<String> userProcess,
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String causebyContent,
			@RequestParam(required = false) String resultContent,
			@RequestParam(required = false) String faultType,
			@RequestParam(required = false) String transType,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpErrorDipDAO.titleErrorDip("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		String userProc ="";
		if (userProcess!=null && userProcess.size()>0)
		{
			userProc= userProcess.get(0);
			for (int i=1;i<userProcess.size();i++)
			{
				userProc+=","+ userProcess.get(i);
			}
		}
		
		model.addAttribute("alDyRpErrorDip", alDyRpErrorDip);	
	
		model.addAttribute("team", teamProcess);
		model.addAttribute("day",day);
		
		model.addAttribute("faultType", faultType);
		model.addAttribute("transType",transType);
		
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
		}
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			model.addAttribute("team", teamProcess);
			model.addAttribute("day",day);
			
			model.addAttribute("faultType", faultType);
			model.addAttribute("transType",transType);
			 return "jsp/BCTruyenDan/DipMatHanForm";
		}
		if (day==null || (day!=null && (day.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false)))
		{
			model.addAttribute("errorday", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("team", teamProcess);
			model.addAttribute("day",day);
			
			model.addAttribute("faultType", faultType);
			model.addAttribute("transType",transType);
			return "jsp/BCTruyenDan/DipMatHanForm";
		}
		if (sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdate+":00")==false)
		{
			model.addAttribute("errorsdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCTruyenDan/DipMatHanForm";
		}
		if (edate!=null && !edate.equals("")   && DateTools.isValid("dd/MM/yyyy HH:mm:ss", edate+":00")==false)
		{
			model.addAttribute("erroredate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCTruyenDan/DipMatHanForm";
		}
		if (userProc.length()>255)
		{
			saveMessageKey(request, "alarmExtend.moreUse");
			model.addAttribute("team", teamProcess);
			model.addAttribute("day",day);
			
			model.addAttribute("faultType", faultType);
			model.addAttribute("transType",transType);
			 return "jsp/BCTruyenDan/DipMatHanForm";
		}
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("team", teamProcess);
			model.addAttribute("day",day);
			
			model.addAttribute("faultType", faultType);
			model.addAttribute("transType",transType);
			 return "jsp/BCTruyenDan/DipMatHanForm";
		}
		else
		{
			/*List<WarningDipSame> alarmSameList = new ArrayList<WarningDipSame>();
			try
			{
				alarmSameList = alDyRpErrorDipDAO.getAlarmSameSystem(alDyRpErrorDip.getBscid(),alDyRpErrorDip.getDip(),day);
				model.addAttribute("alarmSameList", alarmSameList);
			}
			catch (Exception exp){}*/
			alDyRpErrorDip.setDay((new SimpleDateFormat("dd/MM/yyyy")).parse(day));
			if (!edate.equals("")){
				alDyRpErrorDip.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate+":00"));
			}
			if (!sdate.equals("")){
				alDyRpErrorDip.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate+":00"));
			}
			alDyRpErrorDip.setTeamProcess(teamProcess);
			alDyRpErrorDip.setResultContent(resultContent);
			alDyRpErrorDip.setUserProcess(userProc);
			alDyRpErrorDip.setCausebyContent(causebyContent);
			alDyRpErrorDip.setFaultType(faultType);
			alDyRpErrorDip.setTransType(transType);
			Integer record = alDyRpErrorDipDAO.getItemByBSC_DIP_DAY(alDyRpErrorDip.getBscid(),alDyRpErrorDip.getDip(),alDyRpErrorDip.getDayStr());
			if (alDyRpErrorDip.getId()==null)
			{
			
				if (record!=null && record!=0)
				{
					saveMessageKey(request, "alDyRpErrorDip.insertSucceFall");
					model.addAttribute("team", teamProcess);
					model.addAttribute("day",day);
					
					model.addAttribute("faultType", faultType);
					model.addAttribute("transType",transType);
					return "jsp/BCTruyenDan/DipMatHanForm";
				}
				else
				{
					alDyRpErrorDip.setShiftId(shift.getShiftId());
					alDyRpErrorDipDAO.insertSelective(alDyRpErrorDip);
					saveMessageKey(request, "alDyRpErrorDip.insertSucceFull");
				}
			}
					
			else{
					if (record!=null && record!=0 && record.intValue() != alDyRpErrorDip.getId())
					{
						saveMessageKey(request, "alDyRpErrorDip.insertSucceFall");
						model.addAttribute("team", teamProcess);
						model.addAttribute("day",day);
						
						model.addAttribute("faultType", faultType);
						model.addAttribute("transType",transType);
						return "jsp/BCTruyenDan/DipMatHanForm";
					}
					else
					{
						alDyRpErrorDip.setShiftId(shift.getShiftId());
						alDyRpErrorDipDAO.updateByPrimaryKeySelective(alDyRpErrorDip);
						saveMessageKey(request, "alDyRpErrorDip.updateSuccelfull");	
					}
				}	
		}		
		model.addAttribute("bscid", "");
		model.addAttribute("team",  "");
		model.addAttribute("dip",  "");
		model.addAttribute("dvtUserProcess",  "");
		model.addAttribute("faultType",  "");
		model.addAttribute("transType",  "");
		return "redirect:mat_han.htm";
	}
	@RequestMapping(value = "deleteMH", method = RequestMethod.GET)
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
				alDyRpErrorDipDAO.deleteByPrimaryKey(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		}
		else
			saveMessageKey(request, "messsage.confirm.deletefailure");

		return "redirect:mat_han.htm";
	}
	@RequestMapping("list")
	public ModelAndView listAll(
			@RequestParam(required = false) String Sday,
			@RequestParam(required = false) String Eday,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String dip,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String userProcess,
			@RequestParam(required = false) String items,
			@RequestParam(required = false) String itemsE,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String transType,
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
		if (dip==null)
		{
			dip="";
		}
		else
			dip = dip.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		
		if (userProcess==null)
		{
			userProcess="";
		}
		else
			userProcess = userProcess.trim();
		
		if (items==null)
		{
			items="10";
		}
		else
			items = items.trim();
		
		if (itemsE==null)
		{
			itemsE="";
		}
		else
			itemsE = itemsE.trim();
		
		int order = 0;
		String column = "BSCID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<AlDyRpDip> alarmList = new ArrayList<AlDyRpDip>();
		if(Sday == null|| Eday == null
				||(Sday!=null && !Sday.equals("") && DateTools.isValid("dd/MM/yyyy", Sday)==false)
				||(Eday!=null && !Eday.equals("") && DateTools.isValid("dd/MM/yyyy", Eday)==false)	)
			{
				long time = System.currentTimeMillis();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date startDay = dt.toDate();
				Date destDay = new Date(time);
				Sday   = df_full.format(startDay);
				Eday 	= df_full.format(destDay);
			}
		int count=0;
		SYS_PARAMETER countCC = sysParameterDao.getTimerLossDip();
		model.addAttribute("countCC", count);
		
		if (type!=null && type.equals("CC"))
		{	
			
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDipCC(null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			
		}
		else
		{
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDip(null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
		}
		model.addAttribute("type", type);	
		model.addAttribute("bscid", bscid);
		model.addAttribute("team", teamProcess);
		model.addAttribute("Sday", Sday);
		model.addAttribute("Eday", Eday);
		model.addAttribute("dip", dip);
		model.addAttribute("items", items);
		//model.addAttribute("totalTime", totalTime);
		model.addAttribute("itemsE", itemsE);
		//model.addAttribute("totalTimeE", totalTimeE);
		model.addAttribute("userProcess", userProcess);
		model.addAttribute("transType", transType);
		
		int itemN=0;
		int itemEN=0;
		int totalTimeN=0;
		int totalTimeEN=0;
		if (items == null||(items != null&&items.equals("")))
		{
			items="0";
		}
		if (itemsE == null||(itemsE != null&&itemsE.equals("")))
		{
			itemsE="0";
		}
		
		try
		{
			itemN = Integer.parseInt(items);
			itemEN = Integer.parseInt(itemsE);
			if (bscid!=null && !bscid.equals("") )
				bscid = bscid.replace(" ", ",");
			if (dip!=null && !dip.equals("") )
				dip = dip.replace(" ", ",");
			alarmList = alDyRpDipDAO.getAllDipInDay(Sday,Eday,transType,bscid,dip,teamProcess,userProcess,itemN,itemEN,type,count,order,column);
		}
		catch(Exception exp)
		{
			alarmList =null;
		}
	List<String> bscidList = vAlHBscDAO.getBSCIDByTeam(teamProcess);
	model.addAttribute("bscidList", bscidList);
	model.addAttribute("alarmList", alarmList);
	
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "AlRbErrorDip" + dateNow;
	model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCTruyenDan/DipNgayList");
	}
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showFormAll(@RequestParam(required = false) String id,@RequestParam(required = true) String type, ModelMap model, HttpServletRequest request) {
		
		
		AlDyRpDip alarm = new AlDyRpDip();
		//List<WarningDipSame> alarmSameList = new ArrayList<WarningDipSame>();
		if (type!=null && type.equals("CC"))
		{	
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDipCC("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			
		}
		else
		{
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDip("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
		}
		if (id!=null)
		{
			alarm = alDyRpDipDAO.selectByPrimaryKey(Integer.parseInt(id));
			model.addAttribute("transType",alarm.getTransType());
			
		}
	//	model.addAttribute("alarmSameList", alarmSameList);
		model.addAttribute("type", type);	
		model.addAttribute("day", alarm.getDayStr() );
		model.addAttribute("team", alarm.getTeamProcess());
		
		
		model.addAttribute("alDyRpDip", alarm);		
		
		/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
		model.addAttribute("teamList", teamList);*/
		
		return "jsp/BCTruyenDan/DipNgayForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmitAll(@ModelAttribute("alDyRpDip") @Valid AlDyRpDip alDyRpDip, BindingResult result,@RequestParam(required = true) String type,
			@RequestParam(required = false) List<String> userProcess,
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String causebyContent,
			@RequestParam(required = false) String resultContent,
			@RequestParam(required = false) String transType,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		if (type!=null && type.equals("CC"))
		{	
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDipCC("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			
		}
		else
		{
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDip("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
		}
		String userProc ="";
		if (userProcess!=null && userProcess.size()>0)
		{
			userProc= userProcess.get(0);
			for (int i=1;i<userProcess.size();i++)
			{
				userProc+=","+ userProcess.get(i);
			}
		}
		
		model.addAttribute("alDyRpDip", alDyRpDip);	
		model.addAttribute("transType", transType);
		model.addAttribute("team", teamProcess);
		model.addAttribute("day",day);
		model.addAttribute("type", type);	
		AlShift shift = getCaTrucHT(username,null);
		Integer shiftId=null;
		boolean checkCaTruc= false;
		if (shift!=null)
		{
			checkCaTruc=true;
			shiftId = shift.getShiftId();
		}
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			 return "jsp/BCTruyenDan/DipNgayForm";
		}
		if (day==null || (day!=null && (day.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false)))
		{
			model.addAttribute("errorday", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCTruyenDan/DipNgayForm";
		}
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/BCTruyenDan/DipNgayForm";
		}
		else
		{
			alDyRpDip.setDay((new SimpleDateFormat("dd/MM/yyyy")).parse(day));
			alDyRpDip.setTeamProcess(teamProcess);
			alDyRpDip.setResultContent(resultContent);
			alDyRpDip.setUserProcess(userProc);
			alDyRpDip.setCausebyContent(causebyContent);
			alDyRpDip.setTransType(transType);
			alDyRpDip.setShiftId(shiftId);
			if (alDyRpDip.getId()==null)
			{
					alDyRpDipDAO.insertSelective(alDyRpDip);
					saveMessageKey(request, "alDyRpDip.insertSucceFull");
			}
					
			else{
					alDyRpDipDAO.updateByPrimaryKeySelective(alDyRpDip);
					saveMessageKey(request, "alDyRpDip.updateSuccelfull");	
				}	
		}		
		model.addAttribute("bscid", "");
		model.addAttribute("team", "");
		model.addAttribute("dip", "");
		model.addAttribute("items", "");
		model.addAttribute("totalTime", "");
		model.addAttribute("itemsE", "");
		model.addAttribute("totalTimeE", "");
		model.addAttribute("userProcess", "");
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		day = df_full.format(cal.getTime());
		model.addAttribute("day", day);
		return "redirect:list.htm";
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDeleteAll(@RequestParam (required = true) String id,@RequestParam(required = true) String type,
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
		model.addAttribute("type", type);	
		if (checkCaTruc==true)
		{
			try {
				alDyRpDipDAO.deleteByPrimaryKey(Integer.parseInt(id));
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
	
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(
			@RequestParam(required = true) String bscId,
			@RequestParam(required = true) String dip,
			@RequestParam(required = true) String startTime,
			@RequestParam(required = true) String type,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDipDetail();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleD", sysParemeterTitle.get(0).getValue());
		}
		if (dip==null)
		{
			dip="";
		}
		else
			dip = dip.trim();
		if (type==null)
		{
			type="";
		}
		else
			type = type.trim();
		
		int order = 0;
		String column = "SITE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<DetailLostConfig> detailList = new ArrayList<DetailLostConfig>();
		model.addAttribute("type", type);	
		model.addAttribute("bsc", bscId);
		model.addAttribute("dip", dip);
		model.addAttribute("start", startTime);
		String timer="";
		if (startTime!=null)
		{
			timer = startTime.substring(0, 10);
			
			try
			{
				detailList = alDyRpDipDAO.getDetailLostDip(bscId,dip,timer,order,column);
			}
			catch(Exception exp)
			{
				detailList =null;
				saveMessageKey(request, "messsage.NotPatition");
			}
		}
		model.addAttribute("detailList", detailList);
	
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "VAlRbLossConfigDetail_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		return "jsp/BCTruyenDan/DipChiTiet";
	}
	@RequestMapping("lostDip3Day")
	public ModelAndView list3Day(
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String dip,
			@RequestParam(required = false) String teamProcess,
			Model model, HttpServletRequest request) throws ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDip3Day();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		List<AlDyRpDip3Day> alarmList = new ArrayList<AlDyRpDip3Day>();
		if (dip==null)
		{
			dip="";
		}
		else
			dip = dip.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		if (day==null)
		{
			day="";
		}
		else
			day = day.trim();
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (day == null || day.equals("")||(day!=null && !day.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false))
		{
			day = df_full.format(cal.getTime());
		}
		Calendar c = Calendar.getInstance();
		c.setTime(df_full.parse(day));
		c.add(Calendar.DATE, -1);
		String day1="";
		String day2="";
		day1 = df_full.format(c.getTime());
		c.add(Calendar.DATE, -1);
		day2 = df_full.format(c.getTime());
		
		model.addAttribute("bscid", bscid);
		model.addAttribute("team", teamProcess);
		model.addAttribute("day", day);
		model.addAttribute("day1", day1);
		model.addAttribute("day2", day2);
		model.addAttribute("dip", dip);
	
		try
		{
			if (bscid!=null && !bscid.equals("") )
				bscid = bscid.replace(" ", ",");
			if (dip!=null && !dip.equals("") )
				dip = dip.replace(" ", ",");
			alarmList = alDyRpDipDAO.getAllDip3Day(day,bscid,dip,teamProcess);
		}
		catch(Exception exp)
		{
			alarmList =null;
		}
		/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
	model.addAttribute("teamList", teamList);
*/
	List<String> bscidList = vAlHBscDAO.getBSCIDByTeam(teamProcess);
	model.addAttribute("bscidList", bscidList);
	
	model.addAttribute("alarmList", alarmList);
	
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "AlRbLossDip3Day_" + dateNow;
	model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCTruyenDan/Dip3Day");
	}
	// Giam sat canh bao mat lien lac
	@RequestMapping("danh_sach")
	public ModelAndView listMaster(
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String dip,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String dvtUserProcess,
			@RequestParam(required = false) String statusKT,
			@RequestParam(required = false) String totalTimeF,
			@RequestParam(required = false) String totalTimeE,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			@RequestParam(required = false) String timeLoad,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossComunicationDAO.titleLossCommunication(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (dvtUserProcess==null)
		{
			dvtUserProcess="";
		}
		else
			dvtUserProcess = dvtUserProcess.trim();
		if (totalTimeF==null)
		{
			totalTimeF="";
		}
		else
			totalTimeF = totalTimeF.trim();
		if (totalTimeE==null)
		{
			totalTimeE="";
		}
		else
			totalTimeE = totalTimeE.trim();
		
		if (dip==null)
		{
			dip="";
		}
		else
			dip = dip.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		if (reloadStr==null)
		{
			reload="Y";
			reloadStr="Y";
		}
		int order = 0;
		String column = "BSCID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<VAlRbLossComunication> alarmList = new ArrayList<VAlRbLossComunication>();
		
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (day == null || day.equals("")||(day!=null && !day.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false))
		{
			day = df_full.format(cal.getTime());
		}
		
		if (timeLoad == null) {
			timeLoad = "15";
		}
		else
		{
			try
			{
				int timeLoadInt = Integer.parseInt(timeLoad);
			}
			catch(Exception exp)
			{
				timeLoad = "15";
			}
		}
		if (statusKT==null)
		{
			statusKT="N";
		}
		model.addAttribute("bscid", bscid);
		model.addAttribute("team", teamProcess);
		model.addAttribute("day", day);
		model.addAttribute("dip", dip);
		model.addAttribute("totalTimeF", totalTimeF);
		model.addAttribute("totalTimeE", totalTimeE);
		model.addAttribute("dvtUserProcess", dvtUserProcess);
		model.addAttribute("statusKT", statusKT);
		model.addAttribute("reload", reload);
		model.addAttribute("timeLoad", timeLoad);
		
		int totalTimeFN=0;
		int totalTimeEN=0;
		if (totalTimeF == null||(totalTimeF != null&&totalTimeF.equals("")))
		{
			totalTimeFN=0;
			totalTimeF="0";
		}
		if (totalTimeE == null||(totalTimeE != null&&totalTimeE.equals("")))
		{
			totalTimeEN=0;
			totalTimeE="0";
		}
		try
		{
			totalTimeFN= Integer.parseInt(totalTimeF);
			totalTimeEN= Integer.parseInt(totalTimeE);
			if (bscid!=null && !bscid.equals("") )
				bscid = bscid.replace(" ", ",");
			if (dip!=null && !dip.equals("") )
				dip = dip.replace(" ", ",");
			alarmList = vAlRbLossComunicationDAO.getAllComunicationInDay(day,bscid,dip,teamProcess,dvtUserProcess,totalTimeFN,totalTimeEN,statusKT,order,column);
		}
		catch(Exception exp)
		{
			alarmList =null;
		}
	/*	List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
	model.addAttribute("teamList", teamList);*/

	List<String> bscidList = vAlHBscDAO.getBSCIDByTeam(teamProcess);
	model.addAttribute("bscidList", bscidList);
	
	List<SYS_PARAMETER> statusKTList = sysParameterDao.getStatusFinish();
	model.addAttribute("statusKTList", statusKTList);
	
	model.addAttribute("alarmList", alarmList);
	
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "VAlRbLossComunication_" + dateNow;
	model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCTruyenDan/DipListMaster");
	}
	@RequestMapping(value = "formMaster", method = RequestMethod.GET)
	public String showFormMaster(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request) {
		
		/*SYS_PARAMETER titleS= sysParameterDao.getValueByCodeName("TITLE_BAO_CAO_CHI_TIET_2G", "MAT_LIEN_LAC_ADD");
		if (titleS!=null)
			model.addAttribute("titleF", titleS.getValue());
		else
			model.addAttribute("titleF", "TITLE_BAO_CAO_CHI_TIET_2G.MAT_LIEN_LAC_ADD");*/
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossComunicationDAO.titleLossCommunication("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		VAlRbLossComunication alarm = new VAlRbLossComunication();
		List<VAlAlarmLog> alarmSameList = new ArrayList<VAlAlarmLog>();
		
		if (id!=null)
		{
			alarm = vAlRbLossComunicationDAO.selectByID(Integer.parseInt(id));
			if (alarm!=null)
			{
				String system=null;
				String sdate=null;
				String edate=null;
				String operator=null;
				operator =alarm.getBscid();
				system=alarm.getDip();
				sdate=alarm.getSdateStr();
				edate=alarm.getEdateStr();
			/*	String site = system.substring(0, system.length()-1);
*/
				try{
					alarmSameList = vAlAlarmLogDAO.getAlarmSameSystem(operator,system,sdate,edate);
				}
				catch (Exception exp){}
			}
		}
		model.addAttribute("alarmSameList", alarmSameList);
		model.addAttribute("sdate", alarm.getSdateStr() );
		model.addAttribute("edate", alarm.getEdateStr());
		model.addAttribute("ungCuuMpd", alarm.getUngCuuMpd());
		model.addAttribute("team", alarm.getDvtTeamProcess());
		
		model.addAttribute("vAlRbLossComunication", alarm);		
		
		/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
		model.addAttribute("teamList", teamList);*/
			
		List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getStatusDVTUCTT();
		model.addAttribute("ungCuuMpdList", ungCuuMpdList);
		
		return "jsp/BCTruyenDan/DipFormMaster";
	}
	@RequestMapping(value = "formMaster", method = RequestMethod.POST)
	public String onSubmitMaster(@ModelAttribute("vAlRbLossComunication") @Valid VAlRbLossComunication vAlRbLossComunication, 
			BindingResult result,
			@RequestParam(required = false) List<String> dvtUserProcess,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String dvtTeamProcess,
			@RequestParam(required = false) String ungCuuMpd,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossComunicationDAO.titleLossCommunication("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		String userProc ="";
		if (dvtUserProcess!=null && dvtUserProcess.size()>0)
		{
			userProc= dvtUserProcess.get(0);
			for (int i=1;i<dvtUserProcess.size();i++)
			{
				userProc+=","+ dvtUserProcess.get(i);
			}
		}
		model.addAttribute("vAlRbLossComunication", vAlRbLossComunication);	
		
		List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getStatusDVTUCTT();
		model.addAttribute("ungCuuMpdList", ungCuuMpdList);
		model.addAttribute("team", dvtTeamProcess);
		model.addAttribute("sdate",sdate);
		model.addAttribute("edate",edate);
		model.addAttribute("ungCuuMpd", ungCuuMpd);	
		
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		Integer shiftID = null;
		if (shift!=null)
		{
			checkCaTruc=true;
			shiftID= shift.getShiftId();
		}
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			 return "jsp/BCTruyenDan/DipFormMaster";
		}
		if (sdate==null || (sdate!=null && (sdate.equals("")||DateTools.isValid("dd/MM/yyyy", sdate)==false)))
		{
			model.addAttribute("errorsdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCTruyenDan/DipFormMaster";
		}
		if (edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false)
		{
			model.addAttribute("erroredate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCTruyenDan/DipFormMaster";
		}
		if (userProc.length()>255)
		{
			saveMessageKey(request, "alarmExtend.moreUse");
			 return "jsp/BCTruyenDan/DipFormMaster";
		}
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/BCTruyenDan/DipFormMaster";
		}
		else
		{
			List<VAlAlarmLog> alarmSameList = new ArrayList<VAlAlarmLog>();
			try{
				alarmSameList = vAlAlarmLogDAO.getAlarmSameSystem(vAlRbLossComunication.getBscid(),vAlRbLossComunication.getDip(),sdate,edate);
				model.addAttribute("alarmSameList", alarmSameList);
			}
			catch (Exception exp){}
			vAlRbLossComunication.setSdate((new SimpleDateFormat("dd/MM/yyyy")).parse(sdate));
			if (edate!=null && !edate.equals(""))
				vAlRbLossComunication.setEdate((new SimpleDateFormat("dd/MM/yyyy")).parse(edate));
			vAlRbLossComunication.setDvtTeamProcess(dvtTeamProcess);
			vAlRbLossComunication.setDvtUserProcess(userProc);
			vAlRbLossComunication.setUngCuuMpd(ungCuuMpd);
			vAlRbLossComunication.setShiftId(shiftID);
			if (!edate.equals("")&& vAlRbLossComunication.getSdate().getTime()>= vAlRbLossComunication.getEdate().getTime())
			{
				saveMessageKey(request, "cautruc.sosanhNgay");
				 return "jsp/BCTruyenDan/DipFormMaster";
			}
			if (vAlRbLossComunication.getId()==null)
			{
				
					vAlRbLossComunicationDAO.insertSelective(vAlRbLossComunication);
					saveMessageKey(request, "alDyRpDip.insertSucceFull");
			}
					
			else{
					vAlRbLossComunicationDAO.update(vAlRbLossComunication);
					saveMessageKey(request, "alDyRpDip.updateSuccelfull");	
				}	
		}		
		model.addAttribute("bscid", "");
		model.addAttribute("team", "");
		model.addAttribute("dip", "");
		model.addAttribute("totalTimeF", "");
		model.addAttribute("totalTimeE", "");
		model.addAttribute("dvtUserProcess", "");
		model.addAttribute("statusKT", "");
		return "redirect:danh_sach.htm";
	}
	@RequestMapping(value = "deleteMaster", method = RequestMethod.GET)
	public String onDeleteMaster(@RequestParam (required = true) String id,
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
				vAlRbLossComunicationDAO.deleteByPrimaryKey(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		}
		else
			saveMessageKey(request, "messsage.confirm.deletefailure");

		return "redirect:danh_sach.htm";
	}
	@RequestMapping(value="tong_hop")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate,
					   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "TongHopLoiTruyenDan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpDipDAO.titleLossDipTotal();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		// Ngay thang
		
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (startDate == null || startDate.equals("")||(startDate!=null && !startDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startDate+" 00:00:00")==false))
		{
			Calendar c= Calendar.getInstance();
			c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			startDate = df_full.format(c.getTime());
		}
		if (endDate == null || endDate.equals("")||(endDate!=null && !endDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endDate+" 00:00:00")==false))
		{	
			endDate = df_full.format(cal.getTime());
		}
			
		// Lay danh sach cac transtype de tao tieu de cho bao cao (chua tao)*******
		List<String> transTypeList = alDyRpDipDAO.getTranstype();
		
		model.addAttribute("transTypeList", transTypeList);
		model.addAttribute("counttransType", transTypeList.size()+1);
		model.addAttribute("duobletransType", (transTypeList.size()+1)*2);
		
		// Lấy danh sách giá trị báo cáo, moi ban ghi co dang day_countDipMH_countDip_totalDip_-_-_DipWithTranstype-_-_totalCC__-_-_CCWithTranstype-_-_totalCC3Day_-_-_CC3DayWithTranstype-_-_
		List<String> baoCaoList = alDyRpDipDAO.baoCaoTongHop(startDate, endDate);
		
		//Dinh nghia mot mang de bo du lieu
		
		String[][] allList = new String[baoCaoList.size()+1][transTypeList.size()*3+6];
		//add tieu de vao
		String tieude="Ngày_Truyền dẫn mất hẳn_Số lần mất truyền dẫn";
		tieude=tieude+"_Loại truyền dẫn (Tổng)";
		for (int k=0;k<transTypeList.size();k++)
			tieude=tieude+"_Loại truyền dẫn ("+transTypeList.get(k)+")";
		tieude=tieude+"_Số lần chập trờn (Tổng)";
		for (int l=0;l<transTypeList.size();l++)
			tieude=tieude+"_Số lần chập trờn ("+transTypeList.get(l)+")";
		tieude=tieude+"_Chập trờn 3 ngày (Tổng)";
		for (int m=0;m<transTypeList.size();m++)
			tieude=tieude+"_Chập trờn 3 ngày ("+transTypeList.get(m)+")";
		String[] tDList = tieude.split("_");
		
		int sizetitle = transTypeList.size()*3+6;
		for(int t=0;t<sizetitle;t++){
			
			allList[0][t] = tDList[t];
		}
		//add du lieu vao
		for(int i=0;i<baoCaoList.size();i++){
			String[] pDList = baoCaoList.get(i).split("_");
			for(int j=0;j<pDList.length;j++){
				allList[i+1][j] = pDList[j];
			}		
		}	
		List<Integer> count = new ArrayList<Integer>();
		for(int k=0;k< sizetitle ;k++)
			count.add(k);
		
		model.addAttribute("tongHopList", allList);
		model.addAttribute("count", count);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "jsp/BCTruyenDan/TongHopDip";
	}
}
