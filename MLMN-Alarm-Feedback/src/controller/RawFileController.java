package controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.RawFile;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTimeUtils;
import dao.RawFileDAO;


@Controller
public class RawFileController extends BaseController {
	@Autowired
	private RawFileDAO rawFileDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");

	//Cao Anh
	@RequestMapping("/log/rawFile")
	public String filter(
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String convertFlag,
			@RequestParam(required = false) String rawTable,
			@RequestParam(required = false) String fileName, 
			@RequestParam(required = false) String nodeName,
			@RequestParam(required = false) String importFlag,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = rawFileDao.titleRawFile(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		int order = 0;
		String column = "DAY";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("rawFile").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("rawFile").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("rawFile").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		
		if(rawTable == null){
			rawTable="";
		}
		else
			rawTable= rawTable.trim();
		
		if(fileName == null){
			fileName="";
		}
		else
			fileName= fileName.trim();
		
		if(nodeName == null){
			nodeName="";
		}
		else
			nodeName= nodeName.trim();
		
		List<RawFile> rawFiles=null;
		Integer totalSize = 0;
		
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy", startDate)==false))
		{
				totalSize = 0;
				rawFiles = null;
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
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
			try
			{
				rawFiles = rawFileDao.filter(startDate,endDate,convertFlag,rawTable,fileName,nodeName,importFlag,startRecord,endRecord,column,order);
				totalSize = rawFileDao.countRow(startDate,endDate,convertFlag,rawTable,fileName,nodeName,importFlag);
			}
			catch (Exception exp)
			{
				totalSize = 0;
				rawFiles = null;
			}
		}
		
		List<SYS_PARAMETER> convertFlagList = rawFileDao.getConvertFlagList();
		model.addAttribute("convertFlagList", convertFlagList);
		
		List<SYS_PARAMETER> importFlagList = rawFileDao.getImportFlagList();
		model.addAttribute("importFlagList", importFlagList);
		
		model.addAttribute("convertFlagList", convertFlagList);
		model.addAttribute("importFlagList", importFlagList);
		model.addAttribute("rawFileList", rawFiles);
		model.addAttribute("fileName", fileName);
		model.addAttribute("nodeName", nodeName);
		model.addAttribute("rawTable", rawTable);
		model.addAttribute("importFlag", importFlag);  
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("convertFlag", convertFlag);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startRecord", startRecord);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "RawFile_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		return "jsp/QuanLyLog/rawFileList";
	}
	
}
