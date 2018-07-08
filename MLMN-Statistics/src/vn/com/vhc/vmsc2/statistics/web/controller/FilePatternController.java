package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;

import bsh.ParseException;


import vn.com.vhc.vmsc2.statistics.dao.FilePatternDAO;
import vn.com.vhc.vmsc2.statistics.domain.FilePattern;
import vn.com.vhc.vmsc2.statistics.web.filter.FilePatternFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

/**
 * Function        : Danh sach mau file
 * Created By      : 
 * Create Date     :
 * Modified By     : BUIQUANG
 * Modify Date     : 22/01/2014
 * @author BUIQUANG
 * Description     :
 */

@Controller
@RequestMapping("/system/filePattern/*")
public class FilePatternController extends BaseController {
	private FilePatternDAO filePatternDao;

	@Autowired
	public void setFilePatternDao(FilePatternDAO filePatternDao) {
		this.filePatternDao = filePatternDao;
	}
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") FilePatternFilter filter, Model model) {
		
		if(filter.getFilePattern() != null)
			filter.setFilePattern(filter.getFilePattern().trim());
		if(filter.getNodeType() != null)
			filter.setNodeType(filter.getNodeType().trim());
		if(filter.getConvertClass() != null)
			filter.setConvertClass(filter.getConvertClass().trim());
		if(filter.getRawTable() != null)
			filter.setRawTable(filter.getRawTable().trim());
		
		List<FilePattern> filePatterns = filePatternDao.filter(filter.getFilePattern(),filter.getNodeType(),filter.getConvertClass(),filter.getRawTable(),filter.getStatus());
		model.addAttribute("filePatternList", filePatterns);
		return new ModelAndView("filePatternList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int patternId, HttpServletRequest request) {
		try
		{
			filePatternDao.deleteByPrimaryKey(patternId);
			saveMessage(request, "File Pattern đã được xóa thành công.");
		}catch(Exception  e)
		{
			saveMessage(request, "Bạn phải xóa bên ImportMapping trước khi muốn xóa FilePattern.");
		}
		return "redirect:list.htm";
	}
	
	private void FilePatternsAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("FilePatternsAddEdit", "Y");
		else
			model.addAttribute("FilePatternsAddEdit", "N");
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String patternId, ModelMap model) {

		FilePattern filePattern = (patternId == null) ? new FilePattern() : filePatternDao.selectByPrimaryKey(Integer.parseInt(patternId));
		model.addAttribute(filePattern);
		FilePatternsAddEdit(patternId, model);
		
		return "filePatternForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String patternId, @Valid FilePattern filePattern, BindingResult result, HttpServletRequest request,ModelMap model) {
		if (!result.hasErrors())
		{
			FilePattern oldFilePattern= filePatternDao.selectByFilePattern(filePattern.getFilePattern());
			if (filePattern.getPatternId() == null) {
				if (oldFilePattern==null)
				{
					filePatternDao.insert(filePattern);
					saveMessage(request, "File Pattern đã được tạo thành công.");
				}
				else
				{
					saveMessage(request, "File Pattern đã tồn tại");
					model.addAttribute(filePattern);
					FilePatternsAddEdit(patternId, model);
					return "filePatternForm";
				}
	
			} else {
				if (oldFilePattern!=null&& !oldFilePattern.getPatternId().equals(filePattern.getPatternId()))
				{
					saveMessage(request, "File Pattern đã tồn tại");
					model.addAttribute(filePattern);
					FilePatternsAddEdit(patternId, model);
					return "filePatternForm";
				}
				filePatternDao.updateByPrimaryKey(filePattern);
				saveMessage(request, "FilePattern đã được cập nhật thành công.");
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute(filePattern);
			FilePatternsAddEdit(patternId, model);
			return "filePatternForm";
		}
	}
	
	
	
	// Upload File
		@RequestMapping(value = "upload", method = RequestMethod.GET)
		public String showUploadForm() {
			return "filePatternUpload";
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
							// Kiem tra số cột của file
				        	List heard= (List)sheetData.get(0);
				        	if (heard.size() != 12) {
				        		saveMessage(request, "Số lượng cột dữ liệu File_Pattern của file không đúng");
				        		return "filePatternUpload";
				        	}
				        	sheetData.remove(0);
				        	
				        	for (int i = 0; i < sheetData.size(); i++) {
				        		
				        		List list = (List) sheetData.get(i);
				    			
				        		if(list.size() != 12)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Bạn nhập thiếu thông tin");
				            		return "filePatternUpload";
				        		}
				        		
				        		//Tên file
				        		
				        		if(list.get(0).toString().length() > 150)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Tên file có độ dài quá 150");
				            		return "filePatternUpload";
				        		}
				        		if(list.get(0).toString().length() < 1)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu Tên báo cáo không có");
				            		return "filePatternUpload";
				        		}
				        		
				        		//Node mạng
				        		if(list.get(1).toString().length() > 30 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Node mạng có độ dài quá 30");
				            		return "filePatternUpload";
				        		}
				        		
				        		//Bảng dữ liệu
				        		if(list.get(2).toString().length() > 30 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Bảng dữ liệu có độ dài quá 30");
				            		return "filePatternUpload";
				        		}
				        		
				        		//Lớp convert
				        		if(list.get(3).toString().length() > 100 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Lớp convert có độ dài quá 100");
				            		return "filePatternUpload";
				        		}
				        		if(list.get(3).toString().length() < 1)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu Lớp convert không có");
				            		return "filePatternUpload";
				        		}
				        		
				        		//Thư mục con
				        		if(list.get(4).toString().length() > 50 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Tên Thư mục con có độ dài quá 50");
				            		return "filePatternUpload";
				        		}
			
				    			//STT lấy node
				    			if(list.get(5).toString().length() > 5)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "STT lấy node có độ dài quá 2 ký tự");
				            		return "filePatternUpload";
				        		}
				    			
				    			//STT lấy thời gian
				    			if(list.get(6).toString().length() > 5)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "STT lấy thời gian có độ dài quá 2 ký tự");
				            		return "filePatternUpload";
				        		}
				    			
				    			//Ký tự phân cách
				    			if(list.get(7).toString().length() > 5)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Ký tự phân cách có độ dài quá 5");
				            		return "filePatternUpload";
				        		}
				    			
				    			//Ký tự comment
				    			if(list.get(8).toString().length() > 5)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Ký tự comment có độ dài quá 5");
				            		return "filePatternUpload";
				        		}
				    			
				    			//Định dạng thời gian
				    			if(list.get(9).toString().length() > 30)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Định dạng thời gian có độ dài quá 30");
				            		return "filePatternUpload";
				        		}
				    			
				    			//Loại import
				    			if(!list.get(10).toString().toLowerCase().equals("file") && !list.get(10).toString().toLowerCase().equals("import mapping"))
				    			{
				    				model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu cột Loại import không đúng định dạng");
				            		return "filePatternUpload";
				    			}
				    			
				    			//Trạng thái
				    			if(!list.get(11).toString().toLowerCase().equals("không sử dụng") && !list.get(11).toString().toLowerCase().equals("đang sử dụng"))
				    			{
				    				model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu cột Trạng thái không đúng định dạng");
				            		return "filePatternUpload";
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
					return "filePatternUpload";
				}
			}
			else
			{
				saveMessage(request, "Import lỗi.");
			}
			return "filePatternUpload";
		}
		
		@SuppressWarnings({ "rawtypes" })
		private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
		{	
	        excecuteImport(sheetData, model, request);
	        
		}
		
		@SuppressWarnings("rawtypes")
		private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
		{
			List<FilePattern> filePatternList = new ArrayList<FilePattern>();
			
			for (int i = 0; i < sheetData.size(); i++) {
				FilePattern record = new FilePattern();
				
				List list = (List) sheetData.get(i);
				
				record.setFilePattern(list.get(0).toString());			
				record.setNodeType(list.get(1).toString());			
				record.setRawTable(list.get(2).toString());			
				record.setConvertClass(list.get(3).toString());			
				record.setSubDir(list.get(4).toString());
				String str1 = list.get(5).toString();
				record.setNodePatternGroup(Integer.parseInt(str1.substring(0,str1.indexOf("."))));	
				String str2 = list.get(6).toString();
				record.setTimePatternGroup(Integer.parseInt(str1.substring(0,str2.indexOf("."))));
				record.setSeparator(list.get(7).toString());
				record.setCommentChar(list.get(8).toString());
				record.setTimePattern(list.get(9).toString());
				if(list.get(10).toString().toLowerCase().equals("file"))
					record.setImportRule("F");
				else  if(list.get(10).toString().toLowerCase().equals("import mapping"))
					record.setImportRule("M");
				else  if(list.get(10).toString().toLowerCase().isEmpty())
					record.setImportRule("-");
				
				if(list.get(11).toString().toLowerCase().equals("không sử dụng"))
					record.setStatus("N");
				else  if(list.get(11).toString().toLowerCase().equals("đang sử dụng"))
					record.setStatus("Y");
				else  if(list.get(11).toString().toLowerCase().isEmpty())
					record.setStatus("-");
			
				
				filePatternDao.insert(record);
				
				
				filePatternList.add(record);
					
			}
			
			model.addAttribute("filePatternList",filePatternList);
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private List<FilePattern> errorlist(List sheet)
		{
			List<FilePattern> filePatternList = new ArrayList<FilePattern>();
			FilePattern record = new FilePattern();
			int size= sheet.size();
	    	for (int i=size;i<=11;i++)
	    		sheet.add("");
	    	record.setFilePattern(sheet.get(0).toString());			
			record.setNodeType(sheet.get(1).toString());			
			record.setRawTable(sheet.get(2).toString());			
			record.setConvertClass(sheet.get(3).toString());
			record.setSubDir(sheet.get(4).toString());			
			record.setNodePatternGroup(sheet.get(5).hashCode());			
			record.setTimePatternGroup(sheet.get(6).hashCode());	
			record.setSeparator(sheet.get(7).toString());
			record.setCommentChar(sheet.get(8).toString());
			record.setTimePattern(sheet.get(9).toString());
			record.setImportRule(sheet.get(10).toString());
			record.setStatus(sheet.get(11).toString());		
			filePatternList.add(record);
			return filePatternList;
		}
	}
