package controller.feedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.FbProcessDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;

import vo.SYS_PARAMETER;
import vo.SysUserArea;
import vo.SysUsers;
import vo.VRFbProcessDay;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.Helper;
import vo.alarm_site.ReportFeedbackDay;
/**
 * Function        : Bao cao so luong phan anh theo ngay gom tab chi tiet va tong hop
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/so-luong-pa-theo-ngay/*")
public class SlPaTheoNgayController {
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO  sysUserDao;
 	@Autowired 
	private SysUserAreaDAO sysUserAreaDao; 
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @RequestParam(required = false) String date,
					   @RequestParam(required = false) String rpType, 
					   @RequestParam(required = false) String fbSendTelecom,
					   @RequestParam(required = false) String fbDistrict,
					   @PathVariable String function, 
					   @RequestParam(required = false) String fbType, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SLPATheoNgay_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<SYS_PARAMETER> loaiBCList =  sysParameterDao.getLoaiBaoCaoList();
		model.addAttribute("loaiBCList", loaiBCList);
		
		if(date != null ){
			startDate = date;
			endDate = date;
		}
		// Lay ten tt can hien thi
		Helper hp = new Helper();
	 
		
		// Phan quyen FeedBack 
 		// User thuong: load fb theo tinh va nhung fb da duoc check gui dai vien thong
 		// User Quan tri: load tat ca
 			String username = SecurityContextHolder.getContext().getAuthentication().getName();
 			List<SysUsers> userList = sysUserDao.checkRole(username);
 			String district = "";
 			if(fbSendTelecom==null)
 				fbSendTelecom="";
 			if(Helper.checkRole2 (userList, model).equals("N"))
 			{
 				if(fbDistrict==null || fbDistrict.equals(""))
 				{
 					List<SysUserArea> districtList = sysUserAreaDao.getAreaByUsername(username);
 					for(int i=0; i< districtList.size();i++)
 						district += districtList.get(i).getDistrict()+",";
 					if (district.length()>1)
 						fbDistrict =  district.substring(0,district.length()-1);
 					
 				}
 				fbSendTelecom = "Y";
 			}
 			// Load status gui dai vt 
 			List<SYS_PARAMETER> statusList = sysParameterDao.getSysParameterByCode("FEEDBACK_COMBOBOX_SEND_TELECOM" );
		// Ngay thang
		if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false) {			

			startDate = df_full.format(DateTools.startMonth(new Date()));
		
		}
		if (endDate == null || endDate.equals("") || DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df_full.format(cal.getTime());
		}
		
		if(function.equals("tong-hop"))
		{
			List<VRFbProcessDay> vRFbProcessDayList = fbProcessDAO.getVRFbProcessDay(startDate, endDate);
			model.addAttribute("soLuongFBList", vRFbProcessDayList);
		}
		else if(function.equals("chi-tiet")){
			if(rpType == null || rpType == ""){
				List<ReportFeedbackDay> reportFbTypeList = fbProcessDAO.getReportByFbType(startDate, endDate, fbSendTelecom, fbDistrict);
				model.addAttribute("reportFbTypeList", reportFbTypeList);
			
				List<ReportFeedbackDay> reportDepartmentList = fbProcessDAO.getReportByDepartment(null, null, startDate, endDate, fbSendTelecom, fbDistrict);
				model.addAttribute("reportDepartmentList", reportDepartmentList);
			
				List<ReportFeedbackDay> reportDistrictList = fbProcessDAO.getReportByDistrict(null, fbDistrict, startDate, endDate, fbSendTelecom);
				model.addAttribute("reportDistrictList", reportDistrictList);
			
				List<ReportFeedbackDay> reportCausedbyList = fbProcessDAO.getReportByCausedby(startDate, endDate, fbSendTelecom, fbDistrict);
				model.addAttribute("reportCausedbyList", reportCausedbyList);
			}
			else{
				if(rpType.equals("1")){
					List<ReportFeedbackDay> reportFbTypeList = fbProcessDAO.getReportByFbType(startDate, endDate, fbSendTelecom, fbDistrict);
					model.addAttribute("reportFbTypeList", reportFbTypeList);
				}
				else if(rpType.equals("2")){
					List<ReportFeedbackDay> reportDepartmentList = fbProcessDAO.getReportByDepartment(null, null, startDate, endDate, fbSendTelecom, fbDistrict);
					model.addAttribute("reportDepartmentList", reportDepartmentList);
				}
				else if(rpType.equals("3")){
					List<ReportFeedbackDay> reportDistrictList = fbProcessDAO.getReportByDistrict(null, fbDistrict, startDate, endDate, fbSendTelecom);
					model.addAttribute("reportDistrictList", reportDistrictList);
				}
				else if(rpType.equals("4")){
					List<ReportFeedbackDay> reportCausedbyList = fbProcessDAO.getReportByCausedby(startDate, endDate, fbSendTelecom, fbDistrict);
					model.addAttribute("reportCausedbyList", reportCausedbyList);
				}
			}
			
		}
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("loaiBCCBB", rpType);
		
		model.addAttribute("statusList", statusList);	
		model.addAttribute("fbSendTelecom", fbSendTelecom);	
		model.addAttribute("fbDistrict", fbDistrict);	
		model.addAttribute("Center",hp.getProperties("center"));
		return "jspfeedback/baocao/slPaTheoNgay";
	}
}
