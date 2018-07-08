package controller.feedback;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.FbProcess;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;

import dao.FbProcessDAO;

@Controller
@RequestMapping("/feedback/bao-cao-slpa/*")
public class SoLuongPAController {

	@Autowired
 	private FbProcessDAO fbProcessDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value="list")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SLPA_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			startDate = df_full.format(cal.getTime());
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			endDate = df_full.format(cal.getTime());
		}
		
		
		int order = 1;
		String column = "FB_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<FbProcess> slpaList = fbProcessDAO.getFbProcessFilter(startDate, endDate, column, order ==1 ? "ASC" : "DESC");
		model.addAttribute("slpaList", slpaList);
		
		/*Integer totalSize = fbProcessDAO.countRowDaXuLy(startDate, endDate);
 		model.addAttribute("totalSize", totalSize);*/
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		// Ve bieu do
		List<String> _48hList = new ArrayList<String>();
		
		NumberFormat formatterNumber = new DecimalFormat("0.00");
		int slpa1 = fbProcessDAO.countSoLuongPA(startDate, endDate, "NHO_HON_48H");
		int slpa2 = fbProcessDAO.countSoLuongPA(startDate, endDate, "LON_HON_48H");
		Double slpaTrong48h = ((double)slpa1/slpaList.size())*100;
		Double slpaLonHon48h = ((double)slpa2/slpaList.size())*100;	
		
		_48hList.add("Số lượng phản ánh trong 48h-" + slpa1 + "-" + formatterNumber.format(slpaTrong48h).toString());
		_48hList.add("Số lượng phản ánh vượt quá 48h-" + slpa2 + "-" + formatterNumber.format(slpaLonHon48h).toString());
		
		Map<String, List<String>> cssrSeries = new LinkedHashMap<String, List<String>>();
		cssrSeries.put("cssrSeries", _48hList);
		
		model.addAttribute("slpaChart", Chart.pieChartData("slpaChart", "Biểu đồ số lượng phản ánh", cssrSeries));
		
		return "jspfeedback/slpaList";
	}
}
