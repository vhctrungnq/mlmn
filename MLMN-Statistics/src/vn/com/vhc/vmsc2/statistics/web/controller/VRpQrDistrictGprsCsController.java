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
import vn.com.vhc.vmsc2.statistics.dao.VRpQrDistrictGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrDistrictGprsCs;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;

@Controller
@RequestMapping("/report/radio/district-gprs-cs/qr/*")
public class VRpQrDistrictGprsCsController {
	@Autowired
	private VRpQrDistrictGprsCsDAO vRpQrDistrictGprsCsDao;
	@Autowired
	private HProvincesCodeDAO provinceDao;

	@RequestMapping("list")
	public ModelAndView showList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) String district, @RequestParam(required = false) String startQuarter,
			@RequestParam(required = false) String startYear, @RequestParam(required = false) String endQuarter,
			@RequestParam(required = false) String endYear, ModelMap model, HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);

		if (startYear == null )
			startYear = String.valueOf(year);
		if (endYear == null)
			endYear = String.valueOf(year);
		if (startQuarter == null)
			startQuarter = "1";
		if (endQuarter == null)
			endQuarter = "4";
		int order = 0;
		String column = "YEAR";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<VRpQrDistrictGprsCs> detailList = new ArrayList<VRpQrDistrictGprsCs>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startQuarter) && NumberTools.checkNumber(endQuarter)){
			try {
				detailList = vRpQrDistrictGprsCsDao.filter(startQuarter, startYear, endQuarter, endYear,  province, district, region, order, column);
			} catch (Exception e) {
				detailList = null;
			}
		}else{
			detailList = null;
		}

		List<HProvincesCode> provinceList = provinceDao.getAllProvince();
		List<HProvincesCode> regionList = provinceDao.getAllRegion();
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("regionList", regionList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("vRpQrDistrictGprsCs", detailList);
		
		// Lay ten file export
		String time = "tu_quy_" + startQuarter + "_" + startYear + "_den_quy_" + endQuarter + "_" + endYear;
		String exportFileName = "Bao_cao_District_Gprscs_" + time;
		model.addAttribute("exportFileName", exportFileName);
		return new ModelAndView("baocaoquy/qrDistrictGprsCsList");
	}
}
