package controller.feedback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
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

import controller.BaseController;

import bsh.ParseException;

import vo.CHighlightConfigs;
import vo.HProvinceFb;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.ProvinceFilter;
import vo.alarm.utils.URLCoderTools;
import vo.alarm.utils.UploadTools;

import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.FbProcessDAO;
import dao.HProvinceFbDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
/**
 * Function        : Danh sách quận huyện
 * Created By      : VanAnh
 * Create Date     : 
 * Modified By     : BUIQUANG
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/province")
public class HProvinceController extends BaseController {
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired 
	private HProvinceFbDAO hProvinceFbDAO;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired 
	private FbProcessDAO fbProcessDAO;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	//private DateFormat df_full1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	
	// Lay id truc ca hien tai
	/*private AlShift getCaTrucHT()
	{
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		String sang = caList.get(0).getValue();
		String chieu = caList.get(1).getValue();
		String toi = caList.get(2).getValue();
		String caT="";
		String ngayT="";
		AlShift shift = new AlShift();
		Date currentTime = new Date();
		int hour = currentTime.getHours();
		ngayT= df_full1.format(currentTime);
		if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
			caT = "SANG";
		}
		if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
			caT = "CHIEU";
		}
		if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
			caT = "TOI";
		}
		// Lay idshift
		 shift = alShiftDAO.getCaTrucGanNhat();
		if (shift==null)
		{
			shift= alShiftDAO.getCaTruc(caT,ngayT);
		}
		return shift;
	}*/
	
	@RequestMapping("/list")
	public ModelAndView list(
			@ModelAttribute("filter") @Valid ProvinceFilter filter,
			@RequestParam(required = false) String deptCode,
			Model model, HttpServletRequest request) {
		
		
		if (filter.getDistrict() != null) {
			filter.setDistrict(URLCoderTools.decode(filter.getDistrict()));
		}
		System.out.println("district : "+filter.getDistrict());
		/*String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);*/
	
		List<SYS_PARAMETER> sysParemeterTitle = hProvinceFbDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		/*AlShift shift = getCaTrucHT();
		boolean checkCaTruc= false;
		if (shift!=null)
		{
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
			{
				checkCaTruc=true;
			}
			for (String item : user) {
				if (item.equals(username))
				{
					checkCaTruc=true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
		}*/
	
		int order = 0;
		String column = "CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("province").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("province").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<HProvinceFb> alarmList = new ArrayList<HProvinceFb>();
		try
		{
			alarmList = hProvinceFbDAO.getProvinceList(filter,deptCode, column, order);	
		}
		catch (Exception exp)
		{
			alarmList= null;
		}
		model.addAttribute("provinceList", alarmList);
		model.addAttribute("deptCode", deptCode);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "HProvinceFb_" + dateNow;
		model.addAttribute("exportFileName", exportFileName); 
		
		return new ModelAndView("jsp/province/provinceList");
	}
	
	/*@ModelAttribute("teamList")
	public List<MDepartment> teamList() {
		return fbProcessDAO.getDepartmentFbList();
	}*/
	
	/*@ModelAttribute("regionList")
	public List<SYS_PARAMETER> regionList() {
		return hProvinceFbDAO.regionList();
	}*/
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,  HttpServletRequest request) {
		
		try {
			hProvinceFbDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "provincesCode.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id,
			ModelMap model) {

		List<SYS_PARAMETER> sysParemeterTitle = hProvinceFbDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		
		List<SYS_PARAMETER> regionList = hProvinceFbDAO.regionList();
		model.addAttribute("regionList", regionList);
		
		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("deptProcessList", deptProcessList);
		
		HProvinceFb provincesCode = new HProvinceFb();
		if (id != null)
		{
			provincesCode = hProvinceFbDAO.selectByPrimaryKey(id);
			
			model.addAttribute("region", provincesCode.getRegion());		
			model.addAttribute("deptCodeCBB", provincesCode.getDeptCode());
			model.addAttribute("teamCBB", provincesCode.getTeam());	
			
			List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(provincesCode.getDeptCode());
	 		model.addAttribute("teamList", teamList);
	 		
			
		}
		else{
			if(deptProcessList.size() > 0){
		 		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(deptProcessList.get(0).getDeptCode());
		 		model.addAttribute("teamList", teamList);
		 	}
		}
		model.addAttribute("provincesCode", provincesCode);		
		
		return "jsp/province/provinceForm";
	}
	
	private void loadData(Model model, HProvinceFb provincesCode){
		List<MDepartment> deptProcessList = fbProcessDAO.getDepartmentFbList();
 		model.addAttribute("deptProcessList", deptProcessList);
 		
		List<HProvinceFb> teamList = hProvinceFbDAO.getTeamFbList(provincesCode.getDeptCode());
		model.addAttribute("teamList", teamList);
		
		List<SYS_PARAMETER> regionList = hProvinceFbDAO.regionList();
		model.addAttribute("regionList", regionList);
		
		model.addAttribute("region", provincesCode.getRegion());	
		model.addAttribute("deptCodeCBB", provincesCode.getDeptCode());	
		model.addAttribute("teamCBB", provincesCode.getTeam());	
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("provincesCode") @Valid HProvinceFb provincesCode, BindingResult result,
			HttpServletRequest request,Model model) throws ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = hProvinceFbDAO.titleForm("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		
		if (result.hasErrors()) {
			model.addAttribute("titleF", title);
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			
			loadData(model, provincesCode);
			return "jsp/province/provinceForm";
		}
		
		List<HProvinceFb> oldProvincesCode = hProvinceFbDAO.checkExits(provincesCode.getCode(), provincesCode.getDistrict());
		if(provincesCode.getId()==null)
		{
			if(oldProvincesCode.size() < 1)
			{
				hProvinceFbDAO.insert(provincesCode);
				saveMessageKey(request, "provincesCode.insertSucceFull");
			}
			else{
				saveMessageKey(request, "provincesCode.exits");
				model.addAttribute("titleF", title);
				loadData(model, provincesCode);
		 		
				return "jsp/province/provinceForm";
			}
		}
		else{
			if (oldProvincesCode.size()>0 && !oldProvincesCode.get(0).getId().equals(provincesCode.getId())) {
				saveMessageKey(request, "provincesCode.exits");
				model.addAttribute("titleF", title);
				
				loadData(model, provincesCode);
				return "jsp/province/provinceForm";
			} else {
				
				hProvinceFbDAO.updateByPrimaryKey(provincesCode);
				saveMessageKey(request, "provincesCode.updateSucceFull");
			}
		}
		
		return "redirect:list.htm";
	}
	
	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> sysParemeterTitle = hProvinceFbDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		return "jsp/province/provinceUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		List<SYS_PARAMETER> sysParemeterTitle = hProvinceFbDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		model.addAttribute("highlight", highlight);
		return "jsp/province/provinceUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		
		List<HProvinceFb> successList = new ArrayList<HProvinceFb>();
		List<HProvinceFb> failedList = new ArrayList<HProvinceFb>();
		List<HProvinceFb> success = new ArrayList<HProvinceFb>();
		
		String region;
		String branch;
		String location;
		String code;
		String province;
		String district;
		String districtName;
		String deptCode;
		String team;
		String ordering;
		String marks;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 11) {
        		saveMessage(request, "Số lượng cột dữ liệu thông tin quận/huyện của file không đúng");
        		
        		return "jsp/province/provinceUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		HProvinceFb hProvinceFb;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			hProvinceFb = new HProvinceFb();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=11; j++) {
     					data.add("");
     				}
        			region				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			branch				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			location			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			code				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			province			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			district			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			districtName		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			deptCode			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			team				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			ordering			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			marks				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			
        			// Kiem tra loi
        			if (region == null || code == null || province == null || district == null || districtName == null
					){
						error = true;
					}
        			
        			try
	    			{
        				if(ordering != null){
		        			Integer a = Integer.parseInt(ordering);
		        			hProvinceFb.setOrdering(a);
	        			}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			
        			//---------------------------------------------------------------------------
        			
        			hProvinceFb.setRegion(region);
        			hProvinceFb.setBranch(branch);
        			hProvinceFb.setLocation(location);
        			if(code != null)
        				hProvinceFb.setCode(code.trim());
        			hProvinceFb.setProvince(province);
        			if(district != null)
        				hProvinceFb.setDistrict(district.trim());
        			hProvinceFb.setDistrictName(districtName);
        			hProvinceFb.setMarks(marks);
        			hProvinceFb.setDeptCode(deptCode);
        			hProvinceFb.setTeam(team);
        			
        			
        			if (region == null && code == null && province == null && district == null && districtName == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(hProvinceFb);
    					} else  {
    						
    						successList.add(hProvinceFb);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (HProvinceFb record: successList) {
			try {
					List<HProvinceFb> oldProvincesCode = hProvinceFbDAO.checkExits(record.getCode(), record.getDistrict());
	 				if(oldProvincesCode.size()>0)
	        		{
	 					record.setId(oldProvincesCode.get(0).getId());
	 					hProvinceFbDAO.updateByPrimaryKey(record);
	        		}
	 				else
	 				{
	 					hProvinceFbDAO.insert(record);
	 				}
					success.add(record);
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu thông tin quận/huyện không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jsp/province/provinceUpload";
	}
			
}
