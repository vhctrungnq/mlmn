package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.FilePattern;
import vo.HQualityNetwork;
import vo.ImportMapping;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.ImportMappingError;
import vo.alarm.utils.ImportMappingFilter;
import vo.alarm.utils.UploadTools;

import dao.FilePatternDAO;
import dao.ImportMappingDAO;
import dao.SysUsersDAO;



import bsh.ParseException;


@Controller
@RequestMapping("/system/importMapping/*")
public class ImportMappingFormController extends BaseController {
	@Autowired
	private ImportMappingDAO importMappingDao;
	
	@Autowired
	private FilePatternDAO filePatternDao;

	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") ImportMappingFilter filter, Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = importMappingDao.titleImportMapping(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "RAW_TABLE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("importMapping").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("importMapping").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<ImportMapping> importMappingList = new ArrayList<ImportMapping>();
		try
		{
			importMappingList = importMappingDao.getImportMappingList(filter, column, order);	
		}
		catch (Exception exp)
		{
			importMappingList= null;
		}
		model.addAttribute("importMappingList", importMappingList);
		model.addAttribute("filter", filter);
		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ImportMapping_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/CauHinh/importMappingList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String rawTable, @RequestParam(required = true) String tableColumn, @RequestParam(required = true) int patternId, HttpServletRequest request) {
		
		try {
			importMappingDao.deleteByPrimaryKey(patternId, rawTable, tableColumn );
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@ModelAttribute("filePatternList")
	public List<FilePattern> filePatternList() {
		return filePatternDao.getAll();
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String rawTable,
			@RequestParam(required = false) String tableColumn,
			@RequestParam(required = false) String patternId,
			ModelMap model) {

		List<SYS_PARAMETER> sysParemeterTitle = importMappingDao.titleImportMapping("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		ImportMapping importMapping = new ImportMapping();
		if (rawTable!=null&&tableColumn!=null&&patternId!=null)
		{
			importMapping = importMappingDao.selectByPrimaryKey(Integer.parseInt(patternId), rawTable, tableColumn);
		}
		model.addAttribute("importMapping", importMapping);		
		
		return "jsp/CauHinh/importMappingForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("importMapping") @Valid ImportMapping importMapping,
			BindingResult result,
			HttpServletRequest request,Model model) throws ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
	
		List<SYS_PARAMETER> sysParemeterTitle = importMappingDao.titleImportMapping("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		
		if (!result.hasErrors()) {
			
			ImportMapping oldImportMapping = importMappingDao.selectByPrimaryKey(importMapping.getPatternId(),importMapping.getRawTable(),importMapping.getTableColumn());
			importMapping.setCreateDate(new Date());
			if (importMapping.getCreateBy().isEmpty()) {
				importMapping.setCreateBy(username);
				if (oldImportMapping == null) {
					importMappingDao.insert(importMapping);
					saveMessageKey(request, "importMapping.insertSucceFull");
				} else {
					saveMessageKey(request, "importMapping.exits");
					model.addAttribute("titleF", title);
					model.addAttribute("importMapping", importMapping);
					return "jsp/CauHinh/importMappingForm";
				}
			} else {
				if (oldImportMapping != null && !oldImportMapping.getPatternId().equals(importMapping.getPatternId())) {
					saveMessageKey(request, "importMapping.exits");
					model.addAttribute("titleF", title);
					model.addAttribute("importMapping", importMapping);
					return "jsp/CauHinh/importMappingForm";
				} else {
					
					importMappingDao.updateByPrimaryKey(importMapping);
					saveMessageKey(request, "importMapping.updateSucceFull");
				}
			}
			return "redirect:list.htm";
		} else {
			
			model.addAttribute("titleF", title);
			model.addAttribute("importMapping", importMapping);
			return "jsp/CauHinh/importMappingForm";
		}
	}
	
	
	// Upload File
		@RequestMapping(value = "upload", method = RequestMethod.GET)
		public String showUploadForm(Model model) {
			List<SYS_PARAMETER> sysParemeterTitle = importMappingDao.titleImportMapping("UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
			}
			return "jsp/CauHinh/importMappingUpload";
		}
		
		
		@RequestMapping(value = "upload", method = RequestMethod.POST)
		public String upload(@RequestParam("file") MultipartFile filePath,  Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
			
			List<SYS_PARAMETER> sysParemeterTitle = importMappingDao.titleImportMapping("UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
			}
			if (!filePath.isEmpty()) {

				String[] ten = filePath.getOriginalFilename().split("\\.");
				
				String fileExtn = ten[ten.length-1];
				
				if (fileExtn.equals("xls")) {
					try
					{
						@SuppressWarnings("rawtypes")
						List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
				
						if (sheetData.size() > 0) {
							// Kiem tra số cột của file
				        	List heard= (List)sheetData.get(0);
				        	if (heard.size() != 8) {
				        		saveMessageKey(request, "cautruc.loiCautruc");
				        		return "jsp/CauHinh/importMappingUpload";
				        	}
				        	
				        	int maxsize=0;
			        		List footer= (List)sheetData.get(sheetData.size()-1);
			        		/*if (footer.size() != 9 && sheetData.size() > 2) {
			        			sheetData.remove(sheetData.size()-1);
			        			maxsize=sheetData.size();*/
			        		while (footer.size() != 8 && sheetData.size() > 2)
			        		{
			        			System.out.println(footer.size());
			        			boolean kt=true;
			        			for (int k=0;k<footer.size();k++)
			        			{
			        				if (!footer.get(k).toString().trim().equals(""))
			        				{
			        					kt=false;
			        				}
			        			}
			        			if (footer.size() == 8 && kt == false)
			        			{
			        				break;
			        			}
			        			if (footer.size() > 8 && kt == false)
			        			{
			        				boolean kt2=true;
			        				for (int l=8;l<footer.size();l++)
			        					if (!footer.get(l).toString().trim().equals(""))
				        				{
				        					kt2=false;
				        					break;
				        				}
			        				if (kt2==false)
			        				{
				        				model.addAttribute("errorContent","Import không thành công. Cấu trúc dữ liệu trong file không đúng định dạng cho phép");
				        				return "jsp/CauHinh/importMappingUpload";
			        				}
			        				else
			        				{
			        					break;
			        				}
			        			}
			        			else
			        			{
				        			if (kt==true)
				        			{
					        			sheetData.remove(sheetData.size()-1);
					        			maxsize=sheetData.size();
				        			}
				        			else
				        			{
				        				for (int j=footer.size();j<=7;j++)
				         				{
				        					footer.add("");
				         				}
				        			}
					        			footer= (List)sheetData.get(sheetData.size()-1);
			        			}
			        		}
			        		sheetData.remove(0);
				        	for (int i = 0; i < sheetData.size(); i++) {
				        		
				        		List list= (List)sheetData.get(i);
			     				for (int j=list.size();j<=7;j++)
			     				{
			     					list.add("");
			     				}
				    			
				        		/*if(list.size() != 8)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Bạn nhập thiếu thông tin");
				            		return "jsp/CauHinh/importMappingUpload";
				        		}*/
				        		
				        		//Bảng dữ liệu thô
				        		
				        		// Kiem tra kieu du lieu dâ thoa man chua
			     				if (list.get(0).toString().trim().length()>30||list.get(0).toString().trim().length()<1
			     						||list.get(2).toString().trim().length()<1
			     						||list.get(1).toString().trim().length()>30||list.get(1).toString().trim().length()<1
			     						||list.get(3).toString().trim().length()>30
			     						||list.get(4).toString().trim().length()>30
			     						||list.get(5).toString().trim().length()>100
			     						||list.get(6).toString().trim().length()>4
			     						||list.get(7).toString().trim().length()>1)
			     				{
			     					
			     					saveMessageKey(request, "cautruc.loiKichThuoc");
			     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			     					model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			     					return "jsp/CauHinh/importMappingUpload";
			     				}
			     				if (list.get(6).toString().trim()!=null && !list.get(6).toString().trim().equals(""))
			     				{
				     				try
				     				{
				     					int fileColumn = Integer.parseInt(list.get(6).toString().trim());
				     				}
				     				catch(Exception exp)
				     				{
				     					saveMessageKey(request, "importMapping.loiFileColumn");
				     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				     					model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
				     					return "jsp/CauHinh/importMappingUpload";
				     				}
			     				}
			     				int idPatten =0;
			     				FilePattern filePattern = filePatternDao.selectByFilePattern(list.get(2).toString().trim());
			     				if (filePattern!=null)
			     				{
			     					idPatten = filePattern.getPatternId();
			     				}
			     				if (idPatten==0)
			     				{
			     					saveMessageKey(request, "importMapping.FilePatternExits");
			     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			     					model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			     					return "jsp/CauHinh/importMappingUpload";
			     				}
			     				else
			     				{
				     				if(importMappingDao.selectByPrimaryKey(idPatten,list.get(0).toString().trim(),list.get(1).toString().trim()) != null)
					        		{
					        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
					    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
					    				saveMessageKey(request, "importMapping.exits");
					            		return "jsp/CauHinh/importMappingUpload";
					        		}
			     				}
			     				
		
				    		}
				        	importXlsContent(sheetData, model,  request);
				        	saveMessage(request, "Import file thành công.");
						}
						else
							saveMessage(request, "Không có dữ liệu để insert");		
					}
					catch(IOException e)
					{
						saveMessage(request, "Import lỗi.");
					}
				}
				else
				{
					saveMessage(request, "Import file không thành công");
					return "jsp/CauHinh/importMappingUpload";
				}
			}
			else
			{
				saveMessage(request, "Import lỗi.");
			}
			return "jsp/CauHinh/importMappingUpload";
		}
		
		@SuppressWarnings({ "rawtypes" })
		private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
		{	
	        excecuteImport(sheetData, model, request);
	        
		}
		
		private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
		{
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<ImportMapping> importMappingList = new ArrayList<ImportMapping>();
			
			for (int i = 0; i < sheetData.size(); i++) {
				
				ImportMapping record = new ImportMapping();
				List list = (List) sheetData.get(i);
				
				int idPatten =0;
 				FilePattern filePattern = filePatternDao.selectByFilePattern(list.get(2).toString().trim());
 				if (filePattern!=null)
 				{
 					idPatten = filePattern.getPatternId();
 				}
 				record.setPatternId(idPatten);
				record.setRawTable(list.get(0).toString().trim());
				record.setTableColumn(list.get(1).toString().trim());
				record.setDataType(list.get(3).toString().trim());
				record.setDataFormat(list.get(4).toString().trim());
				record.setFilePattern(list.get(2).toString().trim());
				if (list.get(6).toString().trim()!=null && !list.get(6).toString().trim().equals(""))
 				{
					record.setFileColumn(Integer.parseInt(list.get(6).toString().trim()));
 				}
				record.setFileColumnHeader(list.get(5).toString().trim());
				record.setStatus(list.get(7).toString().trim());
				record.setCreateDate(new Date());
				record.setCreateBy(username);
				
				importMappingDao.insert(record);
				importMappingList.add(record);
					
			}
			
			model.addAttribute("importMappingList",importMappingList);
		}
		//dang lam o day 
		private List<ImportMappingError> errorlist(List sheet)
		{
			List<ImportMappingError> importMappingList = new ArrayList<ImportMappingError>();
			ImportMappingError record = new ImportMappingError();
			int size= sheet.size();
	    	for (int i=size;i<=7;i++)
	    		sheet.add("");
			record.setRawTable(sheet.get(0).toString());
			record.setTableColumn(sheet.get(1).toString());
			record.setFilePattern(sheet.get(2).toString());
			record.setDataType(sheet.get(3).toString());
			record.setDataFormat(sheet.get(4).toString());
			record.setFileColumn(sheet.get(6).toString());
			record.setFileColumnHeader(sheet.get(5).toString());
			record.setStatus(sheet.get(7).toString());
			importMappingList.add(record);
			return importMappingList;
		}
}
