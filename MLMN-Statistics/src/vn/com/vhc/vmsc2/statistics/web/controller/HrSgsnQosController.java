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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.SgsnDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrSgsnQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrSgsnQos;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;



@Controller
@RequestMapping("/report/core/sgsn/hr/*")
public class HrSgsnQosController extends BaseController {
	@Autowired
	private VRpHrSgsnQosDAO vRpHrSgsnQosDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private SgsnDAO sgsnDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,
			@RequestParam(required = false) String sgsnid, 
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(region == null){
			region = "";
		}
		if(sgsnid == null){
			sgsnid = "";
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
		List<VRpHrSgsnQos> HrSgsnQos = vRpHrSgsnQosDAO.filter(startHour,startDate,endHour,endDate,region,sgsnid);
		
		if(endDate.compareTo(startDate) < 0)
		{
			startDate = new Date();
			HrSgsnQos = vRpHrSgsnQosDAO.filter2(startHour,startDate,endHour,region,sgsnid);
		}
		else if(endDate.compareTo(startDate)== 0)
		{
			
			HrSgsnQos = vRpHrSgsnQosDAO.filter2(startHour,startDate,endHour,region,sgsnid);
		}
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		List<Sgsn> sgsnList = sgsnDao.getAllSGSN();
		model.addAttribute("sgsnList", sgsnList);
		model.addAttribute("region", region);
		model.addAttribute("sgsnid", sgsnid);
		model.addAttribute("HrSgsnQos", HrSgsnQos);
		model.addAttribute("hourList", hourList);

		/* Chart Start 
		List<String> categories = new ArrayList<String>();
		List<Float> speechCssrList = new ArrayList<Float>();
		List<Float> cs64CssrList = new ArrayList<Float>();
		List<Float> psr99CssrList = new ArrayList<Float>();
		List<Float> hsupaCssrList = new ArrayList<Float>();
		List<Float> hsdpaCssrList = new ArrayList<Float>();
		List<Float> speechDrprList = new ArrayList<Float>();
		List<Float> cs64DrprList = new ArrayList<Float>();
		List<Float> psr99DrprList = new ArrayList<Float>();
		List<Float> hsupaDrprList = new ArrayList<Float>();
		List<Float> hsdpaDrprList = new ArrayList<Float>();
		for (VRpHrBsc3G vRpHrBsc : vRpHrBscList) {
			categories.add(vRpHrBsc.getHour()+":00 "+ dff.format(vRpHrBsc.getDay()));
			speechCssrList.add(vRpHrBsc.getSpeechCssr());
			cs64CssrList.add(vRpHrBsc.getCs64Cssr());
			psr99CssrList.add(vRpHrBsc.getPsr99Cssr());
			hsupaCssrList.add(vRpHrBsc.getHsupaCssr());
			hsdpaCssrList.add(vRpHrBsc.getHsdpaCssr());
			speechDrprList.add(vRpHrBsc.getSpeechDrpr());
			cs64DrprList.add(vRpHrBsc.getCs64Drpr());
			psr99DrprList.add(vRpHrBsc.getPsr99Drpr());
			hsupaDrprList.add(vRpHrBsc.getHsupaDrpr());
			hsdpaDrprList.add(vRpHrBsc.getHsdpaDrpr());
		}

		Map<String, List<Float>> speechCssrSeries = new LinkedHashMap<String, List<Float>>();
		speechCssrSeries.put("Speech CSSR (%)--4572a7", speechCssrList);
		model.addAttribute("speechCssrChart", Chart.multiColumn("speechCssrChart", "Speech CSSR (%)", categories, speechCssrSeries));
		
		Map<String, List<Float>> cs64CssrSeries = new LinkedHashMap<String, List<Float>>();
		cs64CssrSeries.put("CS64 CSSR (%)--aa4643", cs64CssrList);
		model.addAttribute("cs64CssrChart", Chart.multiColumn("cs64CssrChart", "CS64 CSSR (%)", categories, cs64CssrSeries));

		Map<String, List<Float>> psr99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psr99CssrSeries.put("PS R99 CSSR (%)--89a54e", psr99CssrList);
		model.addAttribute("psr99CssrChart", Chart.multiColumn("psr99CssrChart", "PS R99 CSSR (%)", categories, psr99CssrSeries));

		Map<String, List<Float>> hsupaCssrSeries = new LinkedHashMap<String, List<Float>>();
		hsupaCssrSeries.put("PS HSUPA CSSR (%)--80699b", hsupaCssrList);
		model.addAttribute("hsupaCssrChart", Chart.multiColumn("hsupaCssrChart", "PS HSUPA CSSR (%)", categories, hsupaCssrSeries));

		Map<String, List<Float>> hsdpaCssrSeries = new LinkedHashMap<String, List<Float>>();
		hsdpaCssrSeries.put("PS HSDPA CSSR (%)--db843d", hsdpaCssrList);
		model.addAttribute("hsdpaCssrChart", Chart.multiColumn("hsdpaCssrChart", "PS HSDPA CSSR (%)", categories, hsdpaCssrSeries));
		
		Map<String, List<Float>> speechDrprSeries = new LinkedHashMap<String, List<Float>>();
		speechDrprSeries.put("Speech DCR (%)--4572a7", speechDrprList);
		model.addAttribute("speechDrprChart", Chart.multiColumn("speechDrprChart", "Speech DCR (%)", categories, speechDrprSeries));
		
		Map<String, List<Float>> cs64DrprSeries = new LinkedHashMap<String, List<Float>>();
		cs64DrprSeries.put("CS64 DCR (%)--aa4643", cs64DrprList);
		model.addAttribute("cs64DrprChart", Chart.multiColumn("cs64DrprChart", "CS64 DCR (%)", categories, cs64DrprSeries));

		Map<String, List<Float>> psr99DrprSeries = new LinkedHashMap<String, List<Float>>();
		psr99DrprSeries.put("PS R99 DCR (%)--89a54e", psr99DrprList);
		model.addAttribute("psr99DrprChart", Chart.multiColumn("psr99DrprChart", "PS R99 DCR (%)", categories, psr99DrprSeries));

		Map<String, List<Float>> hsupaDrprSeries = new LinkedHashMap<String, List<Float>>();
		hsupaDrprSeries.put("PS HSUPA DCR (%)--80699b", hsupaDrprList);
		model.addAttribute("hsupaDrprChart", Chart.multiColumn("hsupaDrprChart", "PS HSUPA DCR (%)", categories, hsupaDrprSeries));

		Map<String, List<Float>> hsdpaDrprSeries = new LinkedHashMap<String, List<Float>>();
		hsdpaDrprSeries.put("PS HSDPA DCR (%)--db843d", hsdpaDrprList);
		model.addAttribute("hsdpaDrprChart", Chart.multiColumn("hsdpaDrprChart", "PS HSDPA DCR (%)", categories, hsdpaDrprSeries));

		// checkBox
		String[] checkColumns = {"CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME","SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","SPEECH_DROP","SPEECH_DRPR","CS64_DROP","CS64_DRPR","PSR99_DROP","PSR99_DRPR","HSUPA_DROP","HSUPA_DRPR","HSDPA_DROP","HSDPA_DRPR","SPEECH_SHO_SR_OUT","SHO_SR_OUT","SHO_SR_IN","CS_IRAT_HO_SR","PS_IRAT_HO_SR"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrBsc", 9));*/
		
		return "hrSgsnQos";
	}
}
