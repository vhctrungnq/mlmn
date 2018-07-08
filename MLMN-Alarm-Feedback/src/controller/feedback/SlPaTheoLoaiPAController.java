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

import vo.FbProcess;
import vo.FbType;
import vo.SYS_PARAMETER;
import vo.SysUserArea;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.Helper;
import vo.alarm_site.ReportFeedbackDay;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
/**
 * Function        : Bao cao so luong phan anh theo loai phan anh
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/so-luong-pa-theo-loai-pa/*")
public class SlPaTheoLoaiPAController {

	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired
 	private FbTypeDAO FbTypeDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
 	@Autowired 
	private SysUserAreaDAO sysUserAreaDao;
 	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @RequestParam(required = false) String fbSendTelecom, 
					   @RequestParam(required = false) String fbDistrict,
					   @PathVariable String function, 
					   @RequestParam(required = false) String fbType, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SLPATheoLoaiPA_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
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
 					fbDistrict =  district.substring(0,district.length()-1);
 					
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
			List<ReportFeedbackDay> vRFbProcessDayList = fbProcessDAO.getReportByFbType(startDate, endDate,fbSendTelecom, fbDistrict);
			model.addAttribute("soLuongFBList", vRFbProcessDayList);
		}
		if(function.equals("chi-tiet"))
		{
			if(fbType == null)
				fbType = "";
			List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
	 		model.addAttribute("loaiPAList", loaiPAList);
	 		
	 		for(int i = 0 ; i < loaiPAList.size(); i++)
	 		{
	 			String s1 = loaiPAList.get(i).getName().trim();
	 			String s2 = fbType.trim();
	 			if(s1.equals(s2))
	 			{
	 				fbType = loaiPAList.get(i).getCode();
	 				break;
	 			}
	 		}
			List<FbProcess> slFbTheoLoaiPA = fbProcessDAO.getSlFbTheoLoaiPA(startDate, endDate, fbType,fbSendTelecom, fbDistrict);
			model.addAttribute("soLuongFBList", slFbTheoLoaiPA);
		}

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("loaiPACBB", fbType);
		model.addAttribute("statusList", statusList);	
		
		model.addAttribute("fbSendTelecom", fbSendTelecom);	
		model.addAttribute("fbDistrict", fbDistrict);	
		String center = hp.getProperties("center");
		model.addAttribute("Center",center);
		
		
		return "jspfeedback/baocao/slPaTheoLoaiPa";
	}
}
