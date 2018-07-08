package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnThongTinTramDAO;
import dao.QldnTramTTNhienLieuDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnTramTTDien;
import vo.QldnTramTTNhienLieu;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/nhien-lieu/*")
public class QLDNThanhToanNhienLieuController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	QldnTramTTNhienLieuDAO qldnTramTTNhienLieuDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final String datePattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
	private final String fullDatePattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
	// type: Loai thanh toan XXH, TVT,TTL
	@RequestMapping(value = "list", method = RequestMethod.GET) 
	public ModelAndView list(
			@RequestParam(required = false) String ngayChayMfF,
			@RequestParam(required = false) String ngayChayMfT,
			@RequestParam(required = false) String idTram,
			@RequestParam(required = false) String loaiNhienLieu,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String tenXhhVtt,
			@RequestParam(required = false) String type,
			
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SYS_PARAMETER> sysParemeterTitle = sysParameterDao.gettitleQLDNTTNhienLieu("QLDN_TT_NHIEN_LIEU",type);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		if (ngayChayMfT == null || ngayChayMfT.equals("")
				||(ngayChayMfT!=null && !ngayChayMfT.equals("") && DateTools.isValid("dd/MM/yyyy", ngayChayMfT)==false))
		{
			ngayChayMfT = df.format(cal.getTime());
		}
		if (ngayChayMfF == null || ngayChayMfF.equals("")
				||(ngayChayMfF!=null && !ngayChayMfF.equals("") && DateTools.isValid("dd/MM/yyyy", ngayChayMfF)==false))
		{
			cal.add(Calendar.DATE,-1);
			ngayChayMfF = df.format(cal.getTime());
		}
		
		
		if (idTram!=null&&!idTram.equals("null"))
		{
			idTram=idTram.trim();
		}
		else
		{
			idTram="";
		}
		if (tinh!=null&&!tinh.equals("null"))
		{
			tinh=tinh.trim();
		}
		else
		{
			tinh="";
		}
		if (loaiNhienLieu!=null&&!loaiNhienLieu.equals("null"))
		{
			loaiNhienLieu=loaiNhienLieu.trim();
		}
		else
		{
			loaiNhienLieu="";
		}
		if (tenXhhVtt!=null&&!tenXhhVtt.equals("null"))
		{
			tenXhhVtt=tenXhhVtt.trim();
		}
		else
		{
			tenXhhVtt="";
		}
		if (type!=null&&!type.equals("null"))
		{
			type=type.trim();
		}
		else
		{
			type="";
		}
		
		String tableName="QLDN_TRAM_TT_NHIEN_LIEU";
		if (type!=null&&!type.equals(""))
		{
			tableName= tableName+"_"+type.toUpperCase();
		}
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tableName);
		//url
		String url = "data.htm?ngayChayMfF="+ngayChayMfF+"&ngayChayMfT="+ngayChayMfT+"&idTram="+idTram+"&loaiNhienLieu="+loaiNhienLieu
				+"&tinh="+tinh+"&tenXhhVtt="+tenXhhVtt+"&type="+type+"&username="+username;
		//Grid
		String gridData = HelpTableConfigs.getGridPagingUrl(configList, "gridData", url, "listData", listSource, "Menu", null, "multiplerowsextended","Y");
		model.addAttribute("gridData", gridData);	
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = tableName+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("ngayChayMfF", ngayChayMfF);
		model.addAttribute("ngayChayMfT", ngayChayMfT);
		model.addAttribute("idTram", idTram);
		model.addAttribute("loaiNhienLieu", loaiNhienLieu);
		model.addAttribute("tinh", tinh);
		model.addAttribute("tenXhhVtt", tenXhhVtt);
		model.addAttribute("type", type);
		
		return new ModelAndView("jspQLDN/tramTTNhienLieuList");
	}

	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String ngayChayMfF,
			@RequestParam(required = false) String ngayChayMfT,
			@RequestParam(required = false) String idTram,
			@RequestParam(required = false) String loaiNhienLieu,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String tenXhhVtt,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String username,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
		dataList = qldnTramTTNhienLieuDAO.getTramTTNhienLieuList(ngayChayMfF,ngayChayMfT,idTram,loaiNhienLieu,tinh,tenXhhVtt,type,username);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(dataList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	//Xoa
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,@RequestParam (required = true) String type,
			HttpServletRequest request, Model model) {
		try {
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				qldnTramTTNhienLieuDAO.deleteByPrimaryKey(Integer.parseInt(id));
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm?type="+type;
	}
	@RequestMapping("exportData")
	public ModelAndView exportData(@RequestParam(required = false) String ngayChayMfF,
			@RequestParam(required = false) String ngayChayMfT,
			@RequestParam(required = false) String idTram,
			@RequestParam(required = false) String loaiNhienLieu,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String tenXhhVtt,
			@RequestParam(required = false) String type
			, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		try {
				// B1: lay du lieu
			List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
			dataList = qldnTramTTNhienLieuDAO.getTramTTNhienLieuList(ngayChayMfF,ngayChayMfT,idTram,loaiNhienLieu,tinh,tenXhhVtt,type,username);
			List<Object> dataObjs = new ArrayList<Object>();
			for (QldnTramTTNhienLieu record : dataList) {
				dataObjs.add(record);
			}
			// B2 thuc hien export
			String tableName="QLDN_TRAM_TT_NHIEN_LIEU";
			if (type!=null&&!type.equals(""))
			{
				tableName= tableName+"_"+type.toUpperCase();
			}
			List<CTableConfig> headers = cTableConfigDAO.getColumnList(tableName);
			ExportTools.exportFileExcel("QldnTramTTNhienLieu", dataObjs, headers, request, response);

		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return null;

	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) String type,
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
		model.addAttribute("type", type);
		model.addAttribute("record", record);
		return "jspQLDN/tgChayMayForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String add(
			@ModelAttribute("record") @Valid QldnTramTTNhienLieu record,
			BindingResult result, 
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String id, 
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
		/*record.setLoaiThanhToan(loaiThanhToan);*/
		model.addAttribute("ngayChay", ngayChay);
		model.addAttribute("ngayThanhToan", ngayThanhToan);
		model.addAttribute("tgBatDau", tgBatDau);
		model.addAttribute("tgKetThuc", tgKetThuc);
		model.addAttribute("type", type);
		
			// bo cac truong hop nhu 1/2/2017a$ hay 1/2/20a7
			if (ngayThanhToan!=null && ngayThanhToan.matches(datePattern)) {
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
			
		 
			if (ngayChay!=null && ngayChay.matches(datePattern)) {			
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
		
		
		if (tgBatDau!=null && tgBatDau.matches(fullDatePattern)) {
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
		
		if (tgKetThuc!=null && tgKetThuc.matches(fullDatePattern)) {
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
		return "redirect:list.htm?type=" + type;
	}
}
