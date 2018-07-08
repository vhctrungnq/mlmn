package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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

import vo.HRouter;
import vo.ImportMapping;
import vo.SysGroupUser;
import vo.SysUserRoles;
import vo.SysUsers;
import vo.alarm.utils.HRouterFilter;
import vo.alarm.utils.UploadTools;

import com.csvreader.CsvReader;

import dao.HRouterDAO;


@Controller
@RequestMapping("/cauhinh/router/*")
public class HRouterController extends BaseController {
	@Autowired
	private HRouterDAO routerDAO;

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") HRouterFilter filter, Model model) {
		List<HRouter> routes =  routerDAO.filter(filter);
		model.addAttribute("routeList", routes);
		return new ModelAndView("jsp/routerList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,HttpServletRequest request) {

		routerDAO.deleteByPrimaryKey(id);
		saveMessage(request, "Router đã được xóa.");

		return "redirect:list.htm";
	}

	// Them, sua router
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {

		HRouter route = (id == null) ? new HRouter() : routerDAO.selectByPrimaryKey(id);
		model.addAttribute("route", route);
		return "jsp/routerForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("route")@Valid HRouter route, BindingResult result, 
			HttpServletRequest request,Model model) throws ParseException {
		if (!result.hasErrors())
		{
			HRouter oldRoute= routerDAO.selectByUnikey(route.getRouterName());
			if (route.getId()== null) {
				if (oldRoute == null) {
					routerDAO.insert(route);
					saveMessage(request, "Route khởi tạo thành công.");
				} else {
					saveMessage(request, "Route đã tồn tại");
					model.addAttribute(route);
					return "jsp/routerForm";
				}
			} else {
				/*if (oldRoute!=null && oldRoute.getRouterName().equals(route.getRouterName()))
				{
					saveMessage(request, "Route đã tồn tại");
					model.addAttribute(route);
					return "jsp/routerForm";
				}*/
				routerDAO.updateByPrimaryKey(route);
				saveMessage(request, "Route cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{
			saveMessage(request, " Cập nhật Route không thành công.");
			model.addAttribute(route);
			return "jsp/routerForm";
		}
	}
	// import file
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jsp/routerUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
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
			        	if (heard.size() != 5) {
			        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
			        		return "jsp/routerUpload";
			        	}
			        	sheetData.remove(0);
			        	
			        	for (int i = 0; i < sheetData.size(); i++) {
			        		
			        		List list = (List) sheetData.get(i);
			    			
			        		if(list.size() != 5)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("usersList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.thieuThongTin");
			            		return "jsp/routerUpload";
			        		}
			        		
			        		// Ten router
			        		if(routerDAO.selectByUnikey(list.get(0).toString()) != null)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.routerName");
			            		return "jsp/routerUpload";
			        		}
			        		if(list.get(0).toString().length() > 20)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.routerNameVuotMaxlength");
			            		return "jsp/routerUpload";
			        		}
			        		if(list.get(0).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.routerNameNull");
			            		return "jsp/routerUpload";
			        		}
			        		
			        		// Router id
			        		if(list.get(1).toString().length() > 20 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.routerIdVuotMaxlength");
			            		return "jsp/routerUpload";
			        		}
			        		if(list.get(1).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.routerIdNull");
			            		return "jsp/routerUpload";
			        		}
			        		
			        		// Device type
			        		if(list.get(2).toString().length() > 50 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.deviceTypeVuotMaxlength");
			            		return "jsp/routerUpload";
			        		}
			        		
			        		// Function
			        		if(list.get(3).toString().length() > 50 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.functionVuotMaxlength");
			            		return "jsp/routerUpload";
			        		}
			        		
			        		// OAM
			        		if(list.get(4).toString().length() > 15 )
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.oamVuotMaxlength");
			            		return "jsp/routerUpload";
			        		}
			        		if(list.get(4).toString().length() < 1)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.oamNull");
			            		return "jsp/routerUpload";
			        		}	    		
			        		
			        		// id
			        		/*if(routerDAO.selectByPrimaryKey(Integer.parseInt(list.get(5).toString())) != null)
			        		{
			        			model.addAttribute("errorContent", "Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
			    				model.addAttribute("routerList", errorlist((List)sheetData.get(i)));
			    				saveMessageKey(request, "message.alarmRouter.id");
			            		return "jsp/routerUpload";
			        		}*/
			    		}
			        	importXlsContent(sheetData, model,  request);
			        	saveMessageKey(request, "message.confirm.importFileSuccess");
					}
					else
						saveMessageKey(request, "sidebar.admin.userUploadErrorDontInsert");		
				}
				catch(IOException e)
				{
					saveMessageKey(request, "message.confirm.importFileFailure");
				}
			}
			else
			{
				saveMessageKey(request, "message.confirm.dontAccess");
				return "jsp/routerUpload";
			}
		}
		else
		{
			saveMessageKey(request, "message.confirm.importFileFailure");
		}
		return "redirect:list.htm";
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void importXlsContent(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{	
        excecuteImport(sheetData, model, request);
        
	}
	
	private void excecuteImport(List sheetData, Model model, HttpServletRequest request) throws ParseException
	{
		List<HRouter> routerList = new ArrayList<HRouter>();
		
		for (int i = 0; i < sheetData.size(); i++) {
			HRouter record = new HRouter();
			
			List list = (List) sheetData.get(i);
			
			record.setRouterName(list.get(0).toString());
			record.setRouterId(list.get(1).toString());
			record.setDeviceType(list.get(2).toString());
			record.setFunction(list.get(3).toString());
			record.setOam(list.get(4).toString());
			//record.setId(Integer.parseInt(list.get(5).toString()));
			
			routerDAO.insert(record);
			
			routerList.add(record);				
		}
		
		model.addAttribute("routerList",routerList);
	}
	
	private List<HRouter> errorlist(List sheet)
	{
		List<HRouter> routerList = new ArrayList<HRouter>();
		HRouter record = new HRouter();
		int size= sheet.size();
    	for (int i=size;i<=5;i++)
    		sheet.add("");
    	
    	record.setRouterName(sheet.get(0).toString());
    	record.setRouterId(sheet.get(1).toString());
    	record.setDeviceType(sheet.get(2).toString());
    	record.setFunction(sheet.get(4).toString());
    	record.setOam(sheet.get(5).toString());
    	
    	routerList.add(record);
		return routerList;
	}
	/*@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jsp/routerUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if (!file.isEmpty()) {
			
			String[] ten = file.getOriginalFilename().split("\\.");
			
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("csv")){
				try {
					CsvReader reader = new CsvReader(new InputStreamReader(file.getInputStream()));

					reader.readHeaders();

					while (reader.readRecord()) {
						
						HRouter route = new HRouter();
						
						route.setRouterId(reader.get(0));
						route.setRouterName(reader.get(1));
						route.setDeviceType(reader.get(2));
						route.setFunction(reader.get(3));
						route.setOam(reader.get(4));
						route.setId(Integer.parseInt(reader.get(5)));
						
						if (routerDAO.selectByPrimaryKey(Integer.parseInt(reader.get(5))) == null) {
							routerDAO.insert(route);
						} else {
							routerDAO.updateByPrimaryKey(route);
						}
					}

					// TODO: log in upload log table
					reader.close();

					saveMessage(request, "Upload thành công!");
				} catch (IOException e) {
					saveMessage(request, "Upload lỗi!");
				}
				
			}else
			{
				saveMessageKey(request, "Sai định dạng file Upload!");
				return "jsp/routerUpload";
			}			
		}else
		{
			saveMessageKey(request, "Upload lỗi!");
		}

		return "redirect:list.htm";
	}*/
}