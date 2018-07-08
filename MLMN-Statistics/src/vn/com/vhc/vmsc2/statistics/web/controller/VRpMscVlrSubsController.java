package vn.com.vhc.vmsc2.statistics.web.controller; 

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscVlrSubsCnDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscVlrSubsLacDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrMscVlrSubsLacDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscVlrSubsCn;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscVlrSubsLac;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrMscVlrSubsLac;
import vn.com.vhc.vmsc2.statistics.web.filter.MscVlrSubsFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;

@Controller
@RequestMapping("/report/msc/vlrSubsCn/*")
public class VRpMscVlrSubsController extends BaseController{

	@Autowired
	private VRpDyMscVlrSubsCnDAO  vRpDyMscVlrSubsCnDAO ;
	@Autowired
	private VRpDyMscVlrSubsLacDAO vRpDyMscVlrSubsLacDAO;
	@Autowired
	private VRpHrMscVlrSubsLacDAO vRpHrMscVlrSubsLacDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("hrlac")
	public String hrLac(@ModelAttribute("filter") MscVlrSubsFilter filter,
			@RequestParam(required = false) String shour, @RequestParam(required = false) String ehour, 
			@RequestParam(required = false) String sdate,@RequestParam(required = false) String edate,ModelMap model, HttpServletRequest request)
			{
				if (ehour == null) {
					ehour = "23";
				} else {
					ehour = ehour.substring(0, ehour.indexOf(":"));
				}
				if (shour == null) {
					shour = "0";
				} else {
					shour = shour.substring(0, shour.indexOf(":"));
				}
				
				Calendar cal = Calendar.getInstance(); 
				if (sdate== null
						|| sdate.equals("")
						|| (sdate != null && !sdate.equals("") && DateTools
								.isValid("dd/MM/yyyy", sdate) == false)) { 
					sdate = df.format(cal.getTime());
				}
				
				if (edate == null
						|| edate.equals("")
						|| (edate != null && !edate.equals("") && DateTools
								.isValid("dd/MM/yyyy", edate) == false)) { 
					edate = df.format(cal.getTime());
				}
				
				filter.setEdate(edate);
				filter.setEhour(ehour);
				filter.setSdate(sdate);
				filter.setShour(shour);
			
				List<VRpHrMscVlrSubsLac> vRpHrMscVlrSubsLac = new ArrayList<VRpHrMscVlrSubsLac>();
				
				try
				{
					vRpHrMscVlrSubsLac = vRpHrMscVlrSubsLacDAO.getDataList(filter);
				}
				catch (Exception exp)
				{
					vRpHrMscVlrSubsLac = null;
				}
				
				List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
				List<Msc> mscList = mscDAO.getAll();
				String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
				
				filter.setEhour(ehour + ":00");
				filter.setShour(shour + ":00");
				model.addAttribute("hourList", hourList);
				model.addAttribute("regionList", regionList);
				model.addAttribute("mscList", mscList);
				model.addAttribute("vRpHrMscVlrSubsLac", vRpHrMscVlrSubsLac);  
				model.addAttribute("filter", filter);
				
				return "/mscVlrSubs/hrMscVlrSubsLac";
			}
	@RequestMapping("dylac")
	public String dyLac(@ModelAttribute("filter") MscVlrSubsFilter filter,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cn,@RequestParam(required = false) String mscid,
			@RequestParam(required = false) String sdate,@RequestParam(required = false) String edate,
			ModelMap model, HttpServletRequest request)
			{
				List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
				
				Calendar cal = Calendar.getInstance(); 
				if (sdate== null
						|| sdate.equals("")
						|| (sdate != null && !sdate.equals("") && DateTools
								.isValid("dd/MM/yyyy", sdate) == false)) { 
					sdate = df.format(cal.getTime());
				}
				
				if (edate == null
						|| edate.equals("")
						|| (edate != null && !edate.equals("") && DateTools
								.isValid("dd/MM/yyyy", edate) == false)) { 
					edate = df.format(cal.getTime());
				} 
				filter.setEdate(edate); 
				filter.setSdate(sdate); 
			
				List<VRpDyMscVlrSubsLac> vRpDyMscVlrSubsLac = new ArrayList<VRpDyMscVlrSubsLac>();
				
				try
				{
					vRpDyMscVlrSubsLac = vRpDyMscVlrSubsLacDAO.getDataList(filter);
				}
				catch (Exception exp)
				{
					vRpDyMscVlrSubsLac = null;
				}
				 
				model.addAttribute("regionList", regionList);
				model.addAttribute("filter", filter);
				model.addAttribute("vRpDyMscVlrSubsLac", vRpDyMscVlrSubsLac);
				
				return "/mscVlrSubs/dyMscVlrSubsLac";
			}	
	@RequestMapping("dycn")
	public String dycn(@ModelAttribute("filter") MscVlrSubsFilter filter,
			@RequestParam(required = false) String bscid,@RequestParam(required = false) String mscid,
			@RequestParam(required = false) String sdate,@RequestParam(required = false) String edate,
			ModelMap model, HttpServletRequest request)
			{
				List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
				
				Calendar cal = Calendar.getInstance(); 
				if (sdate== null
						|| sdate.equals("")
						|| (sdate != null && !sdate.equals("") && DateTools
								.isValid("dd/MM/yyyy", sdate) == false)) { 
					sdate = df.format(cal.getTime());
				}
				
				if (edate == null
						|| edate.equals("")
						|| (edate != null && !edate.equals("") && DateTools
								.isValid("dd/MM/yyyy", edate) == false)) { 
					edate = df.format(cal.getTime());
				} 
				filter.setEdate(edate); 
				filter.setSdate(sdate); 
			
				List<VRpDyMscVlrSubsCn> vRpDyMscVlrSubsCn = new ArrayList<VRpDyMscVlrSubsCn>();
				
				try
				{
					vRpDyMscVlrSubsCn = vRpDyMscVlrSubsCnDAO.getDataList(filter);
				}
				catch (Exception exp)
				{
					vRpDyMscVlrSubsCn = null;
				}
				
				model.addAttribute("regionList", regionList);
				model.addAttribute("filter", filter);
				model.addAttribute("vRpDyMscVlrSubsCn", vRpDyMscVlrSubsCn);
				
				return "/mscVlrSubs/dyMscVlrSubsCn";
			}
}
