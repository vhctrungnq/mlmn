package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscMgwCPLoadDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrMscMgwCPLoadDAO;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscMgwCPLoad;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrMscMgwCPLoad;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core/processor-load/*")
public class MscProcesloadMgwController extends BaseController{
	@Autowired
	private VRpHrMscMgwCPLoadDAO vRpHrMscMgwCPLoadDAO;
	@Autowired
	private VRpDyMscMgwCPLoadDAO vRpDyMscMgwCPLoadDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
@RequestMapping("dy")
public String showReport(@RequestParam(required = false) String mscid,
		@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
		@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
	{
		
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(mscid == null){
			mscid = "";
		}
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		
	    
		List<VRpDyMscMgwCPLoad> vRpDyMscMgwCPLoad = vRpDyMscMgwCPLoadDAO.filter(df.format(startDate), df.format(endDate), mscid);
		model.addAttribute("mscid", mscid);
		model.addAttribute("vRpDyMscMgwCPLoad", vRpDyMscMgwCPLoad);
		
		/* Chart Start */
		MscFilter h_msc = new MscFilter();
		h_msc.setMscid("MGH");
		List<Msc> mscList = mscDAO.filter(h_msc);
		model.addAttribute("mscList", mscList);
		long time = System.currentTimeMillis();
		DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
		Date destDay = new Date(time - 24 * 60 * 60 * 1000);
		Date startDay = dt.minusDays(30).toDate();
		
	
		Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
		List<String> categories = new ArrayList<String>();
		String chartArea = "";

		for(int i = 0; i < mscList.size(); i++)
		{
			
			List<VRpDyMscMgwCPLoad> dyList = vRpDyMscMgwCPLoadDAO.filter(df.format(startDay), df.format(destDay),mscList.get(i).getMscid());
			List<String> inMaxcpList = new ArrayList<String>();
			for(VRpDyMscMgwCPLoad record : dyList){
				if(record.getProcessorload()==null)
					record.setProcessorload(Float.parseFloat("0"));
				inMaxcpList.add(record.getProcessorload().toString());
				
			
			}
			series.put(mscList.get(i).getMscid(), inMaxcpList);
			
		}
		List<VRpDyMscMgwCPLoad> mscDay = vRpDyMscMgwCPLoadDAO.filterDay(df.format(startDay), df.format(destDay));
		for(int j = 0; j < mscDay.size(); j++){
			categories.add(df.format(mscDay.get(j).getDay()));
		}
		
		chartArea += Chart.timebasicLine("chart", "Biểu đồ Max ProcessLoad (%)", categories, series,"%");
		model.addAttribute("chartdiv", chartArea);
		return "dyMscMgwCPLoadlist";
		
	}
@RequestMapping("hr")
public String hrList(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
		@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
		ModelMap model, HttpServletRequest request)
		{
			
			if (endDate == null) {
				endDate = new Date();
			}
			if (startDate == null) {
				startDate = endDate;
			}
			if(mscid == null){
				mscid = "";
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
	
		    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		    List<VRpHrMscMgwCPLoad> vRpHrMscMgwCPLoad = vRpHrMscMgwCPLoadDAO.filter(startHour,startDate,endHour,endDate,mscid);
			
		    if(endDate.compareTo(startDate) < 0)
			{
				startDate = new Date();
				vRpHrMscMgwCPLoad = vRpHrMscMgwCPLoadDAO.filter2(startHour,startDate,endHour,mscid);
			}
			else if(endDate.compareTo(startDate)== 0)
			{
				
				vRpHrMscMgwCPLoad = vRpHrMscMgwCPLoadDAO.filter2(startHour,startDate,endHour,mscid);
			}
		    model.addAttribute("hourList", hourList);
		    MscFilter h_msc = new MscFilter();
			h_msc.setMscid("MGH");
			List<Msc> mscList = mscDAO.filter(h_msc);
			model.addAttribute("mscid", mscid);
			model.addAttribute("mscList", mscList);
			model.addAttribute("vRpHrMscMgwCPLoad", vRpHrMscMgwCPLoad);
			return "hrMscMgwCPLoadlist";
		}
}
