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

import vn.com.vhc.vmsc2.statistics.dao.SummaryLog3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.SummaryLogCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.SummaryLogDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysParameterDAO;
import vn.com.vhc.vmsc2.statistics.domain.SummaryLog;
import vn.com.vhc.vmsc2.statistics.domain.SummaryLog3g;
import vn.com.vhc.vmsc2.statistics.domain.SummaryLogCore;
import vn.com.vhc.vmsc2.statistics.domain.SysParameter;
import vn.com.vhc.vmsc2.statistics.web.filter.SummaryLogFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;


@Controller
public class SummaryLogController extends BaseController {
	
	@Autowired
	private SummaryLogDAO summaryLogDao;
	@Autowired
	private SysParameterDAO sysParameterDAO;
	@Autowired
	private SummaryLog3gDAO summaryLog3gDao;
	@Autowired
	private SummaryLogCoreDAO summaryLogCoreDao;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Autowired
	public void setSummaryLogDao(SummaryLogDAO summaryLogDao) {
		this.summaryLogDao = summaryLogDao;
	}
	@Autowired
	public void setSummaryLog3gDAO(SummaryLog3gDAO summaryLog3gDao) {
		this.summaryLog3gDao = summaryLog3gDao;
	}
	@Autowired
	public void setSummaryLogCoreDAO(SummaryLogCoreDAO summaryLogCoreDao) {
		this.summaryLogCoreDao = summaryLogCoreDao;
	}

	@RequestMapping("/log/summary")
	public String filter(@ModelAttribute("filter") SummaryLogFilter filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate,@RequestParam(required = false) String logs, Model model, HttpServletRequest request) {
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("summaryLog").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;

		filter.setStartRecord(startRecord);
		filter.setEndRecord(endRecord);
		
		if(logs==null){
			logs="";
		}
		
		Integer totalSize = null;
		List<SummaryLog> summaryLogs=null;
		List<SummaryLog3g> summaryLogs1=null;
		List<SummaryLogCore> summaryLogs2=null;
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				totalSize = null;
				summaryLogs = null;
				model.addAttribute("summaryLogList", summaryLogs);
		}else{
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
			
			summaryLogs = summaryLogDao.filter(filter);
			model.addAttribute("summaryLogList", summaryLogs);
			
			if(logs.equals("3G") == true){
				summaryLogs1 = summaryLog3gDao.filter(filter);
				model.addAttribute("summaryLogList", summaryLogs1);
			}
			else if(logs.equals("Core") == true){
				summaryLogs2 = summaryLogCoreDao.filter(filter);
				model.addAttribute("summaryLogList", summaryLogs2);
			}
			else
			{
				List<SummaryLog> summaryLogs3 = summaryLogDao.filter(filter);
				model.addAttribute("summaryLogList", summaryLogs3);
			}
			totalSize = summaryLogDao.countRow(filter);
		}
		List<SysParameter> summaryType = sysParameterDAO.filter("SUMMARY_LOG");
		model.addAttribute("summaryTypeList",summaryType);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);			
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("logs", logs);
		
		return "summaryLogList";
	}
}
