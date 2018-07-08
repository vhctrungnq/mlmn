package controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.dbadatafiles;
import vo.dbafreespace;
import vo.alarm.utils.Helper;

import dao.CHighlightConfigsDAO;
import dao.dbadatafilesDAO;
import dao.dbafreespaceDAO;

@Controller
@RequestMapping("/log/checktablespace/*")
public class ChecktablespaceController extends BaseController{
	@Autowired
	private dbadatafilesDAO datafileDAO;
	@Autowired
	private dbafreespaceDAO freespaceDAO;
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@RequestMapping("detail")
	public ModelAndView show(@RequestParam(required = false) String user,
							HttpServletRequest request,
							Model model) {			
		List<SYS_PARAMETER> sysParemeterTitle = freespaceDAO.titleFormDetail();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "TABLESPACE_NAME";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
	
		if(user == null)
			user = "";
		List<dbafreespace> freespacelist1 =null;
		Integer totalSize = 0;
		/*try
		{*/
			
			freespacelist1 = freespaceDAO.filter(user,startRecord,endRecord,column,order);
			totalSize = freespaceDAO.countRow(user);
			
		/*}
		catch(Exception exp)
		{
			totalSize = 0;
			freespacelist1 = null;
		}*/
		
		List<dbafreespace> freespacelist =  freespaceDAO.getTablespaceName();
		model.addAttribute("freespacelist", freespacelist);
		
		model.addAttribute("freespacelist1", freespacelist1);
		model.addAttribute("user", user);
		model.addAttribute("totalSize", totalSize);	
		model.addAttribute("startRecord", startRecord);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "TableSpaceLog_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		return new ModelAndView("jsp/QuanLyLog/checktablespace");
		}
	
		@RequestMapping("list")
		public ModelAndView list(@RequestParam(required = false) String user,
								HttpServletRequest request,
								Model model) {	
			List<SYS_PARAMETER> sysParemeterTitle = datafileDAO.titleFormList();
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			int order = 0;
			String column = "FILE_NAME";
			try {
				order = Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			
			int startRecord = 0;
			try {
				startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			} catch (NumberFormatException e) {
			}
			int endRecord = startRecord + 100;
		
			if(user == null)
				user = "";
			List<dbadatafiles> datafileloglist =null;
			Integer totalSize = 0;
			/*try
			{*/
				
				datafileloglist = datafileDAO.filter(user,startRecord,endRecord,column,order);
				totalSize = datafileDAO.countRow(user);
				
			/*}
			catch(Exception exp)
			{
				totalSize = 0;
				datafileloglist = null;
			}*/
			
			List<dbafreespace> freespacelist =  freespaceDAO.getTablespaceName();
			model.addAttribute("freespacelist", freespacelist);
			
			model.addAttribute("datafileloglist", datafileloglist);
			model.addAttribute("user", user);
			model.addAttribute("totalSize", totalSize);	
			model.addAttribute("startRecord", startRecord);
			
			List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "user"));
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "DataFileLog_" + dateNow;
			model.addAttribute("exportFileName", exportFileName);
			return new ModelAndView("jsp/QuanLyLog/checktablespacelist");
		}
}
