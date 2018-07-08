package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlHandingLibrary;
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.SmsContents;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.AlAlarmTypesDAO;
import dao.AlHandingLibraryDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;
import dao.SmsContentsDAO;

/*
 * Created by: AnhCTV
 * Created date: 13/11/2013
*/
@Controller
@RequestMapping("/sendSMS/*")
public class SMSContentController  extends BaseController {

			@Autowired
			private SYS_PARAMETERDAO sysParameterDao;
			
			@Autowired
			private SmsContentsDAO smsContentsDAO;
			
			@Autowired
			private CTableConfigDAO cTableConfigDAO;
			
			@Autowired
			private AlAlarmTypesDAO alAlarmTypesDAO;
			
			@Autowired
			private CHighlightConfigsDAO cHighlightConfigsDAO;
			
			private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			/*List danh sach canh bao history 2G,3G
			 * @param sdate: Thoi gian bat dau
			 * @param edate: Thoi gian ket thuc
			 * function : detail/total
			 */
			@RequestMapping(value = "{function}")
			public ModelAndView list(
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String username,
					@RequestParam(required = false) String isdn,
					@RequestParam(required = false) String alarmType,
					@PathVariable String function,
					Model model, HttpServletRequest request) {
				
				List<SYS_PARAMETER> sysParemeterTitle = smsContentsDAO.titleForm(function,null);
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("title", sysParemeterTitle.get(0).getValue());
				}
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				if (sdate == null || sdate.equals("")
						||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdate)==false))
				{
					sdate = df.format(cal.getTime())+" "+"00:00";
				}
				
				if (edate == null || edate.equals("")
						||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", edate)==false))
				{
					edate = df.format(cal.getTime())+" "+"23:59";
				}
				model.addAttribute("sdate", sdate);			
				model.addAttribute("edate", edate);
				sdate = sdate+":00";
				edate = edate+":00";
				if (username!=null)
				{
					username=username.trim();
				}
				else
				{
					username="";
				}

				if (isdn!=null)
				{
					isdn=isdn.trim();
				}
				else
				{
					isdn="";
				}

				if (alarmType!=null)
				{
					alarmType=alarmType.trim();
				}
				else
				{
					alarmType="";
				}

				String tableName="SMS_CONTENTS";
				if (function.equals("total"))
				{
					tableName="SMS_CONTENTS_TOTAL";
				}
				//do du lieu ra grid
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
				//Lay du lieu cac cot an hien cua grid 
				List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tableName);
				//url
				String url = "dataSMS.htm?sdate="+sdate+"&edate="+edate+"&username="+username+"&isdn="+isdn+"&alarmType="+alarmType+"&function="+function;
				//Grid
				if (function.equals("total"))
				{
					String highligh="     var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
					highligh += "       	return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
					highligh += "         };";
					String gridReport = HelpTableConfigs.getGridPagingReportUrl(configList, "gridSMS", url, null, null, "Menu", highligh,"singlecell",null,null);
					model.addAttribute("gridSMS", gridReport);	
				}
				else
				{
					String gridSMS = HelpTableConfigs.getGridPagingUrl(configList, "gridSMS", url, "listSMS", listSource, "Menu", null, "multiplerowsextended","Y");
					model.addAttribute("gridSMS", gridSMS);	
				}		
				// Lay ten file export
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
				String dateNow = formatter.format(currentDate.getTime());
				String exportFileName = "SMS_CONTENTS_"+ dateNow;
				model.addAttribute("exportFileName", exportFileName);
			
				model.addAttribute("function", function);
				model.addAttribute("username", username);
				model.addAttribute("isdn", isdn);
				model.addAttribute("alarmType", alarmType);

				
				return new ModelAndView("jspalarm/GSHeThong/smsContent");
			}
			
			@RequestMapping("dataSMS")
			public @ResponseBody 
			void dataSuccessList(@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String username,
					@RequestParam(required = false) String isdn,
					@RequestParam(required = false) String alarmType,
					@RequestParam(required = false) String function,
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				List<SmsContents> contentList = new ArrayList<SmsContents>();
				if (function.equals("total"))
				{
					contentList = smsContentsDAO.getSMSContentTotal(sdate, edate);
				}
				else
				{
					contentList = smsContentsDAO.getSMSContentDetail(sdate, edate,username,isdn,alarmType);
				}
				Gson gs = new Gson();
				String jsonCartList = gs.toJson(contentList);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.println(jsonCartList);
				out.close();
			 }
			
			//Xoa
			@RequestMapping(value = "delete", method = RequestMethod.GET)
			public String onDelete(@RequestParam (required = true) String idList,
					HttpServletRequest request, Model model) {
				try {
					String[] idArray = idList.split(",");
					for (String id : idArray) {
						System.out.println(id);
						smsContentsDAO.deleteByPrimaryKey(Integer.parseInt(id));
					}
					saveMessageKey(request, "messsage.confirm.deletesuccess");
				}
				catch (Exception e) {
					saveMessageKey(request, "messsage.confirm.deletefailure");
				}
				return "redirect:list.htm";
			}
			
			

}
