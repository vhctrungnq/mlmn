package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyCellToCellQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBh3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyCellToCellQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBh3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellQosBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCell;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCell;
import vn.com.vhc.vmsc2.statistics.web.utils.ExportTools;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/exportExcel/*")
public class ExportExcelController extends BaseController {
	@Autowired
	private VRpDyCellQosBhDAO vRpDyCellQosBhDao;
	@Autowired
	private VRpDyCell3GDAO vRpDyCellDao;
	@Autowired
	private VRpDyCellBh3GDAO vRpDyCellBh3GDao;
	@Autowired
	private VRpWkCellDAO vRpWkCellDao;
	@Autowired
	private VRpMnCellDAO vRpMnCellDao;
	@Autowired
	private DyCellToCellQosDAO dyCellToCellQosDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("cell")
	public ModelAndView cellQos(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// data
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		if (region.isEmpty())
			region = getCenter("TT6");
		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		if (cellid == null || cellid.length() == 0 || cellid.contains(" ") == false) {
			List<VRpDyCellQosBh> vRpDyCellQosBhList = vRpDyCellQosBhDao.filter(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
			exportCellReport(tempFile, vRpDyCellQosBhList);
		} else {
			String[] strCellId = cellid.split(" ");

			cellid = "'";
			for (int i = 0; i < strCellId.length - 1; i++) {
				cellid += strCellId[i].toString() + "','";
			}
			cellid += strCellId[strCellId.length - 1].toString() + "'";

			List<VRpDyCellQosBh> vRpDyCellQosBhList = vRpDyCellQosBhDao.filterArrayExports(df.format(startDate), df.format(endDate), cellid, province, bscid,
					region);
			exportCellReport(tempFile, vRpDyCellQosBhList);
		}

		// return
		String filename = "CellReport" + df.format(startDate);
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	@RequestMapping("celltocells")
	public ModelAndView cellToCells(@RequestParam(required = false) String bscid, @RequestParam(required = false) String fromCell,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// data
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<DyCellToCellQos> dyCellToCellsQos = dyCellToCellQosDAO.filterCellToCells(bscid, fromCell, startDate, endDate);

		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		exportCellToCellsReport(tempFile, dyCellToCellsQos);

		// return
		String filename = "CellToCellsReport" + df.format(startDate);
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	@RequestMapping("cellstocell")
	public ModelAndView cellsToCell(@RequestParam(required = false) String bscid, @RequestParam(required = false) String toCell,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// data
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<DyCellToCellQos> dyCellsToCellQos = dyCellToCellQosDAO.filterCellToCells(bscid, toCell, startDate, endDate);

		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		exportCellsToCellReport(tempFile, dyCellsToCellQos);

		// return
		String filename = "CellsToCellReport" + df.format(startDate);
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	@RequestMapping("wkCell")
	public ModelAndView wkList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}

		if (region.isEmpty())
			region = getCenter("TT6");

		List<VRpWkCell> vRpWkCellList = vRpWkCellDao.filter(startWeek, startYear, endWeek, endYear, bscid, cellid, province, region);

		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		exportWkCellReport(tempFile, vRpWkCellList);

		// return
		String filename = "CellWeeklyReport" + startWeek + "." + startYear + "-" + endWeek + "." + endYear;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	@RequestMapping("mnCell")
	public ModelAndView mnList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String bscid, @RequestParam(required = false) String province, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}

		if (region.isEmpty())
			region = getCenter("TT6");

		List<VRpMnCell> vRpMnCellList = vRpMnCellDao.filter(startMonth, startYear, endMonth, endYear, bscid, cellid, province, region);

		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		exportMnCellReport(tempFile, vRpMnCellList);

		// return
		String filename = "CellMonthlyReport" + startMonth + "." + startYear + "-" + endMonth + "." + endYear;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	@RequestMapping("cell3g")
	public ModelAndView dyCell3GList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		if (region.isEmpty())
			region = getCenter("TT6");

		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		if (cellid == null || cellid.length() == 0 || cellid.contains(" ") == false) {
			List<VRpDyCell3G> vRpDyCell3List = vRpDyCellDao.filter(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
			exportCell3GReport(tempFile, vRpDyCell3List);
		} else {
			String[] strCellId = cellid.split(" ");

			cellid = "'";
			for (int i = 0; i < strCellId.length - 1; i++) {
				cellid += strCellId[i].toString() + "','";
			}
			cellid += strCellId[strCellId.length - 1].toString() + "'";

			List<VRpDyCell3G> vRpDyCell3List = vRpDyCellDao.filterArrayExports(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
			exportCell3GReport(tempFile, vRpDyCell3List);
		}

		// return
		String filename = "Cell3GReport" + df.format(startDate);
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	@RequestMapping("bhCell3g")
	public ModelAndView dyCell3GBhList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		if (region.isEmpty())
			region = getCenter("TT6");

		List<VRpDyCellBh3G> vRpDyCellBh3GList = vRpDyCellBh3GDao.filter(df.format(startDate), df.format(endDate), cellid, province, bscid, region);

		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/data";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);

		exportCellBh3GReport(tempFile, vRpDyCellBh3GList);

		// return
		String filename = "CellBh3GReport" + df.format(startDate);
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();

		return null;
	}

	private void exportCellToCellsReport(File file, List<DyCellToCellQos> dyCellToCellQos) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cell To Cells Daily Report", 0);

			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			
			String strHeader = "DAY,FROM BSC,FROM CELL,TO BSC,TO CELL,HO ATT,HO SUC,HO SUCR(%),HO REVERSION(%),HO LOST(%)";
			String[] subStrHeader = strHeader.split(",");
			for (int i = 0; i < subStrHeader.length; i++) {
				Label label = new Label(i, 0, subStrHeader[i],cellFormat);
				sheet.addCell(label);
			}

			int i = 1;
			for (DyCellToCellQos vRpDyCellQosBh : dyCellToCellQos) {
				Label lb0 = new Label(0, i, df.format(vRpDyCellQosBh.getDay()),cellFormat);
				sheet.addCell(lb0);
				Label lb1 = new Label(1, i, vRpDyCellQosBh.getBscid(),cellFormat);
				sheet.addCell(lb1);
				Label lb2 = new Label(2, i, vRpDyCellQosBh.getFromCell(),cellFormat);
				sheet.addCell(lb2);
				Label lb3 = new Label(3, i, vRpDyCellQosBh.getToCell(),cellFormat);
				sheet.addCell(lb3);
				Label lb4 = new Label(4, i, vRpDyCellQosBh.getToBsc(),cellFormat);
				sheet.addCell(lb4);
				jxl.write.Number lb5 = new jxl.write.Number(5, i, Helper.int2Double(vRpDyCellQosBh.getHovercnt()),cellFormat);
				sheet.addCell(lb5);
				jxl.write.Number lb6 = new jxl.write.Number(6, i, Helper.int2Double(vRpDyCellQosBh.getHoversuc()),cellFormat);
				sheet.addCell(lb6);
				jxl.write.Number lb7 = new jxl.write.Number(7, i, Helper.float2Double(vRpDyCellQosBh.getHovesucr()),cellFormat);
				sheet.addCell(lb7);
				jxl.write.Number lb8 = new jxl.write.Number(8, i, Helper.float2Double(vRpDyCellQosBh.getHoverev()),cellFormat);
				sheet.addCell(lb8);
				jxl.write.Number lb9 = new jxl.write.Number(9, i, Helper.float2Double(vRpDyCellQosBh.getHovelost()),cellFormat);
				sheet.addCell(lb9);

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

	private void exportCellsToCellReport(File file, List<DyCellToCellQos> dyCellToCellQos) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cells To Cell Daily Report", 0);

			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			
			String strHeader = "DAY,FROM BSC,FROM CELL,TO BSC,TO CELL,HO ATT,HO SUC,HO SUCR(%),HO REVERSION(%),HO LOST(%)";
			String[] subStrHeader = strHeader.split(",");
			for (int i = 0; i < subStrHeader.length; i++) {
				Label label = new Label(i, 0, subStrHeader[i],cellFormat);
				sheet.addCell(label);
			}

			int i = 1;
			for (DyCellToCellQos vRpDyCellQosBh : dyCellToCellQos) {
				Label lb0 = new Label(0, i, df.format(vRpDyCellQosBh.getDay()),cellFormat);
				sheet.addCell(lb0);
				Label lb1 = new Label(1, i, vRpDyCellQosBh.getBscid(),cellFormat);
				sheet.addCell(lb1);
				Label lb2 = new Label(2, i, vRpDyCellQosBh.getFromCell(),cellFormat);
				sheet.addCell(lb2);
				Label lb3 = new Label(3, i, vRpDyCellQosBh.getToCell(),cellFormat);
				sheet.addCell(lb3);
				Label lb4 = new Label(4, i, vRpDyCellQosBh.getToBsc(),cellFormat);
				sheet.addCell(lb4);
				jxl.write.Number lb5 = new jxl.write.Number(5, i, Helper.int2Double(vRpDyCellQosBh.getHovercnt()),cellFormat);
				sheet.addCell(lb5);
				jxl.write.Number lb6 = new jxl.write.Number(6, i, Helper.int2Double(vRpDyCellQosBh.getHoversuc()),cellFormat);
				sheet.addCell(lb6);
				jxl.write.Number lb7 = new jxl.write.Number(7, i, Helper.float2Double(vRpDyCellQosBh.getHovesucr()),cellFormat);
				sheet.addCell(lb7);
				jxl.write.Number lb8 = new jxl.write.Number(8, i, Helper.float2Double(vRpDyCellQosBh.getHoverev()),cellFormat);
				sheet.addCell(lb8);
				jxl.write.Number lb9 = new jxl.write.Number(9, i, Helper.float2Double(vRpDyCellQosBh.getHovelost()),cellFormat);
				sheet.addCell(lb9);

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

	private void exportCellReport(File file, List<VRpDyCellQosBh> vRpDyCellQosBhList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cell Daily Report", 0);
			
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			Label label0 = new Label(0, 0, "Date",cellFormatHeader);
			sheet.addCell(label0);
			Label label1 = new Label(1, 0, "Center",cellFormatHeader);
			sheet.addCell(label1);
			Label label2 = new Label(2, 0, "Province",cellFormatHeader);
			sheet.addCell(label2);
			Label label3 = new Label(3, 0, "Bscid",cellFormatHeader);
			sheet.addCell(label3);
			Label label4 = new Label(4, 0, "Cellid",cellFormatHeader);
			sheet.addCell(label4);
			Label label5 = new Label(5, 0, "TCH Def",cellFormatHeader);
			sheet.addCell(label5);
			Label label6 = new Label(6, 0, "TCH Avail",cellFormatHeader);
			sheet.addCell(label6);
			Label label7 = new Label(7, 0, "TCH Atts",cellFormatHeader);
			sheet.addCell(label7);
			Label label8 = new Label(8, 0, "TCH Block(%)",cellFormatHeader);
			sheet.addCell(label8);
			Label label9 = new Label(9, 0, "TCH Block",cellFormatHeader);
			sheet.addCell(label9);
			Label label10 = new Label(10, 0, "TCH HoBlk(%)",cellFormatHeader);
			sheet.addCell(label10);
			Label label11 = new Label(11, 0, "TCH HoBlk",cellFormatHeader);
			sheet.addCell(label11);
			Label label12 = new Label(12, 0, "TCH Seiz",cellFormatHeader);
			sheet.addCell(label12);
			Label label13 = new Label(13, 0, "CSSR(%)",cellFormatHeader);
			sheet.addCell(label13);
			Label label14 = new Label(14, 0, "TCH Drop(%)",cellFormatHeader);
			sheet.addCell(label14);
			Label label15 = new Label(15, 0, "TCH Drps",cellFormatHeader);
			sheet.addCell(label15);
			Label label16 = new Label(16, 0, "EMPD",cellFormatHeader);
			sheet.addCell(label16);
			Label label17 = new Label(17, 0, "TCH Traffic",cellFormatHeader);
			sheet.addCell(label17);
			Label label18 = new Label(18, 0, "TrafHalf",cellFormatHeader);
			sheet.addCell(label18);
			Label label19 = new Label(19, 0, "SD Def",cellFormatHeader);
			sheet.addCell(label19);
			Label label20 = new Label(20, 0, "SD Attems",cellFormatHeader);
			sheet.addCell(label20);
			Label label21 = new Label(21, 0, "SD Block(%)",cellFormatHeader);
			sheet.addCell(label21);
			Label label22 = new Label(22, 0, "SD Blocks",cellFormatHeader);
			sheet.addCell(label22);
			Label label23 = new Label(23, 0, "SD Drop(%)",cellFormatHeader);
			sheet.addCell(label23);
			Label label24 = new Label(24, 0, "SD Drops",cellFormatHeader);
			sheet.addCell(label24);
			Label label25 = new Label(25, 0, "Downtime(Min)",cellFormatHeader);
			sheet.addCell(label25);
			Label label40 = new Label(26, 0, "TCH MHT(Sec)",cellFormatHeader);
			sheet.addCell(label40);
			Label label41 = new Label(27, 0, "SDCCH MHT(Sec)",cellFormatHeader);
			sheet.addCell(label41);
			Label label42 = new Label(28, 0, "BH",cellFormatHeader);
			sheet.addCell(label42);
			Label label26 = new Label(29, 0, "TCH Avail",cellFormatHeader);
			sheet.addCell(label26);
			Label label27 = new Label(30, 0, "TCH Var",cellFormatHeader);
			sheet.addCell(label27);
			Label label28 = new Label(31, 0, "TCH Atts",cellFormatHeader);
			sheet.addCell(label28);
			Label label29 = new Label(32, 0, "TCH Block(%)",cellFormatHeader);
			sheet.addCell(label29);
			Label label30 = new Label(33, 0, "TCH Blocks",cellFormatHeader);
			sheet.addCell(label30);
			Label label31 = new Label(34, 0, "TCH HoBlk(%)",cellFormatHeader);
			sheet.addCell(label31);
			Label label32 = new Label(35, 0, "TCH HoBlk",cellFormatHeader);
			sheet.addCell(label32);
			Label label33 = new Label(36, 0, "CSSR",cellFormatHeader);
			sheet.addCell(label33);
			Label label34 = new Label(37, 0, "TCH Drop(%)",cellFormatHeader);
			sheet.addCell(label34);
			Label label35 = new Label(38, 0, "TCH Drops",cellFormatHeader);
			sheet.addCell(label35);
			Label label36 = new Label(39, 0, "TCH Traff(E)",cellFormatHeader);
			sheet.addCell(label36);
			Label label37 = new Label(40, 0, "GoS(%)",cellFormatHeader);
			sheet.addCell(label37);
			Label label38 = new Label(41, 0, "SITENAME",cellFormatHeader);
			sheet.addCell(label38);

			int i = 1;
			for (VRpDyCellQosBh vRpDyCellQosBh : vRpDyCellQosBhList) {
				Label day = new Label(0, i, df.format(vRpDyCellQosBh.getDay()),cellFormat);
				sheet.addCell(day);
				Label tt = new Label(1, i, vRpDyCellQosBh.getRegion(),cellFormat);
				sheet.addCell(tt);
				Label prv = new Label(2, i, vRpDyCellQosBh.getProvince(),cellFormat);
				sheet.addCell(prv);
				Label bsc = new Label(3, i, vRpDyCellQosBh.getBscid(),cellFormat);
				sheet.addCell(bsc);
				Label cell = new Label(4, i, vRpDyCellQosBh.getCellid(),cellFormat);
				sheet.addCell(cell);
				jxl.write.Number tDef = new jxl.write.Number(5, i, Helper.int2Double(vRpDyCellQosBh.gettDef()),cellFormat);
				sheet.addCell(tDef);
				jxl.write.Number tAvail = new jxl.write.Number(6, i, Helper.bigdecimal2Double(vRpDyCellQosBh.gettAvail()),cellFormat);
				sheet.addCell(tAvail);
				jxl.write.Number tAttem = new jxl.write.Number(7, i, Helper.int2Double(vRpDyCellQosBh.gettAtts()),cellFormat);
				sheet.addCell(tAttem);
				jxl.write.Number tNblkr = new jxl.write.Number(8, i, Helper.float2Double(vRpDyCellQosBh.gettNblkr()),cellFormat);
				sheet.addCell(tNblkr);
				jxl.write.Number tNblks = new jxl.write.Number(9, i, Helper.int2Double(vRpDyCellQosBh.gettNblks()),cellFormat);
				sheet.addCell(tNblks);
				jxl.write.Number tHoblkr = new jxl.write.Number(10, i, Helper.float2Double(vRpDyCellQosBh.gettHoblkr()),cellFormat);
				sheet.addCell(tHoblkr);
				jxl.write.Number tHoblks = new jxl.write.Number(11, i, Helper.int2Double(vRpDyCellQosBh.gettHoblks()),cellFormat);
				sheet.addCell(tHoblks);
				jxl.write.Number tSeizs = new jxl.write.Number(12, i, Helper.int2Double(vRpDyCellQosBh.gettSeizs()),cellFormat);
				sheet.addCell(tSeizs);
				jxl.write.Number cssr = new jxl.write.Number(13, i, Helper.float2Double(vRpDyCellQosBh.getCssr()),cellFormat);
				sheet.addCell(cssr);
				jxl.write.Number tDrpr = new jxl.write.Number(14, i, Helper.float2Double(vRpDyCellQosBh.gettDrpr()),cellFormat);
				sheet.addCell(tDrpr);
				jxl.write.Number tDrps = new jxl.write.Number(15, i, Helper.int2Double(vRpDyCellQosBh.gettDrps()),cellFormat);
				sheet.addCell(tDrps);
				jxl.write.Number tEmpdr = new jxl.write.Number(16, i, Helper.float2Double(vRpDyCellQosBh.gettEmpdr()),cellFormat);
				sheet.addCell(tEmpdr);
				jxl.write.Number tTraf = new jxl.write.Number(17, i, Helper.float2Double(vRpDyCellQosBh.gettTraf()),cellFormat);
				sheet.addCell(tTraf);
				jxl.write.Number tTrafh = new jxl.write.Number(18, i, Helper.float2Double(vRpDyCellQosBh.gettTrafh()),cellFormat);
				sheet.addCell(tTrafh);
				jxl.write.Number sDef = new jxl.write.Number(19, i, Helper.int2Double(vRpDyCellQosBh.getsDef()),cellFormat);
				sheet.addCell(sDef);
				jxl.write.Number sAtts = new jxl.write.Number(20, i, Helper.int2Double(vRpDyCellQosBh.getsAtts()),cellFormat);
				sheet.addCell(sAtts);
				jxl.write.Number sBlkr = new jxl.write.Number(21, i, Helper.float2Double(vRpDyCellQosBh.getsBlkr()),cellFormat);
				sheet.addCell(sBlkr);
				jxl.write.Number sBlks = new jxl.write.Number(22, i, Helper.int2Double(vRpDyCellQosBh.getsBlks()),cellFormat);
				sheet.addCell(sBlks);
				jxl.write.Number sDrpr = new jxl.write.Number(23, i, Helper.float2Double(vRpDyCellQosBh.getsDrpr()),cellFormat);
				sheet.addCell(sDrpr);
				jxl.write.Number sDrps = new jxl.write.Number(24, i, Helper.int2Double(vRpDyCellQosBh.getsDrps()),cellFormat);
				sheet.addCell(sDrps);
				jxl.write.Number downtime = new jxl.write.Number(25, i, Helper.bigdecimal2Double(vRpDyCellQosBh.getDowntime()),cellFormat);
				sheet.addCell(downtime);
				jxl.write.Number meanholdtime = new jxl.write.Number(26, i, Helper.float2Double(vRpDyCellQosBh.getMeanholdtime()),cellFormat);
				sheet.addCell(meanholdtime);
				jxl.write.Number sdcchmeanholdtime = new jxl.write.Number(27, i, Helper.float2Double(vRpDyCellQosBh.getSdcchmeanholdtime()),cellFormat);
				sheet.addCell(sdcchmeanholdtime);
				jxl.write.Number bh = new jxl.write.Number(28, i, Helper.int2Double(vRpDyCellQosBh.getBh()),cellFormat);
				sheet.addCell(bh);
				jxl.write.Number bhTAvail = new jxl.write.Number(29, i, Helper.bigdecimal2Double(vRpDyCellQosBh.getBhTAvail()),cellFormat);
				sheet.addCell(bhTAvail);
				jxl.write.Number bhTchVar = new jxl.write.Number(30, i, Helper.bigdecimal2Double(vRpDyCellQosBh.getBhTchVar()),cellFormat);
				sheet.addCell(bhTchVar);
				jxl.write.Number bhTAtts = new jxl.write.Number(31, i, Helper.float2Double(vRpDyCellQosBh.getBhTAtts()),cellFormat);
				sheet.addCell(bhTAtts);
				jxl.write.Number bhTBlkr = new jxl.write.Number(32, i, Helper.float2Double(vRpDyCellQosBh.getBhTBlkr()),cellFormat);
				sheet.addCell(bhTBlkr);
				jxl.write.Number bhTBlks = new jxl.write.Number(33, i, Helper.int2Double(vRpDyCellQosBh.getBhTBlks()),cellFormat);
				sheet.addCell(bhTBlks);
				jxl.write.Number bhTHoblkr = new jxl.write.Number(34, i, Helper.float2Double(vRpDyCellQosBh.getBhTHoblkr()),cellFormat);
				sheet.addCell(bhTHoblkr);
				jxl.write.Number bhTHoblks = new jxl.write.Number(35, i, Helper.int2Double(vRpDyCellQosBh.getBhTHoblks()),cellFormat);
				sheet.addCell(bhTHoblks);
				jxl.write.Number bhCssr = new jxl.write.Number(36, i, Helper.float2Double(vRpDyCellQosBh.getBhCssr()),cellFormat);
				sheet.addCell(bhCssr);
				jxl.write.Number bhTDrpr = new jxl.write.Number(37, i, Helper.float2Double(vRpDyCellQosBh.getBhTDrpr()),cellFormat);
				sheet.addCell(bhTDrpr);
				jxl.write.Number bhTDrps = new jxl.write.Number(38, i, Helper.int2Double(vRpDyCellQosBh.getBhTDrps()),cellFormat);
				sheet.addCell(bhTDrps);
				jxl.write.Number bhTTraf = new jxl.write.Number(39, i, Helper.float2Double(vRpDyCellQosBh.getBhTTraf()),cellFormat);
				sheet.addCell(bhTTraf);
				jxl.write.Number bhTGos = new jxl.write.Number(40, i, Helper.float2Double(vRpDyCellQosBh.getBhTGos()),cellFormat);
				sheet.addCell(bhTGos);
				Label sitename = new Label(41, i, vRpDyCellQosBh.getSitename(),cellFormat);
				sheet.addCell(sitename);

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

	private void exportWkCellReport(File file, List<VRpWkCell> vRpWkCellList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cell Daily Report", 0);

			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			
			String strHeader = "WEEK,YEAR,TT,PROVINCE,BSC,CELL,TCH Def,TCH Avail,TCH Attem,TCH Blk (%),TCH HoBlk (%),CSSR (%),TCH Drp (%),TCH Traff,H_Traff,SD Def, SD Attem,SD Blk (%),SD Drp (%),DOWN TIME (Min),DB LOAD (%)";
			String[] subStrHeader = strHeader.split(",");
			for (int i = 0; i < subStrHeader.length; i++) {
				Label label = new Label(i, 0, subStrHeader[i],cellFormatHeader);
				sheet.addCell(label);
			}

			int i = 1;
			for (VRpWkCell vRpWkCell : vRpWkCellList) {
				jxl.write.Number lb0 = new jxl.write.Number(0, i, Helper.int2Double(vRpWkCell.getWeek()),cellFormat);
				sheet.addCell(lb0);
				jxl.write.Number lb1 = new jxl.write.Number(1, i, Helper.int2Double(vRpWkCell.getYear()),cellFormat);
				sheet.addCell(lb1);
				Label lb2 = new Label(2, i, vRpWkCell.getRegion(),cellFormat);
				sheet.addCell(lb2);
				Label lb3 = new Label(3, i, vRpWkCell.getProvince(),cellFormat);
				sheet.addCell(lb3);
				Label lb4 = new Label(4, i, vRpWkCell.getBscid(),cellFormat);
				sheet.addCell(lb4);
				Label lb5 = new Label(5, i, vRpWkCell.getCellid(),cellFormat);
				sheet.addCell(lb5);
				jxl.write.Number lb6 = new jxl.write.Number(6, i, Helper.int2Double(vRpWkCell.gettDef()),cellFormat);
				sheet.addCell(lb6);
				jxl.write.Number lb7 = new jxl.write.Number(7, i, Helper.bigdecimal2Double(vRpWkCell.gettAvail()),cellFormat);
				sheet.addCell(lb7);
				jxl.write.Number lb8 = new jxl.write.Number(8, i, Helper.float2Double(vRpWkCell.gettAtts()),cellFormat);
				sheet.addCell(lb8);
				jxl.write.Number lb9 = new jxl.write.Number(9, i, Helper.float2Double(vRpWkCell.gettNblkr()),cellFormat);
				sheet.addCell(lb9);
				jxl.write.Number lb10 = new jxl.write.Number(10, i, Helper.float2Double(vRpWkCell.gettHoblkr()),cellFormat);
				sheet.addCell(lb10);
				jxl.write.Number lb11 = new jxl.write.Number(11, i, Helper.float2Double(vRpWkCell.getCssr()),cellFormat);
				sheet.addCell(lb11);
				jxl.write.Number lb12 = new jxl.write.Number(12, i, Helper.float2Double(vRpWkCell.gettDrpr()),cellFormat);
				sheet.addCell(lb12);
				jxl.write.Number lb13 = new jxl.write.Number(13, i, Helper.float2Double(vRpWkCell.gettTraf()),cellFormat);
				sheet.addCell(lb13);
				jxl.write.Number lb14 = new jxl.write.Number(14, i, Helper.float2Double(vRpWkCell.gettTrafh()),cellFormat);
				sheet.addCell(lb14);
				jxl.write.Number lb15 = new jxl.write.Number(15, i, Helper.float2Double(vRpWkCell.getsDef()),cellFormat);
				sheet.addCell(lb15);
				jxl.write.Number lb16 = new jxl.write.Number(16, i, Helper.float2Double(vRpWkCell.getsAtts()),cellFormat);
				sheet.addCell(lb16);
				jxl.write.Number lb17 = new jxl.write.Number(17, i, Helper.float2Double(vRpWkCell.getsBlkr()),cellFormat);
				sheet.addCell(lb17);
				jxl.write.Number lb18 = new jxl.write.Number(18, i, Helper.float2Double(vRpWkCell.getsDrpr()),cellFormat);
				sheet.addCell(lb18);
				jxl.write.Number lb19 = new jxl.write.Number(19, i, Helper.bigdecimal2Double(vRpWkCell.getDowntime()),cellFormat);
				sheet.addCell(lb19);
				jxl.write.Number lb20 = new jxl.write.Number(20, i, Helper.float2Double(vRpWkCell.getDataload()),cellFormat);
				sheet.addCell(lb20);
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

	private void exportMnCellReport(File file, List<VRpMnCell> vRpMnCellList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cell Daily Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			String strHeader = "MONTH,YEAR,TT,PROVINCE,BSC,CELL,TCH Def,TCH Avail,TCH Attem,TCH Blk (%),TCH HoBlk (%),CSSR (%),TCH Drp (%),TCH Traff,H_Traff,SD Def, SD Attem,SD Blk (%),SD Drp (%),DOWN TIME (Min),DB LOAD (%)";
			String[] subStrHeader = strHeader.split(",");
			for (int i = 0; i < subStrHeader.length; i++) {
				Label label = new Label(i, 0, subStrHeader[i], cellFormatHeader);
				sheet.addCell(label);
			}

			int i = 1;
			for (VRpMnCell vRpMnCell : vRpMnCellList) {
				jxl.write.Number lb0 = new jxl.write.Number(0, i, Helper.int2Double(vRpMnCell.getMonth()), cellFormat);
				sheet.addCell(lb0);
				jxl.write.Number lb1 = new jxl.write.Number(1, i, Helper.int2Double(vRpMnCell.getYear()), cellFormat);
				sheet.addCell(lb1);
				Label lb2 = new Label(2, i, vRpMnCell.getRegion(), cellFormat);
				sheet.addCell(lb2);
				Label lb3 = new Label(3, i, vRpMnCell.getProvince(), cellFormat);
				sheet.addCell(lb3);
				Label lb4 = new Label(4, i, vRpMnCell.getBscid(), cellFormat);
				sheet.addCell(lb4);
				Label lb5 = new Label(5, i, vRpMnCell.getCellid(), cellFormat);
				sheet.addCell(lb5);
				jxl.write.Number lb6 = new jxl.write.Number(6, i, Helper.int2Double(vRpMnCell.gettDef()), cellFormat);
				sheet.addCell(lb6);
				jxl.write.Number lb7 = new jxl.write.Number(7, i, Helper.bigdecimal2Double(vRpMnCell.gettAvail()), cellFormat);
				sheet.addCell(lb7);
				jxl.write.Number lb8 = new jxl.write.Number(8, i, Helper.float2Double(vRpMnCell.gettAtts()), cellFormat);
				sheet.addCell(lb8);
				jxl.write.Number lb9 = new jxl.write.Number(9, i, Helper.float2Double(vRpMnCell.gettNblkr()), cellFormat);
				sheet.addCell(lb9);
				jxl.write.Number lb10 = new jxl.write.Number(10, i, Helper.float2Double(vRpMnCell.gettHoblkr()), cellFormat);
				sheet.addCell(lb10);
				jxl.write.Number lb11 = new jxl.write.Number(11, i, Helper.float2Double(vRpMnCell.getCssr()), cellFormat);
				sheet.addCell(lb11);
				jxl.write.Number lb12 = new jxl.write.Number(12, i, Helper.float2Double(vRpMnCell.gettDrpr()), cellFormat);
				sheet.addCell(lb12);
				jxl.write.Number lb13 = new jxl.write.Number(13, i, Helper.float2Double(vRpMnCell.gettTraf()), cellFormat);
				sheet.addCell(lb13);
				jxl.write.Number lb14 = new jxl.write.Number(14, i, Helper.float2Double(vRpMnCell.gettTrafh()), cellFormat);
				sheet.addCell(lb14);
				jxl.write.Number lb15 = new jxl.write.Number(15, i, Helper.float2Double(vRpMnCell.getsDef()), cellFormat);
				sheet.addCell(lb15);
				jxl.write.Number lb16 = new jxl.write.Number(16, i, Helper.float2Double(vRpMnCell.getsAtts()), cellFormat);
				sheet.addCell(lb16);
				jxl.write.Number lb17 = new jxl.write.Number(17, i, Helper.float2Double(vRpMnCell.getsBlkr()), cellFormat);
				sheet.addCell(lb17);
				jxl.write.Number lb18 = new jxl.write.Number(18, i, Helper.float2Double(vRpMnCell.getsDrpr()), cellFormat);
				sheet.addCell(lb18);
				jxl.write.Number lb19 = new jxl.write.Number(19, i, Helper.bigdecimal2Double(vRpMnCell.getDowntime()), cellFormat);
				sheet.addCell(lb19);
				jxl.write.Number lb20 = new jxl.write.Number(20, i, Helper.float2Double(vRpMnCell.getDataload()), cellFormat);
				sheet.addCell(lb20);
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

	private void exportCell3GReport(File file, List<VRpDyCell3G> vRpDyCell3List) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cell 3G Daily Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			String strHeader = "Date,Center,Province,Vendor,RNC,Site,Cell,Cell Downtime (Hour),HS Downtime (Hour),EUL Downtime,Speech Traffic (ERL),CS64 Traffic (ERL),PSR99 UL Data (MB),PSR99 DL Data (MB),HSUPA UL Data(MB),HSDPA DL Data (MB),PSR99 UL Thrp (Kbps),PSR99 DL Thrp (Kbps),HSUPA UL Thrp (Kbps),HSDPA DL Thrp (Kbps),Speech CSSR (%),CS64 CSSR (%),PSR99 CSSR (%),HSUPA CSSR (%),HSDPA CSSR (%),Speech Drops,Speech Drop(%),CS64 Drops,CS64 Drop(%),PSR99 Drops,PSR99 Drop(%),HSUPA Drops,HSUPA Drop (%),HSDPA Drops,HSDPA Drop (%),BADCELL,DataLoad(%)";
			String[] subStrHeader = strHeader.split(",");
			for (int i = 0; i < subStrHeader.length; i++) {
				Label label = new Label(i, 0, subStrHeader[i],cellFormatHeader);
				sheet.addCell(label);
			}

			int i = 1;
			for (VRpDyCell3G vRpDyCell3G : vRpDyCell3List) {
				Label lb0 = new Label(0, i, df.format(vRpDyCell3G.getDay()),cellFormat);
				sheet.addCell(lb0);
				Label lb1 = new Label(1, i, vRpDyCell3G.getRegion(),cellFormat);
				sheet.addCell(lb1);
				Label lb2 = new Label(2, i, vRpDyCell3G.getProvince(),cellFormat);
				sheet.addCell(lb2);
				Label lb3 = new Label(3, i, vRpDyCell3G.getVendor(),cellFormat);
				sheet.addCell(lb3);
				Label lb4 = new Label(4, i, vRpDyCell3G.getBscid(),cellFormat);
				sheet.addCell(lb4);
				Label lb5 = new Label(5, i, vRpDyCell3G.getSiteid(),cellFormat);
				sheet.addCell(lb5);
				Label lb6 = new Label(6, i, vRpDyCell3G.getCellid(),cellFormat);
				sheet.addCell(lb6);
				jxl.write.Number lb7 = new jxl.write.Number(7, i, Helper.float2Double(vRpDyCell3G.getCellDowntime()),cellFormat);
				sheet.addCell(lb7);
				jxl.write.Number lb8 = new jxl.write.Number(8, i, Helper.float2Double(vRpDyCell3G.getHsDowntime()),cellFormat);
				sheet.addCell(lb8);
				jxl.write.Number lb9 = new jxl.write.Number(9, i, Helper.float2Double(vRpDyCell3G.getEulDowntime()),cellFormat);
				sheet.addCell(lb9);
				jxl.write.Number lb10 = new jxl.write.Number(10, i, Helper.float2Double(vRpDyCell3G.getSpeechTraff()),cellFormat);
				sheet.addCell(lb10);
				jxl.write.Number lb11 = new jxl.write.Number(11, i, Helper.float2Double(vRpDyCell3G.getCs64Traff()),cellFormat);
				sheet.addCell(lb11);
				jxl.write.Number lb12 = new jxl.write.Number(12, i, Helper.float2Double(vRpDyCell3G.getPsr99UlTraff()),cellFormat);
				sheet.addCell(lb12);
				jxl.write.Number lb13 = new jxl.write.Number(13, i, Helper.float2Double(vRpDyCell3G.getPsr99DlTraff()),cellFormat);
				sheet.addCell(lb13);
				jxl.write.Number lb14 = new jxl.write.Number(14, i, Helper.float2Double(vRpDyCell3G.getHsupaUlTraff()),cellFormat);
				sheet.addCell(lb14);
				jxl.write.Number lb15 = new jxl.write.Number(15, i, Helper.float2Double(vRpDyCell3G.getHsdpaDlTraff()),cellFormat);
				sheet.addCell(lb15);
				jxl.write.Number lb16 = new jxl.write.Number(16, i, Helper.float2Double(vRpDyCell3G.getPsr99UlThroughtput()),cellFormat);
				sheet.addCell(lb16);
				jxl.write.Number lb17 = new jxl.write.Number(17, i, Helper.float2Double(vRpDyCell3G.getPsr99DlThroughtput()),cellFormat);
				sheet.addCell(lb17);
				jxl.write.Number lb18 = new jxl.write.Number(18, i, Helper.float2Double(vRpDyCell3G.getHsupaUlThroughtput()),cellFormat);
				sheet.addCell(lb18);
				jxl.write.Number lb19 = new jxl.write.Number(19, i, Helper.float2Double(vRpDyCell3G.getHsdpaDlThroughtput()),cellFormat);
				sheet.addCell(lb19);
				jxl.write.Number lb20 = new jxl.write.Number(20, i, Helper.float2Double(vRpDyCell3G.getSpeechCssr()),cellFormat);
				sheet.addCell(lb20);
				jxl.write.Number lb21 = new jxl.write.Number(21, i, Helper.float2Double(vRpDyCell3G.getCs64Cssr()),cellFormat);
				sheet.addCell(lb21);
				jxl.write.Number lb22 = new jxl.write.Number(22, i, Helper.float2Double(vRpDyCell3G.getPsr99Cssr()),cellFormat);
				sheet.addCell(lb22);
				jxl.write.Number lb23 = new jxl.write.Number(23, i, Helper.float2Double(vRpDyCell3G.getHsupaCssr()),cellFormat);
				sheet.addCell(lb23);
				jxl.write.Number lb24 = new jxl.write.Number(24, i, Helper.float2Double(vRpDyCell3G.getHsdpaCssr()),cellFormat);
				sheet.addCell(lb24);
				jxl.write.Number lb25 = new jxl.write.Number(25, i, Helper.int2Double(vRpDyCell3G.getSpeechDrop()),cellFormat);
				sheet.addCell(lb25);
				jxl.write.Number lb26 = new jxl.write.Number(26, i, Helper.float2Double(vRpDyCell3G.getSpeechDrpr()),cellFormat);
				sheet.addCell(lb26);
				jxl.write.Number lb27 = new jxl.write.Number(27, i, Helper.int2Double(vRpDyCell3G.getCs64Drop()),cellFormat);
				sheet.addCell(lb27);
				jxl.write.Number lb28 = new jxl.write.Number(28, i, Helper.float2Double(vRpDyCell3G.getCs64Drpr()),cellFormat);
				sheet.addCell(lb28);
				jxl.write.Number lb29 = new jxl.write.Number(29, i, Helper.int2Double(vRpDyCell3G.getPsr99Drop()),cellFormat);
				sheet.addCell(lb29);
				jxl.write.Number lb30 = new jxl.write.Number(30, i, Helper.float2Double(vRpDyCell3G.getPsr99Drpr()),cellFormat);
				sheet.addCell(lb30);
				jxl.write.Number lb31 = new jxl.write.Number(31, i, Helper.int2Double(vRpDyCell3G.getHsupaDrop()),cellFormat);
				sheet.addCell(lb31);
				jxl.write.Number lb32 = new jxl.write.Number(32, i, Helper.float2Double(vRpDyCell3G.getHsupaDrpr()),cellFormat);
				sheet.addCell(lb32);
				jxl.write.Number lb33 = new jxl.write.Number(33, i, Helper.int2Double(vRpDyCell3G.getHsdpaDrop()),cellFormat);
				sheet.addCell(lb33);
				jxl.write.Number lb34 = new jxl.write.Number(34, i, Helper.float2Double(vRpDyCell3G.getHsdpaDrpr()),cellFormat);
				sheet.addCell(lb34);
				Label lb35 = new Label(35, i, vRpDyCell3G.getStatus(),cellFormat);
				sheet.addCell(lb35);
				jxl.write.Number lb36 = new jxl.write.Number(36, i, Helper.float2Double(vRpDyCell3G.getDataload()),cellFormat);
				sheet.addCell(lb36);

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

	private void exportCellBh3GReport(File file, List<VRpDyCellBh3G> vRpDyCellBh3GList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("Cell 3G Busy Hour Report", 0);

			String strHeader = "Center,Province,Vendor,RNC,Site,Cell,Date,Busy Hour,Speech Traffic (ERL),CS64 Traffic (ERL),PSR99 UL Data (MB),PSR99 DL Data (MB),HSUPA UL Data(MB),HSDPA DL Data (MB),PSR99 UL Thrp (Kbps),PSR99 DL Thrp (Kbps),HSUPA UL Thrp (Kbps),HSDPA DL Thrp (Kbps),Speech CSSR (%),CS64 CSSR (%),PSR99 CSSR (%),HSUPA CSSR (%),HSDPA CSSR (%),Speech Drops,Speech Drop(%),CS64 Drops,CS64 Drop(%),PSR99 Drops,PSR99 Drop(%),HSUPA Drops,HSUPA Drop (%),HSDPA Drops,HSDPA Drop (%)";
			String[] subStrHeader = strHeader.split(",");
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			
			for (int i = 0; i < subStrHeader.length; i++) {
				Label label = new Label(i, 0, subStrHeader[i],cellFormatHeader);
				sheet.addCell(label);
			}
			
			int i = 1;
			for (VRpDyCellBh3G vRpDyCell3G : vRpDyCellBh3GList) {
				Label lb0 = new Label(0, i, vRpDyCell3G.getRegion(),cellFormat);
				sheet.addCell(lb0);
				Label lb1 = new Label(1, i, vRpDyCell3G.getProvince(),cellFormat);
				sheet.addCell(lb1);
				Label lb2 = new Label(2, i, vRpDyCell3G.getVendor(),cellFormat);
				sheet.addCell(lb2);
				Label lb3 = new Label(3, i, vRpDyCell3G.getBscid(),cellFormat);
				sheet.addCell(lb3);
				Label lb4 = new Label(4, i, vRpDyCell3G.getSiteid(),cellFormat);
				sheet.addCell(lb4);
				Label lb5 = new Label(5, i, vRpDyCell3G.getCellid(),cellFormat);
				sheet.addCell(lb5);
				Label lb6 = new Label(6, i, df.format(vRpDyCell3G.getDay()),cellFormat);
				sheet.addCell(lb6);
				Label lb7 = new Label(7, i, vRpDyCell3G.getHour() + ":00");
				sheet.addCell(lb7);
				jxl.write.Number lb8 = new jxl.write.Number(8, i, Helper.float2Double(vRpDyCell3G.getSpeechTraff()),cellFormat);
				sheet.addCell(lb8);
				jxl.write.Number lb9 = new jxl.write.Number(9, i, Helper.float2Double(vRpDyCell3G.getCs64Traff()),cellFormat);
				sheet.addCell(lb9);
				jxl.write.Number lb10 = new jxl.write.Number(10, i, Helper.float2Double(vRpDyCell3G.getPsr99UlTraff()),cellFormat);
				sheet.addCell(lb10);
				jxl.write.Number lb11 = new jxl.write.Number(11, i, Helper.float2Double(vRpDyCell3G.getPsr99DlTraff()),cellFormat);
				sheet.addCell(lb11);
				jxl.write.Number lb12 = new jxl.write.Number(12, i, Helper.float2Double(vRpDyCell3G.getHsupaUlTraff()),cellFormat);
				sheet.addCell(lb12);
				jxl.write.Number lb13 = new jxl.write.Number(13, i, Helper.float2Double(vRpDyCell3G.getHsdpaDlTraff()),cellFormat);
				sheet.addCell(lb13);
				jxl.write.Number lb14 = new jxl.write.Number(14, i, Helper.float2Double(vRpDyCell3G.getSpeechCssr()),cellFormat);
				sheet.addCell(lb14);
				jxl.write.Number lb15 = new jxl.write.Number(15, i, Helper.float2Double(vRpDyCell3G.getCs64Cssr()),cellFormat);
				sheet.addCell(lb15);
				jxl.write.Number lb16 = new jxl.write.Number(16, i, Helper.float2Double(vRpDyCell3G.getPsr99Cssr()),cellFormat);
				sheet.addCell(lb16);
				jxl.write.Number lb17 = new jxl.write.Number(17, i, Helper.float2Double(vRpDyCell3G.getHsupaCssr()),cellFormat);
				sheet.addCell(lb17);
				jxl.write.Number lb18 = new jxl.write.Number(18, i, Helper.float2Double(vRpDyCell3G.getHsdpaCssr()),cellFormat);
				sheet.addCell(lb18);
				jxl.write.Number lb19 = new jxl.write.Number(19, i, Helper.int2Double(vRpDyCell3G.getSpeechDrop()),cellFormat);
				sheet.addCell(lb19);
				jxl.write.Number lb20 = new jxl.write.Number(20, i, Helper.float2Double(vRpDyCell3G.getSpeechDrpr()),cellFormat);
				sheet.addCell(lb20);
				jxl.write.Number lb21 = new jxl.write.Number(21, i, Helper.int2Double(vRpDyCell3G.getCs64Drop()),cellFormat);
				sheet.addCell(lb21);
				jxl.write.Number lb22 = new jxl.write.Number(22, i, Helper.float2Double(vRpDyCell3G.getCs64Drpr()),cellFormat);
				sheet.addCell(lb22);
				jxl.write.Number lb23 = new jxl.write.Number(23, i, Helper.int2Double(vRpDyCell3G.getPsr99Drop()),cellFormat);
				sheet.addCell(lb23);
				jxl.write.Number lb24 = new jxl.write.Number(24, i, Helper.float2Double(vRpDyCell3G.getPsr99Drpr()),cellFormat);
				sheet.addCell(lb24);
				jxl.write.Number lb25 = new jxl.write.Number(25, i, Helper.int2Double(vRpDyCell3G.getHsupaDrop()),cellFormat);
				sheet.addCell(lb25);
				jxl.write.Number lb26 = new jxl.write.Number(26, i, Helper.float2Double(vRpDyCell3G.getHsupaDrpr()),cellFormat);
				sheet.addCell(lb26);
				jxl.write.Number lb27 = new jxl.write.Number(27, i, Helper.int2Double(vRpDyCell3G.getHsdpaDrop()),cellFormat);
				sheet.addCell(lb27);
				jxl.write.Number lb28 = new jxl.write.Number(28, i, Helper.float2Double(vRpDyCell3G.getHsdpaDrpr()),cellFormat);
				sheet.addCell(lb28);
				jxl.write.Number lb29 = new jxl.write.Number(29, i, Helper.float2Double(vRpDyCell3G.getPsr99UlThroughtput()),cellFormat);
				sheet.addCell(lb29);
				jxl.write.Number lb30 = new jxl.write.Number(30, i, Helper.float2Double(vRpDyCell3G.getPsr99DlThroughtput()),cellFormat);
				sheet.addCell(lb30);
				jxl.write.Number lb31 = new jxl.write.Number(31, i, Helper.float2Double(vRpDyCell3G.getHsupaUlThroughtput()),cellFormat);
				sheet.addCell(lb31);
				jxl.write.Number lb32 = new jxl.write.Number(32, i, Helper.float2Double(vRpDyCell3G.getHsdpaDlThroughtput()),cellFormat);
				sheet.addCell(lb32);
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
