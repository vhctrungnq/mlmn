package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import vo.AlAlarmWorks;
import vo.AlShift;
import vo.CSystemConfigs;
import vo.DyQualityNetwork;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysMailParameterMaster;
import vo.SysUsers;
import vo.SysUsersCogencyLevel;
import vo.VRbDyQualityNetwork;
import vo.VRpDyAccNw3G;
import vo.VRpDyMscSummary;
import vo.VRpDySgsnSummary;
import vo.VRpDyVt2G;
import vo.alarm.utils.AlarmSetting;
import vo.alarm.utils.FileTools;
import dao.AlAlarmWorksDAO;
import dao.AlShiftDAO;
import dao.CSystemConfigsDAO;
import dao.DyQualityNetworkDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysMailParameterMasterDAO;
import dao.SysUsersCogencyLevelDAO;
import dao.SysUsersDAO;
import dao.VRbDyQualityNetworkDAO;
import dao.VRpDyAccNw3GDAO;
import dao.VRpDyAccNwCoreCsDAO;
import dao.VRpDyMscMgwSummaryDAO;
import dao.VRpDyMscSummaryDAO;
import dao.VRpDySgsnSummaryDAO;
import dao.VRpDyVt2GDAO;

/**
 * Function        : Trang chu alarm
 * Created By      :
 * Create Date     :
 * Modified By     : BUIQUANG
 * Modify Date     : 26/11/2013
 * @author BUIQUANG
 * Description     :
 */
@Controller
public class WelcomeController extends BaseController{
	@Autowired
	private VRpDyVt2GDAO vRpDyVt2GDAO;
	@Autowired
	private VRpDyAccNwCoreCsDAO vRpDyAccNwCoreCsDAO;
	@Autowired
	private VRpDyAccNw3GDAO vRpDyAccNw3GDAO;
	@Autowired
	private VRpDyMscSummaryDAO vRpDyMscSummaryDAO;
	@Autowired
	private VRpDyMscMgwSummaryDAO vRpDyMscMgwSummaryDAO;
	@Autowired
	private AlAlarmWorksDAO alAlarmWorksDAO;
	@Autowired
	private VRbDyQualityNetworkDAO vRbDyQualityNetworkDAO;
	@Autowired
	private DyQualityNetworkDAO dyQualityNetworkDAO;
	@Autowired
	private VRpDySgsnSummaryDAO vRpDySgsnSummaryDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	
	@Autowired
	private SysUsersCogencyLevelDAO sysUsersCogencyLevelDAO;
	
	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;
	

	@Autowired
	private SysMailParameterMasterDAO sysMailParamenterMasterDAO;
	
	
	private Date currentDate = null;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
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
		shift = alShiftDAO.checkRoleCaTruc(caT,ngayT, username, region);
		
		return shift;
	}
	
	
	@RequestMapping("/welcome")
	public ModelAndView welcome(@RequestParam(required = false) Date day,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String action,
			@RequestParam(required = false) String actionProcess,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String userProcess,
			@RequestParam(required = false) String dept,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		//region = getCenter("TT");
		
		long currentTime = System.currentTimeMillis();
		if (day == null)
			day = new Date(currentTime - 24 * 60 * 60 * 1000);
		currentDate = day;
		model.addAttribute("day", df.format(day));
		
		// Đánh giá chất lượng mạng lưới
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
				String maphong="";
				String team="";
				if (userLogin!=null)
				{
					maphong= userLogin.getMaPhong();
					team = userLogin.getTeam();
					model.addAttribute("team", team);
					model.addAttribute("dept", maphong);
				}
				AlShift shift = getCaTrucHT(username,region);
				Integer idShift=null;
				//AlShift shift = alShiftDAO.getCaTrucGanNhat();
				boolean checkCaTruc= false;
				if (userLogin.getIsRoleManager()!=null && userLogin.getIsRoleManager().equals("Y"))
				{
					checkCaTruc=true;
				}
				if (shift!=null)
				{
					checkCaTruc=true;	
					idShift  = shift.getShiftId();
				}
				model.addAttribute("checkCaTruc", checkCaTruc);
				if (action!=null && action.equals("save"))
				{
					AlAlarmWorks danhGiaNew = new AlAlarmWorks();
					danhGiaNew.setWarningType("DANH_GIA_MANG_LUOI");
					danhGiaNew.setActionProcess(actionProcess);
					String depTeam=dept;
					if (teamProcess!=null &&!teamProcess.equals(""))
					{
						depTeam=depTeam+"<br/>"+teamProcess;
					}
					danhGiaNew.setTeamProcess(depTeam);
					danhGiaNew.setStime(day);
					danhGiaNew.setShiftId(idShift);
					danhGiaNew.setAlarm("Đánh giá mạng lưới");
					danhGiaNew.setCreateDate(new Date());
					String nguoiTH= "";
					if (id == null || id.equals(""))
					{
						try
						{
							nguoiTH = username;
							//nguoiTH = username+"<br/>("+ df_full1.format(new Date())+")";
							danhGiaNew.setUserProcess(nguoiTH);
							alAlarmWorksDAO.insertSelective(danhGiaNew);
							saveMessageKey(request, "danhGia.insertSucceFull");
						}
						catch(Exception exp)
						{
							saveMessageKey(request, "danhGia.insertFail");
						}
					}
					else
					{
						try
						{
							danhGiaNew.setId(Integer.parseInt(id));
							nguoiTH = username;
							/*nguoiTH = userProcess+"<br/>"+ username+"<br/>("+ df_full1.format(new Date())+")";*/
							danhGiaNew.setUserProcess(nguoiTH);
							alAlarmWorksDAO.update(danhGiaNew);
							saveMessageKey(request, "danhGia.updateSuccelfull");	
						}
						catch(Exception exp)
						{
							saveMessageKey(request, "danhGia.updateFail");
						}
					}
					
					id="";
				}
				if (id !=null && !id.equals(""))
				{
					try
					{
						AlAlarmWorks danhGia= alAlarmWorksDAO.selectByPrimaryKey(Integer.parseInt(id));
						
						if ((danhGia!=null && danhGia.getUserProcess().equals(username))||userLogin.getIsRoleManager().equals("Y"))
						{
							
							if ( danhGia.getTeamProcess()!=null)
							{
								String[] depTeam = danhGia.getTeamProcess().split("<br/>");
								dept=depTeam[0];
								if (depTeam.length>1)
									team=depTeam[1];
							}
							
							model.addAttribute("team", team);
							model.addAttribute("dept", dept);
							model.addAttribute("actionProcess", danhGia.getActionProcess());
							model.addAttribute("userProcess", danhGia.getUserProcess());
							model.addAttribute("id", danhGia.getId());
						}
						else
						{
							saveMessageKey(request, "danhGia.khongcoquyen");
						}
					}
					catch (Exception exp)
					{}
				}
				
		/*List<VRpDyVt2G> vRpDySummary2G = vRpDyVt2GDAO.filerOfHomePage("V_RP_DY_SUMMARY_2G", df.format(day), region);
		List<VRpDyAccNw3G> vRpDySummary3G = vRpDyAccNw3GDAO.filerOfHomePage("V_RP_DY_SUMMARY_3G", df.format(day), region);
		List<VRpDySgsnSummary> vRpDySummaryCorePs = vRpDySgsnSummaryDao.filerOfHomePage("V_RP_DY_SUMMARY_CORE_PS", df.format(day), region);
		List<VRpDyMscSummary> vRpDySummaryCoreCs = vRpDyMscSummaryDAO.filerOfHomePage("V_RP_DY_SUMMARY_CORE_CS", df.format(day), region);
		List<VRbDyQualityNetwork> vRbDyQualityNetwork = vRbDyQualityNetworkDAO.filerOfHomePage("V_RP_DY_QUALITY_NETWORK", df.format(day), region);
		List<AlAlarmWorks> danhGiaMLList = alAlarmWorksDAO.filerOfHomePage("AL_ALARM_WORKS", df.format(day));*/
		List<VRpDyVt2G> vRpDySummary2G = new ArrayList<VRpDyVt2G>();
		List<VRpDyAccNw3G> vRpDySummary3G = new ArrayList<VRpDyAccNw3G>();
		List<VRpDySgsnSummary> vRpDySummaryCorePs =new ArrayList<VRpDySgsnSummary>();
		List<VRpDyMscSummary> vRpDySummaryCoreCs =new ArrayList<VRpDyMscSummary>();
		List<VRbDyQualityNetwork> vRbDyQualityNetwork = new ArrayList<VRbDyQualityNetwork>();
		List<AlAlarmWorks> danhGiaMLList = alAlarmWorksDAO.filerOfHomePage("AL_ALARM_WORKS", df.format(day));
		
		model.addAttribute("vRpDySummary2G", vRpDySummary2G);
		model.addAttribute("vRpDySummary3G", vRpDySummary3G);
		model.addAttribute("vRpDySummaryCoreCs", vRpDySummaryCoreCs);
		model.addAttribute("vRpDySummaryCorePs", vRpDySummaryCorePs);  
		model.addAttribute("vRbDyQualityNetwork", vRbDyQualityNetwork);
		model.addAttribute("danhGiaMLList", danhGiaMLList);

		//Thong ke chat luong mang luoi
		String strId = "";
		try{
			for(int i = 0; i < vRbDyQualityNetwork.size(); i++)
			{
				strId += vRbDyQualityNetwork.get(i).getQualityCode().toString() + ",";
			}
			strId = strId.substring(0,strId.lastIndexOf(","));
		}
		catch(Exception e){}
		model.addAttribute("row_num", vRbDyQualityNetwork.size());
		model.addAttribute("txtId", strId);
		//email
		String userFrom = "vhcjsc@gmail.com";
		String password = "vhcsoft@123";
		String filePath="";
		String emailTo="";
		List<CSystemConfigs> configList= cSystemConfigsDAO.getSystemConfigMailDefault();
		for (CSystemConfigs cSystemConfigs : configList) {
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.userFrom"))
			{
				userFrom =  cSystemConfigs.getParamValue();
			}
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.passwordFrom"))
			{
				password =  cSystemConfigs.getParamValue();
			}
			/*if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.filePath"))
			{
				filePath =  cSystemConfigs.getParamValue();
			}*/
			
		}
		/*if (!filePath.equals(""))
		{
			List<String> fileAtt = FileTools.getFilename(filePath);
			model.addAttribute("fileAtt", fileAtt);
		}*/
		
		SysMailParameterMaster mailMaster = sysMailParamenterMasterDAO.getInformationMail("welcome");
		String content = "";
		if (mailMaster!=null)
		{
			emailTo =  mailMaster.getMailTo();
			content = mailMaster.getContentHeader();
		}
		//Danh sach nguoi nhan mail
		List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
 		alarmList = sysDefineSmsEmailDAO.getListAll("MAIL", null, "S");
 		String groupArray="var groupList = new Array(";
		String cn="";
		for (SysDefineSmsEmail sys : alarmList) {
			groupArray = groupArray + cn +"\""+sys.getGroupName()+"\"";
			cn=",";
		}
		groupArray = groupArray+");";
		model.addAttribute("groupList", groupArray);
	
		model.addAttribute("password", password);
		model.addAttribute("userFrom", userFrom);
		model.addAttribute("email", emailTo);
		
		model.addAttribute("e_content", content);
		// email
		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName1 = "ChatLuongMangLuoi_" + dateNow;
		model.addAttribute("exportFileName1", exportFileName1);
		
		String exportFileName3 = "DanhGiaMangLuoi_" + dateNow;
		model.addAttribute("exportFileName3", exportFileName3);
		return new ModelAndView("jsp/welcome");
	}

	
	@RequestMapping(value="form/save-data")
    public @ResponseBody 
    Map<String, Object> holescore( String value, String rowsid, HttpServletRequest request, HttpServletResponse response) {
		
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		String[] s = value.split(",");
		String[] rows = rowsid.split(",");
		DyQualityNetwork record = new DyQualityNetwork();
		
		 SimpleDateFormat sdf = new SimpleDateFormat(
		            "dd/MM/yyyy");
		 Date sdate = new Date();
		for(int i=0;i<s.length;i++)
		{
			try {
				sdate = sdf.parse(sdf.format(currentDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			record.setDay(sdate);
			//record.setDay(currentDate);
			record.setQualityCode(rows[i].toString());
			record.setQualityValue(s[i].toString());
			dyQualityNetworkDAO.updateByPrimaryKeySelective(record);
			
				
		}
		data.put("status", "success");
			
	/*	String[] scoreList = score.split(",");
		String[] holeIdList = holeId.split(",");
		
		Scores scores;
		
		for (int i=0; i<holeIdList.length; i++) {
			if (scoreList[i] != null && !scoreList[i].equals("")) {
				
				scores = new Scores();
				scores.setRoundNo(roundNo);
				scores.setPaId(paId);
				scores.setHoleId(Integer.parseInt(holeIdList[i]));
				scores.setScoring(Integer.parseInt(scoreList[i]));
				scores.setCreatedBy(username);
				scores.setCreateDate(new Date());
				
				scoresService.save(scores);
			}
		}
		*/
		
		
		return data;
	}
	
	@RequestMapping("/whitePage")
	public String whitePage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "jsp/whitePage";
	}
	
	/**
	 * Kiem tra dang nhap
	 */
	@RequestMapping("/loginDefault")
	public String login(@RequestParam(required = false) String module,
			@RequestParam(required = false) String loggedout,
			@RequestParam(required = false) String authfailed,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//luu lai module
		RequestContextHolder.currentRequestAttributes().setAttribute("module", module, RequestAttributes.SCOPE_SESSION);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// reset module
		//if (module == null && loggedout == null && authfailed == null) {
			//AlarmSetting._MODULE = null;
		//}
		
		// kiem tra username co thuoc module k?
		/*if(!username.equals("anonymousUser") && !sysUsersDao.getCountUserOfModule(username, module)){
			model.addAttribute("errorFalseModule", "true");
			try {
				response.sendRedirect("loginDefault.htm");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		
		if(module != null || AlarmSetting._MODULE != null){
			String record = "";
			if(module != null)
				record = module;
			else
				record = AlarmSetting._MODULE;
			
			List<SYS_PARAMETER> moduleValue= sysParameterDao.getModuleWhenLogin(record);
			if(moduleValue.size() > 0)
				model.addAttribute("moduleC2", moduleValue.get(0).getRemark());
		}
		
		
		if(!username.equals("anonymousUser")){
			
			return "redirect:temp.htm";
		}
		
		
		
		return "../login";
	}
	
	/**
	 * Dang nhap thanh cong
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/temp")
	public String redirect(
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		boolean loginFlag = false;
		try {
			loginFlag = (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("loginFlag", RequestAttributes.SCOPE_SESSION);
		} catch (Exception ex) {}
		
		// remove attributes login
		if (loginFlag) {
			RequestContextHolder.currentRequestAttributes().removeAttribute("loginFlag", RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.currentRequestAttributes().removeAttribute("globalData", RequestAttributes.SCOPE_SESSION);
			System.out.println("remove attributes login");
		}
		
		// get module da luu session
		// dua vao user va module -> home_page
		String module  = "";
		try {
			if (RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION) != null) {
				module = RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION).toString();
				
			}
			
		} catch(Exception ex) {}
		
		if (module.equals("") && AlarmSetting._MODULE != null) {
			module = AlarmSetting._MODULE;
		}
		
		System.out.println("Load module: " + module);
		
		String record = "";
		if(module != null)
			record = module;
		else
			record = AlarmSetting._MODULE;
		
		if(record != null && !record.equals("") && !sysUsersDao.getCountUserOfModule(SecurityContextHolder.getContext().getAuthentication().getName(), record)) {
			List<SYS_PARAMETER> moduleValue= sysParameterDao.getModuleWhenLogin(record);
			if(moduleValue.size() > 0)
				model.addAttribute("moduleC2", moduleValue.get(0).getRemark());
			
			model.addAttribute("noticeLogin", "Bạn không có quyền truy cập module. Vui lòng đăng nhập.");
			return "../login";
		}
		
		String page = "";
		List<SYS_PARAMETER> homePage = sysParameterDao.getHomePage(module);
		if(homePage.size() > 0)
			page = homePage.get(0).getValue();
		return "redirect:"+ page +".htm";
	}
	
	
	//Xoa
	@RequestMapping(value = "deldanhGia", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,@RequestParam (required = true) String userProcess,
			HttpServletRequest request, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		try {
			if (userProcess.equalsIgnoreCase(username)||userLogin.getIsRoleManager().equalsIgnoreCase("Y"))
			{
				alAlarmWorksDAO.deleteByPrimaryKey(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			else
			{
				saveMessageKey(request, "danhGia.khongcoquyen");
			}
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:welcome.htm";
	}
	
}
