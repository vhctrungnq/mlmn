package controller.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.dictionary.FileTools;
import vo.dictionary.TableConfigsHelper;

import dao.AlManageCrCddDAO;
import dao.AlManageProjectDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

/**
 * Function        : Quan ly thong CR va CDD
 * Created By      : BUIQUANG
 * Create Date     : 20/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-manage-cr-cdd/*")
public class AlManageCrCddController extends BaseController{
	@Autowired
	private AlManageCrCddDAO alManageCrCddDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private AlManageProjectDAO alManageProjectDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String crName,
			@RequestParam(required = false) String vendor, 	   
			@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String crCdd,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String maPhong,
    		@ModelAttribute("filter") AlManageCrCdd filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		if (maPhong==null)
		{
			maPhong = userLogin.getMaPhong();
		}
		boolean checkRegion=false;
		if (userLogin!=null&&maPhong!=null&maPhong.equalsIgnoreCase(userLogin.getMaPhong())){
			checkRegion= true;
		}
		model.addAttribute("checkRegion", checkRegion);
		model.addAttribute("maPhong", maPhong);
		String filterUrl = "";
		String temp = "";
		if(crName != null){
			filterUrl += "crName=" + crName.trim(); 
			temp = "&";
		}
		if(vendor != null){
			filterUrl += temp + "vendor=" + vendor.trim(); 
			temp = "&";
		}
		if(projectCode != null){
			filterUrl += temp + "projectCode=" + projectCode.trim(); 
			temp = "&";
		}
		
		if(crCdd != null)
		{
			filterUrl += temp + "crCdd=" + crCdd.trim();
			temp = "&";
		}
		if(bscid != null){
			filterUrl += temp + "bscid=" + bscid.trim(); 
			temp = "&";
		}
		if(site != null){
			filterUrl += temp + "site=" + site.trim(); 
			temp = "&";
		}if(maPhong != null)
		{
			filterUrl += temp + "maPhong=" + maPhong.trim();
			temp = "&";
		}
		
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		if(crCdd.equals("CR")){
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MANAGE_CR");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MANAGE_CR");
			String url = "data.htm" + filterUrl;
			//Grid
			String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
			model.addAttribute("gridManage", gridManage);
		}
		else if(crCdd.equals("CDD")){
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MANAGE_CDD");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MANAGE_CDD");
			String url = "data.htm" + filterUrl;
			//Grid
			String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
			model.addAttribute("gridManage", gridManage);
		}
		
		model.addAttribute("crName", crName);
		model.addAttribute("vendor", vendor);
		model.addAttribute("projectCode", projectCode);
		model.addAttribute("crCdd", crCdd);
		model.addAttribute("bscid", bscid);
		model.addAttribute("site", site);
		return new ModelAndView("jspalarm/alProject/alManageCrCddList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String crName,
			@RequestParam(required = false) String vendor, 	   
			@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String crCdd,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) String maPhong,
			
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageCrCdd> alManageCrCdd = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "CR_NAME";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = null;
		List<CTableConfig> tableConfigList = null;
		if(crCdd.equals("CR")){
			columnList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_CR", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_CR", null);
		}
		else if(crCdd.equals("CDD")){
			columnList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_CDD", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("AL_MANAGE_CDD", null);
		}
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: tableConfigList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		// Sap xep
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
			alManageCrCdd = alManageCrCddDAO.getAlManageCrCddFilter(crName, vendor, projectCode, crCdd, bscid, site, startRecord, endRecord, sortfield, sortorder, strWhere,maPhong);
			totalRow = alManageCrCddDAO.countAlManageCrCddFilter(crName, vendor, projectCode, crCdd, bscid, site, strWhere,maPhong);
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
		String jsonCartList = gs.toJson(alManageCrCdd);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id,
			@RequestParam (required = true) String crCdd, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			alManageCrCddDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm?crCdd=" + crCdd;
	}
	
	private void loadData(ModelMap model){
		List<AlManageCrCdd> alManageProjectList= alManageCrCddDAO.getProjectCodeYearList("ON_AIR");
		model.addAttribute("projectIdList", alManageProjectList);
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String crCdd,
			@RequestParam(required = false) String crDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		AlManageCrCdd alManageCrCdd = (id == null) ? new AlManageCrCdd() : alManageCrCddDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("alManageCrCdd", alManageCrCdd);
		alManageCrCddAddEdit( id, model);
		loadData(model);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(id != null && id != ""){
			if(alManageCrCdd.getCrDate() != null)
				model.addAttribute("crDate", df.format(alManageCrCdd.getCrDate()));
			model.addAttribute("projectIdCBB", alManageCrCdd.getProjectId());
			if(alManageCrCdd.getCreatedBy().equals(username))
				model.addAttribute("roleUpdateFile", "Y");
			else
				model.addAttribute("roleUpdateFile", "N");
		}
		else{
			model.addAttribute("crDate", df.format(new Date()));
		}
		model.addAttribute("crCdd", crCdd);
		return "jspalarm/alProject/alManageCrCddForm";
	}
	
	private void alManageCrCddAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("alManageCrCddAddEdit", "Y");
		else
			model.addAttribute("alManageCrCddAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String crDate,
			@RequestParam(required = false) String crCdd,
			@ModelAttribute("alManageCrCdd") @Valid AlManageCrCdd alManageCrCdd, BindingResult result, 
			@RequestParam("file") MultipartFile file,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		//Ném lỗi
		if (result.hasErrors()) {
			loadData(model);
			alManageCrCddAddEdit( id, model);
			if(result.hasFieldErrors("resultRate"))
				model.addAttribute("resultRateError", "resultRateError");
			if(result.hasFieldErrors("crDate"))
				model.addAttribute("crDateError", "crDateError");
			model.addAttribute("crCdd", crCdd);
			model.addAttribute("crDate", crDate);
			model.addAttribute("projectIdCBB", alManageCrCdd.getProjectId());
			return "jspalarm/alProject/alManageCrCddForm";
		}
		
		if(id.equals(""))
		{
			if(alManageCrCddDAO.checkCrNameUk(alManageCrCdd.getCrName(), crCdd, null).size() == 0){
				alManageCrCdd.setCreatedBy(username);
				if(crCdd.equals("CR"))
					alManageCrCdd.setCrCdd("CR");
				else if(crCdd.equals("CDD"))
					alManageCrCdd.setCrCdd("CDD");
				fileUpload(file, crCdd, alManageCrCdd, request);
				alManageCrCdd.setMaPhong(userLogin.getMaPhong());
				alManageCrCddDAO.insert(alManageCrCdd);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				loadData(model);
				alManageCrCddAddEdit( id, model);
				model.addAttribute("crCdd", crCdd);
				model.addAttribute("crDate", crDate);
				model.addAttribute("projectIdCBB", alManageCrCdd.getProjectId());
				if(crCdd.equals("CR"))
					saveMessageKey(request, "messsage.confirm.alManageCrCdd.checkUniqueKeyCrName");
				else if(crCdd.equals("CDD"))
					saveMessageKey(request, "messsage.confirm.alManageCrCdd.checkUniqueKeyCddName");
				return "jspalarm/alProject/alManageCrCddForm";
			}
		}
		else{
			if(alManageCrCddDAO.checkCrNameUk(alManageCrCdd.getCrName(), crCdd, id).size() == 0){
				alManageCrCdd.setModifiedBy(username);
				alManageCrCdd.setModifyDate(new Date());
				if(crCdd.equals("CR"))
					alManageCrCdd.setCrCdd("CR");
				else if(crCdd.equals("CDD"))
					alManageCrCdd.setCrCdd("CDD");
				fileUpload(file, crCdd, alManageCrCdd, request);
				alManageCrCddDAO.updateByPrimaryKeySelective(alManageCrCdd);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				loadData(model);
				alManageCrCddAddEdit( id, model);
				model.addAttribute("crCdd", crCdd);
				model.addAttribute("crDate", crDate);
				model.addAttribute("projectIdCBB", alManageCrCdd.getProjectId());
				if(crCdd.equals("CR"))
					saveMessageKey(request, "messsage.confirm.alManageCrCdd.checkUniqueKeyCrName");
				else if(crCdd.equals("CDD"))
					saveMessageKey(request, "messsage.confirm.alManageCrCdd.checkUniqueKeyCddName");
				return "jspalarm/alProject/alManageCrCddForm";
			}
		}
		return "redirect:list.htm?crCdd=" + crCdd;
	}
	
	private void fileUpload(MultipartFile file, String crCdd, AlManageCrCdd alManageCrCdd, HttpServletRequest request){
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory.alarm");
		
		InputStream is = null;
		
		String fileName;
		String fileExtend;
		String filePath = "";

		fileName = FileTools.getFileName(file.getOriginalFilename());
		fileExtend = FileTools.getExtendOfFile(file.getOriginalFilename());
		if(crCdd.equals("CR"))
			filePath = "CR/" + FileTools.getSubFolderByDate(new Date(), "YYYY/MM/");
		else if(crCdd.equals("CDD"))
			filePath = "CDD/" + FileTools.getSubFolderByDate(new Date(), "YYYY/MM/");
		
		FileTools.mkDir(directoryFolder.get(0).getValue().concat(filePath));
		String fileFullPath = directoryFolder.get(0).getValue().concat(filePath).concat(fileName);
		
		boolean error = true;
		try {
			is = request.getInputStream();
			OutputStream  os = null;
			try {
				File f = new File(fileFullPath.concat(fileExtend));
				int i = 0;
				String tmpFile = fileName;
				while (f.exists()) {
					i++;
					fileName = tmpFile.concat("(" + i + ")");
					String newFile = fileFullPath.concat("(" + i + ")").concat(fileExtend);
					f = new File(newFile);
				}
				
				os = new FileOutputStream(f);
			
				os.write(file.getBytes());
				
				os.close();
			} catch (Exception e) {
				//logger.warn("Error while saving file");
				error = false;
			}
            
            if (error) {
            	alManageCrCdd.setFileName(fileName);
            	alManageCrCdd.setFileExtension(fileExtend);
            	alManageCrCdd.setFilePath(filePath.concat(fileName).concat(fileExtend));
            } else {
            	//writer.print("{success: false}");
            }
        } catch (FileNotFoundException ex) {
           // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //writer.print("{success: false}");
        } catch (IOException ex) {
            //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //writer.print("{success: false}");
        } finally {
            try {
                is.close();
            } catch (IOException ignored) { }
        }
	}
	
	/**
	 * Download file khi click vao link ten tep
	 * @param id
	 * @param newpass
	 * @param model
	 * @return
	 */
	@RequestMapping(value="download", method = RequestMethod.GET)
	public HttpEntity<byte[]> download(@RequestParam(required = false) Integer id, @RequestParam(required = false) String newpass, Model model) {
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory.alarm");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		AlManageCrCdd alManageCrCdd = alManageCrCddDAO.selectByPrimaryKey(id);
		
		String fileName = alManageCrCdd.getFileName();
		String filePath = alManageCrCdd.getFilePath();
		String fileExtend = alManageCrCdd.getFileExtension();
		
		File file = new File(rootFolderImage.concat(filePath));
		
		if (!file.exists()) {
			file = new File(rootFolderImage.concat("noprofile.png"));
		}
		
		byte documentBody[] = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			documentBody = new byte[(int)file.length()];
			
			fin.read(documentBody);
			
			fin.close();
		} catch(Exception e) {
			return null;
		}

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", fileExtend));
	    header.set("Content-Disposition", "attachment; filename=\"" + fileName.concat(fileExtend) + "\"");
	    header.setContentLength(documentBody.length);

	    return new HttpEntity<byte[]>(documentBody, header);
	}
	/**
	 * Xoa file
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteFile")
	public @ResponseBody 
	int deleteFile(@RequestParam(required = false) Integer id) {
		
		AlManageCrCdd alManageCrCdd = alManageCrCddDAO.selectByPrimaryKey(id);
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory.alarm");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		String filePath = alManageCrCdd.getFilePath();
		
		File file = new File(rootFolderImage.concat(filePath));
		
		if (file.delete()) {
			AlManageCrCdd record = new AlManageCrCdd();
			record.setId(id);
			alManageCrCddDAO.updateFileInfo(record);
		} else {
			return 0;
		}
		
		return 1;
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			   @RequestParam(required = false) String crName,
			   @RequestParam(required = false) String vendor, 
			   @RequestParam(required = false) String projectCode, 
			   @RequestParam(required = false) String crCdd, 
			   @RequestParam(required = false) String maPhong,
			   @RequestParam(required = false) String bscid,
			   @RequestParam(required = false) String site,
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
		
		List<AlManageCrCdd> alManageCrCdd = alManageCrCddDAO.getAlManageCrCddFilter(crName, vendor, projectCode, crCdd, bscid, site, null, null, sortfield, sortorder, strWhere,maPhong);
		if(crCdd.equals("CR"))
			exportReportCr(tempFile, alManageCrCdd);
		else if(crCdd.equals("CDD"))
			exportReportCdd(tempFile, alManageCrCdd);
		
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "FileInfo_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReportCr(File tempFile, List<AlManageCrCdd> alManageCrCdd) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("CR", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Mã dự án", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Tên dự án", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "CR Name", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Ngày thực hiện", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Đối tác", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Người thực hiện", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Kết quả thực hiện", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Tỷ lệ % hoàn thành", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Tên file", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Người tạo", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Ngày tạo", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Người sửa", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Ngày sửa", cellFormatHeader);
			sheet.addCell(label12);
			i = 1;
			
			for (AlManageCrCdd record : alManageCrCdd) {
				int j=0;
				Label record0 = new Label(j, i, record.getProjectCode(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i, record.getProjectName(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getCrName(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getCrDateStr(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getSupervisor(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getResult(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getResultRateStr(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getFileName(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getCreatedBy(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getCreateDateStr(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getModifiedBy(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getModifyDateStr(), cellFormat);
				sheet.addCell(record12);
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
	
	private void exportReportCdd(File tempFile, List<AlManageCrCdd> alManageCrCdd) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("CDD", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Mã dự án", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Tên dự án", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Tổng đài", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Site", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "CDD Name", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Ngày thực hiện", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Đối tác", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Tỷ lệ % hoàn thành", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Người thực hiện", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Kết quả thực hiện", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Tên file", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Người tạo", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Ngày tạo", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Người sửa", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Ngày sửa", cellFormatHeader);
			sheet.addCell(label14);
			
			i = 1;
			
			for (AlManageCrCdd record : alManageCrCdd) {
				int j=0;
				Label record0 = new Label(j, i, record.getProjectCode(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i, record.getProjectName(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getBscid(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getSite(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getCrName(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getCrDateStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getResultRateStr(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getSupervisor(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getResult(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getFileName(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getCreatedBy(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getCreateDateStr(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getModifiedBy(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getModifyDateStr(), cellFormat);
				sheet.addCell(record14);

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
	
	// Lay danh sach ma du an
  	@RequestMapping("alManageProjectList")
  	public @ResponseBody 
  	List<AlManageCrCdd> alManageProjectList(HttpServletRequest request) {
  		List<AlManageCrCdd> alManageProjectList= alManageCrCddDAO.getProjectCodeYearList("ON_AIR");
  		return alManageProjectList;
  	 }
}
