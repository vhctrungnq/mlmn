package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HTransmissionDeviceDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HTransmissionDevice;
import vn.com.vhc.vmsc2.statistics.web.filter.HTransmissionDeviceFilter;


/**
 * @author galaxy
 * @createDate 10:37:40 AM
 * HTransmissionDevice.java
 *
 */
@Controller
@RequestMapping("transmission-device/*")
public class HTransmissionDeviceController extends BaseController{
	@Autowired
	private HTransmissionDeviceDAO HTransDeviceDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCode;
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") HTransmissionDeviceFilter filter,
			@RequestParam(required = false) String region,
			Model model, HttpServletRequest request) {
		
//		if(region==null){	
//		}else if(region.equals("MT")){
//			filter.setRegion("MT");
//		}else if(region.equals("MD")){
//			filter.setRegion("MD");
//		}else if(region.equals("HCM")){
//			filter.setRegion("HCM");
//		}
		if(region==null){
			filter.setRegion(region);
		}
		int order = 0;
		String column = "";
		String strOrder ="desc";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			if(order==2){
				strOrder = "asc";
			}
		} catch (NumberFormatException e) {
		}
		List<HProvincesCode> regionList = hProvincesCode.getAllRegion();  
		List<String> neTypeList = HTransDeviceDao.getDistinctNeType();
		List<HProvincesCode> provinceList = hProvincesCode.getAllProvince();
		
		List<HTransmissionDevice> hTransmissionDeviceList = HTransDeviceDao.getHTramsmissionDeviceList(filter, column, strOrder);
		
		model.addAttribute("filter", filter);
		model.addAttribute("regionList", regionList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("neTypeList", neTypeList);
		model.addAttribute("hTransmissionDeviceList", hTransmissionDeviceList);
		Date date = new Date();
		@SuppressWarnings({ "deprecation"})
		String exportFileName = "transmission_device_"+date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900);
		model.addAttribute("exportFileName", exportFileName);
		return new ModelAndView("hTransmissionDeviceList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			HttpServletRequest request, Model model) {
			try {
				HTransDeviceDao.deleteById(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			return "redirect:list.htm";
		}
	
	@RequestMapping(value ="form", method = RequestMethod.GET)
	public String showForm(@RequestParam (required = false) String id,
			HttpServletRequest request, Model model) {
		HTransmissionDevice rec = new HTransmissionDevice();	
		if(id !=null){
				
				try {
					 rec = HTransDeviceDao.selectByPrimaryKey(Integer.parseInt(id));
					model.addAttribute("provinceList", hProvincesCode.getProvinceByRegion(rec.getRegion()));
				}
				catch (Exception e) {
					e.printStackTrace();
					saveMessageKey(request, "Not found");
					return "redirect:list.htm";
				}
			}else{
				model.addAttribute("provinceList", hProvincesCode.getProvinceByRegion("HCM"));
			}
			model.addAttribute("hTransmissionDevice",rec );
			model.addAttribute("regionList", hProvincesCode.getAllRegion());
			model.addAttribute("neTypeList", HTransDeviceDao.getDistinctNeType());
			
			return "hTransmissionDeviceForm";
		}
	
	@RequestMapping(value ="form" , method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("hTransmissionDevice") HTransmissionDevice hTransmissionDevice,
			BindingResult result, Model model, HttpServletRequest request) {
		
		if(hTransmissionDevice.getId()==0){
			HTransDeviceDao.insert(hTransmissionDevice);
			saveMessageKey(request, "Thêm thành công");
		}else{
			HTransDeviceDao.updateHTransmissionDevice(hTransmissionDevice);
			saveMessageKey(request, "Cập nhật thành công");
		}
		model.addAttribute( "regionList", hProvincesCode.getAllRegion());  
		model.addAttribute( "neTypeList" , HTransDeviceDao.getDistinctNeType());
		model.addAttribute( "provinceList", hProvincesCode.getProvinceByRegion(hTransmissionDevice.getRegion()));
		model.addAttribute( "hTransmissionDevice", hTransmissionDevice);
		
		if(result.hasErrors()){
			saveMessageKey(request, "Làm ơn điền lại thông tin.");
			return "hTransmissionDeviceForm";
		}
		return "redirect:list.htm";
	}

	@RequestMapping(value="ajax/getProvinceByRegion")
	public @ResponseBody List<HProvincesCode> getProvinceByRegion( String region){
//		if(!region.equals("MT") && !region.equals("HCM") ){
//			region="MĐ";
//		}
		List<HProvincesCode> provinceList = hProvincesCode.getProvinceByRegion(region);
		return provinceList;
	}
	
	@RequestMapping(value="ajax/searchNeType")
	public @ResponseBody List<String> searchNeType( String term){
		List<String> neTypeList = new ArrayList<String>();
		if(term!=null){
			neTypeList = HTransDeviceDao.searchNeType(term);
		}
		return neTypeList;
	}
}
