package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.dbadatafilesDAO;
import vn.com.vhc.vmsc2.statistics.dao.dbafreespaceDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.dbadatafiles;
import vn.com.vhc.vmsc2.statistics.domain.dbafreespace;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/log/checktablespace/*")
public class ChecktablespaceController extends BaseController{
	@Autowired
	private dbadatafilesDAO datafileDAO;
	@Autowired
	private dbafreespaceDAO freespaceDAO;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	@RequestMapping("detail")
	public ModelAndView show(@RequestParam(required = false) String user,
							HttpServletRequest request,
							Model model) {			
		if(user == null)
			user = "";
		List<dbafreespace> freespacelist1 =  freespaceDAO.filter(user);
		List<dbafreespace> freespacelist =  freespaceDAO.getAll();
		
		model.addAttribute("freespacelist1", freespacelist1);
		model.addAttribute("freespacelist", freespacelist);
		model.addAttribute("user", user);
		return new ModelAndView("checktablespace");
		}
	
		@RequestMapping("list")
		public ModelAndView list(@RequestParam(required = false) String user,
								HttpServletRequest request,
								Model model) {			
			if(user == null)
				user = "";
			List<dbadatafiles> datafileloglist =  datafileDAO.filter(user);
			List<dbafreespace> freespacelist =  freespaceDAO.getAll();
			model.addAttribute("freespacelist", freespacelist);
			model.addAttribute("datafileloglist", datafileloglist);
			model.addAttribute("user", user);
			
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "user"));
			return new ModelAndView("checktablespacelist");
		}
}
