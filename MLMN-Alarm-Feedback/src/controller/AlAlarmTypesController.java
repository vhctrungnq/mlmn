package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.AlAlarmTypes;
import vo.CableParameter;
import vo.CableTransmission;
import vo.SYS_PARAMETER;

import dao.AlAlarmTypesDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/alarmTypes")
public class AlAlarmTypesController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@RequestMapping("/list")
	public ModelAndView list(AlAlarmTypes filter,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmTypesDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "VENDOR";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) { }
		
		if (filter.getVendor() != null) {
			filter.setVendor(filter.getVendor().trim());
		}
		if (filter.getNeType() != null) {
			filter.setNeType(filter.getNeType().trim());
		}
		if (filter.getAlarmName() != null) {
			filter.setAlarmName(filter.getAlarmName().trim());
		}
		if (filter.getMeaning() != null) {
			filter.setMeaning(filter.getMeaning().trim());
		}
		if (filter.getAlarmMapingId() != null) {
			filter.setAlarmMapingId(filter.getAlarmMapingId().trim());
		}
		if (filter.getAlarmMapingName() != null) {
			filter.setAlarmMapingName(filter.getAlarmMapingName().trim());
		}
		if (filter.getAlarmType() != null) {
			filter.setAlarmType(filter.getAlarmType().trim());
		}
		List<AlAlarmTypes> alarmTypesList = alAlarmTypesDAO.getAlarmTypeList(filter, column, order);
		
		model.addAttribute("alarmTypesList", alarmTypesList);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AlarmTypes_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jspalarm/cauhinh/alarmTypesList");
		
	}
	@RequestMapping(value="ajax/get-inf-search")
    public @ResponseBody 
    List<String> ajaxGetInfoSearch(@RequestParam(required = false) String focus, @RequestParam(required = false) String term,
    		HttpServletRequest request, HttpServletResponse response) {
		
		List<String> paramList = new ArrayList<String>();
		
		if (focus.equals("vendor") && term.length() < 3) {
			return paramList;
		}
		
		List <String> resultParameter = alAlarmTypesDAO.getAlarmTypeSearch(focus, term);
		for (String item: resultParameter) {
			paramList.add(item);
		}
		
		return paramList;
	}
}
