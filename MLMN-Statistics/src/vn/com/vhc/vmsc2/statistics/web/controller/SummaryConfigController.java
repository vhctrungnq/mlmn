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

import bsh.ParseException;


import vn.com.vhc.vmsc2.statistics.dao.SummaryConfigDAO;
import vn.com.vhc.vmsc2.statistics.domain.SummaryConfig;
import vn.com.vhc.vmsc2.statistics.web.filter.SummaryConfigFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;


@Controller
@RequestMapping("/system/kpi/*")
public class SummaryConfigController extends BaseController {
	private SummaryConfigDAO summaryConfigDao;

	@Autowired
	public void setSummaryConfigDao(SummaryConfigDAO summaryConfigDao) {
		this.summaryConfigDao = summaryConfigDao;
	}

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") SummaryConfigFilter filter, Model model) {
		List<SummaryConfig> summaryConfigs = summaryConfigDao.filter(filter);
		model.addAttribute("summaryConfigList", summaryConfigs);
		return new ModelAndView("summaryConfigList");
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer configId, ModelMap model, HttpServletResponse response) {
		SummaryConfig summaryConfig = (configId == null) ? new SummaryConfig() : summaryConfigDao.selectByPrimaryKey(configId);
		model.addAttribute(summaryConfig);
		return "summaryConfigForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid SummaryConfig summaryConfig, BindingResult result, HttpServletRequest request,Model model, HttpServletResponse response) {

		SummaryConfig oldSummaruConfig=summaryConfigDao.exitSummaryConfig(summaryConfig.getTableName(), summaryConfig.getColumnName());
		if (!result.hasErrors())
		{
			
			if (summaryConfig.getConfigId() == null) {
				if (oldSummaruConfig==null)
				{
					summaryConfigDao.insert(summaryConfig);
					saveMessage(request, "SummaryConfig được tạo thành công");
				}
				else
				{
					saveMessage(request, "Đã tồn tại trường: "+oldSummaruConfig.getColumnName()+" trong bảng Summary: "+oldSummaruConfig.getTableName());
					model.addAttribute(summaryConfig);
					return "summaryConfigForm";
				}
	
			} else {
				if (oldSummaruConfig!=null && !oldSummaruConfig.getConfigId().equals(summaryConfig.getConfigId()))
				{
					saveMessage(request, "Đã tồn tại trường: "+oldSummaruConfig.getColumnName()+" trong bảng Summary: "+oldSummaruConfig.getTableName());
					model.addAttribute(summaryConfig);
					return "summaryConfigForm";
				}
				else
				{
					summaryConfigDao.updateByPrimaryKey(summaryConfig);
					saveMessage(request, "SummaryConfig được cập nhật thành công");
				}
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute(summaryConfig);
			return "summaryConfigForm";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int configId, HttpServletRequest request) {

		summaryConfigDao.deleteByPrimaryKey(configId);
		saveMessage(request, "SummaryConfig đã được xóa");

		return "redirect:list.htm";
	}

	// Upload File
		@RequestMapping(value = "upload", method = RequestMethod.GET)
		public String showUploadForm() {
			return "summaryConfigUpload";
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
				        	if (heard.size() != 8) {
				        		saveMessage(request, "Số lượng cột dữ liệu KPI của file không đúng");
				        		return "summaryConfigUpload";
				        	}
				        	sheetData.remove(0);
				        	
				        	for (int i = 0; i < sheetData.size(); i++) {
				        		
				        		List list = (List) sheetData.get(i);
				    			
				        		if(list.size() != 8)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Bạn nhập thiếu thông tin");
				            		return "summaryConfigUpload";
				        		}
				        		SummaryConfig oldsummaryConfig= summaryConfigDao.exitSummaryConfig(list.get(0).toString(),list.get(1).toString());
				        		if(oldsummaryConfig != null)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Summary đã tồn tại");
				            		return "summaryConfigUpload";
				        		}
				        		//Tên bảng Summary
				        		if(list.get(0).toString().length() > 30)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Summary có độ dài quá 30");
				            		return "summaryConfigUpload";
				        		}
				        		if(list.get(0).toString().length() < 1)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu Summary không có");
				            		return "summaryConfigUpload";
				        		}
				        		
				        		//Tên cột Summary
				        		if(list.get(1).toString().length() > 30 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Tên cột có độ dài quá 30");
				            		return "summaryConfigUpload";
				        		}
				        		if(list.get(1).toString().length() < 1)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu Tên cột không có");
				            		return "summaryConfigUpload";
				        		}
				        		
				        		//Kiểu dữ liệu
				        		if(list.get(2).toString().length() > 20 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Kiểu dữ liệu có độ dài quá 20");
				            		return "summaryConfigUpload";
				        		}
				        		if(list.get(2).toString().length() < 1)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu Kiểu dữ liệu không có");
				            		return "summaryConfigUpload";
				        		}
				        		
				        		//Bảng dữ liệu thô
				        		if(list.get(3).toString().length() > 30 )
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Bảng dữ liệu thô có độ dài quá 30");
				            		return "summaryConfigUpload";
				        		}
			
				    			//Công thức Summary
				    			if(list.get(4).toString().length() > 500)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Công thức summary có độ dài quá 500");
				            		return "summaryConfigUpload";
				        		}
				    			
				    			//Kiểu Summary
				    			if(list.get(5).toString().length() > 4)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Kiểu Summary có độ dài quá 4");
				            		return "summaryConfigUpload";
				        		}
				    			
				    			//Trạng thái
				    			if(!list.get(6).toString().toLowerCase().equals("không sử dụng") && !list.get(6).toString().toLowerCase().equals("đang sử dụng"))
				    			{
				    				model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Dữ liệu cột trạng thái không đúng định dạng");
				            		return "summaryConfigUpload";
				    			}
				    			
				    			//Ghi chú
				    			if(list.get(7).toString().length() > 512)
				        		{
				        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
				    				model.addAttribute("summaryConfigList", errorlist((List)sheetData.get(i)));
				    				saveMessage(request, "Ghi chú có độ dài quá 512");
				            		return "summaryConfigUpload";
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
					return "summaryConfigUpload";
				}
			}
			else
			{
				saveMessage(request, "Import lỗi.");
			}
			return "summaryConfigUpload";
		}
		
		@SuppressWarnings({ "rawtypes" })
		private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
		{	
	        excecuteImport(sheetData, model, request);
	        
		}
		
		@SuppressWarnings("rawtypes")
		private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
		{
			List<SummaryConfig> summaryConfigList = new ArrayList<SummaryConfig>();
			
			for (int i = 0; i < sheetData.size(); i++) {
				SummaryConfig record = new SummaryConfig();
				
				List list = (List) sheetData.get(i);
				
				record.setTableName(list.get(0).toString());
				
				record.setColumnName(list.get(1).toString());
				
				record.setDataType(list.get(2).toString());
				
				record.setTableSource(list.get(3).toString());
				
				record.setFormula(list.get(4).toString());
				
				record.setSummaryType(list.get(5).toString());
				
				if(list.get(6).toString().toLowerCase().equals("không sử dụng"))
					record.setStatus("N");
				else  if(list.get(6).toString().toLowerCase().equals("đang sử dụng"))
					record.setStatus("Y");
				else  if(list.get(6).toString().toLowerCase().isEmpty())
					record.setStatus("-");
				
				record.setDescription(list.get(7).toString());

				summaryConfigDao.insert(record);
				
				
				summaryConfigList.add(record);
					
			}
			
			model.addAttribute("summaryConfigList",summaryConfigList);
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private List<SummaryConfig> errorlist(List sheet)
		{
			List<SummaryConfig> summaryConfigList = new ArrayList<SummaryConfig>();
			SummaryConfig record = new SummaryConfig();
			int size= sheet.size();
	    	for (int i=size;i<=7;i++)
	    		sheet.add("");
			record.setTableName(sheet.get(0).toString());
			record.setColumnName(sheet.get(1).toString());
			record.setDataType(sheet.get(2).toString());
			record.setTableSource(sheet.get(3).toString());	
			record.setFormula(sheet.get(4).toString());
			record.setSummaryType(sheet.get(5).toString());
			record.setStatus(sheet.get(6).toString());
			record.setDescription(sheet.get(7).toString());
			summaryConfigList.add(record);
			return summaryConfigList;
		}
}
