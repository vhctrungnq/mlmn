package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HIpbbBwlistDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VDyIpbbLinkDAO;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.IPBBLinkTruyenDan;
import vn.com.vhc.vmsc2.statistics.domain.VDyIpbbLink;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools;

/**
 * @author galaxy
 * @createDate 9:47:06 AM
 * BWLinkTruyenDanController.java
 *
 */

@Controller
@RequestMapping("/report/core/ipbb-link/*")
public class IPBBLinkTruyenDanController extends BaseController{
	
	@Autowired
	private HIpbbBwlistDAO IpbbBwlistDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private VDyIpbbLinkDAO vdylinkDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("dy-link")
	public String dyList(
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model,HttpServletRequest request
			 ) {
		
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(link == null){
			link = "";
		}
		if(direction == null){
			direction = "";
		}
		
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		model.addAttribute("directionList", directionList);
		
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		model.addAttribute("linkList", linkList);
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		  
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "dyList");
		model.addAttribute("highlight", highlight);
		
		List<VDyIpbbLink> dyList = new ArrayList<VDyIpbbLink>();
		
		try {
			dyList = vdylinkDao.filterBadLink(df.format(startDate), df.format(endDate),link,direction);
		} catch (Exception e) {
			dyList = null;
		}
		
		model.addAttribute("dyList", dyList);    
		model.addAttribute("highlight", highlight);
		model.addAttribute("dyList", dyList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction); 
		
		return "bwLinkTruyenDan/dyIpbbLinkList";
	}
	
	@RequestMapping("wk-link")
	public String wkList(
			 @RequestParam(required = false) String startYear,
			 @RequestParam(required = false) String endYear,
			 @RequestParam(required = false) String startWeek,
			 @RequestParam(required = false) String endWeek,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model,HttpServletRequest request
			 ) {
		
		int sYear = 0, eYear = 0, sWeek = 0, eWeek = 0;
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR);
		int weeknow = calender.get(Calendar.WEEK_OF_YEAR);
		
		if(startYear == null || endYear == null){
			sYear = yearnow;
			eYear = yearnow;
			startYear = String.valueOf(yearnow);
			endYear = String.valueOf(yearnow);
		}else{
			try {
				sYear = Integer.parseInt(startYear);
				eYear = Integer.parseInt(endYear);
			} catch (Exception e) {} 
		}
		
		if(startWeek == null || endWeek == null){
			sWeek = weeknow;
			eWeek = weeknow;
			startWeek = String.valueOf(weeknow);
			endWeek = String.valueOf(weeknow);
		}else{
			try {
				sWeek = Integer.parseInt(startWeek);
				eWeek = Integer.parseInt(endWeek);
			} catch (Exception e) {} 
		}

		if(link == null){
			link = "";
		}
		
		if(direction == null){
			direction = "";
		}

		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		List<IPBBLinkTruyenDan> weekList = new ArrayList<IPBBLinkTruyenDan>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startWeek) && NumberTools.checkNumber(endWeek)){
			try {
				weekList = vdylinkDao.filterWeek(sYear, eYear, sWeek, eWeek, link, direction);
			} catch (Exception e) {
				weekList = null;
			}
		}else{
			weekList = null;
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "weekList");
		
		model.addAttribute("highlight", highlight);
		model.addAttribute("weekList", weekList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		
		return "bwLinkTruyenDan/wkIpbbLinkList";
	}
	
	@RequestMapping("mn-link")
	public String mnList(
			 @RequestParam(required = false) String startYear,
			 @RequestParam(required = false) String endYear,
			 @RequestParam(required = false) String startMonth,
			 @RequestParam(required = false) String endMonth,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model,HttpServletRequest request
			 ) {
		
		int sYear = 0, eYear = 0, sMonth = 0, eMonth = 0;
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR);
		int monthnow = calender.get(Calendar.MONTH)+1;
		
		if(startYear == null || endYear == null){
			sYear = yearnow;
			eYear = yearnow;
			startYear = String.valueOf(yearnow);
			endYear = String.valueOf(yearnow);
		}else{
			try {
				sYear = Integer.parseInt(startYear);
				eYear = Integer.parseInt(endYear);
			} catch (Exception e) {} 
		}
		
		if(startMonth == null || endMonth == null){
			sMonth = monthnow;
			eMonth = monthnow;
			startMonth = String.valueOf(monthnow);
			endMonth = String.valueOf(monthnow);
		}else{
			try {
				sMonth = Integer.parseInt(startMonth);
				eMonth = Integer.parseInt(endMonth);
			} catch (Exception e) {} 
		}

		if(link == null){
			link = "";
		}
		
		if(direction == null){
			direction = "";
		}

		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		List<IPBBLinkTruyenDan> monthList = new ArrayList<IPBBLinkTruyenDan>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startMonth) && NumberTools.checkNumber(endMonth)){
			try {
				monthList = vdylinkDao.filterMonth(sYear, eYear, sMonth, eMonth, link, direction);
			} catch (Exception e) {
				monthList = null;
			}
		}else{
			monthList = null;
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "monthList");
		
		model.addAttribute("highlight", highlight);
		model.addAttribute("monthList", monthList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		
		return "bwLinkTruyenDan/mnIpbbLinkList";
	}
	
	@RequestMapping("qr-link")
	public String qrList(
			 @RequestParam(required = false) String startYear,
			 @RequestParam(required = false) String endYear,
			 @RequestParam(required = false) String startQuarter,
			 @RequestParam(required = false) String endQuarter,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model,HttpServletRequest request
			 ) {
		
		int sYear = 0, eYear = 0, sQuarter = 0, eQuarter = 0;
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR);
		int quarternow = DateTools.getQuarterByMonth(calender.get(Calendar.MONTH));
		
		if(startYear == null || endYear == null){
			sYear = yearnow;
			eYear = yearnow;
			startYear = String.valueOf(yearnow);
			endYear = String.valueOf(yearnow);
		}else{
			try {
				sYear = Integer.parseInt(startYear);
				eYear = Integer.parseInt(endYear);
			} catch (Exception e) {} 
		}
		
		if(startQuarter == null || endQuarter == null){
			sQuarter = quarternow;
			eQuarter = quarternow;
			startQuarter = String.valueOf(quarternow);
			endQuarter = String.valueOf(quarternow);
		}else{
			try {
				sQuarter = Integer.parseInt(startQuarter);
				eQuarter = Integer.parseInt(endQuarter);
			} catch (Exception e) {} 
		}

		if(link == null){
			link = "";
		}
		
		if(direction == null){
			direction = "";
		}

		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		List<IPBBLinkTruyenDan> quarterList = new ArrayList<IPBBLinkTruyenDan>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)
				&& NumberTools.checkNumber(startQuarter) && NumberTools.checkNumber(endQuarter)){
			try {
				quarterList = vdylinkDao.filterQuarter(sYear, eYear, sQuarter, eQuarter, link, direction);
			} catch (Exception e) {
				quarterList = null;
			}
		}else{
			quarterList = null;
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "quarterList");
		
		model.addAttribute("highlight", highlight);
		model.addAttribute("quarterList", quarterList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		
		return "bwLinkTruyenDan/qrIpbbLinkList";
	}
	
	@RequestMapping("yr-link")
	public String yrList(
			 @RequestParam(required = false) String startYear,
			 @RequestParam(required = false) String endYear, 
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model,HttpServletRequest request
			 ) {
		
		int sYear = 0, eYear = 0;
		Calendar calender = Calendar.getInstance();
		int yearnow = calender.get(Calendar.YEAR); 
		
		if(startYear == null || endYear == null){
			sYear = yearnow;
			eYear = yearnow;
			startYear = String.valueOf(yearnow);
			endYear = String.valueOf(yearnow);
		}else{
			try {
				sYear = Integer.parseInt(startYear);
				eYear = Integer.parseInt(endYear);
			} catch (Exception e) {} 
		} 

		if(link == null){
			link = "";
		}
		
		if(direction == null){
			direction = "";
		}

		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		List<IPBBLinkTruyenDan> yearList = new ArrayList<IPBBLinkTruyenDan>();
		
		if(NumberTools.checkNumber(startYear) && NumberTools.checkNumber(endYear)){
			try {
				yearList = vdylinkDao.filterYear(sYear, eYear, link, direction);
			} catch (Exception e) {
				yearList = null;
			}
		}else{
			yearList = null;
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "yearList");
		
		model.addAttribute("highlight", highlight);
		model.addAttribute("yearList", yearList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear); 
		
		return "bwLinkTruyenDan/yrIpbbLinkList";
	}
	
	@RequestMapping("option-link")
	public String optionList(
			 @RequestParam(required = false) String startTime,
			 @RequestParam(required = false) String endTime, 
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model,HttpServletRequest request
			 ) {
		 
		long currentTime = System.currentTimeMillis();
		Date yesterday = new Date(currentTime - 24 * 60 * 60 * 1000);
		
		if(startTime == null){
			startTime = df.format(yesterday) + " 00:00";
		}
		
		if(endTime == null){
			endTime = df.format(yesterday) + " 23:59";
		}

		if(link == null){
			link = "";
		}
		
		if(direction == null){
			direction = "";
		}

		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		List<VDyIpbbLink> optionList = new ArrayList<VDyIpbbLink>();
		
		if(DateTools.isValid("dd/MM/yyyy HH:mm", startTime) || DateTools.isValid("dd/MM/yyyy HH:mm", endTime)){
			try {
				optionList = vdylinkDao.filterOption(startTime, endTime, link, direction);
				
				for (VDyIpbbLink i : optionList) {
					i.setSdatetime(startTime);
					i.setEdatetime(endTime);
				}
			} catch (Exception e) {
				System.out.println(e);
				optionList = null;
			}
		}else{
			optionList = null;
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "optionList");
		
		model.addAttribute("highlight", highlight);
		model.addAttribute("optionList", optionList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime); 
		
		return "bwLinkTruyenDan/optIpbbLinkList";
	}
}
