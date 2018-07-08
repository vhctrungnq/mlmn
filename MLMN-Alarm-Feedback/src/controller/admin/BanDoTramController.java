package controller.admin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.CHighlightConfigsDAO;
import dao.HCellDAO;
import dao.RAlarmLogDAO;
import dao.RpHrSite2g3gDAO;

import vo.CHighlightConfigs;
import vo.HCell;
import vo.RAlarmLog;
import vo.RpHrSite2g3g;
import vo.alarm.utils.Helper;

/**
 * Function        : Hien thi ban do tram ben module ALARM
 * Created By      : BUIQUANG
 * Create Date     : 12/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/map/*")
public class BanDoTramController {

	@Autowired
	private HCellDAO hCellDAO;
	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private RpHrSite2g3gDAO rpHrSite2g3gDAO;
	
	@RequestMapping(value="list")
    public String list(@RequestParam(required = false) String siteid,  ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if(siteid != null && siteid != ""){
			
			// thong tin cua site
			HCell siteInfoList = hCellDAO.getSiteInfoFilter(siteid);
			model.addAttribute("siteInfoList", siteInfoList);
			
			// Danh sach canh bao
			List<RAlarmLog> rAlarmLogInfoList = rAlarmLogDAO.getRAlarmLogInfoFilter(siteid);
			model.addAttribute("rAlarmLogInfoList", rAlarmLogInfoList);
			
			// To mau dong Total cua KPI
			List<CHighlightConfigs> highlightConfigKpi = cHighlightConfigsDAO.getByKey("kpi_total_row");
			model.addAttribute("highlightKPI", Helper.highLightRows(highlightConfigKpi, "item1"));
			
			// To mau serverity
			List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("severity_info");
			model.addAttribute("highlight", Helper.highLightRAlarmLogInfo(highlightConfigList, "item"));
			
			// Danh sach traffic
			List<RpHrSite2g3g> traffic2g3gList = rpHrSite2g3gDAO.getTraffic2g3gInfoFilter(siteid);
			model.addAttribute("traffic2g3gList", traffic2g3gList);
		}
		return "jspadmin/banDoTramList";
	}
}
