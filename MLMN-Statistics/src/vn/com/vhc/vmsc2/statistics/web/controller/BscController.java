package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.MDepartmentDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.BscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.ExportTools;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;
import bsh.ParseException;


@Controller
@RequestMapping("/network/bsc/*")
public class BscController extends BaseController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private MscDAO mscDao; 
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired 
	private HighlightConfigDAO highlightConfigDAO;
	

	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<HighlightConfig> highlightConfig= highlightConfigDAO.getByKey("NOT_NULL");
		if (highlightConfig.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfig.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") BscFilter filter, Model model) {
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(filter.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(filter.getDept(), filter.getTeam());
		
		List<Bsc> bscList = bscDao.filter(filter);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		model.addAttribute("bscList", bscList);
		model.addAttribute("filter",filter);  
		
		return new ModelAndView("bscList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String bscid, HttpServletRequest request) {

		bscDao.deleteByPrimaryKey(bscid);
		saveMessage(request, "BSC đã được xóa.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String bscid, ModelMap model) {
		Bsc bsc = new Bsc();
		List<MDepartment> deptList = new ArrayList<MDepartment>();
		List<MDepartment> teamList = new ArrayList<MDepartment>();
		List<MDepartment> subteamList = new ArrayList<MDepartment>();
		List<Msc> mscList = mscDao.getAll(); 
		
		if(bscid == null){
			//Add new
			bsc = new Bsc();
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(null);
			bscDao.getSubTeamByTeam(null, null);
		}else{
			//Edit
			bsc = bscDao.selectByPrimaryKey(bscid);
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(bsc.getDept());
			subteamList = bscDao.getSubTeamByTeam(bsc.getDept(), bsc.getTeam());
		}

		model.addAttribute("bsc", bsc);
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		model.addAttribute("mscList", mscList);

		return "bscForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid Bsc bsc,BindingResult result, HttpServletRequest request,Model model) {
		Date date = new Date();
		
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(bsc.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(bsc.getDept(), bsc.getTeam());
		List<Msc> mscList = mscDao.getAll(); 
		
		if (!result.hasErrors())
		{
			Bsc oldBsc= bscDao.selectByPrimaryKey(bsc.getBscid());
			if (bsc.getLaunchDate() == null) {
				if (oldBsc == null) { 
					bsc.setRegion("TT2");
					bsc.setLaunchDate(date); 
					
					bscDao.insert(bsc);
					saveMessage(request, "Thêm mới BSC thành công.");
				}
				else {
					saveMessage(request, "BSC đã tồn tại");
					model.addAttribute("bsc", bsc); 
					model.addAttribute("mscList", mscList);
					model.addAttribute("deptList", deptList);
					model.addAttribute("teamList", teamList);
					model.addAttribute("subteamList", subteamList);
					
					return "bscForm";
				}
			} else {
				if (oldBsc!=null && !oldBsc.getBscid().equals(bsc.getBscid()))
				{
					saveMessage(request, "BSC đã tồn tại");
					model.addAttribute("bsc", bsc); 
					model.addAttribute("mscList", mscList);
					model.addAttribute("deptList", deptList);
					model.addAttribute("teamList", teamList);
					model.addAttribute("subteamList", subteamList);
					
					return "bscForm";
				}
				bsc.setLaunchDate(date);
				bscDao.updateByPrimaryKey(bsc);
				saveMessage(request, "BSC được cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("bsc", bsc);
			model.addAttribute("mscList", mscList);
			model.addAttribute("deptList", deptList);
			model.addAttribute("teamList", teamList);
			model.addAttribute("subteamList", subteamList);
			
			return "bscForm";
		}
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "bscUpload";
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
		
		return "bscUpload";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {  
		
		List<Bsc> successList = new ArrayList<Bsc>();
		List<Bsc> failedList = new ArrayList<Bsc>();
		List<Bsc> success = new ArrayList<Bsc>();
		
		String bscid ;
		String vendor;
		String dept;
		String team ;
		String subteam ;
		String negroup ;
		String locationName ;
		String mscid ;
		String smsCssr ;
		String smsDrc ;
		String trau;
		String trx ;
		String nsei;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 13) {
        		saveMessageKey(request, "Số lượng cột dữ liệu không đúng.");
        		
        		return "bscUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		Bsc bsc;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			bsc = new Bsc();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=13; j++) {
     					data.add("");
     				}
        			 
					bscid					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
					vendor					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
					dept					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
					team					= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
					subteam					= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
					negroup					= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
					locationName			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
					mscid					= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
					smsCssr					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
					smsDrc					= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
					trau					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
					trx						= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim(); 
					nsei					= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim(); 
        			
					//Kiem tra loi
					if(dept != null){
						MDepartment testDept		= mDepartmentDAO.selectByPrimaryKey(dept);
						if(testDept == null){
							error = true; 
						}
					}
					
					if(team != null){
						MDepartment testTeam 		= mDepartmentDAO.selectByPrimaryKey(team);
						if(testTeam == null){
							error = true; 
						}
					}
					
					if(subteam != null){
						MDepartment testSubTeam		= mDepartmentDAO.selectByPrimaryKey(subteam);
						if(testSubTeam == null){
							error = true; 
						}
					}
	        		
					if(mscid != null){
						Msc testMsc = mscDao.selectByPrimaryKey(mscid);
						if(testMsc == null){
							error = true; 
						}
					}
					
					/*if(vendor != null){
						if(!vendor.equalsIgnoreCase("ALCATEL") && !vendor.equalsIgnoreCase("HUAWEI") 
								&& !vendor.equalsIgnoreCase("ERICSSON") && !vendor.equalsIgnoreCase("NOKIA SIEMENS")){
							error = true;
						}
					}*/
					
	        		//Kiem tra kieu number
	        		if((trx != null && isFloat(trx) == false) || (trau != null && isFloat(trau) == false)
	        				|| (smsDrc != null && isFloat(smsDrc) == false) || (smsCssr != null && isFloat(smsCssr) == false)){
	        			error = true; 
	        		}
	        		
	        		//Kiem tra do dai
					if(bscid == null
							|| (bscid != null && bscid.length() > 40)
							|| (vendor != null && vendor.length() > 20)
							|| (dept != null && dept.length() > 90)
							|| (team != null && team.length() > 40)
							|| (subteam != null && subteam.length() > 40)
							|| (negroup != null && negroup.length() > 40)
							|| (locationName != null && locationName.length() > 40)
							|| (mscid != null && mscid.length() > 40)
							|| (smsCssr != null && smsCssr.length() > 6) 
							|| (smsDrc != null && smsDrc.length() > 6)
							|| (trau != null && trau.length() > 6)
							|| (trx != null && trx.length() > 6)
							|| (nsei != null && nsei.length() > 40)
						){
						error = true;
					} 
        			
        			//Set value ---------------------------------------------------------------------------
					
					bsc.setBscid(bscid);
					bsc.setVendor(vendor);
					bsc.setDept(dept);
					bsc.setTeam(team);
					bsc.setSubTeam(subteam);
					bsc.setNeGroup(negroup);
					bsc.setLocationName(locationName);
					bsc.setMscid(mscid); 
					bsc.setNsei(nsei);
					
					bsc.setLaunchDate(new Date());
					
					//-------------------------------------------------
					
					if (error) {
						bsc.setStrTrau(trau);
						bsc.setStrTrx(trx);
						bsc.setStrSmsCssr(smsCssr);
						bsc.setStrSmsDrc(smsDrc);
						
						failedList.add(bsc);
					}else{ 
						if(smsCssr == null){
							bsc.setSmsCssr(Float.parseFloat("0"));
						}else{
							bsc.setSmsCssr(Float.parseFloat(smsCssr));
						} 
						
						if(smsDrc == null){
							bsc.setSmsDrc(Float.parseFloat("0"));
						}else{
							bsc.setSmsDrc(Float.parseFloat(smsDrc));
						} 
						
						if(trau == null){
							bsc.setTrau(Float.parseFloat("0"));
						}else{
							bsc.setTrau(Float.parseFloat(trau));
						}  
						
						if(trx == null){
							bsc.setTrx(Float.parseFloat("0"));
						}else{
							bsc.setTrx(Float.parseFloat(trx));
						} 
						
						successList.add(bsc);
					}
					
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (Bsc record: successList) {
			try {
					Bsc bscOld = bscDao.selectByPrimaryKey(record.getBscid());
					if(bscOld == null){ 
						bscDao.insertSelective(record);
					}else{
						bscDao.updateByPrimaryKeySelective(record);
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
			model.addAttribute("status", "Dữ liệu Bsc không hợp lệ do:");	// Upload lỗi
			model.addAttribute("status1","1.Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác.");
			model.addAttribute("status2","2.Thông tin Dept, Team, Subteam, Vendor hoặc Msc chưa tồn tại.");
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "bscUpload";
	} 
	
	@RequestMapping("exportExcel")
  	public ModelAndView reportAlarmLog(
  			   @RequestParam(required = false) String dept,
			   @RequestParam(required = false) String team, 
			   @RequestParam(required = false) String subTeam, 
			   @RequestParam(required = false) String vendor, 
			   @RequestParam(required = false) String mscid, 
			   @RequestParam(required = false) String bscid, 
			   HttpServletRequest request, HttpServletResponse response) throws Exception { 
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		
		BscFilter filter = new BscFilter();
		
		filter.setDept(dept);
		filter.setTeam(team);
		filter.setSubTeam(subTeam);
		filter.setVendor(vendor);
		filter.setMscid(mscid);
		filter.setBscid(bscid);
		
		List<Bsc> bscList = bscDao.filter(filter);
		
		exportReport(tempFile, bscList);
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "BscList_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<Bsc> bscList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Bsc List", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "BSC", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Dept", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Team", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Sub Team", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Ne Group", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Location Name", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "MSC", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "SMS CSSR", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "SMS DRC", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "TRAU", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "TRX", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "NSEI", cellFormatHeader);
			sheet.addCell(label12);
			i = 1;
			
			for (Bsc record : bscList) {
				int j=0;
				Label record0 = new Label(j, i, record.getBscid(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getVendor(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getDept(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getSubTeam(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNeGroup(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getLocationName(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getMscid(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, String.valueOf(record.getSmsCssr()), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i,String.valueOf( record.getSmsDrc()), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, String.valueOf(record.getTrau()), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, String.valueOf(record.getTrx()), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getNsei(), cellFormat);
				sheet.addCell(record12);
				
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
	
	@RequestMapping("ajax/loadTeamByDept")
	public @ResponseBody
	List<MDepartment> loadTeamByDept(@RequestParam(required = false) String deptid, HttpServletRequest request) {
		List<MDepartment> record = bscDao.getTeamByDept(deptid);
		return record;
	} 
	
	@SuppressWarnings("unused")
	private boolean isFloat(String number){
		try {
			Float _number = Float.parseFloat(number);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}