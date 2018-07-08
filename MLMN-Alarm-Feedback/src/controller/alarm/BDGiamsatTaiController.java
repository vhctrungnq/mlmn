package controller.alarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.HBsc;
import vo.VRpHrEBscLoad3gOnline;
import vo.VRpHrEBscLoadOnline;
import vo.VRpHrNLoadOnline;
import controller.BaseController;
import dao.HBscDAO;
import dao.VRpHrCellCssrDAO;
import dao.VRpHrEBscLoadOnlineDAO;

@Controller
@RequestMapping("/alarm/giam-sat-tai/*")
public class BDGiamsatTaiController  extends BaseController{

	@Autowired
	private HBscDAO hBscDao;
	@Autowired
	private VRpHrEBscLoadOnlineDAO hrEBscLoadOnlineDAO; 
	@Autowired
	private VRpHrCellCssrDAO vRpHrCellCssrDAO;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm") ; 
	@RequestMapping(value = "{function}")
    public String list(  @RequestParam(required = false) String startDate,
						 @RequestParam(required = false) String endDate,
						 @RequestParam(required = false) String neid,
						 @RequestParam(required = false) String cardType,
						 @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		List <VRpHrEBscLoad3gOnline> vRpHrEBscLoad3gList;
		List <VRpHrEBscLoadOnline> vRpHrEBscLoadList;
		List <VRpHrNLoadOnline> vRpHrNloadList; 
		Date edate, sdate ;
		if (endDate == null) {
			edate = new Date();
			endDate = df_full.format(edate).toString();
		}
		if (startDate == null) {
			DateTime dt = new DateTime();
			sdate =   dt.minusHours(24).toDate();
			startDate = df_full.format(sdate).toString();
		} 
		if(neid==null)
			neid = "";
		if(cardType == null)
			cardType = "";
		
		
		 
		List<HBsc>  bscList = hBscDao.getBscListByVendorAndNetwork(function); 
		if(function.equals("ericsson-load-2g"))
		{ 
			vRpHrEBscLoadList = hrEBscLoadOnlineDAO.filterEricsson(function,startDate,endDate,neid,cardType);
			model.addAttribute("vRpHrEBscLoadList", vRpHrEBscLoadList);
			model.addAttribute("objectname", "Int");
		}
		else if(function.equals("ericsson-load-3g"))
		{ 
			vRpHrEBscLoad3gList = hrEBscLoadOnlineDAO.filter3G(function,startDate,endDate,neid,cardType);
			model.addAttribute("vRpHrEBscLoad3gList", vRpHrEBscLoad3gList);
			model.addAttribute("objectname", "Object");
		}
		else if(function.equals("nokia-load"))
		{ 
			vRpHrNloadList  =      hrEBscLoadOnlineDAO.filterNokia(function,startDate,endDate,neid,cardType);
			model.addAttribute("vRpHrNloadList", vRpHrNloadList);
			model.addAttribute("objectname", "Card type");
		}
		model.addAttribute("startDate",  startDate);
		model.addAttribute("endDate",  endDate );
		model.addAttribute("neid", neid);
		model.addAttribute("cardType", cardType);
		model.addAttribute("function", function);
		model.addAttribute("bscList", bscList);
		
		
		return "/jspalarm/giamSatTai";
	}
}