package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.Cell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.SYS_PARAMETERDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.SYS_PARAMETER;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.utils.ExportTools;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

/**
 * Danh sach cell
 * @author BUIQUANG
 * create date:
 * change history: 18/11/2013
 */

/**
 * update user - AnhNT
 * update time - 08/10/2015
 * decription - Them truong thong tin:
 * + thoi gian ung cuu thong tin dung han ban ngay
 * + thoi gian ung cuu thong tin dung han ban dem
 * + thoi gian backup ac quy moi
 * + thoi gian backup acquy cu
 * + thoi diem cap nhat moi
 * + thoi diem cap nhat cu
 * + do chenh lech thoi gian backup ac quy
 * 
 * */
@Controller
@RequestMapping("/network/cell3g/*")
public class Cell3gController extends BaseController {
	@Autowired
	private Bsc3GDAO bsc3gDao;
	@Autowired
	private Cell3GDAO cell3gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private SYS_PARAMETERDAO SysParameterDAO;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<HighlightConfig> highlightConfigList2 = highlightConfigDao.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping("list")
	public ModelAndView list(
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String siteid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@ModelAttribute("filter") Cell3G filter,@RequestParam(required = false) String bsc3gid, ModelMap model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		// begin pageSize
		int pageSize = 100;
		try {
			pageSize = Integer.parseInt((String) RequestContextHolder.currentRequestAttributes().getAttribute("pageSize", RequestAttributes.SCOPE_SESSION));
		} catch (Exception ex) {}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * pageSize;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + pageSize;
						
		int order = 1;
		String column = "BSCID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if(filter.getBscid() != null)
		{
			bscid = bscid.trim();
			filter.setBscid(filter.getBscid().trim());
		}
		if(filter.getSiteid() != null)
		{
			siteid = siteid.trim();
			filter.setSiteid(filter.getSiteid().trim());
		}
		if(filter.getCellid() != null){
			cellid = cellid.trim();
			filter.setCellid(filter.getCellid().trim());
		}
		
		
		List<Cell3G> cellList = cell3gDao.getHCell3GFilter(filter.getBscid(), filter.getSiteid(), filter.getCellid(),
				filter.getVendor(), filter.getProvince(), filter.getDistrict(), startRecord, endRecord, column, order ==1 ? "ASC" : "DESC");
		model.addAttribute("cellList", cellList);

		Integer totalSize = cell3gDao.countHCell3GFilter(filter.getBscid(), filter.getSiteid(), filter.getCellid(),
				filter.getVendor(), filter.getProvince(), filter.getDistrict());
		model.addAttribute("totalSize", totalSize);
		
		loadData(model, filter, "S");
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Cell3G_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("startRecord", startRecord);
		model.addAttribute("bscid", bscid);
		model.addAttribute("siteid", siteid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("vendor", vendor);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		return new ModelAndView("cell3gList");
	}
	
	private void loadData(ModelMap model, Cell3G filter, String conditions){
		List<SYS_PARAMETER> vendorList= SysParameterDAO.getVendorList();
		model.addAttribute("vendorList", vendorList);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		
		List<HProvincesCode> districtList = null;
		if(filter.getProvince() != null)
			districtList = hProvincesCodeDao.getHProvincesCodeList(filter.getProvince());
		else{
			if(conditions != null && conditions.equals("S"))
				districtList = hProvincesCodeDao.getHProvincesCodeList(filter.getProvince());
			else if(provinceList.size() > 0)
				districtList = hProvincesCodeDao.getHProvincesCodeList(provinceList.get(0).getProvince());
		}
		model.addAttribute("districtList", districtList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		List<Bsc3G> bscList = bsc3gDao.getAll();
		model.addAttribute("bscList", bscList);
		
		// Danh sach is ibc, cell blacklist
		List<SYS_PARAMETER> yesNoList= SysParameterDAO.getYesNoList();
		model.addAttribute("yesNoList", yesNoList);
		
		model.addAttribute("vendorCBB", filter.getVendor());
		model.addAttribute("provinceCBB", filter.getProvince());
		model.addAttribute("districtCBB", filter.getDistrict());
		model.addAttribute("regionCBB", filter.getRegion());
		model.addAttribute("bscidCBB", filter.getBscid());
		model.addAttribute("isIbcCBB", filter.getIsIbc());
		model.addAttribute("cellBlacklistCBB", filter.getCellBlacklist());
		model.addAttribute("cellBorder", filter.getCellBorder());
		model.addAttribute("seaCell", filter.getSeaCell());
		model.addAttribute("specialCell", filter.getSpecialCell());
		model.addAttribute("tgUcttdhNgay", filter.getTgUcttdhNgay());
		model.addAttribute("tgUcttdhDem", filter.getTgUcttdhDem());
		model.addAttribute("tgBackupAcquyMoi", filter.getTgBackupAcquyMoi());
		model.addAttribute("tgBackupAcquyCu", filter.getTgBackupAcquyCu());
		model.addAttribute("timeCapnhatMoi", filter.getTimeCapnhatMoi());
		model.addAttribute("timeCapnhatCu", filter.getTimeCapnhatCu());
		model.addAttribute("doLechTgBackupAcquy", filter.getDoLechTgBackupAcquy());
		
		model.addAttribute("mcc", filter.getMcc());
		model.addAttribute("mnc", filter.getMnc());
		model.addAttribute("addressEng", filter.getAddressEng());
		model.addAttribute("azimuth", filter.getAzimuth());
		
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, HttpServletRequest request) {

		cell3gDao.deleteByPrimaryKey(bscid, cellid);
		saveMessageKey(request, "messsage.confirm.deletesuccess");

		return "redirect:list.htm";
	}
	
	private void HCell3gAddEdit(Integer id, ModelMap model)
	{
		if(id != null)
			model.addAttribute("HCell3gAddEdit", "Y");
		else
			model.addAttribute("HCell3gAddEdit", "N");
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, ModelMap model) {
		
		Cell3G cell3g = (bscid==null) ? new Cell3G() : cell3gDao.selectByPrimaryKey(bscid, cellid);
		model.addAttribute("cell3g", cell3g);
		Integer id = cell3g.getId();
		HCell3gAddEdit(id, model);
		
		loadData(model, cell3g, null);
		
		return "cell3gForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) Integer id, 
			@ModelAttribute("cell3g") @Valid Cell3G cell3g, BindingResult result,
			@RequestParam(required = false) String timeCapnhatMoi,
    		@RequestParam(required = false) String timeCapnhatCu,
			HttpServletRequest request, ModelMap model, HttpServletResponse response) {
		if (result.hasErrors())
		{
			
			if(result.hasFieldErrors("longitude"))
				model.addAttribute("longitudeError", "longitudeError");
			if (result.hasFieldErrors("latitude"))
				model.addAttribute("latitudeError", "latitudeError");
			if(result.hasFieldErrors("cid"))
				model.addAttribute("cidError", "cidError");
			if(result.hasFieldErrors("lac"))
				model.addAttribute("lacError", "lacError");
			if(result.hasFieldErrors("bandwidth"))
				model.addAttribute("bandwidthError", "bandwidthError");
			if(result.hasFieldErrors("subscriber"))
				model.addAttribute("subscriberError", "subscriberError");
			HCell3gAddEdit(id, model);
			loadData(model, cell3g, null);
			model.addAttribute("timeCapnhatMoi", timeCapnhatMoi);
			model.addAttribute("timeCapnhatCu", timeCapnhatCu);
			return "cell3gForm";
		}
		if(id ==null)
		{
			if(cell3gDao.checkPrimaryKeyBscCell3g(cell3g.getBscid(), cell3g.getCellid(), null).size() == 0){
				try {
					cell3g.setTimeCapnhatMoi(df.parse(timeCapnhatMoi));
				} catch (java.text.ParseException e) {
					cell3g.setTimeCapnhatMoi(new Date());
				}
				
				try {
					cell3g.setTimeCapnhatCu(df.parse(timeCapnhatMoi));
				} catch (java.text.ParseException e) {
					cell3g.setTimeCapnhatCu(new Date());
				}
				
				if (cell3g.getCellid() != null && !cell3g.getCellid().equals("")) {
					cell3gDao.insert(cell3g);
					saveMessageKey(request, "messsage.confirm.addsuccess");
				} else {
					saveMessageKey(request, "message.error.cellIsNull");
					HCell3gAddEdit(id, model);
					loadData(model, cell3g, null);
					model.addAttribute("timeCapnhatMoi", timeCapnhatMoi);
					model.addAttribute("timeCapnhatCu", timeCapnhatCu);
					return "cellForm";
				}
			}
			else{
				saveMessage(request, "Rncid và Cellid đã tồn tại");
				HCell3gAddEdit(id, model);
				loadData(model, cell3g, null);
				model.addAttribute("timeCapnhatMoi", timeCapnhatMoi);
				model.addAttribute("timeCapnhatCu", timeCapnhatCu);
				return "cell3gForm";
			}
		}
		else{
			if(cell3gDao.checkPrimaryKeyBscCell3g(cell3g.getBscid(), cell3g.getCellid(), String.valueOf(id)).size() == 0){
				try {
					cell3g.setTimeCapnhatMoi(df.parse(timeCapnhatMoi));
				} catch (java.text.ParseException e) {
					cell3g.setTimeCapnhatMoi(new Date());
				}
				
				try {
					cell3g.setTimeCapnhatCu(df.parse(timeCapnhatMoi));
				} catch (java.text.ParseException e) {
					cell3g.setTimeCapnhatCu(new Date());
				}
				
				cell3gDao.updateByPrimaryKeySelective(cell3g);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				saveMessage(request, "Rncid và Cellid đã tồn tại");
				HCell3gAddEdit(id, model);
				loadData(model, cell3g, null);
				model.addAttribute("timeCapnhatMoi", timeCapnhatMoi);
				model.addAttribute("timeCapnhatCu", timeCapnhatCu);
				return "cell3gForm";
			}
		}
		return "redirect:list.htm";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "cell3gUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "cell3gUpload";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		
		List<Cell3G> successList = new ArrayList<Cell3G>();
		List<Cell3G> failedList = new ArrayList<Cell3G>();
		List<Cell3G> success = new ArrayList<Cell3G>();
		
		String bscid;
		String cellid;
		String cellname;
		String vendor;
		String siteid;
		String sitename;
		String type;
		String transType;
		String region;
		String area;
		String province;
		String district;
		String villageName;
		String address;
		String longitude;
		String latitude;
		String cid;
		String lac;
		String vendorTrans;
		String bandwidth;
		String nextHop;
		String subscriber;
		String cellBorder;
		String cellBlacklist;
		String isIbc;
		String batteryDuration;
		String phone;
		String targetUCTT;
		String tgUcttdhNgay, tgUcttdhDem, tgBackupAcquyMoi, tgBackupAcquyCu, timeCapnhatMoi,
		timeCapnhatCu, doLechTgBackupAcquy;
		String description;
		
		String mcc,mnc,addressEng,azimuth;

		String siteDistance;
		String khoangCachTram;
		String bandwidthStr;
		String subscribersInfo;
		String createDate;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 52) {
        		saveMessageKey(request, "sidebar.admin.uploadErrorStructuresNumber");
        		
        		return "cell3gUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		Cell3G cell;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cell = new Cell3G();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=52; j++) {
     					data.add("");
     				}
        			bscid				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			type				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			siteid				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			sitename			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			cellid				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			cellname			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			vendor				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			vendorTrans			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			transType			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			bandwidth			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			nextHop				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			subscriber			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			region				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			area				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			province			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			district			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			villageName			= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			address				= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			longitude			= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			latitude			= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			cid					= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim();
        			lac					= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim();
        			cellBorder			= data.get(22).toString().trim().equals("")?null:data.get(22).toString().trim();
        			cellBlacklist		= data.get(23).toString().trim().equals("")?null:data.get(23).toString().trim();
        			String seaCell		= data.get(24).toString().trim().equals("")?null:data.get(24).toString().trim();
        			String specialCell	= data.get(25).toString().trim().equals("")?null:data.get(25).toString().trim();
        			isIbc				= data.get(26).toString().trim().equals("")?null:data.get(26).toString().trim();
        			String siteLevel	= data.get(27).toString().trim().equals("")?null:data.get(27).toString().trim();
        			String transConfigs	= data.get(28).toString().trim().equals("")?null:data.get(28).toString().trim();
        			String delayStandar	= data.get(29).toString().trim().equals("")?null:data.get(29).toString().trim();
        			String launchDateStr= data.get(30).toString().trim().equals("")?null:data.get(30).toString().trim();
        			String lastActiveStr= data.get(31).toString().trim().equals("")?null:data.get(31).toString().trim();
        			batteryDuration		= data.get(32).toString().trim().equals("")?null:data.get(32).toString().trim();
        			phone				= data.get(33).toString().trim().equals("")?null:data.get(33).toString().trim();
        			targetUCTT			= data.get(34).toString().trim().equals("")?null:data.get(34).toString().trim();
        			tgUcttdhNgay		= data.get(35).toString().trim().equals("")?null:data.get(35).toString().trim();
        			tgUcttdhDem			= data.get(36).toString().trim().equals("")?null:data.get(36).toString().trim();
        			tgBackupAcquyMoi	= data.get(37).toString().trim().equals("")?null:data.get(37).toString().trim();
        			tgBackupAcquyCu		= data.get(38).toString().trim().equals("")?null:data.get(38).toString().trim();
        			timeCapnhatMoi		= data.get(39).toString().trim().equals("")?null:data.get(39).toString().trim();
        			timeCapnhatCu		= data.get(40).toString().trim().equals("")?null:data.get(40).toString().trim();
        			doLechTgBackupAcquy	= data.get(41).toString().trim().equals("")?null:data.get(41).toString().trim();
        			
        			mcc					= data.get(42).toString().trim().equals("")?null:data.get(42).toString().trim();
        			mnc					= data.get(43).toString().trim().equals("")?null:data.get(43).toString().trim();
        			addressEng			= data.get(44).toString().trim().equals("")?null:data.get(44).toString().trim();
        			azimuth				= data.get(45).toString().trim().equals("")?null:data.get(45).toString().trim();
        			description			= data.get(46).toString().trim().equals("")?null:data.get(46).toString().trim();
        			
        			createDate 			= data.get(47).toString().trim().equals("")?null:data.get(47).toString().trim();
        			siteDistance 		= data.get(48).toString().trim().equals("")?null:data.get(48).toString().trim();
        			bandwidthStr 		= data.get(49).toString().trim().equals("")?null:data.get(49).toString().trim();
    			    subscribersInfo     = data.get(50).toString().trim().equals("")?null:data.get(50).toString().trim();
        			khoangCachTram 		= data.get(51).toString().trim().equals("")?null:data.get(51).toString().trim();
        			// Kiem tra loi
        			if (bscid == null || cellid == null
        					/*|| (bscid != null && bscid.length() > 30)
        					|| (cellid != null && cellid.length() > 70)
        					|| (cellname != null && cellname.length() > 100)
        					|| (vendor != null && vendor.length() > 30)
        					|| (siteid != null && siteid.length() > 70)
        					|| (sitename != null && sitename.length() > 100)
        					|| (type != null && type.length() > 20)
        					|| (transType != null && transType.length() > 50)
        					|| (region != null && region.length() > 20)
        					|| (area != null && area.length() > 50)
        					|| (province != null && province.length() > 50)
        					|| (district != null && district.length() > 50)
        					|| (villageName != null && villageName.length() > 100)
        					|| (address != null && address.length() > 100)
        					|| (vendorTrans != null && vendorTrans.length() > 50)
        					|| (nextHop != null && nextHop.length() > 50)
        					|| (cellBorder != null && cellBorder.length() > 100)
        					|| (cellBlacklist != null && cellBlacklist.length() > 3)
        					|| (seaCell != null && seaCell.length() > 3)
        					|| (specialCell != null && specialCell.length() > 3)*/
        					/*|| (vendor != null && !vendor.toString().toLowerCase().equals("alcatel") 
        							&& !vendor.toString().toLowerCase().equals("huawei")
									&& !vendor.toString().toLowerCase().equals("ericsson") 
									&& !vendor.toString().toLowerCase().equals("nokia siemens"))*/
							|| (cellBlacklist != null 
								&& !cellBlacklist.toString().toLowerCase().equals("y")
								&& !cellBlacklist.toString().toLowerCase().equals("n")
									)
							|| (isIbc != null 
								&& !isIbc.toString().toLowerCase().equals("y")
								&& !isIbc.toString().toLowerCase().equals("n")
									)
							|| (cellBorder != null 
								&& !cellBorder.toString().toLowerCase().equals("y")
								&& !cellBorder.toString().toLowerCase().equals("n")
									)
							|| (seaCell != null 
								&& !seaCell.toString().toLowerCase().equals("y")
								&& !seaCell.toString().toLowerCase().equals("n")
									)
							|| (specialCell != null 
								&& !specialCell.toString().toLowerCase().equals("y")
								&& !specialCell.toString().toLowerCase().equals("n")
									)
						) {
						error = true;
					}
        			
        			try{
	        			if(longitude != null){
	        				Double a = Double.parseDouble(longitude);
		        			cell.setLongitude(a);
	        			}
	        			if(latitude != null){
	        				Double a = Double.parseDouble(latitude);
		        			cell.setLatitude(a);
	        			}
	        			if(cid != null){
		        			Integer a = Integer.parseInt(cid);
		        			cell.setCid(a);
	        			}
	        			if(lac != null){
		        			Integer a = Integer.parseInt(lac);
		        			cell.setLac(a);
	        			}
	        			if(bandwidth != null){
		        			Integer a = Integer.parseInt(bandwidth);
		        			cell.setBandwidth(a);
	        			}
	        			if(subscriber != null){
		        			Integer a = Integer.parseInt(subscriber);
		        			cell.setSubscriber(a);
	        			}
	        			if(siteLevel != null){
		        			Integer a = Integer.parseInt(siteLevel);
		        			cell.setSiteLevel(a);
	        			}
	        			if(delayStandar != null){
		        			Integer a = Integer.parseInt(delayStandar);
		        			cell.setDelayStandar(a);
	        			}
	        			if(batteryDuration != null){
		        			Integer a = Integer.parseInt(batteryDuration);
		        			cell.setBatteryDuration(a);
	        			}
	        			if(targetUCTT != null){
		        			Integer a = Integer.parseInt(targetUCTT);
		        			cell.setTargetUctt(a);
	        			}
	        			if(tgUcttdhNgay != null){
		        			Integer a = Integer.parseInt(tgUcttdhNgay);
		        			cell.setTgUcttdhNgay(a);
	        			}
	        			if(tgUcttdhDem != null){
		        			Integer a = Integer.parseInt(tgUcttdhDem);
		        			cell.setTgUcttdhDem(a);
	        			}
	        			if(tgBackupAcquyMoi != null){
	        				Integer a = Integer.parseInt(tgBackupAcquyMoi);
		        			cell.setTgBackupAcquyMoi(a);
	        			}
	        			
	        			if(tgBackupAcquyCu != null){
	        				Integer a = Integer.parseInt(tgBackupAcquyCu);
		        			cell.setTgBackupAcquyCu(a);
	        			}
	        			
	        			if(timeCapnhatMoi != null && !timeCapnhatMoi.equals("")){
	        				cell.setTimeCapnhatMoi((new SimpleDateFormat("dd/MM/yyyy")).parse(timeCapnhatMoi));
	        			}
	        			
	        			if(timeCapnhatCu != null){
	        				cell.setTimeCapnhatCu((new SimpleDateFormat("dd/MM/yyyy")).parse(timeCapnhatCu));
	        			}
	        			
	        			if(doLechTgBackupAcquy != null){
	        				Integer a = Integer.parseInt(doLechTgBackupAcquy);
		        			cell.setDoLechTgBackupAcquy(a);
	        			}
	        			
	        			if(azimuth != null){
	        				Float a = Float.parseFloat(azimuth);
		        			cell.setAzimuth(a);
	        			}
	        			
	        			if (launchDateStr!=null  && !launchDateStr.equals("")  )
	    				{
	        				cell.setLaunchDate((new SimpleDateFormat("dd/MM/yyyy")).parse(launchDateStr));
	        				
	    				}
	        			if (lastActiveStr!=null  && !lastActiveStr.equals("")  )
	    				{
	        				cell.setLastActive((new SimpleDateFormat("dd/MM/yyyy")).parse(lastActiveStr));
	        				
	    				}
	        			
	        			if (createDate!=null  && !lastActiveStr.equals("")  )
	    				{
	        				cell.setCreateDate((new SimpleDateFormat("dd/MM/yyyy")).parse(createDate));
	        				
	    				}
	        			if (siteDistance !=null  && ! siteDistance.equals("")  )
	    				{
	        				cell.setSiteDistance(Float.parseFloat(siteDistance));        				
	    				}
	        			if (khoangCachTram !=null  && ! khoangCachTram .equals("")  )
	    				{
	        				cell.setKhoangCachTram (Float.parseFloat(khoangCachTram ));        				
	    				}
        			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			
        			cell.setBscid(bscid);
        			cell.setType(type);
        			cell.setSiteid(siteid);
        			cell.setSiteName(sitename);
        			cell.setCellid(cellid);
        			cell.setCellName(cellname);
        			cell.setVendor(vendor);
        			cell.setVendorTrans(vendorTrans);
        			cell.setTransType(transType);
        			cell.setNextHop(nextHop);
        			cell.setRegion(region);
        			cell.setArea(area);
        			cell.setProvince(province);
        			cell.setDistrict(district);
        			cell.setVillageName(villageName);
        			cell.setAddress(address);
        			cell.setIsIbc(isIbc);
        			cell.setCellBorder(cellBorder);
        			cell.setCellBlacklist(cellBlacklist);
        			cell.setSeaCell(seaCell);
        			cell.setSpecialCell(specialCell);
        			cell.setTransConfigs(transConfigs);
        			cell.setDescription(description);
        			cell.setLastActiveI(lastActiveStr);
        			cell.setLaunchDateI(launchDateStr);
        			cell.setPhone(phone);
        			cell.setDescription(description);
        			cell.setTimeCapnhatCu1(timeCapnhatCu);
        			cell.setTimeCapnhatMoi1(timeCapnhatMoi);
        			cell.setMcc(mcc);
        			cell.setMnc(mnc);
        			cell.setAddressEng(addressEng);
        			
        			cell.setBandwidthStr(bandwidthStr);
        			cell.setSubscribersInfo(subscribersInfo);
        			if (bscid == null && cellid == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cell);
    					} else  {
    						
    						successList.add(cell);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (Cell3G record: successList) {
			try {
				if(cell3gDao.checkPrimaryKeyBscCell3g(record.getBscid(), record.getCellid(), null).size() == 0){
					cell3gDao.insert(record);
				}
				else{
						cell3gDao.updateByPrimaryKeySelective(record);
				}
					
				success.add(record);	
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu cell 3g không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "cell3gUpload";
	}
	
	@RequestMapping("exportExcel")
  	public ModelAndView reportAlarmLog(@RequestParam(required = false) String bscid,
  			@RequestParam(required = false) String siteid,
  			@RequestParam(required = false) String cellid,
  			@RequestParam(required = false) String vendor,
  			@RequestParam(required = false) String province,
  			@RequestParam(required = false) String district,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		
		List<Cell3G> cellList = cell3gDao.getHCell3GFilter(bscid, siteid, cellid,
				vendor, province, district, null, null, "BSCID", "ASC");
		
		exportReport(tempFile, cellList);
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "Cell3G_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<Cell3G> cellList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Cell 3G", 0);
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			int i=0;
			
			Label label0 = new Label(i, 0, "Rncid", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label15 = new Label(i, 0, "Ne Type", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			
			Label label1 = new Label(i, 0, "Siteid", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label13 = new Label(i, 0, "Site Name", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label2 = new Label(i, 0, "Cellid", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label14 = new Label(i, 0, "Cell Name", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label4 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label16 = new Label(i, 0, "Vendor Transmission", cellFormatHeader);
			sheet.addCell(label16);
			i++;
			Label label3 = new Label(i, 0, "Transmission Type", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label17 = new Label(i, 0, "Bandwidth", cellFormatHeader);
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Next Hop", cellFormatHeader);
			sheet.addCell(label18);
			i++;
			Label label19 = new Label(i, 0, "Subscriber", cellFormatHeader);
			sheet.addCell(label19);
			i++;
			Label label20 = new Label(i, 0, "Region", cellFormatHeader);
			sheet.addCell(label20);
			i++;
			Label label21 = new Label(i, 0, "Area", cellFormatHeader);
			sheet.addCell(label21);
			i++;
			Label label5 = new Label(i, 0, "Province", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "District", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Village Name", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label10 = new Label(i, 0, "Address", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label8 = new Label(i, 0, "Longitude", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Latitude", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label22 = new Label(i, 0, "Cid", cellFormatHeader);
			sheet.addCell(label22);
			i++;
			Label label23 = new Label(i, 0, "Lac", cellFormatHeader);
			sheet.addCell(label23);
			i++;
			Label label24 = new Label(i, 0, "Cell Border", cellFormatHeader);
			sheet.addCell(label24);
			i++;
			Label label25 = new Label(i, 0, "Cell Blacklist", cellFormatHeader);
			sheet.addCell(label25);
			i++;
			Label label26 = new Label(i, 0, "Sea Cell", cellFormatHeader);
			sheet.addCell(label26);
			i++;
			Label label27 = new Label(i, 0, "Special Cell", cellFormatHeader);
			sheet.addCell(label27);
			i++;
			Label label28 = new Label(i, 0, "IBC", cellFormatHeader);
			sheet.addCell(label28);
			i++;
			Label label29 = new Label(i, 0, "Site Level", cellFormatHeader);
			sheet.addCell(label29);
			i++;
			Label label30 = new Label(i, 0, "Trans Configs", cellFormatHeader);
			sheet.addCell(label30);
			i++;
			Label label11 = new Label(i, 0, "Delay Standar", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Launch Date", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label32 = new Label(i, 0, "Last Active", cellFormatHeader);
			sheet.addCell(label32);
			i++;
			Label label33 = new Label(i, 0, "Battery Duration", cellFormatHeader);
			sheet.addCell(label33);
			i++;
			Label label34 = new Label(i, 0, "Phone", cellFormatHeader);
			sheet.addCell(label34);
			i++;
			Label label38 = new Label(i, 0, "Target UCTT", cellFormatHeader);
			sheet.addCell(label38);
			i++;
			Label label39 = new Label(i, 0, "Tg Ucttdh Ngay", cellFormatHeader);
			sheet.addCell(label39);
			i++;
			Label label40 = new Label(i, 0, "Tg Ucttdh Dem", cellFormatHeader);
			sheet.addCell(label40);
			i++;
			Label label41 = new Label(i, 0, "Tg Backup Acquy Moi", cellFormatHeader);
			sheet.addCell(label41);
			i++;
			Label label42 = new Label(i, 0, "Tg Backup Acquy Cu", cellFormatHeader);
			sheet.addCell(label42);
			i++;
			Label label43 = new Label(i, 0, "Time Cap Nhat Moi", cellFormatHeader);
			sheet.addCell(label43);
			i++;
			Label label44 = new Label(i, 0, "Time Cap Nhat Cu", cellFormatHeader);
			sheet.addCell(label44);
			i++;
			Label label45 = new Label(i, 0, "Do lech Tg Backup Acquy", cellFormatHeader);
			sheet.addCell(label45);
			i++;
			
			Label label46 = new Label(i, 0, "Mcc", cellFormatHeader);
			sheet.addCell(label46);
			i++;
			Label label47 = new Label(i, 0, "Mnc", cellFormatHeader);
			sheet.addCell(label47);
			i++;
			Label label48 = new Label(i, 0, "Address Eng", cellFormatHeader);
			sheet.addCell(label48);
			i++;
			Label label49 = new Label(i, 0, "Azimuth", cellFormatHeader);
			sheet.addCell(label49);
			i++;
			Label label35 = new Label(i, 0, "Description", cellFormatHeader);
			sheet.addCell(label35);
			i++;
			Label createDate = new Label(i, 0, "Create Date", cellFormatHeader);
			sheet.addCell(createDate);
			i++;
			Label siteDistance = new Label(i, 0, "Site Distance", cellFormatHeader);
			sheet.addCell(siteDistance);
			i++;
			Label bandwidthStr = new Label(i, 0, "Bandwidth Str", cellFormatHeader);
			sheet.addCell(bandwidthStr);
			i++;
			Label subscribersInfo = new Label(i, 0, "Subscribers Info", cellFormatHeader);
			sheet.addCell(subscribersInfo);
			i++;
			Label khoangCachTram = new Label(i, 0, "Khoang Cach Tram", cellFormatHeader);
			sheet.addCell(khoangCachTram);
			i = 1;
			
			for (Cell3G record : cellList) {
				int j=0;
				Label record0 = new Label(j, i, record.getBscid(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record13 = new Label(j, i, record.getType(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record1 = new Label(j, i,record.getSiteid(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record14 = new Label(j, i, record.getSiteName(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record2 = new Label(j, i, record.getCellid(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record15 = new Label(j, i, record.getCellName(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record4 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record16 = new Label(j, i, record.getVendorTrans(), cellFormat);
				sheet.addCell(record16);
				j++;
				Label record3 = new Label(j, i, record.getTransType(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record17 =  new Label(j, i,"", cellFormat);
				if (record.getBandwidth()!=null)
				{
					record17 = new Label(j, i, String.valueOf(record.getBandwidth()), cellFormat);
				}
				sheet.addCell(record17);
				j++;
				Label record18 = new Label(j, i, record.getNextHop(), cellFormat);
				sheet.addCell(record18);
				j++;
				Label record19 = new Label(j, i,"", cellFormat);
				if (record.getSubscriber()!=null)
				{
					record19 = new Label(j, i, String.valueOf(record.getSubscriber()), cellFormat);
				}
				sheet.addCell(record19);
				j++;
				Label record20 = new Label(j, i, record.getRegion(), cellFormat);
				sheet.addCell(record20);
				j++;
				Label record21 = new Label(j, i, record.getArea(), cellFormat);
				sheet.addCell(record21);
				j++;
				Label record5 = new Label(j, i, record.getProvince(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getDistrict(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getVillageName(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record10 = new Label(j, i, record.getAddress(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record8 = new Label(j, i, record.getLongitudeStr(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getLatitudeStr(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record22 =  new Label(j, i,"", cellFormat);
				if (record.getCid()!=null)
				{
					record22 = new Label(j, i, String.valueOf(record.getCid()), cellFormat);
				}
				sheet.addCell(record22);
				j++;
				Label record23 = new Label(j, i,"", cellFormat);
				if (record.getLac()!=null)
				{
					record23 = new Label(j, i, String.valueOf(record.getLac()), cellFormat);
				}
				sheet.addCell(record23);
				j++;
				Label record24 = new Label(j, i, record.getCellBorder(), cellFormat);
				sheet.addCell(record24);
				j++;
				Label record25 = new Label(j, i, record.getCellBlacklist(), cellFormat);
				sheet.addCell(record25);
				j++;
				Label record26 = new Label(j, i, record.getSeaCell(), cellFormat);
				sheet.addCell(record26);
				j++;
				Label record27 = new Label(j, i, record.getSpecialCell(), cellFormat);
				sheet.addCell(record27);
				j++;
				Label record28 = new Label(j, i, record.getIsIbc(), cellFormat);
				sheet.addCell(record28);
				j++;
				Label record29 = new Label(j, i,"", cellFormat);
				if (record.getSiteLevel()!=null)
				{
					record29 = new Label(j, i, String.valueOf(record.getSiteLevel()), cellFormat);
				}
				sheet.addCell(record29);
				j++;
				Label record30 = new Label(j, i, record.getTransConfigs(), cellFormat);
				sheet.addCell(record30);
				j++;
				Label record31 =  new Label(j, i,"", cellFormat);
				if (record.getDelayStandar()!=null)
				{
					record31 = new Label(j, i, String.valueOf(record.getDelayStandar()), cellFormat);
				}
				sheet.addCell(record31);
				j++;
				Label record32 = new Label(j, i, record.getLaunchDateStr(), cellFormat);
				sheet.addCell(record32);
				j++;
				Label record34 = new Label(j, i, record.getLastActiveStr(), cellFormat);
				sheet.addCell(record34);
				j++;
				Label record33 =  new Label(j, i,"", cellFormat);
				if (record.getBatteryDuration()!=null)
				{
					record33 = new Label(j, i, String.valueOf(record.getBatteryDuration()), cellFormat);
				}
				sheet.addCell(record33);
				j++;
				Label record35 = new Label(j, i, record.getPhone(), cellFormat);
				sheet.addCell(record35);
				j++;
				Label record38 = new Label(j, i,"", cellFormat);
				if (record.getTargetUctt()!=null)
				{
					record38 = new Label(j, i, String.valueOf(record.getTargetUctt()), cellFormat);
				}
				sheet.addCell(record38);
				j++;
				Label record39 = new Label(j, i,"", cellFormat);
				if (record.getTgUcttdhNgay()!=null)
				{
					record39 = new Label(j, i, String.valueOf(record.getTgUcttdhNgay()), cellFormat);
				}
				sheet.addCell(record39);
				j++;
				Label record40 = new Label(j, i,"", cellFormat);
				if (record.getTgUcttdhDem()!=null)
				{
					record38 = new Label(j, i, String.valueOf(record.getTgUcttdhDem()), cellFormat);
				}
				sheet.addCell(record40);
				j++;
				Label record42 = new Label(j, i,"", cellFormat);
				if (record.getTgBackupAcquyMoi()!=null)
				{
					record42 = new Label(j, i, String.valueOf(record.getTgBackupAcquyMoi()), cellFormat);
				}
				sheet.addCell(record42);
				j++;
				Label record43 = new Label(j, i,"", cellFormat);
				if (record.getTgBackupAcquyCu()!=null)
				{
					record43 = new Label(j, i, String.valueOf(record.getTgBackupAcquyCu()), cellFormat);
				}
				sheet.addCell(record43);
				j++;
				Label record44 = new Label(j, i, record.getTimeCapnhatMoiStr(), cellFormat);
				sheet.addCell(record44);
				j++;
				Label record45 = new Label(j, i, record.getTimeCapnhatCuStr(), cellFormat);
				sheet.addCell(record45);
				j++;
				Label record46 = new Label(j, i,"", cellFormat);
				if (record.getDoLechTgBackupAcquy()!=null)
				{
					record46 = new Label(j, i, String.valueOf(record.getDoLechTgBackupAcquy()), cellFormat);
				}
				sheet.addCell(record46);
				j++;
				Label record47 = new Label(j, i, record.getMcc(), cellFormat);
				sheet.addCell(record47);
				j++;
				Label record48 = new Label(j, i, record.getMnc(), cellFormat);
				sheet.addCell(record48);
				j++;
				Label record49 = new Label(j, i, record.getAddressEng(), cellFormat);
				sheet.addCell(record49);
				j++;
				Label record50 = new Label(j, i,"", cellFormat);
				if (record.getAzimuth()!=null)
				{
					record50 = new Label(j, i, String.valueOf(record.getAzimuth()), cellFormat);
				}
				sheet.addCell(record50);
				j++;
				Label record36 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record36);
				
				j++;
				Label createDateValue = new Label(j, i, "", cellFormat);
				if (record.getCreateDate() != null) {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					createDateValue = new Label(j, i, df.format(record.getCreateDate()), cellFormat);
				}
				sheet.addCell(createDateValue);
				j++;
				Label siteDistanceValue = new Label(j, i, "", cellFormat);
				if (record.getSiteDistance() != null) {
					siteDistanceValue = new Label(j, i, String.valueOf(record.getSiteDistance()), cellFormat);
				}
				sheet.addCell(siteDistanceValue);
				j++;
				Label bandwidthStrValue = new Label(j, i, record.getBandwidthStr(), cellFormat);
				sheet.addCell(bandwidthStrValue);
				j++;
				Label subscribersInfoValue = new Label(j, i, record.getSubscribersInfo(), cellFormat);
				sheet.addCell(subscribersInfoValue);
				j++;
				Label khoangCachTramValue = new Label(j, i, "", cellFormat);
				if (record.getKhoangCachTram() != null) {
					khoangCachTramValue = new Label(j, i, String.valueOf(record.getKhoangCachTram()), cellFormat);
				}
				sheet.addCell(khoangCachTramValue);
				
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}