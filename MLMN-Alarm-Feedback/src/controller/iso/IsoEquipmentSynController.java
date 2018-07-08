package controller.iso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.IsoInventory;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.dictionary.TableConfigsHelper;


import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.IsoInventoryDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

/**
 * Ten file: IsoEquipmentSynController.java
 * Muc dich: Tong hop thiet bi
 * @author QuangBui
 * Ngay tao: 29/08/2013
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/iso/tong-hop-thiet-bi/*")
public class IsoEquipmentSynController {

	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String deptCode,
					   @RequestParam(required = false) String neType,
					   @RequestParam(required = false) String team, 
					   @RequestParam(required = false) String subTeam, 
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "TongHopThietBi_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		boolean rolesManagerDeptCode = false;
		boolean rolesManagerTeam = false;
		boolean rolesManagerSubTeam = false;
		SysUsers user = sysUsersDao.selectSysUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user.getIsRoleManager() != null && user.getIsRoleManager().equals("Y")){
			
		}
		else{
			if(user.getSubTeam() != null && !user.getSubTeam().equals("")){
				subTeam = user.getSubTeam();
				rolesManagerSubTeam = true;
			}
			if(user.getTeam() != null && !user.getTeam().equals("")){
				team = user.getTeam();
				rolesManagerTeam = true;
			}
			if(user.getMaPhong() != null && !user.getMaPhong().equals("")){
				deptCode = user.getMaPhong();
				rolesManagerDeptCode = true;
			}
		}
		model.addAttribute("rolesManagerDeptCode", rolesManagerDeptCode);
		model.addAttribute("rolesManagerTeam", rolesManagerTeam);
		model.addAttribute("rolesManagerSubTeam", rolesManagerSubTeam);
		
		String filter = "";
		String temp = "";
		if(deptCode != null){
			filter += "deptCode=" + deptCode; 
			temp = "&";
		}
		if(team != null){
			filter += temp + "team=" + team; 
			temp = "&";
		}
		if(subTeam != null){
			filter += temp + "subTeam=" + subTeam; 
			temp = "&";
		}
		if(neType != null){
			filter += temp + "neType=" + neType;
		}
		if(filter != "")
			filter = "?" + filter;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_TOTAL_DEVICE");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ISO_TOTAL_DEVICE");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filter;
		//Grid
		String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
		model.addAttribute("gridReport", gridReport);
		
		
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("team", team);
		model.addAttribute("subTeam", subTeam);
		model.addAttribute("neType", neType);
		model.addAttribute("function", function);
		
		return "jspiso/isoEquipmentSynList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String deptCode,
			   @RequestParam(required = false) String team, 
			   @RequestParam(required = false) String subTeam, 
			   @RequestParam(required = false) String neType,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoInventory> isoEquipmentSynList = isoInventoryDAO.getIsoEquipmentSyn(deptCode, team,subTeam, neType);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoEquipmentSynList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String team, 	   
			@RequestParam(required = false) String subTeam,
			@RequestParam(required = false) String neType,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		List<IsoInventory> isoEquipmentSynList = isoInventoryDAO.getIsoEquipmentSyn(deptCode, team, subTeam, neType);
		exportReport(tempFile, isoEquipmentSynList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "TongHopThietBi_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<IsoInventory> isoEquipmentSynList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Department", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Location Name", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Team", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Sub Team", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Ne Group", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Ne Type", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Ne Quantity", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Active", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Inactive", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Warranty", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "In stock", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Total", cellFormatHeader);
			sheet.addCell(label11);
			
			i = 1;
			
			for (IsoInventory record : isoEquipmentSynList) {
				int j=0;
				Label record0 = new Label(j, i, record.getDeptCode(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getLocationName(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getSubTeam(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getNeGroup(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getNeQtyStr(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getDangHoatDongStr(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getKhongHoatDongStr(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getBaoHanhStr(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getTrongKhoStr(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getTongStr(), cellFormat);
				sheet.addCell(record11);

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
