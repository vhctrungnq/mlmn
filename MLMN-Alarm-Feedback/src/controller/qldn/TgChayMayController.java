package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnTramTTNhienLieuDAO;
import vo.CTableConfig;
import vo.QldnTramTTNhienLieu;
import vo.alarm.utils.HelpTableConfigs;

/**
 * @author TRUNGNQ
 *
 */

@Controller
@RequestMapping("qldn/tg-chay-may/*")
public class TgChayMayController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	QldnTramTTNhienLieuDAO qldnTramTTNhienLieuDAO;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final String datePattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
	private final String fullDatePattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
	@RequestMapping("list")
	public String showList(@RequestParam(required = true) String loaiThanhToan,
			@RequestParam(required = false) String sdate, 
			@RequestParam(required = false) String edate, ModelMap model) {

		if (edate == null) {
			edate = df.format(new Date());
		} 
		if (sdate == null) {
			sdate = df.format(new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000));
		}

		try {
			df.parse(edate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			edate = df.format(new Date());
		}

		try {
			df.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			sdate = df.format(new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000));
		}
		String table = "";
		if (loaiThanhToan.equals("XHH")) {
			table = "QLDN_TRAM_TT_NHIEN_LIEU_XHH";
		} else if (loaiThanhToan.equals("TTL")) {
			table = "QLDN_TRAM_TT_NHIEN_LIEU_TTL";
		} else if (loaiThanhToan.equals("TVT")) {
			table = "QLDN_TRAM_TT_NHIEN_LIEU_TVT";
		}
		// do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(table);
		// Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(table);
		String url = "data.htm?loaiThanhToan=" + loaiThanhToan +  "&sdate=" + sdate + "&edate=" + edate;
		// Grid
		String gridManage = HelpTableConfigs.getGridPagingUrl(configList, "jqxgrid", url, "jqxlistbox", listSource,
				"Menu", null, "multiplerowsextended", "Y");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateNow = formatter.format(new Date());
		String exportFileName = table + dateNow;
		model.addAttribute("loaiThanhToan", loaiThanhToan);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("gridManage", gridManage);
		return "jspQLDN/tgChayMay";
	}

	@RequestMapping("data")
	public void data(@RequestParam(required = true) String loaiThanhToan,
			@RequestParam String sdate, @RequestParam String edate,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
		dataList = qldnTramTTNhienLieuDAO.getData(loaiThanhToan, sdate, edate);
		Gson gs = new Gson();
		String jsonDataList = gs.toJson(dataList);
		PrintWriter out;
		out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonDataList);
		out.close();
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) String loaiThanhToan,
			@RequestParam(required = false) String id, ModelMap model) {
		QldnTramTTNhienLieu record = new QldnTramTTNhienLieu();
		if (id != null && !id.equals("")) {
			record = qldnTramTTNhienLieuDAO.selectByPrimaryKey(Integer.parseInt(id));
			if (record != null) {
				if (record.getNgayChayMf() != null) {
					model.addAttribute("ngayChay", df.format(record.getNgayChayMf()));
				}
				if (record.getTgBatDau() != null) {
					model.addAttribute("tgBatDau", dff.format(record.getTgBatDau()));
				}
				if (record.getTgKetThuc() != null) {
					model.addAttribute("tgKetThuc", dff.format(record.getTgKetThuc()));
				}
				if (record.getNgayThanhToan() != null) {
					model.addAttribute("ngayThanhToan", df.format(record.getNgayThanhToan()));
				}
			}
		}
		model.addAttribute("loaiThanhToan", loaiThanhToan);
		model.addAttribute("record", record);
		return "jspQLDN/tgChayMayForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String add(@RequestParam(required = true) String loaiThanhToan,
			@ModelAttribute("record") @Valid QldnTramTTNhienLieu record,
			BindingResult result, @RequestParam(required = false) String id, 
			@RequestParam(required = false) String ngayChay,
			@RequestParam(required = false) String ngayThanhToan,
			@RequestParam(required = false) String tgBatDau,
			@RequestParam(required = false) String tgKetThuc,
			HttpServletRequest request, ModelMap model) {
		dff.setLenient(false);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();			
		Date tgBatDauDate = null;
		Date tgKetThucDate = null;
		Date ngayChayDate = null;
	//	record.setLoaiThanhToan(loaiThanhToan);
		model.addAttribute("ngayChay", ngayChay);
		model.addAttribute("ngayThanhToan", ngayThanhToan);
		model.addAttribute("tgBatDau", tgBatDau);
		model.addAttribute("tgKetThuc", tgKetThuc);
		model.addAttribute("loaiThanhToan", loaiThanhToan);
		if (loaiThanhToan.equals("TTL")) {
			// bo cac truong hop nhu 1/2/2017a$ hay 1/2/20a7
			if (ngayThanhToan.matches(datePattern)) {
				try {
					Date ngayThanhToanDate = df.parse(ngayThanhToan);
					if (ngayThanhToanDate.compareTo(new Date()) > 0) {
						model.addAttribute("errorField", "ngayThanhToan");
						model.addAttribute("reason", "invalid value!");
						return "jspQLDN/tgChayMayForm";
					}
					record.setNgayThanhToan(ngayThanhToanDate);
					//if (record.getN)
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					model.addAttribute("errorField", "ngayThanhToan");
					model.addAttribute("reason", "wrong format!");
				}
			} else {				
				model.addAttribute("errorField", "ngayThanhToan");
				model.addAttribute("reason", "wrong format!");
			}
			
		} else {
			if (ngayChay.matches(datePattern)) {			
				try {
					ngayChayDate = df.parse(ngayChay);
					if (ngayChayDate.compareTo(new Date()) > 0) {
						model.addAttribute("errorField", "ngayChay");
						model.addAttribute("reason", "invalid value!");
						return "jspQLDN/tgChayMayForm";
					}
					record.setNgayChayMf(ngayChayDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					model.addAttribute("errorField", "ngayChay");
					model.addAttribute("reason", "wrong format!");
					return "jspQLDN/tgChayMayForm";
				}
			} else {
				model.addAttribute("errorField", "ngayChay");
				model.addAttribute("reason", "wrong format!");
				return "jspQLDN/tgChayMayForm";
			}		
		}
		
		if (tgBatDau.matches(fullDatePattern)) {
			try {
				tgBatDauDate = dff.parse(tgBatDau);
				if (tgBatDauDate.compareTo(new Date()) > 0) {
					model.addAttribute("reason", "invalid value!");
					model.addAttribute("errorField", "tgBatDau");
					return "jspQLDN/tgChayMayForm";
				}
				record.setTgBatDau(tgBatDauDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				model.addAttribute("errorField", "tgBatDau");
				model.addAttribute("reason", "wrong format!");
				return "jspQLDN/tgChayMayForm";
			}
		} else {
			model.addAttribute("errorField", "tgBatDau");
			model.addAttribute("reason", "wrong format!");
			return "jspQLDN/tgChayMayForm";
		}
		
		if (tgKetThuc.matches(fullDatePattern)) {
			try {
				tgKetThucDate = dff.parse(tgKetThuc);
				if (tgBatDauDate.compareTo(new Date()) > 0) {
					model.addAttribute("reason", "invalid value!");
					model.addAttribute("errorField", "tgKetThuc");
					//saveMessageKey(request, "Thời gian kết thúc không thể lớn hơn thời gian hiện tại");
					return "jspQLDN/tgChayMayForm";
				}
				record.setTgKetThuc(tgKetThucDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				model.addAttribute("errorField", "tgKetThuc");
				model.addAttribute("reason", "wrong format!");
				return "jspQLDN/tgChayMayForm";
			}
		} else {
			model.addAttribute("errorField", "tgKetThuc");
			model.addAttribute("reason", "wrong format!");
			return "jspQLDN/tgChayMayForm";
		}
		

		if (tgBatDauDate != null && tgKetThucDate != null && tgBatDauDate.compareTo(tgKetThucDate) >= 0) {
			saveMessageKey(request, "Thời gian kết thúc phải sau thời gian bắt đầu sự cố");
			return "jspQLDN/tgChayMayForm";
		}

		if (record.getId() != null) {
			record.setNgaySua(new Date());
			record.setNvSua(username);
			qldnTramTTNhienLieuDAO.updateByPrimaryKey(record);
			saveMessageKey(request, "define.updateSuccelfull");
		} else {
			// tao trigger
			//record.setId(qldnTramTTNhienLieuDAO.getMaxId() + 1);
			record.setNgayNhap(new Date());
			record.setNvNhap(username);
			qldnTramTTNhienLieuDAO.insertSelective(record);
			saveMessageKey(request, "define.insertSucceFull");
		}
		model.clear();
		return "redirect:list.htm?loaiThanhToan=" + loaiThanhToan;
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete (@RequestParam(required = false) String loaiThanhToan,
			@RequestParam(required = false) String idList,
			HttpServletRequest request, HttpServletResponse response) {
		String[] idArray = idList.split(",");
		int count = 0;
		for (String id : idArray) {
			qldnTramTTNhienLieuDAO.deleteByPrimaryKey(Integer.parseInt(id));
			count++;

		}
		saveMessage(request, "Xóa thành công "+count+" bản ghi");
		return "redirect:list.htm?loaiThanhToan=" + loaiThanhToan;
	}

}
