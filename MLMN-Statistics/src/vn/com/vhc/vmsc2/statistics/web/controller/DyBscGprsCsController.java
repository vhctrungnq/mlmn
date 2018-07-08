package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscGprsCs;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;



@Controller
@RequestMapping("/report/radio/bsc-gprs-cs/dy/*")
public class DyBscGprsCsController extends BaseController {
	@Autowired
	private VRpDyBscGprsCsDAO vRpDyBscGprsCsDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");


		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpDyBscGprsCs> vRpDyBscGprsCs = vRpDyBscGprsCsDao.filter(s[0],s[1], bscid, region);

		model.addAttribute("bscid", bscid);
		model.addAttribute("vRpDyBscGprsCs", vRpDyBscGprsCs);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		return new ModelAndView("dyBscGprsCsList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyBscGprsCs> vRpDyBscGprsCsList = vRpDyBscGprsCsDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("vRpDyBscGprsCsList", vRpDyBscGprsCsList);
			model.addAttribute("bscid", bscid);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
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
			for (VRpDyBscGprsCs vRpDyBsc : vRpDyBscGprsCsList) {
				categories.add(df.format(vRpDyBsc.getDay()));
				csxLevel1List.add(vRpDyBsc.getCsxLevel1());
				csxLevel2List.add(vRpDyBsc.getCsxLevel2());
				csxLevel3List.add(vRpDyBsc.getCsxLevel3());
				csxLevel4List.add(vRpDyBsc.getCsxLevel4());
				mcsxLevel1List.add(vRpDyBsc.getMcsxLevel1());
				mcsxLevel2List.add(vRpDyBsc.getMcsxLevel2());
				mcsxLevel3List.add(vRpDyBsc.getMcsxLevel3());
				mcsxLevel4List.add(vRpDyBsc.getMcsxLevel4());
				mcsxLevel5List.add(vRpDyBsc.getMcsxLevel5());
				mcsxLevel6List.add(vRpDyBsc.getMcsxLevel6());
				mcsxLevel7List.add(vRpDyBsc.getMcsxLevel7());
				mcsxLevel8List.add(vRpDyBsc.getMcsxLevel8());
				mcsxLevel9List.add(vRpDyBsc.getMcsxLevel9());
				dataloadList.add(vRpDyBsc.getDataload());
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
			String[] checkColumns = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
					"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9", "dataload" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBscGprsCs", 5));
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyBscGprsCs";
	}
}
