package controller.qldn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.MDepartment;
import vo.QldnDonGiaDien;
import vo.QldnDonViThuHuong;
import vo.QldnPhanBoChiPhi;
import vo.QldnQlMatBang;
import vo.QldnThongTinMayNo;
import vo.QldnThongTinTram;
import vo.QldnTramTTDien;
import vo.QldnTramTTNhienLieu;
import vo.QldnUploadLog;
import vo.RAlarmLog;
import vo.RAlarmLogTemp;
import vo.SYS_PARAMETER;
import vo.SysGroupUser;
import vo.SysUserRoles;
import vo.SysUsers;
import vo.VAlHCell;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnDonGiaDienDAO;
import dao.QldnDonViThuHuongDAO;
import dao.QldnPhanBoChiPhiDAO;
import dao.QldnQlMatBangDAO;
import dao.QldnThongTinMayNoDAO;
import dao.QldnThongTinTramDAO;
import dao.QldnTramTTDienDAO;
import dao.QldnTramTTNhienLieuDAO;
import dao.QldnUploadLogDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.VAlHCellDAO;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Controller
@RequestMapping("/import-qldn/*")
public class QLDNImportController  extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private QldnDonViThuHuongDAO qldnDonViThuHuongDAO;
	
	@Autowired
	private QldnUploadLogDAO qldnUploadLogDAO;
	
	@Autowired
	private QldnPhanBoChiPhiDAO qldnPhanBoChiPhiDao;
	
	@Autowired
	private QldnThongTinMayNoDAO qldnThongTinMayNoDao;
	
	@Autowired
	private QldnThongTinTramDAO qldnThongTinTramDao;
	
	@Autowired
	private QldnDonGiaDienDAO qldnDonGiaDienDao;
	
	@Autowired
	private QldnTramTTNhienLieuDAO qldnTramTTNhienLieuDao;
	
	@Autowired
	private QldnTramTTDienDAO qldnTramTTDienDao;
	
	@Autowired
	private QldnQlMatBangDAO qldnQlMatBangDao;
	
	@Autowired
	private VAlHCellDAO vAlHCellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String typeFile,@RequestParam(required = false) String netWork,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> typeFileList = sysParameterDao.getTypeOfImportQLDN(null);
		model.addAttribute("typeFileList", typeFileList);
		model.addAttribute("typeFile", typeFile);
		
		return "jspQLDN/importQLDN";
	}
	
	@RequestMapping("loadFileExample")
   	public @ResponseBody 
   	List<SYS_PARAMETER> loadFileExample(@RequestParam(required = false) String typeFile, HttpServletRequest request, HttpServletResponse response) {
   		List<SYS_PARAMETER> typeFileList = sysParameterDao.getTypeOfImportQLDN(typeFile);
   		return typeFileList;
   	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String list(
			@RequestParam("filePath") MultipartFile filePath,
			@RequestParam(required = false) String typeFile,
			Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> typeFileList = sysParameterDao.getTypeOfImportQLDN(null);
		model.addAttribute("typeFileList", typeFileList);
		model.addAttribute("typeFile", typeFile);
		String resultImport="";
		
		//*Kiem tra dinh dang file 

		System.out.println("filePath.getSize(): "+filePath.getSize());
		if (!filePath.isEmpty()&&filePath.getSize()<=800000000) {

			String[] ten= filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			String filename=ten[0];
			System.out.println("Duoi file: "+fileExtn);
			System.out.println("Ten file: "+filename);
			//System.out.println("Ten file getOriginalFilename: "+filePath.toString());
			if (fileExtn.equalsIgnoreCase("xls")&&typeFile!=null&&!typeFile.equals("")) {
				@SuppressWarnings("rawtypes")
				List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
				
				if (typeFile.equals("QLDN_TRAM_TT_DIEN"))
				{
					//GOI DEN HAM CONVERT DU LIEU CHO FILE ALCATEL
					resultImport = convertQldnTramTTDien(sheetData,username,model,request);
				}
				if (typeFile.equals("QLDN_TRAM_TT_DIEN_HCM"))
				{
					//GOI DEN HAM CONVERT DU LIEU CHO FILE ALCATEL
					resultImport = convertQldnTramTTDienHCM(sheetData,username,model,request,response);
				}
				if (typeFile.equals("QLDN_TRAM_TT_NHIEN_LIEU"))
				{
					resultImport = convertFileQldnTramTTNhienLieu(sheetData, username,model,request); 
					
				}
				if (typeFile.equals("QLDN_THONG_TIN_TRAM"))
				{
					resultImport = convertFileQldnThongTinTram(sheetData,typeFile, username,model,request); 
					
				}
				if (typeFile.equals("QLDN_THONG_TIN_TRAM_HCM"))
				{
					resultImport = convertFileQldnThongTinTramHCM(sheetData, typeFile,username,model,request); 
					
				}
				if (typeFile.equals("QLDN_THONG_TIN_MAY_NO"))
				{
					resultImport = convertFileQldnThongTinMayNo(sheetData, username,model,request); 
					
				}
				if (typeFile.equals("QLDN_QL_MAT_BANG"))
				{
					resultImport = convertFileQldnQlMatBang(sheetData, username,model,request); 
					
				}
				if (typeFile.equals("QLDN_PHAN_BO_CHI_PHI"))
				{
					resultImport = convertFileQldnPhanBoChiPhi(sheetData, username,model,request); 
					
				}
				if (typeFile.equals("QLDN_DON_VI_THU_HUONG"))
				{
					resultImport = convertFileQldnDonViThuHuong(sheetData, username,model,request); 
				}
					
				model.addAttribute("resultImport", resultImport);
				
				
			}
			else {
				resultImport="Cần lựa chọn đúng loại file trước khi upload!";
				 saveMessage(request, "Cần lựa chọn loại file trước khi upload!");
			}
		}
		else {
			resultImport="EmptyFile!";
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		QldnUploadLog log = new QldnUploadLog();
		log.setLoaiFile(typeFile);
		if (resultImport.length()>3000) {
			resultImport = resultImport.substring(0, 3000);
		}
		log.setLogUpload(resultImport);
		log.setNguoiNhap(username);
		log.setTgUpload(new Date());
		log.setFilePath(filePath.toString());
		qldnUploadLogDAO.insertSelective(log);
		
		return "jspQLDN/importQLDN";
		
	}
	

	private String convertQldnTramTTDienHCM(List sheetData, String username, Model model, HttpServletRequest request, HttpServletResponse response) {
		String idTram;
		String makh;
		String thangQuyTt;
		String namTt;
		String tuNgay;
		String denNgay;
		String csc1;
		String csc2;
		String csc3;
		String csm1;
		String csm2;
		String csm3;
		String tienTt;
		String ghiChu;
		long dienNangTt=0;
		long chenhLechDn= 0;
		long chenhLechTien= 0;
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		List<QldnTramTTDien> PEList= new ArrayList<>();
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 13) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnTramTTDien record;
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			String error="";
        			String con="";
        			record = new QldnTramTTDien();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=12; j++) {
     					data.add("");
     				}
        			
        			makh		= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			thangQuyTt		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			namTt		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			tuNgay		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			denNgay	= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			csc1		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			csc2		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			csc3		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim().replaceAll("-", "");
        			csm1		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim().replaceAll("-", "");
        			csm2		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim().replaceAll("-", "");
        			csm3		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim().replaceAll("-", "");
        			tienTt		= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim().replaceAll("-", "");
        			ghiChu		= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim().replaceAll("-", "");
        			
        			dienNangTt=0;
        			record.setTgttTq("Tháng");
        			record.setGhiChu(ghiChu);
        			record.setMakh(makh);
        			if ( makh!=null&&!makh.equals(""))
    				{
    					QldnThongTinTram thongTinTram = qldnThongTinTramDao.getTramByMaKH(makh,null);
    					if (thongTinTram!=null && thongTinTram.getIdTram()!=null) {
    						idTram = thongTinTram.getIdTram();
    						record.setIdTram(idTram);
    					} else {
    						/*error=error+con+"Chưa cấu hình mapping PE:"+makh+",Số CT:"+soct;
    						con=",";*/
    						QldnTramTTDien recordError = new QldnTramTTDien();
    						recordError.setMakh(makh);
    						PEList.add(recordError);
    					}
    				}
        			try {
        				if (thangQuyTt!=null &&!thangQuyTt.equals("")) {
        					record.setThangQuyTt(Integer.parseInt(thangQuyTt));
        				}else {
        					error=error+con+"Chưa nhập tháng thanh toán";
        					con=",";}
        				if (namTt!=null &&!namTt.equals("")) {
        					record.setNamTt(Integer.parseInt(namTt));
	        			}else {
	    					error=error+con+"Chưa nhập năm thanh toán";
	    					con=",";}
        				if (tuNgay!=null &&!tuNgay.equals(""))
        					record.setTuNgay(df.parse(tuNgay));
        				if (denNgay!=null &&!denNgay.equals("")) {
        					record.setDenNgay(df.parse(denNgay));
        					if (record.getDenNgay().compareTo(record.getTuNgay())<0) {
		    					error=error+con+" Thời gian kết thúc ("+denNgay+") phải bé hơn thời gian bắt đầu ("+tuNgay+")";
		    					con=",";
        					}
        					String mon= denNgay.substring(3, 5);
        					if (thangQuyTt!=null && Integer.parseInt(thangQuyTt)!=Integer.parseInt(mon))
        					{
        						error=error+con+" Không trung khớp giữa tháng/quý thanh toán ("+thangQuyTt+") với ngày kết thúc ("+denNgay+") ";
		    					con=",";
        					}
	    				}
        				if (csm1!=null &&!csm1.equals(""))
        				{
        					dienNangTt=dienNangTt+ Long.parseLong(csm1);
        					record.setCsm1(Long.parseLong(csm1));
        				}
        					
        				if (csm2!=null &&!csm2.equals(""))
        				{
        					dienNangTt=dienNangTt+ Long.parseLong(csm2);
        					record.setCsm2(Long.parseLong(csm2));
        				}
        				if (csm3!=null &&!csm3.equals(""))
        				{
        					dienNangTt= dienNangTt+ Long.parseLong(csm3);
        					record.setCsm3(Long.parseLong(csm3));
        				}
        				if (csc1!=null &&!csc1.equals(""))
        				{
        					dienNangTt=dienNangTt- Long.parseLong(csc1);
        					record.setCsc1(Long.parseLong(csc1));
        				}
        				if (csc2!=null &&!csc2.equals(""))
        				{
        					dienNangTt=dienNangTt- Long.parseLong(csc2);
        					record.setCsc2(Long.parseLong(csc2));
        				}
        					
        				if (csc3!=null &&!csc3.equals(""))
        				{
        					dienNangTt=dienNangTt- Long.parseLong(csc3);
        					record.setCsc3(Long.parseLong(csc3));
        				}
        				if (dienNangTt < 0)
        				{
        					dienNangTt = dienNangTt+100000;
        				}
        				
        				record.setDienNangTt(dienNangTt);
        				if (tienTt!=null &&!tienTt.equals(""))
        					record.setTienTt(Long.parseLong(tienTt));
        				
	        			if (error.equals("")&&record.getIdTram()!=null)
	    				{
	        				Integer thangTruoc;
	        				Integer namTruoc;
	        				if (record.getThangQuyTt()==1) {
	        					thangTruoc= 12;
	        					namTruoc = record.getNamTt() -1;
	        				}
	        				else
	        				{
	        					thangTruoc= record.getThangQuyTt() -1;
	        					namTruoc = record.getNamTt();
	        				}
	        				QldnTramTTDien ttThangTruoc = qldnTramTTDienDao.getTramTTDien(record.getIdTram(),record.getTgttTq(),thangTruoc.toString(),namTruoc.toString());
	            			if (ttThangTruoc!=null)
	            			{
	            				if (ttThangTruoc.getDienNangTt()==null)
	            					ttThangTruoc.setDienNangTt(Long.parseLong("0"));
	            				if (ttThangTruoc.getTienTt()==null)
	            					ttThangTruoc.setTienTt(Long.parseLong("0"));
	            				if (record.getDienNangTt()==null)
	            					record.setDienNangTt(Long.parseLong("0"));
	            				if (record.getTienTt()==null)
	            					record.setTienTt(Long.parseLong("0"));
	            				
	            				chenhLechDn = record.getDienNangTt() - ttThangTruoc.getDienNangTt();
	            				record.setChenhLechDn(chenhLechDn);
	            				chenhLechTien = record.getTienTt() - ttThangTruoc.getTienTt();
	            				record.setChenhLechTien(chenhLechTien);
	            			}
	            			QldnTramTTDien checkExit = qldnTramTTDienDao.checkExit(record.getMakh(),tuNgay,denNgay);
	            			if (checkExit!=null&&checkExit.getId()!=null )
	            			{
	            				record.setId(checkExit.getId());
	            				record.setNvSua(username);
	            				record.setNgaySua(new Date());
	            				qldnTramTTDienDao.updateByPrimaryKey(record);
	            				numUpdate++;
	            			}
	            			else
	            			{
	            				record.setNgayNhap(new Date());
	            				record.setNvNhap(username);
	            				qldnTramTTDienDao.insert(record);
	            				numInsert++;
	            			}
	        			}
        			
        			}
	        		catch (Exception exp){
	        			System.out.println(exp.toString());
	        			error=error+con+" Lỗi trong quá trình insert";
    					con=",";
	        		} 
        			if (!error.equals(""))
        			{
        				numError++;
	        			numRecodeError=numRecodeError+conn+"Lỗi dòng ("+i+ "): "+error;
	        			conn = "<br>";
        			}
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " <br>Kết quả import lỗi: <br> "+numRecodeError;
		
		String resultImport = "Result: <br> Số bản nghi lỗi:"+numError+numRecodeError+ ",<br> Record insert:"+numInsert+ ",<br> Record update:"+numUpdate;
		if (PEList.size()>0)
		{
			return exportPENoConfig(resultImport,PEList,request,response);
		}
		
		return resultImport;
	}

	private String exportPENoConfig(String resultImport,List<QldnTramTTDien> pEList, HttpServletRequest request, HttpServletResponse response ) {
		try {
			// temp file
			String basePath = request.getSession().getServletContext().getRealPath("");
			String dataDir = basePath + "/upload";
			String tempName = UUID.randomUUID().toString();
			String ext = "xls";
			String outfile = tempName + "." + ext;
			File tempFile = new File(dataDir + "/" + outfile);
						
			WorkbookSettings wbSetting = new WorkbookSettings();  
           // wbSetting.setUseTemporaryFileDuringWrite(true);  
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile,wbSetting);
			WritableSheet sheet = workbook.createSheet("Alarm report", 0);
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			  
			//caption
			if (resultImport!=null) {
				resultImport = resultImport.replaceAll("</br>", ",");
				resultImport = resultImport.replaceAll("<br>", ",");
			}
			Label label00 = new Label(0, 0, resultImport,cellFormat);
			sheet.mergeCells(0, 0, 10, 0);
			sheet.setRowView(0, 38*20);
		    sheet.addCell(label00);
			
		    sheet.setColumnView(0, 20);
		    sheet.setColumnView(1, 20);
			
			Label label0 = new Label(0, 2, "Mã Khách Hàng",cellFormatHeader);
			sheet.addCell(label0);
			
			int i = 3;
			for (QldnTramTTDien item : pEList) {
				Label NE = new Label(0, i,item.getMakh(), cellFormat);
				sheet.addCell(NE);
				i++;
			}
			workbook.write();
			workbook.close();
			
			// return
			String dateNow = df.format(new Date());
			String filename = "PE_Chua_Config_" + dateNow;
			response.setContentType("application/ms-excel");
			response.setContentLength((int) tempFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");
			FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
			// xoa file temp
			tempFile.delete();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:upload.htm?typeFile=QLDN_TRAM_TT_DIEN_HCM";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String convertFileQldnDonViThuHuong(List sheetData, String username,
			 Model model, HttpServletRequest request) {
	
		String tenDv;
		String maSoThue;
		String soTaiKhoan;
		String tenNganHang;
		String diaChiNh;
		String lienHe;
		String ghiChu;
		

		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 7) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnDonViThuHuong donVi;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			donVi = new QldnDonViThuHuong();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=5; j++) {
     					data.add("");
     				}
        			tenDv				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			maSoThue			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			soTaiKhoan			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			tenNganHang			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			diaChiNh			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			lienHe				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			ghiChu				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			
        			
        			donVi.setTenDv(tenDv);
        			donVi.setMaSoThue(maSoThue);
        			donVi.setSoTaiKhoan(soTaiKhoan);
        			donVi.setTenNganHang(tenNganHang);
        			donVi.setDiaChiNh(diaChiNh);
        			donVi.setLienHe(lienHe);
        			donVi.setGhiChu(ghiChu);
        			
        			QldnDonViThuHuong checkDV = qldnDonViThuHuongDAO.checkDVThuHuong(tenDv);
        			try {
	        			if (checkDV!=null)
	        			{
	        				donVi.setId(checkDV.getId());
	        				donVi.setModifiedBy(username);
	            			donVi.setModifiedDate(new Date());
	            			qldnDonViThuHuongDAO.updateByPrimaryKeySelective(donVi);
	            			numUpdate++;
	        			}
	        			else
	        			{
	        				donVi.setCreateDate(new Date());
	            			donVi.setCreatedBy(username);
	            			qldnDonViThuHuongDAO.insertSelective(donVi);
	            			numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
		
		
	}

	private String convertFileQldnPhanBoChiPhi(List sheetData, String username,
	 Model model, HttpServletRequest request) {
		
		Integer year;
		String loaiChiPhi;
		String tinh;
		Long chiPhi;
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 4) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnPhanBoChiPhi record;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			record = new QldnPhanBoChiPhi();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=3; j++) {
     					data.add("");
     				}
        			
        			year			= data.get(0).toString().trim().equals("")?null:Integer.parseInt(data.get(0).toString().trim());
        			loaiChiPhi		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			tinh			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			chiPhi			= data.get(3).toString().trim().equals("")?null:Long.parseLong(data.get(3).toString().trim());
        			
        			
        			record.setYear(year);
        			record.setLoaiChiPhi(loaiChiPhi);
        			record.setTinh(tinh);
        			record.setChiPhi(chiPhi);
        			
        			QldnPhanBoChiPhi checkExit = qldnPhanBoChiPhiDao.checkPhanBoChiPhi(year,loaiChiPhi,tinh);
        			try {
	        			if (checkExit!=null)
	        			{
	        				record.setId(checkExit.getId());
	        				record.setNguoiSua(username);
	        				record.setNgaySua(new Date());
	        				qldnPhanBoChiPhiDao.updateByPrimaryKeySelective(record);
	            			numUpdate++;
	        			}
	        			else
	        			{
	        				record.setNgayNhap(new Date());
	        				record.setNguoiNhap(username);
	        				qldnPhanBoChiPhiDao.insertSelective(record);
	            			numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
	}

	
	private String convertFileQldnThongTinMayNo(List sheetData, String username,
		 Model model, HttpServletRequest request) {
		
		String idTram;
		String idDtxd;
		String ats;
		String hieuMayNo;
		String congSuat;
		String dinhMuc;
		String soHopDong;
		String tenChuNha;
		String loaiNhienLieu;
		String loaiThanhToan;
		String tenXhhVtt;
		String ghiChu;
		String tinhTrang;
		String mucTai;
		String dmDien;
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 15) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnThongTinMayNo record;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			record = new QldnThongTinMayNo();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=14; j++) {
     					data.add("");
     				}
        			
        			
        			
        			idTram			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			idDtxd			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			ats			    = data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			hieuMayNo		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			congSuat		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			dinhMuc			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			soHopDong		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			tenChuNha		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			loaiNhienLieu	= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			loaiThanhToan	= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			tenXhhVtt		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			ghiChu			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			tinhTrang		= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			mucTai			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			dmDien			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			
        			record.setIdTram(idTram);
        			record.setIdDtxd(idDtxd);
        			record.setAts(ats);
        			record.setHieuMayNo(hieuMayNo);
        			record.setCongSuat(congSuat);
        			record.setDinhMuc(dinhMuc);
        			record.setSoHopDong(soHopDong);
        			record.setTenChuNha(tenChuNha);
        			record.setLoaiNhienLieu(loaiNhienLieu);
        			record.setLoaiThanhToan(loaiThanhToan);
        			record.setTenXhhVtt(tenXhhVtt);
        			record.setGhiChu(ghiChu);
        			record.setTinhTrang(tinhTrang);
        			record.setMucTai(mucTai);
        			record.setDmDien(dmDien);
        			
        			QldnThongTinMayNo checkExit = qldnThongTinMayNoDao.checkThongTinMayNo(idTram,idDtxd);
        			try {
	        			if (checkExit!=null)
	        			{
	        				record.setId(checkExit.getId());
	        				record.setNguoiSua(username);
	        				record.setNgaySua(new Date());
	        				qldnThongTinMayNoDao.updateByPrimaryKeySelective(record);
	            			numUpdate++;
	        			}
	        			else
	        			{
	        				record.setNgayTao(new Date());
	        				record.setNguoiTao(username);
	        				qldnThongTinMayNoDao.insertSelective(record);
	            			numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
	}

	private String convertFileQldnThongTinTram(List sheetData, String typeFile, String username,
		 Model model, HttpServletRequest request) {
		
		String idTram;
		String tentramomc;
		String loaitram;
		String diachitram;
		String nguonccd;
		String chitietNc;
		String httt;
		String dienDvth;
		String makh;
		String tentramhd;
		String sohd;
		String soct;
		String dgLoai;
		String dg1Gia;
		String dg3Muc1;
		String dg3Muc2;
		String dg3Muc3;
		String dgTlKd;
		String dgTlKdGia;
		String dgTlSx;
		String dgTlSxGia;
		String ghichu;
		String tontai;
		String tgttTq;
		String tgttCus;
		String ngayps;
		String thangpsdien;
		String thanghuytram;
		String cstramDk;
		String dnttDk;
		String giaiphaptietkiem;
		String tgThuchien;
		String nguoittdien;
		String soLuongBTS;
		String soMayLanh;
		String soTuNguon;
		String onAp;
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		List<QldnThongTinTram> failedList = new ArrayList<QldnThongTinTram>();
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 37) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnThongTinTram record;
        		QldnDonGiaDien record2;
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			record = new QldnThongTinTram();
        			record2 = new QldnDonGiaDien();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=36; j++) {
     					data.add("");
     				}
        			
        			idTram			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			tentramomc		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			loaitram		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			diachitram		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			nguonccd			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			chitietNc			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			httt			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			dienDvth		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			makh		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			tentramhd			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			sohd		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			soct			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			dgLoai		= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			dg1Gia			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			dg3Muc1			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			dg3Muc2			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			dg3Muc3			= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			dgTlKd			= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			dgTlKdGia			= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			dgTlSx			= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			dgTlSxGia			= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim();
        			ghichu		= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim();
        			tontai			= data.get(22).toString().trim().equals("")?null:data.get(22).toString().trim();
        			tgttTq		= data.get(23).toString().trim().equals("")?null:data.get(23).toString().trim();
        			tgttCus			= data.get(24).toString().trim().equals("")?null:data.get(24).toString().trim();
        			ngayps			= data.get(25).toString().trim().equals("")?null:data.get(25).toString().trim();
        			thangpsdien			= data.get(26).toString().trim().equals("")?null:data.get(26).toString().trim();
        			thanghuytram			= data.get(27).toString().trim().equals("")?null:data.get(27).toString().trim();
        			cstramDk			= data.get(28).toString().trim().equals("")?null:data.get(28).toString().trim();
        			dnttDk		= data.get(29).toString().trim().equals("")?null:data.get(29).toString().trim();
        			giaiphaptietkiem	= data.get(30).toString().trim().equals("")?null:data.get(30).toString().trim();
        			tgThuchien		= data.get(31).toString().trim().equals("")?null:data.get(31).toString().trim();
        			nguoittdien			= data.get(32).toString().trim().equals("")?null:data.get(32).toString().trim();
        			soLuongBTS= data.get(33).toString().trim().equals("")?null:data.get(33).toString().trim();
        			soMayLanh		= data.get(34).toString().trim().equals("")?null:data.get(34).toString().trim();
        			soTuNguon  	= data.get(35).toString().trim().equals("")?null:data.get(35).toString().trim();
        			onAp		= data.get(36).toString().trim().equals("")?null:data.get(36).toString().trim();
        			
        			record.setIdTram(idTram);
        			record.setChitietNc(chitietNc);
        			record.setCstramDk(cstramDk);
        			record.setDiachitram(diachitram);
        			record.setDienDvth(dienDvth);
        			record.setDnttDk(dnttDk);
        			record.setGhichu(ghichu);
        			record.setGiaiphaptietkiem(giaiphaptietkiem);
        			record.setHttt(httt);
        			record.setLoaitram(loaitram);
        			record.setMakh(makh);
        			record.setNguoittdien(nguoittdien);
        			record.setNguonccd(nguonccd);
        			record.setSoct(soct);
        			record.setSohd(sohd);
        			record.setTentramhd(tentramhd);
        			record.setTentramomc(tentramomc);
        			record.setTontai(tontai);
        			record.setTgttCus(tgttCus);
    				record.setTgttTq(tgttTq);
    				//record.setOnAp(onAp);
    				
        			record2.setIdTram(idTram);
        			record2.setDg1Gia(dg1Gia);
        			record2.setDg3Muc1(dg3Muc1);
        			record2.setDg3Muc2(dg3Muc2);
        			record2.setDg3Muc3(dg3Muc3);
        			record2.setDgLoai(dgLoai);
        			record2.setDgTlKd(dgTlKdGia);
        			record2.setDgTlKdGia(dgTlKdGia);
        			record2.setDgTlSx(dgTlSxGia);
        			record2.setDgTlSxGia(dgTlSxGia);
        			
        			QldnThongTinTram checkExit = qldnThongTinTramDao.selectByPrimaryKey(idTram);
        			
        			try {
        				if (tgThuchien!=null &&!tgThuchien.equals(""))
        					record.setTgThuchien(df_full.parse(tgThuchien));
        				if (thanghuytram!=null &&!thanghuytram.equals(""))
        					record.setThanghuytram(df_full.parse(thanghuytram));
        				if (thangpsdien!=null &&!thangpsdien.equals(""))
        					record.setThangpsdien(df_full.parse(thangpsdien));
        				if (ngayps!=null &&!ngayps.equals(""))
        					record.setNgayps(df_full.parse(ngayps));
        				else
        					record.setNgayps(new Date());
        				/*if (soLuongBTS!=null &&!soLuongBTS.equals(""))
        					record.setSoLuongBTS(Integer.parseInt(soLuongBTS));*/
        				if (soMayLanh!=null &&!soMayLanh.equals(""))
        					record.setSoMayLanh(Integer.parseInt(soMayLanh));
        				if (soTuNguon!=null &&!soTuNguon.equals(""))
        					record.setSoTuNguon(Integer.parseInt(soTuNguon));
        				
        				VAlHCell siteInfo = vAlHCellDao.getInfoSite(record.getIdTram());
    					if (siteInfo!=null) {
    						record.setTinh(siteInfo.getProvince());
    						record.setHuyen(siteInfo.getDistrict());
    					}
	        			if (checkExit!=null)
	        			{
	        				record.setNguoiSua(username);
	        				record.setNgaySua(new Date());
	        				qldnThongTinTramDao.updateByPrimaryKey(record);
	        				qldnDonGiaDienDao.updateByPrimaryKey(record2);
	            			numUpdate++;
	        			}
	        			else
	        			{
	        				record.setNgayTao(new Date());
	        				record.setNguoiTao(username);
	        				qldnThongTinTramDao.insert(record);
	        				qldnDonGiaDienDao.insert(record2);
	            			numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			failedList.add(record);
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
		
	}

	private String convertFileQldnTramTTNhienLieu(List sheetData,
			String username,Model model,
			HttpServletRequest request) {

		String idTram;
		String ngayChayMf;
		String tgBatDau;
		String tgKetThuc;
		String donGia;
		String ghiChu;
		String dinhMuc;
		String tongLitDm;
		String soHQuyDoi;
		String thanhTien;
		String ngayThanhToan;
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 11) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnTramTTNhienLieu record;
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			record = new QldnTramTTNhienLieu();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=10; j++) {
     					data.add("");
     				}
        			
        			idTram			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			ngayChayMf		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			tgBatDau		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			tgKetThuc		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			donGia			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			ghiChu			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			dinhMuc			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			tongLitDm		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			soHQuyDoi		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			thanhTien		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			ngayThanhToan	= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			
        			record.setIdTram(idTram);
        			record.setGhiChu(ghiChu);
        			record.setDinhMuc(dinhMuc);
        			record.setTongLitDm(tongLitDm);
        			
        			try {
        				
        				if (ngayChayMf!=null &&!ngayChayMf.equals(""))
        					record.setNgayChayMf(df_full.parse(ngayChayMf));
        				if (tgBatDau!=null &&!tgBatDau.equals(""))
        					record.setTgBatDau(df_full.parse(tgBatDau));
        				if (tgKetThuc!=null &&!tgKetThuc.equals(""))
        					record.setTgKetThuc(df_full.parse(tgKetThuc));
        				if (ngayThanhToan!=null &&!ngayThanhToan.equals(""))
        					record.setNgayThanhToan(df_full.parse(ngayThanhToan));
        				if (donGia!=null &&!donGia.equals(""))
        					record.setDonGia(Long.parseLong(donGia));
        				if (soHQuyDoi!=null &&!soHQuyDoi.equals(""))
        					record.setSoHQuyDoi(Integer.parseInt(soHQuyDoi));
        				if (thanhTien!=null &&!thanhTien.equals(""))
        					record.setThanhTien(Long.parseLong(thanhTien));
        				
        				QldnTramTTNhienLieu checkExit = qldnTramTTNhienLieuDao.selectByKey(idTram,record.getNgayChayMf(),record.getTgBatDau());
            			
	        			if (checkExit!=null)
	        			{
	        				record.setId(checkExit.getId());
	        				record.setNvSua(username);
	        				record.setNgaySua(new Date());
	        				qldnTramTTNhienLieuDao.updateByPrimaryKey(record);
	        				numUpdate++;
	        			}
	        			else
	        			{
	        				record.setNgayNhap(new Date());
	        				record.setNvNhap(username);
	        				qldnTramTTNhienLieuDao.insert(record);
	        				numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
	}

	private String convertQldnTramTTDien(List sheetData, String username,
			 Model model, HttpServletRequest request) {
		
		String idTram;
		String makh;
		String soct;
		String tgttTq;
		String thangQuyTt;
		String namTt;
		String tuNgay;
		String denNgay;
		String csc1;
		String csc2;
		String csc3;
		String csm1;
		String csm2;
		String csm3;
		String tienTt;
		String kqToKt;
		String ghiChu;
		String ngayTttw;
		String ngayUnc;
		String soHoaDon;
		String soHskt;
		long dienNangTt=0;
		long chenhLechDn= 0;
		long chenhLechTien= 0;
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 20) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnTramTTDien record;
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			String error="";
        			String con="";
        			record = new QldnTramTTDien();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=19; j++) {
     					data.add("");
     				}
        			
        			makh		= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			soct		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			tgttTq		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			thangQuyTt		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			namTt	= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			tuNgay		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			denNgay		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			csc1		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim().replaceAll("-", "");
        			csc2		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim().replaceAll("-", "");
        			csc3		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim().replaceAll("-", "");
        			csm1		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim().replaceAll("-", "");
        			csm2		= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim().replaceAll("-", "");
        			csm3		= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim().replaceAll("-", "");
        			tienTt		= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			kqToKt		= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			ghiChu		= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			ngayTttw		= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			ngayUnc	= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			soHoaDon	= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			soHskt		= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			dienNangTt=0;
        			record.setTgttTq(tgttTq);
        			record.setKqToKt(kqToKt);
        			record.setGhiChu(ghiChu);
        			record.setSoHoaDon(soHoaDon);
        			record.setSoHskt(soHskt);
        			record.setGhiChu(ghiChu);
        			record.setMakh(makh);
        			record.setSoct(soct);
        			if ( makh!=null&&!makh.equals(""))
    				{
    					QldnThongTinTram thongTinTram = qldnThongTinTramDao.getTramByMaKH(makh,soct);
    					if (thongTinTram!=null) {
    						idTram = thongTinTram.getIdTram();
    						record.setIdTram(idTram);
    					} else {
    						error=error+con+"Chưa cấu hình mapping PE:"+makh+",Số CT:"+soct;
    						con=",";
    					}
    				}
        			try {
        				if (thangQuyTt!=null &&!thangQuyTt.equals("")) {
        					record.setThangQuyTt(Integer.parseInt(thangQuyTt));
        				}else {
        					error=error+con+"Chưa nhập tháng thanh toán";
        					con=",";}
        				if (namTt!=null &&!namTt.equals("")) {
        					record.setNamTt(Integer.parseInt(namTt));
	        			}else {
	    					error=error+con+"Chưa nhập năm thanh toán";
	    					con=",";}
        				if (tuNgay!=null &&!tuNgay.equals(""))
        					record.setTuNgay(df.parse(tuNgay));
        				if (denNgay!=null &&!denNgay.equals("")) {
        					record.setDenNgay(df.parse(denNgay));
        					if (record.getDenNgay().compareTo(record.getTuNgay())<0) {
		    					error=error+con+" Thời gian kết thúc ("+denNgay+") phải bé hơn thời gian bắt đầu ("+tuNgay+")";
		    					con=",";
        					}
        					String mon= denNgay.substring(3, 5);
        					if (thangQuyTt!=null && Integer.parseInt(thangQuyTt)!=Integer.parseInt(mon))
        					{
        						error=error+con+" Không trung khớp giữa tháng/quý thanh toán ("+thangQuyTt+") với ngày kết thúc ("+denNgay+") ";
		    					con=",";
        					}
	    				}
        				if (csm1!=null &&!csm1.equals(""))
        				{
        					dienNangTt=dienNangTt+ Long.parseLong(csm1);
        					record.setCsm1(Long.parseLong(csm1));
        				}
        					
        				if (csm2!=null &&!csm2.equals(""))
        				{
        					dienNangTt=dienNangTt+ Long.parseLong(csm2);
        					record.setCsm2(Long.parseLong(csm2));
        				}
        				if (csm3!=null &&!csm3.equals(""))
        				{
        					dienNangTt= dienNangTt+ Long.parseLong(csm3);
        					record.setCsm3(Long.parseLong(csm3));
        				}
        				if (csc1!=null &&!csc1.equals(""))
        				{
        					dienNangTt=dienNangTt- Long.parseLong(csc1);
        					record.setCsc1(Long.parseLong(csc1));
        				}
        				if (csc2!=null &&!csc2.equals(""))
        				{
        					dienNangTt=dienNangTt- Long.parseLong(csc2);
        					record.setCsc2(Long.parseLong(csc2));
        				}
        					
        				if (csc3!=null &&!csc3.equals(""))
        				{
        					dienNangTt=dienNangTt- Long.parseLong(csc3);
        					record.setCsc3(Long.parseLong(csc3));
        				}
        				
        				if (tienTt!=null &&!tienTt.equals(""))
        					record.setTienTt(Long.parseLong(tienTt));
        				if (ngayTttw!=null &&!ngayTttw.equals(""))
        					record.setNgayTttw(df.parse(ngayTttw));
        				if (ngayUnc!=null &&!ngayUnc.equals(""))
        					record.setNgayUnc(df.parse(ngayUnc));
    				
        				record.setDienNangTt(dienNangTt);
        				if (error.equals(""))
        				{
	        				Integer thangTruoc;
	        				Integer namTruoc;
	        				if (record.getThangQuyTt()==1) {
	        					thangTruoc= 12;
	        					namTruoc = record.getNamTt() -1;
	        				}
	        				else
	        				{
	        					thangTruoc= record.getThangQuyTt() -1;
	        					namTruoc = record.getNamTt();
	        				}
	        				
	        				QldnTramTTDien ttThangTruoc = qldnTramTTDienDao.selectByKey(record.getIdTram(),thangTruoc,namTruoc);
	            			if (ttThangTruoc!=null)
	            			{
	            				chenhLechDn = record.getDienNangTt() - ttThangTruoc.getDienNangTt();
	            				record.setChenhLechDn(chenhLechDn);
	            				chenhLechTien = record.getTienTt() - ttThangTruoc.getTienTt();
	            				record.setChenhLechTien(chenhLechTien);
	            			}
	        				
	        				QldnTramTTDien checkExit = qldnTramTTDienDao.checkExit(record.getMakh(),tuNgay,denNgay);
	            			if (checkExit!=null)
		        			{
		        				record.setId(checkExit.getId());
		        				record.setNvSua(username);
		        				record.setNgaySua(new Date());
		        				qldnTramTTDienDao.updateByPrimaryKey(record);
		        				numUpdate++;
		        			}
		        			else
		        			{
		        				record.setNgayNhap(new Date());
		        				record.setNvNhap(username);
		        				qldnTramTTDienDao.insert(record);
		        				numInsert++;
		        			}
        				}
	
        			}
	        		catch (Exception exp){
	        			error=error+con+" Lỗi trong quá trình insert";
    					con=",";
	        		} 
        			if (!error.equals(""))
        			{
        				numError++;
	        			numRecodeError=numRecodeError+conn+"Lỗi dòng ("+i+ "): "+error;
	        			conn = "<br>";
        			}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " <br>Kết quả import lỗi: <br> "+numRecodeError;
		
		String resultImport = "Result: <br> Số bản nghi lỗi:"+numError+numRecodeError+ ",<br> Record insert:"+numInsert+ ",<br> Record update:"+numUpdate;
		return resultImport;
	}
	
	private String convertFileQldnQlMatBang(List sheetData, String username,
			Model model, HttpServletRequest request) {
			
		String maTram;
		String idHopdong;
		String diachitram;
		String diachilienhe;
		String sdtlienhe;
		String tinh;
		String huyen;
		String phuongxa;
		String toquanly;
		String htSohuu;
		String chuthehd;
		String sohd;
		String nhTenchutk;
		String nhSotk;
		String nhTennh;
		String nhTencn;
		String ttTungay;
		String ttDenngay;
		String ttSotientruocvat;
		String ttVat;
		String ttSotiensauvat;
		String ngaySoPhu;
		String ttNgaytieptheo;
		String ttDenngaytieptheo;
		String ttSotiencktt;
		String hdThoihan;
		String hdNgayky;
		String hdNgayhieuluc;
		String hdNgayketthuc;
		String hdNgayhethan;
		String hdGiaquydinh;
		String hdGiamb;
		String hdGiapbts;
		String hdGiapmpd;
		String hdGiacotanten;
		String hdKhoandien;
		String hdGiakhac;
		String hdDongiathangVat;
		String hdVat;
		String dongiathangNovat;
		String giacu;
		String chiphigiamThang;
		String sothangggNam;
		String sotienggNam;
		String cmnd;
		String cmndNgaycap;
		String cmndNoicap;
		String mst;
		String thuesuatvat;
		String chukytt;
		String kinhdo;
		String vido;
		String sdtUctt;
		String ngaytinhtien;
		String ngayps;
		String cnqsdd;
		String cnqsddCu;
		String phongBts;
		String phongMn;
		String anten;
		String noteVip;
		String noteUqhd;
		String noteThuhoitien;
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 63) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnQlMatBang record;
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			record = new QldnQlMatBang();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=62; j++) {
     					data.add("");
     				}
        			
        			maTram		= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			idHopdong	= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			diachitram	= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			diachilienhe= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			sdtlienhe	= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			tinh		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			huyen		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			phuongxa	= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			toquanly	= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			htSohuu		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			chuthehd	= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			sohd		= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			nhTenchutk	= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			nhSotk		= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			nhTennh		= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			nhTencn		= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			ttTungay	= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			ttDenngay	= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			ttSotientruocvat= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			ttVat		= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			ttSotiensauvat= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim();
        			ngaySoPhu	= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim();
        			ttNgaytieptheo= data.get(22).toString().trim().equals("")?null:data.get(22).toString().trim();
        			ttDenngaytieptheo= data.get(23).toString().trim().equals("")?null:data.get(23).toString().trim();
        			ttSotiencktt	= data.get(24).toString().trim().equals("")?null:data.get(24).toString().trim();
        			hdThoihan		= data.get(25).toString().trim().equals("")?null:data.get(25).toString().trim();
        			hdNgayky		= data.get(26).toString().trim().equals("")?null:data.get(26).toString().trim();
        			hdNgayhieuluc	= data.get(27).toString().trim().equals("")?null:data.get(27).toString().trim();
        			hdNgayketthuc	= data.get(28).toString().trim().equals("")?null:data.get(28).toString().trim();
        			hdNgayhethan	= data.get(29).toString().trim().equals("")?null:data.get(29).toString().trim();
        			hdGiaquydinh	= data.get(30).toString().trim().equals("")?null:data.get(30).toString().trim();
        			hdGiamb			= data.get(31).toString().trim().equals("")?null:data.get(31).toString().trim();
        			hdGiapbts		= data.get(32).toString().trim().equals("")?null:data.get(32).toString().trim();
        			hdGiapmpd		= data.get(33).toString().trim().equals("")?null:data.get(33).toString().trim();
        			hdGiacotanten	= data.get(34).toString().trim().equals("")?null:data.get(34).toString().trim();
        			hdKhoandien		= data.get(35).toString().trim().equals("")?null:data.get(35).toString().trim();
        			hdGiakhac		= data.get(36).toString().trim().equals("")?null:data.get(36).toString().trim();
        			hdDongiathangVat= data.get(37).toString().trim().equals("")?null:data.get(37).toString().trim();
        			hdVat			= data.get(38).toString().trim().equals("")?null:data.get(38).toString().trim();
        			dongiathangNovat= data.get(39).toString().trim().equals("")?null:data.get(39).toString().trim();
        			giacu			= data.get(40).toString().trim().equals("")?null:data.get(40).toString().trim();
        			chiphigiamThang	= data.get(41).toString().trim().equals("")?null:data.get(41).toString().trim();
        			sothangggNam	= data.get(42).toString().trim().equals("")?null:data.get(42).toString().trim();
        			sotienggNam		= data.get(43).toString().trim().equals("")?null:data.get(43).toString().trim();
        			cmnd			= data.get(44).toString().trim().equals("")?null:data.get(44).toString().trim();
        			cmndNgaycap		= data.get(45).toString().trim().equals("")?null:data.get(45).toString().trim();
        			cmndNoicap		= data.get(46).toString().trim().equals("")?null:data.get(46).toString().trim();
        			mst				= data.get(47).toString().trim().equals("")?null:data.get(47).toString().trim();
        			thuesuatvat		= data.get(48).toString().trim().equals("")?null:data.get(48).toString().trim();
        			chukytt			= data.get(49).toString().trim().equals("")?null:data.get(49).toString().trim();
        			kinhdo			= data.get(50).toString().trim().equals("")?null:data.get(50).toString().trim();
        			vido			= data.get(51).toString().trim().equals("")?null:data.get(51).toString().trim();
        			sdtUctt			= data.get(52).toString().trim().equals("")?null:data.get(52).toString().trim();
        			ngaytinhtien	= data.get(53).toString().trim().equals("")?null:data.get(53).toString().trim();
        			ngayps			= data.get(54).toString().trim().equals("")?null:data.get(54).toString().trim();
        			cnqsdd			= data.get(55).toString().trim().equals("")?null:data.get(55).toString().trim();
        			cnqsddCu		= data.get(56).toString().trim().equals("")?null:data.get(56).toString().trim();
        			phongBts		= data.get(57).toString().trim().equals("")?null:data.get(57).toString().trim();
        			phongMn			= data.get(58).toString().trim().equals("")?null:data.get(58).toString().trim();
        			anten			= data.get(59).toString().trim().equals("")?null:data.get(59).toString().trim();
        			noteVip			= data.get(60).toString().trim().equals("")?null:data.get(60).toString().trim();
        			noteUqhd		= data.get(61).toString().trim().equals("")?null:data.get(61).toString().trim();
        			noteThuhoitien	= data.get(62).toString().trim().equals("")?null:data.get(62).toString().trim();
        			
        			record.setAnten(anten);
        			record.setChukytt(chukytt);
        			record.setChuthehd(chuthehd);
        			record.setCmnd(cmnd);
        			record.setCmndNoicap(cmndNoicap);
        			record.setCnqsdd(cnqsdd);
        			record.setCnqsddCu(cnqsddCu);
        			record.setDiachilienhe(diachilienhe);
        			record.setDiachitram(diachitram);
        			record.setHdKhoandien(hdKhoandien);
        			record.setHdThoihan(hdThoihan);
        			record.setHtSohuu(htSohuu);
        			record.setHuyen(huyen);
        			record.setIdHopdong(idHopdong);
        			record.setKinhdo(kinhdo);
        			record.setMaTram(maTram);
        			record.setMst(mst);
        			record.setNhSotk(nhSotk);
        			record.setNhTenchutk(nhTenchutk);
        			record.setNhTencn(nhTencn);
        			record.setNhTennh(nhTennh);
        			record.setNoteThuhoitien(noteThuhoitien);
        			record.setNoteUqhd(noteUqhd);
        			record.setNoteVip(noteVip);
        			record.setPhongBts(phongBts);
        			record.setPhongMn(phongMn);
        			record.setPhuongxa(phuongxa);
        			record.setSdtlienhe(sdtlienhe);
        			record.setSdtUctt(sdtUctt);
        			record.setSohd(sohd);
        			record.setThuesuatvat(thuesuatvat);
        			record.setTinh(ngaytinhtien);
        			record.setToquanly(toquanly);
        			record.setVido(vido);
        			
        			try {
        			
        				if (chiphigiamThang!=null &&!chiphigiamThang.equals(""))
        					record.setChiphigiamThang(Long.parseLong(chiphigiamThang));
        				if (dongiathangNovat!=null &&!dongiathangNovat.equals(""))
        					record.setDongiathangNovat(Long.parseLong(dongiathangNovat));
        				if (giacu!=null &&!giacu.equals(""))
        					record.setGiacu(Long.parseLong(giacu));
        				if (hdDongiathangVat!=null &&!hdDongiathangVat.equals(""))
        					record.setHdDongiathangVat(Long.parseLong(hdDongiathangVat));
        				if (hdGiacotanten!=null &&!hdGiacotanten.equals(""))
        					record.setHdGiacotanten(Long.parseLong(hdGiacotanten));
        				if (hdGiakhac!=null &&!hdGiakhac.equals(""))
        					record.setHdGiakhac(Long.parseLong(hdGiakhac));
        				if (hdGiamb!=null &&!hdGiamb.equals(""))
        					record.setHdGiamb(Long.parseLong(hdGiamb));
        				if (hdGiapbts!=null &&!hdGiapbts.equals(""))
        					record.setHdGiapbts(Long.parseLong(hdGiapbts));
        				if (hdGiaquydinh!=null &&!hdGiaquydinh.equals(""))
        					record.setHdGiaquydinh(Long.parseLong(hdGiaquydinh));
        				if (hdVat!=null &&!hdVat.equals(""))
        					record.setHdVat(Long.parseLong(hdVat));
        				if (sothangggNam!=null &&!sothangggNam.equals(""))
        					record.setSothangggNam(Integer.parseInt(sothangggNam));
        				if (sotienggNam!=null &&!sotienggNam.equals(""))
        					record.setSotienggNam(Integer.parseInt(sotienggNam));
        				if (ttSotiencktt!=null &&!ttSotiencktt.equals(""))
        					record.setTtSotiencktt(Long.parseLong(ttSotiencktt));
        				if (ttSotiensauvat!=null &&!ttSotiensauvat.equals(""))
        					record.setTtSotiensauvat(Long.parseLong(ttSotiensauvat));
        				if (ttSotientruocvat!=null &&!ttSotientruocvat.equals(""))
        					record.setTtSotientruocvat(Long.parseLong(ttSotientruocvat));
        				if (ttVat!=null &&!ttVat.equals(""))
        					record.setTtVat(Long.parseLong(ttVat));
        				
        				if (cmndNgaycap!=null &&!cmndNgaycap.equals(""))
        					record.setCmndNgaycap(df_full.parse(cmndNgaycap));
        				if (hdNgayhethan!=null &&!hdNgayhethan.equals(""))
        					record.setHdNgayhethan(df_full.parse(hdNgayhethan));
        				if (hdNgayhieuluc!=null &&!hdNgayhieuluc.equals(""))
        					record.setHdNgayhieuluc(df_full.parse(hdNgayhieuluc));
        				if (hdNgayketthuc!=null &&!hdNgayketthuc.equals(""))
        					record.setHdNgayketthuc(df_full.parse(hdNgayketthuc));
        				if (hdNgayky!=null &&!hdNgayky.equals(""))
        					record.setHdNgayky(df_full.parse(hdNgayky));
        				if (ngayps!=null &&!ngayps.equals(""))
        					record.setNgayps(df_full.parse(ngayps));
        				if (ngaySoPhu!=null &&!ngaySoPhu.equals(""))
        					record.setNgaySoPhu(df_full.parse(ngaySoPhu));
        				if (ngaytinhtien!=null &&!ngaytinhtien.equals(""))
        					record.setNgaytinhtien(df_full.parse(ngaytinhtien));
        				if (ttDenngay!=null &&!ttDenngay.equals(""))
        					record.setTtDenngay(df_full.parse(ttDenngay));
        				if (ttDenngaytieptheo!=null &&!ttDenngaytieptheo.equals(""))
        					record.setTtDenngaytieptheo(df_full.parse(ttDenngaytieptheo));
        				if (ttNgaytieptheo!=null &&!ttNgaytieptheo.equals(""))
        					record.setTtNgaytieptheo(df_full.parse(ttNgaytieptheo));
        				if (ttTungay!=null &&!ttTungay.equals(""))
        					record.setTtTungay(df_full.parse(ttTungay));
        				
        				QldnQlMatBang checkExit = qldnQlMatBangDao.selectByKey(maTram,idHopdong);
            			
	        			if (checkExit!=null)
	        			{
	        				record.setId(checkExit.getId());
	        				record.setNguoiSua(username);
	        				record.setNgaySua(new Date());
	        				qldnQlMatBangDao.updateByPrimaryKey(record);
	        				numUpdate++;
	        			}
	        			else
	        			{
	        				record.setNgayTao(new Date());
	        				record.setNguoiTao(username);
	        				qldnQlMatBangDao.insert(record);
	        				numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		
        		}
        	}
		}
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
		}

// import file thong tin tram fomat mien tay
	private String convertFileQldnThongTinTramHCM(List sheetData,String typeFile,String username, Model model,
			HttpServletRequest request) {
		
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		//check xem user co quyen thuc hien tren du lieu cua cac DVT khong
		int checkUserRole = sysUsersDao.getUserRoleQLDN(username);
		
		String idTram;
		String makh;
		String soct;
		String dienDvth;
		String ghichu;
		String loaitram=null;
		String daiVT=null;
		String tu2G=null;
		String tu3G=null;
		String tu4G=null;
		// check=1: user thuoc phong ky thuat. Lay thong tin dai vt tu du lieu import, nguoc lai: lay thong tin dai vt la phong ban cua user truc thuoc
		
		int numInsert=0;
		int numUpdate=0;
		int numError = 0;
		String numRecodeError="";
		String conn="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
			if (checkUserRole!=1 && heard.size() != 5) {
				saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");	
        		return "jspQLDN/importQLDN";
			}
        	if (heard.size() != 10 && heard.size() != 5) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		return "jspQLDN/importQLDN";
        	}
        	
        	if (sheetData.size() > 1) {
        		QldnThongTinTram record;
        		for (int i=1; i<sheetData.size(); i++) {
        			
        			record = new QldnThongTinTram();
        			List data= (List) sheetData.get(i);
        			if (heard.size() > 5) {
	        			for (int j=data.size(); j<=9; j++) {
	     					data.add("");
	     				}
        			}
        			else
        			{
        				for (int j=data.size(); j<=4; j++) {
	     					data.add("");
	     				}
        			}
        			idTram			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			makh			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			soct			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			dienDvth		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			ghichu			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			if (checkUserRole==1 && heard.size() > 7) {
        				loaitram			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
            			daiVT = data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
            			tu2G = data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
            			tu3G = data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
            			tu4G = data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			}
        			else
        			{
        				daiVT = userLogin.getMaPhong();
        			}
        			record.setIdTram(idTram);
        			record.setDienDvth(dienDvth);
        			record.setGhichu(ghichu);
        			record.setMakh(makh);
        			record.setSoct(soct);
        			record.setLoaitram(loaitram);
        			record.setNhom(daiVT);
    				record.setTu2G(tu2G);
    				record.setTu3G(tu3G);
    				record.setTu4G(tu4G);
        			if (makh==null||(makh!=null&&makh.equals("")))
        			{
        				numError++;
	        			numRecodeError=numRecodeError+conn+i+": Chưa nhập Mã PE";
	        			conn = ",";
        			}
        			else
        			{
        			//QldnThongTinTram checkExit = qldnThongTinTramDao.selectByMaKH(makh);
        			QldnThongTinTram checkExit = qldnThongTinTramDao.selectByPrimaryKey(idTram);
        			try {
        				record.setNgayps(new Date());
        				
    					if (checkExit!=null)
	        			{
    						if (checkExit.getNhom()!=null && !checkExit.getNhom().equals(daiVT))
    						{
    							numError++;
    		        			numRecodeError=numRecodeError+conn+i+": Nhập sai trạm của phòng ban khác";
    		        			conn = ",";
    						}
    						else
    						{
		        				record.setNguoiSua(username);
		        				record.setNgaySua(new Date());
		        				qldnThongTinTramDao.updateByPrimaryKeySelective(record);
		            			numUpdate++;
    						}
	        			}
	        			else
	        			{
	        				record.setNgayTao(new Date());
	        				record.setNguoiTao(username);
	        				qldnThongTinTramDao.insert(record);
	        				numInsert++;
	        			}
	
	        		}
	        		catch (Exception exp){
	        			numError++;
	        			numRecodeError=numRecodeError+conn+i;
	        			conn = ",";
	        		}
        		  }
        		}
        	}
		}
		/*if (numInsert>0 ||numUpdate>0 ) {
			qldnThongTinTramDao.updateInforDatasite(null);
		}*/
		
		if (numError>0)
			numRecodeError = " (Error at line: "+numRecodeError+")";
		
		String resultImport = "Result: record error:"+numError+numRecodeError+ ", record insert:"+numInsert+ ", record update:"+numUpdate;
		return resultImport;
	}
	
}
