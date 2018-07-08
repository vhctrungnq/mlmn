package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HCsiDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCsiCmdLicenceDAO;
import vn.com.vhc.vmsc2.statistics.domain.HCsi;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCsiCmdLicence;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

@Controller
@RequestMapping("/report/core/csi-cmd-licence/*")
public class CsiCmdLicenceController extends BaseController{
	@Autowired
	private VRpDyCsiCmdLicenceDAO vRpDyCsiCmdDAO; 
	@Autowired
	private HCsiDAO hCsiDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@RequestMapping("dy")
	public ModelAndView Show(
			 
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String nodeid,
			
			 ModelMap model) { 
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid); 
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		String title = "CMD LICENCE REPORT DAILY";
		model.addAttribute("title", title);
		List<VRpDyCsiCmdLicence> dyCsiList = vRpDyCsiCmdDAO.getCsiCmdLicence(nodeid,"", s[0], s[1]);
		model.addAttribute("dyCsiList", dyCsiList);
		 /* Chart Start */
		long time = endDate.getTime();
		DateTime dt = new DateTime(time); 
		Date startDay = dt.minusDays(30).toDate();
		List<String> categories = new ArrayList<String>();
		List<HCsi> cmdList = hCsiDao.filter("CMD", "", "", "");
		Map<String, List<Float>> series1 = new LinkedHashMap<String, List<Float>>(); 
		String chartCmdLicenceOnline = "",  html="<table  style='width:99%' >";
		List<String> cmdType = new ArrayList<String>();
		cmdType.add("CMD online");
		cmdType.add("CMD offline");
		
		long time2 = startDay.getTime();
		DateTime dt2 = new DateTime(time2);
		for(int j = 0; j < 30; j++){
			Date d = dt2.plusDays(j).toDate();
			categories.add(df.format(d));
		}
		/* Chart Start */ 
		if(cmdList.size() > 0)
		{
			for(int i=0; i < cmdList.size(); i++)
			{  
				for(int k = 0; k < cmdType.size(); k++)
				{
					List<Float> csiCmdLicenceOnline = new ArrayList<Float>();
					for(int j = 0; j < 30; j++){
						Date d = dt2.plusDays(j).toDate(); 
					List<VRpDyCsiCmdLicence> csiCmdOnline = vRpDyCsiCmdDAO.getCsiCmdLicence(cmdList.get(i).getNodeSub(),cmdType.get(k), df.format(d), df.format(d));
				 
						if(csiCmdOnline.size()==0)
						{ 
							csiCmdLicenceOnline.add(Float.parseFloat("0"));
						}
						else
						{ 
							csiCmdLicenceOnline.add(csiCmdOnline.get(0).getUtil()); 
						} 
					}
					series1.put(cmdType.get(k), csiCmdLicenceOnline); 
					
				}
				
				
			 
		chartCmdLicenceOnline += Chart.basicLineNew("chartCmdLicenceOnline"+cmdList.get(i).getNodeSub(), "CMD Licence Util (%), "+ cmdList.get(i).getNodeSub(),"(%)", categories, series1 ); 	
		 
	    model.addAttribute("chartCmdLicenceOnline" , chartCmdLicenceOnline);
		html += "<tr><td><div id='chartCmdLicenceOnline"+cmdList.get(i).getNodeSub()+ "'></div></td></tr>" ;
		 
		
		
		}
		
	}
		html = html+"</table>" ;
		model.addAttribute("html" , html); 
		return new ModelAndView("dyCsiCmdLicence");
	}
}
