package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscGprsCs;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/bsc-gprs-cs/mn/*")
public class MnBscGprsCsController extends BaseController {
	@Autowired
	private VRpMnBscGprsCsDAO vRpMnBscGprsCsDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		List<VRpMnBscGprsCs> vRpMnBscGprsCs = vRpMnBscGprsCsDao.filter(startMonth, startYear, endMonth, endYear, bscid, region);

		model.addAttribute("bscid", bscid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		
		model.addAttribute("vRpMnBscGprsCs", vRpMnBscGprsCs);
		model.addAttribute("region", region);
		
		return new ModelAndView("mnBscGprsCsList");
	}

	@RequestMapping("detail")
	public String showReport( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		List<VRpMnBscGprsCs> vRpMnBscGprsCsList = vRpMnBscGprsCsDao.filter(startMonth,startYear, endMonth, endYear, bscid, region);

		model.addAttribute("vRpMnBscGprsCsList", vRpMnBscGprsCsList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> csxLevel1List = new ArrayList<Float>();
		List<Float> csxLevel2List = new ArrayList<Float>();
		List<Float> csxLevel3List = new ArrayList<Float>();
		List<Float> csxLevel4List = new ArrayList<Float>();
		List<Float> mcsxLevel1List = new ArrayList<Float>();
		List<Float> mcsxLevel2List = new ArrayList<Float>();
		List<Float> mcsxLevel3List = new ArrayList<Float>();
		List<Float> mcsxLevel4List = new ArrayList<Float>();
		List<Float> mcsxLevel5List = new ArrayList<Float>();
		List<Float> mcsxLevel6List = new ArrayList<Float>();
		List<Float> mcsxLevel7List = new ArrayList<Float>();
		List<Float> mcsxLevel8List = new ArrayList<Float>();
		List<Float> mcsxLevel9List = new ArrayList<Float>();
		List<Float> dataloadList = new ArrayList<Float>();
		for (VRpMnBscGprsCs vRpMnBsc : vRpMnBscGprsCsList) {
			categories.add(Integer.toString(vRpMnBsc.getMonth())+"/"+vRpMnBsc.getYear());
			csxLevel1List.add(vRpMnBsc.getCsxLevel1());
			csxLevel2List.add(vRpMnBsc.getCsxLevel2());
			csxLevel3List.add(vRpMnBsc.getCsxLevel3());
			csxLevel4List.add(vRpMnBsc.getCsxLevel4());
			mcsxLevel1List.add(vRpMnBsc.getMcsxLevel1());
			mcsxLevel2List.add(vRpMnBsc.getMcsxLevel2());
			mcsxLevel3List.add(vRpMnBsc.getMcsxLevel3());
			mcsxLevel4List.add(vRpMnBsc.getMcsxLevel4());
			mcsxLevel5List.add(vRpMnBsc.getMcsxLevel5());
			mcsxLevel6List.add(vRpMnBsc.getMcsxLevel6());
			mcsxLevel7List.add(vRpMnBsc.getMcsxLevel7());
			mcsxLevel8List.add(vRpMnBsc.getMcsxLevel8());
			mcsxLevel9List.add(vRpMnBsc.getMcsxLevel9());
			dataloadList.add(vRpMnBsc.getDataload());
		}
		Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
		csxLevel1Series.put("CSX Level1--80699b", csxLevel1List);
		model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

		Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
		csxLevel2Series.put("CSX Level2--80699b", csxLevel2List);
		model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

		Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
		csxLevel3Series.put("CSX Level3--80699b", csxLevel3List);
		model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

		Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
		csxLevel4Series.put("CSX Level4--80699b", csxLevel4List);
		model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

		Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel1Series.put("MCSX Level1--db843d", mcsxLevel1List);
		model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

		Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel2Series.put("MCSX Level2--80699b", mcsxLevel2List);
		model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

		Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel3Series.put("MCSX Level3--80699b", mcsxLevel3List);
		model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

		Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel4Series.put("MCSX Level4--db843d", mcsxLevel4List);
		model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

		Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel5Series.put("MCSX Level5--db843d", mcsxLevel5List);
		model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

		Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel6Series.put("MCSX Level6--80699b", mcsxLevel6List);
		model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

		Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel7Series.put("MCSX Level7--80699b", mcsxLevel7List);
		model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

		Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel8Series.put("MCSX Level8--db843d", mcsxLevel8List);
		model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

		Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel9Series.put("MCSX Level9--db843d", mcsxLevel9List);
		model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));

		// checkBox
		String[] checkColumns = {"CSX_LEVEL1","CSX_LEVEL2","CSX_LEVEL3","CSX_LEVEL4","MCSX_LEVEL1","MCSX_LEVEL2","MCSX_LEVEL3","MCSX_LEVEL4","MCSX_LEVEL5","MCSX_LEVEL6","MCSX_LEVEL7","MCSX_LEVEL8","MCSX_LEVEL9","DATALOAD"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnBscGprsCs", 6));

		return "mnBscGprsCs";
	}
}
