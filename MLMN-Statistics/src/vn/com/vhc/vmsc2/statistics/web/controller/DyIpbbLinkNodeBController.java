package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyIpbbLinkNodeBDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyIpbbLinkNodeB;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) by VHCSoft 2016
 * </p>
 * <p>
 * Company: VietNam High Technology Software JSC.
 * </p>
 * <p>
 * Create Date:Jun 20, 2016
 * </p>
 * 
 * @author VHC - Software
 * @version 1.0
 */

@Controller
@RequestMapping("/report/ipbb-nodeb/*")
public class DyIpbbLinkNodeBController  extends BaseController{
	@Autowired
	private DyIpbbLinkNodeBDAO dyIpbbNodeBDao;
	private SimpleDateFormat f = new SimpleDateFormat("ddMMyyyy");
	private SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "dy-list")
	public ModelAndView list(@RequestParam(required = false) String nodeId, 
			@RequestParam(required = false) String tentram, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String nguongtren,
			@RequestParam(required = false) String nguongduoi, @RequestParam(required = false) String tendoitac, Model model) {
		
		if(nguongtren == null) nguongtren = "70";
		if(nguongduoi == null) nguongduoi = "30";
		if(startDate == null) startDate = ff.format(new Date());
		if(endDate == null) endDate = ff.format(new Date());
			
		List<DyIpbbLinkNodeB> dyIpbbNodeBList = dyIpbbNodeBDao.getDataByFilter(nodeId, tentram, startDate, endDate, 
				nguongtren, nguongduoi, tendoitac);
		
		String exportFileName = "dyIpbbNodeB_"+f.format(new Date());
		
		model.addAttribute("dyIpbbNodeBList", dyIpbbNodeBList);
		model.addAttribute("nodeId", nodeId);
		model.addAttribute("tentram", tentram);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("nguongtren", nguongtren);
		model.addAttribute("nguongduoi", nguongduoi);
		model.addAttribute("tendoitac", tendoitac);
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("/ipbbNodeB/dyIpbbNodeBList");
	}
}

