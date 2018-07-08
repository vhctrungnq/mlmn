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

import vo.AlDyRpFlutterPower;
import vo.AlRpFlutter3Day;
import vo.AlShift;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.VAlAlarmLog;
import vo.WarningDipSame;

import vo.VAlHBsc;
import vo.alarm.utils.DateTools;

import dao.AlDyRpFlutterPowerDAO;
import dao.AlShiftDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlAlarmLogDAO;
import dao.VAlHBscDAO;

@Controller
@RequestMapping("/FlutterPower/*")
public class RpFlutterPowerController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;

	@Autowired
	private MDepartmentDAO mDepartmentDAO;

	@Autowired
	private AlShiftDAO alShiftDAO;

	@Autowired
	private AlDyRpFlutterPowerDAO alDyRpFlutterPowerDAO;

	@Autowired
	private VAlHBscDAO vAlHBscDAO;

	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@Autowired 
	private VAlAlarmLogDAO vAlAlarmLogDAO;
	

	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");

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
	public ModelAndView list(@RequestParam(required = false) String startDate, String endDate,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String totalF,
			@RequestParam(required = false) String totalTimeE,
			@RequestParam(required = false) String items,
			@RequestParam(required = false) String itemsE,
			Model model,
			HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpFlutterPowerDAO.titleFlutterPower(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc = false;
		if (shift != null||userLogin.getIsRoleManager().equals("Y")) {
			checkCaTruc = true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (site==null)
		{
			site="";
		}
		else
			site = site.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		if (totalF==null)
		{
			totalF="";
		}
		else
			totalF = totalF.trim();
		if (totalTimeE==null)
		{
			totalTimeE="";
		}
		else
			totalTimeE = totalTimeE.trim();
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
		int order =0;
		String column = "BSCID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		Calendar cal = Calendar.getInstance();	 
		cal.setTime(new Date(cal.getTimeInMillis() - 24 * 60 * 60 * 1000));
		if (endDate == null || endDate.equals("")||(endDate!=null && !endDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endDate+" 00:00:00")==false))
		{	
			  
			endDate = df_full.format(cal.getTime());
		}
		if (startDate == null || startDate.equals("")||(startDate!=null && !startDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startDate+" 00:00:00")==false))
		{
			
			startDate = endDate;
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("bscid", bscid);
		model.addAttribute("site", site);
		model.addAttribute("team", teamProcess);
		model.addAttribute("totalF", totalF);
		model.addAttribute("totalTimeE", totalTimeE);
		model.addAttribute("items", items);
		model.addAttribute("itemsE", itemsE);
		List<AlDyRpFlutterPower> alarmList = new ArrayList<AlDyRpFlutterPower>();
		int totalTimeFN=0;
		int totalTimeEN=0;
		int itemSl=0;
		int itemSlE=0;
		if (totalF == null||(totalF != null&&totalF.equals("")))
		{
			totalTimeFN=0;
			totalF="0";
		}
		if (totalTimeE == null||(totalTimeE != null&&totalTimeE.equals("")))
		{
			totalTimeEN=0;
			totalTimeE="0";
		}
		if (items == null||(items != null&&items.equals("")))
		{
			itemSl=0;
			items="0";
		}
		if (itemsE == null||(itemsE != null&&itemsE.equals("")))
		{
			itemSlE=0;
			itemsE="0";
		}
		
		try
		{
			totalTimeFN = Integer.parseInt(totalF);
			totalTimeEN = Integer.parseInt(totalTimeE);
			itemSl = Integer.parseInt(items);
			itemSlE = Integer.parseInt(itemsE);
		} 
		catch(Exception exp)
		{
			alarmList = null;
			return new ModelAndView("jsp/BCMatDien/ChapChonNhieuNgayList");
		}
		try {
			if (site!=null && !site.equals("") )
				site = site.replace(" ", ",");
			if (bscid!=null && !bscid.equals(""))
				bscid = bscid.replace(" ", ",");
			
			alarmList = alDyRpFlutterPowerDAO.getVAlDyRpFlutterPowerFilter(startDate, endDate, bscid, site, teamProcess,totalTimeFN,totalTimeEN,itemSl,itemSlE, order, column);
		} catch (Exception exp) {
				exp.printStackTrace();
		}
	
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);

		List<String> bscidList = vAlHBscDAO.getBSC23GByTeam(teamProcess);
		model.addAttribute("bscidList", bscidList);

		model.addAttribute("alarmList", alarmList);

		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "alRbFlutterPower" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/BCMatDien/ChapChonNhieuNgayList");
	}
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request) {
		
		
		AlDyRpFlutterPower alarm = new AlDyRpFlutterPower();
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpFlutterPowerDAO.titleFlutterPower("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		if (id!=null)
		{
			alarm = alDyRpFlutterPowerDAO.selectByPrimaryKey(Integer.parseInt(id));
			
		}
		model.addAttribute("day", alarm.getDayStr() );
		model.addAttribute("team", alarm.getTeamProcess());
		model.addAttribute("alDyRpFlutterPower", alarm);		
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		
		return "jsp/BCMatDien/ChapChonNhieuNgayForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("alDyRpFlutterPower") @Valid AlDyRpFlutterPower alDyRpFlutterPower, BindingResult result,
			@RequestParam(required = false) List<String> userProcess,
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String causebyContent,
			@RequestParam(required = false) String resultContent,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpFlutterPowerDAO.titleFlutterPower("ADD");
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
	
		model.addAttribute("alDyRpFlutterPower", alDyRpFlutterPower);	

		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		model.addAttribute("team", teamProcess);
		model.addAttribute("causebyContent", causebyContent);
		model.addAttribute("day",day);
		
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
		}
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			 return "jsp/BCMatDien/ChapChonNhieuNgayForm";
		}
		if (day==null || (day!=null &&( day.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false)))
		{
			model.addAttribute("errorday", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/ChapChonNhieuNgayForm";
		}
		if (userProc.length() > 255){
			saveMessageKey(request, "alarmExtend.moreUse");
			return "jsp/BCMatDien/ChapChonNhieuNgayForm";
			
		}
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/BCMatDien/ChapChonNhieuNgayForm";
		}
		else
		{
			alDyRpFlutterPower.setDay((new SimpleDateFormat("dd/MM/yyyy")).parse(day));
			alDyRpFlutterPower.setTeamProcess(teamProcess);
			alDyRpFlutterPower.setResultContent(resultContent);
			alDyRpFlutterPower.setUserProcess(userProc);
			if (alDyRpFlutterPower.getId()==null)
			{
				alDyRpFlutterPower.setShiftId(shift.getShiftId());
				alDyRpFlutterPowerDAO.insertSelective(alDyRpFlutterPower);
					saveMessageKey(request, "vAlDyRpErrorPower.insertSucceFull");
			}
					
			else{
				alDyRpFlutterPower.setShiftId(shift.getShiftId());
				alDyRpFlutterPowerDAO.updateByPrimaryKeySelective(alDyRpFlutterPower);
					saveMessageKey(request, "vAlDyRpErrorPower.updateSuccelfull");	
				}	
		}		
		return "redirect:list.htm";
	}
	

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String id,
			HttpServletRequest request, Model model) {
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		AlShift shift = getCaTrucHT(username,null);
		boolean checkCaTruc = false;
		if (shift != null||userLogin.getIsRoleManager().equals("Y") ) {
			checkCaTruc = true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (checkCaTruc == true) {
			try {
				alDyRpFlutterPowerDAO.deleteByPrimaryKey(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			} catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		} else
			saveMessageKey(request, "messsage.confirm.deletefailure");

		return "redirect:list.htm";
	}
	
	/*____________________Chập chờn 3 ngày liên tiếp_______________________*/
	
	@RequestMapping("lostFlutter3Day")
	public ModelAndView list3Day(
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String teamProcess,
			Model model, HttpServletRequest request) throws ParseException {
		
		/*SYS_PARAMETER titleS= sysParameterDao.getValueByCodeName("TITLE_MAT_DIEN", "CHAP_CHON_3NGAY");
		if (titleS!=null)
			model.addAttribute("title", titleS.getValue());
		else
			model.addAttribute("title", "TITLE_MAT_DIEN.CHAP_CHON_3NGAY");*/
		List<SYS_PARAMETER> sysParemeterTitle = alDyRpFlutterPowerDAO.titleFlutter3Day();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		List<AlRpFlutter3Day> alarmList = new ArrayList<AlRpFlutter3Day>();
		if (site==null)
		{
			site="";
		}
		else
			site = site.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
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
	
		model.addAttribute("day", day);
		model.addAttribute("bscid", bscid);
		model.addAttribute("site", site);
		model.addAttribute("team", teamProcess);
		
		model.addAttribute("day1", day1);
		model.addAttribute("day2", day2);
	
		try
		{
			if (site!=null && !site.equals("") )
				site = site.replace(" ", ",");
			if (bscid!=null && !bscid.equals(""))
				bscid = bscid.replace(" ", ",");
			alarmList = alDyRpFlutterPowerDAO.getAllFlutter3Day(day, bscid, site, teamProcess);
		}
		catch(Exception exp)
		{
			alarmList =null;
		}
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
	model.addAttribute("teamList", teamList);

	List<String> bscidList = vAlHBscDAO.getBSC23GByTeam(teamProcess);
	model.addAttribute("bscidList", bscidList);
	
	model.addAttribute("alarmList", alarmList);
	
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "AlRbLossFlutter3Day_" + dateNow;
	model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCMatDien/Flutter3Day");
	}
}
