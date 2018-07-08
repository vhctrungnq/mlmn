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

import com.google.gson.Gson;

import controller.BaseController;

import vo.AlManageCrCdd;
import vo.AlManageOnAir;
import vo.AlManageProject;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.HBsc;
import vo.SYS_PARAMETER;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import dao.AlManageCrCddDAO;
import dao.AlManageOnAirDAO;
import dao.AlManageProjectDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.HBscDAO;
import dao.SYS_PARAMETERDAO;

/**
 * Function        : Quan ly lich phat song
 * Created By      : BUIQUANG
 * Create Date     : 25/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-manage-on-air/*")
public class AlManageOnAirController extends BaseController{

	@Autowired
	private AlManageOnAirDAO alManageOnAirDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private AlManageProjectDAO alManageProjectDAO;
	@Autowired
	private HBscDAO hBscDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private AlManageCrCddDAO alManageCrCddDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
    		@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String network, 	   
			@RequestParam(required = false) String ne,
			@RequestParam(required = false) String siteid,
    		@ModelAttribute("filter") AlManageOnAir filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String filterUrl = "";
		String temp = "";
		if(projectCode != null){
			filterUrl += temp + "projectCode=" + projectCode.trim(); 
			temp = "&";
		}
		if(network != null){
			filterUrl += temp + "network=" + network.trim(); 
		}
		if(ne != null){
			filterUrl += temp + "ne=" + ne.trim(); 
		}
		if(siteid != null){
			filterUrl += temp + "siteid=" + siteid.trim(); 
			temp = "&";
		}
		
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MANAGE_ON_AIR");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MANAGE_ON_AIR");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
		model.addAttribute("gridManage", gridManage);
		
		model.addAttribute("projectCode", projectCode);
		model.addAttribute("network", network);
		model.addAttribute("neName", ne);
		model.addAttribute("siteid", siteid);
		
		return new ModelAndView("jspalarm/alProject/alManageOnAirList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String network, 	   
			@RequestParam(required = false) String ne,
			@RequestParam(required = false) String siteid,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageOnAir> alManageOnAir = null;
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
		List<CTableConfig> columnList =null;
		if(sortfield != null && !sortfield.equals(""))
			columnList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_ON_AIR", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_ON_AIR", null);
		
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
			alManageOnAir = alManageOnAirDAO.getAlManageOnAirFilter(projectCode, network, ne, siteid, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = alManageOnAirDAO.countAlManageOnAirFilter(projectCode, network, ne, siteid, strWhere);
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
		String jsonCartList = gs.toJson(alManageOnAir);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, 
			@RequestParam(required = false) String projectCode,
			HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			alManageOnAirDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		if(projectCode != null && !projectCode.equals("")){
				return "redirect:list.htm?projectCode="+projectCode;
		}	
		else
			return "redirect:list.htm";
	}
	
	private void loadData(ModelMap model){
		List<AlManageProject> alManageProjectList = alManageProjectDAO.getProjectUnfinishedList("ON_AIR");
		model.addAttribute("projectIdList", alManageProjectList);
		
		List<SYS_PARAMETER> networkForOnAirList = sysParameterDao.getNetworkForOnAir();
		model.addAttribute("networkList", networkForOnAirList);
		
		// Danh sach goi nho bscid
		List<HBsc> bscList = hBscDao.getBscidList();
		String bscArray="var bscList = new Array(";
		String cnId="";
		for (int i=0;i<bscList.size();i++) {
			bscArray = bscArray + cnId +"\""+bscList.get(i).getBscid()+"\"";
			cnId=",";
		}
		bscArray = bscArray+");";
		model.addAttribute("bscList", bscArray);
		
		// Danh sach goi nho rncid
		List<HBsc> rncList = hBscDao.getRncidList();
		String rncArray="var rncList = new Array(";
		String cn1Id="";
		for (int i=0;i<rncList.size();i++) {
			rncArray = rncArray + cn1Id +"\""+rncList.get(i).getBscid()+"\"";
			cn1Id=",";
		}
		rncArray = rncArray+");";
		model.addAttribute("rncList", rncArray);
		
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String crDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		AlManageOnAir alManageOnAir = (id == null) ? new AlManageOnAir() : alManageOnAirDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("alManageOnAir", alManageOnAir);
		alManageOnAirAddEdit( id, model);
		loadData(model);
		
		if(id != null && id != ""){
			if(alManageOnAir.getIntNe() != null)
				model.addAttribute("intNe", df.format(alManageOnAir.getIntNe()));
			if(alManageOnAir.getOnAir() != null)
				model.addAttribute("onAir", df.format(alManageOnAir.getOnAir()));
			
			model.addAttribute("projectIdCBB", alManageOnAir.getProjectId());
			model.addAttribute("networkCBB", alManageOnAir.getNetwork());
			model.addAttribute("neName", alManageOnAir.getNe());
			model.addAttribute("cddName", alManageOnAir.getCddName());
		}
		else{
			
		}
		model.addAttribute("projectCode", projectCode);
		return "jspalarm/alProject/alManageOnAirForm";
	}
	
	private void alManageOnAirAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("alManageOnAirAddEdit", "Y");
		else
			model.addAttribute("alManageOnAirAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String intNe,
			@RequestParam(required = false) String onAir,
			@RequestParam(required = false) String projectCode,
			@ModelAttribute("alManageOnAir") @Valid AlManageOnAir alManageOnAir, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//Ném lỗi
		if (result.hasErrors()) {
			loadData(model);
			alManageOnAirAddEdit( id, model);
			if(result.hasFieldErrors("clusters"))
				model.addAttribute("clustersError", "clustersError");
			if(result.hasFieldErrors("intNe"))
				model.addAttribute("intNeError", "intNeError");
			if(result.hasFieldErrors("onAir"))
				model.addAttribute("onAirError", "onAirError");
			if(result.hasFieldErrors("noCells"))
				model.addAttribute("noCellsError", "noCellsError");
			if(result.hasFieldErrors("noTrxs"))
				model.addAttribute("noTrxsError", "noTrxsError");
			model.addAttribute("cddName", alManageOnAir.getCddName());
			model.addAttribute("intNe", intNe);
			model.addAttribute("onAir", onAir);
			model.addAttribute("projectIdCBB", alManageOnAir.getProjectId());
			model.addAttribute("neName", alManageOnAir.getNe());
			model.addAttribute("projectCode", projectCode);
			return "jspalarm/alProject/alManageOnAirForm";
		}
		
		if(id.equals(""))
		{
			if(alManageOnAirDAO.checkOnAirPrimaryKey(alManageOnAir.getSiteid(), alManageOnAir.getNe(), alManageOnAir.getNetwork(), alManageOnAir.getProjectId().toString(), null).size() == 0){
				alManageOnAir.setCreatedBy(username);
				alManageOnAirDAO.insert(alManageOnAir);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				loadData(model);
				alManageOnAirAddEdit( id, model);
				model.addAttribute("intNe", intNe);
				model.addAttribute("onAir", onAir);
				model.addAttribute("cddName", alManageOnAir.getCddName());
				model.addAttribute("projectIdCBB", alManageOnAir.getProjectId());
				model.addAttribute("neName", alManageOnAir.getNe());
				saveMessageKey(request, "messsage.confirm.alManageOnAir.checkUniqueKeyOnAir");
				model.addAttribute("projectCode", projectCode);
				return "jspalarm/alProject/alManageOnAirForm";
			}
		}
		else{
			if(alManageOnAirDAO.checkOnAirPrimaryKey(alManageOnAir.getSiteid(), alManageOnAir.getNe(), alManageOnAir.getNetwork(), alManageOnAir.getProjectId().toString(), id).size() == 0){
				alManageOnAir.setModifiedBy(username);
				alManageOnAir.setModifyDate(new Date());
				
				alManageOnAirDAO.updateByPrimaryKeySelective(alManageOnAir);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				loadData(model);
				alManageOnAirAddEdit( id, model);
				model.addAttribute("intNe", intNe);
				model.addAttribute("onAir", onAir);
				model.addAttribute("cddName", alManageOnAir.getCddName());
				model.addAttribute("projectIdCBB", alManageOnAir.getProjectId());
				model.addAttribute("neName", alManageOnAir.getNe());
				saveMessageKey(request, "messsage.confirm.alManageOnAir.checkUniqueKeyOnAir");
				model.addAttribute("projectCode", projectCode);
				return "jspalarm/alProject/alManageOnAirForm";
			}
		}
		if(projectCode != null && !projectCode.equals("")){
			return "redirect:list.htm?projectCode="+projectCode;
		}	
		else
			return "redirect:list.htm";
	}
	
	// Lay danh sach bsc
  	@RequestMapping("dsBsc")
  	public @ResponseBody 
  	List<HBsc> dsBsc() {
  		List<HBsc> bscList = hBscDao.getBscidList();

  		return bscList;
  	 }
  	
  	// Lay danh sach rnc
   	@RequestMapping("dsRnc")
   	public @ResponseBody 
   	List<HBsc> dsRnc() {
   		List<HBsc> rncList = hBscDao.getRncidList();

   		return rncList;
   	 }
   	
   	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String network, 	   
			@RequestParam(required = false) String ne,
			@RequestParam(required = false) String siteid, 
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
		
		List<AlManageOnAir> alManageOnAir = alManageOnAirDAO.getAlManageOnAirFilter(projectCode, network, ne, siteid, null, null, sortfield, sortorder, strWhere);
		exportReport(tempFile, alManageOnAir);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "AlManageOnAir_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<AlManageOnAir> alManageOnAir) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("CR", 0);

			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
		    
		    int i=0;
			Label label0 = new Label(i, 0, "Site ID", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Site Name", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Network", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "CDD Name", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Cluster", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Project Code", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Project Supervisor", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Int.", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "On Air", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "No. Cells", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "No. TRXs", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "License", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Note", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Alarm Test", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Call Test", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "ExtAlm", cellFormatHeader);
			sheet.addCell(label16);
			i++;
			Label label17 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Description", cellFormatHeader);
			sheet.addCell(label18);
			i++;
			i = 1;
			
			for (AlManageOnAir record : alManageOnAir) {
				int j=0;
				Label record0 = new Label(j, i, record.getSiteid(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getSiteName(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNetwork(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getCddName(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getClustersStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getProjectCode(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getProjectSupervisor(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getIntNeStr(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getOnAirStr(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getNoCellsStr(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getNoTrxsStr(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getLicense(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getNote(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getAlarmTest(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getCallTest(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getExtalm(), cellFormat);
				sheet.addCell(record16);
				j++;
				Label record17 = new Label(j, i, record.getStatus(), cellFormat);
				sheet.addCell(record17);
				j++;
				Label record18 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record18);
				j++;
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
	public String showUploadForm(@RequestParam(required = false) String projectCode,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("projectCode", projectCode);
		return "jspalarm/alProject/alManageOnAirUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam(required = false) String projectCode,
			@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		model.addAttribute("projectCode", projectCode);
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
		
		return "jspalarm/alProject/alManageOnAirUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AlManageOnAir> successList = new ArrayList<AlManageOnAir>();
		List<AlManageOnAir> failedList = new ArrayList<AlManageOnAir>();
		List<AlManageOnAir> success = new ArrayList<AlManageOnAir>();
		
		String siteid;
		String siteName;
		String ne;
		String network;
		String cddName;
		String clusters;
		String projectCode;
		String projectSupervisor;
		String intNe;
		String onAir;
		String noCells;
		String noTrxs;
		String license;
		String note;
		String alarmTest;
		String callTest;
		String extalm;
		String status;
		String description;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 19) {
        		saveMessageKey(request, "sidebar.admin.alManageOnAirUploadErrorStructuresNumber");
        		
        		return "jspalarm/alProject/alManageOnAirUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AlManageOnAir alManageOnAir;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			alManageOnAir = new AlManageOnAir();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=19; j++) {
     					data.add("");
     				}
        			siteid				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			siteName			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			ne					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			network				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cddName				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			clusters			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			projectCode			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			projectSupervisor	= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			intNe				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			onAir				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			noCells				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			noTrxs				= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			license				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			note				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			alarmTest			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			callTest			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			extalm				= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			status				= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			description			= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			
        			// Kiem tra loi
        			if (siteid == null || projectCode == null || network == null
        					|| (projectCode!= null && alManageProjectDAO.getObjectProjectByCode("ON_AIR", projectCode.trim()) == null)
						) {
						error = true;
					}
        			
        			try
	    			{
        				if(clusters != null){
		        			Integer a = Integer.parseInt(clusters);
		        			alManageOnAir.setClusters(a);
	        			}
        				if(noCells != null){
		        			Integer a = Integer.parseInt(noCells);
		        			alManageOnAir.setNoCells(a);
	        			}
        				if(noTrxs != null){
		        			Integer a = Integer.parseInt(noTrxs);
		        			alManageOnAir.setNoTrxs(a);
	        			}
        				
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			try
	    			{
        				if(intNe != null){
		    				alManageOnAir.setIntNe(new SimpleDateFormat("dd/MM/yyyy").parse(intNe));
	    				}
        				if(onAir != null){
		    				alManageOnAir.setOnAir(new SimpleDateFormat("dd/MM/yyyy").parse(onAir));
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			if(siteid != null)
        				alManageOnAir.setSiteid(siteid.trim());
        			alManageOnAir.setSiteName(siteName);
        			alManageOnAir.setNe(ne);
        			alManageOnAir.setNetwork(network);
        			alManageOnAir.setCddName(cddName);
        			if(projectCode != null)
        				alManageOnAir.setProjectCode(projectCode.trim());
        			alManageOnAir.setProjectSupervisor(projectSupervisor);
        			alManageOnAir.setLicense(license);
        			alManageOnAir.setNote(note);
        			alManageOnAir.setAlarmTest(alarmTest);
        			alManageOnAir.setCallTest(callTest);
        			alManageOnAir.setExtalm(extalm);
        			alManageOnAir.setStatus(status);
        			alManageOnAir.setDescription(description);
        			if (siteid == null && projectCode == null && network == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(alManageOnAir);
    					} else  {
    						
    						successList.add(alManageOnAir);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AlManageOnAir record: successList) {
			try {
					
					AlManageProject alManageProject = alManageProjectDAO.getObjectProjectByCode("ON_AIR", record.getProjectCode());
					record.setProjectId(alManageProject.getId());
					
					if(alManageOnAirDAO.checkOnAirPrimaryKey(record.getSiteid(), record.getNe(), record.getNetwork(), record.getProjectId().toString(), null).size() == 0){
						record.setCreatedBy(createdBy);
						alManageOnAirDAO.insert(record);
					}else{
						record.setModifiedBy(createdBy);
						alManageOnAirDAO.updateByUniqueKey(record);
					}
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
			model.addAttribute("status", "Dữ liệu lịch phát sóng không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspalarm/alProject/alManageOnAirUpload";
	}
	

	// Lay danh sach network
  	@RequestMapping("networkForOnAirList")
  	public @ResponseBody 
  	List<SYS_PARAMETER> networkForOnAirList(HttpServletRequest request) {
  		List<SYS_PARAMETER> networkForOnAirList = sysParameterDao.getNetworkForOnAir();
  		return networkForOnAirList;
  	 }
  	
  	
  	// Lay danh sach cdd name
  	@RequestMapping("loadCddBySiteidAndNe")
  	public @ResponseBody 
  	List<AlManageCrCdd> loadCddBySiteidAndNe(@RequestParam(required = false) String siteid,
			@RequestParam(required = false) String ne, HttpServletRequest request) {
  		List<AlManageCrCdd> loadCddBySiteidAndNe = alManageCrCddDAO.loadCddBySiteidAndNe(siteid, ne);
  		return loadCddBySiteidAndNe;
  	 }
}
