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
import vn.com.vhc.vmsc2.statistics.dao.VRpQrBsc3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrBsc3G;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;

@Controller
@RequestMapping("/report/radio3g/rnc/qr/*")
public class VRpQrBsc3gController {

	@Autowired
	private VRpQrBsc3GDAO vRpQrBsc3GDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;
	@Autowired
	private HProvincesCodeDAO provinceDao;
	
	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, 	@RequestParam(required = false) String bscid,		 
			@RequestParam(required = false) String startQuarter, @RequestParam(required = false) String startYear,
			@RequestParam(required = false) String endQuarter,
			@RequestParam(required = false) String endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR);
		
		if(startYear == null) startYear = String.valueOf(yearnow);
		if(endYear == null) endYear =  String.valueOf(yearnow);
		if (startQuarter == null) startQuarter = "1";
		if(endQuarter == null) endQuarter = "4";
		
		int order = 0;
		String column = "YEAR";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<VRpQrBsc3G> detailList = new ArrayList<VRpQrBsc3G>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startQuarter) && NumberTools.checkNumber(endQuarter)){
			try {
				detailList = vRpQrBsc3GDao.filter(startQuarter, startYear, endQuarter, endYear, bscid, region, order, column);
			} catch (Exception e) {
				detailList = null;
			}
		}else{
			detailList = null;
		}
		
		List<Bsc3G> bscList = bsc3GDao.getAllBsc();
		List<HProvincesCode> regionList = provinceDao.getAllRegion();
		
		model.addAttribute("regionList", regionList);
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		model.addAttribute("bscid", bscid);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscList", bscList);
		model.addAttribute("vRpQrBsc3GList", detailList);
		
		// Lay ten file export
		String time = "tu_quy_"+startQuarter+"_"+startYear+"_den_quy_"+endQuarter+"_"+endYear;
		String exportFileName = "Bao_cao_Bsc_3G_" + time;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("baocaoquy/qrBsc3GList");
	}
}
