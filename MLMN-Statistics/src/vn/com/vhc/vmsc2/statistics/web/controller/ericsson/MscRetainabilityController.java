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

import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscRetainabilityDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrMscRetainabilityDAO;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscRetainability;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrMscRetainability;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core-era/retainability/*")
public class MscRetainabilityController extends BaseController{
	@Autowired
	private VRpHrMscRetainabilityDAO hrMscRetainabilityDAO;
	@Autowired
	private VRpDyMscRetainabilityDAO dyMscRetainabilityDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("hr")
	public String showReport(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter,@RequestParam(required = false) String hour, ModelMap model, HttpServletRequest request)
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
					endHour = endHour.substring(0, endHour.indexOf(":"));
				}
				if (startHour == null) {
					startHour = "0";
				} else {
					startHour = startHour.substring(0, startHour.indexOf(":"));
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
			    List<VRpHrMscRetainability> hrMscRetainability = hrMscRetainabilityDAO.filter(startHour,startDate,endHour,endDate,mscid);
				
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrMscRetainability = hrMscRetainabilityDAO.filter2(startHour,startDate,endHour,mscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrMscRetainability = hrMscRetainabilityDAO.filter2(startHour,startDate,endHour,mscid);
				}
				
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscid", mscid);
				model.addAttribute("mscList", mscList);
				model.addAttribute("hrMscRetainability", hrMscRetainability);
				model.addAttribute("hourList", hourList);
				return "hrMscRetainabilityList";
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
				List<VRpDyMscRetainability> dyMscRetainability = dyMscRetainabilityDAO.filter(mscid,df.format(startDate), df.format(endDate));
				model.addAttribute("dyMscRetainability", dyMscRetainability);
				model.addAttribute("mscid", mscid);
				
				/* Chart Start */
				MscFilter mscFilter = new MscFilter();
				mscFilter.setType("MSC");
				List<Msc> mscList = mscDAO.filter(mscFilter);
				model.addAttribute("mscList", mscList);
				long time = System.currentTimeMillis();
				DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
				Date destDay = new Date(time - 24 * 60 * 60 * 1000);
				Date startDay = dt.minusDays(30).toDate();
				
			
				Map<String, List<Float>> seriesInterGSM = new LinkedHashMap<String, List<Float>>();
				Map<String, List<Float>> seriesInterU2G = new LinkedHashMap<String, List<Float>>();
				Map<String, List<Float>> seriesIntraGSM = new LinkedHashMap<String, List<Float>>();
				Map<String, List<Float>> seriesIntraU2G = new LinkedHashMap<String, List<Float>>();
				List<String> categories = new ArrayList<String>();
				String chartAreaInterGSM = "";
				String chartAreaInterU2G = "";
				String chartAreaIntraGSM = "";
				String chartAreaIntraU2G = "";

				for(int i = 0; i < mscList.size(); i++)
				{
					
					List<VRpDyMscRetainability> dyList = dyMscRetainabilityDAO.filter(mscList.get(i).getMscid(),df.format(startDay), df.format(destDay));
					List<Float> interListgsm = new ArrayList<Float>();
					List<Float> interListu2g = new ArrayList<Float>();
					List<Float> intraListgsm = new ArrayList<Float>();
					List<Float> intraListu2g = new ArrayList<Float>();
					for(VRpDyMscRetainability record : dyList){
						if(record.getSuccrInterMsc()==null)
							record.setSuccrInterMsc(Float.parseFloat("0"));
						if(record.getSuccrU2gInterMscHo()==null)
							record.setSuccrU2gInterMscHo(Float.parseFloat("0"));
						if(record.getSuccrIntraMscHoGsm()==null)
							record.setSuccrIntraMscHoGsm(Float.parseFloat("0"));
						if(record.getSuccrIntraMscHoU2g()==null)
							record.setSuccrIntraMscHoU2g(Float.parseFloat("0"));
						
						
						
						interListgsm.add(record.getSuccrInterMsc());
						interListu2g.add(record.getSuccrU2gInterMscHo());
						intraListgsm.add(record.getSuccrIntraMscHoGsm());
						intraListu2g.add(record.getSuccrIntraMscHoU2g());
						
					
					}
					seriesInterGSM.put(mscList.get(i).getMscid(), interListgsm);
					seriesInterU2G.put(mscList.get(i).getMscid(), interListu2g);
					seriesIntraGSM.put(mscList.get(i).getMscid(), intraListgsm);
					seriesIntraU2G.put(mscList.get(i).getMscid(), intraListu2g);
					
				}
				List<VRpDyMscRetainability> mscDay = dyMscRetainabilityDAO.filterDay( df.format(startDay), df.format(destDay));
				for(int j = 0; j < mscDay.size(); j++){
					categories.add(df.format(mscDay.get(j).getDay()));
				}
				
				chartAreaInterGSM += Chart.basicLineNew("chartInterGSM", "Biểu đồ Inter - MSC GSM (%)","%", categories, seriesInterGSM);
				chartAreaInterU2G += Chart.basicLineNew("chartInterU2G", "Biểu đồ Inter - MSC U2G (%)","%", categories, seriesInterU2G);
				chartAreaIntraGSM += Chart.basicLineNew("chartIntraGSM", "Biểu đồ Intra - MSC GSM (%)","%", categories, seriesIntraGSM);
				chartAreaIntraU2G += Chart.basicLineNew("chartIntraU2G", "Biểu đồ Intra - MSC U2G (%)","%", categories, seriesIntraU2G);
				
				model.addAttribute("chartdivInterGSM", chartAreaInterGSM);
				model.addAttribute("chartdivInterU2G", chartAreaInterU2G);
				model.addAttribute("chartdivIntraGSM", chartAreaIntraGSM);
				model.addAttribute("chartdivIntraU2G", chartAreaIntraU2G);
				
				
				return "dyMscRetainabilityList";
			}	
}
