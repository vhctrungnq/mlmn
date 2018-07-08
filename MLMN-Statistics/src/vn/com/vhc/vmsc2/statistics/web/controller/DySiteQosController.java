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

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDySiteQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDySiteQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class DySiteQosController extends BaseController {
	@Autowired
	private VRpDySiteQosDAO vRpDySiteQosDao;
	@Autowired
	private VRpDySiteQosBhDAO vRpDySiteQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/site-qos/dy/list")
	public ModelAndView dySiteQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String siteid, @RequestParam(required = false) String day, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date d;
			if (day == null) {
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				d = df.parse(day);
			}

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			model.addAttribute("region", region);
			
			List<VRpDySiteQos> vRpDySiteQos = vRpDySiteQosDao.filter(province, bscid, siteid, d, region);

			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("bscid", bscid);
			model.addAttribute("siteid", siteid);
			model.addAttribute("day", df.format(d));
			model.addAttribute("vRpDySiteQos", vRpDySiteQos);
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDySiteQos"));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dySiteQosList");
	}

	@RequestMapping("/report/radio/site-qos/dy/details")
	public String sQoswReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
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
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			model.addAttribute("region", region);
			
			List<VRpDySiteQos> vRpDySiteQosDetails = vRpDySiteQosDao.filterDetails(df.format(startDay), df.format(endDay), bscid, siteid, region);

			model.addAttribute("vRpDySiteQosDetails", vRpDySiteQosDetails);
			model.addAttribute("siteid", siteid);
			model.addAttribute("bscid", bscid);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<String> siteList = cellDao.getSiteOfBsc(bscid);
			model.addAttribute("siteList", siteList);

			// chart
			List<String> categories = new ArrayList<String>();
			List<Float> cssrList = new ArrayList<Float>();
			List<Float> tTrafList = new ArrayList<Float>();
			List<Float> haftrateList = new ArrayList<Float>();
			List<Float> tAttList = new ArrayList<Float>();
			List<Float> tDrpsList = new ArrayList<Float>();
			List<Float> tDrprList = new ArrayList<Float>();
			List<Float> tBlkrList = new ArrayList<Float>();
			List<Float> sAttList = new ArrayList<Float>();
			List<Float> sDrpsList = new ArrayList<Float>();
			List<Float> sDrprList = new ArrayList<Float>();
			List<Float> sBlksList = new ArrayList<Float>();
			List<Float> sBlkrList = new ArrayList<Float>();
			for (VRpDySiteQos vRpDySiteQos : vRpDySiteQosDetails) {
				categories.add(df.format(vRpDySiteQos.getDay()));
				cssrList.add(vRpDySiteQos.getCssr());
				tTrafList.add(vRpDySiteQos.gettTraf());
				haftrateList.add(vRpDySiteQos.getHaftratePercent());
				tAttList.add(Helper.int2Float(vRpDySiteQos.gettAtts()));
				tDrpsList.add(Helper.int2Float(vRpDySiteQos.gettDrps()));
				tDrprList.add(vRpDySiteQos.gettDrpr());
				tBlkrList.add(vRpDySiteQos.gettBlkr());
				sAttList.add(Helper.int2Float(vRpDySiteQos.getsAtts()));
				sDrpsList.add(Helper.int2Float(vRpDySiteQos.getsDrps()));
				sDrprList.add(vRpDySiteQos.getsDrpr());
				sBlksList.add(Helper.int2Float(vRpDySiteQos.getsBlks()));
				sBlkrList.add(vRpDySiteQos.getsBlkr());

			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("T_TRAF", tTrafList);
			series.put("HAFT_RATE (%)", haftrateList);
			series.put("T_ATT", tAttList);
			series.put("T_DRP", tDrpsList);
			series.put("T_DRPR (%)", tDrprList);
			series.put("T_NBLKR (%)", tBlkrList);
			series.put("CSSR (%)", cssrList);
			series.put("S_ATT", sAttList);
			series.put("S_DRP", sDrpsList);
			series.put("S_DRPR (%)", sDrprList);
			series.put("S_BLK", sBlksList);
			series.put("S_BLKR (%)", sBlkrList);

			model.addAttribute("chart", Chart.comboDualAxes("SITE " + siteid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "T_DEF", "T_AVAIL", "T_TRAF", "HAFT_RATE", "T_ATT", "T_SEIZ", "T_DRP", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "S_DEF",
					"S_AVAIL", "S_ATT", "S_SEIZ", "S_DRP", "S_DRPR", "S_BLK", "S_BLKR", "DATALOAD" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDySiteQosDetail", 7));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dySiteQosDetails";
	}

	@RequestMapping("/report/radio/site-qos/dy/bhDetails")
	public String sQoswReportBh(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
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
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			model.addAttribute("region", region);
			
			List<VRpDySiteQosBh> vRpDySiteQosBhDetails = vRpDySiteQosBhDao.filterDetails(df.format(startDay), df.format(endDay), bscid, siteid, region);

			model.addAttribute("vRpDySiteQosBhDetails", vRpDySiteQosBhDetails);
			model.addAttribute("siteid", siteid);
			model.addAttribute("bscid", bscid);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<String> siteList = cellDao.getSiteOfBsc(bscid);
			model.addAttribute("siteList", siteList);

			/* Start Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhCssrList = new ArrayList<Float>();
			List<Float> bhTNblksList = new ArrayList<Float>();
			List<Float> bhTNblkrList = new ArrayList<Float>();
			List<Float> bhTDrpsList = new ArrayList<Float>();
			List<Float> bhTDrprList = new ArrayList<Float>();
			List<Float> bhTTrafList = new ArrayList<Float>();
			List<Float> bhTTrafhList = new ArrayList<Float>();
			List<Float> bhTGosList = new ArrayList<Float>();
			List<Float> bhSBlksList = new ArrayList<Float>();
			List<Float> bhSBlkrList = new ArrayList<Float>();
			List<Float> bhSDrpsList = new ArrayList<Float>();
			List<Float> bhSDrprList = new ArrayList<Float>();
			for (VRpDySiteQosBh VRpDySiteQosBh : vRpDySiteQosBhDetails) {
				categories.add(df.format(VRpDySiteQosBh.getDay()));
				bhCssrList.add(VRpDySiteQosBh.getBhCssr());
				bhTNblksList.add(VRpDySiteQosBh.getBhTNblks());
				bhTNblkrList.add(VRpDySiteQosBh.getBhTNblkr());
				bhTDrpsList.add(VRpDySiteQosBh.getBhTDrps());
				bhTDrprList.add(VRpDySiteQosBh.getBhTDrpr());
				bhTTrafList.add(VRpDySiteQosBh.getBhTTraf());
				bhTTrafhList.add(VRpDySiteQosBh.getBhTTrafh());
				bhTGosList.add(VRpDySiteQosBh.getBhTGos());
				bhSBlksList.add(VRpDySiteQosBh.getBhSBlks());
				bhSBlkrList.add(VRpDySiteQosBh.getBhSBlkr());
				bhSDrpsList.add(VRpDySiteQosBh.getBhSDrps());
				bhSDrprList.add(VRpDySiteQosBh.getBhSDrpr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("TRAF", bhTTrafList);
			series.put("H_TRAF", bhTTrafhList);
			series.put("T_DRPS", bhTDrpsList);
			series.put("T_DRPR (%)", bhTDrprList);
			series.put("T_NBLKS", bhTNblksList);
			series.put("T_NBLKR (%)", bhTNblkrList);
			series.put("CSSR (%)", bhCssrList);
			series.put("GoS (%)", bhTGosList);
			series.put("S_DRPS", bhSDrpsList);
			series.put("S_DRPR (%)", bhSDrprList);
			series.put("S_BLKS", bhSBlksList);
			series.put("S_BLKR (%)", bhSBlkrList);

			model.addAttribute("chart", Chart.comboDualAxes("SITE BH " + siteid + " Daily Report", categories, series));
			// checkBox
			String[] checkColumns = { "T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKR", "CSSR", "GoS", "S_DEF", "S_AVAIL",
					"S_ATT", "S_DRPS", "S_DRPR", "S_BLKS", "S_BLKR" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDySiteQosBhDetail", 8));
			/* End Chart */
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dySiteQosBhDetails";
	}
}
