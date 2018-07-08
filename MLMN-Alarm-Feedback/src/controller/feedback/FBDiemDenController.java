package controller.feedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlMonitorSite;
import vo.CTableConfig;
import vo.FbProcess;
import vo.FbType;
import vo.HProvinceFb;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.HProvinceFbDAO;
import dao.SYS_PARAMETERDAO;
import dao.SmsContentsDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;

/**
 * Function        : Quan ly danh sach FB diem den
 * Created By      : AnhCTV
 * Create Date     : 14/06/2016
 * Modified By     : 
 * Modify Date     :
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/feedback/diemden/*")
public class FBDiemDenController  extends BaseController{

	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
 	@Autowired
 	private FbProcessDAO fbProcessDAO;
 	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
 	@Autowired 
	private SysUsersDAO  sysUserDao;
 	@Autowired 
	private SysUserAreaDAO sysUserAreaDao;;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
 	private FbTypeDAO FbTypeDAO;
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
 	
 	
 	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
 	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
 	
 	@RequestMapping(value = "list")
 	public ModelAndView list(@RequestParam(required = false) String thoiGianPAFrom,
 							 @RequestParam(required = false) String thoiGianPATo,
 							 @RequestParam(required = false) String thoiGianXLFrom,
 							 @RequestParam(required = false) String thoiGianXLTo,
 							 @RequestParam(required = false) String loaiPA,
 							 @RequestParam(required = false) String deptProcess,
 							 @RequestParam(required = false) String team,
 							 @RequestParam(required = false) String status,
 							 ModelMap model, HttpServletRequest request) {
 		
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
		
		if (thoiGianXLFrom != null && !thoiGianXLFrom.equals("") && !DateTools.isValid("dd/MM/yyyy", thoiGianXLFrom)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -8);
			
			thoiGianXLFrom = df.format(cal.getTime());
		}
		
		if (thoiGianXLTo != null && !thoiGianXLTo.equals("")&& !DateTools.isValid("dd/MM/yyyy", thoiGianXLTo)) {
			thoiGianXLTo = df.format(new Date());
		}
		
		if (thoiGianXLFrom == null) {
			thoiGianXLFrom = "";
		}
		
		if (thoiGianXLTo == null ) {
			thoiGianXLTo = "";
		}
		
		if (loaiPA!=null)
		{
			loaiPA=loaiPA.trim();
		}
		else
		{
			loaiPA="";
		}
		if (deptProcess!=null)
		{
			deptProcess=deptProcess.trim();
		}
		else
		{
			deptProcess="";
		}
		if (team!=null)
		{
			team=team.trim();
		}
		else
		{
			team="";
		}
		if (status!=null)
		{
			status=status.trim();
		}
		else
		{
			status="";
		}
		// Lay ten file export
 		Calendar currentDate = Calendar.getInstance();	

 		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
 		String dateNow = formatter.format(currentDate.getTime());
 		String exportFileName = "DSDiemDen_" + dateNow;
 		model.addAttribute("exportFileName", exportFileName);
		 		
 		// Phong/Dai
 		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("deptProcessList", deptProcessList);
		
 		// To VT
 		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcess);
 		model.addAttribute("teamList", teamList);
 		
 		List<FbType> loaiPAList = FbTypeDAO.getFBTypeList();
 		model.addAttribute("loaiPAList", loaiPAList);
 			
 		List<SYS_PARAMETER> sysParameterByCodeList = fbProcessDAO.getQLTTFBList();//sysParameterDao.getSysParameterByCode("STATUS_OF_CENTRAL_FEEDBACK");
 		model.addAttribute("QLTTFBList", sysParameterByCodeList);		


 		/*Begin Grid*/
		String nameTable="FB_PROCESS";
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(nameTable);
		//url
		String url = "data.htm?loaiPA="+loaiPA+
				"&thoiGianPAFrom="+thoiGianPAFrom+"&thoiGianPATo="+thoiGianPATo+
				"&thoiGianXLFrom="+thoiGianXLFrom+"&thoiGianXLTo="+thoiGianXLTo+
				"&deptProcess="+deptProcess+"&team="+team+
				"&status="+status;
		//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport);	
		
		/*End Grid*/	
		
		model.addAttribute("thoiGianPAFrom", thoiGianPAFrom);
 		model.addAttribute("thoiGianPATo", thoiGianPATo);
 		model.addAttribute("thoiGianXLFrom", thoiGianXLFrom);
 		model.addAttribute("thoiGianXLTo", thoiGianXLTo);
		model.addAttribute("loaiPA", loaiPA);
		model.addAttribute("deptProcess", deptProcess);
		model.addAttribute("team", team);
		model.addAttribute("status", status);	
		
 		return new ModelAndView("jspfeedback/diemDenList");

 	}
 	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String loaiPA,
			@RequestParam(required = false) String thoiGianPAFrom,
			@RequestParam(required = false) String thoiGianPATo,
			@RequestParam(required = false) String thoiGianXLFrom,
			@RequestParam(required = false) String thoiGianXLTo,
			@RequestParam(required = false) String deptProcess,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String status,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<FbProcess> recordList = new ArrayList<FbProcess>();
		recordList = fbProcessDAO.getFeedbackDiemDen(loaiPA, thoiGianPAFrom,
 				thoiGianPATo, thoiGianXLFrom, thoiGianXLTo, deptProcess, team, status);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(recordList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }

 	@RequestMapping("export")
 	public String export(@RequestParam(required = false) String loaiPA,
			@RequestParam(required = false) String thoiGianPAFrom,
			@RequestParam(required = false) String thoiGianPATo,
			@RequestParam(required = false) String thoiGianXLFrom,
			@RequestParam(required = false) String thoiGianXLTo,
			@RequestParam(required = false) String deptProcess,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String status,
 			HttpServletRequest request, HttpServletResponse response) throws Exception {
 		
 		if (loaiPA==null)
 			loaiPA = "";
 		if (deptProcess==null)
 			deptProcess = "";
 		if (team==null)
 			team = "";
 		if (thoiGianPAFrom==null)
 			thoiGianPAFrom = "";
 		if (thoiGianPATo==null)
 			thoiGianPATo="";
 		if (status==null)
 			status = "";
 		if (thoiGianXLFrom==null)
 			thoiGianXLFrom = "";
 		if (thoiGianXLTo==null)
 			thoiGianXLTo = "";
 		
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
 		paramList.add(loaiPA);
 		paramList.add(thoiGianPAFrom);
 		paramList.add(thoiGianPATo);
 		paramList.add(thoiGianXLFrom);
 		paramList.add(thoiGianXLTo);
 		paramList.add(deptProcess);
 		paramList.add(team);
 		paramList.add(status);
 		
 		String tableName="FB_PROCESS";
 		// Lay ten file export
 		Calendar currentDate = Calendar.getInstance();
 		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
 		String dateNow = formatter.format(currentDate.getTime());
 		String exportFileName= "DS_DiemDen_"+ dateNow;
 		
 		List<CTableConfig> columnList = cTableConfigDAO.getColumnList(tableName);
 		ExportTools.exportToExcel("PK_FB_PROCESS_MLMN.GET_FEEDBACK_DIEM_DEN(?, ?, ?, ?, ?, ?, ?, ?, ?)",paramList,tempFile,exportFileName, tableName,"DANH SÁCH PHẢN ÁNH ĐIỂM ĐEN",columnList,"Y");
 		
 		response.setContentType("application/ms-excel");
 		response.setContentLength((int) tempFile.length());
 		response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "." + ext + "\"");
 		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
 		tempFile.delete();
 		
 		return null;
 	}
}
