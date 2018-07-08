package controller.feedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import controller.BaseController;

import vo.FbRpProcess;
import vo.FbType;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;

import dao.FbProcessDAO;
import dao.FbRpProcessDAO;
import dao.FbTypeDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/feedback/tim-kiem-bc-xlpa-theo-ngay/*")
public class TimKiemBC_XLPADateController extends BaseController{

	@Autowired
 	private FbTypeDAO FbTypeDAO;
	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private FbRpProcessDAO fbRpProcessDAO;
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value="list")
    public String list(@RequestParam(required = false) String startDay,
			   		   @RequestParam(required = false) String endDay,
			   		   @RequestParam(required = false) String fbType,
			   		   @RequestParam(required = false) String networkType,
			   		   @RequestParam(required = false) String nguyenNhan,
			   		   @RequestParam(required = false) String deptProcess, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Ngay thang
		if (startDay == null || startDay.equals("")|| DateTools.isValid("dd/MM/yyyy", startDay)==false) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			startDay = df_full.format(cal.getTime());
		
		}
		if (endDay == null || endDay.equals("")|| DateTools.isValid("dd/MM/yyyy", endDay)==false)
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			endDay = df_full.format(cal.getTime());
		}
		
		if(nguyenNhan != null){
			nguyenNhan = nguyenNhan.trim();
		}
		
		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
		
 		List<SYS_PARAMETER> loaiMangList = fbProcessDAO.getTenLoaiMang();
 		model.addAttribute("loaiMangList", loaiMangList);
 		
 		List<MDepartment> toXuLyList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("toXuLyList", toXuLyList);
 		
 		List<FbRpProcess> bcXuLyPATheoNgayList = fbRpProcessDAO.getBCXLPATheoNgayFilter(null, null, startDay, endDay, fbType, networkType, nguyenNhan, deptProcess);
 		model.addAttribute("bcXuLyPATheoNgayList", bcXuLyPATheoNgayList);
 		
 		model.addAttribute("startDay", startDay);
		model.addAttribute("endDay", endDay);
		model.addAttribute("loaiPACBB", fbType);
		model.addAttribute("loaiMangCBB", networkType);
		model.addAttribute("nguyenNhan", nguyenNhan);
		model.addAttribute("toXuLyCBB", deptProcess);
		
		return "jspfeedback/timKiemBCXLPATheoNgayList";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		try
		{
			fbRpProcessDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
		}
	
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		FbRpProcess fbRpProcess = (id == null) ? new FbRpProcess() : fbRpProcessDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("xuLyPhanAnhForm", fbRpProcess);
		
		
		
		return "jspfeedback/xuLyPhanAnhForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("xuLyPhanAnhForm") @Valid FbRpProcess fbRpProcess, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		
		
		//Ném lỗi
		if (result.hasErrors()) {
			
			return "jspfeedback/xuLyPhanAnhForm";
		}
		
		fbRpProcess.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if(id != "")
		{
			fbRpProcessDAO.updateByPrimaryKey(fbRpProcess);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:list.htm";
	}
}
