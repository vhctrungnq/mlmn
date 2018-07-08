package vn.com.vhc.vmsc2.statistics.web.controller.ericsson;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.DyMscCallSetupBcDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscCallSetupBcDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyMscCallSetupBc;
import vn.com.vhc.vmsc2.statistics.domain.HrMscCallSetupBc;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;

@Controller
@RequestMapping("/report/core-era/call-setup-success/*")
public class MscCallSetupSuccController extends BaseController{

	@Autowired
	private DyMscCallSetupBcDAO dyMscCallSetupBcDAO;
	@Autowired
	private HrMscCallSetupBcDAO hrMscCallSetupBcDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat dfNumber = new DecimalFormat("#.##");
	private String avgs = "";
	@RequestMapping("dy/list")
	public String ListReport(@RequestParam(required = false) String mscid,
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
				List<DyMscCallSetupBc> dyMscCallSetupBc = dyMscCallSetupBcDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("dyMscCallSetupBc", dyMscCallSetupBc);
				
				double sum = 0,avg = 0;	
				for(int i = 0; i < dyMscCallSetupBc.size(); i++)
				{
					if(dyMscCallSetupBc.get(i).getSuccrRatio() == null)
					{
						continue;
					}
					 sum = sum + dyMscCallSetupBc.get(i).getSuccrRatio();
				}
				
				avg = (sum/(dyMscCallSetupBc.size()));
				avgs = dfNumber.format(avg);
				model.addAttribute("avg",avgs);
				
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscList", mscList);
				return "dyMscCallSetupBc";
			}	
	@RequestMapping("dy/detail")
	public String dyReport(@RequestParam(required = false) String mscid,
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
				List<DyMscCallSetupBc> dyMscCallSetupBcdetail = dyMscCallSetupBcDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("dyMscCallSetupBcdetail", dyMscCallSetupBcdetail);
				double sum = 0,avg = 0;	
				for(int i = 0; i < dyMscCallSetupBcdetail.size(); i++)
				{
					if(dyMscCallSetupBcdetail.get(i).getSuccrRatio() == null)
					{
						continue;
					}
					 sum = sum + dyMscCallSetupBcdetail.get(i).getSuccrRatio();
				}
				
				avg = (sum/(dyMscCallSetupBcdetail.size()));
				avgs = dfNumber.format(avg);
				model.addAttribute("avg",avgs);
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscList", mscList);
				return "dyMscCallSetupBcdetail";
			}
	@RequestMapping("hr/detail")
	public String hrList(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model, HttpServletRequest request)
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
				if (endHour == null) {
					endHour = "23";
				} else {
					if(endHour.indexOf(":") >0)
						endHour = endHour.substring(0, endHour.indexOf(":"));
					else
						endHour = "0";
					}
				if (startHour == null) {
					startHour = "0";
				} else {	
					if(startHour.indexOf(":") >0)
						startHour = startHour.substring(0, startHour.indexOf(":"));
					else
						startHour = "0";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("startHour", startHour + ":00");
				model.addAttribute("endHour", endHour + ":00");
		
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			    List<HrMscCallSetupBc> hrMscCallSetupBc = hrMscCallSetupBcDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
			    if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrMscCallSetupBc = hrMscCallSetupBcDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrMscCallSetupBc = hrMscCallSetupBcDAO.filter2(startHour,startDate,endHour,mscid);
				}
			    model.addAttribute("hourList", hourList);
			    MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("hrMscCallSetupBc", hrMscCallSetupBc);
				return "hrMscCallSetupBc";
			}
}
