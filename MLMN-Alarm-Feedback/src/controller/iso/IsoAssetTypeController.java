package controller.iso;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.CTableConfig;
import vo.IsoAssetType;
import vo.alarm.utils.HelpTableConfigs;

import dao.CTableConfigDAO;
import dao.IsoAssetTypeDAO;
/**
 * Ten file: IsoAssetTypeController.java
 * Muc dich: Quan ly phan nhom thiet bi
 * @author QuangBui
 * Ngay tao: 23/08/2013
 * Lich su thay doi:
 *  
 */
@Controller
@RequestMapping("/iso/phan-nhom-thiet-bi/*")
public class IsoAssetTypeController extends BaseController{

	@Autowired
	private IsoAssetTypeDAO isoAssetTypeDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@RequestMapping("list")
	public ModelAndView list( ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "NhomThietBi_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_ASSET_TYPE");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid 
		model.addAttribute("listSource", HelpTableConfigs.getListSource(configList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm"); 
		
		return new ModelAndView("jspiso/isoAssetTypeList");
	}
	
	@RequestMapping("/data")
	public void doGet(
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoAssetType> isoAssetTypeList = isoAssetTypeDAO.getIsoAssetTypeFilter();
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoAssetTypeList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		IsoAssetType isoAssetType = (id == null ) ? new IsoAssetType() : isoAssetTypeDAO.selectById(Integer.parseInt(id));
		model.addAttribute("isoAssetType", isoAssetType);
		
		isoAssetTypeAddEdit(id, model);
		
		return "jspiso/isoAssetTypeForm";
	}
	
	private void isoAssetTypeAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("isoAssetTypeAddEdit", "Y");
		else
			model.addAttribute("isoAssetTypeAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("isoAssetType") @Valid IsoAssetType isoAssetType, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			isoAssetTypeAddEdit(id, model);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("sapXepError", "sapXepError");
			return "jspiso/isoAssetTypeForm";
		}
		
		if(id == "")
		{
			if(isoAssetTypeDAO.selectByPrimaryKey(isoAssetType.getCode()) == null){
				isoAssetType.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				isoAssetTypeDAO.insert(isoAssetType);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				saveMessageKey(request, "message.isoAssetType.maLoaiThietBiTonTai");
				isoAssetTypeAddEdit(id, model);
				return "jspiso/isoAssetTypeForm";
			}
		}
		else{
			isoAssetType.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			isoAssetTypeDAO.updateByPrimaryKey(isoAssetType);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			isoAssetTypeDAO.deleteById(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
}
