package controller.alarm;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.CTableConfig;
import vo.VRpDySiteGroups;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.VRpDySiteGroupsDAO;

/**
 * Ten file: KpisMarkGroupController.java
 * Muc dich: Cham diem ca nhan theo nhom
 * @author ThangPX
 * Ngay tao: 29/05/2014
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/alarm/kpi-mark-group/*")
public class KpisMarkGroupController {

	@Autowired
	private VRpDySiteGroupsDAO vRpDySiteGroupsDAO;
	@Autowired  
	private CTableConfigDAO cTableConfigDAO; 
	public static final String MESSAGES_KEY = "successMessagesKey";
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			

			startDate = df.format(DateTools.minusWeek(new Date(),1));
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df.format(cal.getTime());
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ChamdiemKPIs_TheoNhom_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		 
		String filter = "";
		String temp = "";
		if(startDate != null){
			filter += "startDate=" + startDate; 
			temp = "&";
		}
		if(endDate != null){
			filter += temp + "endDate=" + endDate;  
		} 
		if(filter != "")
			filter = "?" + filter;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_SITE_GROUPS");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_RP_DY_SITE_GROUPS");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filter;
		//lay du lieu column group cua grid
		String groupColumn="";
		
		List<String> columnGrid = cTableConfigDAO.getGroupList("V_RP_DY_SITE_GROUPS");
		String con2="";
		groupColumn =" var columngroups=[";
		for (String alarmTypeG : columnGrid) {
			groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
			con2 = ",";
		}
		groupColumn= groupColumn+	"]; ";
		//Grid
		String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", groupColumn);
		model.addAttribute("gridReport", gridReport);
		
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate); 
		model.addAttribute("function", function);
		
		return "jspalarm/kpimark/groupList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<VRpDySiteGroups> siteDeptsList = vRpDySiteGroupsDAO.getInfo(startDate, endDate);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(siteDeptsList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	  
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		List<VRpDySiteGroups> siteDeptsList = vRpDySiteGroupsDAO.getInfo(startDate, endDate);
		exportReport(tempFile, siteDeptsList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ChamdiemKPIs_TheoNhom_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<VRpDySiteGroups> siteTeamsList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
 
			
			String str="Ngày tổng hợp,Tổ VT, Nhóm,Số lần MLL tối đa trạm cấp 1 KH,Số lần MLL tối đa trạm cấp 1 TH,Số lần MLL tối đa trạm cấp 2 KH, Số lần MLL tối đa trạm cấp 2 TH, Số lần MLL tối đa trạm cấp 3 KH, Số lần MLL tối đa trạm cấp 3 TH," +
					"TGXL MLL ban ngày trạm cấp 1 KH, TGXL MLL ban ngày trạm cấp 1 TH, TGXL MLL ban ngày trạm cấp 2 KH, TGXL MLL ban ngày trạm cấp 2 TH, TGXL MLL ban ngày trạm cấp 3 KH, TGXL MLL ban ngày trạm cấp 3 TH," +
					"TGXL MLL ban đêm trạm cấp 1 KH, TGXL MLL ban đêm trạm cấp 1 TH, TGXL MLL ban đêm trạm cấp 2 KH, TGXL MLL ban đêm trạm cấp 2 TH, TGXL MLL ban đêm trạm cấp 3 KH, TGXL MLL ban đêm trạm cấp 3 TH";
			Label temp;
			
			String[] s = str.split(",");
			for(int i=0; i < s.length; i++)
			{
				temp = new Label(i, 0, s[i], cellFormatHeader);
				sheet.addCell(temp);
			}
		    int k = 1;
			Label record2;
			for (VRpDySiteGroups record : siteTeamsList) {
				int j=0;
				Label record1 = new Label(j, k,df.format(record.getSumDate()), cellFormat);
				sheet.addCell(record1);
				j++; 
				Label record0 = new Label(j, k, record.getTeam(), cellFormat);
				sheet.addCell(record0);
				j++;
			    record2 = new Label(j, k, record.getTeamGroups(), cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getLostContactNum1Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum1Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum2Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum2Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum3Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum3Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTime1Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTime1Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTime2Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getPrcessTime2Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getPrcessTime3Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getPrcessTime3Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime1Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime1Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime2Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime2Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime3Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime3Th(),cellFormat);
				sheet.addCell(record2); 
			 
				k++;
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
	private Label checkNull(int j, int i, Integer src,WritableCellFormat cellFormat)
	{
		Label record;
		if(src!= null)
			record = new Label(j, i, src.toString(), cellFormat);
		else
			record = new Label(j, i, "", cellFormat);
		
		return record;
	}
// Upload
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		 
		return "jspalarm/kpimark/uploadForm";
	}
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		 
		if (!filePath.isEmpty()) { 
			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xlsx")||fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		 
		return "jspalarm/kpimark/uploadForm"; 
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveMessageKey(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute(MESSAGES_KEY, messages);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		 
		
	 
		
		List<VRpDySiteGroups> successList = new ArrayList<VRpDySiteGroups>();
		List<VRpDySiteGroups> failedList = new ArrayList<VRpDySiteGroups>();
		List<VRpDySiteGroups> success = new ArrayList<VRpDySiteGroups>();

		String dept;
		String team;
		String group;
		String mll1; 
		String mll2;
		String mll3;
		String tgxlmll_bn_1;
		String tgxlmll_bn_2;
		String tgxlmll_bn_3;
		String tgxlmll_bd_1;
		String tgxlmll_bd_2;
		String tgxlmll_bd_3;
		String chitieu_uctt_ngay;
		String chitieu_uctt_dem;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 14) {
        		 
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jspalarm/kpimark/uploadForm";
        	}
        	
        	if (sheetData.size() > 1) {
        		List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 14 && sheetData.size() > 2) {
        			boolean kt=true;
        			for (int k=0;k<footer.size();k++) {
        				if (!footer.get(k).toString().trim().equals("")) {
        					kt=false;
        				}
        			}
        			if (kt==true) {
	        			sheetData.remove(sheetData.size()-1);
	        		//	maxsize=sheetData.size();
        			}
        			else {
        				for (int j=footer.size(); j<=4; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			VRpDySiteGroups dySiteGroups = new VRpDySiteGroups();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=11; j++) {
     					data.add("");
     				}
        			
        			dept 		= data.get(0).toString().equals("")?null:data.get(0).toString();
        			team	= data.get(1).toString().equals("")?null:data.get(1).toString();
        			group		= data.get(2).toString().equals("")?null:data.get(2).toString(); 
        			mll1	= data.get(3).toString().equals("")?"0":data.get(3).toString(); 
        			mll2		= data.get(4).toString().equals("")?"0":data.get(4).toString(); 
        			mll3		= data.get(5).toString().equals("")?"0":data.get(5).toString(); 
        			tgxlmll_bn_1		= data.get(6).toString().equals("")?"0":data.get(6).toString(); 
        			tgxlmll_bn_2		= data.get(7).toString().equals("")?"0":data.get(7).toString(); 
        			tgxlmll_bn_3		= data.get(8).toString().equals("")?"0":data.get(8).toString(); 
        			tgxlmll_bd_1		= data.get(9).toString().equals("")?"0":data.get(9).toString(); 
        			tgxlmll_bd_2		= data.get(10).toString().equals("")?"0":data.get(10).toString(); 
        			tgxlmll_bd_3		= data.get(11).toString().equals("")?"0":data.get(11).toString();
        			chitieu_uctt_ngay   = data.get(12).toString().equals("")?"0":data.get(12).toString();
        			chitieu_uctt_dem    = data.get(13).toString().equals("")?"0":data.get(13).toString();
					if (dept == null || team == null || group == null ) 
					{ 
						error = true;
					} 
					dySiteGroups.setDept(dept);
					dySiteGroups.setTeam(team);
					dySiteGroups.setTeamGroups(group);
					dySiteGroups.setLostContactNum1Kh(Integer.parseInt(mll1));
					dySiteGroups.setLostContactNum2Kh(Integer.parseInt(mll2));
					dySiteGroups.setLostContactNum3Kh(Integer.parseInt(mll3));
					dySiteGroups.setPrcessTime1Kh(Integer.parseInt(tgxlmll_bn_1));
					dySiteGroups.setPrcessTime2Kh(Integer.parseInt(tgxlmll_bn_2));
					dySiteGroups.setPrcessTime3Kh(Integer.parseInt(tgxlmll_bn_3));
					dySiteGroups.setNightPrcessTime1Kh(Integer.parseInt(tgxlmll_bd_1));
					dySiteGroups.setNightPrcessTime2Kh(Integer.parseInt(tgxlmll_bd_2));
					dySiteGroups.setNightPrcessTime3Kh(Integer.parseInt(tgxlmll_bd_3));
					dySiteGroups.setChiTieuMLLNgay(Integer.parseInt(chitieu_uctt_ngay));
					dySiteGroups.setChiTieuMLLDem(Integer.parseInt(chitieu_uctt_dem));
        				if (error) {
    						failedList.add(dySiteGroups);
    					} else  {
    						successList.add(dySiteGroups);
    					}
        			
				}
        	}
		}
		vRpDySiteGroupsDAO.deleteAll();
		for (VRpDySiteGroups item: successList) {  
		    	
			vRpDySiteGroupsDAO.insert(item) ;
			 success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList); 
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ContactNumber_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName); 	
		return "jsp/ContactNumber/contactNumberUpload";
	}
}
