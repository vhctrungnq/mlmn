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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.MDepartmentDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.Bsc3gFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.ExportTools;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;
import bsh.ParseException;


@Controller
@RequestMapping("/network/rnc/*")
public class Bsc3gController extends BaseController {
	@Autowired
	private Bsc3GDAO bsc3gDao;
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
	public ModelAndView list(@ModelAttribute("filter") Bsc3gFilter filter, Model model) {
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(filter.getDept());
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(filter.getDept(), filter.getTeam());
		List<Bsc3G> bsc3g = bsc3gDao.filter(filter);
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
		model.addAttribute("bsc3gList", bsc3g); 
		model.addAttribute("filter",filter);
		
		return new ModelAndView("bsc3gList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String bscid, HttpServletRequest request) {

		bsc3gDao.deleteByPrimaryKey(bscid);
		saveMessage(request, "RNC đã được xóa.");
	

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String bscid, ModelMap model) {
		Bsc3G bsc3g = new Bsc3G();
		List<Msc> mscList = mscDao.getAll();
		List<MDepartment> deptList = null;
		List<MDepartment> teamList = null;
		List<MDepartment> subteamList = null;
		
		if(bscid == null){
			//Add new
			bsc3g = new Bsc3G();
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(null);
			subteamList = bscDao.getSubTeamByTeam(null,null);
		}else{
			//Edit
			bsc3g = bsc3gDao.selectByPrimaryKey(bscid);
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(bsc3g.getDept());
			subteamList = bscDao.getSubTeamByTeam(bsc3g.getDept(),bsc3g.getTeam());
		}
		
		model.addAttribute("bsc3g", bsc3g);
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		model.addAttribute("mscList", mscList);
		
		return "bsc3gForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("bsc3g")@Valid Bsc3G bsc3g, BindingResult result, HttpServletRequest request,Model model) {
		Date date = new Date();
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(bsc3g.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(bsc3g.getDept(),bsc3g.getTeam());
		List<Msc> mscList = mscDao.getAll(); 
		
		if (!result.hasErrors())
		{
			Bsc3G oldBsc3g= bsc3gDao.selectByPrimaryKey(bsc3g.getBscid());
			if (bsc3g.getLaunchDate() == null) {
				if (oldBsc3g == null) {
					bsc3g.setRegion("TT2");
					bsc3g.setLaunchDate(date);
					
					bsc3gDao.insert(bsc3g);
					saveMessage(request, "BSC 3G khởi tạo thành công.");
				} else {
					saveMessage(request, "BSC 3G đã tồn tại");
					model.addAttribute("bsc3g", bsc3g); 
					model.addAttribute("mscList", mscList);
					model.addAttribute("deptList", deptList);
					model.addAttribute("teamList", teamList);
					model.addAttribute("subteamList", subteamList);
					
					return "bsc3gForm";
				}
			}
			else {
				if (oldBsc3g!=null && !oldBsc3g.getBscid().equals(bsc3g.getBscid()))
				{
					saveMessage(request, "BSC 3G đã tồn tại");
					model.addAttribute("bsc3g", bsc3g);
					model.addAttribute("mscList", mscList);
					model.addAttribute("deptList", deptList);
					model.addAttribute("teamList", teamList);
					model.addAttribute("subteamList", subteamList);
					
					return "bsc3gForm";
				}
				bsc3g.setLaunchDate(date);
				bsc3gDao.updateByPrimaryKey(bsc3g);
				saveMessage(request, "BSC 3G đã cập nhật thành công.");
			}
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("bsc3g", bsc3g);	
			model.addAttribute("mscList", mscList);
			model.addAttribute("deptList", deptList);
			model.addAttribute("teamList", teamList);
			model.addAttribute("subteamList", subteamList);
			
			return "bsc3gForm";
		}
	} 

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "bsc3gUpload";
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
		
		return "bsc3gUpload";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {  
		
		List<Bsc3G> successList = new ArrayList<Bsc3G>();
		List<Bsc3G> failedList = new ArrayList<Bsc3G>();
		List<Bsc3G> success = new ArrayList<Bsc3G>();
		
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
		String nsei;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 11) {
        		saveMessageKey(request, "Số lượng cột dữ liệu không đúng.");
        		
        		return "bsc3gUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		Bsc3G bsc3g;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			bsc3g = new Bsc3G();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=11; j++) {
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
					nsei					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			
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
	        		if((smsDrc != null && isFloat(smsDrc) == false) || (smsCssr != null && isFloat(smsCssr) == false)){
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
							|| (nsei != null && nsei.length() > 40)
						){
						error = true;
					} 
        			
        			//Set value ---------------------------------------------------------------------------
					
					bsc3g.setBscid(bscid);
					bsc3g.setVendor(vendor);
					bsc3g.setDept(dept);
					bsc3g.setTeam(team);
					bsc3g.setSubTeam(subteam);
					bsc3g.setNeGroup(negroup);
					bsc3g.setLocationName(locationName);
					bsc3g.setMscid(mscid); 
					bsc3g.setNsei(nsei);
					
					bsc3g.setLaunchDate(new Date());
					
					//-------------------------------------------------
					
					if (error) {
						bsc3g.setStrSmsCssr(smsCssr);
						bsc3g.setStrSmsDrc(smsDrc);
						
						failedList.add(bsc3g);
					}else{ 
						if(smsCssr == null){
							bsc3g.setSmsCssr(Float.parseFloat("0"));
						}else{
							bsc3g.setSmsCssr(Float.parseFloat(smsCssr));
						} 
						
						if(smsDrc == null){
							bsc3g.setSmsDrc(Float.parseFloat("0"));
						}else{
							bsc3g.setSmsDrc(Float.parseFloat(smsDrc));
						}
						
						successList.add(bsc3g);
					}
					
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (Bsc3G record: successList) {
			try {
				Bsc3G bscOld = bsc3gDao.selectByPrimaryKey(record.getBscid());
					if(bscOld == null){ 
						bsc3gDao.insertSelective(record);
					}else{
						bsc3gDao.updateByPrimaryKeySelective(record);
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
		
		return "bsc3gUpload";
	} 

	@RequestMapping("exportExcel")
  	public ModelAndView reportAlarmLog(
  			   @RequestParam(required = false) String deptid,
			   @RequestParam(required = false) String teamid, 
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
		
		Bsc3gFilter filter = new Bsc3gFilter();
		
		filter.setDept(deptid);
		filter.setTeam(teamid);
		filter.setSubTeam(subTeam);
		filter.setVendor(vendor);
		filter.setMscid(mscid);
		filter.setBscid(bscid);
		
		List<Bsc3G> bscList = bsc3gDao.filter(filter);
		
		exportReport(tempFile, bscList);
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "RncList_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<Bsc3G> bscList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Rnc List", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "RNC", cellFormatHeader);
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
			Label label10 = new Label(i, 0, "NSEI", cellFormatHeader);
			sheet.addCell(label10);
			
			i = 1;
			
			for (Bsc3G record : bscList) {
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
				Label record10 = new Label(j, i,record.getNsei(), cellFormat);
				sheet.addCell(record10);
				
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