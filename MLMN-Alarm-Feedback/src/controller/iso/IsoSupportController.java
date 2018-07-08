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
import vo.IsoSupport;
import vo.SYS_PARAMETER;
import vo.alarm.utils.HelpTableConfigs;

import dao.CTableConfigDAO;
import dao.IsoSupportDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Ten file: IsoSupportController.java
 * Muc dich: Dinh nghia cong thuc ho tro kha nang phan cung
 * @author QUANG
 * Ngay tao: 21/09/2013
 * Lich su thay doi:
 */
@Controller
@RequestMapping("/iso/dinh-nghia-cong-thuc/*")
public class IsoSupportController extends BaseController{

	@Autowired
	private IsoSupportDAO isoSupportDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@RequestMapping("list")
	public ModelAndView list( @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String boardName, 
			   @RequestParam(required = false) String unit, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "IsoSupport_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_SUPPORT");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_SUPPORT");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		
		String filterUrl = "";
		String temp = "";
		if(ne != null){
			filterUrl += "ne=" + ne; 
			temp = "&";
		}
		if(boardName != null){
			filterUrl += temp + "boardName=" + boardName; 
			temp = "&";
		}
		
		if(unit != null){
			filterUrl += temp + "unit=" + unit;
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);
		model.addAttribute("neName", ne);
		model.addAttribute("boardName", boardName);
		model.addAttribute("unit", unit);
		
		return new ModelAndView("jspiso/isoSupportList");
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String ne,
			   @RequestParam(required = false) String boardName, 
			   @RequestParam(required = false) String unit,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoSupport> isoSupportList = isoSupportDAO.getIsoSupportFilter( ne, boardName, unit);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoSupportList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		IsoSupport isoSupport = (id == null ) ? new IsoSupport() : isoSupportDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("isoSupport", isoSupport);
		
		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
		model.addAttribute("vendorForResourceList", vendorForResourceList);
		
		isoSupportAddEdit(id, model);
		
		if(id == null)
		{
			
		}
		else
		{
			model.addAttribute("vendorCBB", isoSupport.getVendor());
		}
		
		return "jspiso/isoSupportForm";
	}
	
	private void isoSupportAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("isoSupportAddEdit", "Y");
		else
			model.addAttribute("isoSupportAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			 @ModelAttribute("isoSupport") @Valid IsoSupport isoSupport, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		//Ném lỗi
		if (result.hasErrors()) {
			isoSupportAddEdit(id, model);
			List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
			model.addAttribute("vendorForResourceList", vendorForResourceList);
			model.addAttribute("vendorCBB", isoSupport.getVendor());
			if(result.hasFieldErrors("value"))
				model.addAttribute("valueError", "valueError");

			return "jspiso/isoSupportForm";
		}
		
		if(id == "")
		{
			try{
				isoSupport.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				isoSupportDAO.insert(isoSupport);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			catch(Exception e){
				saveMessageKey(request, "message.isoSupport.errorAdd");
				isoSupportAddEdit(id, model);
				List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
				model.addAttribute("vendorForResourceList", vendorForResourceList);
				model.addAttribute("vendorCBB", isoSupport.getVendor());
				return "jspiso/isoSupportForm";
			}
		}
		else{
			try{
				isoSupport.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				isoSupportDAO.updateByPrimaryKey(isoSupport);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			catch(Exception e){
				saveMessageKey(request, "message.isoSupport.errorAdd");
				isoSupportAddEdit(id, model);
				List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
				model.addAttribute("vendorForResourceList", vendorForResourceList);
				model.addAttribute("vendorCBB", isoSupport.getVendor());
				return "jspiso/isoSupportForm";
			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			isoSupportDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
}
