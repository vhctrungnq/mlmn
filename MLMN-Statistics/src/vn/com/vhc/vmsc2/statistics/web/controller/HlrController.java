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
import vn.com.vhc.vmsc2.statistics.dao.HlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hlr;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.HlrFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;



@Controller
@RequestMapping("/network/hlr/*")
public class HlrController extends BaseController {
	@Autowired
	private HlrDAO hlrDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") HlrFilter filter, Model model) {
		List<Hlr> hlrs = hlrDao.filter(filter);
		model.addAttribute("hlrList", hlrs);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		return new ModelAndView("hlrList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String hlrid, HttpServletRequest request) {

		hlrDao.deleteByPrimaryKey(hlrid);
		saveMessage(request, "HLR đã xóa.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String hlrid, ModelMap model) {

		Hlr hlr = (hlrid == null) ? new Hlr() : hlrDao.selectByPrimaryKey(hlrid);
		model.addAttribute(hlr);
		return "hlrForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid Hlr hlr, BindingResult result, HttpServletRequest request,Model model) {
		if (!result.hasErrors())
		{
			Hlr oldHlr= hlrDao.selectByPrimaryKey(hlr.getHlrid());
			if (hlr.getRegion().length() == 0 ) {
				if(oldHlr==null){
					hlr.setRegion("TT2");
					hlrDao.insert(hlr);
					saveMessage(request, "HLR khởi tạo thành công.");
				}
				else{
					saveMessage(request,"HLR đã tồn tại");
					model.addAttribute(hlr);
					return "hlrForm";
				}
			}
			else
			{
				
				if (oldHlr!=null && !oldHlr.getHlrid().equals(hlr.getHlrid()))
				{
					saveMessage(request,"HLR đã tồn tại");
					model.addAttribute(hlr);
					return "hlrForm";
				}
				 hlrDao.updateByPrimaryKey(hlr);
				 saveMessage(request, "HLR cập nhật thành công.");
				}
				return "redirect:list.htm";
		}
		else
		{
			/*saveMessage(request, "Cập nhật HLR không thành công.");*/
			model.addAttribute(hlr);
			return "hlrForm";
		}
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "hlrUpload";
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
			        	if (heard.size() != 5) {
			        		saveMessage(request, "Số lượng cột dữ liệu HLR của file không đúng");
			        		return "hlrUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 5)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu file Import bị thừa hoặc thiếu ! Xin kiểm tra lại.");
			            		return "hlrUpload";
			        		}
			        		
			        		//HLR
			        		if(hlrDao.selectByPrimaryKey(list.get(0).toString()) != null)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "HLR đã tồn tại");
			            		return "hlrUpload";
			        		}
			        		if(list.get(0).toString().length() > 50)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "HLR có độ dài quá 50");
			            		return "hlrUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu HLR không có");
			            		return "hlrUpload";
			        		}
			        		// Nhà sản xuất
			        		
			        		if(list.get(1).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Nhà sản xuất có độ dài quá 20");
			            		return "hlrUpload";
			        		}
			        		
			        		// Phần cứng
			        		if(list.get(2).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Phần cứng có độ dài quá 20");
			            		return "hlrUpload";
			        		}
			        		
			        		// Phần mềm
			        		if(list.get(3).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Phần mềm có độ dài quá 20");
			            		return "hlrUpload";
			        		}
			        		
			        		// Vị trí lắp đặt
			        		if(list.get(4).toString().length() > 80)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("HlrList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Vị trị lắp đặt có độ dài quá 80");
			            		return "hlrUpload";
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
				return "hlrUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "hlrUpload";
	}
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<Hlr> Hlr = new ArrayList<Hlr>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			Hlr record = new Hlr();
			
			List list = (List) sheetData.get(i);
			
			record.setHlrid(list.get(0).toString());
			record.setVendor(list.get(1).toString());
			record.setHardwareVersion(list.get(2).toString());
			record.setSoftwareVersion(list.get(3).toString());
			record.setLocation(list.get(4).toString());

			
			hlrDao.insert(record);			
			Hlr.add(record);
				
		}
		
		model.addAttribute("Hlr",Hlr);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Hlr> errorlist(List sheet)
	{
		List<Hlr> Hlr = new ArrayList<Hlr>();
		Hlr record = new Hlr();
		int size= sheet.size();
    	for (int i=size;i<=4;i++)
    		sheet.add("");
    	
    	record.setHlrid(sheet.get(0).toString());
		record.setVendor(sheet.get(1).toString());
		record.setHardwareVersion(sheet.get(2).toString());
		record.setSoftwareVersion(sheet.get(2).toString());
		record.setLocation(sheet.get(4).toString());

		
		
		Hlr.add(record);
		return Hlr;
	}
}