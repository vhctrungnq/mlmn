package controller.admin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.RAlarmLogDAO;
import vn.com.vhc.vmsc2.statistics.dao.RpHrCell2g3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.RAlarmLog;
import vn.com.vhc.vmsc2.statistics.domain.RpHrCell2g3g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


/**
 * Chuc nang: Hien thi ban do tram
 * @author BUIQUANG
 * Ngay tao: 12/11/2013
 * Lich su thay doi:
 */

@Controller
@RequestMapping("/map/*")
public class BanDoTramController {

	@Autowired
	private CellDAO cellDao;
	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	@Autowired 
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private RpHrCell2g3gDAO rpHrCell2g3gDAO;
	
	@RequestMapping(value="list")
    public String list(@RequestParam(required = false) String siteid,  ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if(siteid != null && siteid != ""){
			
			// thong tin cua site
			Cell siteInfoList = cellDao.getSiteInfoFilter(siteid);
			model.addAttribute("siteInfoList", siteInfoList);
			
			// Danh sach canh bao
			List<RAlarmLog> rAlarmLogInfoList = rAlarmLogDAO.getRAlarmLogInfoFilter(siteid);
			model.addAttribute("rAlarmLogInfoList", rAlarmLogInfoList);
			
			// To mau dong Total cua KPI
			List<HighlightConfig> highlightConfigKpi = highlightConfigDao.getAlarmHighlightByKey("kpi_total_row");
			model.addAttribute("highlightKPI", Helper.highLightRows(highlightConfigKpi, "item1"));
			
			// To mau serverity
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAlarmHighlightByKey("severity_info");
			model.addAttribute("highlight", Helper.highLightRAlarmLogInfo(highlightConfigList, "item"));
			
			// Danh sach KPI
			List<RpHrCell2g3g> traffic2g3gList = rpHrCell2g3gDAO.getTraffic2g3gInfoFilter(siteid);
			model.addAttribute("traffic2g3gList", traffic2g3gList);
		}
		return "admin/banDoTramList";
	}
}
