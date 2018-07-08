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

import vn.com.vhc.vmsc2.statistics.dao.DyMsccploadDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMsccploadDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyMsccpload;
import vn.com.vhc.vmsc2.statistics.domain.HrMsccpload;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core-era/msc-cpload/*")
public class MsccploadController extends BaseController{
	@Autowired
	private HrMsccploadDAO hrMscCPLDAO;
	@Autowired
	private DyMsccploadDAO DyMsccpDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
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
			    List<HrMsccpload> hrMSCCPL = hrMscCPLDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
			    if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrMSCCPL = hrMscCPLDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrMSCCPL = hrMscCPLDAO.filter2(startHour,startDate,endHour,mscid);
				}
			    
			    MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("hrMSCCPL", hrMSCCPL);
				model.addAttribute("hourList", hourList);
				return "hrMSCcploadlist";
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
				List<DyMsccpload> dyMSCCP = DyMsccpDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("dyMSCCP", dyMSCCP);
				model.addAttribute("mscid", mscid);
				
				/* Chart Start */
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscList", mscList);
				long time = endDate.getTime();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date destDay = new Date(time - 24 * 60 * 60 * 1000);
				Date startDay = dt.minusDays(30).toDate();
				
			
				Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
				List<String> categories = new ArrayList<String>();
				String chartArea = "";

				for(int i = 0; i < mscList.size(); i++)
				{
					
					List<DyMsccpload> dyList = DyMsccpDAO.filter(mscList.get(i).getMscid(),df.format(startDay), df.format(destDay));
					List<Float> inMaxcpList = new ArrayList<Float>();
					for(DyMsccpload record : dyList){
						if(record.getMaxCpLoad()==null)
							record.setMaxCpLoad(Float.parseFloat("0"));
						inMaxcpList.add(record.getMaxCpLoad());
						
					
					}
					series.put(mscList.get(i).getMscid(), inMaxcpList);
					
				}
				List<DyMsccpload> mscDay = DyMsccpDAO.filterDay(df.format(startDay), df.format(destDay));
				for(int j = 0; j < mscDay.size(); j++){
					categories.add(df.format(mscDay.get(j).getDay()));
				}
				
				chartArea += Chart.basicLineNew("chart", "Biểu đồ MaxCPLoad (%)","%", categories, series);
				model.addAttribute("chartdiv", chartArea);
			
				
				
				return "dyMsccploadlist";
			}	
}
