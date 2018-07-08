package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
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
import dao.QldnThongTinMayNoDAO;
import vo.CTableConfig;
import vo.QldnThongTinMayNo;
import vo.QldnTramTTDien;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/thong-tin-may-no/*")
public class ThongTinMayNoController {
    @Autowired
    private QldnThongTinMayNoDAO qldnThongTinMayNoDAO;
    @Autowired
    private CTableConfigDAO cTableConfigDAO;
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
//DoaiNH viet chức năng quản lí thông tin máy nổ ngày 09-10-2017
    @RequestMapping("list")
    public ModelAndView listThongTinMayNo(
    	@RequestParam(required = false) String idTram,
    	@RequestParam(required = false) String idDtxd,
    	@RequestParam(required = false) String loaiThanhToan,
    	@RequestParam(required = false) String tenTram,
    	@RequestParam(required = false) String tinh,
    	@RequestParam(required = false) String to,
    	@RequestParam(required = false) String nhom,
		@RequestParam(required = false) String tenXhhVtt,
		@RequestParam(required = false) String hieuMayNo,
		@RequestParam(required = false) String congSuat,
		@RequestParam(required = false) String dinhMuc,
		@RequestParam(required = false) String loaiNhienLieu,
		@RequestParam(required = false) String ats,
	Model model, HttpServletRequest request, HttpServletResponse response){
    	 if(idTram == null) {
             idTram = "";
         }
 	if(idDtxd == null ) {
 	    idDtxd = "";
 	}
         if(loaiThanhToan == null) {
             loaiThanhToan = "";
         }
 	if(tenTram == null ) {
 	    tenTram = "";
 	}
 	if(tinh == null) {
 	    tinh = "";
         }
 	if(to == null) {
 	    to = "";
         }
 	if(nhom == null ) {
 	    nhom = "";
 	}
         if(tenXhhVtt == null) {
             tenXhhVtt = "";
         }
 	if(hieuMayNo == null ) {
 	    hieuMayNo = "";
 	}
 	if(congSuat == null) {
 	    congSuat = "";
         }
 	if(dinhMuc == null) {
 	    dinhMuc = "";
         }
 	if(loaiNhienLieu == null ) {
 	    loaiNhienLieu = "";
 	}
         if(ats == null) {
             ats = "";
         }
    	//do du lieu ra grid
         String tableName="QLDN_THONG_TIN_MAY_NO";
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tableName);
		//url
		String url = "dataThongTinMayNo.htm?idTram="+idTram+"&idDtxd="+idDtxd+"&loaiThanhToan="+loaiThanhToan+
						"&tenTram="+tenTram+"&tinh="+tinh+"&to="+to+"&nhom="+nhom+"&tenXhhVtt="+tenXhhVtt+
						"&hieuMayNo="+hieuMayNo+"&congSuat="+congSuat+"&dinhMuc="+dinhMuc+"&loaiNhienLieu="+loaiNhienLieu+"&ats="+ats;
		//Grid
		String gridData = HelpTableConfigs.getGridPagingUrl(configList, "gridData", url, "listData", listSource, "Menu", null, "multiplerowsextended","Y");
		model.addAttribute("gridData", gridData);
		
	    model.addAttribute("idTram", idTram);
	    model.addAttribute("idDtxd", idDtxd);
	    model.addAttribute("loaiThanhToan", loaiThanhToan);
	    model.addAttribute("tenTram", tenTram);
	    model.addAttribute("tinh", tinh);
	    model.addAttribute("to", to);
	    model.addAttribute("nhom", nhom);
	    model.addAttribute("tenXhhVtt", tenXhhVtt);
	    model.addAttribute("hieuMayNo", hieuMayNo);
	    model.addAttribute("congSuat", congSuat);
	    model.addAttribute("dinhMuc", dinhMuc);
	    model.addAttribute("loaiNhienLieu", loaiNhienLieu);
	    model.addAttribute("ats", ats);
	 
	return new ModelAndView("jspQLDN/thongTinMayNo");
	
	
    }
    @RequestMapping("dataThongTinMayNo")
    public void dataTTMayNo( 
	    @RequestParam(required = false) String idTram,
	    	@RequestParam(required = false) String idDtxd,
	    	@RequestParam(required = false) String loaiThanhToan,
	    	@RequestParam(required = false) String tenTram,
	    	@RequestParam(required = false) String tinh,
	    	@RequestParam(required = false) String to,
	    	@RequestParam(required = false) String nhom,
		@RequestParam(required = false) String tenXhhVtt,
		@RequestParam(required = false) String hieuMayNo,
		@RequestParam(required = false) String congSuat,
		@RequestParam(required = false) String dinhMuc,
		@RequestParam(required = false) String loaiNhienLieu,
		@RequestParam(required = false) String ats,
	    Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {
        
        List<QldnThongTinMayNo> dataList = new ArrayList<QldnThongTinMayNo>();
       
    	dataList =qldnThongTinMayNoDAO.getThongTinMayNoList(idTram, idDtxd, loaiThanhToan,tenTram,tinh,to, nhom, tenXhhVtt, hieuMayNo,congSuat,dinhMuc,loaiNhienLieu,ats );
    	Gson gs = new Gson();
		String jsonDataList = gs.toJson(dataList);
		PrintWriter out;
		out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonDataList);
		out.close();
    }
  //lấy thong tin may no
    @RequestMapping(value = "formTTMN", method = RequestMethod.GET)
    public String editTTMN(   @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request){
	QldnThongTinMayNo record = new QldnThongTinMayNo();
	
	record = (id == null) ? new QldnThongTinMayNo() : qldnThongTinMayNoDAO.selectByPrimaryKey(Integer.parseInt(id));
	model.addAttribute("record", record);
	
	model.addAttribute("loaiThanhToan",record.getLoaiThanhToan());
	model.addAttribute("loaiNhienLieu",record.getLoaiNhienLieu());
	model.addAttribute("ats",record.getAts());
	
	return "jspQLDN/themThongTinMayNo";
    }
    //thêm, sửa thông tin máy nổ
    @RequestMapping(value = "formTTMN", method = RequestMethod.POST)
    public String addTTMN(
	    @ModelAttribute("record") @Valid QldnThongTinMayNo record, 
	    BindingResult result,
	    ModelMap model, HttpServletRequest request,
	    	@RequestParam(required = false) String loaiThanhToan,
	    	@RequestParam(required = false) String loaiNhienLieu,
	    	@RequestParam(required = false) String ats
	   ) {
	try {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    record.setLoaiNhienLieu(loaiNhienLieu);
	    record.setLoaiThanhToan(loaiThanhToan);
	    record.setAts(ats);
	    if(result.hasErrors()) {
			saveMessageKey(request, "Thiếu thông tin");
			model.addAttribute("record", record);
			model.addAttribute("loaiThanhToan", loaiThanhToan);
			model.addAttribute("loaiNhienLieu", loaiNhienLieu);
			model.addAttribute("ats", ats);
			return "jspQLDN/themThongTinMayNo";
	    }else {
		if(record.getId() == null) {
		    record.setNguoiTao(username);
		    record.setNgayTao(new Date());
		    qldnThongTinMayNoDAO.insert(record);
		    saveMessageKey(request, "messsage.confirm.addsuccess");
		}else {
		    record.setNguoiSua(username);
		    record.setNgaySua(new Date());
		    qldnThongTinMayNoDAO.updateByPrimaryKey(record);
		    saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
	    }
	} catch (Exception e) {
	    model.addAttribute("record", record);
		model.addAttribute("loaiThanhToan", loaiThanhToan);
		model.addAttribute("loaiNhienLieu", loaiNhienLieu);
		model.addAttribute("ats", ats);
		return "jspQLDN/themThongTinMayNo";
	}

	model.clear();
	return "redirect:list.htm" ;
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id, HttpServletRequest request, HttpServletResponse response) {
		String [] del = id.split(",");
		try{
		    for(int i = 0; i < del.length; i++) {
			qldnThongTinMayNoDAO.deleteByPrimaryKey(Integer.parseInt(del[i]));
		    }
		     saveMessageKey(request, "messsage.confirm.deletesuccess");
		}catch (Exception e) {
		    saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
    @RequestMapping("exportData_Export_TT_MayNo")
    public ModelAndView exportData(
	    @RequestParam(required = false) String idTram,
	    	@RequestParam(required = false) String idDtxd,
	    	@RequestParam(required = false) String loaiThanhToan,
	    	@RequestParam(required = false) String tenTram,
	    	@RequestParam(required = false) String tinh,
	    	@RequestParam(required = false) String to,
	    	@RequestParam(required = false) String nhom,
		@RequestParam(required = false) String tenXhhVtt,
		@RequestParam(required = false) String hieuMayNo,
		@RequestParam(required = false) String congSuat,
		@RequestParam(required = false) String dinhMuc,
		@RequestParam(required = false) String loaiNhienLieu,
		@RequestParam(required = false) String ats,
	     HttpServletRequest request, HttpServletResponse response)
      throws Exception {
     
     try {
    
      // B1: lay du lieu
         List<QldnThongTinMayNo> dataList = new ArrayList<QldnThongTinMayNo>();
       
     	dataList =qldnThongTinMayNoDAO.getThongTinMayNoList(idTram, idDtxd, loaiThanhToan,tenTram,tinh,to, nhom, tenXhhVtt, hieuMayNo,congSuat,dinhMuc,loaiNhienLieu,ats );
     	
     	 List<Object> dataObjs = new ArrayList<Object>();
             for (QldnThongTinMayNo record : dataList) {
              dataObjs.add(record);
             }
             // B2 thuc hien export
             List<CTableConfig> headers = cTableConfigDAO.getColumnList("QLDN_THONG_TIN_MAY_NO_REPORT");
             ExportTools.exportFileExcel("QldnThongTinMayNo", dataObjs, headers, request, response);
     
     } catch (Exception exp) {
      exp.printStackTrace();
     }

     return null;

    }
}
