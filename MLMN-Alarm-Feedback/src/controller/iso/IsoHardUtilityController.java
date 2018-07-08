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
import org.springframework.web.servlet.ModelAndView;

import vo.CTableConfig;
import vo.VRpDyIsoUtility;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.VRpDyIsoUtilityDAO;

/**
 * Ten file: IsoHardUtilityController.java
 * Muc dich: Tinh hieu suat su dung thuc te cua phan cung
 * @author QuangBui
 * Ngay tao: 11/10/2013
 * Lich su thay doi:
 *  
 */
@Controller
@RequestMapping("/iso/hard-utility/*")
public class IsoHardUtilityController {

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private VRpDyIsoUtilityDAO vRpDyIsoUtilityDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
	public ModelAndView list( @RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,
			   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			
			startDate = df.format(cal.getTime());
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df.format(cal.getTime());
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "HardUtility_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
 
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_ISO_UTILITY");
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));  
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("V_RP_DY_ISO_UTILITY");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		 
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType="+function;
		//lay du lieu column group cua grid
		 
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("url", url);
				
		return new ModelAndView("jspiso/isoHardUtilityList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String neType, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		List<VRpDyIsoUtility> isoLicenseHardList = vRpDyIsoUtilityDAO.getInfo(startDate, endDate, neType);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoLicenseHardList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
}
