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

import vo.CTableConfig;
import vo.MDepartment;
import vo.QldnDonGiaDien;
import vo.QldnThongTinTram;
import vo.SysUsers;
import vo.VAlHCell;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.MDepartmentDAO;
import dao.QldnDonGiaDienDAO;
import dao.QldnThongTinTramDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import dao.VAlHCellDAO;

@Controller
@RequestMapping("/dien/thong-tin-tram/*")
public class QLDNThongTinTramController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	@Autowired
	private VAlHCellDAO vAlHCellDao;
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired
	private QldnThongTinTramDAO qldnThongTinTramDao;
	
	@Autowired
	private QldnDonGiaDienDAO qldnDonGiaDienDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			@RequestParam(required = false) String idTram,
			@RequestParam(required = false) String loaitram,
			@RequestParam(required = false) String daiVT,
			@RequestParam(required = false) String dienDvth,
			@RequestParam(required = false) String makh,
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (idTram!=null&&!idTram.equals("null"))
		{
			idTram=idTram.trim();
		}
		else
		{
			idTram="";
		}
		if (makh!=null&&!makh.equals("null"))
		{
			makh=makh.trim();
		}
		else
		{
			makh="";
		}

		
		if (daiVT!=null&&!daiVT.equals("null"))
		{
			if (userLogin.getIsRoleManager().equalsIgnoreCase("N"))
			{
				daiVT=daiVT.trim();
			}
			else
				daiVT="";
		}
		else
		{
			daiVT=userLogin.getMaPhong();
		}
		
		if (dienDvth!=null&&!dienDvth.equals("null"))
		{
			dienDvth=dienDvth.trim();
		}
		else
		{
			dienDvth="";
		}
		
		if (loaitram!=null&&!loaitram.equals("null"))
		{
			loaitram=loaitram.trim();
		}
		else
		{
			loaitram="";
		}
		// Danh sách phòng ban
		List<MDepartment> maPhongList = mDepartmentDAO.getDepartmentForShiftList("1");
		model.addAttribute("maPhongList", maPhongList);
		
		//Lay danh sach province theo user
		/*String cn="";
		List<String> siteList = qldnThongTinTramDao.getFilterThongTinTramList("ID_TRAM",username);
		String siteArray="var siteList = new Array(";//Danh sach district cho cobobox
		cn="";
		for (String dis : siteList) {
			siteArray = siteArray + cn +"\""+dis+"\"";
			cn=",";
		}
		siteArray = siteArray+");";
		model.addAttribute("siteList", siteArray);*/
			
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_THONG_TIN_TRAM");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_THONG_TIN_TRAM");
		//url
		String url = "data.htm?idTram="+idTram+"&loaitram="+loaitram
				+"&daiVT="+daiVT+"&dienDvth="+dienDvth
				+"&makh="+makh+"&username="+username;
		//Grid
		String gridData = HelpTableConfigs.getGridPagingUrl(configList, "gridData", url, "listData", listSource, "Menu", null, "multiplerowsextended","Y");
		model.addAttribute("gridData", gridData);	
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_THONG_TIN_TRAM"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("isRolaManager", userLogin.getIsRoleManager());
		model.addAttribute("idTram", idTram);
		model.addAttribute("loaitram", loaitram);
		model.addAttribute("dienDvth", dienDvth);
		model.addAttribute("makh", makh);
		model.addAttribute("daiVT", daiVT);
		return "jspQLDN/thongTinTramList";
	}

	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String idTram,
			@RequestParam(required = false) String loaitram,
			@RequestParam(required = false) String daiVT,
			@RequestParam(required = false) String dienDvth,
			@RequestParam(required = false) String makh,
			@RequestParam(required = false) String username,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<QldnThongTinTram> dataList = new ArrayList<QldnThongTinTram>();
		dataList = qldnThongTinTramDao.getThongTinTramList(idTram,loaitram,daiVT,dienDvth,makh,username);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(dataList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	//Xoa
		@RequestMapping(value = "delete", method = RequestMethod.GET)
		public String onDelete(@RequestParam (required = true) String idList,
				HttpServletRequest request, Model model) {
			try {
				String[] idArray = idList.split(",");
				for (String idTram : idArray) {
					qldnDonGiaDienDao.deleteByPrimaryKey(idTram);
					qldnThongTinTramDao.deleteByPrimaryKey(idTram);
				}
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			return "redirect:list.htm";
		}
		//Them, sua
		@RequestMapping(value = "formHCM", method = RequestMethod.GET)
		public String formHCM(@RequestParam (required = false) String idTram,
				HttpServletRequest request, Model model) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByUsename(username);
			model.addAttribute("isRoleManager", userLogin.getIsRoleManager());
			model.addAttribute("nhom", userLogin.getMaPhong());
			// Danh sách phòng ban
			List<MDepartment> maPhongList = mDepartmentDAO.getDepartmentForShiftList("1");
			model.addAttribute("maPhongList", maPhongList);
			
			QldnThongTinTram thongTinTram = new QldnThongTinTram();
			QldnDonGiaDien dongiaDien = new QldnDonGiaDien();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String typeF="";
			if (idTram!=null&&!idTram.equals(""))
			{
				thongTinTram = qldnThongTinTramDao.selectByPrimaryKey(idTram);
				typeF="edit";
				model.addAttribute("loaitram", thongTinTram.getLoaitram());
				model.addAttribute("dienDvth", thongTinTram.getDienDvth());
				
			}
			else
			{
				typeF="add";
			}
			
			model.addAttribute("thongTinTram", thongTinTram);		
			model.addAttribute("typeF", typeF);
			return "jspQLDN/thongTinTramFormHCM";
		}
				
		@RequestMapping(value = "formHCM", method = RequestMethod.POST)
		public String onSubmitHCM(@ModelAttribute("thongTinTram") @Valid QldnThongTinTram thongTinTram, BindingResult result,
				@RequestParam(required = false) String typeF,
				@RequestParam(required = false) String loaitram,
				@RequestParam(required = false) String dienDvth,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			SysUsers userLogin = sysUsersDao.selectByUsename(username);
			model.addAttribute("isRoleManager", userLogin.getIsRoleManager());
			// Danh sách phòng ban
			List<MDepartment> maPhongList = mDepartmentDAO.getDepartmentForShiftList("1");
			model.addAttribute("maPhongList", maPhongList);
			//Lay danh sach Cell theo user
			List<String> cellList = sysUserEquipmentNameDAO.getCellList(null,null,null,null);
		
		boolean bool=true;
		if (thongTinTram.getIdTram()==null)
		{
			model.addAttribute("erroridTram", "*");
			bool= false;
		}
		if (loaitram==null)
		{
			model.addAttribute("errorLoaitram", "*");
			bool= false;
		}
		if (thongTinTram.getMakh()==null)
		{
			model.addAttribute("errorMakh", "*");
			bool= false;
		}
			if (result.hasErrors() || bool== false) {
				saveMessageKey(request, "alarmExtend.errorField");
				model.addAttribute("thongTinTram", thongTinTram);		
				model.addAttribute("typeF", typeF);
				model.addAttribute("loaitram", loaitram);
				return "jspQLDN/thongTinTramFormHCM";
			}
			else
			{
				thongTinTram.setLoaitram(loaitram);
				thongTinTram.setTgttTq("Tháng");
				thongTinTram.setNguoittdien(username);
				thongTinTram.setNgayps(new Date());
				thongTinTram.setDienDvth(dienDvth);
				if (typeF!=null && typeF.equalsIgnoreCase("edit"))
				{
					//sua chua
					thongTinTram.setNgaySua(new Date());
					thongTinTram.setNguoiSua(username);
					qldnThongTinTramDao.updateByPrimaryKey(thongTinTram);
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
					
				}
				else
				{
					
					VAlHCell siteInfo = vAlHCellDao.getInfoSite(thongTinTram.getIdTram());
					if (siteInfo!=null) {
						thongTinTram.setTinh(siteInfo.getProvince());
						thongTinTram.setHuyen(siteInfo.getDistrict());
					}
					thongTinTram.setNgayTao(new Date());
					thongTinTram.setNguoiTao(username);
					qldnThongTinTramDao.insert(thongTinTram);
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}
			}
			return "redirect:list.htm";
		}
		
		
		// them,sua
		@RequestMapping(value = "form", method = RequestMethod.GET)
		public String form(@RequestParam (required = false) String idTram,
				HttpServletRequest request, Model model) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			QldnThongTinTram thongTinTram = new QldnThongTinTram();
			QldnDonGiaDien dongiaDien = new QldnDonGiaDien();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String ngayps = "";
			String thangpsdien="";
			String thanghuytram="";
			String tgThuchien="";
			String typeF="";
			if (idTram!=null&&!idTram.equals(""))
			{
				thongTinTram = qldnThongTinTramDao.selectByPrimaryKey(idTram);
				ngayps = thongTinTram.getNgaypsStr();
				thangpsdien = thongTinTram.getThangpsdienStr();
				thanghuytram = thongTinTram.getThanghuytramStr();
				tgThuchien= thongTinTram.getTgThuchienStr();
				dongiaDien = qldnDonGiaDienDao.selectByPrimaryKey(idTram);
				if (dongiaDien!=null)
				{
					thongTinTram.setDg1Gia(dongiaDien.getDg1Gia());
					thongTinTram.setDg3Muc1(dongiaDien.getDg3Muc1());
					thongTinTram.setDg3Muc2(dongiaDien.getDg3Muc2());
					thongTinTram.setDg3Muc3(dongiaDien.getDg3Muc3());
					thongTinTram.setDgLoai(dongiaDien.getDgLoai());
					thongTinTram.setDgTlKd(dongiaDien.getDgTlKd());
					thongTinTram.setDgTlKdGia(dongiaDien.getDgTlKdGia());
					thongTinTram.setDgTlSx(dongiaDien.getDgTlSx());
					thongTinTram.setDgTlSxGia(dongiaDien.getDgTlSxGia());
				}
				typeF="edit";
				model.addAttribute("loaitram", thongTinTram.getLoaitram());
				model.addAttribute("nguonccd", thongTinTram.getNguonccd());
				model.addAttribute("httt", thongTinTram.getHttt());
				model.addAttribute("dienDvth", thongTinTram.getDienDvth());
				model.addAttribute("dgLoai", thongTinTram.getDgLoai());
				model.addAttribute("tgttTq", thongTinTram.getTgttTq());
				model.addAttribute("tgttCus", thongTinTram.getTgttCus());
				model.addAttribute("nguoittdien", thongTinTram.getNguoittdien());
			}
			else
			{
				ngayps = df.format(cal.getTime());
				thongTinTram.setNguoittdien(username);
				typeF="add";
				model.addAttribute("nguoittdien", username);
			}
			
			model.addAttribute("thongTinTram", thongTinTram);		
			model.addAttribute("ngayps", ngayps); 
			model.addAttribute("thangpsdien", thangpsdien); 
			model.addAttribute("thanghuytram", thanghuytram); 
			model.addAttribute("tgThuchien", tgThuchien); 
			model.addAttribute("typeF", typeF);
			return "jspQLDN/thongTinTramForm";
		}
				
		@RequestMapping(value = "form", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("thongTinTram") @Valid QldnThongTinTram thongTinTram, BindingResult result,
				@RequestParam(required = false) String ngayps,
				@RequestParam(required = false) String thangpsdien,
				@RequestParam(required = false) String thanghuytram,
				@RequestParam(required = false) String tgThuchien,
				@RequestParam(required = false) String typeF,
				@RequestParam(required = false) String loaitram,
				@RequestParam(required = false) String nguonccd,
				@RequestParam(required = false) String httt,
				@RequestParam(required = false) String dienDvth,
				@RequestParam(required = false) String dgLoai,
				@RequestParam(required = false) String tgttTq,
				@RequestParam(required = false) String tgttCus,
				@RequestParam(required = false) String nguoittdien,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			//Lay danh sach Cell theo user
			List<String> cellList = sysUserEquipmentNameDAO.getCellList(null,null,null,null);
		
		boolean bool=true;
		if (ngayps==null || (ngayps!=null && (ngayps.equals("")||DateTools.isValid("dd/MM/yyyy", ngayps)==false)))
		{
			model.addAttribute("errorngayps", "*");
			bool= false;
		}
		if (thangpsdien!=null && !thangpsdien.equals("") && DateTools.isValid("dd/MM/yyyy", thangpsdien)==false)
		{
			model.addAttribute("errorthangpsdien", "*");
			bool= false;
		}
		if ( thanghuytram!=null && !thanghuytram.equals("") &&  DateTools.isValid("dd/MM/yyyy", thanghuytram)==false)
		{
			model.addAttribute("errorthanghuytram", "*");
			bool= false;
		}
		if (tgThuchien!=null && !tgThuchien.equals("") && DateTools.isValid("dd/MM/yyyy", tgThuchien)==false)
		{
			model.addAttribute("errortgThuchien", "*");
			bool= false;
		}
		if (thongTinTram.getIdTram()==null)
		{
			model.addAttribute("erroridTram", "*");
			bool= false;
		}
		if (loaitram==null)
		{
			model.addAttribute("errorLoaitram", "*");
			bool= false;
		}
		if (nguonccd==null)
		{
			model.addAttribute("errorNguonccd", "*");
			bool= false;
		}
		if (httt==null)
		{
			model.addAttribute("errorHttt", "*");
			bool= false;
		}
		if (thongTinTram.getMakh()==null)
		{
			model.addAttribute("errorMakh", "*");
			bool= false;
		}
		if (dgLoai==null)
		{
			model.addAttribute("errorDgLoai", "*");
			bool= false;
		}
		if (nguoittdien==null)
		{
			model.addAttribute("errorNguoittdien", "*");
			bool= false;
		}
			if (result.hasErrors() || bool== false) {
				saveMessageKey(request, "alarmExtend.errorField");
				model.addAttribute("thongTinTram", thongTinTram);		
				model.addAttribute("ngayps", ngayps); 
				model.addAttribute("thangpsdien", thangpsdien); 
				model.addAttribute("thanghuytram", thanghuytram); 
				model.addAttribute("tgThuchien", tgThuchien); 
				model.addAttribute("typeF", typeF);
				model.addAttribute("loaitram", loaitram);
				model.addAttribute("nguonccd", nguonccd);
				model.addAttribute("httt", httt);
				model.addAttribute("dienDvth", dienDvth);
				model.addAttribute("dgLoai", dgLoai);
				model.addAttribute("tgttTq", tgttTq);
				model.addAttribute("tgttCus", tgttCus);
				model.addAttribute("nguoittdien", nguoittdien);
				model.addAttribute("typeF", typeF);
				return "jspQLDN/thongTinTramForm";
			}
			else
			{
				thongTinTram.setLoaitram(loaitram);
				thongTinTram.setNguonccd(nguonccd);
				thongTinTram.setHttt(httt);
				thongTinTram.setDienDvth(dienDvth);
				thongTinTram.setDgLoai(dgLoai);
				thongTinTram.setTgttTq(tgttTq);
				thongTinTram.setTgttCus(tgttCus);
				thongTinTram.setNguoittdien(nguoittdien);
				if (ngayps != null && !ngayps.equals("")) {
					thongTinTram.setNgayps(df.parse(ngayps));
				}
				if (thangpsdien != null && !thangpsdien.equals("")) {
					thongTinTram.setThangpsdien(df.parse(thangpsdien));
				}
				if (thanghuytram != null && !thanghuytram.equals("")) {
					thongTinTram.setThanghuytram(df.parse(thanghuytram));
				}
				if (tgThuchien != null && !tgThuchien.equals("")) {
					thongTinTram.setTgThuchien(df.parse(tgThuchien));
				}
				
				QldnDonGiaDien dgDien = new QldnDonGiaDien();
				dgDien.setIdTram(thongTinTram.getIdTram());
				dgDien.setDgLoai(thongTinTram.getDgLoai());
				dgDien.setDg1Gia(thongTinTram.getDg1Gia());
				dgDien.setDg3Muc1(thongTinTram.getDg3Muc1());
				dgDien.setDg3Muc2(thongTinTram.getDg3Muc2());
				dgDien.setDg3Muc3(thongTinTram.getDg3Muc3());
				dgDien.setDgTlKd(thongTinTram.getDgTlKd());
				dgDien.setDgTlKdGia(thongTinTram.getDgTlKdGia());
				dgDien.setDgTlSx(thongTinTram.getDgTlSx());
				dgDien.setDgTlSxGia(thongTinTram.getDgTlSxGia());
				
				if (typeF!=null && typeF.equalsIgnoreCase("edit"))
				{
					//sua chua
					thongTinTram.setNgaySua(new Date());
					thongTinTram.setNguoiSua(username);
					qldnThongTinTramDao.updateByPrimaryKey(thongTinTram);
					qldnDonGiaDienDao.updateByPrimaryKey(dgDien);
					
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
					
				}
				else
				{
					
					VAlHCell siteInfo = vAlHCellDao.getInfoSite(thongTinTram.getIdTram());
					if (siteInfo!=null) {
						thongTinTram.setTinh(siteInfo.getProvince());
						thongTinTram.setHuyen(siteInfo.getDistrict());
					}
					thongTinTram.setNgayTao(new Date());
					thongTinTram.setNguoiTao(username);
					qldnThongTinTramDao.insert(thongTinTram);
					qldnDonGiaDienDao.insert(dgDien);
					
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}
			}
			return "redirect:list.htm";
		}
		
		@RequestMapping("exportData")
		public ModelAndView exportData(
				@RequestParam(required = false) String idTram,
				@RequestParam(required = false) String loaitram,
				@RequestParam(required = false) String daiVT,
				@RequestParam(required = false) String dienDvth,
				@RequestParam(required = false) String makh,
			   HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			try {
				// B1: lay du lieu
				List<QldnThongTinTram> dataList = new ArrayList<QldnThongTinTram>();
				dataList = qldnThongTinTramDao.getThongTinTramList(idTram,loaitram,daiVT,dienDvth,makh,username);
				List<Object> dataObjs = new ArrayList<Object>();
				for (QldnThongTinTram record : dataList) {
					dataObjs.add(record);
				}
				// B2 thuc hien export
				List<CTableConfig> headers = cTableConfigDAO.getColumnList("QLDN_THONG_TIN_TRAM");
				ExportTools.exportFileExcel("QldnThongTinTram", dataObjs, headers, request, response);

			} catch (Exception exp) {
				exp.printStackTrace();
			}

			return null;

		}
		
		@RequestMapping(value = "capNhatDM", method = RequestMethod.GET)
		public String capNhatDM(
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			try
			{
				qldnThongTinTramDao.updateDinhMuc(null, null, null, null);
				saveMessage(request, "Cập nhật định mức thành công");	
			}
			catch( Exception ex)
			{
				saveMessage(request, "Cập nhật định mức không thành công");	
			}	
			return "redirect:list.htm";
		}
}
