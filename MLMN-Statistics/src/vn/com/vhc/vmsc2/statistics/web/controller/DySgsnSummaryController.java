package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.SgsnDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDySgsnSummaryDAO;
import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnSummary;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("report/core/sgsn/dy/*")
public class DySgsnSummaryController extends BaseController{
	@Autowired
	private VRpDySgsnSummaryDAO vRpDySgsnSummaryDao;
	@Autowired
	private SgsnDAO sgsnDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String sgsnid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		
		
		List<VRpDySgsnSummary> vRpDySgsnSummary = vRpDySgsnSummaryDao.filter(df.format(startDate), df.format(endDate), sgsnid);
		
		model.addAttribute("sgsnid", sgsnid);
		List<Sgsn> sgsnList = sgsnDao.getAllSGSN();
		model.addAttribute("sgsnList", sgsnList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDySgsnSummary", vRpDySgsnSummary);
		
		// Bieu do cho du lieu 7 ngay gan nhat
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(endDate);
		cal1.add(Calendar.DAY_OF_MONTH, -29);
		String startDate7 = df.format(cal1.getTime());
		
		String endDate7 = df.format(endDate);
		
		model.addAttribute("startDate7", startDate7);
		model.addAttribute("endDate7", endDate7);
		
		List<VRpDySgsnSummary> getDayList = vRpDySgsnSummaryDao.getDayList(startDate7, endDate7);
		List<String> categories = new ArrayList<String>();
		for(int i=0;i<getDayList.size();i++)
		{
			String strDay = df.format(getDayList.get(i).getDay());
			strDay = strDay.substring(0, strDay.lastIndexOf("/"));
			categories.add(strDay);
		}
			
			//categories.add(df.format(getDayList.get(i).getDay()));
		// Attach Uti
		Map<String, List<Float>> seriesAttachUti = new LinkedHashMap<String, List<Float>>();
		// Attach
		Map<String, List<Float>> seriesAttach = new LinkedHashMap<String, List<Float>>();
		// PDP
		Map<String, List<Float>> seriesPdp = new LinkedHashMap<String, List<Float>>();
		// Thp
		Map<String, List<Float>> seriesThp = new LinkedHashMap<String, List<Float>>();
		// Inter rau
		Map<String, List<Float>> seriesInterRau = new LinkedHashMap<String, List<Float>>();
		// Authen
		Map<String, List<Float>> seriesAuthen = new LinkedHashMap<String, List<Float>>();
		// Paging
		Map<String, List<Float>> seriesPaging = new LinkedHashMap<String, List<Float>>();
		
		List<VRpDySgsnSummary> getSgsnidList = vRpDySgsnSummaryDao.getSgsnidList(startDate7, endDate7);
		
		
		for(int i=0;i<getSgsnidList.size();i++){
			List<VRpDySgsnSummary> getData = vRpDySgsnSummaryDao.getData(startDate7, endDate7, getSgsnidList.get(i).getSgsnid());
			
			List<Float> seriesListAttachUti = new ArrayList<Float>();
			List<Float> seriesListAttachColumn = new ArrayList<Float>();
			List<Float> seriesListAttachLine = new ArrayList<Float>();
			List<Float> seriesListPdpColumn = new ArrayList<Float>();
			List<Float> seriesListPdpLine = new ArrayList<Float>();
			List<Float> seriesListThp = new ArrayList<Float>();
			List<Float> seriesListInterRau = new ArrayList<Float>();
			List<Float> seriesListAuthen = new ArrayList<Float>();
			List<Float> seriesListPaging = new ArrayList<Float>();
			
			
			for(int j=0;j<getData.size();j++){
				seriesListAttachUti.add(getData.get(j).getUtilSubs());
				seriesListAttachColumn.add(getData.get(j).getAttachedSub());
				seriesListAttachLine.add(getData.get(j).getAttachSr());
				seriesListPdpColumn.add(getData.get(j).getActPdp());
				seriesListPdpLine.add(getData.get(j).getPssr());
				seriesListThp.add(getData.get(j).getThp());
				seriesListInterRau.add(getData.get(j).getInterRauSucr());
				seriesListAuthen.add(getData.get(j).getAuthSucr());
				seriesListPaging.add(getData.get(j).getPagingSucr());
			}
			
			
			seriesAttachUti.put(getSgsnidList.get(i).getSgsnid(), seriesListAttachUti);
			seriesAttach.put(getSgsnidList.get(i).getSgsnid() + "-" + "column", seriesListAttachColumn);
			seriesAttach.put(getSgsnidList.get(i).getSgsnid() + "-" + "spline", seriesListAttachLine);
			seriesPdp.put(getSgsnidList.get(i).getSgsnid() + "-" + "column", seriesListPdpColumn);
			seriesPdp.put(getSgsnidList.get(i).getSgsnid() + "-" + "spline", seriesListPdpLine);
			seriesThp.put(getSgsnidList.get(i).getSgsnid(), seriesListThp);
			seriesInterRau.put(getSgsnidList.get(i).getSgsnid(), seriesListInterRau);
			seriesAuthen.put(getSgsnidList.get(i).getSgsnid(), seriesListAuthen);
			seriesPaging.put(getSgsnidList.get(i).getSgsnid(), seriesListPaging);
		}
		
		model.addAttribute("dlDataChartUti", Chart.basicLineNew("dlDataChartUti", "Attach Uti (%)", "%", categories, seriesAttachUti));
		model.addAttribute("dlDataChartAttach", Chart.dualAxesLineAndColumn("dlDataChartAttach", "ATTACH", "Attach SR(%)", "No.Attach Subs", categories, seriesAttach));
		model.addAttribute("dlDataChartPdp", Chart.dualAxesLineAndColumn("dlDataChartPdp", "PDP per SGSN", "PDP SR(%)", "PDP (#)", categories, seriesPdp));
		model.addAttribute("dlDataChartThp", Chart.basicLineNew("dlDataChartThp", "Throughput (Mbps)", "Mbit/s", categories, seriesThp));
		model.addAttribute("dlDataChartInterRau", Chart.basicLineNew("dlDataChartInterRau", "Inter rau", "%", categories, seriesInterRau));
		model.addAttribute("dlDataChartAuthen", Chart.basicLineNew("dlDataChartAuthen", "Authen", "%", categories, seriesAuthen));
		model.addAttribute("dlDataChartPaging", Chart.basicLineNew("dlDataChartPaging", "Paging", "%", categories, seriesPaging));
		
		return new ModelAndView("dySgsnSummaryList");
	}

}
