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

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrCell3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrCell3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;


@Controller
@RequestMapping("/report/radio3g/cell/yr/*")
public class VRpYrCell3gController extends BaseController {
	@Autowired
	private VRpYrCell3GDAO vRpYrCellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;

	@RequestMapping("list")
	public ModelAndView dyList(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String function, 
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String bscid, 
			@RequestParam(required = false) String province, 
			@RequestParam(required = false) String startYear, 
			@RequestParam(required = false) String endYear, 
			ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR);
		
		if(startYear == null) startYear = String.valueOf(yearnow);
		if(endYear == null) endYear = String.valueOf(yearnow); 
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}

		List<VRpYrCell3G> detailList = new ArrayList<VRpYrCell3G>();

		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)){
			try {
				detailList = vRpYrCellDao.filter(startYear, endYear, cellid, bscid, province, region, 
						column, order);
			} catch (Exception e) {
				detailList = null;
			}
		}else{
			detailList = null;
		}
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		List<Bsc3G> bscList = bsc3GDao.getAllBsc();
		model.addAttribute("bscList", bscList);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("vRpYrCellList", detailList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpYrCell"));

		return new ModelAndView("yrCell3GList");
	}
}
