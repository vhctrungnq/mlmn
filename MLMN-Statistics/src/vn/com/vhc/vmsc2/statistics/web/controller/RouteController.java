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
import vn.com.vhc.vmsc2.statistics.dao.RouteDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.Route;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.RouteFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;


@Controller
@RequestMapping("/network/route/*")
public class RouteController extends BaseController {
	@Autowired
	private RouteDAO routeDao;
	@Autowired
	private SysUsersDAO sysUsersDao;

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") RouteFilter filter, Model model) {
		List<Route> routes = routeDao.filter(filter);
		model.addAttribute("routeList", routes);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		
		return new ModelAndView("routeList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String routeid, @RequestParam(required = false) String fromMscid,
			@RequestParam(required = false) String toMscid, HttpServletRequest request) {

		routeDao.deleteByPrimaryKey(fromMscid, routeid, toMscid);
		saveMessage(request, "Route đã được xóa.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String routeid, @RequestParam(required = false) String fromMscid,
			@RequestParam(required = false) String toMscid, ModelMap model) {

		Route route = (routeid == null) ? new Route() : routeDao.selectByPrimaryKey(fromMscid, routeid, toMscid);
		model.addAttribute(route);
		return "routeForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("route")@Valid Route route, BindingResult result, HttpServletRequest request,Model model) {
		route.setLaunchDate(new Date());
		if (!result.hasErrors())
		{
			Route oldRoute= routeDao.selectByPrimaryKey(route.getFromMscid(), route.getRouteid(), route.getToMscid());
			if (route.getRegion().length() == 0) {
				if (oldRoute == null) {
					route.setRegion("TT2");
					routeDao.insert(route);
					saveMessage(request, "Route khởi tạo thành công.");
				} else {
					saveMessage(request, "Route đã tồn tại");
					model.addAttribute(route);
					return "routeForm";
				}
			} else {
				if (oldRoute!=null && !oldRoute.getRouteid().equals(route.getRouteid()))
				{
					saveMessage(request, "Route đã tồn tại");
					model.addAttribute(route);
					return "routeForm";
				}
				routeDao.updateByPrimaryKey(route);
				saveMessage(request, "Route cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{ 
			model.addAttribute(route);
			return "routeForm";
		}
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "routeUpload";
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
			        		saveMessage(request, "Số lượng cột dữ liệu ROUTE của file không đúng");
			        		return "routeUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 5)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu file Import bị thừa hoặc thiếu ! Xin kiểm tra lại.");
			            		return "routeUpload";
			        		}
			        		
			        		//ROUTE
			 
			        		if(list.get(0).toString().length() > 50)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "ROUTE có độ dài quá 50");
			            		return "routeUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu ROUTE không có");
			            		return "routeUpload";
			        		}
			        		
			        		// ĐIỂM ĐẦU
			        		
			        		if(list.get(1).toString().length() > 50)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "ĐIỂM ĐẦU có độ dài quá 50");
			            		return "routeUpload";
			        		}
			        		if(list.get(1).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu ĐIỂM ĐẦU không có");
			            		return "routeUpload";
			        		}
			        		
			        		// ĐIỂM CUỐI
			        		
			        		if(list.get(2).toString().length() > 30)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "ĐIỂM CUỐI có độ dài quá 30");
			            		return "routeUpload";
			        		}
			        		if(list.get(2).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "Dữ liệu ĐIỂM CUỐI không có");
			            		return "routeUpload";
			        		}
			        		
			        		// TÊN ROUTE
			        		if(list.get(3).toString().length() > 50)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "TÊN ROUTE có độ dài quá 50");
			            		return "routeUpload";
			        		}
			        		
			        		// DEV
			        		if(list.get(4).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("RouteList", errorlist((List)sheetData.get(i)));
			    				saveMessage(request, "DEV có độ dài quá 20");
			            		return "routeUpload";
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
				return "routeUpload";
			}
		}
		else
		{
			saveMessage(request, "Import lỗi.");
		}
		return "routeUpload";
	}
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	@SuppressWarnings("rawtypes")
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<Route> Route = new ArrayList<Route>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			Route record = new Route();
			
			List list = (List) sheetData.get(i);
			
			record.setRouteid(list.get(0).toString());
			record.setFromMscid(list.get(1).toString());
			record.setToMscid(list.get(2).toString());
			record.setRoutename(list.get(3).toString());
			record.setDev(list.get(4).toString());
			
			routeDao.insert(record);			
			Route.add(record);
				
		}
		
		model.addAttribute("Route",Route);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Route> errorlist(List sheet)
	{
		List<Route> Route = new ArrayList<Route>();
		Route record = new Route();
		int size= sheet.size();
    	for (int i=size;i<=4;i++)
    		sheet.add("");
    	
    	record.setRouteid(sheet.get(0).toString());
    	record.setFromMscid(sheet.get(1).toString());
		record.setToMscid(sheet.get(2).toString());
		record.setRoutename(sheet.get(3).toString());
		record.setDev(sheet.get(4).toString());
		
		

		
		
		Route.add(record);
		return Route;
	}
}