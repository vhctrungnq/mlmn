 package controller.feedback;

 import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import dao.CHighlightConfigsDAO;
import dao.FbDeptPlacesDAO;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.FbVipDAO;
import dao.HProvinceFbDAO;
import dao.HWardsDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VHBscProvinceDAO;
import vo.CHighlightConfigs;
import vo.FbProcess;
import vo.FbType;
import vo.FbVip;
import vo.FeedbackJob;
import vo.HProvinceFb;
import vo.HWards;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.VHBscProvince;
import vo.alarm.utils.DateTools;

/**
 * Function        : Quan ly thong tin phan anh
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
 @Controller
 @RequestMapping("/feedback/quan-ly-thong-tin-lpa/*")
 public class QLTTPhanAnhController extends BaseController{

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

 	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
 	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
 	
 	@RequestMapping(value = "{function}")
 	public ModelAndView list(@RequestParam(required = false) String thoiGianPAFrom,
 							 @RequestParam(required = false) String thoiGianPATo,
 							 @RequestParam(required = false) String thoiGianXLFrom,
 							 @RequestParam(required = false) String thoiGianXLTo,
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

 		List<SYS_PARAMETER> sysParameterByCodeList = fbProcessDAO.getQLTTFBList();//sysParameterDao.getSysParameterByCode("QL_THONG_TIN_PHAN_ANH");
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
 				filter.setStatus(sysParameterByCodeList.get(0).getValue());
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
 		
 		List<FbProcess> feedbackList = fbProcessDAO.getQLThongTinPA(filter.getFbCode(),filter.getFbType(), filter.getNetworkType(), thoiGianPAFrom,
 				thoiGianPATo, filter.getSubscribers(), filter.getSubscriberType(), thoiGianXLFrom, thoiGianXLTo, filter.getDeptProcess(), filter.getTeam(), filter.getStatus(), filter.getProvince(), filter.getDistrict(),
 				filter.getWards(), filter.getVipCode(),filter.getFbIbc(), column, order == 1 ? "ASC" : "DESC");
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

 		return new ModelAndView("jspfeedback/QLTTPhanAnhList");

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
 	public String xuLyTheoLo(@RequestParam(required = false) String checkedList, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String[] checkList = checkedList.split("-");
		
		List<SysUsers> nguoiXuLyList = sysUsersDAO.selectAllSysUsers();
 		model.addAttribute("nguoiXuLyList", nguoiXuLyList);
 		
 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		model.addAttribute("checkedList", checkedList);
		if(checkList.length == 1)
		{
			
			
			model.addAttribute("XuLyFeedback", "N");
			
			FbProcess fbProcess = fbProcessDAO.selectJoinFBProById(checkList[0].toString());
			model.addAttribute("fbProcess", fbProcess);
			
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
			model.addAttribute("nguyenNhan", fbProcessList.get(0).getCausedby());
			model.addAttribute("fbIbcCBB", fbProcessList.get(0).getFbIbc());
			model.addAttribute("processStatus", fbProcessList.get(0).getProcessStatus());
			model.addAttribute("processMotional", fbProcessList.get(0).getProcessMotional());
			model.addAttribute("processHandleMethod", fbProcessList.get(0).getProcessHandleMethod());
			model.addAttribute("site", fbProcessList.get(0).getSite());
			model.addAttribute("processResultLocal", fbProcessList.get(0).getProcessResultLocal());
			/*model.addAttribute("bscRncCBB", fbProcessList.get(0).getBscRnc());
			model.addAttribute("deptCodeCBB", fbProcessList.get(0).getDeptProcess());*/
		}
		
		return "jspfeedback/xuLyTheoLoList";
	}
 	
 	@RequestMapping(value = "checkedList", method = RequestMethod.POST)
 	public String onSubmitXLTheoLo(@RequestParam(required = false) String checkedList,
 								   @RequestParam(required = false) String status,
 								   @RequestParam(required = false) String timeProcess,
 								   @RequestParam(required = false) String content,
 								   @RequestParam(required = false) String contentOld,
 								   @RequestParam(required = false) String action, @ModelAttribute("checkform") @Valid FbProcess fbProcess, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response){
 		String username = SecurityContextHolder.getContext().getAuthentication().getName();
 		fbProcess.setModifiedBy(username);
 		
 		List<SYS_PARAMETER> fbIbcList = sysParameterDao.getListFbIbc();
 		model.addAttribute("fbIbcList", fbIbcList);
 		
		String[] checkList = checkedList.split("-");
 		Date now = new Date();
 		if (action.equals("save")) {	
			try{
				if(checkList.length == 1) // Xử lý từng phản ánh
				{
					
					if (timeProcess != null && !timeProcess.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", timeProcess)==false)
					{
						loadAgainForm(checkedList, model);
						model.addAttribute("errortimeProcess", "*");
						return "jspfeedback/xuLyTheoLoList";
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
					thayDoiTrangThai(status, timeProcess, fbProcess);
					fbProcess.setId(Long.parseLong(checkList[0]));
					fbProcess.setResponseContent(contentOld);
					fbProcessDAO.updateByPrimaryKey(fbProcess);
					
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
			model.addAttribute("nguyenNhan", fbProcessList.get(0).getCausedby());
			model.addAttribute("fbIbcCBB", fbProcessList.get(0).getFbIbc());
			model.addAttribute("processStatus", fbProcessList.get(0).getProcessStatus());
			model.addAttribute("processMotional", fbProcessList.get(0).getProcessMotional());
			model.addAttribute("processHandleMethod", fbProcessList.get(0).getProcessHandleMethod());
			/*model.addAttribute("bscRncCBB", fbProcessList.get(0).getBscRnc());
			model.addAttribute("deptCodeCBB", fbProcessList.get(0).getDeptProcess());*/
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
 		// Chia startDate, endDate thanh nhung khoang thoi gian nho hon
 		int dayNumbers = dayNumbers(startDate, endDate);
 		if(dayNumbers>1){ // Neu khoang thoi gian nhieu hon 1 ngay thi tach thanh nhieu khoang
 			String[][] ara = cutTimes(startDate, endDate);
 			for(int k=0;k<ara.length;k++){
 				if(ara[k][0] != null && ara[k][1] != null)
 				{
 					if(status == null || status == ""){
 			 			for(int i=0;i<getStatusFbList.size()-1;i++){
 			 				result += FeedbackJob.feedbacks(getStatusFbList.get(i).getValue(), ara[k][0], ara[k][1]);
 			 				result += "<br>";
 			 				intene += "<br>";
 			 			}
 			 		}
 			 		else{
 			 			result += FeedbackJob.feedbacks(status, ara[k][0], ara[k][1]);
 			 			result += "<br>";
			 			intene += "<br>";
 			 		}
 				}	
 			}
 		}
 		else{
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
 	
 	private int dayNumbers(String startDateStr, String endDateStr) throws ParseException{
 		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
 		Date startDate = df.parse(startDateStr);
 		Date endDate = df.parse(endDateStr);

		return calculateDays(startDate, endDate);
 	}
 	
 	private String[][] cutTimes(String startDateStr, String endDateStr) throws ParseException{
 		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
 		Date startDate = df.parse(startDateStr);
 		Date endDate = df.parse(endDateStr);

		int dayNumbers = calculateDays(startDate, endDate);
		String[][] ara = new String[dayNumbers+1][2];
 		String temp = null;
 		int daysBetween = 0;  
		while (startDate.before(endDate)) {  
			temp = startDateStr;
			startDate = addDays(startDate,1); 
			if(startDate.before(endDate)){
				startDateStr = df.format(startDate);
				ara[daysBetween][0] = temp;
				ara[daysBetween][1] = startDateStr;
			    daysBetween++;
		    }
			else{
				ara[daysBetween][0] = temp;
				ara[daysBetween][1] = endDateStr;
				break;
			}
		  }
 		return ara;
 	}
 	
 	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days); //minus number would decrement the days
        return cal.getTime();
    }
 	
 	public static int calculateDays(Date dateEarly, Date dateLater) {  
 		  return (int)(dateLater.getTime() - dateEarly.getTime()) / (24 * 60 * 60 * 1000);  
 	}
 	
 	// Xoa feedback
  	@RequestMapping(value = "delete", method = RequestMethod.GET)
 	public String onDelete(@RequestParam (required = true) Long id, HttpServletRequest request, HttpServletResponse response) {
 		
 		try
 		{
 			String username = SecurityContextHolder.getContext().getAuthentication().getName();
 			//fbProcessDAO.deleteByPrimaryKey(id);
 			FbProcess record = new FbProcess();
 			record.setId(id);
 			record.setDeleteDate(new Date());
 			record.setDeletedBy(username);
 			fbProcessDAO.updateFeedbackDeleted(record);
 			saveMessageKey(request, "messsage.confirm.deletesuccess");
 		}
 		catch(Exception e)
 		{
 			saveMessageKey(request, "messsage.error.delete");
 			}
 		
 		
 		return "redirect:list.htm";
 	}
 }

