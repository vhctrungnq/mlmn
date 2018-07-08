package vn.com.vhc.vmsc2.statistics.web.controller.ericsson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import vn.com.vhc.vmsc2.statistics.dao.DyBscSignalUtilDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscSignalUtilDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscSignalUtil;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.HrBscSignalUtil;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


/**
 * Ten file: DySignallingUtilization2gController.java
 * Muc dich: Hien thi bao cho 2G Ericsson Utility A I/F, báo hiệu HSL
 * @author QBu
 * Ngay tao: 24/07/2013
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/report/radio/bsc/ericsson/signal-util/*")
public class DySignallingUtilization2gController {

	@Autowired
	private DyBscSignalUtilDAO DyBscSignalUtilDao;
	@Autowired
	private HrBscSignalUtilDAO HrBscSignalUtilDao;
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
		String exportFileName = "BscSignalUtil2g_" + dateNow;
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
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("BSC_SIGNAL_UTIL");
		model.addAttribute("highlight", Helper.highLightEricsson2g(highlightConfigList, "item"));
		
		int order = 1;
		String column = "DAY";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		
		if(function.equals("dy"))
		{
			// Hien thi list dy bsc signal util
			List<DyBscSignalUtil> dyBscSignalUtilList = DyBscSignalUtilDao.getDyBscSignalUtilFilter(bsc, startDate, endDate, column, order==1? "ASC" : "DESC");
			model.addAttribute("dyBscSignalUtilList", dyBscSignalUtilList);
				
		}
		else{
			
			String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			model.addAttribute("hourList", hourList);
			if(shour == null)
				shour = "0:00";
			if(ehour == null)
				ehour = "23:00";
			
			List<HrBscSignalUtil> hrBscSignalUtilList = HrBscSignalUtilDao.getHrBscSignalUtilFilter(bsc, startDate, endDate, shour, ehour, column, order==1? "ASC" : "DESC");
			model.addAttribute("hrBscSignalUtilList", hrBscSignalUtilList);
			
		}
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("shourCBB", shour);
		model.addAttribute("ehourCBB", ehour);
		model.addAttribute("function", function);
		model.addAttribute("bscCBB", bsc);
		
		return "aEricsson/dyBscSignalUtil";
	}
}
