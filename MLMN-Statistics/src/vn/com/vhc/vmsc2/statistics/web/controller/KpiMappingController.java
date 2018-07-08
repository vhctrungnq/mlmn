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
import vn.com.vhc.vmsc2.statistics.dao.KpiMappingDAO;
import vn.com.vhc.vmsc2.statistics.domain.KpiMapping;
import vn.com.vhc.vmsc2.statistics.web.filter.KpiMappingFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;
@Controller
@RequestMapping("/system/kpimapping/*")
public class KpiMappingController extends BaseController{
	private KpiMappingDAO kpimappinglistDao;
	
	@Autowired
	public void setKpiMappingDao(KpiMappingDAO kpimappinglistDao) {
		this.kpimappinglistDao = kpimappinglistDao;
	}

	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") KpiMappingFilter filter, Model model) {
		List<KpiMapping> KpiMaplists = kpimappinglistDao.filter(filter);
		model.addAttribute("KpiMaplists", KpiMaplists);
		
		return new ModelAndView("kpimappingList");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer configId, ModelMap model, HttpServletResponse response) {
		
		KpiMapping kpiMapping = (configId == null) ? new KpiMapping() : kpimappinglistDao.selectByPrimaryKey(configId);
		model.addAttribute("kpiMapping", kpiMapping);
		
		return "kpiMappingForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit( @ModelAttribute("kpiMapping") @Valid KpiMapping kpiMapping, BindingResult result, HttpServletRequest request,Model model,HttpServletResponse response) {	
		
		/*KpiMapping og=kpimappinglistDao.exitKpiMapping(kpiMappingldKpiMappin.getReportName());*/
		if (!result.hasErrors())
		{
			KpiMapping oldKpiMapping= kpimappinglistDao.selectByPrimaryKey(kpiMapping.getConfigId());
			if (kpiMapping.getConfigId() == null) {
				if (oldKpiMapping==null)
				{
					kpimappinglistDao.insert(kpiMapping);
					saveMessage(request, "KpiMapping được tạo thành công");
				}
				else
				{
					saveMessage(request, "KpiMapping đã tồn tại");
					model.addAttribute(kpiMapping);
					return "kpiMappingForm";
				}
	
			} else {
				if (oldKpiMapping!=null && !oldKpiMapping.getConfigId().equals(kpiMapping.getConfigId()))
				{
					saveMessage(request, "KpiMapping đã tồn tại");
					model.addAttribute(kpiMapping);
					return "kpiMappingForm";
				}
				else
				{
					kpimappinglistDao.updateByPrimaryKey(kpiMapping);
					saveMessage(request, "KpiMapping được cập nhật thành công");
				}
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute(kpiMapping);
			return "kpiMappingForm";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int configId, HttpServletRequest request, ModelMap model) {
		kpimappinglistDao.deleteByPrimaryKey(configId);
		saveMessage(request, "KpiMapping được xóa thành công.");
		return "redirect:list.htm";
	}


	
	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "KpimappingUpload";
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
			        	if (heard.size() != 11) {
			        		saveMessage(request, "Số lượng cột dữ liệu KPI_MAPPING của file không đúng");
			        		return "KpimappingUpload";
			        	}
					
			        	sheetData.remove(0);
						
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 11)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Bạn nhập thiếu thông tin");
			            		return "KpimappingUpload";
			        		}
			        	
			        		//REPORT_NAME
			        		
			        		if(list.get(0).toString().length() > 500)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên báo cáo có độ dài quá 500");
			            		return "KpimappingUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Tên báo cáo không có");
			            		return "KpimappingUpload";
			        		}
			        		
			        		//REPORT_NAME_COLUMN
			        		if(list.get(1).toString().length() > 100 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên cột báo cáo có độ dài quá 100");
			            		return "KpimappingUpload";
			        		}
			        		if(list.get(1).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Tên cột báo cáo không có");
			            		return "KpimappingUpload";
			        		}
			        		
			        		//FORMULA
			        		if(list.get(2).toString().length() > 700 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Công thức có độ dài quá 700");
			            		return "KpimappingUpload";
			        		}
			        		
			        		//Vendor
			        		if(list.get(3).toString().length() > 30 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Nhà sản xuất có độ dài quá 30");
			            		return "KpimappingUpload";
			        		}
			        		if(list.get(3).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Nhà sản xuất không có");
			            		return "KpimappingUpload";
			        		}
			        		
			        		//TABLE_NAME
			        		if(list.get(4).toString().length() > 30 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên bảng có độ dài quá 30");
			            		return "KpimappingUpload";
			        		}
			        		if(list.get(4).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu TABLE_NAME không có");
			            		return "KpimappingUpload";
			        		}
		
			    			//TABLE_COLUMN
			    			if(list.get(5).toString().length() > 50)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên cột có độ dài quá 50");
			            		return "KpimappingUpload";
			        		}
			    			if(list.get(5).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Tên cột không có");
			            		return "KpimappingUpload";
			        		}
			    			
			    			//DATABASE_FORMULA
			    			if(list.get(6).toString().length() > 700)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Công thức CSDL có độ dài quá 700");
			            		return "KpimappingUpload";
			        		}
			    			
			    			//STATUS
			    			if(!list.get(7).toString().toLowerCase().equals("không sử dụng") && !list.get(7).toString().toLowerCase().equals("đang sử dụng"))
			    			{
			    				model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu cột Trạng thái không đúng định dạng");
			            		return "KpimappingUpload";
			    			}
			    			
			    			//DESCRIPTION
			    			if(list.get(8).toString().length() > 300)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Ghi chú có độ dài quá 300");
			            		return "KpimappingUpload";
			        		}
			    			
			    			//NETWORK
			    			if(list.get(9).toString().length() > 30)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên mạng có độ dài quá 30");
			            		return "KpimappingUpload";
			        		}
			    			
			    			//VERSION
			    			if(list.get(10).toString().length() > 10)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("KpiMaplists", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên Phiên bản có độ dài quá 10");
			            		return "KpimappingUpload";
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
				return "KpimappingUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "KpimappingUpload";
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<KpiMapping> KpiMaplists = new ArrayList<KpiMapping>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			KpiMapping record = new KpiMapping();
			
			List list = (List) sheetData.get(i);
			
			record.setReportName(list.get(0).toString());			
			record.setReportNameColumn(list.get(1).toString());			
			record.setFormula(list.get(2).toString());			
			record.setVendor(list.get(3).toString());			
			record.setTableName(list.get(4).toString());			
			record.setTableColumn(list.get(5).toString());			
			record.setDatabaseFormula(list.get(6).toString());			
			if(list.get(7).toString().toLowerCase().equals("không sử dụng"))
				record.setStatus("N");
			else  if(list.get(7).toString().toLowerCase().equals("đang sử dụng"))
				record.setStatus("Y");
			else  if(list.get(7).toString().toLowerCase().isEmpty())
				record.setStatus("-");
			
			record.setDescription(list.get(8).toString());
			record.setNetwork(list.get(9).toString());
			record.setVersion(list.get(10).toString());
			
			kpimappinglistDao.insert(record);
			
			
			KpiMaplists.add(record);
				
		}
		
		model.addAttribute("KpiMaplists",KpiMaplists);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<KpiMapping> errorlist(List sheet)
	{
		List<KpiMapping> KpiMaplists = new ArrayList<KpiMapping>();
		KpiMapping record = new KpiMapping();
		int size= sheet.size();
    	for (int i=size;i<=10;i++)
    		sheet.add("");
    	record.setReportName(sheet.get(0).toString());			
		record.setReportNameColumn(sheet.get(1).toString());			
		record.setFormula(sheet.get(2).toString());			
		record.setVendor(sheet.get(3).toString());
		record.setTableName(sheet.get(4).toString());			
		record.setTableColumn(sheet.get(5).toString());			
		record.setDatabaseFormula(sheet.get(6).toString());	
		record.setStatus(sheet.get(7).toString());
		record.setDescription(sheet.get(8).toString());
		record.setNetwork(sheet.get(9).toString());			
		record.setVersion(sheet.get(10).toString());	
		KpiMaplists.add(record);
		return KpiMaplists;
	}
}
