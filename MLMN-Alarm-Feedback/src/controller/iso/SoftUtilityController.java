package controller.iso;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.IsoLicenseSoft;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.CTableConfigDAO;
import dao.IsoLicenseSoftDAO;
/**
 * Ten file: SoftUtilityController.java
 * Muc dich: Soft Utility
 * @author QUANG
 * Ngay tao: 06/09/2013
 * Lich su thay doi:
 * Nguoi sua: ThangPX
 * Ngay sua: 15/04/2014
 * Muc dich: Chinh sua dieu kien tim kiem
 */

@Controller
@RequestMapping("/iso/soft-utility/*")
public class SoftUtilityController {
	
	@Autowired
	private IsoLicenseSoftDAO isoLicenseSoftDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @RequestParam(required = false) String vendor, 
					   @RequestParam(required = false) String neType, 
					   @RequestParam(required = false) String neName, 
					   @RequestParam(required = false) String featureCode, 
					   @RequestParam(required = false) String featureName,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SoftUtility_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			

			startDate = df.format(DateTools.startMonth(new Date()));
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df.format(cal.getTime());
		}
		
		if(vendor != null)
			vendor = vendor.trim();
		if(neType != null)
			neType = neType.trim();
		if(neName != null)
			neName = neName.trim();
		if(featureCode != null)
			featureCode = featureCode.trim();
		if(featureName != null)
			featureName = featureName.trim();
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_SOFT_UTILITY");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_SOFT_UTILITY");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		
		String filterUrl = "";
		String temp = "";
		if(startDate != null){
			filterUrl += "startDate=" + startDate; 
			temp = "&";
		}
		if(endDate != null){
			filterUrl += temp + "endDate=" + endDate; 
			temp = "&";
		}
		
		if(vendor != null){
			filterUrl += temp + "vendor=" + vendor; 
			temp = "&";
		}
		if(neType != null){
			filterUrl += temp + "neType=" + neType; 
			temp = "&";
		}
		if(neName != null){
			filterUrl += temp + "neName=" + neName; 
			temp = "&";
		}
		if(featureCode != null){
			filterUrl += temp + "featureCode=" + featureCode; 
			temp = "&";
		} 
		if(featureName != null){
			filterUrl += temp + "featureName=" + featureName;
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("vendor", vendor);
		model.addAttribute("neType", neType);
		model.addAttribute("neName", neName);
		model.addAttribute("featureCode", featureCode);
		model.addAttribute("featureName", featureName);
		
		return "jspiso/isoSoftUtilityList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String vendor, 
			   @RequestParam(required = false) String neType, 
			   @RequestParam(required = false) String neName, 
			   @RequestParam(required = false) String featureCode, 
			   @RequestParam(required = false) String featureName, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoLicenseSoft> isoLicenseSoftList = isoLicenseSoftDAO.getIsoSoftUtilityFilter(startDate, endDate, vendor, neType, neName, featureCode, featureName);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoLicenseSoftList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
}
