package vn.com.vhc.vmsc2.statistics.web.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscHo;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;

@Controller
@RequestMapping("/report/2g/bsc-low-succ/hr/*")
public class HrBscLossSuccessHoController extends BaseController {
	@Autowired
	private VRpHrBscHoDAO vRpHrBscHoDao;
	@Autowired
	private BscDAO bscDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("details")
	public ModelAndView showReport(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) String endHour, 
			@RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filtercell,
			ModelMap model, HttpServletRequest request) {
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		}else{
			if(endHour.indexOf(":") > 0){
				endHour = endHour.substring(0, endHour.indexOf(":"));
			}else{
				endHour = "23";
			}			
		}
		if (startHour == null) {
			startHour = "0";
		}else{
			if(startHour.indexOf(":") > 0){
				startHour = startHour.substring(0, startHour.indexOf(":"));
			}else{
				startHour = "0";
			}		
		}
		if(bscid == null){
			bscid = "";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		List<VRpHrBscHo> hrBscLossSuccessHo = vRpHrBscHoDao.filter1(startHour,startDate,endHour,endDate,bscid);
		
		 if(endDate.compareTo(startDate) < 0)
			{
				startDate = new Date();
				hrBscLossSuccessHo = vRpHrBscHoDao.filter2(startHour,startDate,endHour,bscid);
			}
			else if(endDate.compareTo(startDate)== 0)
			{
				hrBscLossSuccessHo = vRpHrBscHoDao.filter2(startHour,startDate,endHour,bscid);
			}
		
		model.addAttribute("hrBscLossSuccessHo", hrBscLossSuccessHo);
		model.addAttribute("bscid", bscid);
		return new ModelAndView("hrBscLossSuccessHo");
	}
}



