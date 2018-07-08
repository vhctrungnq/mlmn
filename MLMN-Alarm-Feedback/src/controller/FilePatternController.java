package controller;

import java.io.IOException;
import java.io.InputStreamReader;
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

import vo.CSystemConfigs;
import vo.FilePattern;
import vo.ImportMapping;
import vo.SYS_PARAMETER;
import vo.alarm.utils.FilePatternFilter;
import vo.alarm.utils.ImportMappingError;
import vo.alarm.utils.UploadTools;

import bsh.ParseException;

import com.csvreader.CsvReader;

import dao.FilePatternDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/cauhinh/filePattern/*")
public class FilePatternController extends BaseController {
	
	@Autowired
	private FilePatternDAO filePatternDao;

	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") FilePatternFilter filter, Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = filePatternDao.titleFilePatten(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "FILE_PATTERN";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("filePattern").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("filePattern").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<FilePattern> filePatterns = new ArrayList<FilePattern>();
		try
		{
			filePatterns = filePatternDao.getFilePatternList(filter, column, order);	
		}
		catch (Exception exp)
		{
			filePatterns= null;
		}
		
		model.addAttribute("filePatternList", filePatterns);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "FilePattern_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		return new ModelAndView("jsp/CauHinh/filePatternList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int patternId, HttpServletRequest request, ModelMap model) {

		try {
			filePatternDao.deleteByPrimaryKey(patternId);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer patternId, ModelMap model) {

		
		List<SYS_PARAMETER> sysParemeterTitle = filePatternDao.titleFilePatten("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		FilePattern filePattern =  new FilePattern();
		if (patternId !=null)
		{
			filePattern = filePatternDao.selectByPrimaryKey(patternId);
		}
		model.addAttribute("filePattern", filePattern);
		return "jsp/CauHinh/filePatternForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("filePattern") @Valid FilePattern filePattern,
			BindingResult result, 
			HttpServletRequest request,Model model) throws ParseException {	
		
		List<SYS_PARAMETER> sysParemeterTitle = filePatternDao.titleFilePatten("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		if (!result.hasErrors())
		{
			FilePattern oldFilePattern= filePatternDao.selectByFilePatternAndConvertClass(filePattern.getFilePattern(),filePattern.getConvertClass());
			if (filePattern.getPatternId() == null) {
				if (oldFilePattern==null)
				{
					filePatternDao.insert(filePattern);
					saveMessageKey(request, "systemConfig.insertSucceFull");
				}
				else
				{
					saveMessageKey(request, "filePattern.exits");
					model.addAttribute("titleF", title);
					model.addAttribute("filePattern", filePattern);
					return "jsp/CauHinh/filePatternForm";
				}
	
			} else {
				if (oldFilePattern!=null&& !oldFilePattern.getPatternId().equals(filePattern.getPatternId()))
				{
					saveMessageKey(request, "filePattern.exits");
					model.addAttribute("titleF", title);
					model.addAttribute("filePattern", filePattern);
					return "jsp/CauHinh/filePatternForm";
				}
				filePatternDao.updateByPrimaryKey(filePattern);
				saveMessageKey(request, "systemConfig.updateSucceFull");
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("titleF", title);
			model.addAttribute("filePattern", filePattern);
			return "jsp/CauHinh/filePatternForm";
		}
	}
	
	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> sysParemeterTitle = filePatternDao.titleFilePatten("UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		return "jsp/CauHinh/filePatternUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		List<SYS_PARAMETER> sysParemeterTitle = filePatternDao.titleFilePatten("UPLOAD");
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
			        	if (heard.size() != 12) {
			        		saveMessageKey(request, "cautruc.loiCautruc");
			        		return "jsp/CauHinh/filePatternUpload";
			        	}
			        	
			        	int maxsize=0;
		        		List footer= (List)sheetData.get(sheetData.size()-1);
		        		
		        		while (footer.size() != 12 && sheetData.size() > 2)
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
		        			if (footer.size() == 12 && kt == false)
		        			{
		        				break;
		        			}
		        			if (footer.size() > 12 && kt == false)
		        			{
		        				boolean kt2=true;
		        				for (int l=12;l<footer.size();l++)
		        					if (!footer.get(l).toString().trim().equals(""))
			        				{
			        					kt2=false;
			        					break;
			        				}
		        				if (kt2==false)
		        				{
			        				model.addAttribute("errorContent","Import không thành công. Cấu trúc dữ liệu trong file không đúng định dạng cho phép");
			        				return "jsp/CauHinh/filePatternUpload";
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
			        				for (int j=footer.size();j<=11;j++)
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
		     				for (int j=list.size();j<=11;j++)
		     				{
		     					list.add("");
		     				}
			    			
			        		//Bảng dữ liệu thô
			        		
			        		// Kiem tra kieu du lieu dâ thoa man chua
		     				if (list.get(0).toString().trim().length()>200||list.get(0).toString().trim().length()<1
		     						||list.get(1).toString().trim().length()>30
		     						||list.get(2).toString().trim().length()>30
		     						||list.get(3).toString().trim().length()>100||list.get(3).toString().trim().length()<1
		     						||list.get(4).toString().trim().length()>50
		     						||list.get(5).toString().trim().length()>2
		     						||list.get(6).toString().trim().length()>2
		     						||list.get(7).toString().trim().length()>5
		     						||list.get(8).toString().trim().length()>5
		     						||list.get(9).toString().trim().length()>30
		     						||list.get(10).toString().trim().length()>1
		     						||list.get(11).toString().trim().length()>1)
		     				{
		     					
		     					saveMessageKey(request, "cautruc.loiKichThuoc");
		     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
		     					model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
		     					return "jsp/CauHinh/filePatternUpload";
		     				}
		     				if (list.get(5).toString().trim()!=null && !list.get(5).toString().trim().equals(""))
		     				{
			     				try
			     				{
			     					int fileColumn = Integer.parseInt(list.get(5).toString().trim());
			     				}
			     				catch(Exception exp)
			     				{
			     					saveMessageKey(request, "filePattern.nodePatternGroupError");
			     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			     					model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
			     					return "jsp/CauHinh/filePatternUpload";
			     				}
		     				}
		     				if (list.get(6).toString().trim()!=null && !list.get(6).toString().trim().equals(""))
		     				{
			     				try
			     				{
			     					int fileColumn = Integer.parseInt(list.get(6).toString().trim());
			     				}
			     				catch(Exception exp)
			     				{
			     					saveMessageKey(request, "filePattern.timePatternGroupEror");
			     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			     					model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
			     					return "jsp/CauHinh/filePatternUpload";
			     				}
		     				}
		     				
		     				FilePattern oldFilePattern= filePatternDao.selectByFilePatternAndConvertClass(list.get(0).toString().trim(),list.get(3).toString().trim());
		     				if (oldFilePattern!=null)
		     				{
		     					saveMessageKey(request, "filePattern.exits");
		     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
		     					model.addAttribute("filePatternList", errorlist((List)sheetData.get(i)));
		     					return "jsp/CauHinh/filePatternUpload";
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
				return "jsp/CauHinh/filePatternUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "jsp/CauHinh/filePatternUpload";
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
		List<FilePattern> filePatternList = new ArrayList<FilePattern>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			
			FilePattern record = new FilePattern();
			List list = (List) sheetData.get(i);
		
			record.setFilePattern(list.get(0).toString().trim());
			record.setNodeType(list.get(1).toString().trim());
			record.setRawTable(list.get(2).toString().trim());
			record.setConvertClass(list.get(3).toString().trim());
			record.setSubDir(list.get(4).toString().trim());
			if (list.get(5).toString().trim()!=null && !list.get(5).toString().trim().equals(""))
			{
				record.setNodePatternGroup(Integer.parseInt(list.get(5).toString().trim()));
				record.setNodePatternGroupStr(list.get(5).toString().trim());
			}
			if (list.get(6).toString().trim()!=null && !list.get(6).toString().trim().equals(""))
			{
				record.setTimePatternGroup(Integer.parseInt(list.get(6).toString().trim()));
				record.setTimePatternGroupStr(list.get(6).toString().trim());
			}
			record.setSeparator(list.get(7).toString().trim());
			record.setCommentChar(list.get(8).toString().trim());
			record.setTimePattern(list.get(9).toString().trim());
			record.setImportRule(list.get(10).toString().trim());
			record.setStatus(list.get(11).toString().trim());
			
			filePatternDao.insert(record);
			filePatternList.add(record);
				
		}
		
		model.addAttribute("filePatternList",filePatternList);
        
	}
	
	//dang lam o day 
	private List<FilePattern> errorlist(List sheet)
	{
		List<FilePattern> filePatternList = new ArrayList<FilePattern>();
		FilePattern record = new FilePattern();
		int size= sheet.size();
    	for (int i=size;i<=11;i++)
    		sheet.add("");
    	record.setFilePattern(sheet.get(0).toString().trim());
		record.setNodeType(sheet.get(1).toString().trim());
		record.setRawTable(sheet.get(2).toString().trim());
		record.setConvertClass(sheet.get(3).toString().trim());
		record.setSubDir(sheet.get(4).toString().trim());
		if (sheet.get(5).toString().trim()!=null && !sheet.get(5).toString().trim().equals(""))
		{
			record.setNodePatternGroupStr(sheet.get(5).toString().trim());
		}
		if (sheet.get(6).toString().trim()!=null && !sheet.get(6).toString().trim().equals(""))
		{
			record.setTimePatternGroupStr(sheet.get(6).toString().trim());
		}
		record.setSeparator(sheet.get(7).toString().trim());
		record.setCommentChar(sheet.get(8).toString().trim());
		record.setTimePattern(sheet.get(9).toString().trim());
		record.setImportRule(sheet.get(10).toString().trim());
		record.setStatus(sheet.get(11).toString().trim());
		filePatternList.add(record);
		return filePatternList;
	}
}
