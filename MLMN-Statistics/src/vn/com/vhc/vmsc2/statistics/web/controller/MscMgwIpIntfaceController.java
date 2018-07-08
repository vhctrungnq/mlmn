package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.DyMscMgwIpIntfaceDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscMgwIpIntfaceDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscMgwIpIntfaceUserDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyMscMgwIpIntface;
import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwIpIntface;
import vn.com.vhc.vmsc2.statistics.domain.HrMscMgwIpIntfaceUser;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;

@Controller
@RequestMapping("/report/core/ip-interface/*")
public class MscMgwIpIntfaceController extends BaseController{
	@Autowired
	private HrMscMgwIpIntfaceUserDAO hrMscIpfaceUserDAO;
	@Autowired
	private HrMscMgwIpIntfaceDAO hrMscIpfaceDAO;
	@Autowired
	private DyMscMgwIpIntfaceDAO dyMscIpfaceDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("detail")
	public String Report(@RequestParam(required = true) String mscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
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
				
				int startRecord = 0;
			    try {
			     startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("cell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			    } catch (NumberFormatException e) {
			    }
			    int endRecord = startRecord + 100;
			    filter.setStartRecord(startRecord);
			    filter.setEndRecord(endRecord);
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
				List<HrMscMgwIpIntfaceUser> HrMscipfaceuser = hrMscIpfaceUserDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					HrMscipfaceuser = hrMscIpfaceUserDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					HrMscipfaceuser = hrMscIpfaceUserDAO.filter2(startHour,startDate,endHour,mscid);
				}
				
				MscFilter h_msc = new MscFilter();
				h_msc.setType("MGW");
				List<Msc> mscList = mscDAO.filter(h_msc);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("HrMscipfaceuser", HrMscipfaceuser);
				model.addAttribute("hourList", hourList);
				return "hrMSCMgwIpIntfaceUser";
			}
	
	@RequestMapping("hr")
	public String ShowReport(@RequestParam(required = true) String mscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
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
				
				int startRecord = 0;
			    try {
			     startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("cell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			    } catch (NumberFormatException e) {
			    }
			    int endRecord = startRecord + 100;
			    filter.setStartRecord(startRecord);
			    filter.setEndRecord(endRecord);
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
				List<HrMscMgwIpIntface> HrMscIpface = hrMscIpfaceDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					HrMscIpface = hrMscIpfaceDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					HrMscIpface = hrMscIpfaceDAO.filter2(startHour,startDate,endHour,mscid);
				}
				
				MscFilter h_msc = new MscFilter();
				h_msc.setType("MGW");
				List<Msc> mscList = mscDAO.filter(h_msc);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("HrMscIpface", HrMscIpface);
				model.addAttribute("hourList", hourList);
				return "hrMSCMgwIpIntface";
			}
	
	@RequestMapping("dy")
	public String showReport(@RequestParam(required = true) String mscid,
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
				List<DyMscMgwIpIntface> DyMscipface = dyMscIpfaceDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("DyMscipface", DyMscipface);
				MscFilter h_msc = new MscFilter();
				h_msc.setType("MGW");
				List<Msc> mscList = mscDAO.filter(h_msc);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				return "dyMscMgwIpIntface";
			}
}
