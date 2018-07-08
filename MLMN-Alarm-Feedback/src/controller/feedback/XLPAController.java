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
import vo.alarm.utils.DateTools;

import dao.FbRpProcessDAO;


@Controller
@RequestMapping("/feedback/bao-cao-xu-ly-phan-anh/*")
public class XLPAController extends BaseController{
	
	
	@Autowired
	private FbRpProcessDAO fbRpProcessDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping(value="list", method = RequestMethod.GET)
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate,
					   @RequestParam(required = false) String nguongChonNguyenNhan, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "BaoCaoXLPA_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			startDate = df_full.format(cal.getTime());
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			endDate = df_full.format(cal.getTime());
		}
		
		
		List<FbRpProcess> bcXuLyPAList = fbRpProcessDAO.getBCXLPAFilter(startDate, endDate, null, null, null, null, null);
 		model.addAttribute("bcXuLyPAList", bcXuLyPAList);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nguongChonNguyenNhan", nguongChonNguyenNhan);
				
		return "jspfeedback/bcXuLyPhanAnhList";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String onSubmitList(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,
			   @RequestParam(required = false) String nguongChonNguyenNhan, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "BaoCaoXLPA_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {
			
			startDate = df_full.format(DateTools.startMonth(new Date()));
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			endDate = df_full.format(cal.getTime());
		}
		
		try
		{
			int ordering = 0;
			if(nguongChonNguyenNhan != null && nguongChonNguyenNhan != ""){
				ordering = Integer.parseInt(nguongChonNguyenNhan);
			}
			System.out.println(ordering);
			
		}
		catch(Exception e)
		{
			nguongChonNguyenNhan = "";
		}
		
		// Them vao bang FB_RP_PROCESS nhung phan anh chua xu ly theo nguong chon
		if(nguongChonNguyenNhan != null && nguongChonNguyenNhan != ""){
			List<FbRpProcess> phanAnhTheoNguong = fbRpProcessDAO.getBCXLPAJoinInsert(startDate, endDate, nguongChonNguyenNhan);
			
			for(int i=0;i<phanAnhTheoNguong.size();i++){
				FbRpProcess record = new FbRpProcess();
				record.setDay(phanAnhTheoNguong.get(i).getDay());
				record.setFromRpDate(phanAnhTheoNguong.get(i).getFromRpDate());
				record.setToRpDate(phanAnhTheoNguong.get(i).getToRpDate());
				record.setFbType(phanAnhTheoNguong.get(i).getFbType());
				record.setNetworkType(phanAnhTheoNguong.get(i).getNetworkType());
				record.setTotalFb(phanAnhTheoNguong.get(i).getTotalFb());
				record.setCausedby(phanAnhTheoNguong.get(i).getCausedby());
				record.setResponseContent(phanAnhTheoNguong.get(i).getResponseContent());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				fbRpProcessDAO.insert(record);
				
				
			}
		}
		
		List<FbRpProcess> bcXuLyPAList = fbRpProcessDAO.getBCXLPAFilter(startDate, endDate, null, null, null, null, null);
 		model.addAttribute("bcXuLyPAList", bcXuLyPAList);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nguongChonNguyenNhan", nguongChonNguyenNhan);
		
		return "jspfeedback/bcXuLyPhanAnhList";
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
