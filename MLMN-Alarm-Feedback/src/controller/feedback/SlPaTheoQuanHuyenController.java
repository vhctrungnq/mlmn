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
import org.springframework.web.bind.annotation.ResponseBody;

import vo.FbProcess;
import vo.HProvinceFb;
import vo.SYS_PARAMETER;
import vo.SysUserArea;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.Helper;
import vo.alarm_site.ReportFeedbackDay;

import dao.FbProcessDAO;
import dao.HProvinceFbDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
/**
 * Function        : Bao cao so luong phan anh theo quan huyen
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/so-luong-pa-theo-quan-huyen/*")
public class SlPaTheoQuanHuyenController {

	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
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
					   @PathVariable String function, 
					   @RequestParam(required = false) String fbSendTelecom,
					  
					   @RequestParam(required = false) String province,
					   @RequestParam(required = false) String district, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SLPATheoLoaiPA_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<HProvinceFb> tinhThanhList = hProvinceFbDAO.distinctProvinceCode();
 		model.addAttribute("tinhThanhList", tinhThanhList);
		
		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.getDistrictCodeList(province);
 		model.addAttribute("quanHuyenList", quanHuyenList);
		
 		// Lay ten tt can hien thi
 		Helper hp = new Helper();
 		 
 			
 			// Phan quyen FeedBack 
 	 		// User thuong: load fb theo tinh va nhung fb da duoc check gui dai vien thong
 	 		// User Quan tri: load tat ca
 	 			String username = SecurityContextHolder.getContext().getAuthentication().getName();
 	 			List<SysUsers> userList = sysUserDao.checkRole(username); 
 	 			if(fbSendTelecom==null)
 	 				fbSendTelecom="";
 	 			if(Helper.checkRole2 (userList, model).equals("N"))
 	 			{
 	 				if(district==null || district.equals(""))
 	 				{
 	 					List<SysUserArea> districtList = sysUserAreaDao.getAreaByUsername(username);
 	 					for(int i=0; i< districtList.size();i++)
 	 						district += districtList.get(i).getDistrict()+",";
 	 					district =  district.substring(0,district.length()-1);
 	 					
 	 				}
 	 				fbSendTelecom = "Y";
 	 			}
 	 			// Load status gui dai vt 
 	 			List<SYS_PARAMETER> statusList = sysParameterDao.getSysParameterByCode("FEEDBACK_COMBOBOX_SEND_TELECOM" );
 	 			
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
		if(function.equals("tong-hop"))
		{
			List<ReportFeedbackDay> vRFbProcessDayList = fbProcessDAO.getReportByDistrict(province, district, startDate, endDate,fbSendTelecom);
			model.addAttribute("soLuongFBList", vRFbProcessDayList);
		}
		if(function.equals("chi-tiet"))
		{
		 
			List<FbProcess> slFbTheoQuanHuyen = fbProcessDAO.getSlFbTheoQuanHuyen(startDate, endDate, province, district,fbSendTelecom);
			model.addAttribute("soLuongFBList", slFbTheoQuanHuyen);
		}

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("provinceCBB", province);
		model.addAttribute("districtCBB", district);
		
		model.addAttribute("statusList", statusList);	
		model.addAttribute("fbSendTelecom", fbSendTelecom);	 
		model.addAttribute("Center",hp.getProperties("center"));
		return "jspfeedback/baocao/slPaTheoQuanHuyen";
	}
	
	// Lay danh sach quan huyen theo tinh thanh pho
   	@RequestMapping("loadQuanHuyen")
   	public @ResponseBody 
   	List<HProvinceFb> loadQuanHuyen(@RequestParam(required = false) String province, HttpServletRequest request, HttpServletResponse response) {
   		List<HProvinceFb> quanHuyenList = hProvinceFbDAO.getDistrictCodeList(province);
   		return quanHuyenList;
   	}
}
