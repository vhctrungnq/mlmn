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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.FbProcessDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;

import vo.FbProcess;
import vo.SYS_PARAMETER;
import vo.SysUserArea;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.Helper;
/**
 * Function        : Bao cao so luong phan anh theo nguyen nhan
 * Created By      : BUIQUANG
 * Create Date     : 
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/so-luong-pa-theo-nguyen-nhan/*")
public class SlPaTheoNguyenNhanController {

	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO  sysUserDao;
 	@Autowired 
	private SysUserAreaDAO sysUserAreaDao; 
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value="list")
    public String list( @RequestParam(required = false) String startDate,
    					@RequestParam(required = false) String fbSendTelecom,
    					@RequestParam(required = false) String fbDistrict,
					    @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SLPATheoNN_" + dateNow;
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
		
		List<FbProcess> slFbTheoNN = fbProcessDAO.getSlFbTheoNguyenNhan(startDate, endDate, fbSendTelecom, fbDistrict);
		model.addAttribute("soLuongFBList", slFbTheoNN);
				
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);	
		
		model.addAttribute("statusList", statusList);	
		model.addAttribute("fbSendTelecom", fbSendTelecom);	 
		model.addAttribute("Center",hp.getProperties("center"));
		return "jspfeedback/baocao/slPaTheoNguyenNhan";
	}
}
