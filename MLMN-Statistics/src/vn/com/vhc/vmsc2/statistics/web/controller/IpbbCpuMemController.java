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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIpbbCpuMemDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbCpuMem;
import vn.com.vhc.vmsc2.statistics.web.filter.IPBBFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/core/ip-backbone-cpu-mem/*")
public class IpbbCpuMemController extends BaseController{
	@Autowired
	private VRpDyIpbbCpuMemDAO vipbbdataDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@RequestMapping("dy")
	public ModelAndView Show(
			 
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String link,
			
			 Model model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(link == null){
			link = "";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("link", link);
		
		String title = "CPU/MEMORY DAILY";
		model.addAttribute("title", title);
		List<VRpDyIpbbCpuMem> ipbbCpuList = vipbbdataDao.filterDaily("V_RP_DY_IPBB_CPU_MEM",link, df.format(startDate), df.format(endDate));
		/* Chart Start */
		long time = endDate.getTime();
		DateTime dt = new DateTime(time);
		Date destDay = new Date(time - 24 * 60 * 60 * 1000);
		Date startDay = dt.minusDays(30).toDate();
		List<String> categories = new ArrayList<String>();
		Map<String, List<Float>> sLCpu = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> sLmem = new LinkedHashMap<String, List<Float>>();
		List<VRpDyIpbbCpuMem> linkList = vipbbdataDao.getLinkList(df.format(startDay), df.format(destDay));
		String cACpu = "", cAMem = "";
		
		long time2 = startDay.getTime();
		DateTime dt2 = new DateTime(time2);
		for(VRpDyIpbbCpuMem strLink: linkList)
		{
			List<Float> link1List = new ArrayList<Float>();
			List<Float> link2List = new ArrayList<Float>();
			for(int j = 0; j < 30; j++){
				Date d = dt2.plusDays(j).toDate();
				List<VRpDyIpbbCpuMem> ipbbCpuMemGraph = vipbbdataDao.filterDaily("V_RP_DY_IPBB_CPU_MEM",strLink.getLink(), df.format(d), df.format(d));
				if(ipbbCpuMemGraph.size()==0)
				{
					link1List.add(Float.parseFloat("0"));
					link2List.add(Float.parseFloat("0"));
				}
				else
				{
					link1List.add(ipbbCpuMemGraph.get(0).getCpuTotal());
					link2List.add(ipbbCpuMemGraph.get(0).getCoverageMem());
				}
				
			}
			 
			sLCpu.put(strLink.getLink(), link1List);
			sLmem.put(strLink.getLink(), link2List);
		}
		
		for(int j = 0; j < 30; j++){
			Date d = dt2.plusDays(j).toDate();
			categories.add(df.format(d));
		}
		cACpu += Chart.basicLineIPBB("cCpu", "CPU Usage (%)","(%)", categories, sLCpu );
		cAMem += Chart.basicLineIPBB("cMem", "Memory Usage (%)","(%)", categories, sLmem  );		
		model.addAttribute("cACpu", cACpu);
		model.addAttribute("cAMem", cAMem); 
		
		// Highlight cpu, mem coverage
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "ipbbCpuList"));
		
		model.addAttribute("ipbbCpuList",ipbbCpuList);
		model.addAttribute("level","dy");
		model.addAttribute("dy", "ui-tabs-selected");
		
		/* Chart Start */
		
		return new ModelAndView("dyIpbbCpuMem");
	}
	
	@RequestMapping("hr")
	public String Showreport(
			 @ModelAttribute("filter") IPBBFilter filter,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) String link,
			
			 Model model) {
		
		if(link == null){
			link = "";
		}
		String title = "CPU/MEMORY HOURLY";
		model.addAttribute("title", title);
		Date edate ;
		long currentTime = System.currentTimeMillis();
		if (endDate==null) {
			edate = new Date(currentTime - 24 * 60 * 60 * 1000);
			endDate = df.format(edate).toString();
		}
		if (startDate==null) {
			startDate = endDate;
		}
		if(startDate.equalsIgnoreCase(endDate))
		{
			startDate = startDate + " 00:00";
			endDate = endDate + " 23:59";
			
		}
		List<VRpDyIpbbCpuMem> ipbbCpuList = vipbbdataDao.filterHourly("V_RP_HR_IPBB_CPU_MEM",link, startDate,endDate);
		model.addAttribute("ipbbCpuList",ipbbCpuList);
		model.addAttribute("level","hr");
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("hr", "ui-tabs-selected");
		return "dyIpbbCpuMem";
	}
	
}
