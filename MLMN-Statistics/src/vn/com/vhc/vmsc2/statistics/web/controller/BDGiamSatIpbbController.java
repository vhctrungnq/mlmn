package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.VRpipbbdataDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpipbbdata;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;



@Controller
@RequestMapping("/report/bieu-do-giam-sat-ipbb/*")
public class BDGiamSatIpbbController {
	@Autowired
	private VRpipbbdataDAO vRpipbbdataDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String autoRefresh,@RequestParam(required = false) String date,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		List <VRpipbbdata> vRpipbbdata;
		long currentTime = System.currentTimeMillis();
		if (date == null) {
			date = df.format(new Date(currentTime - 24 * 60 * 60 * 1000));
		}
		vRpipbbdata = vRpipbbdataDAO.getBadLink(date,function);
		List<String> directionIn = new ArrayList<String>();
		List<Float> badLinkUtilIn = new ArrayList<Float>();
		List<String> directionOut = new ArrayList<String>();
		List<Float> badLinkUtilOut = new ArrayList<Float>();
		List<Float> ipbbBaseline = new ArrayList<Float>();
	
		for(int i=0; i<vRpipbbdata.size();i++)
		{
			badLinkUtilIn.add(vRpipbbdata.get(i).getInMaxUtilization());
			directionIn.add(vRpipbbdata.get(i).getDirection());
			badLinkUtilOut.add(vRpipbbdata.get(i).getOutMaxUtilization());
			directionOut.add(vRpipbbdata.get(i).getDirection());
			ipbbBaseline.add((float) 50);
		}
		
		Map<String, List<Float>> seriesIn = new LinkedHashMap<String, List<Float>>();
		seriesIn.put("Traffic in--2f7ed8", badLinkUtilIn);
		Map<String, List<Float>> seriesOut = new LinkedHashMap<String, List<Float>>();
		seriesOut.put("Traffic out--2f7ed8", badLinkUtilOut);
		
		seriesIn.put("baseline-line-FF0000", ipbbBaseline);
		seriesOut.put("baseline-line-FF0000", ipbbBaseline);
		model.addAttribute("chartUtilIn", Chart.columnChart("chartUtilIn", "IPBB Data -  Traffic in","Max utilization (Kbit/s)","30","100", directionIn, seriesIn));
		model.addAttribute("chartUtilOut", Chart.columnChart("chartUtilOut", "IPBB Data - Traffic out","Max utilization (Kbit/s)","30","100", directionOut, seriesOut));
		
		if(autoRefresh == null)
			model.addAttribute("autoRefresh", "60");
		else
			model.addAttribute("autoRefresh", autoRefresh);
		
		model.addAttribute("vRpipbbdata", vRpipbbdata);
		model.addAttribute("function", function);
		model.addAttribute("date", date);
		return "bieuDoGiamSatIpbb";
	}
}
