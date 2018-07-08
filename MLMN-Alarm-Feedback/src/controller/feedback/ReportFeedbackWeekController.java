package controller.feedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import vo.HProvinceFb;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.Helper;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.HProvinceFbDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
/**
 * Function        : Bao cao so luong phan anh trong tuan
 * Created By      : BUIQUANG
 * Create Date     : 1/1/2014
 * Modified By     : 
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/bao-cao-feedback-tuan/*")
public class ReportFeedbackWeekController {

	@Autowired
 	private FbTypeDAO FbTypeDAO;
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
 	@Autowired 
	private SysUserAreaDAO sysUserAreaDao;
 	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @RequestParam(required = false) String fbSendTelecom,  
					   @PathVariable String function, 
					   @RequestParam(required = false) String fbType, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "FeedbackTuan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		Integer totalFb = 0;
		// Lay ten tt can hien thi
				Helper hp = new Helper();
			 
				
				// Phan quyen FeedBack 
		 		// User thuong: load fb theo tinh va nhung fb da duoc check gui dai vien thong
		 		// User Quan tri: load tat ca
		 			String username = "";
		 			List<SysUsers> userList = sysUserDao.checkRole(SecurityContextHolder.getContext().getAuthentication().getName()); 
		 			if(fbSendTelecom==null)
		 				fbSendTelecom="";
		 			if(Helper.checkRole2 (userList, model).equals("N"))
		 			{
		 				username = SecurityContextHolder.getContext().getAuthentication().getName();
		 				fbSendTelecom = "Y";
		 			}
		 			// Load status gui dai vt 
		 			List<SYS_PARAMETER> statusList = sysParameterDao.getSysParameterByCode("FEEDBACK_COMBOBOX_SEND_TELECOM" );
		 			
		// Ngay thang
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			endDate = df.format(cal.getTime());
		}
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			cal.add(Calendar.DAY_OF_MONTH, -8);
			startDate = df.format(cal.getTime());
		}
		
		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 		
 		// Ve bieu do
 		String script = "";
 		List<String> categories = new ArrayList<String>();
 		List<HProvinceFb> hProvinceFbList = hProvinceFbDAO.getProvinceForFbWeekList();
 		for(HProvinceFb record : hProvinceFbList){
			categories.add(record.getProvince());
		}
 		
 		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
 		if(fbType != null && !fbType.equals("")){
 			List<Float> seriesList = new ArrayList<Float>();
 			List<HProvinceFb> dataByFbTypeList = hProvinceFbDAO.getDataForFeedbackWeek(startDate, endDate, fbType,fbSendTelecom, username);
 			
 			for(int j=0;j<dataByFbTypeList.size();j++){
					seriesList.add(Float.parseFloat(dataByFbTypeList.get(j).getQty().toString()));
 			}
 			String fbName = "";
 			for(FbType column: loaiPAList)
 			{
 				if(fbType.equals(column.getCode()))
 				{
 					fbName = column.getName();
 					break;
 				}
 			}
 			
 			series.put(fbName, seriesList);
 		}
 		else{
 			
 			List<FbType> fbTypeForFbWeekList = FbTypeDAO.getFbTypeForFbWeekList(startDate, endDate);
 			for(int i=0;i<fbTypeForFbWeekList.size();i++){
 				List<HProvinceFb> dataByFbTypeList = hProvinceFbDAO.getDataForFeedbackWeek(startDate, endDate, fbTypeForFbWeekList.get(i).getCode(),fbSendTelecom, username);
 				
 				List<Float> seriesList = new ArrayList<Float>();
 				for(int j=0;j<dataByFbTypeList.size();j++){
 					seriesList.add(Float.parseFloat(dataByFbTypeList.get(j).getQty().toString()));
 					
 				}
 				series.put(fbTypeForFbWeekList.get(i).getName(), seriesList);
 	 		}
 		}
 		
 		script = Chart.columnBasicForFeedback("chartFeedbackWeek", "", categories, series);
 		model.addAttribute("chartdivScript", script);
 		
 		List<FbProcess> gridWeekList = fbProcessDAO.getGridForFeedbackWeek(startDate, endDate, fbType,fbSendTelecom, username);
 		model.addAttribute("gridWeekList", gridWeekList);
 		for(int i=0;i<gridWeekList.size();i++)
 			totalFb += Integer.parseInt(gridWeekList.get(i).getQty());
 		
 		
 		model.addAttribute("totalFb", totalFb);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("loaiPACBB", fbType);
		
		model.addAttribute("statusList", statusList);	
		model.addAttribute("fbSendTelecom", fbSendTelecom);	 
		String center = hp.getProperties("center");
		model.addAttribute("Center",center);
		return "jspfeedback/baocao/feedbackWeekList";
	}
}
