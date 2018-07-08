package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscGprsCsBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscGprsCsBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class BhBscGprsCsController extends BaseController {
	@Autowired
	private VRpDyBscGprsCsBhDAO vRpDyBscGprsCsBhDao;
	@Autowired
	private VRpWkBscGprsCsBhDAO vRpWkBscGprsCsBhDao;
	@Autowired
	private VRpMnBscGprsCsBhDAO vRpMnBscGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/bsc-gprs-cs/dy/bh")
	public ModelAndView dyList( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date startDay, endDay;
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
				
			if (region == null)
				region = "";
			
			List<VRpDyBscGprsCsBh> vRpDyBscGprsCsBh = vRpDyBscGprsCsBhDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("region", region);
			model.addAttribute("bscid", bscid);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyBscGprsCsBh", vRpDyBscGprsCsBh);
			//start chart
			List<String> categories = new ArrayList<String>();
			List<Float> bhCsxLevel1List = new ArrayList<Float>();
			List<Float> bhCsxLevel2List = new ArrayList<Float>();
			List<Float> bhCsxLevel3List = new ArrayList<Float>();
			List<Float> bhCsxLevel4List = new ArrayList<Float>();
			List<Float> bhMcsxLevel1List = new ArrayList<Float>();
			List<Float> bhMcsxLevel2List=new ArrayList<Float>();
			List<Float> bhMcsxLevel3List = new ArrayList<Float>();
			List<Float> bhMcsxLevel4List=new ArrayList<Float>();
			List<Float> bhMcsxLevel5List = new ArrayList<Float>();
			List<Float> bhMcsxLevel6List = new ArrayList<Float>();
			List<Float> bhMcsxLevel7List=new ArrayList<Float>();
			List<Float> bhMcsxLevel8List=new ArrayList<Float>();
			List<Float> bhMcsxLevel9List=new ArrayList<Float>();
			
			for (VRpDyBscGprsCsBh vRpDyBsc : vRpDyBscGprsCsBh) {
				categories.add(vRpDyBsc.getBh()+":00/"+df.format(vRpDyBsc.getDay()));
				bhCsxLevel1List.add(vRpDyBsc.getBhCsxLevel1());
				bhCsxLevel2List.add(vRpDyBsc.getBhCsxLevel2());
				bhCsxLevel3List.add(vRpDyBsc.getBhCsxLevel3());
				bhCsxLevel4List.add(vRpDyBsc.getBhCsxLevel4());
				bhMcsxLevel1List.add(vRpDyBsc.getBhMcsxLevel1());
				bhMcsxLevel2List.add(vRpDyBsc.getBhMcsxLevel2());
				bhMcsxLevel3List.add(vRpDyBsc.getBhMcsxLevel3());
				bhMcsxLevel4List.add(vRpDyBsc.getBhMcsxLevel4());
				bhMcsxLevel5List.add(vRpDyBsc.getBhMcsxLevel5());
				bhMcsxLevel6List.add(vRpDyBsc.getBhMcsxLevel6());
				bhMcsxLevel7List.add(vRpDyBsc.getBhMcsxLevel7());
				bhMcsxLevel8List.add(vRpDyBsc.getBhMcsxLevel8());
				bhMcsxLevel9List.add(vRpDyBsc.getBhMcsxLevel9());
			}
			Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
			csxLevel1Series.put("CSX Level1--80699b", bhCsxLevel1List);
			model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

			Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
			csxLevel2Series.put("CSX Level2--80699b", bhCsxLevel2List);
			model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

			Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
			csxLevel3Series.put("CSX Level3--80699b", bhCsxLevel3List);
			model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

			Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
			csxLevel4Series.put("CSX Level4--80699b", bhCsxLevel4List);
			model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

			Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel1Series.put("MCSX Level1--db843d", bhMcsxLevel1List);
			model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

			Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel2Series.put("MCSX Level2--80699b", bhMcsxLevel2List);
			model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

			Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel3Series.put("MCSX Level3--80699b", bhMcsxLevel3List);
			model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

			Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel4Series.put("MCSX Level4--db843d", bhMcsxLevel4List);
			model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

			Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel5Series.put("MCSX Level5--db843d", bhMcsxLevel5List);
			model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

			Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel6Series.put("MCSX Level6--80699b", bhMcsxLevel6List);
			model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

			Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel7Series.put("MCSX Level7--80699b", bhMcsxLevel7List);
			model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

			Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel8Series.put("MCSX Level8--db843d", bhMcsxLevel8List);
			model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

			Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel9Series.put("MCSX Level9--db843d", bhMcsxLevel9List);
			model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));
			// checkBox
			String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1" ,"bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1" ,"bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			//end chart


		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyBscGprsCsBh");
	}
	
	@RequestMapping("/report/radio/bsc-gprs-cs/mn/bh")
	public ModelAndView mnList( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startMonth,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth -3;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.MONTH,-3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
	    
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
			
		if (region == null)
			region = "";

		List<VRpMnBscGprsCsBh> vRpMnBscGprsCsBh = vRpMnBscGprsCsBhDao.filter(startMonth,startYear, endMonth, endYear, bscid, region);

		model.addAttribute("vRpMnBscGprsCsBh", vRpMnBscGprsCsBh);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhCsxLevel1List = new ArrayList<Float>();
		List<Float> bhCsxLevel2List = new ArrayList<Float>();
		List<Float> bhCsxLevel3List = new ArrayList<Float>();
		List<Float> bhCsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel1List = new ArrayList<Float>();
		List<Float> bhMcsxLevel2List=new ArrayList<Float>();
		List<Float> bhMcsxLevel3List = new ArrayList<Float>();
		List<Float> bhMcsxLevel4List=new ArrayList<Float>();
		List<Float> bhMcsxLevel5List = new ArrayList<Float>();
		List<Float> bhMcsxLevel6List = new ArrayList<Float>();
		List<Float> bhMcsxLevel7List=new ArrayList<Float>();
		List<Float> bhMcsxLevel8List=new ArrayList<Float>();
		List<Float> bhMcsxLevel9List=new ArrayList<Float>();
		
		for (VRpMnBscGprsCsBh vRpMnBsc: vRpMnBscGprsCsBh) {
			categories.add(Integer.toString(vRpMnBsc.getMonth())+"/"+Integer.toString(vRpMnBsc.getYear()));
			bhCsxLevel1List.add(vRpMnBsc.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpMnBsc.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpMnBsc.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpMnBsc.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpMnBsc.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpMnBsc.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpMnBsc.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpMnBsc.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpMnBsc.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpMnBsc.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpMnBsc.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpMnBsc.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpMnBsc.getBhMcsxLevel9());
		}
		Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
		csxLevel1Series.put("CSX Level1--80699b", bhCsxLevel1List);
		model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

		Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
		csxLevel2Series.put("CSX Level2--80699b", bhCsxLevel2List);
		model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

		Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
		csxLevel3Series.put("CSX Level3--80699b", bhCsxLevel3List);
		model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

		Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
		csxLevel4Series.put("CSX Level4--80699b", bhCsxLevel4List);
		model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

		Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel1Series.put("MCSX Level1--db843d", bhMcsxLevel1List);
		model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

		Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel2Series.put("MCSX Level2--80699b", bhMcsxLevel2List);
		model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

		Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel3Series.put("MCSX Level3--80699b", bhMcsxLevel3List);
		model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

		Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel4Series.put("MCSX Level4--db843d", bhMcsxLevel4List);
		model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

		Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel5Series.put("MCSX Level5--db843d", bhMcsxLevel5List);
		model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

		Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel6Series.put("MCSX Level6--80699b", bhMcsxLevel6List);
		model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

		Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel7Series.put("MCSX Level7--80699b", bhMcsxLevel7List);
		model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

		Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel8Series.put("MCSX Level8--db843d", bhMcsxLevel8List);
		model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

		Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel9Series.put("MCSX Level9--db843d", bhMcsxLevel9List);
		model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));
		// checkBox
		String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1" ,"bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1" ,"bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		//end chart

		return new ModelAndView("mnBscGprsCsBh");
	}

	@RequestMapping("/report/radio/bsc-gprs-cs/wk/bh")
	public ModelAndView wkList( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endWeek==null){
			cal.add(Calendar.WEEK_OF_YEAR, -2);
			endWeek = cal.get(Calendar.WEEK_OF_YEAR);
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startWeek == null) {
			if (endWeek > 4) {
				startWeek = endWeek -4;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.WEEK_OF_YEAR,-6);
				startWeek = cal1.get(Calendar.WEEK_OF_YEAR);
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
			
		if (region == null)
			region = "";

		List<VRpWkBscGprsCsBh> vRpWkBscGprsCsBh = vRpWkBscGprsCsBhDao.filter(startWeek, startYear,endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscGprsCsBh", vRpWkBscGprsCsBh);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhCsxLevel1List = new ArrayList<Float>();
		List<Float> bhCsxLevel2List = new ArrayList<Float>();
		List<Float> bhCsxLevel3List = new ArrayList<Float>();
		List<Float> bhCsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel1List = new ArrayList<Float>();
		List<Float> bhMcsxLevel2List=new ArrayList<Float>();
		List<Float> bhMcsxLevel3List = new ArrayList<Float>();
		List<Float> bhMcsxLevel4List=new ArrayList<Float>();
		List<Float> bhMcsxLevel5List = new ArrayList<Float>();
		List<Float> bhMcsxLevel6List = new ArrayList<Float>();
		List<Float> bhMcsxLevel7List=new ArrayList<Float>();
		List<Float> bhMcsxLevel8List=new ArrayList<Float>();
		List<Float> bhMcsxLevel9List=new ArrayList<Float>();
		
		for (VRpWkBscGprsCsBh vRpWkBsc : vRpWkBscGprsCsBh) {
			categories.add(Integer.toString(vRpWkBsc.getWeek())+"/"+Integer.toString(vRpWkBsc.getYear()));
			bhCsxLevel1List.add(vRpWkBsc.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpWkBsc.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpWkBsc.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpWkBsc.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpWkBsc.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpWkBsc.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpWkBsc.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpWkBsc.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpWkBsc.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpWkBsc.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpWkBsc.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpWkBsc.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpWkBsc.getBhMcsxLevel9());
		}
		Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
		csxLevel1Series.put("CSX Level1--80699b", bhCsxLevel1List);
		model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

		Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
		csxLevel2Series.put("CSX Level2--80699b", bhCsxLevel2List);
		model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

		Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
		csxLevel3Series.put("CSX Level3--80699b", bhCsxLevel3List);
		model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

		Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
		csxLevel4Series.put("CSX Level4--80699b", bhCsxLevel4List);
		model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

		Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel1Series.put("MCSX Level1--db843d", bhMcsxLevel1List);
		model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

		Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel2Series.put("MCSX Level2--80699b", bhMcsxLevel2List);
		model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

		Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel3Series.put("MCSX Level3--80699b", bhMcsxLevel3List);
		model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

		Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel4Series.put("MCSX Level4--db843d", bhMcsxLevel4List);
		model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

		Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel5Series.put("MCSX Level5--db843d", bhMcsxLevel5List);
		model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

		Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel6Series.put("MCSX Level6--80699b", bhMcsxLevel6List);
		model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

		Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel7Series.put("MCSX Level7--80699b", bhMcsxLevel7List);
		model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

		Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel8Series.put("MCSX Level8--db843d", bhMcsxLevel8List);
		model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

		Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel9Series.put("MCSX Level9--db843d", bhMcsxLevel9List);
		model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));
		// checkBox
		String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1" ,"bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1" ,"bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		//end chart

		return new ModelAndView("wkBscGprsCsBh");
	}
}