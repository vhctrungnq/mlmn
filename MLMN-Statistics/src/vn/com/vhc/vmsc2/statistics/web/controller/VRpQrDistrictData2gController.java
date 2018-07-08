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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrDistrictData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrDistrictData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;


@Controller
@RequestMapping("/report/radio/district-data/qr/*")
public class VRpQrDistrictData2gController extends BaseController {
	@Autowired
	private VRpQrDistrictData2gDAO vRpQrDistrictData2gDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;


	@RequestMapping("list")
	public ModelAndView dyList(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,  
			@RequestParam(required = false) String startQuarter,
			@RequestParam(required = false) String startYear, 
			@RequestParam(required = false) String endQuarter,
			@RequestParam(required = false) String endYear, 
			ModelMap model, 
			HttpServletRequest request) {
		
		
		if (region == null || region.equals("TT6"))
			region = "MLMN";
		
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR);
		
		if(startYear == null) startYear = String.valueOf(yearnow);
		if(endYear == null) endYear =  String.valueOf(yearnow);
		if (startQuarter == null) startQuarter = "1";
		if(endQuarter == null) endQuarter = "4";
		
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList",provinceList);
		/*List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);*/
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<VRpQrDistrictData2g> detailList = new ArrayList<VRpQrDistrictData2g>();
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startQuarter) && NumberTools.checkNumber(endQuarter)){
			try {
				detailList = vRpQrDistrictData2gDao.filter(startQuarter, startYear, endQuarter, endYear, province, region, order, column);
			} catch (Exception e) {
				detailList = null;
			}
		}else{
			detailList = null;
		}
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		model.addAttribute("vRpQrDistrictData2gList", detailList);
		model.addAttribute("region", region);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "detailList"));

		String time = "tu_quy_"+startQuarter+"_"+startYear+"den_quy_"+endQuarter+"_"+endYear;
		String exportFileName = "Bao_cao_district_data_2g_" + time;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("baocaoquy/qrDistrictData2gList");
	}
}
