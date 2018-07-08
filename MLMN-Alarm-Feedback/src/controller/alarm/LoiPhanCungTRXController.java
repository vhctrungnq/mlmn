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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.joda.time.DateTime;

import controller.BaseController;

import vo.AlDyMblAblLog;
import vo.AlShift;
import vo.HBsc;
import vo.HProvincesCode;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;

import dao.AlDyMblAblLogDAO;
import dao.AlShiftDAO;
import dao.HBscDAO;
import dao.HProvincesCodeDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlAlarmLogDAO;
import dao.VAlHCellDAO;

@Controller
@RequestMapping("/TRX2G/*")
public class LoiPhanCungTRXController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired 
	private VAlHCellDAO vAlHCellDAO;
	@Autowired 
	private VAlAlarmLogDAO vAlAlarmLogDAO;
	@Autowired
	private AlDyMblAblLogDAO alDyMblAblLogDAO;
	@Autowired
	private HBscDAO hBscDao;
	
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	// Lay id truc ca hien tai
	private AlShift getCaTrucHT(String username, String region)
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
	// QUANG edit
	@RequestMapping("TRX_List")
	public ModelAndView list(
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String dvtTeamProcess,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String trx,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = true) String alarmType,
			Model model, HttpServletRequest request) {
		
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			AlShift shift = getCaTrucHT(username,null);
			boolean checkCaTruc= false;
			if (shift!=null)
			{
				checkCaTruc=true;
				model.addAttribute("checkCaTruc", checkCaTruc);
			}
		}
		catch(Exception e){}
		
		int order = 0;
		String column = "MO";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
		column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		
		
		if (trx==null)
		{
			trx="";
		}
		else
			trx = trx.trim();
		
		if (cellid==null)
		{
			cellid="";
		}
		else
			cellid = cellid.trim();
		
		
		if(startDate == null|| endDate == null
				||(startDate!=null && !startDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startDate+" 00:00:00")==false)
				||(endDate!=null && !endDate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endDate+" 00:00:00")==false)	)
			{
				long time = System.currentTimeMillis();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date startDay = dt.toDate();
				Date destDay = new Date(time);
				startDate   = df_full.format(startDay);
				endDate 	= df_full.format(destDay);
			}		
		
		List<HProvincesCode> teamList = hProvincesCodeDAO.getDeptList();
		model.addAttribute("dvXuLyList", teamList);
		
		
		List<HBsc> bscidList = hBscDao.getBscByDept(dvtTeamProcess);
		model.addAttribute("bscidList", bscidList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("dvtTeamProcess", dvtTeamProcess);
		model.addAttribute("trx", trx);
		model.addAttribute("alarmType", alarmType);

		
		if (!bscid.equals("") )
			bscid = bscid.replace(" ", ",");
		if (!trx.equals("") )
			trx = trx.replace(" ", ",");
		if (!cellid.equals("") )
			cellid = cellid.replace(" ", ",");
		if (alarmType== null || alarmType.equals(""))
		{	
			List<SYS_PARAMETER> titleSystem = alDyMblAblLogDAO.titleTrx();
			if (titleSystem.size() > 0)
				model.addAttribute("title", titleSystem.get(0).getValue());
		}
		else
		{
			List<SYS_PARAMETER> titleSystem = alDyMblAblLogDAO.titleMBL_ABL_Form(alarmType);
			if (titleSystem.size() > 0)
				model.addAttribute("title", titleSystem.get(0).getValue());
		
			List<AlDyMblAblLog> filter = alDyMblAblLogDAO.getListAblMbl(alarmType, startDate, endDate, dvtTeamProcess, bscid, trx,cellid, column, order);
			model.addAttribute("mblAblList", filter);	
		
		}
			
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AlRbLossErrorTRX_"+alarmType+"_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/BCMatDien/LoiPhanCungTRXList");
	}
	
	@RequestMapping(value = "trx_form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id,
			@RequestParam(required = false) String alarmType, 
			ModelMap model, HttpServletRequest request) {
		
		AlDyMblAblLog alDyMblAblLog = (id == null) ? new AlDyMblAblLog() : alDyMblAblLogDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("alDyMblAblLog", alDyMblAblLog);
		
		List<SYS_PARAMETER> titleSystem = alDyMblAblLogDAO.titleCapNhatMblAbl(alarmType);
		if (titleSystem.size() > 0)
			model.addAttribute("titleForm", titleSystem.get(0).getValue());
		model.addAttribute("day", alDyMblAblLog.getDayStr() );
		model.addAttribute("dvtTeamProcess", alDyMblAblLog.getDvtTeamProcess());	
		model.addAttribute("alarmType", alarmType);	
		model.addAttribute("trx", alDyMblAblLog.getTrx());	
		
		
		List<HProvincesCode> teamList = hProvincesCodeDAO.getDeptList();
		model.addAttribute("dvXuLyList", teamList);
		
		List<SYS_PARAMETER> vendorList = sysParameterDao.getVendorList();
		model.addAttribute("vendorList", vendorList);
		model.addAttribute("vendor", alDyMblAblLog.getVendor());
		
		return "jsp/BCMatDien/LoiPhanCungTRXForm";
	}

	@RequestMapping(value = "trx_form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("alDyMblAblLog") @Valid AlDyMblAblLog alDyMblAblLog, BindingResult result,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String dvtUserProcess,
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String dvtTeamProcess,
			@RequestParam(required = false) String alarmType, 
			@RequestParam(required = false) String mo,
			@RequestParam(required = false) String vendor,
			ModelMap model, HttpServletRequest request) throws ParseException {
		

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<HProvincesCode> teamList = hProvincesCodeDAO.getDeptList();
		model.addAttribute("dvXuLyList", teamList);
		
		List<SYS_PARAMETER> vendorList = sysParameterDao.getVendorList();
		model.addAttribute("vendorList", vendorList);
		model.addAttribute("vendor", vendor);
		
		model.addAttribute("alarmType",alarmType);
		model.addAttribute("day",day);
		
		if (alarmType==null || alarmType.equals(""))
		{	
			// Tieu de form sua loi phan cung
			List<SYS_PARAMETER> titleSystem = alDyMblAblLogDAO.titleAlarmTrxForm();
			if (titleSystem.size() > 0)
				model.addAttribute("titleForm", titleSystem.get(0).getValue());
		}
		else
		{
			// Tieu de form sua mbl, abl
			List<SYS_PARAMETER> titleSystem = alDyMblAblLogDAO.titleCapNhatMblAbl(alarmType);
			if (titleSystem.size() > 0)
				model.addAttribute("titleForm", titleSystem.get(0).getValue());
		}
		AlShift shift = getCaTrucHT(username, null);
		boolean checkCaTruc= false;
		Integer shiftId=null;
		if (shift!=null)
		{
			checkCaTruc=true;
			shiftId= shift.getShiftId();
		}
		if (checkCaTruc==false)
		{
			model.addAttribute("dvtTeamProcess", dvtTeamProcess);
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			 return "jsp/BCMatDien/LoiPhanCungTRXForm";
		}
		if (day==null || (day!=null && (day.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false)))
		{
			model.addAttribute("errorsdate", "*");
			model.addAttribute("dvtTeamProcess", dvtTeamProcess);
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/LoiPhanCungTRXForm";
		}
		
		if (result.hasErrors()) {
			
			model.addAttribute("dvtTeamProcess", dvtTeamProcess);
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/BCMatDien/LoiPhanCungTRXForm";
		}
		else
		{
			if (!day.equals("")){
				alDyMblAblLog.setDay((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(day+" 00:00:00"));
			}
			else
				alDyMblAblLog.setDay(null);
			alDyMblAblLog.setVendor(vendor);
			alDyMblAblLog.setShiftId(shiftId);
			try{
				alDyMblAblLogDAO.updateByPrimaryKey(alDyMblAblLog);
			}
			catch(Exception e){
				
				model.addAttribute("dvtTeamProcess", dvtTeamProcess);
				//model.addAttribute("trx", trx);	
				saveMessageKey(request, "alarmExtend.errorField");
				return "jsp/BCMatDien/LoiPhanCungTRXForm";
			}
			saveMessageKey(request, "vAlRbErrorTRX.updateSuccelfull");	
		}

		return "redirect:TRX_List.htm";
	}
	
	@RequestMapping(value = "trx_delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			@RequestParam(required = true) String alarmType, 
			HttpServletRequest request, Model model) {
		try{
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
					
					alDyMblAblLogDAO.deleteByPrimaryKey(Integer.parseInt(id));
					saveMessageKey(request, "messsage.confirm.deletesuccess");
				}
				catch (Exception e) {
					saveMessageKey(request, "messsage.confirm.deletefailure");
				}
			}
			else
				saveMessageKey(request, "messsage.confirm.deletefailureShift");
		
		}
		catch(Exception e){}
		
		return "redirect:TRX_List.htm?alarmType=" + alarmType;
	}
	
	@RequestMapping("getBscid")
	public @ResponseBody
	List<HBsc> getBSCIDByTeam(@RequestParam String dvtTeamProcess) {
		List<HBsc> record = hBscDao.getBscByDept(dvtTeamProcess);
		return record;
	}
	
	@ModelAttribute("cellList")
	public List<String> cellList() {
		return alDyMblAblLogDAO.getlistCell();
	}
	
	@ModelAttribute("trxList")
	public List<String> trxList() {
		return alDyMblAblLogDAO.getListMo();
	}

}
