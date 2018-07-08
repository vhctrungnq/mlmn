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
import vo.QldnDonGiaDien;
import vo.QldnThongTinTram;
import vo.QldnTramTTDien;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnDonGiaDienDAO;
import dao.QldnThongTinTramDAO;
import dao.QldnTramTTDienDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/dien/thanh-toan-tram/*")
public class QLDNThanhToanDienController extends BaseController {
		@Autowired
		private CTableConfigDAO cTableConfigDAO;
		@Autowired
		private SYS_PARAMETERDAO sysParameterDao;
		@Autowired 
		private SysUsersDAO sysUsersDao;
		
		@Autowired
		private QldnThongTinTramDAO qldnThongTinTramDao;
		
		@Autowired
		private QldnDonGiaDienDAO qldnDonGiaDienDao;
		
		@Autowired
		private QldnTramTTDienDAO qldnTramTTDienDao;
		
		@Autowired
		private SysUserAreaDAO sysUserAreaDAO;
		
		
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		@RequestMapping(value = "list", method = RequestMethod.GET) 
		public ModelAndView list(
				@RequestParam(required = false) String idTram,
				@RequestParam(required = false) String loaitram,
				//@RequestParam(required = false) String tinh,
				@RequestParam(required = false) String daiVT,
				//@RequestParam(required = false) String tgttTq,
				@RequestParam(required = false) String thangQuyTt,
				@RequestParam(required = false) String namTt,
				@RequestParam(required = false) String status,
				@RequestParam(required = false) String type,
				Model model, HttpServletRequest request) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
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
			if (daiVT!=null&&!daiVT.equals("null"))
			{
				daiVT=daiVT.trim();
			}
			else
			{
				daiVT="";
			}

			if (thangQuyTt!=null&&!thangQuyTt.equals("null"))
			{
				thangQuyTt=thangQuyTt.trim();
			}
			else
			{
				thangQuyTt = String.format("%02d", cal.get(Calendar.MONTH) + 1);
				
			}
			
			if (namTt!=null&&!namTt.equals("null"))
			{
				namTt=namTt.trim();
			}
			else
			{
				namTt= String.valueOf(cal.get(Calendar.YEAR));
			}
			
			if (type!=null&&!type.equals("null"))
			{
				type=type.trim();
			}
			else
			{
				type="";
			}
			if (status!=null&&!status.equals("null"))
			{
				status=status.trim();
			}
			else
			{
				status="";
			}
			if (loaitram!=null&&!loaitram.equals("null")&&!loaitram.equals("ALL"))
			{
				loaitram=loaitram.trim();
			}
			else
			{
				loaitram="";
			}
			
			//Lay danh sach province theo user
			/*String cn="";
			List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
			String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
			cn="";
			for (String dis : provinceList) {
				provinceArray = provinceArray + cn +"\""+dis+"\"";
				cn=",";
			}
			provinceArray = provinceArray+");";
			model.addAttribute("provinceList", provinceArray);*/
			
			String cn="";
			List<String> daiVTList = sysUserAreaDAO.getDaiVTArrayList(username);
			String daiVTArray="var daiVTList = new Array(";//Danh sach district cho cobobox
			cn="";
			for (String dis : daiVTList) {
				daiVTArray = daiVTArray + cn +"\""+dis+"\"";
				cn=",";
			}
			daiVTArray = daiVTArray+");";
			model.addAttribute("daiVTList", daiVTArray);
			
			String tableName="QLDN_TRAM_TT_DIEN";
			if (type!=null&&!type.equals(""))
			{
				tableName= tableName+"_"+type.toUpperCase();
			}
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
			//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tableName);
			//url
			String url = "data.htm?idTram="+idTram+"&loaitram="+loaitram
					+"&tinh=&daiVT="+daiVT+"&thangQuyTt="+thangQuyTt+"&namTt="+namTt+"&status="+status+"&type="+type+"&username="+username;
			//Grid
			String gridData = HelpTableConfigs.getGridPagingUrl(configList, "gridData", url, "listData", listSource, "Menu", null, "multiplerowsextended","Y");
			model.addAttribute("gridData", gridData);	
					
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = tableName+ dateNow;
			model.addAttribute("exportFileName", exportFileName);
			
			model.addAttribute("idTram", idTram);
			model.addAttribute("loaitram", loaitram);
			//model.addAttribute("tinh", tinh);
			model.addAttribute("daiVT", daiVT);
			model.addAttribute("thangQuyTt", thangQuyTt);
			model.addAttribute("namTt", namTt);
			model.addAttribute("status", status);
			model.addAttribute("type", type);
			return new ModelAndView("jspQLDN/tramTTDienList");
		}

		@RequestMapping("data")
		public @ResponseBody 
		void dataSuccessList(
				@RequestParam(required = false) String idTram,
				@RequestParam(required = false) String loaitram,
				@RequestParam(required = false) String tinh,
				@RequestParam(required = false) String daiVT,
				@RequestParam(required = false) String thangQuyTt,
				@RequestParam(required = false) String namTt,
				@RequestParam(required = false) String status,
				@RequestParam(required = false) String type,
				@RequestParam(required = false) String username,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			
			List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
			dataList = qldnTramTTDienDao.getTramTTDienList(null,null,idTram,loaitram,tinh,null,daiVT,null,thangQuyTt,namTt,null,status,null,type,username);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		 }
		
		//Xoa
		@RequestMapping(value = "delete", method = RequestMethod.GET)
		public String onDelete(@RequestParam (required = true) String idList,@RequestParam (required = false) String type,
				HttpServletRequest request, Model model) {
			try {
				String[] idArray = idList.split(",");
				for (String id : idArray) {
					qldnTramTTDienDao.deleteByPrimaryKey(Integer.parseInt(id));
				}
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			return "redirect:list.htm?type="+type;
		}
		//Them, sua
		@RequestMapping(value = "form", method = RequestMethod.GET)
		public String form(@RequestParam (required = false) String id,@RequestParam (required = false) String type,
				HttpServletRequest request, Model model) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			QldnTramTTDien qldnTramTTDien = new QldnTramTTDien();
			
			String tuNgay = "";
			String denNgay="";
			String ngayNhap="";
			String ngayTttw="";
			String ngayUnc="";
			String tgttTq="";
			String tgttTqTK="Tháng";
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int thangQuyTtTK= cal.get(Calendar.MONTH);
			int namTtTK= cal.get(Calendar.YEAR);
			
			if (id!=null&&!id.equals(""))
			{
				qldnTramTTDien = qldnTramTTDienDao.selectByPrimaryKey(Integer.parseInt(id));
				tuNgay = qldnTramTTDien.getTuNgayStr();
				denNgay = qldnTramTTDien.getDenNgayStr();
				ngayNhap = qldnTramTTDien.getNgayNhapStr();
				ngayTttw= qldnTramTTDien.getNgayTttwStr();
				ngayUnc = qldnTramTTDien.getNgayUncStr();
				tgttTq = qldnTramTTDien.getTgttTq();
				tgttTqTK= tgttTq;
				thangQuyTtTK= qldnTramTTDien.getThangQuyTt();
				namTtTK= qldnTramTTDien.getNamTt();
			}
			else
			{
				ngayNhap = df.format(cal.getTime());
				qldnTramTTDien.setNvNhap(username);
			}
			
			model.addAttribute("tuNgay",tuNgay);
			model.addAttribute("denNgay", denNgay);
			model.addAttribute("ngayNhap", ngayNhap);
			model.addAttribute("ngayTttw",ngayTttw);
			model.addAttribute("ngayUnc", ngayUnc);
			model.addAttribute("tgttTq", tgttTq);
			model.addAttribute("type", type);
			model.addAttribute("qldnTramTTDien", qldnTramTTDien);
			model.addAttribute("username", username);
			
			model.addAttribute("tgttTqTK", tgttTqTK);
			model.addAttribute("thangQuyTtTK", thangQuyTtTK);
			model.addAttribute("namTtTK", namTtTK);
			return "jspQLDN/tramTTDienFormHCM";
		}
						
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("qldnTramTTDien") @Valid QldnTramTTDien qldnTramTTDien,BindingResult result,
			@RequestParam (required = true) String type, 
			@RequestParam(required = false) String tuNgay,
			@RequestParam(required = false) String denNgay,
			@RequestParam(required = false) String ngayNhap,
			@RequestParam(required = false) String ngayTttw,
			@RequestParam(required = false) String ngayUnc,
			@RequestParam(required = false) String tgttTqTK,
			@RequestParam(required = false) String thangQuyTtTK,
			@RequestParam(required = false) String namTtTK,
			ModelMap model, HttpServletRequest request) throws ParseException {
					
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					
				boolean bool=true;
				if ((tuNgay==null&&type!=null&&!type.equalsIgnoreCase("tw")) || (tuNgay!=null && (tuNgay.equals("")||DateTools.isValid("dd/MM/yyyy", tuNgay)==false)))
				{
					model.addAttribute("errortuNgay", "*");
					bool= false;
				}
				if (denNgay!=null && (denNgay.equals("")||DateTools.isValid("dd/MM/yyyy", denNgay)==false))
				{
					model.addAttribute("errordenNgay", "*");
					bool= false;
				}
				if ( ngayNhap!=null && (ngayNhap.equals("")||DateTools.isValid("dd/MM/yyyy", ngayNhap)==false))
				{
					model.addAttribute("errorngayNhap", "*");
					bool= false;
				}
				if (ngayTttw!=null && (ngayTttw.equals("")||DateTools.isValid("dd/MM/yyyy", ngayTttw)==false))
				{
					model.addAttribute("errorngayTttw", "*");
					bool= false;
				}
				if (ngayUnc!=null && (ngayUnc.equals("")||DateTools.isValid("dd/MM/yyyy", ngayUnc)==false))
				{
					model.addAttribute("errorngayUnc", "*");
					bool= false;
				}
				if (qldnTramTTDien.getIdTram()==null)
				{
					model.addAttribute("erroridTram", "*");
					bool= false;
				}
			
					if (result.hasErrors() || bool== false) {
						saveMessageKey(request, "alarmExtend.errorField");
						model.addAttribute("tuNgay",tuNgay);
						model.addAttribute("denNgay", denNgay);
						model.addAttribute("ngayNhap", ngayNhap);
						model.addAttribute("ngayTttw",ngayTttw);
						model.addAttribute("ngayUnc", ngayUnc);
						
						model.addAttribute("tgttTqTK",tgttTqTK);
						model.addAttribute("thangQuyTtTK", thangQuyTtTK);
						model.addAttribute("namTtTK", namTtTK);
						
						model.addAttribute("type", type);
						model.addAttribute("qldnTramTTDien", qldnTramTTDien);
						
						return "jspQLDN/tramTTDienFormHCM";
					}
					else
					{
						qldnTramTTDien.setTgttTq("Tháng");
						qldnTramTTDien.setNvNhap(username);
						
						if (tuNgay != null && !tuNgay.equals("")) {
							qldnTramTTDien.setTuNgay(df.parse(tuNgay));
						}
						if (denNgay != null && !denNgay.equals("")) {
							qldnTramTTDien.setDenNgay(df.parse(denNgay));
							/*System.out.println(denNgay.substring(3, 5));
							System.out.println(denNgay.substring(6));*/
							qldnTramTTDien.setThangQuyTt(Integer.parseInt(denNgay.substring(3, 5)));
							qldnTramTTDien.setNamTt(Integer.parseInt(denNgay.substring(6)));
						}
						if (ngayNhap != null && !ngayNhap.equals("")) {
							qldnTramTTDien.setNgayNhap(df.parse(ngayNhap));
						}
						if (ngayTttw != null && !ngayTttw.equals("")) {
							qldnTramTTDien.setNgayTttw(df.parse(ngayTttw));
						}
						if (ngayUnc != null && !ngayUnc.equals("")) {
							qldnTramTTDien.setNgayUnc(df.parse(ngayUnc));
						}
						long dienNangTt=0;
						if (qldnTramTTDien.getCsm1()!=null)
        				{
        					dienNangTt=dienNangTt+ qldnTramTTDien.getCsm1();
        				}
						if (qldnTramTTDien.getCsm2()!=null)
        				{
        					dienNangTt=dienNangTt+ qldnTramTTDien.getCsm2();
        				}
						if (qldnTramTTDien.getCsm3()!=null)
        				{
        					dienNangTt=dienNangTt+ qldnTramTTDien.getCsm3();
        				}
        				
        				if (qldnTramTTDien.getCsc1()!=null)
        				{
        					dienNangTt=dienNangTt- qldnTramTTDien.getCsc1();
        				}
        				if (qldnTramTTDien.getCsc2()!=null)
        				{
        					dienNangTt=dienNangTt- qldnTramTTDien.getCsc2();
        				}
        				if (qldnTramTTDien.getCsc3()!=null)
        				{
        					dienNangTt=dienNangTt- qldnTramTTDien.getCsc3();
        				}
        				
        				if (dienNangTt < 0)
        				{
        					dienNangTt = dienNangTt+100000;
        				}
        				qldnTramTTDien.setDienNangTt(dienNangTt);
        				
						Integer thangTruoc;
        				Integer namTruoc;
        				if (qldnTramTTDien.getThangQuyTt()==1) {
        					thangTruoc= 12;
        					namTruoc = qldnTramTTDien.getNamTt() -1;
        				}
        				else
        				{
        					thangTruoc= qldnTramTTDien.getThangQuyTt() -1;
        					namTruoc = qldnTramTTDien.getNamTt();
        				}
        				QldnTramTTDien ttThangTruoc = qldnTramTTDienDao.getTramTTDien(qldnTramTTDien.getIdTram(),qldnTramTTDien.getTgttTq(),thangTruoc.toString(),namTruoc.toString());
            			if (ttThangTruoc!=null)
            			{
            				if (ttThangTruoc.getDienNangTt()==null)
            					ttThangTruoc.setDienNangTt(Long.parseLong("0"));
            				if (ttThangTruoc.getTienTt()==null)
            					ttThangTruoc.setTienTt(Long.parseLong("0"));
            				if (qldnTramTTDien.getDienNangTt()==null)
            					qldnTramTTDien.setDienNangTt(Long.parseLong("0"));
            				if (qldnTramTTDien.getTienTt()==null)
            					qldnTramTTDien.setTienTt(Long.parseLong("0"));
            				
            				Long chenhLechDn = qldnTramTTDien.getDienNangTt() - ttThangTruoc.getDienNangTt();
            				qldnTramTTDien.setChenhLechDn(chenhLechDn);
            				Long chenhLechTien = qldnTramTTDien.getTienTt() - ttThangTruoc.getTienTt();
            				qldnTramTTDien.setChenhLechTien(chenhLechTien);
            			}
						
						if (qldnTramTTDien.getId()!=null)
						{
							//sua chua
							qldnTramTTDien.setNgaySua(new Date());
							qldnTramTTDien.setNvSua(username);
							if (type.equalsIgnoreCase("tw")){
								qldnTramTTDienDao.updateByPrimaryKeySelective(qldnTramTTDien);
							}
							else
							{
							 qldnTramTTDienDao.updateByPrimaryKey(qldnTramTTDien);
							}
							saveMessageKey(request, "alarmExtend.updateSuccelfull");	
							
						}
						else
						{
							qldnTramTTDien.setNgayNhap(new Date());
							qldnTramTTDienDao.insert(qldnTramTTDien);
							saveMessageKey(request, "alarmExtend.insertSucceFull");
						}
					}
					return "redirect:list.htm?type="+type;
				}
				
				@RequestMapping("exportData")
				public ModelAndView exportData(
						@RequestParam(required = false) String idTram,
						@RequestParam(required = false) String loaitram,
						@RequestParam(required = false) String tinh,
						@RequestParam(required = false) String daiVT,
						@RequestParam(required = false) String thangQuyTt,
						@RequestParam(required = false) String namTt,
						@RequestParam(required = false) String status,
						@RequestParam(required = true) String type, HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					
					try {
						// B1: lay du lieu
						List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
						dataList = qldnTramTTDienDao.getTramTTDienList(null,null,idTram,loaitram,tinh,null,daiVT,null,thangQuyTt,namTt,null,status,null,type,username);
						List<Object> dataObjs = new ArrayList<Object>();
						for (QldnTramTTDien record : dataList) {
							dataObjs.add(record);
						}
						// B2 thuc hien export
						String tableName="QLDN_TRAM_TT_DIEN";
						if (type!=null&&!type.equals(""))
						{
							tableName= tableName+"_"+type.toUpperCase();
						}
						List<CTableConfig> headers = cTableConfigDAO.getColumnList(tableName);
						ExportTools.exportFileExcel("QldnTramTTDien", dataObjs, headers, request, response);

					} catch (Exception exp) {
						exp.printStackTrace();
					}

					return null;

				}
}
