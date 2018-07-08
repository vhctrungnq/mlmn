package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvince3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.OptionProvince3g;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;

@Controller
@RequestMapping("/report/radio3g/province/option/*")
public class OptionProvince3gController {
	@Autowired
	private HProvincesCodeDAO provinceDao;
	@Autowired
	private VRpDyProvince3GDAO vRpDyProvince3gDao;
	private DateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView showlist(
			@RequestParam(required = false) String province, 
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String starttime,
			@RequestParam(required = false) String endtime, 
			ModelMap model, HttpServletRequest request) throws ParseException {

		if (starttime == null) {
			starttime = df.format(new Date()) + " 00:00";
		}
		
		if(endtime == null) {
			endtime = dff.format(new Date());
		}
		String startDate, endDate;
		int startHour, endHour; 
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		List<OptionProvince3g> dataList =  new ArrayList<OptionProvince3g>();

		if(DateTools.isValid("dd/MM/yyyy HH:mm", starttime) 
				&& DateTools.isValid("dd/MM/yyyy HH:mm", endtime)){
			Date d = dff.parse(starttime);
			Date d2 = dff.parse(endtime);
			calendar.setTime(d);
			calendar2.setTime(d2);

			startDate = df.format(d);
			endDate = df.format(d2);
			startHour = calendar.get(Calendar.HOUR_OF_DAY);
			endHour = calendar2.get(Calendar.HOUR_OF_DAY);

			dataList = vRpDyProvince3gDao.Province3gOption(startDate, endDate, startHour, endHour, 
					province, region);

		} else {
			dataList = null;
		}
			List<HProvincesCode> regionList=provinceDao.getAllRegion();
			List<HProvincesCode> provinceList= provinceDao.getAllProvince();
			model.addAttribute("regionList", regionList);
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("region", region);
			model.addAttribute("province", province);
			model.addAttribute("starttime", starttime);
			model.addAttribute("endtime", endtime);

			model.addAttribute("optionProvince3gList", dataList);

			// Lay ten file export
			String time = starttime+"_"+endtime;
			String exportFileName = "Bao_cao_Province_qos_3g__" + time;
			model.addAttribute("exportFileName", exportFileName);

			return new ModelAndView("baocaotuychon/optionProvince3gList");
		}
}
