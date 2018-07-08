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

import vn.com.vhc.vmsc2.statistics.dao.VRpHrCsiCmdVolatilityDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCsiCmdVolatility;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;



@Controller
@RequestMapping("/report/core/csi-cmd-volatility/*")
public class CsiCmdVolatilityController {
 
	@Autowired
	private VRpHrCsiCmdVolatilityDAO vRpCsicmddataDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String autoRefresh,@RequestParam(required = false) String date,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		List <VRpHrCsiCmdVolatility> vRpCsicmdList;
		if (date == null) {
			date = df.format(new Date());
		}
		vRpCsicmdList = vRpCsicmddataDAO.getList(date,function);
		List<String> directionIn = new ArrayList<String>();
		List<Float> traffInNew = new ArrayList<Float>();
		List<Float> traffInOld = new ArrayList<Float>();
		List<String> directionOut = new ArrayList<String>();
		List<Float> traffOutNew = new ArrayList<Float>();
		List<Float> traffOutOld = new ArrayList<Float>();
		for(int i=0; i<vRpCsicmdList.size();i++)
			{
				traffInNew.add(vRpCsicmdList.get(i).getCpuused());
				traffInOld.add(vRpCsicmdList.get(i).getPreviousCpuused());
				traffOutNew.add(vRpCsicmdList.get(i).getMemused());
				traffOutOld.add(vRpCsicmdList.get(i).getPreviousMemused());
				
				directionIn.add(vRpCsicmdList.get(i).getType());
				directionOut.add(vRpCsicmdList.get(i).getType());
			}
		
		
		
		Map<String, List<Float>> seriesIn = new LinkedHashMap<String, List<Float>>();
		seriesIn.put("CPU Usage--2f7ed8", traffInNew);
		seriesIn.put("CPU Usage Previous--4572a7", traffInOld);
		Map<String, List<Float>> seriesOut = new LinkedHashMap<String, List<Float>>();
		seriesOut.put("MEMORY Usage--2f7ed8", traffOutNew);
		seriesOut.put("MEMORY Usage Previous--4572a7", traffOutOld);
		
		
		model.addAttribute("chartUtilIn", Chart.columnChart("chartUtilIn", "CSI CMD - CPU Usage","Usage (%)","0","100", directionIn, seriesIn));
		model.addAttribute("chartUtilOut", Chart.columnChart("chartUtilOut", "CSI MEMORY - MEMORY Usage","Usage (%)","0","100", directionOut, seriesOut));
		
		if(autoRefresh == null)
			model.addAttribute("autoRefresh", "60");
		else
			model.addAttribute("autoRefresh", autoRefresh);
		
		model.addAttribute("function", function);
		model.addAttribute("date", date);
		model.addAttribute("vRpCsicmdList", vRpCsicmdList);
		return "hrCsiCmdVolatility";
	}
}
