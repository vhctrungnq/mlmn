package controller.cable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.CTableConfig;
import vo.CableSwitchboard;
import vo.SysFiles;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.Helper;
import vo.dictionary.TableConfigsHelper;

import dao.CTableConfigDAO;
import dao.CableSwitchboardDAO;
import dao.SysFilesDAO;
import dao.SysUsersDAO;
/**
 * Function        : Quan ly du lieu cap tong dai
 * Created By      : BUIQUANG
 * Create Date     : 28/02/2014
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/cable/switchboard/*")
public class CableSwitchboardController extends BaseController{

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private CableSwitchboardDAO cableSwitchboardDAO;
	@Autowired
	private SysFilesDAO sysFilesDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String site, 
			@RequestParam(required = false) String name, 	 
    		@ModelAttribute("filter") CableSwitchboard filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String filterUrl = "";
		String temp = "";
		if(vendor != null){
			filterUrl += temp + "vendor=" + vendor.trim(); 
			temp = "&";
		}
		if(site != null){
			filterUrl += temp + "site=" + site.trim(); 
			temp = "&";
		}
		if(name != null){
			filterUrl += temp + "name=" + name.trim(); 
			temp = "&";
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("CABLE_SWITCHBOARD");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("CABLE_SWITCHBOARD");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "multiplerowsextended", null);
		model.addAttribute("gridManage", gridManage);
		
		model.addAttribute("vendor", vendor);
		model.addAttribute("site", site);
		model.addAttribute("name", name);
		
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);	
		return new ModelAndView("jspcable/cableSwitchboardList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String site, 
			@RequestParam(required = false) String name,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CableSwitchboard> cableSwitchboard = null;
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
			columnList = cTableConfigDAO.getTableConfigGet("CABLE_SWITCHBOARD", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("CABLE_SWITCHBOARD", null);
		
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
			cableSwitchboard = cableSwitchboardDAO.getCableSwitchboardFilter(vendor, site, name, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = cableSwitchboardDAO.countCableSwitchboard(vendor, site, name, strWhere);
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
		String jsonCartList = gs.toJson(cableSwitchboard);
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
			cableSwitchboardDAO.deleteByPrimaryKey(id);
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
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		CableSwitchboard cableSwitchboard = (id == null) ? new CableSwitchboard() : cableSwitchboardDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("cableSwitchboard", cableSwitchboard);
		cableSwitchboardAddEdit( id, model);
		if(id != null && id != ""){
			String fileList = "";
			List<SysFiles> sysFilesList = sysFilesDAO.getSysFilesByMapping(cableSwitchboard.getId().toString());
			String hello = "";
			for(int i=0;i<sysFilesList.size();i++){
				fileList += hello + sysFilesList.get(i).getId();
				hello = ",";
			}
			cableSwitchboard.setFileId(fileList);
		}
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);	
		return "jspcable/cableSwitchboardForm";
	}
	
	private void cableSwitchboardAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("cableSwitchboardAddEdit", "Y");
		else
			model.addAttribute("cableSwitchboardAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String fileId,
			@ModelAttribute("cableSwitchboard") @Valid CableSwitchboard cableSwitchboard, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//Ném lỗi
		if (result.hasErrors()) {
			cableSwitchboardAddEdit( id, model);
				
			return "jspcable/cableSwitchboardForm";
		}
		
		if(id.equals(""))
		{
			try{
				if(cableSwitchboardDAO.checkCableSwitchboardUk(cableSwitchboard.getVendor(), cableSwitchboard.getNeType(), cableSwitchboard.getName(), cableSwitchboard.getSite(), null).size() == 0){
					cableSwitchboard.setCreatedBy(username);
					
					int idResult = cableSwitchboardDAO.insertAndResult(cableSwitchboard);
					if(fileId != "" && fileId != null){
						String[] fileList = fileId.split(",");
						for(int i=0;i<fileList.length;i++){
							SysFiles record = new SysFiles();
							record.setMappingId(idResult);
							record.setFileType("CABLE");
							if(!fileList[i].equals(""))
								record.setId(Integer.parseInt(fileList[i]));
							sysFilesDAO.updateByPrimaryKeySelective(record);
						}
					}
					saveMessageKey(request, "messsage.confirm.addsuccess");
				}
				else{
					cableSwitchboardAddEdit( id, model);
					saveMessageKey(request, "messsage.confirm.cableSwitchboard.checkUniqueKey");
					return "jspcable/cableSwitchboardForm";
				}
			}
			catch(Exception e){
				cableSwitchboardAddEdit( id, model);
				
				return "jspcable/cableSwitchboardForm";
			}
		}
		else{
			if(cableSwitchboardDAO.checkCableSwitchboardUk(cableSwitchboard.getVendor(), cableSwitchboard.getNeType(), cableSwitchboard.getName(), cableSwitchboard.getSite(), id).size() == 0){
				cableSwitchboard.setModifiedBy(username);
				
				cableSwitchboardDAO.updateByPrimaryKey(cableSwitchboard);
				
				if(fileId != "" && fileId != null){
					String[] fileList = fileId.split(",");
					for(int i=0;i<fileList.length;i++){
						SysFiles record = new SysFiles();
						record.setFileType("CABLE");
						record.setMappingId(Integer.parseInt(id));
						if(!fileList[i].equals(""))
							record.setId(Integer.parseInt(fileList[i]));
						sysFilesDAO.updateByPrimaryKeySelective(record);
					}
				}
				
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				cableSwitchboardAddEdit( id, model);
				saveMessageKey(request, "messsage.confirm.cableSwitchboard.checkUniqueKey");
				return "jspcable/cableSwitchboardForm";
			}
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String name, 
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
		
		List<CableSwitchboard> cableSwitchboard = cableSwitchboardDAO.getCableSwitchboardFilter(vendor, site, tempName, null, null, sortfield, sortorder, strWhere);
		exportReport(tempFile, cableSwitchboard);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "CableTransmissionPartners_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<CableSwitchboard> cableSwitchboard) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("CableSwitchboard", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Name", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Site", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Description", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Created By", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Create Date", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Modified By", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Modified Date", cellFormatHeader);
			sheet.addCell(label8);
			
			i = 1;
			
			for (CableSwitchboard record : cableSwitchboard) {
				int j=0;
				Label record0 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeType(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getName(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getSite(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getCreatedBy(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getCreateDateStr(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getModifiedBy(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getModifyDateStr(), cellFormat);
				sheet.addCell(record8);
				
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
}
