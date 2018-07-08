package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysParameterDAO;
import vn.com.vhc.vmsc2.statistics.dao.SystemConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.TaiThietBiDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.SysParameter;
import vn.com.vhc.vmsc2.statistics.domain.SystemConfig;
import vn.com.vhc.vmsc2.statistics.domain.TaiThietBi;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTools;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.NumberTools; 

/**
 * @author galaxy
 * @createDate 9:17:38 AM
 * TaiThietBiController.java
 *
 */
@Controller
@RequestMapping("/report/taithietbi/*")
public class TaiThietBiController extends BaseController {
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Autowired
	private TaiThietBiDAO taithietbiDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private SysParameterDAO sysParamterDao;
	@Autowired
	private SystemConfigDAO systemConfigsDAO;
	
	@RequestMapping("hr")
	public ModelAndView hrList(
			@RequestParam(required = false) String type, 
			@RequestParam(required = false) String sDate, 
			@RequestParam(required = false) String eDate,
			@RequestParam(required = false) String sHour, 
			@RequestParam(required = false) String eHour, 
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot, 
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String nguongtren,
			@RequestParam(required = false) String nguongduoi,
			ModelMap model, HttpServletRequest request) { 
		
		String title = "";
		if(sDate == null) sDate = df.format(new Date());
		if(eDate == null) eDate = df.format(new Date());
		if(sHour == null) sHour = "0";
		if(eHour == null) eHour = "23";
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức giờ";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức giờ";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức giờ";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(DateTools.isValid("dd/MM/yyyy", sDate) || DateTools.isValid("dd/MM/yyyy", eDate)
				|| NumberTools.checkNumber(sHour) || NumberTools.checkNumber(eHour)){
			try {
				dataList = taithietbiDao.filterHr(sDate, eDate, sHour, eHour, neid, slot, region, 
						nguongtren, nguongduoi, order, column);
			} catch (Exception e) {
				System.out.println(e);
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION"); 
		
		model.addAttribute("type",type);
		model.addAttribute("regionList",regionList);
		model.addAttribute("sDate",sDate);
		model.addAttribute("eDate", eDate);
		model.addAttribute("sHour", sHour);
		model.addAttribute("eHour", eHour);
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("region", region); 
		model.addAttribute("nguongtren", nguongtren); 
		model.addAttribute("nguongduoi", nguongduoi); 
		model.addAttribute("title", title); 
		model.addAttribute("dataList", dataList);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_gio";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/hrList");
	}
	
	@RequestMapping("dy")
	public ModelAndView dyList(
			@RequestParam(required = false) String sDate, 
			@RequestParam(required = false) String eDate, 
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot,  
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String nguongtren,
			@RequestParam(required = false) String nguongduoi,
			ModelMap model, HttpServletRequest request) { 
		
		String title="";
		if(sDate == null) sDate = df.format(new Date());
		if(eDate == null) eDate = df.format(new Date()); 
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức ngày";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức ngày";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức ngày";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}

		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION"); 
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(DateTools.isValid("dd/MM/yyyy", sDate) || DateTools.isValid("dd/MM/yyyy", eDate)){
			try {
				dataList = taithietbiDao.filterDy(sDate, eDate, neid, slot,region,nguongtren,nguongduoi, order, column);
			} catch (Exception e) {
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		model.addAttribute("sDate",sDate);
		model.addAttribute("eDate", eDate); 
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("dataList", dataList);
		model.addAttribute("regionList",regionList);
		model.addAttribute("title",title);
		model.addAttribute("region",region);
		model.addAttribute("type",type);
		model.addAttribute("nguongtren",nguongtren);
		model.addAttribute("nguongduoi",nguongduoi);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_ngay";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/dyList");
	}
	
	@RequestMapping("wk")
	public ModelAndView wkList(
			@RequestParam(required = false) String sYear, 
			@RequestParam(required = false) String eYear,
			@RequestParam(required = false) String sWeek, 
			@RequestParam(required = false) String eWeek, 
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot,  
			@RequestParam(required = false) String type, 
			@RequestParam(required = false) String nguongtren, 
			@RequestParam(required = false) String nguongduoi, 
			@RequestParam(required = false) String region, 
			ModelMap model, HttpServletRequest request) { 
		
		String title="";
		Calendar cal = Calendar.getInstance();
		int yearnow = cal.get(Calendar.YEAR);
		int weeknow = cal.get(Calendar.WEEK_OF_YEAR);
		
		if(sYear == null) sYear = String.valueOf(yearnow);
		if(eYear == null) eYear = String.valueOf(yearnow);
		if(sWeek == null) sWeek = String.valueOf(weeknow);
		if(eWeek == null) eWeek = String.valueOf(weeknow);
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức tuần";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức tuần";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức tuần";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(NumberTools.checkNumber(sYear) || NumberTools.checkNumber(eYear)
				|| NumberTools.checkNumber(sWeek) || NumberTools.checkNumber(eWeek)){
			try {
				dataList = taithietbiDao.filterWk(sYear, eYear, sWeek, eWeek, neid, slot, region, nguongtren, nguongduoi, order, column);
			} catch (Exception e) {
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION");
		model.addAttribute("title",title);
		model.addAttribute("region",region);
		model.addAttribute("type",type);
		model.addAttribute("nguongtren",nguongtren);
		model.addAttribute("nguongduoi",nguongduoi);
		model.addAttribute("regionList",regionList);
		
		model.addAttribute("sYear",sYear);
		model.addAttribute("eYear", eYear);
		model.addAttribute("sWeek", sWeek);
		model.addAttribute("eWeek", eWeek);
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("dataList", dataList);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_tuan";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/wkList");
	}
	
	@RequestMapping("mn")
	public ModelAndView mnList(
			@RequestParam(required = false) String sYear, 
			@RequestParam(required = false) String eYear,
			@RequestParam(required = false) String sMonth, 
			@RequestParam(required = false) String eMonth, 
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot,  
			@RequestParam(required = false) String type,  
			@RequestParam(required = false) String region,  
			@RequestParam(required = false) String nguongtren,  
			@RequestParam(required = false) String nguongduoi,  
			ModelMap model, HttpServletRequest request) { 
		
		String title="";
		Calendar cal = Calendar.getInstance();
		int yearnow = cal.get(Calendar.YEAR);
		int monthnow = cal.get(Calendar.MONTH);
		
		if(sYear == null) sYear = String.valueOf(yearnow);
		if(eYear == null) eYear = String.valueOf(yearnow);
		if(sMonth == null) sMonth = String.valueOf(monthnow);
		if(eMonth == null) eMonth = String.valueOf(monthnow);
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức tháng";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức tháng";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức tháng";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(NumberTools.checkNumber(sYear) || NumberTools.checkNumber(eYear)
				|| NumberTools.checkNumber(sMonth) || NumberTools.checkNumber(eMonth)){
			try {
				dataList = taithietbiDao.filterMn(sYear, eYear, sMonth, eMonth, neid, slot, region, nguongtren, nguongduoi, order, column);
			} catch (Exception e) {
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION");
		model.addAttribute("title",title);
		model.addAttribute("region",region);
		model.addAttribute("type",type);
		model.addAttribute("nguongtren",nguongtren);
		model.addAttribute("nguongduoi",nguongduoi);
		model.addAttribute("regionList",regionList);
		
		model.addAttribute("sYear",sYear);
		model.addAttribute("eYear", eYear);
		model.addAttribute("sMonth", sMonth);
		model.addAttribute("eMonth", eMonth);
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("dataList", dataList);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_thang";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/mnList");
	}
	
	@RequestMapping("qr")
	public ModelAndView qrList(
			@RequestParam(required = false) String sYear, 
			@RequestParam(required = false) String eYear,
			@RequestParam(required = false) String sQuarter, 
			@RequestParam(required = false) String eQuarter, 
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot,  
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String nguongtren,
			@RequestParam(required = false) String nguongduoi,
			ModelMap model, HttpServletRequest request) { 
		
		String title="";
		Calendar cal = Calendar.getInstance();
		int yearnow = cal.get(Calendar.YEAR);
		int quarternow = DateTools.getQuarterByMonth(cal.get(Calendar.MONTH));
		
		if(sYear == null) sYear = String.valueOf(yearnow);
		if(eYear == null) eYear = String.valueOf(yearnow);
		if(sQuarter == null) sQuarter = String.valueOf(quarternow);
		if(eQuarter == null) eQuarter = String.valueOf(quarternow);
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức quý";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức quý";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức quý";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(NumberTools.checkNumber(sYear) || NumberTools.checkNumber(eYear)
				|| NumberTools.checkNumber(sQuarter) || NumberTools.checkNumber(eQuarter)){
			try {
				dataList = taithietbiDao.filterQr(sYear, eYear, sQuarter, eQuarter, neid, slot, region, nguongtren, nguongduoi, order, column);
			} catch (Exception e) {
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION");
		model.addAttribute("title",title);
		model.addAttribute("region",region);
		model.addAttribute("type",type);
		model.addAttribute("nguongtren",nguongtren);
		model.addAttribute("nguongduoi",nguongduoi);
		model.addAttribute("regionList",regionList);
		
		model.addAttribute("sYear",sYear);
		model.addAttribute("eYear", eYear);
		model.addAttribute("sQuarter", sQuarter);
		model.addAttribute("eQuarter", eQuarter);
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("dataList", dataList);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_quy";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/qrList");
	}
	
	@RequestMapping("yr")
	public ModelAndView yrList(
			@RequestParam(required = false) String sYear, 
			@RequestParam(required = false) String eYear,  
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot,  
			@RequestParam(required = false) String type, 
			@RequestParam(required = false) String region, 
			@RequestParam(required = false) String nguongtren, 
			@RequestParam(required = false) String nguongduoi, 
			ModelMap model, HttpServletRequest request) { 
		
		String title="";
		Calendar cal = Calendar.getInstance();
		int yearnow = cal.get(Calendar.YEAR);
		
		if(sYear == null) sYear = String.valueOf(yearnow);
		if(eYear == null) eYear = String.valueOf(yearnow);
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức năm";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức năm";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức năm";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(NumberTools.checkNumber(sYear) || NumberTools.checkNumber(eYear)){
			try {
				dataList = taithietbiDao.filterYr(sYear, eYear, neid, slot, region, nguongtren, nguongduoi, order, column);
			} catch (Exception e) {
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION");
		model.addAttribute("title",title);
		model.addAttribute("region",region);
		model.addAttribute("type",type);
		model.addAttribute("nguongtren",nguongtren);
		model.addAttribute("nguongduoi",nguongduoi);
		model.addAttribute("regionList",regionList);
		
		model.addAttribute("sYear",sYear);
		model.addAttribute("eYear", eYear); 
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("dataList", dataList);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_nam";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/yrList");
	}
	
	@RequestMapping("option")
	public ModelAndView optionList(
			@RequestParam(required = false) String sDate, 
			@RequestParam(required = false) String eDate, 
			@RequestParam(required = false) String neid,
			@RequestParam(required = false) String slot,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String nguongtren,
			@RequestParam(required = false) String nguongduoi,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request) { 
		
		String title="";
		if(sDate == null) sDate = df.format(new Date()) + " 00:00";
		if(eDate == null) eDate = dff.format(new Date()); 
		
		SystemConfig nguongtrenList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongtren");
		SystemConfig nguongduoiList = systemConfigsDAO.exitSystemConfig("taithietbi.nguongduoi");		
		
		if(type.equals("tai-cao")){
			title = "Báo cáo thiết bị tải cao mức tùy chọn";
			if(nguongduoi == null) nguongduoi = nguongtrenList.getParamValue();
			nguongtren = "100";
		}else if(type.equals("tai-trungbinh")){
			title = "Báo cáo thiết bị tải trung bình mức tùy chọn";
			if(nguongtren == null) nguongtren = nguongtrenList.getParamValue(); 
			if(nguongduoi == null) nguongduoi = nguongduoiList.getParamValue();
		}else{
			title = "Báo cáo thiết bị tải thấp mức tùy chọn";
			if(nguongtren == null) nguongtren = nguongduoiList.getParamValue();
			nguongduoi="0";
		}
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("TAI_THIET_BI");
		String highlight = Helper.to_mau_link_tai(highlightConfigList, "item");
		model.addAttribute("highlight", highlight);
		
		List<TaiThietBi> dataList = new ArrayList<TaiThietBi>();
		
		if(DateTools.isValid("dd/MM/yyyy HH:mm", sDate) || DateTools.isValid("dd/MM/yyyy HH:mm", eDate)){
			try {
				dataList = taithietbiDao.filterOption(sDate, eDate, neid, slot, region, nguongtren, nguongduoi, order, column);
			} catch (Exception e) {
				dataList = null;
			}
		}else{
			dataList = null;
		}
		
		List<SysParameter> regionList = sysParamterDao.filter("REGION");
		model.addAttribute("title",title);
		model.addAttribute("region",region);
		model.addAttribute("type",type);
		model.addAttribute("nguongtren",nguongtren);
		model.addAttribute("nguongduoi",nguongduoi);
		model.addAttribute("regionList",regionList);
		
		model.addAttribute("sDate",sDate);
		model.addAttribute("eDate", eDate); 
		model.addAttribute("neid", neid);
		model.addAttribute("slot", slot); 
		model.addAttribute("dataList", dataList);
		
		// Lay ten file export 
		String exportFileName = "Bao_cao_taithietbi_muc_tuy_chon";
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("bcTaithietbi/optionList");
	}
}
