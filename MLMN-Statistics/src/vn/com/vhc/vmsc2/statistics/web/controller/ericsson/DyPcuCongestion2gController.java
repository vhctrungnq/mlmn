package vn.com.vhc.vmsc2.statistics.web.controller.ericsson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscGprsDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscGprsDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscGprs;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.HrBscGprs;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

/**
 * Ten file: DyPcuCongestion2gController.java
 * Muc dich: Hien thi bao cho 2g ericsson tai pcu
 * @author QBu
 * Ngay tao: 23/07/2013
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/report/radio/bsc/ericsson/pcu-cong/*")
public class DyPcuCongestion2gController {

	@Autowired
	private HrBscGprsDAO HrBscGprsDao;
	@Autowired
	private DyBscGprsDAO DyBscGprsDao;
	@Autowired
	private BscDAO BscDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @RequestParam(required = false) String bsc, 
					   @RequestParam(required = false) String shour, 
					   @RequestParam(required = false) String ehour, 
					   @PathVariable String function, 
					   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "PcuCongestion2g_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		// Ngay thang
		if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate) == false) {

			startDate = df.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));

		}
		if (endDate == null || endDate.equals("") || DateTools.isValid("dd/MM/yyyy", endDate) == false) {
			endDate = startDate;
		}
		
		List<Bsc> bscList = BscDao.selectBsc2g();
		model.addAttribute("bscList", bscList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("PCU_CONG_RATE");
		model.addAttribute("highlight", Helper.highLightEricsson2g(highlightConfigList, "item"));
		
		int order = 1;
		String column = "DAY";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		
		String html = "";
		String script = "";
		if(function.equals("dy"))
		{
			// Hien thi list dy bsc gprs
			List<DyBscGprs> dyBscGprsList = DyBscGprsDao.getDyBscGprsFilter(bsc, startDate, endDate, column, order==1? "ASC" : "DESC");
			model.addAttribute("dyBscGprsList", dyBscGprsList);
			
			//List<Bsc> deptList = BscDao.getDeptHBsc(); // Dept gồm VT ĐÔNG, VT TÂY	
			
			List<String> categories = new ArrayList<String>();	
			List<DyBscGprs> dayList = DyBscGprsDao.getDyDayBscGprs(endDate);
			for(int i=0;i<dayList.size();i++){
				categories.add(df.format(dayList.get(i).getDay()));			
			}
			
			String[] dept = {"VT ĐÔNG", "VT TÂY"};
			String[] deptEng = {"VTDONG", "VTTAY"};
			
			for(int i=0;i<dept.length;i++){
				
				html += "<tr><td><div id=\"" + "chartdiv" + deptEng[i] + "\" style=\"width: 98%; height: 450px; margin: 1em auto;\"></div></td></tr>";
				
				List<Bsc> bscByDeptList = BscDao.getHBscByDept(dept[i]); // Lay BSC theo DEPT trong bảng H_BSC
				Map<String, List<Float>> dlDataSeries = new LinkedHashMap<String, List<Float>>();
				for(int j=0;j<bscByDeptList.size();j++){
					List<DyBscGprs> cploadRateList = DyBscGprsDao.getPcuCongRateByBscid(bscByDeptList.get(j).getBscid(), endDate);
					List<Float> dlDataList = new ArrayList<Float>();
					for(int k=0;k<cploadRateList.size();k++){
						dlDataList.add(cploadRateList.get(k).getPcuCongRate());
					}
					dlDataSeries.put(bscByDeptList.get(j).getBscid(), dlDataList);
				}
				script += Chart.basicLine("chartdiv" + deptEng[i], dept[i], "PCU Congestion (%)", categories, dlDataSeries);
			}
			
		}
		else{
			
			String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			model.addAttribute("hourList", hourList);
			if(shour == null)
				shour = "0:00";
			if(ehour == null)
				ehour = "23:00";
			
			List<HrBscGprs> hrBscGprsList = HrBscGprsDao.getHrBscGprsFilter(bsc, startDate, endDate, shour, ehour, column, order==1? "ASC" : "DESC");
			model.addAttribute("hrBscGprsList", hrBscGprsList);
			
		}
		
		model.addAttribute("chartdivScript", script);
		model.addAttribute("html", html);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("shourCBB", shour);
		model.addAttribute("ehourCBB", ehour);
		model.addAttribute("function", function);
		model.addAttribute("bscCBB", bsc);
		
		return "aEricsson/dyBscPcuCongestion";
	}
	
}
