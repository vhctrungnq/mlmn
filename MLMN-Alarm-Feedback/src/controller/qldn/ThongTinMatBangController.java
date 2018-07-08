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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.QldnQlMatBangDAO;
import vo.CTableConfig;
import vo.HProvincesCode;
import vo.QldnQlMatBang;
import vo.alarm.utils.ExportTools;

@Controller
@RequestMapping("/mat-bang/report/*")
public class ThongTinMatBangController {
    @Autowired
    private QldnQlMatBangDAO qldnQlMatBangDAO;
    @Autowired
    private CTableConfigDAO cTableConfigDAO;
    @Autowired
    private HProvincesCodeDAO hProvincesCodeDAO;
    private SimpleDateFormat dff = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final String MESSAGES_KEY = "successMessagesKey";
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void saveMessageKey(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute(MESSAGES_KEY, messages);
	}
    
    // DoaiNH viết quản lí thông tin trạm ngày 29-09-2017
    @RequestMapping("thong-tin-mat-bang")
    public ModelAndView listQuanLiThongTinMatBang(
	    @RequestParam(required = false) String maTram,
	    @RequestParam(required = false) String idHopdong,
	    @RequestParam(required = false) String sohd,
	    @RequestParam(required = false) String tinh,
	    @RequestParam(required = false) String huyen,
	    @RequestParam(required = false) String htSohuu,
	    @RequestParam(required = false) String dvSohuu,
	    @RequestParam(required = false) String chuthehd,
	    @RequestParam(required = false) String dongiathangNovat,
	    @RequestParam(required = false) String hdNgayketthuc,
	    @RequestParam(required = false) String ngaytinhtien,
	    Model model, HttpServletRequest request, HttpServletResponse response){
	
	
	model.addAttribute("maTram", maTram);
	model.addAttribute("idHopdong", idHopdong);
	model.addAttribute("sohd", sohd);
	model.addAttribute("tinh", tinh);
	model.addAttribute("huyen", huyen);  
	model.addAttribute("htSohuu", htSohuu); 
	model.addAttribute("dvSohuu", dvSohuu); 
	model.addAttribute("chuthehd", chuthehd); 
	model.addAttribute("dongiathangNovat", dongiathangNovat); 
	model.addAttribute("hdNgayketthuc", hdNgayketthuc); 
	model.addAttribute("ngaytinhtien", ngaytinhtien); 
	
	List<HProvincesCode> hProvincesCodeList = hProvincesCodeDAO.getDistrictByProvince(null, null);
	model.addAttribute("hProvincesCodeList", hProvincesCodeList); 
	return new ModelAndView("jspQLDN/thongTinMatBang");
	
	
    }
    @RequestMapping("dataTTMatBang")
    public void dataTTMatBang(  
	    @RequestParam(required = false) String maTram,
	    @RequestParam(required = false) String idHopdong,
	    @RequestParam(required = false) String sohd,
	    @RequestParam(required = false) String tinh,
	    @RequestParam(required = false) String huyen,
	    @RequestParam(required = false) String htSohuu,
	    @RequestParam(required = false) String dvSohuu,
	    @RequestParam(required = false) String chuthehd,
	    @RequestParam(required = false) String dongiathangNovat,
	    @RequestParam(required = false) String hdNgayketthuc,
	    @RequestParam(required = false) String ngaytinhtien,
	    Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
    	if(maTram == null) {
    	    maTram = "";
    	}
    	if(idHopdong == null ) {
    	    idHopdong = "";
    	}
    	if(sohd == null) {
    	    sohd = "";
    	}
    	if(tinh == null ) {
    	    tinh = "";
    	}
    	if(huyen == null) {
    	    huyen = "";
    	}
    	if(htSohuu == null) {
    	    htSohuu = "";
    	}
    	if(dvSohuu == null) {
    	    dvSohuu = "";
    	}
    	if (chuthehd == null) {
    	    chuthehd = "";
    	}
    	if (dongiathangNovat == null) {
    	    dongiathangNovat = "";
    	}
    	if (hdNgayketthuc == null) {
    	    hdNgayketthuc = "";
    	}
    	if (ngaytinhtien == null) {
    	    ngaytinhtien = "";
    	}
	List<QldnQlMatBang> dataList = new ArrayList<QldnQlMatBang>();
	dataList =qldnQlMatBangDAO.getTTMatBangList(maTram, idHopdong, sohd, tinh, huyen,
		htSohuu, dvSohuu, chuthehd, dongiathangNovat,hdNgayketthuc,ngaytinhtien);
	Gson gs = new Gson();
	String jsonDataList = gs.toJson(dataList);
	PrintWriter out;
	out = response.getWriter();
	response.setContentType("application/json");
	out.println(jsonDataList);
	out.close();
    }
    //lấy thong tin mat bang
    @RequestMapping(value = "formTTMB", method = RequestMethod.GET)
    public String editTTMN(   @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request){
	QldnQlMatBang record = new QldnQlMatBang();
	
	record = (id == null) ? new QldnQlMatBang() : qldnQlMatBangDAO.selectByPrimaryKey(Integer.parseInt(id));
	model.addAttribute("record", record);
	model.addAttribute("maTram",record.getMaTram());
	model.addAttribute("idHopdong",record.getIdHopdong());
	model.addAttribute("sohd",record.getSohd());
	model.addAttribute("tinh",record.getTinh());
	model.addAttribute("huyen",record.getHuyen());
	model.addAttribute("htSohuu",record.getHtSohuu());
	model.addAttribute("tenDonViSoHuu",record.getTenDonViSoHuu());
	model.addAttribute("chukytt",record.getChukytt());
	model.addAttribute("ghiChu",record.getGhiChu());
	model.addAttribute("chuthehd",record.getChuthehd());
	model.addAttribute("nguonBTS",record.getNguonBTS());
	model.addAttribute("dongiathangNovat",record.getDongiathangNovat());
	model.addAttribute("ttVat",record.getTtVat());
	model.addAttribute("hdDongiathangVat",record.getHdDongiathangVat());
	model.addAttribute("ttSotiencktt",record.getTtSotiencktt());
	model.addAttribute("sotienggNam",record.getSotienggNam());
	model.addAttribute("tongTienHD",record.getTongTienHD());
	model.addAttribute("nguoiTao",record.getNguoiTao());
	if (record.getNgayps() != null) {
	    model.addAttribute("ngayphatsong", dff.format(record.getNgayps()));
	}
	if (record.getNgayThanhToan() != null) {
	    model.addAttribute("ngaythanhtoan", dff.format(record.getNgayThanhToan()));
	}
	if (record.getHdNgayhieuluc() != null) {
	    model.addAttribute("Ngayhieuluc", dff.format(record.getHdNgayhieuluc()));
	}
	if (record.getHdNgayketthuc() != null) {
	    model.addAttribute("Ngayketthuc", dff.format(record.getHdNgayketthuc()));
	}
	if (record.getHdNgayhethan() != null) {
	    model.addAttribute("Ngayhethan", dff.format(record.getHdNgayhethan()));
	}
	
	return "jspQLDN/themThongTinMatBang";
    }
    //thêm, sửa thông tin 
    @RequestMapping(value = "formTTMB", method = RequestMethod.POST)
    public String addTTMB(
	    @ModelAttribute("record") @Valid QldnQlMatBang record, 
	    BindingResult result,
	    @RequestParam(required = false) String maTram,
	    	@RequestParam(required = false) String idHopdong,
	    	@RequestParam(required = false) String sohd,
	    	@RequestParam(required = false) String tinh,
	    	@RequestParam(required = false) String huyen,
		@RequestParam(required = false) String htSohuu,
		@RequestParam(required = false) String tenDonViSoHuu,
		@RequestParam(required = false) String ngayphatsong,
		@RequestParam(required = false) String ngaythanhtoan,
		@RequestParam(required = false) String chukytt,
		@RequestParam(required = false) String Ngayhieuluc,
		@RequestParam(required = false) String Ngayketthuc,
		@RequestParam(required = false) String ghiChu,
		@RequestParam(required = false) String Ngayhethan,
		@RequestParam(required = false) String chuthehd,
		@RequestParam(required = false) String nguonBTS,
		@RequestParam(required = false) String dongiathangNovat,
		@RequestParam(required = false) String ttVat,
		@RequestParam(required = false) String hdDongiathangVat,
		@RequestParam(required = false) String ttSotiencktt,
		@RequestParam(required = false) String sotienggNam,
		@RequestParam(required = false) String tongTienHD,
		@RequestParam(required = false) String nguoiTao,
	    ModelMap model, HttpServletRequest request
	    	
	   ) {
	Date p_ngayThanhToan = null;
	Date p_hdNgayhieuluc =  null;
	Date p_hdNgayketthuc =  null;
	Date p_hdNgayhethan =  null;
	Date p_ngayps =  null;
	
	try {
	    if(Ngayhieuluc != null && !Ngayhieuluc.equals("") ){
    	    	p_hdNgayhieuluc =  dff.parse(Ngayhieuluc);
    	    	record.setHdNgayhieuluc(p_hdNgayhieuluc);
	    }if(Ngayketthuc != null && !Ngayketthuc.equals("") ) {
		 p_hdNgayketthuc =  dff.parse(Ngayketthuc);
		 record.setHdNgayketthuc(p_hdNgayketthuc);
	    }if(ngaythanhtoan != null && !ngaythanhtoan.equals("")) {
		p_ngayThanhToan = dff.parse(ngaythanhtoan);
		record.setNgayThanhToan(p_ngayThanhToan);
	    }if(Ngayhethan != null && !Ngayhethan.equals("")) {
		p_hdNgayhethan =  dff.parse(Ngayhethan);
		record.setHdNgayhethan(p_hdNgayhethan);
	    }if(ngayphatsong != null && !ngayphatsong.equals("")) {
		p_ngayps =  dff.parse(ngayphatsong);	   
		record.setNgayps(p_ngayps);
	    }
	    
	} catch (ParseException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	model.addAttribute("record", record);
	model.addAttribute("maTram", maTram);
	model.addAttribute("idHopdong", idHopdong);
	model.addAttribute("sohd", sohd);
	model.addAttribute("tinh", tinh);
	model.addAttribute("huyen", huyen);
	model.addAttribute("htSohuu", htSohuu);
	model.addAttribute("tenDonViSoHuu", tenDonViSoHuu);
	model.addAttribute("ngayphatsong", p_ngayps);
	model.addAttribute("ngayThanhToan", p_ngayThanhToan);
	model.addAttribute("chukytt", chukytt);
	model.addAttribute("Ngayhieuluc", p_hdNgayhieuluc);
	model.addAttribute("Ngayketthuc", p_hdNgayketthuc);
	model.addAttribute("chuthehd", chuthehd);
	model.addAttribute("ghiChu", ghiChu);
	model.addAttribute("Ngayhethan", p_hdNgayhethan);
	model.addAttribute("nguonBTS", nguonBTS);
	model.addAttribute("dongiathangNovat", dongiathangNovat);
	model.addAttribute("ttVat", ttVat);
	model.addAttribute("hdDongiathangVat", hdDongiathangVat);
	model.addAttribute("ttSotiencktt", ttSotiencktt);
	model.addAttribute("sotienggNam", sotienggNam);
	model.addAttribute("tongTienHD", tongTienHD);
	model.addAttribute("nguoiTao", nguoiTao);
	
	try {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    if(result.hasErrors()) {
		saveMessageKey(request, "Thiếu thông tin");
		return "jspQLDN/themThongTinMatBang";
	    }else {
		
		if(record.getId() == null) {
		    /*record.setNguoiTao(username);
		    record.setNgayTao(new Date());*/
		    qldnQlMatBangDAO.insert(record);
		    saveMessageKey(request, "messsage.confirm.addsuccess");
		}else {
		    /*record.setNguoiSua(username);
		    record.setNgaySua(new Date());*/
		    qldnQlMatBangDAO.updateByPrimaryKey(record);
		    saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
	   }
    
	    }catch (Exception e) {
		return "jspQLDN/themThongTinMatBang";
	}
	model.clear();
	return "redirect:thong-tin-mat-bang.htm" ;
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id, HttpServletRequest request, HttpServletResponse response) {
		String [] del = id.split(",");
		try{
		    for(int i = 0; i < del.length; i++) {
			qldnQlMatBangDAO.deleteByPrimaryKey(Integer.parseInt(del[i]));
		    }
		     saveMessageKey(request, "messsage.confirm.deletesuccess");
		}catch (Exception e) {
		    saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:thong-tin-mat-bang.htm" ;
	}
    @RequestMapping("exportData_Export_TT_Mat_Bang")
    public ModelAndView exportData(
	    @RequestParam(required = false) String maTram,
	    @RequestParam(required = false) String idHopdong,
	    @RequestParam(required = false) String sohd,
	    @RequestParam(required = false) String tinh,
	    @RequestParam(required = false) String huyen,
	    @RequestParam(required = false) String htSohuu,
	    @RequestParam(required = false) String dvSohuu,
	    @RequestParam(required = false) String chuthehd,
	    @RequestParam(required = false) String dongiathangNovat,
	    @RequestParam(required = false) String hdNgayketthuc,
	    @RequestParam(required = false) String ngaytinhtien,
	     HttpServletRequest request, HttpServletResponse response)
      throws Exception {
     
     try {
    
      // B1: lay du lieu
         List<QldnQlMatBang> dataList = new ArrayList<QldnQlMatBang>();
       
     	dataList =qldnQlMatBangDAO.getTTMatBangList(maTram, idHopdong, sohd, tinh, huyen,
		htSohuu, dvSohuu, chuthehd, dongiathangNovat,hdNgayketthuc,ngaytinhtien);
     	
     	 List<Object> dataObjs = new ArrayList<Object>();
             for (QldnQlMatBang record : dataList) {
              dataObjs.add(record);
             }
             // B2 thuc hien export
             List<CTableConfig> headers = cTableConfigDAO.getColumnList("QLDN_THONG_TIN_MAT_BANG_REPORT");
             ExportTools.exportFileExcel("QldnThongTinMatBang", dataObjs, headers, request, response);
     
     } catch (Exception exp) {
      exp.printStackTrace();
     }

     return null;

    }
}
