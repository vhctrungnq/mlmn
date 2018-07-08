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
import vn.com.vhc.vmsc2.statistics.dao.VRpQrCellGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrCellGprsCs;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;

@Controller
@RequestMapping("/report/radio/cell-gprs-cs/qr/*")
public class VRpQrCellGprsCsController extends BaseController {
	
	@Autowired
	private VRpQrCellGprsCsDAO vRpQrCellGprsCsDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO provinceDao;
	
	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String bscid, @RequestParam(required = false) String province, 
			@RequestParam(required = false) String startQuarter,
			@RequestParam(required = false) String startYear, @RequestParam(required = false) String endQuarter,
			@RequestParam(required = false) String endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		int yearnow = calendar.get(Calendar.YEAR);

		if (startYear == null )
			startYear = String.valueOf(yearnow);
		if (endYear == null)
			endYear = String.valueOf(yearnow);
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
		
		List<VRpQrCellGprsCs> detailList = new ArrayList<VRpQrCellGprsCs>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startQuarter) && NumberTools.checkNumber(endQuarter)){
			try {
				detailList = vRpQrCellGprsCsDao.filter(startQuarter, startYear, endQuarter, endYear, cellid, province, bscid, region, order, column);
			} catch (Exception e) {
				detailList = null;
			}
		}else{
			detailList = null;
		}
		
		List<Bsc> bscList = bscDao.getAll();
		List<HProvincesCode> provinceList = provinceDao.getAllProvince();
		List<HProvincesCode> regionList = provinceDao.getAllRegion();
		model.addAttribute("cellid", cellid);
		model.addAttribute("region", region);
		model.addAttribute("regionList", regionList);
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscList", bscList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpQrCellGprsCsList", detailList);
		
		// Lay ten file export
		String time = "tu_quy_" + startQuarter + "_" + startYear + "_den_quy_" + endQuarter + "_" + endYear;
		String exportFileName = "Bao_cao_cell_Gprscs_" + time;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("baocaoquy/qrCellGprsCsList");
	}
}
