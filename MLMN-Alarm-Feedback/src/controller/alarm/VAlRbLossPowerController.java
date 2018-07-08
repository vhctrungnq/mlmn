package controller.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.AlMonitorTemperatureSite;
import vo.AlShift;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CableTransmission;
import vo.DetailLostConfig;
import vo.IsoInventory;
import vo.MDepartment;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysUsers;
import vo.VAlRbLossPower;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import dao.AlAlarmTypesDAO;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.RAlarmLossPowerLogDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import dao.VAlHBscDAO;
import dao.VAlHCellDAO;
import dao.VAlRbLossConfigurationDAO;
import dao.VAlRbLossPowerDAO;

@Controller
@RequestMapping("/LossPower/*")
public class VAlRbLossPowerController extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired 
	private VAlRbLossPowerDAO vAlRbLossPowerDAO;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired 
	private VAlHBscDAO vAlHBscDAO;
	
	@Autowired 
	private VAlHCellDAO vAlHCellDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;

	@Autowired 
	private VAlRbLossConfigurationDAO vAlRbLossConfigurationDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	@Autowired
	private MDepartmentDAO mDepartmentDao;
	
	@Autowired
	private RAlarmLossPowerLogDAO rAlarmLossPowerLogDAO;
	
	
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
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
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String totalMD,
			@RequestParam(required = false) String totalMDE,
			@RequestParam(required = false) String dvtTeamProcess,
			@RequestParam(required = false) String dvtUserProcess,
			@RequestParam(required = false) String ungCuuMpd,
			@RequestParam(required = false) String nodeTruyenDan,
			@RequestParam(required = false) String acLow,
			@RequestParam(required = false) String mch1800,
			@RequestParam(required = false) String statusKTMD,
			@RequestParam(required = false) String statusKTMLL,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			@RequestParam(required = false) String timeLoad,
			@RequestParam(required = false) String region,
			Model model, HttpServletRequest request) {
		
	
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		AlShift shift = getCaTrucHT(username,region);
		boolean checkCaTruc= false;
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			checkCaTruc=true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (cellid==null)
		{
			cellid="";
		}
		else
			cellid = cellid.trim();
		if (bscid==null)
		{
			bscid="";
		}
		else
			bscid = bscid.trim();
		if (region==null)
		{
			region="";
		}
		else
			region = region.trim();
		
		if (totalMD==null)
		{
			totalMD="";
		}
		else
			totalMD = totalMD.trim();
		if (totalMDE==null)
		{
			totalMDE="";
		}
		else
			totalMDE = totalMDE.trim();
		if (dvtUserProcess==null)
		{
			dvtUserProcess="";
		}
		else
			dvtUserProcess = dvtUserProcess.trim();
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
		if (statusKTMD==null)
		{
			statusKTMD="N";
		}
		if (statusKTMLL==null)
		{
			statusKTMLL="N";
		}
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm(null);
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
		List<VAlRbLossPower> alarmList = new ArrayList<VAlRbLossPower>();
		
		Calendar cal = Calendar.getInstance();	 
		cal.setTime(new Date(cal.getTimeInMillis() - 24 * 60 * 60 * 1000));
		if (endTime == null || endTime.equals("")||(endTime!=null && !endTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endTime+" 00:00:00")==false))
		{	
			  
			endTime = df_full.format(cal.getTime());
		}
		if (startTime == null || startTime.equals("")||(startTime!=null && !startTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startTime+" 00:00:00")==false))
		{
			
			startTime = endTime;
		}
		
		model.addAttribute("acLow", acLow);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		
		model.addAttribute("dvtUserProcess", dvtUserProcess);
		model.addAttribute("statusKTMLL", statusKTMLL);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("ungCuuMpd", ungCuuMpd);
		model.addAttribute("nodeTruyenDan", nodeTruyenDan);
		model.addAttribute("mch1800", mch1800);
		model.addAttribute("team", dvtTeamProcess);
		model.addAttribute("reload", reload);
		model.addAttribute("timeLoad", timeLoad);
		model.addAttribute("region", region);
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		
		
		List<SYS_PARAMETER> paraList = sysParameterDao.getStatusNode();
		model.addAttribute("paraList", paraList);
		
		List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getStatusDVTUCTT();
		model.addAttribute("ungCuuMpdList", ungCuuMpdList);
		
		List<String> bscidList = vAlHBscDAO.getBSC23GByTeam(dvtTeamProcess);
		model.addAttribute("bscidList", bscidList);
		
		List<SYS_PARAMETER> statusKTMDList = sysParameterDao.getStatusFinish();
		model.addAttribute("statusKTMDList", statusKTMDList);
		model.addAttribute("statusKTMD", statusKTMD);
		
		int totalTimeFN=0;
		int totalTimeEN=0;
		if (totalMD == null||(totalMD != null&&totalMD.equals("")))
		{
			totalTimeFN=0;
			totalMD="0";
		}
		if (totalMDE == null||(totalMDE != null&&totalMDE.equals("")))
		{
			totalTimeEN=0;
			totalMDE="0";
		}
		
			try
			{
				totalTimeFN = Integer.parseInt(totalMD);
				totalTimeEN = Integer.parseInt(totalMDE);
			} 
			catch(Exception exp)
			{
				alarmList = null;
				return new ModelAndView("jsp/BCMatDien/BCMatDienList");
			}
			if (cellid!=null && !cellid.equals("") )
				cellid = cellid.replace(" ", ",");
			if (bscid!=null && !bscid.equals(""))
				bscid = bscid.replace(" ", ",");
			
			alarmList = vAlRbLossPowerDAO.getVAlRbLossPowerFilter(startTime,endTime,bscid,cellid,totalTimeFN, totalTimeEN, dvtTeamProcess,dvtUserProcess,ungCuuMpd,nodeTruyenDan,acLow,mch1800,statusKTMD,statusKTMLL,order,column,region);

		model.addAttribute("alarmList", alarmList);
		
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("SHIFT_ID");
		if (highlightConfigList2.size()>0)
		{
			String highlight = " $(this).css({"+highlightConfigList2.get(0).getStyle()+"});";
			model.addAttribute("highlight",highlight);
			
		}
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("UNG_CUU_MPD");
		if (highlightConfigList.size()>1)
		{
			String highlight1 = " $(this).find(\"td\").css({"+highlightConfigList.get(0).getStyle()+"});";
			model.addAttribute("highlight1",highlight1);
			String highlight2 = " $(this).find(\"td\").css({"+highlightConfigList.get(1).getStyle()+"});";
			model.addAttribute("highlight2",highlight2);
			
		}
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "VAlRbLossPower" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
				
		model.addAttribute("totalMD", totalMD);
		model.addAttribute("totalMDE", totalMDE);
		return new ModelAndView("jsp/BCMatDien/BCMatDienList");
	}
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		VAlRbLossPower alarm = new VAlRbLossPower();
		if (id!=null)
		{
			alarm = vAlRbLossPowerDAO.selectByPrimaryKey(Integer.parseInt(id));
			model.addAttribute("sdate", alarm.getSdateStr() );
			model.addAttribute("edate", alarm.getEdateStr() );
			model.addAttribute("ddhBaoMd", alarm.getDDHBaoMdStr() );
			model.addAttribute("mllSdate", alarm.getMLLSdateStr() );
			model.addAttribute("mllEdate", alarm.getMLLEdateStr() );
			model.addAttribute("ddhBaoMll", alarm.getDDHBaoMllStr() );
			model.addAttribute("team", alarm.getDvtTeamProcess() );
			model.addAttribute("ungCuuMpd", alarm.getUngCuuMpd() );
			model.addAttribute("nodeTruyenDan", alarm.getNodeTruyenDan() );
			model.addAttribute("acLow", alarm.getAcLow() );
			model.addAttribute("mch1800", alarm.getMch1800());
			model.addAttribute("region", alarm.getRegion());
		}
		model.addAttribute("vAlRbLossPower", alarm);		
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		/*List<SYS_PARAMETER> paraList = sysParameterDao.getSysParameterByCode("LossPower_Node");*/
		List<SYS_PARAMETER> paraList = sysParameterDao.getStatusNode();
		model.addAttribute("paraList", paraList);
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
				
		/*List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getSysParameterByCode("LossPower_ĐVT_UCTT_MPĐ");*/
		List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getStatusDVTUCTT();
		model.addAttribute("ungCuuMpdList", ungCuuMpdList);
		
		return "jsp/BCMatDien/BCMatDienForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("vAlRbLossPower") @Valid VAlRbLossPower vAlRbLossPower, BindingResult result,
			@RequestParam(required = false) List<String> dvtUserProcess,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String ddhBaoMd,
			@RequestParam(required = false) String mllSdate,
			@RequestParam(required = false) String mllEdate,
			@RequestParam(required = false) String ddhBaoMll,
			@RequestParam(required = false) String dvtTeamProcess,
			@RequestParam(required = false) String ungCuuMpd,
			@RequestParam(required = false) String nodeTruyenDan,
			@RequestParam(required = false) String acLow,
			@RequestParam(required = false) String mch1800,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request) throws ParseException {
		

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("ADD");
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
	
		model.addAttribute("vAlRbLossPower", vAlRbLossPower);	
		
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		
		List<SYS_PARAMETER> paraList = sysParameterDao.getStatusNode();
		model.addAttribute("paraList", paraList);
		
		List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getStatusDVTUCTT();
		model.addAttribute("ungCuuMpdList", ungCuuMpdList);
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
				
		model.addAttribute("sdate",sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("ddhBaoMd",ddhBaoMd);
		model.addAttribute("mllSdate", mllSdate);
		model.addAttribute("mllEdate", mllEdate);
		model.addAttribute("ddhBaoMll",ddhBaoMll);
		model.addAttribute("team", dvtTeamProcess);
		model.addAttribute("ungCuuMpd",ungCuuMpd);
		model.addAttribute("nodeTruyenDan",nodeTruyenDan);
		model.addAttribute("acLow", acLow);
		model.addAttribute("mch1800", mch1800 );
		model.addAttribute("dvtUserProcess", userProc );
		model.addAttribute("region", region );
		
		AlShift shift = getCaTrucHT(username,region);
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
			 return "jsp/BCMatDien/BCMatDienForm";
		}
		if (sdate==null || (sdate!=null && (sdate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdate+":00")==false)))
		{
			model.addAttribute("errorsdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/BCMatDienForm";
		}
		if (edate!=null && !edate.equals("")   && DateTools.isValid("dd/MM/yyyy HH:mm:ss", edate+":00")==false)
		{
			model.addAttribute("erroredate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/BCMatDienForm";
		}
		if (ddhBaoMd!=null && !ddhBaoMd.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", ddhBaoMd+":00")==false)
		{
			model.addAttribute("errorddhBaoMd", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/BCMatDienForm";
		}
		if (mllSdate!=null && !mllSdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", mllSdate+":00")==false)
		{
			model.addAttribute("errormllSdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/BCMatDienForm";
		}
		if (mllEdate!=null && !mllEdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", mllEdate+":00")==false)
		{
			model.addAttribute("errormllEdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/BCMatDienForm";
		}
		if (ddhBaoMll!=null  && !ddhBaoMll.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", ddhBaoMll+":00")==false)
		{
			model.addAttribute("errorddhBaoMll", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			return "jsp/BCMatDien/BCMatDienForm";
		}
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/BCMatDien/BCMatDienForm";
		}
		else
		{
			vAlRbLossPower.setMch1800(mch1800);
			vAlRbLossPower.setAcLow(acLow);
			vAlRbLossPower.setUngCuuMpd(ungCuuMpd);
			vAlRbLossPower.setNodeTruyenDan(nodeTruyenDan);
			vAlRbLossPower.setDvtTeamProcess(dvtTeamProcess);
			vAlRbLossPower.setDvtUserProcess(userProc);
			vAlRbLossPower.setRegion(region);
			vAlRbLossPower.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate+":00"));
			if (!edate.equals("")){
				vAlRbLossPower.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate+":00"));
			}
			else
				vAlRbLossPower.setEdate(null);
			if (!mllSdate.equals(""))
			{
				vAlRbLossPower.setMllSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(mllSdate+":00"));
			}
			else
				vAlRbLossPower.setMllSdate(null);
			
			if (!mllEdate.equals(""))
			{
				vAlRbLossPower.setMllEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(mllEdate+":00"));
			}
			else
				vAlRbLossPower.setMllEdate(null);
			if (!ddhBaoMd.equals(""))
			{
				vAlRbLossPower.setDdhBaoMd((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(ddhBaoMd+":00"));
			}
			else
				vAlRbLossPower.setDdhBaoMd(null);
			if (!ddhBaoMll.equals(""))
			{
				vAlRbLossPower.setDdhBaoMll((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(ddhBaoMll+":00"));
			}
			else
				vAlRbLossPower.setDdhBaoMll(null);
			if ((!edate.equals("")&& vAlRbLossPower.getSdate().getTime()>= vAlRbLossPower.getEdate().getTime())
					||(!mllEdate.equals("")&&vAlRbLossPower.getMllSdate().getTime()>= vAlRbLossPower.getMllEdate().getTime()))
			{
				saveMessageKey(request, "cautruc.sosanhNgay");
				return "jsp/BCMatDien/BCMatDienForm";
			}
			vAlRbLossPower.setShiftId(shiftID);
			if (vAlRbLossPower.getId()==null)
			{
					vAlRbLossPowerDAO.insertSelective(vAlRbLossPower);
					saveMessageKey(request, "vAlRbLossPower.insertSucceFull");
			}
					
			else{
					vAlRbLossPowerDAO.UPDATEVAlRbLossPower(vAlRbLossPower);
					saveMessageKey(request, "vAlRbLossPower.updateSuccelfull");	
				}
				
		}
		
		model.addAttribute("acLow", "");
		model.addAttribute("mch1800", "");
		model.addAttribute("bscid", "");
		model.addAttribute("cellid", "");
		model.addAttribute("totalMD", "");
		model.addAttribute("totalMDE", "");
		model.addAttribute("dvtUserProcess", "");
		model.addAttribute("statusKTMLL", "");
		model.addAttribute("ungCuuMpd", "");
		model.addAttribute("nodeTruyenDan", "");
		model.addAttribute("region", "");
		model.addAttribute("team", "");
		return "redirect:list.htm";
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id, @RequestParam (required = true) String region,
			HttpServletRequest request, Model model) {	
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		AlShift shift = getCaTrucHT(username,region);
		if (shift!=null||userLogin.getIsRoleManager().equals("Y"))
		{
			try {
				vAlRbLossPowerDAO.deleteByPrimaryKey(Integer.parseInt(id));
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
			@RequestParam(required = true) String cellid,
			@RequestParam(required = true) String startTime,
			Model model, HttpServletRequest request) {
		
		int order = 0;
		String column = "SITE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("DETAIL");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleD", sysParemeterTitle.get(0).getValue());
		}
		List<DetailLostConfig> detailList = new ArrayList<DetailLostConfig>();
		model.addAttribute("bsc", bscId);
		model.addAttribute("cellid", cellid);
		model.addAttribute("start", startTime);
		String timer="";
		if (startTime!=null)
		{
			timer = startTime.substring(0, 10);
		
			try
			{
				detailList = vAlRbLossConfigurationDAO.getDetail(bscId,cellid,timer,order,column);
				
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

		return "jsp/BCMatDien/BCMatDienCT";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		
		return "jsp/BCMatDien/BCMatDienUpload";
	}
	// Upload File
		@RequestMapping(value = "upload", method = RequestMethod.POST)
			public String upload(@RequestParam("file") MultipartFile filePath, 
					Model model, 
					HttpServletRequest request,
					HttpServletResponse response) throws ParseException, IOException {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("UPLOAD");
			if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
				}
				
				if (!filePath.isEmpty()) {

					String[] ten = filePath.getOriginalFilename().split("\\.");
					
					String fileExtn = ten[ten.length-1];
					
					if (fileExtn.equals("xls")) {
						try
						{
							@SuppressWarnings("rawtypes")
							List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					
							if (sheetData.size() > 0) {
								// Kiem tra số cột của file
					        	List heard= (List)sheetData.get(0);
					        	if (heard.size() != 19) {
					        		saveMessageKey(request, "cautruc.loiCautruc");
					        		return "jsp/BCMatDien/BCMatDienUpload";
					        	}
					        	
					        	int maxsize=0;
				        		List footer= (List)sheetData.get(sheetData.size()-1);
				        
				        		while (footer.size() != 19 && sheetData.size() > 2)
				        		{
				        			System.out.println(footer.size());
				        			boolean kt=true;
				        			for (int k=0;k<footer.size();k++)
				        			{
				        				if (!footer.get(k).toString().trim().equals(""))
				        				{
				        					kt=false;
				        				}
				        			}
				        			if (footer.size() == 19 && kt == false)
				        			{
				        				break;
				        			}
				        			if (footer.size() > 19 && kt == false)
				        			{
				        				boolean kt2=true;
				        				for (int l=19;l<footer.size();l++)
				        					if (!footer.get(l).toString().trim().equals(""))
					        				{
					        					kt2=false;
					        					break;
					        				}
				        				if (kt2==false)
				        				{
					        				model.addAttribute("errorContent","Import không thành công. Cấu trúc dữ liệu trong file không đúng định dạng cho phép");
					        				return "jsp/BCMatDien/BCMatDienUpload";
				        				}
				        				else
				        				{
				        					break;
				        				}
				        			}
				        			else
				        			{
					        			if (kt==true)
					        			{
						        			sheetData.remove(sheetData.size()-1);
						        			maxsize=sheetData.size();
					        			}
					        			else
					        			{
					        				for (int j=footer.size();j<=18;j++)
					         				{
					        					footer.add("");
					         				}
					        			}
						        			footer= (List)sheetData.get(sheetData.size()-1);
				        			}
				        		}
				        		sheetData.remove(0);
					        	for (int i = 0; i < sheetData.size(); i++) {
					        		
					        		List list= (List)sheetData.get(i);
				     				for (int j=list.size();j<=18;j++)
				     				{
				     					list.add("");
				     				}
					        		
					        		//Bảng dữ liệu thô
					        		
					        		// Kiem tra kieu du lieu dâ thoa man chua
				     				if (list.get(1).toString().trim().length()>20||list.get(1).toString().trim().length()<1
				     						||list.get(2).toString().trim().length()>20||list.get(2).toString().trim().length()<1
				     						||list.get(3).toString().trim().length()>19||list.get(3).toString().trim().length()<1
				     						||list.get(4).toString().trim().length()>19
				     						||list.get(6).toString().trim().length()>19
				     						||list.get(7).toString().trim().length()>19
				     						||list.get(8).toString().trim().length()>19
				     						||list.get(10).toString().trim().length()>19
				     						||list.get(11).toString().trim().length()>50
				     						||list.get(12).toString().trim().length()>50
				     						||list.get(13).toString().trim().length()>1
				     						||list.get(14).toString().trim().length()>1
				     						||list.get(15).toString().trim().length()>1
				     						||list.get(16).toString().trim().length()>1
				     						||list.get(17).toString().trim().length()>255)
				     				{
				     					
				     					saveMessageKey(request, "vAlRbLossPower.loiKichThuoc");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
				     				
				     				if (list.get(5).toString().trim()!=null && !list.get(5).toString().trim().equals(""))
				     				{
				     					System.out.println(list.get(5).toString());
					     				try
					     				{
					     					double d = Double.parseDouble(list.get(5).toString().trim()); 
					  
					     				}
					     				catch(Exception exp)
					     				{
					     					saveMessageKey(request, "vAlRbLossPower.loiTGMD");
					     					model.addAttribute("errorContent","Import không thành công. Kiểm tra lại kiểu dữ liệu của cột MĐ (Phút).Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
					     				}
				     				}
				     				
				     				if (list.get(9).toString().trim()!=null && !list.get(9).toString().trim().equals(""))
				     				{
					     				try
					     				{
					     					double d = Double.parseDouble(list.get(9).toString().trim()); 
					     					
					     				}
					     				catch(Exception exp)
					     				{
					     					saveMessageKey(request, "vAlRbLossPower.loiTGMLL");
					     					model.addAttribute("errorContent","Import không thành công. Kiểm tra lại kiểu dữ liệu của cột MLL (Phút). Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
					     				}
				     				}
				     				if (list.get(3).toString().trim()==null || (list.get(3).toString().trim()!=null && (list.get(3).toString().trim().equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", list.get(3).toString().trim())==false)))
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.sdateError");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
				     				if (list.get(4).toString().trim()!=null && !list.get(4).toString().trim().equals("")   && DateTools.isValid("dd/MM/yyyy HH:mm:ss", list.get(4).toString().trim())==false)
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.edateError");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
				     				if (list.get(6).toString().trim()!=null && !list.get(6).toString().trim().equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", list.get(6).toString().trim())==false)
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.ddhBaoMdError");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
				     				if (list.get(7).toString().trim()!=null && !list.get(7).toString().trim().equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", list.get(7).toString().trim())==false)
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.mllSdateError");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
				     				if (list.get(8).toString().trim()!=null && !list.get(8).toString().trim().equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", list.get(8).toString().trim())==false)
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.mllEdateError");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
				     				if (list.get(10).toString().trim()!=null  && !list.get(10).toString().trim().equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", list.get(10).toString().trim())==false)
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.ddhBaoMllError");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}	
				     				if ((!list.get(4).toString().trim().equals("")
				     						&& (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(3).toString().trim()).getTime()>=(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(4).toString().trim()).getTime())
										||(!list.get(8).toString().trim().equals("")
												&&!list.get(7).toString().trim().equals("")
												&&(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(7).toString().trim()).getTime()>= (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(8).toString().trim()).getTime()))
									{
										saveMessageKey(request, "cautruc.sosanhNgay");
										model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
									}
				     				if (list.get(11).toString().trim()!=null  && !list.get(11).toString().trim().equals("") )
				     				{
				     					List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
				     					boolean kt= false;
				     					for (MDepartment team : teamList) {
											if (team.getDeptCode().toUpperCase().equals(list.get(11).toString().trim().toUpperCase()))
											{
												kt=true;
												break;
											}
										}
				     					if (kt== false)
				     					{
				     						saveMessageKey(request, "vAlRbLossPower.teamError");
					     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
				     					}
				     					else
				     					{
				     						if (list.get(12).toString().trim()!=null  && !list.get(12).toString().trim().equals("") )
						     				{
					     						List<SysUsers> userList = sysUsersDao.getUserByMaPhong(list.get(11).toString().trim());
					     						String[] userL = list.get(12).toString().trim().split(",");
					     						for (int j = 0; j < userL.length; j++) {
													String user = userL[j];
						     						boolean kt2= false;
							     					for (SysUsers member : userList) {
														if (member.getUsername().toUpperCase().equals(user.toUpperCase()))
														{
															kt2=true;
															break;
														}
													}
							     					if (kt2== false)
							     					{
							     						saveMessageKey(request, "vAlRbLossPower.userError");
								     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
								     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
								     					return "jsp/BCMatDien/BCMatDienUpload";
							     					}
					     						}
						     				}
				     					}
				     					
				     				}
				     				
				     				if (list.get(13).toString().trim()!=null  && !list.get(13).toString().trim().equals("") )
				     				{
				     					List<SYS_PARAMETER> ungCuuMpdList = sysParameterDao.getStatusDVTUCTT();
				     					boolean kt= false;
				     					for (SYS_PARAMETER para : ungCuuMpdList) {
											if (para.getName().toUpperCase().equals(list.get(13).toString().trim().toUpperCase()))
											{
												kt=true;
												break;
											}
										}
				     					if (kt== false)
				     					{
				     						saveMessageKey(request, "vAlRbLossPower.UCCTError");
					     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
				     					}
				     				}
				     				List<SYS_PARAMETER> paraList = sysParameterDao.getStatusNode();
				     				if (list.get(14).toString().trim()!=null  && !list.get(14).toString().trim().equals("") )
				     				{
				     		
				     					boolean kt= false;
				     					for (SYS_PARAMETER para : paraList) {
											if (para.getName().toUpperCase().equals(list.get(14).toString().trim().toUpperCase()))
											{
												kt=true;
												break;
											}
										}
				     					if (kt== false)
				     					{
				     						saveMessageKey(request, "vAlRbLossPower.NodeError");
					     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
				     					}
				     				}
				     				if (list.get(15).toString().trim()!=null  && !list.get(15).toString().trim().equals("") )
				     				{
				     					boolean kt= false;
				     					for (SYS_PARAMETER para : paraList) {
											if (para.getName().toUpperCase().equals(list.get(15).toString().trim().toUpperCase()))
											{
												kt=true;
												break;
											}
										}
				     					if (kt== false)
				     					{
				     						saveMessageKey(request, "vAlRbLossPower.ACCUError");
					     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
				     					}
				     				}
				     				if (list.get(16).toString().trim()!=null  && !list.get(16).toString().trim().equals("") )
				     				{
				     					boolean kt= false;
				     					for (SYS_PARAMETER para : paraList) {
											if (para.getName().toUpperCase().equals(list.get(16).toString().trim().toUpperCase()))
											{
												kt=true;
												break;
											}
										}
				     					if (kt== false)
				     					{
				     						saveMessageKey(request, "vAlRbLossPower.MCH1800Error");
					     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
					     					return "jsp/BCMatDien/BCMatDienUpload";
				     					}
				     				}
				     				List<VAlRbLossPower> record = new ArrayList<VAlRbLossPower>();
				     				record = vAlRbLossPowerDAO.checkExits(list.get(3).toString().trim(), list.get(1).toString().trim(), list.get(2).toString().trim(),null);
				     				if (record==null || record.size()<1)
				     				{
				     					saveMessageKey(request, "vAlRbLossPower.dontExit");
				     					model.addAttribute("errorContent","Import không thành công. Cảnh báo không tồn tại. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("alarmList", errorlist((List)sheetData.get(i)));
				     					return "jsp/BCMatDien/BCMatDienUpload";
				     				}
					    		}
					        	importXlsContent(sheetData,username, model,  request);
					        	saveMessage(request, "Import file thành công.");
							}
							else
								saveMessage(request, "Không có dữ liệu để insert");		
						}
						catch(IOException e)
						{
							saveMessage(request, "Import lỗi.");
						}
					}
					else
					{
						saveMessage(request, "Import file không thành công");
						return "jsp/BCMatDien/BCMatDienUpload";
					}
				}
				else
				{
					saveMessage(request, "Import lỗi.");
				}
				return "jsp/BCMatDien/BCMatDienUpload";
			}
			
			@SuppressWarnings({ "rawtypes" })
			private void importXlsContent(List sheetData,String username, Model model, HttpServletRequest request) throws ParseException
			{	
		        excecuteImport(sheetData,username,model, request);
		        
			}
			
			private void excecuteImport(List sheetData,String username, Model model, HttpServletRequest request) throws ParseException
			{
				AlShift shift = getCaTrucHT(username,null);
				List<VAlRbLossPower> alarmList = new ArrayList<VAlRbLossPower>();
				
				for (int i = 0; i < sheetData.size(); i++) {
					
					//VAlRbLossPower record = new VAlRbLossPower();
					List list = (List) sheetData.get(i);
					
					List<VAlRbLossPower> lossPower = new ArrayList<VAlRbLossPower>();
					lossPower = vAlRbLossPowerDAO.checkExits(list.get(3).toString().trim(), list.get(1).toString().trim(), list.get(2).toString().trim(),null);
     				for (VAlRbLossPower record : lossPower) {
     					System.out.println(record.getId());
						if (!list.get(4).toString().trim().equals("")){
							record.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(4).toString().trim()));
						}
						else
							record.setEdate(null);
						if (!list.get(6).toString().trim().equals(""))
						{
							record.setDdhBaoMd((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(6).toString().trim()));
						}
						else
							record.setDdhBaoMd(null);
						if (!list.get(7).toString().trim().equals(""))
						{
							record.setMllSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(7).toString().trim()));
						}
						else
							record.setMllSdate(null);
						
						if (!list.get(8).toString().trim().equals(""))
						{
							record.setMllEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(8).toString().trim()));
						}
						else
							record.setMllEdate(null);
						
						if (!list.get(10).toString().trim().equals(""))
						{
							record.setDdhBaoMll((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(list.get(10).toString().trim()));
						}
						else
							record.setDdhBaoMll(null);
						
						record.setSdateStrImport(list.get(3).toString().trim());
						record.setEdateStrImport(list.get(4).toString().trim());
						record.setTgMDImport(list.get(5).toString().trim());
						record.setDdMDStrImport(list.get(6).toString().trim());
						record.setMllsdateStrImport(list.get(7).toString().trim());
						record.setMlledateStrImport(list.get(8).toString().trim());
						record.setTgMLLImport(list.get(9).toString().trim());
						record.setDdhMllStrImport(list.get(10).toString().trim());
						record.setDvtTeamProcess(list.get(11).toString().trim());
						record.setDvtUserProcess(list.get(12).toString().trim());
						record.setUngCuuMpd(list.get(13).toString().trim());
						record.setNodeTruyenDan(list.get(14).toString().trim());
						record.setAcLow(list.get(15).toString().trim());
						record.setMch1800(list.get(16).toString().trim());
						record.setDescription(list.get(17).toString().trim());
						record.setRegion(list.get(18).toString().trim());
						if (shift!=null){
							record.setShiftId(shift.getShiftId());}
						
						vAlRbLossPowerDAO.UPDATEVAlRbLossPower(record);
						alarmList.add(record);
     				}
						
				}
				
				model.addAttribute("alarmList",alarmList);
			}
			//dang lam o day 
			private List<VAlRbLossPower> errorlist(List sheet)
			{
				List<VAlRbLossPower> alarmList = new ArrayList<VAlRbLossPower>();
				VAlRbLossPower record = new VAlRbLossPower();
				int size= sheet.size();
		    	for (int i=size;i<=17;i++)
		    		sheet.add("");
		    	
		    	record.setBscid(sheet.get(1).toString().trim());
				record.setCellid(sheet.get(2).toString().trim());
		    	record.setSdateStrImport(sheet.get(3).toString().trim());
				record.setEdateStrImport(sheet.get(4).toString().trim());
				record.setTgMDImport(sheet.get(5).toString().trim());
				record.setDdMDStrImport(sheet.get(6).toString().trim());
				record.setMllsdateStrImport(sheet.get(7).toString().trim());
				record.setMlledateStrImport(sheet.get(8).toString().trim());
				record.setTgMLLImport(sheet.get(9).toString().trim());
				record.setDdhMllStrImport(sheet.get(10).toString().trim());
				record.setDvtTeamProcess(sheet.get(11).toString().trim());
				record.setDvtUserProcess(sheet.get(12).toString().trim());
				record.setUngCuuMpd(sheet.get(13).toString().trim());
				record.setNodeTruyenDan(sheet.get(14).toString().trim());
				record.setAcLow(sheet.get(15).toString().trim());
				record.setMch1800(sheet.get(16).toString().trim());
				record.setDescription(sheet.get(17).toString().trim());
				alarmList.add(record);
				return alarmList;
			}
			
	@RequestMapping("compareList")
	public String compareList(
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String dept,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String siteName,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String type,
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
				if (cellid!=null)
				{
					cellid=cellid.replaceAll(" ", "");
				}
				else
				{
					cellid="";
				}
				if (bscid!=null)
				{
					bscid=bscid.replaceAll(" ", "");
				}
				else
				{
					bscid="";
				}
				
				if (district!=null)
				{
					district=district.trim();
				}
				else
				{
					district="";
				}
				if (province!=null)
				{
					province=province.trim();
				}
				else
				{
					province="";
				}
				if (teamProcess==null)
				{
					teamProcess="";
				}
				else
				{
					teamProcess = teamProcess.trim();
				}
				if (dept==null)
				{
					dept="";
				}
				else
					dept = dept.trim();
				if (siteName==null)
				{
					siteName="";
				}
				else
					siteName = siteName.trim();
				
				Calendar cal = Calendar.getInstance();	 
				cal.setTime(new Date(cal.getTimeInMillis() - 24 * 60 * 60 * 1000));
				if (endTime == null || endTime.equals("")||(endTime!=null && !endTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endTime+" 00:00:00")==false))
				{	
					  
					endTime = df_full.format(cal.getTime());
				}
				if (startTime == null || startTime.equals("")||(startTime!=null && !startTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startTime+" 00:00:00")==false))
				{
					
					startTime = endTime;
				}
				
				List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("COMPARE_LIST");
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("title", sysParemeterTitle.get(0).getValue());
				}
				
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
				model.addAttribute("cellid", cellid);
				model.addAttribute("teamProcess", teamProcess);
				model.addAttribute("siteName", siteName);
				model.addAttribute("province", province);
				model.addAttribute("district", district);
				model.addAttribute("bscid", bscid);
				model.addAttribute("dept", dept);
				//Lay danh sach district theo user
				List<String> districtList = sysUserAreaDAO.getDistrictList(province,teamProcess,null);
				String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
				String cn="";
				for (String dis : districtList) {
					districtArray = districtArray + cn +"\""+dis+"\"";
					cn=",";
				}
				districtArray = districtArray+");";
				model.addAttribute("districtList", districtArray);
				//Lay danh sach team theo user
				List<MDepartment> teamList = mDepartmentDao.loadTeamByDepartment(dept);
				String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (MDepartment dis : teamList) {
					teamArray = teamArray + cn +"\""+dis.getDeptValue()+"\"";
					cn=",";
				}
				teamArray = teamArray+");";
				model.addAttribute("teamList", teamArray);
				//Lay danh sach province theo user
				List<String> provinceList = sysUserAreaDAO.getProvinceList(null);
				String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : provinceList) {
					provinceArray = provinceArray + cn +"\""+dis+"\"";
					cn=",";
				}
				provinceArray = provinceArray+");";
				model.addAttribute("provinceList", provinceArray);
				//Lay danh sach BSC theo user
				List<String> bscList = sysUserEquipmentNameDAO.getBSCList(null,null,null);
				String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
				cn="";
				for (String bsc : bscList) {
					bscArray = bscArray + cn +"\""+bsc+"\"";
					cn=",";
				}
				bscArray = bscArray+");";
				model.addAttribute("bscList", bscArray);  
				//Lay danh sach Cell theo user
				//List<String> cellList = sysUserEquipmentNameDAO.getCellList(null,bscid,district,username);
				List<String> cellList = null;
				String cellArray="var cellList = new Array(";
				cn="";
				/*for (String cell : cellList) {
					cellArray = cellArray + cn +"\""+cell+"\"";
					cn=",";
				}*/
				cellArray = cellArray+");";
				model.addAttribute("cellList", cellArray);   
					
					//do du lieu ra grid
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("COMPARE_LOSS_POWER");
				//Lay du lieu cac cot an hien cua grid
				List<CTableConfig> listSource = cTableConfigDAO.getColumnList("COMPARE_LOSS_POWER");
				if (type !=null && type.equals("all"))
				{
					//Grid du lieu mat dien  khop
					String url = "dataCompare.htm?startTime="+startTime+"&endTime="+endTime+"&cellid="+cellid+"&teamProcess="+teamProcess+
							"&siteName="+siteName+"&province="+province+"&district="+district+"&bscid="+bscid+"&dept="+dept+"&type=Y";
					String jqxgrid = HelpTableConfigs.getGridPagingUrl(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, "multiplerowsextended", null);
					model.addAttribute("jqxgrid", jqxgrid);
						
					//Grid du lieu mat dien khong khop
					String urlUnCompare = "dataCompare.htm?startTime="+startTime+"&endTime="+endTime+"&cellid="+cellid+"&teamProcess="+teamProcess+
							"&siteName="+siteName+"&province="+province+"&district="+district+"&bscid="+bscid+"&dept="+dept+"&type=N";
					String jqxgridUnCompare  = HelpTableConfigs.getGridPagingUrl(configList, "jqxgridUnCompare", urlUnCompare, "jqxlistboxUnCompare", listSource, "MenuUnCompare", null, "multiplerowsextended","Y");
					model.addAttribute("jqxgridUnCompare", jqxgridUnCompare);
					
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "DoiSoatMayNoKhop_" + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					String exportFileName2 = "DoiSoatMayNoKhongKhop_" + dateNow;
					model.addAttribute("exportFileName2", exportFileName2);
				}
				else
				{
					//Grid du lieu mat dien  khop
					String url = "dataCompare.htm?startTime="+startTime+"&endTime="+endTime+"&cellid="+cellid+"&teamProcess="+teamProcess+
							"&siteName="+siteName+"&province="+province+"&district="+district+"&bscid="+bscid+"&dept="+dept+"&type="+type;
					String jqxgrid = HelpTableConfigs.getGridPagingUrl(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, "multiplerowsextended", null);
					model.addAttribute("jqxgrid", jqxgrid);
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "DoiSoatMayNo_" + dateNow;
					model.addAttribute("exportFileName", exportFileName);
				}
				model.addAttribute("type", type);
				return "jspalarm/compareLossPower/compareList";
			}
	
			@RequestMapping("dataCompare")
			public void dataCompare(@RequestParam(required = false) String startTime,
					@RequestParam(required = false) String endTime,
					@RequestParam(required = false) String cellid,
					@RequestParam(required = false) String teamProcess,
					@RequestParam(required = false) String siteName,
					@RequestParam(required = false) String province,
					@RequestParam(required = false) String district,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String dept,
					@RequestParam(required = false) String type,
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				List<VAlRbLossPower> powerList = new ArrayList<VAlRbLossPower>();
				powerList = vAlRbLossPowerDAO.getCompareLossPowerMLMNList(startTime,endTime,cellid,teamProcess,siteName,province,district,bscid,dept,type);
				Gson gs = new Gson();
				String jsonCartList = gs.toJson(powerList);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.println(jsonCartList);
				out.close();
				
			 }
			
			//Xoa
			@RequestMapping(value = "deleteCompare", method = RequestMethod.GET)
			public String compareDelete(@RequestParam (required = true) String idList,@RequestParam (required = true) String idLogList,@RequestParam (required = true) String type,
					HttpServletRequest request, Model model) {
				try {
					vAlRbLossPowerDAO.deleteAlarmLossPowerCompare(idList,idLogList);
					saveMessageKey(request, "messsage.confirm.deletesuccess");
				}
				catch (Exception e) {
					saveMessageKey(request, "messsage.confirm.deletefailure");
				}
				return "redirect:compareList.htm?type="+type;
			}
			//Them, sua
			@RequestMapping(value = "compareForm", method = RequestMethod.GET)
			public String compareForm(@RequestParam (required = true) String id,
					@RequestParam (required = true) String idLog,
					@RequestParam (required = true) String bscid,
					@RequestParam (required = true) String cellid,
					@RequestParam (required = true) String sdate,
					@RequestParam (required = true) String shiftSdate,
					@RequestParam(required = false) String type,
					HttpServletRequest request, Model model) {
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("COMPARE_FORM");
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
				}
				Integer recordID =null;
				VAlRbLossPower record = new VAlRbLossPower();
				
				if (id!=null&&!id.equals(""))
				{
					 recordID = Integer.parseInt(id);	
				}
				System.out.println("sdate:"+sdate);
				System.out.println("shiftSdate:"+shiftSdate);
				record = vAlRbLossPowerDAO.selectCompareByID(recordID,idLog,bscid,cellid,sdate,shiftSdate);
				if (record.getId()!=null||record.getIdLog()!=null)
				{
					model.addAttribute("sdate", record.getSdateStr());		
					model.addAttribute("edate", record.getEdateStr());
					model.addAttribute("shiftSdate", record.getShiftSdateStr());		
					model.addAttribute("shiftEdate", record.getShiftEdateStr());		
				}
				model.addAttribute("record", record);	
				model.addAttribute("type", type);	
				return "jspalarm/compareLossPower/compareForm";
			}
			
			@RequestMapping(value = "compareForm", method = RequestMethod.POST)
			public String compareFormSubmit(@ModelAttribute("record") @Valid VAlRbLossPower record, BindingResult result,
					@RequestParam(required = false) String shiftSdate,
					@RequestParam(required = false) String shiftEdate,
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String type,
					ModelMap model, HttpServletRequest request) throws ParseException {
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				model.addAttribute("record", record);	
			
				model.addAttribute("sdate", sdate);		
				model.addAttribute("edate", edate);
				List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("COMPARE_FORM");
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
				}
				AlShift shift = getCaTrucHT(username,null);
				boolean checkCaTruc= false;
				int shiftID = 1;
				if (shift!=null)
				{
					checkCaTruc=true;
					model.addAttribute("checkCaTruc", checkCaTruc);
					shiftID = shift.getShiftId();
				}
				else
				{
					checkCaTruc=true;
				}
				if (checkCaTruc==false)
				{
					saveMessageKey(request, "cautruc.KhongCoQuyen");
					model.addAttribute("shiftSdate", shiftSdate);		
					model.addAttribute("shiftEdate", shiftEdate);	
					model.addAttribute("type", type);	
					return "jspalarm/compareLossPower/compareForm";
				}
				if (shiftSdate!=null  && !shiftSdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", shiftSdate+":00")==false)
				{
					model.addAttribute("errorshiftSdate", "*");
					saveMessageKey(request, "alarmExtend.errorField");
					model.addAttribute("shiftSdate", shiftSdate);		
					model.addAttribute("shiftEdate", shiftEdate);
					model.addAttribute("type", type);	
					return "jspalarm/compareLossPower/compareForm";
				}
				if (shiftEdate!=null  && !shiftEdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", shiftEdate+":00")==false)
				{
					model.addAttribute("errorshiftEdate", "*");
					saveMessageKey(request, "alarmExtend.errorField");
					model.addAttribute("shiftSdate", shiftSdate);		
					model.addAttribute("shiftEdate", shiftEdate);
					model.addAttribute("type", type);	
					return "jspalarm/compareLossPower/compareForm";
				}
				System.out.println("eror:"+result.getObjectName());
				if (result.hasErrors() ) {
					model.addAttribute("shiftSdate", shiftSdate);		
					model.addAttribute("shiftEdate", shiftEdate);
					model.addAttribute("type", type);	
					saveMessageKey(request, "define.errorField");
					 return "jspalarm/compareLossPower/compareForm";
				}
				else
				{
					if (shiftSdate!=null && !shiftSdate.equals(""))
					{
						record.setShiftSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(shiftSdate+":00"));
					}
					if (shiftEdate!=null && !shiftEdate.equals(""))
					{
						record.setShiftEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(shiftEdate+":00"));
					}
					if (sdate!=null&&!sdate.equals(""))
					{
						record.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate));
					}
					if (edate!=null&&!edate.equals(""))
					{
						record.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate));
					}
					record.setShiftUser(username);
					/*try
					{*/
					
						record.setShiftId(shiftID);
						vAlRbLossPowerDAO.updateCompareLossPower(record,"E");
						saveMessageKey(request, "define.updateSuccelfull");	
						
					/*}
					catch(Exception exp)
					{
						saveMessageKey(request, "cautruc.loiKichThuoc");
					}*/
				}
				return "redirect:compareList.htm?type="+type;
			}
	
		@RequestMapping("exportResource")
	  	public ModelAndView exportResource(
	  			@RequestParam(required = false) String startTime,
				@RequestParam(required = false) String endTime,
				@RequestParam(required = false) String cellid,
				@RequestParam(required = false) String teamProcess,
				@RequestParam(required = false) String siteName,
				@RequestParam(required = false) String province,
				@RequestParam(required = false) String district,
				@RequestParam(required = false) String bscid,
				   @RequestParam(required = false) String sortfield,
				   @RequestParam(required = false) String sortorder,
				   @RequestParam(required = false) String strWhere,
				   @RequestParam(required = false) String dept,
	  			HttpServletRequest request, HttpServletResponse response) throws Exception {
	  		strWhere = strWhere.replace("___", "%");
	  		if (cellid!=null)
			{
				cellid=cellid.replaceAll(" ", "");
			}
			else
			{
				cellid="";
			}
			if (bscid!=null)
			{
				bscid=bscid.replaceAll(" ", "");
			}
			else
			{
				bscid="";
			}
			
			if (district!=null)
			{
				district=district.trim();
			}
			else
			{
				district="";
			}
			if (province!=null)
			{
				province=province.trim();
			}
			else
			{
				province="";
			}
			if (teamProcess==null)
			{
				teamProcess="";
			}
			else
				teamProcess = teamProcess.trim();
			if (dept==null)
			{
				dept="";
			}
			else
				dept = dept.trim();
			if (siteName==null)
			{
				siteName="";
			}
			else
				siteName = siteName.trim();
			
			if (sortorder==null||sortorder.equals("null"))
			{
				sortorder="";
			}
			else
				sortorder = sortorder.trim();
			
			if (sortfield==null)
			{
				sortfield="";
			}
			else
				sortfield = sortfield.trim();
			
			if (strWhere==null)
			{
				strWhere="";
			}
			else
				strWhere = strWhere.trim();
	  		
	  		// temp file
			String basePath = request.getSession().getServletContext().getRealPath("");
			String dataDir = basePath + "/upload";
			String tempName = UUID.randomUUID().toString();
			String ext = "xls";
			String outfile = tempName + "." + ext;
			File tempFile = new File(dataDir + "/" + outfile);
			
			List<VAlRbLossPower> dataList = vAlRbLossPowerDAO.getCompareLossPowerList(startTime,endTime,cellid,teamProcess,siteName,province,district,bscid,strWhere, null,null,sortfield,sortorder,dept);
			exportReport(tempFile, dataList);
			
			// return
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String filename = "DoiSoatMatDien_"+ dateNow;
			response.setContentType("application/ms-excel");
			response.setContentLength((int) tempFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

			
			FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

			tempFile.delete();
	  		return null;
	  	}
	  
		private void exportReport(File tempFile, List<VAlRbLossPower> isoEquipmentList) {
			try {
				WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
				WritableSheet sheet = workbook.createSheet("Equipment Report", 0);
				
				WritableCellFormat cellFormatHeader = new WritableCellFormat();
				cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
				WritableCellFormat cellFormat = new WritableCellFormat();
				cellFormat = ExportTools.getCellFormat(cellFormat);
			    
				int i=0;
				Label label0 = new Label(i, 0, "NE", cellFormatHeader);
				sheet.addCell(label0);  
				i++;
				Label label1 = new Label(i, 0, "Mã trạm", cellFormatHeader);
				sheet.addCell(label1);
				i++;
				Label label2 = new Label(i, 0, "Tên trạm", cellFormatHeader);
				sheet.addCell(label2);
				i++;
				Label label3 = new Label(i, 0, "Tỉnh/thành", cellFormatHeader);
				sheet.addCell(label3);
				i++;
				Label label4 = new Label(i, 0, "Quận/huyện", cellFormatHeader);
				sheet.addCell(label4);
				i++;
				Label label5 = new Label(i, 0, "Phòng ban", cellFormatHeader);
				sheet.addCell(label5);
				i++;
				Label label6 = new Label(i, 0, "Tổ", cellFormatHeader);
				sheet.addCell(label6);
				i++;
				Label label7 = new Label(i, 0, "Nhóm", cellFormatHeader);
				sheet.addCell(label7);
				i++;
				Label label8 = new Label(i, 0, "TGBĐ Logfile", cellFormatHeader);
				sheet.addCell(label8);
				i++;
				Label label9 = new Label(i, 0, "TGKT Logfile", cellFormatHeader);
				sheet.addCell(label9);
				i++;
				Label label17 = new Label(i, 0, "Số phút MĐ (Logfile)", cellFormatHeader);
				sheet.addCell(label17);
				i++;
				Label label12 = new Label(i	, 0, "TGBĐ Vận hành", cellFormatHeader);
				sheet.addCell(label12);
				i++;
				Label label13 = new Label(i, 0, "TGKT Vận hành", cellFormatHeader);
				sheet.addCell(label13);
				i++;
				Label label14 = new Label(i, 0, "Tổng TG Vận hành", cellFormatHeader);
				sheet.addCell(label14);
				i++;
				Label label19 = new Label(i, 0, "Độ lệch", cellFormatHeader);
				sheet.addCell(label19);
				i++;
				Label label20 = new Label(i, 0, "Đánh giá", cellFormatHeader);
				sheet.addCell(label20);
				i++;
				Label label15 = new Label(i, 0, "Ghi chú", cellFormatHeader);
				sheet.addCell(label15);
				i++;
				Label label16 = new Label(i, 0, "Người trực", cellFormatHeader);
				sheet.addCell(label16);
				i = 1;
				
				for (VAlRbLossPower record : isoEquipmentList) {
					int j=0;
					Label record0 = new Label(j, i, record.getBscid(), cellFormat);
					sheet.addCell(record0);
					j++;
					Label record1 = new Label(j, i,record.getCellid(), cellFormat);
					sheet.addCell(record1);
					j++;
					Label record2 = new Label(j, i, record.getSiteName(), cellFormat);
					sheet.addCell(record2);
					j++;
					Label record3 = new Label(j, i, record.getProvince(), cellFormat);
					sheet.addCell(record3);
					j++;
					Label record4 = new Label(j, i, record.getDistrict(), cellFormat);
					sheet.addCell(record4);
					j++;
					Label record5 = new Label(j, i, record.getDept(), cellFormat);
					sheet.addCell(record5);
					j++;
					Label record6 = new Label(j, i, record.getTeam(), cellFormat);
					sheet.addCell(record6);
					j++;
					Label record7 = new Label(j, i, record.getGroups(), cellFormat);
					sheet.addCell(record7);
					j++;
					Label record8 = new Label(j, i, record.getSdateStr(), cellFormat);
					sheet.addCell(record8);
					j++;
					Label record9 = new Label(j, i, record.getEdateStr(), cellFormat);
					sheet.addCell(record9);
					j++;
					jxl.write.Number record18 = new jxl.write.Number(j, i, record.getTotalData()==null?0:record.getTotalData(), cellFormat);
					sheet.addCell(record18);
					j++;
					
					Label record10 = new Label(j, i, record.getShiftSdateStr(), cellFormat);
					sheet.addCell(record10);
					j++;
					Label record11 = new Label(j, i, record.getShiftEdateStr(), cellFormat);
					sheet.addCell(record11);
					j++;
					jxl.write.Number record17 = new jxl.write.Number(j, i, record.getTotalLog()==null?0:record.getTotalLog(), cellFormat);
					sheet.addCell(record17);
					j++;
					jxl.write.Number record19 = new jxl.write.Number(j, i, record.getTotalCompare()==null?0:record.getTotalCompare(), cellFormat);
					sheet.addCell(record19);
					j++;
					Label record20= new Label(j, i, record.getAssess(), cellFormat);
					sheet.addCell(record20);
					j++;
					Label record15 = new Label(j, i, record.getDescription(), cellFormat);
					sheet.addCell(record15);
					j++;
					Label record16 = new Label(j, i, record.getShiftUser(), cellFormat);
					sheet.addCell(record16);
					j++;
					i++;
				}
				
				workbook.write();
				workbook.close();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		@RequestMapping(value = "compareUpload", method = RequestMethod.GET)
		public String showUploadForm(@RequestParam(required = false) String type,Model model) {
			
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("COMPARE_UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
			}
			model.addAttribute("type", type);
			return "jspalarm/compareLossPower/compareUpload";
		}
		
		@RequestMapping(value = "compareUpload", method = RequestMethod.POST)
		public String upload(@RequestParam("filePath") MultipartFile filePath,
				@RequestParam(required = false) String type,
				Model model, HttpServletRequest request) throws IOException, ParseException {
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("COMPARE_UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
			}
			if (!filePath.isEmpty()) {

				String[] ten=filePath.getOriginalFilename().split("\\.");
				String fileExtn = ten[ten.length-1];
				
				if (fileExtn.equals("xls")) {
					/*if (fileExtn.equals("xlsx")) {
						@SuppressWarnings("rawtypes")
						List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
						importContent(sheetData,model,request);
					}*/
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile4(filePath.getInputStream());
					importContent(sheetData,model,request);
					
				}
				else {
					 saveMessageKey(request, "cautruc.typeFIle");
				}
			}
			else {
				saveMessageKey(request, "cautruc.emptyFile");
			}
			model.addAttribute("type", type);
			return "jspalarm/compareLossPower/compareUpload";
		}
		
		private String importContent(List sheetData,
				Model model, HttpServletRequest request) throws ParseException {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossPowerDAO.titleForm("COMPARE_UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
			}
			List<VAlRbLossPower> successList = new ArrayList<VAlRbLossPower>();
			List<VAlRbLossPower> failedList = new ArrayList<VAlRbLossPower>();
			List<VAlRbLossPower> success = new ArrayList<VAlRbLossPower>();
			
			String bscid;
			String cellid;
			String siteName;
			String province;
			String district;
			String dept;
			String team;
			String groups;
			String sdateStr;
			String edateStr;
			String shiftSdateStr;
			String shiftEdateStr;
			String compareSdateStr;
			String compareEdateStr;
			String compareDuaration;
			String description;
			String shiftUser;
			int successSize=0;
			
			if (sheetData.size() > 0) {
				List heard= (List)sheetData.get(0);
				
	        	/*if (heard.size() != 17) {
	        		saveMessageKey(request, "Định dạng tệp không đúng");
	        		
	        		return "jspalarm/compareLossPower/compareUpload";
	        	}*/
	        	
	        	if (sheetData.size() > 1) {
	        		VAlRbLossPower recordLossPW;
	        		
	        		for (int i=1; i<sheetData.size(); i++) {
	        			boolean error = false;
	        			
	        			recordLossPW = new VAlRbLossPower();
	        			
	        			List data= (List) sheetData.get(i);
	        			
	        			for (int j=data.size(); j<=18; j++) {
	     					data.add("");
	     				}
	        			bscid=data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
    					cellid=data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
    					siteName=data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
    					province=data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
    					district=data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
    					dept=data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
    					team=data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
    					groups=data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
    					sdateStr=data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
    					edateStr=data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
    					String totalLog=data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
    					compareSdateStr =data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
    					compareEdateStr=data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
    					compareDuaration=data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
    					String totalCompare =data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
    					String assess =data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
    					description=data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
    					shiftUser=data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
    					
	        
	        			recordLossPW.setBscid(bscid);
	        			recordLossPW.setSiteName(siteName);
	        			recordLossPW.setCellid(cellid);
	        			recordLossPW.setProvince(province);
	        			recordLossPW.setDistrict(district);
	        			recordLossPW.setDept(dept);
	        			recordLossPW.setTeam(team);
	        			recordLossPW.setGroups(groups);
	        			recordLossPW.setDescription(description);
	        			recordLossPW.setShiftUser(username);
	        			recordLossPW.setSdateI(sdateStr);
	        			recordLossPW.setEdateI(edateStr);
	        			recordLossPW.setShiftSdateI(compareSdateStr);
	        			recordLossPW.setShiftEdateI(compareEdateStr);
	        			recordLossPW.setAssess(assess);
	        			
	        			if (sdateStr!=null  && !sdateStr.equals("")  )
	    				{
	        				if (sdateStr.length()==16)
	        				{
	        					sdateStr=sdateStr+":00";
	        				}
	        				if (DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdateStr)==false)
	        				{
	        					error = true;
	        				}
	        				else
	        				{
	        					recordLossPW.setSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdateStr));
	        				}
	    				}if (edateStr!=null  && !edateStr.equals("")  )
	    				{
	    					if (edateStr.length()==16)
	        				{
	    						edateStr=edateStr+":00";
	        				}
	        				if (DateTools.isValid("dd/MM/yyyy HH:mm:ss", edateStr)==false)
	        				{
	        					error = true;
	        				}
	        				else
	        				{
	        					recordLossPW.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edateStr));
	        				}
	    				}
	        			if (compareSdateStr!=null  && !compareSdateStr.equals("")  )
	    				{
	        				if (compareSdateStr.length()==16)
	        				{
	        					compareSdateStr=compareSdateStr+":00";
	        				}
	        				if (DateTools.isValid("dd/MM/yyyy HH:mm:ss", compareSdateStr)==false)
	        				{
	        					error = true;
	        				}
	        				else
	        				{
	        					recordLossPW.setShiftSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(compareSdateStr));
	        				}
	    				}
	        			if (compareEdateStr!=null  && !compareEdateStr.equals(""))
	    				{
	        				if (compareEdateStr.length()==16)
	        				{
	        					compareEdateStr=compareEdateStr+":00";
	        				}
	    					if (DateTools.isValid("dd/MM/yyyy HH:mm:ss", compareEdateStr)==false)
	        				{
	        					error = true;
	        				}
	        				else
	        				{
	        					recordLossPW.setShiftEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(compareEdateStr));
	        				}
	    				}
	        			
	        			
	        			if (cellid == null) {
	        				// nothing
	        				error = true;
	        			} 
        				if (error) {
    						failedList.add(recordLossPW);
    					} else  {
    						//successList.add(recordLossPW);
    						try
    						{
    							vAlRbLossPowerDAO.insertRAlarmLossPowerTemp(recordLossPW);
    							successSize++;
    							success.add(recordLossPW);
    						}
    						catch(Exception exp)
    						{
    							failedList.add(recordLossPW);
    						}
    					}
	        			
	        		}
	        	}
			}
			
			/*System.out.println("successList.size(): " + successList.size());
			int successSize=successList.size();
			for (VAlRbLossPower cab: successList) {
				
				try {
					String sdateSt= null;
					String shiftSdateSt=null;
					if (cab.getSdateStr()!=null&&!cab.getSdateStr().equalsIgnoreCase("null"))
					{
						sdateSt= cab.getSdateStr();
					}
					if (cab.getShiftSdateStr()!=null&&!cab.getShiftSdateStr().equalsIgnoreCase("null"))
					{
						shiftSdateSt= cab.getShiftSdateStr();
					}
					VAlRbLossPower cable = vAlRbLossPowerDAO.selectCompareByID(null,null, cab.getBscid(), cab.getCellid(),sdateSt,shiftSdateSt);
					
					if (cable!=null&&(cable.getIdLog()!=null||cable.getId()!=null)) { 
						System.out.println("update:"+cab.getBscid());		
	    				cab.setId(cable.getId());
	    				cab.setIdLog(cable.getIdLog());
	    				cab.setAlarmMappingId(cable.getAlarmMappingId());
	    				cab.setAlarmMappingName(cable.getAlarmMappingName());
	    				cab.setSiteName(cable.getSiteName());
	    				cab.setProvince(cable.getProvince());
	    				cab.setDistrict(cable.getDistrict());
	    				cab.setDept(cable.getDept());
	    				cab.setTeam(cable.getTeam());
	    				cab.setGroups(cable.getGroups());
	    				vAlRbLossPowerDAO.updateCompareLossPower(cab,"E");
					} 
	    			else
	    			{
	    				System.out.println("insert :"+cab.getBscid());		
	    				vAlRbLossPowerDAO.updateCompareLossPower(cab,"I");
	    			}
					success.add(cab);
				} catch (Exception ex) {
					failedList.add(cab);
					successSize= successSize-1;
				}
			}*/
			
			if (failedList.size() == 0 && successSize > 0) {
				model.addAttribute("status", "Upload thành công.");				// Upload thành công
			} else if (failedList.size() == 0 && successSize == 0) {
				model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
			} else {
				model.addAttribute("status", "Dữ liệu không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
			}
			
			model.addAttribute("failNum", failedList.size());
			model.addAttribute("successNum", successSize);
			model.addAttribute("totalNum", failedList.size()+successSize);
			model.addAttribute("successList", success);
			model.addAttribute("failedList", failedList);
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "DSMatdien_" + dateNow;
					
			model.addAttribute("exportFileName", exportFileName);
					
			
			return "jspalarm/compareLossPower/compareUpload";
			
		}
		
}
