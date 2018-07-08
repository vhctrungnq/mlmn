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

import org.apache.poi.hssf.util.HSSFColor.TEAL;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import vo.AlDyRpDip3G;
import vo.AlDyRpDip3G3Day;
import vo.AlShift;
import vo.CHighlightConfigs;
import vo.DetailLostConfig;
import vo.DetailLostConfig3g;
import vo.FilePattern;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.VAlAlarmLog;
import vo.VAlHRnc;
import vo.VAlRbLossAntenna;
import vo.VAlRbLossConfig3G;
import vo.VRAlarmLog3G;
import vo.VRlAlarmLog3G;
import vo.V_RB_LOSSCONFIG_3G;
import vo.WarningDipSame;
import vo.alarm.utils.DateTools;

import dao.AlDyRpDip3GDAO;
import dao.AlDyRpErrorDipDAO;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.HAlDyTransTypeDAO;
import dao.HAlTransTypeDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlAlarmLogDAO;
import dao.VAlHRncDAO;
import dao.VAlRbLossAntennaDAO;
import dao.VAlRbLossConfig3GDAO;
import dao.VHCell3GDAO;
import dao.VRlAlarmLog3GDAO;

@Controller
@RequestMapping("/BCChiTiet3G/*")
public class BCChiTiet3GController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private VHCell3GDAO vHCell3GDAO;
	
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired 
	private VAlHRncDAO vAlHRncDAO;
	
	@Autowired 
	private VAlRbLossAntennaDAO vAlRbLossAntennaDAO;
	
	@Autowired 
	private AlDyRpDip3GDAO alDyRpDip3GDAO;
	
	@Autowired 
	private VRlAlarmLog3GDAO vRlAlarmLog3GDAO;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired 
	private HAlTransTypeDAO hAlTransTypeDAO;
	
	@Autowired 
	private VAlRbLossConfig3GDAO vAlRbLossConfig3GDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
		@RequestMapping("dsach_ch")
		public ModelAndView list(
				@RequestParam(required = false) String startTime,
				@RequestParam(required = false) String endTime,
				@RequestParam(required = false) String rncid,
				@RequestParam(required = false) String alarmLevel,
				@RequestParam(required = false) String cellid,
				@RequestParam(required = false) String totalMD,
				@RequestParam(required = false) String totalTimeE,
				@RequestParam(required = false) String dvtTeamProcess,
				@RequestParam(required = false) String dvtUserProcess,
				/*@RequestParam(required = false) String causeby,*/
				@RequestParam(required = false) String statusKTMCH,
				@RequestParam(required = true) String alarmType,
				@RequestParam(required = false) String reload,
				@RequestParam(required = false) String reloadStr,
				@RequestParam(required = false) String timeLoad,
				Model model, HttpServletRequest request) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			AlShift shift = getCaTrucHT(username,null);
			boolean checkCaTruc= false;
			if (shift!=null)
			{
				checkCaTruc=true;
				model.addAttribute("checkCaTruc", checkCaTruc);
			}
			if (rncid==null)
			{
				rncid="";
			}
			else
				rncid = rncid.trim();
			if (alarmLevel==null)
			{
				alarmLevel="";
			}
			else
				alarmLevel = alarmLevel.trim();
			if (cellid==null)
			{
				cellid="";
			}
			else
				cellid = cellid.trim();
			if (totalMD==null)
			{
				totalMD="";
			}
			else
				totalMD = totalMD.trim();
			if (totalTimeE==null)
			{
				totalTimeE="";
			}
			else
				totalTimeE= totalTimeE.trim();
			if (dvtUserProcess==null)
				{
				dvtUserProcess="";
				}
				else
					dvtUserProcess = dvtUserProcess.trim();
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossConfig3GDAO.titleForm(alarmType,null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getAlarmType();
			model.addAttribute("alarmTypeList", alarmTypeList);
			
			if (statusKTMCH==null)
			{
				statusKTMCH="N";
			}
			if (reloadStr==null)
			{
				reload="Y";
				reloadStr="Y";
			}
			if (timeLoad == null) {
				timeLoad = "180";
			}
			else
			{
				try
				{
					int timeLoadInt = Integer.parseInt(timeLoad);
				}
				catch(Exception exp)
				{
					timeLoad = "180";
				}
			}
			model.addAttribute("reload", reload);
			model.addAttribute("timeLoad", timeLoad);
			model.addAttribute("team",dvtTeamProcess);
			model.addAttribute("rncid", rncid);
			model.addAttribute("alarmLevel", alarmLevel);
			model.addAttribute("cellid", cellid);
			model.addAttribute("dvtUserProcess", dvtUserProcess);
			/*model.addAttribute("causeby", causeby);*/
			model.addAttribute("statusKTMCH", statusKTMCH);
			
			model.addAttribute("alarmType", alarmType);
			model.addAttribute("totalMD", totalMD);
			model.addAttribute("totalTimeE", totalTimeE);
			
			int order = 0;
			String column = "RNCID";
			try {
				order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			
			if (startTime == null || startTime.equals("")
					||(startTime!=null && !startTime.equals("") && DateTools
								.isValid("dd/MM/yyyy HH:mm", startTime)==false))
			{
				startTime = df_full.format(cal.getTime())+" "+"00:00";
			}
			
			if (endTime == null || endTime.equals("")
					||(endTime!=null && !endTime.equals("") && DateTools
								.isValid("dd/MM/yyyy HH:mm", endTime)==false))
			{
				endTime = df_full.format(cal.getTime())+" "+"23:59";
			}
			
			List<V_RB_LOSSCONFIG_3G> alarmList = new ArrayList<V_RB_LOSSCONFIG_3G>();
			
			int totalTimeEN=0;
			int totalTime=0;
			if (totalMD == null||(totalMD != null&&totalMD.equals("")))
			{
				totalTime=0;
				totalMD="0";
			}
			if (totalTimeE == null||(totalTimeE != null&&totalTimeE.equals("")))
			{
				totalTimeEN=0;
				totalTimeE="0";
			}
			try
			{
				totalTime = Integer.parseInt(totalMD);
				totalTimeEN = Integer.parseInt(totalTimeE);
				if (rncid!=null && !rncid.equals(""))
					rncid = rncid.replace(" ", ",");
				if (alarmLevel!=null && !alarmLevel.equals(""))
					alarmLevel = alarmLevel.replace(" ", ",");
				alarmList = vAlRbLossConfig3GDAO.getLossConfig3GList(startTime,endTime,rncid,alarmLevel,cellid,totalTime,totalTimeEN,dvtTeamProcess,dvtUserProcess,null,statusKTMCH,alarmType,column, order);	
			}
			catch (Exception exp)
			{
				alarmList= null;
			}
			model.addAttribute("alarmList", alarmList);
			
			List<SYS_PARAMETER> statusKTMCHList = sysParameterDao.getStatusFinish();
			model.addAttribute("statusKTMCHList", statusKTMCHList);
			
			/*List<String> rncList = vAlHRncDAO.getRNCList(null);
			model.addAttribute("rncList", rncList);*/
			List<String> siteidList = vHCell3GDAO.getSite3GByBsc(rncid);
			model.addAttribute("siteidList", siteidList);
			
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "AlLossCofiguratuon3G_" + dateNow;
			model.addAttribute("exportFileName", exportFileName);
			List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("SHIFT_ID");
			if (highlightConfigList2.size()>0)
			{
				String highlight = " $(this).css({"+highlightConfigList2.get(0).getStyle()+"});";
				model.addAttribute("highlight",highlight);
				
			}
			return new ModelAndView("jsp/BCChiTiet3G/BCMatCauHinhList");
		}
		
		@RequestMapping(value = "cau_hinh_delete", method = RequestMethod.GET)
		public String onDelete(@RequestParam(required = false) String id,
				@RequestParam(required = true) String alarmType,
				HttpServletRequest request, Model model) {	
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
			AlShift shift = getCaTrucHT(username,null);
			
			if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
			{
				int alarmID=0;
				try {
					alarmID = Integer.parseInt(id);
					vAlRbLossConfig3GDAO.deleteByPrimaryKey(alarmID);
					saveMessageKey(request, "messsage.confirm.deletesuccess");
					}
				catch (Exception e) {
					saveMessageKey(request, "messsage.confirm.erorDateOrNumber");
				}
				
			}
			else
				saveMessageKey(request, "messsage.confirm.deletefailureShift");
			
			return "redirect:dsach_ch.htm?alarmType="+alarmType;
		}
		@RequestMapping(value = "cau_hinh_form", method = RequestMethod.GET)
		public String showForm(@RequestParam(required = false) String id,
				@RequestParam(required = false) String alarmType,
				ModelMap model, HttpServletRequest request) {
			
			V_RB_LOSSCONFIG_3G alarm = new V_RB_LOSSCONFIG_3G();
			String sdate="";
			String edate="";
			String team="";
			String rncid="";
			String alarmLevel="";
			String ddhBaoMch="";
			String causeSdate="";
			String causeEdate="";
			String causeBySy="";
			int alarmID=0;
			try {
				alarmID = Integer.parseInt(id);
				}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.erorDateOrNumber");
			}
			
			/*alarm = vAlRbLossConfig3GDAO.selectByMore(stime,rncid,alarmLevel,alarmID);*/
			alarm = vAlRbLossConfig3GDAO.selectByPrimaryKey(alarmID);
			List<VRAlarmLog3G> alarmSameList = new ArrayList<VRAlarmLog3G>();
			if (alarm!=null)
			{
				edate = alarm.getEdateStr();
				rncid=alarm.getRncid();
				sdate=alarm.getSdateStr();
				team= alarm.getDvtTeamProcess();
				alarmLevel= alarm.getAlarmLevel();
				ddhBaoMch= alarm.getDdhBaoMchStr();
				causeSdate= alarm.getCauseSdateStr();
				causeEdate= alarm.getCauseEdateStr();
				causeBySy= alarm.getCausebySy();
				/*String site = alarmLevel.substring(0, alarmLevel.length()-1);*/
				try
				{
					alarmSameList = vRlAlarmLog3GDAO.getAlarmSameSystem(rncid, alarmLevel, null, sdate, edate);
				}
				catch (Exception exp){}
			}
			model.addAttribute("alarmSameList", alarmSameList);
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossConfig3GDAO.titleForm(alarmType,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
			}
			
			model.addAttribute("sdate", sdate);
			model.addAttribute("edate",edate );
			model.addAttribute("ddhBaoMch",ddhBaoMch );
			model.addAttribute("causeSdate",causeSdate );
			model.addAttribute("causeEdate",causeEdate );
			model.addAttribute("team", team );
			model.addAttribute("alarmType", alarmType);
			model.addAttribute("causebySystem", causeBySy);
			model.addAttribute("vAlRbLossConfig3G", alarm);		
			
			/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
			model.addAttribute("teamList", teamList);*/
			List<SYS_PARAMETER> causebySyList = sysParameterDao.getCausebySystem();
			model.addAttribute("causebySyList", causebySyList);
			/*List<String> rncList = vAlHRncDAO.getRNCList(null);
			model.addAttribute("rncList", rncList);*/
			model.addAttribute("rncid", rncid);
			
			List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("SHIFT_ID");
			if (highlightConfigList2.size()>0)
			{
				String highlight = " $(this).css({"+highlightConfigList2.get(0).getStyle()+"});";
				model.addAttribute("highlight",highlight);
				
			}
			return "jsp/BCChiTiet3G/BCMatCauHinhForm";
		}
		
		@RequestMapping(value = "cau_hinh_form", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("vAlRbLossConfig3G") @Valid V_RB_LOSSCONFIG_3G vAlRbLossConfig3G, BindingResult result,
				@RequestParam(required = false) List<String> dvtUserProcess,
				@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				@RequestParam(required = false) String ddhBaoMch,
				@RequestParam(required = false) String causeSdate,
				@RequestParam(required = false) String causeEdate,
				@RequestParam(required = false) String dvtTeamProcess,
				@RequestParam(required = false) String rncid,
				@RequestParam(required = false) String alarmType, 
				@RequestParam(required = false) String causebySystem,
				ModelMap model, HttpServletRequest request) throws ParseException {
			

			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
			List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("SHIFT_ID");
			if (highlightConfigList2.size()>0)
			{
				String highlight = " $(this).css({"+highlightConfigList2.get(0).getStyle()+"});";
				model.addAttribute("highlight",highlight);
				
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
			model.addAttribute("vAlRbLossConfig3G", vAlRbLossConfig3G);	
			model.addAttribute("team", dvtTeamProcess);
			model.addAttribute("sdate",sdate);
			model.addAttribute("edate", edate);
			model.addAttribute("ddhBaoMch",ddhBaoMch);
			model.addAttribute("causeEdate",causeEdate);
			model.addAttribute("causeSdate",causeSdate);
			model.addAttribute("alarmType",alarmType);
			model.addAttribute("causebySystem",causebySystem);
			model.addAttribute("rncid", rncid);
			
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossConfig3GDAO.titleForm(alarmType,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
			}
			
			AlShift shift = getCaTrucHT(username,null);
			boolean checkCaTruc= false;
			Integer shiftID=null;
			if (shift!=null)
			{
				checkCaTruc=true;
				shiftID = shift.getShiftId();
			}
			if (checkCaTruc==false)
			{
				saveMessageKey(request, "cautruc.KhongCoQuyen");
				 return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			
			if (sdate==null || (sdate!=null && (sdate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdate+":00")==false)))
			{
				model.addAttribute("errorsdate", "*");
				saveMessageKey(request, "alarmExtend.errorField");
				return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			if (edate!=null && !edate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", edate+":00")==false)
			{
				model.addAttribute("erroredate", "*");
				saveMessageKey(request, "alarmExtend.errorField");
				return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			if (ddhBaoMch!=null && !ddhBaoMch.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", ddhBaoMch+":00")==false)
			{
				model.addAttribute("errorddhBaoMch", "*");
				saveMessageKey(request, "alarmExtend.errorField");
				return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			if (causeSdate!=null && !causeSdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", causeSdate+":00")==false)
			{
				model.addAttribute("errorcauseSdate", "*");
				saveMessageKey(request, "alarmExtend.errorField");
				return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			if (causeEdate!=null && !causeEdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", causeEdate+":00")==false)
			{
				model.addAttribute("errorcauseEdate", "*");
				saveMessageKey(request, "alarmExtend.errorField");
				return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			if (result.hasErrors()) {
				saveMessageKey(request, "alarmExtend.errorField");
				 return "jsp/BCChiTiet3G/BCMatCauHinhForm";
			}
			else
			{
				List<VRAlarmLog3G> alarmSameList = new ArrayList<VRAlarmLog3G>();
				try
				{
					alarmSameList = vRlAlarmLog3GDAO.getAlarmSameSystem(vAlRbLossConfig3G.getRncid(),vAlRbLossConfig3G.getAlarmLevel(),vAlRbLossConfig3G.getAlarmType(),sdate,edate);
					model.addAttribute("alarmSameList", alarmSameList);
				}
				catch (Exception exp){}
				
				vAlRbLossConfig3G.setAlarmType(alarmType);
				vAlRbLossConfig3G.setDvtTeamProcess(dvtTeamProcess);
				vAlRbLossConfig3G.setDvtUserProcess(userProc);
				vAlRbLossConfig3G.setShiftId(shiftID);
				vAlRbLossConfig3G.setCausebySy(causebySystem);
				if (!sdate.equals("")){
					vAlRbLossConfig3G.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate+":00"));
				}
				else
					vAlRbLossConfig3G.setSdate(null);
				
				if (!edate.equals("")){
					vAlRbLossConfig3G.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate+":00"));
				}
				else
					vAlRbLossConfig3G.setEdate(null);
				if (!causeSdate.equals("")){
					vAlRbLossConfig3G.setCauseSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(causeSdate+":00"));
				}
				else
					vAlRbLossConfig3G.setCauseSdate(null);
				if (!causeEdate.equals("")){
					vAlRbLossConfig3G.setCauseEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(causeEdate+":00"));
				}
				else
					vAlRbLossConfig3G.setCauseEdate(null);
				if (!ddhBaoMch.equals("")){
					vAlRbLossConfig3G.setDdhBaoMch((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(ddhBaoMch+":00"));
				}
				else
					vAlRbLossConfig3G.setDdhBaoMch(null);
				if ((!edate.equals("")&& vAlRbLossConfig3G.getSdate().getTime()>= vAlRbLossConfig3G.getEdate().getTime())
						||(!causeEdate.equals("")&&!causeSdate.equals("")&& vAlRbLossConfig3G.getCauseSdate().getTime()>= vAlRbLossConfig3G.getCauseEdate().getTime()))
					
				{
					saveMessageKey(request, "cautruc.sosanhNgay");
					return "jsp/BCChiTiet3G/BCMatCauHinhForm";
				}
				if (userProc.length()>255)
				{
					saveMessageKey(request, "alarmExtend.moreUse");
					return "jsp/BCChiTiet3G/BCMatCauHinhForm";
				}
				/*if (vAlRbLossConfig3G.getId()==null)
				{
						
						vAlRbLossConfig3GDAO.insertSelective(vAlRbLossConfig3G);
						saveMessageKey(request, "vAlRbLossConfig3G.insertSucceFull");
				}
						
				else{*/
				try
				{
						vAlRbLossConfig3GDAO.update(vAlRbLossConfig3G);
						saveMessageKey(request, "vAlRbLossConfig3G.updateSuccelfull");	
				}
				catch(Exception exp)
				{
				}
				/*}*/
					
			}
			
			
			model.addAttribute("rncid","");
			model.addAttribute("team","");
			
			sysParemeterTitle = vAlRbLossConfig3GDAO.titleForm(alarmType,null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			
			model.addAttribute("team","");
			model.addAttribute("rncid", "");
			model.addAttribute("alarmLevel", "");
			model.addAttribute("cellid", "");
			model.addAttribute("dvtUserProcess", "");
			return "redirect:dsach_ch.htm";
		}
		
		@ModelAttribute("transTypeList")
		public List<String> transTypeList() {
			return hAlTransTypeDAO.getNameTransTypeList();
		}
		
		@ModelAttribute("teamList")
		public List<MDepartment> teamList() {
			return mDepartmentDAO.getDepartementBySystem();
			
		}
		
		@ModelAttribute("rncList")
		public List<String> rncList() {
			return vAlHRncDAO.getRNCList(null);
			
		}
	
		@RequestMapping("chapchon")
		public ModelAndView chapChonlist(
				@RequestParam(required = false) String Sday,
				@RequestParam(required = false) String Eday,
				@RequestParam(required = false) String items,
				@RequestParam(required = false) String itemsE,
				@RequestParam(required = false) String transType,
				@RequestParam(required = false) String rnc,
				@RequestParam(required = false) String nodeb,
				@RequestParam(required = false) String dvtTeamProcess,
				@RequestParam(required = false) String dvtUserProcess,
				Model model, HttpServletRequest request) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDip3GDAO.titleLossDip(null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			AlShift shift = getCaTrucHT(username,null);
			boolean checkCaTruc= false;
			if (shift!=null)
			{
				checkCaTruc=true;
				model.addAttribute("checkCaTruc", checkCaTruc);
			}
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
			
			
			if (rnc==null)
			{
				rnc="";
			}
			else
				rnc = rnc.trim();
			if (nodeb==null)
			{
				nodeb="";
			}
			else
				nodeb = nodeb.trim();
			if (dvtUserProcess==null)
			{
				dvtUserProcess="";
			}
			else
				dvtUserProcess = dvtUserProcess.trim();
			
			int order = 0;
			String column = "RNC";
			try {
				order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
		
			if(Sday == null|| Eday == null
					||(Sday!=null && !Sday.equals("") && DateTools.isValid("dd/MM/yyyy", Sday)==false)
					||(Eday!=null && !Eday.equals("") && DateTools.isValid("dd/MM/yyyy", Eday)==false)	)
				{
					long time = System.currentTimeMillis();
					DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
					Date startDay = dt.toDate();
					Date destDay = new Date(time);
					Sday   = df.format(startDay);
					Eday 	= df.format(destDay);
				}
			model.addAttribute("team",dvtTeamProcess);
			model.addAttribute("rnc", rnc);
			model.addAttribute("items", items);
			model.addAttribute("itemsE", itemsE);
			model.addAttribute("transType", transType);
			model.addAttribute("nodeb", nodeb);
			model.addAttribute("Sday", Sday);
			model.addAttribute("Eday", Eday);
			
			List<AlDyRpDip3G> alarmList = new ArrayList<AlDyRpDip3G>();
			int itemSl=0;
			int itemSlE=0;
			
			
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
				
					itemSl = Integer.parseInt(items);
					itemSlE = Integer.parseInt(itemsE);
					if (rnc!=null && !rnc.equals(""))
						rnc = rnc.replace(" ", ",");
					if (nodeb!=null && !nodeb.equals(""))
						nodeb = nodeb.replace(" ", ",");
					alarmList = alDyRpDip3GDAO.getLossDip3GList(Sday,Eday,rnc,nodeb,dvtTeamProcess,dvtUserProcess,itemSl,itemSlE,transType,column, order);	
			}
			catch (Exception exp)
			{
				alarmList= null;
			}
			model.addAttribute("alarmList", alarmList);
			
			
	
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "AlDyRbDip3G_" + dateNow;
			model.addAttribute("exportFileName", exportFileName);
			
			return new ModelAndView("jsp/BCChiTiet3G/ChapTronTDList");
		}
		
		@RequestMapping(value = "chapchon_delete", method = RequestMethod.GET)
		public String chapchonDelete(@RequestParam(required = false) String id,
				HttpServletRequest request, Model model) {	
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			AlShift shift = getCaTrucHT(username,null);
			boolean checkCaTruc= false;
			if (shift!=null)
			{
				try {
					alDyRpDip3GDAO.deleteByPrimaryKey(Integer.parseInt(id));
					saveMessageKey(request, "messsage.confirm.deletesuccess");
					}
				catch (Exception e) {
					saveMessageKey(request, "messsage.confirm.erorDateOrNumber");
				}
				
			}
			else
				saveMessageKey(request, "messsage.confirm.deletefailureShift");
			
			return "redirect:chapchon.htm";
		}
		@RequestMapping(value = "chapchon_form", method = RequestMethod.GET)
		public String chapchonshow(@RequestParam(required = false) String id,
				ModelMap model, HttpServletRequest request) {

			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDip3GDAO.titleLossDip("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			
			AlDyRpDip3G alarm = new AlDyRpDip3G();
			int alarmID=0;
			try {
					alarmID = Integer.parseInt(id);
					alarm = alDyRpDip3GDAO.selectByPrimaryKey(alarmID);
					model.addAttribute("transType",alarm.getTransType());
					model.addAttribute("day",alarm.getDayStr());
				}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.erorDateOrNumber");
			}
			model.addAttribute("team", alarm.getDvtTeamProcess() );
			model.addAttribute("alDyRpDip3G", alarm);		
			model.addAttribute("rnc", alarm.getRnc());
			
			return "jsp/BCChiTiet3G/ChapTronTDForm";
		}
		
		@RequestMapping(value = "chapchon_form", method = RequestMethod.POST)
		public String chapchonSubmit(@ModelAttribute("alDyRpDip3G") @Valid AlDyRpDip3G alDyRpDip3G, BindingResult result,
				@RequestParam(required = false) List<String> dvtUserProcess,
				@RequestParam(required = false) String day,
				@RequestParam(required = false) String dvtTeamProcess,
				@RequestParam(required = false) String transType,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDip3GDAO.titleLossDip("ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			String userProc ="";
			if (dvtUserProcess!=null && dvtUserProcess.size()>0)
			{
				userProc= dvtUserProcess.get(0);
				for (int i=1;i<dvtUserProcess.size();i++)
				{
					userProc+=","+ dvtUserProcess.get(i);
				}
			}
			model.addAttribute("alDyRpDip3G", alDyRpDip3G);	
			model.addAttribute("day",day);
		
			AlShift shift = getCaTrucHT(username,null);
			boolean checkCaTruc= false;
			Integer shiftID= null;
			if (shift!=null)
			{
				checkCaTruc=true;
				shiftID = shift.getShiftId();
			}
			if (checkCaTruc==false)
			{
				saveMessageKey(request, "cautruc.KhongCoQuyen");
				model.addAttribute("team", dvtTeamProcess);
				model.addAttribute("transType", transType);
				 return "jsp/BCChiTiet3G/ChapTronTDForm";
			}
			if (day==null || (day!=null &&( day.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", day+" 00:00:00")==false)))
			{
				model.addAttribute("errorsdate", "*");
				saveMessageKey(request, "alarmExtend.errorField");
				model.addAttribute("team", dvtTeamProcess);
				model.addAttribute("transType", transType);
				return "jsp/BCChiTiet3G/ChapTronTDForm";
			}
			if (result.hasErrors()) {
				saveMessageKey(request, "alarmExtend.errorField");
				model.addAttribute("team", dvtTeamProcess);
				model.addAttribute("transType", transType);
				 return "jsp/BCChiTiet3G/ChapTronTDForm";
			}
			else
			{
				alDyRpDip3G.setDvtTeamProcess(dvtTeamProcess);
				alDyRpDip3G.setDvtUserProcess(userProc);
				alDyRpDip3G.setShiftId(shiftID);
				alDyRpDip3G.setTransType(transType);
				if (!day.equals("")){
					alDyRpDip3G.setDay((new SimpleDateFormat("dd/MM/yyyy")).parse(day));
				}
				if (alDyRpDip3G.getId()==null)
				{
						
						alDyRpDip3GDAO.insertSelective(alDyRpDip3G);
						saveMessageKey(request, "alDyRpDip3G.insertSucceFull");
				}
						
				else{
						alDyRpDip3GDAO.update(alDyRpDip3G);
						saveMessageKey(request, "alDyRpDip3G.updateSuccelfull");	
					}
					
			}
			model.addAttribute("rncid","");
			model.addAttribute("team","");
			
			return "redirect:chapchon.htm";
		}
		@RequestMapping("lostDip3G3Day")
		public ModelAndView list3Day(
				@RequestParam(required = false) String day,
				@RequestParam(required = false) String rnc,
				@RequestParam(required = false) String nodeb,
				@RequestParam(required = false) String dvtTeamProcess,
				Model model, HttpServletRequest request) throws ParseException {
			List<SYS_PARAMETER> sysParemeterTitle = alDyRpDip3GDAO.titleLossDip3G(null);
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			if (rnc==null)
			{
				rnc="";
			}
			else
				rnc = rnc.trim();
			if (nodeb==null)
			{
				nodeb="";
			}
			else
				nodeb = nodeb.trim();
			List<AlDyRpDip3G3Day> alarmList = new ArrayList<AlDyRpDip3G3Day>();
			
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
			
			model.addAttribute("rnc", rnc);
			model.addAttribute("team", dvtTeamProcess);
			model.addAttribute("day", day);
			model.addAttribute("day1", day1);
			model.addAttribute("day2", day2);
			model.addAttribute("nodeb", nodeb);
		
			try
			{
				if (rnc!=null && !rnc.equals(""))
					rnc = rnc.replace(" ", ",");
				if (nodeb!=null && !nodeb.equals(""))
					nodeb = nodeb.replace(" ", ",");
				alarmList = alDyRpDip3GDAO.getAllDip3G3Day(day,rnc,nodeb,dvtTeamProcess);
			}
			catch(Exception exp)
			{
				alarmList =null;
			}
		model.addAttribute("rnc", rnc);
		model.addAttribute("alarmList", alarmList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AlLossDip3G3Day_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		return new ModelAndView("jsp/BCChiTiet3G/ChapTron3Ngay");
		
	}
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String detail(
			@RequestParam(required = true) String rncid,
			@RequestParam(required = true) String alarmLevel,
			@RequestParam(required = true) String startTime,
			@RequestParam(required = true) String alarmType,
			
			Model model, HttpServletRequest request) {
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossConfig3GDAO.titleFormDetail();
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleD", sysParemeterTitle.get(0).getValue());
			}
			
			List<VRAlarmLog3G> detailList = new ArrayList<VRAlarmLog3G>();
			model.addAttribute("alarmLevel",alarmLevel);
			model.addAttribute("rncid", rncid);
			model.addAttribute("startTime", startTime);
			model.addAttribute("alarmType", alarmType);
			
			if (startTime!=null)
			{
				try
				{
					detailList = vRlAlarmLog3GDAO.getAlarmSameSystem(rncid,alarmLevel,alarmType,startTime,null);
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
			String exportFileName = "VAlRbLossConfig3GDetail_" + dateNow;
			model.addAttribute("exportFileName", exportFileName);

			return "jsp/BCChiTiet3G/MCHChiTiet";
		}
		
		@RequestMapping("ajax/checkedList")
		public @ResponseBody
		int checkedList(@RequestParam(required = false) String checkedList,
				@RequestParam(required = false) String uncheckedList, HttpServletRequest request) {
			if(checkedList != "" || uncheckedList != "")
			{
				String[] checkList = checkedList.split("-");
				String[] uncheckList = uncheckedList.split("-");
				for(int i=0;i<checkList.length;i++)
				{
					vAlRbLossConfig3GDAO.updateStatusViewByPrimaryKey("3G","Y", checkList[i].toString());
				}
				
				for(int j=0;j<uncheckList.length;j++)
				{
					vAlRbLossConfig3GDAO.updateStatusViewByPrimaryKey("3G","N", uncheckList[j].toString());
				}
				saveMessageKey(request, "vAlRbLossConfiguration.updateStatusSuccelfull");
			}
			else
				saveMessageKey(request, "vAlRbLossConfiguration.updateFall");
			return 1;
		}
}
