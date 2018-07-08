package controller.feedback;

import java.io.IOException;
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

import controller.BaseController;

import vo.CHighlightConfigs;
import vo.HProvinceFb;
import vo.HWards;
import vo.SYS_PARAMETER;
import vo.alarm.utils.HWardsFilter;
import vo.alarm.utils.UploadTools;
import bsh.ParseException;

import dao.CHighlightConfigsDAO;
import dao.FbProcessDAO;
import dao.HProvinceFbDAO;
import dao.HWardsDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : Danh sách phường xã
 * Created By      : VanAnh
 * Create Date     : 
 * Modified By     : BUIQUANG
 * Modify Date     : 8/1/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/ward")
public class HWardController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;	
	@Autowired 
	private HWardsDAO hWardsDAO;
	@Autowired 
	private HProvinceFbDAO hProvinceFbDAO;
	@Autowired 
	private FbProcessDAO fbProcessDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@RequestMapping("/list")
	public ModelAndView list(
			@ModelAttribute("filter") @Valid HWardsFilter filter,
			@RequestParam(required = false) String provinceCode,
			@RequestParam(required = false) String districtCode,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = hWardsDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		int order = 0;
		String column = "VILLAGE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("wards").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("wards").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<HWards> alarmList = new ArrayList<HWards>();
		try
		{
			filter.setProvinceCode(provinceCode);
			filter.setDistrictCode(districtCode);
			alarmList = hWardsDAO.getWardList(filter,column, order);	
		}
		catch (Exception exp)
		{
			alarmList= null;
		}
		model.addAttribute("wardsList", alarmList);
		model.addAttribute("filter", filter);
		model.addAttribute("provinceCode", provinceCode);
		model.addAttribute("districtCode", districtCode);
		
		List<HProvinceFb> provinceCodeList = hProvinceFbDAO.distinctProvinceCode();
		model.addAttribute("provinceCodeList", provinceCodeList);
		
		List<HProvinceFb> districtCodeList = hProvinceFbDAO.getDistrictCodeList(provinceCode);
		model.addAttribute("districtCodeList", districtCodeList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "HWard_" + dateNow;
		model.addAttribute("exportFileName", exportFileName); 
		
		return new ModelAndView("jsp/province/wardList");
	}
	
	/*@ModelAttribute("provinceCodeList")
	public List<HProvinceFb> provinceCodeList() {
		return hProvinceFbDAO.distinctProvinceCode();
	}*/
	
	/*@ModelAttribute("districtCodeList")
	public List<HProvincesCode> districtCodeList() {
		return hProvincesCodeDAO.getDistrictCodeList(null);
	}*/
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,  HttpServletRequest request) {
		
		try {
			hWardsDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id,
			ModelMap model) {

		List<SYS_PARAMETER> sysParemeterTitle = hWardsDAO.titleForm("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		
		List<HProvinceFb> provinceCodeList = hProvinceFbDAO.distinctProvinceCode();
		model.addAttribute("provinceCodeList", provinceCodeList);
		
		HWards wards = new HWards();
		if (id != null)
		{
			wards = hWardsDAO.selectByPrimaryKey(id);	
			model.addAttribute("provinceCode", wards.getProvinceCode());	
			model.addAttribute("districtCode", wards.getDistrictCode());
			
			List<HProvinceFb> districtCodeList = hProvinceFbDAO.getDistrictCodeList(wards.getProvinceCode());
			model.addAttribute("districtCodeList", districtCodeList);
		}
		else
		{
			
			if (provinceCodeList.size()>0)
			{
				List<HProvinceFb> districtCodeList = hProvinceFbDAO.getDistrictCodeList(provinceCodeList.get(0).getCode());
				model.addAttribute("districtCodeList", districtCodeList);
			}
		}
		model.addAttribute("wards", wards);		
		
		return "jsp/province/wardForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("wards") @Valid HWards wards,
			BindingResult result,
			@RequestParam(required = false) String provinceCode,
			@RequestParam(required = false) String districtCode,
			HttpServletRequest request,Model model) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<HProvinceFb> provinceCodeList = hProvinceFbDAO.distinctProvinceCode();
		model.addAttribute("provinceCodeList", provinceCodeList);
		
		List<HProvinceFb> districtCodeList = hProvinceFbDAO.getDistrictCodeList(provinceCode);
		model.addAttribute("districtCodeList", districtCodeList);
		
		List<SYS_PARAMETER> sysParemeterTitle = hWardsDAO.titleForm("ADD");
		String title= "";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		
		if (!result.hasErrors()) {
			
			List<HWards> oldwards = hWardsDAO.checkExits(wards.getVillage());
			wards.setProvinceCode(provinceCode);
			wards.setDistrictCode(districtCode);
			if (wards.getId()==null) {
				wards.setCreateDate(new Date());
				wards.setCreatedBy(username);
				if (oldwards.size() < 1) {
					hWardsDAO.insertSelective(wards);
					saveMessageKey(request, "wards.insertSucceFull");
				} else {
					saveMessageKey(request, "wards.exits");
					model.addAttribute("titleF", title);
					model.addAttribute("wards", wards);
					model.addAttribute("provinceCode", provinceCode);
					model.addAttribute("districtCode", districtCode);
					return "jsp/province/wardForm";
				}
			} else {
				wards.setModifyDate(new Date());
				wards.setModifiedBy(username);
				if (oldwards.size()>0 )
				{
					if (!oldwards.get(0).getId().equals(wards.getId())) {
					saveMessageKey(request, "wards.exits");
					model.addAttribute("titleF", title);
					model.addAttribute("wards", wards);
					model.addAttribute("provinceCode", provinceCode);
					model.addAttribute("districtCode", districtCode);
					return "jsp/province/wardForm";
					}
					else
					{
						hWardsDAO.updateByPrimaryKey(wards);
						saveMessageKey(request, "wards.updateSucceFull");
					}
				} else {
					
					hWardsDAO.updateByPrimaryKey(wards);
					saveMessageKey(request, "wards.updateSucceFull");
				}
			}
			return "redirect:list.htm";
		} else {
			
			model.addAttribute("titleF", title);
			model.addAttribute("wards", wards);
			saveMessageKey(request, "wards.notenough");
			model.addAttribute("provinceCode", provinceCode);
			model.addAttribute("districtCode", districtCode);
			return "jsp/province/wardForm";
		}
	}
	
	// Upload File
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> sysParemeterTitle = hWardsDAO.titleForm("UPLOAD");
		if(sysParemeterTitle.size() > 0)
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		
		return "jsp/province/wardUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		List<SYS_PARAMETER> sysParemeterTitle = hWardsDAO.titleForm("UPLOAD");
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
		return "jsp/province/wardUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<HWards> successList = new ArrayList<HWards>();
		List<HWards> failedList = new ArrayList<HWards>();
		List<HWards> success = new ArrayList<HWards>();
		
		String village;
		String villageName;
		String districtCode;
		String provinceCode;
		String ordering;
		String description;
		
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		saveMessage(request, "Số lượng cột dữ liệu thông tin phường/xã của file không đúng");
        		
        		return "jsp/province/wardUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		HWards hWards;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			hWards = new HWards();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			village				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			villageName			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			districtCode		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			provinceCode		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			ordering			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			description			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			
        			// Kiem tra loi
        			if (village == null || villageName == null || districtCode == null || provinceCode == null
					){
						error = true;
					}
        			
        			try
	    			{
        				if(ordering != null){
		        			Integer a = Integer.parseInt(ordering);
		        			hWards.setOrdering(a);
	        			}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			
        			//---------------------------------------------------------------------------
        			
        			hWards.setVillage(village);
        			hWards.setVillageName(villageName);
        			hWards.setDistrictCode(districtCode);
        			hWards.setProvinceCode(provinceCode);
        			hWards.setDescription(description);
        			
        			if (village == null && villageName == null && districtCode == null && provinceCode == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(hWards);
    					} else  {
    						
    						successList.add(hWards);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (HWards record: successList) {
			try {
					List<HWards> oldwards = hWardsDAO.checkExits(record.getVillage());
	 				if(oldwards.size()>0)
	        		{
	 					record.setModifiedBy(createdBy);
	 					record.setId(oldwards.get(0).getId());
	 					hWardsDAO.updateByPrimaryKey(record);
	        		}
	 				else
	 				{
	 					record.setCreatedBy(createdBy);
	 					hWardsDAO.insert(record);
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
			model.addAttribute("status", "Dữ liệu thông tin phường/xã không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jsp/province/wardUpload";
	}
		
}
