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
import vn.com.vhc.vmsc2.statistics.dao.DyBscUtilbw3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscUtilbw3gDAO; 
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;  
import vn.com.vhc.vmsc2.statistics.domain.DyBscUtilbw3g;
import vn.com.vhc.vmsc2.statistics.domain.HrBscUtilbw3g;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;

@Controller
@RequestMapping("/report/radio3g/rnc/ericsson/util-bw/*")
public class BscUtilBw3gController extends BaseController{
	
		@Autowired
		private DyBscUtilbw3gDAO dyBscUtilbw3gDao;
		@Autowired
		private HrBscUtilbw3gDAO hrBscUtilbw3gDao;
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
					
				    List<HrBscUtilbw3g> hrBscUtilbw3g = hrBscUtilbw3gDao.dataList(sdate, shour, edate, ehour, bscid, order, column); 
					
					model.addAttribute("sdate", sdate);
					model.addAttribute("edate", edate);
					model.addAttribute("shourCBB", shour + ":00");
					model.addAttribute("ehourCBB", ehour + ":00"); 
					model.addAttribute("bscCBB", bscid);
					model.addAttribute("hrBscUtilbw3g", hrBscUtilbw3g);
					model.addAttribute("hourList", hourList);
					
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "HrBscUtilbw3G_" + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					
					return "aEricsson/hrBscUtilbw3g";
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
					
					List<Bsc3G> bscList = bsc3gDao.getBsc3GByVendor("ERICSSON"); 
					
					List<DyBscUtilbw3g> dyBscUtilbw3g = dyBscUtilbw3gDao.dataList(sdate, edate, bscid, order, column);
					
					// Ve bieu do cac chi so
					String html = ""; 
					List<String> categories = new ArrayList<String>();	
					List<DyBscUtilbw3g> dayList = dyBscUtilbw3gDao.getDay(edate);
					for(int i=0;i<dayList.size();i++){
						categories.add(df.format(dayList.get(i).getDay()));			
					}
					
					//Ten id bieu do tuong ung
					String[] deptEng = {"1", "2", "3", "4", "5"};
					
					for(int i = 0; i< 5; i++){
						html += "<tr><td><div id=\"" + "chartdiv" + deptEng[i] + "\" style=\"width: 98%; height: 400px; margin: 1em auto;\"></div></td></tr>";
					} 

					Map<String, List<Float>> dlDataSeries1 = new LinkedHashMap<String, List<Float>>();
					Map<String, List<Float>> dlDataSeries2 = new LinkedHashMap<String, List<Float>>();
					Map<String, List<Float>> dlDataSeries3 = new LinkedHashMap<String, List<Float>>();
					Map<String, List<Float>> dlDataSeries4 = new LinkedHashMap<String, List<Float>>();
					Map<String, List<Float>> dlDataSeries5 = new LinkedHashMap<String, List<Float>>();
					
					for(int j=0;j<bscList.size();j++){
						List<DyBscUtilbw3g> dataChar = dyBscUtilbw3gDao.dataChar(bscList.get(j).getBscid(), edate);
						
						List<Float> dlDataList1 = new ArrayList<Float>();
						List<Float> dlDataList2 = new ArrayList<Float>();
						List<Float> dlDataList3 = new ArrayList<Float>();
						List<Float> dlDataList4 = new ArrayList<Float>();
						List<Float> dlDataList5 = new ArrayList<Float>();
						
						for(int k=0;k<dataChar.size();k++){
							dlDataList1.add(dataChar.get(k).getIupsDlul());
							dlDataList2.add(dataChar.get(k).getIupsDl());
							dlDataList3.add(dataChar.get(k).getIupsUl());
							dlDataList4.add(dataChar.get(k).getUsedCapacity());
							dlDataList5.add(dataChar.get(k).getUserFachDchHs());
						}
						dlDataSeries1.put(bscList.get(j).getBscid(), dlDataList1);
						dlDataSeries2.put(bscList.get(j).getBscid(), dlDataList2);
						dlDataSeries3.put(bscList.get(j).getBscid(), dlDataList3);
						dlDataSeries4.put(bscList.get(j).getBscid(), dlDataList4);
						dlDataSeries5.put(bscList.get(j).getBscid(), dlDataList5);
					}
					 
					String script1 = Chart.basicLine("chartdiv1", "Biểu đồ IuPS_DLUL_kbps 30 ngày", "IuPS_DLUL (kbps)", categories, dlDataSeries1);
					String script2 = Chart.basicLine("chartdiv2", "Biểu đồ IuPS_DL_kbps 30 ngày", "IuPS_DL (kbps)", categories, dlDataSeries2);
					String script3 = Chart.basicLine("chartdiv3", "Biểu đồ IuPS_UL_kbps 30 ngày", "IuPS_UL (kbps)", categories, dlDataSeries3);
					String script4 = Chart.basicLine("chartdiv4", "Biểu đồ Used Capacity Iub Thoughput 30 ngày", "Used Capacity Iub Thoughput", categories, dlDataSeries4);
					String script5 = Chart.basicLine("chartdiv5", "Biểu đồ Users On FACH/DCH/HS 30 ngày", "Users On FACH/DCH/HS", categories, dlDataSeries5);
					 
					//addAttribute cac gia tri
					model.addAttribute("chartdivScript1", script1);
					model.addAttribute("chartdivScript2", script2);
					model.addAttribute("chartdivScript3", script3);
					model.addAttribute("chartdivScript4", script4);
					model.addAttribute("chartdivScript5", script5);
					model.addAttribute("html", html);
					model.addAttribute("sdate", sdate);
					model.addAttribute("edate", edate); 
					model.addAttribute("bscCBB", bscid);
					model.addAttribute("bscList", bscList);
					model.addAttribute("dyBscUtilbw3g", dyBscUtilbw3g);
			 
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "DyBscUtilbw3G_" + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					
					return "aEricsson/dyBscUtilbw3g";
		} 
}
