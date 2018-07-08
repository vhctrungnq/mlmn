package controller;

import java.io.IOException;
import java.text.DateFormat;
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

import vo.AlShiftFes; 
import vo.CHighlightConfigs; 
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.AlShiftFesFilter;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;
import bsh.ParseException;
import dao.AlShiftFesDAO;
import dao.CHighlightConfigsDAO;
import dao.HContactNumberDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/system/shiftFes/*")
public class AlShiftFesController extends BaseController {
	@Autowired
	private AlShiftFesDAO alShiftFesDao;  
	@Autowired
	private HContactNumberDAO hContactNumberDao;  
	@Autowired
	private SYS_PARAMETERDAO  sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{
			//System.out.println("Highligh:"+highlightConfigList2.get(0).getStyle());
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") AlShiftFesFilter filter,
			@RequestParam(required = false) String sTime,
			@RequestParam(required = false) String eTime,
			@RequestParam(required = false) String regionTk,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = alShiftFesDao.titleForm("SHIFT_FES_LIST");		
		model.addAttribute("title", title.get(0).getValue());
		loadData(model, filter.getLeaderName());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		if (regionTk==null ||regionTk.equals("")){
			regionTk =userLogin.getRegion();
		}
		filter.setDept(regionTk);
		boolean checkRegion=false;
		if (userLogin!=null&&regionTk!=null&regionTk.equalsIgnoreCase(userLogin.getRegion())){
			checkRegion= true;
		}
		model.addAttribute("checkRegion", checkRegion);
		model.addAttribute("regionTk", regionTk);
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("shiftFes").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("shiftFes").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		 
		if (sTime== null
				|| sTime.equals("")
				|| (sTime != null && !sTime.equals("") && DateTools
						.isValid("dd/MM/yyyy HH:mm", sTime) == false)) { 
			sTime = "01/01/"+ Calendar.getInstance().get(Calendar.YEAR) +" 00:00";
		}
		filter.setsTime(sTime);
		
		if (eTime == null
				|| eTime.equals("")
				|| (eTime != null && !eTime.equals("") && DateTools
						.isValid("dd/MM/yyyy HH:mm", eTime) == false)) {
		
			eTime= "31/12/"+ Calendar.getInstance().get(Calendar.YEAR) +" 23:59";  
		}
		filter.seteTime(eTime);
	
		List<AlShiftFes> shiftFesList = new ArrayList<AlShiftFes>();
		try
		{
			shiftFesList = alShiftFesDao.getAlShiftFes(filter, order, column);
		}
		catch (Exception exp)
		{
			shiftFesList= null;
		}
		 
		List<SYS_PARAMETER> getRegency = hContactNumberDao.getRegency();
		 
		model.addAttribute("getRegencyList", getRegency);
		model.addAttribute("shiftFesList", shiftFesList);
		model.addAttribute("sTime", filter.getsTime());
		model.addAttribute("eTime", filter.geteTime());
		model.addAttribute("filter", filter);
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ShiftFes_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/ShiftFes/shiftFesList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			alShiftFesDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, Model  model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShiftFes alShiftFes;
		String sdate = "", edate = "";
		loadData(model, "");
		List<SYS_PARAMETER> titleAdd = alShiftFesDao.titleForm("SHIFT_FES_ADD");
		List<SYS_PARAMETER> titleUpdate = alShiftFesDao.titleForm("SHIFT_FES_UPDATE");
		List<SYS_PARAMETER> getRegency = hContactNumberDao.getRegency(); 
		
		if (id == null) { 	 
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			alShiftFes = new AlShiftFes();
			alShiftFes.setDept(userLogin.getRegion());
			sdate = dataFormat.format(new Date());
			edate = df_full.format(new Date())+" 23:59"; 
		} else { 
			model.addAttribute("titleF", titleUpdate.get(0).getValue());
			alShiftFes = alShiftFesDao.selectByPrimaryKey(id);
			
			sdate = dataFormat.format(alShiftFes.getStime());
			edate = dataFormat.format(alShiftFes.getEtime());
		} 
		
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("getRegencyList", getRegency);
		model.addAttribute("alShiftFes", alShiftFes);
		
		return "jsp/ShiftFes/shiftFesForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@ModelAttribute("alShiftFes") @Valid AlShiftFes alShiftFes, BindingResult result, 
    		@RequestParam(required = false) String sdate, @RequestParam(required = false) String edate,
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) { 
			 
		List<SYS_PARAMETER> getRegency = hContactNumberDao.getRegency();
		List<SYS_PARAMETER> titleAdd = alShiftFesDao.titleForm("SHIFT_FES_ADD");
		List<SYS_PARAMETER> titleUpdate = alShiftFesDao.titleForm("SHIFT_FES_UPDATE"); 
        boolean error = false;
		
		if(sdate == null || (sdate != null && !DateTools.isValid("dd/MM/yyyy HH:mm", sdate))){ 
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			model.addAttribute("getRegencyList", getRegency); 
			model.addAttribute("sdate", sdate);
			model.addAttribute("edate", edate);
			model.addAttribute("alShiftFes", alShiftFes); 
			model.addAttribute("SDateError", "Thông tin chưa được nhập hoặc thông tin nhập vào sai định dạng");
			error = true; 
		}else{
			try {
				alShiftFes.setStime(dataFormat.parse(sdate));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(edate == null || (edate != null && !DateTools.isValid("dd/MM/yyyy HH:mm", edate))){ 
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			model.addAttribute("getRegencyList", getRegency);
			model.addAttribute("sdate", sdate);
			model.addAttribute("edate", edate);
			model.addAttribute("alShiftFes", alShiftFes); 
			model.addAttribute("EDateError", "Thông tin chưa được nhập hoặc thông tin nhập vào sai định dạng");
			error = true; 
		}else{
			try {
				alShiftFes.setEtime(dataFormat.parse(edate));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		
		if(error == true){
			return "jsp/ShiftFes/shiftFesForm";
		}
		
		if (alShiftFes.getShiftId() == null) { 
			if (result.hasErrors()) { 
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("getRegencyList", getRegency);
				model.addAttribute("alShiftFes", alShiftFes); 
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				
				return "jsp/ShiftFes/shiftFesForm";
				
			}
			
			List<AlShiftFes> shiftFesOld = alShiftFesDao.selectByColumn(sdate, edate, 
					alShiftFes.getLeaderName(), alShiftFes.getStaffName(), alShiftFes.getLeaderPhone(), alShiftFes.getStaffPhone());
			
			if(shiftFesOld.size() != 0){
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("getRegencyList", getRegency);
				model.addAttribute("alShiftFes", alShiftFes); 
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				
				saveMessageKey(request, "shiftFes.exits");
				return "jsp/ShiftFes/shiftFesForm";
				
			}
			
			alShiftFesDao.insert(alShiftFes);
			saveMessageKey(request, "shiftFes.insertSucceFull");
			
		} else {  
			if (result.hasErrors()) {  
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("getRegencyList", getRegency);
				model.addAttribute("alShiftFes", alShiftFes);
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				
				return "jsp/ShiftFes/shiftFesForm";
			} 
			
			List<AlShiftFes> shiftFesOld = alShiftFesDao.selectByColumn(sdate,edate, 
					alShiftFes.getLeaderName(), alShiftFes.getStaffName(), alShiftFes.getLeaderPhone(), alShiftFes.getStaffPhone());
			
			if(shiftFesOld.size() != 0 && !shiftFesOld.get(0).getShiftId().equals(alShiftFes.getShiftId())){
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("getRegencyList", getRegency);
				model.addAttribute("alShiftFes", alShiftFes); 
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				
				saveMessageKey(request, "shiftFes.exits");
				return "jsp/ShiftFes/shiftFesForm";
			}
			
			alShiftFesDao.updateByPrimaryKeySelective(alShiftFes);
			saveMessageKey(request, "shiftFes.updateSucceFull");
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = alShiftFesDao.titleForm("SHIFT_FES_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jsp/ShiftFes/shiftFesUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = alShiftFesDao.titleForm("SHIFT_FES_UPLOAD"); 
		if (!filePath.isEmpty()) { 
			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xlsx")||fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jsp/ShiftFes/shiftFesUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		
		List<SYS_PARAMETER> titleU = alShiftFesDao.titleForm("SHIFT_FES_UPLOAD");  
		
		List<AlShiftFes> successList = new ArrayList<AlShiftFes>();
		List<AlShiftFes> failedList = new ArrayList<AlShiftFes>();
		List<AlShiftFes> success = new ArrayList<AlShiftFes>();

		String stime;
		String etime;
		String dept;
		String leader_name;
		String leader_phone;
		String staff_name;
		String staff_phone;
		String regency;
		String decription;
		
		if (sheetData.size() > 0) { 
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 9) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		
        		return "jsp/ShiftFes/shiftFesUpload";
        	} 
        	
        	if (sheetData.size() > 1) {
        		AlShiftFes alShiftFes;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			alShiftFes = new AlShiftFes();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=9; j++) {
     					data.add("");
     				}
        			
        			 stime = data.get(0).toString().equals("")?null:data.get(0).toString();
	       			 etime = data.get(1).toString().equals("")?null:data.get(1).toString();
	       			 dept = data.get(2).toString().equals("")?userLogin.getRegion():data.get(2).toString();
	       			 leader_name = data.get(3).toString().equals("")?null:data.get(3).toString();
	       			 leader_phone = data.get(4).toString().equals("")?null:data.get(4).toString();
	       			 regency = data.get(5).toString().equals("")?null:data.get(5).toString();
	       			 staff_name = data.get(6).toString().equals("")?null:data.get(6).toString();
	       			 staff_phone = data.get(7).toString().equals("")?null:data.get(7).toString(); 
	       			 decription = data.get(8).toString().equals("")?null:data.get(8).toString(); 
	       			 
	       			if (leader_name == null || staff_name == null || stime == null || etime == null
	       					|| (leader_name != null && leader_name.length() >= 200)
	       					|| (staff_name != null && staff_name.length() >= 200)
	       					|| (stime != null && DateTools.isValid("dd/MM/yyyy HH:mm", stime) == false)
	       					|| (etime != null && DateTools.isValid("dd/MM/yyyy HH:mm", etime) == false)
							|| (leader_phone != null && leader_phone.length() >= 100)
							|| (staff_phone != null && staff_phone.length() >= 100)
							|| (decription != null && decription.length() >= 300) 
							|| (regency != null && regency.length() >= 200)
							|| (dept != null && !dept.equalsIgnoreCase(userLogin.getRegion()) )) 
					{ 
						error = true;
					}
	       			
	       			Date sdate = null; Date edate = null;
	       			try {
	       				if(stime != null && DateTools.isValid("dd/MM/yyyy HH:mm", stime) == true){
	       					sdate = dataFormat.parse(stime); 
	       				}  
	       				if(etime != null && DateTools.isValid("dd/MM/yyyy HH:mm", etime) == true){
	       					edate = dataFormat.parse(etime);
	       				}
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
	       			alShiftFes.setStime(sdate);
	       			alShiftFes.setEtime(edate);
	       			alShiftFes.setDept(dept);
	       			alShiftFes.setLeaderName(leader_name);
	       			alShiftFes.setLeaderPhone(leader_phone);
	       			alShiftFes.setStaffName(staff_name);
	       			alShiftFes.setStaffPhone(staff_phone); 
	       			alShiftFes.setRegency(regency); 
	       			alShiftFes.setDecription(decription);  
        			
        			if (leader_name == null && staff_name == null  && stime == null && etime == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(alShiftFes);
    					} else  {
    						successList.add(alShiftFes);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AlShiftFes cab: successList) {   
			try {
				List<AlShiftFes> shiftFesList = alShiftFesDao.selectByColumn(dataFormat.format(cab.getStime()), dataFormat.format(cab.getEtime()), 
					cab.getLeaderName(), cab.getStaffName(), cab.getLeaderPhone(), cab.getStaffPhone());
				
				if (shiftFesList.size() != 0) { 
					cab.setShiftId(shiftFesList.get(0).getShiftId());
					alShiftFesDao.updateByPrimaryKeySelective(cab);
				} 
				else
				{ 
					alShiftFesDao.insertSelective(cab);
				}
				success.add(cab);
			} catch (Exception ex) {
				failedList.add(cab);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ĐHKT không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ShiftFes_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		
		return "jsp/ShiftFes/shiftFesUpload";
	}
	
	/* 
	 * Update by: ThangPX_20140319
	 * Lay List nguoi quan ly, chuc vu
	 * */
	private void loadData(Model model,String leaderName)
	{
		// Danh sach goi nho nguoi quan ly
		List<String> leaderList = alShiftFesDao.getLeaderList(leaderName);
		String leaderArray="var leaderList = new Array(";
		String cnId="";
		for (int i=0;i<leaderList.size();i++) {
			leaderArray = leaderArray + cnId +"\""+leaderList.get(i)+"\"";
			cnId=",";
		}
		leaderArray = leaderArray+");";
		model.addAttribute("leaderList", leaderArray);
		
		// Danh sach goi nho chuc vu
		List<SYS_PARAMETER> regencyList = hContactNumberDao.getRegency();
		String regencyArray="var regencyList = new Array(";
		String regencyId="";
		for (int i=0;i<regencyList.size();i++) {
			regencyArray = regencyArray + regencyId +"\""+regencyList.get(i).getValue()+"\"";
			regencyId=",";
		}
		regencyArray = regencyArray+");"; 
		model.addAttribute("regencyList", regencyArray);
		 
	}
	
}
