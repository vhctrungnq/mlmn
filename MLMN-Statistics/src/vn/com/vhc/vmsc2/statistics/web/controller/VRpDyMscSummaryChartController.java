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

import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscSummaryChartDAO;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscSummaryChart;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core/chart-summary/*")
public class VRpDyMscSummaryChartController extends BaseController{
@Autowired
private VRpDyMscSummaryChartDAO  vRpDyMscSummaryChartDao;
@Autowired
private MscDAO mscDAO;

private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

@RequestMapping("dy")
public String showReport(
	 @RequestParam(required = false) Date endDate,
	 ModelMap model)
		{
	long currentTime = System.currentTimeMillis();		
	if (endDate == null) {
		endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
	}
			
			model.addAttribute("endDate", df.format(endDate));

			
			/* Chart Start */
			MscFilter h_msc = new MscFilter();
			h_msc.setMscid("MSH");
			List<Msc> mscList = mscDAO.filter(h_msc);
			model.addAttribute("mscList", mscList);
			long time = endDate.getTime();
			DateTime dt = new DateTime(time);
			Date destDay = new Date(time);
			Date startDay = dt.minusDays(30).toDate();
			
		
			Map<String, List<Float>> series1 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series2 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series3 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series4 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series5 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series6 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series7 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series8 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series9 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series10 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series11 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series12 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series13 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series14 = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> series15 = new LinkedHashMap<String, List<Float>>();
			
			List<String> categories = new ArrayList<String>();
			String  chartArea1 = "", chartArea2 = "", chartArea3 = "", chartArea4 = "", chartArea5 = "", chartArea6 = "",
					chartArea7 = "", chartArea8 = "", chartArea9 = "", chartArea10 = "", chartArea11 = "",chartArea12 = "",
					chartArea13 = "", chartArea14 = "", chartArea15 = "";
			int minVlr = 4000000;
			for(int i = 0; i < mscList.size(); i++)
			{
				
				List<VRpDyMscSummaryChart> dyList = vRpDyMscSummaryChartDao.filter(mscList.get(i).getMscid(),df.format(startDay), df.format(destDay));
				
				List<Float> authList = new ArrayList<Float>();
				List<Float> mapSignList = new ArrayList<Float>();
				List<Float> vlrList = new ArrayList<Float>();
				List<Float> pagingLaList = new ArrayList<Float>();
				List<Float> trunkrouteList = new ArrayList<Float>();
				List<Float> lossrouteList = new ArrayList<Float>();
				List<Float> locationUpdateList = new ArrayList<Float>();
				List<Float> lULacList = new ArrayList<Float>();
				List<Float> chasignList = new ArrayList<Float>();
				List<Float> rabsignList = new ArrayList<Float>();
				List<Float> incTraffList = new ArrayList<Float>();
				List<Float> outGoList = new ArrayList<Float>();
				List<Float> termSMSList = new ArrayList<Float>();
				List<Float> mmUmtList = new ArrayList<Float>();
				List<Float> contextSeizureList = new ArrayList<Float>();
				
				for(VRpDyMscSummaryChart record : dyList){
					if(record.getPiSuccAuth()==null)
						record.setPiSuccAuth(Float.parseFloat("0"));
					if(record.getMapSignSucc()==null)
						record.setMapSignSucc(Float.parseFloat("0"));
					if(record.getTnsvlr()==null)
						record.setTnsvlr(0);
					if(record.getPagingSucc()==null)
						record.setPagingSucc(Float.parseFloat("0"));
					if(record.getTrunkUtil()==null)
						record.setTrunkUtil(Float.parseFloat("0"));
					if(record.getSuccLroutePerform()==null)
						record.setSuccLroutePerform(Float.parseFloat("0"));
					if(record.getLuRegSucc()==null)
						record.setLuRegSucc(Float.parseFloat("0"));
					if(record.getPiLuLaDb()==null)
						record.setPiLuLaDb(Float.parseFloat("0"));
					if(record.getSuccrChassigGsm()==null)
						record.setSuccrChassigGsm(Float.parseFloat("0"));
					if(record.getRabAssigSuccr() ==null)
						record.setRabAssigSuccr(Float.parseFloat("0"));
					if(record.getIncTraff()==null)
						record.setIncTraff(Float.parseFloat("0"));
					if(record.getOugTraff()==null)
						record.setOugTraff(Float.parseFloat("0"));
					if(record.getTermSmsSucc()==null)
						record.setTermSmsSucc(Float.parseFloat("0"));
					if(record.getMmUmtsSucc()==null)
						record.setMmUmtsSucc(Float.parseFloat("0"));
					if(record.getContextSeizureSucc()==null)
						record.setContextSeizureSucc(Float.parseFloat("0"));
					
					
				
					authList.add(record.getPiSuccAuth());
					mapSignList.add(record.getMapSignSucc());
					
					// Check min VLR
					if(record.getTnsvlr() < minVlr)
						minVlr = record.getTnsvlr();
					
					vlrList.add(Float.parseFloat(record.getTnsvlr().toString()));
					pagingLaList.add(record.getPagingSucc());
					trunkrouteList.add(record.getTrunkUtil());
					lossrouteList.add(record.getSuccLroutePerform());
					locationUpdateList.add(record.getLuRegSucc());
					lULacList.add(record.getPiLuLaDb());
					chasignList.add(record.getSuccrChassigGsm());
					rabsignList.add(record.getRabAssigSuccr());
					incTraffList.add(record.getIncTraff());
					outGoList.add(record.getOugTraff());
					termSMSList.add(record.getTermSmsSucc());
					mmUmtList.add(record.getMmUmtsSucc());
					contextSeizureList.add(record.getContextSeizureSucc());
					
				
				}
				series1.put(mscList.get(i).getMscid(), authList);
				series2.put(mscList.get(i).getMscid(), mapSignList);
				series3.put(mscList.get(i).getMscid()+ "-" + "column", vlrList);
				series4.put(mscList.get(i).getMscid(), pagingLaList);
				series5.put(mscList.get(i).getMscid(), trunkrouteList);
				series6.put(mscList.get(i).getMscid(), lossrouteList);
				series7.put(mscList.get(i).getMscid(), locationUpdateList);
				series8.put(mscList.get(i).getMscid(), lULacList);
				series9.put(mscList.get(i).getMscid(), chasignList);
				series10.put(mscList.get(i).getMscid(), rabsignList);
				series11.put(mscList.get(i).getMscid(), incTraffList);
				series12.put(mscList.get(i).getMscid(), outGoList);
				series13.put(mscList.get(i).getMscid(), termSMSList);
				series14.put(mscList.get(i).getMscid(), mmUmtList);
				series15.put(mscList.get(i).getMscid(), contextSeizureList);
				
			}
			List<VRpDyMscSummaryChart> mscDay = vRpDyMscSummaryChartDao.filter1(df.format(startDay), df.format(destDay));
			for(int j = 0; j < mscDay.size(); j++){
				String strDay = df.format(mscDay.get(j).getDay());
				strDay = strDay.substring(0, strDay.lastIndexOf("/"));
				categories.add(strDay);
			}
			minVlr  = minVlr - (minVlr*10/100);
			chartArea1 += Chart.basicLineNew("chart1", "Biểu đồ Authencation Success (%)","%", categories, series1);
			chartArea2 += Chart.basicLineNew("chart2", "Biểu đồ MapSignal Performance (%)","%", categories, series2);
			chartArea3 += Chart.multiColumnWelcome("chart3", "Biểu đồ Vlr Subs", categories, series3, minVlr+"", "");
			//chartArea3 += Chart.dualAxesLineAndColumn("chart3", "VLRsub", "", "Vlr Subs", categories, series3);
			chartArea4 += Chart.basicLineNew("chart4", "Biểu đồ Paging Success LAC (%)","%", categories, series4);
			chartArea5 += Chart.basicLineNew("chart5", "Biểu đồ TrunkRoute Performance (%)","%", categories, series5);
			chartArea6 += Chart.basicLineNew("chart6", "Biểu đồ LossRoute Performance (%)","%", categories, series6);
			chartArea7 += Chart.basicLineNew("chart7", "Biểu đồ Location Update (%)","%", categories, series7);
			chartArea8 += Chart.basicLineNew("chart8", "Biểu đồ Location Update LAC (%)","%", categories, series8);
			chartArea9 += Chart.basicLineNew("chart9", "Biểu đồ Channel Assigment (%)","%", categories, series9);
			chartArea10+= Chart.basicLineNew("chart10", "Biểu đồ Rab Assigment (%)","%", categories, series10);
			chartArea11 += Chart.basicLineNew("chart11", "Biểu đồ Incomming Traffic (%)","%", categories, series11);
			chartArea12 += Chart.basicLineNew("chart12", "Biểu đồ OutGoing Traffic (%)","%", categories, series12);
			chartArea13 += Chart.basicLineNew("chart13", "Biểu đồ Term SMS Success (%)","%", categories, series13);
			chartArea14 += Chart.basicLineNew("chart14", "Biểu đồ Multi - media (%)","%", categories, series14);
			chartArea15 += Chart.basicLineNew("chart15", "Biểu đồ ContextSeizure (%)","%", categories, series15);
			
			model.addAttribute("chartdiv1", chartArea1);
			model.addAttribute("chartdiv2", chartArea2);
			model.addAttribute("chartdiv3", chartArea3);
			model.addAttribute("chartdiv4", chartArea4);
			model.addAttribute("chartdiv5", chartArea5);
			model.addAttribute("chartdiv6", chartArea6);
			model.addAttribute("chartdiv7", chartArea7);
			model.addAttribute("chartdiv8", chartArea8);
			model.addAttribute("chartdiv9", chartArea9);
			model.addAttribute("chartdiv10", chartArea10);
			model.addAttribute("chartdiv11", chartArea11);
			model.addAttribute("chartdiv12", chartArea12);
			model.addAttribute("chartdiv13", chartArea13);
			model.addAttribute("chartdiv14", chartArea14);
			model.addAttribute("chartdiv15", chartArea15);
			
			
			
			return "dyMscSummaryChartList";
		}	
}
