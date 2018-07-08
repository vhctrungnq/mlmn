package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import dao.QldnDonViThuHuongDAO;
import vo.QldnDonViThuHuong;

@Controller
@RequestMapping("qldn/donvithuhuong/")
public class DonViThuHuongController {
    @Autowired
    private QldnDonViThuHuongDAO qldnDonViThuHuongDAO;

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
    @RequestMapping("list")
    public ModelAndView listDonViThuHuong(@RequestParam(required = false) String tenDv,
	    @RequestParam(required = false) String maSoThue,@RequestParam(required = false) String soTaiKhoan
	    ,@RequestParam(required = false) String tenNganHang,@RequestParam(required = false) String ghiChu,
	    Model model, HttpServletRequest request, HttpServletResponse response){
	if(tenDv ==null) {
	    tenDv = "";
	}
	if(maSoThue == null ) {
	    maSoThue = "";
	}
	if(soTaiKhoan == null) {
	    soTaiKhoan = "";
	}
	if(tenNganHang == null ) {
	    tenNganHang = "";
	}
	if(ghiChu == null) {
	    ghiChu = "";
	}
	// do du lieu ra grid
	List<QldnDonViThuHuong> listDonViThuHuong = qldnDonViThuHuongDAO.getList(tenDv, maSoThue, soTaiKhoan, tenNganHang, ghiChu );
	model.addAttribute("tenDv", tenDv);
	model.addAttribute("maSoThue", maSoThue);
	model.addAttribute("soTaiKhoan", soTaiKhoan);
	model.addAttribute("tenNganHang", tenNganHang);
	model.addAttribute("ghiChu", ghiChu);  
	
	model.addAttribute("listDonViThuHuong", listDonViThuHuong);
	Calendar currentDate = Calendar.getInstance();
	  SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	  String dateNow = formatter.format(currentDate.getTime());
	  String exportFileName = "QLDN_ĐƠN_VỊ_THỤ_HƯỞNG"+ dateNow;
	  model.addAttribute("exportFileName", exportFileName);
	return new ModelAndView("jspQLDN/donViThuHuong");
	
	
    }
    @RequestMapping("data")
    public void data(@RequestParam(required = false) String tenDv,
	    @RequestParam(required = false) String maSoThue,@RequestParam(required = false) String soTaiKhoan
	    ,@RequestParam(required = false) String tenNganHang,@RequestParam(required = false) String ghiChu,
	    Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
	
	List<QldnDonViThuHuong> dataList = new ArrayList<QldnDonViThuHuong>();
	dataList = qldnDonViThuHuongDAO.getList(tenDv, maSoThue, soTaiKhoan, tenNganHang, ghiChu );
	Gson gs = new Gson();
	String jsonDataList = gs.toJson(dataList);
	PrintWriter out;
	out = response.getWriter();
	response.setContentType("application/json");
	out.println(jsonDataList);
	out.close();
    }
    //lấy đơn vị thụ hưởng
    @RequestMapping(value = "formDVTH", method = RequestMethod.GET)
    public String edit( @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request){
	QldnDonViThuHuong record = new QldnDonViThuHuong();
	
	record = (id == null) ? new QldnDonViThuHuong() : qldnDonViThuHuongDAO.selectByPrimaryKey(Integer.parseInt(id));
	model.addAttribute("record", record);
	model.addAttribute("tenDv",record.getTenDv());
	model.addAttribute("maSoThue",record.getMaSoThue());
	model.addAttribute("soTaiKhoan",record.getSoTaiKhoan());
	model.addAttribute("tenNganHang",record.getTenNganHang());
	model.addAttribute("diaChiNh",record.getDiaChiNh());
	model.addAttribute("lienHe",record.getLienHe());
	model.addAttribute("ghiChu",record.getGhiChu());
	return "jspQLDN/themDonViThuHuong";
    }
    //thêm, sửa đơn vị thụ hưởng
    @RequestMapping(value = "formDVTH", method = RequestMethod.POST)
    public String add(
	    @ModelAttribute("record") @Valid QldnDonViThuHuong record, 
	    BindingResult result,
	    ModelMap model, HttpServletRequest request,
	    @RequestParam(required = false) String tenDv,
	    @RequestParam(required = false) String maSoThue,
	    @RequestParam(required = false) String soTaiKhoan,
	    @RequestParam(required = false) String tenNganHang,
	    @RequestParam(required = false) String diaChiNh,
	    @RequestParam(required = false) String ghiChu,
	    @RequestParam(required = false) String lienHe,
	    @RequestParam(required = false) String createdBy,
	    @RequestParam(required = false) String modifiedBy,
	    @RequestParam(required = false) String createDate,
	    @RequestParam(required = false) String modifiedDate
	   ) {
	try {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    if(result.hasErrors()) {
		saveMessageKey(request, "Thiếu thông tin");
		model.addAttribute("record", record);
		model.addAttribute("tenDv", tenDv);
		model.addAttribute("maSoThue", maSoThue);
		model.addAttribute("soTaiKhoan", soTaiKhoan);
		model.addAttribute("tenNganHang", tenNganHang);
		model.addAttribute("diaChiNh", diaChiNh);
		model.addAttribute("ghiChu", ghiChu);
		model.addAttribute("lienHe", lienHe);
		model.addAttribute("ghiChu", ghiChu);
		model.addAttribute("modifiedBy", modifiedBy);
		model.addAttribute("createDate", createDate);
		model.addAttribute("modifiedDate", modifiedDate);
		return "jspQLDN/thenDonViThuHuong";
	    }else {
		if(record.getId() == null) {
		    record.setCreatedBy(username);
		    record.setCreateDate(new Date());
		    qldnDonViThuHuongDAO.insert(record);
		    saveMessageKey(request, "messsage.confirm.addsuccess");
		}else {
		    record.setModifiedBy(username);
		    record.setModifiedDate(new Date());
		    qldnDonViThuHuongDAO.updateByPrimaryKey(record);
		    saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
	    }
	} catch (Exception e) {
	    model.addAttribute("record", record);
		model.addAttribute("tenDv", tenDv);
		model.addAttribute("maSoThue", maSoThue);
		model.addAttribute("soTaiKhoan", soTaiKhoan);
		model.addAttribute("tenNganHang", tenNganHang);
		model.addAttribute("diaChiNh", diaChiNh);
		model.addAttribute("ghiChu", ghiChu);
		model.addAttribute("lienHe", lienHe);
		model.addAttribute("ghiChu", ghiChu);
		model.addAttribute("modifiedBy", modifiedBy);
		model.addAttribute("createDate", createDate);
		model.addAttribute("modifiedDate", modifiedDate);
		return "jspQLDN/thenDonViThuHuong";
	}

	model.clear();
	return "redirect:list.htm" ;
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id, HttpServletRequest request, HttpServletResponse response) {
		String [] del = id.split(",");
		try{
		    for(int i = 0; i < del.length; i++) {
			qldnDonViThuHuongDAO.deleteByPrimaryKey(Integer.parseInt(del[i]));
		    }
		     saveMessageKey(request, "messsage.confirm.deletesuccess");
		}catch (Exception e) {
		    saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
   
	
	
}
