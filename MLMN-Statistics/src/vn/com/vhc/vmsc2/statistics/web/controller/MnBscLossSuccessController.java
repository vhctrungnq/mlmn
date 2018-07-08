
package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHo;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;

	@Controller
	@RequestMapping("/report/2g/bsc-low-succ/mn/*")
	public class MnBscLossSuccessController extends BaseController {
		@Autowired
		private VRpMnBscHoDAO vRpMnBscHoDAO;
		@Autowired
		private BscDAO bscDao;
		@RequestMapping("details")
		public ModelAndView mnList(@RequestParam(required = false) String bscid,
				@RequestParam(required = false) Integer startMonth,
				@RequestParam(required = false) Integer startYear,
				@RequestParam(required = false) Integer endMonth,
				@RequestParam(required = false) Integer endYear,
				@ModelAttribute("filter") CellFilter filtercell,
				ModelMap model, HttpServletRequest request) {
			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			if(endMonth==null){
				cal.add(Calendar.MONTH,-1);
				endMonth = cal.get(Calendar.MONTH)+1;
				endYear = cal.get(Calendar.YEAR);
			}
			
			if (startMonth == null) {
				if (endMonth > 3) {
					startMonth = endMonth -3;
					startYear = endYear;
				}
				else {
					cal1.add(Calendar.MONTH,-3);
					startMonth = cal1.get(Calendar.MONTH)+1;
					startYear = cal1.get(Calendar.YEAR);
				}
			}
			if(bscid == null){
				bscid = "";
			}
			String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		    model.addAttribute("monthList", monthList);
			model.addAttribute("startMonth", startMonth);
			model.addAttribute("endMonth", endMonth);
			model.addAttribute("startYear", startYear);
			model.addAttribute("endYear", endYear);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<VRpMnBscHo> vRpMnBscHolist = vRpMnBscHoDAO.filterLS(startMonth.toString(), endMonth.toString(), startYear.toString(),endYear.toString(), bscid);
			model.addAttribute("bscid", bscid);
			model.addAttribute("vRpMnBscHo", vRpMnBscHolist);

			return new ModelAndView("mnBscLossSuccessHo");
		}
}