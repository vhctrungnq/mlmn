package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.Cell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.SgsnDAO;
import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;


@Controller
@RequestMapping("/ajax/*")
public class AjaxController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;
	@Autowired
	private Cell3GDAO cell3GDao;
	@Autowired
	private MscDAO mscDao;
	@Autowired
	private HlrDAO hlrDao;
	@Autowired
	private StpDAO stpDao;
	@Autowired
	private SgsnDAO sgsnDao;

	@RequestMapping("getBsc")
	public @ResponseBody
	List<String> getBsc(@RequestParam String term) {
		return bscDao.getBscids(term);
	}
	
	@RequestMapping("getBsc3G")
	public @ResponseBody
	List<String> getBsc3G(@RequestParam String term) {
		return bsc3GDao.getBscids3G(term);
	}

	@RequestMapping("getCell")
	public @ResponseBody
	List<String> getCell(@RequestParam String term) {
		return cellDao.getCellids(term);
	}
	
	@RequestMapping("getCell3G")
	public @ResponseBody
	List<String> getCell3G(@RequestParam String term) {
		return cell3GDao.getCellids3G(term);
	}

	@RequestMapping("getCellOfBsc")
	public @ResponseBody
	List<Cell> getCellOfBsc(@RequestParam String bscid) {
		 List<Cell> ls =  cellDao.getCellOfBsc(bscid);
		 return ls;
	}
	
	@RequestMapping("getCellOfBsc3G")
	public @ResponseBody
	List<Cell3G> getCellOfBsc3G(@RequestParam String bscid) {
		return cell3GDao.getCellOfBsc3G(bscid);
	}

	@RequestMapping("getSite")
	public @ResponseBody
	List<String> getSite(@RequestParam String term) {
		return cellDao.getSiteids(term);
	}

	@RequestMapping("getSiteOfBsc")
	public @ResponseBody
	List<String> getSiteOfBsc(@RequestParam String bscid) {
		return cellDao.getSiteOfBsc(bscid);
	}

	@RequestMapping("getMsc")
	public @ResponseBody
	List<String> getMsc(@RequestParam String term) {
		List<String> record =  mscDao.getMscids(term);
		return record;
	}

	@RequestMapping("getHlr")
	public @ResponseBody
	List<String> getHlr(@RequestParam String term) {
		return hlrDao.getHlrids(term);
	}

	@RequestMapping("getStp")
	public @ResponseBody
	List<String> getStp(@RequestParam String term) {
		return stpDao.getStpids(term);
	}
	
	@RequestMapping("getSGSN")
	public @ResponseBody
	List<String> getSGSN(@RequestParam String term) {
		return sgsnDao.getSgsnids(term);
	} 
	
	@RequestMapping("loadTeamByDept")
	public @ResponseBody
	List<MDepartment> loadTeamByDept(@RequestParam String dept) {
		List<MDepartment> record = bscDao.getTeamByDept(dept);
		return record;
	}

	@RequestMapping("loadSubTeamByTeam")
	public @ResponseBody
	List<MDepartment> loadSubTeamByTeam(@RequestParam String dept,@RequestParam String team) {
		List<MDepartment> record = bscDao.getSubTeamByTeam(dept, team);
		return record;
	}
	
	@RequestMapping("getBsc2g3g")
	public @ResponseBody
	List<String> getBsc2g3g(@RequestParam String term) {
		List<String> record = bscDao.getBsc2g3g(term);
		return record;
	}
}
