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
import vn.com.vhc.vmsc2.statistics.dao.S7linksetDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.S7linkset;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.S7linksetFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;



@Controller
@RequestMapping("/network/linkset/*")
public class S7linksetController extends BaseController {
	@Autowired
	private S7linksetDAO linksetDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") S7linksetFilter filter, Model model) {
		List<S7linkset> linksets = linksetDao.filter(filter);
		model.addAttribute("linksetList", linksets);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		
		return new ModelAndView("linksetList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String linksetid,@RequestParam(required = false) String nodeid, HttpServletRequest request) {

		linksetDao.deleteByPrimaryKey(linksetid, nodeid);
		saveMessage(request, "Linkset đã được xóa thành công.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String linksetid,@RequestParam(required = false) String nodeid, ModelMap model) {

		S7linkset linkset = (linksetid == null||nodeid == null) ? new S7linkset() : linksetDao.selectByPrimaryKey(linksetid,nodeid);
		model.addAttribute("linkset",linkset);
		return "linksetForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("linkset") @Valid S7linkset linkset, BindingResult result, HttpServletRequest request,Model model) {

		linkset.setLaunchDate(new Date());
		if (result.hasErrors())
		{	
			return "linksetForm";
		}
		else
		{
			S7linkset oldLinkset= linksetDao.selectByPrimaryKey(linkset.getLinksetid(),linkset.getNodeid());
			if (linkset.getRegion().length() == 0) {
				if(oldLinkset==null){
					linkset.setRegion("TT2");
					linksetDao.insert(linkset);
					saveMessage(request, "Linkset được thêm mới thành công.");
				}
				else{
					saveMessage(request,"Linkset đã tồn tại");
					model.addAttribute("linkset",linkset);
					return "linksetForm";
				}
			} else {
				if (oldLinkset!=null && !oldLinkset.getLinksetid().equals(linkset.getLinksetid()))
				{
					saveMessage(request,"Linkset đã tồn tại");
					model.addAttribute("linkset",linkset);
					return "linksetForm";
				}
				linksetDao.updateByPrimaryKey(linkset);
				saveMessage(request, "Linkset được cập nhật thành công.");
			}
	
			return "redirect:list.htm";
		}
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "linksetUpload";
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
			        	if (heard.size() != 4) {
			        		saveMessage(request, "Số lượng cột dữ liệu LINKSET của file không đúng");
			        		return "linksetUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 4)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu file Import bị thừa hoặc thiếu ! Xin kiểm tra lại.");
			            		return "linksetUpload";
			        		}
			        		
			        		//Linkset Id
			        	
			        		if(list.get(0).toString().length() > 15)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Linkset Id có độ dài quá 15");
			            		return "linksetUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Linkset Id không có");
			            		return "linksetUpload";
			        		}
			        		
			        		// Node Mạng
			        		
			        		if(list.get(1).toString().length() > 50)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Node Mạng có độ dài quá 50");
			            		return "linksetUpload";
			        		}
			        		if(list.get(1).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu Node Mạng không có");
			            		return "linksetUpload";
			        		}
			        		
			        		// Tên Linkset
			        		
			        		if(list.get(2).toString().length() > 100)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Tên Linkset có độ dài quá 100");
			            		return "linksetUpload";
			        		}
			        		
			        		// Số Link
			        		if(list.get(3).toString().length() > 10)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("LinksetList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Số Link có độ dài quá 10");
			            		return "linksetUpload";
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
				return "linksetUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "linksetUpload";
	}
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<S7linkset> S7linkset = new ArrayList<S7linkset>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			S7linkset record = new S7linkset();
			
			List list = (List) sheetData.get(i);
			
			record.setLinksetid(list.get(0).toString());
			record.setNodeid(list.get(1).toString());
			record.setLinksetName(list.get(2).toString());
			String str1 = list.get(3).toString();
			record.setLinkDevice(Integer.parseInt(str1.substring(0,str1.indexOf("."))));
			
			linksetDao.insert(record);			
			S7linkset.add(record);
				
		}
		
		model.addAttribute("S7linkset",S7linkset);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<S7linkset> errorlist(List sheet)
	{
		List<S7linkset> S7linkset = new ArrayList<S7linkset>();
		S7linkset record = new S7linkset();
		int size= sheet.size();
    	for (int i=size;i<=6;i++)
    		sheet.add("");
    	
    	record.setLinksetid(sheet.get(0).toString());
    	record.setNodeid(sheet.get(1).toString());
		record.setLinksetName(sheet.get(2).toString());
		record.setLinkDevice(sheet.get(3).hashCode());
		
		
		
		S7linkset.add(record);
		return S7linkset;
	}
}
