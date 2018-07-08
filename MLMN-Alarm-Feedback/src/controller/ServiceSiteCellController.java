package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.Province;
import vo.SYS_PARAMETER;
import vo.ServiceSiteCell;
import dao.SYS_PARAMETERDAO;
import dao.VAlHBscDAO;
import dao.VAlHCellDAO;
import dao.VAlRbLossConfigurationDAO;
@Controller
@RequestMapping("/Service/*")
public class ServiceSiteCellController  extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired 
	private VAlRbLossConfigurationDAO vAlRbLossConfigurationDAO;
	
	@Autowired 
	private VAlHBscDAO vAlHBscDAO;
	
	@Autowired 
	private VAlHCellDAO vAlHCellDAO;
	
	@RequestMapping("list")
	public String list(ServiceSiteCell serviceSiteCell,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String province,
			@RequestParam(required = true) String netWork,
			Model model, HttpServletRequest request) {
		
		
		if (serviceSiteCell.getBscid()!=null)
			serviceSiteCell.setBscid(serviceSiteCell.getBscid().trim());
		if (serviceSiteCell.getAddress()!=null)
			serviceSiteCell.setAddress(serviceSiteCell.getAddress().trim());
		serviceSiteCell.setDistrict(district);
		serviceSiteCell.setProvince(province);
		if (netWork==null)
		{
			netWork="2G";
		}
		else
			netWork = netWork.trim();
		int order = 0;
		String column = "SDATE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		model.addAttribute("serviceSiteCell", serviceSiteCell);
		model.addAttribute("district", district);
		model.addAttribute("province", province);
		model.addAttribute("netWork", netWork);
		model.addAttribute("typeNetWork", netWork);
		
		List<SYS_PARAMETER> sysParemeterTitle = vAlRbLossConfigurationDAO.titleServiceSiteCell(netWork);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}

		
		List<Province> provinceList = vAlRbLossConfigurationDAO.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		
		List<Province> districtList = vAlRbLossConfigurationDAO.getDistrictByProvince(province);
		model.addAttribute("districtList", districtList);
		
		
		List<String> bscidList = vAlRbLossConfigurationDAO.getBSCIDByDistrict(district,netWork);
		model.addAttribute("bscidList", bscidList);
		
		List<String> addressList = vAlRbLossConfigurationDAO.getAddressByDistrict(district,netWork);
		model.addAttribute("addressList", addressList);
		
		List<ServiceSiteCell> alarmList = new ArrayList<ServiceSiteCell>();
		
		try
		{
			alarmList = vAlRbLossConfigurationDAO.getLossConfigurationService(netWork,serviceSiteCell,order,column);
			
		}
		catch(Exception exp)
		{
			alarmList =null;
		}
		model.addAttribute("alarmList", alarmList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhSachMatSongSite_"+netWork+"_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		return "jsp/Service/serviceSiteCell";
	}
}
