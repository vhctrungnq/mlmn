package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.Stp;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.StpFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;


@Controller
@RequestMapping("/network/stp/*")
public class StpController extends BaseController {
	@Autowired
	private StpDAO stpDao;

	@Autowired
	private SysUsersDAO sysUsersDao;

	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") StpFilter filter, Model model) {
		List<Stp> stps = stpDao.filter(filter);
		model.addAttribute("stpList", stps);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		return new ModelAndView("stpList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String stpid, HttpServletRequest request) {

		stpDao.deleteByPrimaryKey(stpid);
		saveMessage(request, "Stp đã được xóa thành công.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String stpid, ModelMap model) {

		Stp stp = (stpid == null) ? new Stp() : stpDao.selectByPrimaryKey(stpid);
		model.addAttribute(stp);
		return "stpForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid Stp stp, BindingResult result, HttpServletRequest request,Model model) {
			stp.setCreateDate(new Date());
			if (!result.hasErrors())
			{
				Stp oldStp=stpDao.selectByPrimaryKey(stp.getStpid());
				if (stp.getRegion().length() == 0) {
					stp.setRegion("TT2");
					if (oldStp == null) {
						stpDao.insert(stp);
						saveMessage(request, "STP đã được tạo thành công.");
					} else {
						saveMessage(request, "STP đã tồn tại.");
						model.addAttribute(stp);
						return "stpForm";
					}
		
				} else {
					if (oldStp!=null && !oldStp.getStpid().equals(stp.getStpid()))
					{
						saveMessage(request, "STP đã tồn tại.");
						model.addAttribute(stp);
						return "stpForm";
					}
					stpDao.updateByPrimaryKey(stp);
					saveMessage(request, "STP đã cập nhật thành công.");
				}
		
				return "redirect:list.htm";
			}
			else
			{
				/*saveMessage(request, "Cập nhật STP không thành công.");*/
				model.addAttribute(stp);
				return "stpForm";
			}
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "stpUpload";
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
			        	if (heard.size() != 10) {
			        		saveMessage(request, "Số lượng cột dữ liệu STP của file không đúng");
			        		return "stpUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 10)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu file Import bị thừa hoặc thiếu ! Xin kiểm tra lại.");
			            		return "stpUpload";
			        		}
			        		
			        		//Tên STP
			        		if(stpDao.selectByPrimaryKey(list.get(0).toString()) != null)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "STP đã tồn tại");
			            		return "stpUpload";
			        		}
			        		if(list.get(0).toString().length() > 60)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "STP có độ dài quá 60");
			            		return "stpUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu STP không có");
			            		return "stpUpload";
			        		}
			        		
			        		// SLIID
			        		
			        		if(list.get(1).toString().length() > 30)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "SLIID có độ dài quá 30");
			            		return "stpUpload";
			        		}
			        		
			        		// Nhà sản xuất
			        		
			        		if(list.get(2).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Nhà sản xuất có độ dài quá 20");
			            		return "stpUpload";
			        		}
			        		
			        		// Phiên bản phần cứng
			        		if(list.get(3).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Phiên bản phần cứng có độ dài quá 20");
			            		return "stpUpload";
			        		}
			        		
			        		// Phiên bản phần mềm
			        		if(list.get(4).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Phiên bản phần mềm có độ dài quá 20");
			            		return "stpUpload";
			        		}
			        		
			        		// Vị trí lắp đặt
			        		if(list.get(5).toString().length() > 80)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Vị trị lắp đặt có độ dài quá 80");
			            		return "stpUpload";
			        		}
			        		
			        		// Dung lượng báo hiệu 64K
			        		if(list.get(6).toString().length() > 4)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dung lượng báo hiệu 64K có độ dài quá 4");
			            		return "stpUpload";
			        		}
			        		
			        		// Dung lượng báo hiệu HSL
			        		if(list.get(7).toString().length() > 4)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dung lượng báo hiệu HSL có độ dài quá 4");
			            		return "stpUpload";
			        		}
			        		
			        		// Số STM
			        		if(list.get(8).toString().length() > 4)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Số STM có độ dài quá 4");
			            		return "stpUpload";
			        		}
			        		
			        		// Số STEB
			        		if(list.get(9).toString().length() > 4)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("StpList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Số STEB có độ dài quá 4");
			            		return "stpUpload";
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
				return "stpUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "stpUpload";
	}
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<Stp> Stp = new ArrayList<Stp>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			Stp record = new Stp();
			
			List list = (List) sheetData.get(i);
			
			record.setStpid(list.get(0).toString());
			record.setSliid(list.get(1).toString());
			record.setVendor(list.get(2).toString());
			record.setHardwareVersion(list.get(3).toString());
			record.setSoftwareVersion(list.get(4).toString());
			record.setLocation(list.get(5).toString());
			record.setType64k(Float.parseFloat(list.get(6).toString()));
			record.setTypeHsl(Float.parseFloat(list.get(7).toString()));
			record.setNoStm(Float.parseFloat(list.get(8).toString()));
			record.setNoSteb(Float.parseFloat(list.get(9).toString()));
			

			
			stpDao.insert(record);			
			Stp.add(record);
				
		}
		
		model.addAttribute("Stp",Stp);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Stp> errorlist(List sheet)
	{
		List<Stp> Stp = new ArrayList<Stp>();
		Stp record = new Stp();
		int size= sheet.size();
    	for (int i=size;i<=3;i++)
    		sheet.add("");
    	
    	record.setStpid(sheet.get(0).toString());
    	record.setSliid(sheet.get(1).toString());
		record.setVendor(sheet.get(2).toString());
		record.setHardwareVersion(sheet.get(3).toString());
		record.setSoftwareVersion(sheet.get(4).toString());
		record.setLocation(sheet.get(5).toString());
		record.setType64k(Float.parseFloat(sheet.get(6).toString()));
		record.setTypeHsl(Float.parseFloat(sheet.get(7).toString()));
		record.setNoStm(Float.parseFloat(sheet.get(8).toString()));
		record.setNoSteb(Float.parseFloat(sheet.get(9).toString()));
		

		
		
		Stp.add(record);
		return Stp;
	}
}
