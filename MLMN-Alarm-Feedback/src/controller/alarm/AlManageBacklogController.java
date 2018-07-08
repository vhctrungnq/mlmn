package controller.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.AlManageBacklog;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.MDepartment;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import controller.BaseController;

import dao.AlManageBacklogDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.SysUsersDAO;

/**
 * Function        : Quan ly su co ton dong
 * Created By      : BUIQUANG
 * Create Date     : 15/12/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-manage-backlog/*")
public class AlManageBacklogController extends BaseController{

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private AlManageBacklogDAO alManageBacklogDAO;
	@Autowired
	private MDepartmentDAO mDepartmentDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String work,
			@RequestParam(required = false) String team, 	   
    		@ModelAttribute("filter") AlManageBacklog filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers sysUser =  sysUsersDao.selectByPrimaryKey(username);
		String teamOfUser = sysUser.getMaPhong();
		if (team==null){
			team = teamOfUser;
		}
		boolean checkRegion= false;
		if (team!=null && team.equalsIgnoreCase(teamOfUser)){
			checkRegion = true;
		}
		model.addAttribute("checkRegion", checkRegion);
		
		String filterUrl = "";
		String temp = "";
		if(work != null){
			filterUrl += temp + "work=" + work.trim(); 
			temp = "&";
		}
		if(team != null){
			filterUrl += temp + "team=" + team.trim(); 
			temp = "&";
		}
		if(teamOfUser != null){
			filterUrl += temp + "teamOfUser=" + teamOfUser.trim(); 
			temp = "&";
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MANAGE_BACKLOG");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MANAGE_BACKLOG");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "multiplerowsextended", null);
		model.addAttribute("gridManage", gridManage);
		
		model.addAttribute("work", work);
		model.addAttribute("team", team);
		model.addAttribute("teamOfUser", teamOfUser);
		return new ModelAndView("jspalarm/diary/alManageBacklogList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String work,
			@RequestParam(required = false) String team, 
			@RequestParam(required = false) String teamOfUser, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageBacklog> alManageBacklog = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		if(request.getParameter("sortorder") != null)
			sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = null;
		if(sortfield != null && !sortfield.equals(""))
			columnList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_BACKLOG", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_BACKLOG", null);
		
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: tableConfigList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		
		// Sap xep
		if(columnList != null)
			for(CTableConfig column: columnList)
			{
				sortfield = column.getTableColumn();
				break;
			}
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		try
		{
			alManageBacklog = alManageBacklogDAO.getAlManageBacklogFilter(work, team, teamOfUser, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = alManageBacklogDAO.countAlManageBacklogFilter(work, team, teamOfUser, strWhere);
		}
		catch(Exception exp)
		{
		}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strWhere = strWhere.replace("%", "___");
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
	    strjson += "{\"Rows\":" ;
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageBacklog);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			alManageBacklogDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		AlManageBacklog alManageBacklog = (id == null) ? new AlManageBacklog() : alManageBacklogDAO.selectByPrimaryKey(Integer.parseInt(id));
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		
		alManageBacklogAddEdit( id, model);
		
		if(id != null && id != ""){
			if(alManageBacklog.getStartDate() != null)
				model.addAttribute("startDate", df.format(alManageBacklog.getStartDate()));
			if(alManageBacklog.getEndDate() != null)
				model.addAttribute("endDate", df.format(alManageBacklog.getEndDate()));	
			model.addAttribute("team", alManageBacklog.getTeam());
		}
		else{
			alManageBacklog.setTeam(userLogin.getMaPhong());
			model.addAttribute("startDate", df.format(new Date()));
		}
		model.addAttribute("alManageBacklog", alManageBacklog);
		return "jspalarm/diary/alManageBacklogForm";
	}
	
	private void alManageBacklogAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("alManageBacklogAddEdit", "Y");
		else
			model.addAttribute("alManageBacklogAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@ModelAttribute("alManageBacklog") @Valid AlManageBacklog alManageBacklog, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		//Ném lỗi
		if (result.hasErrors()) {
			alManageBacklogAddEdit( id, model);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("team", alManageBacklog.getTeam());
			if(result.hasFieldErrors("startDate"))
				model.addAttribute("startDateError", "startDateError");
			if(result.hasFieldErrors("endDate"))
				model.addAttribute("endDateError", "endDateError");
			
			return "jspalarm/diary/alManageBacklogForm";
		}
		
		if(startDate != null && !startDate.equals(""))
			alManageBacklog.setStartDate(df.parse(startDate));
		if(endDate != null && !endDate.equals(""))
			alManageBacklog.setEndDate(df.parse(endDate));
		
		if(id.equals(""))
		{
			//if(alManageBacklogDAO.checkWorkBacklogUk(alManageBacklog.getWork(), null).size() == 0){
				alManageBacklog.setCreatedBy(username);
				alManageBacklog.setTeam(userLogin.getMaPhong());
				alManageBacklogDAO.insert(alManageBacklog);
				
				saveMessageKey(request, "messsage.confirm.addsuccess");
			/*}
			else{
				alManageBacklogAddEdit( id, model);
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				saveMessageKey(request, "messsage.confirm.alManageBacklog.checkUniqueKey");
				return "jspalarm/diary/alManageBacklogForm";
			}*/
		}
		else{
			//if(alManageBacklogDAO.checkWorkBacklogUk(alManageBacklog.getWork(), id).size() == 0){
				alManageBacklog.setModifiedBy(username);
				alManageBacklog.setModifyDate(new Date());
				
				alManageBacklogDAO.updateByPrimaryKeySelective(alManageBacklog);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			/*}
			else{
				alManageBacklogAddEdit( id, model);
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				saveMessageKey(request, "messsage.confirm.alManageBacklog.checkUniqueKey");
				return "jspalarm/diary/alManageBacklogForm";
			}*/
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String work,
			@RequestParam(required = false) String team, 	
			@RequestParam(required = false) String teamOfUser, 
  			@RequestParam(required = false) String sortfield,
  			@RequestParam(required = false) String sortorder,
  			@RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		strWhere = strWhere.replace("___", "%");
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		
		List<AlManageBacklog> alManageBacklog = alManageBacklogDAO.getAlManageBacklogFilter(work, team, teamOfUser, null, null, sortfield, sortorder, strWhere);
		exportReport(tempFile, alManageBacklog);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "AlManageBacklog_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<AlManageBacklog> alManageBacklog) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Backlog", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
		 
			String strHeader = "Công việc,Phần tử mạng,Tình trạng xử lý,Bắt đầu,Kết thúc,Đơn vị xử lý,Cấu hình trước khi thay đổi,Cấu hình sau khi thay đổi,Nguyên nhân,Ghi chú";
			String[] arrayHeader = strHeader.split(",");
			for(int k = 0; k < arrayHeader.length; k++)
			{
				Label label0 = new Label(k, 0, arrayHeader[k], cellFormatHeader);
				sheet.addCell(label0);
			 
			}
			 
			int i = 1;
			
			for (AlManageBacklog record : alManageBacklog) {
				int j=0;
				Label record0 = new Label(j, i, record.getWork(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record6 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record1 = new Label(j, i,record.getProcess(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getStartDateStr(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getEndDateStr(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record7 = new Label(j, i, record.getPreviousConfig(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getLaterConfig(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getCauseby(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record5 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record5);
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jspalarm/diary/alManageBacklogUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspalarm/diary/alManageBacklogUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers sysUser =  sysUsersDao.selectByPrimaryKey(createdBy);
		
		List<AlManageBacklog> successList = new ArrayList<AlManageBacklog>();
		List<AlManageBacklog> failedList = new ArrayList<AlManageBacklog>();
		List<AlManageBacklog> success = new ArrayList<AlManageBacklog>();
		
		String work;
		String process;
		String startDate;
		String endDate;
		String team;
		String description;
		String ne, previousConfig, laterConfig, causeby;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 10) {
        		saveMessageKey(request, "sidebar.admin.alManageBacklogUploadErrorStructuresNumber");
        		
        		return "jspalarm/diary/alManageBacklogUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AlManageBacklog alManageBacklog;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			alManageBacklog = new AlManageBacklog();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=10; j++) {
     					data.add("");
     				}
        			work			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			ne				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			process			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			startDate		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			endDate			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			team			= data.get(5).toString().trim().equals("")?sysUser.getMaPhong():data.get(5).toString().trim();
        			previousConfig	= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			laterConfig		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			causeby			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			description		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			
        			
        			// Kiem tra loi
        			if (work == null || team == null
        					//|| (work!= null && alManageBacklogDAO.checkWorkBacklogUk(work, null).size() == 0)
        					|| (team != null &&( mDepartmentDao.getVMDepartmentBacklog(team).size() == 0||!sysUser.getMaPhong().equalsIgnoreCase(team)))
						) {
						error = true;
					}
        			
        			
        			try
	    			{
        				if(startDate != null){
        					alManageBacklog.setStartDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startDate));
	    				}
        				if(endDate != null){
        					alManageBacklog.setEndDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(endDate));
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			alManageBacklog.setWork(work);
        			alManageBacklog.setProcess(process);
        			alManageBacklog.setTeam(team);
        			alManageBacklog.setDescription(description);
        			alManageBacklog.setNe(ne);
        			alManageBacklog.setPreviousConfig(previousConfig);
        			alManageBacklog.setLaterConfig(laterConfig);
        			alManageBacklog.setCauseby(causeby);
        			if (work == null && team == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(alManageBacklog);
    					} else  {
    						
    						successList.add(alManageBacklog);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AlManageBacklog record: successList) {
			try {	
				//if(alManageBacklogDAO.checkWorkBacklogUk(record.getWork(), null).size() == 0){
						record.setCreatedBy(createdBy);
						alManageBacklogDAO.insert(record);
					/*}else{
						record.setModifiedBy(createdBy);
						alManageBacklogDAO.updateByUniqueKey(record);
					}*/
					success.add(record);
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu sự cố tồn đọng không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác hoặc thông tin đơn vị xử lý không được phép nhập");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspalarm/diary/alManageBacklogUpload";
	}
	
	// Lay danh sach don vi xu ly
  	@RequestMapping("teamList")
  	public @ResponseBody 
  	List<MDepartment> teamList(HttpServletRequest request) {
  		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers sysUser =  sysUsersDao.selectByPrimaryKey(username);
		String teamOfUser = sysUser.getTeam();
  		
  		List<MDepartment> teamList = mDepartmentDao.getVMDepartmentBacklog(teamOfUser);
  		
  		return teamList;
  	 }
}
