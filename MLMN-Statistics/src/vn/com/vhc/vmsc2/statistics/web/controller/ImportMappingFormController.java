package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import bsh.ParseException;
import vn.com.vhc.vmsc2.statistics.dao.FilePatternDAO;
import vn.com.vhc.vmsc2.statistics.dao.ImportMappingDAO;
import vn.com.vhc.vmsc2.statistics.domain.FilePattern;
import vn.com.vhc.vmsc2.statistics.domain.ImportMapping;
import vn.com.vhc.vmsc2.statistics.web.filter.ImportMappingFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;



@Controller
@RequestMapping("/system/importMapping/*")
public class ImportMappingFormController extends BaseController {
	private ImportMappingDAO importMappingDao;
	private FilePatternDAO filePatternDao;

	@Autowired
	public void setImportMappingDao(ImportMappingDAO importMappingDao) {
		this.importMappingDao = importMappingDao;
	}

	@Autowired
	public void setFilePatternDao(FilePatternDAO filePatternDao) {
		this.filePatternDao = filePatternDao;
	}

	@ModelAttribute("filePatternList")
	public List<FilePattern> filePatternList() {
		return filePatternDao.getAll();
	}
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") ImportMappingFilter filter, Model model) {
		List<ImportMapping> importMappings = importMappingDao.filter(filter);
		model.addAttribute("importMappingList", importMappings);
		return new ModelAndView("importMappingList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String rawTable, @RequestParam(required = true) String tableColumn, @RequestParam(required = true) int patternId, HttpServletRequest request) {
		
		importMappingDao.deleteByPrimaryKey(patternId, rawTable, tableColumn );
		saveMessage(request, "ImportMapping has been deleted successfully.");
		
		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String rawTable, @RequestParam(required = false) String tableColumn,
			@RequestParam(required = false) Integer patternId, ModelMap model) {

		ImportMapping importMapping = (patternId == null) ? new ImportMapping() : importMappingDao.selectByPrimaryKey(patternId, rawTable, tableColumn);
		model.addAttribute(importMapping);
		return "importMappingForm";
	}
	
	
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid ImportMapping importMapping, BindingResult result, HttpServletRequest request,Model model) {
		if (!result.hasErrors())
		{
			ImportMapping oldImportMapping= importMappingDao.exitsFilePattern(importMapping.getRawTable(), importMapping.getTableColumn());
			if (importMapping.getCreateBy().isEmpty()){
				if (importMapping.getPatternId() == null) {
				if(oldImportMapping==null){
					// TODO add current user
					importMapping.setCreateBy("HuanPX");
					importMappingDao.insert(importMapping);
					saveMessage(request, "ImportMapping khởi tạo thành công.");
				}
				}
				else{
					saveMessage(request,"ImportMapping đã tồn tại");
					model.addAttribute(importMapping);
					return "importMappingForm";
				}
			}
			else
			{
				
				if (oldImportMapping!=null && !oldImportMapping.getPatternId().equals(importMapping.getPatternId()))
				{
					saveMessage(request,"ImportMapping đã tồn tại");
					model.addAttribute(importMapping);
					return "importMappingForm";
				}
				importMappingDao.updateByPrimaryKey(importMapping);
				 saveMessage(request, "ImportMapping cập nhật thành công.");
				}
				return "redirect:list.htm";
		}
		else
		{
			model.addAttribute(importMapping);
			return "importMappingForm";
		}
	}
	
	/*@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid ImportMapping importMapping, BindingResult result, HttpServletRequest request, Model model) {
		if (!result.hasErrors()) 
		{
			if (importMapping.getCreateBy().isEmpty()) {
				// TODO add current user
				importMapping.setCreateBy("QuanNH");
				importMappingDao.insert(importMapping);
				saveMessage(request, "ImportMapping được tạo mới thành công.");

			} else {
				importMappingDao.updateByPrimaryKey(importMapping);
				saveMessage(request, "ImportMapping được cập nhật thành công.");
				
				return "redirect:list.htm";
			}
		} else {
			model.addAttribute(importMapping);
			return "importMappingForm";
		}
		return "redirect:list.htm";
	}*/

	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "importMappingUpload";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten = filePath.getOriginalFilename().split("\\.");
			
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				try
				{
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					
					
					if (sheetData.size() > 0) {
						// Kiểm tra số cột của file
			        	List heard= (List)sheetData.get(0);
			        	if (heard.size() != 8) {
			        		saveMessage(request, "Số lượng cột ImportMapping của file không đúng");
			        		return "importMappingUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 8)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import d? li?u trong file b? l?i t?i b?n ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Bạn nhập thiếu thông tin");
			            		return "importMappingUpload";
			        		}
			        		
			        		//Bảng dữ liệu thô
			        		if(list.get(0).toString().length() > 30)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên Bảng dữ liệu thô có độ dài quá 30");
			            		return "importMappingUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu bảng dữ liệu thô không có");
			            		return "importMappingUpload";
			        		}
			        		
			        		//Tên cột trong bảng
			        		if(list.get(1).toString().length() > 30 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên cột trong bảng dài quá 30");
			            		return "importMappingUpload";
			        		}
			        		if(list.get(1).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Tên cột không có");
			            		return "importMappingUpload";
			        		}
			        		
			        		//Kiểu dữ liệu
			        		if(list.get(2).toString().length() > 30 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Kiểu dữ liệu có độ dài quá 10");
			            		return "importMappingUpload";
			        		}
			        		
			        		//Định dạng dữ liệu
			        		if(list.get(3).toString().length() > 30 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Định dạng dữ liệu có độ dài quá 20");
			            		return "importMappingUpload";
			        		}
		
			    			//Tên File
			        		int idPatten =0;
		     				FilePattern filePattern = filePatternDao.selectByFilePattern(list.get(4).toString().trim());
		     				if (filePattern!=null)
		     				{
		     					idPatten = filePattern.getPatternId();
		     				}
		     				if (idPatten==0)
		     				{
		     					saveMessage(request, "Không tồn tại tên file");
		     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
		     					model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
		     					return "importMappingUpload";
		     				}
		     				else
		     				{
			     				if(importMappingDao.selectByPrimaryKey(idPatten,list.get(0).toString().trim(),list.get(1).toString().trim()) != null)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "ImportMapping đã tồn tại");
				            		return "importMappingUpload";
				        		}
		     				}
			    			
			    			//Thứ tự trong cột
			        		if(list.get(5).toString().length() > 4 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Thứ tự trong cột có độ dài quá 4");
			            		return "importMappingUpload";
			        		}
			        		
			        		//Tiêu đề cột trong file
			        		if(list.get(6).toString().length() > 100 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("importMappingList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tiêu đề cột trong file có độ dài quá 100");
			            		return "importMappingUpload";
			        		}
			    			
			        		//Trạng thái
			    			if(!list.get(7).toString().toLowerCase().equals("không sử dụng") && !list.get(9).toString().toLowerCase().equals("đang sử dụng"))
			    			{
			    				model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("usersList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu cột trạng thái không đúng định dạng");
			            		return "importMappingUpload";
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
				return "importMappingUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "importMappingUpload";
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<ImportMapping> importMappingList = new ArrayList<ImportMapping>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			ImportMapping record = new ImportMapping();
			
			List list = (List) sheetData.get(i);
			
			int idPatten =0;
			FilePattern filePattern = filePatternDao.selectByFilePattern(list.get(4).toString().trim());
			if (filePattern!=null)
			{
				idPatten = filePattern.getPatternId();
			}
			record.setPatternId(idPatten);
			record.setRawTable(list.get(0).toString());
			
			record.setTableColumn(list.get(1).toString());
			
			record.setDataType(list.get(2).toString());
			
			record.setDataFormat(list.get(3).toString());
			
			record.setFilePattern(list.get(4).toString().trim());
			
			String str1 = list.get(5).toString();
			record.setFileColumn(Integer.parseInt(str1.substring(0,str1.indexOf("."))));
			
			record.setFileColumnHeader(list.get(6).toString());
			
			if(list.get(7).toString().toLowerCase().equals("không sử dụng"))
				record.setTrangthai("N");
			else  if(list.get(7).toString().toLowerCase().equals("đang sử dụng"))
				record.setTrangthai("Y");
			record.setCreateBy(username);
			
			importMappingDao.insert(record);
			
			
			importMappingList.add(record);
				
		}
		
		model.addAttribute("importMappingList",importMappingList);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ImportMapping> errorlist(List sheet)
	{
		List<ImportMapping> importMappingList = new ArrayList<ImportMapping>();
		ImportMapping record = new ImportMapping();
		int size= sheet.size();
    	for (int i=size;i<=7;i++)
    		sheet.add("");
		record.setRawTable(sheet.get(0).toString());
		record.setTableColumn(sheet.get(1).toString());
		record.setDataType(sheet.get(2).toString());
		record.setDataFormat(sheet.get(3).toString());
		record.setFilePattern(sheet.get(4).toString());
		record.setFileColumn(sheet.get(5).hashCode());
		record.setFileColumnHeader(sheet.get(6).toString());
		record.setTrangthai(sheet.get(7).toString());
		importMappingList.add(record);
		return importMappingList;
	}
	
}
