package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.RawFileDAO;
import vn.com.vhc.vmsc2.statistics.domain.RawFile;
import vn.com.vhc.vmsc2.statistics.web.filter.RawFileFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;


@Controller
public class RawFileController extends BaseController {
	@Autowired
	private RawFileDAO rawFileDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	/*@Autowired
	public void setRawFileDao(RawFileDAO rawFileDao) {
		this.rawFileDao = rawFileDao;
	}*/

	/*@RequestMapping("/log/rawFile")
	public String filter(@ModelAttribute("filter") TimeLog filter, Model model, HttpServletRequest request) {
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("rawFile").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		
		filter.setStartRecord(startRecord);
		filter.setEndRecord(endRecord);
		
		List<RawFile> rawFiles = rawFileDao.filter(filter);
		model.addAttribute("rawFileList", rawFiles);

		Integer totalSize = rawFileDao.countRow(filter);
		model.addAttribute("totalSize", totalSize);
		
		return "rawFileList";
	}*/
	//Cao Anh
	@RequestMapping("/log/rawFile")
		
	public String filter(@ModelAttribute("filter") RawFileFilter filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String rawTable, @RequestParam(required = false) String fileName,
			@RequestParam(required = false) String nodeName, @RequestParam(required = false) String convertFlag,
			@RequestParam(required = false) String importFlag,
			Model model, HttpServletRequest request) {
		
		List<RawFile> rawFileList = null;
		
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy", startDate)==false))
		{
				rawFileList = null;
				model.addAttribute("rawFileList", rawFileList);
		}
		else
		{
			if (startDate == null ||startDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy", startDate)==false) {
				Calendar cal = Calendar.getInstance();	
				if(endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy", endDate)==true)
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
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
			filter.setEndDate(endDate);	
			rawFileList = rawFileDao.filter(filter);
		} 
		model.addAttribute("fileName", fileName);
		model.addAttribute("rawFileList", rawFileList);
		model.addAttribute("nodeName", nodeName);
		model.addAttribute("rawTable", rawTable);
		model.addAttribute("importFlag", importFlag);  
		model.addAttribute("convertFlag", convertFlag);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "rawFileList";
	}
}