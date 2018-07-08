package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyMscQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.DyMscQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Diagram;

@Controller
@RequestMapping("/report/core/diagram/")
public class DiagramController extends BaseController {
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private DyMscQosDAO dyMscQosDao;
	private DateFormat df = new SimpleDateFormat("ddMMyyyy");

	@RequestMapping("cscore")
	public ModelAndView cscoreView(@RequestParam(required = false) Date day, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		if (day == null) {
			day = new Date(currentTime - 24 * 60 * 60 * 1000);
		}

		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";
		String fileName = "CSCore" + df.format(day) + ".svg";
		File file = new File(dataDir + "/" + fileName);
		
		if (!file.exists()) {
			List<DyBscCore> dyBscCoreList = dyBscCoreDao.filter(day, "");
			List<DyMscQos> dyMscQosList = dyMscQosDao.filter(day, "");

			String diagram = Diagram.CSCore(dyBscCoreList, dyMscQosList);

			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(dataDir + "/" + fileName));
				out.write(diagram);
				out.close();
			} catch (IOException e) {
			}
		}
		
		model.addAttribute("day", day);
		model.addAttribute("fileName", fileName);
		
		return new ModelAndView("cscoreDiagram");
	}
	
	@RequestMapping("pscore")
	public ModelAndView pscoreView(@RequestParam(required = false) Date day, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		if (day == null) {
			day = new Date(currentTime - 24 * 60 * 60 * 1000);
		}

		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";
		String fileName = "PSCore" + df.format(day) + ".svg";
		File file = new File(dataDir + "/" + fileName);
		
		if (!file.exists()) {
			List<DyBscCore> dyBscCoreList = dyBscCoreDao.filter(day, "");

			String diagram = Diagram.PSCore(dyBscCoreList);

			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(dataDir + "/" + fileName));
				out.write(diagram);
				out.close();
			} catch (IOException e) {
			}
		}
		
		model.addAttribute("day", day);
		model.addAttribute("fileName", fileName);
		
		return new ModelAndView("pscoreDiagram");
	}
}
