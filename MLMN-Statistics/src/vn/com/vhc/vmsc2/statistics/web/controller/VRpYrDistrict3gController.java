package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpYrDistrict3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrDistrict3G;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;

@Controller
@RequestMapping("/report/radio3g/district/yr/*")
public class VRpYrDistrict3gController {

	@Autowired
	private VRpYrDistrict3GDAO vRpYrDistrict3GDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO provinceDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,
			@RequestParam(required = false) String bscid, @RequestParam(required = false) String province, @RequestParam(required = false) String startYear,
			@RequestParam(required = false) String endYear, ModelMap model, HttpServletRequest request) {

		Calendar calendar = Calendar.getInstance();
		int yearnow = calendar.get(Calendar.YEAR);

		if (startYear == null)
			startYear = String.valueOf(yearnow);
		if (endYear == null)
			endYear = String.valueOf(yearnow);

		int order = 0;
		String column = "YEAR";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}

		List<VRpYrDistrict3G> detailList = new ArrayList<VRpYrDistrict3G>();

		if (NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)) {

			try {
				detailList = vRpYrDistrict3GDao.filter(startYear, endYear, province, bscid, region, order, column);
			} catch (Exception e) {
				detailList = null;
			}
		} else {
			detailList = null;
		}

		List<Bsc> bscList = bscDao.getAll();
		List<HProvincesCode> provinceList = provinceDao.getAllProvince();
		List<HProvincesCode> regionList = provinceDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		model.addAttribute("region", region);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscList", bscList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpYrDistrict3GList", detailList);

		// Lay ten file export
		String time = "tu_nam" + "_" + startYear + "_" + "den_nam" + "_" + endYear;
		String exportFileName = "Bao_cao_District_3G_" + time;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("baocaonam/yrDistrict3GList");
	}
}
