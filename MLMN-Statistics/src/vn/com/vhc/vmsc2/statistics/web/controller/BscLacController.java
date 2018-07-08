package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.HBscLacDAO;
import vn.com.vhc.vmsc2.statistics.dao.SYS_PARAMETERDAO;
import vn.com.vhc.vmsc2.statistics.domain.HBscLac;
import vn.com.vhc.vmsc2.statistics.domain.SYS_PARAMETER;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;


@Controller
@RequestMapping("/network/bsc-lac/*")
public class BscLacController extends BaseController {
	@Autowired
	private HBscLacDAO lacDao;
	@Autowired
	private SYS_PARAMETERDAO sysPdao;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") HBscLac filter, Model model) {
		List<SYS_PARAMETER> vendorList = sysPdao.getSysParameterByCode("VENDOR");
		List<SYS_PARAMETER> branchList = sysPdao.getSysParameterByCode("BRANCH");
		 
		List<HBscLac> lacList = lacDao.filter(filter);
		
		model.addAttribute("vendorList", vendorList);
		model.addAttribute("branchList", branchList); 
		model.addAttribute("lacList", lacList); 
		model.addAttribute("filter",filter);  
		
		return new ModelAndView("bscLacList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String bscid,@RequestParam(required = true) int lac, HttpServletRequest request) {

		lacDao.deleteByPrimaryKey(bscid, lac);
		saveMessage(request, "Lac đã được xóa.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String bscid,@RequestParam(required = false) String lac, ModelMap model) {
		HBscLac bsc = new HBscLac();
		List<SYS_PARAMETER> vendorList = sysPdao.getSysParameterByCode("VENDOR");
		List<SYS_PARAMETER> branchList = sysPdao.getSysParameterByCode("BRANCH");
		
		if(bscid == null || lac == null){
			//Add new  
			 bsc = new HBscLac();
		}else{
			//Edit
			int lacid = Integer.parseInt(lac);
			bsc = lacDao.selectByPrimaryKey(bscid, lacid);
		 }

		model.addAttribute("bsc", bsc);
		model.addAttribute("vendorList", vendorList);
		model.addAttribute("branchList", branchList);

		return "bscLacForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid HBscLac bsc,BindingResult result, HttpServletRequest request,Model model) {
		Date date = new Date();
		List<SYS_PARAMETER> vendorList = sysPdao.getSysParameterByCode("VENDOR");
		List<SYS_PARAMETER> branchList = sysPdao.getSysParameterByCode("BRANCH");
		if (!result.hasErrors())
		{
			HBscLac oldBsc= lacDao.selectByPrimaryKey(bsc.getBscid(), bsc.getLac());
			if (bsc.getLaunchDate() == null) {
				if (oldBsc == null) { 
					bsc.setRegion(getCenter("TT2"));
					bsc.setLaunchDate(date); 
					
					lacDao.insert(bsc);
					saveMessage(request, "Thêm mới thành công.");
				}
				else {
					saveMessage(request, "Lac đã tồn tại");
					model.addAttribute("bsc", bsc); 
					model.addAttribute("vendorList", vendorList);
					model.addAttribute("branchList", branchList); 
					
					return "bscLacForm";
				}
			} else {
				if (oldBsc!=null && oldBsc.getLac() == bsc.getLac())
				{
					saveMessage(request, "Lac đã tồn tại");
					model.addAttribute("bsc", bsc); 
					model.addAttribute("vendorList", vendorList);
					model.addAttribute("branchList", branchList); 
					
					return "bscLacForm";
				}
				bsc.setLaunchDate(date);
				lacDao.updateByPrimaryKey(bsc); 
				saveMessage(request, "Cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("bsc", bsc);
			model.addAttribute("vendorList", vendorList);
			model.addAttribute("branchList", branchList); 
			saveMessage(request, "Cập nhật thất bại do có trường không đúng định dạng.");
			return "bscLacForm";
		}
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "bscLacUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
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
		
		return "bscLacUpload";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {  
		
		List<HBscLac> successList = new ArrayList<HBscLac>();
		List<HBscLac> failedList = new ArrayList<HBscLac>();
		List<HBscLac> success = new ArrayList<HBscLac>();
		
		String region ;
		String vendor;
		String bscid;
		String lac ;
		String rac ;
		String cn ; 
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		saveMessageKey(request, "Số lượng cột dữ liệu không đúng.");
        		
        		return "bscLacUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		HBscLac bsc;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			bsc = new HBscLac();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			 
        			region					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
					vendor					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
					bscid					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
					lac					= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
					rac					= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
					cn					= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
					 
					
	        		//Kiem tra kieu number
					if((lac!= null && isFloat(lac)== false) || (rac!=null & isFloat(rac) == false))
						error = true;
	        		
	        		//Kiem tra do dai
					if(bscid == null || lac == null || cn == null) 
						error = true;
					if(lac==null)
						lac= "0";
					if(rac == null)
						rac ="0";
        			
        			//Set value ---------------------------------------------------------------------------
					bsc.setRegion(region);
					bsc.setVendor(vendor);
					bsc.setLac(Integer.parseInt(lac));
					bsc.setRac(Integer.parseInt(rac));
					bsc.setBscid(bscid);
					bsc.setVendor(vendor);
					bsc.setLaunchDate(new Date());
					
					//-------------------------------------------------
					
					if (error) {
						failedList.add(bsc);
					}else{  
						successList.add(bsc);
					}
					
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (HBscLac record: successList) {
			try {
					HBscLac bscOld = lacDao.selectByPrimaryKey(record.getBscid(), record.getLac()); 
					if(bscOld == null){ 
						lacDao.insertSelective(record);
					}else{
						lacDao.updateByPrimaryKeySelective(record);
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
			model.addAttribute("status", "Dữ liệu Bsc không hợp lệ do:");	// Upload lỗi
			model.addAttribute("status1","1.Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác.");
			model.addAttribute("status2","2.Thông tin chưa tồn tại.");
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "bscLacUpload";
	} 
	@SuppressWarnings("unused")
	private boolean isFloat(String number){
		try {
			Float _number = Float.parseFloat(number);
			return true;
		} catch (Exception e) { 
			return false;
		}
	}
	 
}