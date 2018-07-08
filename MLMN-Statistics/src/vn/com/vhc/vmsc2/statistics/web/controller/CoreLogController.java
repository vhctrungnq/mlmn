package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.CoreLogDAO;
import vn.com.vhc.vmsc2.statistics.domain.CoreLog;
import vn.com.vhc.vmsc2.statistics.web.filter.TimeLog;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;


@Controller
public class CoreLogController extends BaseController {

	@Autowired
	private CoreLogDAO coreLogDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@RequestMapping("/log/coreService")
	public ModelAndView filter(@ModelAttribute("filter") TimeLog filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model, HttpServletRequest request){
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("coreLog").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		
		filter.setStartRecord(startRecord);
		filter.setEndRecord(endRecord);

		List<CoreLog> coreLogs=null;
		Integer totalSize = null;
		
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				totalSize = null;
				coreLogs = null;
		}
		else
		{
			if (startDate == null ||startDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false) {
				Calendar cal = Calendar.getInstance();	
				if(endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==true)
				{
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
				else
				{
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
				startDate = df_full.format(cal.getTime());
			}
			filter.setStartDate(startDate);
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
			filter.setEndDate(endDate);	
			coreLogs = coreLogDao.filter(filter);
			totalSize = coreLogDao.countRow(filter);
		}
		
		model.addAttribute("coreLogList", coreLogs);
		model.addAttribute("totalSize", totalSize);		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return new ModelAndView("coreLogList");
	}
}
