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

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegion2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBranchTraffic2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnRegion2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrBranchTraffic2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrRegion2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrBranchTraffic2G3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrRegion2G3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBranchTraffic2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnRegion2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrBranchTraffic2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrRegion2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrBranchTraffic2G3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrRegion2G3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class OtherReportController extends BaseController {
	@Autowired
	private VRpDyRegion2G3GDAO vRpDyRegion2G3GDao;
	@Autowired
	private VRpMnRegion2G3GDAO vRpMnRegion2G3GDao;
	@Autowired
	private VRpQrRegion2G3GDAO vRpQrRegion2G3GDao;
	@Autowired
	private VRpYrRegion2G3GDAO vRpYrRegion2G3GDao;
	@Autowired
	private VRpMnBranchTraffic2G3GDAO vRpMnBranchTraffic2G3GDao;
	@Autowired
	private VRpQrBranchTraffic2G3GDAO vRpQrBranchTraffic2G3GDao;
	@Autowired
	private VRpYrBranchTraffic2G3GDAO vRpYrBranchTraffic2G3GDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dff = new SimpleDateFormat("dd/MM");

	@RequestMapping("/report/other/mn")
	public ModelAndView otherReportMn(@RequestParam(required = false) Integer month,@RequestParam(required = false) Integer year,@RequestParam(required = false) Integer startMonth,@RequestParam(required = false) Integer startYear,@RequestParam(required = false) Integer endMonth,@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calendar = Calendar.getInstance();

		if (month == null) {
			calendar.add(Calendar.MONTH,-1);
			month = calendar.get(Calendar.MONTH)+1;
			year = calendar.get(Calendar.YEAR);
		}

		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);

		Date startDate = calendar.getTime();
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		Date endDate = calendar.getTime();
		
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		
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

		String region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpDyRegion2G3G> vRpDyRegionList = vRpDyRegion2G3GDao.filter(df.format(startDate), df.format(endDate), region);

		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight1", Helper.highLight(highlightConfigList, "vRpDyRegion"));
		model.addAttribute("highlight2", Helper.highLight(highlightConfigList, "vRpMnRegion2G3G"));
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> tTraf2gList = new ArrayList<Float>();
		List<Float> tTraf3gList = new ArrayList<Float>();
		List<Float> cssr2gList = new ArrayList<Float>();
		List<Float> cssr3gList = new ArrayList<Float>();
		List<Float> dcr2gList = new ArrayList<Float>();
		List<Float> dcr3gList = new ArrayList<Float>();
		List<Float> data2GList = new ArrayList<Float>();
		List<Float> data3GList = new ArrayList<Float>();
		List<Float> badCell2gList = new ArrayList<Float>();
		List<Float> badCell3gList = new ArrayList<Float>();

		for (VRpDyRegion2G3G dyRegion : vRpDyRegionList) {
			categories.add(dff.format(dyRegion.getDay()));
			
			tTraf2gList.add(dyRegion.getTraf2g());
			tTraf3gList.add(dyRegion.getTraf3g());
			
			cssr2gList.add(dyRegion.getCssr2g());
			cssr3gList.add(dyRegion.getCssr3g());
			
			dcr2gList.add(dyRegion.getDcr2g());
			dcr3gList.add(dyRegion.getDcr3g());
			
			float tmpData2G =dyRegion.getUlData2g()+dyRegion.getDlData2g();
			float tmpData3G =dyRegion.getUlData3g()+dyRegion.getDlData3g();
			
			data2GList.add(tmpData2G);
			data3GList.add(tmpData3G);
			
			badCell2gList.add(dyRegion.getBadCell2g());
			badCell3gList.add(dyRegion.getBadCell3g());
		}

		Map<String, List<Float>> trafSeries = new LinkedHashMap<String, List<Float>>();
		trafSeries.put("Traffic 3g(Erl)-column-ac4642", tTraf3gList);
		trafSeries.put("Traffic 2g(Erl)-column-92cdd8", tTraf2gList);
		trafSeries.put("Cssr 2g(%)-line-8ba850-r", cssr2gList);
		trafSeries.put("Dcr 2g(%)-line-14a458-r", dcr2gList);
		trafSeries.put("Bad Cell 2g(%)-line-f5f54e-r", badCell2gList);
		trafSeries.put("Bad Cell 3g(%)-line-d7833b-r", badCell3gList);
		model.addAttribute("trafChart", Chart.stackedColumn("trafChart", "Chỉ tiêu chất lượng(KPI) Tháng " + month + "/" + year, categories, trafSeries));
		
		Map<String, List<Float>> ulDataSeries = new LinkedHashMap<String, List<Float>>();
		ulDataSeries.put("Data 3g(GB)-column", data3GList);
		ulDataSeries.put("Data 2g(GB)-column", data2GList);
		model.addAttribute("dataChart", Chart.stackedColumn("dataChart", "DATA - tháng " + month + "/" + year, categories, ulDataSeries));
		
		List<VRpMnRegion2G3G> vRpMnRegion2G3GList = vRpMnRegion2G3GDao.filter(startMonth, startYear, endMonth, endYear, region);
		model.addAttribute("vRpMnRegion2G3GList", vRpMnRegion2G3GList);
		
		List<String> categoriesMonth = new ArrayList<String>();
		List<Float> tTraf2gMonthList = new ArrayList<Float>();
		List<Float> tTraf3gMonthList = new ArrayList<Float>();
		List<Float> cssr2gMonthList = new ArrayList<Float>();
		List<Float> cssr3gMonthList = new ArrayList<Float>();
		List<Float> dcr2gMonthList = new ArrayList<Float>();
		List<Float> dcr3gMonthList = new ArrayList<Float>();
		List<Float> dataMonthList = new ArrayList<Float>();
		List<Float> badCell2gMonthList = new ArrayList<Float>();
		List<Float> badCell3gMonthList = new ArrayList<Float>();
		List<Float> trxMonthList = new ArrayList<Float>();
		List<Float> erlTrxMonthList = new ArrayList<Float>();
		List<String> categoriesRegion = new ArrayList<String>();

		Map<String,List<Float>> seriesTraffRegion = new LinkedHashMap<String, List<Float>>();

		for (VRpMnRegion2G3G mnRegion : vRpMnRegion2G3GList) {
			categoriesMonth.add(mnRegion.getMonth()+"/"+mnRegion.getYear());
			
			tTraf2gMonthList.add(mnRegion.getTraf2g());
			tTraf3gMonthList.add(mnRegion.getTraf3g());
			
			List<Float> tmpTraffList = new ArrayList<Float>();
			tmpTraffList.add(mnRegion.getTraf2g());
			tmpTraffList.add(mnRegion.getTraf3g());
			
			seriesTraffRegion.put("Tháng "+mnRegion.getMonth()+"/"+mnRegion.getYear(), tmpTraffList);
			
			cssr2gMonthList.add(mnRegion.getCssr2g());
			cssr3gMonthList.add(mnRegion.getCssr3g());
			
			dcr2gMonthList.add(mnRegion.getDcr2g());
			dcr3gMonthList.add(mnRegion.getDcr3g());
			
			float tmpData = mnRegion.getUlData2g()+mnRegion.getUlData3g()+mnRegion.getDlData2g()+mnRegion.getDlData3g();
			
			dataMonthList.add(tmpData);

			badCell2gMonthList.add(mnRegion.getBadCell2g());
			badCell3gMonthList.add(mnRegion.getBadCell3g());
			
			trxMonthList.add(Helper.int2Float(mnRegion.getTrx2g()));
			erlTrxMonthList.add(Helper.int2Float(mnRegion.getErlTrx2g()));
		}
		
		Map<String, List<Float>> trafMonthSeries = new LinkedHashMap<String, List<Float>>();
		trafMonthSeries.put("Traffic 3g(Erl)-column-ac4642", tTraf3gMonthList);
		trafMonthSeries.put("Traffic 2g(Erl)-column-92cdd8", tTraf2gMonthList);
		trafMonthSeries.put("Cssr 2g(%)-line-8ba850-r", cssr2gMonthList);
		trafMonthSeries.put("Dcr 2g(%)-line-14a458-r", dcr2gMonthList);
		trafMonthSeries.put("Bad Cell 2g(%)-line-f5f54e-r", badCell2gMonthList);
		trafMonthSeries.put("Bad Cell 3g(%)-line-d7833b-r", badCell3gMonthList);
		model.addAttribute("traffMonthChart", Chart.stackedColumn("traffMonthChart", "Chỉ tiêu chất lượng (KPI) theo tháng", categoriesMonth, trafMonthSeries));
		
		Map<String, List<Float>> trafByMonthSeries = new LinkedHashMap<String, List<Float>>();
		trafByMonthSeries.put("Traffic 3g(Erl)-column-ae4c49", tTraf3gMonthList);
		trafByMonthSeries.put("Traffic 2g(Erl)-column-cfc4dc", tTraf2gMonthList);
		trafByMonthSeries.put("Tổng Data(GB)-line-745a92-r", dataMonthList);
		trafByMonthSeries.put("TRX-line-dc873d-r", trxMonthList);
		trafByMonthSeries.put("Erl/TRX-line-0000ff-r", erlTrxMonthList);
		model.addAttribute("traffByMonthChart", Chart.stackedColumnOther("traffByMonthChart", "DATA TRAFFIC - theo tháng ", categoriesMonth, trafByMonthSeries));
		
		categoriesRegion.add("Traffic 2G(Erl)");
		categoriesRegion.add("Traffic 3G(Erl)");
		model.addAttribute("traffRegionChart", Chart.columnBasic("traffRegionChart", "TRAFFIC- KPI trung tâm theo tháng", categoriesRegion, seriesTraffRegion));
		
		List<VRpMnBranchTraffic2G3G> vRpMnBranchTraffic2G3GList = vRpMnBranchTraffic2G3GDao.filter(startMonth, startYear, endMonth, endYear, region);
		model.addAttribute("vRpMnBranchTraffic2G3GList", vRpMnBranchTraffic2G3GList);
		
		List<VRpMnBranchTraffic2G3G> vRpMnBranchTrafficRate2G3GList = vRpMnBranchTraffic2G3GDao.filterRate(month, year, region);
		model.addAttribute("vRpMnBranchTrafficRate2G3GList", vRpMnBranchTrafficRate2G3GList);
		
		List<String> traffBranch2GList = new ArrayList<String>();
		List<String> traffBranch3GList = new ArrayList<String>();
		List<String> traffBranch2G3GList = new ArrayList<String>();
		List<String> dataTraffBranch2GList = new ArrayList<String>();
		List<String> dataTraffBranch3GList = new ArrayList<String>();
		List<String> dataTraffBranch2G3GList = new ArrayList<String>();
		float totTraff2G = 0;
		float totTraff3G = 0;
		float totTraff2G3G = 0;
		float totDataTraff2G = 0;
		float totDataTraff3G = 0;
		float totDataTraff2G3G = 0;
		for (VRpMnBranchTraffic2G3G vRpMnBranchTrafficRate : vRpMnBranchTrafficRate2G3GList) {
			if (vRpMnBranchTrafficRate.getTraf2g()==null)
				totTraff2G3G +=0;
			else
				totTraff2G3G +=vRpMnBranchTrafficRate.getTraf2g3g();
			if (vRpMnBranchTrafficRate.getTraf3g()==null)
				totTraff3G +=0;
			else
				totTraff3G +=vRpMnBranchTrafficRate.getTraf3g();
			if (vRpMnBranchTrafficRate.getTraf2g()==null)
				totTraff2G +=0;
			else
				totTraff2G +=vRpMnBranchTrafficRate.getTraf2g();
			if (vRpMnBranchTrafficRate.getDlData2g()==null) {
				totDataTraff2G +=0;
				totDataTraff2G3G +=0;
			}
			else { 
				totDataTraff2G +=vRpMnBranchTrafficRate.getDlData2g();
				totDataTraff2G3G += vRpMnBranchTrafficRate.getDlData2g();
			}
			if (vRpMnBranchTrafficRate.getUlData2g()==null) {
				totDataTraff2G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff2G +=vRpMnBranchTrafficRate.getUlData2g();
				totDataTraff2G3G += vRpMnBranchTrafficRate.getUlData2g();
			}
			if (vRpMnBranchTrafficRate.getDlData3g()==null) {
				totDataTraff3G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff3G +=vRpMnBranchTrafficRate.getDlData3g();
				totDataTraff2G3G += vRpMnBranchTrafficRate.getDlData3g();
			}
			if (vRpMnBranchTrafficRate.getUlData3g()==null) {
				totDataTraff3G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff3G +=vRpMnBranchTrafficRate.getUlData3g();
				totDataTraff2G3G += vRpMnBranchTrafficRate.getUlData3g();
			}
			
		}
		String title="";
		for (VRpMnBranchTraffic2G3G vRpMnBranchTrafficRate : vRpMnBranchTrafficRate2G3GList) {
			title = vRpMnBranchTrafficRate.getMonth()+"/"+vRpMnBranchTrafficRate.getYear();
			float rateTraff2G3G;
			float rateTraff3G;
			float rateTraff2G;
			float rateDataTraff2G3G;
			float rateDataTraff3G;
			float rateDataTraff2G;
			float tempData2G = vRpMnBranchTrafficRate.getDlData2g()+vRpMnBranchTrafficRate.getUlData2g();
			float tempData3G = vRpMnBranchTrafficRate.getDlData3g()+vRpMnBranchTrafficRate.getUlData3g();
			float tempData2G3G = tempData2G + tempData3G;
			if (totTraff2G3G == 0)
				rateTraff2G3G =0;
			else
				rateTraff2G3G =Math.round(100*100*(vRpMnBranchTrafficRate.getTraf2g3g()/totTraff2G3G));
			if (totTraff3G == 0)
				rateTraff3G =0;
			else
				rateTraff3G =Math.round(100*100*(vRpMnBranchTrafficRate.getTraf3g()/totTraff3G));
			if (totTraff2G == 0)
				rateTraff2G =0;
			else
				rateTraff2G =Math.round(100*100*(vRpMnBranchTrafficRate.getTraf2g()/totTraff2G));
			
			if (totDataTraff2G3G == 0)
				rateDataTraff2G3G =0;
			else
				rateDataTraff2G3G =Math.round(100*100*((tempData2G3G)/totDataTraff2G3G));
			if (totDataTraff3G == 0)
				rateDataTraff3G =0;
			else
				rateDataTraff3G =Math.round(100*100*((tempData3G)/totDataTraff3G));
			if (totDataTraff2G == 0)
				rateDataTraff2G =0;
			else
				rateDataTraff2G =Math.round(100*100*((tempData2G)/totDataTraff2G));
			
			traffBranch2G3GList.add(vRpMnBranchTrafficRate.getBranch()+"-"+vRpMnBranchTrafficRate.getTraf2g3g()+"-"+rateTraff2G3G/100);
			traffBranch3GList.add(vRpMnBranchTrafficRate.getBranch()+"-"+vRpMnBranchTrafficRate.getTraf3g()+"-"+rateTraff3G/100);
			traffBranch2GList.add(vRpMnBranchTrafficRate.getBranch()+"-"+vRpMnBranchTrafficRate.getTraf2g()+"-"+rateTraff2G/100);
			
			dataTraffBranch2G3GList.add(vRpMnBranchTrafficRate.getBranch()+"-"+tempData2G3G+"-"+rateDataTraff2G3G/100);
			dataTraffBranch3GList.add(vRpMnBranchTrafficRate.getBranch()+"-"+tempData3G+"-"+rateDataTraff3G/100);
			dataTraffBranch2GList.add(vRpMnBranchTrafficRate.getBranch()+"-"+tempData2G+"-"+rateDataTraff2G/100);
		}
		
		Map<String, List<String>> traffBrach2GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach2GSeries.put("traffBranch2GSeries", traffBranch2G3GList);
		model.addAttribute("traffBranch2GChart", Chart.pieChart("traffBranch2GChart","TRAFFIC 2G tháng : "+title, traffBrach2GSeries));
		Map<String, List<String>> traffBrach3GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach3GSeries.put("traffBrach3GSeries", traffBranch3GList);
		model.addAttribute("traffBranch3GChart", Chart.pieChart("traffBranch3GChart","TRAFFIC 3G tháng : "+title, traffBrach3GSeries));
		Map<String, List<String>> traffBrach2G3GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach2G3GSeries.put("traffBrach2G3GSeries", traffBranch2GList);
		model.addAttribute("traffBranch2G3GChart", Chart.pieChart("traffBranch2G3GChart","TRAFFIC 2G+3G tháng : "+title, traffBrach2G3GSeries));
		
		Map<String, List<String>> dataTraffBranch2G3GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch2G3GSeries.put("dataTraffBranch2G3GSeries", dataTraffBranch2G3GList);
		model.addAttribute("dataTraffBranch2G3GChart", Chart.pieChartData("dataTraffBranch2G3GChart","DATA 2G+3G tháng : "+title, dataTraffBranch2G3GSeries));
		Map<String, List<String>> dataTraffBranch3GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch3GSeries.put("dataTraffBranch3GSeries", dataTraffBranch3GList);
		model.addAttribute("dataTraffBranch3GChart", Chart.pieChartData("dataTraffBranch3GChart","DATA 3G tháng : "+title, dataTraffBranch3GSeries));
		Map<String, List<String>> dataTraffBranch2GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch2GSeries.put("dataTraffBranch2GSeries", dataTraffBranch2GList);
		model.addAttribute("dataTraffBranch2GChart", Chart.pieChartData("dataTraffBranch2GChart","DATA 2G tháng : "+title, dataTraffBranch2GSeries));
		
		List<String> categoriesBranch = new ArrayList<String>();
		boolean[] isUsed = new boolean[32068]; 
		
		for(int i=0;i<vRpMnBranchTraffic2G3GList.size();i++) 
			isUsed[i] = false;
		 
		Map<String,List<Float>> seriesTraff2gBranch = new LinkedHashMap<String, List<Float>>();
		Map<String,List<Float>> seriesTraff3gBranch = new LinkedHashMap<String, List<Float>>();
		Map<String,List<Float>> seriesDataBranch = new LinkedHashMap<String, List<Float>>();
		int j = 0;
		for (VRpMnBranchTraffic2G3G vRpMnBranchTraffic : vRpMnBranchTraffic2G3GList) {
			int tmpMonth = vRpMnBranchTraffic.getMonth();
			int tmpYear = vRpMnBranchTraffic.getYear();
			int n = 0;
			for (int m = 0; m < categoriesBranch.size(); m++)
				if (categoriesBranch.get(m) !=vRpMnBranchTraffic.getBranch())
				{
					n++;
				}
			if (n ==categoriesBranch.size()) categoriesBranch.add(vRpMnBranchTraffic.getBranch());
			if (isUsed[j]== false) {
				List<Float> tmpTraff2gList = new ArrayList<Float>();
				List<Float> tmpTraff3gList = new ArrayList<Float>();
				List<Float> tmpDataList = new ArrayList<Float>();
				int k = 0;
				for (VRpMnBranchTraffic2G3G vRpMnBranchTraffic1 : vRpMnBranchTraffic2G3GList) {
					if (tmpMonth == vRpMnBranchTraffic1.getMonth()&&tmpYear==vRpMnBranchTraffic1.getYear()){
						tmpTraff2gList.add(vRpMnBranchTraffic1.getTraf2g());
						tmpTraff3gList.add(vRpMnBranchTraffic1.getTraf3g());
						float tmpData =vRpMnBranchTraffic1.getUlData2g()+vRpMnBranchTraffic1.getDlData2g()+
							vRpMnBranchTraffic1.getUlData3g()+vRpMnBranchTraffic1.getDlData3g();
						tmpDataList.add(tmpData);
						isUsed[k] = true;
						k++;
					}
				}
				isUsed[j]=true;
				seriesTraff2gBranch.put("Tháng "+vRpMnBranchTraffic.getMonth()+"/"+vRpMnBranchTraffic.getYear(), tmpTraff2gList);
				seriesTraff3gBranch.put("Tháng "+vRpMnBranchTraffic.getMonth()+"/"+vRpMnBranchTraffic.getYear(), tmpTraff3gList);
				seriesDataBranch.put("Tháng "+vRpMnBranchTraffic.getMonth()+"/"+vRpMnBranchTraffic.getYear(), tmpDataList);
			}
			j++;
		}
		model.addAttribute("traff2GBranchChart", Chart.columnBasic("traff2GBranchChart", "TRAFFIC 2G - KPI chi nhánh theo tháng", categoriesBranch, seriesTraff2gBranch));
		model.addAttribute("traff3GBranchChart", Chart.columnBasic("traff3GBranchChart", "TRAFFIC 3G - KPI chi nhánh theo tháng", categoriesBranch, seriesTraff3gBranch));
		model.addAttribute("dataBranchChart", Chart.columnBasic("dataBranchChart", "TRAFFIC - DATA chi nhánh theo tháng", categoriesBranch, seriesDataBranch));
		
		return new ModelAndView("otherReportMonth");
	}
	
	@RequestMapping("/report/other/qr")
	public ModelAndView otherReportQr(@RequestParam(required = false) Integer quarter,@RequestParam(required = false) Integer year,@RequestParam(required = false) Integer startQuarter,@RequestParam(required = false) Integer startYear,@RequestParam(required = false) Integer endQuarter,@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		
		if (quarter == null)
			quarter = Helper.getQuarter();
		if (year== null)
			year = calender.get(Calendar.YEAR);
		
		if (endYear ==null)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endQuarter == null)
			endQuarter = Helper.getQuarter();
		
		if (startYear ==null)
		{
			startYear = endYear;
		}
		if (startQuarter == null)
			startQuarter = endQuarter;
		if (startYear > endYear) 
			startYear = endYear;
		if (startQuarter> endQuarter&& startYear>=endYear){
			startQuarter = endQuarter;
			startYear = endYear;
		}
		
		model.addAttribute("quarter", quarter);
		model.addAttribute("year", year);
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		int startMonth=0;
		int endMonth=0;
		
		int startYearMonth= year;
		int endYearMonth= year;
		
		if (quarter == 1) {
			startMonth =1;
			endMonth=3;
		}
		if (quarter == 2) {
			startMonth =4;
			endMonth=6;
		}
		if (quarter == 3) {
			startMonth =7;
			endMonth=9;
		}
		if (quarter == 4) {
			startMonth =10;
			endMonth=12;
		}
			
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYearMonth", startYearMonth);
		model.addAttribute("endYearMonth", endYearMonth);

		String region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpMnRegion2G3G> vRpMnRegion2G3GList = vRpMnRegion2G3GDao.filter(startMonth, startYearMonth, endMonth, endYearMonth, region);

		model.addAttribute("vRpMnRegion2G3GList", vRpMnRegion2G3GList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight1", Helper.highLight(highlightConfigList, "vRpMnRegion"));
		model.addAttribute("highlight2", Helper.highLight(highlightConfigList, "vRpQrRegion2G3G"));
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> tTraf2gList = new ArrayList<Float>();
		List<Float> tTraf3gList = new ArrayList<Float>();
		List<Float> cssr2gList = new ArrayList<Float>();
		List<Float> cssr3gList = new ArrayList<Float>();
		List<Float> dcr2gList = new ArrayList<Float>();
		List<Float> dcr3gList = new ArrayList<Float>();
		List<Float> data2GList = new ArrayList<Float>();
		List<Float> data3GList = new ArrayList<Float>();
		List<Float> badCell2gList = new ArrayList<Float>();
		List<Float> badCell3gList = new ArrayList<Float>();

		for (VRpMnRegion2G3G vRpMnRegion : vRpMnRegion2G3GList) {
			categories.add(vRpMnRegion.getMonth()+""+vRpMnRegion.getYear());
			
			tTraf2gList.add(vRpMnRegion.getTraf2g());
			tTraf3gList.add(vRpMnRegion.getTraf3g());
			
			cssr2gList.add(vRpMnRegion.getCssr2g());
			cssr3gList.add(vRpMnRegion.getCssr3g());
			
			dcr2gList.add(vRpMnRegion.getDcr2g());
			dcr3gList.add(vRpMnRegion.getDcr3g());
			
			float tmpData2G =vRpMnRegion.getUlData2g()+vRpMnRegion.getDlData2g();
			float tmpData3G =vRpMnRegion.getUlData3g()+vRpMnRegion.getDlData3g();
			
			data2GList.add(tmpData2G);
			data3GList.add(tmpData3G);
			
			badCell2gList.add(vRpMnRegion.getBadCell2g());
			badCell3gList.add(vRpMnRegion.getBadCell3g());
		}

		Map<String, List<Float>> trafSeries = new LinkedHashMap<String, List<Float>>();
		trafSeries.put("Traffic 3g(Erl)-column-ac4642", tTraf3gList);
		trafSeries.put("Traffic 2g(Erl)-column-92cdd8", tTraf2gList);
		trafSeries.put("Cssr 2g(%)-line-8ba850-r", cssr2gList);
		trafSeries.put("Dcr 2g(%)-line-14a458-r", dcr2gList);
		trafSeries.put("Bad Cell 2g(%)-line-f5f54e-r", badCell2gList);
		trafSeries.put("Bad Cell 3g(%)-line-d7833b-r", badCell3gList);
		model.addAttribute("trafChart", Chart.stackedColumn("trafChart", "Chỉ tiêu chất lượng (KPI) Quý " + quarter + "/" + year, categories, trafSeries));
		
		Map<String, List<Float>> dataSeries = new LinkedHashMap<String, List<Float>>();
		dataSeries.put("Data 3g(GB)-column", data3GList);
		dataSeries.put("Data 2g(GB)-column", data2GList);
		model.addAttribute("dataChart", Chart.stackedColumn("dataChart", "DATA - Quý " + quarter + "/" + year, categories, dataSeries));
		
		List<VRpQrRegion2G3G> vRpQrRegion2G3GList = vRpQrRegion2G3GDao.filter(startQuarter, startYear, endQuarter, endYear, region);
		model.addAttribute("vRpQrRegion2G3GList", vRpQrRegion2G3GList);
		
		List<String> categoriesQuarter = new ArrayList<String>();
		List<Float> tTraf2gQuarterList = new ArrayList<Float>();
		List<Float> tTraf3gQuarterList = new ArrayList<Float>();
		List<Float> cssr2gQuarterList = new ArrayList<Float>();
		List<Float> cssr3gQuarterList = new ArrayList<Float>();
		List<Float> dcr2gQuarterList = new ArrayList<Float>();
		List<Float> dcr3gQuarterList = new ArrayList<Float>();
		List<Float> dataQuarterList = new ArrayList<Float>();
		List<Float> badCell2gQuarterList = new ArrayList<Float>();
		List<Float> badCell3gQuarterList = new ArrayList<Float>();
		List<Float> trxQuarterList = new ArrayList<Float>();
		List<Float> erlTrxQuarterList = new ArrayList<Float>();
		List<String> categoriesRegion = new ArrayList<String>();

		Map<String,List<Float>> seriesTraffRegion = new LinkedHashMap<String, List<Float>>();

		for (VRpQrRegion2G3G qrRegion : vRpQrRegion2G3GList) {
			categoriesQuarter.add(qrRegion.getQuarter()+"/"+qrRegion.getYear());
			
			tTraf2gQuarterList.add(qrRegion.getTraf2g());
			tTraf3gQuarterList.add(qrRegion.getTraf3g());
			
			List<Float> tmpTraffList = new ArrayList<Float>();
			tmpTraffList.add(qrRegion.getTraf2g());
			tmpTraffList.add(qrRegion.getTraf3g());
			
			seriesTraffRegion.put("Qúy "+qrRegion.getQuarter()+"/"+qrRegion.getYear(), tmpTraffList);
			
			cssr2gQuarterList.add(qrRegion.getCssr2g());
			cssr3gQuarterList.add(qrRegion.getCssr3g());
			
			dcr2gQuarterList.add(qrRegion.getDcr2g());
			dcr3gQuarterList.add(qrRegion.getDcr3g());
			
			float tmpData = qrRegion.getUlData2g()+qrRegion.getUlData3g()+qrRegion.getDlData2g()+qrRegion.getDlData3g();
			
			dataQuarterList.add(tmpData);

			badCell2gQuarterList.add(qrRegion.getBadCell2g());
			badCell3gQuarterList.add(qrRegion.getBadCell3g());
			
			trxQuarterList.add(Helper.int2Float(qrRegion.getTrx2g()));
			erlTrxQuarterList.add(Helper.int2Float(qrRegion.getErlTrx2g()));
		}
		
		Map<String, List<Float>> trafQuarterSeries = new LinkedHashMap<String, List<Float>>();
		trafQuarterSeries.put("Traffic 3g(Erl)-column-ac4642", tTraf3gQuarterList);
		trafQuarterSeries.put("Traffic 2g(Erl)-column-92cdd8", tTraf2gQuarterList);
		trafQuarterSeries.put("Cssr 2g(%)-line-8ba850-r", cssr2gQuarterList);
		trafQuarterSeries.put("Dcr 2g(%)-line-14a458-r", dcr2gQuarterList);
		trafQuarterSeries.put("Bad Cell 2g(%)-line-f5f54e-r", badCell2gQuarterList);
		trafQuarterSeries.put("Bad Cell 3g(%)-line-d7833b-r", badCell3gQuarterList);
		model.addAttribute("traffQuarterChart", Chart.stackedColumn("traffQuarterChart", "Chỉ tiêu chất lượng(KPI) theo quý ", categoriesQuarter, trafQuarterSeries));
		
		Map<String, List<Float>> trafByQuarterSeries = new LinkedHashMap<String, List<Float>>();
		trafByQuarterSeries.put("Traffic 3g(Erl)-column-ae4c49", tTraf3gQuarterList);
		trafByQuarterSeries.put("Traffic 2g(Erl)-column-cfc4dc", tTraf2gQuarterList);
		trafByQuarterSeries.put("Tổng Data(GB)-line-745a92-r", dataQuarterList);
		trafByQuarterSeries.put("TRX-line-dc873d-r", trxQuarterList);
		trafByQuarterSeries.put("Erl/TRX-line-0000ff-r", erlTrxQuarterList);
		model.addAttribute("traffByQuarterChart", Chart.stackedColumnOther("traffByQuarterChart", "DATA TRAFFIC - theo qúy ", categoriesQuarter, trafByQuarterSeries));
		
		categoriesRegion.add("Traffic 2G(Erl)");
		categoriesRegion.add("Traffic 3G(Erl)");
		model.addAttribute("traffRegionChart", Chart.columnBasic("traffRegionChart", "TRAFFIC- KPI trung tâm theo quý", categoriesRegion, seriesTraffRegion));
		
		List<VRpQrBranchTraffic2G3G> vRpQrBranchTraffic2G3GList = vRpQrBranchTraffic2G3GDao.filter(startQuarter, startYear, endQuarter, endYear, region);
		model.addAttribute("vRpQrBranchTraffic2G3GList", vRpQrBranchTraffic2G3GList);
		
		List<VRpQrBranchTraffic2G3G> vRpQrBranchTrafficRate2G3GList = vRpQrBranchTraffic2G3GDao.filterRate(quarter, year, region);
		model.addAttribute("vRpQrBranchTrafficRate2G3GList", vRpQrBranchTrafficRate2G3GList);
		
		List<String> traffBranch2GList = new ArrayList<String>();
		List<String> traffBranch3GList = new ArrayList<String>();
		List<String> traffBranch2G3GList = new ArrayList<String>();
		List<String> dataTraffBranch2GList = new ArrayList<String>();
		List<String> dataTraffBranch3GList = new ArrayList<String>();
		List<String> dataTraffBranch2G3GList = new ArrayList<String>();
		float totTraff2G = 0;
		float totTraff3G = 0;
		float totTraff2G3G = 0;
		float totDataTraff2G = 0;
		float totDataTraff3G = 0;
		float totDataTraff2G3G = 0;
		for (VRpQrBranchTraffic2G3G vRpQrBranchTrafficRate : vRpQrBranchTrafficRate2G3GList) {
			if (vRpQrBranchTrafficRate.getTraf2g()==null)
				totTraff2G3G +=0;
			else
				totTraff2G3G +=vRpQrBranchTrafficRate.getTraf2g3g();
			if (vRpQrBranchTrafficRate.getTraf3g()==null)
				totTraff3G +=0;
			else
				totTraff3G +=vRpQrBranchTrafficRate.getTraf3g();
			if (vRpQrBranchTrafficRate.getTraf2g()==null)
				totTraff2G +=0;
			else
				totTraff2G +=vRpQrBranchTrafficRate.getTraf2g();
			if (vRpQrBranchTrafficRate.getDlData2g()==null) {
				totDataTraff2G +=0;
				totDataTraff2G3G +=0;
			}
			else { 
				totDataTraff2G +=vRpQrBranchTrafficRate.getDlData2g();
				totDataTraff2G3G += vRpQrBranchTrafficRate.getDlData2g();
			}
			if (vRpQrBranchTrafficRate.getUlData2g()==null) {
				totDataTraff2G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff2G +=vRpQrBranchTrafficRate.getUlData2g();
				totDataTraff2G3G += vRpQrBranchTrafficRate.getUlData2g();
			}
			if (vRpQrBranchTrafficRate.getDlData3g()==null) {
				totDataTraff3G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff3G +=vRpQrBranchTrafficRate.getDlData3g();
				totDataTraff2G3G += vRpQrBranchTrafficRate.getDlData3g();
			}
			if (vRpQrBranchTrafficRate.getUlData3g()==null) {
				totDataTraff3G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff3G +=vRpQrBranchTrafficRate.getUlData3g();
				totDataTraff2G3G += vRpQrBranchTrafficRate.getUlData3g();
			}
			
		}
		String title="";
		for (VRpQrBranchTraffic2G3G vRpQrBranchTrafficRate : vRpQrBranchTrafficRate2G3GList) {
			title = vRpQrBranchTrafficRate.getQuarter()+"/"+vRpQrBranchTrafficRate.getYear();
			float rateTraff2G3G;
			float rateTraff3G;
			float rateTraff2G;
			float rateDataTraff2G3G;
			float rateDataTraff3G;
			float rateDataTraff2G;
			float tempData2G = vRpQrBranchTrafficRate.getDlData2g()+vRpQrBranchTrafficRate.getUlData2g();
			float tempData3G = vRpQrBranchTrafficRate.getDlData3g()+vRpQrBranchTrafficRate.getUlData3g();
			float tempData2G3G = tempData2G + tempData3G;
			if (totTraff2G3G == 0)
				rateTraff2G3G =0;
			else
				rateTraff2G3G =Math.round(100*100*(vRpQrBranchTrafficRate.getTraf2g3g()/totTraff2G3G));
			if (totTraff3G == 0)
				rateTraff3G =0;
			else
				rateTraff3G =Math.round(100*100*(vRpQrBranchTrafficRate.getTraf3g()/totTraff3G));
			if (totTraff2G == 0)
				rateTraff2G =0;
			else
				rateTraff2G =Math.round(100*100*(vRpQrBranchTrafficRate.getTraf2g()/totTraff2G));
			
			if (totDataTraff2G3G == 0)
				rateDataTraff2G3G =0;
			else
				rateDataTraff2G3G =Math.round(100*100*((vRpQrBranchTrafficRate.getDlData2g()+vRpQrBranchTrafficRate.getUlData2g()+vRpQrBranchTrafficRate.getDlData3g()+vRpQrBranchTrafficRate.getUlData3g())/totDataTraff2G3G));
			if (totDataTraff3G == 0)
				rateDataTraff3G =0;
			else
				rateDataTraff3G =Math.round(100*100*((vRpQrBranchTrafficRate.getDlData3g()+vRpQrBranchTrafficRate.getUlData3g())/totDataTraff3G));
			if (totDataTraff2G == 0)
				rateDataTraff2G =0;
			else
				rateDataTraff2G =Math.round(100*100*((vRpQrBranchTrafficRate.getDlData2g()+vRpQrBranchTrafficRate.getUlData2g())/totDataTraff2G));
			
			traffBranch2G3GList.add(vRpQrBranchTrafficRate.getBranch()+"-"+vRpQrBranchTrafficRate.getTraf2g3g()+"-"+rateTraff2G3G/100);
			traffBranch3GList.add(vRpQrBranchTrafficRate.getBranch()+"-"+vRpQrBranchTrafficRate.getTraf3g()+"-"+rateTraff3G/100);
			traffBranch2GList.add(vRpQrBranchTrafficRate.getBranch()+"-"+vRpQrBranchTrafficRate.getTraf2g()+"-"+rateTraff2G/100);
			
			dataTraffBranch2G3GList.add(vRpQrBranchTrafficRate.getBranch()+"-"+tempData2G3G+"-"+rateDataTraff2G3G/100);
			dataTraffBranch3GList.add(vRpQrBranchTrafficRate.getBranch()+"-"+tempData3G+"-"+rateDataTraff3G/100);
			dataTraffBranch2GList.add(vRpQrBranchTrafficRate.getBranch()+"-"+tempData2G+"-"+rateDataTraff2G/100);
		}
		
		Map<String, List<String>> traffBrach2GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach2GSeries.put("traffBranch2GSeries", traffBranch2G3GList);
		model.addAttribute("traffBranch2GChart", Chart.pieChart("traffBranch2GChart","TRAFFIC 2G Quý : "+title, traffBrach2GSeries));
		Map<String, List<String>> traffBrach3GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach3GSeries.put("traffBrach3GSeries", traffBranch3GList);
		model.addAttribute("traffBranch3GChart", Chart.pieChart("traffBranch3GChart","TRAFFIC 3G Quý : "+title, traffBrach3GSeries));
		Map<String, List<String>> traffBrach2G3GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach2G3GSeries.put("traffBrach2G3GSeries", traffBranch2GList);
		model.addAttribute("traffBranch2G3GChart", Chart.pieChart("traffBranch2G3GChart","TRAFFIC 2G+3G Quý : "+title, traffBrach2G3GSeries));
		
		Map<String, List<String>> dataTraffBranch2G3GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch2G3GSeries.put("dataTraffBranch2G3GSeries", dataTraffBranch2G3GList);
		model.addAttribute("dataTraffBranch2G3GChart", Chart.pieChartData("dataTraffBranch2G3GChart","DATA 2G+3G Quý : "+title, dataTraffBranch2G3GSeries));
		Map<String, List<String>> dataTraffBranch3GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch3GSeries.put("dataTraffBranch3GSeries", dataTraffBranch3GList);
		model.addAttribute("dataTraffBranch3GChart", Chart.pieChartData("dataTraffBranch3GChart","DATA 3G Quý : "+title, dataTraffBranch3GSeries));
		Map<String, List<String>> dataTraffBranch2GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch2GSeries.put("dataTraffBranch2GSeries", dataTraffBranch2GList);
		model.addAttribute("dataTraffBranch2GChart", Chart.pieChartData("dataTraffBranch2GChart","DATA 2G Quý : "+title, dataTraffBranch2GSeries));
		
		List<String> categoriesBranch = new ArrayList<String>();
		boolean[] isUsed = new boolean[32068];
		
		for(int i=0;i<vRpQrBranchTraffic2G3GList.size();i++) 
			isUsed[i] = false;
		
		Map<String,List<Float>> seriesTraff2gBranch = new LinkedHashMap<String, List<Float>>();
		Map<String,List<Float>> seriesTraff3gBranch = new LinkedHashMap<String, List<Float>>();
		Map<String,List<Float>> seriesDataBranch = new LinkedHashMap<String, List<Float>>();
		int j = 0;
		for (VRpQrBranchTraffic2G3G vRpQrBranchTraffic : vRpQrBranchTraffic2G3GList) {
			int tmpQuarter = vRpQrBranchTraffic.getQuarter();
			int tmpYear = vRpQrBranchTraffic.getYear();
			int n = 0;
			for (int m = 0; m < categoriesBranch.size(); m++)
				if (categoriesBranch.get(m) !=vRpQrBranchTraffic.getBranch())
				{
					n++;
				}
			if (n ==categoriesBranch.size()) categoriesBranch.add(vRpQrBranchTraffic.getBranch());
			if (isUsed[j]== false) {
				List<Float> tmpTraff2gList = new ArrayList<Float>();
				List<Float> tmpTraff3gList = new ArrayList<Float>();
				List<Float> tmpDataList = new ArrayList<Float>();
				int k = 0;
				for (VRpQrBranchTraffic2G3G vRpQrBranchTraffic1 : vRpQrBranchTraffic2G3GList) {
					if (tmpQuarter == vRpQrBranchTraffic1.getQuarter()&&tmpYear==vRpQrBranchTraffic1.getYear()){
						tmpTraff2gList.add(vRpQrBranchTraffic1.getTraf2g());
						tmpTraff3gList.add(vRpQrBranchTraffic1.getTraf3g());
						float tmpData =vRpQrBranchTraffic1.getUlData2g()+vRpQrBranchTraffic1.getDlData2g()+
						vRpQrBranchTraffic1.getUlData3g()+vRpQrBranchTraffic1.getDlData3g();
						tmpDataList.add(tmpData);
						isUsed[k] = true;
						k++;
					}
				}
				isUsed[j]=true;
				seriesTraff2gBranch.put("Tháng "+vRpQrBranchTraffic.getQuarter()+"/"+vRpQrBranchTraffic.getYear(), tmpTraff2gList);
				seriesTraff3gBranch.put("Tháng "+vRpQrBranchTraffic.getQuarter()+"/"+vRpQrBranchTraffic.getYear(), tmpTraff3gList);
				seriesDataBranch.put("Tháng "+vRpQrBranchTraffic.getQuarter()+"/"+vRpQrBranchTraffic.getYear(), tmpDataList);
			}
			j++;
		}
		model.addAttribute("traff2GBranchChart", Chart.columnBasic("traff2GBranchChart", "TRAFFIC 2G - KPI chi nhánh theo qúy", categoriesBranch, seriesTraff2gBranch));
		model.addAttribute("traff3GBranchChart", Chart.columnBasic("traff3GBranchChart", "TRAFFIC 3G - KPI chi nhánh theo quý", categoriesBranch, seriesTraff3gBranch));
		model.addAttribute("dataBranchChart", Chart.columnBasic("dataBranchChart", "TRAFFIC - DATA chi nhánh theo quý", categoriesBranch, seriesDataBranch));
		
		return new ModelAndView("otherReportQuarter");
	}
	
	@RequestMapping("/report/other/yr")
	public ModelAndView otherReportYr(@RequestParam(required = false) Integer year,@RequestParam(required = false) Integer startYear,@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		
		if (year== null)
			year = calender.get(Calendar.YEAR);
		
		if (endYear ==null)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		
		if (startYear ==null)
		{
			startYear = endYear;
		}
		
		if (startYear > endYear) 
			startYear = endYear;
		
		model.addAttribute("year", year);
		
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		int startMonth=1;
		int endMonth=12;
		
		int startYearMonth= year;
		int endYearMonth= year;
			
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYearMonth", startYearMonth);
		model.addAttribute("endYearMonth", endYearMonth);

		String region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpMnRegion2G3G> vRpMnRegion2G3GList = vRpMnRegion2G3GDao.filter(startMonth, startYearMonth, endMonth, endYearMonth, region);

		model.addAttribute("vRpMnRegion2G3GList", vRpMnRegion2G3GList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight1", Helper.highLight(highlightConfigList, "vRpMnRegion"));
		model.addAttribute("highlight2", Helper.highLight(highlightConfigList, "vRpYrRegion2G3G"));
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> tTraf2gList = new ArrayList<Float>();
		List<Float> tTraf3gList = new ArrayList<Float>();
		List<Float> cssr2gList = new ArrayList<Float>();
		List<Float> cssr3gList = new ArrayList<Float>();
		List<Float> dcr2gList = new ArrayList<Float>();
		List<Float> dcr3gList = new ArrayList<Float>();
		List<Float> data2GList = new ArrayList<Float>();
		List<Float> data3GList = new ArrayList<Float>();
		List<Float> badCell2gList = new ArrayList<Float>();
		List<Float> badCell3gList = new ArrayList<Float>();

		for (VRpMnRegion2G3G vRpMnRegion : vRpMnRegion2G3GList) {
			categories.add(vRpMnRegion.getMonth()+"/"+vRpMnRegion.getYear());
			
			tTraf2gList.add(vRpMnRegion.getTraf2g());
			tTraf3gList.add(vRpMnRegion.getTraf3g());
			
			cssr2gList.add(vRpMnRegion.getCssr2g());
			cssr3gList.add(vRpMnRegion.getCssr3g());
			
			dcr2gList.add(vRpMnRegion.getDcr2g());
			dcr3gList.add(vRpMnRegion.getDcr3g());
			
			float tmpData2G =vRpMnRegion.getUlData2g()+vRpMnRegion.getDlData2g();
			float tmpData3G =vRpMnRegion.getUlData3g()+vRpMnRegion.getDlData3g();
			
			data2GList.add(tmpData2G);
			data3GList.add(tmpData3G);
			
			badCell2gList.add(vRpMnRegion.getBadCell2g());
			badCell3gList.add(vRpMnRegion.getBadCell3g());
		}

		Map<String, List<Float>> trafSeries = new LinkedHashMap<String, List<Float>>();
		trafSeries.put("Traffic 3g(Erl)-column-ac4642", tTraf3gList);
		trafSeries.put("Traffic 2g(Erl)-column-92cdd8", tTraf2gList);
		trafSeries.put("Cssr 2g(%)-line-8ba850-r", cssr2gList);
		trafSeries.put("Dcr 2g(%)-line-14a458-r", dcr2gList);
		trafSeries.put("Bad Cell 2g(%)-line-f5f54e-r", badCell2gList);
		trafSeries.put("Bad Cell 3g(%)-line-d7833b-r", badCell3gList);
		model.addAttribute("trafChart", Chart.stackedColumn("trafChart", "Chỉ tiêu chất lượng (KPI) Năm "+ year, categories, trafSeries));
		
		Map<String, List<Float>> dataSeries = new LinkedHashMap<String, List<Float>>();
		dataSeries.put("Data 3g(GB)-column", data3GList);
		dataSeries.put("Data 2g(GB)-column", data2GList);
		model.addAttribute("dataChart", Chart.stackedColumn("dataChart", "DATA - Năm "+ year, categories, dataSeries));
		
		List<VRpYrRegion2G3G> vRpYrRegion2G3GList = vRpYrRegion2G3GDao.filter(startYear,endYear, region);
		model.addAttribute("vRpYrRegion2G3GList", vRpYrRegion2G3GList);
		
		List<String> categoriesQuarter = new ArrayList<String>();
		List<Float> tTraf2gQuarterList = new ArrayList<Float>();
		List<Float> tTraf3gQuarterList = new ArrayList<Float>();
		List<Float> cssr2gQuarterList = new ArrayList<Float>();
		List<Float> cssr3gQuarterList = new ArrayList<Float>();
		List<Float> dcr2gQuarterList = new ArrayList<Float>();
		List<Float> dcr3gQuarterList = new ArrayList<Float>();
		List<Float> dataQuarterList = new ArrayList<Float>();
		List<Float> badCell2gQuarterList = new ArrayList<Float>();
		List<Float> badCell3gQuarterList = new ArrayList<Float>();
		List<Float> trxQuarterList = new ArrayList<Float>();
		List<Float> erlTrxQuarterList = new ArrayList<Float>();
		List<String> categoriesRegion = new ArrayList<String>();

		Map<String,List<Float>> seriesTraffRegion = new LinkedHashMap<String, List<Float>>();

		for (VRpYrRegion2G3G yrRegion : vRpYrRegion2G3GList) {
			categoriesQuarter.add(yrRegion.getYear().toString());
			
			tTraf2gQuarterList.add(yrRegion.getTraf2g());
			tTraf3gQuarterList.add(yrRegion.getTraf3g());
			
			List<Float> tmpTraffList = new ArrayList<Float>();
			tmpTraffList.add(yrRegion.getTraf2g());
			tmpTraffList.add(yrRegion.getTraf3g());
			
			seriesTraffRegion.put("Năm "+yrRegion.getYear(), tmpTraffList);
			
			cssr2gQuarterList.add(yrRegion.getCssr2g());
			cssr3gQuarterList.add(yrRegion.getCssr3g());
			
			dcr2gQuarterList.add(yrRegion.getDcr2g());
			dcr3gQuarterList.add(yrRegion.getDcr3g());
			
			float tmpData = yrRegion.getUlData2g()+yrRegion.getUlData3g()+yrRegion.getDlData2g()+yrRegion.getDlData3g();
			
			dataQuarterList.add(tmpData);

			badCell2gQuarterList.add(yrRegion.getBadCell2g());
			badCell3gQuarterList.add(yrRegion.getBadCell3g());
			
			trxQuarterList.add(Helper.int2Float(yrRegion.getTrx2g()));
			erlTrxQuarterList.add(Helper.int2Float(yrRegion.getErlTrx2g()));
		}
		
		Map<String, List<Float>> trafQuarterSeries = new LinkedHashMap<String, List<Float>>();
		trafQuarterSeries.put("Traffic 3g(Erl)-column-ac4642", tTraf3gQuarterList);
		trafQuarterSeries.put("Traffic 2g(Erl)-column-92cdd8", tTraf2gQuarterList);
		trafQuarterSeries.put("Cssr 2g(%)-line-8ba850-r", cssr2gQuarterList);
		trafQuarterSeries.put("Dcr 2g(%)-line-14a458-r", dcr2gQuarterList);
		trafQuarterSeries.put("Bad Cell 2g(%)-line-f5f54e-r", badCell2gQuarterList);
		trafQuarterSeries.put("Bad Cell 3g(%)-line-d7833b-r", badCell3gQuarterList);
		model.addAttribute("traffQuarterChart", Chart.stackedColumn("traffYearChart", "Chỉ tiêu chất lượng (KPI) theo Năm ", categoriesQuarter, trafQuarterSeries));
		
		Map<String, List<Float>> trafByQuarterSeries = new LinkedHashMap<String, List<Float>>();
		trafByQuarterSeries.put("Traffic 3g(Erl)-column-ae4c49", tTraf3gQuarterList);
		trafByQuarterSeries.put("Traffic 2g(Erl)-column-cfc4dc", tTraf2gQuarterList);
		trafByQuarterSeries.put("Tổng Data(GB)-line-745a92-r", dataQuarterList);
		trafByQuarterSeries.put("TRX-line-dc873d-r", trxQuarterList);
		trafByQuarterSeries.put("Erl/TRX-line-0000ff-r", erlTrxQuarterList);
		model.addAttribute("traffByQuarterChart", Chart.stackedColumnOther("traffByYearChart", "DATA TRAFFIC - theo Năm ", categoriesQuarter, trafByQuarterSeries));
		
		categoriesRegion.add("Traffic 2G(Erl)");
		categoriesRegion.add("Traffic 3G(Erl)");
		model.addAttribute("traffRegionChart", Chart.columnBasic("traffRegionChart", "Traffic- KPI trung tâm theo Năm", categoriesRegion, seriesTraffRegion));
		
		List<VRpYrBranchTraffic2G3G> vRpYrBranchTraffic2G3GList = vRpYrBranchTraffic2G3GDao.filter(startYear, endYear, region);
		model.addAttribute("vRpYrBranchTraffic2G3GList", vRpYrBranchTraffic2G3GList);
		
		List<VRpYrBranchTraffic2G3G> vRpYrBranchTrafficRate2G3GList = vRpYrBranchTraffic2G3GDao.filterRate(year, region);
		model.addAttribute("vRpYrBranchTrafficRate2G3GList", vRpYrBranchTrafficRate2G3GList);
		
		List<String> traffBranch2GList = new ArrayList<String>();
		List<String> traffBranch3GList = new ArrayList<String>();
		List<String> traffBranch2G3GList = new ArrayList<String>();
		List<String> dataTraffBranch2GList = new ArrayList<String>();
		List<String> dataTraffBranch3GList = new ArrayList<String>();
		List<String> dataTraffBranch2G3GList = new ArrayList<String>();
		float totTraff2G = 0;
		float totTraff3G = 0;
		float totTraff2G3G = 0;
		float totDataTraff2G = 0;
		float totDataTraff3G = 0;
		float totDataTraff2G3G = 0;
		for (VRpYrBranchTraffic2G3G vRpYrBranchTrafficRate : vRpYrBranchTrafficRate2G3GList) {
			if (vRpYrBranchTrafficRate.getTraf2g()==null)
				totTraff2G3G +=0;
			else
				totTraff2G3G +=vRpYrBranchTrafficRate.getTraf2g3g();
			if (vRpYrBranchTrafficRate.getTraf3g()==null)
				totTraff3G +=0;
			else
				totTraff3G +=vRpYrBranchTrafficRate.getTraf3g();
			if (vRpYrBranchTrafficRate.getTraf2g()==null)
				totTraff2G +=0;
			else
				totTraff2G +=vRpYrBranchTrafficRate.getTraf2g();
			if (vRpYrBranchTrafficRate.getDlData2g()==null) {
				totDataTraff2G +=0;
				totDataTraff2G3G +=0;
			}
			else { 
				totDataTraff2G +=vRpYrBranchTrafficRate.getDlData2g();
				totDataTraff2G3G += vRpYrBranchTrafficRate.getDlData2g();
			}
			if (vRpYrBranchTrafficRate.getUlData2g()==null) {
				totDataTraff2G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff2G +=vRpYrBranchTrafficRate.getUlData2g();
				totDataTraff2G3G += vRpYrBranchTrafficRate.getUlData2g();
			}
			if (vRpYrBranchTrafficRate.getDlData3g()==null) {
				totDataTraff3G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff3G +=vRpYrBranchTrafficRate.getDlData3g();
				totDataTraff2G3G += vRpYrBranchTrafficRate.getDlData3g();
			}
			if (vRpYrBranchTrafficRate.getUlData3g()==null) {
				totDataTraff3G +=0;
				totDataTraff2G3G +=0;
			}
			else {
				totDataTraff3G +=vRpYrBranchTrafficRate.getUlData3g();
				totDataTraff2G3G += vRpYrBranchTrafficRate.getUlData3g();
			}
			
		}
		String title="";
		for (VRpYrBranchTraffic2G3G vRpYrBranchTrafficRate : vRpYrBranchTrafficRate2G3GList) {
			title = vRpYrBranchTrafficRate.getYear().toString();
			float rateTraff2G3G;
			float rateTraff3G;
			float rateTraff2G;
			float rateDataTraff2G3G;
			float rateDataTraff3G;
			float rateDataTraff2G;
			float tempData2G = vRpYrBranchTrafficRate.getDlData2g()+vRpYrBranchTrafficRate.getUlData2g();
			float tempData3G = vRpYrBranchTrafficRate.getDlData3g()+vRpYrBranchTrafficRate.getUlData3g();
			float tempData2G3G = tempData2G + tempData3G;
			if (totTraff2G3G == 0)
				rateTraff2G3G =0;
			else
				rateTraff2G3G =Math.round(100*100*(vRpYrBranchTrafficRate.getTraf2g3g()/totTraff2G3G));
			if (totTraff3G == 0)
				rateTraff3G =0;
			else
				rateTraff3G =Math.round(100*100*(vRpYrBranchTrafficRate.getTraf3g()/totTraff3G));
			if (totTraff2G == 0)
				rateTraff2G =0;
			else
				rateTraff2G =Math.round(100*100*(vRpYrBranchTrafficRate.getTraf2g()/totTraff2G));
			
			if (totDataTraff2G3G == 0)
				rateDataTraff2G3G =0;
			else
				rateDataTraff2G3G =Math.round(100*100*((vRpYrBranchTrafficRate.getDlData2g()+vRpYrBranchTrafficRate.getUlData2g()+vRpYrBranchTrafficRate.getDlData3g()+vRpYrBranchTrafficRate.getUlData3g())/totDataTraff2G3G));
			if (totDataTraff3G == 0)
				rateDataTraff3G =0;
			else
				rateDataTraff3G = Math.round(100*100*((vRpYrBranchTrafficRate.getDlData3g()+vRpYrBranchTrafficRate.getUlData3g())/totDataTraff3G));
			if (totDataTraff2G == 0)
				rateDataTraff2G =0;
			else
				rateDataTraff2G = Math.round(100*100*((vRpYrBranchTrafficRate.getDlData2g()+vRpYrBranchTrafficRate.getUlData2g())/totDataTraff2G));
			
			traffBranch2G3GList.add(vRpYrBranchTrafficRate.getBranch()+"-"+vRpYrBranchTrafficRate.getTraf2g3g()+"-"+rateTraff2G3G/100);
			traffBranch3GList.add(vRpYrBranchTrafficRate.getBranch()+"-"+vRpYrBranchTrafficRate.getTraf3g()+"-"+rateTraff3G/100);
			traffBranch2GList.add(vRpYrBranchTrafficRate.getBranch()+"-"+vRpYrBranchTrafficRate.getTraf2g()+"-"+rateTraff2G/100);
			
			dataTraffBranch2G3GList.add(vRpYrBranchTrafficRate.getBranch()+"-"+tempData2G3G+"-"+rateDataTraff2G3G/100);
			dataTraffBranch3GList.add(vRpYrBranchTrafficRate.getBranch()+"-"+tempData3G+"-"+rateDataTraff3G/100);
			dataTraffBranch2GList.add(vRpYrBranchTrafficRate.getBranch()+"-"+tempData2G+"-"+rateDataTraff2G/100);
		}
		
		Map<String, List<String>> traffBrach2GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach2GSeries.put("traffBranch2GSeries", traffBranch2G3GList);
		model.addAttribute("traffBranch2GChart", Chart.pieChart("traffBranch2GChart","TRAFFIC 2G Năm : "+title, traffBrach2GSeries));
		Map<String, List<String>> traffBrach3GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach3GSeries.put("traffBrach3GSeries", traffBranch3GList);
		model.addAttribute("traffBranch3GChart", Chart.pieChart("traffBranch3GChart","TRAFFIC 3G Năm : "+title, traffBrach3GSeries));
		Map<String, List<String>> traffBrach2G3GSeries = new LinkedHashMap<String, List<String>>();
		traffBrach2G3GSeries.put("traffBrach2G3GSeries", traffBranch2GList);
		model.addAttribute("traffBranch2G3GChart", Chart.pieChart("traffBranch2G3GChart","TRAFFIC 2G+3G Năm : "+title, traffBrach2G3GSeries));
		
		Map<String, List<String>> dataTraffBranch2G3GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch2G3GSeries.put("dataTraffBranch2G3GSeries", dataTraffBranch2G3GList);
		model.addAttribute("dataTraffBranch2G3GChart", Chart.pieChart("dataTraffBranch2G3GChart","DATA 2G+3G Năm : "+title, dataTraffBranch2G3GSeries));
		Map<String, List<String>> dataTraffBranch3GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch3GSeries.put("dataTraffBranch3GSeries", dataTraffBranch3GList);
		model.addAttribute("dataTraffBranch3GChart", Chart.pieChart("dataTraffBranch3GChart","DATA 3G Năm : "+title, dataTraffBranch3GSeries));
		Map<String, List<String>> dataTraffBranch2GSeries = new LinkedHashMap<String, List<String>>();
		dataTraffBranch2GSeries.put("dataTraffBranch2GSeries", dataTraffBranch2GList);
		model.addAttribute("dataTraffBranch2GChart", Chart.pieChart("dataTraffBranch2GChart","DATA 2G Năm : "+title, dataTraffBranch2GSeries));
		
		List<String> categoriesBranch = new ArrayList<String>();
		boolean[] isUsed = new boolean[32068];
		
		for(int i=0;i<vRpYrBranchTraffic2G3GList.size();i++) 
			isUsed[i] = false;
		
		Map<String,List<Float>> seriesTraff2gBranch = new LinkedHashMap<String, List<Float>>();
		Map<String,List<Float>> seriesTraff3gBranch = new LinkedHashMap<String, List<Float>>();
		Map<String,List<Float>> seriesDataBranch = new LinkedHashMap<String, List<Float>>();
		int j = 0;
		for (VRpYrBranchTraffic2G3G vRpYrBranchTraffic : vRpYrBranchTraffic2G3GList) {
			int tmpYear = vRpYrBranchTraffic.getYear();
			int n = 0;
			for (int m = 0; m < categoriesBranch.size(); m++)
				if (categoriesBranch.get(m) !=vRpYrBranchTraffic.getBranch())
				{
					n++;
				}
			if (n ==categoriesBranch.size()) categoriesBranch.add(vRpYrBranchTraffic.getBranch());
			if (isUsed[j]== false) {
				List<Float> tmpTraff2gList = new ArrayList<Float>();
				List<Float> tmpTraff3gList = new ArrayList<Float>();
				List<Float> tmpDataList = new ArrayList<Float>();
				int k = 0;
				for (VRpYrBranchTraffic2G3G vRpYrBranchTraffic1 : vRpYrBranchTraffic2G3GList) {
					if (tmpYear==vRpYrBranchTraffic1.getYear()){
						tmpTraff2gList.add(vRpYrBranchTraffic1.getTraf2g());
						tmpTraff3gList.add(vRpYrBranchTraffic1.getTraf3g());
						float tmpData =vRpYrBranchTraffic1.getUlData2g()+vRpYrBranchTraffic1.getDlData2g()+
						vRpYrBranchTraffic1.getUlData3g()+vRpYrBranchTraffic1.getDlData3g();
						tmpDataList.add(tmpData);
						isUsed[k] = true;
						k++;
					}
				}
				isUsed[j]=true;
				seriesTraff2gBranch.put("Năm "+vRpYrBranchTraffic.getYear(), tmpTraff2gList);
				seriesTraff3gBranch.put("Năm "+vRpYrBranchTraffic.getYear(), tmpTraff3gList);
				seriesDataBranch.put("Năm "+vRpYrBranchTraffic.getYear(), tmpDataList);
			}
			j++;
		}
		model.addAttribute("traff2GBranchChart", Chart.columnBasic("traff2GBranchChart", "TRAFFIC 2G - KPI chi nhánh theo năm", categoriesBranch, seriesTraff2gBranch));
		model.addAttribute("traff3GBranchChart", Chart.columnBasic("traff3GBranchChart", "TRAFFIC 3G - KPI chi nhánh theo năm", categoriesBranch, seriesTraff3gBranch));
		model.addAttribute("dataBranchChart", Chart.columnBasic("dataBranchChart", "TRAFFIC - DATA chi nhánh theo năm", categoriesBranch, seriesDataBranch));
		
		return new ModelAndView("otherReportYear");
	}
}
