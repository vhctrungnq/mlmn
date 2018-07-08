package controller.assets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;


import vo.AsCards;
import vo.AsProposedWarehouse;
import vo.alarm.utils.DateTools;

import dao.AsCardsDAO;
import dao.AsExportWarehouseDAO;
import dao.AsProposedWarehouseDAO;

/**
 * Ten file: XetDuyetController.java
 * Muc dich: Xet duyet thiet bi
 * @author QBu
 * Ngay tao: 11/06/2013
 * Lich su thay doi:
 *  
 */
@Controller
@RequestMapping("/assets/xet-duyet-thiet-bi/*")
public class XetDuyetController extends BaseController{

	@Autowired
	private AsProposedWarehouseDAO asProposedWarehouseDAO;
	@Autowired
	private AsExportWarehouseDAO asExportWarehouseDao; 
	@Autowired
	private AsCardsDAO asCardsDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_hh = new SimpleDateFormat("HH");
	private DateFormat df_mm = new SimpleDateFormat("mm");
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String exportDateFrom,
			 				 @RequestParam(required = false) String exportDateTo,
			 				 @RequestParam(required = false) String votesNo,
			 				 @RequestParam(required = false) String usersEx,
			 				 @RequestParam(required = false) String description,
			 				 @RequestParam(required = false) String baoGomThietBi,
			 				 @RequestParam(required = false) String action,
			 				 @RequestParam(required = false) String keySoLuong,
				 			 @RequestParam(required = false) String keyId,
			 				 @ModelAttribute("filter") AsProposedWarehouse filter,
			 				 ModelMap model, HttpServletRequest request, HttpServletResponse response) throws JRException, IOException
	{
		
		if(filter.getBoPhanSd() != null)
			filter.setBoPhanSd(filter.getBoPhanSd().trim());
		if(filter.getDonViSd() != null)
			filter.setDonViSd(filter.getDonViSd().trim());
		if(filter.getNguoiSd() != null)
			filter.setNguoiSd(filter.getNguoiSd().trim());
		if(filter.getCreatedBy() != null)
			filter.setCreatedBy(filter.getCreatedBy().trim());
		model.addAttribute("action", action);
		
		// Ngay thang
 		if (exportDateFrom != null && !exportDateFrom.equals("") && DateTools.isValid("dd/MM/yyyy", exportDateFrom)==false) {			

 			exportDateFrom = df.format(DateTools.startMonth(new Date()));
		
		}
 		if (exportDateTo != null && !exportDateTo.equals("") && DateTools.isValid("dd/MM/yyyy", exportDateTo)==false)
 		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			exportDateTo = df.format(cal.getTime());
		}
		 		
		int order = 1;
		String column = "CREATE_DATE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if (action!= null && action.equals("xetDuyet")) {
			
			
			model.addAttribute("dialog", "Y");
			
			if(keySoLuong.equals("")){
				saveMessageKey(request, "messsage.confirm.errorData");
				List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
						filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
						exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
				model.addAttribute("xetDuyetList", xetDuyetList);
				getRownumRowId(xetDuyetList, model,exportDateFrom, exportDateTo
						, votesNo, usersEx, description, baoGomThietBi);
				
				model.addAttribute("dialog", "N");
				return new ModelAndView("jspassets/xetDuyetList");
			}
			
			try{
				String[] value_soLuong = keySoLuong.split(",");
				String[] value_id = keyId.split(",");
				
				for(int i=0;i<value_id.length;i++){
					
					AsProposedWarehouse record = new AsProposedWarehouse();
					record.setId(Integer.parseInt(value_id[i]));
					record.setSoLuong(Integer.parseInt(value_soLuong[i]));
					asProposedWarehouseDAO.updateByPrimaryKeySelective(record);
				}
				
				// Kiem tra so luong yeu cau hop ly hay chua?
				int count = 0;
				String str = "";
				for(int i=0;i<value_id.length;i++){
					int soLuong = Integer.parseInt(value_soLuong[i]);
					for(int j=0;j<i;j++){
						if(asProposedWarehouseDAO.getAmountTwoId(value_id[i], value_id[j]) == 1)
							soLuong += Integer.parseInt(value_soLuong[j]);
					}
					
					int countAmount = asProposedWarehouseDAO.getCountAmount(value_id[i], Integer.toString(soLuong));
					if(countAmount > 0){
						count++;
						
						str = str + i + ",";
						
					}
					
				}
				// To mau
				model.addAttribute("highlight", str);
				if(count > 0){
					
					saveMessageKey(request, "messsage.confirm.errorAmount");
					List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
							filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
							exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
					model.addAttribute("xetDuyetList", xetDuyetList);
					getRownumRowId(xetDuyetList, model,exportDateFrom, exportDateTo
							, votesNo, usersEx, description, baoGomThietBi);
					
					model.addAttribute("dialog", "N");
					return new ModelAndView("jspassets/xetDuyetList");
				}
			}
			catch(Exception e){
				
				saveMessageKey(request, "messsage.confirm.errorXetDuyet");
				List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
						filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
						exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
				model.addAttribute("xetDuyetList", xetDuyetList);
				getRownumRowId(xetDuyetList, model,exportDateFrom, exportDateTo
						, votesNo, usersEx, description, baoGomThietBi);
				model.addAttribute("dialog", "N");
				return new ModelAndView("jspassets/xetDuyetList");
			}
			
			
			// Kiem tra chi xet duyet cho 1 nguoi de nghi trong 1 ngay
			int countCreatedBy = asProposedWarehouseDAO.getCountCreatedBy(filter.getBoPhanSd(),
					filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
					exportDateTo, filter.getCreatedBy());
			if(countCreatedBy > 1)
			{
				saveMessageKey(request, "messsage.confirm.errorCreatedBy");
				List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
						filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
						exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
				model.addAttribute("xetDuyetList", xetDuyetList);
				getRownumRowId(xetDuyetList, model,exportDateFrom, exportDateTo
						, votesNo, usersEx, description, baoGomThietBi);
				model.addAttribute("dialog", "N");
				return new ModelAndView("jspassets/xetDuyetList");
			}
			
			// Kiem tra so phieu da nhap chua?
			if(votesNo.equals("")){
				
				model.addAttribute("errorVotesNo", "*");
				List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
						filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
						exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
				model.addAttribute("xetDuyetList", xetDuyetList);
				getRownumRowId(xetDuyetList, model,exportDateFrom, exportDateTo
						, votesNo, usersEx, description, baoGomThietBi);
				model.addAttribute("dialog", "N");
				return new ModelAndView("jspassets/xetDuyetList");
			}
			//----------------------------------------------------------------------------------------------------
			
			/*List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
					filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
					exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
			model.addAttribute("xetDuyetList", xetDuyetList);
			
			for(int i=0;i<xetDuyetList.size();i++){
				AsProposedWarehouse record = new AsProposedWarehouse();
				record.setId(xetDuyetList.get(i).getId());
				record.setTrangThai("Y");
				record.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setModifyDate(new Date());
				
				asProposedWarehouseDAO.updateByPrimaryKeySelective(record);
				
				AsExportWarehouse asExportWarehouse = new AsExportWarehouse();
				asExportWarehouse.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				asExportWarehouse.setProductCode(xetDuyetList.get(i).getProductCode());
				asExportWarehouse.setSerialNo(xetDuyetList.get(i).getSerialNo());
				asExportWarehouse.setUnit(xetDuyetList.get(i).getDonVi());
				asExportWarehouse.setVendor(xetDuyetList.get(i).getNhaSx());
				asExportWarehouse.setAmountEx(xetDuyetList.get(i).getSoLuong());
				asExportWarehouse.setExportDate(new Date());
				asExportWarehouse.setVotesNo(votesNo);
				asExportWarehouse.setUsersEx(usersEx);
				asExportWarehouse.setOrganization(xetDuyetList.get(i).getBoPhanSd());
				asExportWarehouse.setDepartmentId(xetDuyetList.get(i).getDonViSd());
				asExportWarehouse.setUsers(xetDuyetList.get(i).getNguoiSd());
				asExportWarehouse.setDescription(xetDuyetList.get(i).getDescription());
				asExportWarehouse.setAmountReturn(0);
				asExportWarehouse.setLyDoXuat(xetDuyetList.get(i).getLyDoXuat());
				asExportWarehouseDao.insert(asExportWarehouse);
					
			}*/
		
			/*votesNo = "";
			usersEx = "";
			description = "";
			saveMessageKey(request, "messsage.confirm.xetDuyetThanhCong");*/
		}
		
		List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(filter.getBoPhanSd(),
				filter.getDonViSd(), filter.getNguoiSd(), exportDateFrom,
				exportDateTo, filter.getCreatedBy(), column, order==1? "ASC":"DESC");
		model.addAttribute("xetDuyetList", xetDuyetList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "XetDuyet_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		getRownumRowId(xetDuyetList, model,exportDateFrom, exportDateTo
				, votesNo, usersEx, description, baoGomThietBi);
		
		
		return new ModelAndView("jspassets/xetDuyetList");
	}
	
	private void getRownumRowId(List<AsProposedWarehouse> xetDuyetList, ModelMap model, String exportDateFrom, String exportDateTo
			, String votesNo, String usersEx, String description, String baoGomThietBi){
		//get row_num, rowId  
 		int row_num = 0;
		String rowId = "";
		
		if(xetDuyetList.size() != 0){
			for (int i = 0; i < xetDuyetList.size(); i++) {
				rowId += xetDuyetList.get(i).getId() + ",";
			}
			row_num = xetDuyetList.size();
			rowId = rowId.substring(0, rowId.lastIndexOf(","));
		}
		
		model.addAttribute("row_num", row_num);
		model.addAttribute("rowId", rowId);
		model.addAttribute("exportDateFrom", exportDateFrom);
		model.addAttribute("exportDateTo", exportDateTo);
		model.addAttribute("votesNo", votesNo);
		model.addAttribute("usersEx", usersEx);
		model.addAttribute("description", description);
		model.addAttribute("baoGomThietBi", baoGomThietBi);
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
				asProposedWarehouseDAO.deleteByPrimaryKey(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "xetDuyetDocx", method = RequestMethod.GET)
	public ModelAndView xetDuyetDocx(@RequestParam(required = false) String boPhanSd, @RequestParam(required = false) String donViSd, @RequestParam(required = false) String nguoiSd,
			@RequestParam(required = false) String exportDateFrom, 
			@RequestParam(required = false) String exportDateTo, 
			@RequestParam(required = false) String usersEx,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String soPhieu,
			@RequestParam(required = false) String baoGomThietBi,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		saveMessageKey(request, "messsage.confirm.xetDuyetThanhCong");
		List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(boPhanSd, donViSd, nguoiSd, exportDateFrom, exportDateTo, createdBy, "CREATE_DATE", null);
		
		try{
		
			for(int i=0;i<xetDuyetList.size();i++){
				AsProposedWarehouse record = new AsProposedWarehouse();
				record.setId(xetDuyetList.get(i).getId());
				record.setTrangThai("Y");
				record.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setModifyDate(new Date());
				
				asProposedWarehouseDAO.updateByPrimaryKeySelective(record);
				
				AsCards asCards = new AsCards();
				asCards.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				asCards.setProductCode(xetDuyetList.get(i).getProductCode());
				asCards.setSerialNo(xetDuyetList.get(i).getSerialNo());
				
				String ngayXuat = df.format(xetDuyetList.get(i).getNgayXuat());
				asCards.setVotesNo(soPhieu);
				asCards.setUsersEx(usersEx);
				asCards.setOrganization(xetDuyetList.get(i).getBoPhanSd());
				asCards.setDepartmentId(xetDuyetList.get(i).getDonViSd());
				/*asCards.setUsers(xetDuyetList.get(i).getNguoiSd());
				asCards.setDescription(xetDuyetList.get(i).getDescription());*/
				asCards.setAmount(xetDuyetList.get(i).getSoLuong());
				asCards.setLyDoXuat(xetDuyetList.get(i).getLyDoXuat());
				asCards.setNguonLayTb(description);
				asCards.setBaoGomThietBi(baoGomThietBi);
				
				asCardsDAO.updateXetDuyet(asCards, ngayXuat);
					
			}
		
		}catch(Exception e){}
		
		String dateNow = df.format(new Date());
		String hour = df_hh.format(new Date());
		String minute = df_mm.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/xuatkhoDDH.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(xetDuyetList);
		
		Map parameters = new HashMap();
		parameters.put("dateNow", dateNow);
		parameters.put("nguoiThucHien", usersEx);
		parameters.put("soPhieu", soPhieu);
		if(xetDuyetList.size() > 0)
			parameters.put("toVT",xetDuyetList.get(0).getDonViSd());
		parameters.put("thoiGian", hour + "h" + minute + ", ngày " + dateNow);
		if(xetDuyetList.size() > 0)
			parameters.put("lyDoXuat", xetDuyetList.get(0).getLyDoXuat()); 
		parameters.put("nguonLayThietBi", description);
		parameters.put("baoGomThietBi", baoGomThietBi);
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		response.setHeader("Content-Disposition", "inline; filename=\"xuatKhoDDH__"+dateNow+".docx\"");
		
		JRDocxExporter exporterDocx = new JRDocxExporter();
		exporterDocx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		OutputStream ouputStream = response.getOutputStream();
		exporterDocx.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporterDocx.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return new ModelAndView("jspassets/xetDuyetList");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "xetDuyetXls", method = RequestMethod.GET)
	public ModelAndView xetDuyetXls(@RequestParam(required = false) String boPhanSd, @RequestParam(required = false) String donViSd, @RequestParam(required = false) String nguoiSd,
			@RequestParam(required = false) String exportDateFrom, 
			@RequestParam(required = false) String exportDateTo, 
			@RequestParam(required = false) String usersEx,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String createdBy,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		String vBoPhanSd = URLDecoder.decode(boPhanSd, "UTF-8");
		String vDonViSd = URLDecoder.decode(donViSd, "UTF-8");
		String vNguoiSd = URLDecoder.decode(nguoiSd, "UTF-8");
		String vDescription = URLDecoder.decode(description, "UTF-8");
		String vUsersEx = URLDecoder.decode(usersEx, "UTF-8");
		
		List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(vBoPhanSd, vDonViSd, vNguoiSd, exportDateFrom, exportDateTo, createdBy, "CREATE_DATE", null);
		
		String dateNow = df.format(new Date());
		String hour = df_hh.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/xuatKhoDDH.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(xetDuyetList);
		
		Map parameters = new HashMap();
		parameters.put("dateNow", dateNow);
		parameters.put("nguoiThucHien", vUsersEx);
		if(xetDuyetList.size() > 0)
			parameters.put("toVT",xetDuyetList.get(0).getDonViSd());
		parameters.put("thoiGian", hour + ", ngày " + dateNow);
		if(xetDuyetList.size() > 0)
			parameters.put("lyDoXuat", xetDuyetList.get(0).getLyDoXuat()); 
		parameters.put("nguonLayThietBi", vDescription);
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\"xuatKhoDDH__"+dateNow+".xls\"");
		JRXlsExporter exporterXLS = new JRXlsExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_X, 0);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return new ModelAndView("jspassets/xetDuyetList");
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "xetDuyetPdf", method = RequestMethod.GET)
	public ModelAndView xetDuyetPdf(@RequestParam(required = false) String boPhanSd, @RequestParam(required = false) String donViSd, @RequestParam(required = false) String nguoiSd,
			@RequestParam(required = false) String exportDateFrom, 
			@RequestParam(required = false) String exportDateTo,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String usersEx,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		List<AsProposedWarehouse> xetDuyetList = asProposedWarehouseDAO.getXetDuyetFilter(boPhanSd, donViSd, nguoiSd, exportDateFrom, exportDateTo, createdBy, null, null);
		
		String dateNow = df.format(new Date());
		String hour = df_hh.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/xuatKhoDDH.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(xetDuyetList);
		
		Map parameters = new HashMap();
		parameters.put("dateNow", dateNow);
		parameters.put("nguoiThucHien", usersEx);
		if(xetDuyetList.size() > 0)
			parameters.put("toVT",xetDuyetList.get(0).getDonViSd());
		parameters.put("thoiGian", hour + ", ngày " + dateNow);
		if(xetDuyetList.size() > 0)
			parameters.put("lyDoXuat", xetDuyetList.get(0).getLyDoXuat()); 
		parameters.put("nguonLayThietBi", description);
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=\"xuatKhoDDH__"+dateNow+".pdf\"");
		response.setCharacterEncoding("UTF-8");
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, "UTF-8");
		String fontFamily ="http://"+request.getLocalAddr()+":"+request.getLocalPort()+"/font/arial.ttf";
		JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", fontFamily);		
		exporter.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return new ModelAndView("jspassets/xetDuyetList");
	}
}
