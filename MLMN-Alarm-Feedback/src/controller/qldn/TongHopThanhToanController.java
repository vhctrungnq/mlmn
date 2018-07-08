package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import dao.CTableConfigDAO;
import dao.QldnTramTTDienDAO;
import vo.CTableConfig;
import vo.QldnTramTTDien;
import vo.alarm.utils.ExportTools;

@Controller
@RequestMapping("/dien/report/*")
public class TongHopThanhToanController {
  
        
        @Autowired
      private QldnTramTTDienDAO qldnTramTTDienDAO;
        @Autowired
      private CTableConfigDAO cTableConfigDAO;
        // DoaiNH viết tong hop thanh toan ngày 29-09-2017
        @RequestMapping("tong-hop-thanh-toan")
        public ModelAndView listQuanLiThongTinTram(
        	@RequestParam(required = false) String region,
        	@RequestParam(required = false) String tinh,
        	@RequestParam(required = false) String chuKyTT,
    	    	@RequestParam(required = false) String thangQuyTt,
    	    	@RequestParam(required = false) String nam,
    	    	@RequestParam(required = true) String type,
    	    
    	    Model model, HttpServletRequest request, HttpServletResponse response){
   
    	    model.addAttribute("region", region);
    	    model.addAttribute("tinh", tinh);
    	    model.addAttribute("chuKyTT", chuKyTT);
	    model.addAttribute("thangQuyTt", thangQuyTt);
	    model.addAttribute("nam", nam);
    	    model.addAttribute("type", type);
    	 
    	return new ModelAndView("jspQLDN/reportDien/tongHopThanhToanDien");
    	
    	
        }
        @RequestMapping("dataTongHopThanhToan")
        public void dataTTNhieuKy( 
        	@RequestParam(required = false) String region,
        	@RequestParam(required = false) String tinh,
        	@RequestParam(required = false) String chuKyTT,
    	    	@RequestParam(required = false) String thangQuyTt,
    	    	@RequestParam(required = false) String nam,
    	    	@RequestParam(required = true) String type,
    	    Model model, HttpServletRequest request, HttpServletResponse response)
    	    throws IOException {
            
            List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
            if(region == null) {
        	region = "";
            }
    	    if(tinh == null ) {
    		tinh = "";
    	    }
            if(chuKyTT == null) {
        	chuKyTT = "";
            }
	    if(thangQuyTt == null ) {
		thangQuyTt = "";
	    }
	    if(nam == null) {
		nam = "";
            }
            if(type.equals("")) {
        	dataList =qldnTramTTDienDAO.getTongHopTTDienList(region, tinh, chuKyTT,thangQuyTt,nam,type );
            }else if(type.equals("TW")) {
        	
        	dataList =qldnTramTTDienDAO.getTongHopTTDienTWList(region, tinh, chuKyTT,thangQuyTt,nam,type);
            }
        Gson gs = new Gson();
    	String jsonDataList = gs.toJson(dataList);
    	PrintWriter out;
    	out = response.getWriter();
    	response.setContentType("application/json");
    	out.println(jsonDataList);
    	out.close();
        }
        @RequestMapping("exportData_Export_TT_Dien")
        public ModelAndView exportData(
        	@RequestParam(required = false) String region,
        	@RequestParam(required = false) String tinh,
        	@RequestParam(required = false) String chuKyTT,
    	    	@RequestParam(required = false) String thangQuyTt,
    	    	@RequestParam(required = false) String nam,
    	    	@RequestParam(required = false) String type,
    	    HttpServletRequest request, HttpServletResponse response)
          throws Exception {
         
         try {
        
          // B1: lay du lieu
             List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
            if(type.equals("")) {
         	dataList =qldnTramTTDienDAO.getTongHopTTDienList(region, tinh, chuKyTT,thangQuyTt,nam,type );
         	
         	 List<Object> dataObjs = new ArrayList<Object>();
                 for (QldnTramTTDien record : dataList) {
                  dataObjs.add(record);
                 }
                 // B2 thuc hien export
                 List<CTableConfig> headers = cTableConfigDAO.getColumnList("QLDN_TONG_HOP_THANH_TOAN_DIEN");
                 ExportTools.exportFileExcel("QldnTongHopThanhToanDien", dataObjs, headers, request, response);

            }else if(type.equals("TW")) {
         	dataList =qldnTramTTDienDAO.getTongHopTTDienTWList(region, tinh, chuKyTT,thangQuyTt,nam,type);
         	
         	 List<Object> dataObjs = new ArrayList<Object>();
                 for (QldnTramTTDien record : dataList) {
                  dataObjs.add(record);
                 }
                 // B2 thuc hien export
                 List<CTableConfig> headers = cTableConfigDAO.getColumnList("QLDN_TONG_HOP_THANH_TOAN_TAM_UNG");
                 ExportTools.exportFileExcel("QldnTongHopThanhToanDienTamUng", dataObjs, headers, request, response);

             }
         
         } catch (Exception exp) {
          exp.printStackTrace();
         }

         return null;

        }
}
