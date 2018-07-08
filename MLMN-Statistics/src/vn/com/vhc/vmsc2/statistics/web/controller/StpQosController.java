package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyStpDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrStpDAO;
import vn.com.vhc.vmsc2.statistics.domain.Stp;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyStp;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrStp;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core/stp-qos/*")
public class StpQosController extends BaseController{
	
	@Autowired
	private VRpHrStpDAO hrStpDAO;
	@Autowired
	private VRpDyStpDAO dyStpDAO; 
	@Autowired
	private StpDAO stpDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@RequestMapping("hr")
	public String showReport(@RequestParam(required = false) String stpid, @RequestParam(required = false) String sliid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
			{
				
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
				if(stpid == null){
					stpid = "";
				}
				if(sliid == null){
					sliid = "";
				}
				if (endHour == null) {
					endHour = "23";
				} else {
					if(endHour.indexOf(":") >0)
						endHour = endHour.substring(0, endHour.indexOf(":"));
					else
						endHour = "0";
					}
				if (startHour == null) {
					startHour = "0";
				} else {	
					if(startHour.indexOf(":") >0)
						startHour = startHour.substring(0, startHour.indexOf(":"));
					else
						startHour = "0";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("startHour", startHour + ":00");
				model.addAttribute("endHour", endHour + ":00");
				
				int startRecord = 0;
			    try {
			     startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("cell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			    } catch (NumberFormatException e) {
			    }
			    int endRecord = startRecord + 100;
			    filter.setStartRecord(startRecord);
			    filter.setEndRecord(endRecord);
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			    List<VRpHrStp> hrStp = hrStpDAO.filter(startHour,startDate,endHour,endDate,stpid, sliid);
				
			    if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrStp = hrStpDAO.filter2(startHour,startDate,endHour,stpid, sliid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrStp = hrStpDAO.filter2(startHour,startDate,endHour,stpid, sliid);
				}
			    
			    List<Stp> stpList = stpDAO.getStpList();
			    List<Stp> sliList = stpDAO.getSliList();
				model.addAttribute("stpid", stpid);
				model.addAttribute("sliid", sliid);
				model.addAttribute("stpList", stpList);
				model.addAttribute("sliList", sliList);
				model.addAttribute("hrStp", hrStp);
				model.addAttribute("hourList", hourList);
				return "hrStpList";
			}
	
	@RequestMapping("dy")
	public String showReport(@RequestParam(required = false) String stpid, 
		 @RequestParam(required = false) Date startDate,
		 @RequestParam(required = false) Date endDate,
		 ModelMap model)
			{
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
				if(stpid == null){
					stpid = "";
				}
			
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				
				List<Stp> stpList = stpDAO.getStpList();
				List<VRpDyStp> dyStp = dyStpDAO.filter(df.format(startDate), df.format(endDate),stpid);
				
				
				model.addAttribute("stpList", stpList);
				model.addAttribute("stpid", stpid);
				
				model.addAttribute("dyStp", dyStp);
				

				/* Chart Start */
				long time = endDate.getTime();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date destDay = new Date(time - 24 * 60 * 60 * 1000);
				Date startDay = dt.minusDays(30).toDate();
				
			
				Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
				Map<String, List<Float>> series2 = new LinkedHashMap<String, List<Float>>();
				List<String> categories = new ArrayList<String>();
				String chartArea = "", chartArea2="";

				for(int i = 0; i < stpList.size(); i++)
				{
					
					List<VRpDyStp> dyList = dyStpDAO.filter(df.format(startDay), df.format(destDay),stpList.get(i).getStpid());
					List<Float> inMaxUtilList = new ArrayList<Float>();
					List<Float> inMaxcpList = new ArrayList<Float>();
					for(VRpDyStp record : dyList){
						if(record.getUtility()==null)
							record.setUtility(Float.parseFloat("0"));
						if(record.getMaxCpLoad()==null)
							record.setMaxCpLoad(Float.parseFloat("0"));
						inMaxUtilList.add(record.getMaxUtility());
						inMaxcpList.add(record.getMaxCpLoad());
						
					
					}
					series.put(stpList.get(i).getStpid(), inMaxUtilList);
					series2.put(stpList.get(i).getStpid(), inMaxcpList);
					
				}
				List<VRpDyStp> mscDay = dyStpDAO.filterDay(df.format(startDay), df.format(destDay));
				for(int j = 0; j < mscDay.size(); j++){
					categories.add(df.format(mscDay.get(j).getDay()));
				}
				
				chartArea += Chart.basicLineNew("chart", "Biểu đồ UTILITY (%)","%", categories, series);
				chartArea += Chart.basicLineNew("chart2", "Biểu đồ MAX CPLOAD (%)","%", categories, series2);
				model.addAttribute("chartdiv", chartArea);
				model.addAttribute("chartdiv2", chartArea2);
				return "dyStpList";
			}	
}
