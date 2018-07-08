
package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCell;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;
/**
 * 
 * @author Mr. ThangPX
 * @return Quality Network Report 11/06/2013
 */
@Controller
@RequestMapping("/report/radio/quality/*")
public class QualityNetworkController extends BaseController{

	@Autowired
	private VRpHrCellDAO  vRpHrCellDAO ;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	//Quality Network Report for currentdate
	@RequestMapping("{function}")
	public ModelAndView hrList(
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, 
			@RequestParam(required = false) Date startDate,
			@PathVariable String function,
			ModelMap model, HttpServletRequest request)
			{
		//SetDate = currentdate
			long currentTime = System.currentTimeMillis();		
			
			if (startDate == null) {
				startDate = new Date(currentTime);
			}
			String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
				
			
				// Highlight some important index
				List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
				model.addAttribute("highlight", Helper.highLight(highlightConfigList, "hrCellList"));
				// Get Bscid bind to combobox
				List<Bsc> bscList = bscDao.getAll();
				bscid = ModelAddtributes.checkBscid(model, bscList, bscid,"");
				if(cellid == null)
					cellid = "";
				
				model.addAttribute("cellid", cellid);
				List<VRpHrCell> hrCellList = vRpHrCellDAO.filterQualityNetwork(function, bscid,cellid,s[0],s[1],s[2]);
				model.addAttribute("hrCellList", hrCellList);
				model.addAttribute("function", function);
				model.addAttribute("title", function.toUpperCase());
				return new ModelAndView("hrCellQualityNetwork");
			}	
}
