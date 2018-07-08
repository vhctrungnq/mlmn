 package controller.feedback;

 import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.CHighlightConfigs;
import vo.CSystemConfigs;
import vo.CTableConfig;
import vo.FbProcess;
import vo.FbProcessFilter;
import vo.FbType;
import vo.FbVip;
import vo.FeedbackJob;
import vo.HProvinceFb;
import vo.HWards;
import vo.MDepartment;
import vo.M_FILE_ATTACHMENT;
import vo.SYS_PARAMETER;
import vo.SmsContents;
import vo.SysMailParameterMaster;
import vo.SysUserArea;
import vo.SysUsers;
import vo.VHBscProvince;
import vo.W_WORKING_MANAGEMENTS;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.Helper;
import vo.alarm.utils.Mail;
import vo.alarm.utils.UAgentInfo;
import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CSystemConfigsDAO;
import dao.CTableConfigDAO;
import dao.FbDeptPlacesDAO;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.FbVipDAO;
import dao.HProvinceFbDAO;
import dao.HWardsDAO;
import dao.SYS_PARAMETERDAO;
import dao.SmsContentsDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
import dao.VHBscProvinceDAO;

/**
 * Function        : Quan ly thong tin phan anh dung cho trung tam 6
 * Created By      : BUIQUANG
 * Create Date     : 15/1/2014
 * Modified By     : 
 * Modify Date     :
 * @author BUIQUANG
 * Description     :
 */
 @Controller
 @RequestMapping("/feedback/general-feedback/*")
 public class GeneralFeedbackController extends BaseController{

	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
 	@Autowired
 	private FbProcessDAO fbProcessDAO;
 	@Autowired
 	private FbDeptPlacesDAO FbDeptPlacesDAO;
 	@Autowired
 	private FbTypeDAO FbTypeDAO;
 	@Autowired
 	private SysUsersDAO sysUsersDAO;
 	@Autowired
 	private VHBscProvinceDAO vHBscProvinceDAO;
 	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
 	@Autowired
 	private HWardsDAO hWardsDAO;
 	@Autowired
 	private FbVipDAO fbVipDao;
 	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
 	@Autowired 
	private SysUsersDAO  sysUserDao;
 	@Autowired 
	private SysUserAreaDAO sysUserAreaDao;;
	@Autowired
	private SmsContentsDAO smsContentsDAO;
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;

	
 	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
 	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
 	
 	/*@RequestMapping(value = "{function}")
 	public ModelAndView list(@RequestParam(required = false) String thoiGianPAFrom,
 							 @RequestParam(required = false) String thoiGianPATo,
 							 @RequestParam(required = false) String thoiGianXLFrom,
 							 @RequestParam(required = false) String thoiGianXLTo,
 							 @RequestParam(required = false) String checkColumn,
 							@RequestParam(required = false) String tableWidth,
 							 @PathVariable String function, 
 							 @ModelAttribute("filter") FbProcess filter, ModelMap model, HttpServletRequest request) {
 		
 		// Ngay thang
		if (thoiGianPAFrom == null || thoiGianPAFrom.equals("") || DateTools.isValid("dd/MM/yyyy", thoiGianPAFrom)==false) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			
			thoiGianPAFrom = df.format(cal.getTime());
		
		}
		if (thoiGianPATo == null || thoiGianPATo.equals("") || DateTools.isValid("dd/MM/yyyy", thoiGianPATo)==false)
		{
			thoiGianPATo = df.format(new Date());
		}
		
		if (thoiGianXLFrom != null && thoiGianXLFrom.equals("")) {
			thoiGianXLFrom = null;
		}
		
		if (thoiGianXLTo != null && thoiGianXLTo.equals("")) {
			thoiGianXLTo = null;
		}
		if (thoiGianXLFrom != null && !DateTools.isValid("dd/MM/yyyy", thoiGianXLFrom)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -8);
			
			thoiGianXLFrom = df.format(cal.getTime());
		}
		
		if (thoiGianXLTo != null && !DateTools.isValid("dd/MM/yyyy", thoiGianXLTo)) {
			thoiGianXLTo = df.format(new Date());
		}
		
		if(filter.getSubscribers() != null)
			filter.setSubscribers(filter.getSubscribers().trim());
		if(filter.getDistrict() != null && filter.getDistrict() != "")
		{
			String[] pDList = filter.getDistrict().split("//");
			filter.setDistrict(pDList[1]);
			filter.setProvince(pDList[0]);
		}
		model.addAttribute("checkColumn", checkColumn);
		model.addAttribute("tableWidth", tableWidth);
 		// Lay ten file export
 		Calendar currentDate = Calendar.getInstance();	

 		SimpleDateFormat now= new SimpleDateFormat("dd/MM/yyyy");
 		String dateNow1 = now.format(currentDate.getTime());
 		model.addAttribute("ngayHienTai", dateNow1);		

 		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
 		String dateNow = formatter.format(currentDate.getTime());
 		String exportFileName = "DSFeedback_" + dateNow;
 		model.addAttribute("exportFileName", exportFileName);
 		
 		
 		int order = 1;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
 		
 		List<SYS_PARAMETER> getListTTPACount = sysParameterDao.getListTTPACount();
 		model.addAttribute("getListTTPACount", getListTTPACount);

 		List<SYS_PARAMETER> sysParameterByCodeList = fbProcessDAO.getQLTTFBList();//sysParameterDao.getSysParameterByCode("STATUS_OF_CENTRAL_FEEDBACK");
 		model.addAttribute("QLTTFBList", sysParameterByCodeList);		

 		List<SYS_PARAMETER> loaiTBList = fbProcessDAO.getTenLoaiTB();//sysParameterDao.getSysParameterByCode("TEN_LOAI_TB");
 		model.addAttribute("loaiTBList", loaiTBList);	

 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();//sysParameterDao.getSysParameterByCode("TEN_LOAI_MANG");
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		// Phong/Dai
 		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("deptProcessList", deptProcessList);
		
 		// To VT
 		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(filter.getDeptProcess());
 		model.addAttribute("teamList", teamList);
 		
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
 		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.getProvinceFbByDeptCode(filter.getDeptProcess());
 		model.addAttribute("quanHuyenList", quanHuyenList);
 		
 		List<HWards> phuongXaList = hWardsDAO.selectWards( filter.getProvince(), filter.getDistrict());
 		model.addAttribute("phuongXaList", phuongXaList);
 		
 		List<FbVip> fbVipList = fbVipDao.getFbVipList();
 		model.addAttribute("fbVipList", fbVipList);
 		
 		if(filter.getStatus() == null){
 			
 			if(function.equals("list"))
 			{
 				String username = SecurityContextHolder.getContext().getAuthentication().getName();
 				List<SysUsers> userList = sysUserDao.checkRole(username);
 				if(Helper.checkRole2(userList, model).equals("Y"))
 					filter.setStatus(sysParameterByCodeList.get(0).getValue());
 				else
 					filter.setStatus("");
	 			model.addAttribute("trangThaiCBB", filter.getStatus());
	 			model.addAttribute("urlForm", sysParameterByCodeList.get(0).getRemark());
 			}
 			else
 			{
 				for(int i=0;i<sysParameterByCodeList.size();i++)
 				{
 					if(function.equals(sysParameterByCodeList.get(i).getRemark())){
	 					filter.setStatus(sysParameterByCodeList.get(i).getValue());
	 		 			model.addAttribute("trangThaiCBB", filter.getStatus());
	 		 			model.addAttribute("urlForm", sysParameterByCodeList.get(i).getRemark());
	 		 			break;
 					}
 				}
 			}
 		}
 		else
 		{
 			for(int i=0;i<sysParameterByCodeList.size();i++)
			{
 				if(filter.getStatus().equals(sysParameterByCodeList.get(i).getValue())){
 					model.addAttribute("urlForm", sysParameterByCodeList.get(i).getRemark());
 					break;
 				}
			}
 			
 			model.addAttribute("trangThaiCBB", filter.getStatus());
 		}
 		// Phan quyen FeedBack 
 		// User thuong: load fb theo tinh va nhung fb da duoc check gui dai vien thong
 		// User Quan tri: load tat ca
 			String username = SecurityContextHolder.getContext().getAuthentication().getName();
 			List<SysUsers> userList = sysUserDao.checkRole(username);
 			String district = "";
 			// Load status gui dai vt 
 			List<SYS_PARAMETER> statusList = sysParameterDao.getSysParameterByCode("FEEDBACK_COMBOBOX_SEND_TELECOM" );
 			
 			if(Helper.checkRole2 (userList, model).equals("N"))
 			{
 		 
 				if(filter.getDistrict()==null || filter.getDistrict().equals(""))
 				{
 					List<SysUserArea> districtList = sysUserAreaDao.getAreaByUsername(username);
 					for(int i=0; i< districtList.size();i++)
 						district += districtList.get(i).getDistrict()+",";
 					filter.setDistrict(district.substring(0,district.length()-1));
 					
 					
 				}
 				if(filter.getFbSendTelecom()== null)
 					filter.setFbSendTelecom("IS_FB_N");
 				else if (filter.getFbSendTelecom().equals(""))
 					filter.setFbSendTelecom("Y");
 				for(int j =0; j<statusList.size(); j ++)
 				{
 					if(statusList.get(j).getName().equals("N"))
 						statusList.remove(j);
 				}
 					
 				
 			}
		
 			
 		List<FbProcess> feedbackList = fbProcessDAO.getGeneralFeedback(filter.getFbCode(),filter.getFbType(), filter.getNetworkType(), thoiGianPAFrom,
 				thoiGianPATo, filter.getSubscribers(), filter.getSubscriberType(), thoiGianXLFrom, thoiGianXLTo, filter.getDeptProcess(), filter.getTeam(), filter.getStatus(), filter.getProvince(), filter.getDistrict(),
 				filter.getWards(), filter.getVipCode(),filter.getFbIbc(),filter.getFbSendTelecom(), column, order == 1 ? "ASC" : "DESC");
 		model.addAttribute("feedbackList", feedbackList);
 		
 		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.fbProcessByStatus();
		if (highlightConfigList.size()>3)
		{
			String highlight0 = "$(this).find(\"td\").css({"+highlightConfigList.get(0).getStyle()+"});";
			model.addAttribute("highlight0",highlight0);
			String highlight1 = "$(this).find(\"td\").css({"+highlightConfigList.get(1).getStyle()+"});";
			model.addAttribute("highlight1",highlight1);
			String highlight2 = "$(this).find(\"td\").css({"+highlightConfigList.get(2).getStyle()+"});";
			model.addAttribute("highlight2",highlight2);
			String highlight3 = "$(this).find(\"td\").css({"+highlightConfigList.get(3).getStyle()+"});";
			model.addAttribute("highlight3",highlight3);
		}
 		
		
		
 		model.addAttribute("thoiGianPAFrom", thoiGianPAFrom);
 		model.addAttribute("thoiGianPATo", thoiGianPATo);
 		model.addAttribute("thoiGianXLFrom", thoiGianXLFrom);
 		model.addAttribute("thoiGianXLTo", thoiGianXLTo);
		model.addAttribute("loaiPACBB", filter.getFbType());
		model.addAttribute("loaiMangCBB", filter.getNetworkType());
		model.addAttribute("loaiTBCBB", filter.getSubscriberType());	
		model.addAttribute("tinhThanhCBB", filter.getProvince());
		model.addAttribute("quanHuyenCBB", filter.getDistrict());
		model.addAttribute("phuongXaCBB", filter.getWards());
		model.addAttribute("deptProcessCBB", filter.getDeptProcess());
		model.addAttribute("teamCBB", filter.getTeam());
		model.addAttribute("vipCBB", filter.getVipCode());
		model.addAttribute("fbIbcCBB", filter.getFbIbc());	
		
		model.addAttribute("statusList", statusList);	
		model.addAttribute("fbSendTelecom", filter.getFbSendTelecom());	
		

 		return new ModelAndView("jspfeedback/tt6/generalFeedbackList");

 	}*/
 	@RequestMapping(value = "{function}")
 	public ModelAndView list(@RequestParam(required = false) String thoiGianPAFrom,
 							 @RequestParam(required = false) String thoiGianPATo,
 							 @RequestParam(required = false) String thoiGianXLFrom,
 							 @RequestParam(required = false) String thoiGianXLTo,
 							 @RequestParam(required = false) String checkColumn,
 							@RequestParam(required = false) String tableWidth,
 							 @PathVariable String function, 
 							 @ModelAttribute("filter") FbProcess filter, ModelMap model, HttpServletRequest request) {
 		
 		// Ngay thang
		if (thoiGianPAFrom == null || thoiGianPAFrom.equals("") || DateTools.isValid("dd/MM/yyyy", thoiGianPAFrom)==false) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			
			thoiGianPAFrom = df.format(cal.getTime());
		
		}
		if (thoiGianPATo == null || thoiGianPATo.equals("") || DateTools.isValid("dd/MM/yyyy", thoiGianPATo)==false)
		{
			thoiGianPATo = df.format(new Date());
		}
		
		if (thoiGianXLFrom != null && thoiGianXLFrom.equals("")) {
			thoiGianXLFrom = null;
		}
		
		if (thoiGianXLTo != null && thoiGianXLTo.equals("")) {
			thoiGianXLTo = null;
		}
		if (thoiGianXLFrom != null && !DateTools.isValid("dd/MM/yyyy", thoiGianXLFrom)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -8);
			
			thoiGianXLFrom = df.format(cal.getTime());
		}
		
		if (thoiGianXLTo != null && !DateTools.isValid("dd/MM/yyyy", thoiGianXLTo)) {
			thoiGianXLTo = df.format(new Date());
		}
		
		if(filter.getSubscribers() != null)
			filter.setSubscribers(filter.getSubscribers().trim());
		if(filter.getDistrict() != null && filter.getDistrict() != "")
		{
			String[] pDList = filter.getDistrict().split("//");
			filter.setDistrict(pDList[1]);
			filter.setProvince(pDList[0]);
		}
		model.addAttribute("checkColumn", checkColumn);
		model.addAttribute("tableWidth", tableWidth);
 		// Lay ten file export
 		Calendar currentDate = Calendar.getInstance();	

 		SimpleDateFormat now= new SimpleDateFormat("dd/MM/yyyy");
 		String dateNow1 = now.format(currentDate.getTime());
 		model.addAttribute("ngayHienTai", dateNow1);		

 		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
 		String dateNow = formatter.format(currentDate.getTime());
 		String exportFileName = "DSFeedback_" + dateNow;
 		model.addAttribute("exportFileName", exportFileName);
 		
 		
 		int order = 1;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
 		
 		List<SYS_PARAMETER> getListTTPACount = sysParameterDao.getListTTPACount();
 		model.addAttribute("getListTTPACount", getListTTPACount);

 		List<SYS_PARAMETER> sysParameterByCodeList = fbProcessDAO.getQLTTFBList();//sysParameterDao.getSysParameterByCode("STATUS_OF_CENTRAL_FEEDBACK");
 		model.addAttribute("QLTTFBList", sysParameterByCodeList);		

 		List<SYS_PARAMETER> loaiTBList = fbProcessDAO.getTenLoaiTB();//sysParameterDao.getSysParameterByCode("TEN_LOAI_TB");
 		model.addAttribute("loaiTBList", loaiTBList);	

 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();//sysParameterDao.getSysParameterByCode("TEN_LOAI_MANG");
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		// Phong/Dai
 		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("deptProcessList", deptProcessList);
		
 		// To VT
 		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(filter.getDeptProcess());
 		model.addAttribute("teamList", teamList);
 		
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
 		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.getProvinceFbByDeptCode(filter.getDeptProcess());
 		model.addAttribute("quanHuyenList", quanHuyenList);
 		
 		List<HWards> phuongXaList = hWardsDAO.selectWards( filter.getProvince(), filter.getDistrict());
 		model.addAttribute("phuongXaList", phuongXaList);
 		
 		List<FbVip> fbVipList = fbVipDao.getFbVipList();
 		model.addAttribute("fbVipList", fbVipList);
 		
 		if(filter.getStatus() == null){
 			
 			if(function.equals("list"))
 			{
 				String username = SecurityContextHolder.getContext().getAuthentication().getName();
 				List<SysUsers> userList = sysUserDao.checkRole(username);
 				if(Helper.checkRole2(userList, model).equals("Y"))
 					filter.setStatus(sysParameterByCodeList.get(0).getValue());
 				else
 					filter.setStatus("");
	 			model.addAttribute("trangThaiCBB", filter.getStatus());
	 			model.addAttribute("urlForm", sysParameterByCodeList.get(0).getRemark());
 			}
 			else
 			{
 				for(int i=0;i<sysParameterByCodeList.size();i++)
 				{
 					if(function.equals(sysParameterByCodeList.get(i).getRemark())){
	 					filter.setStatus(sysParameterByCodeList.get(i).getValue());
	 		 			model.addAttribute("trangThaiCBB", filter.getStatus());
	 		 			model.addAttribute("urlForm", sysParameterByCodeList.get(i).getRemark());
	 		 			break;
 					}
 				}
 			}
 		}
 		else
 		{
 			for(int i=0;i<sysParameterByCodeList.size();i++)
			{
 				if(filter.getStatus().equals(sysParameterByCodeList.get(i).getValue())){
 					model.addAttribute("urlForm", sysParameterByCodeList.get(i).getRemark());
 					break;
 				}
			}
 			
 			model.addAttribute("trangThaiCBB", filter.getStatus());
 		}
 		// Phan quyen FeedBack 
 		// User thuong: load fb theo tinh va nhung fb da duoc check gui dai vien thong
 		// User Quan tri: load tat ca
 			String username = SecurityContextHolder.getContext().getAuthentication().getName();
 			List<SysUsers> userList = sysUserDao.checkRole(username);
 			String district = "";
 			// Load status gui dai vt 
 			List<SYS_PARAMETER> statusList = sysParameterDao.getSysParameterByCode("FEEDBACK_COMBOBOX_SEND_TELECOM" );
 			
 			if(Helper.checkRole2 (userList, model).equals("N"))
 			{
 		 
 				/*if(filter.getDistrict()==null || filter.getDistrict().equals(""))
 				{
 					List<SysUserArea> districtList = sysUserAreaDao.getAreaByUsername(username);
 					for(int i=0; i< districtList.size();i++)
 						district += districtList.get(i).getDistrict()+",";
 					if (district.length()>=1)
 						filter.setDistrict(district.substring(0,district.length()-1));
 					
 					
 				}*/
 				
 				if(filter.getFbSendTelecom()== null)
 					filter.setFbSendTelecom("IS_FB_N");
 				else if (filter.getFbSendTelecom().equals(""))
 					filter.setFbSendTelecom("Y");
 				for(int j =0; j<statusList.size(); j ++)
 				{
 					if(statusList.get(j).getName().equals("N"))
 						statusList.remove(j);
 				}
 			}
 		//List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.fbProcessByStatus();
 		/*Begin Grid*/
		String nameTable="FB_PROCESS";
 		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		String url = "data/feedback.htm?fbcode="+(filter.getFbCode()== null?"":filter.getFbCode())+"&fbtype="+(filter.getFbType()== null?"":filter.getFbType())+"&networkType="+(filter.getNetworkType()== null?"":filter.getNetworkType())+
				"&thoiGianPAFrom="+thoiGianPAFrom+"&thoiGianPATo="+thoiGianPATo+
				"&subscribers="+ (filter.getSubscribers()== null?"":filter.getSubscribers())+"&subscriberType="+(filter.getSubscriberType()== null?"":filter.getSubscriberType())+
				"&thoiGianXLFrom="+(thoiGianXLFrom== null?"":thoiGianXLFrom)+"&thoiGianXLTo="+(thoiGianXLTo== null?"":thoiGianXLTo)+"&deptProcess="+(filter.getDeptProcess()== null?"":filter.getDeptProcess())+"&team="+(filter.getTeam()== null?"":filter.getTeam())+
				"&status="+(filter.getStatus()== null?"":filter.getStatus())+"&province="+(filter.getProvince()== null?"":filter.getProvince())+
				"&district="+(filter.getDistrict()== null?"":filter.getDistrict())+"&wards="+(filter.getWards()== null?"":filter.getWards())+
				"&vipCode="+(filter.getVipCode()== null?"":filter.getVipCode())+"&fbIbc="+(filter.getFbIbc()== null?"":filter.getFbIbc())+"&fbSendTelecom="+(filter.getFbSendTelecom()== null?"":filter.getFbSendTelecom())+"&function="+function;
	
		// TRUNGNQ 23/5/2018 them dieu kien tim kiem theo site
		String site = filter.getSite() == null ? "" : filter.getSite();
		url += "&site=" + site;
		
		model.addAttribute("url",url); 
		
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList(nameTable);
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		/*End Grid*/			
		/*Lay danh sach du lieu thoi gian xu ly, con lai cua FB de dem nguoc*/
		List<FbProcess> feedbackList = fbProcessDAO.getTimeCountdownFB(filter,thoiGianPAFrom,thoiGianPATo,thoiGianXLFrom,thoiGianXLTo,function);
 		model.addAttribute("feedbackList", feedbackList);
		
 		model.addAttribute("status", (filter.getStatus()== null?"":filter.getStatus()));
 		model.addAttribute("subscribers", (filter.getSubscribers()== null?"":filter.getSubscribers()));
 		model.addAttribute("fbcode", (filter.getFbCode()== null?"":filter.getFbCode()));
 		
 		model.addAttribute("thoiGianPAFrom", thoiGianPAFrom);
 		model.addAttribute("thoiGianPATo", thoiGianPATo);
 		model.addAttribute("thoiGianXLFrom", thoiGianXLFrom);
 		model.addAttribute("thoiGianXLTo", thoiGianXLTo);
		model.addAttribute("loaiPACBB", filter.getFbType());
		model.addAttribute("loaiMangCBB", filter.getNetworkType());
		model.addAttribute("loaiTBCBB", filter.getSubscriberType());	
		model.addAttribute("tinhThanhCBB", filter.getProvince());
		model.addAttribute("quanHuyenCBB", filter.getDistrict());
		model.addAttribute("phuongXaCBB", filter.getWards());
		model.addAttribute("deptProcessCBB", filter.getDeptProcess());
		model.addAttribute("teamCBB", filter.getTeam());
		model.addAttribute("vipCBB", filter.getVipCode());
		model.addAttribute("fbIbcCBB", filter.getFbIbc());	
		
		model.addAttribute("statusList", statusList);	
		model.addAttribute("fbSendTelecom", filter.getFbSendTelecom());	
		

 		return new ModelAndView("jspfeedback/tt6/generalFeedbackListMLMN");

 	}
 	
 	@RequestMapping(value = "data/feedback", method = RequestMethod.GET)
	public @ResponseBody 
	void dataFeedback(@RequestParam(required = false) String fbcode,
			@RequestParam(required = false) String fbtype,
			@RequestParam(required = false) String networkType,
			@RequestParam(required = false) String thoiGianPAFrom,
			@RequestParam(required = false) String thoiGianPATo,
			@RequestParam(required = false) String subscribers,
			@RequestParam(required = false) String subscriberType,
			@RequestParam(required = false) String thoiGianXLFrom,
			@RequestParam(required = false) String thoiGianXLTo,
			@RequestParam(required = false) String deptProcess,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String wards,
			@RequestParam(required = false) String vipCode,
			@RequestParam(required = false) String fbIbc,
			@RequestParam(required = false) String fbSendTelecom,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String site,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		
		List<FbProcess> feedbackList = fbProcessDAO.getGeneralFeedback(fbcode,fbtype, networkType, thoiGianPAFrom,
 				thoiGianPATo, subscribers, subscriberType, thoiGianXLFrom, thoiGianXLTo, deptProcess, team, status, province, district,
 				wards, vipCode,fbIbc,fbSendTelecom, site, null, null);
 		Gson gs = new Gson();
		String jsonCartList = gs.toJson(feedbackList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		
	 }

 	
 	/**
 	 * Khi click vao xu ly tung phan anh hoac xu ly phan anh theo lo
 	 * @param checkedList
 	 * @param model
 	 * @param request
 	 * @param response
 	 * @return
 	 */
 	@RequestMapping(value = "checkedList", method = RequestMethod.GET)
 	public String xuLyTheoLo(@RequestParam(required = false) String checkedList,
 			@RequestParam(required = false) String isManager,
 			@RequestParam(required = false) String note,
 			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String[] checkList = checkedList.split(",");
		String deptCode="";
		
		model.addAttribute("note", note);
		
		List<SysUsers> nguoiXuLyList = sysUsersDAO.selectAllSysUsers();
 		model.addAttribute("nguoiXuLyList", nguoiXuLyList);
 		
 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		model.addAttribute("checkedList", checkedList);
 		model.addAttribute("isManager", isManager);
 		
 		List<String> dvtFeedbackContentList = fbProcessDAO.getDvtFeedbackContentList();
		String feedbackContentList="var dvtFeedbackContentList = new Array(";
		String cn2="";
		for (String content : dvtFeedbackContentList) {
			feedbackContentList = feedbackContentList + cn2 +"\""+content+"\"";
			cn2=",";
		}
		feedbackContentList = feedbackContentList+");";
		model.addAttribute("dvtFeedbackContentList", feedbackContentList);
		
 		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);
		if(checkList.length == 1)
		{
		
			model.addAttribute("XuLyFeedback", "N");
			FbProcess fbProcess = fbProcessDAO.selectJoinFBProById(checkList[0].toString());
			model.addAttribute("fbProcess", fbProcess);
			model.addAttribute("fbSendTelecomStr", fbProcess.getFbSendTelecom());
			model.addAttribute("isFeedbackedStr", fbProcess.getIsFeedbacked());
			// Nguoi dang nhap
			if(fbProcess.getProcessUser() != null && !fbProcess.getProcessUser().equals(""))
				model.addAttribute("nguoiDangNhap", fbProcess.getProcessUser());
			else
				model.addAttribute("nguoiDangNhap", SecurityContextHolder.getContext().getAuthentication().getName());
			
	 		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
	 		model.addAttribute("quanHuyenList", quanHuyenList);
	 		
	 		
	 		List<HWards> phuongXaList = hWardsDAO.selectWards(fbProcess.getProvince(), fbProcess.getDistrict());
	 		model.addAttribute("phuongXaList", phuongXaList);
	 		
	 		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentByDistrict(fbProcess.getProvince(), fbProcess.getDistrict());
	 		model.addAttribute("deptProcessList", deptProcessList);
	 		if(deptProcessList.size() > 0){
		 		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcessList.get(0).getDeptCode());
		 		model.addAttribute("teamList", teamList);
	 		}
	 		try{
	 		
		 		if(fbProcess.getNetworkType().equals(loaiMangList.get(0).getValue())){
		 			List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		 			model.addAttribute("bscList", bscProvince);
		 		}
		 		else if(fbProcess.getNetworkType().equals(loaiMangList.get(1).getValue()))
		 		{
		 			List<VHBscProvince> bscProvince3G = vHBscProvinceDAO.selectBSCProvince3G(null, null);
		 			model.addAttribute("bscList", bscProvince3G);
		 		}
		 		else{
		 			List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		 			model.addAttribute("bscList", bscProvince);
		 		}
	 		}catch(Exception e){}
	 		model.addAttribute("fbIbcCBB", fbProcess.getFbIbc());
	 		model.addAttribute("content", fbProcess.getResponseContent());
	 		model.addAttribute("fbComment", fbProcess.getFbComment());
	 		model.addAttribute("dvtFeedbackContent", fbProcess.getDvtFeedbackContent());
	 		deptCode = fbProcess.getDeptProcess();
		}
		else
		{
			// Nguoi dang nhap
	 		model.addAttribute("nguoiDangNhap", SecurityContextHolder.getContext().getAuthentication().getName());
	 		
			List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
	 		model.addAttribute("deptProcessList", deptProcessList);
	 		
		 	List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(null);
		 	model.addAttribute("teamList", teamList);
	 		
	 		
			String p_where = " WHERE fbpr.ID = " + checkList[0].toString();
			for(int i=1;i<checkList.length;i++){
				p_where = p_where + " OR fbpr.ID = " + checkList[i].toString();
			}
			List<FbProcess> fbProcessList = fbProcessDAO.selectFBTheoLo(p_where);
			model.addAttribute("fbProcessList", fbProcessList);
			model.addAttribute("fbSendTelecomStr", fbProcessList.get(0).getFbSendTelecom());
			model.addAttribute("isFeedbackedStr", fbProcessList.get(0).getIsFeedbacked());
			try{
				if(fbProcessList.get(0).getNetworkType().equals(loaiMangList.get(0).getValue())){
		 			List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		 			model.addAttribute("bscList", bscProvince);
		 		}
		 		else if(fbProcessList.get(0).getNetworkType().equals(loaiMangList.get(1).getValue()))
		 		{
		 			List<VHBscProvince> bscProvince3G = vHBscProvinceDAO.selectBSCProvince3G(null, null);
		 			model.addAttribute("bscList", bscProvince3G);
		 		}
			}catch(Exception e){}
			
			model.addAttribute("XuLyFeedback", "Y");
			model.addAttribute("noiDungPhanAnh", fbProcessList.get(0).getFbContent());
			model.addAttribute("khachHangVip", fbProcessList.get(0).getVipName());
			if(fbProcessList.get(0).getProcessDate() != null)
				model.addAttribute("thoiGianXuLy", df_full.format(fbProcessList.get(0).getProcessDate()));
			model.addAttribute("noiDungXuLy", fbProcessList.get(0).getResponseContent());
			model.addAttribute("comment", fbProcessList.get(0).getFbComment());
			model.addAttribute("nguyenNhan", fbProcessList.get(0).getCausedby());
			model.addAttribute("fbIbcCBB", fbProcessList.get(0).getFbIbc());
			model.addAttribute("processStatus", fbProcessList.get(0).getProcessStatus());
			model.addAttribute("processMotional", fbProcessList.get(0).getProcessMotional());
			model.addAttribute("processHandleMethod", fbProcessList.get(0).getProcessHandleMethod());
			model.addAttribute("site", fbProcessList.get(0).getSite());
			model.addAttribute("processResultLocal", fbProcessList.get(0).getProcessResultLocal());
			model.addAttribute("dvtFeedbackContent", fbProcessList.get(0).getDvtFeedbackContent());
			/*model.addAttribute("bscRncCBB", fbProcessList.get(0).getBscRnc());
			model.addAttribute("deptCodeCBB", fbProcessList.get(0).getDeptProcess());*/
			deptCode = fbProcessList.get(0).getDeptProcess();
		}
		List<SysUsers> fullNameList = sysUsersDAO.getUserByMaPhong(deptCode);
		String ccEmailArray="var ccEmailList = new Array(";//Danh sach district cho cobobox
		String cn="";
		for (SysUsers dis : fullNameList) {
			ccEmailArray = ccEmailArray + cn +"\""+dis.getEmail()+"\"";
			cn=",";
		}
		ccEmailArray = ccEmailArray+");";
		model.addAttribute("ccEmailList", ccEmailArray);
		
		return "jspfeedback/tt6/generalFeedbackProcessForm";
	}
 	
 	@RequestMapping(value = "checkedList", method = RequestMethod.POST)
 	public String onSubmitXLTheoLo(@RequestParam(required = false) String checkedList,
 								   @RequestParam(required = false) String status,
 								   @RequestParam(required = false) String timeProcess,
 								   @RequestParam(required = false) String content,
 								   @RequestParam(required = false) String contentOld,
 								   @RequestParam(required = false) String fbSendTelecomStr, 
 								   @RequestParam(required = false) String isSendTelecomStr, 
 								   @RequestParam(required = false) String isFeedbackedStr, 
 								   @RequestParam(required = false) String fbOldComment,
 								  /*@RequestParam(required = false) String slPhanAnhLai, 
								   @RequestParam(required = false) String toaDoDeXuat,*/
 								   @RequestParam(required = false) String note,
 								   @RequestParam(required = false) String action, @ModelAttribute("checkform") @Valid FbProcess fbProcess, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response){
 		String username = SecurityContextHolder.getContext().getAuthentication().getName();
 		List<SysUsers> userList = sysUserDao.checkRole(username);
 		List<SysUsers> fullNameList = sysUsersDAO.getUserByMaPhong(fbProcess.getDeptProcess());
		String ccEmailArray="var ccEmailList = new Array(";//Danh sach district cho cobobox
		String cn="";
		for (SysUsers dis : fullNameList) {
			ccEmailArray = ccEmailArray + cn +"\""+dis.getEmail()+"\"";
			cn=",";
		}
		ccEmailArray = ccEmailArray+");";
		model.addAttribute("ccEmailList", ccEmailArray);
		
 		fbProcess.setModifiedBy(username);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList); 
 		String userAgent;
 		if(isThisRequestCommingFromAMobileDevice(request))
 			userAgent = "Mobile";
 		else
 			userAgent = "Web(PC)";
 		
		String[] checkList = checkedList.split(",");
 		Date now = new Date();
 		if (action.equals("save")) {	
			try{
				if(checkList.length == 1) // Xử lý từng phản ánh
				{
					if(Helper.checkRole2 (userList, model).equals("N") && isFeedbackedStr.equals("Y")){
						fbProcess.setIsFeedbacked("W");
						//AnhCTV them 12/11/2015
						fbProcess.setDvtFeedBackedDate(now);
						//end
					}
					else 
					{
						fbProcess.setIsFeedbacked(isFeedbackedStr);
						//AnhCTV them 12/11/2015
						if (isFeedbackedStr.equals("Y")){
							fbProcess.setDvtFeedBackedDate(now);
						}//end
					}
					
					fbProcess.setFbSendTelecom(fbSendTelecomStr); 
					if(isSendTelecomStr.equals("Y"))
						fbProcess.setCheckTimeTelecom(now);
					if (timeProcess != null && !timeProcess.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", timeProcess)==false)
					{
						loadAgainForm(checkedList, model);
						model.addAttribute("errortimeProcess", "*");
						return "jspfeedback/tt6/generalFeedbackProcessForm";
					}
					
					if(fbProcess.getDistrict() != null && fbProcess.getDistrict() != ""){
						String[] pDList = fbProcess.getDistrict().split("//");
						fbProcess.setProvince(pDList[0]);
						fbProcess.setDistrict(pDList[1]);
					}
					
					if(fbProcess.getResponseContent() != null && fbProcess.getResponseContent() != ""){
						contentOld += "<br>------------------------------------------------------------<br>";
						contentOld += "<div><b>" + username + "</b>&nbsp;&nbsp;<i>" + df_full.format(now) + "</i></div><br>" + fbProcess.getResponseContent();
						//thayDoiTrangThai(fbProcess, now);
					}

					if(fbProcess.getFbComment() != null && fbProcess.getFbComment() != ""){
						fbOldComment += "------------------------------------------------------------";
						fbOldComment += "<div><b>" + username + "&nbsp;-&nbsp;"+ fbProcess.getDeptProcess()+"&nbsp;-&nbsp;"+ userAgent+"-</b>&nbsp;<i>" + df_full.format(now) + "</i></div>" + fbProcess.getFbComment();
						//thayDoiTrangThai(fbProcess, now);
						fbOldComment = fbOldComment.replace("<p>", "");
						fbOldComment = fbOldComment.replace("</p>", "");
					}
					if(timeProcess!= null)
					{
						if(!timeProcess.equals(""))
							thayDoiTrangThai(status, timeProcess, fbProcess);
					}
					
					fbProcess.setId(Long.parseLong(checkList[0]));
					
					fbProcess.setFbComment(fbOldComment);
					fbProcess.setResponseContent(contentOld);
					fbProcessDAO.updateByPrimaryKeySelective(fbProcess);
					
					saveMessageKey(request, "messsage.confirm.xuLy");
				}
				else // Xử lý nhiều phản ánh
				{
					FbProcess record = new FbProcess();
					String soHieuLo = fbProcessDAO.danhSoHieuLo();
					record.setFleetNo(soHieuLo);
					record.setFleetDate(now);
					record.setProcessDate(now);
					
					record.setProcessUser(fbProcess.getProcessUser());
					record.setCausedby(fbProcess.getCausedby());
					record.setProcessStatus(fbProcess.getProcessStatus());
					record.setProcessHandleMethod(fbProcess.getProcessHandleMethod());
					record.setProcessMotional(fbProcess.getProcessMotional());
					record.setSite(fbProcess.getSite());
					record.setProcessResultLocal(fbProcess.getProcessResultLocal());
					record.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
					record.setDvtFeedbackContent(fbProcess.getDvtFeedbackContent());
					
					String p_where = " WHERE fbpr.ID = " + checkList[0].toString();
					for(int i=1;i<checkList.length;i++){
						p_where = p_where + " OR fbpr.ID = " + checkList[i].toString();
					}
					List<FbProcess> fbProcessList = fbProcessDAO.selectFBTheoLo(p_where);
					model.addAttribute("fbProcessList", fbProcessList);
					
					for(int i=0;i<fbProcessList.size();i++){
		
						record.setStatus(fbProcessList.get(i).getStatus());
						record.setDeadline(fbProcessList.get(i).getDeadline());
						if(fbProcess.getBscRnc() != ""){
							record.setBscRnc(fbProcess.getBscRnc());
						}
						else{
							record.setBscRnc(fbProcessList.get(i).getBscRnc());
						}
						
						if(fbProcess.getDeptProcess() != ""){
							record.setDeptProcess(fbProcess.getDeptProcess());
						}
						else{
							record.setDeptProcess(fbProcessList.get(i).getDeptProcess());
						}
						
						if(fbProcess.getFbIbc() != null){
							record.setFbIbc(fbProcess.getFbIbc());
						}else{
							record.setFbIbc(fbProcessList.get(i).getFbIbc());
						}
						
						String noiDung = fbProcessList.get(i).getResponseContent();
						if(fbProcess.getResponseContent() != null && fbProcess.getResponseContent() != ""){
							
							noiDung += "<br>------------------------------------------------------------<br>";
							noiDung += "<div><b>" + username + "</b>&nbsp;&nbsp;<i>" + df_full.format(now) + "</i></div><br>" + fbProcess.getResponseContent();
							record.setResponseContent(noiDung);
						}
						else
							record.setResponseContent(noiDung);
						
						//Comment
						String comment = fbProcessList.get(i).getFbComment();
						if(fbProcess.getFbComment()!= null && fbProcess.getFbComment() != ""){
							
							comment += "------------------------------------------------------------";
							comment += "<div><b>" + username + "&nbsp;-&nbsp;"+ fbProcessList.get(i).getDeptProcess()+"&nbsp;-&nbsp;"+ userAgent+"-</b>&nbsp;<i>" + df_full.format(now) + "</i></div>" + fbProcess.getFbComment();
							 
							
						} 
						
						record.setFbComment(comment);
						//thayDoiTrangThai(record, now);
						record.setId(fbProcessList.get(i).getId());
						fbProcessDAO.updateFeedbackForTheoLo(record);
					}
					
					saveMessageKey(request, "messsage.confirm.xuLyTheoLo");
				}
				
			}
			catch(Exception e){
				saveMessage(request, "Xử lý feedback bị lỗi");
			}
 		}
 		else if (action.equals("updateCentralFb")) {
 			try{
				if(checkList.length == 1)
				{
					
					int i = FeedbackJob.updateToCentralFeedback(fbProcess.getDeptFbId(),fbProcess.getCentralFbId(), df_full.format(now), content);
					if(i == 1)
						saveMessage(request, "Cập nhật vào central feedback thành công");
					else
						saveMessage(request, "Cập nhật vào central feedback thất bại");
					return "redirect:checkedList.htm?checkedList="+checkList[0];
				}
				else{
					saveMessage(request, "Cập nhật vào central fb thất bại");
				}
 			}
			catch(Exception e){}
 		}
		if (note!=null &&note.equals("DD") )
			return "redirect:/feedback/diemden/list.htm";
		else
			return "redirect:list.htm";
 	}
 	
 	// Cho phép cập nhật thời gian kết thúc xử lý, nếu là trạng thái chưa xử lý và cập nhật thời gian thì chuyển thành fb đã đóng.
 	private void thayDoiTrangThai(String status, String timeProcess, FbProcess fbProcess){
 		
 		if(status.equals("0") && timeProcess != null && !timeProcess.equals("")){
 			fbProcess.setStatus("3");
 		}
 		else
 			fbProcess.setStatus(status);
 		try {
			fbProcess.setProcessDate(df_full.parse(timeProcess));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
 	
 	private void loadAgainForm(String checkedList, ModelMap model){
 		String[] checkList = checkedList.split("-");
		
		List<SysUsers> nguoiXuLyList = sysUsersDAO.selectAllSysUsers();
 		model.addAttribute("nguoiXuLyList", nguoiXuLyList);
 		
 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
		// Nguoi dang nhap
 		model.addAttribute("nguoiDangNhap", SecurityContextHolder.getContext().getAuthentication().getName());
 		model.addAttribute("checkedList", checkedList);
		if(checkList.length == 1)
		{
			model.addAttribute("XuLyFeedback", "N");
			
			FbProcess fbProcess = fbProcessDAO.selectJoinFBProById(checkList[0].toString());
			model.addAttribute("fbProcess", fbProcess);
			
	 		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
	 		model.addAttribute("quanHuyenList", quanHuyenList);
	 		
	 		List<HWards> phuongXaList = hWardsDAO.selectWards(fbProcess.getProvince(), fbProcess.getDistrict());
	 		model.addAttribute("phuongXaList", phuongXaList);
	 		
	 		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentByDistrict(fbProcess.getProvince(), fbProcess.getDistrict());
	 		model.addAttribute("deptProcessList", deptProcessList);
	 		
	 		if(deptProcessList.size() > 0){
	 			List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcessList.get(0).getDeptCode());
	 	 		model.addAttribute("teamList", teamList);
	 		}
	 		
	 		try{
	 		
		 		if(fbProcess.getNetworkType().equals(loaiMangList.get(0).getValue())){
		 			List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		 			model.addAttribute("bscList", bscProvince);
		 		}
		 		else if(fbProcess.getNetworkType().equals(loaiMangList.get(1).getValue()))
		 		{
		 			List<VHBscProvince> bscProvince3G = vHBscProvinceDAO.selectBSCProvince3G(null, null);
		 			model.addAttribute("bscList", bscProvince3G);
		 		}
		 		else{
		 			List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		 			model.addAttribute("bscList", bscProvince);
		 		}
	 		}catch(Exception e){}
	 		model.addAttribute("fbIbcCBB", fbProcess.getFbIbc());
	 		model.addAttribute("content", fbProcess.getResponseContent());
		}
		else
		{
	 		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
	 		model.addAttribute("deptProcessList", deptProcessList);
	 		
	 		if(deptProcessList.size() > 0){
		 		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcessList.get(0).getDeptCode());
		 		model.addAttribute("teamList", teamList);
	 		}
	 		
			String p_where = " WHERE fbpr.ID = " + checkList[0].toString();
			for(int i=1;i<checkList.length;i++){
				p_where = p_where + " OR fbpr.ID = " + checkList[i].toString();
			}
			List<FbProcess> fbProcessList = fbProcessDAO.selectFBTheoLo(p_where);
			model.addAttribute("fbProcessList", fbProcessList);
			
			try{
				if(fbProcessList.get(0).getNetworkType().equals(loaiMangList.get(0).getValue())){
		 			List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		 			model.addAttribute("bscList", bscProvince);
		 		}
		 		else if(fbProcessList.get(0).getNetworkType().equals(loaiMangList.get(1).getValue()))
		 		{
		 			List<VHBscProvince> bscProvince3G = vHBscProvinceDAO.selectBSCProvince3G(null, null);
		 			model.addAttribute("bscList", bscProvince3G);
		 		}
			}catch(Exception e){}
			
			model.addAttribute("XuLyFeedback", "Y");
			model.addAttribute("noiDungPhanAnh", fbProcessList.get(0).getFbContent());
			model.addAttribute("khachHangVip", fbProcessList.get(0).getVipName());
			if(fbProcessList.get(0).getProcessDate() != null)
				model.addAttribute("thoiGianXuLy", df_full.format(fbProcessList.get(0).getProcessDate()));
			model.addAttribute("noiDungXuLy", fbProcessList.get(0).getResponseContent());
			model.addAttribute("comment", fbProcessList.get(0).getFbComment());
			model.addAttribute("nguyenNhan", fbProcessList.get(0).getCausedby());
			model.addAttribute("fbIbcCBB", fbProcessList.get(0).getFbIbc());
			model.addAttribute("processStatus", fbProcessList.get(0).getProcessStatus());
			model.addAttribute("processMotional", fbProcessList.get(0).getProcessMotional());
			model.addAttribute("processHandleMethod", fbProcessList.get(0).getProcessHandleMethod()); 
		}
 	}
 	
 	@RequestMapping("ajax/loadBscProvince")
	public @ResponseBody
	List<VHBscProvince> loadBscProvince(@RequestParam(required = false) String loaiMang, HttpServletRequest request){
 		
 		List<VHBscProvince> record = null;
 		
 		if(loaiMang.equals("2G"))
 			record = vHBscProvinceDAO.selectBSCProvince(null, null);
 		else if(loaiMang.equals("3G"))
 			record = vHBscProvinceDAO.selectBSCProvince3G(null, null);
 		return record;
 	}
 	
 	@RequestMapping("ajax/loadPhuongXa")
	public @ResponseBody
	List<HWards> loadPhuongXa(@RequestParam(required = false) String provinceDistrict, HttpServletRequest request) throws UnsupportedEncodingException{
 		List<HWards> record = null;
 		String encodedurl = URLDecoder.decode(provinceDistrict, "UTF-8"); 
		String noiDungNew = encodedurl.replace("\n", "<br>");
 		if(noiDungNew != ""){
 		String[] pDList = noiDungNew.split("//");
 		
 		record = hWardsDAO.selectWards(pDList[0], pDList[1]);
 		}
 		else
 		{
 			record = hWardsDAO.selectWards(null, null);
 		}
 		return record;
 	}
 	
 	@RequestMapping("ajax/loadProvince")
	public @ResponseBody
	List<HProvinceFb> loadProvince(@RequestParam(required = false) String deptProcess, HttpServletRequest request){
 		List<HProvinceFb> record = hProvinceFbDAO.getProvinceFbByDeptCode(deptProcess);
 		return record;
 	}
 	
 	@RequestMapping("ajax/loadDeptProcess")
	public @ResponseBody
	List<MDepartment> loadDeptProcess(@RequestParam(required = false) String provinceDistrict, HttpServletRequest request) throws UnsupportedEncodingException{
 		List<MDepartment> record = null;
 		String encodedurl = URLDecoder.decode(provinceDistrict, "UTF-8"); 
		String noiDungNew = encodedurl.replace("\n", "<br>");
 		if(noiDungNew != ""){
	 		String[] pDList = noiDungNew.split("//");
	 		record = fbProcessDAO.getDepartmentByDistrict(pDList[0], pDList[1]);
 		}
 		else
 		{
 			record = fbProcessDAO.getDepartmentByDistrict(null, null);
 		}
 		return record;
 	}
 	
 	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String fbDate,
			 			   @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
 		
 		
 		// Ngay thang
		if (fbDate == null || fbDate.equals("") || DateTools.isValid("dd/MM/yyyy HH:mm:ss", fbDate)==false) {
			
			fbDate = df_full.format(new Date());
		
		}
 			
 		FbProcess fbProcess = new FbProcess();
 		model.addAttribute("thongTinPAForm", fbProcess);
 		
 		List<FbVip> fbVipList = fbVipDao.getFbVipList();
 		model.addAttribute("fbVipList", fbVipList);
 		
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		List<SYS_PARAMETER> loaiTBList = fbProcessDAO.getTenLoaiTB();
 		model.addAttribute("loaiTBList", loaiTBList);
 		
 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
 		model.addAttribute("quanHuyenList", quanHuyenList);
 		
 		List<HWards> phuongXaList = hWardsDAO.selectWards(quanHuyenList.get(0).getCode(), quanHuyenList.get(0).getDistrict());
 		model.addAttribute("phuongXaList", phuongXaList);
 		
		List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		model.addAttribute("bscList", bscProvince);
		
		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentByDistrict(quanHuyenList.get(0).getCode(), quanHuyenList.get(0).getDistrict());
 		model.addAttribute("deptProcessList", deptProcessList);
 		
 		if(deptProcessList.size() > 0){
 			List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcessList.get(0).getDeptCode());
 	 		model.addAttribute("teamList", teamList);
 		}
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
		
		model.addAttribute("fbDate", fbDate);

 		return "jspfeedback/thongTinPAForm";	
 	}
 	
 	private void loadAgainForm(String fbDate, ModelMap model, FbProcess fbProcess){
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		List<SYS_PARAMETER> loaiTBList = fbProcessDAO.getTenLoaiTB();
 		model.addAttribute("loaiTBList", loaiTBList);
 		
 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		List<FbVip> fbVipList = fbVipDao.getFbVipList();
 		model.addAttribute("fbVipList", fbVipList);
 		
 		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.selectProvincesCode();
 		model.addAttribute("quanHuyenList", quanHuyenList);
 		
 		List<HWards> phuongXaList = hWardsDAO.selectWards( fbProcess.getProvince(), fbProcess.getDistrict());
 		model.addAttribute("phuongXaList", phuongXaList);
 		
		List<VHBscProvince> bscProvince = vHBscProvinceDAO.selectBSCProvince(null, null);
		model.addAttribute("bscList", bscProvince);
		
		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentByDistrict(fbProcess.getProvince(), fbProcess.getDistrict());
 		model.addAttribute("deptProcessList", deptProcessList);
 		
 		if(deptProcessList.size() > 0){
 			List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcessList.get(0).getDeptCode());
 	 		model.addAttribute("teamList", teamList);
 		}
		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
		model.addAttribute("loaiPACBB", fbProcess.getFbType());
		model.addAttribute("loaiThueBaoCBB", fbProcess.getSubscriberType());
		model.addAttribute("loaiMangCBB", fbProcess.getNetworkType());
		model.addAttribute("provinceCBB", fbProcess.getProvince());
		model.addAttribute("districtCBB", fbProcess.getDistrict());
		model.addAttribute("wardsCBB", fbProcess.getWards());
		model.addAttribute("bscRncCBB", fbProcess.getBscRnc());
		model.addAttribute("fbContent", fbProcess.getFbContent());
		model.addAttribute("fbIbcCBB", fbProcess.getFbIbc());
		model.addAttribute("deptProcessCBB", fbProcess.getDeptProcess());
		model.addAttribute("teamCBB", fbProcess.getTeam());
		model.addAttribute("fbDate", fbDate);
 	}
 	
 	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String fbDate, @ModelAttribute("thongTinPAForm") @Valid FbProcess fbProcess, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
 		
 		
 		if(fbProcess.getDistrict() != null && fbProcess.getDistrict() != "")
		{
			String[] pDList = fbProcess.getDistrict().split("//");
			fbProcess.setDistrict(pDList[1]);
			fbProcess.setProvince(pDList[0]);
		}
 		
 		//Ném lỗi
		if (result.hasErrors()) {
			loadAgainForm(fbDate, model, fbProcess);
			if(result.hasFieldErrors("fbDate"))
				model.addAttribute("fbDateError", "fbDateError");
			return "jspfeedback/thongTinPAForm";
		}
		
		if (fbDate == null || fbDate.equals("") || DateTools.isValid("dd/MM/yyyy HH:mm:ss", fbDate)==false)
		{
			loadAgainForm(fbDate, model, fbProcess);
			model.addAttribute("errorFbDate", "*");
			return "jspfeedback/thongTinPAForm";
		}
		
		fbProcess.setFbDate(df_full.parse(fbDate));
		List<FbVip> fbVip = fbVipDao.selectByVipCode(fbProcess.getVipCode());

		Calendar cal = Calendar.getInstance();
		cal.setTime(df_full.parse(fbDate));
		if(fbVip.size() > 0){
			String timeProcess = fbVip.get(0).getTimeProcess();
			String hour = timeProcess.substring(0, timeProcess.indexOf(":"));
			String minute = timeProcess.substring(timeProcess.indexOf(":")+1, timeProcess.length());
			
			cal.add(Calendar.HOUR, Integer.parseInt(hour));
			cal.add(Calendar.MINUTE, Integer.parseInt(minute));
		}
		else{
			cal.add(Calendar.HOUR, 20);
		}
		fbProcess.setDeadline(cal.getTime());
		fbProcess.setStatus("0");
		fbProcess.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		fbProcessDAO.insert(fbProcess);
		saveMessageKey(request, "messsage.confirm.addsuccess");
		
 		return "redirect:list.htm";
 	}
 	
 	@RequestMapping(value = "getDataFeedback", method = RequestMethod.GET)
	public String showFormGetDataFB( ModelMap model, HttpServletRequest request, HttpServletResponse response) {
 		String repeats = "60";
 		
 		List<SYS_PARAMETER> getStatusFbList = sysParameterDao.getStatusOfCentralFb();
 		model.addAttribute("getStatusFbList", getStatusFbList);
 		
 		
 		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String endDate = df_full.format(cal.getTime());
		cal.add(Calendar.MINUTE, -(Integer.parseInt(repeats)+15));
		String startDate = df_full.format(cal.getTime());

 		model.addAttribute("startDate", startDate);
 		model.addAttribute("endDate", endDate);
 		model.addAttribute("repeats", repeats);
		
 		return "jspfeedback/coreServiceHand";
 	}
 	
 	@RequestMapping(value = "getDataFeedback", method = RequestMethod.POST)
	public String onSubmitGetDataFB(@RequestParam(required = false) String status,
			   @RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,
			   @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
 		
 		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (endDate == null || endDate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", endDate)==false){
			endDate = df_full.format(cal.getTime());
		}
		if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy HH:mm:ss", startDate)==false){
			cal.add(Calendar.MINUTE, -75);
			startDate = df_full.format(cal.getTime());
		}
 		
		List<SYS_PARAMETER> getStatusFbList = sysParameterDao.getStatusOfCentralFb();
 		model.addAttribute("getStatusFbList", getStatusFbList);
 		
 		String result = "";
 		String intene = "";
 		if(status == null || status == ""){
 			for(int i=0;i<getStatusFbList.size()-1;i++){
 				result += FeedbackJob.feedbacks(getStatusFbList.get(i).getValue(), startDate, endDate);
 				result += "<br>";
 				intene += "<br>";
 			}
 		}
 		else{
 			result = FeedbackJob.feedbacks(status, startDate, endDate);
 		}
 		
 		if(result != "" && !result.equals(intene))
 			saveMessage(request, "Thực thi thành công");
 		else
 			saveMessage(request, "Thực thi thất bại");
 		model.addAttribute("status", status);
 		model.addAttribute("startDate", startDate);
 		model.addAttribute("endDate", endDate);
 		model.addAttribute("result","result:<br>" + result);
 		
 		return "jspfeedback/coreServiceHand";
 	}
 	
 	// Xoa feedback
 	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,@RequestParam (required = false) String deleteBy,
			@RequestParam (required = false) String note,
			HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			if(deleteBy == null) deleteBy = username;
			//fbProcessDAO.deleteByPrimaryKey(id);
			String[] idL = idList.split(",");
			for (String id : idL) {
				FbProcess record = new FbProcess();
				record.setId(Long.parseLong(id));
				record.setDeleteDate(new Date());
				record.setDeletedBy(deleteBy);
				fbProcessDAO.updateFeedbackDeleted(record);
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "messsage.error.delete");
			}
		
		
		if (note!=null &&note.equals("DD") )
			return "redirect:/feedback/diemden/list.htm";
		else
			return "redirect:list.htm";
	}
 	
 	@RequestMapping("sendSMSFeedbackDVT")
	public @ResponseBody
	Map<String, Object> sendSMSFeedbackDVT(@RequestBody SmsContents smsContents) {
	
		String mess = smsContents.getMessage(); 
		String district = smsContents.getDisctict();
		String isFeedbackedStr = smsContents.getIsdnType();
		String ccEmail = smsContents.getCcEmail();
		String idFBChecked = smsContents.getIsdn();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		district = district.replace("\n", "");
		mess = mess.replace("\n", "");
		if (idFBChecked!=null){
			String[] checkList = idFBChecked.split(",");
			String p_where = " WHERE fbpr.ID = " + checkList[0].toString();
			for(int i=1;i<checkList.length;i++){
				p_where = p_where + " OR fbpr.ID = " + checkList[i].toString();
			}
			List<FbProcess> fbProcessList = fbProcessDAO.selectFBTheoLo(p_where);
			for (FbProcess fbProcess : fbProcessList) {
				//send SMS
				smsContentsDAO.sendSmsFBDVT(fbProcess.getId().toString(),mess,username,district,isFeedbackedStr,ccEmail);
			}
			//send Email
			String kq = sendMailFBGuiDVT(fbProcessList,ccEmail,district,mess,isFeedbackedStr);	
			
		}
		
		
		SmsContents record = new SmsContents();
		record.setMessage(mess);
		record.setUsername(username); 
		record.setDisctict(district);
		record.setIsdnType(isFeedbackedStr);
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			data.put("value", "1");
		} catch (Exception e) {
			data.put("value", "0");
		} 
		 
		return data;
		
	}
 	private String sendMailFBGuiDVT(List<FbProcess> fbProcessList, String ccEmail,
			String district,String noidungXLy,String isFeedbackedStr) {
 		//cau hinh mail
 				List<CSystemConfigs> p = cSystemConfigsDAO.getSystemConfigMail();
 				Properties props = new Properties();
 				for (CSystemConfigs item: p) {
 					props.put(item.getParamName(), item.getParamValue());
 					//System.out.println(item.getName() + "-" + item.getValue());
 				}
 				String password="";
 				String userFrom = "";
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
 					
 				}
 				
 				String mailTo="";
 				String con="";
 				String subjet ="Feedback_request ĐVHM gửi tổ Viễn thông khu vực";
 				String errorSend="";
 				String status="";
 				String headerContent="";
 				if (isFeedbackedStr!=null&&isFeedbackedStr.equals("on"))
 					headerContent="Bạn có FB đã phản hồi gửi từ DDH:</br>";
 				else if (isFeedbackedStr!=null&&isFeedbackedStr.equals("off"))
 					headerContent="Bạn có FB mới gửi từ DDH:</br>";
 				
 				List<SysUsers> userReceivedList = sysUserDao.getUserByDeptDistrict(fbProcessList.get(0).getDeptProcess(), fbProcessList.get(0).getTeam());
 				for (SysUsers sysUsers : userReceivedList) {
 					mailTo+=con+sysUsers.getEmail();
					con=",";
				}
 				
 				// content
 				String content=null;
 				content= noidungMailGuiDVT(fbProcessList,headerContent,noidungXLy);
 				if ((mailTo==null||mailTo.equals(""))&&ccEmail!=null) 
 					mailTo= ccEmail;
 				if (mailTo!=null)
 				{
 				//gui mail
 				try
 				{
 					Mail mail = new Mail(props, null, userFrom, password,
 							mailTo,ccEmail,null, subjet, content);
 					/*Mail mail = new Mail(props, fileName, userFrom, password,
 							"caoanhhus@gmail.com", subjet, content);
 					*/
 					String result = mail.send();
 					
 					if (result != null) {
 						errorSend="Error in sending mail";
 						status="F";
 						
 					}
 					
 				}
 				catch(Exception ex)
 				{
 					System.out.println("Gui mail that bai");
 					errorSend="Error in sending mail";
 					status="F";
 				}
 				cSystemConfigsDAO.saveMail(subjet,mailTo,errorSend,status,null,"Gửi bằng tay",null);
 				}
 				return status;
 			}

 			private String noidungMailGuiDVT(List<FbProcess> fbProcessList, String headerContent, String  noidungXLy) {
 				String content ="";
 				if (headerContent!=null&&!headerContent.equalsIgnoreCase("null"))
 				{
 					content = "<span>"+headerContent+"</span>";
 				}
 				
 				content=content + "<br/>";
 				content=content + "<center><div>";
 				content=content +"<table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \"W_WORKING_MANAGEMENTS\" >";
 				// header
 				content=content + "<thead>";
 				content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color: #00B050;color:white;\">";
 				content=content + "<th style=\"border:1px solid;\" >Loại phản ánh</th>";
 				content=content + "<th style=\"border:1px solid;\" >SĐT</th>";
 				content=content + "<th style=\"border:1px solid;\" >Địa chỉ</th>";
 				content=content + "<th style=\"border:1px solid;\" >Nội dung phản ánh</th>";
 				content=content + "<th style=\"border:1px solid;\" >Nội dung xử lý</th>";
 				content=content + "</tr>";
 				content=content + "</thead>";
 				//body
 				content=content + "<tbody>";
 				for (FbProcess fbProcess : fbProcessList) {
 					String fbName="";
 	 				if (fbProcess.getFbName()!=null&&!fbProcess.getFbName().equals(""))
 	 				{
 	 					fbName=fbProcess.getFbName();
 	 				}
 	 				String sdt="";
 	 				if (fbProcess.getSubscribers()!=null&&!fbProcess.getSubscribers().equals(""))
 	 				{
 	 					sdt=fbProcess.getSubscribers();
 	 				}
 	 				String diaChi="";
 	 				if (fbProcess.getAddress()!=null&&!fbProcess.getAddress().equals(""))
 	 				{
 	 					diaChi=fbProcess.getAddress();
 	 				}
 	 				String noiDung="";
 	 				if (fbProcess.getFbContent()!=null&&!fbProcess.getFbContent().equals(""))
 	 				{
 	 					noiDung=fbProcess.getFbContent();
 	 				}
 	 				String noiDungTraoDoi="";
 	 				if (fbProcess.getFbContent()!=null&&!fbProcess.getFbContent().equals(""))
 	 				{
 	 					noiDungTraoDoi=fbProcess.getFbComment();
 	 				}
 	 				
 	 				content=content + "<tr>";
 	 				content=content +"<td style=\"border:1px solid;width:180px;\">"+fbName+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:130px;\">"+sdt+"</td>";

 	 				content=content +"<td style=\"border:1px solid;width:180px;\">"+diaChi+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:300px;\">"+noiDung+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:500px;\">"+noiDungTraoDoi+"</td>";
 	 				content=content + "</tr>";
				}
 				content=content + "</tbody>";
 				content=content +"</table>";
 				content=content +"</div></center>";
 			
 				return content;
			}

			

	@RequestMapping("send-sms-ddh")
	public @ResponseBody
	Map<String, Object> insertSMSDDH(@RequestBody SmsContents smsContents) {
	
		 
		String mess = smsContents.getMessage();  
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		mess = mess.replace("\n", "");
		String idFBChecked = smsContents.getIsdn();
		String isFeedbackedStr = smsContents.getIsdnType();
		String ccEmail = smsContents.getCcEmail();
		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			String[] checkList = idFBChecked.split(",");
			String p_where = " WHERE fbpr.ID = " + checkList[0].toString();
			for(int i=1;i<checkList.length;i++){
				p_where = p_where + " OR fbpr.ID = " + checkList[i].toString();
			}
			List<FbProcess> fbProcessList = fbProcessDAO.selectFBTheoLo(p_where);
			for (FbProcess fbProcess : fbProcessList) {
				//send SMS
				SmsContents record = new SmsContents();
				record.setMessage(mess);
				record.setUsername(username); 
				record.setIsdn(fbProcess.getId().toString());
				smsContentsDAO.sendSmsDDh(record);
			}
			
			//send Email
			String kq = sendMailFBGuiDDH(fbProcessList,ccEmail,isFeedbackedStr,mess);	
			
			data.put("value", "1");
		} catch (Exception e) {
			data.put("value", "0");
		} 
		 
		return data;
		
	}
	private String sendMailFBGuiDDH(List<FbProcess> fbProcessList, String ccEmail,String isFeedbackedStr,String noidungMailGuiDDH) {
 		//cau hinh mail
 				List<CSystemConfigs> p = cSystemConfigsDAO.getSystemConfigMail();
 				Properties props = new Properties();
 				for (CSystemConfigs item: p) {
 					props.put(item.getParamName(), item.getParamValue());
 					//System.out.println(item.getName() + "-" + item.getValue());
 				}
 				String password="";
 				String userFrom = "";
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
 					
 				}
 				String stb="";
 				String con="";
 				for (FbProcess fbProcess : fbProcessList) {
 					String site="";
 					if (fbProcess.getSite()!=null)
 					{
 						site=fbProcess.getSite();
 					}
 					stb= stb+con+fbProcess.getSubscribers()+"_"+fbProcess.getAddress()+"_"+site;
 					con=",";
				}
 				
 				String subjet ="KQ ĐVT PH_"+stb;
 				String errorSend="";
 				String status="";
 				String headerContent="";
 				headerContent="Kết quả ĐVT phản hồi:</br>";
 				String mailTo="";
 				con="";
 				List<SysUsers> userReceivedList = sysUserDao.getUserAllByMaPhong("DDH", "XLFB");
 				for (SysUsers sysUsers : userReceivedList) {
 					mailTo+=con+sysUsers.getEmail();
					con=",";
				}
 				
 				// content
 				String content=null;
 				content= noidungMailGuiDDH(fbProcessList,headerContent,noidungMailGuiDDH);
 				if ((mailTo==null||mailTo.equals(""))&&ccEmail!=null) 
 					mailTo= ccEmail;
 				if (mailTo!=null)
 				{
 				//gui mail
 				try
 				{
 					Mail mail = new Mail(props, null, userFrom, password,
 							mailTo,ccEmail,null, subjet, content);
 					/*Mail mail = new Mail(props, fileName, userFrom, password,
 							"caoanhhus@gmail.com", subjet, content);
 					*/
 					String result = mail.send();
 					
 					if (result != null) {
 						errorSend="Error in sending mail";
 						status="F";
 						
 					}
 					
 				}
 				catch(Exception ex)
 				{
 					System.out.println("Gui mail that bai");
 					errorSend="Error in sending mail";
 					status="F";
 				}
 				cSystemConfigsDAO.saveMail(subjet,mailTo,errorSend,status,null,"Gửi bằng tay",null);
 				}
 				return status;
 			}

 			private String noidungMailGuiDDH(List<FbProcess> fbProcessList, String headerContent, String  noidungPH) {
 				String content ="";
 				if (headerContent!=null&&!headerContent.equalsIgnoreCase("null"))
 				{
 					content = "<span>"+headerContent+"</span>";
 				}
 				
 				content=content + "<br/>";
 				content=content + "<center><div>";
 				content=content +"<table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \"W_WORKING_MANAGEMENTS\" >";
 				// header
 				content=content + "<thead>";
 				content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color: #00B050;color:white;\">";
 				content=content + "<th style=\"border:1px solid;\" >Loại phản ánh</th>";
 				content=content + "<th style=\"border:1px solid;\" >SĐT</th>";
 				content=content + "<th style=\"border:1px solid;\" >Địa chỉ</th>";
 				content=content + "<th style=\"border:1px solid;\" >Nội dung phản ánh</th>";
 				content=content + "<th style=\"border:1px solid;\" >Nội dung xử lý</th>";
 				content=content + "<th style=\"border:1px solid;\" >Đài VT phản hổi</th>";
 				content=content + "</tr>";
 				content=content + "</thead>";
 				//body
 				content=content + "<tbody>";
 				for (FbProcess fbProcess : fbProcessList) {
 					String fbName="";
 	 				if (fbProcess.getFbName()!=null&&!fbProcess.getFbName().equals(""))
 	 				{
 	 					fbName=fbProcess.getFbName();
 	 				}
 	 				String sdt="";
 	 				if (fbProcess.getSubscribers()!=null&&!fbProcess.getSubscribers().equals(""))
 	 				{
 	 					sdt=fbProcess.getSubscribers();
 	 				}
 	 				String diaChi="";
 	 				if (fbProcess.getAddress()!=null&&!fbProcess.getAddress().equals(""))
 	 				{
 	 					diaChi=fbProcess.getAddress();
 	 				}
 	 				String noiDung="";
 	 				if (fbProcess.getFbContent()!=null&&!fbProcess.getFbContent().equals(""))
 	 				{
 	 					noiDung=fbProcess.getFbContent();
 	 				}
 	 				String noiDungTraoDoi="";
 	 				if (fbProcess.getFbContent()!=null&&!fbProcess.getFbContent().equals(""))
 	 				{
 	 					noiDungTraoDoi=fbProcess.getFbComment();
 	 				}
 	 				content=content + "<tr>";
 	 				content=content +"<td style=\"border:1px solid;width:180px;\">"+fbName+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:130px;\">"+sdt+"</td>";

 	 				content=content +"<td style=\"border:1px solid;width:180px;\">"+diaChi+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:300px;\">"+noiDung+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:500px;\">"+noiDungTraoDoi+"</td>";
 	 				content=content +"<td style=\"border:1px solid;width:500px;\">"+noidungPH+"</td>";
 	 				content=content + "</tr>";
				}
 				content=content + "</tbody>";
 				content=content +"</table>";
 				content=content +"</div></center>";
 			
 				return content;
			}

			
 	private boolean isThisRequestCommingFromAMobileDevice(HttpServletRequest request){

 	    // http://www.hand-interactive.com/m/resources/detect-mobile-java.htm
 	    String userAgent = request.getHeader("User-Agent");
 	    String httpAccept = request.getHeader("Accept");

 	    UAgentInfo detector = new UAgentInfo(userAgent, httpAccept);

 	    if (detector.detectMobileQuick()) {
 	        return true;
 	    }

 	    return false;
 	}
 	
 	// AnhCTV:14/11/2015: chuyen FB cho dai vien thong khac
	@RequestMapping("moveFeedback")
	public @ResponseBody
	String moveFeedback(@RequestBody FbProcess record) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		/*try {*/
			record.setModifyDate(new Date());
			record.setModifiedBy(username);
			fbProcessDAO.removeFbProcess(record);
			return "1";
		/*} catch (Exception e) {
			return "0";
		} */

	}
	// chuyen FB sang trang thai la fb diem den
	@RequestMapping(value = "checkDiemDen", method = RequestMethod.GET)
	public String checkDiemDen (@RequestParam(required = false) String idList,@RequestParam(required = false) String status,@RequestParam(required = false) String note,
 			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		/*try {*/
			fbProcessDAO.checkDiemDen(idList,status,username);
			saveMessageKey(request, "messsage.confirm.checkDiemDensuccess");
		/*} catch (Exception e) {
			return "0";
			saveMessageKey(request, "messsage.confirm.checkDiemDenFail");
		} */
			if (note!=null &&note.equals("DD") )
				return "redirect:/feedback/diemden/list.htm";
			else
				return "redirect:list.htm";

	}
	
	@RequestMapping("export")
	public String export(@RequestParam(required = false) String fbcode,
			@RequestParam(required = false) String fbtype,
			@RequestParam(required = false) String networkType,
			@RequestParam(required = false) String thoiGianPAFrom,
			@RequestParam(required = false) String thoiGianPATo,
			@RequestParam(required = false) String subscribers,
			@RequestParam(required = false) String subscriberType,
			@RequestParam(required = false) String thoiGianXLFrom,
			@RequestParam(required = false) String thoiGianXLTo,
			@RequestParam(required = false) String deptProcess,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String wards,
			@RequestParam(required = false) String vipCode,
			@RequestParam(required = false) String fbIbc,
			@RequestParam(required = false) String fbSendTelecom,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String site,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (fbcode==null)
			fbcode = "";
		if (fbtype==null)
			fbtype = "";
		if (networkType==null)
			networkType = "";
		if (thoiGianPAFrom==null)
			thoiGianPAFrom = "";
		if (thoiGianPATo==null)
			thoiGianPATo="";
		if (subscribers==null)
			subscribers = "";
		if (subscriberType==null)
			subscriberType = "";
		if (thoiGianXLFrom==null)
			thoiGianXLFrom = "";
		if (thoiGianXLTo==null)
			thoiGianXLTo = "";
		if (team==null)
			team="";
		if (deptProcess==null)
			deptProcess = "";
		if (status==null)
			status = "";
		if (province==null)
			province = "";
		if (district==null)
			district = "";
		if (wards==null)
			wards="";
		if (vipCode==null)
			vipCode = "";
		if (fbIbc==null)
			fbIbc = "";
		if (fbSendTelecom==null)
			fbSendTelecom = "";
		if (function==null)
			function = "";
		if (site == null) {
			site = "";
		}
		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;
		// return
		// Lay ten file export		
		File tempFile = new File(dataDir + "/" + outfile);
		List<String> paramList = new ArrayList<String>();
		paramList.add(fbcode);
		paramList.add(fbtype);
		paramList.add(networkType);
		paramList.add(thoiGianPAFrom);
		paramList.add(thoiGianPATo);
		paramList.add(subscribers);
		paramList.add(subscriberType);
		paramList.add(thoiGianXLFrom);
		paramList.add(thoiGianXLTo);
		paramList.add(deptProcess);
		paramList.add(team);
		paramList.add(status);
		paramList.add(province);
		paramList.add(district);
		paramList.add(wards);
		paramList.add(vipCode);
		paramList.add(fbIbc);
		paramList.add(fbSendTelecom);
		paramList.add(site);
		paramList.add(null);
		paramList.add(null);
				
			
		String tableName="FB_PROCESS";
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName= tableName+ dateNow;
		
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList(tableName);
		ExportTools.exportToExcel("PK_FB_PROCESS_MLMN.GET_GENERAL_FEEDBACK(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",paramList,tempFile,exportFileName, tableName,"THÔNG TIN PHẢN ÁNH",columnList,"Y");
		
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "." + ext + "\"");
		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
		tempFile.delete();
		
		return null;
	}
 }


