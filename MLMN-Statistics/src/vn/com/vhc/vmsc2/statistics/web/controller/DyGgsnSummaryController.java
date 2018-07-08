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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HggsnDAO;
import vn.com.vhc.vmsc2.statistics.dao.VrpDyGgsnSummaryDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hggsn;
import vn.com.vhc.vmsc2.statistics.domain.VrpDyGgsnSummary;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core/ggsn/*")
public class DyGgsnSummaryController extends BaseController{
@Autowired
private VrpDyGgsnSummaryDAO  DyGgsnSummaryDao;
@Autowired
private HggsnDAO GGSNDao;

private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

@RequestMapping("dy")
public String showReport(
	 @RequestParam(required = false) String ggsnid,
	 @RequestParam(required = false) Date startDate,
	 @RequestParam(required = false) Date endDate,
	 Model model, HttpServletRequest request)
		{
	long currentTime = System.currentTimeMillis();		
	if (endDate == null) {
		endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
	}
	if (startDate == null) {
		startDate = endDate;
	}
			if(ggsnid == null){
				ggsnid = "";
			}
			
			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			model.addAttribute("ggsnid", ggsnid);
			
			List<VrpDyGgsnSummary> DyGgsnSummary = DyGgsnSummaryDao.filter(df.format(startDate), df.format(endDate),ggsnid);
			model.addAttribute("DyGgsnSummary", DyGgsnSummary);
			
			
			List<Hggsn> Ggsnlist = GGSNDao.getAllGgsn();
			model.addAttribute("Ggsnlist", Ggsnlist);
			model.addAttribute("ggsnid", ggsnid);
				
			// Chart Start 
			long time = endDate.getTime();
			DateTime dt = new DateTime(time);
			Date destDay = new Date(time);
			Date startDay = dt.minusDays(30).toDate();
			
			Map<String, List<Float>> seriesCPLOAD = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesPSSR = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesPDP = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesTHP = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesTraffic = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesOCS = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesGxCCR = new LinkedHashMap<String, List<Float>>();
			
			List<String> categories = new ArrayList<String>();
			String chartAreaCPLOAD = "";
			String chartAreaPSSR = "";
			String chartAreaPDP = "";
			String chartAreaTHP = "";
			String chartAreaTrafic = "";
			String chartAreaOCS = "";
			String chartAreaGxCCR = "";
			
			for(int i = 0; i < Ggsnlist.size(); i++)
			{
				
				List<VrpDyGgsnSummary> GgsnListChart = DyGgsnSummaryDao.filter(df.format(startDay), df.format(destDay), Ggsnlist.get(i).getGgsnid().toString());
				List<Float> chartListCPLOAD = new ArrayList<Float>();
				List<Float> chartListPSSR = new ArrayList<Float>();
				List<Float> chartListPDP = new ArrayList<Float>();
				List<Float> chartListTHP = new ArrayList<Float>();
				List<Float> chartListTraffic = new ArrayList<Float>();
				List<Float> chartListOCS = new ArrayList<Float>();
				List<Float> chartListGxCCR = new ArrayList<Float>();
				
				for(VrpDyGgsnSummary record : GgsnListChart){
					if( record.getCpuLoadr() == null)
						record.setCpuLoadr(Float.parseFloat("0"));
					if( record.getPssr() == null)
						record.setPssr(Float.parseFloat("0"));
					if( record.getPdpUtil() == null)
						record.setPdpUtil(Float.parseFloat("0"));
					if( record.getThp() == null)
						record.setThp(Float.parseFloat("0"));
					if( record.getTraffic() == null)
						record.setTraffic(Float.parseFloat("0"));
					if( record.getOcsSessionIniSr() == null)
						record.setOcsSessionIniSr(Float.parseFloat("0"));
					if( record.getGxCcrSr() == null)
						record.setGxCcrSr(Float.parseFloat("0"));
					
					chartListCPLOAD.add(record.getCpuLoadr());
					chartListPSSR.add(record.getPssr());
					chartListPDP.add(record.getPdpUtil());
					chartListTHP.add(record.getThp());
					chartListTraffic.add(record.getTraffic());
					chartListOCS.add(record.getOcsSessionIniSr());
					chartListGxCCR.add(record.getGxCcrSr());
				}
				seriesCPLOAD.put(Ggsnlist.get(i).getGgsnid(), chartListCPLOAD);
				seriesPSSR.put(Ggsnlist.get(i).getGgsnid(), chartListPSSR);
				seriesPDP.put(Ggsnlist.get(i).getGgsnid(), chartListPDP);
				seriesTHP.put(Ggsnlist.get(i).getGgsnid(), chartListTHP);
				seriesTraffic.put(Ggsnlist.get(i).getGgsnid(), chartListTraffic);
				seriesOCS.put(Ggsnlist.get(i).getGgsnid(), chartListOCS);
				seriesGxCCR.put(Ggsnlist.get(i).getGgsnid(), chartListGxCCR);
			}
			List<VrpDyGgsnSummary> ggsnDay = DyGgsnSummaryDao.filter1(df.format(startDay), df.format(destDay));
			for(int i=0;i<ggsnDay.size();i++)
			{
				String strDay = df.format(ggsnDay.get(i).getDay());
				strDay = strDay.substring(0, strDay.lastIndexOf("/"));
				categories.add(strDay);
			}
			
			chartAreaCPLOAD += Chart.basicLineNew("chartCPLOAD", "Biểu đồ CPU LOAD 30 ngày gần nhất","%", categories, seriesCPLOAD);
			chartAreaPSSR += Chart.basicLineNew("chartPSSR", "Biểu đồ PSSR 30 ngày gần nhất","%", categories, seriesPSSR);
			chartAreaPDP += Chart.basicLineNew("chartPDP", "Biểu đồ PDP util 30 ngày gần nhất","%", categories, seriesPDP);
			chartAreaTHP += Chart.basicLineNew("chartTHP", "Biểu đồ THP 30 ngày gần nhất","%", categories, seriesTHP);
			chartAreaTrafic += Chart.basicLineNew("chartTrafic", "Biểu đồ Traffic (MB) 30 ngày gần nhất","%", categories, seriesTraffic);
			chartAreaOCS += Chart.basicLineNew("chartOCS", "Biểu đồ OCS Session Ini SR(%) 30 ngày gần nhất","%", categories, seriesOCS);
			chartAreaGxCCR += Chart.basicLineNew("chartGxCCR", "Biểu đồ Gx CCR SR(%) 30 ngày gần nhất","%", categories, seriesGxCCR);

			model.addAttribute("chartAreaCPLOAD", chartAreaCPLOAD);
			model.addAttribute("chartAreaPSSR", chartAreaPSSR);
			model.addAttribute("chartdivPDP", chartAreaPDP);
			model.addAttribute("chartdivTHP", chartAreaTHP);
			model.addAttribute("chartdivTrafic", chartAreaTrafic);
			model.addAttribute("chartdivOCS", chartAreaOCS);
			model.addAttribute("chartdivGxCCR", chartAreaGxCCR);

			
			return "dyGgsnSummaryList";
		}

}
