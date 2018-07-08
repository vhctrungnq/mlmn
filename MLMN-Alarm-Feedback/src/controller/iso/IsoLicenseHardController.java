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

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.IsoLicenseHard;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.CTableConfigDAO;
import dao.IsoLicenseHardDAO;
/**
 * Ten file: IsoLicenseHardController.java
 * Muc dich: Quan ly kha nang phan cung
 * @author QUANG
 * Ngay tao: 24/09/2013
 * Lich su thay doi:
 */
@Controller
@RequestMapping("/iso/quan-ly-kha-nang-phan-cung/*")
public class IsoLicenseHardController {

	@Autowired
	private IsoLicenseHardDAO isoLicenseHardDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
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
		String exportFileName = "IsoLicenseHard_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		//do du lieu ra grid
		List<CTableConfig> configList;
		List<CTableConfig> columnList;
		if(function.equals("bsc-bts"))
		{
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_LICENSE_HARD_2G");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_LICENSE_HARD_2G");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "dataBSC.htm" + "?startDate="+startDate+"&endDate="+endDate);
		}
		else if(function.equals("rnc-nodeb"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_LICENSE_HARD_3G");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_LICENSE_HARD_3G");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=RNC_NODEB");
		}
		else if(function.equals("msc"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_LICENSE_HARD_MSC");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_LICENSE_HARD_MSC");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=MSC");
		}
		else if(function.equals("sgsn"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_LICENSE_HARD_SGSN");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_LICENSE_HARD_SGSN");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=SGSN");
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
				
		return new ModelAndView("jspiso/isoLicenseHardList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String neType, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoLicenseHard> isoLicenseHardList = isoLicenseHardDAO.getLicenseHardByNeType(startDate, endDate, neType);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoLicenseHardList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	@RequestMapping("/dataBSC")
	public void dataBSC(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoLicenseHard> isoLicenseHardList = isoLicenseHardDAO.getLicenseHardBsc(startDate, endDate);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoLicenseHardList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
}
	
