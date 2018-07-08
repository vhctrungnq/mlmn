package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellCapacityDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellQosBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellCapacity;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@RequestMapping("/report/radio/cell-capacity/dy/*")
@Controller
public class DyCellCapacityController extends BaseController{
	@Autowired
	private VRpDyCellCapacityDAO vRpDyCellCapacityDao;
	@Autowired
	private VRpDyCellQosBhDAO vRpDyCellQosBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		if (cellid == null|| cellid.length()==0 || cellid.contains(" ")==false) {
		
		int startRecord = 0;
		int order = 1;
		String column = "DAY, REGION, PROVINCE, BSCID, CELLID";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellCapacity").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellCapacity").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vRpDyCellCapacity").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;

		List<VRpDyCellCapacity> vRpDyCellCapacity = vRpDyCellCapacityDao.filter(df.format(startDate), df.format(endDate), cellid, province, bscid, region, startRecord, endRecord, column, order == 1 ? "ASC" : "DESC");
		Integer totalSize = vRpDyCellCapacityDao.countRow(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("region", region);
		model.addAttribute("province", province);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyCellCapacity", vRpDyCellCapacity);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCellCapacity"));
		}
		else {
			String tempCellId = cellid;
			String[] strCellId = cellid.split(" ");
			
			cellid = "'";
			for (int i =0; i<strCellId.length-1;i++) {
				cellid += strCellId[i].toString()+"','";
			}
			cellid += strCellId[strCellId.length-1].toString()+"'";
			int startRecord = 0;
			int order = 1;
			String column = "DAY, REGION, PROVINCE, BSCID, CELLID";
			try {
				startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellCapacity").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
				order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellCapacity").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("vRpDyCellCapacity").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			int endRecord = startRecord + 100;

			List<VRpDyCellCapacity> vRpDyCellCapacity = vRpDyCellCapacityDao.filterArray(df.format(startDate), df.format(endDate), cellid, province, bscid, region, startRecord,
					endRecord, column, order == 1 ? "ASC" : "DESC");
			Integer totalSize = vRpDyCellQosBhDao.countRowArray(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
			model.addAttribute("totalSize", totalSize);

			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);

			model.addAttribute("region", region);
			model.addAttribute("province", province);
			model.addAttribute("bscid", bscid);
			model.addAttribute("cellid", tempCellId);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);

			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			model.addAttribute("vRpDyCellCapacity", vRpDyCellCapacity);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));
		}

		return new ModelAndView("dyCellCapacityList");
	}

}
