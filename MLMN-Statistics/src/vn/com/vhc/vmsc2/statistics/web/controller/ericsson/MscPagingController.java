package vn.com.vhc.vmsc2.statistics.web.controller.ericsson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.DyMscPagingDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscPagingDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscPagingbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyMscPaging;
import vn.com.vhc.vmsc2.statistics.domain.HrMscPaging;
import vn.com.vhc.vmsc2.statistics.domain.HrMscPagingbc;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core-era/msc-pag-glo/*")
public class MscPagingController extends BaseController{
	@Autowired
	private HrMscPagingbcDAO hrMscPGBCDAO;
	@Autowired
	private HrMscPagingDAO hrMscPGDAO;
	@Autowired
	private DyMscPagingDAO dyMscPGDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("detail")
	public String Report(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
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
				List<HrMscPagingbc> hrMSCPGBC = hrMscPGBCDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrMSCPGBC = hrMscPGBCDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrMSCPGBC = hrMscPGBCDAO.filter2(startHour,startDate,endHour,mscid);
				}
				
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("hrMSCPGBC", hrMSCPGBC);
				model.addAttribute("hourList", hourList);
				return "hrMSCPGBClist";
			}
	
	@RequestMapping("hr")
	public String showReport(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
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
				List<HrMscPaging> hrMSCPG = hrMscPGDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrMSCPG = hrMscPGDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrMSCPG = hrMscPGDAO.filter2(startHour,startDate,endHour,mscid);
				}
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("hrMSCPG", hrMSCPG);
				model.addAttribute("hourList", hourList);
				return "hrMSCPGlist";
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
				List<DyMscPaging> dyMSCPG = dyMscPGDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("dyMSCPG", dyMSCPG);
				model.addAttribute("mscid", mscid);
				
				/* Chart Start */
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				long time = System.currentTimeMillis();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date destDay = new Date(time - 24 * 60 * 60 * 1000);
				Date startDay = dt.minusDays(30).toDate();
				
			
				Map<String, List<Float>> seriesGSM = new LinkedHashMap<String, List<Float>>();
				Map<String, List<Float>> seriesWDCA  = new LinkedHashMap<String, List<Float>>();

				
				List<String> categories = new ArrayList<String>();
				String chartAreaGSM = "";
				String chartAreaWDCA = "";

				for(int i = 0; i < mscList.size(); i++)
				{
					
					List<DyMscPaging> dyList = dyMscPGDAO.filter(mscList.get(i).getMscid(),df.format(startDay), df.format(destDay));
					List<Float> chartListGSM = new ArrayList<Float>();
					List<Float> chartListWDCA = new ArrayList<Float>();
					for(DyMscPaging record : dyList){
						if(record.getEndUserGsmSucc()==null)
							record.setEndUserGsmSucc(Float.parseFloat("0"));
						if(record.getEndUserWcdmaSucc()==null)
							record.setEndUserWcdmaSucc(Float.parseFloat("0"));
						chartListGSM.add(record.getEndUserGsmSucc());
						chartListWDCA.add(record.getEndUserWcdmaSucc());
						
					
					}
					seriesGSM.put(mscList.get(i).getMscid(), chartListGSM);
					seriesWDCA.put(mscList.get(i).getMscid(), chartListWDCA);
					
				}
				List<DyMscPaging> mscDay = dyMscPGDAO.filterDay(df.format(startDay), df.format(destDay));
				for(int j = 0; j < mscDay.size(); j++){
					categories.add(df.format(mscDay.get(j).getDay()));
				}
				
				chartAreaGSM += Chart.basicLineNew("chartGSM", "Biểu đồ Succ GMS paging end user (%)","%", categories, seriesGSM);
				chartAreaWDCA += Chart.basicLineNew("chartWDCA", "Biểu đồ Succ WDCA paging end user (%)","%", categories, seriesWDCA);
				model.addAttribute("chartdivGSM", chartAreaGSM);
				model.addAttribute("chartdivWDCA", chartAreaWDCA);
				return "dyMscPGList";
			}	
}
