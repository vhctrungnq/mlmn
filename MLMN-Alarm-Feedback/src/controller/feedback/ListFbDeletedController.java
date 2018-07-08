package controller.feedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.FbVipDAO;
import dao.HProvinceFbDAO;
import dao.HWardsDAO;
import dao.SYS_PARAMETERDAO;

import vo.FbProcess;
import vo.FbType;
import vo.FbVip;
import vo.HProvinceFb;
import vo.HWards;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;

/**
 * Function        : Danh sach feedback da xoa
 * Created By      : BUIQUANG
 * Create Date     : 20/2/2014
 * Modified By     : 
 * Modify Date     :
 * @author BUIQUANG
 * Description     :
 */

@Controller
@RequestMapping("/feedback/list-fb-deleted/*")
public class ListFbDeletedController extends BaseController{

	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
	@Autowired
 	private FbTypeDAO FbTypeDAO;
	@Autowired
 	private HWardsDAO hWardsDAO;
 	@Autowired
 	private FbVipDAO fbVipDao;
 	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
 	
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
		
		List<FbProcess> feedbackDeletedList = fbProcessDAO.getFbDeletedList(filter.getFbCode(),filter.getFbType(), filter.getNetworkType(), thoiGianPAFrom,
 				thoiGianPATo, filter.getSubscribers(), filter.getSubscriberType(), thoiGianXLFrom, thoiGianXLTo, filter.getDeptProcess(), filter.getTeam(), filter.getStatus(), filter.getProvince(), filter.getDistrict(),
 				filter.getWards(), filter.getVipCode(),filter.getFbIbc(), column, order == 1 ? "ASC" : "DESC");
 		model.addAttribute("feedbackDeletedList", feedbackDeletedList);
 		
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
 		
 		
		return new ModelAndView("jspfeedback/tt6/fbDeletedList");
	}
	
	// Xoa feedback
 	@RequestMapping(value = "recovery", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Long id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			FbProcess record = new FbProcess();
			record.setId(id);
			record.setDeleteDate(null);
			record.setDeletedBy(null);
			fbProcessDAO.updateFeedbackDeleted(record);
			saveMessage(request, "Phục hồi thành công");
		}
		catch(Exception e)
		{
			saveMessage(request, "Phục hồi thất bại");
			}
		
		
		return "redirect:list.htm";
	}
}
