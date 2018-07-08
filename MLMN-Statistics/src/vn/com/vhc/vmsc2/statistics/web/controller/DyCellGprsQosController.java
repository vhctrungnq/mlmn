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
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellGprsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/cell-gprs-qos/dy/*")
public class DyCellGprsQosController extends BaseController {
	@Autowired
	private VRpDyCellGprsQosDAO vRpDyCellGprsQosDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

//		int startRecord = 0;
//		int order = 1;
//		String column = "DAY, REGION, PROVINCE, BSCID, CELLID";
//		try {
//			startRecord = (Integer
//					.parseInt(request.getParameter((new ParamEncoder("vRpDyCellGprsQos").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
//			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellGprsQos").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
//			column = request.getParameter((new ParamEncoder("vRpDyCellGprsQos").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
//		} catch (NumberFormatException e) {
//		}
//		int endRecord = startRecord + 100;

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpDyCellGprsQos> vRpDyCellGprsQos = vRpDyCellGprsQosDao.filter(df.format(startDate), df.format(endDate), cellid, province, bscid, region);

//		Integer totalSize = vRpDyCellGprsQosDao.countRow(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
//		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("province", province);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyCellGprsQos", vRpDyCellGprsQos);
		model.addAttribute("region", region);
		
		return new ModelAndView("dyCellGprsQosList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
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
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<VRpDyCellGprsQos> vRpDyCellGprsQosList = vRpDyCellGprsQosDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);

			model.addAttribute("vRpDyCellGprsQosList", vRpDyCellGprsQosList);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);
			
			// chart
			List<String> categories = new ArrayList<String>();
			List<Float> dlTbfSucrList = new ArrayList<Float>();
			List<Float> ulTbfSucrList = new ArrayList<Float>();

			for (VRpDyCellGprsQos vRpDyCell : vRpDyCellGprsQosList) {
				categories.add(df.format(vRpDyCell.getDay()));
				dlTbfSucrList.add(vRpDyCell.getDlTbfSucr());
				ulTbfSucrList.add(vRpDyCell.getUlTbfSucr());
			}
			
			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", dlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", ulTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

			// checkBox
			String[] checkColumns = { "DL_TBF_REQ", "DL_TBF_SUCR", "UL_TBF_REQ", "UL_TBF_SUCR", "GDL_TRAF", "GUL_TRAF", "EDL_TRAF","EUL_TRAF", "DATALOAD" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCellGprsQos", 6));
			
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyCellGprsQos";
	}
}
