package vn.com.vhc.vmsc2.statistics.web.controller.ericsson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.DyMscsmsDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscsmsDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyMscsms;
import vn.com.vhc.vmsc2.statistics.domain.HrMscsms;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;

@Controller
@RequestMapping("/report/core-era/msc-sms/*")
public class MscSMSController extends BaseController{
	@Autowired
	private HrMscsmsDAO  hrMscsmsDAO ;
	@Autowired
	private DyMscsmsDAO  dyMscsmsDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("hr")
	public String showReport(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
		 ModelMap model)
			{
				
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
				if (endHour == null) {
					endHour = "23";
				} else {
					if(endHour.indexOf(":") > 0){
						endHour = endHour.substring(0, endHour.indexOf(":"));
					}
					else{
						endHour = "23";
					}
				}
				if (startHour == null) {
					startHour = "0";
				} else {
					if(startHour.indexOf(":") > 0 ){
						startHour = startHour.substring(0, startHour.indexOf(":"));
					}else{
						startHour = "0";
					}	
				}
				if(mscid == null){
					mscid = "";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("startHour", startHour + ":00");
				model.addAttribute("endHour", endHour + ":00");
				model.addAttribute("mscid", mscid);
				String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
				model.addAttribute("hourList", hourList);
				List<HrMscsms> hrMSCSMS = hrMscsmsDAO.filter(startHour,startDate,endHour,endDate,mscid);
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrMSCSMS = hrMscsmsDAO.filter(startHour,endHour,endDate,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					hrMSCSMS = hrMscsmsDAO.filter(startHour,endHour,endDate,mscid);
				}
				model.addAttribute("hrMSCSMS", hrMSCSMS);
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscList", mscList);
				
				return "hrMscSMS";
			}	
	
	@RequestMapping("dy")
	public String showReport(@RequestParam(required = false) String mscid,
		 @RequestParam(required = false) Date startDate,
		 @RequestParam(required = false) Date endDate,
		 ModelMap model)
			{
				
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
				if(mscid == null){
					mscid = "";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("mscid", mscid);
				List<DyMscsms> dyMSCSMS = dyMscsmsDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("dyMSCSMS", dyMSCSMS);
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscList", mscList);
				return "dyMscSMSEra";
			}	
}
