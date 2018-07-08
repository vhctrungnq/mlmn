 package vn.com.vhc.vmsc2.statistics.web.controller.ericsson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscUtilbwPort3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscUtilbwPort3gDAO; 
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G; 
import vn.com.vhc.vmsc2.statistics.domain.DyBscUtilbwPort3g;
import vn.com.vhc.vmsc2.statistics.domain.HrBscUtilbwPort3g;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;

@Controller
@RequestMapping("/report/radio3g/rnc/ericsson/util-bw-port/*")
public class BscUtilBwPort3gController extends BaseController{
	
		@Autowired
		private DyBscUtilbwPort3gDAO dyBscUtilbwPort3gDao;
		@Autowired
		private HrBscUtilbwPort3gDAO hrBscUtilbwPort3gDao;
		@Autowired
		private Bsc3GDAO bsc3gDao;
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		@RequestMapping("hr")
		public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String sdate,
				@RequestParam(required = false) String shour, @RequestParam(required = false) String edate, 
				@RequestParam(required = false) String ehour,ModelMap model, HttpServletRequest request)
				{
					int order = 0;
					String column = "";
					try {
						order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
						column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
					} catch (NumberFormatException e) {
					}  
					
					if (sdate== null || sdate.equals("") || (sdate != null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate) == false)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						sdate = df.format(cal.getTime());
					} 
					
					if (edate == null || edate.equals("") || (edate != null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate) == false)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						edate = df.format(cal.getTime());
					}
			 
					if (shour == null) shour = "0";
					else shour = shour.substring(0, shour.indexOf(":"));
					
					if (ehour == null) ehour = "23";
					else ehour = ehour.substring(0, ehour.indexOf(":"));
					
				    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
					
				    List<Bsc3G> bscList = bsc3gDao.getBsc3GByVendor("ERICSSON");
					model.addAttribute("bscList", bscList);
					
				    List<HrBscUtilbwPort3g> hrBscUtilbwPort3g = hrBscUtilbwPort3gDao.dataList(sdate, shour, edate, ehour, bscid, order, column); 
					
					model.addAttribute("sdate", sdate);
					model.addAttribute("edate", edate);
					model.addAttribute("shourCBB", shour + ":00");
					model.addAttribute("ehourCBB", ehour + ":00"); 
					model.addAttribute("bscCBB", bscid);
					model.addAttribute("hrBscUtilbwPort3g", hrBscUtilbwPort3g);
					model.addAttribute("hourList", hourList);
					
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "HrBscUtilbwPort3G_" + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					
					return "aEricsson/hrBscUtilbwPort3g";
}
		
		@RequestMapping("dy")
		public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String sdate,
			 @RequestParam(required = false) String edate,  ModelMap model , HttpServletRequest request)
				{
					int order = 0;
					String column = "";
					try {
						order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
						column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
					} catch (NumberFormatException e) {
					}  
					
					if (sdate== null
							|| sdate.equals("")
							|| (sdate != null && !sdate.equals("") && DateTools
									.isValid("dd/MM/yyyy", sdate) == false)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						sdate = df.format(cal.getTime());
					} 
					
					if (edate == null
							|| edate.equals("")
							|| (edate != null && !edate.equals("") && DateTools
									.isValid("dd/MM/yyyy", edate) == false)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						edate = df.format(cal.getTime());
					} 
					
					//Lay danh sach Bsc 3G cua vendor ericsson
					List<Bsc3G> bscList = bsc3gDao.getBsc3GByVendor("ERICSSON"); 
					
					//Lay du lieu bao cao Utility BW Port 3g
					List<DyBscUtilbwPort3g> dyBscUtilbwPort3g = dyBscUtilbwPort3gDao.dataList(sdate, edate, bscid, order, column);
					
					//Lay du lieu ve bieu do theo chi so "TOTAL_OCTETS"
					String html = "";
					String script = "";
					List<String> categories = new ArrayList<String>();	
					List<DyBscUtilbwPort3g> dayList = dyBscUtilbwPort3gDao.getDay(edate);
					for(int i=0;i<dayList.size();i++){
						categories.add(df.format(dayList.get(i).getDay()));			
					}
					
					String dept = "Biểu đồ Total Octets 30 ngày";
					
					html += "<tr><td><div id=\"" + "chartdiv" + "\" style=\"width: 98%; height: 400px; margin: 1em auto;\"></div></td></tr>";

					Map<String, List<Float>> dlDataSeries = new LinkedHashMap<String, List<Float>>();
					for(int j=0;j<bscList.size();j++){
						List<DyBscUtilbwPort3g> dataChar = dyBscUtilbwPort3gDao.dataChar(bscList.get(j).getBscid(), edate);
						List<Float> dlDataList = new ArrayList<Float>();
						
						for(int k=0;k<dataChar.size();k++){
							dlDataList.add(dataChar.get(k).getTotalOctets());
						}
						dlDataSeries.put(bscList.get(j).getBscid(), dlDataList);
					}
					script += Chart.basicLine("chartdiv", dept, "Total Octets", categories, dlDataSeries);
					
					//addAttribute cac gia tri 
					model.addAttribute("chartdivScript", script);
					model.addAttribute("html", html);
					model.addAttribute("sdate", sdate);
					model.addAttribute("edate", edate); 
					model.addAttribute("bscCBB", bscid);
					model.addAttribute("bscList", bscList);
					model.addAttribute("dyBscUtilbwPort3g", dyBscUtilbwPort3g);
			 
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "DyBscUtilbwPort3G_" + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					
					return "aEricsson/dyBscUtilbwPort3g";
		} 
}
