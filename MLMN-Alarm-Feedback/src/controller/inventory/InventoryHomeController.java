package controller.inventory;

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

import dao.CTableConfigDAO;
import dao.IsoInventoryDAO;

import vo.CTableConfig;
import vo.IsoInventory;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
/**
 * Chuc nang: trang chu tai nguyen mang luoi
 * @author BUIQUANG
 *
 */
@Controller
@RequestMapping("/inventory/home/*")
public class InventoryHomeController {

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
			   		   @RequestParam(required = false) String endDate,
					   @PathVariable String function, 
					   @RequestParam(required = false) String fbType, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Home_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
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
		
		//do du lieu ra grid
		List<CTableConfig> configListVendor = cTableConfigDAO.getTableConfigsForGrid("ISO_INVENTORY_HOME_VENDOR");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnListVendor = cTableConfigDAO.getColumnList("ISO_INVENTORY_HOME_VENDOR");
		//Lay duong link url de lay du lieu do vao grid
		String urlVendor = "dataOfVendor.htm" + "?startDate="+startDate+"&endDate="+endDate;
		String gridVendor = HelpTableConfigs.getGridPagingUrlInventoryHome(configListVendor, "gridVendor", urlVendor,"jqxlistboxVendor", columnListVendor, "menuVendor", null, null);
		model.addAttribute("gridVendor", gridVendor);
		
		//do du lieu ra grid
		List<CTableConfig> configListNetwork = cTableConfigDAO.getTableConfigsForGrid("ISO_INVENTORY_HOME");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnListNetwork = cTableConfigDAO.getColumnList("ISO_INVENTORY_HOME");
		//Lay duong link url de lay du lieu do vao grid
		String urlNetwork = "data.htm" + "?startDate="+startDate+"&endDate="+endDate;
		String gridNetwork = HelpTableConfigs.getGridPagingUrlInventoryHome(configListNetwork, "gridNetwork", urlNetwork,"jqxlistboxNetwork", columnListNetwork, "menuNetwork", null, null);
		model.addAttribute("gridNetwork", gridNetwork);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		return "jspiso/inventoryHomeList";
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoInventory> isoInventoryHomeList = isoInventoryDAO.getIsoInventoryHome(startDate, endDate);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoInventoryHomeList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	@RequestMapping("/dataOfVendor")
	public void dataOfVendor(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoInventory> isoInventoryHomeList = isoInventoryDAO.getIsoInventoryHomeVendor(startDate, endDate);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoInventoryHomeList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
}
