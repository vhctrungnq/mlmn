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

import vn.com.vhc.vmsc2.statistics.dao.VRpHrIpbbTrafficDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrIpbbTraffic;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;



@Controller
@RequestMapping("/report/bieu-do-sut-giam-traff-ipbb/*")
public class BDSutGiamTrafficIpbbController  extends BaseController {
	@Autowired
	private VRpHrIpbbTrafficDAO vRpipbbdataDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String autoRefresh,@RequestParam(required = false) String date,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		long currentTime = System.currentTimeMillis();
		if (date == null) {
			date = df.format(new Date(currentTime - 24 * 60 * 60 * 1000));
		}
		List <VRpHrIpbbTraffic> vRpipbbList = vRpipbbdataDAO.getList(date,function);
		List<String> directionIn = new ArrayList<String>();
		List<Float> traffInNew = new ArrayList<Float>();
		List<Float> traffInOld = new ArrayList<Float>();
		List<String> directionOut = new ArrayList<String>();
		List<Float> traffOutNew = new ArrayList<Float>();
		List<Float> traffOutOld = new ArrayList<Float>();
		for(int i=0; i<vRpipbbList.size();i++)
			{
				traffInNew.add(vRpipbbList.get(i).getTraffInNew());
				traffInOld.add(vRpipbbList.get(i).getTraffInOld());
				traffOutNew.add(vRpipbbList.get(i).getTraffOutNew());
				traffOutOld.add(vRpipbbList.get(i).getTraffOutOld());
				
				directionIn.add(vRpipbbList.get(i).getDirection());
				directionOut.add(vRpipbbList.get(i).getDirection());
			}
		
		
		
		Map<String, List<Float>> seriesIn = new LinkedHashMap<String, List<Float>>();
		seriesIn.put("Traffic in new--2f7ed8", traffInNew);
		seriesIn.put("Traffic in Old--4572a7", traffInOld);
		Map<String, List<Float>> seriesOut = new LinkedHashMap<String, List<Float>>();
		seriesOut.put("Traffic out new--2f7ed8", traffOutNew);
		seriesOut.put("Traffic out old--4572a7", traffOutOld);
		
		
		model.addAttribute("chartUtilIn", Chart.columnChart("chartUtilIn", "IPBB Data -  Traffic in","Max utilization (Kbit/s)","30","100", directionIn, seriesIn));
		model.addAttribute("chartUtilOut", Chart.columnChart("chartUtilOut", "IPBB Data - Traffic out","Max utilization (Kbit/s)","30","100", directionOut, seriesOut));
		
		if(autoRefresh == null)
			model.addAttribute("autoRefresh", "60");
		else
			model.addAttribute("autoRefresh", autoRefresh);
		
		model.addAttribute("function", function);
		model.addAttribute("date", date);
		model.addAttribute("vRpipbbList", vRpipbbList);
		return "bieuDoSutGiamTrafficIpbb";
	}
}
